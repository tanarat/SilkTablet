package org.hsc.silk.table;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;


public final class ChecklistItemTable {
    public static final String TABLE_NAME = "checklist_item_table";

    public static class ChecklistItemColumns implements BaseColumns {
        public static final String NAME = "name_column";
        public static final String VALUE = "value_column";
        public static final String CHECKLIST_ID = "checklist_id_column";
        public static final String REGULATION_ITEM_ID = "regulation_item_id_column";
    }



    public static void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + ChecklistItemTable.TABLE_NAME + " (");
        sb.append(BaseColumns._ID + " INT PRIMARY KEY NOT NULL, ");
        sb.append(ChecklistItemColumns.NAME + " VARCHAR(100), ");
        sb.append(ChecklistItemColumns.VALUE + " VARCHAR(10), ");
        sb.append(ChecklistItemColumns.CHECKLIST_ID + " INTEGER, ");
        sb.append(ChecklistItemColumns.REGULATION_ITEM_ID + " INTEGER");
        sb.append(");");
        db.execSQL(sb.toString());
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ChecklistItemTable.TABLE_NAME);
        ChecklistItemTable.onCreate(db);
    }


}