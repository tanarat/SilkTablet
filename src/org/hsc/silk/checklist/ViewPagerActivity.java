package org.hsc.silk.checklist;

import java.util.ArrayList;
import java.util.List;









import org.hsc.silk.R;
import org.hsc.silk.dao.SheetItemDao;
import org.hsc.silk.model.AuditSheetModel;
import org.hsc.silk.model.AuditSheetModel.AuditSheetModelListener;
import org.hsc.silk.model.RegItemRow;
import org.hsc.silk.model.SheetItem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class ViewPagerActivity extends SlidingFragmentActivity implements
		RegItemListFragment.Callback, AuditSheetModelListener {
	public static final String tag = "ViewPagerActivity";
	public static final String SHEET_ID = "sheetId";

	AuditSheetModel mAuditSheet;

	RegItemListFragment mFrag;
	ViewPager vp;

	RegItemListAdapter regItemListAdapter;
//	SheetItemListener sheetItemListener;
	SheetItemDao sheetItemDao;
	
	List<SheetItem> sheetItemList;
	private ArrayList<SheetItemFragment> sheetItemFragmentList;
	
	int requestCode = 1;

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		Log.i(tag, "Hey... get result from SheetDetailActivity...now!!!");
	}
	
	public ViewPagerActivity() {
		super();

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.title_activity_audit_sheet);
		setBehindContentView(R.layout.fragment_sheet_item_list);
		
		int sheetId = getIntent().getIntExtra(SHEET_ID, 0);
		

		mAuditSheet = new AuditSheetModel(this, sheetId);
		mAuditSheet.setAuditSheetModelListener(this);
		
		sheetItemDao = new SheetItemDao(this);
