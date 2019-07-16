package demo.ricelee.letterbar;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import ricelee.ui.letterbar.utils.Utils;

public class MainActivity extends AppCompatActivity {

    char a = '#';
    char b = '*';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_all).setOnClickListener(view ->
                startActivity(new Intent(this, AllLetterActivity.class)));
        findViewById(R.id.btn_auto).setOnClickListener(view ->
                startActivity(new Intent(this, AutoLetterActivity.class)));
        findViewById(R.id.btn_all_2).setOnClickListener(v ->
                startActivity(new Intent(this, AllLetter2Activity.class)));

        StringBuilder stringBuilder = new StringBuilder();
        for (char c = 'A'; c <= 'Z'; c++) {
            stringBuilder.append(String.format("[%s,%s]", c, (int) c)).append("\t");
        }
        stringBuilder.append((String.format("[%s,%s]", a, (int) a))).append("\t");
        stringBuilder.append((String.format("[%s,%s]", b, (int) b))).append("\t");

        Log.e(getClass().getSimpleName(), stringBuilder.toString());

//        Rect mRect = new Rect();
//
//        Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mPaint.setDither(false);
////        mPaint.setStrokeWidth(5);
//        mPaint.setTextSize(NameUtils.sp2px(20));
//
//        mPaint.setTextAlign(Paint.Align.CENTER);
//        int width = 0, height = 0;
//        for (char ch = 'A'; ch <= 'Z'; ch++) {
//            mPaint.getTextBounds(String.valueOf(ch), 0, 1, mRect);
//            width = Math.max(mRect.width(), width);
//            height = Math.max(mRect.height(), height);
//            Log.e(getClass().getSimpleName(), ch + "\trect:" + mRect.toString()
//                    + "\twidth:" + mRect.width() + "\theight:" + mRect.height());
//        }
//        Log.e(getClass().getSimpleName(), "\twidth:" + width + "\theight:" + height);
    }
}
