package com.example.renhongtao.hello;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Environment;



public class HttpUtil {

	private static final String TAG = HttpUtil.class.getSimpleName();
	static URL url = null;
	private static String responseData;


	public static String get(String httpUrl) {
		try {
			url = new URL(httpUrl);
    		HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
			urlConn.setRequestMethod("GET");
			urlConn.setConnectTimeout(5000);
			urlConn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			urlConn.setRequestProperty("Charset", "UTF-8");
			urlConn.connect();

			int responseCode = urlConn.getResponseCode();


			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream inputStream = urlConn.getInputStream();
				responseData = dealResponseResult(inputStream);

			} else {
				responseData = "FAIL";
			}
		} catch (IOException e) {

			responseData =  "FAIL";
			e.printStackTrace();
		} finally {
			return responseData;
		}
	}

	public static String dealResponseResult(InputStream inputStream) {
		String resultData = null; // 存储处理结果
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		try {
			while ((len = inputStream.read(data)) != -1) {
				byteArrayOutputStream.write(data, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		resultData = new String(byteArrayOutputStream.toByteArray());
		return resultData;
	}

	public static void saveToSDCard(String filename, String content)
			throws Exception {
		File file = new File(Environment.getExternalStorageDirectory(),
				filename);
		OutputStream out = new FileOutputStream(file);
		out.write(content.getBytes());
		out.close();
	}
}
