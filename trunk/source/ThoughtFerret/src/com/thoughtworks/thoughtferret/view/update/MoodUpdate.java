package com.thoughtworks.thoughtferret.view.update;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.thoughtworks.thoughtferret.R;
import com.thoughtworks.thoughtferret.model.mood.MoodRating;
import com.thoughtworks.thoughtferret.model.mood.MoodRatingDao;
import com.thoughtworks.thoughtferret.model.tags.MoodTags;
import com.thoughtworks.thoughtferret.model.tags.MoodTagsDao;
import com.thoughtworks.thoughtferret.view.ApplicationBackground;

public class MoodUpdate extends Activity {
	
	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;

	private ImageButton speakButton;
    private KeywordsEditor keywordsEditor;
    private MoodRatingDao moodRatingDao;
    private MoodTagsDao moodTagsDao;
    private RatingBar moodRate;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moodupdate);
        speakButton = (ImageButton) findViewById(R.id.speakButton);
        keywordsEditor = (KeywordsEditor) findViewById(R.id.keywordsEditor);
        moodRate = (RatingBar) findViewById(R.id.moodRate);
        setupVoiceRecognition();
        
        moodRatingDao = new MoodRatingDao(this);
        moodTagsDao = new MoodTagsDao(this);
        
        ApplicationBackground appBackground = new ApplicationBackground(this, ApplicationBackground.GradientDirection.HORIZONTAL, true);
        BitmapDrawable drawable = new BitmapDrawable(appBackground.getBitmap());        

        LinearLayout homeBackground = (LinearLayout) findViewById(R.id.moodUpdateBackground);
        homeBackground.setBackgroundDrawable(drawable);
    }
    
	@Override
    public void onBackPressed() {
		this.finish();
		overridePendingTransition(0, 0);
		return;
    }
	
	private void setupVoiceRecognition() {
    	PackageManager pm = getPackageManager();
    	List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
    	if (activities.size() == 0) {
    		speakButton.setEnabled(false);
    	}    	
    }
   
    public void speakClick(View v) {
        startVoiceRecognitionActivity();
    }

    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech recognition demo");
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String[] words = matches.get(0).split("\\s+");
            keywordsEditor.addKeywords(Arrays.asList(words));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
 
    public void okClick(View view) {
    	int rating = (int)moodRate.getRating();
		MoodRating moodRating = new MoodRating(rating);
    	MoodTags moodTags = keywordsEditor.getMoodTags(rating);
    	moodRatingDao.persist(moodRating);
    	moodTagsDao.persist(moodTags);
    }
    
}
