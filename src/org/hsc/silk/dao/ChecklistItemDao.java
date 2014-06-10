package org.hsc.silk.dao;

import java.util.List;

import org.hsc.silk.db.SILKOpenHelper;
import org.hsc.silk.db.SILKProvider;
import org.hsc.silk.model.Checklist;
import org.hsc.silk.model.ChecklistItem;
import org.hsc.silk.table.ChecklistItemTable;
import org.hsc.silk.table.ChecklistTable;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ChecklistItemDao extends Dao<ChecklistItem> {
	public static final String tag = "ChecklistItemDao";

	protected SILKOpenHelper dbHelper;
	protected SQLiteDatabase db;
	
	public ChecklistItemDao(Context context) {
		super(ChecklistItem.class, context, SILKProvider.CHECKLIST_ITEM_CONTENT_URI);
		dbHelper = new SILKOpenHelper(context);
	}
	private boolean isExist(int id, SQLiteDatabase db){
		boolean exist = false;
		
		StringBuilder sb = new StringBuilder();
		sb.append("Select " + ChecklistItemTable.ChecklistItemColumns._ID );
		sb.append(" from " + ChecklistItemTable.TABLE_NAME);
		sb.append(" where " + ChecklistItemTable.ChecklistItemColumns._ID + " = ?");
		
		Cursor cursor = db.rawQuery(sb.toString(), new String[]{String.valueOf(id)});
		Log.i(tag, "cursor count : " + cursor.getCount());
		if(cursor.getCount() > 0){
			exist = true;
		}
		cursor.close();
		
		return exist;
	}
	public void save(List<ChecklistItem> checklistItemList) {
		db = dbHelper.getWritableDatabase();
		db.beginTransaction();
		for (ChecklistItem obj : checklistItemList) {
			if(isExist(obj.getId(), db)){
				//update
				db.update(ChecklistItemTable.TABLE_NAME, obj.toContentValues(), ChecklistItemTable.ChecklistItemColumns._ID + " = ?",new String[]{ String.valueOf(obj.getId())});
				Log.i(tag, "update checklist item : " + obj.getId());
			}else{
				//insert
				db.insert(ChecklistItemTable.TABLE_NAME, null, obj.toContentValues());
				Log.i(tag, "insert checklist item : " + obj.getId());
			}
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();
	}
	
	
}
