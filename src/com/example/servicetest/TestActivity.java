package com.example.servicetest;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

public class TestActivity extends Activity {

	private TestService ts;
	private ServiceConnection tsConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {
			ts = ((TestService.LocalBinder)service).getService();
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			ts = null;
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		startService(new Intent(this, TestService.class));
		bindService(new Intent(this, TestService.class),
				tsConnection,
				Context.BIND_AUTO_CREATE);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(tsConnection);
		if (isFinishing()) {
			stopService(new Intent(this, TestService.class));
		}
	}
}
