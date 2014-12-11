package com.example.welthershow;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Xml;
import android.widget.Toast;

public class LoadingActivity extends Activity {
	protected static final int LA_CODE = 5;
	protected static final int LA_ERROR = 0;
	private static final int RESULT_ERR = 0;
	//String flag = null;
	String[] arr;
	private final String basePath = "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx/getWeatherbyCityName?theCityName=";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		Bundle budle=this.getIntent().getExtras();
		String name=budle.getString("name");
		//flag = budle.getString("flag");
		CONN(name);
		
	}
	
	
	protected void  CONN(String name){
	    	final String cityName = name;
//	    	System.out.println(cityName);
	    	final List<String> list = new ArrayList<String>();
	    	if(TextUtils.isEmpty(cityName)){
	    		Toast.makeText(LoadingActivity.this, "�����������", 0).show();
	    	}else {
	    		//setContentView(R.layout.loading);
	    		final String path = basePath+URLEncoder.encode(cityName);
	    		new Thread(){
	    			@Override
					public void run() {
	    				try {
	    					//����Ҫ���ʵĵ�ַ��URL
	    					System.out.println("run");
							URL url = new URL(path);
							//�õ�������������Ӷ���
	    					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    					//��������ʽΪGET
	    					conn.setRequestMethod("GET");
	    					//�������ӳ�ʱʱ��
	    					conn.setConnectTimeout(5000);
	    					//��÷��������ص�״̬��
	    					int code = conn.getResponseCode();
							//System.out.println("run");
							if(code == 200){
								System.out.println("code == 200");
								InputStream is = conn.getInputStream();
								//��������������
								XmlPullParser parser = Xml.newPullParser();
								//�������������ַ�����
								parser.setInput(is, "UTF-8");
								int Eventtype = parser.getEventType();
								while(Eventtype != XmlPullParser.END_DOCUMENT){
									String string = new String();
									if(Eventtype == XmlPullParser.START_TAG){
										if("string".equals(parser.getName())){
											string = parser.nextText();
											list.add(string);
										}
									}
									Eventtype = parser.next();
								}
//								if(flag.equals("fromMain")){
									arr = list.toArray(new String[list.size()]);
									for(String str : arr){
										System.out.println(str);
									}
									Intent intent = new Intent();
									intent.putExtra("arr", arr);
									setResult(LA_CODE,intent);
									finish();
//								}else{
//									
//								}
								
							}else{
								
							}
						} catch (MalformedURLException e) {
//							Intent intent = new Intent();
//							setResult(LA_ERROR,intent);
//							finish();
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
//							Intent intent = new Intent();
//							setResult(LA_ERROR,intent);
//							finish();
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (XmlPullParserException e) {
//							s
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	    			};
	    		}.start();
	    	}
	
	 }
	
    @Override
	protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	Intent intent = new Intent();
		setResult(RESULT_ERR, intent);
    }
 }
