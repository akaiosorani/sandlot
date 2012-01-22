package jp.akaiosorani.android.sandlot;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndCategory;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;

public class ResultItem {

	private SyndEntry entry;
	private String uri;
	private int position;
	
	static public void Init (Context context) {
		if (CONTENT_TEMPLATE != null) {
			return;
		}
		StringBuilder builder = new StringBuilder();
		builder.append("<html><head></head><body><h3>%s</h3><ul>");
		builder.append(String.format("<li>%s:%%s</li>", context.getString(R.string.content_author)));
		builder.append(String.format("<li>%s:%%s</li>", context.getString(R.string.content_category)));
		builder.append(String.format("<li>%s:%%s</li>", context.getString(R.string.content_published)));
		builder.append(String.format("<li><a href=\"%%s\">%s</a></li>", context.getString(R.string.content_show_details)));
		builder.append("</ul></body></html>");
		CONTENT_TEMPLATE = builder.toString();
	}
	
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
	
	private static String CONTENT_TEMPLATE;
	
	public String getContent() {
		String format = CONTENT_TEMPLATE;
		String title = entry.getTitle();
		String author = entry.getAuthor();
		String category = "";
		List<SyndCategory> categories = entry.getCategories();
		if (categories.size() > 0) {
			StringBuilder builder = new StringBuilder(categories.get(0).getName());
			for(int i=1;i<categories.size();i++) {
				builder.append("/").append(categories.get(i).getName());
			}
			category = builder.toString();
		}
		
		Date publishedDate = entry.getPublishedDate();
		String published = (publishedDate == null) ? "" : String.format("%tF", publishedDate);
		String link = entry.getLink();
		return String.format(format, title, author, category, published, link); 
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
