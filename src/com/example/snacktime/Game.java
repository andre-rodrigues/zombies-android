package com.example.snacktime;

import java.util.ArrayList;

import android.os.AsyncTask;

public class Game {
	private boolean isRunning = false;
	private GameLoop gameLoop;
	private static Game instance;
	
	private OnStopListener stopListener;
	
	private Game() {
		gameLoop = new GameLoop();
	}
	
	public static Game getInstance() {
		if (instance == null){ 
			instance = new Game();
		}
		
		return instance;
	}
	
	public void addGameObject(GameObject object) {
		gameLoop.gameObjects.add(object);
	}
	
	public boolean removeGameObject(GameObject object) {
		return gameLoop.gameObjects.remove(object);
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
		public ArrayList<GameObject> gameObjects;
		private long start;
		private long elapsedTime;
		private int fps = 0;
		
		public GameLoop() {
			gameObjects = new ArrayList<GameObject>();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			long startFps = System.currentTimeMillis();
			
			while(!isCancelled()) {
				start = System.currentTimeMillis();
				
				for(final GameObject object : gameObjects) {
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
