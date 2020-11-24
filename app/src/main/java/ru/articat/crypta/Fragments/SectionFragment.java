package ru.articat.crypta.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFlat;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import ru.articat.crypta.Adapters.SectionAdapter;
import ru.articat.crypta.DataBases.DBHelperForumList;
import ru.articat.crypta.MainActivity;
import ru.articat.crypta.Models.SecionModel;
import ru.articat.crypta.Parsing.VolleyResponce;
import ru.articat.crypta.R;
import ru.articat.crypta.Settings.Constants;
import ru.articat.crypta.Settings.ForumLinksList;
import ru.articat.crypta.Util.Rotate3DAnimation;
import ru.articat.crypta.Util.TypeFaceUtil;

import static ru.articat.crypta.Settings.Constants.TAG;


// Список тем в выбранном разделе
public class SectionFragment extends Fragment implements OnClickListener, OnScrollListener
{
	private int mLastFirstVisibleItem;
	private boolean selected = false;
	private ProgressDialog pd;
//	public String type;//, userNick;
	private ListView listView;
	private ButtonFlat btnSelect;
	private ButtonFlat btnLast;

	private final ArrayList<SecionModel> forum = new ArrayList<>();
	
	private Elements title;
	private int allPages;
	private int pageSelector=1;//, linkSelector=0;
	private String start;//, pagesTotal;
	private int startPosition;
	private ArrayList<String> ssylki = new ArrayList<>();

	private String urlTema;
	private String titleList;
	private String pagesList;
	private String nickList;
	private String dateList;
	private String lastUserList;
	private String lastUserDateList;
	private String lastUserTimeList;
	private String cityList;
	private String answersList;
	private String viewsList;
	private Boolean videoList;
	
	private SectionAdapter adapter;
	// --Commented out by Inspection (20.09.2017 22:30):private DoPreferences doPref;
	private Resources res;
	private DBHelperForumList dbHelper;
	private SharedPreferences sharedPrefs;
	private int FLAG=-1;

//	int THEMES, primaryColor, secondaryColor;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		TypeFaceUtil.overrideFont(getActivity(), "SERIF", "fonts/Roboto-Condensed.ttf");
		sharedPrefs = PreferenceManager
			.getDefaultSharedPreferences(getActivity());
        super.onCreate(savedInstanceState);
		Log.w(Constants.TAG, "SectionFragment.onCreate");
        Bundle bundle = getArguments();
		if (bundle != null) {
			startPosition = bundle.getInt("startPosition", 0);
		}
    }
	
   @Override
   public View onCreateView(LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
		  Log.w(Constants.TAG, "SectionFragment.onCreateView");
		  View view = inflater.inflate(R.layout.fragment_section,
			  container, false);
			  setHasOptionsMenu(true); // отобразить свой actionbar
			  
	   dbHelper= new DBHelperForumList(getActivity());
	   res=getActivity().getResources();
	   listView = (ListView)view.findViewById(R.id.listView1);
	   ButtonFlat btnFirst = (ButtonFlat) view.findViewById(R.id.btnFirst);
	   ButtonFlat btnPre = (ButtonFlat) view.findViewById(R.id.btnPre);
	   btnSelect=(ButtonFlat)view.findViewById(R.id.btnSelect);
	   ButtonFlat btnNext = (ButtonFlat) view.findViewById(R.id.btnNext);
	   btnLast=(ButtonFlat)view.findViewById(R.id.btnLast);
	
		btnFirst.setOnClickListener(this);
		btnPre.setOnClickListener(this);
		btnSelect.setOnClickListener(this);
		btnNext.setOnClickListener(this);
		btnLast.setOnClickListener(this);
		
	// узнаем количество страниц
//		getPages(startPosition);

	// получаем ссылки на страницы 
		ssylki=new ForumLinksList().getLinks();
		
	// стартовый форум
		start=ssylki.get(startPosition);

	// Настраиваем адаптер для спиннера
//		ArrayAdapter<?> adapter =
//			ArrayAdapter.createFromResource(getActivity(), R.array.spisok_forumov, R.layout.spinner_closed_item);
//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

	// Вызываем адаптер
		
	// спиннер на выбранную позицию
//		headSpinner.post(new Runnable() {
//        @Override
//        public void run() {
//            headSpinner.setSelection(startPosition);
//        }
//    });
//		headSpinner.setAdapter(adapter);
//		headSpinner.setOnItemSelectedListener(this);

		btnLast.setText(String.valueOf(allPages));
		btnSelect.setText(pageSelector +"/"+ allPages);
		ForumList();
	   	start(startPosition);
//		listView.setDivider(null);
	//	footer=createFooter();
	//	listView1.addFooterView(footer);
		
		// Floating buttons
		
//}
	  return view;
	  
   } // end onCreateView
   
  // actionbar для этого фрагмента
