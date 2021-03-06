package jp.akaiosorani.android.sandlot;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class NdlSearchConditionView extends LinearLayout {

	Spinner typeSelector;
	EditText valueEdit;
	
	public NdlSearchConditionView(Context context) {
	    super(context);
		setLayout(context);
    }
	
	public NdlSearchConditionView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setLayout(context);
	}

	private void setLayout(Context context) {
		setOrientation(LinearLayout.VERTICAL);
		
	    TextView label1 = new TextView(context);
	    label1.setText(context.getString(R.string.type));
	    addView(label1);
	    
	    typeSelector = new Spinner(context);
	    addView(typeSelector);
	    
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    adapter.add("タイトル");
	    adapter.add("著者");
	    adapter.add("全ての項目");
	    typeSelector.setAdapter(adapter);
	    
	    TextView label2 = new TextView(context);
	    label2.setText(context.getString(R.string.value));
	    addView(label2);
	    
	    valueEdit = new EditText(context);
	    addView(valueEdit);
	}

	public void setType(int position) {
		typeSelector.setSelection(position);
	}
	
	public int getType() { 
		return typeSelector.getSelectedItemPosition();
	}
	
	public void setValue(String value) {
		valueEdit.setText(value);
	}

	public String getValue() {
		return valueEdit.getText().toString();
	}
	
	public void clear() {
		typeSelector.setSelected(false);
		valueEdit.setText("");
	}
	
}
