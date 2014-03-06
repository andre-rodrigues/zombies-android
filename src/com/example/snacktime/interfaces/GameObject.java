package com.example.snacktime.interfaces;

public interface GameObject {
	public void update();
	public boolean post(Runnable runnable);
}
