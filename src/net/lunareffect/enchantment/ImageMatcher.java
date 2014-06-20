package net.lunareffect.enchantment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.KeyPoint;
import org.opencv.imgproc.Imgproc;

import android.util.Log;

public class ImageMatcher {

	public static class FeatureData {
		public MatOfKeyPoint keypoints;
		public Mat descriptors;

		public FeatureData(MatOfKeyPoint keypoints, Mat descriptors) {
			this.keypoints = keypoints;
			this.descriptors = descriptors;
		}

		public FeatureData(String kpFn, String dscFn){
			try{
				String kpContents = Util.readFile(kpFn);
				this.keypoints = Util.jsonToMatOfKeyPoint(kpContents);
				String dscContents = Util.readFile(dscFn);
				this.descriptors = Util.jsonToMat(dscContents);
			}catch(JSONException e){
				Log.e("JSONException", "Exception occured reading JSON Files.", e);
			}
		}
	}

	public static class MatchData implements Comparable<MatchData> {
		public ArrayList<KeyPoint> objectPoints, imagePoints;
		public float ratio;

		public MatchData(ArrayList<KeyPoint> objectPoints,
				ArrayList<KeyPoint> imagePoints, float ratio) {
			this.objectPoints = objectPoints;
			this.imagePoints = imagePoints;
			this.ratio = ratio;
		}

		@Override
		public int compareTo(MatchData another) {
			if (this.ratio < another.ratio) {
				return -1;
			}
			if (this.ratio > another.ratio) {
				return 1;
			}
			return 0;
		}

	}

	public static FeatureData detectFeatures(Mat mat) {
		FeatureDetector detector = FeatureDetector.create(FeatureDetector.ORB);
		DescriptorExtractor extractor = DescriptorExtractor
				.create(DescriptorExtractor.ORB);
		MatOfKeyPoint keypoints = new MatOfKeyPoint();
		Mat descriptors = new Mat();
		detector.detect(mat, keypoints);
		extractor.compute(mat, keypoints, descriptors);
		return new FeatureData(keypoints, descriptors);
	}
	public static MatchData findMatches(FeatureData referenceData,
			FeatureData actualData) {
		DescriptorMatcher matcher = DescriptorMatcher
				.create(DescriptorMatcher.BRUTEFORCE);
		List<MatOfDMatch> matches = new ArrayList<MatOfDMatch>();
		matcher.knnMatch(referenceData.descriptors, actualData.descriptors,
				matches, 2);
		ArrayList<KeyPoint> objectPoints = new ArrayList<KeyPoint>(), imagePoints = new ArrayList<KeyPoint>();
		for (MatOfDMatch match : matches) {
			DMatch[] dmatches = match.toArray();
			if (dmatches.length == 2
					&& dmatches[0].distance < dmatches[1].distance * 0.75) {
				imagePoints
						.add(referenceData.keypoints.toArray()[dmatches[0].queryIdx]);
				objectPoints
						.add(actualData.keypoints.toArray()[dmatches[0].trainIdx]);
			}
		}
		float ratio = ((float) objectPoints.size())
				/ ((float) actualData.keypoints.size().width);
		return new MatchData(objectPoints, imagePoints, ratio);
	}

	public static Mat convertToGray(Mat mat) {
		Mat gray = new Mat();
		Imgproc.cvtColor(mat, gray, Imgproc.COLOR_BGR2GRAY);
		return gray;
	}
}