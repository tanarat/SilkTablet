package org.hsc.silk.checklist;

//import com.jeremyfeinstein.slidingmenu.example.R;

import org.hsc.silk.R;
import org.hsc.silk.dao.SheetItemDao;
import org.hsc.silk.model.SheetItem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SheetItemFragment extends Fragment {
	public static final int REQ_SHEET_ITEM_DETAIL = 0;
	public static final String tag = "SheetItemFragment";
	public static final String REGULATION_ITEM_VALUE = "regulationItemValue";
	public static final String REGULATION_ITEM_NAME = "regulationItemName";
	public static final String SHEET_ID = "sheetId";
	public static final String REGULATION_ITEM_ID = "regulationItemId";
	

	
	private ArrayAdapter<SheetItem> adapter;

	private ListView lvSheetItem;
	private TextView tvRegulationItemValue, tvRegulationItemName, tvTitle;
	
	int sheetId, regulationItemId;
//	List<SheetItem> sheetItemList ;
	
	private SheetItemDao sheetItemDao;

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i(tag, "onResume .. return from SheetItemDetailActivity..");
//		sheetItemList = po.getSheetItemList(sheetId, regulationItemId);
//		adapter.clear();
//		adapter.addAll(sheetItemList);
//		adapter.notifyDataSetChanged();
	}

	
	

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.i(tag, "onPause...");
	}




	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		
		
		Bundle b = getArguments();
		sheetId = b.getInt(SHEET_ID);
		regulationItemId = b.getInt(REGULATION_ITEM_ID);
//		sheetItemList = po.getSheetItemList(sheetId, regulationItemId);
		
		View v = inflater.inflate(R.layout.fragment_regulationitem_detail, null);
		lvSheetItem = (ListView) v.findViewById(R.id.lvChecklistItem);
		tvRegulationItemValue = (TextView) v.findViewById(R.id.tvValue);
		tvRegulationItemName = (TextView) v.findViewById(R.id.tvName);

		tvRegulationItemValue.setText(b.getString(REGULATION_ITEM_VALUE));
		tvRegulationItemName.setText(b.getString(REGULATION_ITEM_NAME));
		

	
//		adapter = new SheetItemListAdapter(getActivity());
//		adapter.addAll(sheetItemList);
		lvSheetItem.setAdapter(adapter);
	
		return v;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

	}

	public void setAdapter(ArrayAdapter<SheetItem> adapter) {
		this.adapter = adapter;
		adapter.notifyDataSetChanged();
	}
	
	
}