//   public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//    inflater.inflate(R.menu.fragment_forums, menu);
//    super.onCreateOptionsMenu(menu, inflater);
//	
//	   MenuItem item = menu.findItem(R.id.user_nick);
//	   item.setTitle(userNick);
//  }
   
	private void ForumList() {
		// Create the adapter to convert the array to views
		adapter = new SectionAdapter(getActivity(), forum);
		// Attach the adapter to a ListView
		
	// обработка выбора listview
		listView.setOnScrollListener(this);
		listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,
										final int position, long id) {
				//	Log.d("mplog", "itemClick: position = " + position + ", id = "
				//		  + id);
					if (sharedPrefs.getBoolean("key_taktil", false)) {
						Vibrator vb = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
						vb.vibrate(res.getInteger(R.integer.taktil));
					}
					Animation rowRotateAnimation = new Rotate3DAnimation(0.0f, 90.0f, 100.0f, false, view);
					rowRotateAnimation.setDuration(res.getInteger(R.integer.anim_duration));
					rowRotateAnimation.setAnimationListener(new Animation.AnimationListener() {

							@Override
							public void onAnimationStart(Animation p1) {
								// TODO: Implement this method
							}

							@Override
							public void onAnimationEnd(Animation p1) {
								// TODO: Implement this method
								ThemeFragment yfc = new ThemeFragment();
								Bundle bundle = new Bundle();
								bundle.putString("link", forum.get(position).urlTema);
								yfc.setArguments(bundle);
	
								((MainActivity)getActivity()).FragmentChanger(yfc);
							}

							@Override
							public void onAnimationRepeat(Animation p1) {
								// TODO: Implement this method
							}
							
							
					});
					
					view.startAnimation(rowRotateAnimation);
						

				//	}
				}
			});

		listView.setAdapter(adapter);
	}
	
	// создание Footer
//    View createFooter() {
//		footer = v.inflate(getActivity(), R.layout.forum_footer, null);
//		return footer;
//    }    

// кнопочки навигации
	@Override
	public void onClick(View p1)
	{
		
		switch (p1.getId()) 
		{
			case R.id.btnFirst:
				pageSelector=1;
				callAsynkTask();
				break;

			case R.id.btnPre:
				if (pageSelector>1) {
					pageSelector--; 
					callAsynkTask();
				}
				break;

			case R.id.btnSelect:
				pageSelectorDialog();
				break;

			case R.id.btnNext:
				if (pageSelector<allPages) {
					pageSelector++; 
					callAsynkTask();
				}
				break;

			case R.id.btnLast:
				pageSelector=allPages;
				callAsynkTask();
				break;
		}

	}

	private void callAsynkTask(){
	//	ForumsTitles forumsTitles=new ForumsTitles();
		btnSelect.setText(pageSelector +"/"+ allPages);
		start=ssylki.get(startPosition);
		Log.d(TAG, "callAsynkTask: "+start+"&page="+String.valueOf(pageSelector));
		init(start+"/page/"+String.valueOf(pageSelector));
	}
	
	
	// ScrollListener
	@Override
	public void onScrollStateChanged(AbsListView p1, int p2)
	{
		// TODO: Implement this method
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
						 int visibleItemCount, int totalItemCount)
	{
		// TODO: Implement this method
		if(mLastFirstVisibleItem<firstVisibleItem)
		{
			// SCROLLING DOWN
			((MainActivity)getActivity()).fabHide(true);
		}
		if(mLastFirstVisibleItem>firstVisibleItem)
		{
			// SCROLLING UP
			((MainActivity)getActivity()).fabHide(false);
		}
		mLastFirstVisibleItem=firstVisibleItem;

	}
	

