package net.lunareffect.enchantment;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

public class ImageLoader{
	public static Mat loadImage(String fn){
		return Highgui.imread(fn,Highgui.IMREAD_GRAYSCALE);
	}
}