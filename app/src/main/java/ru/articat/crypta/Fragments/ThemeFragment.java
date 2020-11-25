package ru.articat.crypta.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.widgets.SnackBar;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.ArrayList;
import java.util.Date;

import ru.articat.crypta.Adapters.ThemeAdapter;
import ru.articat.crypta.Interfaces.ThemeInterface;
import ru.articat.crypta.MainActivity;
import ru.articat.crypta.Models.ThemeHeader;
import ru.articat.crypta.Models.ThemeModel;
import ru.articat.crypta.Parsing.ThemeParsing;
import ru.articat.crypta.R;
import ru.articat.crypta.Settings.Constants;
import ru.articat.crypta.Util.Content;
import ru.articat.crypta.Util.TypeFaceUtil;



//import ru.articat.nicy.Settings.DoPreferences;
//import ru.articat.nicy.Settings.ThemesChanger;

//import android.widget.SeekBar.*;
//import com.birin.easylistviewadapters.EasyListAdapter;
//import com.birin.easylistviewadapters.ListRowViewSetter;
//import com.birin.easylistviewadapters.Row;
//import com.birin.easylistviewadapters.utils.ChildViewsClickHandler;
//

public class ThemeFragment extends Fragment implements OnClickListener, OnScrollListener, ThemeInterface {

    private String TAG = Constants.TAG;

    @Override
    public void themeDataCallback(ArrayList<ThemeModel> t, ThemeHeader th, int p) //ArrayList<ThemeModel> tema, ArrayList<ThemeHeader> themeHeader)
    {
        Log.i(TAG, "themeDataCallback");
        // TODO: Implement this method
        this.themeHeader = th;
        this.pages = p;

        btnLast.setText("" + pages);
        btnSelect.setText(pageSelector + "/" + pages);

        checkHeaderState();
        if (lv_tema.getHeaderViewsCount() == 0) {
            Log.i(TAG, "lv_tema.getHeaderViewsCount() == 0");
            header = createHeader();
            lv_tema.addHeaderView(header);
        }
        adapter = new ThemeAdapter(getActivity(), t, pages);
        lv_tema.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        FLAG = true;
        long lEndTime = new Date().getTime();

        long difference = lEndTime - lStartTime;
        if (sharedPrefs.getBoolean("key_snack", false)) {
            new SnackBar(getActivity(), "Loading time: " + difference / 1000.0 + " mls.").show();
        }
    }


    //	Context context;
    private int mLastFirstVisibleItem;
    private boolean FLAG;
    private View view;
    private View header;//, savedHeader;//, createdView;
    //	TemaAdapter adapter;
    private ThemeAdapter adapter;
    private ButtonFlat btnSelect;
    private ButtonFlat btnLast;
    private String start;
    private String link;
    private int pages;
    private int pageSelector = 1;
    private boolean selected = false;
    private ListView lv_tema;
    //	View v;
    private Resources res;
    //	int THEMES, /*darkTextColor,*/ lightTextColor, secondaryColor, primaryColor;
//	boolean isHeadShowing=true;
    private SharedPreferences sharedPrefs;
    private Vibrator vb;
    //	LinearLayout layoutForAdd;
    private ExpandableLinearLayout layoutForAdd;
    private int sel;
    private int primaryColor;
    private ThemeHeader themeHeader = new ThemeHeader();
    private ViewGroup parent;
    private long lStartTime;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TypeFaceUtil.overrideFont(getActivity(), "SERIF", "fonts/Roboto-Condensed.ttf");
        sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
//		THEMES = Integer.parseInt(sharedPrefs.getString("select_themes_list", "1"));
//
//		ThemesChanger.onActivityCreateSetTheme(getActivity(), THEMES);

        view = inflater.inflate(R.layout.fragment_tema,
                container, false);
        setHasOptionsMenu(true);// отобразить свой actionbar
        setRetainInstance(true);

        this.parent = container;

