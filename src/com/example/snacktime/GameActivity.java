package com.example.snacktime;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

public class GameActivity extends Activity {
	
	private ArrayList<GameObject> gameObjects;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		RelativeLayout baseView = (RelativeLayout) findViewById(R.id.layout);
		
//		layout.addView(zombie);
		
		gameObjects = new ArrayList<GameObject>();
		Zombie zombie = new Zombie(this);
		zombie.setPosition(50,  80);
		zombie.startAnimation();
		baseView.addView(zombie);
		gameObjects.add(zombie);
		
		new AsyncGameLoop(gameObjects).execute();
	}
	
	private class AsyncGameLoop extends AsyncTask<Void, GameObject, Void> {
		private ArrayList<GameObject> gameObjects;
		
		public AsyncGameLoop(ArrayList<GameObject> objects) {
			this.gameObjects = objects;
		}
		
		@Override
		protected Void doInBackground(Void... arg0) {
			long lastCycle = System.currentTimeMillis();
			int framesRunned = 0;
			while(true) {
				long elapsedTime = (System.currentTimeMillis() - lastCycle);
				
				if (framesRunned < 60 && (TimeUnit.MILLISECONDS.toSeconds(elapsedTime) < 1)) {
					for(GameObject object : gameObjects) {
//						object.update();
						publishProgress(object);
					}
					lastCycle = System.currentTimeMillis();
					framesRunned++;
				} else {
					continue;
				}
				
//				if (elapsedTime < (1/60)) {
//					continue;
//				} else {
//					for(GameObject object : gameObjects) {
////						object.update();
//						publishProgress(object);
//					}
//					lastCycle = System.currentTimeMillis();
//				}
			}
		}
		
		@Override
		protected void onProgressUpdate(GameObject... object) {
			object[0].update();
		}
	}
}
