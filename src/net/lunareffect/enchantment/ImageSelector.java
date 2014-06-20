package net.lunareffect.enchantment;

import net.lunareffect.enchantment.Book.Page;
import net.lunareffect.enchantment.ImageMatcher.FeatureData;
import net.lunareffect.enchantment.ImageMatcher.MatchData;

import org.opencv.core.Mat;

public class ImageSelector {
	public static class Match{
		private Page page;
		private MatchData data;
		
		public Match(Page page, MatchData data) {
			this.page = page;
			this.data = data;
		}
		public Page getPage() {
			return page;
		}
		public void setPage(Page page) {
			this.page = page;
		}
		public MatchData getData() {
			return data;
		}
		public void setData(MatchData data) {
			this.data = data;
		}
	}
	
	private Book book;
	
	public ImageSelector(Book book){
		this.book = book;
	}
	
	public Match getBestMatch(Mat mat){
		FeatureData features = ImageMatcher.detectFeatures(mat);
		Match bestMatch = null;
		for(Page page : book.getPages()){
			MatchData matchData = ImageMatcher.findMatches(features, page.getFeatures());
			if(bestMatch == null){
				bestMatch = new Match(page, matchData);
			}else{
				if(matchData.compareTo(bestMatch.getData())>0){
					bestMatch = new Match(page, matchData);
				}
			}
		}
		return bestMatch;
	}
}
