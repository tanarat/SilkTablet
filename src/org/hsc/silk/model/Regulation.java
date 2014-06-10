package org.hsc.silk.model;
import java.util.ArrayList;
import java.util.List;

import org.hsc.silk.table.RegulationTable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.provider.BaseColumns;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class Regulation extends ModelBase {
	public static final String TAG_REGULATION_ID = "regulationId";
	public static final String TAG_REGULATION_NAME = "regulationName";
	
    private Context context;
    private int id;
    private String name;


    public Regulation() {
        super();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void fromCursor(Cursor cursor, Context context) {
        this.id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
        this.name = cursor.getString(cursor.getColumnIndex(RegulationTable.RegulationColumns.NAME));
        this.context = context;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(RegulationTable.RegulationColumns._ID, this.id);
        values.put(RegulationTable.RegulationColumns.NAME, this.name);
        return values;
    }

    public static Regulation newInstance(Cursor cursor, Context context) {
        Regulation regulation = new Regulation();
        regulation.fromCursor(cursor, context);
        return regulation;
    }

    public static List<Regulation> newInstanceList(JSONArray jsonArray) {
		List<Regulation> list = new ArrayList<Regulation>();
		for(int i = 0; i < jsonArray.length(); i++){
			JSONObject jsonObj;
			try {
				jsonObj = jsonArray.getJSONObject(i);
				Regulation obj = Regulation.newInstance(jsonObj);
				list.add(obj);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return list;
	}
	
	public static Regulation newInstance(JSONObject jsonObject) {
		Regulation obj = new Regulation();
		
		try {
			int id = jsonObject.getInt(TAG_REGULATION_ID);
			String name = jsonObject.getString(TAG_REGULATION_NAME);
			
			obj.setId(id);
			obj.setName(name);
			
			
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

	public void setId(int id) {
		this.id = id;
	}


}