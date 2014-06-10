package org.hsc.silk.model;

import java.util.ArrayList;
import java.util.List;

import org.hsc.silk.db.SILKOpenHelper;



import org.hsc.silk.table.SheetItemTable;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RegItemRow {
	
	private int sheetId;
	private int regItemId;
	private String regItemValue;
	private String regItemName;
	private boolean isGroup;
	private int noSheetItem;
	private int noConformItem;
	
	private static SILKOpenHelper dbHelper;
	private static SQLiteDatabase db;
	public static List<RegItemRow> getList(int sheetId, Context context){
		
		dbHelper = new SILKOpenHelper(context);
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
		List<RegItemRow> results = new ArrayList<RegItemRow>();
		while (!cursor.isAfterLast()) {
			RegItemRow obj = new RegItemRow();
			obj.setSheetId(sheetId);
			obj.setRegItemId(cursor.getInt(cursor.getColumnIndex("reg_item_id")));
			obj.setRegItemValue(cursor.getString(cursor.getColumnIndex("reg_item_value")));
			obj.setRegItemName(cursor.getString(cursor.getColumnIndex("reg_item_name")));
			obj.setGroup(cursor.getInt(cursor.getColumnIndex("isgroup")) == 1);
			obj.setNoSheetItem(cursor.getInt(cursor.getColumnIndex("no_sheet_item")));
			obj.setNoConformItem(cursor.getInt(cursor.getColumnIndex("no_conform")));
			results.add(obj);
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return results;
	}
	public void updateNoConformItem(Context context) {
		SILKOpenHelper dbHelper = new SILKOpenHelper(context);
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		StringBuilder b = new StringBuilder();
		b.append("select count(*) from " + SheetItemTable.TABLE_NAME);
		b.append(" where " + SheetItemTable.SheetItemColumns.SHEET_ID + " = ?");
		b.append(" and " + SheetItemTable.SheetItemColumns.REGULATION_ITEM_ID + " = ?");
		b.append(" and " + SheetItemTable.SheetItemColumns.ANSWER + " = ?");
		Cursor cursor = db.rawQuery(b.toString(), new String[] { String.valueOf(sheetId), String.valueOf(regItemId), "1" });
		cursor.moveToFirst();
		setNoConformItem(cursor.getInt(0));
		cursor.close();
		db.close();
		
	}
	public int getSheetId() {
		return sheetId;
	}
	public void setSheetId(int sheetId) {
		this.sheetId = sheetId;
	}
	public int getRegItemId() {
		return regItemId;
	}
	public void setRegItemId(int regItemId) {
		this.regItemId = regItemId;
	}
	public String getRegItemValue() {
		return regItemValue;
	}
	public void setRegItemValue(String regItemValue) {
		this.regItemValue = regItemValue;
	}
	public String getRegItemName() {
		return regItemName;
	}
	public void setRegItemName(String regItemName) {
		this.regItemName = regItemName;
	}
	public int getNoSheetItem() {
		return noSheetItem;
	}
	public void setNoSheetItem(int noSheetItem) {
		this.noSheetItem = noSheetItem;
	}
	public int getNoConformItem() {
		return noConformItem;
	}
	public void setNoConformItem(int noConformItem) {
		this.noConformItem = noConformItem;
	}

	public boolean isGroup() {
		return isGroup;
	}

	public void setGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}

	
}
