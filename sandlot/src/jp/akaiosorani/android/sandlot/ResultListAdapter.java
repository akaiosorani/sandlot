package jp.akaiosorani.android.sandlot;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ResultListAdapter extends ArrayAdapter<ResultItem> {

	private List<ResultItem> items;
	
	ResultListAdapter(Context context, List<ResultItem> items) {
		super(context, android.R.layout.simple_list_item_1, items);
		this.items = items;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Context context = getContext();
		ResultItem item = items.get(position);
		if(convertView == null) {
			TextView text = new TextView(context);
			convertView = text;
			
		}

		TextView view = (TextView)convertView;
		view.setText(item.getTitle());
		
		return convertView;
	}
	
}
