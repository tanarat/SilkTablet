package org.hsc.silk.db;

import org.hsc.silk.model.Setting;
import org.hsc.silk.table.AuditTypeTable;
import org.hsc.silk.table.BpLocationTable;
import org.hsc.silk.table.BpartnerTable;
import org.hsc.silk.table.ChecklistItemTable;
import org.hsc.silk.table.ChecklistTable;
import org.hsc.silk.table.RegulationItemTable;
import org.hsc.silk.table.RegulationTable;
import org.hsc.silk.table.RemarkChoiceTable;
import org.hsc.silk.table.SettingTable;
import org.hsc.silk.table.SheetItemTable;
import org.hsc.silk.table.SheetTable;
import org.hsc.silk.table.UserTable;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SILKOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "silk.db";
    private static final int DATABASE_VERSION = 1;

    public SILKOpenHelper(Context context) {
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
        
        initSettingTable(db);
    }
    
    private void initSettingTable(SQLiteDatabase db){
    	Setting setting = new Setting();
    	setting.setDataVersion(0);
    	setting.setDefault(true);
        ContentValues values = setting.toContentValues(); 
        db.insert(SettingTable.TABLE_NAME, null, values);
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