package org.hsc.silk.dao;

import java.util.ArrayList;
import java.util.List;

import org.hsc.silk.db.SILKOpenHelper;
import org.hsc.silk.db.SILKProvider;
import org.hsc.silk.model.SheetItem;
import org.hsc.silk.table.ChecklistItemTable;
import org.hsc.silk.table.RegulationItemTable;
import org.hsc.silk.table.SheetItemTable;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SheetItemDao extends Dao<SheetItem> {
	public static final String tag = "SheetItemDao";
	protected SILKOpenHelper dbHelper;
	protected SQLiteDatabase db;
	private Context mContext;
	public SheetItemDao(Context context) {
		super(SheetItem.class, context, SILKProvider.SHEET_ITEM_CONTENT_URI);
		this.mContext = context;
		dbHelper = new SILKOpenHelper(context);
	}
	private boolean isExist(int id, SQLiteDatabase db){
		boolean exist = false;
		
		StringBuilder sb = new StringBuilder();
		sb.append("Select " + SheetItemTable.SheetItemColumns._ID );
		sb.append(" from " + SheetItemTable.TABLE_NAME);
		sb.append(" where " + SheetItemTable.SheetItemColumns._ID + " = ?");
		
		Cursor cursor = db.rawQuery(sb.toString(), new String[]{String.valueOf(id)});
		Log.i(tag, "cursor count : " + cursor.getCount());
		if(cursor.getCount() > 0){
			exist = true;
		}
		cursor.close();
		
		return exist;
	}
	public void save(SheetItem sheetItem){
		if(getById(sheetItem.getId())!=null){
			update(sheetItem);
		}else{
			insert(sheetItem);
		}
	}
	public void createSheetItemList(int checklistId, int sheetId){ 
		db = dbHelper.getWritableDatabase();
		StringBuilder sb = new StringBuilder();
		sb.append("	Select ");
		sb.append(ChecklistItemTable.ChecklistItemColumns._ID + ", ");
		sb.append(ChecklistItemTable.ChecklistItemColumns.REGULATION_ITEM_ID);
		sb.append(" from " + ChecklistItemTable.TABLE_NAME);
		sb.append(" where " + ChecklistItemTable.ChecklistItemColumns.CHECKLIST_ID + " = ?");
		
		Cursor cursor = db.rawQuery(sb.toString(), new String[]{String.valueOf(checklistId)});
		while (cursor.moveToNext()) {
			
			SheetItem sheetItem = new SheetItem();
			int checklistItemId = cursor.getInt(cursor.getColumnIndex(ChecklistItemTable.ChecklistItemColumns._ID));
			int regulationItemId = cursor.getInt(cursor.getColumnIndex(ChecklistItemTable.ChecklistItemColumns.REGULATION_ITEM_ID));
			
			sheetItem.setChecklistItemId(checklistItemId);
			sheetItem.setRegulationItemId(regulationItemId);
			sheetItem.setSheetId(sheetId);
			save(sheetItem);
			
//			Log.i(tag, "[sheetId : " +  sheetId + ", checklistItemId : " + checklistItemId + ", regulationItemId : " + regulationItemId);
			
		}
		db.close();
	}
	public List<SheetItem> getSheetItemList(int sheetId, int regulationItemId) {
		db = dbHelper.getReadableDatabase();
		StringBuilder b = new StringBuilder();
		b.append("select ");
		b.append(" a." + SheetItemTable.SheetItemColumns._ID);
		b.append(", a." + SheetItemTable.SheetItemColumns.SHEET_ID);
		b.append(", a." + SheetItemTable.SheetItemColumns.CHECKLIST_ITEM_ID);
		b.append(", a." + SheetItemTable.SheetItemColumns.REGULATION_ITEM_ID);
		b.append(", a." + SheetItemTable.SheetItemColumns.ANSWER);
		b.append(", a." + SheetItemTable.SheetItemColumns.REMARK);
		b.append(", a." + SheetItemTable.SheetItemColumns.REMARK_CHOICE);
		b.append(", b." + ChecklistItemTable.ChecklistItemColumns.VALUE);
		b.append(" from " + SheetItemTable.TABLE_NAME + " a");
		b.append(", " + ChecklistItemTable.TABLE_NAME + " b");
		b.append(" where a." + SheetItemTable.SheetItemColumns.SHEET_ID + " = ?");
		b.append(" and a." + SheetItemTable.SheetItemColumns.REGULATION_ITEM_ID + " = ?");
		b.append(" and a." + SheetItemTable.SheetItemColumns.CHECKLIST_ITEM_ID + " = " );
		b.append(" b." + ChecklistItemTable.ChecklistItemColumns._ID);
		b.append(" order by b." + ChecklistItemTable.ChecklistItemColumns.VALUE);
		
//		Log.i(tag, b.toString());
		Cursor cursor = db.rawQuery(b.toString(), new String[] { String.valueOf(sheetId), String.valueOf(regulationItemId) });
		cursor.moveToFirst();
		List<SheetItem> results = new ArrayList<SheetItem>();
		while (!cursor.isAfterLast()) {
			SheetItem object = SheetItem.newInstance(cursor,mContext);
			object.fromCursor(cursor, mContext);
			results.add(object);
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return results;
	}
	public SheetItem getSheetItem(int sheetId, int checklistItemId) {
		String selection = SheetItemTable.SheetItemColumns.SHEET_ID + " = ? and "
				+ SheetItemTable.SheetItemColumns.CHECKLIST_ITEM_ID + " = ?";
		String[] selectionArgs = {String.valueOf(sheetId) , String.valueOf(checklistItemId)};
		List<SheetItem> sheetItemList = (List<SheetItem>) get(selection, selectionArgs);
		if(sheetItemList != null && sheetItemList.size() != 0){
			SheetItem sheetItem = sheetItemList.get(0);
//			sheetItem.setChecklistItem(checklistItemDao.getById(checklistItemId));
			return sheetItem;
		}else{
			return null;
		}
	}
	public List<SheetItem> getSheetItemList(int sheetId) {
		String selection = SheetItemTable.SheetItemColumns.SHEET_ID + " = ? ";
		String[] selectionArgs = {String.valueOf(sheetId)};
		return (List<SheetItem>) get(selection, selectionArgs);
	}
	
	/*
	 * number of sheet items for sheet
	 */
	public int countSheetItem(int sheetId) {
		int sheetItemCount = 0;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		StringBuilder b = new StringBuilder();
		b.append(" select count(*) from " + SheetItemTable.TABLE_NAME);
		b.append(" where " + SheetItemTable.SheetItemColumns.SHEET_ID
				+ " = ?");

		Cursor cursor = db.rawQuery(b.toString(),
				new String[] { String.valueOf(sheetId) });
		cursor.moveToFirst();
		sheetItemCount = cursor.getInt(0);
		cursor.close();
		db.close();
		return sheetItemCount;
	}
	public int countConformItem(int sheetId) {
		int count = 0;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		StringBuilder b = new StringBuilder();
		b.append(" select count(*) from " + SheetItemTable.TABLE_NAME);
		b.append(" where " + SheetItemTable.SheetItemColumns.SHEET_ID + " = ? ");
		b.append(" and " + SheetItemTable.SheetItemColumns.ANSWER + " = ?");
		Cursor cursor = db.rawQuery(b.toString(),
				new String[] { String.valueOf(sheetId), "1" });
		cursor.moveToFirst();
		count = cursor.getInt(0);
		cursor.close();
		db.close();
		return count;
	}
}
