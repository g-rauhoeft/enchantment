package net.lunareffect.enchantment.cameraview;

import net.lunareffect.enchantment.AsyncMatcher;
import net.lunareffect.enchantment.AsyncMatcher.MatchCallback;
import net.lunareffect.enchantment.Book;
import net.lunareffect.enchantment.Enchantment;
import net.lunareffect.enchantment.ImageSelector.Match;
import net.lunareffect.enchantment.results.PageFragment;
import net.lunareffect.enchantment.LargeTextFragment;
import net.lunareffect.enchantment.R;

import org.json.JSONException;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CameraView extends Fragment implements CvCameraViewListener2{
    private Button cameraButton;
	private CameraBridgeViewBase cameraView;
	private Mat lastFrame;
	private boolean calculatingMatch;

	public AsyncMatcher getMatcher(){
		Book book = ((Enchantment)this.getActivity()).getBook();
		return new AsyncMatcher(book, new MatchCallback(){
			@Override
			public void onMatchFound(Match match) {
				calculatingMatch = false;
				Fragment newFragment = new PageFragment();
				Bundle arguments = new Bundle();
				arguments.putInt("index", match.getPage().getIndex());
				newFragment.setArguments(arguments);
				FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
				transaction.replace(R.id.main_content, newFragment);
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.video_view_fragment, container, false);
    }
	
	@Override
	public void onStart() {
		super.onStart();
		cameraButton = (Button) this.getView().findViewById(R.id.camera_button);
		cameraButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				getMatcher().execute(lastFrame);
				calculatingMatch = true;
			}
		});
		cameraView = (CameraBridgeViewBase) this.getActivity().findViewById(R.id.javaCameraView);
		cameraView.setVisibility(SurfaceView.VISIBLE);
		cameraView.setCvCameraViewListener(this);
		cameraView.enableView();
	}

	@Override
	public void onCameraViewStarted(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCameraViewStopped() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
		if(calculatingMatch){
			return this.lastFrame;
		}else{
			this.lastFrame = inputFrame.gray().clone();
			return inputFrame.rgba();
		}
	}
}
