package ru.articat.crypta;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFlat;

import java.util.ArrayList;

import ru.articat.crypta.Settings.Constants;
import ru.articat.crypta.Settings.DoPreferences;

import static ru.articat.crypta.Settings.Constants.TAG;

public class LockActivity extends AppCompatActivity implements OnClickListener {

	private ImageView img1;
	private ImageView img2;
	private ImageView img3;
	private ImageView img4;
	private TextView txtCreate;
	private TextView txtRepeate;
	private Boolean pinCodeCreated=false;
	private String pin;
	private String tempCode;
	private int trying=0;
	//	int THEMES;
private Resources res;
	private SharedPreferences sharedPrefs;
	
	private final ArrayList<String> pass = new ArrayList<>();
	
	private DoPreferences doPref;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		PreferenceManager.setDefaultValues(this, R.xml.app_preferences, false);
//		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lock);

		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		int selectedTheme = Integer.parseInt(sharedPrefs.getString("key_theme", "0"));
		if(selectedTheme ==0) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
		else if(selectedTheme ==1)AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
		else if(selectedTheme ==2)AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

		res= getResources();
		Constants.WHATOPEN=4;

		doPref=new DoPreferences(this);
		pin=doPref.loadData(Constants.PINCODE, "");
		pinCodeCreated = !pin.equals(""); //  pin.equals("")? false : true;
		pass.clear();
		
		if (!sharedPrefs.getBoolean("key_lock", true)) {
			Toast.makeText(this, R.string.pincode_off, Toast.LENGTH_SHORT).show();
			start();
		}

		ButtonFlat btn1 = (ButtonFlat) findViewById(R.id.one_btn);
		ButtonFlat btn2 = (ButtonFlat) findViewById(R.id.two_btn);
		ButtonFlat btn3 = (ButtonFlat) findViewById(R.id.three_btn);
		ButtonFlat btn4 = (ButtonFlat) findViewById(R.id.four_btn);
		ButtonFlat btn5 = (ButtonFlat) findViewById(R.id.five_btn);
		ButtonFlat btn6 = (ButtonFlat) findViewById(R.id.six_btn);
		ButtonFlat btn7 = (ButtonFlat) findViewById(R.id.seven_btn);
		ButtonFlat btn8 = (ButtonFlat) findViewById(R.id.eight_btn);
		ButtonFlat btn9 = (ButtonFlat) findViewById(R.id.nine_btn);
		ButtonFlat btn0 = (ButtonFlat) findViewById(R.id.zero_btn);
//		btnOk=(ButtonFlat)findViewById(R.id.but_ok);
//		btnClear=(ButtonFlat)findViewById(R.id.but_cancel);
		
		img1=(ImageView)findViewById(R.id.lockView1);
		img2=(ImageView)findViewById(R.id.lockView2);
		img3=(ImageView)findViewById(R.id.lockView3);
		img4=(ImageView)findViewById(R.id.lockView4);
		ImageView logo = (ImageView) findViewById(R.id.lockImageView1);
//        Glide
//				.with(this)
//				.load("http://res.cloudinary.com/dsge8g7xn/image/upload/v1505322809/nicy_white_a593m4.gif")
//				.error(R.drawable.logo_alpha)
//				.into(logo);

		txtCreate=(TextView)findViewById(R.id.lockTextViewCreate);
		txtRepeate=(TextView)findViewById(R.id.lockTextViewRepeat);
		
		if (!pinCodeCreated){
			txtCreate.setVisibility(View.VISIBLE);
			Log.d(TAG, "pinCode NOT Created");
		}
		
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		btn6.setOnClickListener(this);
		btn7.setOnClickListener(this);
		btn8.setOnClickListener(this);
		btn9.setOnClickListener(this);
		btn0.setOnClickListener(this);
//		btnOk.setOnClickListener(this);
//		btnClear.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View p1) {

