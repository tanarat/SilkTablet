package org.hsc.silk.checklist;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.hsc.silk.MyTimePickerDialog;
import org.hsc.silk.R;
import org.hsc.silk.dao.AuditTypeDao;
import org.hsc.silk.dao.BpDao;
import org.hsc.silk.dao.BpLocDao;
import org.hsc.silk.dao.ChecklistDao;
import org.hsc.silk.dao.SheetDao;
import org.hsc.silk.dao.SheetItemDao;
import org.hsc.silk.model.AuditType;
import org.hsc.silk.model.BpLocation;
import org.hsc.silk.model.Bpartner;
import org.hsc.silk.model.Checklist;
import org.hsc.silk.model.Sheet;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class SheetDetailActivity extends Activity implements OnClickListener , OnDateSetListener, OnTimeSetListener {
	
	public static final String tag = "SheetDetailActivity";
	public static final String SHEET = "sheet";
	
	static final int REQ_CHECKLIST = 0;
	static final int REQ_BPARTNER = 1;
	static final int REQ_BP_LOCATION = 2;
	static final int REQ_AUDIT_TYPE = 3;
	
	static final int DEFAULT_START_TIME = 9;

	
	
	private Button btnSave, btnCancel, btnChecklist, btnBPartner, btnBPLocation, btnAuditType, btnAuditDate, btnAuditTime, btnView;
	private TextView txtChecklist, txtAuditDate, txtAuditTime, txtBPartner, txtBPLocation, txtLocationDetail, txtAuditType, 
				txtNumberOfItem, txtNumberOfConform, txtNumberOfNonConform;
	private View buttomPanel, buttomPanel1;
	
	private Sheet mSheet;
	private Checklist mChecklist;
	private Bpartner mBpartner;
	private BpLocation mBpLoc;
	private AuditType mAuditType;
	private Date mStart, mFinish;
	private Calendar mCalendar;
	
	private int mYear, mMonth, mDay, mHour, mMinute;
	private boolean editMode = true;
	
	private SheetDao mSheetDao;
	private BpDao mBpDao;
	private BpLocDao mBpLocDao;
	private SheetItemDao mSheetItemDao;
	private ChecklistDao mChecklistDao;
	private AuditTypeDao mAuditTypeDao;
	
	private int sheetItemCount = 0, conformItemCount = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sheet_detail);
		
		mSheetDao = new SheetDao(this);
		mBpDao = new BpDao(this);
		mBpLocDao = new BpLocDao(this);
		mSheetItemDao = new SheetItemDao(this);
		mChecklistDao = new ChecklistDao(this);
		mAuditTypeDao = new AuditTypeDao(this);
		mCalendar = Calendar.getInstance();
		mSheet = getIntent().getParcelableExtra(SHEET);
