package com.example.antispam;
 
import java.util.List;
import java.util.Map;

import com.example.db.BlackListDA;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
 
public class WhiteListAdapter extends ArrayAdapter {
  private Context context;
	private List<Map> list;
 
	public WhiteListAdapter(Context context, int textViewResourceId, List<Map> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = objects;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.list_whitelist, null);
		}
		Map map = list.get(position);
		if (map != null) {
			TextView txtId = (TextView) v.findViewById(R.id.txtWhiteId);
			if (txtId != null) {
				txtId.setText(map.get(BlackListDA.KEY_ID).toString());
			}
			TextView txtValue = (TextView) v.findViewById(R.id.txtWhiteList);
			if (txtValue != null) {
				txtValue.setText(map.get(BlackListDA.KEY_NUM).toString());
			}
		}
		return v;
	}
 
}