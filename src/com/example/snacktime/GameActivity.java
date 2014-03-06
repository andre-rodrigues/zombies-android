package com.example.snacktime;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class GameActivity extends Activity {
	
	private Game game;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		game = Game.getInstance();
		
		RelativeLayout baseView = (RelativeLayout) findViewById(R.id.layout);

		int direction = 1;
		for (int i = 0; i < 100; i++) {
			direction = (i % 2) == 0 ? 1 : -1;
			Zombie zombie = new Zombie(this, i * 5, i * 5, 1 * direction, i);
			zombie.startAnimation();
			baseView.addView(zombie);
			
			game.addGameObject(zombie);	
		}
		
		Zombie zombie = new Zombie(this, 80, 80, 0.1, 0.1);
		zombie.startAnimation();
		baseView.addView(zombie);
		game.addGameObject(zombie);
		
		game.start();
	}
}
