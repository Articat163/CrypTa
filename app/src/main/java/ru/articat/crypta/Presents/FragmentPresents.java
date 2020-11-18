package ru.articat.crypta.Presents;

import android.os.*;
import android.support.v4.app.*;
import android.view.*;
import ru.articat.crypta.*;

public class FragmentPresents extends Fragment {

	static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";


	public static FragmentPresents newInstance() {
		FragmentPresents pageFragment = new FragmentPresents();
//		Bundle arguments = new Bundle();
//		arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
//		pageFragment.setArguments(arguments);
		return pageFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

			}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {


		return inflater.inflate(R.layout.fragment_presents, null);
	}
}
