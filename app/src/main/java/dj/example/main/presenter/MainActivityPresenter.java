package dj.example.main.presenter;

import android.text.TextUtils;

import com.androidquery.callback.AjaxStatus;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import dj.example.main.activities.BaseActivity;
import dj.example.main.activities.MainActivity;
import dj.example.main.adapters.GenericAdapter;
import dj.example.main.apicalls.MainActivityNetworkCalls;
import dj.example.main.model.HeaderThumbnailData;
import dj.example.main.model.responses.FrontCapApiResponse;
import dj.example.main.utils.NetworkResultValidator;
import dj.example.main.utils.RandomUtils;

/**
 * Created by CSC on 10/26/2018.
 */

public class MainActivityPresenter extends GenericPresenter{

    public void loadScreen(MainActivity activity){
        activity.startProgress();
        MainActivityNetworkCalls networkCalls = new MainActivityNetworkCalls();
        networkCalls.queryForFrontCapAPI(activity, this);
    }

    @Override
    public void serverCallEnds(final BaseActivity baseActivity, int id, String url, final Object json, final AjaxStatus status) {
        super.serverCallEnds(baseActivity, id, url, json, status);
        if (id == MainActivityNetworkCalls.FRONT_CAP_API_CALL) {
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        boolean success = NetworkResultValidator.getInstance().isResultOK((String) json, status,
                                baseActivity.getViewForLayoutAccess());
                        if (success) {
                            FrontCapApiResponse response = new Gson().fromJson((String) json, FrontCapApiResponse.class);
                            List<HeaderThumbnailData> dataList = new ArrayList<>();
                            for (FrontCapApiResponse.FrontCapData data: response.getContent()){
                                List<HeaderThumbnailData.ThumbnailData> thumbnailDataList = new ArrayList<>();
                                int type = RandomUtils.getViewTypeFC(data.sectionType);
                                if (type == GenericAdapter.HORIZONTAL_FREE_SCROLL){
                                    for (FrontCapApiResponse.FrontCapData.Products products: data.getProducts()){
                                        HeaderThumbnailData.ThumbnailData thumbnailData = new HeaderThumbnailData.ThumbnailData(type, products.imageURL,
                                                products.name, products.price, products.type);
                                        thumbnailDataList.add(thumbnailData);
                                    }
                                }else if (type == GenericAdapter.BANNER){
                                    HeaderThumbnailData.ThumbnailData thumbnailData = new HeaderThumbnailData.ThumbnailData(type, data.bannerImage);
                                    thumbnailDataList.add(thumbnailData);
                                }else if (type == GenericAdapter.SPLIT_BANNER){
                                    HeaderThumbnailData.ThumbnailData thumbnailData1 = new HeaderThumbnailData.ThumbnailData(type, data.firstImage);
                                    HeaderThumbnailData.ThumbnailData thumbnailData2 = new HeaderThumbnailData.ThumbnailData(type, data.firstImage);
                                    thumbnailDataList.add(thumbnailData1);
                                    thumbnailDataList.add(thumbnailData2);
                                }
                                if (TextUtils.isEmpty(data.name))
                                    data.name = "";
                                dataList.add(new HeaderThumbnailData(data.name, thumbnailDataList, type));
                            }
                            baseActivity.stopProgress();
                            baseActivity.onSuccessAPICall(dataList);
                            return;
                        }
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                    baseActivity.stopProgress();
                }
            };
            new Thread(runnable).start();
        }

    }
}
