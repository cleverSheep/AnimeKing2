package com.murrayde.retrofittesting.util;

import android.content.Context;
import android.widget.ImageView;

import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.murrayde.retrofittesting.R;

public class ImageUtil {

    private static void loadImage(ImageView imageView, String url, CircularProgressDrawable circularProgressDrawable) {
        RequestOptions options = new RequestOptions()
                .placeholder(circularProgressDrawable)
                .transform(new CenterCrop(), new RoundedCorners(16))
                .error(R.mipmap.ic_launcher_round);
        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(options)
                .load(url)
                .into(imageView);
    }

    private static CircularProgressDrawable getProgressDrawable(Context context) {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(10f);
        circularProgressDrawable.setCenterRadius(50f);
        circularProgressDrawable.start();
        return circularProgressDrawable;
    }

    public static void loadImage(ImageView view, String url) {
        loadImage(view, url, getProgressDrawable(view.getContext()));
    }


}
