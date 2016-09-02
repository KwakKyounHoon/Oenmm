package com.onemeter.omm.onemm.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onemeter.omm.onemm.R;
import com.wefika.flowlayout.FlowLayout;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    public SearchFragment() {
        // Required empty public constructor
    }

    private int id = 300;
    FlowLayout flowLayout;
    TextView userText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        flowLayout = (FlowLayout) view.findViewById(R.id.flowlayout);

        TextView btn;
        TextView params;
        btn = new TextView(getContext());

        btn.setText("asdfasdf");
        this.flowLayout.addView(btn);

//        params = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.MATCH_PARENT, FlowLayout.LayoutParams.MATCH_PARENT);
//        btn.setLayoutParams(params);

        btn = new TextView(getContext());
        btn.setText("1234");
        this.flowLayout.addView(btn);
//        params = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.MATCH_PARENT, FlowLayout.LayoutParams.wr);
//        btn.setLayoutParams(params);

        ButterKnife.bind(this, view);
        return view;
    }


//
//    @OnClick(R.id.btn_search_result)
//    public void resultClick(View view) {
//        ((TabSearchFragment) getParentFragment()).showSearchResult();
//    }


}
