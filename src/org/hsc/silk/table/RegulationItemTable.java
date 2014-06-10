package org.hsc.silk.table;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;


public final class RegulationItemTable {
    public static final String TABLE_NAME = "regulation_item_table";

    public static class RegulationItemColumns implements BaseColumns {
        public static final String NAME = "name_column";
        public static final String VALUE = "value_column";
        public static final String REGULATION_ID = "regulation_id_column";
        public static final String PARENT_ITEM_ID = "parent_item_id_column";
        public static final String IS_GROUP = "is_group_column";
    }



    public static void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + RegulationItemTable.TABLE_NAME + " (");
        sb.append(BaseColumns._ID + " INT PRIMARY KEY NOT NULL, ");
        sb.append(RegulationItemColumns.NAME + " VARCHAR(100), ");
        sb.append(RegulationItemColumns.VALUE + " VARCHAR(10), ");
        sb.append(RegulationItemColumns.REGULATION_ID + " INTEGER, ");
        sb.append(RegulationItemColumns.PARENT_ITEM_ID + " INTEGER, ");
        sb.append(RegulationItemColumns.IS_GROUP + " BOOLEAN");
        sb.append(");");
        db.execSQL(sb.toString());
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RegulationItemTable.TABLE_NAME);
        RegulationItemTable.onCreate(db);
    }


}