package com.onemeter.omm.onemm;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.onemeter.omm.onemm.data.NetWorkResultType;
import com.onemeter.omm.onemm.data.SettingSave;
import com.onemeter.omm.onemm.manager.NetworkManager;
import com.onemeter.omm.onemm.manager.NetworkRequest;
import com.onemeter.omm.onemm.request.ChargeMoneyRequest;

/**
 * Created by Tacademy on 2016-09-12.
 */
public class SettingDialog extends DialogFragment {

    SettingSave settingSave;
    Button btn;
    TextView price;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.view_setting_dial, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        builder.setView(inflater.inflate(R.layout.view_setting_dial, null));

        builder.setView(view);
        builder.setTitle(null).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String sumMoney = price.getText().toString();
                ChargeMoneyRequest request = new ChargeMoneyRequest(getContext(), sumMoney);
                NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP, request, new NetworkManager.OnResultListener<NetWorkResultType>() {
                    @Override
                    public void onSuccess(NetworkRequest<NetWorkResultType> request, NetWorkResultType result) {
                        Toast.makeText(MyApplication.getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(NetworkRequest<NetWorkResultType> request, int errorCode, String errorMessage, Throwable e) {

                    }
                });
            }
        });

        builder.setMessage("충전할 금액을 설정하세요");

        price = (TextView)view.findViewById(R.id.text_price);
        btn = (Button) view.findViewById(R.id.btn_1000);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPrice += 1000;
                price.setText(""+currentPrice);
            }
        });

        btn = (Button) view.findViewById(R.id.btn_5000);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPrice += 5000;
                price.setText(""+currentPrice);
            }
        });

        btn = (Button) view.findViewById(R.id.btn_10000);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPrice += 10000;
                price.setText(""+currentPrice);
            }
        });

        btn = (Button) view.findViewById(R.id.btn_reset);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPrice = 0;
                price.setText(""+currentPrice);
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
