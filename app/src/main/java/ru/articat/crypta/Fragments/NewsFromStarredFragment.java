package ru.articat.crypta.Fragments;

import androidx.core.app.*;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import ru.articat.crypta.R;

/**
 * Created by Юрий on 25.06.2017.
 */

public class NewsFromStarredFragment extends Fragment {
    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";


    public static NewsFromStarredFragment newInstance() {
        NewsFromStarredFragment fragmentNewsFromStarred = new NewsFromStarredFragment();

        return fragmentNewsFromStarred;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_from_starred, null);

        return view;
    }
}
