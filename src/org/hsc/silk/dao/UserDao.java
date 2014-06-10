package org.hsc.silk.dao;

import java.util.List;

import org.hsc.silk.db.SILKProvider;
import org.hsc.silk.model.User;
import org.hsc.silk.table.UserTable;

import android.content.Context;
import android.util.Log;

public class UserDao extends Dao<User> {
	public static final String tag = "UserDao";

	public UserDao(Context context) {
		super(User.class, context, SILKProvider.USER_CONTENT_URI);
	}
	
	public User getUser(String name, String password){
		Log.i(tag, "getUser("+name+","+password+")");
		String selection = UserTable.UserColumns.NAME + " = ? and " + UserTable.UserColumns.PASSWORD + " = ?";
		String[] seletecionArgs = new String[]{name, password};
		List<User> list = get(selection, seletecionArgs); 
		if(list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}
}
