package jp.akaiosorani.android.sandlot;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ResultListActivity extends Activity {
	ListView resultListView;
	private ArrayAdapter<String> adapter;
	
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

            int count = content.Count();
            String[] items = new String[count];
            for(int i=0;i<count;i++) {
            	items[i] = content.getTitle(i);
            }
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
            
            resultListView.setAdapter(adapter);
        }
    }
	

}
