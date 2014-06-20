package net.lunareffect.enchantment;

import org.opencv.core.Mat;

import net.lunareffect.enchantment.ImageSelector.Match;
import android.os.AsyncTask;

public class AsyncMatcher extends AsyncTask<Mat, Void, Match>{
	private ImageSelector selector;
	
	public static interface MatchCallback{
		public void onMatchFound(Match match);
	}
	
	private MatchCallback callback;
	
	public AsyncMatcher(Book book, MatchCallback callback){
		this.selector = new ImageSelector(book);
		this.callback = callback;
	}
	
	@Override
	protected Match doInBackground(Mat... mats) {
		return this.selector.getBestMatch(mats[0]);
	}
	
	@Override
	protected void onPostExecute(Match result){
		this.callback.onMatchFound(result);
	}
}
