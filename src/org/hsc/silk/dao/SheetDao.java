package org.hsc.silk.dao;

import java.util.ArrayList;
import java.util.List;

import org.hsc.silk.db.SILKOpenHelper;
import org.hsc.silk.db.SILKProvider;
import org.hsc.silk.model.AuditType;
import org.hsc.silk.model.Sheet;
import org.hsc.silk.model.SheetItem;
import org.hsc.silk.table.AuditTypeTable;
import org.hsc.silk.table.BpLocationTable;
import org.hsc.silk.table.ChecklistTable;
import org.hsc.silk.table.SheetTable;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class SheetDao extends Dao<Sheet> {
	public static final String tag = "SheetDao";
	private Context mContext;
	protected SILKOpenHelper dbHelper;
	protected SQLiteDatabase db;
	
	public SheetDao(Context context) {
		super(Sheet.class, context, SILKProvider.SHEET_CONTENT_URI);
		this.mContext = context;
		dbHelper = new SILKOpenHelper(context);
	}
	private boolean isExist(int id, SQLiteDatabase db){
		boolean exist = false;
		
		StringBuilder sb = new StringBuilder();
		sb.append("Select " + SheetTable.SheetColumns._ID );
		sb.append(" from " + SheetTable.TABLE_NAME);
		sb.append(" where " + SheetTable.SheetColumns._ID + " = ?");
		
		Cursor cursor = db.rawQuery(sb.toString(), new String[]{String.valueOf(id)});
		Log.i(tag, "cursor count : " + cursor.getCount());
		if(cursor.getCount() > 0){
			exist = true;
		}
		cursor.close();
		
		return exist;
	}
	public void save(Sheet sheet){
		Uri uri;
		if(sheet.getId() != 0 && getById(sheet.getId()) != null){
			update(sheet);
		}else{
			uri = insert(sheet);
			sheet.setId((int) ContentUris.parseId(uri));
		}
	}
	public List<Sheet> getList(){
		db = dbHelper.getReadableDatabase();
		List<Sheet> list = new ArrayList<Sheet>();
		
		StringBuilder sb = new StringBuilder();
		sb.append("select ");
		sb.append(" sheet." + SheetTable.SheetColumns._ID);
		sb.append(", sheet." + SheetTable.SheetColumns.NAME);
		sb.append(", sheet." + SheetTable.SheetColumns.CHECKLIST_ID);
		sb.append(", sheet." + SheetTable.SheetColumns.BPARTNER_ID);
		sb.append(", sheet." + SheetTable.SheetColumns.BP_LOCATION_ID);
		sb.append(", sheet." + SheetTable.SheetColumns.AUDIT_TYPE);
		sb.append(", sheet." + SheetTable.SheetColumns.AUDIT_DATE);
		sb.append(", sheet." + SheetTable.SheetColumns.AUDIT_TIMESLOT);
		sb.append(", sheet." + SheetTable.SheetColumns.STATUS);
		sb.append(", sheet." + SheetTable.SheetColumns.CREATE_SHEET_ITEM);
		sb.append(", checklist." + ChecklistTable.ChecklistColumns.NAME + " as checklist_name");
		sb.append(", audit_type." + AuditTypeTable.AuditTypeColumns.NAME + " as audit_type_name");
		sb.append(", sheet." + SheetTable.SheetColumns.AUDIT_DATE + " ");
		sb.append("from (((" + SheetTable.TABLE_NAME + " as sheet ");
		sb.append("left outer join " + ChecklistTable.TABLE_NAME + " as checklist ");
		sb.append("on sheet." + SheetTable.SheetColumns.CHECKLIST_ID + " = checklist." + ChecklistTable.ChecklistColumns._ID + ")");
		sb.append("left outer join " + AuditTypeTable.TABLE_NAME + " as audit_type ");
		sb.append("on sheet." + SheetTable.SheetColumns.AUDIT_TYPE + " = audit_type." + AuditTypeTable.AuditTypeColumns._ID + ")");
		sb.append("left outer join " + BpLocationTable.TABLE_NAME + " as bp_loc ");
		sb.append("on sheet." + SheetTable.SheetColumns.BP_LOCATION_ID + " = bp_loc." + BpLocationTable.BpLocationColumns._ID + ")");
		sb.append("order by sheet." + SheetTable.SheetColumns._ID + " desc");
		
		Log.i(tag, sb.toString());
		
		Cursor cursor = db.rawQuery(sb.toString(), null);
		
		while (cursor.moveToNext()) {
			list.add(Sheet.newInstance(cursor, mContext));
		}
		cursor.close();
		db.close();
		return list;
	}
	@Override
	public int delete(Sheet sheet) {
		// TODO Auto-generated method stub
		int ret = super.delete(sheet);
		
		SheetItemDao sheetItemDao = new SheetItemDao(mContext);
		
		List<SheetItem> sheetItemList = sheetItemDao.getSheetItemList(sheet.getId());
		for (SheetItem sheetItem : sheetItemList) {
			sheetItemDao.delete(sheetItem);
		}
		Log.i(tag, "delete " + ret + "sheet, and " + sheetItemList.size() + " sheet items");
		return ret;
	}
}
