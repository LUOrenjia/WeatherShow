package com.example.welthershow;




import java.util.HashMap;
import java.util.Map;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static final int MA_CODE = 1;

	
	private TextView Date_y;
	private TextView CityName;
	private TextView Today_Temp;
	private TextView Today_Weather;
	private TextView Today_Wind;
	private TextView Index_d;
	private TextView Next_Day_Weather;
	private TextView Next_Day_Wind;
	private TextView Next_Day_Temp;
	private TextView After_Day_Weather;
	private TextView After_Day_Wind;
	private TextView After_Day_Temp;
	
	private ImageView Weather_Icon_1 ;
	private ImageView Weather_Icon_2 ;
	private ImageView Weather_Icon_3 ;
	
	//用来装天气情况和相应图标的映射关系
	private Map ImageMap = new HashMap();
	private void InitMap(){

		ImageMap.put("多云", R.drawable.weathericon_condition_04);
		ImageMap.put("多云转晴", R.drawable.weathericon_condition_03);
		ImageMap.put("多云转阴", R.drawable.weathericon_condition_03);
		ImageMap.put("晴转多云", R.drawable.weathericon_condition_03);
		ImageMap.put("晴转阴", R.drawable.weathericon_condition_03);
		ImageMap.put("阴转多云", R.drawable.weathericon_condition_03);
		ImageMap.put("晴", R.drawable.weathericon_condition_01);
		ImageMap.put("小雨", R.drawable.weathericon_condition_08);
		ImageMap.put("阴转小雨", R.drawable.weathericon_condition_08);
		ImageMap.put("小雨转中雨", R.drawable.weathericon_condition_08);
		ImageMap.put("小雨转多云", R.drawable.weathericon_condition_08);
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewInit();
        InitMap();
        SharedPreferences sp = getSharedPreferences("pref", MODE_PRIVATE);
        String cityName = sp.getString("CityName", "");
        if(cityName.isEmpty()){
        	Intent intent = new Intent(MainActivity.this, SetCityActivity.class);
        	startActivityForResult(intent, MA_CODE);
        }else{
        	Intent intent = new Intent(MainActivity.this,LoadingActivity.class);
			Bundle myBund=new Bundle();
			System.out.println("        "+cityName);
			myBund.putString("name", cityName);
			//myBund.putString("flag", "fromMain");
			intent.putExtras(myBund);
			startActivityForResult(intent, MA_CODE);
        	
        }

    }
    
    private void ViewInit(){
        Date_y = (TextView) findViewById(R.id.date_y);
        CityName = (TextView) findViewById(R.id.cityField);
        Today_Temp = (TextView) findViewById(R.id.currentTemp);
        Today_Weather = (TextView) findViewById(R.id.currentWeather);
        Today_Wind = (TextView) findViewById(R.id.currentWind);
        Index_d = (TextView) findViewById(R.id.index_d);
        Next_Day_Weather = (TextView) findViewById(R.id.weather02);
        Next_Day_Wind = (TextView) findViewById(R.id.wind02);
        Next_Day_Temp = (TextView) findViewById(R.id.temp02);
        After_Day_Weather = (TextView) findViewById(R.id.weather03);
        After_Day_Wind = (TextView) findViewById(R.id.wind03);
        After_Day_Temp = (TextView) findViewById(R.id.temp03);
        Weather_Icon_1 = (ImageView) findViewById(R.id.weather_icon01);
        Weather_Icon_2 = (ImageView) findViewById(R.id.weather_icon02);
        Weather_Icon_3 = (ImageView) findViewById(R.id.weather_icon03);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);
//    	&& resultCode != LoadingActivity.LA_ERROR
    	if(requestCode == MA_CODE && resultCode != 0 ){
    		String[] arr = data.getStringArrayExtra("arr");
    		//设置更新后的界面内容
    		Date_y.setText(arr[4].split(" ")[0].trim());
    		CityName.setText(arr[1].trim());
    		Today_Weather.setText(arr[6].split(" ")[1].trim());
    		Today_Wind.setText(arr[7].trim());
    		Today_Temp.setText(arr[5].trim());
    		Index_d.setText(arr[11].trim());
    		Next_Day_Temp.setText(arr[12].trim());
    		Next_Day_Weather.setText(arr[13].split(" ")[1].trim());
    		Next_Day_Wind.setText(arr[14].trim());
    		After_Day_Temp.setText(arr[17].trim());
    		After_Day_Weather.setText(arr[18].split(" ")[1].trim());
    		After_Day_Wind.setText(arr[19].trim());
    		Weather_Icon_1.setImageResource((Integer) ImageMap.get(arr[6].split(" ")[1].trim()));
    		Weather_Icon_2.setImageResource((Integer) ImageMap.get(arr[13].split(" ")[1].trim()));
    		Weather_Icon_3.setImageResource((Integer) ImageMap.get(arr[18].split(" ")[1].trim()));
//    		
    	}
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// TODO Auto-generated method stub
    	System.out.println(item.getItemId());
    	switch (item.getItemId()) {
		case R.id.change_city:
			Intent intent = new Intent(MainActivity.this, SetCityActivity.class);
        	startActivityForResult(intent, MA_CODE);
			break;
		case R.id.about:
			Intent aboutintent = new Intent(MainActivity.this,AboutActivity.class);
			startActivity(aboutintent);
			break;
		case R.id.exit:
			finish();
			break;
		default:
			
			break;
		}
    	return super.onOptionsItemSelected(item);
    }
   
}
