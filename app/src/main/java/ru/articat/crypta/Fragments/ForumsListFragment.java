package ru.articat.crypta.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;

import ru.articat.crypta.Adapters.ForumsListAdapter;
import ru.articat.crypta.DataBases.DBHelperForumList;
import ru.articat.crypta.Interfaces.GetTotalThemesInterface;
import ru.articat.crypta.MainActivity;
import ru.articat.crypta.Models.ForumsListModel;
import ru.articat.crypta.Parsing.GetForumsTotalThemes;
import ru.articat.crypta.R;
import ru.articat.crypta.Settings.Constants;
import ru.articat.crypta.Settings.ForumLinksList;
import ru.articat.crypta.Util.Rotate3DAnimation;
import ru.articat.crypta.Util.TypeFaceUtil;


public class ForumsListFragment extends Fragment implements GetTotalThemesInterface {

	private String TAG = Constants.TAG;

	@Override
	public void totalThemesCallback(ArrayList result)
	{
		// TODO: Implement this method
		totalList=result;
		for(int i=0; i<=totalList.size()-1; i++){
			dbHelper.updateTotal(dbHelper.getWritableDatabase(), String.valueOf(i+1), (Integer) totalList.get(i));
		}
		fillAdapter();
		adapter.notifyDataSetChanged();
	}


	private ForumsListAdapter adapter;
	private final ArrayList<ForumsListModel> forumsList = new ArrayList<>();

	private DBHelperForumList dbHelper;
	private Resources res;
//	private DoPreferences doPref;
	private ArrayList totalList;
//	int THEMES, primaryColor, windowColor, fieldColor;
private SharedPreferences sharedPrefs;

