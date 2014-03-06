package com.example.snacktime;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class Zombie extends ImageView implements GameObject {
	private AnimationDrawable animationDrawable;
	private float x;
	private float y;

	public Zombie(Context context) {
		super(context);
		LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		setLayoutParams(params);
		
		setBackgroundResource(R.drawable.zombie_walking);
		setAnimationDrawable((AnimationDrawable) getBackground());
	}
	
	public void setPosition(float x, float y) {
		this.setX(x);
		this.setY(y);
		LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.leftMargin = (int) x;
		params.topMargin = (int) y;
		setLayoutParams(params);
	}
	
	@Override
	public void setX(float x) {
		this.x = x;
	}
	
	@Override
	public void setY(float y) {
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

	@Override
	public void update() {
		setPosition(this.x + 1, this.y + 1);
	}
}
