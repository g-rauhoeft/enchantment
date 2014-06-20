package net.lunareffect.enchantment.results;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class PageAdapter extends BaseAdapter{

	private Activity context;
	private JSONArray information;

	public PageAdapter(Activity context, JSONArray information){
		this.context = context;
		this.information = information;
	}
	
	@Override
	public int getCount() {
		return information.length();
	}

	@Override
	public Object getItem(int position) {
		try {
			return information.getJSONObject(position);
		} catch (JSONException e) {
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		try {
			JSONObject data = this.information.getJSONObject(position);
			return PageViewFactory.getView(context.getApplicationContext(), data);
		} catch (JSONException e) {
			return null;
		}
	}

}
