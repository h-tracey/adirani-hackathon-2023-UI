package com.example.hackathon.utility;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class FragmentUtility {
    private static String checkFragmentTag;

    public static boolean isFragmentExist(FragmentActivity activity, int viewID) {
        return activity == null || activity.isFinishing() || activity.isDestroyed() || activity.getFragmentManager() == null || isFragmentExist(getFragment(activity, viewID));
    }


    public static boolean isFragmentExist(FragmentActivity activity, String tag) {
        return activity == null || activity.isFinishing() || activity.isDestroyed() || activity.getFragmentManager() == null || isFragmentExist(getFragment(activity, tag));
    }

    private static boolean isFragmentExist(Fragment fragment) {
        try {
            return fragment != null;
        } catch (IllegalStateException e) {
            return true;
        }
    }

    private static Fragment getFragment(FragmentActivity activity, String tag) {
        return activity.getSupportFragmentManager().findFragmentByTag(tag);
    }

    private static Fragment getFragment(FragmentActivity activity, int viewID) {
        return activity.getSupportFragmentManager().findFragmentById(viewID);
    }

    public static void showDialog(FragmentActivity activity, String tag, DialogFragment dialog, boolean show) {
        if (activity == null || activity.isFinishing() || activity.isDestroyed() || activity.getSupportFragmentManager() == null || dialog == null)
            return;

        //dialog.isRemoving()
        if (show && !dialog.isAdded() && !dialog.isVisible()) {
            dialog.show(activity.getSupportFragmentManager(), tag);
        } else if (dialog.isVisible() && !show) {
            dialog.dismissAllowingStateLoss();
        }
    }

    public static void addFragment(FragmentActivity activity, int viewID, Fragment fragment, String tag,
                                   Bundle bundle) {

        addFragment(activity, viewID, fragment, tag, bundle, false);
    }

    public static void addFragment(FragmentActivity activity, int viewID, Fragment fragment, String tag,
                                   Bundle bundle, boolean addToBackStack) {
        if (activity == null || activity.isFinishing() || activity.isDestroyed() || activity.getFragmentManager() == null || fragment == null)
            return;

        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();

        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        fragmentTransaction.add(viewID, fragment, tag);

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(tag);
        }

        fragmentTransaction.commitAllowingStateLoss();
    }

    public static void replaceFragment(FragmentActivity activity, int viewID, Fragment fragment,
                                       String tag, Bundle bundle, boolean addToBackStack) {
        if (activity == null || activity.isFinishing() || activity.isDestroyed() || activity.getFragmentManager() == null || fragment == null)
            return;
        try {
            FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();

            if (bundle != null) {
                fragment.setArguments(bundle);
            }

            fragmentTransaction.replace(viewID, fragment, tag);

            if (addToBackStack) {
                fragmentTransaction.addToBackStack(tag);
            }

            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            String s = e.getMessage();
        }
    }

    public static void removeFragment(FragmentActivity activity, Fragment fragment, Intent intent) {
        if (activity == null || activity.isFinishing() || activity.isDestroyed() || activity.getFragmentManager() == null)
            return;

        if (intent != null) {
            fragment.setArguments(intent.getExtras());
        }

        activity.getSupportFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
    }

    /* 隱藏FragmentManager內所有fragment*/
    public static void hideAllFragment(FragmentActivity activity, List<WeakReference<Fragment>> fragList) {
        if (activity == null || activity.isFinishing() || activity.isDestroyed() || activity.getFragmentManager() == null)
            return;

        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();

        for (Fragment fragment : getActiveFragments(fragList)) {
            fragmentTransaction.hide(fragment).commitAllowingStateLoss();
        }
    }

    private static List<Fragment> getActiveFragments(List<WeakReference<Fragment>> fragList) {
        ArrayList<Fragment> ret = new ArrayList<Fragment>();
        for (WeakReference<Fragment> ref : fragList) {
            Fragment f = ref.get();
            if (f != null) {
                if (f.isVisible()) {
                    ret.add(f);
                }
            }
        }
        return ret;
    }

    public static void addOrShowFragment(FragmentActivity activity, int container, Fragment fragment, String tag) {
        if (activity == null || activity.isFinishing() || activity.isDestroyed() || activity.getFragmentManager() == null || tag.equals(getCheckFragmentTag()))
            return;

        Fragment fragmentByTag = getFragment(activity, tag);
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();

        setCheckFragmentTag(tag);
        if (fragmentByTag == null || !fragment.isAdded() && !fragment.isInLayout()) {

            fragmentTransaction
                    .remove(fragment)
                    .commitAllowingStateLoss();

            activity.getSupportFragmentManager().beginTransaction()
                    .add(container, fragment, tag)
                    .commitAllowingStateLoss();
        } else {
            fragmentTransaction
                    .show(fragmentByTag)
                    .commitAllowingStateLoss();
        }
    }

    private static String getCheckFragmentTag() {
        return checkFragmentTag;
    }

    private static void setCheckFragmentTag(String checkFragmentTag) {
        FragmentUtility.checkFragmentTag = checkFragmentTag;
    }

    public static void hideFragment(FragmentActivity activity, String tag) {
        setCheckFragmentTag("");
        if (activity == null || activity.isFinishing() || activity.isDestroyed() || activity.getFragmentManager() == null || tag.isEmpty())
            return;
        Fragment fragment = getFragment(activity, tag);

        if (fragment != null) {
            activity.getSupportFragmentManager().beginTransaction().hide(fragment).commitAllowingStateLoss();
        }
    }
}
