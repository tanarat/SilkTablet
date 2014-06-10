package org.hsc.silk.model;
import java.util.ArrayList;
import java.util.List;

import org.hsc.silk.table.BpLocationTable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

public class BpLocation extends ModelBase implements Parcelable{

	public static final String TAG_BP_LOCATION_ID = "id";
	public static final String TAG_BP_LOCATION_NAME = "name";
	public static final String TAG_PHONE = "phone";
	public static final String TAG_PHONE2 = "phone2";
	public static final String TAG_FAX = "fax";
	public static final String TAG_ADDRESS1 = "address1";
	public static final String TAG_ADDRESS2 = "address2";
	public static final String TAG_ADDRESS3 = "address3";
	public static final String TAG_ADDRESS4 = "address4";
	public static final String TAG_POSTAL = "postal";
	public static final String TAG_CITY = "city";

    private Context context;
    private int id;
    private String name;
    private String phone;
    private String phone2;
    private String fax;
    private String address1;
    private String address2;
    private String address3;
    private String address4;
    private String postal;
    private String city;


    public BpLocation() {
        super();
    }
    public BpLocation(Parcel in){
		readFromParcel(in);
	}

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void fromCursor(Cursor cursor, Context context) {
        this.id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
        this.name = cursor.getString(cursor.getColumnIndex(BpLocationTable.BpLocationColumns.NAME));
        this.phone = cursor.getString(cursor.getColumnIndex(BpLocationTable.BpLocationColumns.PHONE));
        this.phone2 = cursor.getString(cursor.getColumnIndex(BpLocationTable.BpLocationColumns.PHONE2));
        this.fax = cursor.getString(cursor.getColumnIndex(BpLocationTable.BpLocationColumns.FAX));
        this.address1 = cursor.getString(cursor.getColumnIndex(BpLocationTable.BpLocationColumns.ADDRESS1));
        this.address2 = cursor.getString(cursor.getColumnIndex(BpLocationTable.BpLocationColumns.ADDRESS2));
        this.address3 = cursor.getString(cursor.getColumnIndex(BpLocationTable.BpLocationColumns.ADDRESS3));
        this.address4 = cursor.getString(cursor.getColumnIndex(BpLocationTable.BpLocationColumns.ADDRESS4));
        this.postal = cursor.getString(cursor.getColumnIndex(BpLocationTable.BpLocationColumns.POSTAL));
        this.city = cursor.getString(cursor.getColumnIndex(BpLocationTable.BpLocationColumns.CITY));
        this.context = context;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(BpLocationTable.BpLocationColumns._ID, this.id);
        values.put(BpLocationTable.BpLocationColumns.NAME, this.name);
        values.put(BpLocationTable.BpLocationColumns.PHONE, this.phone);
        values.put(BpLocationTable.BpLocationColumns.PHONE2, this.phone2);
        values.put(BpLocationTable.BpLocationColumns.FAX, this.fax);
        values.put(BpLocationTable.BpLocationColumns.ADDRESS1, this.address1);
        values.put(BpLocationTable.BpLocationColumns.ADDRESS2, this.address2);
        values.put(BpLocationTable.BpLocationColumns.ADDRESS3, this.address3);
        values.put(BpLocationTable.BpLocationColumns.ADDRESS4, this.address4);
        values.put(BpLocationTable.BpLocationColumns.POSTAL, this.postal);
        values.put(BpLocationTable.BpLocationColumns.CITY, this.city);
        return values;
    }

    public static BpLocation newInstance(Cursor cursor, Context context) {
        BpLocation bpLocation = new BpLocation();
        bpLocation.fromCursor(cursor, context);
        return bpLocation;
    }

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getAddress4() {
		return address4;
	}

	public void setAddress4(String address4) {
		this.address4 = address4;
	}

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(getId());
		dest.writeString(getName());
		dest.writeString(getPhone());
		dest.writeString(getPhone2());
		dest.writeString(getFax());
		dest.writeString(getAddress1());
		dest.writeString(getAddress2());
		dest.writeString(getAddress3());
		dest.writeString(getAddress4());
		dest.writeString(getPostal());
		dest.writeString(getCity());
	}
	public void readFromParcel(Parcel in){
		setId(in.readInt());
		setName(in.readString());
		setPhone(in.readString());
		setPhone2(in.readString());
		setFax(in.readString());
		setAddress1(in.readString());
		setAddress2(in.readString());
		setAddress3(in.readString());
		setAddress4(in.readString());
		setPostal(in.readString());
		setCity(in.readString());
	}
	public static final Parcelable.Creator<BpLocation> CREATOR = new Parcelable.Creator<BpLocation> () {

		@Override
		public BpLocation createFromParcel(Parcel in) {
			// TODO Auto-generated method stub
			return new BpLocation(in);
		}

		@Override
		public BpLocation[] newArray(int size) {
			// TODO Auto-generated method stub
			return new BpLocation[size];
		}
	};
	public static List<BpLocation> newInstanceList(JSONArray jsonArray) {
		List<BpLocation> bpList = new ArrayList<BpLocation>();
		for(int i = 0; i < jsonArray.length(); i++){
			JSONObject jsonObj;
			try {
				jsonObj = jsonArray.getJSONObject(i);
				BpLocation auditType = BpLocation.newInstance(jsonObj);
				bpList.add(auditType);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return bpList;
	}
	
	private static BpLocation newInstance(JSONObject jsonObj) {
		BpLocation obj = new BpLocation();
		
		try {
			obj.setId(jsonObj.getInt(TAG_BP_LOCATION_ID));
			obj.setName(jsonObj.getString(TAG_BP_LOCATION_NAME));
			obj.setPhone(jsonObj.getString(TAG_PHONE));
			obj.setPhone2(jsonObj.getString(TAG_PHONE2));
			obj.setFax(jsonObj.getString(TAG_FAX));
			obj.setAddress1(jsonObj.getString(TAG_ADDRESS1));
			obj.setAddress2(jsonObj.getString(TAG_ADDRESS2));
			obj.setAddress3(jsonObj.getString(TAG_ADDRESS3));
			obj.setAddress4(jsonObj.getString(TAG_ADDRESS4));
			obj.setPostal(jsonObj.getString(TAG_POSTAL));
			obj.setCity(jsonObj.getString(TAG_CITY));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return obj;
	}
}