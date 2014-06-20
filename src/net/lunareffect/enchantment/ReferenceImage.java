package net.lunareffect.enchantment;

import net.lunareffect.enchantment.ImageMatcher.FeatureData;

public class ReferenceImage {
	private FeatureData featureData;
	private String filename;
	public ReferenceImage(FeatureData featureData, String filename) {
		super();
		this.featureData = featureData;
		this.filename = filename;
	}
	public FeatureData getFeatureData() {
		return featureData;
	}
	public void setFeatureData(FeatureData featureData) {
		this.featureData = featureData;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
}
