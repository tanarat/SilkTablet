package org.hsc.silk;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

public class MainMenuActivity extends Activity {
	private GridView gvMenu;
	private MenuListAdapter menuListAdapter;
	private MainMenu mMenu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mMenu = new MainMenu();
		setContentView(R.layout.activity_main_menu);
		gvMenu = (GridView) findViewById(R.id.gvMenu);
		menuListAdapter = new MenuListAdapter(this);
		menuListAdapter.addAll(mMenu.getMenuList());
		gvMenu.setAdapter(menuListAdapter);
	}
	
	class MenuListAdapter extends ArrayAdapter<MainMenuItem>{
		private Context context;
		public MenuListAdapter(Context context) {
			super(context, 0);
			this.context = context;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Button b = new Button(context);
			b.setText(getItem(position).getMenuName());
			return b;
		}
	}

}
