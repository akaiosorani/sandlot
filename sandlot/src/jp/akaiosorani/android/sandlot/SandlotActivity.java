package jp.akaiosorani.android.sandlot;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class SandlotActivity extends Activity {
	
	private int conditionCount = 1;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
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
    
    private String getWord() {
    	return getConditionView(0).getValue();
    }
    
    private void search() {
    	NdlOpenSearch searcher = new NdlOpenSearch();
    	searcher.setTitle(getWord());

    	String queryString = searcher.getQueryString();
    	Toast.makeText(this, queryString, Toast.LENGTH_LONG).show();

    	Uri query = searcher.build();
    	FeedLoadingTask task = new FeedLoadingTask(this);
    	task.execute(query);    	
    }
    
    private void clear() {
    	getConditionView(0).clear();
    }
    
}