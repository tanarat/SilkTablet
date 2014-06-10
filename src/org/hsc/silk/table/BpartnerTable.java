package org.hsc.silk.table;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;


public final class BpartnerTable {
    public static final String TABLE_NAME = "bpartner_table";

    public static class BpartnerColumns implements BaseColumns {
        public static final String NAME = "name_column";
    }



    public static void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + BpartnerTable.TABLE_NAME + " (");
        sb.append(BaseColumns._ID + " INT PRIMARY KEY NOT NULL, ");
        sb.append(BpartnerColumns.NAME + " VARCHAR(100)");
        sb.append(");");
        db.execSQL(sb.toString());
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BpartnerTable.TABLE_NAME);
        BpartnerTable.onCreate(db);
    }


}