package org.hsc.silk.model;
import java.util.ArrayList;
import java.util.List;

import org.hsc.silk.table.AuditTypeTable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

public class AuditType extends ModelBase implements Parcelable{
	public static final String TAG_AUDIT_TYPE_ID = "id";
	public static final String TAG_AUDIT_TYPE_NAME = "name";
    private Context context;
    private int id;
    private String name;


    public AuditType() {
        super();
    }
    public AuditType(Parcel in){
		readFromParcel(in);
	}

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void fromCursor(Cursor cursor, Context context) {
        this.id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
        this.name = cursor.getString(cursor.getColumnIndex(AuditTypeTable.AuditTypeColumns.NAME));
        this.context = context;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(AuditTypeTable.AuditTypeColumns._ID, this.id);
        values.put(AuditTypeTable.AuditTypeColumns.NAME, this.name);
        return values;
    }

    public static AuditType newInstance(Cursor cursor, Context context) {
        AuditType auditType = new AuditType();
        auditType.fromCursor(cursor, context);
        return auditType;
    }
    public static List<AuditType> newInstanceList(JSONArray jsonArray) {
		List<AuditType> auditTypeList = new ArrayList<AuditType>();
		for(int i = 0; i < jsonArray.length(); i++){
			JSONObject jsonObj;
			try {
				jsonObj = jsonArray.getJSONObject(i);
				AuditType auditType = AuditType.newInstance(jsonObj);
				auditTypeList.add(auditType);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return auditTypeList;
	}

	private static AuditType newInstance(JSONObject jsonObj) {
		AuditType auditType = new AuditType();
		
		try {
			int id = jsonObj.getInt(TAG_AUDIT_TYPE_ID);
			String name = jsonObj.getString(TAG_AUDIT_TYPE_NAME);

			
			auditType.setId(id);
			auditType.setName(name);

			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return auditType;
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
	}
	
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(getId());
		dest.writeString(getName());
	}
	
	public static final Parcelable.Creator<AuditType> CREATOR = new Parcelable.Creator<AuditType> () {

		@Override
		public AuditType createFromParcel(Parcel in) {
			// TODO Auto-generated method stub
			return new AuditType(in);
		}

		@Override
		public AuditType[] newArray(int size) {
			// TODO Auto-generated method stub
			return new AuditType[size];
		}
	};
}