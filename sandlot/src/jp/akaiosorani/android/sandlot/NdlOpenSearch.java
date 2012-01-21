package jp.akaiosorani.android.sandlot;

import android.net.Uri;

public class NdlOpenSearch {

	private static String NDL_SEARCH_OPENSEARCH_URI = "http://iss.ndl.go.jp/api/opensearch";
	
	private String title = null;
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Uri getQuery() {

		Uri query = Uri.parse(NDL_SEARCH_OPENSEARCH_URI + "?title=" + Uri.encode(title));
		return query;
	}
	
	public String getQueryString() {
		return getQuery().toString();
	}
}
