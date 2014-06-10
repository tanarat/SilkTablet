package org.hsc.silk.model;
import java.util.ArrayList;
import java.util.List;

import org.hsc.silk.table.RemarkChoiceTable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;

public class RemarkChoice extends ModelBase {
	public static final String TAG_REMARK_CHOICE_ID = "remarkChoiceId";
	public static final String TAG_REMARK_CHOICE_NAME = "name";
	public static final String TAG_VALUE = "value";
	public static final String TAG_DESCRIPTION = "description";
	public static final String TAG_CHECKLIST_ITEM_ID = "checklistItemId";
    private Context context;
    private int id;
    private String name;
    private String value;
    private String description;
    private int checklistItemId;


    public RemarkChoice() {
        super();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void fromCursor(Cursor cursor, Context context) {
        this.id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
        this.name = cursor.getString(cursor.getColumnIndex(RemarkChoiceTable.RemarkChoiceColumns.NAME));
        this.value = cursor.getString(cursor.getColumnIndex(RemarkChoiceTable.RemarkChoiceColumns.VALUE));
        this.description = cursor.getString(cursor.getColumnIndex(RemarkChoiceTable.RemarkChoiceColumns.DESCRIPTION));
        this.checklistItemId = cursor.getInt(cursor.getColumnIndex(RemarkChoiceTable.RemarkChoiceColumns.CHECKLIST_ITEM_ID));
        this.context = context;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(RemarkChoiceTable.RemarkChoiceColumns._ID, this.id);
        values.put(RemarkChoiceTable.RemarkChoiceColumns.NAME, this.name);
        values.put(RemarkChoiceTable.RemarkChoiceColumns.VALUE, this.value);
        values.put(RemarkChoiceTable.RemarkChoiceColumns.DESCRIPTION, this.description);
        values.put(RemarkChoiceTable.RemarkChoiceColumns.CHECKLIST_ITEM_ID, this.checklistItemId);
        return values;
    }

    public static RemarkChoice newInstance(Cursor cursor, Context context) {
        RemarkChoice remarkChoice = new RemarkChoice();
        remarkChoice.fromCursor(cursor, context);
        return remarkChoice;
    }
    public static List<RemarkChoice> newInstanceList(JSONArray jsonArray) {
		List<RemarkChoice> remarkChoiceList = new ArrayList<RemarkChoice>();
		for(int i = 0; i < jsonArray.length(); i++){
			JSONObject jsonObj;
			try {
				jsonObj = jsonArray.getJSONObject(i);
				RemarkChoice remarkChoice = RemarkChoice.newInstance(jsonObj);
				remarkChoiceList.add(remarkChoice);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return remarkChoiceList;
	}

	public static RemarkChoice newInstance(JSONObject jsonObj) {
		RemarkChoice remarkChoice = new RemarkChoice();
		try{
			remarkChoice.setId(jsonObj.getInt(TAG_REMARK_CHOICE_ID));
			remarkChoice.setName(jsonObj.getString(TAG_REMARK_CHOICE_NAME));
			remarkChoice.setValue(jsonObj.getString(TAG_VALUE));
			remarkChoice.setDescription(jsonObj.getString(TAG_DESCRIPTION));
			remarkChoice.setChecklistItemId(jsonObj.getInt(TAG_CHECKLIST_ITEM_ID));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return remarkChoice;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getChecklistItemId() {
		return checklistItemId;
	}

	public void setChecklistItemId(int checklistItemId) {
		this.checklistItemId = checklistItemId;
	}

	public void setId(int id) {
		this.id = id;
	}

}