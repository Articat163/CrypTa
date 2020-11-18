package ru.articat.crypta;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.widgets.SnackBar;

import ru.articat.crypta.Fragments.ForumsListFragment;
import ru.articat.crypta.Fragments.MarkedListFragment;
import ru.articat.crypta.Fragments.SectionFragment;
import ru.articat.crypta.Fragments.ThemeFragment;
import ru.articat.crypta.Settings.AppSettings;
import ru.articat.crypta.Settings.Constants;
import ru.articat.crypta.Settings.DoPreferences;
import ru.articat.crypta.Settings.SettingsActivity;
import ru.articat.crypta.Util.AppVersionDialog;
import ru.articat.crypta.Util.Blur;
import ru.articat.crypta.Util.CircleImageView;
import ru.articat.crypta.Util.Converter;
import ru.articat.crypta.Util.TypeFaceUtil;


//import com.google.android.youtube.player.YouTubeBaseActivity;
//import com.google.android.youtube.player.YouTubePlayerFragment;


@TargetApi(11)
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SharedPreferences.OnSharedPreferenceChangeListener //, NewsByDayListAdapter.CallbackFromNewsByDay//, IsCookiesCorrectInterface/* implements iSlideMenuCallback*/
{

	@Override
	public void onSharedPreferenceChanged(SharedPreferences p1, String p2) {
		Constants.AUTHORIZED=true;
		recreate();
	}


//	@Override
//	public void getAllMessagesCallback()
//	{
//		startRepeatingTask();
//	}

// наш плеер, который будет проигрывать видео
//	private YouTubePlayer youtubeplayer;
	
// --Commented out by Inspection START (20.09.2017 22:11):
////	public static final String APP_SAVE = "MP_Data";
//private String url;
// --Commented out by Inspection STOP (20.09.2017 22:11)
	private boolean doubleBackToExitPressedOnce = false;
//	private MyPlaybackEventListener playbackEventListener;
	
//	YouTubePlayerView youTubeView;
	
//	private DrawerLayout mDrawerLayout;
//    private ListView mDrawerList;

	private CircleImageView roundView;
	private ImageView headerImage;
// --Commented out by Inspection START (20.09.2017 22:11):
////	Converter convert;
////	DrawerArrowDrawable drawerArrowDrawable;
////	private float offset;
//// 	private boolean flipped;
//	private final static int NEWS_INTERVAL = 1000 * 60 * 2; //2 minutes
// --Commented out by Inspection STOP (20.09.2017 22:11)
	private final static int MESSAGES_INTERVAL = 1000 * 20; // 20 seconds
	private final Handler mHandler = new Handler();
	private final FragmentManager fm = getSupportFragmentManager();
	
//	SharedPreferences mySett;
private TextView txtUserNick;
	// --Commented out by Inspection (20.09.2017 22:11):private AppSettings appSettings;
//	RelativeLayout drawerPane, headerLayout, footerLayout;
//	FloatingActionButton fab;
private DoPreferences doPref;
//	SlideMenuAdapter mAdapter;
//	ButtonIcon settings, exit;
//	int THEMES;
//	int primaryColor, secondaryColor;
private SharedPreferences sharedPrefs;
	private Vibrator vb;
	private Resources res;
//	CookieManager manager;
private FloatingActionButton fab;
	private Boolean showSnack;
	private NavigationView navigationView;
	DrawerLayout drawer;
	private String TAG = Constants.TAG;

    @Override
    public void onCreate(Bundle savedInstanceState)
	{
		
		TypeFaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Condensed.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		super.onCreate(savedInstanceState);

		sharedPrefs.registerOnSharedPreferenceChangeListener(this);
        setContentView(R.layout.activity_main);

		int selectedTheme = Integer.parseInt(sharedPrefs.getString("key_theme", "0"));
		if(selectedTheme ==0) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
		else if(selectedTheme ==1)AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
		else if(selectedTheme ==2)AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

		if (sharedPrefs.getBoolean("key_snack", false)) {
			if(selectedTheme ==0) new SnackBar(this, "DAY/NIGHT AUTO MODE").show();
			else if(selectedTheme ==1) new SnackBar(this, "DAY MODE").show();
			else if(selectedTheme ==2) new SnackBar(this, "NIGHT MODE").show();
		}

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        Log.i(TAG, "TEST LOG");

//        manager = new CookieManager();
//		CookieHandler.setDefault( manager  );
		
//		appSettings= new AppSettings();
		doPref=new DoPreferences(this);
		vb = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

		res=getResources();

		showSnack=sharedPrefs.getBoolean("key_snack", false);

		fab = (FloatingActionButton) findViewById(R.id.fab);

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);

			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				loadSlideMenuPhoto();
				setNawItemChecked(Constants.SELECTEDITEM);
			}
		};

		drawer.setDrawerListener(toggle);
		toggle.syncState();

		navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		View header = navigationView.getHeaderView(0);
