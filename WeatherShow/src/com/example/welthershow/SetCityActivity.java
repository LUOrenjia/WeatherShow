package com.example.welthershow;



import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;



public class SetCityActivity extends Activity {
	
	protected static final int RESULT_CODE = 2;
	private static final int RESULT_ERR = 0;
	protected static final int SA_CODE = 4;
	private EditText CityName;
	private Button commit;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.set_city);
        CityName = (EditText) findViewById(R.id.CityName);
        commit = (Button) findViewById(R.id.commit);
        commit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub	id
				if(!CityName.getText().toString().trim().equals("")){
				Intent intent = new Intent(SetCityActivity.this,LoadingActivity.class);
				Bundle myBund=new Bundle();
				myBund.putString("name", CityName.getText().toString().trim());
				//myBund.putString("flag", "fromSet");
				intent.putExtras(myBund);
				SharedPreferences sp = getSharedPreferences("pref", MODE_PRIVATE);
				Editor editor = sp.edit();
				editor.putString("CityName", CityName.getText().toString().trim());
				editor.commit();
				startActivityForResult(intent, SA_CODE);
				}
			}
		});
    }
    
   
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);
    	if(requestCode == SA_CODE && resultCode != 0){
    	String[] arr = data.getStringArrayExtra("arr");
    	Intent intent = new Intent();
		intent.putExtra("arr", arr);
		setResult(RESULT_CODE, intent);
		finish();
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
    


