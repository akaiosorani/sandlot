package jp.akaiosorani.android.sandlot;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class ContentActivity extends Activity {

	TextView view;
	WebView wView;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);
        
//        view = (TextView)findViewById(R.id.textView);
        wView = (WebView)findViewById(R.id.textView);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String uriString = bundle.getString("uri");
            Toast.makeText(this, uriString, Toast.LENGTH_LONG).show();
            ResultContent content = ResultContent.getResult(uriString);
//          view.setText(content.getContent());
//          view.setText(content.getOriginal());
          	wView.loadDataWithBaseURL("about:blank", content.getOriginal(), "text/html", "utf-8", null);
//            wView.loadDataWithBaseURL("about:blank", "<html><title></title><body>あいうえお</body></html>", "text/html", "utf-8", null);
        }
    }

}