//		TextView text = (TextView) header.findViewById(R.id.textView);

        txtUserNick=(TextView)header.findViewById(R.id.slidemenuheaderTextView1);


        //*************************************
        //
        // 	 Add Header to Navigation Drawer
        //
        //*************************************
	// add slide profile background
		headerImage=(ImageView)header.findViewById(R.id.ivHeader);
	// add rounded profile image
		roundView=(CircleImageView)header.findViewById(R.id.roundedImageView1);
		roundView.setImageDrawable(getResources().getDrawable(R.drawable.profile_bg));
		

		headerImage.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
//					Toast.makeText(getApplicationContext(), "Clicked",Toast.LENGTH_SHORT).show();
					selectItem(R.id.profile);
				}
			});
		
        if (savedInstanceState == null) {
            selectItem(R.id.forums);
        }
		
	//*************************************
	//
	// 	 Add Footer to Navigation Drawer
	//
	//*************************************
	
//		exit=(ButtonIcon)findViewById(R.id.slidemenufooterImageButtonExit);
//		exit.setOnClickListener(new OnClickListener(){
//		@Override
//		public void onClick(View v){
//			if (sharedPrefs.getBoolean("key_taktil", false)) {
//				vb.vibrate(res.getInteger(R.integer.taktil));
//			}
//			exitDialog();
//		}
//	});
//
//		settings=(ButtonIcon)findViewById(R.id.slidemenufooterButtonSettings);
//	settings.setOnClickListener(new OnClickListener(){
//		@Override
//		public void onClick(View v){
//			if (sharedPrefs.getBoolean("key_taktil", false)) {
//				vb.vibrate(res.getInteger(R.integer.taktil));
//			}
//			Intent settingsIntent = new Intent(getApplication(), SettingsActivity.class);
//
//			settingsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			getApplication().startActivity(settingsIntent);
//		}
//	});
	

	//*************************************
	//
	//  		arrow animation
	//
	//*************************************
		

//	final ImageView imageView = (ImageView) findViewById(R.id.drawer_indicator);
//	TextView txtSelectedName= (TextView) findViewById(R.id.titleSelectedName);
//	final Resources resources = getResources();
//
//	drawerArrowDrawable = new DrawerArrowDrawable(resources);
//		drawerArrowDrawable.setStrokeColor(resources.getColor(R.color.window_background_color_1));
//	imageView.setImageDrawable(drawerArrowDrawable);
//
//	// drawer listener
//	mDrawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
//	@Override
//	public void onDrawerSlide(View drawerView, float slideOffset) {
//		offset = slideOffset;
//
//		// Sometimes slideOffset ends up so close to but not quite 1 or 0.
//		if (slideOffset >= .995) {
//			flipped = true;
//			drawerArrowDrawable.setFlip(flipped);
//		} else if (slideOffset <= .005) {
//			flipped = false;
//			drawerArrowDrawable.setFlip(flipped);
//		}
//
//		drawerArrowDrawable.setParameter(offset);
//	}

//				@Override
//				private boolean onPrepareOptionsMenu(Menu menu) {
//					boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//					if (drawerOpen) {
//
//
//						mAdapter.notifyDataSetChanged();
//						mDrawerList.setAdapter(mAdapter);
//					}
//					return false;
//				}
//
//	// меню закрыто
//	@Override
//	public void onDrawerClosed(View drawerView) {
//		if (!Constants.FABLOCK && Constants.WHATOPEN<=2) fabHide(false);
//		}
//
//
//	// меню открыто
//	@Override
//	public void onDrawerOpened(View drawerView) {
//		loadSlideMenuPhoto();
//		mAdapter.notifyDataSetChanged();
//		mDrawerList.setAdapter(mAdapter);
//		fabHide(true);
//		}
//	});

