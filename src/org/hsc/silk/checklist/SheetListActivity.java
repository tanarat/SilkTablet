package org.hsc.silk.checklist;

import java.text.SimpleDateFormat;

import org.hsc.silk.R;
import org.hsc.silk.dao.SheetDao;
import org.hsc.silk.model.Sheet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SheetListActivity extends Activity {
	public static final String tag = "SheetListActivity";
	private SheetListAdapter adapter;
	private ListView lvSheet;
	private SheetDao dao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sheet_list);
		dao = new SheetDao(this);
		adapter = new SheetListAdapter(this);
		lvSheet = (ListView) findViewById(R.id.lvSheet);
		lvSheet.setAdapter(adapter);
		lvSheet.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				viewSheet(adapter.getItem(position));
			}
		});
		
		registerForContextMenu(lvSheet);
		
		
		
		
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		if (v.getId()==R.id.lvSheet) {
	          MenuInflater inflater = getMenuInflater();
	          inflater.inflate(R.menu.menu_sheet, menu);
	      }
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {

		AdapterView.AdapterContextMenuInfo menuinfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
	      switch(item.getItemId()) {
	         
	          case R.id.delete:
	        	  	deleteSheet(adapter.getItem(menuinfo.position));
	                return true;
	          default:
	                return super.onContextItemSelected(item);
	      }
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		adapter.clear();
		adapter.addAll(dao.getList());
		adapter.notifyDataSetChanged();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.sheet, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if(id == R.id.action_new_sheet){
			newSheet();
		}
		return super.onOptionsItemSelected(item);
	}
	private void viewSheet(Sheet sheet){
		Intent intent = new Intent(this, SheetDetailActivity.class);
		Log.i(tag, "viewSheet, sheetId : " + sheet.getId());
		intent.putExtra(SheetDetailActivity.SHEET, sheet);
		startActivity(intent);
	}
	private void newSheet(){
		Intent intent = new Intent(this, SheetDetailActivity.class);
		startActivity(intent);
	}
	private void deleteSheet(final Sheet sheet){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Confirm")
		.setMessage("Delete sheet?")
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				dao.delete(sheet);
				onResume();
			}
		})
		.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		builder.create().show();
	}
	private class SheetListAdapter extends ArrayAdapter<Sheet>{

		public SheetListAdapter(Context context) {
			super(context, 0);
		}
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return getItem(position).getId();
		}
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_sheet_list, null);
			}
			
			TextView tvSheetName = (TextView) convertView.findViewById(R.id.tvSheetName);
			TextView tvChecklistName = (TextView) convertView.findViewById(R.id.tvChecklistName);
			TextView tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);

			Sheet sheet = getItem(position);
			tvSheetName.setText(sheet.getName());
			tvChecklistName.setText(sheet.getChecklistName());
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy  HH:mm");
			tvDescription.setText(sheet.getAuditTypeName() + "  [ " + sdf.format(sheet.getAuditDate())+" ]");
			
			if((position%2)==0)
				convertView.setBackgroundColor(Color.parseColor("#dddddd"));
			else
				convertView.setBackgroundColor(Color.parseColor("#eeeeee"));
			return convertView;
		}		
	}
}
