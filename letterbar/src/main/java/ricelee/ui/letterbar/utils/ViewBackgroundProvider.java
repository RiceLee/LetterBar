//package ricelee.ui.letterbar.utils;
//
//import android.graphics.drawable.GradientDrawable;
//import android.os.Build;
//import android.view.View;
//
//public class ViewBackgroundProvider {
//
//
//    public static void setViewBackground(View view, ViewBackgroundBuilder builder) {
//        GradientDrawable gradientDrawable = builder.buildDrawable();
//        setViewBackground(view, gradientDrawable);
//    }
//
//    public static void setViewBackground(View view, int fillType, int solidColor,
//                                         int strokeWidth, int strokeColor,
//                                         float radius,
//                                         float topLeftRadius, float topRightRadius,
//                                         float bottomLeftRadius, float bottomRightRadius) {
//        GradientDrawable gradientDrawable = new ViewBackgroundBuilder().setFillStyle(fillType)
//                .setSolidColor(solidColor)
//                .setStrokeWidth(strokeWidth)
//                .setStrokeColor(strokeColor)
//                .setRadius(radius)
//                .setRadius(topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius).buildDrawable();
//        setViewBackground(view, gradientDrawable);
//    }
//
//    public static void setViewBackground(View view, GradientDrawable gradientDrawable) {
//        gradientDrawable.setBounds(0, 0, view.getWidth(), view.getHeight());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            view.setBackground(gradientDrawable);
//        } else {
//            view.setBackgroundDrawable(gradientDrawable);
//        }
//    }
//
//}
