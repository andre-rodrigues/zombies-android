package com.example.snacktime;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;

public class GameActivity extends Activity {
	RelativeLayout gameView;
	private Game game;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		game = Game.getInstance();
		
		gameView = (RelativeLayout) findViewById(R.id.layout);

		for (int i = 0; i < 100; i++) {
			Zombie zombie = new Zombie(this, 
					Math.random() * 500, 
					Math.random() * 700, 
					Math.cos(Math.random() * 50), 
					Math.sin(Math.random()) * 10);
			
			zombie.startAnimation();
			gameView.addView(zombie);
			
			game.addGameObject(zombie);	
		}
		
		gameView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				if (event.getPointerCount() > 1 && game.getGameObjects().size() > 0) {
					game.removeGameObject(0);
				} else {
					Zombie zombie = new Zombie(GameActivity.this, 
							Math.random() * 500, 
							Math.random() * 700, 
							Math.cos(Math.random() * 50), 
							Math.sin(Math.random()) * 10);
					
					zombie.startAnimation();
					gameView.addView(zombie);
					game.addGameObject(zombie);
				}
				return false;
			}
		});
		
		game.start();
	}
}
