package org.hsc.silk.checklist;

import java.util.List;

import org.hsc.silk.R;
import org.hsc.silk.client.SILKClient;
import org.hsc.silk.dao.AuditTypeDao;
import org.hsc.silk.model.BpLocation;
import org.hsc.silk.model.Bpartner;
import org.hsc.silk.model.AuditType;

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

public class SelectAuditTypeActivity extends Activity {
	public static final String SELECTED_AUDIT_TYPE = "selected_audit_type";
	
	ListView lvAuditType;
	List<AuditType> auditTypeList;
	AuditTypeListAdapter auditTypeListAdapter;
	AuditTypeDao dao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_select_bp_loc);
		dao = new AuditTypeDao(this);
		auditTypeList = dao.get(null, null);
		auditTypeListAdapter = new AuditTypeListAdapter(this);
		auditTypeListAdapter.addAll(auditTypeList);
		lvAuditType = (ListView) findViewById(R.id.lvBpLoc);
		lvAuditType.setAdapter(auditTypeListAdapter);
		lvAuditType.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				AuditType auditType = auditTypeListAdapter.getItem(position);
				Intent resultIntent = new Intent();
				resultIntent.putExtra(SELECTED_AUDIT_TYPE, auditType);
				setResult(Activity.RESULT_OK, resultIntent);
				finish();
			}
		});
		
	}
	
	private class AuditTypeListAdapter extends ArrayAdapter<AuditType>{

		public AuditTypeListAdapter(Context context) {
			super(context, 0);
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_item01, null);
			}
			TextView tvAuditTypeName = (TextView) convertView.findViewById(R.id.textView1);
			tvAuditTypeName.setText(((AuditType)getItem(position)).getName());
			return convertView;
		}
		
	}
}
