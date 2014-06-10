package org.hsc.silk.table;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;


public final class SheetTable {
    public static final String TABLE_NAME = "sheet_table";

    public static class SheetColumns implements BaseColumns {
        public static final String NAME = "name_column";
        public static final String CHECKLIST_ID = "checklist_id_column";
        public static final String BPARTNER_ID = "bpartner_id_column";
        public static final String BP_LOCATION_ID = "bp_location_id_column";
        public static final String AUDIT_TYPE = "audit_type_column";
        public static final String AUDIT_DATE = "audit_date_column";
        public static final String AUDIT_TIMESLOT = "audit_timeslot_column";
        public static final String STATUS = "status_column";
		public static final String CREATE_SHEET_ITEM = "create_sheet_item";
    }



    public static void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + SheetTable.TABLE_NAME + " (");
        sb.append(BaseColumns._ID + " INTEGER PRIMARY KEY, ");
        sb.append(SheetColumns.NAME + " VARCHAR(100), ");
        sb.append(SheetColumns.CHECKLIST_ID + " INTEGER, ");
        sb.append(SheetColumns.BPARTNER_ID + " INTEGER, ");
        sb.append(SheetColumns.BP_LOCATION_ID + " INTEGER, ");
        sb.append(SheetColumns.AUDIT_TYPE + " INTEGER, ");
        sb.append(SheetColumns.AUDIT_DATE + " TIMESTAMP, ");
        sb.append(SheetColumns.AUDIT_TIMESLOT + " INTEGER, ");
        sb.append(SheetColumns.STATUS + " INTEGER, ");
        sb.append(SheetColumns.CREATE_SHEET_ITEM + " INTEGER");
        sb.append(");");
        db.execSQL(sb.toString());
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SheetTable.TABLE_NAME);
        SheetTable.onCreate(db);
    }


}