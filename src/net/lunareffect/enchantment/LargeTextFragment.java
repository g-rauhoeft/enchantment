package net.lunareffect.enchantment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LargeTextFragment extends Fragment{
	private String labelText = "";
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.large_text_fragment, container, false);
    }
	
	@Override 
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.labelText = this.getArguments().getString("Label");
	}
	
	@Override
	public void onStart() {
		super.onStart();
		TextView loadingText = (TextView) this.getView().findViewById(R.id.large_text);
		loadingText.setText(labelText);
	}
}
