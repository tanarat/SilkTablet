package org.hsc.silk.table;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;


public final class UserTable {
    public static final String TABLE_NAME = "user_table";

    public static class UserColumns implements BaseColumns {
        public static final String NAME = "name_column";
        public static final String PASSWORD = "password_column";
        public static final String TITLE = "title_column";
        public static final String FULL_NAME = "full_name_column";
    }



    public static void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + UserTable.TABLE_NAME + " (");
        sb.append(BaseColumns._ID + " INT PRIMARY KEY NOT NULL, ");
        sb.append(UserColumns.NAME + " VARCHAR(100), ");
        sb.append(UserColumns.PASSWORD + " VARCHAR(100), ");
        sb.append(UserColumns.TITLE + " VARCHAR(30), ");
        sb.append(UserColumns.FULL_NAME + " VARCHAR(100)");
        sb.append(");");
        db.execSQL(sb.toString());
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserTable.TABLE_NAME);
        UserTable.onCreate(db);
    }


}