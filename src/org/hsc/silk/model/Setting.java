package org.hsc.silk.model;
import org.hsc.silk.table.SettingTable;

import android.provider.BaseColumns;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class Setting extends ModelBase {
    private Context context;
    private int id;
    private int dataVersion;
    public int getDataVersion() {
		return dataVersion;
	}

	public void setDataVersion(int dataVersion) {
		this.dataVersion = dataVersion;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	private boolean isDefault;


    public Setting() {
        super();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void fromCursor(Cursor cursor, Context context) {
        this.id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
        this.dataVersion = cursor.getInt(cursor.getColumnIndex(SettingTable.SettingColumns.DATA_VERSION));
        this.isDefault = cursor.getInt(cursor.getColumnIndex(SettingTable.SettingColumns.IS_DEFAULT)) == 1;
        this.context = context;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(SettingTable.SettingColumns.DATA_VERSION, this.dataVersion);
        values.put(SettingTable.SettingColumns.IS_DEFAULT, this.isDefault);
        return values;
    }

    public static Setting newInstance(Cursor cursor, Context context) {
        Setting setting = new Setting();
        setting.fromCursor(cursor, context);
        return setting;
    }

	@Override
	public String toString() {
		return "Setting [id=" + id + ", dataVersion=" + dataVersion
				+ ", isDefault=" + isDefault + "]";
	}


}