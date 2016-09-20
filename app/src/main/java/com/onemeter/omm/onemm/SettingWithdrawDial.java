package com.onemeter.omm.onemm;

import android.app.Dialog;
import android.content.DialogInterface;
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        builder.setTitle(null).setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String finalPoint = (String) price.getText();
                if (Integer.parseInt(finalPoint) >= 10000 && Integer.parseInt(money) >= Integer.parseInt(finalPoint)) {
                    Toast.makeText(getContext(), " 출금이 완료되었습니다 ", Toast.LENGTH_SHORT).show();
                    // 출금시 현재 보유금 차감
                } else {
                    Toast.makeText(getContext(), " 출금이 불가능 합니다", Toast.LENGTH_SHORT).show();

                }
            }
        });
        if (Integer.parseInt(money) >= 10000)
            builder.setMessage("출금 가능액 : " + money + "원");
        else
            builder.setMessage("출금은 만원이상 가능합니다.");

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
        return builder.create();
    }

    int currentPrice = 0;

    @Override
    public void onStop() {
        super.onStop();
    }
}
