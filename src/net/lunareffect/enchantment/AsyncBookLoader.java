package net.lunareffect.enchantment;

import org.json.JSONException;

import android.os.AsyncTask;

public class AsyncBookLoader extends AsyncTask<String, Integer, Book> implements Book.BookLoaderCallback{
	private LoadingScreen screen;

	public AsyncBookLoader(LoadingScreen screen){
		this.screen = screen;
	}
	
    protected void onProgressUpdate(Integer... progress) {
    	screen.getProgressText().setText(progress[0]+"/"+progress[1]);
    }

	@Override
	protected Book doInBackground(String... params) {
		try {
			return Book.load(Util.baseDirectory+params[0], this);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void onProgressChanged(int current, int max) {
		this.publishProgress(current, max);
	}
	
	@Override
    protected void onPostExecute(Book result) {
		screen.onLoad(result);
    }
}
