package com.example.mealmonkey.Recelerview.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.mealmonkey.Data.Model.Banner;
import com.example.mealmonkey.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BannerAdapter extends PagerAdapter {

    private List<Banner> bannerList;
    private Context context;

    public BannerAdapter(List<Banner> bannerList, Context context) {
        this.bannerList = bannerList;
        this.context = context;
    }

    @Override
    public int getCount() {
        if(bannerList == null){
            return 0;
        } else {
            return bannerList.size();
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.bannr_layout, container, false);

        ImageView imageView = view.findViewById(R.id.BannerImageID);

       Picasso.get().load(bannerList.get(position).getFoodImage()).into(imageView);

        container.addView(view);
        return view;
    };
}
