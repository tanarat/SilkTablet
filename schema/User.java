package org.hsc.silk.model;
import android.provider.BaseColumns;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class User extends ModelBase {
    private Context context;
    private int id;
    private String name;
    private String password;
    private String title;
    private String fullName;


    public User() {
        super();
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


}