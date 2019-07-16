package demo.ricelee.letterbar;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;

import ricelee.ui.letterbar.LetterBar;
import ricelee.ui.letterbar.PopupBehavior;
import ricelee.ui.letterbar.SingleLetterPopup;
import ricelee.ui.letterbar.utils.TextViewBuilder;
import ricelee.ui.letterbar.utils.Utils;
import ricelee.ui.letterbar.utils.ViewBackgroundBuilder;

public class AllLetterActivity extends AppCompatActivity {
    LetterBar letterBar4;
    private ConstraintLayout layout;


    int dp_10 = (int) Utils.dp2px(10f);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_letter);
        letterBar4 = findViewById(R.id.bar4);
        layout = findViewById(R.id.layout);

    }

    public void clickEvent(View view) {
        int[] viewLocation = Utils.getViewLocation(letterBar4);
        switch (view.getId()) {
            case R.id.btn_add_first:
                letterBar4.addFirstDrawable(R.drawable.icon_search);
                break;
            case R.id.btn_add_last:
                letterBar4.addLastDrawable(R.drawable.icon_search);
                break;
            case R.id.btn_add_string:
                letterBar4.addFirstString("热").refresh();
                break;
            case R.id.btn_add_char:
//                letterBar4.addAutoCharLetter(new CharLetter.ComparableCharLetter('A'));
                break;
            case R.id.btn_center:
                letterBar4.setLetterTouchListener(new SingleLetterPopup().setDuration(2000)
                        .setTextViewBuilder(new TextViewBuilder()
                                .setBackgroundDrawable(new ViewBackgroundBuilder().setRadius(dp_10, dp_10, dp_10, dp_10)
                                        .setShape(GradientDrawable.RECTANGLE)
                                        .setSolidColor(Color.YELLOW).buildDrawable())
                                .setPadding(dp_10, dp_10, dp_10, dp_10)
                                .setTextSize(16f).setTextColor(Color.RED))
                        .setPopupBehavior(PopupBehavior.createCenterBehavior())
                        .createPopup());
                break;
            case R.id.btn_stable_1:
                letterBar4.setLetterTouchListener(
                        new SingleLetterPopup().setTextViewBuilder(new TextViewBuilder()
                                .setBackgroundDrawable(new ViewBackgroundBuilder().setRadius(dp_10, dp_10, dp_10, dp_10)
                                        .setShape(GradientDrawable.RECTANGLE)
                                        .setSolidColor(Color.YELLOW).buildDrawable())
                                .setPadding(dp_10, dp_10, dp_10, dp_10)
                                .setTextSize(16f).setTextColor(Color.RED))
                                .setPopupBehavior(PopupBehavior.createStableBehavior(layout, Gravity.START | Gravity.TOP, 0, 0))
                                .createPopup());

                break;
            case R.id.btn_stable_2:
                letterBar4.setLetterTouchListener(
                        new SingleLetterPopup().setTextViewBuilder(new TextViewBuilder()
                                .setBackgroundDrawable(new ViewBackgroundBuilder().setRadius(dp_10, dp_10, dp_10, dp_10)
                                        .setShape(GradientDrawable.RECTANGLE)
                                        .setSolidColor(Color.YELLOW).buildDrawable())
                                .setPadding(dp_10, dp_10, dp_10, dp_10)
                                .setTextSize(16f).setTextColor(Color.RED))
                                .setPopupBehavior(PopupBehavior.createStableBehavior(letterBar4, Gravity.NO_GRAVITY, (int) (viewLocation[0] - Utils.dp2px(100)), viewLocation[1]))
                                .createPopup());
                break;
            case R.id.btn_stable_3:
                letterBar4.setLetterTouchListener(
                        new SingleLetterPopup()
                                .setTextViewBuilder(new TextViewBuilder().setBackgroundDrawable(new ViewBackgroundBuilder().setRadius(dp_10, dp_10, dp_10, dp_10)
                                        .setShape(GradientDrawable.RECTANGLE)
                                        .setSolidColor(Color.YELLOW).buildDrawable())
                                        .setPadding(dp_10, dp_10, dp_10, dp_10)
                                        .setTextSize(16f).setTextColor(Color.RED))
                                .setPopupBehavior(PopupBehavior.createStableBehavior(layout, Gravity.BOTTOM | Gravity.LEFT, 0, (int) Utils.dp2px(100)))
                                .createPopup());
                break;
            case R.id.btn_followY:
                letterBar4.setLetterTouchListener(

                        new SingleLetterPopup().setTextViewBuilder(new TextViewBuilder()
                                .setBackgroundDrawable(new ViewBackgroundBuilder().setRadius(dp_10, dp_10, dp_10, dp_10)
                                        .setShape(GradientDrawable.RECTANGLE)
                                        .setSolidColor(Color.YELLOW).buildDrawable())
                                .setPadding(dp_10, dp_10, dp_10, dp_10)
                                .setTextSize(16f).setTextColor(Color.RED))
                                .setPopupBehavior(PopupBehavior.createFollowYBehavior((int) (viewLocation[0] - Utils.dp2px(100))))
                                .createPopup());
                break;
            case R.id.btn_center_item:
                letterBar4.setLetterTouchListener(
                        new SingleLetterPopup().setTextViewBuilder(new TextViewBuilder()
                                .setBackgroundDrawable(new ViewBackgroundBuilder().setRadius(dp_10, dp_10, dp_10, dp_10)
                                        .setShape(GradientDrawable.RECTANGLE)
                                        .setSolidColor(Color.YELLOW).buildDrawable())
                                .setPadding(dp_10, dp_10, dp_10, dp_10)
                                .setTextSize(16f).setTextColor(Color.RED))
                                .setPopupBehavior(PopupBehavior.createCenterItemBehavior((int) (viewLocation[0] - Utils.dp2px(100))))
                                .createPopup());
                break;
        }
    }
}
