package com.golubroman.golub.warehouse;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindDrawable;
import butterknife.ButterKnife;

/**
 * Created by User on 24.02.2017.
 */

public class PagerFragment extends Fragment {
    final static String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    private int pageNumber;
    private static List<Integer> imageList;
    private ImageView imageView;
    private ViewGroup rootView;
    private static PagerFragment pagerFragment;
    private static Bundle arguments;

    static PagerFragment onInstance(int position){
        pagerFragment = new PagerFragment();
        arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, position);
        pagerFragment.setArguments(arguments);
        return pagerFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Adding drawable resources
                to List<Integer> imageList
                for further displaying
            */

        imageList = new ArrayList<>();
        imageList.add(R.drawable.poster1);
        imageList.add(R.drawable.poster2);
        imageList.add(R.drawable.poster3);
        imageList.add(R.drawable.poster4);
        imageList.add(R.drawable.poster5);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup)inflater.inflate(R.layout.view_pager_fragment, container, false);

        /* Binding got view to
                activity MainActivity
            */

        ButterKnife.bind(this, rootView);
        imageView = ButterKnife.findById(rootView, R.id.image_view_pager);
        imageView.setImageResource(imageList.get(pageNumber));
        return rootView;
    }
}