        Log.i(TAG, "ThemeFragment. onCreateView");
        Bundle bundle = getArguments();
        if (bundle != null) {
            link = bundle.getString("link", "null");

//			String ans=bundle.getString("answers", "1");
//			Log.i(TAG, "answers " + ans);
            //	pages=(int)(Math.ceil(Double.parseDouble(ans)/25));
            //	Log.i(TAG, "pages "+pages);
            //	Log.i(TAG, "Math "+Math.ceil(Integer.parseInt(ans)/25));
//            link = link.trim();
//            link = link.substring(0, link.indexOf("page"));
            //	Log.i("mplog", "reKey= "+reKey+" reValue=  "+reValue);
            //	Log.i(TAG, "Forums logCookies= "+ loginCookies);
        }

        vb = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getActivity().getTheme();
        theme.resolveAttribute(android.R.attr.textColorPrimary, typedValue, true);
        TypedArray arr =
                getActivity().obtainStyledAttributes(typedValue.data, new int[]{
                        android.R.attr.textColorPrimary});
        primaryColor = arr.getColor(0, -1);

        // смотрим в настройках показывать или нет шапку темы
        sel = Integer.parseInt(sharedPrefs.getString("key_header", "2"));
        header = View.inflate(getActivity(), R.layout.tema_header, null);
        layoutForAdd = (ExpandableLinearLayout) header.findViewById(R.id.llForAdd);
        //	DoPreferences doPref=new DoPreferences(getActivity());
        //	String value=doPref.loadData(Constants.COOKIESVALUE, null);
        //	loginCookies.put("PHPSESSID", value);

        //	Constants.WHATOPEN=1; // открыта тема
        //	Log.i(TAG, "onCreayeView: открыта тема");
        res = getActivity().getResources();
        //	footer=(LinearLayout)view.findViewById(R.id.footer);
        //	footer=createFooter();

        ButtonFlat btnFirst = (ButtonFlat) view.findViewById(R.id.btnFirst);
        ButtonFlat btnPre = (ButtonFlat) view.findViewById(R.id.btnPre);
        btnSelect = (ButtonFlat) view.findViewById(R.id.btnSelect);
        ButtonFlat btnNext = (ButtonFlat) view.findViewById(R.id.btnNext);
        btnLast = (ButtonFlat) view.findViewById(R.id.btnLast);

        btnFirst.setOnClickListener(this);
        btnPre.setOnClickListener(this);
        btnSelect.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnLast.setOnClickListener(this);

        btnLast.setText("" + pages);
        btnSelect.setText(pageSelector + "/" + pages);


        // стартовая ссылка
        start = link + "/page/" + pageSelector;
        Log.i(TAG, "StartLink: " + start);
        Constants.LINKTOTHEME = start;
//		imgGetter = new ImageGetter(getActivity());
        //	adapter = new ThemeAdapter(getActivity(), tema, pages, secondaryColor, /*darkTextColor,*/ lightTextColor);
        lv_tema = (ListView) view.findViewById(R.id.lv_tema);
        lv_tema.setOnScrollListener(this);
        // Attach the adapter to a ListView
        //  lv_tema.setDivider(null);
        //  lv_tema.addFooterView(footer);

        if (FLAG) {
            Log.i(TAG, "Flag true");
            header = createHeader();
            lv_tema.addHeaderView(header);
            lv_tema.setAdapter(adapter);
        } else {
            Log.i(TAG, "Flag false");
            //	NewTema newTema=new NewTema();
            //  newTema.execute();
            startParsing(start);
        }

//		imageLoader = ImageLoader.getInstance(); // Получили экземпляр
//		imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity())); // Проинициализировали конфигом по умолчанию


//		picOptions = new DisplayImageOptions.Builder()
//			.showImageOnLoading(R.drawable.loading)
//			.showImageForEmptyUri(R.drawable.no_img)
//			.showImageOnFail(R.drawable.no_img)
//			.cacheInMemory(true)
//			.cacheOnDisk(true)
//			.considerExifParams(true)
//			.bitmapConfig(Bitmap.Config.RGB_565)
//			.build();

