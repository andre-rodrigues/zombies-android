package com.example.snacktime;

public interface GameObject {
	public void update();
	public boolean post(Runnable runnable);
}