//	imageView.setOnClickListener(new View.OnClickListener() {
//	@Override
//		public void onClick(View v) {
//		if (AppSettings.unlock){
//			if (mDrawerLayout.isDrawerVisible(START)) {
//				mDrawerLayout.closeDrawer(START);
//			} else {
//				mDrawerLayout.openDrawer(START);
//			}
//		}
//	}
//	});
	// end arrow animation
	
	
	// FAB
//		fab = new FloatingActionButton.Builder(this)
//			.withDrawable((getResources().getDrawable(R.drawable.pen)))
//			.withButtonColor(secondaryColor)
//			.withGravity(Gravity.BOTTOM | Gravity.RIGHT)
//			.withMargins(0, 0, 16, 16)
//			.create();
//		fabHide(true);
		
		fab.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				if (sharedPrefs.getBoolean("key_taktil", false)) {
					vb.vibrate(res.getInteger(R.integer.taktil));
				}
				switch (Constants.WHATOPEN){
					
					case 0: // создать тему
						Toast.makeText(getApplication(), "В разработке: создать новую тему", Toast.LENGTH_SHORT).show();

					break;
					
					case 1: // ответить в теме
						Toast.makeText(getApplication(), "В разработке: ответ в теме", Toast.LENGTH_SHORT).show();

					break;
					
					case 2: // послать сообщения из fragmentProfile
//						FragmentProfile fragment = (FragmentProfile)fm.findFragmentById(R.id.fragment_place);
//						fragment.messageFromProfile();
					break;
					
					case 3: // fab в закладках
						Toast.makeText(getApplication(), "здесь скоро будет сортировка", Toast.LENGTH_SHORT).show();
					break;
				}
			}
		});

    /** end fab */
		
//		url="pfPF0AcfPZE"; // стартовое видео
//		playbackEventListener = new MyPlaybackEventListener();
//		youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
//		youTubeView.initialize(DeveloperKey.DEVELOPER_KEY, this);
//		youTubeView.setVisibility(View.GONE); // скрываем преер при загрузке приложения

//		mySett = getSharedPreferences(APP_SAVE, MODE_PRIVATE);
//		Editor ed = mySett.edit();
////		ed.commit();
//		ed.apply();
		
		// если ещё не прошли авторизацию
//		if(!Constants.AUTHORIZED){
//		FragmentChanger(new FragmentLogin()); // запуск фрагмента авторизации
//		// блокируем боковое меню пока не авторизовался
////		mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//		}
//		else

		setUserNick();
	}

    /** end onCreate */



	
	/** фоновая проверка новых личных сообщений */

//	private final Runnable mHandlerTask = new Runnable(){
//
//		@Override
//		public void run() {
//			new GetNumberUnreadMessages(getApplication()).init();
//		}
//	};
//
//	private void startRepeatingTask(){
//	//	Toast.makeText(this, "startRepeatingTask", Toast.LENGTH_SHORT).show();
//		if (sharedPrefs.getBoolean("key_notify_background", true)) {
//			if (showSnack) snack("Background notifications ON");
//
//			mHandlerTask.run();
//		}
//		else{
//			if (showSnack) snack("Background notifications OFF");
//
//		}
//	}

	private void stopRepeatingTask(){
	//	Toast.makeText(this, "STOP RepeatingTask", Toast.LENGTH_SHORT).show();
//		mHandler.removeCallbacks(mHandlerTask);
	}

	private void snack(String text){
		new SnackBar(this, text).show();
	}
	
	
	//*************************************
	//
	//  		loadSlideMenuPhoto
	//
	//*************************************
	
	private void loadSlideMenuPhoto(){
		Bitmap bm;
		if(AppSettings.profile_photo ==null || !sharedPrefs.getBoolean("key_avatar", true)) {
			 bm= Converter.drawableToBitmap(getResources().getDrawable(R.drawable.profile_bg));
		}
		else bm= AppSettings.profile_photo;
		
		Blur blur= new Blur(this);
		headerImage.setImageBitmap( blur.blurRenderScript(bm, 20));
		headerImage.setColorFilter(Color.argb(83, 0, 0, 0));
		roundView.setImageBitmap(bm);
	}
	
	