        //  loadData(); // загружаем сохранения


        return view;
    }

    // создание Footer
//    View createFooter() {
//		footer = v.inflate(getActivity(), R.layout.forum_footer, null);
//		return footer;
//    }    
//	
//	
    // actionbar для этого фрагмента
//	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//		inflater.inflate(R.menu.fragment_forums, menu);
//		super.onCreateOptionsMenu(menu, inflater);
//
//		MenuItem item = menu.findItem(R.id.user_nick);
//		item.setTitle(userNick);
//	}


    // ScrollListener
    @Override
    public void onScrollStateChanged(AbsListView p1, int p2) {
        // TODO: Implement this method
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // TODO: Implement this method
        if (mLastFirstVisibleItem < firstVisibleItem) {
            // SCROLLING DOWN
            ((MainActivity) getActivity()).fabHide(true);
        }
        if (mLastFirstVisibleItem > firstVisibleItem) {
            // SCROLLING UP
            ((MainActivity) getActivity()).fabHide(false);
        }
        mLastFirstVisibleItem = firstVisibleItem;

    }

    /** кнопочки навигации */

    @Override
    public void onClick(View p1) {
        //	NewTema newTema=new NewTema();
        switch (p1.getId()) {
            case R.id.btnFirst:
                if (pageSelector != 1) {
                    pageSelector = 1;
                    start = link + "/page/1";
                    Constants.LINKTOTHEME = start;
                    //	newTema.execute();
                    startParsing(start);
                } else {
                    Toast toast = Toast.makeText(getActivity(), "Уже первая", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;

            case R.id.btnPre:
                if (pageSelector != 1) {
                    pageSelector = pageSelector - 1;
                    start = link + "/page/" + pageSelector;
                    Constants.LINKTOTHEME = start;
                    //	newTema.execute();
                    startParsing(start);
                }

                break;

            case R.id.btnSelect:
                pageSelectorDialog();
                break;

            case R.id.btnNext:
                if (pageSelector < pages) {
                    pageSelector = pageSelector + 1;
                    start = link + "/page/" + pageSelector;
                    Constants.LINKTOTHEME = start;
                    //	newTema.execute();
                    startParsing(start);
                }

                break;

            case R.id.btnLast:
                if (pageSelector != pages) {
                    pageSelector = pages;
                    start = link + "/page/" + pages;
                    Constants.LINKTOTHEME = start;
                    //	newTema.execute();
                    startParsing(start);
                } else {
                    Toast toast = Toast.makeText(getActivity(), "Уже последняя", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;

            case R.id.imgAnswer:
                showPopupMenu(view);
                break;
        }

    }


    /***************************************
    *									   *
    *          создание Header             *
    *									   *
    //*************************************/

    private View createHeader() {
        Log.i(TAG, "createHeader");
        //	videoLink = video;
        layoutForAdd.setOrientation(LinearLayout.VERTICAL);
        //	Log.i(TAG, "sel= "+sel+" pageSelector= "+pageSelector);
        //	checkHeaderState();

        TextView txtTextHeader = (TextView) header.findViewById(R.id.txtTextHeader);
        txtTextHeader.setText(themeHeader.getTitle());
        // сворачиваем или разворачиваем шапку темы
        txtTextHeader.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //	Log.i(TAG, "click");
                if (sharedPrefs.getBoolean("key_taktil", true)) {
                    vb.vibrate(res.getInteger(R.integer.taktil));
                }
                layoutForAdd.toggle();
            }
        });
        Constants.THEMETITLE = themeHeader.getTitle();
        getActivity().setTitle(Constants.THEMETITLE);
        // иконка меню для ответов и т.п. в шапке темы
        ImageView imgAnswer = (ImageView) header.findViewById(R.id.imgAnswer);
        imgAnswer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Your code that you want to execute on this button click
                showPopupMenu(v); // выводим меню под кнопкой меню в шапке
            }

        });

        // аватарка автора темы если пусто, то из drawable
        ImageView myImage = (ImageView) header.findViewById(R.id.ivHeaderAvatar);
        if (themeHeader.getAva() != "" && themeHeader.getAva().contains("https://")) {
            Glide
                    .with(getActivity())
                    .load(themeHeader.getAva())
                    .thumbnail(.1f)
                    .error(R.drawable.no_img)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(myImage);
        } else {
            /**
             * This library from: https://github.com/amulyakhare/TextDrawable
             * This light-weight library provides images with letter/text like the Gmail app.
             */
            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            // generate random color
//				int backColor = generator.getRandomColor();
            // generate color based on a key (same key returns the same color)
            int backColor = generator.getColor(themeHeader.getNick());
            TextDrawable drawable = TextDrawable.builder()
                    .buildRect(themeHeader.getNick().substring(0, 1), backColor);

//				ImageView image = (ImageView) findViewById(R.id.image_view);
            myImage.setImageDrawable(drawable);
//            myImage.setImageResource(R.drawable.no_img);
        }

        TextView txtHeaderNick = (TextView) header.findViewById(R.id.txtHeaderNick);
        TextView txtHeaderCity = (TextView) header.findViewById(R.id.txtHeaderCity);
        //	Log.w(TAG, "themeHeader.getCity()= "+themeHeader.getCity());

        txtHeaderCity.setText("");
        txtHeaderNick.setTextColor(primaryColor);


        txtHeaderNick.setText(themeHeader.getNick().trim());
        final TextView txtHeadAdd = (TextView) header.findViewById(R.id.txtHeadAdd);
        txtHeadAdd.setText(themeHeader.getAddDate().replace("Добавлено: ", "").trim());
