package org.hsc.silk.checklist;

import java.util.List;

import org.hsc.silk.R;
import org.hsc.silk.client.SILKClient;
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

public class SearchBpActivity extends Activity {
	public static final String SELECTED_BPARTNER = "selected_bpartner";
	SILKClient silkClient;
	EditText edtNameSearch;
	ListView lvBpartner;
	List<Bpartner> bpList;
	BpartnerListAdapter bpListAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		silkClient = new SILKClient(this);
		setContentView(R.layout.activity_search_bp);
		edtNameSearch = (EditText) findViewById(R.id.edtNameSearch);
		edtNameSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				String searchText = edtNameSearch.getText().toString();
				if (searchText.length() > 0) {
//					xx(searchText);
					bpList = silkClient.getBPartnerList(searchText);
					bpListAdapter.clear();
					bpListAdapter.addAll(bpList);
					bpListAdapter.notifyDataSetChanged();
				}
			}
		});
		
		bpListAdapter = new BpartnerListAdapter(this);
		lvBpartner = (ListView) findViewById(R.id.lvBpartner);
		lvBpartner.setAdapter(bpListAdapter);
		lvBpartner.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Bpartner bp = bpListAdapter.getItem(position);
				Intent resultIntent = new Intent();
				resultIntent.putExtra(SELECTED_BPARTNER, bp);
				setResult(Activity.RESULT_OK, resultIntent);
				finish();
			}
		});
		
	}
	
	private class BpartnerListAdapter extends ArrayAdapter<Bpartner>{

		public BpartnerListAdapter(Context context) {
			super(context, 0);
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_item01, null);
			}
			TextView tvBpName = (TextView) convertView.findViewById(R.id.textView1);
			tvBpName.setText(((Bpartner)getItem(position)).getName());
			return convertView;
		}
		
	}
}
