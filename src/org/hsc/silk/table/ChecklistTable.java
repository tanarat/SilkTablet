package org.hsc.silk.table;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;


public final class ChecklistTable {
    public static final String TABLE_NAME = "checklist_table";

    public static class ChecklistColumns implements BaseColumns {
        public static final String NAME = "name_column";
        public static final String DESCRIPTION = "description_column";
        public static final String REGULATION_ID = "regulation_id_column";
    }



    public static void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + ChecklistTable.TABLE_NAME + " (");
        sb.append(BaseColumns._ID + " INT PRIMARY KEY NOT NULL, ");
        sb.append(ChecklistColumns.NAME + " VARCHAR(100), ");
        sb.append(ChecklistColumns.DESCRIPTION + " VARCHAR(100), ");
        sb.append(ChecklistColumns.REGULATION_ID + " INTEGER");
        sb.append(");");
        db.execSQL(sb.toString());
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ChecklistTable.TABLE_NAME);
        ChecklistTable.onCreate(db);
    }


}