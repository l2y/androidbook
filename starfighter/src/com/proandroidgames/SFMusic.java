package com.proandroidgames;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.content.Context;

public class SFMusic extends Service{
	
	public static boolean isRunning = false;
	MediaPlayer player;
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override 
	public void onCreate(){
		super.onCreate();
		setMusicOptions(this, SFEngine.LOOP_BACKGROUND_MUSIC, SFEngine.R_VOLUME, SFEngine.L_VOLUME, SFEngine.SPLASH_SCREEN_MUSIC);
		
		SFEngine.musicThread = new Thread(){
			public void run(){
				Intent bgmusic = new Intent(getApplicationContext(), SFMusic.class);
				startService(bgmusic);
				SFEngine.context = getApplicationContext();
			}
		};
		SFEngine.musicThread.start();
	}
	
	public void setMusicOptions(Context context, boolean isLooped, int rVolume, int lVolume, int soundFile){
		player = MediaPlayer.create(context, soundFile);
		player.setLooping(isLooped);
		player.setVolume(rVolume,lVolume);
	}
	
	public int onStartCommand(Intent intent, int flags, int startId){
		try
		{
			player.start();
			isRunning = true;
		}catch (Exception e){
			isRunning = false;
			player.stop();
		}
			return 1;
	}
	
	public void onStartCommand(Intent intent, int startId){
		
	}
	
	public void onStop(){
		isRunning = false;
	}
	
	public IBinder onUnBind(Intent arg0){
		return null;
	}
	
	public void onPause(){
		
	}
	
	@Override 
	public void onDestroy(){
		player.stop();
		player.release();
	}
	
	@Override 
	public void onLowMemory() {
		player.stop();
	}
	
}