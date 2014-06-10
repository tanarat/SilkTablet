package org.hsc.silk;

import java.util.ArrayList;
import java.util.List;

public class MainMenu {
	private List<MainMenuItem> menuList = new ArrayList<MainMenuItem>();
	public MainMenu(){
		menuList.add(new MainMenuItem("Update"));
		menuList.add(new MainMenuItem("Sheet"));
		menuList.add(new MainMenuItem("Logout"));
	}
	public List<MainMenuItem> getMenuList() {
		// TODO Auto-generated method stub
		return menuList;
	}
	
}
