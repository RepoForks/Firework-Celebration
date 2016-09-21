package io.intrepid.russell.fireworkcelebration;

import android.animation.TimeInterpolator;
import android.support.annotation.FloatRange;

/**
 * Android's default {@link android.view.animation.BounceInterpolator BounceInterpolator} is too bouncy!
 * This one only bounces once.
 * <p/>
 * The bounce occurs at the fraction of the total duration specified by the {@code bounceLocation} parameter
 * (default {@value DEFAULT_BOUNCE}), and the height is computed such that gravity is constant and the
 * final position is correct.
 */
public class SingleBounceInterpolator implements TimeInterpolator
{
    private static final float DEFAULT_BOUNCE = 0.6f;

    private final float a;
    private final float g;
    private final float h;

    public SingleBounceInterpolator()
    {
        this(DEFAULT_BOUNCE);
    }

    public SingleBounceInterpolator(@FloatRange(from = 0, to = 1) float bounceLocation)
    {
        a = bounceLocation;
        g = 2 / (a * a);
        h = (a - 1) * (a - 1) / (4 * a * a);
    }

    @Override
    public float getInterpolation(float t)
    {
        if (t < a)
        {
            return g / 2f * t * t;
        }
        else
        {
            float t2 = t - (a + 1) / 2f;
            return 1 - h + g / 2f * t2 * t2;
        }
    }
}
