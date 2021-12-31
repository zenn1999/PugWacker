package com.workspace.PugWacker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.util.Log;

public class PugWackerActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	
	private static final String TAG = GamePanel.class.getSimpleName();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        //Log.d(TAG, "View added");
        Button newGame = (Button)findViewById(R.id.bNew);
        Button exit = (Button)findViewById(R.id.bExit);
        newGame.setOnClickListener(this);
        exit.setOnClickListener(this);
     
    }
    
    @Override
    protected void onDestroy() {
    	Log.d(TAG, "Destroying...");
    	super.onDestroy();
    }
    
    @Override
    protected void onStop() {
    	Log.d(TAG, "Stopping...");
    	super.onStop();
    }

	@Override
	public void onClick(View view) {
		switch(view.getId()) {
		case R.id.bExit:
			finish();
			break;
		case R.id.bNew:
			Intent game = new Intent(PugWackerActivity.this, Game.class);
			startActivity(game);
		}
		
	}
}