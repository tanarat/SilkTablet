package org.hsc.silk.dao;

import org.hsc.silk.db.SILKProvider;
import org.hsc.silk.model.Setting;
import org.hsc.silk.table.SettingTable;

import android.content.Context;

public class SettingDao extends Dao<Setting> {
	
	public SettingDao(Context context){
		super(Setting.class, context, SILKProvider.SETTING_CONTENT_URI);
	}

	public Setting getDefault() {
		String selection = SettingTable.SettingColumns.IS_DEFAULT + " = ?";
		String[] seletecionArgs = new String[]{String.valueOf("1")}; 
		return get(selection, seletecionArgs).get(0);
	}

	
}