//		txtHeaderNick.setAlpha(.87f);
//		txtHeaderCity.setAlpha(.87f);
//		txtHeadAdd.setAlpha(.87f);

        Content content = new Content(getActivity(), parent);
        layoutForAdd.addView(content.getLayout(themeHeader.getText()));

        return header;
    }


    /***************************************
    *									   *
    *              popupmenu               *
    *									   *
    ***************************************/

    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), v);
        popupMenu.inflate(R.menu.popup); // Для Android 4.0

        popupMenu
                .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.menu1: // просмотр профиля

                                Toast.makeText(getContext(),
                                        "Доступно в следующих версиях",
                                        Toast.LENGTH_SHORT).show();

                                return true;

                            case R.id.menu2: // добавить в друзья
                                Toast.makeText(getActivity(),
                                        "Доступно в следующих версиях",
                                        Toast.LENGTH_SHORT).show();
                                return true;

                            case R.id.menu3: // послать сообщение
                                Toast.makeText(getContext(),
                                        "Доступно в следующих версиях",
                                        Toast.LENGTH_SHORT).show();
                                return true;

//						case R.id.menu4: // цитировать
//							Toast.makeText(getActivity(),
//										   "Доступно в следующих версиях",
//										   Toast.LENGTH_SHORT).show();
//										   
//										return true;

                            case R.id.menu4: // ответить на тему
//							
                                Toast.makeText(getContext(),
                                        "Доступно в следующих версиях",
                                        Toast.LENGTH_SHORT).show();

                                return true;

                            default:
                                return false;
                        }
                    }
                });


        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {

            @Override
            public void onDismiss(PopupMenu menu) {
                // TODO Auto-generated method stub
                //	Toast.makeText(getActivity(), "onDismiss", Toast.LENGTH_SHORT).show();
            }
        });
        popupMenu.show();
    }

    //***************************************
    //*										*
    //*  сообщение отправлено обновляемся   *
    //*										*
    //***************************************

