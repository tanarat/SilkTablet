package org.hsc.silk.model;

import java.util.List;

import org.json.JSONArray;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

abstract public class ModelBase {

	public ModelBase() {
		super();
	}
	
	abstract public void fromCursor(Cursor cursor, Context context);
	abstract public int getId();
	abstract public ContentValues toContentValues();
	

}