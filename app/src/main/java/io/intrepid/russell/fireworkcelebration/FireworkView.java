package io.intrepid.russell.fireworkcelebration;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.StyleRes;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.view.ContextThemeWrapper;
import android.util.AttributeSet;
import android.widget.ImageView;

public class FireworkView extends ImageView
{
    private AnimatedVectorDrawableCompat drawable;
    private boolean isRunning;

    public FireworkView(Context context)
    {
        this(context, null);
    }

    public FireworkView(Context context, AttributeSet attrs)
    {
        this(context, attrs, R.style.Firework);
    }

    public FireworkView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        TypedArray ta = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.FireworkView, 0, R.style.Firework);
        @StyleRes int style = ta.getResourceId(R.styleable.FireworkView_firework_style, R.style.Firework);

        drawable = AnimatedVectorDrawableCompat.create(new ContextThemeWrapper(context, style), R.drawable.firework_animated);
        setImageDrawable(drawable);
    }

    public void startAnimation(int startMs, int intervalMs)
    {
        isRunning = true;
        runAnimation(startMs, intervalMs);
    }

    public void stopAnimation()
    {
        isRunning = false;
        drawable.stop();
    }

    private void runAnimation(int startMs, final int intervalMs)
    {
        postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if (!isRunning) return;

                drawable.start();
                runAnimation(intervalMs, intervalMs);
            }
        }, startMs);
    }
}