// внутрений класс который делает запросы для страниц
public void init(String url){
//	public class ForumsTitles extends AsyncTask<String, Void, String> {

	// запускаем ProgressDialog в момент запуска потока
	//	protected void onPreExecute() {
	LoadDialog(res.getString(R.string.loading));
	//	}
	// Метод выполняющий запрос в фоне

	// класс который захватывает страницу
	VolleyResponce vr= new VolleyResponce(getActivity());
	vr.getSimpleResponce(url, new VolleyResponce.VolleyCallback(){

		@Override
		public void requestComplete() {
			// TODO: Implement this method
			Log.d(TAG, "requestComplete: ");
		}

		@Override
		public String requestResult(String res) {
			// TODO: Implement this method
			Log.d(TAG, "requestResult: ");
			Document doc;
			//		try {
			// определяем откуда будем брать данные
			doc = Jsoup.parse(res);
			Element mainBox = doc.select("ol.cTopicList").first();
			//	.cookies(loginCookies)
			//	.data("page", arg[1])
			//	.get();
			// задаем с какого места
			title = mainBox.select("li.ipsDataItem");
//					Log.i(TAG, "elements" + title.toString());
			// чистим наш аррей лист для того что бы заполнить
			forum.clear();
			allPages = Integer.parseInt(doc.select("div[data-role=tablePagination]>ul.ipsPagination").attr("data-pages"));
			setPages();
//					Log.i(TAG, "requestResult: PAGES" + doc.select("div[data-role=tablePagination]>ul.ipsPagination").attr("data-pages"));
			// и в цикле захватываем все данные какие есть на странице
			for (Element elems : title) {
				// записываем в аррей лист
//						titleList=elems.select("a").eq(0).text();
				titleList = elems.select("span.ipsType_break").select("a").eq(0).text();
				//	urlTema.add(elems.select("a").eq(0).attr("abs:href"));
				urlTema = elems.select("span.ipsType_break").select("a").eq(0).attr("href"); // ссылка на выбранную тему
//						Log.i(TAG, "requestResult LINK:" + urlTema);

				nickList = elems.select("a.ipsType_break").eq(0).text();
				dateList = elems.select("div.ipsDataItem_meta > time[data-short]").text();
				videoList=(false); // нет на форуме CryptoTalk
				cityList=""; // нет на форуме CryptoTalk
				answersList = elems.select("ul.ipsDataItem_stats > li").first().select("span.ipsDataItem_stats_number").text(); // ответов
//				Log.v(Constants.TAG, "answersList = " + answersList);
//				Log.v(Constants.TAG, "Float.parseFloat(answersList) / 25f = " + Float.parseFloat(answersList) / 25f);
//				Log.v(Constants.TAG, "(int)Math.ceil(Float.parseFloat(answersList) / 25f) = " + (int)Math.ceil(Float.parseFloat(answersList) / 25f));
				pagesList = "" + (int)Math.ceil((Float.parseFloat(answersList)+1f) / 25f);
				viewsList = elems.select("ul.ipsDataItem_stats > li.ipsType_light > span.ipsDataItem_stats_number").text(); // просмотров
				lastUserDateList = elems.select("ul.ipsDataItem_lastPoster > li.ipsType_light > a.ipsType_blendLinks > time").attr("title"); // дата последнего ответа
				lastUserTimeList = "";
				lastUserList = elems.select("ul.ipsDataItem_lastPoster").text(); // последний ответ
//						Log.i(TAG, "requestResult ДАТА: " + elems.select("ul.ipsDataItem_lastPoster > li.ipsType_light > a.ipsType_blendLinks > time").attr("title"));
//						Log.i(TAG, "requestResult ВРЕМЯ: " + elems.select("ul.ipsDataItem_lastPoster > time[data-short]").text());
				forum.add(new SecionModel(urlTema, titleList, pagesList, nickList, dateList, lastUserList, lastUserDateList, lastUserTimeList, cityList, answersList, viewsList, videoList));

			}

			adapter.notifyDataSetChanged();
//			}
			// выключаем прогресс диалог
//					if (pd.isShowing()) {
//						pd.dismiss();
//					}
			pd.dismiss();

			// после запроса обновляем листвью
			listView.setAdapter(adapter);
//			if (pageSelector==1){ // только на 1-ой странице обновляемся
//				Pages pages=new Pages();
//				pages.execute();
//			}
//		}
//	}
			return null;
		}

		@Override
		public int requestError(int res)
		{
			// TODO: Implement this method
			return 0;
		}


	});

} //***


	
// обработка выбора из списка форумов

	private void start(int p3)
	{
		// TODO: Implement this method
//		Log.i(TAG, "Spinner onItemSelected: "+p3);
	//	Toast toast = Toast.makeText(getApplicationContext(), 
	//								 "Selected item: " + p3, Toast.LENGTH_SHORT); 

	//	toast.show(); 
	if (FLAG != p3){
		FLAG=p3;
	//	linkSelector=p3;
//		startPosition=p3;
//		pageSelector=1;
		start=ssylki.get(p3);
//		getPages(p3);
//		Constants.FORUMSLINK= dbHelper.getForumLink(dbHelper.getReadableDatabase(), String.valueOf(p3+1));
//		Constants.FORUMNAME= dbHelper.getForumName(dbHelper.getReadableDatabase(), String.valueOf(p3+1));
//		getActivity().setTitle(Constants.FORUMNAME);
		Log.i(TAG, "FORUMSLINK: "+ Constants.FORUMSLINK);
		Log.i(TAG, "FORUMNAME: "+ Constants.FORUMNAME);
		callAsynkTask();
	//	ForumsTitles forumsTitles=new ForumsTitles();
	//	forumsTitles.execute(start, String.valueOf(pageSelector));

		//	if (pageSelector==1){ // только на 1-ой странице обновляемся
		//		Pages pages=new Pages();
		//		pages.execute();
	}
//		}	Constants.FORUMSLINK= dbHelper.getForumLink(dbHelper.getReadableDatabase(), String.valueOf(p3+1));
		Constants.FORUMNAME= dbHelper.getForumName(dbHelper.getReadableDatabase(), String.valueOf(p3+1));
		getActivity().setTitle(Constants.FORUMNAME);
	}

