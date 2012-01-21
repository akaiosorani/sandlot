package jp.akaiosorani.android.sandlot;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

public class FeedLoadingTask extends AsyncTask<Uri, Integer, ResultContent> {

	Activity parent = null;
	
	private Uri uri;
	private ProgressDialog dialog;
	
	ResultContent content = null;
	
	FeedLoadingTask(Activity activity) {
		parent = activity;
	}
	
	@Override
    protected ResultContent doInBackground(Uri... params) {
		uri = params[0];
    	ResultContent content = new ResultContent();
    	content.parse(uri);
	    return content;
    }
	
	@Override
	protected void onPreExecute() {
        dialog = new ProgressDialog(parent);
        dialog.setMessage("読み込み中です、、、");
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.show();
	}


	@Override
	protected void onPostExecute(ResultContent result) {
		dialog.hide();
        Intent intent = new Intent(parent, ContentActivity.class);
    	intent.putExtra("uri", uri.toString());
    	parent.startActivityForResult(intent, 0);
	}

	
}
