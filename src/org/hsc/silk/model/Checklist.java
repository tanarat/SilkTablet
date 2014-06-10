package org.hsc.silk.model;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Pack200.Packer;

import org.hsc.silk.table.ChecklistTable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class Checklist extends ModelBase implements Parcelable {
	public static final String TAG_CHECKLIST_ID = "checklistId";
	public static final String TAG_CHECKLIST_NAME = "checklistName";
	public static final String TAG_REGULATION_ID = "regulationId";
	
	private Context context;
    private int id;
    private String name;
    private String description;
    private int regulationId;


    public Checklist() {
        super();
    }
    public Checklist(Parcel in){
		readFromParcel(in);
	}
    @Override
    public int getId() {
        return id;
    }

    @Override
    public void fromCursor(Cursor cursor, Context context) {
        this.id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
        this.name = cursor.getString(cursor.getColumnIndex(ChecklistTable.ChecklistColumns.NAME));
        this.description = cursor.getString(cursor.getColumnIndex(ChecklistTable.ChecklistColumns.DESCRIPTION));
        this.regulationId = cursor.getInt(cursor.getColumnIndex(ChecklistTable.ChecklistColumns.REGULATION_ID));
        this.context = context;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(ChecklistTable.ChecklistColumns._ID, this.id);
        values.put(ChecklistTable.ChecklistColumns.NAME, this.name);
        values.put(ChecklistTable.ChecklistColumns.DESCRIPTION, this.description);
        values.put(ChecklistTable.ChecklistColumns.REGULATION_ID, this.regulationId);
        return values;
    }

    public static Checklist newInstance(Cursor cursor, Context context) {
        Checklist checklist = new Checklist();
        checklist.fromCursor(cursor, context);
        return checklist;
    }

	public static List<Checklist> newInstanceList(JSONArray jsonArray) {
		List<Checklist> checklistList = new ArrayList<Checklist>();
		for(int i = 0; i < jsonArray.length(); i++){
			JSONObject checklistJsonObj;
			try {
				checklistJsonObj = jsonArray.getJSONObject(i);
				Checklist checklist = Checklist.newInstance(checklistJsonObj);
				checklistList.add(checklist);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return checklistList;
	}
	
	public static Checklist newInstance(JSONObject jsonObject) {
		Checklist checklist = new Checklist();
		
		try {
			int checklistId = jsonObject.getInt(TAG_CHECKLIST_ID);
			String checklistName = jsonObject.getString(TAG_CHECKLIST_NAME);
			int regulationId = jsonObject.getInt(TAG_REGULATION_ID);
			
			checklist.setId(checklistId);
			checklist.setName(checklistName);
			checklist.setRegulationId(regulationId);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return checklist;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getRegulationId() {
		return regulationId;
	}

	public void setRegulationId(int regulationId) {
		this.regulationId = regulationId;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void readFromParcel(Parcel in){
		setId(in.readInt());
		setName(in.readString());
		setRegulationId(in.readInt());
	}
	
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(getId());
		dest.writeString(getName());
		dest.writeInt(getRegulationId());
	}
	
	public static final Parcelable.Creator<Checklist> CREATOR = new Parcelable.Creator<Checklist> () {

		@Override
		public Checklist createFromParcel(Parcel in) {
			// TODO Auto-generated method stub
			return new Checklist(in);
		}

		@Override
		public Checklist[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Checklist[size];
		}
	};
}