package org.hsc.silk.table;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;


public final class SheetItemTable {
    public static final String TABLE_NAME = "sheet_item_table";

    public static class SheetItemColumns implements BaseColumns {
    	public static final String SHEET_ID = "sheet_id_column";
        public static final String CHECKLIST_ITEM_ID = "checklist_item_id_column";
        public static final String ANSWER = "answer_column";
        public static final String REMARK = "remark_column";
		public static final String REGULATION_ITEM_ID = "regulation_item_id_column";
		public static final String REMARK_CHOICE = "remark_choice_column";
    }



    public static void onCreate(SQLiteDatabase db) {
    	StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + SheetItemTable.TABLE_NAME + " (");
        sb.append(BaseColumns._ID + " INTEGER PRIMARY KEY, ");
        sb.append(SheetItemColumns.SHEET_ID + " INT, ");
        sb.append(SheetItemColumns.CHECKLIST_ITEM_ID + " INT, ");
        sb.append(SheetItemColumns.REGULATION_ITEM_ID + " INT, ");
        sb.append(SheetItemColumns.ANSWER + " INT, ");
        sb.append(SheetItemColumns.REMARK + " VARCHAR(500), ");
        sb.append(SheetItemColumns.REMARK_CHOICE + " VARCHAR(50) ");
        sb.append(");");
        db.execSQL(sb.toString());
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SheetItemTable.TABLE_NAME);
        SheetItemTable.onCreate(db);
    }


}