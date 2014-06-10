package org.hsc.silk.dao;

import java.util.List;

import org.hsc.silk.db.SILKOpenHelper;
import org.hsc.silk.db.SILKProvider;
import org.hsc.silk.model.Checklist;
import org.hsc.silk.table.ChecklistTable;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ChecklistDao extends Dao<Checklist> {
	public static final String tag = "ChecklistDao";
	
	protected SILKOpenHelper dbHelper;
	protected SQLiteDatabase db;

	public ChecklistDao(Context context) {
		super(Checklist.class, context, SILKProvider.CHECKLIST_CONTENT_URI);
		dbHelper = new SILKOpenHelper(context);
	}

	private boolean isExist(int id, SQLiteDatabase db){
		boolean exist = false;
		
		StringBuilder sb = new StringBuilder();
		sb.append("Select " + ChecklistTable.ChecklistColumns._ID );
		sb.append(" from " + ChecklistTable.TABLE_NAME);
		sb.append(" where " + ChecklistTable.ChecklistColumns._ID + " = ?");
		
		Cursor cursor = db.rawQuery(sb.toString(), new String[]{String.valueOf(id)});
		Log.i(tag, "cursor count : " + cursor.getCount());
		if(cursor.getCount() > 0){
			exist = true;
		}
		cursor.close();
		
		return exist;
	}
	
	public void save(List<Checklist> checklistList) {
		db = dbHelper.getWritableDatabase();
		db.beginTransaction();
		for (Checklist checklist : checklistList) {
			if(isExist(checklist.getId(), db)){
				//update
				db.update(ChecklistTable.TABLE_NAME, checklist.toContentValues(), ChecklistTable.ChecklistColumns._ID + " = ?",new String[]{ String.valueOf(checklist.getId())});
				Log.i(tag, "update checklist : " + checklist.getId());
			}else{
				//insert
				db.insert(ChecklistTable.TABLE_NAME, null, checklist.toContentValues());
				Log.i(tag, "insert checklist : " + checklist.getId());
			}
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		
		db.close();
	}

	
	
}