//		Log.i(tag, "mSheet.getId() : " + mSheet.getId());
		editMode = (mSheet == null);
		
		initView();
	}
	
	private void initView(){
		btnSave = (Button) findViewById(R.id.btnSave);
		btnSave.setOnClickListener(this);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(this);
		btnChecklist = (Button) findViewById(R.id.btnChecklist);
		btnChecklist.setOnClickListener(this);
		
		btnBPartner = (Button) findViewById(R.id.btnBPartner);
		btnBPartner.setOnClickListener(this);
		
		btnBPLocation = (Button) findViewById(R.id.btnBPLocation);
		btnBPLocation.setOnClickListener(this);
		btnAuditType = (Button) findViewById(R.id.btnAuditType);
		btnAuditType.setOnClickListener(this);
		btnAuditDate = (Button) findViewById(R.id.btnAuditDate);
		btnAuditDate.setOnClickListener(this);
		txtAuditDate = (TextView) findViewById(R.id.txtAuditDate);
		btnAuditTime = (Button) findViewById(R.id.btnAuditTime);
		txtAuditTime = (TextView) findViewById(R.id.txtAuditTime);
		btnAuditTime.setOnClickListener(this);
		
		btnView = (Button) findViewById(R.id.btnView);
		btnView.setOnClickListener(this);
		
		txtChecklist = (TextView) findViewById(R.id.txtChecklist);
		txtBPartner = (TextView) findViewById(R.id.txtBPartner);
		txtBPLocation = (TextView) findViewById(R.id.txtBPLocation);
		txtLocationDetail = (TextView) findViewById(R.id.txtLocationDetail);
		txtAuditType = (TextView) findViewById(R.id.txtAuditType);
		
		txtNumberOfItem = (TextView) findViewById(R.id.txtNumberOfItem);
		txtNumberOfConform = (TextView) findViewById(R.id.txtNumberOfConform);
		txtNumberOfNonConform = (TextView) findViewById(R.id.txtNumberOfNonConform);
		
		buttomPanel = findViewById(R.id.buttomPanel);
		buttomPanel1 = findViewById(R.id.buttomPanel1);
		
		
		buttomPanel.setVisibility((editMode)? View.VISIBLE : View.INVISIBLE);
		buttomPanel1.setVisibility((editMode)? View.INVISIBLE : View.VISIBLE);
		btnChecklist.setEnabled(editMode);
		btnBPartner.setEnabled(editMode);
		btnBPLocation.setEnabled(editMode);
		btnAuditType.setEnabled(editMode);
		btnAuditDate.setEnabled(editMode);
		btnAuditTime.setEnabled(editMode);
		
		
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(mSheet != null){
			mSheet.setContext(this);
			setChecklist(mChecklistDao.getById(mSheet.getChecklistId()));
			setBpartner(mBpDao.getById(mSheet.getBpartnerId()));
			setBpLocation(mBpLocDao.getById(mSheet.getBpLocationId()));
			setAuditType(mAuditTypeDao.getById(mSheet.getAuditType()));
			setAuditDate(mSheet.getAuditDate());
			setSheetItemCount(mSheet.getSheetItemCount());
			setConformItemCount(mSheet.getNumberOfConform());
			
		}else{
			
			mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
			mMonth = mCalendar.get(Calendar.MONTH);
			mYear = mCalendar.get(Calendar.YEAR);
			mHour = DEFAULT_START_TIME;
		}
		toggleSaveButton();
	}
	public void setSheetItemCount(int numberOfSheetItem) {
		this.sheetItemCount = numberOfSheetItem;
		txtNumberOfItem.setText(sheetItemCount + " ");
	}
	private void setConformItemCount(int count){
		this.conformItemCount = count;
		txtNumberOfConform.setText(conformItemCount + " ");
		txtNumberOfNonConform.setText(sheetItemCount - conformItemCount + " ");
	}
	@Override
	public void onClick(View v) {
		Intent sIntent = null;
		switch (v.getId()) {
			case R.id.btnChecklist:
				sIntent = new Intent(this, SelectChecklistActivity.class);
				startActivityForResult(sIntent, REQ_CHECKLIST);
				break;
			case R.id.btnBPartner:
				sIntent = new Intent(this, SearchBpActivity.class);
				startActivityForResult(sIntent, REQ_BPARTNER);
				break;
			case R.id.btnBPLocation:
				if(mBpartner == null){
					Toast.makeText(this, "Please select BPartner", Toast.LENGTH_SHORT).show();
				}else{
					sIntent = new Intent(this, SelectBpLocActivity.class);
					sIntent.putExtra(SelectBpLocActivity.BP_ID, mBpartner.getId());
					startActivityForResult(sIntent, REQ_BP_LOCATION);
				}
				break;
			case R.id.btnAuditType:
				sIntent = new Intent(this, SelectAuditTypeActivity.class);
				startActivityForResult(sIntent, REQ_AUDIT_TYPE);
				break;
			case R.id.btnAuditDate:
				selectDateDialog();
				break;
			case R.id.btnAuditTime:
				selectTimeDialog();
				break;
			case R.id.btnView:
				viewAuditDetail();
				break;
			case R.id.btnSave:
				saveNewSheet();
				break;
			case R.id.btnCancel:
				finish();
				
		}
		
	}
	private void viewAuditDetail(){
		Intent intent = new Intent(this, ViewPagerActivity.class);
		Log.i(tag, "view audit detail.." + mSheet.getId());
		intent.putExtra(ViewPagerActivity.SHEET_ID, mSheet.getId());
		startActivity(intent);
	}
	private void saveNewSheet(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Confirm")
		.setMessage("Create new sheet?")
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				/* save new sheet*/
				Sheet sheet = new Sheet(mBpartner.getName(), mChecklist.getId(), mBpartner.getId(), mBpLoc.getId(), mAuditType.getId(), mStart);
				mSheetDao.save(sheet);
				mBpDao.save(mBpartner);
				mBpLocDao.save(mBpLoc);
				if(!sheet.isCreateSheetItem()){
					/*
					 * Create list of sheet item
					 */	
					mSheetItemDao.createSheetItemList(mChecklist.getId(), sheet.getId());		
					sheet.setCreateSheetItem(true);
					mSheetDao.save(sheet);
				}
				finish();
				
			}
		})
		.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		builder.create().show();
	}
	
	private void selectDateDialog(){
		
		DatePickerDialog dialog = new DatePickerDialog(this, this, mYear, mMonth, mDay);
		dialog.show();
	}
	private void selectTimeDialog(){
		
		MyTimePickerDialog dialog = new MyTimePickerDialog(this, this, mHour, mMinute, true);
		dialog.show();
	}
	
	
	
	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		mHour = hourOfDay;
		mMinute = minute;
		mCalendar.set(mYear, mMonth, mDay, mHour, mMinute, 0);
		setAuditDate(mCalendar.getTime());
	}


	
	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		mYear = year;
		mMonth = monthOfYear;
		mDay = dayOfMonth;
		mCalendar.set(mYear, mMonth, mDay, mHour, mMinute, 0);
		setAuditDate(mCalendar.getTime());
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == REQ_CHECKLIST && resultCode == RESULT_OK){
			setChecklist((Checklist) data.getParcelableExtra(SelectChecklistActivity.SELECTED_CHECKLIST));
			
		}else if(requestCode == REQ_BPARTNER && resultCode == RESULT_OK){
			setBpartner((Bpartner)data.getParcelableExtra(SearchBpActivity.SELECTED_BPARTNER));
			
		}else if(requestCode == REQ_BP_LOCATION && resultCode == RESULT_OK){
			setBpLocation((BpLocation)data.getParcelableExtra(SelectBpLocActivity.SELECTED_BP_LOC));
			
		}else if(requestCode == REQ_AUDIT_TYPE && resultCode == RESULT_OK){
			setAuditType((AuditType)data.getParcelableExtra(SelectAuditTypeActivity.SELECTED_AUDIT_TYPE));
		}
	}
	
	private void toggleSaveButton(){
		if(mChecklist != null && mBpartner != null && mBpLoc != null && mAuditType != null && mStart != null){
			btnSave.setEnabled(true);
		}else{
			btnSave.setEnabled(false);
		}
	}
	
	public void refreshBpLocView() {
		txtBPLocation.setText(mBpLoc.getName());
		
		StringBuilder s = new StringBuilder();
		if(mBpLoc.getAddress1() != null){
			s.append(mBpLoc.getAddress1() + " ");
		}
		if(mBpLoc.getAddress2() != null && !mBpLoc.getAddress2().equals("null")){
			s.append(mBpLoc.getAddress2() + " ");
		}
		if(mBpLoc.getAddress3() != null && !mBpLoc.getAddress3().equals("null")){
			s.append(mBpLoc.getAddress3() + " ");
		}
		if(mBpLoc.getAddress4() != null && !mBpLoc.getAddress4().equals("null")){
			s.append(mBpLoc.getAddress4() + " ");
		}
		if(mBpLoc.getCity() != null && !mBpLoc.getCity().equals("null")){
			s.append(mBpLoc.getCity() + " ");
		}
		if(mBpLoc.getPostal() != null){
			s.append(mBpLoc.getPostal() + " ");
		}
		txtLocationDetail.setText(s.toString());
	}

	public void setChecklist(Checklist checklist){
		this.mChecklist = checklist;
		if(mChecklist != null){
			txtChecklist.setText(mChecklist.getName());
			toggleSaveButton();
		}
	}
	public void setBpartner(Bpartner bpartner){
		this.mBpartner = bpartner;
		if(mBpartner != null){
			txtBPartner.setText(mBpartner.getName());
			toggleSaveButton();
		}
	}
	public void setBpLocation(BpLocation bpLoc) {
		this.mBpLoc = bpLoc;
		if(mBpLoc != null){
			refreshBpLocView();
			toggleSaveButton();
		}
	}
	public void setAuditType(AuditType auditType) {
		this.mAuditType = auditType;
		if(mAuditType != null){
			txtAuditType.setText(mAuditType.getName());
			toggleSaveButton();
		}
	}
	public void setAuditDate(Date auditDate) {
		this.mStart = auditDate;
		mCalendar.setTime(mStart);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		txtAuditDate.setText(sdf.format(mStart));
		sdf.applyPattern("HH:mm");
		txtAuditTime.setText(sdf.format(mStart));
		toggleSaveButton();
	}
}