// --Commented out by Inspection START (20.09.2017 22:11):
//	// разблокируем slide menu
//	public void unlockSlideMenu(){
////		mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//		fabHide(true);
//
		// если база уже загружена запускаем проверку новых личных смс
//		if(doPref.loadData("firstSynk", "false").equals("true")) startRepeatingTask();
//		// иначе грузим базу с сайта
//		else new GetAllMessages(this, this).execute();
//	}
// --Commented out by Inspection STOP (20.09.2017 22:11)

	
	// выводим имя пользователя
	private void setUserNick(){
		txtUserNick.setText(doPref.loadData(Constants.USERNAME, ""));
//        if(doPref.loadData("firstSynk", "false").equals("true")) startRepeatingTask();
//            // иначе грузим базу с сайта
//        else new GetAllMessages(this, this).execute();
	}

	
	
	// прячем или показываем FAB
	public void fabHide(boolean hide){
		if(hide) fab.hide();
		else fab.show();
	}

  
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//				Toast.makeText(this, "Доступно в следующих версиях", Toast.LENGTH_LONG).show();
//            return super.onOptionsItemSelected(item);
//        }


	/* The click listner for ListView in the navigation drawer */
//    private class DrawerItemClickListener implements ListView.OnItemClickListener {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//			if (sharedPrefs.getBoolean("key_taktil", false)) {
//				vb.vibrate(res.getInteger(R.integer.taktil));
//			}
//            selectItem(position);
//        }
//    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			Intent settingsIntent = new Intent(getApplication(), SettingsActivity.class);
			settingsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			getApplication().startActivity(settingsIntent);
			return true;
		}
		else if (id == R.id.action_exit) {
			exitDialog();
//            exitDialog();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		// Handle navigation view item clicks here.

		selectItem(item.getItemId());

		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}


	// выбор пункта в navigation drawer
    private void selectItem(int position) {
//		Log.i(TAG, "select item: "+position);
        fabHide(true);
		switch(position){

			case R.id.profile: // профиль. Вызывается нажатием на фото в Header
				Toast.makeText(this, R.string.in_process, Toast.LENGTH_SHORT).show();
				break;
			
			case R.id.unread: // непрочитанное
				Toast.makeText(this, R.string.in_process, Toast.LENGTH_SHORT).show();
				break;
			
			case R.id.messages: // сообщения
				Toast.makeText(this, R.string.in_process, Toast.LENGTH_SHORT).show();
				break;
			
			case R.id.favorites: // избранное
				FragmentChanger(new MarkedListFragment());
				break;
		
			case R.id.bookmarks: // закладки
				Toast.makeText(this, R.string.in_process, Toast.LENGTH_SHORT).show();
				break;
		
			case R.id.forums: // список форумов
				FragmentChanger(new ForumsListFragment());
				break;
			
			case R.id.activity: // активность
				Toast.makeText(this, R.string.in_process, Toast.LENGTH_SHORT).show();
				break;

            case R.id.about: // версия приложения
				new AppVersionDialog(this).showDialog();
                break;

            case R.id.help: // помощь проекту
                /** моя визитка Яндекс.Деньги */
//                https://money.yandex.ru/to/410015323448809
                /** тема приложения на сайте */

//                new AppHelpDialog(this).showDialog();
				Toast.makeText(this, R.string.in_process, Toast.LENGTH_SHORT).show();
                break;

            case R.id.sms_to_author: // обратная связь
				Toast.makeText(this, R.string.in_process, Toast.LENGTH_SHORT).show();
                break;

            case R.id.app_setting: // настройки
                Intent settingsIntent = new Intent(getApplication(), SettingsActivity.class);
                settingsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplication().startActivity(settingsIntent);
                break;

            case R.id.app_exit: // выход
//                fabHide(true);
                exitDialog();
                break;
		}
//		navigationView.setCheckedItem(position);
//		Log.d(TAG, "navigationView.getIdnavigationView.getIdnavigationView.getId: "+navigationView.getId());
//		navigationView.getMenu().getItem(selected).setChecked(true);
    }


	
//	public void openDrawer(){
//		mDrawerLayout.openDrawer(drawerPane);
//	}
	
	
	//*************************************
	//
	//  			YouTube
	//
	//*************************************
   
