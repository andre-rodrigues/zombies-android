package com.example.snacktime;

import com.example.snacktime.interfaces.Collidable;
import com.example.snacktime.interfaces.GameObject;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class Zombie extends ImageView implements GameObject, Collidable{
	private AnimationDrawable animationDrawable;
	private double x;
	private double y;
	private double vx;
	private double vy;
	private int width = 180;
	private int height = 186;

	public Zombie(Context context) {
		super(context);
		LayoutParams params = new RelativeLayout.LayoutParams(width, height);
		setLayoutParams(params);
		
		setBackgroundResource(R.drawable.zombie_walking);
		setAnimationDrawable((AnimationDrawable) getBackground());
	}
	
	public Zombie(Context context, double x, double y, double vx, double vy) {
		this(context);
		setPosition(x, y);
		setVelocity(vx, vy);
	}
	
	public void setPosition(double x, double y) {
		this.setX(x);
		this.setY(y);
		LayoutParams params = new RelativeLayout.LayoutParams(width, height);
		params.leftMargin = (int) x;
		params.topMargin = (int) y;
		setLayoutParams(params);
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}

	public AnimationDrawable getAnimationDrawable() {
		return animationDrawable;
	}

	public void setAnimationDrawable(AnimationDrawable animation) {
		animationDrawable = animation;
	}
	
	public void startAnimation() {
		animationDrawable.start();
	}
	
	public void stopAnimation() {
		animationDrawable.stop();
	}
	
	public void setVelocity(double x, double y) {
		this.vx = x;
		this.vy = y;
	}

	@Override
	public void update() {
		checkBoundaries();
		
		setPosition(this.x + this.vx, this.y + this.vy);
	}
	
	public void checkBoundaries() {
		if (x > (720 - width)  || x < 0)  this.vx = this.vx * -1;
		if (y > (1200 - height) || y < 0) this.vy = this.vy * -1;
	}
}
