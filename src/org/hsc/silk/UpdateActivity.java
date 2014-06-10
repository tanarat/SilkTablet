package org.hsc.silk;

import org.hsc.silk.client.SILKClient;
import org.hsc.silk.client.SILKClient.SILKClientListener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;

public class UpdateActivity extends Activity {
	ProgressDialog progressBarDialog;
	Handler updateHandler;
	SILKClient silkClient;
	SILKClientListener silkListener;
	int progress = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		silkClient = new SILKClient(this);
		updateHandler = new Handler();
		setContentView(R.layout.activity_update);
		findViewById(R.id.update_button).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				lanchProgressBarDialog();
			}
		});
		
		silkListener = new SILKClientListener() {
			
			@Override
			public void onUpdateFinished() {
				progressBarDialog.dismiss();
			}
			
			@Override
			public void onProgressChanged(final int p) {
				updateHandler.post(new Runnable() {
					
					@Override
					public void run() {
						progressBarDialog.setProgress(p);
					}
				});
			}
		};
	}

	private void lanchProgressBarDialog() {
		progressBarDialog = new ProgressDialog(this);
		progressBarDialog.setMessage("Update in progress..");
		progressBarDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressBarDialog.setProgress(0);
		progressBarDialog.setMax(SILKClient.MAX_UPDATE_PROGRESS);
		progressBarDialog.show();

		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				silkClient.updateData(silkListener);				
			}
		}).start();
		
	}
}
