package org.hsc.silk.model;
import java.util.ArrayList;
import java.util.List;

import org.hsc.silk.table.RegulationItemTable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.provider.BaseColumns;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class RegulationItem extends ModelBase {
	public static final String TAG_REGULATION_ITEM_ID = "regulationItemId";
	public static final String TAG_VAVLUE = "value";
	public static final String TAG_REGULATION_ITEM_NAME = "regulationItemName";
	public static final String TAG_REGULATION_ID = "regulationId";
	public static final String TAG_PARENT_ITEM_ID = "parentItemId";
	public static final String TAG_IS_GROUP = "isGroup";
	
    private Context context;
    private int id;
    private String name;
    private String value;
    private int regulationId;
    private int parentItemId;
    private boolean isGroup;


    public RegulationItem() {
        super();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void fromCursor(Cursor cursor, Context context) {
        this.id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
        this.name = cursor.getString(cursor.getColumnIndex(RegulationItemTable.RegulationItemColumns.NAME));
        this.value = cursor.getString(cursor.getColumnIndex(RegulationItemTable.RegulationItemColumns.VALUE));
        this.regulationId = cursor.getInt(cursor.getColumnIndex(RegulationItemTable.RegulationItemColumns.REGULATION_ID));
        this.parentItemId = cursor.getInt(cursor.getColumnIndex(RegulationItemTable.RegulationItemColumns.PARENT_ITEM_ID));
        this.isGroup = cursor.getInt(cursor.getColumnIndex(RegulationItemTable.RegulationItemColumns.IS_GROUP)) == 1;
        this.context = context;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(RegulationItemTable.RegulationItemColumns._ID, this.id);
        values.put(RegulationItemTable.RegulationItemColumns.NAME, this.name);
        values.put(RegulationItemTable.RegulationItemColumns.VALUE, this.value);
        values.put(RegulationItemTable.RegulationItemColumns.REGULATION_ID, this.regulationId);
        values.put(RegulationItemTable.RegulationItemColumns.PARENT_ITEM_ID, this.parentItemId);
        values.put(RegulationItemTable.RegulationItemColumns.IS_GROUP, this.isGroup);
        return values;
    }

    public static RegulationItem newInstance(Cursor cursor, Context context) {
        RegulationItem regulationItem = new RegulationItem();
        regulationItem.fromCursor(cursor, context);
        return regulationItem;
    }

    public static List<RegulationItem> newInstanceList(JSONArray jsonArray) {
		List<RegulationItem> list = new ArrayList<RegulationItem>();
		for(int i = 0; i < jsonArray.length(); i++){
			JSONObject jsonObj;
			try {
				jsonObj = jsonArray.getJSONObject(i);
				RegulationItem obj = RegulationItem.newInstance(jsonObj);
				list.add(obj);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return list;
	}
	
	public static RegulationItem newInstance(JSONObject jsonObject) {
		RegulationItem obj = new RegulationItem();
		
		try {
			int id = jsonObject.getInt(TAG_REGULATION_ITEM_ID);
			String value = jsonObject.getString(TAG_VAVLUE);
			String name = jsonObject.getString(TAG_REGULATION_ITEM_NAME);
			int regulationId = jsonObject.getInt(TAG_REGULATION_ID);
			int parentItemId = jsonObject.getInt(TAG_PARENT_ITEM_ID);
			String isGroup = jsonObject.getString(TAG_IS_GROUP);
			
			obj.setId(id);
			obj.setValue(value);
			obj.setName(name);
			obj.setRegulationId(regulationId);
			obj.setParentItemId(parentItemId);
			obj.setGroup(isGroup.equals("Y"));
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return obj;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getRegulationId() {
		return regulationId;
	}

	public void setRegulationId(int regulationId) {
		this.regulationId = regulationId;
	}

	public int getParentItemId() {
		return parentItemId;
	}

	public void setParentItemId(int parentItemId) {
		this.parentItemId = parentItemId;
	}

	public boolean isGroup() {
		return isGroup;
	}

	public void setGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}

	public void setId(int id) {
		this.id = id;
	}


}