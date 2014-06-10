package org.hsc.silk.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hsc.silk.dao.SheetItemDao;
import org.hsc.silk.model.SheetItem.SheetItemListener;


import android.content.Context;
import android.util.Log;

public class AuditSheetModel implements SheetItemListener {
	public static String tag = "AuditSheetModel";
	private Context context;

	private SheetItemDao sheetItemPO;
	private int sheetId;
	private List<RegItemRow> regItemRowList;
	
	private Map<Integer, List<SheetItem>> sheetItemMap = new HashMap<Integer, List<SheetItem>>();
	
	public AuditSheetModel(Context context,int sheetId){
		this.context = context;
		this.setSheetId(sheetId);
		
		
		this.regItemRowList = RegItemRow.getList(sheetId, context);
		sheetItemPO = new SheetItemDao(context);
		
		for (int i = 0; i < regItemRowList.size(); i++) {
			List<SheetItem> sheetItemList = sheetItemPO.getSheetItemList(sheetId, regItemRowList.get(i).getRegItemId());
			for (SheetItem sheetItem : sheetItemList) {
//				Log.i(tag, "sheetItem : " + sheetItem.getValue() + " " + sheetItem.getName());
				sheetItem.addSheetItemListener(this);
			}
			sheetItemMap.put(i, sheetItemList);
		}

	}

	public List<RegItemRow> getRegItemRowList() {
		return regItemRowList;
	}

	public void setRegItemRowList(List<RegItemRow> regItemRowList) {
		this.regItemRowList = regItemRowList;
	}

	public List<SheetItem> getSheetItemListAt(int index){
		return sheetItemMap.get(index);
	}

	public void updateRegItemRow(int regulationItemId) {
		RegItemRow regItemRow = findRegItemRow(regulationItemId);
		regItemRow.updateNoConformItem(context);
		notifyRegItemListChanged();
	}
	
	public interface AuditSheetModelListener{
		public void onRegItemListChanged();
	}
	private AuditSheetModelListener auditSheetModelListener;
	
	private void notifyRegItemListChanged(){
		if(auditSheetModelListener != null)
			auditSheetModelListener.onRegItemListChanged();
	}
	private RegItemRow findRegItemRow(int regulationItemId){
		for (RegItemRow regItemRow : regItemRowList) {
			if(regItemRow.getRegItemId() == regulationItemId){
				return regItemRow;
			}	
		}
		return null;
	}

	public int getSheetId() {
		return sheetId;
	}

	public void setSheetId(int sheetId) {
		this.sheetId = sheetId;
	}

	@Override
	public void onSheetItemChanged(SheetItem sheetItem) {
		updateRegItemRow(sheetItem.getRegulationItemId());
		
	}

	public AuditSheetModelListener getAuditSheetModelListener() {
		return auditSheetModelListener;
	}

	public void setAuditSheetModelListener(AuditSheetModelListener auditSheetModelListener) {
		this.auditSheetModelListener = auditSheetModelListener;
	}


	
}
