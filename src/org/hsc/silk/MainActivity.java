package org.hsc.silk;

import org.hsc.silk.checklist.SheetListActivity;
import org.hsc.silk.client.SILKClient;
import org.hsc.silk.client.SILKClient.SILKClientListener;
import org.hsc.silk.dao.SettingDao;
import org.hsc.silk.model.Setting;
import org.hsc.silk.model.User;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

//public class MainActivity extends FragmentActivity implements OnClickListener{
public class MainActivity extends Activity implements OnClickListener{
	private static final int REQUEST_LOGIN = 1;
	private static String tag = "MainActivity";
	private SilkTablet app;
	private Setting setting;
	private SettingDao settingDao;
	
	ProgressDialog progressBarDialog;
	Handler updateHandler;
	SILKClient silkClient;
	SILKClientListener silkListener;
	int progress = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (SilkTablet) getApplication();
		silkClient = new SILKClient(this);
		updateHandler = new Handler();
		
		setContentView(R.layout.activity_main);
		
//		if (app.getUser() == null){
//			doLogin();
//		}
		
//		findViewById(R.id.btnUpdate).setOnClickListener(this);
		findViewById(R.id.btnSheet).setOnClickListener(this);
//		findViewById(R.id.btnLogout).setOnClickListener(this);
		
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
	private void doLogout(){
		app.setUser(null);
		finish();
	}
	private void doLogin() {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivityForResult(intent, REQUEST_LOGIN);
	}
	private void doUpdateData(){
		lanchProgressBarDialog();
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQUEST_LOGIN){
			if(resultCode == RESULT_OK){
				app.setUser((User)data.getParcelableExtra("user"));
				Log.i(tag, app.getUser().toString());
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
        case R.id.menuUpdate:
//          Toast.makeText(this, "Option1", Toast.LENGTH_SHORT).show();
        	doUpdateData();
          return true;
        case R.id.menuLogout:
//          Toast.makeText(this, "Option2", Toast.LENGTH_SHORT).show();
        	doLogout();
          return true;
                   
        default:
          return super.onOptionsItemSelected(item);
        } 
		
//		if(id == R.id.action_update){
//			Log.i(tag, "Update");
//			doUpdateData();
//		}
//		if(id == R.id.action_logout){
//			Log.i(tag, "Logout");
//			doLogout();
//		}
//		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.btnSheet:
			doSheet();
			break;
		
		default:
			
		}
		
	}

	private void doSheet(){
		Intent intent = new Intent(this, SheetListActivity.class);
		startActivity(intent);
	}

}
