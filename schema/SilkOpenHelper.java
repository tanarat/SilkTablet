package org.hsc.silk.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SilkOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "silk.db";
    private static final int DATABASE_VERSION = 1;

    public SilkOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ChecklistTable.onCreate(db);
        ChecklistItemTable.onCreate(db);
        RegulationTable.onCreate(db);
        RegulationItemTable.onCreate(db);
        SheetTable.onCreate(db);
        SheetItemTable.onCreate(db);
        BpartnerTable.onCreate(db);
        BpLocationTable.onCreate(db);
        AuditTypeTable.onCreate(db);
        RemarkChoiceTable.onCreate(db);
        SettingTable.onCreate(db);
        UserTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        ChecklistTable.onUpgrade(db, oldVersion, newVersion);
        ChecklistItemTable.onUpgrade(db, oldVersion, newVersion);
        RegulationTable.onUpgrade(db, oldVersion, newVersion);
        RegulationItemTable.onUpgrade(db, oldVersion, newVersion);
        SheetTable.onUpgrade(db, oldVersion, newVersion);
        SheetItemTable.onUpgrade(db, oldVersion, newVersion);
        BpartnerTable.onUpgrade(db, oldVersion, newVersion);
        BpLocationTable.onUpgrade(db, oldVersion, newVersion);
        AuditTypeTable.onUpgrade(db, oldVersion, newVersion);
        RemarkChoiceTable.onUpgrade(db, oldVersion, newVersion);
        SettingTable.onUpgrade(db, oldVersion, newVersion);
        UserTable.onUpgrade(db, oldVersion, newVersion);
    }


}