	@Override
	public View onCreateView(LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState)
	{
		TypeFaceUtil.overrideFont(getActivity(), "SERIF", "fonts/Roboto-Condensed.ttf");
		sharedPrefs = PreferenceManager
			.getDefaultSharedPreferences(getActivity());
//		THEMES = Integer.parseInt(sharedPrefs.getString("select_themes_list", "1"));
//
//		ThemesChanger.onActivityCreateSetTheme(getActivity(), THEMES);

		getActivity().setTitle(getResources().getString(R.string.forums));
		
		View view = inflater.inflate(R.layout.fragment_forums_list,
									 container, false);
		setHasOptionsMenu(true); // отобразить свой actionbar
		
			
		// получаем color в зависимомти от темы приложения
//		primaryColor=new ThemesChanger().getColorByName(getActivity(), "primary_color_", THEMES);
//		windowColor=new ThemesChanger().getColorByName(getActivity(), "window_background_color_", THEMES);
//		fieldColor=new ThemesChanger().getColorByName(getActivity(), "field_color_light_", THEMES);
//		secondaryColor=new ThemesChanger().getColorByName(this, "secondary_color_", THEMES);
		
//		switch(THEMES){
//			case 1:
//				primaryColor=getResources().getColor(R.color.primary_color1);
//			//	secondaryColor=getResources().getColor(R.color.secondary_color_1);
//				break;
//			case 2:
//				primaryColor=getResources().getColor(R.color.primary_color2);
//			//	secondaryColor=getResources().getColor(R.color.secondary_color_2);
//				break;
//		}
	//	Constants.WHATOPEN=4;
	
		res = getResources();
//		doPref = new DoPreferences(getActivity());
		dbHelper = new DBHelperForumList(getActivity());
	//	dataBase = dbHelper.getReadableDatabase();
		// если в базе нет записей, то заполняем базу
		if (!dbHelper.isDbFilled(dbHelper.getReadableDatabase(), getActivity())) {
			Log.w(Constants.TAG, "Базы ещё нет");
			dbLinksInit();
		} 
		totalList= new ArrayList();
		new GetForumsTotalThemes(getActivity(), this).execute();
		// заполняем адаптер
		adapter = new ForumsListAdapter(getActivity(), forumsList, this);
		ListView lv = (ListView) view.findViewById(R.id.fragmentforumslistListView);
	//	adapter.setNotifyOnChange(true);
	//	lv.setAdapter(adapter);
		fillAdapter();
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
				final int position, long id)
				{
					if (sharedPrefs.getBoolean("key_taktil", false)) {
						Vibrator vb = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
						vb.vibrate(res.getInteger(R.integer.taktil));
					}
					Animation rowRotateAnimation = new Rotate3DAnimation(0.0f, 90.0f, 100.0f, false, view);
					rowRotateAnimation.setDuration(res.getInteger(R.integer.anim_duration));
					rowRotateAnimation.setAnimationListener(new Animation.AnimationListener() {

							@Override
							public void onAnimationStart(Animation animation) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onAnimationRepeat(Animation animation) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onAnimationEnd(Animation animation) {
								// TODO Auto-generated method stub

								// TODO: Implement this method
								SectionFragment yfc = new SectionFragment();
								Bundle bundle = new Bundle();
//								Log.d(TAG, "adapter.getId()= "+((forumsList.get(position).id)-1));
								bundle.putInt("startPosition", forumsList.get(position).id-1); //position);
//								bundle.putString("name", forumsList.get(position).getTitle());
//								Log.d(TAG, "ForumsListFragment. bundle.putString: "+ forumsList.get(position).getTitle());
								yfc.setArguments(bundle);

								((MainActivity)getActivity()).FragmentChanger(yfc);
//								android.app.FragmentManager fragmentManager = getFragmentManager();
//								fragmentManager
//									.beginTransaction()
//									.addToBackStack(null)
//									.replace(R.id.fragment_place,  yfc)
//									.commit();

							}
						});
					view.startAnimation(rowRotateAnimation);
					
				
				}

			
		});
		adapter.notifyDataSetChanged();
		return view;
	}

	// заполняем базу данных ПРИ ПЕРВОМ запуске
	private void  dbLinksInit()
	{
		Log.w(Constants.TAG, "dbLinksInit");
		ArrayList<String> links = new ForumLinksList().getLinks();
		String[] titles = res.getStringArray(R.array.spisok_forumov);
	//	dbHelper.deleteDb(getActivity());
		for (int i = 0; i <= links.size() - 1; i++) {
			dbHelper.insertLinks(dbHelper.getWritableDatabase(), links.get(i), titles[i], 0, "false");
		}
	}

	// наполняем адаптер информацией из базы данных
	private void fillAdapter() {

		Log.d(TAG, "fillAdapter");
		SQLiteDatabase dataBase = dbHelper.getReadableDatabase();
		forumsList.clear();
//		Cursor cursor = dataBase.query(dbHelper.TABLE_NAME, new String[]{dbHelper.FORUM_LINK,
//										   dbHelper.FORUM_NAME, dbHelper.TOTAL_THEMES},
//									   null, null,
//									   null, null, null);
	//	String selectQuery = "SELECT  * FROM " + dbHelper.TABLE_NAME;
		String selectQuery = "SELECT  * FROM " + DBHelperForumList.TABLE_NAME+" ORDER BY "+ DBHelperForumList.STAR +" DESC";
	//	String selectQuery= "SELECT *, COUNT() as COUNT FROM (SELECT * FROM "+dbHelper.TABLE_NAME+" ORDER BY "+dbHelper.COLUMN_ID+" ) GROUP BY "+dbHelper.STAR+" DESC";
		
			Cursor cur = dataBase.rawQuery(selectQuery, null);

//			cursor.moveToFirst();
//
//			String link = cursor.getString(cursor.getColumnIndex(dbHelper.FORUM_LINK));
//			String name = cursor.getString(cursor.getColumnIndex(dbHelper.FORUM_NAME));
//				Log.i(TAG, "name = "+name);
//			int total = cursor.getInt(cursor.getColumnIndex(dbHelper.TOTAL_THEMES));
//				forumsList.add(new ForumsListModel(link, name, total));
		if (cur.moveToFirst()) {

			// определяем номера столбцов по имени в выборке
			int columnIdIndex = cur.getColumnIndex(DBHelperForumList.COLUMN_ID);
			int linkIndex = cur.getColumnIndex(DBHelperForumList.FORUM_LINK);
			int nameIndex = cur.getColumnIndex(DBHelperForumList.FORUM_NAME);
			int totalIndex = cur.getColumnIndex(DBHelperForumList.TOTAL_THEMES);
			int star = cur.getColumnIndex(DBHelperForumList.STAR);
			do {

				forumsList.add(new ForumsListModel(cur.getInt(columnIdIndex), cur.getString(linkIndex), cur.getString(nameIndex), cur.getInt(totalIndex), cur.getString(star)));

				// переход на следующую строку 
				// а если следующей нет (текущая - последняя), то false - выходим из цикла
			} while (cur.moveToNext());
			cur.close();
			dataBase.close();
		}
		
	}

	@Override
	public void onResume() {
	//	Log.i(TAG, "*******ON_RESUME*******");
		Constants.WHATOPEN=4;
		Constants.SELECTEDITEM=R.id.forums;
		((MainActivity)getActivity()).fabHide(true);
		super.onResume();
	}

	@Override
	public void onStop()
	{
		super.onStop();
	}
	
	
	public void refreshList(){
		forumsList.clear();
		fillAdapter();
		adapter.notifyDataSetChanged();
	//	lv.invalidate();
	//	lv.setAdapter(adapter);
	}
	
	
}
