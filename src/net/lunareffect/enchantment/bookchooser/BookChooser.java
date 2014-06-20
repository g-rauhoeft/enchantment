package net.lunareffect.enchantment.bookchooser;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import net.lunareffect.enchantment.R;
import net.lunareffect.enchantment.Util;
import net.lunareffect.enchantment.R.id;
import net.lunareffect.enchantment.R.layout;

import org.json.JSONException;

import android.app.Activity;
import android.app.Fragment;
import android.app.LauncherActivity.ListItem;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TwoLineListItem;

public class BookChooser extends Fragment {
	
	public ArrayList<BookMeta> loadBooks(){
		String directory = Util.baseDirectory;
		File file = new File(directory);
		ArrayList<BookMeta> books = new ArrayList<BookMeta>();
		if(file.isDirectory()){
			for(String filename : file.list(new FilenameFilter(){
				@Override
				public boolean accept(File dir, String filename) {
					return filename.endsWith(".json");
				}
			})){
				try {
					books.add(BookMeta.load(filename));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return books;
	}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.book_chooser_fragment, container, false);
    }
    
	@Override
	public void onStart() {
		super.onStart();
		ArrayList<BookMeta> metaData = loadBooks();
		ListView listView = (ListView) this.getView().findViewById(R.id.book_list);
		listView.setAdapter(new BookListAdapter(this.getActivity(), metaData));
	}
}
