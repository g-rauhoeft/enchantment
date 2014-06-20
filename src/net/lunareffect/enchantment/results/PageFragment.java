package net.lunareffect.enchantment.results;

import net.lunareffect.enchantment.Book;
import net.lunareffect.enchantment.Enchantment;
import net.lunareffect.enchantment.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class PageFragment extends Fragment{
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.page_view, container, false);
    }
	
	@Override 
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//this.labelText = this.getArguments().getString("Label");
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Book book = ((Enchantment)this.getActivity()).getBook();
		int index = this.getArguments().getInt("index");
		GridView gridView = (GridView) this.getView().findViewById(R.id.page_grid);
		PageAdapter adapter = new PageAdapter(getActivity(), book.getPages().get(index).getViewData());
		gridView.setAdapter(adapter);
	}
}
