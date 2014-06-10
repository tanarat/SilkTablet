package org.hsc.silk.table;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;


public final class SettingTable {
    public static final String TABLE_NAME = "setting_table";

    public static class SettingColumns implements BaseColumns {
        public static final String DATA_VERSION = "data_version_column";
        public static final String IS_DEFAULT = "is_default_column";
    }



    public static void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + SettingTable.TABLE_NAME + " (");
        sb.append(BaseColumns._ID + " INTEGER PRIMARY KEY, ");
        sb.append(SettingColumns.DATA_VERSION + " INTEGER, ");
        sb.append(SettingColumns.IS_DEFAULT + " BOOLEAN");
        sb.append(");");
        db.execSQL(sb.toString());
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SettingTable.TABLE_NAME);
        SettingTable.onCreate(db);
    }


}