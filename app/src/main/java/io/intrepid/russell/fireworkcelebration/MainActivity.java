package io.intrepid.russell.fireworkcelebration;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
{
    private static final int MEDAL_DELAY = 200;
    private static final int MEDAL_DURATION = 2500;
    private static final int FIREWORK_INTERVAL = 500;

    @BindViews({R.id.firework1, R.id.firework2, R.id.firework3, R.id.firework4, R.id.firework5, R.id.firework6, R.id.firework7})
    List<FireworkView> fireworkViews;

    @BindView(R.id.celebration_root)
    View root;
    @BindView(R.id.celebration_medal)
    ImageView medalView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setResult(RESULT_OK, getIntent());

        medalView.setVisibility(View.INVISIBLE);

        ValueAnimator translation = buildMedalTranslationAnim();
        ValueAnimator rotation = buildMedalRotationAnim();

        translation.start();
        rotation.start();
    }

    @Override
    public void onStart()
    {
        super.onStart();

        int interval = fireworkViews.size() * FIREWORK_INTERVAL;
        int delay = 0;
        for (FireworkView fireworkView : fireworkViews)
        {
            fireworkView.startAnimation(delay, interval);
            delay += FIREWORK_INTERVAL;
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        for (FireworkView fireworkView : fireworkViews)
        {
            fireworkView.stopAnimation();
        }
    }

    private ValueAnimator buildMedalTranslationAnim()
    {
        ValueAnimator translation = ValueAnimator.ofFloat(0, 1);
        translation.setDuration(MEDAL_DURATION);
        translation.setStartDelay(MEDAL_DELAY);
        translation.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {
                medalView.setVisibility(View.VISIBLE);
            }
        });
        translation.setInterpolator(new SingleBounceInterpolator());
        translation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                float fraction = animation.getAnimatedFraction();
                medalView.setTranslationY((fraction - 1) * root.getHeight());
            }
        });
        return translation;
    }

    private ValueAnimator buildMedalRotationAnim()
    {
        ValueAnimator rotation = ValueAnimator.ofFloat(-1, 1);
        rotation.setDuration(MEDAL_DURATION);
        rotation.setStartDelay(MEDAL_DELAY);
        rotation.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {
                medalView.setVisibility(View.VISIBLE);
            }
        });
        rotation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                float fraction = animation.getAnimatedFraction();
                medalView.setScaleX(getMedalWidth(fraction));
            }
        });
        return rotation;
    }

    private static float percentDistance(float min, float max, @FloatRange(from = 0, to = 1) float t)
    {
        float out = (t - min) / (max - min);
        return (t < min ? 0 : t > max ? 1 : out);
    }

    private static float getMedalWidth(@FloatRange(from = 0, to = 1) float t)
    {
        if (t < 0) return 0;
        else if (t < 0.2f) return percentDistance(0, 0.2f, t);
        else if (t < 0.6f) return 1 - 2 * percentDistance(0.2f, 0.6f, t);
        else if (t < 1.0f) return -1 + 2 * percentDistance(0.6f, 1.0f, t);
        else return 1;
    }
}
