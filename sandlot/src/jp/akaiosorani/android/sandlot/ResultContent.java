package jp.akaiosorani.android.sandlot;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.http.util.ByteArrayBuffer;

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

public class ResultContent {

	private static final HttpTransport transport = AndroidHttp.newCompatibleTransport();
	
	private static final Map<String, ResultContent> results = new HashMap<String, ResultContent>();

	private String original = null;
	private byte[] originalBytes = null;
    private SyndFeed feed = null;

    private String uri;
    
    public static ResultContent getResult(String uri) {
    	return results.get(uri);
    }
    
    public ResultContent(String uri) {
    	this.uri = uri;
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
    	for (int i=0;i<list.size();i++) {
    		SyndEntry e = list.get(i);
    		ResultItem item = new ResultItem(e, uri, i);
    		items.add(item);
    	}
    	return items;
    }

    public ResultItem getItem(int position) {
    	List<SyndEntry> list = feed.getEntries();
    	SyndEntry entry = list.get(position);
    	return new ResultItem(entry, uri, position);
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
        	InputStream stream = request.execute().getContent();
        	if (stream != null) {
        		ByteArrayBuffer buffer = new ByteArrayBuffer(0);
        		int v = stream.read();
        		while(v != -1) {
        			buffer.append(v);
        			v = stream.read();
        		}
        		originalBytes = new byte[buffer.length()];
        		System.arraycopy(buffer.buffer(), 0, originalBytes, 0, buffer.length());
    	    	original = new String(originalBytes, "utf-8");
        	}
        } catch (IOException e) {
	        e.printStackTrace();
        } catch (IllegalArgumentException e) {
	        e.printStackTrace();
        }
	}
	
	public void parse() {
        try {
        	Reader reader = new InputStreamReader(new ByteArrayInputStream(originalBytes));
            SyndFeedInput input = new SyndFeedInput();
	        feed = input.build(reader);
	        reader.close();
	        results.put(uri.toString(), this);
        } catch (IllegalArgumentException e) {
	        e.printStackTrace();
        } catch (FeedException e) {
	        e.printStackTrace();
        } catch (IOException e) {
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
	        e.printStackTrace();
        } catch (IOException e) {
	        e.printStackTrace();
        }
    }
}
