package com.example.snacktime;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;

import com.example.snacktime.interfaces.GameObject;

public class Game {
	private boolean isRunning = false;
	private GameLoop gameLoop;
	private static Game instance;
	public ArrayList<GameObject> gameObjects;
	
	private OnStopListener stopListener;
	
	private Game() {
		gameObjects = new ArrayList<GameObject>();
		gameLoop = new GameLoop(this);
	}
	
	public static Game getInstance() {
		if (instance == null){ 
			instance = new Game();
		}
		
		return instance;
	}
	
	public void addGameObject(GameObject object) {
		gameObjects.add(object);
	}
	
	public List<GameObject> getGameObjects() {
		return gameObjects;	
	}
	
	public boolean removeGameObject(GameObject object) {
		return gameObjects.remove(object);
	}
	
	public GameObject removeGameObject(int index) {
		return gameObjects.remove(index);
	}
	
	public void start() {
		this.isRunning = true;
		
		gameLoop.start();
	}
	
	public void stop() {
		this.isRunning = false;
		
		gameLoop.cancel(true);
	}
	
	public boolean isRunning() {
		return isRunning;
	}
	
	public void onStopListener(OnStopListener listener) {
		this.stopListener = listener;
	}
	
	public interface OnStopListener {
		public void onStop();
	}
	
	// Background Loop
	private class GameLoop extends AsyncTask<Void, Void, Void> {
		private long start;
		private long elapsedTime;
		private int fps = 0;
		private Game game;
		
		public GameLoop(Game game) {
			this.game = game;
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			long startFps = System.currentTimeMillis();
			
			while(!isCancelled()) {
				start = System.currentTimeMillis();
				
				for(final GameObject object : game.getGameObjects()) {
					object.post(new Runnable() {
	
						@Override
						public void run() {
							object.update();									
						}
					});
				}
				
				elapsedTime = System.currentTimeMillis() - start;
				
				if ((System.currentTimeMillis() - startFps) >= 1000) {
					startFps = System.currentTimeMillis();
					System.out.println("FPS: " + fps);
					fps = 0;
				} else {
					fps++;
				}
					
				
				try {
					long timeToSleep = (long) (1/60.0 * 1000 - elapsedTime);
					
					if (timeToSleep > 0)
						Thread.sleep(timeToSleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return null;
		}
		
		public void start(Void... params) {
			this.execute(params);
		}
		
		@Override
		protected void onCancelled() {
			if (stopListener != null)
				stopListener.onStop();
		}
	}
}
