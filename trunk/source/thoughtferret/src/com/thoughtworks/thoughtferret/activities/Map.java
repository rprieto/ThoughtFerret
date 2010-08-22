package com.thoughtworks.thoughtferret.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;

import com.thoughtworks.thoughtferret.R;

public class Map extends Activity {

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        
//        TelephonyManager tm  = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE); 
//        GsmCellLocation location = (GsmCellLocation) tm.getCellLocation();
//        int cellId = location.getCid();
//        location.getLac();
	}
	

}
