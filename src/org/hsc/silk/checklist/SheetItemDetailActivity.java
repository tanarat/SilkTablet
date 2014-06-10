package org.hsc.silk.checklist;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;








import org.hsc.silk.R;
import org.hsc.silk.dao.RemarkChoiceDao;
import org.hsc.silk.dao.SheetItemDao;
import org.hsc.silk.model.RemarkChoice;
import org.hsc.silk.model.SheetItem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

public class SheetItemDetailActivity extends FragmentActivity {
	public static final String tag = "SheetItemDetailActivity";

	public static final String SHEET_ID = "sheetId";
	public static final String CHECKLIST_ITEM_ID = "checklistItemId";
	public static final String SHEET_ITEM = "sheetItem";
	public static final String REGULATION_ITEM_VALUE = "checklistItemValue";
	public static final String REGULATION_ITEM_NAME = "checklistItemName";


	
	private int sheetId;
	private int checklistItemId;
	private Fragment f;
	
	public String regulationItemName, regulationItemValue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sheet_item_detail);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		
		
		sheetId = getIntent().getIntExtra(SHEET_ID, 0);
		checklistItemId = getIntent().getIntExtra(CHECKLIST_ITEM_ID, 0);
		regulationItemName = getIntent().getStringExtra(REGULATION_ITEM_NAME);
		regulationItemValue = getIntent().getStringExtra(REGULATION_ITEM_VALUE);

		f = new SheetItemFragment();
		Bundle b = new Bundle();

		b.putInt(SHEET_ID, sheetId);
		b.putInt(CHECKLIST_ITEM_ID, checklistItemId);
		b.putString(REGULATION_ITEM_VALUE, regulationItemValue);
		b.putString(REGULATION_ITEM_NAME, regulationItemName);
		f.setArguments(b);

		if (savedInstanceState == null) {

			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, f).commit();
		}

	}

	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class SheetItemFragment extends Fragment {

		public static final String SHEET_ID = "sheetId";
		public static final String CHECKLIST_ITEM_ID = "checklistItemId";

		private SheetItem sheetItem;
		private SheetItemDao sheetItemDao;
		private RemarkChoiceDao remarkChoiceDao;
//		private PO po;
		
		
		
		//View components
	
		private TextView txtChecklistItemName, txtChecklistItemValue, tvRegulationItemValue, tvRegulationItemName;
		private Switch swConform;
		private EditText edtRemark;
		private ListView lvRemarkChoice;

//		public SheetItemFragment() {
//		}

		@Override
		public void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			saveSheetItem();
			
		}

		private void saveSheetItem() {
//			po.save(sheetItem);
			sheetItemDao.save(sheetItem);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
//			po = new PO(getActivity());
			sheetItemDao = new SheetItemDao(getActivity());
			remarkChoiceDao = new RemarkChoiceDao(getActivity());
			Bundle b = getArguments();
			int sheetId = b.getInt(SHEET_ID);
			int checklistItemId = b.getInt(CHECKLIST_ITEM_ID);
			String regulationItemName, regulationItemValue;
			
			regulationItemValue = b.getString(REGULATION_ITEM_VALUE);
			regulationItemName = b.getString(REGULATION_ITEM_NAME);
			
			Log.i(tag, "regulationItemName : " + regulationItemName + " , regulationItemValue : " + regulationItemValue);
//			sheetItem = po.getSheetItem(sheetId, checklistItemId);
			sheetItem = sheetItemDao.getSheetItem(sheetId, checklistItemId);

			View rootView = inflater.inflate(R.layout.fragment_sheet_item,
					container, false);
			tvRegulationItemValue = (TextView) rootView.findViewById(R.id.tvRegulationItemValue);
			tvRegulationItemValue.setText(regulationItemValue);
			tvRegulationItemName = (TextView) rootView.findViewById(R.id.tvRegulationItemName);
			tvRegulationItemName.setText(regulationItemName);
			
			txtChecklistItemValue = (TextView) rootView.findViewById(R.id.txtChecklistItemValue);
			txtChecklistItemValue.setText(sheetItem.getChecklistItem().getValue());
			
			txtChecklistItemName = (TextView) rootView.findViewById(R.id.txtChecklistItemName);
			txtChecklistItemName.setText(sheetItem.getChecklistItem().getName());

			swConform = (Switch) rootView.findViewById(R.id.switch1);
			swConform.setChecked(sheetItem.getAnswer() == 1);
			/*
			swConform.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					sheetItem.setAnswer((isChecked) ? 1 : 0);
				}
			});
			*/
			edtRemark = (EditText) rootView.findViewById(R.id.edtRemark);
			edtRemark.setText(sheetItem.getRemark());
			edtRemark.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
				
				@Override
				public void afterTextChanged(Editable s) {
					sheetItem.setRemark(s.toString());
				}
			});
			
			
			

			lvRemarkChoice = (ListView) rootView.findViewById(R.id.lvRemarkChoices);
			
			RemarkChoiceAdapter adapter = new RemarkChoiceAdapter(getActivity(), sheetItem);
//			List<RemarkChoice> remarkChoiceList = po.getRemarkChoiceList(sheetItem.getChecklistItemId());
			List<RemarkChoice> remarkChoiceList = remarkChoiceDao.getRemarkChoiceList(sheetItem.getChecklistItemId());
			adapter.addAll(remarkChoiceList);
			lvRemarkChoice.setAdapter(adapter);

			TextView tvRemarkChoice = (TextView) rootView.findViewById(R.id.txtRemarkChoice);
			if(remarkChoiceList.size() > 0){
				tvRemarkChoice.setText(R.string.lbl_remark_choices);
			}
			
			return rootView;
		}

		class RemarkChoiceAdapter extends ArrayAdapter<RemarkChoice>{
			Context context;
			Set<Integer> selectedChoice = new HashSet<Integer>();
			public RemarkChoiceAdapter(Context context, SheetItem sheetItem) {
				super(context, 0);
				this.context = context;
				
				String str = sheetItem.getRemarkChoice();
					if(str != null && str.length() != 0){
						str = str.substring(1, str.length() - 1);
						Log.i(tag, "xxxx : " + str);
						StringTokenizer token = new StringTokenizer(str, ",");
						while (token.hasMoreElements()) {
								String t = ((String) token.nextElement()).trim();
								selectedChoice.add(Integer.parseInt(t));
						}
					}
				
			}
			
			
			@Override
			public long getItemId(int position) {
				return ((RemarkChoice)getItem(position)).getId();
			}
			@Override
			public View getView(final int position, View view, ViewGroup parent) {
				
				view = LayoutInflater.from(context).inflate(R.layout.row_remark_choice, null);

				CheckBox ckb = (CheckBox) view.findViewById(R.id.checkBox1);
				ckb.setText(getItem(position).getName());
				int remarkChoiceId = getItem(position).getId();
				
				
				ckb.setChecked(selectedChoice.contains(remarkChoiceId));
				
				
				ckb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(isChecked){
							selectedChoice.add((int)getItemId(position));
						}else{
							selectedChoice.remove((int)getItemId(position));
						}
						Log.i(tag, selectedChoice.toString());
						sheetItem.setRemarkChoice(selectedChoice.toString());
					}
				});
				return view;
			}
			
		}
		
	}

}
