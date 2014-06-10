package org.hsc.silk.checklist;



import org.hsc.silk.R;
import org.hsc.silk.model.RegulationItem;
import org.hsc.silk.model.Sheet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RegulationItemListFragment extends Fragment implements OnItemClickListener {
	public static final String tag = "RegItemListFragment";
	
	public static final String ITEM_LIST = "itemList";
	public static final String SHEET_ID = "sheetId";
	

	private Callback callback;
	private ListView lv;
	private ArrayAdapter<RegulationItem> adapter;
	
	public RegulationItemListFragment() {
		super();
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_reg_item_list, null);
		lv = (ListView) view.findViewById(R.id.lvData);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
		return view;
	}
	@Override
	public void onItemClick(AdapterView<?> view, View arg1, int index, long id) {
		// TODO Auto-generated method stub
		RegulationItem obj = (RegulationItem)view.getItemAtPosition(index);
		
		if(callback != null && !obj.isGroup())
			callback.onItemSelected(index, (int)id, obj);
	}
	public void setAdapter(ArrayAdapter<RegulationItem> adapter){
		this.adapter = adapter;
		adapter.notifyDataSetChanged();
	}
	public Callback getCallback() {
		return callback;
	}
	public void setCallback(Callback callback) {
		this.callback = callback;
	}

	public interface Callback{
		void onItemSelected(int index, int id, Object t);
	}

	
	
}
