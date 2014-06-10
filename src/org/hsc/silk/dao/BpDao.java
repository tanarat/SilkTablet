package org.hsc.silk.dao;

import org.hsc.silk.db.SILKOpenHelper;
import org.hsc.silk.db.SILKProvider;
import org.hsc.silk.model.Bpartner;
import org.hsc.silk.table.BpartnerTable;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BpDao extends Dao<Bpartner> {
	public static final String tag = "BpartnerDao";
	protected SILKOpenHelper dbHelper;
	protected SQLiteDatabase db;
	public BpDao(Context context) {
		super(Bpartner.class, context, SILKProvider.BPARTNER_CONTENT_URI);
		dbHelper = new SILKOpenHelper(context);
	}
	private boolean isExist(int id, SQLiteDatabase db){
		boolean exist = false;
		
		StringBuilder sb = new StringBuilder();
		sb.append("Select " + BpartnerTable.BpartnerColumns._ID );
		sb.append(" from " + BpartnerTable.TABLE_NAME);
		sb.append(" where " + BpartnerTable.BpartnerColumns._ID + " = ?");
		
		Cursor cursor = db.rawQuery(sb.toString(), new String[]{String.valueOf(id)});
		Log.i(tag, "cursor count : " + cursor.getCount());
		if(cursor.getCount() > 0){
			exist = true;
		}
		cursor.close();
		
		return exist;
	}
	public void save(Bpartner bp){
		if(getById(bp.getId())!=null){
			update(bp);
		}else{
			insert(bp);
		}
	}
}