//	public void PlayVideo(final String url){
//		// вызываем этот метод из фрагмента для просмотра видео
//		youTubeView.setVisibility(View.VISIBLE);
//		try {
//			youtubeplayer.cueVideo(url);
//		} catch (Exception e) {
//			e.printStackTrace();
////			Toast.makeText(this, R.string.error_player, Toast.LENGTH_SHORT).show();
//            youTubeView.setVisibility(View.GONE);
//            final Dialog dialog = new Dialog(this, getString(R.string.cannot_show_video), getString(R.string.open_youtube));
//            dialog.addCancelButton("cancel");
//
//            dialog.show();
//            dialog.setOnAcceptButtonClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    diaLog.d(ismiss();
//                    watchYoutubeVideo(url);
//                }
//            });
//			dialog.setOnCancelButtonClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    diaLog.d(ismiss();
//                }
//            });
//		}
//	}
//
//  @Override
//  public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
//      boolean wasRestored) {
//    if (!wasRestored) {
//		youtubeplayer=player;
//		player.setPlaybackEventListener(playbackEventListener);
//	//	player.cueVideo("wKJ9KzGQq0w");
//    }
//  }
//
//  @Override
//  protected YouTubePlayer.Provider getYouTubePlayerProvider() {
//    return (YouTubePlayerView) findViewById(R.id.youtube_view);
//  }
//
//// реакция приложения на поведение плеера
//  private final class MyPlaybackEventListener implements PlaybackEventListener {
//
//    @Override
//    public void onPlaying() {
//      Log.i(TAG, "PLAYING");
//    }
//
//    @Override
//    public void onBuffering(boolean isBuffering) {
//		Log.i(TAG, "BUFFERING");
//    }
//
//    @Override
//    public void onStopped() {
//	//	youTubeView.setVisibility(View.GONE);
//		Log.i(TAG, "STOPPED");
//
//    }
//
//    @Override
//    public void onPaused() {
//		youTubeView.setVisibility(View.GONE);
//		Log.i(TAG, "PAUSED");
//    }
//
//    @Override
//    public void onSeekTo(int endPositionMillis) {
//		Log.i(TAG, "SEEKING");
//	}
//  }

	
// смена фрагментов
	public void FragmentChanger(Fragment fr) {
			fabHide(false);
			FragmentTransaction fragmentTransaction = fm.beginTransaction();
			fragmentTransaction.replace(R.id.fragment_place, fr);
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
//		fm.beginTransaction()
//				.addToBackStack(null)
//				.replace(R.id.fragment_place, fr)
//				.commit();


//		Log.w(TAG, "Fragment Count: "+fm.getBackStackEntryCount());
		}
		
	
	/** нажатие аппаратной кнопки назад */

	 @Override
	public void onBackPressed(){
		 Log.w(TAG, "BackPressed. Count: "+fm.getBackStackEntryCount());

			DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
			if (drawer.isDrawerOpen(GravityCompat.START)) {
				drawer.closeDrawer(GravityCompat.START);
			} else {
                int fragments = fm.getBackStackEntryCount();
                if (fragments == 1) {
					Log.d(TAG, "onBackPressed: key_tap_exit = " + sharedPrefs.getBoolean("key_tap_exit", true));
                    if (sharedPrefs.getBoolean("key_tap_exit", true))doubleTapExit();
                    else exitDialog();
                } else {
                    if (fm.getBackStackEntryCount() > 1) {
                        fm.popBackStack();
                    } else {
                        super.onBackPressed();

                    }
                }
            }
	}

    private void doubleTapExit() {
        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
			Toast.makeText(getApplication(), R.string.see_you_later, Toast.LENGTH_SHORT).show();
			finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.double_tap_for_exit, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


    /** диалоговое окошко ВЫХОД*/

	private void exitDialog(){
		
		LinearLayout dialoglayout;	
		dialoglayout = (LinearLayout)this.getLayoutInflater().inflate(R.layout.dialog_exit, null); 
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(dialoglayout);
		builder.setCancelable(true);
		final AlertDialog alert;
		alert=builder.show();
		ButtonFlat btnYes= (ButtonFlat)dialoglayout.findViewById(R.id.dialogexitButtonYes);
		
		btnYes.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				if (sharedPrefs.getBoolean("key_taktil", false)) {
					vb.vibrate(res.getInteger(R.integer.taktil));
				}
				alert.dismiss();
				Toast.makeText(getApplication(), R.string.see_you_later, Toast.LENGTH_SHORT).show();
				finish();
			//	finishAffinity();
			}
		});
		ButtonFlat btnNo= (ButtonFlat)dialoglayout.findViewById(R.id.dialogexitButtonNo);
		
		btnNo.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				if (sharedPrefs.getBoolean("key_taktil", false)) {
					vb.vibrate(res.getInteger(R.integer.taktil));
				}
				alert.dismiss();
			}
		});
		
	}
	  
	  
	/** кнопка меню на телефоне */

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//			if (mDrawerLayout.isDrawerVisible(drawerPane)) mDrawerLayout.closeDrawer(drawerPane);
//			else openDrawer();
		return keyCode == KeyEvent.KEYCODE_MENU || super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onResume() {
		Log.i(TAG, "MainActivity onResume");
		navigationView.setCheckedItem(R.id.forums);
    super.onResume();
}

	@Override
	protected void onPause() {
	Log.i(TAG, "MainActivity onPause");
    super.onPause();
}

	@Override
	public void onStop() {
		Log.i(TAG, "MainActivity onStop");
    /* release youtube when go to other fragment or back pressed */
//		if (youtubeplayer != null) {
//			youtubeplayer.release();
//		}
//		youtubeplayer = null;
		super.onStop();
	}

	@Override
	protected void onDestroy()
	{
		Log.i(TAG, "MainActivity onDestroy");
		super.onDestroy();
		//Stop the analytics tracking
		
		// останавливаем фоновую проверку личных смс:
		stopRepeatingTask(); 
		// если не отмечено запомнить логин и пароль, то удаляем запись
		if(doPref.loadData(Constants.REMEMBER, "false").equals("false")){
			doPref.saveData(Constants.LOGIN, "");
			doPref.saveData(Constants.PASSWORD, "");
		}
		Constants.AUTHORIZED=false;
	}
	
	public void onSaveInstanceState(Bundle outState){
		Log.w(TAG, "MainActivity.onSaveInstanceState");
		/* release youtube when home button pressed. */
//		if (youtubeplayer != null) {
//			youtubeplayer.release();
//		}
//		youtubeplayer = null;
		super.onSaveInstanceState(outState);
	}

    // Call Back method  to get the Message form NewsActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode == 2 && resultCode == RESULT_OK)
        {
			String callFrom= data.getStringExtra("FROM"); // откуда нажали
            String intentLink = data.getStringExtra("LINK"); // ссылка

			// определяем откуда нажатие и ссылка
			switch (callFrom){
				case "SECTION": // открываем список тем в разделе
					SectionFragment ff = new SectionFragment();
					Bundle b1 = new Bundle();
					b1.putInt("startPosition", Integer.parseInt(intentLink));
					ff.setArguments(b1);
					FragmentChanger(ff);
					break;

				case "THEME":  // открывает тему
					ThemeFragment ft = new ThemeFragment();
					Bundle b2 = new Bundle();
					b2.putString("link", intentLink);
					ft.setArguments(b2);
					FragmentChanger(ft);
					break;

				case "DRAWER": // выбор пункта меню
					int Id =Integer.parseInt(intentLink); // ссылка
					selectItem(Id);
					break;

			}
//			navigationView.setCheckedItem(R.id.forums);
//			navigationView.getMenu().getItem(4).setChecked(true);
        }

        else if(requestCode == 3 && resultCode == RESULT_OK){ // result from AllMessages Activity

			int Id = data.getIntExtra("ID", 0); // ссылка
			selectItem(Id);
		}
		else{
			if (showSnack) snack("CANCELED");
//			navigationView.getMenu().getItem(4).setChecked(true);
//			navigationView.setCheckedItem(R.id.forums);
		}
    }

    // если в приложении какой-то косяк с проигрыванием видео,
    // то вызываем приложение youtube или браузер.
    public void watchYoutubeVideo(String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }

    public void setNawItemChecked(int id){
//		Log.d(TAG, "setNawItemChecked: WHATOPEN: "+ Constants.WHATOPEN);
        navigationView.setCheckedItem(id);
	}

}
