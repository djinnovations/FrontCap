package dj.example.main.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dj.example.main.R;
import dj.example.main.fragments.MainFragment;
import dj.example.main.fragments.SingleMenuFragment;
import dj.example.main.model.HeaderThumbnailData;
import dj.example.main.presenter.MainActivityPresenter;
import dj.example.main.utils.MyPrefManager;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    /*@Override
    public ArrayList<Pair<Class, String>> getTabFragmentsList() {
        ArrayList<Pair<Class, String>> list = new ArrayList<>();
        list.add(new Pair<Class, String>(TabFragment1.class, "Tab - 1"));
        list.add(new Pair<Class, String>(TabFragment2.class, "Tab - 2"));
        return list;
    }*/

    @Override
    public ProgressBar getProgressBar() {
        return progressBar;
    }

    @Override
    public Activity getSelf() {
        return this;
    }

    @Override
    public View getViewForLayoutAccess() {
        return activity_main;
    }

    @BindView(R.id.mainHolder)
    FrameLayout activity_main;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;


    private SingleMenuFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fc);
        ButterKnife.bind(this);
        setToolBar();

        presenter = new MainActivityPresenter();
        //MyPrefManager.getInstance().updateSessionCounts();
        getSupportFragmentManager().beginTransaction().replace(activity_main.getId(), new MainFragment()).commit();
    }

    private void setToolBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Categories");
    }

    @Override
    protected void onGarbageCollection() {
        mainFragment = null;
        presenter = null;
    }

    MainActivityPresenter presenter;

    public void initiateCall(SingleMenuFragment fragment){
        mainFragment = fragment;
        presenter.loadScreen(this);
    }

    @Override
    public void onSuccessAPICall(final Object object) {
        //super.onSuccessAPICall(object);
        Runnable runnable = new Runnable() {
            public void run() {
                if (object instanceof List){
                    Object temp = ((List) object).get(0);
                    if (temp instanceof HeaderThumbnailData){
                        List<HeaderThumbnailData> dataList = (List<HeaderThumbnailData>) object;
                        mainFragment.changeData(dataList);
                    }
                }
            }
        };
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    /*@Override
    protected void onGarbageCollection() {
        activePageData = null;
        history = null;
        activePage = null;
    }*/

    /*@Override
    protected NavigationDataObject getPrimaryNavigationObj() {
        return MyApplication.getInstance().getNavigationObj(R.id.nav_home);
    }*/
}
