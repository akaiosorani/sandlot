package jp.akaiosorani.android.sandlot;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;

public class ResultItem {

	private SyndEntry entry;
	public ResultItem(SyndEntry entry) {
		this.entry = entry;
	}
	
	public String getTitle() {
		return entry.getTitle();
	}
	
	public String getLink() {
		return entry.getLink();
	}
	
	public void goToLink(Context context) {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(entry.getLink()));
		context.startActivity(intent);
	}
}
