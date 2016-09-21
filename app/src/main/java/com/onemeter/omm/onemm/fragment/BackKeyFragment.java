package com.onemeter.omm.onemm.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by Tacademy on 2016-08-26.
 */
public class BackKeyFragment extends Fragment {

    public boolean onBackPressed() {
        FragmentManager fm = getChildFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
            return true;
        }
        return false;
    }

    public void popAll(){
        FragmentManager fm = getChildFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//            fm.popBackStack();
        }
    }
}
