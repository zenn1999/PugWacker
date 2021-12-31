package com.workspace.PugWacker;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.util.Log;

public class MainThread extends Thread {
	
	private static final String TAG = MainThread.class.getSimpleName();
	
	 private SurfaceHolder _surfaceHolder;
	    private GamePanel _panel;
	    private boolean _run = false;
	   
	    
	   
	    
	    public MainThread(SurfaceHolder surfaceHolder, GamePanel panel) {
	        _surfaceHolder = surfaceHolder;
	        _panel = panel;
	    }
	 
	    public void setRunning(boolean run) {
	        _run = run;
	    }
	    
	    
	 
	    @Override
	    public void run() {
	    	 Canvas c;
	    	 Log.d(TAG, "Starting game loop");
	            while (_run) {
	                c = null;
	                try {
	                    c = _surfaceHolder.lockCanvas(null);
	                    synchronized (_surfaceHolder) {
	                    	
	                        _panel.onDraw(c);
	                    }
	                } finally {
	                    // do this in a finally so that if an exception is thrown
	                    // during the above, we don't leave the Surface in an
	                    // inconsistent state
	                    if (c != null) {
	                        _surfaceHolder.unlockCanvasAndPost(c);
	                    }
	                }
	            }
	        }
	 
	    

}
