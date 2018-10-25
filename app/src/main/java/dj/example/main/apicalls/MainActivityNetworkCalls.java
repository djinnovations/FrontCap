package dj.example.main.apicalls;

import android.util.Log;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import dj.example.main.activities.BaseActivity;
import dj.example.main.presenter.GenericPresenter;
import dj.example.main.utils.IDUtils;

/**
 * Created by CSC on 10/26/2018.
 */

public class MainActivityNetworkCalls {

    private String TAG = "MainActivityNetworkCalls";

    public static int FRONT_CAP_API_CALL = IDUtils.generateViewId();

    public void queryForFrontCapAPI(final BaseActivity activity, final GenericPresenter presenter){
        AjaxCallback ajaxCallback = new AjaxCallback() {
            public void callback(String url, Object json, AjaxStatus status) {
                presenter.serverCallEnds(activity, FRONT_CAP_API_CALL, url, json, status);
            }
        };
        ajaxCallback.method(AQuery.METHOD_GET);
        String url = "https://api.myjson.com/bins/chou4";
        Log.d(TAG, "GET url- queryForFrontCapAPI()" + TAG + ": " + url);
        activity.getAQuery().ajax(url, String.class, ajaxCallback);
    }
}
