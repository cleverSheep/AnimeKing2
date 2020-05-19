package com.murrayde.animekingtrivia.util;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.murrayde.animekingtrivia.R;

public class QuestionUtil {
    public static final Long QUESTION_LIMIT = 10L;
    public static final Long QUESTION_COUNT_MIN = 7L;
    public static Long QUESTION_TIMER = 17000L;

    public static void setUpQuestionWithImage(ImageView image, String url, TextView questionView, String question) {
        Glide.with(image.getContext())
                .load(url)
                .placeholder(R.drawable.castle)
                .dontAnimate()
                .transform(new CenterCrop(), new RoundedCorners(16))
                .into(image);
        questionView.setText(question);
    }
}
