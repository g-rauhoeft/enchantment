package net.lunareffect.enchantment;

import java.util.ArrayList;

import net.lunareffect.enchantment.ImageMatcher.FeatureData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Book {
	private String author;
	private String title;
	private Page cover;
	private ArrayList<Page> pages;
	private static BookLoaderCallback callback = null;
	
	public static interface BookLoaderCallback{
		public void onProgressChanged(int current, int max);
	}
	
	public Book(String author, String title, Page cover, ArrayList<Page> pages) {
		super();
		this.author = author;
		this.title = title;
		this.cover = cover;
		this.pages = pages;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Page getCover() {
		return cover;
	}

	public void setCover(Page cover) {
		this.cover = cover;
	}

	public ArrayList<Page> getPages() {
		return pages;
	}

	public void setPages(ArrayList<Page> pages) {
		this.pages = pages;
	}

	public static class Page {
		private String filename;
		private String label;
		private FeatureData features;
		private JSONArray viewData;
		private int index;

		public Page(String filename, String label, int index) {
			super();
			this.setFilename(filename);
			this.setLabel(label);
			this.setIndex(index);
		}
		
		public Page(String filename, String label, FeatureData features, int index) {
			this(filename, label, index);
			this.setFeatures(features);
		}
		
		public Page(String filename, String label, FeatureData features, JSONArray viewData, int index) {
			this(filename, label, features, index);
			this.setViewData(viewData);
		}

		public String getFilename() {
			return filename;
		}

		public void setFilename(String filename) {
			this.filename = filename;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public FeatureData getFeatures() {
			return features;
		}

		public void setFeatures(FeatureData features) {
			this.features = features;
		}

		public JSONArray getViewData() {
			return viewData;
		}

		public void setViewData(JSONArray viewData) {
			this.viewData = viewData;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	public static Book parse(String jsonString) throws JSONException {
		JSONObject object = new JSONObject(new JSONTokener(jsonString));
		String author = object.getString("author");
		String title = object.getString("title");
		JSONObject jsonCover = object.getJSONObject("cover");
		String coverFile = title+"/"+jsonCover.getString("file");
		String coverLabel = jsonCover.getString("label");
		FeatureData coverFeatures = new FeatureData(Util.baseDirectory+coverFile+"-kp.json",
				Util.baseDirectory+coverFile+"-dsc.json");
		Page cover = new Page(coverFile, coverLabel, coverFeatures, -1);
		JSONArray jsonPages = object.getJSONArray("pages");
		ArrayList<Page> pages = new ArrayList<Page>();
		for (int i = 0; i < jsonPages.length(); i++) {
			if(Book.callback != null){
				callback.onProgressChanged(i, jsonPages.length());
			}
			JSONObject jsonPage = jsonPages.getJSONObject(i);
			String file = title+"/"+jsonPage.getString("file");
			String label = jsonPage.getString("label");
			JSONArray viewData = jsonPage.getJSONArray("views");
			FeatureData features = new FeatureData(Util.baseDirectory+file+"-kp.json",
					Util.baseDirectory+file+"-dsc.json");
			pages.add(new Page(file, label, features, viewData, i));
		}
		Book book = new Book(author, title, cover, pages);
		return book;
	}

	public static Book load(String filename, BookLoaderCallback callback) throws JSONException{
		Book.callback = callback;
		return Book.load(filename);
	}
	
	public static Book load(String filename) throws JSONException {
		String contents = Util.readFile(filename);
		if (contents != null) {
			return Book.parse(contents);
		} else {
			return null;
		}
	}
}
