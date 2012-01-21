package jp.akaiosorani.android.sandlot;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;

public class ResultItem {

	private SyndEntry entry;
	private String uri;
	private int position;
	public ResultItem(SyndEntry entry, String uri, int position) {
		this.entry = entry;
		this.uri = uri;
		this.position = position;
	}
	
	public String getTitle() {
		return entry.getTitle();
	}
	
	public String getLink() {
		return entry.getLink();
	}
	
	private static final String CONTENT_TEMPLATE = "<html><head></head><body><h3>%s</h3><ul><li>%s</li><li>%s</li><li><a href=\"%s\">詳細を見る</a></li></ul></body></html>";
	
	public String getContent() {
		String format = CONTENT_TEMPLATE;
		return String.format(format, entry.getTitle(), entry.getAuthor(), entry.getPublishedDate(), entry.getLink()); 
	}
	
	public void goToLink(Context context) {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(entry.getLink()));
		context.startActivity(intent);
	}
	
	public void goToDetailContent(Context context) {
		Intent intent = new Intent(context, ContentActivity.class);
		intent.putExtra("uri", uri);
		intent.putExtra("position", this.position);
		context.startActivity(intent);
	}
}
