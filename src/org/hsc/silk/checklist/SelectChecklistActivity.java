package org.hsc.silk.checklist;

import java.util.List;

import org.hsc.silk.R;
import org.hsc.silk.client.SILKClient;
import org.hsc.silk.dao.ChecklistDao;
import org.hsc.silk.model.BpLocation;
import org.hsc.silk.model.Bpartner;
import org.hsc.silk.model.Checklist;

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

public class SelectChecklistActivity extends Activity {
	public static final String SELECTED_CHECKLIST = "selected_checklist";
	
	SILKClient silkClient;
	ListView lvChecklist;
	List<Checklist> checklistList;
	ChecklistListAdapter checklistListAdapter;
	ChecklistDao dao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_select_bp_loc);
		dao = new ChecklistDao(this);
		checklistList = dao.get(null, null);
		checklistListAdapter = new ChecklistListAdapter(this);
		checklistListAdapter.addAll(checklistList);
		lvChecklist = (ListView) findViewById(R.id.lvBpLoc);
		lvChecklist.setAdapter(checklistListAdapter);
		lvChecklist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Checklist checklist = checklistListAdapter.getItem(position);
				Intent resultIntent = new Intent();
				resultIntent.putExtra(SELECTED_CHECKLIST, checklist);
				setResult(Activity.RESULT_OK, resultIntent);
				finish();
			}
		});
		
	}
	
	private class ChecklistListAdapter extends ArrayAdapter<Checklist>{

		public ChecklistListAdapter(Context context) {
			super(context, 0);
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_item01, null);
			}
			TextView tvChecklistName = (TextView) convertView.findViewById(R.id.textView1);
			tvChecklistName.setText(((Checklist)getItem(position)).getName());
			return convertView;
		}
		
	}
}
