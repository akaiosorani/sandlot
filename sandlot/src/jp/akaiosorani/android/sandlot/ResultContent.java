package jp.akaiosorani.android.sandlot;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringBufferInputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
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

	private String original = null;
	private byte[] originalBytes = null;
    private SyndFeed feed = null;

    public static ResultContent getResult(String uri) {
    	return results.get(uri);
    }
    
    public String getOriginal() {
    	return original;
    }
    public String getUri() {
    	return feed.getUri();
    }

    public String getTitle() {
    	return feed.getTitle();
    }
    public String getTitle(int index) {
    	List<SyndEntry> list = feed.getEntries();
    	return list.get(index).getTitle();
    }
    
    public int Count() {
    	return feed.getEntries().size();
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
    
    public List<ResultItem> getItems() {
    	List<ResultItem> items = new ArrayList<ResultItem>();
    	List<SyndEntry> list = feed.getEntries();
    	ListIterator<SyndEntry> it = list.listIterator();
    	for(;it.hasNext();) {
    		SyndEntry e = it.next();
    		ResultItem item = new ResultItem(e);
    		items.add(item);
    	}
    	return items;
    }
    
	public void load(Uri uri) {
        GenericUrl url = new GenericUrl(uri.toString());
        HttpRequestFactory requestFactory = transport.createRequestFactory(new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest request) {
            	
            }
        });
        try {
        	HttpRequest request = requestFactory.buildGetRequest(url);
        	original = request.execute().parseAsString();
        	parse();
/*
        	Reader reader = new InputStreamReader(new StringBufferInputStream(original));
	        SyndFeedInput input = new SyndFeedInput();
	        feed = input.build(reader);
	        reader.close();
*/
	        results.put(uri.toString(), this);
        } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        } catch (IllegalArgumentException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
/*
        } catch (FeedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
*/
        }
	}
	
	private void parse() {
        try {
        	Reader reader = new InputStreamReader(new ByteArrayInputStream(originalBytes));
            SyndFeedInput input = new SyndFeedInput();
	        feed = input.build(reader);
	        reader.close();
        } catch (IllegalArgumentException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        } catch (FeedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	}

	public void loadTest(Uri uri, String filename) {
    	File f = new File(filename);
    	byte[] buffer = new byte[(int)f.length()];
    	InputStream stream;
        try {
	        stream = new FileInputStream(f);
	        stream.read(buffer);
	    	originalBytes = buffer;
	    	original = new String(buffer, "utf-8");
	    	stream.close();
        } catch (FileNotFoundException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
    	parse();
        results.put(uri.toString(), this);
    }
}
