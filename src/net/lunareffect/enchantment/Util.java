package net.lunareffect.enchantment;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.KeyPoint;

import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;

public class Util {
	public static final String baseDirectory = Environment.getExternalStorageDirectory()+"/Reflect/";
	public static Mat jsonToMat(String json) throws JSONException{
		JSONObject object = new JSONObject(new JSONTokener(json));
		int		rows = object.getInt("rows"),
				cols = object.getInt("cols"),
				type = object.getInt("type");
		String dataString = object.getString("data");
		
		byte[] data = Base64.decode(dataString, Base64.DEFAULT);
		Mat mat = new Mat(rows, cols, type);
		mat.put(0, 0, data);
		return mat;
	}

	public static String matToJson(Mat mat) throws JSONException {
		JSONObject object = new JSONObject();
		int		rows = mat.rows(),
				cols = mat.cols(),
				type = mat.type(),
				size = (int) mat.elemSize();
		byte[] data = new byte[cols*rows*size];
		mat.get(0, 0, data);
		String dataString = Base64.encodeToString(data, Base64.DEFAULT);
			object.put("rows", rows);
			object.put("cols", cols);
			object.put("type", type);
			object.put("data", dataString);
		String json = object.toString();
		return json;
	}

	public static String matOfKeyPointToJson(MatOfKeyPoint mat) throws JSONException{
		JSONObject object = new JSONObject();
		List<KeyPoint> keypoints = mat.toList();
		JSONArray kpJson = new JSONArray();
		for(KeyPoint point : keypoints){
			JSONObject kpObject = new JSONObject();
			kpObject.put("angle", point.angle);
			kpObject.put("class_id", point.class_id);
			kpObject.put("octave", point.octave);
			kpObject.put("response", point.response);
			kpObject.put("x", point.pt.x);
			kpObject.put("y", point.pt.y);
			kpObject.put("size", point.size);
			kpJson.put(kpObject);
		}
		object.put("data", kpJson);
		String json = object.toString();
		return json;
	}
	public static MatOfKeyPoint jsonToMatOfKeyPoint(String json) throws JSONException{
		JSONObject object = new JSONObject(new JSONTokener(json));
		ArrayList<KeyPoint> keypointList = new ArrayList<KeyPoint>();
		JSONArray jsonKeypoints = object.getJSONArray("data");
		for(int i = 0; i < jsonKeypoints.length(); i++){
			JSONObject kpObject = jsonKeypoints.getJSONObject(i);
			float 	angle		= (float) kpObject.getDouble("angle");
			int 	classId 	= kpObject.getInt("class_id");
			int		octave		= kpObject.getInt("octave");
			float	response	= (float) kpObject.getDouble("response");
			float	x			= (float)kpObject.getDouble("x");
			float	y			= (float)kpObject.getDouble("y");
			float 	size		= (float) kpObject.getDouble("size");
			KeyPoint keyPoint = new KeyPoint(x, y, size, angle, response, octave, classId);
			keypointList.add(keyPoint);
		}
		MatOfKeyPoint keypoints = new MatOfKeyPoint();
		keypoints.fromList(keypointList);
		return keypoints;
	}
	
	public static String readFile(String filename){
		String contents = "";
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filename));
			for(String line = reader.readLine(); line != null; line = reader.readLine()){
				contents += line;
			}
			reader.close();
		} catch (FileNotFoundException e) {
			Log.e("Fatal", "File " + filename + " was not found", e);
		} catch (IOException e) {
			Log.e("Fatal", "An error occured while reading or closing " + filename + ".", e);
		}
		return contents;
	}
}
