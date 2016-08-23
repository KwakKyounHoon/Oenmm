package com.onemeter.omm.onemm;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_MESSAGE = "param1";

    // TODO: Rename and change types of parameters
    private String message;


    public MessageFragment() {
        // Required empty public constructor
    }

    public static MessageFragment newInstance(String message) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            message = getArguments().getString(ARG_MESSAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        TextView tv = (TextView)view.findViewById(R.id.text_message);
        tv.setText(message);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("MessageFragment", "onResume : " + message);
        if (getUserVisibleHint()) {
            getActivity().setTitle(message);
        }
//        getActivity().setTitle(message);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i("MessageFragment", "setUserVisibleHint : " + message);
        if (getActivity() == null) return;
        if (isVisibleToUser) {
            getActivity().setTitle(message);
        }
    }
}
