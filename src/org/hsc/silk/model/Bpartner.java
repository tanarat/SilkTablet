package org.hsc.silk.model;
import java.util.ArrayList;
import java.util.List;

import org.hsc.silk.table.BpartnerTable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class Bpartner extends ModelBase implements Parcelable {
	public static final String TAG_BPARTNER_ID = "id";
	public static final String TAG_BPARTNER_NAME = "name";
    private Context context;
    private int id;
    private String name;


    public Bpartner() {
        super();
    }
    public Bpartner(Parcel in){
		readFromParcel(in);
	}

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void fromCursor(Cursor cursor, Context context) {
        this.id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
        this.name = cursor.getString(cursor.getColumnIndex(BpartnerTable.BpartnerColumns.NAME));
        this.context = context;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(BpartnerTable.BpartnerColumns._ID, this.id);
        values.put(BpartnerTable.BpartnerColumns.NAME, this.name);
        return values;
    }

    public static Bpartner newInstance(Cursor cursor, Context context) {
        Bpartner bpartner = new Bpartner();
        bpartner.fromCursor(cursor, context);
        return bpartner;
    }
    public static List<Bpartner> newInstanceList(JSONArray jsonArray) {
		List<Bpartner> bpList = new ArrayList<Bpartner>();
		for(int i = 0; i < jsonArray.length(); i++){
			JSONObject jsonObj;
			try {
				jsonObj = jsonArray.getJSONObject(i);
				Bpartner auditType = Bpartner.newInstance(jsonObj);
				bpList.add(auditType);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return bpList;
	}

	private static Bpartner newInstance(JSONObject jsonObj) {
		Bpartner obj = new Bpartner();
		
		try {
			int id = jsonObj.getInt(TAG_BPARTNER_ID);
			String name = jsonObj.getString(TAG_BPARTNER_NAME);

			
			obj.setId(id);
			obj.setName(name);

			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return obj;
	}

	public void readFromParcel(Parcel in){
		setId(in.readInt());
		setName(in.readString());
	}
	
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(getId());
		dest.writeString(getName());
	}
	
	public static final Parcelable.Creator<Bpartner> CREATOR = new Parcelable.Creator<Bpartner> () {

		@Override
		public Bpartner createFromParcel(Parcel in) {
			// TODO Auto-generated method stub
			return new Bpartner(in);
		}

		@Override
		public Bpartner[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Bpartner[size];
		}
	};
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


}