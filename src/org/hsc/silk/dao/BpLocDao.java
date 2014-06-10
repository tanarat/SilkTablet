package org.hsc.silk.dao;

import org.hsc.silk.db.SILKOpenHelper;
import org.hsc.silk.db.SILKProvider;
import org.hsc.silk.model.BpLocation;
import org.hsc.silk.table.BpLocationTable;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BpLocDao extends Dao<BpLocation> {
	public static final String tag = "BpLocationDao";
	protected SILKOpenHelper dbHelper;
	protected SQLiteDatabase db;
	public BpLocDao(Context context) {
		super(BpLocation.class, context, SILKProvider.BP_LOCATION_CONTENT_URI);
		dbHelper = new SILKOpenHelper(context);
	}
	private boolean isExist(int id, SQLiteDatabase db){
		boolean exist = false;
		
		StringBuilder sb = new StringBuilder();
		sb.append("Select " + BpLocationTable.BpLocationColumns._ID );
		sb.append(" from " + BpLocationTable.TABLE_NAME);
		sb.append(" where " + BpLocationTable.BpLocationColumns._ID + " = ?");
		
		Cursor cursor = db.rawQuery(sb.toString(), new String[]{String.valueOf(id)});
		Log.i(tag, "cursor count : " + cursor.getCount());
		if(cursor.getCount() > 0){
			exist = true;
		}
		cursor.close();
		
		return exist;
	}
	public void save(BpLocation bp){
		if(getById(bp.getId())!=null){
			update(bp);
		}else{
			insert(bp);
		}
	}
}
