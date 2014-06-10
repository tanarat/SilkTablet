package org.hsc.silk.dao;

import java.util.List;

import org.hsc.silk.db.SILKOpenHelper;
import org.hsc.silk.db.SILKProvider;
import org.hsc.silk.model.AuditType;
import org.hsc.silk.model.Checklist;
import org.hsc.silk.model.RegulationItem;
import org.hsc.silk.table.AuditTypeTable;
import org.hsc.silk.table.ChecklistTable;
import org.hsc.silk.table.RegulationItemTable;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AuditTypeDao extends Dao<AuditType> {
	public static final String tag = "AuditTypeDao";
	
	protected SILKOpenHelper dbHelper;
	protected SQLiteDatabase db;

	public AuditTypeDao(Context context) {
		super(AuditType.class, context, SILKProvider.AUDIT_TYPE_CONTENT_URI);
		dbHelper = new SILKOpenHelper(context);
	}

	private boolean isExist(int id, SQLiteDatabase db){
		boolean exist = false;
		
		StringBuilder sb = new StringBuilder();
		sb.append("Select " + AuditTypeTable.AuditTypeColumns._ID );
		sb.append(" from " + AuditTypeTable.TABLE_NAME);
		sb.append(" where " + AuditTypeTable.AuditTypeColumns._ID + " = ?");
		
		Cursor cursor = db.rawQuery(sb.toString(), new String[]{String.valueOf(id)});
		Log.i(tag, "cursor count : " + cursor.getCount());
		if(cursor.getCount() > 0){
			exist = true;
		}
		cursor.close();
		
		return exist;
	}
	
	public void save(List<AuditType> list) {
		db = dbHelper.getWritableDatabase();
		db.beginTransaction();
		for (AuditType obj : list) {
			if(isExist(obj.getId(), db)){
				//update
				db.update(AuditTypeTable.TABLE_NAME, obj.toContentValues(), AuditTypeTable.AuditTypeColumns._ID + " = ?",new String[]{ String.valueOf(obj.getId())});
				Log.i(tag, "update audit type : " + obj.getId());
			}else{
				//insert
				db.insert(AuditTypeTable.TABLE_NAME, null, obj.toContentValues());
				Log.i(tag, "insert audit type : " + obj.getId());
			}
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		
		db.close();
	}
	
	
}
