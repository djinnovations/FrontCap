package dj.example.main.presenter;

import android.util.Log;

import com.androidquery.callback.AjaxStatus;

import dj.example.main.activities.BaseActivity;

/**
 * Created by CSC on 10/26/2018.
 */

public class GenericPresenter {

    private String TAG = "GenericPresenter";

    public void serverCallEnds(BaseActivity baseActivity, int id, String url, Object json, AjaxStatus status) {
        Log.d(TAG, "url queried-" + TAG + ": " + url);
        Log.d(TAG, "response-" + TAG + ": " + json);

    }
}
