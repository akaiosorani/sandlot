package jp.akaiosorani.android.sandlot;

import android.net.Uri;

public class NdlOpenSearch {

	private static String NDL_SEARCH_OPENSEARCH_URI = "http://iss.ndl.go.jp/api/opensearch";

	private static String URI_FORMAT = "%s?cnt=%d";
	
	private int index = 1;
	
	private int count;
	
	private String title = null;
	
	private String any = null;
	
	private String author = null;
	
	private String provider = null;
	
	public NdlOpenSearch(int count) {
		this.count = count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}

	public Uri build() {
		String baseString = String.format(URI_FORMAT, NDL_SEARCH_OPENSEARCH_URI, count);
		StringBuilder builder = new StringBuilder(baseString);
		if (index > 1) {
			builder.append("&idx=").append(index);
		}
		if (title != null && !title.equals("")) {
			builder.append("&title=").append(Uri.encode(title));
		}
		if (any != null && !any.equals("")) {
			builder.append("&any=").append(Uri.encode(any));
		}
		if (author != null && !author.equals("")) {
			builder.append("&creator=").append(Uri.encode(author));
		}
		
		
		Uri query = Uri.parse(builder.toString());
		return query;
	}
	
	public String getQueryString() {
		return build().toString();
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setAny(String any) {
	    this.any = any;
    }

	public void setAuthor(String author) {
		this.author = author;
	}
	
	public void setProvider(String provider) {
		this.provider = provider;
	}
	
}
