package com.example.flipimage;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class SampleActivity extends AppCompatActivity {

    LinearLayout mainLayout;
    CardView cardView;

    boolean isExanded = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sample);
        mainLayout = (LinearLayout) findViewById(R.id.mainLyout);
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , 300);

        cardView = new CardView(this);
        cardView.setLayoutParams(cardParams);

        cardView.setVisibility(View.GONE);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , 150);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , 150);
        params.leftMargin = 10;
        params.rightMargin = 10;
        params.topMargin = 10;
        params.bottomMargin = 10;


        params1.leftMargin = 10;
        params1.rightMargin = 10;
        params1.topMargin = 10;
        params1.bottomMargin = 10;

        LinearLayout layout = new LinearLayout(SampleActivity.this);
        layout.setPadding(0, 50 , 0 , 50);
        layout.setLayoutParams(params);
        layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.imageholder));


        LinearLayout layout1 = new LinearLayout(SampleActivity.this);
        layout1.setPadding(0, 10 , 0 , 10);
        layout1.setLayoutParams(params1);
        layout1.setBackgroundDrawable(getResources().getDrawable(R.drawable.imageholder));

        mainLayout.addView(layout);
        mainLayout.addView(cardView);
        mainLayout.addView(layout1);


        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isExanded) {

                    expand(cardView , 1000,  500);

                } else {

                    collapse(cardView , 1000 , 0);
                }


            }
        });

        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //expand(layout1);

                if (isExanded) {

                    collapse(cardView , 1000 ,200);


                }


            }
        });


    }



    public void expand(final View v, int duration, int targetHeight) {

        int prevHeight  = v.getHeight();

        v.setVisibility(View.VISIBLE);

        Animation a = new Animation()
        {


            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                final int newHeight = targetHeight;
                v.getLayoutParams().height = newHeight;
                v.requestLayout();
            }

            @Override
            public void initialize(int width, int height, int parentWidth, int parentHeight) {
                super.initialize(width, height, parentWidth, parentHeight);

            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration(duration);
        a.setInterpolator(new DecelerateInterpolator());
        v.startAnimation(a);
        isExanded = true;
//
    }

    public void collapse(final View v, int duration, int targetHeight) {
        int prevHeight  = v.getHeight();
        Animation a = new Animation()
        {
            //int initialHeight;

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                final int newHeight = targetHeight;
                v.getLayoutParams().height = newHeight;
                v.requestLayout();
            }

            @Override
            public void initialize(int width, int height, int parentWidth, int parentHeight) {
                super.initialize(width, height, parentWidth, parentHeight);

            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration(duration);
        a.setInterpolator(new DecelerateInterpolator());
        v.startAnimation(a);
        isExanded = false;

    }


}
