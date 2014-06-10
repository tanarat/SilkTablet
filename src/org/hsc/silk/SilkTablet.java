package org.hsc.silk;

import org.hsc.silk.dao.SettingDao;
import org.hsc.silk.model.Setting;
import org.hsc.silk.model.User;

import android.app.Application;
import android.util.Log;

public class SilkTablet extends Application {
	private static String tag = "SilkTablet";
	private User user;
	
	
	
	public SilkTablet() {
		super();
		Log.i(tag, "SilkTablet object created");
	
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	
	
}
