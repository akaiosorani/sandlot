package jp.akaiosorani.android.sandlot;

import android.net.Uri;

public class NdlOpenSearch {

	private static String NDL_SEARCH_OPENSEARCH_URI = "http://iss.ndl.go.jp/api/opensearch";

	private static String URI_FORMAT = "%s?cnt=%d";
	
	private int count = 20;
	
	private String title = null;
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Uri build() {
		String baseString = String.format(URI_FORMAT, NDL_SEARCH_OPENSEARCH_URI, count);
		StringBuilder builder = new StringBuilder(baseString);
		builder.append("&title=").append(Uri.encode(title));
		Uri query = Uri.parse(builder.toString());
		return query;
	}
	
	public String getQueryString() {
		return build().toString();
	}
}
