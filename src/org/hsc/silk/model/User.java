package org.hsc.silk.model;
import org.hsc.silk.table.UserTable;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class User extends ModelBase implements Parcelable{
    private Context context;
    private int id;
    private String name;
    private String password;
    private String title;
    private String fullName;


    public User() {
        super();
    }

    public User(Parcel in) {
    	readFromParcel(in);
	}

	public void readFromParcel(Parcel in) {
		this.id = in.readInt();
		this.name = in.readString();
		this.password = in.readString();
		this.title = in.readString();
		this.fullName = in.readString();
	}
	public void writeToParcel(Parcel dest, int flags){
		dest.writeInt(this.id);
		dest.writeString(this.name);
		dest.writeString(this.password);
		dest.writeString(this.title);
		dest.writeString(this.fullName);
	}
	@Override
    public int getId() {
        return id;
    }

    @Override
    public void fromCursor(Cursor cursor, Context context) {
        this.id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
        this.name = cursor.getString(cursor.getColumnIndex(UserTable.UserColumns.NAME));
        this.password = cursor.getString(cursor.getColumnIndex(UserTable.UserColumns.PASSWORD));
        this.title = cursor.getString(cursor.getColumnIndex(UserTable.UserColumns.TITLE));
        this.fullName = cursor.getString(cursor.getColumnIndex(UserTable.UserColumns.FULL_NAME));
        this.context = context;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(BaseColumns._ID, this.id);
        values.put(UserTable.UserColumns.NAME, this.name);
        values.put(UserTable.UserColumns.PASSWORD, this.password);
        values.put(UserTable.UserColumns.TITLE, this.title);
        values.put(UserTable.UserColumns.FULL_NAME, this.fullName);
        return values;
    }

    public static User newInstance(Cursor cursor, Context context) {
        User user = new User();
        user.fromCursor(cursor, context);
        return user;
    }
    public static User newInstance(JSONObject jsonObj) {
    	User user = new User();
    	try {
			user.id = jsonObj.getInt("id");
			user.name = jsonObj.getString("name");
			user.password = jsonObj.getString("password");
			user.title = jsonObj.getString("title");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {

		@Override
		public User createFromParcel(Parcel in) {
			return new User(in);
		}

		@Override
		public User[] newArray(int size) {
			return new User[size];
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "User : ["+ id + ", " +name + ", " + title + ", " + fullName + ", " + password + "]";
	}


}