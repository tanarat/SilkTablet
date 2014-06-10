package org.hsc.silk.model;
import java.util.ArrayList;
import java.util.List;

import org.hsc.silk.dao.ChecklistItemDao;
import org.hsc.silk.table.SheetItemTable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;

public class SheetItem extends ModelBase {
	private Context context;
    private int id;
    private int sheetId;
    private int checklistItemId;
    private ChecklistItem checklistItem;
    private int regulationItemId;
    private int answer;
    private String remark;
    private String remarkChoice ;


    public SheetItem() {
        super();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void fromCursor(Cursor cursor, Context context) {
    	this.id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
        this.sheetId = cursor.getInt(cursor.getColumnIndex(SheetItemTable.SheetItemColumns.SHEET_ID));
        this.checklistItemId = cursor.getInt(cursor.getColumnIndex(SheetItemTable.SheetItemColumns.CHECKLIST_ITEM_ID));
        this.regulationItemId = cursor.getInt(cursor.getColumnIndex(SheetItemTable.SheetItemColumns.REGULATION_ITEM_ID));
        this.answer = cursor.getInt(cursor.getColumnIndex(SheetItemTable.SheetItemColumns.ANSWER));
        this.remark = cursor.getString(cursor.getColumnIndex(SheetItemTable.SheetItemColumns.REMARK));
        this.remarkChoice = cursor.getString(cursor.getColumnIndex(SheetItemTable.SheetItemColumns.REMARK_CHOICE));
        this.context = context;
    }

    @Override
    public ContentValues toContentValues() {
    	ContentValues values = new ContentValues();
        values.put(SheetItemTable.SheetItemColumns.SHEET_ID, this.sheetId);
        values.put(SheetItemTable.SheetItemColumns.CHECKLIST_ITEM_ID, this.checklistItemId);
        values.put(SheetItemTable.SheetItemColumns.REGULATION_ITEM_ID, this.regulationItemId);
        values.put(SheetItemTable.SheetItemColumns.ANSWER, this.answer);
        values.put(SheetItemTable.SheetItemColumns.REMARK, this.remark);
        values.put(SheetItemTable.SheetItemColumns.REMARK_CHOICE, this.remarkChoice);
        return values;
    }

    public static SheetItem newInstance(Cursor cursor, Context context) {
        SheetItem sheetItem = new SheetItem();
        sheetItem.fromCursor(cursor, context);
        return sheetItem;
    }

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public int getSheetId() {
		return sheetId;
	}

	public void setSheetId(int sheetId) {
		this.sheetId = sheetId;
	}

	public int getChecklistItemId() {
		return checklistItemId;
	}

	public void setChecklistItemId(int checklistItemId) {
		this.checklistItemId = checklistItemId;
	}

	public int getAnswer() {
		return answer;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ChecklistItem getChecklistItem() {
		if(checklistItem == null){
			ChecklistItemDao dao = new ChecklistItemDao(context);
			checklistItem = dao.getById(getChecklistItemId());
		}
		return checklistItem;
	}

	public void setChecklistItem(ChecklistItem checklistItem) {
		this.checklistItem = checklistItem;
	}

	public int getRegulationItemId() {
		return regulationItemId;
	}

	public void setRegulationItemId(int regulationItemId) {
		this.regulationItemId = regulationItemId;
	}

	public String getRemarkChoice() {
		return remarkChoice;
	}

	public void setRemarkChoice(String remarkChoice) {
		this.remarkChoice = remarkChoice;
	}
	
	private List<SheetItemListener> listeners = new ArrayList<SheetItemListener>();
    public interface SheetItemListener{
    	public void onSheetItemChanged(SheetItem sheetItem);
    }
    public void addSheetItemListener(SheetItemListener sheetItemListener) {
		this.listeners.add(sheetItemListener);
	}

	public void notifyListener() {
		for (SheetItemListener listener : listeners) {
			listener.onSheetItemChanged(this);
		}
	}

	

}