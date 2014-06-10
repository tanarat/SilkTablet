package org.hsc.silk.table;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;


public final class RemarkChoiceTable {
    public static final String TABLE_NAME = "remark_choice_table";

    public static class RemarkChoiceColumns implements BaseColumns {
        public static final String NAME = "name_column";
        public static final String VALUE = "value_column";
        public static final String DESCRIPTION = "description_column";
        public static final String CHECKLIST_ITEM_ID = "checklist_item_id_column";
    }



    public static void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + RemarkChoiceTable.TABLE_NAME + " (");
        sb.append(BaseColumns._ID + " INT PRIMARY KEY NOT NULL, ");
        sb.append(RemarkChoiceColumns.NAME + " VARCHAR(100), ");
        sb.append(RemarkChoiceColumns.VALUE + " VARCHAR(10), ");
        sb.append(RemarkChoiceColumns.DESCRIPTION + " VARCHAR(100), ");
        sb.append(RemarkChoiceColumns.CHECKLIST_ITEM_ID + " INTEGER");
        sb.append(");");
        db.execSQL(sb.toString());
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RemarkChoiceTable.TABLE_NAME);
        RemarkChoiceTable.onCreate(db);
    }


}