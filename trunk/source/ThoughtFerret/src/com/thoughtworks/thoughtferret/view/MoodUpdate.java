package com.thoughtworks.thoughtferret.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.thoughtworks.thoughtferret.R;

public class MoodUpdate extends Activity implements OnClickListener {
	
	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;

	private Button speakButton;
    private EditText keywords;
    //private WrappingViewGroup keywordsGroup;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moodupdate);
        speakButton = (Button) findViewById(R.id.speakButton);
        keywords = (EditText) findViewById(R.id.keywords);
        //keywordsGroup = (WrappingViewGroup) findViewById(R.id.keywordsGroup);
        
//        WordView wordView = new WordView(this, null);
//        ViewGroup container = (ViewGroup) findViewById(R.id.tempGroup);
//        container.addView(wordView);    
        
        
//        for (int i = 0; i < 10; i++) {
//            TextView t = new TextView(this);
//            t.setText(Long.toHexString(Double.doubleToLongBits(Math.random())));
//            t.setBackgroundColor(Color.LTGRAY);
//            t.setSingleLine(true);
//            keywordsGroup.addView(t, new WrappingViewGroup.LayoutParams(2, 0));
//        }
        
     // Check to see if a recognition activity is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() != 0) {
            speakButton.setOnClickListener(this);
        } else {
            speakButton.setEnabled(false);
            speakButton.setText("Recognizer not present");
        }
    }
   
    
    /**
     * Handle the click on the start recognition button.
     */
    public void onClick(View v) {
        if (v.getId() == R.id.speakButton) {
            startVoiceRecognitionActivity();
        }
    }

    /**
     * Fire an intent to start the speech recognition activity.
     */
    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech recognition demo");
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

    /**
     * Handle the results from the recognition activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it could have heard
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            keywords.setText(matches.get(0));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    
}