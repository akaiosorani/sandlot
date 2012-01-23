package jp.akaiosorani.android.sandlot;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;

public class FeedLoadingTask extends AsyncTask<Uri, Integer, ResultContent> {

	private static final int PROGRESS_LOADING = 1;
	private static final int PROGRESS_ANALYZING = 2;
	
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
    	ResultContent content = new ResultContent(uri.toString());
    	content.load(uri);
//    	content.loadTest(uri, "/sdcard/murakami10.rss");
    	this.publishProgress(PROGRESS_ANALYZING);
    	content.parse();
	    return content;
    }
	
	@Override
	protected void onPreExecute() {
        dialog = new ProgressDialog(parent);
        dialog.setMessage(parent.getString(R.string.msg_loading));
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.show();
	}


	@Override
	protected void onPostExecute(ResultContent result) {
		dialog.hide();
        Intent intent = new Intent(parent, ResultListActivity.class);
    	intent.putExtra("uri", uri.toString());
    	parent.startActivityForResult(intent, 0);
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
	    super.onProgressUpdate(values);
	    switch (values[0]) {
	    case PROGRESS_LOADING:
		    dialog.setMessage(parent.getString(R.string.msg_loading));
	    	break;
	    case PROGRESS_ANALYZING:
		    dialog.setMessage(parent.getString(R.string.msg_analyzing));
	    	break;
	    }
	}
	
}
