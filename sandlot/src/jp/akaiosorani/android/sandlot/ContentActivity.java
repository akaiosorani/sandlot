package jp.akaiosorani.android.sandlot;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

public class ContentActivity extends Activity {
	private WebView wView;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);
        
        wView = (WebView)findViewById(R.id.textView);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String uriString = bundle.getString("uri");
            int position = bundle.getInt("position");
            Toast.makeText(this, uriString, Toast.LENGTH_LONG).show();

            ResultContent content = ResultContent.getResult(uriString);
            ResultItem item = content.getItem(position);
          	wView.loadDataWithBaseURL("about:blank", item.getContent(), "text/html", "utf-8", null);
        }
    }

}