//	@Override
//	public void MessageWasSend()
//	{
//		FLAG = false;
//	}

    /***************************************
    *									   *
    *  диалоговое окошко выбора страницы   *
    *									   *
    ***************************************/

    private void pageSelectorDialog() {
        LinearLayout viewdialog;
        viewdialog = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.pages_selector, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(viewdialog);
        builder.setCancelable(true);
        //	builder.show();
        final AlertDialog alert;
        alert = builder.show();

        TextView txtAllPages = (TextView) viewdialog.findViewById(R.id.txtAllPages);
        txtAllPages.setText("" + pages);

        final TextView txtPage = (TextView) viewdialog.findViewById(R.id.txtPage);
        txtPage.setText("" + pageSelector);

        final DiscreteSeekBar sbSelector = (DiscreteSeekBar) viewdialog.findViewById(R.id.sbSelector);
        sbSelector.setMax(pages - 1);
        sbSelector.setProgress(pageSelector - 1);
        sbSelector.setNumericTransformer(new DiscreteSeekBar.NumericTransformer() {

            @Override
            public int transform(int p1) {
                // TODO: Implement this method
                return p1 + 1;
            }


        });
        sbSelector.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar p1) {
                selected = false;
            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar p1) {
                if (!selected) {
                    selected = true; // страница выбрана
                    alert.dismiss();
                    pageSelector = sbSelector.getProgress() + 1;
                    btnSelect.setText(pageSelector + "/" + pages);
                    start = link + "/page/" + pageSelector;
                    //	NewTema newTema=new NewTema();
                    Constants.LINKTOTHEME = start;
                    //	newTema.execute();
                    startParsing(start);
                }
            }

            @Override
            public void onProgressChanged(DiscreteSeekBar p1, int p2, boolean p3) {
                // TODO: Implement this method
                txtPage.setText(String.valueOf(sbSelector.getProgress() + 1));
            }
        });

    }

    @Override
    public void onResume() {
        // TODO: Implement this method
        super.onResume();
        Constants.WHATOPEN = 1; // открыта тема
        Constants.FABLOCK = false;
        Log.i(TAG, "onResume: открыта тема");

//		if(savedHeader!=null) {
//			Log.d(TAG, "not_null");
//			lv_tema.removeHeaderView(header);
//			lv_tema.addHeaderView(savedHeader);
//		}
//		else Log.i(TAG, "null");
        //	Log.i(TAG, "headerTitle= "+ hTit);
        if (Constants.JUSTSENDTOTHEME) {
            start = link + "/page/" + pages;
            Constants.LINKTOTHEME = start;
            startParsing(start);
            //	scrollMyListViewToBottom();
            Constants.JUSTSENDTOTHEME = false;
        }
    }

//	private void scrollMyListViewToBottom()
//	{
//		lv_tema.post(new Runnable() {
//				@Override
//				public void run()
//				{
//					// Select the last row so it will scroll into view...
//					lv_tema.setSelection(adapter.getCount() - 1);
//				}
//			});
//	}

    // состояние header
    private void checkHeaderState() {
        switch (sel) {
            case 1: // всегда показывать
                if (!layoutForAdd.isExpanded()) layoutForAdd.expand();
                break;

            case 2: // только на первой

                if (pageSelector == 1) {
                    if (!layoutForAdd.isExpanded()) layoutForAdd.expand();
                } else {
                    if (layoutForAdd.isExpanded()) layoutForAdd.collapse();
                }
                break;

            case 3: // всегда скрывать

                if (layoutForAdd.isExpanded()) layoutForAdd.collapse();
                break;
        }
    }

    private void startParsing(String s) {
        lStartTime = new Date().getTime(); // start time

        new ThemeParsing(getActivity(), this).doParse(s);
    }

    @Override
    public void onPause() {
        Log.d(TAG, "ThemeFragment.onPause");
        // TODO: Implement this method
        super.onPause();
    }


}


