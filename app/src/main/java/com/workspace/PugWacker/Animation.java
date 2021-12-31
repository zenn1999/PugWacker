package com.workspace.PugWacker;


import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.animation.Animation.AnimationListener;
public class Animation implements AnimationListener {
	
	private static final String TAG = Animation.class.getSimpleName();
	
	private Bitmap mAnimation;     // the variable that holds the actual bitmap
    private int mXPos;           //sprite position (top left pixel of image)
    private int mYPos;
    private Rect mSRectangle;  //source rectangle variable
    private int mFPS;
    private int mNoOfFrames;   //number of frames in sprite sheet
    private int mCurrentFrame;
    private long mFrameTimer;
    private int mSpriteHeight;     // height of individual frame
    private int mSpriteWidth;      // width of individual frame
   
    Random rand= new Random();
    
    
    
    public Animation() {
    	 mSRectangle = new Rect(0,0,0,0);
    	    mFrameTimer = 1;
    	    mCurrentFrame = 0;
    	  
    	   
    }
    
    public void init(Bitmap theBitmap,int x, int y, int Height, int Width, int theFPS, int theFrameCount) {
        mAnimation = theBitmap;
        mSpriteHeight = Height;
        mSpriteWidth = Width;
        mSRectangle.top = 0;
        mSRectangle.bottom = mSpriteHeight;
        mSRectangle.left = 0;
        mSRectangle.right = mSpriteWidth;
        mFPS = 1000 /theFPS;
        mNoOfFrames = theFrameCount;
        mXPos = x;
	    mYPos = y;
	    
	   

    }
    
   
    
    
    
    public void Update(long GameTime) {
    
        if(GameTime > mFrameTimer + mFPS ) {
            mFrameTimer = GameTime;
            mCurrentFrame +=1;
     
            if(mCurrentFrame >= mNoOfFrames) {
                mCurrentFrame = 0;
               mXPos = rand.nextInt(700);

            }
            
        }
     
        mSRectangle.left = mCurrentFrame * mSpriteWidth;
        mSRectangle.right = mSRectangle.left + mSpriteWidth;
        
        Log.d (TAG, "Current frame" + mCurrentFrame + ",x=" + mXPos);
    }
    
    public void draw(Canvas canvas) {
    	this.Update(System.currentTimeMillis());
        Rect dest = new Rect(getXPos(), getYPos(), getXPos() + mSpriteWidth,
                        getYPos() + mSpriteHeight);
     
        canvas.drawBitmap(mAnimation, mSRectangle, dest, null);
     
    }
    
    public boolean isColition(float x2, float y2) {
		return x2 > mXPos && x2 < mXPos + mSpriteWidth && y2 > mYPos && y2 < mYPos + mSpriteHeight;
    }

	private int getYPos() {
		// TODO add screen border collision
		return mYPos;
	}

	private int getXPos() {
		
		// TODO add screen border collision
		return mXPos;
	}

	@Override
	public void onAnimationEnd(android.view.animation.Animation arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationRepeat(android.view.animation.Animation arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationStart(android.view.animation.Animation arg0) {
		// TODO Auto-generated method stub
		
	}

}
