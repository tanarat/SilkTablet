package org.hsc.silk.model;
import java.util.Date;

import org.hsc.silk.dao.SheetDao;
import org.hsc.silk.dao.SheetItemDao;
import org.hsc.silk.table.AuditTypeTable;
import org.hsc.silk.table.SheetTable;
import org.hsc.silk.table.SheetTable;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class Sheet extends ModelBase implements Parcelable{
    private Context context;
    private int id;
    private String name;
    private int checklistId;
    private int bpartnerId;
    private int bpLocationId;
    private int auditType;
    private Date auditDate;
    private int auditTimeslot;
    private int status;
    private boolean createSheetItem;
    private String checklistName;
    private String auditTypeName;

    private SheetDao sheetDao;
    private SheetItemDao sheetItemDao;

    public Sheet() {
        super();
    }

    public Sheet(String name, int checklistId, int bpartnerId, int bpLocationId, int auditTypeId, Date mStart) {
    	this.name = name;
    	this.checklistId = checklistId;
    	this.bpartnerId = bpartnerId;
    	this.bpLocationId = bpLocationId;
    	this.auditType = auditTypeId;
    	this.auditDate = mStart;
	}

	public Sheet(Parcel in) {
		readFromParcel(in);
	}

	@Override
    public int getId() {
        return id;
    }

    @Override
    public void fromCursor(Cursor cursor, Context context) {
        this.id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
        this.name = cursor.getString(cursor.getColumnIndex(SheetTable.SheetColumns.NAME));
        this.checklistId = cursor.getInt(cursor.getColumnIndex(SheetTable.SheetColumns.CHECKLIST_ID));
        this.bpartnerId = cursor.getInt(cursor.getColumnIndex(SheetTable.SheetColumns.BPARTNER_ID));
        this.bpLocationId = cursor.getInt(cursor.getColumnIndex(SheetTable.SheetColumns.BP_LOCATION_ID));
        this.auditType = cursor.getInt(cursor.getColumnIndex(SheetTable.SheetColumns.AUDIT_TYPE));
        this.auditDate = new Date(cursor.getLong(cursor.getColumnIndex(SheetTable.SheetColumns.AUDIT_DATE)));
        this.auditTimeslot = cursor.getInt(cursor.getColumnIndex(SheetTable.SheetColumns.AUDIT_TIMESLOT));
        this.status = cursor.getInt(cursor.getColumnIndex(SheetTable.SheetColumns.STATUS));
        this.createSheetItem = (cursor.getInt(cursor.getColumnIndex(SheetTable.SheetColumns.CREATE_SHEET_ITEM)) == 1);
        
        int columnIndex = cursor.getColumnIndex("checklist_name");
        if(columnIndex != -1)
        	this.checklistName = cursor.getString(columnIndex);
        
        columnIndex = cursor.getColumnIndex("audit_type_name");
        if(columnIndex != -1)
        	this.auditTypeName = cursor.getString(columnIndex);
        
//        this.context = context;
        setContext(context);
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(SheetTable.SheetColumns.NAME, this.name);
        values.put(SheetTable.SheetColumns.CHECKLIST_ID, this.checklistId);
        values.put(SheetTable.SheetColumns.BPARTNER_ID, this.bpartnerId);
        values.put(SheetTable.SheetColumns.BP_LOCATION_ID, this.bpLocationId);
        values.put(SheetTable.SheetColumns.AUDIT_TYPE, this.auditType);
        values.put(SheetTable.SheetColumns.AUDIT_DATE, this.auditDate.getTime());
        values.put(SheetTable.SheetColumns.AUDIT_TIMESLOT, this.auditTimeslot);
        values.put(SheetTable.SheetColumns.STATUS, this.status);
        values.put(SheetTable.SheetColumns.CREATE_SHEET_ITEM,
				(this.createSheetItem) ? 1 : 0);
        return values;
    }

    public static Sheet newInstance(Cursor cursor, Context context) {
        Sheet sheet = new Sheet();
        sheet.fromCursor(cursor, context);
        return sheet;
    }

	public boolean isCreateSheetItem() {
		return createSheetItem;
	}

	public void setCreateSheetItem(boolean createSheetItem) {
		this.createSheetItem = createSheetItem;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
		sheetItemDao = new SheetItemDao(context);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getChecklistId() {
		return checklistId;
	}

	public void setChecklistId(int checklistId) {
		this.checklistId = checklistId;
	}

	public int getBpartnerId() {
		return bpartnerId;
	}

	public void setBpartnerId(int bpartnerId) {
		this.bpartnerId = bpartnerId;
	}

	public int getBpLocationId() {
		return bpLocationId;
	}

	public void setBpLocationId(int bpLocationId) {
		this.bpLocationId = bpLocationId;
	}

	public int getAuditType() {
		return auditType;
	}

	public void setAuditType(int auditType) {
		this.auditType = auditType;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public int getAuditTimeslot() {
		return auditTimeslot;
	}

	public void setAuditTimeslot(int auditTimeslot) {
		this.auditTimeslot = auditTimeslot;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getChecklistName() {
		return checklistName;
	}

	public void setChecklistName(String checklistName) {
		this.checklistName = checklistName;
	}

	public String getAuditTypeName() {
		return auditTypeName;
	}

	public void setAuditTypeName(String auditTypeName) {
		this.auditTypeName = auditTypeName;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void readFromParcel(Parcel in){
		setId(in.readInt());
		setName(in.readString());
		setChecklistId(in.readInt());
		setBpartnerId(in.readInt());
		setBpLocationId(in.readInt());
		setAuditType(in.readInt());
		setAuditDate((Date)in.readSerializable());
		setCreateSheetItem(in.readByte() != 0);
		setChecklistName(in.readString());
		setAuditTypeName(in.readString());
	}
	
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(getId());
		dest.writeString(getName());
		dest.writeInt(getChecklistId());
		dest.writeInt(getBpartnerId());
		dest.writeInt(getBpLocationId());
		dest.writeInt(getAuditType());
		dest.writeSerializable(getAuditDate());
		dest.writeByte((byte) (isCreateSheetItem()  ? 1 : 0));
		dest.writeString(getChecklistName());
		dest.writeString(getAuditTypeName());
	}
	
	public static final Parcelable.Creator<Sheet> CREATOR = new Parcelable.Creator<Sheet> () {

		@Override
		public Sheet createFromParcel(Parcel in) {
			// TODO Auto-generated method stub
			return new Sheet(in);
		}

		@Override
		public Sheet[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Sheet[size];
		}
	};

	private int sheetItemCount = -1;
	
	public int getSheetItemCount() {
		if(sheetItemCount == -1){
			sheetItemCount = sheetItemDao.countSheetItem(this.id);
		}
		return sheetItemCount;
	}

	public int getNumberOfConform() {
		return sheetItemDao.countConformItem(this.id);
	}


}