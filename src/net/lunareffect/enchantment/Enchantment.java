package net.lunareffect.enchantment;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

import net.lunareffect.enchantment.bookchooser.BookChooser;
import net.lunareffect.enchantment.bookchooser.BookMeta;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class Enchantment extends Activity{	
	private BookMeta chosenBook;
	private Book book;
	
	private BaseLoaderCallback loaderCallback = new BaseLoaderCallback(this) {
		@Override
		public void onManagerConnected(int status) {
			switch (status) {
				case LoaderCallbackInterface.SUCCESS: {
					Fragment newFragment = new BookChooser();
					FragmentTransaction transaction = getFragmentManager().beginTransaction();
					transaction.replace(R.id.main_content, newFragment);
					transaction.addToBackStack(null);
					transaction.commit();
				} break;
				default: {
					super.onManagerConnected(status);
				} break;
			}
		}
	};
	
	@Override
	public void onResume() {
		super.onResume();
		OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this,
				loaderCallback);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enchantment);
	}

	public BookMeta getChosenBook() {
		return chosenBook;
	}

	public void setChosenBook(BookMeta chosenBook) {
		this.chosenBook = chosenBook;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
}
