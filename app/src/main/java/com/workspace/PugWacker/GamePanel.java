package com.workspace.PugWacker;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
	
	

	
	private MainThread _thread;
	
	private long LastClick;
	private int score;
	private int highScore = 0;
	private int wins = 0;
	
	private long time;


	private int FPS = 6;
	
	Paint paint = new Paint();
	
	private SoundPool sounds;
	private int wacked;
	
	Random rand= new Random();
	
	private Animation Pug1;
	private Animation Pug2;
	
	private List<Animation> pugs = new ArrayList<Animation>();
   
	
	 public GamePanel(Context context) {
	        super(context);
	        sounds = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
	        wacked = sounds.load(context, R.raw.whacked, 1);

		 GameTimer gTimer = new GameTimer(60000, 1000);
	       gTimer.start();
	   
		     getHolder().addCallback(this);
	         _thread = new MainThread(getHolder(), this);
	         // create a graphic
	         Pug1 = new Animation();
	         Pug2 = new Animation();
	         
	         Pug1.init(BitmapFactory.decodeResource(getResources(), R.drawable.bigup), 300, 75, 128, 128, FPS, 3);
	         Pug2.init(BitmapFactory.decodeResource(getResources(), R.drawable.bigup), 180, 280, 128, 128, FPS, 3);
	         

	         setFocusable(true);
	
	 }
	 
	 public class GameTimer extends CountDownTimer {
			
			

			public GameTimer(long millisInFuture, long countDownInterval) {
				super(millisInFuture, countDownInterval);
				// TODO Auto-generated constructor stub
			}

			@Override
			public void onFinish() {
				// when timer is finished do something
				if (highScore < score) {
				highScore = score;
				}
				if (score >= 50) {
					this.cancel();
					pugs.remove(Pug1);
					pugs.remove(Pug2);
					score = 0;
					if (highScore >= 50) {
						
					    FPS = rand.nextInt(9);
					    if (FPS <= 1) {
					    	FPS = FPS + 2;
					    }
					    highScore = 0;
					    pugs.add(Pug1);
					    pugs.add(Pug2);
					    this.start();
					    wins = wins + 1;
					    if (wins == 3) {
					    	this.cancel();
					    	pugs.remove(Pug1);
					    	pugs.remove(Pug2);
					        
					    }
					    if (highScore >= 100) {
						    //Add special prize
					    }
					}
				} else {
					
					this.start();
					score = 0;
				}
				
			}

			@Override
			public void onTick(long millisUntilFinished) {
				time = millisUntilFinished / 1000;

			} 

		}
	 
	 
	 
	 private void createSprites() {
			pugs.add(Pug1);
			pugs.add(Pug2);
			
			
	 }
	 
	Bitmap backGround = BitmapFactory.decodeResource(getResources(), R.drawable.level1);

	 
	@Override
	    public void onDraw(Canvas canvas) {
		 canvas.drawBitmap(backGround, 0, 0, null);
		 
		 for (Animation animation : pugs) {
				animation.draw(canvas);  //draws all the pug to the screen
				update();  // called to update the animation
		 }
		 paint.setTextSize(28);
		 paint.setColor(Color.BLUE);
		 canvas.drawText(Score(), 20, 20, paint);
		 
		 paint.setTextSize(28);
		 paint.setColor(Color.BLUE);
		 canvas.drawText(Time(), 680, 20, paint);
		 
		 paint.setTextSize(28);
		 paint.setColor(Color.BLUE);
		 canvas.drawText(HighScore(), 380, 20, paint);
	    }

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		createSprites();   //called to create the sprites
		 _thread.setRunning(true);
		 _thread.start();
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		 boolean retry = true;
		    _thread.setRunning(false);
		    while (retry) {
		        try {
		            _thread.join();
		            retry = false;
		        } catch (InterruptedException e) {
		            // we will try it again and again...
		        }
		    }	
	}
	
	public String Score()
	{
	    String currentpoints;
	    currentpoints = "" + score;
	    return "Score " + currentpoints;
	    
	    
	}
	
	

	public String Time() {
		String currenttime;
		currenttime = "" + time;
		return "Time :" + currenttime;
	}
	
	public String HighScore() {
		String currenthigh;
		currenthigh = "" + highScore;
		return "High  " + currenthigh;
	}
	
	public void update() {
		
		Pug1.Update(System.currentTimeMillis());
		Pug2.Update(System.currentTimeMillis());
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (System.currentTimeMillis() - LastClick > 300) {	
			LastClick = System.currentTimeMillis();
			float x = event.getX();
			float y = event.getY();
			synchronized (getHolder()) {	
			for (int i = pugs.size() - 1; i >= 0; i--) {
				Animation sprite = pugs.get(i);
				if (sprite.isColition(x, y)) {
					
					score = score + 1;
					sounds.play(wacked, 1.0f, 1.0f, 0, 0, 1.5f);
					break;
				} 
			}	
			}	
		    }
		return true;
		
	}

}
