package jp.akaiosorani.android.sandlot;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class ResultListActivity extends Activity {
	ListView resultListView;
	List<ResultItem> items;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultlist);
        
        resultListView = (ListView)findViewById(R.id.resultListView);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String uriString = bundle.getString("uri");
            Toast.makeText(this, uriString, Toast.LENGTH_LONG).show();
            ResultContent content = ResultContent.getResult(uriString);

            items = content.getItems();
            ResultListAdapter adapter = new ResultListAdapter(this, items);
            resultListView.setAdapter(adapter);
        
            setClickEvent();
        }
    }
	
	private void setClickEvent() {
		resultListView.setOnItemClickListener(listener);
		resultListView.setOnItemLongClickListener(listener2);
	}
	
	private OnItemClickListener listener = new OnItemClickListener() {
		@Override
        public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
			goToDetailContent(position);
        }
	};
	
	private OnItemLongClickListener listener2 = new OnItemLongClickListener() {
		@Override
        public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id) {
			goToLink(position);
	        return false;
        }
	};

	private void goToDetailContent(int position) {
		ResultItem item = items.get(position);
		item.goToDetailContent(ResultListActivity.this);
	}
	
	private void goToLink(int position) {
		ResultItem item = items.get(position);
		item.goToLink(ResultListActivity.this);
	}

}
