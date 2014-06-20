package net.lunareffect.enchantment.bookchooser;

import net.lunareffect.enchantment.Util;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class BookMeta {
	private String filename, author, title;

	public BookMeta(String filename, String author, String title) {
		super();
		this.filename = filename;
		this.author = author;
		this.title = title;
	}
	
	public static BookMeta load(String filename) throws JSONException{
		String path = Util.baseDirectory+filename;
		String contents = Util.readFile(path);
		JSONObject json = null;
		json = new JSONObject(new JSONTokener(contents));
		String author, title;
		author = json.getString("author");
		title = json.getString("title");
		return new BookMeta(filename, author, title);
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
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
}