		if (sharedPrefs.getBoolean("key_taktil", true)) {
			Vibrator vb = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
			vb.vibrate(res.getInteger(R.integer.taktil));
		}
		switch (p1.getId()){
			
			case R.id.one_btn:
				if(pass.size()<4) pass.add("1");
				break;
				
			case R.id.two_btn:
				if(pass.size()<4) pass.add("2");
				break;
				
			case R.id.three_btn:
				if(pass.size()<4) pass.add("3");
				break;

			case R.id.four_btn:
				if(pass.size()<4) pass.add("4");
				break;

			case R.id.five_btn:
				if(pass.size()<4) pass.add("5");
				break;
				
			case R.id.six_btn:
				if(pass.size()<4) pass.add("6");
				break;

			case R.id.seven_btn:
				if(pass.size()<4) pass.add("7");
				break;

			case R.id.eight_btn:
				if(pass.size()<4) pass.add("8");
				break;
				
			case R.id.nine_btn:
				if(pass.size()<4) pass.add("9");
				break;

			case R.id.zero_btn:
				if(pass.size()<4) pass.add("0");
				break;

//			case R.id.but_ok:
//				checkPassword();
//
//
//				break;
//
//			case R.id.but_cancel:
//				pass.clear();
//				clearDraw();
//				break;
				
		}
		draw();
		autoCheck();
	}
	
	
	private void draw(){
		if(pass.size()<=4){
			switch (pass.size()){
				case 1:
					img1.setBackgroundResource(R.drawable.round_lock_pressed);
					break;
					
				case 2:
					img2.setBackgroundResource(R.drawable.round_lock_pressed);
					break;
					
				case 3:
					img3.setBackgroundResource(R.drawable.round_lock_pressed);
					break;
					
				case 4:
					img4.setBackgroundResource(R.drawable.round_lock_pressed);
					break;
			}
		}
		
	}

	private void autoCheck(){
		if(pass.size()==4) checkPassword();
	}

	private void clearDraw(){
		img1.setBackgroundResource(R.drawable.round_lock);
		img2.setBackgroundResource(R.drawable.round_lock);
		img3.setBackgroundResource(R.drawable.round_lock);
		img4.setBackgroundResource(R.drawable.round_lock);
		
	}
	
	
	private void checkPassword(){
		
		if(!pinCodeCreated){ // пин-кода нет
			if(pass.size()==4){ // введены 4 цифры
				if(trying==0){ // первая попытка
					tempCode=pass.get(0)+pass.get(1)+pass.get(2)+pass.get(3);
					trying=1;
					txtCreate.setVisibility(View.GONE);
					txtRepeate.setVisibility(View.VISIBLE);
					clearDraw();
					pass.clear();
				} // end trying
				
				else{ // вторая попытка
					String secondTry=pass.get(0)+pass.get(1)+pass.get(2)+pass.get(3);
					if(tempCode.equals(secondTry)){ // обе попытки совпали
						txtRepeate.setVisibility(View.GONE);
						doPref.saveData(Constants.PINCODE, tempCode);
						start();
					} // end tempCode
					
					else{ // не совпали
						Toast.makeText(this, res.getString(R.string.not_equals), Toast.LENGTH_SHORT).show();
						trying=0;
						txtCreate.setVisibility(View.VISIBLE);
						txtRepeate.setVisibility(View.GONE);
						clearDraw();
						pass.clear();
					} // end не совпали
				} // end вторая попытка
				
				
			} // end pass.size
			else{ // пин-код меньше 4 цифр
				Toast.makeText(this, res.getString(R.string.four_numbs), Toast.LENGTH_SHORT).show();
			}
		} // end pinCodeCorrect
		
		else{ // пин-код есть
			if(pass.size()==4){ // ввели 4 цифры
				tempCode=pass.get(0)+pass.get(1)+pass.get(2)+pass.get(3);
				if(pin.equals(tempCode)){ // верный
				
				// показываем стандартное сообщение
				// которое можем поменять в
				// appbrain dashboard!
				// Get latest value for welcome_message, defaults to "открываем сообщения"
//					String welcomeMessage =
//						AppBrain.getSettings().get("welcome_message", res.getString(R.string.ok));
//
//				// Show as toast
//					Toast.makeText(this, welcomeMessage, Toast.LENGTH_LONG).show();
					start();
					
				} // end pin.equals
				
				else{ // не верный
					Toast.makeText(this, res.getString(R.string.not_success) , Toast.LENGTH_SHORT).show();
					clearDraw();
					pass.clear();
				} // end else
			} // end pass.size
		} // end pinCodeCreated
	}
	
	private void start(){
		Intent mainIntent = new Intent(this, LoginActivity.class);
//		Toast.makeText(this, res.getString(R.string.wellcom), Toast.LENGTH_SHORT).show();
		mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		this.startActivity(mainIntent);
		finish();
//		Log.i(TAG, "LockActivity. start");
//		Toast.makeText(this, res.getString(R.string.wellcom), Toast.LENGTH_SHORT).show();
//		Intent mainIntent = new Intent(this, MainActivity.class);Intent mainIntent = new Intent(this, MainActivity.class);
//		mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		this.startActivity(mainIntent);
//		finish();
	}
	
}
