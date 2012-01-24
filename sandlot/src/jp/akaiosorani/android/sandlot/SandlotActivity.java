package jp.akaiosorani.android.sandlot;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SandlotActivity extends Activity {
	
	private int conditionCount = 1;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ResultItem.Init(this);
        // init preferences
        PreferenceManager.setDefaultValues(this, R.xml.setting, false);
        
        ((Button) findViewById(R.id.clearButton)).setOnClickListener(clearListener);
        ((Button) findViewById(R.id.searchButton)).setOnClickListener(searchListener);
        conditionCount = 1;
        clear();
    }
    
    OnClickListener clearListener = new OnClickListener() {
        public void onClick(View v) {
        	clear();
        }
    };
    
    OnClickListener searchListener = new OnClickListener() {
    	public void onClick(View v) {
    		search();
    	}
    };
    
    private void addCondition() {
    	LinearLayout layout = (LinearLayout)findViewById(R.id.conditionRoot);
    	layout.addView(new NdlSearchConditionView(this));
    	conditionCount++;
    }
   
    private void removeCondition() {
    	if (conditionCount<=1) {
    		return;
    	}
    	LinearLayout layout = (LinearLayout)findViewById(R.id.conditionRoot);
    	layout.removeViewAt(layout.getChildCount()-1);
    	conditionCount--;
    }
        
    private NdlSearchConditionView getConditionView(int pos) {    	
    	return (NdlSearchConditionView)findViewById(R.id.condition);
    }

    private int getType() {
    	return getConditionView(0).getType();
    }
    private void setType(int type) {
    	getConditionView(0).setType(type);
    }
    private String getWord() {
    	return getConditionView(0).getValue();
    }
    private void setWord(String value) {
    	getConditionView(0).setValue(value);
    }
    
    private void search() {
    	NdlOpenSearch searcher = new NdlOpenSearch(PreferencesSettings.getCount(this));
    	int type = getType();
    	switch(type) {
    	case 0: 
        	searcher.setTitle(getWord());
    		break;
    	case 1: 
        	searcher.setAuthor(getWord());
    		break;
    	case 2: 
        	searcher.setAny(getWord());
    		break;
    	}

    	String queryString = searcher.getQueryString();
    	Toast.makeText(this, queryString, Toast.LENGTH_LONG).show();

    	Uri query = searcher.build();
    	FeedLoadingTask task = new FeedLoadingTask(this);
    	task.execute(query);    	
    }
    
    private void clear() {
    	getConditionView(0).clear();
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        
        outState.putInt("count", conditionCount);
        for (int i=0;i<conditionCount;i++) {
        	outState.putInt("type" + Integer.toString(i), getType());
        	outState.putString("value" + Integer.toString(i), getWord());
        }
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        conditionCount = savedInstanceState.getInt("count");
        for (int i=0;i<conditionCount;i++) {
        	int type = savedInstanceState.getInt("type" + Integer.toString(i));
        	String value = savedInstanceState.getString("value" + Integer.toString(i));
        	setType(type);
        	setWord(value);
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add("preferences");
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if (item.getTitle() == "preferences") {
    		Intent intent = new Intent(this, PreferencesSettings.class);
    		startActivity(intent);
    		return true;
    	}
    	
        return super.onOptionsItemSelected(item);
    }
    
    
    
}