package org.hsc.silk;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;


public class MyTimePickerDialog extends TimePickerDialog {

	public MyTimePickerDialog(Context arg0, OnTimeSetListener callBack, int hourOfDay, int minute, boolean is24HourView) {
		  super(arg0, callBack, hourOfDay, minute, is24HourView);
		  // TODO Auto-generated constructor stub
		 }

		 @Override
		 public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		  // TODO Auto-generated method stub
		  //super.onTimeChanged(arg0, arg1, arg2);
		  if (mIgnoreEvent)
		            return;
		        if (minute%TIME_PICKER_INTERVAL!=0){
		            int minuteFloor=minute-(minute%TIME_PICKER_INTERVAL);
		            minute=minuteFloor + (minute==minuteFloor+1 ? TIME_PICKER_INTERVAL : 0);
		            if (minute==60)
		                minute=0;
		            mIgnoreEvent=true;
		            view.setCurrentMinute(minute);
		            mIgnoreEvent=false;
		        }
		 }

		 private final int TIME_PICKER_INTERVAL=15;
		 private boolean mIgnoreEvent=false;

	
	

}