//	@Override
//	public void onNothingSelected(AdapterView<?> p1)
//	{
//		// TODO: Implement this method
//	}

//	@OverridegetActivity().setTitle(Constants.FORUMNAME);
private void LoadDialog(String caption)
	{
		pd = new ProgressDialog(getActivity());
		pd.setMessage(caption);
		pd.setCancelable(false);
		pd.show();
	}
   
	//******************************
	//
	// 		количество страниц
	//
	//******************************
	public void setPages(){
		btnLast.setText(String.valueOf(allPages));
		btnSelect.setText(pageSelector +"/"+ allPages);
	}
	
	//******************************
	//
	// 	  окошко выбора страницы
	//
	//******************************
	
//	@Override
	private void pageSelectorDialog() {
		LinearLayout viewdialog;	
		viewdialog = (LinearLayout)getActivity().getLayoutInflater().inflate(R.layout.pages_selector, null); 
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(viewdialog);
		builder.setCancelable(true);
		//	builder.show();
		final AlertDialog alert;
		alert=builder.show();

		TextView txtAllPages = (TextView)viewdialog.findViewById(R.id.txtAllPages);
		txtAllPages.setText(""+allPages);

		final TextView txtPage = (TextView)viewdialog.findViewById(R.id.txtPage);
		txtPage.setText(""+pageSelector);

		final DiscreteSeekBar sbSelector=(DiscreteSeekBar)viewdialog.findViewById(R.id.sbSelector);
		sbSelector.setMax(/*Integer.parseInt*/(allPages)-1);
		sbSelector.setProgress(pageSelector-1);
		
		sbSelector.setNumericTransformer(new DiscreteSeekBar.NumericTransformer(){

				@Override
				public int transform(int p1)
				{
					// TODO: Implement this method
					return p1+1;
				}

			
		});
		sbSelector.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener(){

				@Override
				public void onStartTrackingTouch(DiscreteSeekBar p1) {
					selected = false;
				}

				@Override
				public void onStopTrackingTouch(DiscreteSeekBar p1) {
					if (!selected) {
						selected = true; // страница выбрана
						alert.dismiss();
						pageSelector=sbSelector.getProgress()+1;
						start=ssylki.get(startPosition);
						Log.d(TAG, "onStopTrackingTouch: selected = " + selected);
						callAsynkTask();
					}
				}

				@Override
				public void onProgressChanged(DiscreteSeekBar p1, int p2, boolean p3) {
					// TODO: Implement this method
					txtPage.setText(String.valueOf(sbSelector.getProgress()+1));
				}
			});

	}

	@Override
	public void onResume()
	{
		// TODO: Implement this method
		super.onResume();
		
	//	listView1.addFooterView(footer); // футер теряется почему-то при возврате из темы
		Constants.WHATOPEN=0; // открыт форум
		Constants.SELECTEDITEM=R.id.forums;
		Constants.FABLOCK=false; // разблокируем FAB
		Log.i(TAG, "onResume: открыт форум:"+" FORUMNAME: "+ Constants.FORUMNAME);
		getActivity().setTitle(Constants.FORUMNAME);
	}
	
	
}

