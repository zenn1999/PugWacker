package com.workspace.PugWacker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Game extends Activity {
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		getIntent();
		GamePanel panel = new GamePanel(this);
		setContentView(panel);
	}

}
