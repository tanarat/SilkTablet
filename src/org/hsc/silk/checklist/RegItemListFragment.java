package org.hsc.silk.checklist;



import org.hsc.silk.R;
import org.hsc.silk.model.RegItemRow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RegItemListFragment extends Fragment implements OnItemClickListener {
	public static final String ITEM_LIST = "itemList";
	public static final String SHEET_ID = "sheetId";
	public static final String tag = "RegItemListFragment";

	private Callback callback;
	private ListView lv;
//	private ArrayAdapter<AuditSheet.RegItem> adapter; 
	private ArrayAdapter<RegItemRow> adapter;
	public RegItemListFragment() {
		super();
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_select_list, null);

		lv = (ListView) view.findViewById(R.id.lvData);

		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
		return view;
	}
	@Override
	public void onItemClick(AdapterView<?> view, View arg1, int index, long id) {
		// TODO Auto-generated method stub
		RegItemRow obj = (RegItemRow)view.getItemAtPosition(index);
		
		if(callback != null && !obj.isGroup())
			callback.onItemSelected(index, (int)id, obj);
	}
	public Callback getCallback() {
		return callback;
	}
	public void setCallback(Callback callback) {
		this.callback = callback;
	}
//	public ArrayAdapter<AuditSheet.RegItem> getAdapter() {
//		return adapter;
//	}
	public ArrayAdapter<RegItemRow> getAdapter() {
		return adapter;
	}

//	public void setAdapter(ArrayAdapter<AuditSheet.RegItem> adapter) {
//		this.adapter = adapter;
//		adapter.notifyDataSetChanged();
//	}
	public void setAdapter(ArrayAdapter<RegItemRow> adapter) {
		this.adapter = adapter;
		adapter.notifyDataSetChanged();
	}
	public interface Callback{
		void onItemSelected(int index, int id, Object t);
	}
	
}
