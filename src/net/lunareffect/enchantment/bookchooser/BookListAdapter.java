package net.lunareffect.enchantment.bookchooser;

import java.util.ArrayList;

import net.lunareffect.enchantment.Enchantment;
import net.lunareffect.enchantment.LoadingScreen;
import net.lunareffect.enchantment.R;
import net.lunareffect.enchantment.R.id;
import net.lunareffect.enchantment.R.layout;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class BookListAdapter extends BaseAdapter {
	private ArrayList<BookMeta> list;
	private LayoutInflater inflater;
	private Activity context;

	public BookListAdapter(Activity context, ArrayList<BookMeta> list) {
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final BookMeta meta = this.list.get(position);
		View listItem = inflater.inflate(R.layout.book_list_item, null);
		TextView author = (TextView) listItem.findViewById(R.id.book_author);
		TextView title = (TextView) listItem.findViewById(R.id.book_title);
		author.setText(meta.getAuthor());
		title.setText(meta.getTitle());
		listItem.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				((Enchantment)context).setChosenBook(meta);
				Fragment newFragment = new LoadingScreen();
				FragmentTransaction transaction = context.getFragmentManager().beginTransaction();
				transaction.replace(R.id.main_content, newFragment);
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});
		return listItem;
	}

}