//		sheetItemListener = new SheetItemListener() {
//
//			@Override
//			public void onSheetItemChanged(SheetItem sheetItem) {
//				mAuditSheet.updateRegItemRow(sheetItem.getRegulationItemId());
//				
//				regItemListAdapter.notifyDataSetChanged();
//
//				Log.i(tag, "hey hey ... update sheetItemList too!!");
//			}
//		};

		Bundle b = new Bundle();
		b.putInt(RegItemListFragment.SHEET_ID, sheetId);

		if (savedInstanceState == null) {
			FragmentTransaction t = this.getSupportFragmentManager()
					.beginTransaction();
			mFrag = new RegItemListFragment();
			regItemListAdapter = new RegItemListAdapter(getApplicationContext());
			regItemListAdapter.addAll(mAuditSheet.getRegItemRowList());
			regItemListAdapter.addAll(mAuditSheet.getRegItemRowList());
			regItemListAdapter.notifyDataSetChanged();
			mFrag.setAdapter(regItemListAdapter);
			t.replace(R.id.container, mFrag);
			t.commit();
		} else {
			mFrag = (RegItemListFragment) this.getSupportFragmentManager()
					.findFragmentById(R.id.container);
		}

		mFrag.setCallback(this);

		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		sheetItemFragmentList = new ArrayList<SheetItemFragment>();
		for (int i = 0; i < mAuditSheet.getRegItemRowList().size(); i++) {
			RegItemRow regItem = mAuditSheet.getRegItemRowList().get(i);
			SheetItemFragment f = new SheetItemFragment();
			SheetItemListAdapter sheetItemListAdapter = new SheetItemListAdapter(getApplicationContext());
			sheetItemListAdapter.addAll(mAuditSheet.getSheetItemListAt(i));
			sheetItemListAdapter.setRegulationItemValue(mAuditSheet.getRegItemRowList().get(i).getRegItemValue());
			sheetItemListAdapter.setRegulationItemName(mAuditSheet.getRegItemRowList().get(i).getRegItemName());
			f.setAdapter(sheetItemListAdapter);
			Bundle bb = new Bundle();
			bb.putInt(SheetItemFragment.SHEET_ID, regItem.getSheetId());
			bb.putInt(SheetItemFragment.REGULATION_ITEM_ID, regItem.getRegItemId());
			bb.putString(SheetItemFragment.REGULATION_ITEM_VALUE,mAuditSheet.getRegItemRowList().get(i).getRegItemValue());
			bb.putString(SheetItemFragment.REGULATION_ITEM_NAME, mAuditSheet.getRegItemRowList().get(i).getRegItemName());
			f.setArguments(bb);
			sheetItemFragmentList.add(f);
		}

		vp = new ViewPager(this);
		vp.setId("VP".hashCode());
		vp.setAdapter(new RegulationItemPagerAdapter(getSupportFragmentManager(), this));
		setContentView(vp);

		vp.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageSelected(int position) {
				switch (position) {
				case 0:
					getSlidingMenu().setTouchModeAbove(
							SlidingMenu.TOUCHMODE_FULLSCREEN);
					break;
				default:
					getSlidingMenu().setTouchModeAbove(
							SlidingMenu.TOUCHMODE_MARGIN);
					break;
				}
			}

		});

		vp.setCurrentItem(0);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

	}
	@Override
	public void onRegItemListChanged() {
		regItemListAdapter.clear();
		regItemListAdapter.addAll(mAuditSheet.getRegItemRowList());
		regItemListAdapter.notifyDataSetChanged();
		
	}

	@Override
	public void onItemSelected(int index, int id, Object t) {

		vp.setCurrentItem(index);
		toggle();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public class RegulationItemPagerAdapter extends FragmentPagerAdapter {
		Activity activity;


		public RegulationItemPagerAdapter(FragmentManager fm, Activity activity) {
			super(fm);
			this.activity = activity;	
		}

		@Override
		public int getCount() {
			return sheetItemFragmentList.size();
		}

		@Override
		public Fragment getItem(int position) {
			return sheetItemFragmentList.get(position);
		}

	}

	

	class RegItemListAdapter extends ArrayAdapter<RegItemRow> {

		public RegItemListAdapter(Context context) {
			super(context, 0);

		}

		private class ViewHolder {
			TextView tvRegItemName;
			TextView tvValue;
			TextView tvResult;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(
						R.layout.row_reg_item, null);
				holder = new ViewHolder();
				holder.tvRegItemName = (TextView) convertView
						.findViewById(R.id.txtRegItemName);
				holder.tvValue = (TextView) convertView
						.findViewById(R.id.txtValue);
				holder.tvResult = (TextView) convertView
						.findViewById(R.id.txtResult);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			RegItemRow item = getItem(position);

			if (item.isGroup()) {
				convertView.setBackgroundColor(Color.GRAY);
				holder.tvRegItemName.setTextColor(Color.WHITE);
				holder.tvValue.setTextColor(Color.WHITE);
				holder.tvResult.setText("");
				holder.tvResult.setVisibility(View.INVISIBLE);
			} else {
				convertView.setBackgroundColor(Color.WHITE);
				holder.tvRegItemName.setTextColor(Color.BLACK);
				holder.tvValue.setTextColor(Color.BLACK);
				holder.tvResult.setTextColor(Color.BLACK);
				holder.tvResult.setVisibility(View.VISIBLE);
			}
			holder.tvRegItemName.setText(item.getRegItemName());
			holder.tvValue.setText(item.getRegItemValue());
			holder.tvResult.setText(item.getNoSheetItem() + " / "
					+ item.getNoConformItem());
			return convertView;
		}

	}
	
	class SheetItemListAdapter extends ArrayAdapter<SheetItem> {
		private Context context;
		private String regulationItemValue;
		private String regulationItemName;

		public SheetItemListAdapter(Context context) {
			super(context, 0);
			this.context = context;
		}
		@Override
		public long getItemId(int position) {
			return ((SheetItem) getItem(position)).getId();
		}

		
		@Override
		public View getView(int position, View view, ViewGroup parent) {

			if (view == null) {
				view = LayoutInflater.from(getContext()).inflate(
						R.layout.row_check_item, null);
			}
			final SheetItem item = getItem(position);

			TextView tv1 = (TextView) view.findViewById(R.id.textView1);
			TextView tv2 = (TextView) view.findViewById(R.id.textView2);

			tv2.setText(item.getChecklistItem().getValue());
			tv2.setTextColor(Color.BLACK);
			tv1.setText(item.getChecklistItem().getName());
			tv1.setTextColor(Color.BLACK);

			Switch swConform = (Switch) view.findViewById(R.id.switch1);
			swConform.setChecked(item.getAnswer() == 1);
			swConform.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					item.setAnswer((isChecked) ? 1 : 0);
					sheetItemDao.save(item);
//					po.save(item);
					item.notifyListener();
				}
			});

			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
//					Log.i(tag, "selected item : " + item.getValue() + " " + item.getName());
					onSheetItemSelected(item);
				}
			});
			return view;
		}

		private void onSheetItemSelected(SheetItem selectedItem){
			Intent intent = new Intent(context,SheetItemDetailActivity.class);
//			intent.putExtra(SheetItemDetailActivity.SHEET_ITEM, selectedItem);
			
			intent.putExtra(SheetItemDetailActivity.REGULATION_ITEM_VALUE,regulationItemValue);
			intent.putExtra(SheetItemDetailActivity.REGULATION_ITEM_NAME,regulationItemName);
			intent.putExtra(SheetItemDetailActivity.SHEET_ID,selectedItem.getSheetId());
			intent.putExtra(SheetItemDetailActivity.CHECKLIST_ITEM_ID,selectedItem.getChecklistItemId());
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		}
		public String getRegulationItemValue() {
			return regulationItemValue;
		}
		public void setRegulationItemValue(String regulationItemValue) {
			this.regulationItemValue = regulationItemValue;
		}
		public String getRegulationItemName() {
			return regulationItemName;
		}
		public void setRegulationItemName(String regulationItemName) {
			this.regulationItemName = regulationItemName;
		}
		

	}
}
