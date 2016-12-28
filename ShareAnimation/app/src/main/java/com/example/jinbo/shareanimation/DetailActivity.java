package com.example.jinbo.shareanimation;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

public class DetailActivity extends AppCompatActivity {
    private LinearLayout mActivityDetail;
    private RelativeLayout mRlTop;
    private ImageView mIv;
    private ScrollView mRlBottom;
    private TextView mTv;
    Transition transition;
    private ImageView mIvWhole;
    private RelativeLayout mRlWhole;
    Transition enterTransition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transition = TransitionInflater.from(this).inflateTransition(R.transition.curved_motion);
        //transition.setInterpolator(new AccelerateDecelerateInterpolator());
        enterTransition = TransitionInflater.from(this).inflateTransition(R.transition.slide_bottom);
       getWindow().setEnterTransition(enterTransition);
        getWindow().setSharedElementEnterTransition(transition);

        Transition transition1 = TransitionInflater.from(this).inflateTransition(R.transition.curved_motion);
        getWindow().setSharedElementReturnTransition(transition1);
        // getWindow().setReturnTransition(enterTransition);
        setContentView(R.layout.activity_detail);
        mActivityDetail = (LinearLayout) findViewById(R.id.activity_detail);
        mRlTop = (RelativeLayout) findViewById(R.id.rl_top);
        mIv = (ImageView) findViewById(R.id.iv);
       // mRlBottom = (ScrollView) findViewById(R.id.rl_bottom);
        mTv = (TextView) findViewById(R.id.tv);
        mIvWhole = (ImageView) findViewById(R.id.iv_whole);
        mRlWhole = (RelativeLayout) findViewById(R.id.rl_whole);
        final int img = getIntent().getIntExtra("image", 0);
        Log.e("Transition", "start1 ");
        loadRoundImage(this, mIv, img);
        mIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideView(v, mIvWhole);
            }
        });
        mIvWhole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideView(mIv, mIvWhole);
            }
        });
        Glide.with(this).load(R.drawable.back).placeholder(R.color.colorAccent).into(mIvWhole);
        Log.e("Transition", "start2 ");
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                Log.e("Transition", "start ");
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                Log.e("Transition", "end ");
                //showView(mIv, mRlWhole, img);
                showView(mIv, mIvWhole, img);
               // mIv.setAlpha(0f);
            }

            @Override
            public void onTransitionCancel(Transition transition) {
                Log.e("Transition", "cancel ");
            }

            @Override
            public void onTransitionPause(Transition transition) {
                Log.e("Transition", "pause ");
            }

            @Override
            public void onTransitionResume(Transition transition) {
                Log.e("Transition", "onResume ");
            }
        });
    }

    public void showView(View view, final View finalView, int img) {
        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getTop() + view.getBottom()) / 2;
       /* int cx = finalView.getWidth()/2;
        int cy = finalView.getHeight() / 2;*/
        Log.e("viewPosition", "cx: " + cx + " cy: " + cy);
        int finalRadius = finalView.getWidth();
        int initRadius = view.getWidth() / 2;
        final Animator animator = ViewAnimationUtils.createCircularReveal(finalView, cx, cy, initRadius, finalRadius);
        animator.setDuration(500);

        finalView.setVisibility(View.VISIBLE);
       /* animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.e("Animator", "onStart ");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.e("Animator", "onEnd ");

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.e("Animator", "onCancel ");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.e("Animator", "onRepeat ");
            }
        });*/
        animator.start();
    }

    public void hideView(final View view, final View finalView) {
        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getTop() + view.getBottom()) / 2;
        final int finalRadius = finalView.getWidth();
        int initRadius = view.getWidth() / 2;
        Animator animator = ViewAnimationUtils.createCircularReveal(finalView, cx, cy, finalRadius, initRadius);
        animator.setDuration(500);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mIv.setAlpha(1f);
                finalView.setVisibility(View.INVISIBLE);
                finalView.setBackgroundColor(Color.alpha(0));
                back();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        // hideView(mIv,mRlWhole);
        back();
    }

    public void back() {
        super.onBackPressed();
        //finishAfterTransition();
    }

    public void loadRoundImage(Context context, final ImageView imageView, Object path) {
        Glide.with(context)
                .load(path)
                .asBitmap()
                .centerCrop()
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }


}
