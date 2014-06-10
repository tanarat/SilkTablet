package org.hsc.silk.table;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;


public final class BpLocationTable {
    public static final String TABLE_NAME = "bp_location_table";

    public static class BpLocationColumns implements BaseColumns {
    	public static final String NAME = "name";
        public static final String PHONE = "phone_column";
        public static final String PHONE2 = "phone2_column";
        public static final String FAX = "fax_column";
        public static final String ADDRESS1 = "address1_column";
        public static final String ADDRESS2 = "address2_column";
        public static final String ADDRESS3 = "address3_column";
        public static final String ADDRESS4 = "address4_column";
        public static final String POSTAL = "postal_column";
        public static final String CITY = "city_column";
    }



    public static void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + BpLocationTable.TABLE_NAME + " (");
        sb.append(BaseColumns._ID + " INT PRIMARY KEY NOT NULL, ");
        sb.append(BpLocationColumns.NAME + " VARCHAR(100), ");
        sb.append(BpLocationColumns.PHONE + " VARCHAR(15), ");
        sb.append(BpLocationColumns.PHONE2 + " VARCHAR(15), ");
        sb.append(BpLocationColumns.FAX + " VARCHAR(15), ");
        sb.append(BpLocationColumns.ADDRESS1 + " VARCHAR(100), ");
        sb.append(BpLocationColumns.ADDRESS2 + " VARCHAR(100), ");
        sb.append(BpLocationColumns.ADDRESS3 + " VARCHAR(100), ");
        sb.append(BpLocationColumns.ADDRESS4 + " VARCHAR(100), ");
        sb.append(BpLocationColumns.POSTAL + " VARCHAR(5), ");
        sb.append(BpLocationColumns.CITY + " VARCHAR(100)");
        sb.append(");");
        db.execSQL(sb.toString());
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BpLocationTable.TABLE_NAME);
        BpLocationTable.onCreate(db);
    }


}