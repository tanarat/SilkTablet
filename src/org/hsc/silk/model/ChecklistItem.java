package org.hsc.silk.model;
import java.util.ArrayList;
import java.util.List;

import org.hsc.silk.table.ChecklistItemTable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.provider.BaseColumns;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class ChecklistItem extends ModelBase {
	public static final String TAG_CHECKLIST_ITEM_ID = "checklistItemId";
	public static final String TAG_VALUE = "value";
	public static final String TAG_CHECKLIST_ITEM_NAME = "checklistItemName";
	public static final String TAG_CHECKLIST_ID = "checklistId";
	public static final String TAG_REGULATION_ITEM_ID = "regulationItemId";
	
    private Context context;
    private int id;
    private String name;
    private String value;
    private int checklistId;
    private int regulationItemId;


    public ChecklistItem() {
        super();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void fromCursor(Cursor cursor, Context context) {
        this.id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
        this.name = cursor.getString(cursor.getColumnIndex(ChecklistItemTable.ChecklistItemColumns.NAME));
        this.value = cursor.getString(cursor.getColumnIndex(ChecklistItemTable.ChecklistItemColumns.VALUE));
        this.checklistId = cursor.getInt(cursor.getColumnIndex(ChecklistItemTable.ChecklistItemColumns.CHECKLIST_ID));
        this.regulationItemId = cursor.getInt(cursor.getColumnIndex(ChecklistItemTable.ChecklistItemColumns.REGULATION_ITEM_ID));
        this.context = context;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(ChecklistItemTable.ChecklistItemColumns._ID, this.id);
        values.put(ChecklistItemTable.ChecklistItemColumns.NAME, this.name);
        values.put(ChecklistItemTable.ChecklistItemColumns.VALUE, this.value);
        values.put(ChecklistItemTable.ChecklistItemColumns.CHECKLIST_ID, this.checklistId);
        values.put(ChecklistItemTable.ChecklistItemColumns.REGULATION_ITEM_ID, this.regulationItemId);
        return values;
    }

    public static ChecklistItem newInstance(Cursor cursor, Context context) {
        ChecklistItem checklistItem = new ChecklistItem();
        checklistItem.fromCursor(cursor, context);
        return checklistItem;
    }

    public static List<ChecklistItem> newInstanceList(JSONArray jsonArray) {
		List<ChecklistItem> list = new ArrayList<ChecklistItem>();
		for(int i = 0; i < jsonArray.length(); i++){
			JSONObject jsonObj;
			try {
				jsonObj = jsonArray.getJSONObject(i);
				ChecklistItem obj = ChecklistItem.newInstance(jsonObj);
				list.add(obj);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return list;
	}
	
	public static ChecklistItem newInstance(JSONObject jsonObject) {
		ChecklistItem obj = new ChecklistItem();
		
		try {
			int id = jsonObject.getInt(TAG_CHECKLIST_ITEM_ID);
			String name = jsonObject.getString(TAG_CHECKLIST_ITEM_NAME);
			String value = jsonObject.getString(TAG_VALUE);
			int checklistId = jsonObject.getInt(TAG_CHECKLIST_ID);
			int regulationItemId = jsonObject.getInt(TAG_REGULATION_ITEM_ID);
			
			
			obj.setId(id);
			obj.setName(name);
			obj.setValue(value);
			obj.setChecklistId(checklistId);
			obj.setRegulationItemId(regulationItemId);
			
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

	public int getChecklistId() {
		return checklistId;
	}

	public void setChecklistId(int checklistId) {
		this.checklistId = checklistId;
	}

	public int getRegulationItemId() {
		return regulationItemId;
	}

	public void setRegulationItemId(int regulationItemId) {
		this.regulationItemId = regulationItemId;
	}

	public void setId(int id) {
		this.id = id;
	}


}