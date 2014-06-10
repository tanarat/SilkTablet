package org.hsc.silk.db;

import org.hsc.silk.table.SettingTable;
import org.hsc.silk.table.*;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.provider.BaseColumns;

public class SILKProvider extends ContentProvider {
    private SILKOpenHelper dbHelper;
    private SQLiteDatabase database;
    public static final String AUTHORITY = "org.hsc.silk.db.contentprovider";
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int CHECKLISTS = 1001;
    private static final int CHECKLIST_ID = 1002;
    public static final String CHECKLIST_PATH = "checklists";
    public static final Uri CHECKLIST_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + CHECKLIST_PATH);
    public static final String CHECKLIST_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/checklists";
    public static final String CHECKLIST_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/checklist";
    static {
        sURIMatcher.addURI(AUTHORITY, CHECKLIST_PATH, CHECKLISTS);
        sURIMatcher.addURI(AUTHORITY, CHECKLIST_PATH + "/#", CHECKLIST_ID);
    }

    private static final int CHECKLIST_ITEMS = 1003;
    private static final int CHECKLIST_ITEM_ID = 1004;
    public static final String CHECKLIST_ITEM_PATH = "checklist_items";
    public static final Uri CHECKLIST_ITEM_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + CHECKLIST_ITEM_PATH);
    public static final String CHECKLIST_ITEM_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/checklist_items";
    public static final String CHECKLIST_ITEM_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/checklist_item";
    static {
        sURIMatcher.addURI(AUTHORITY, CHECKLIST_ITEM_PATH, CHECKLIST_ITEMS);
        sURIMatcher.addURI(AUTHORITY, CHECKLIST_ITEM_PATH + "/#", CHECKLIST_ITEM_ID);
    }

    private static final int REGULATIONS = 1005;
    private static final int REGULATION_ID = 1006;
    public static final String REGULATION_PATH = "regulations";
    public static final Uri REGULATION_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + REGULATION_PATH);
    public static final String REGULATION_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/regulations";
    public static final String REGULATION_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/regulation";
    static {
        sURIMatcher.addURI(AUTHORITY, REGULATION_PATH, REGULATIONS);
        sURIMatcher.addURI(AUTHORITY, REGULATION_PATH + "/#", REGULATION_ID);
    }

    private static final int REGULATION_ITEMS = 1007;
    private static final int REGULATION_ITEM_ID = 1008;
    public static final String REGULATION_ITEM_PATH = "regulation_items";
    public static final Uri REGULATION_ITEM_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + REGULATION_ITEM_PATH);
    public static final String REGULATION_ITEM_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/regulation_items";
    public static final String REGULATION_ITEM_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/regulation_item";
    static {
        sURIMatcher.addURI(AUTHORITY, REGULATION_ITEM_PATH, REGULATION_ITEMS);
        sURIMatcher.addURI(AUTHORITY, REGULATION_ITEM_PATH + "/#", REGULATION_ITEM_ID);
    }

    private static final int SHEETS = 1009;
    private static final int SHEET_ID = 1010;
    public static final String SHEET_PATH = "sheets";
    public static final Uri SHEET_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + SHEET_PATH);
    public static final String SHEET_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/sheets";
    public static final String SHEET_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/sheet";
    static {
        sURIMatcher.addURI(AUTHORITY, SHEET_PATH, SHEETS);
        sURIMatcher.addURI(AUTHORITY, SHEET_PATH + "/#", SHEET_ID);
    }

    private static final int SHEET_ITEMS = 1011;
    private static final int SHEET_ITEM_ID = 1012;
    public static final String SHEET_ITEM_PATH = "sheet_items";
    public static final Uri SHEET_ITEM_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + SHEET_ITEM_PATH);
    public static final String SHEET_ITEM_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/sheet_items";
    public static final String SHEET_ITEM_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/sheet_item";
    static {
        sURIMatcher.addURI(AUTHORITY, SHEET_ITEM_PATH, SHEET_ITEMS);
        sURIMatcher.addURI(AUTHORITY, SHEET_ITEM_PATH + "/#", SHEET_ITEM_ID);
    }

    private static final int BPARTNERS = 1013;
    private static final int BPARTNER_ID = 1014;
    public static final String BPARTNER_PATH = "bpartners";
    public static final Uri BPARTNER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BPARTNER_PATH);
    public static final String BPARTNER_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/bpartners";
    public static final String BPARTNER_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/bpartner";
    static {
        sURIMatcher.addURI(AUTHORITY, BPARTNER_PATH, BPARTNERS);
        sURIMatcher.addURI(AUTHORITY, BPARTNER_PATH + "/#", BPARTNER_ID);
    }

    private static final int BP_LOCATIONS = 1015;
    private static final int BP_LOCATION_ID = 1016;
    public static final String BP_LOCATION_PATH = "bp_locations";
    public static final Uri BP_LOCATION_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BP_LOCATION_PATH);
    public static final String BP_LOCATION_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/bp_locations";
    public static final String BP_LOCATION_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/bp_location";
    static {
        sURIMatcher.addURI(AUTHORITY, BP_LOCATION_PATH, BP_LOCATIONS);
        sURIMatcher.addURI(AUTHORITY, BP_LOCATION_PATH + "/#", BP_LOCATION_ID);
    }

    private static final int AUDIT_TYPES = 1017;
    private static final int AUDIT_TYPE_ID = 1018;
    public static final String AUDIT_TYPE_PATH = "audit_types";
    public static final Uri AUDIT_TYPE_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + AUDIT_TYPE_PATH);
    public static final String AUDIT_TYPE_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/audit_types";
    public static final String AUDIT_TYPE_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/audit_type";
    static {
        sURIMatcher.addURI(AUTHORITY, AUDIT_TYPE_PATH, AUDIT_TYPES);
        sURIMatcher.addURI(AUTHORITY, AUDIT_TYPE_PATH + "/#", AUDIT_TYPE_ID);
    }

    private static final int REMARK_CHOICES = 1019;
    private static final int REMARK_CHOICE_ID = 1020;
    public static final String REMARK_CHOICE_PATH = "remark_choices";
    public static final Uri REMARK_CHOICE_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + REMARK_CHOICE_PATH);
    public static final String REMARK_CHOICE_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/remark_choices";
    public static final String REMARK_CHOICE_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/remark_choice";
    static {
        sURIMatcher.addURI(AUTHORITY, REMARK_CHOICE_PATH, REMARK_CHOICES);
        sURIMatcher.addURI(AUTHORITY, REMARK_CHOICE_PATH + "/#", REMARK_CHOICE_ID);
    }

    private static final int SETTINGS = 1021;
    private static final int SETTING_ID = 1022;
    public static final String SETTING_PATH = "settings";
    public static final Uri SETTING_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + SETTING_PATH);
    public static final String SETTING_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/settings";
    public static final String SETTING_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/setting";
    static {
        sURIMatcher.addURI(AUTHORITY, SETTING_PATH, SETTINGS);
        sURIMatcher.addURI(AUTHORITY, SETTING_PATH + "/#", SETTING_ID);
    }

    private static final int USERS = 1023;
    private static final int USER_ID = 1024;
    public static final String USER_PATH = "users";
    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + USER_PATH);
    public static final String USER_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/users";
    public static final String USER_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/user";
    static {
        sURIMatcher.addURI(AUTHORITY, USER_PATH, USERS);
        sURIMatcher.addURI(AUTHORITY, USER_PATH + "/#", USER_ID);
    }



    @Override
    public boolean onCreate() {
        dbHelper = new SILKOpenHelper(getContext());
        database = dbHelper.getWritableDatabase();
        return true;
    }

    @Override
    public String getType(Uri uri) {
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
        case CHECKLISTS:
            return CHECKLIST_CONTENT_TYPE;
        case CHECKLIST_ID:
            return CHECKLIST_CONTENT_ITEM_TYPE;
        case CHECKLIST_ITEMS:
            return CHECKLIST_ITEM_CONTENT_TYPE;
        case CHECKLIST_ITEM_ID:
            return CHECKLIST_ITEM_CONTENT_ITEM_TYPE;
        case REGULATIONS:
            return REGULATION_CONTENT_TYPE;
        case REGULATION_ID:
            return REGULATION_CONTENT_ITEM_TYPE;
        case REGULATION_ITEMS:
            return REGULATION_ITEM_CONTENT_TYPE;
        case REGULATION_ITEM_ID:
            return REGULATION_ITEM_CONTENT_ITEM_TYPE;
        case SHEETS:
            return SHEET_CONTENT_TYPE;
        case SHEET_ID:
            return SHEET_CONTENT_ITEM_TYPE;
        case SHEET_ITEMS:
            return SHEET_ITEM_CONTENT_TYPE;
        case SHEET_ITEM_ID:
            return SHEET_ITEM_CONTENT_ITEM_TYPE;
        case BPARTNERS:
            return BPARTNER_CONTENT_TYPE;
        case BPARTNER_ID:
            return BPARTNER_CONTENT_ITEM_TYPE;
        case BP_LOCATIONS:
            return BP_LOCATION_CONTENT_TYPE;
        case BP_LOCATION_ID:
            return BP_LOCATION_CONTENT_ITEM_TYPE;
        case AUDIT_TYPES:
            return AUDIT_TYPE_CONTENT_TYPE;
        case AUDIT_TYPE_ID:
            return AUDIT_TYPE_CONTENT_ITEM_TYPE;
        case REMARK_CHOICES:
            return REMARK_CHOICE_CONTENT_TYPE;
        case REMARK_CHOICE_ID:
            return REMARK_CHOICE_CONTENT_ITEM_TYPE;
        case SETTINGS:
            return SETTING_CONTENT_TYPE;
        case SETTING_ID:
            return SETTING_CONTENT_ITEM_TYPE;
        case USERS:
            return USER_CONTENT_TYPE;
        case USER_ID:
            return USER_CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        long id = 0;
        switch (uriType) {
        case CHECKLISTS:
            id = database.insert(ChecklistTable.TABLE_NAME, null, values);
            getContext().getContentResolver().notifyChange(uri, null);
            return Uri.parse("content://" + AUTHORITY + "/" + CHECKLIST_PATH + "/" + id);
        case CHECKLIST_ITEMS:
            id = database.insert(ChecklistItemTable.TABLE_NAME, null, values);
            getContext().getContentResolver().notifyChange(uri, null);
            return Uri.parse("content://" + AUTHORITY + "/" + CHECKLIST_ITEM_PATH + "/" + id);
        case REGULATIONS:
            id = database.insert(RegulationTable.TABLE_NAME, null, values);
            getContext().getContentResolver().notifyChange(uri, null);
            return Uri.parse("content://" + AUTHORITY + "/" + REGULATION_PATH + "/" + id);
        case REGULATION_ITEMS:
            id = database.insert(RegulationItemTable.TABLE_NAME, null, values);
            getContext().getContentResolver().notifyChange(uri, null);
            return Uri.parse("content://" + AUTHORITY + "/" + REGULATION_ITEM_PATH + "/" + id);
        case SHEETS:
            id = database.insert(SheetTable.TABLE_NAME, null, values);
            getContext().getContentResolver().notifyChange(uri, null);
            return Uri.parse("content://" + AUTHORITY + "/" + SHEET_PATH + "/" + id);
        case SHEET_ITEMS:
            id = database.insert(SheetItemTable.TABLE_NAME, null, values);
            getContext().getContentResolver().notifyChange(uri, null);
            return Uri.parse("content://" + AUTHORITY + "/" + SHEET_ITEM_PATH + "/" + id);
        case BPARTNERS:
            id = database.insert(BpartnerTable.TABLE_NAME, null, values);
            getContext().getContentResolver().notifyChange(uri, null);
            return Uri.parse("content://" + AUTHORITY + "/" + BPARTNER_PATH + "/" + id);
        case BP_LOCATIONS:
            id = database.insert(BpLocationTable.TABLE_NAME, null, values);
            getContext().getContentResolver().notifyChange(uri, null);
            return Uri.parse("content://" + AUTHORITY + "/" + BP_LOCATION_PATH + "/" + id);
        case AUDIT_TYPES:
            id = database.insert(AuditTypeTable.TABLE_NAME, null, values);
            getContext().getContentResolver().notifyChange(uri, null);
            return Uri.parse("content://" + AUTHORITY + "/" + AUDIT_TYPE_PATH + "/" + id);
        case REMARK_CHOICES:
            id = database.insert(RemarkChoiceTable.TABLE_NAME, null, values);
            getContext().getContentResolver().notifyChange(uri, null);
            return Uri.parse("content://" + AUTHORITY + "/" + REMARK_CHOICE_PATH + "/" + id);
        case SETTINGS:
            id = database.insert(SettingTable.TABLE_NAME, null, values);
            getContext().getContentResolver().notifyChange(uri, null);
            return Uri.parse("content://" + AUTHORITY + "/" + SETTING_PATH + "/" + id);
        case USERS:
            id = database.insert(UserTable.TABLE_NAME, null, values);
            getContext().getContentResolver().notifyChange(uri, null);
            return Uri.parse("content://" + AUTHORITY + "/" + USER_PATH + "/" + id);
        default:
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int uriType = sURIMatcher.match(uri);
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        switch (uriType) {
        case CHECKLISTS:
            queryBuilder.setTables(ChecklistTable.TABLE_NAME);
            break;
        case CHECKLIST_ID:
            queryBuilder.setTables(ChecklistTable.TABLE_NAME);
            queryBuilder.appendWhere(BaseColumns._ID + "=" + uri.getLastPathSegment());
            break;
        case CHECKLIST_ITEMS:
            queryBuilder.setTables(ChecklistItemTable.TABLE_NAME);
            break;
        case CHECKLIST_ITEM_ID:
            queryBuilder.setTables(ChecklistItemTable.TABLE_NAME);
            queryBuilder.appendWhere(BaseColumns._ID + "=" + uri.getLastPathSegment());
            break;
        case REGULATIONS:
            queryBuilder.setTables(RegulationTable.TABLE_NAME);
            break;
        case REGULATION_ID:
            queryBuilder.setTables(RegulationTable.TABLE_NAME);
            queryBuilder.appendWhere(BaseColumns._ID + "=" + uri.getLastPathSegment());
            break;
        case REGULATION_ITEMS:
            queryBuilder.setTables(RegulationItemTable.TABLE_NAME);
            break;
        case REGULATION_ITEM_ID:
            queryBuilder.setTables(RegulationItemTable.TABLE_NAME);
            queryBuilder.appendWhere(BaseColumns._ID + "=" + uri.getLastPathSegment());
            break;
        case SHEETS:
            queryBuilder.setTables(SheetTable.TABLE_NAME);
            break;
        case SHEET_ID:
            queryBuilder.setTables(SheetTable.TABLE_NAME);
            queryBuilder.appendWhere(BaseColumns._ID + "=" + uri.getLastPathSegment());
            break;
        case SHEET_ITEMS:
            queryBuilder.setTables(SheetItemTable.TABLE_NAME);
            break;
        case SHEET_ITEM_ID:
            queryBuilder.setTables(SheetItemTable.TABLE_NAME);
            queryBuilder.appendWhere(BaseColumns._ID + "=" + uri.getLastPathSegment());
            break;
        case BPARTNERS:
            queryBuilder.setTables(BpartnerTable.TABLE_NAME);
            break;
        case BPARTNER_ID:
            queryBuilder.setTables(BpartnerTable.TABLE_NAME);
            queryBuilder.appendWhere(BaseColumns._ID + "=" + uri.getLastPathSegment());
            break;
        case BP_LOCATIONS:
            queryBuilder.setTables(BpLocationTable.TABLE_NAME);
            break;
        case BP_LOCATION_ID:
            queryBuilder.setTables(BpLocationTable.TABLE_NAME);
            queryBuilder.appendWhere(BaseColumns._ID + "=" + uri.getLastPathSegment());
            break;
        case AUDIT_TYPES:
            queryBuilder.setTables(AuditTypeTable.TABLE_NAME);
            break;
        case AUDIT_TYPE_ID:
            queryBuilder.setTables(AuditTypeTable.TABLE_NAME);
            queryBuilder.appendWhere(BaseColumns._ID + "=" + uri.getLastPathSegment());
            break;
        case REMARK_CHOICES:
            queryBuilder.setTables(RemarkChoiceTable.TABLE_NAME);
            break;
        case REMARK_CHOICE_ID:
            queryBuilder.setTables(RemarkChoiceTable.TABLE_NAME);
            queryBuilder.appendWhere(BaseColumns._ID + "=" + uri.getLastPathSegment());
            break;
        case SETTINGS:
            queryBuilder.setTables(SettingTable.TABLE_NAME);
            break;
        case SETTING_ID:
            queryBuilder.setTables(SettingTable.TABLE_NAME);
            queryBuilder.appendWhere(BaseColumns._ID + "=" + uri.getLastPathSegment());
            break;
        case USERS:
            queryBuilder.setTables(UserTable.TABLE_NAME);
            break;
        case USER_ID:
            queryBuilder.setTables(UserTable.TABLE_NAME);
            queryBuilder.appendWhere(BaseColumns._ID + "=" + uri.getLastPathSegment());
            break;
        default:
            throw new IllegalArgumentException("Unknown URI: " + uri);        
        }
        Cursor cursor = queryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        int rowsDeleted = 0;
        switch (uriType) {
        case CHECKLISTS:
            rowsDeleted = database.delete(ChecklistTable.TABLE_NAME, selection, selectionArgs);
            break;
        case CHECKLIST_ID:
            String checklistId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsDeleted = database.delete(ChecklistTable.TABLE_NAME, BaseColumns._ID + "=" + checklistId, null);
            } else {
                rowsDeleted = database.delete(ChecklistTable.TABLE_NAME, BaseColumns._ID + "=" + checklistId + " AND " + selection, selectionArgs);
            }
            break;
        case CHECKLIST_ITEMS:
            rowsDeleted = database.delete(ChecklistItemTable.TABLE_NAME, selection, selectionArgs);
            break;
        case CHECKLIST_ITEM_ID:
            String checklistItemId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsDeleted = database.delete(ChecklistItemTable.TABLE_NAME, BaseColumns._ID + "=" + checklistItemId, null);
            } else {
                rowsDeleted = database.delete(ChecklistItemTable.TABLE_NAME, BaseColumns._ID + "=" + checklistItemId + " AND " + selection, selectionArgs);
            }
            break;
        case REGULATIONS:
            rowsDeleted = database.delete(RegulationTable.TABLE_NAME, selection, selectionArgs);
            break;
        case REGULATION_ID:
            String regulationId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsDeleted = database.delete(RegulationTable.TABLE_NAME, BaseColumns._ID + "=" + regulationId, null);
            } else {
                rowsDeleted = database.delete(RegulationTable.TABLE_NAME, BaseColumns._ID + "=" + regulationId + " AND " + selection, selectionArgs);
            }
            break;
        case REGULATION_ITEMS:
            rowsDeleted = database.delete(RegulationItemTable.TABLE_NAME, selection, selectionArgs);
            break;
        case REGULATION_ITEM_ID:
            String regulationItemId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsDeleted = database.delete(RegulationItemTable.TABLE_NAME, BaseColumns._ID + "=" + regulationItemId, null);
            } else {
                rowsDeleted = database.delete(RegulationItemTable.TABLE_NAME, BaseColumns._ID + "=" + regulationItemId + " AND " + selection, selectionArgs);
            }
            break;
        case SHEETS:
            rowsDeleted = database.delete(SheetTable.TABLE_NAME, selection, selectionArgs);
            break;
        case SHEET_ID:
            String sheetId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsDeleted = database.delete(SheetTable.TABLE_NAME, BaseColumns._ID + "=" + sheetId, null);
            } else {
                rowsDeleted = database.delete(SheetTable.TABLE_NAME, BaseColumns._ID + "=" + sheetId + " AND " + selection, selectionArgs);
            }
            break;
        case SHEET_ITEMS:
            rowsDeleted = database.delete(SheetItemTable.TABLE_NAME, selection, selectionArgs);
            break;
        case SHEET_ITEM_ID:
            String sheetItemId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsDeleted = database.delete(SheetItemTable.TABLE_NAME, BaseColumns._ID + "=" + sheetItemId, null);
            } else {
                rowsDeleted = database.delete(SheetItemTable.TABLE_NAME, BaseColumns._ID + "=" + sheetItemId + " AND " + selection, selectionArgs);
            }
            break;
        case BPARTNERS:
            rowsDeleted = database.delete(BpartnerTable.TABLE_NAME, selection, selectionArgs);
            break;
        case BPARTNER_ID:
            String bpartnerId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsDeleted = database.delete(BpartnerTable.TABLE_NAME, BaseColumns._ID + "=" + bpartnerId, null);
            } else {
                rowsDeleted = database.delete(BpartnerTable.TABLE_NAME, BaseColumns._ID + "=" + bpartnerId + " AND " + selection, selectionArgs);
            }
            break;
        case BP_LOCATIONS:
            rowsDeleted = database.delete(BpLocationTable.TABLE_NAME, selection, selectionArgs);
            break;
        case BP_LOCATION_ID:
            String bpLocationId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsDeleted = database.delete(BpLocationTable.TABLE_NAME, BaseColumns._ID + "=" + bpLocationId, null);
            } else {
                rowsDeleted = database.delete(BpLocationTable.TABLE_NAME, BaseColumns._ID + "=" + bpLocationId + " AND " + selection, selectionArgs);
            }
            break;
        case AUDIT_TYPES:
            rowsDeleted = database.delete(AuditTypeTable.TABLE_NAME, selection, selectionArgs);
            break;
        case AUDIT_TYPE_ID:
            String auditTypeId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsDeleted = database.delete(AuditTypeTable.TABLE_NAME, BaseColumns._ID + "=" + auditTypeId, null);
            } else {
                rowsDeleted = database.delete(AuditTypeTable.TABLE_NAME, BaseColumns._ID + "=" + auditTypeId + " AND " + selection, selectionArgs);
            }
            break;
        case REMARK_CHOICES:
            rowsDeleted = database.delete(RemarkChoiceTable.TABLE_NAME, selection, selectionArgs);
            break;
        case REMARK_CHOICE_ID:
            String remarkChoiceId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsDeleted = database.delete(RemarkChoiceTable.TABLE_NAME, BaseColumns._ID + "=" + remarkChoiceId, null);
            } else {
                rowsDeleted = database.delete(RemarkChoiceTable.TABLE_NAME, BaseColumns._ID + "=" + remarkChoiceId + " AND " + selection, selectionArgs);
            }
            break;
        case SETTINGS:
            rowsDeleted = database.delete(SettingTable.TABLE_NAME, selection, selectionArgs);
            break;
        case SETTING_ID:
            String settingId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsDeleted = database.delete(SettingTable.TABLE_NAME, BaseColumns._ID + "=" + settingId, null);
            } else {
                rowsDeleted = database.delete(SettingTable.TABLE_NAME, BaseColumns._ID + "=" + settingId + " AND " + selection, selectionArgs);
            }
            break;
        case USERS:
            rowsDeleted = database.delete(UserTable.TABLE_NAME, selection, selectionArgs);
            break;
        case USER_ID:
            String userId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsDeleted = database.delete(UserTable.TABLE_NAME, BaseColumns._ID + "=" + userId, null);
            } else {
                rowsDeleted = database.delete(UserTable.TABLE_NAME, BaseColumns._ID + "=" + userId + " AND " + selection, selectionArgs);
            }
            break;
        default:
            throw new IllegalArgumentException("Unknown URI: " + uri);      
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        int rowsUpdated = 0;
        switch (uriType) {
        case CHECKLISTS:
            rowsUpdated = database.update(ChecklistTable.TABLE_NAME, values, selection, selectionArgs);
            break;
        case CHECKLIST_ID:
            String checklistId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsUpdated = database.update(ChecklistTable.TABLE_NAME, values, BaseColumns._ID + "=" + checklistId, null);
            } else {
                rowsUpdated = database.update(ChecklistTable.TABLE_NAME, values, BaseColumns._ID + "=" + checklistId + " AND " + selection, selectionArgs);
            }
            break;
        case CHECKLIST_ITEMS:
            rowsUpdated = database.update(ChecklistItemTable.TABLE_NAME, values, selection, selectionArgs);
            break;
        case CHECKLIST_ITEM_ID:
            String checklistItemId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsUpdated = database.update(ChecklistItemTable.TABLE_NAME, values, BaseColumns._ID + "=" + checklistItemId, null);
            } else {
                rowsUpdated = database.update(ChecklistItemTable.TABLE_NAME, values, BaseColumns._ID + "=" + checklistItemId + " AND " + selection, selectionArgs);
            }
            break;
        case REGULATIONS:
            rowsUpdated = database.update(RegulationTable.TABLE_NAME, values, selection, selectionArgs);
            break;
        case REGULATION_ID:
            String regulationId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsUpdated = database.update(RegulationTable.TABLE_NAME, values, BaseColumns._ID + "=" + regulationId, null);
            } else {
                rowsUpdated = database.update(RegulationTable.TABLE_NAME, values, BaseColumns._ID + "=" + regulationId + " AND " + selection, selectionArgs);
            }
            break;
        case REGULATION_ITEMS:
            rowsUpdated = database.update(RegulationItemTable.TABLE_NAME, values, selection, selectionArgs);
            break;
        case REGULATION_ITEM_ID:
            String regulationItemId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsUpdated = database.update(RegulationItemTable.TABLE_NAME, values, BaseColumns._ID + "=" + regulationItemId, null);
            } else {
                rowsUpdated = database.update(RegulationItemTable.TABLE_NAME, values, BaseColumns._ID + "=" + regulationItemId + " AND " + selection, selectionArgs);
            }
            break;
        case SHEETS:
            rowsUpdated = database.update(SheetTable.TABLE_NAME, values, selection, selectionArgs);
            break;
        case SHEET_ID:
            String sheetId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsUpdated = database.update(SheetTable.TABLE_NAME, values, BaseColumns._ID + "=" + sheetId, null);
            } else {
                rowsUpdated = database.update(SheetTable.TABLE_NAME, values, BaseColumns._ID + "=" + sheetId + " AND " + selection, selectionArgs);
            }
            break;
        case SHEET_ITEMS:
            rowsUpdated = database.update(SheetItemTable.TABLE_NAME, values, selection, selectionArgs);
            break;
        case SHEET_ITEM_ID:
            String sheetItemId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsUpdated = database.update(SheetItemTable.TABLE_NAME, values, BaseColumns._ID + "=" + sheetItemId, null);
            } else {
                rowsUpdated = database.update(SheetItemTable.TABLE_NAME, values, BaseColumns._ID + "=" + sheetItemId + " AND " + selection, selectionArgs);
            }
            break;
        case BPARTNERS:
            rowsUpdated = database.update(BpartnerTable.TABLE_NAME, values, selection, selectionArgs);
            break;
        case BPARTNER_ID:
            String bpartnerId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsUpdated = database.update(BpartnerTable.TABLE_NAME, values, BaseColumns._ID + "=" + bpartnerId, null);
            } else {
                rowsUpdated = database.update(BpartnerTable.TABLE_NAME, values, BaseColumns._ID + "=" + bpartnerId + " AND " + selection, selectionArgs);
            }
            break;
        case BP_LOCATIONS:
            rowsUpdated = database.update(BpLocationTable.TABLE_NAME, values, selection, selectionArgs);
            break;
        case BP_LOCATION_ID:
            String bpLocationId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsUpdated = database.update(BpLocationTable.TABLE_NAME, values, BaseColumns._ID + "=" + bpLocationId, null);
            } else {
                rowsUpdated = database.update(BpLocationTable.TABLE_NAME, values, BaseColumns._ID + "=" + bpLocationId + " AND " + selection, selectionArgs);
            }
            break;
        case AUDIT_TYPES:
            rowsUpdated = database.update(AuditTypeTable.TABLE_NAME, values, selection, selectionArgs);
            break;
        case AUDIT_TYPE_ID:
            String auditTypeId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsUpdated = database.update(AuditTypeTable.TABLE_NAME, values, BaseColumns._ID + "=" + auditTypeId, null);
            } else {
                rowsUpdated = database.update(AuditTypeTable.TABLE_NAME, values, BaseColumns._ID + "=" + auditTypeId + " AND " + selection, selectionArgs);
            }
            break;
        case REMARK_CHOICES:
            rowsUpdated = database.update(RemarkChoiceTable.TABLE_NAME, values, selection, selectionArgs);
            break;
        case REMARK_CHOICE_ID:
            String remarkChoiceId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsUpdated = database.update(RemarkChoiceTable.TABLE_NAME, values, BaseColumns._ID + "=" + remarkChoiceId, null);
            } else {
                rowsUpdated = database.update(RemarkChoiceTable.TABLE_NAME, values, BaseColumns._ID + "=" + remarkChoiceId + " AND " + selection, selectionArgs);
            }
            break;
        case SETTINGS:
            rowsUpdated = database.update(SettingTable.TABLE_NAME, values, selection, selectionArgs);
            break;
        case SETTING_ID:
            String settingId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsUpdated = database.update(SettingTable.TABLE_NAME, values, BaseColumns._ID + "=" + settingId, null);
            } else {
                rowsUpdated = database.update(SettingTable.TABLE_NAME, values, BaseColumns._ID + "=" + settingId + " AND " + selection, selectionArgs);
            }
            break;
        case USERS:
            rowsUpdated = database.update(UserTable.TABLE_NAME, values, selection, selectionArgs);
            break;
        case USER_ID:
            String userId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsUpdated = database.update(UserTable.TABLE_NAME, values, BaseColumns._ID + "=" + userId, null);
            } else {
                rowsUpdated = database.update(UserTable.TABLE_NAME, values, BaseColumns._ID + "=" + userId + " AND " + selection, selectionArgs);
            }
            break;
        default:
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }


}