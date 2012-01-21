package jp.akaiosorani.android.sandlot;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.google.api.client.extensions.android2.AndroidHttp;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.FeedException;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.SyndFeedInput;

import android.net.Uri;

public class ResultContent implements Serializable {

	private static final HttpTransport transport = AndroidHttp.newCompatibleTransport();
	
	private static final Map<String, ResultContent> results = new HashMap<String, ResultContent>();

    private SyndFeed feed = null;

    public static ResultContent getResult(String uri) {
    	return results.get(uri);
    }
    public String getUri() {
    	return feed.getUri();
    }

    public String getTitle() {
    	return feed.getTitle();
    }
    
    public String getContent() {
    	StringBuilder content = new StringBuilder();
    	content.append(getTitle());
    	List<SyndEntry> list = feed.getEntries();
    	ListIterator<SyndEntry> it = list.listIterator();
    	for(;it.hasNext();) {
    		SyndEntry e = it.next();
    		content.append(e.getTitle());
    	}
    	return content.toString();
    }
    
	public void parse(Uri uri) {
        GenericUrl url = new GenericUrl(uri.toString());
        HttpRequestFactory requestFactory = transport.createRequestFactory(new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest request) {
            	
            }
        });
        try {
        	HttpRequest request = requestFactory.buildGetRequest(url);
	        Reader reader = new InputStreamReader(request.execute().getContent());
	        SyndFeedInput input = new SyndFeedInput();
	        feed = input.build(reader);

	        results.put(uri.toString(), this);
        } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        } catch (IllegalArgumentException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        } catch (FeedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	}
}
