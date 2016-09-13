package com.onemeter.omm.onemm.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onemeter.omm.onemm.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingAgreeFragment extends Fragment {

    @BindView(R.id.text_box1)
    TextView editUse;
    @BindView(R.id.text_box2)
    TextView editInfo;

    public SettingAgreeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting_agree, container, false);
        ButterKnife.bind(this,view);

        editInfo.setMovementMethod(new ScrollingMovementMethod());
        editUse.setMovementMethod(new ScrollingMovementMethod());

        editInfo.setText(readText2());
        editUse.setText(readText1());

        return view;
    }

    private String readText2() {
        String data = null;
        InputStream inputStream = getResources().openRawResource(R.raw.text_2);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int i;
        try {
            i = inputStream.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }

            data = new String(byteArrayOutputStream.toByteArray(),"MS949");
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private String readText1() {
        String data = null;
        InputStream inputStream = getResources().openRawResource(R.raw.text_1);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int i;
        try {
            i = inputStream.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }

            data = new String(byteArrayOutputStream.toByteArray(),"MS949");
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }


    @OnClick(R.id.btn_back)
    public void backClick(View view) {
        ((TabMyFragment) (getParentFragment())).popFragment();
    }
}
