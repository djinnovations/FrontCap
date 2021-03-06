package dj.example.main.adapters;

import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dj.example.main.R;
import dj.example.main.model.HeaderThumbnailData;
import dj.example.main.uiutils.ResourceReader;
import dj.example.main.utils.IDUtils;

/**
 * Created by User on 02-02-2018.
 */

public class ThumbnailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements GenericAdapterInterface {


    public static final int PORTRAIT = IDUtils.generateViewId();
    public static final int LANDSCAPE = IDUtils.generateViewId();

    //public static final int HORIZONTAL_FREE_SCROLL = IDUtils.generateViewId();


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getRootLayout(viewType), parent, false);
        return getViewHolder(view, viewType);
    }

    public ThumbnailAdapter(){

    }

    List<HeaderThumbnailData.ThumbnailData> dataList = new ArrayList<>();

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BaseItemHolder) holder).onItemViewUpdate(dataList.get(position), holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void changeData(List dataList) throws Exception {
        if (dataList == null)
            return;
        if (dataList.size() <= 0)
            return;
        if (!(dataList.get(0) instanceof HeaderThumbnailData.ThumbnailData))
            throw new IllegalArgumentException("Required data type \"HeaderThumbnailData.ThumbnailData\"");
        this.dataList.clear();
        this.dataList.addAll(dataList);
        (new Handler(Looper.getMainLooper())).postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        }, 100);
    }

    @Override
    public int getRootLayout(int viewType) {
        if (viewType == PORTRAIT)
            return R.layout.viewholder_thumbnail;
        if(viewType == LANDSCAPE)
            return R.layout.viewholder_wide_thumbnail;
        if (viewType == GenericAdapter.HORIZONTAL_FREE_SCROLL)
            return R.layout.viewholder_horizontal_free_scroll;
        if (viewType == GenericAdapter.SPLIT_BANNER || viewType == GenericAdapter.BANNER)
            return R.layout.viewholder_banner;
        /*if (viewType == GenericAdapter.BANNER)
            return R.layout.viewholder_banner;*/

        return 0;
    }

    @Override
    public void setOnClickListener(RecyclerView.ViewHolder holder) {
        ((ThumbnailViewHolder) holder).ivThumbnail.setOnClickListener((View.OnClickListener) holder);
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View view, int viewType) {
        return new ThumbnailViewHolder(view);
    }


    class ThumbnailViewHolder extends BaseItemHolder implements View.OnClickListener{

        @BindView(R.id.ivThumbnail)
        ImageView ivThumbnail;
        @Nullable
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @Nullable
        @BindView(R.id.tvTitle1)
        TextView tvTitle1;
        @Nullable
        @BindView(R.id.tvTitle2)
        TextView tvTitle2;

        public ThumbnailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            EventBus.getDefault().post(dataList.get(getAdapterPosition()));
        }

        @Override
        public void onItemViewUpdate(Object dataObject, RecyclerView.ViewHolder holder, int position) {
            HeaderThumbnailData.ThumbnailData data = (HeaderThumbnailData.ThumbnailData) dataObject;
            if (tvTitle != null){
                if (!TextUtils.isEmpty(data.getTitle())) {
                    tvTitle.setVisibility(View.VISIBLE);
                    tvTitle.setText(data.getTitle());
                }
                else tvTitle.setVisibility(View.GONE);
            }
            if (tvTitle1 != null){
                if (!TextUtils.isEmpty(data.getTitle1())) {
                    tvTitle1.setVisibility(View.VISIBLE);
                    tvTitle1.setText(data.getTitle1());
                }
                else tvTitle1.setVisibility(View.GONE);
            }
            if (tvTitle2 != null){
                if (!TextUtils.isEmpty(data.getTitle2())) {
                    tvTitle2.setVisibility(View.VISIBLE);
                    tvTitle2.setText(data.getTitle2());
                }
                else tvTitle2.setVisibility(View.GONE);
            }
            if (ThumbnailAdapter.this.getItemViewType(position) == LANDSCAPE){
                ivThumbnail.setImageDrawable(new ColorDrawable(ResourceReader
                        .getInstance().getColorFromResource(R.color.darkorange)));
                return;
            }
            if (!TextUtils.isEmpty(data.getThumbnailUrl())){
                Picasso.with(itemView.getContext())
                        .load(data.getThumbnailUrl())
                        /*.placeholder(R.drawable.vector_icon_profile_white_outline)*/
                        .into(ivThumbnail);
            }
        }
    }
}
