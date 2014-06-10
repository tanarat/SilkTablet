package org.hsc.silk.dao;

import java.util.List;

import org.hsc.silk.db.SILKOpenHelper;
import org.hsc.silk.db.SILKProvider;
import org.hsc.silk.model.Checklist;
import org.hsc.silk.model.Regulation;
import org.hsc.silk.table.ChecklistTable;
import org.hsc.silk.table.RegulationTable;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class RegulationDao extends Dao<Regulation> {
	public static final String tag = "RegulationDao";
	
	protected SILKOpenHelper dbHelper;
	protected SQLiteDatabase db;

	public RegulationDao(Context context) {
		super(Regulation.class, context, SILKProvider.REGULATION_CONTENT_URI);
		dbHelper = new SILKOpenHelper(context);
	}

	private boolean isExist(int id, SQLiteDatabase db){
		boolean exist = false;
		
		StringBuilder sb = new StringBuilder();
		sb.append("Select " + RegulationTable.RegulationColumns._ID );
		sb.append(" from " + RegulationTable.TABLE_NAME);
		sb.append(" where " + RegulationTable.RegulationColumns._ID + " = ?");
		
		Cursor cursor = db.rawQuery(sb.toString(), new String[]{String.valueOf(id)});
		Log.i(tag, "cursor count : " + cursor.getCount());
		if(cursor.getCount() > 0){
			exist = true;
		}
		cursor.close();
		
		return exist;
	}
	
	public void save(List<Regulation> list) {
		db = dbHelper.getWritableDatabase();
		db.beginTransaction();
		for (Regulation obj : list) {
			if(isExist(obj.getId(), db)){
				//update
				db.update(RegulationTable.TABLE_NAME, obj.toContentValues(), RegulationTable.RegulationColumns._ID + " = ?",new String[]{ String.valueOf(obj.getId())});
				Log.i(tag, "update regulation : " + obj.getId());
			}else{
				//insert
				db.insert(RegulationTable.TABLE_NAME, null, obj.toContentValues());
				Log.i(tag, "insert regulation : " + obj.getId());
			}
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		
		db.close();
	}
	
	
}
