package mdl.sinlov.android.helper.viewutils;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

/**
 * <pre>
 *     sinlov
 *
 *     /\__/\
 *    /`    '\
 *  ≈≈≈ 0  0 ≈≈≈ Hello world!
 *    \  --  /
 *   /        \
 *  /          \
 * |            |
 *  \  ||  ||  /
 *   \_oo__oo_/≡≡≡≡≡≡≡≡o
 *
 * </pre>
 * Created by sinlov on 16/11/28.
 */
public class SpannableTextSetter {
    private static ForegroundColorSpan foregroundColorSpan;

    /**
     * draw spannable TextView at Inclusive
     *
     * @param tv    {@link TextView}
     * @param text  {@link String}
     * @param start int
     * @param end   int
     * @param color {@link android.graphics.Color#RED} or other
     * @param flags {@link Spannable#SPAN_EXCLUSIVE_INCLUSIVE} or other
     */
    public static void drawSpannableTextViewInclusive(TextView tv, String text, int start, int end, int flags, int color) {
        SpannableStringBuilder spannableString = new SpannableStringBuilder(text);
        if (null == foregroundColorSpan) {
            foregroundColorSpan = new ForegroundColorSpan(color);
        }
        spannableString.setSpan(foregroundColorSpan, start, end, flags);
        tv.setText(spannableString);
    }

    private SpannableTextSetter() {
    }
}
