package org.hsc.silk.checklist;

import java.util.List;

import org.hsc.silk.R;
import org.hsc.silk.client.SILKClient;
import org.hsc.silk.model.BpLocation;
import org.hsc.silk.model.Bpartner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class SelectBpLocActivity extends Activity {
	public static final String SELECTED_BP_LOC = "selected_bp_location";
	public static final String BP_ID = "bpid";
	SILKClient silkClient;
	ListView lvBpLoc;
	List<BpLocation> bpLocList;
	BpLocListAdapter bpLocListAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		silkClient = new SILKClient(this);
		setContentView(R.layout.activity_select_bp_loc);
		int bpid = getIntent().getIntExtra(BP_ID, 0);
		bpLocList = silkClient.getBPLocationList(bpid);
		bpLocListAdapter = new BpLocListAdapter(this);
		bpLocListAdapter.addAll(bpLocList);
		lvBpLoc = (ListView) findViewById(R.id.lvBpLoc);
		lvBpLoc.setAdapter(bpLocListAdapter);
		lvBpLoc.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				BpLocation bpLoc = bpLocListAdapter.getItem(position);
				Intent resultIntent = new Intent();
				resultIntent.putExtra(SELECTED_BP_LOC, bpLoc);
				setResult(Activity.RESULT_OK, resultIntent);
				finish();
			}
		});
		
	}
	
	private class BpLocListAdapter extends ArrayAdapter<BpLocation>{

		public BpLocListAdapter(Context context) {
			super(context, 0);
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_item01, null);
			}
			TextView tvBpLocName = (TextView) convertView.findViewById(R.id.textView1);
			tvBpLocName.setText(((BpLocation)getItem(position)).getName());
			return convertView;
		}
		
	}
}
