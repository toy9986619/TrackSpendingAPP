package com.example.tsaujt.finalproject;

import java.lang.reflect.Field;
import java.util.Hashtable;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

/**
 * Created by TsauJT on 2017/5/30.
 */

public class TypeFaceUtil  {
    public static void overrideFont(Context context, String defaultFontNameToOverride, String customFontFileNameInAssets) {
        try {
            final Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(), customFontFileNameInAssets);

            final Field defaultFontTypefaceField = Typeface.class.getDeclaredField(defaultFontNameToOverride);
            defaultFontTypefaceField.setAccessible(true);
            defaultFontTypefaceField.set(null, customFontTypeface);
        } catch (Exception e) {
            //Log.e("Can not set custom font " + customFontFileNameInAssets + " instead of " + defaultFontNameToOverride);
        }
    }


}
