package org.hsc.silk.dao;

import java.util.ArrayList;
import java.util.List;

import org.hsc.silk.db.SILKOpenHelper;
import org.hsc.silk.db.SILKProvider;
import org.hsc.silk.model.RegulationItem;
import org.hsc.silk.table.RegulationItemTable;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class RegulationItemDao extends Dao<RegulationItem> {
	public static final String tag = "RegulationItemDao";
	
	protected SILKOpenHelper dbHelper;
	protected SQLiteDatabase db;

	public RegulationItemDao(Context context) {
		super(RegulationItem.class, context, SILKProvider.REGULATION_ITEM_CONTENT_URI);
		dbHelper = new SILKOpenHelper(context);
	}

	private boolean isExist(int id, SQLiteDatabase db){
		boolean exist = false;
		
		StringBuilder sb = new StringBuilder();
		sb.append("Select " + RegulationItemTable.RegulationItemColumns._ID );
		sb.append(" from " + RegulationItemTable.TABLE_NAME);
		sb.append(" where " + RegulationItemTable.RegulationItemColumns._ID + " = ?");
		
		Cursor cursor = db.rawQuery(sb.toString(), new String[]{String.valueOf(id)});
		Log.i(tag, "cursor count : " + cursor.getCount());
		if(cursor.getCount() > 0){
			exist = true;
		}
		cursor.close();
		
		return exist;
	}
	
	public void save(List<RegulationItem> list) {
		db = dbHelper.getWritableDatabase();
		db.beginTransaction();
		for (RegulationItem obj : list) {
			if(isExist(obj.getId(), db)){
				//update
				db.update(RegulationItemTable.TABLE_NAME, obj.toContentValues(), RegulationItemTable.RegulationItemColumns._ID + " = ?",new String[]{ String.valueOf(obj.getId())});
				Log.i(tag, "update regulation item : " + obj.getId());
			}else{
				//insert
				db.insert(RegulationItemTable.TABLE_NAME, null, obj.toContentValues());
				Log.i(tag, "insert regulation item : " + obj.getId());
			}
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		
		db.close();
	}

	public List<RegulationItem> getRegulationItemListOfSheet(int sheetId) {
		db = dbHelper.getReadableDatabase();
		StringBuilder b = new StringBuilder();
		b.append(" select a.reg_item_id, c.reg_item_value, c.reg_item_name, c.isgroup, a.no_sheet_item, b.no_conform from");
		b.append(" (");
		b.append(" select reg_item.reg_item_id, count(answer) as no_sheet_item");
		b.append(" from (select _id as reg_item_id from regulation_item_table where regulation_id_column = ( select _id from regulation_table where _id = ( select regulation_id_column from checklist_table where _id = ( select checklist_id_column from sheet_table where _id = " + sheetId+ " ))) order by value_column) as reg_item left outer join (select regulation_item_id_column as reg_item_id, answer_column as answer from sheet_item_table where sheet_id_column = " + sheetId+ " ) as sheet_item on reg_item.reg_item_id = sheet_item.reg_item_id");
		b.append(" group by reg_item.reg_item_id");
		b.append(" ) as a"); 
		b.append(" left outer join"); 
		b.append(" (");
		b.append(" select reg_item.reg_item_id, count(answer) as no_conform");
		b.append(" from (select _id as reg_item_id from regulation_item_table where regulation_id_column = ( select _id from regulation_table where _id = ( select regulation_id_column from checklist_table where _id = ( select checklist_id_column from sheet_table where _id = " + sheetId+ " ))) order by value_column) as reg_item left outer join (select regulation_item_id_column as reg_item_id, answer_column as answer from sheet_item_table where sheet_id_column = " + sheetId+ " and answer = 1) as sheet_item on reg_item.reg_item_id = sheet_item.reg_item_id");
		b.append(" group by reg_item.reg_item_id"); 
		b.append(" ) as b"); 
		b.append(" on a.reg_item_id = b.reg_item_id"); 
		b.append(" left outer join");
		b.append(" (select _id, name_column as reg_item_name, value_column as reg_item_value, is_group_column as isgroup");
		b.append(" from regulation_item_table");
		b.append(" ) as c");
		b.append(" on a.reg_item_id = c._id");
		b.append(" order by c.reg_item_value");
		
		Cursor cursor = db.rawQuery(b.toString(), null);
		cursor.moveToFirst();
		List<RegulationItem> results = new ArrayList<RegulationItem>();
		while (!cursor.isAfterLast()) {
			RegulationItem obj = new RegulationItem();
			obj.setId(sheetId);
			obj.setId(cursor.getInt(cursor.getColumnIndex("reg_item_id")));
			obj.setValue(cursor.getString(cursor.getColumnIndex("reg_item_value")));
			obj.setName(cursor.getString(cursor.getColumnIndex("reg_item_name")));
			obj.setGroup(cursor.getInt(cursor.getColumnIndex("isgroup")) == 1);
			
//			obj.setNoSheetItem(cursor.getInt(cursor.getColumnIndex("no_sheet_item")));
//			obj.setNoConformItem(cursor.getInt(cursor.getColumnIndex("no_conform")));
			results.add(obj);
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return results;
	}
	
	
}
