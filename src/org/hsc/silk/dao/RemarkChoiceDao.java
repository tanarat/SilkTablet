package org.hsc.silk.dao;

import java.util.List;

import org.hsc.silk.db.SILKOpenHelper;
import org.hsc.silk.db.SILKProvider;
import org.hsc.silk.model.RemarkChoice;
import org.hsc.silk.table.RemarkChoiceTable;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class RemarkChoiceDao extends Dao<RemarkChoice> {
	public static final String tag = "RemarkChoiceDao";
	
	protected SILKOpenHelper dbHelper;
	protected SQLiteDatabase db;

	public RemarkChoiceDao(Context context) {
		super(RemarkChoice.class, context, SILKProvider.REMARK_CHOICE_CONTENT_URI);
		dbHelper = new SILKOpenHelper(context);
	}

	private boolean isExist(int id, SQLiteDatabase db){
		boolean exist = false;
		
		StringBuilder sb = new StringBuilder();
		sb.append("Select " + RemarkChoiceTable.RemarkChoiceColumns._ID );
		sb.append(" from " + RemarkChoiceTable.TABLE_NAME);
		sb.append(" where " + RemarkChoiceTable.RemarkChoiceColumns._ID + " = ?");
		
		Cursor cursor = db.rawQuery(sb.toString(), new String[]{String.valueOf(id)});
		Log.i(tag, "cursor count : " + cursor.getCount());
		if(cursor.getCount() > 0){
			exist = true;
		}
		cursor.close();
		
		return exist;
	}
	
	public void save(List<RemarkChoice> list) {
		db = dbHelper.getWritableDatabase();
		db.beginTransaction();
		for (RemarkChoice obj : list) {
			if(isExist(obj.getId(), db)){
				//update
				db.update(RemarkChoiceTable.TABLE_NAME, obj.toContentValues(), RemarkChoiceTable.RemarkChoiceColumns._ID + " = ?",new String[]{ String.valueOf(obj.getId())});
				Log.i(tag, "update remark choice : " + obj.getId());
			}else{
				//insert
				db.insert(RemarkChoiceTable.TABLE_NAME, null, obj.toContentValues());
				Log.i(tag, "insert remark choice : " + obj.getId());
			}
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		
		db.close();
	}

	public List<RemarkChoice> getRemarkChoiceList(int checklistItemId) {
		String selection = RemarkChoiceTable.RemarkChoiceColumns.CHECKLIST_ITEM_ID + " = ?";
		String[] selectionArgs = {String.valueOf(checklistItemId)};
		return get(selection, selectionArgs);
	}
	
	
}
