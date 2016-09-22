package com.onemeter.omm.onemm;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Tacademy on 2016-09-13.
 */
public class SettingWithdrawDial extends DialogFragment {

    String money;
    Button btn;
    TextView pointView;
    TextView price;

    private static String MONEY = "money";

    public SettingWithdrawDial() {
        // Required empty public constructor
    }

    public static SettingWithdrawDial newInstance(String message) {
        SettingWithdrawDial fragment = new SettingWithdrawDial();
        Bundle args = new Bundle();
        args.putString(MONEY, message);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            money = getArguments().getString(MONEY);
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.view_setting_withdraw, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        pointView = (TextView) view.findViewById(R.id.text_money);
        if (Integer.parseInt(money) >= 10000)
            pointView.setText(money + "원");
        else
            pointView.setText("보유금 부족");

        pointView.setTextColor(Color.parseColor("#FA8072"));

        price = (TextView) view.findViewById(R.id.text_price);
        btn = (Button) view.findViewById(R.id.btn_1000);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPrice += 1000;
                price.setText("" + currentPrice);
            }
        });

        btn = (Button) view.findViewById(R.id.btn_5000);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPrice += 5000;
                price.setText("" + currentPrice);
            }
        });

        btn = (Button) view.findViewById(R.id.btn_10000);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPrice += 10000;
                price.setText("" + currentPrice);
            }
        });

        btn = (Button) view.findViewById(R.id.btn_reset);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPrice = 0;
                price.setText("" + currentPrice);
            }
        });

        btn = (Button) view.findViewById(R.id.btn_ok);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String finalPoint = (String) price.getText();
                if (Integer.parseInt(finalPoint) >= 10000 && Integer.parseInt(money) >= Integer.parseInt(finalPoint)) {
                    Toast.makeText(getContext(), " 출금이 완료 되었습니다. ", Toast.LENGTH_SHORT).show();
                    dismiss();
                } else {
                    Toast.makeText(getContext(), " 금액이 부족합니다. ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return builder.create();
    }

    int currentPrice = 0;

    @Override
    public void onStop() {
        super.onStop();
    }
}
