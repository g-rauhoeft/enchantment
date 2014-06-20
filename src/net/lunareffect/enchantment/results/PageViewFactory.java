package net.lunareffect.enchantment.results;

import java.util.HashMap;

import net.lunareffect.enchantment.Util;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PageViewFactory {
	private static HashMap<String, PageViewProducer> producers = null;
	
	public static interface PageViewProducer{
		public View getView(Context context, JSONObject data);
	}
	
	public static void init(){
		if(producers == null){
			producers = new HashMap<String, PageViewProducer>();
			producers.put("text", new PageViewProducer(){
				@Override
				public View getView(Context context, JSONObject data) {
					float size = 12;
					String text = "";
					int color = Color.BLACK;
					int background = Color.WHITE;
					try {
						size = (float) data.getDouble("font_size");
						text = data.getString("text");
						color = Color.parseColor(data.getString("font_color"));
						background = Color.parseColor(data.getString("background_color"));
					} catch (JSONException e) {
						e.printStackTrace();
					}
					TextView textView = new TextView(context);
					textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
					textView.setTextColor(color);
					textView.setText(text);
					textView.setBackgroundColor(background);
					textView.setPadding(8, 8, 8, 8);
					return textView;
				}
			});
			producers.put("image", new PageViewProducer(){
				@Override
				public View getView(Context context, JSONObject data) {
					String path = Util.baseDirectory+"";
					try{
						path += data.getString("path");
					}catch(JSONException e){
						e.printStackTrace();
						return null;
					}
					ImageView imageView = new ImageView(context);
					BitmapDrawable drawable = new BitmapDrawable(context.getResources(), path);
					imageView.setImageBitmap(drawable.getBitmap());
		            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
					return imageView;
				}
			});
		}
	}
	
	public static View getView(Context context, JSONObject data){
		init();
		String type = "";
		try {
			type = data.getString("type");
			PageViewProducer producer = producers.get(type);
			if(producer != null){
				return producer.getView(context, data);
			}
		} catch (JSONException e) {
			return null;
		}
		return null;
	}
}
