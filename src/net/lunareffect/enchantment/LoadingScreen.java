package net.lunareffect.enchantment;

import net.lunareffect.enchantment.bookchooser.BookMeta;
import net.lunareffect.enchantment.cameraview.CameraView;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadingScreen extends Fragment{
	private ProgressBar progressBar;
	private TextView progressText;
    public TextView getProgressText() {
		return progressText;
	}

	public void setProgressText(TextView progressText) {
		this.progressText = progressText;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.loading_screen_fragment, container, false);
    }
    
	@Override
	public void onStart() {
		super.onStart();
		BookMeta meta = ((Enchantment)this.getActivity()).getChosenBook();
		TextView loadingText = (TextView) this.getView().findViewById(R.id.loading_text);
		loadingText.setText(meta.getTitle());
		progressText = (TextView) this.getView().findViewById(R.id.load_progress);
		progressText.setText("");
		this.setProgressBar((ProgressBar) this.getView().findViewById(R.id.loading_bar));
		new AsyncBookLoader(this).execute(meta.getFilename());
	}
	
	public void onLoad(Book book){
		((Enchantment)this.getActivity()).setBook(book);
		Fragment newFragment = new CameraView();
		FragmentTransaction transaction = this.getActivity().getFragmentManager().beginTransaction();
		transaction.replace(R.id.main_content, newFragment);
		transaction.commit();
	}

	public ProgressBar getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}
}
