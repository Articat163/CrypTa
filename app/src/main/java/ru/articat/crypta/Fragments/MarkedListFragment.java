package ru.articat.crypta.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import ru.articat.crypta.DataBases.DBHelperMarkedList;
import ru.articat.crypta.MainActivity;
import ru.articat.crypta.R;
import ru.articat.crypta.Settings.Constants;
import ru.articat.crypta.Util.TypeFaceUtil;

import static ru.articat.crypta.Settings.Constants.TAG;

/** Сохраняем темы в Избранное. */

public class MarkedListFragment extends Fragment // implements LoaderCallbacks<Cursor>
{


	private DBHelperMarkedList dbHelper;


	@Override
	public View onCreateView(LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState)
	{
		TypeFaceUtil.overrideFont(getActivity(), "SERIF", "fonts/Roboto-Condensed.ttf");
		View view = inflater.inflate(R.layout.fragment_marked_list,
									 container, false);
		setHasOptionsMenu(true); // отобразить свой actionbar



		ListView lvData = (ListView) view.findViewById(R.id.fragmentmarkedlistListView);
		dbHelper=new DBHelperMarkedList(getActivity());
		//	dbHelper.open(dbHelper.getReadableDatabase());

		// формируем столбцы сопоставления
		String[] from = new String[] {DBHelperMarkedList.SECTION, DBHelperMarkedList.TITLE, DBHelperMarkedList.TEXT, DBHelperMarkedList.NICK, DBHelperMarkedList.DATE, DBHelperMarkedList.USER, DBHelperMarkedList.UDATE, DBHelperMarkedList.UTIME, DBHelperMarkedList.CITY, DBHelperMarkedList.ANSWERS, DBHelperMarkedList.VIEWS};
		int[] to = new int[] { R.id.markitemTextViewSection, R.id.markitemtxtTitle, R.id.markitemtxtText,
			R.id.markitemtxtNick, R.id.markitemtxtDate, R.id.markitemtxtLastUser, R.id.markitemtxtLastDate, R.id.markitemtxtLastTime, R.id.markitemtxtCity,
			R.id.markitemtxtAnswers, R.id.markitemtxtViews};

		// получаем курсор
		Cursor cursor = dbHelper.getAllData(dbHelper.getReadableDatabase());
		getActivity().startManagingCursor(cursor);

		// создаем адаптер и настраиваем список
		SimpleCursorAdapter scAdapter = new SimpleCursorAdapter(getActivity(), R.layout.mark_item, cursor, from, to, 0);
		lvData.setAdapter(scAdapter);

		lvData.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,
										int position, long id) {
					//	Log.d("mplog", "itemClick: position = " + position + ", id = "
					//		  + id);
					Cursor cur = (Cursor) parent.getAdapter().getItem(position);
					ThemeFragment yfc = new ThemeFragment();
					Bundle bundle = new Bundle();

					bundle.putString("link", cur.getString(2));
					Log.i(TAG, "MarkedList.answers= "+cur.getString(2));
					if(cur.getString(2).equals("0")) bundle.putString("answers", "2");
					else bundle.putString("answers", cur.getString(1));
					yfc.setArguments(bundle);
					
					((MainActivity)getActivity()).FragmentChanger(yfc);
//					android.app.FragmentManager fragmentManager = getFragmentManager();
//					fragmentManager
//						.beginTransaction()
//						.addToBackStack(null)
//						.replace(R.id.fragment_place,  yfc)
//						.commit();

					//	}
				}
			});

		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// закрываем подключение при выходе
		dbHelper.close();
	}

	@Override
	public void onResume(){
		super.onResume();
		getActivity().setTitle(getString(R.string.favorites));
		Constants.WHATOPEN=3;
		Constants.SELECTEDITEM=R.id.favorites;
	}

}
