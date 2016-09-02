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

        TextView userText;
        FlowLayout.LayoutParams params;
        params = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT, FlowLayout.LayoutParams.WRAP_CONTENT);

        for (int i = 0; i < 15; i++) {
            userText = new TextView(getContext());
            userText.setLayoutParams(params);
            userText.setText("  추천 스타  ");
            // userText.setId(); ID설정
            this.flowLayout.addView(userText);
        }
        ButterKnife.bind(this, view);
        return view;
    }



    @OnClick(R.id.btn_search_result)
    public void resultClick(View view) {
        ((TabSearchFragment) getParentFragment()).showSearchResult();
    }


}
