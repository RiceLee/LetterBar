package demo.ricelee.letterbar;

import android.graphics.Color;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ricelee.ui.letterbar.CharLetter;
import ricelee.ui.letterbar.ILetter;
import ricelee.ui.letterbar.LetterBar;
import ricelee.ui.letterbar.PopupBehavior;
import ricelee.ui.letterbar.SingleLetterPopup;
import ricelee.ui.letterbar.utils.TextViewBuilder;
import ricelee.ui.letterbar.utils.Utils;
import ricelee.ui.letterbar.utils.ViewBackgroundBuilder;

public class AllLetter2Activity extends AppCompatActivity {
    private LetterBar letterBar;
    private RecyclerView recyclerView;
    List<RecyclerBean> recyclerBeans = new ArrayList<>();

    MyAdapter myAdapter;
    int dp_10 = (int) ricelee.ui.letterbar.utils.Utils.dp2px(10f);

    private Drawable buildDrawable() {
        return new ViewBackgroundBuilder().setRadius(Utils.dp2px(60f))
                .setShape(GradientDrawable.RECTANGLE)
                .setSolidColor(Color.YELLOW).buildDrawable();
    }

    private Drawable buildWeChatDrawable() {
        ShapeDrawable shapeDrawable = new ShapeDrawable();
        Path path = new Path();
        path.setFillType(Path.FillType.WINDING);
//        path.addCircle(20f, 20f, 20f, Path.Direction.CCW);
        path.addArc(0f, 0f, 20f, 40f, -90f, 180f);
//        path.moveTo(20f, 0f);
//        path.quadTo(35f, 10f, 50f, 20f);
        path.close();
//        path.addRect(new RectF(20f, 0f, 50f, 20f), Path.Direction.CCW);
        //        path.addArc(new RectF(20f, 0f, 50f, 20f), 120f, 90f);
        PathShape pathShape = new PathShape(path, 50f, 40f);
        shapeDrawable.setShape(pathShape);
//        shapeDrawable.setPadding(dp_10, dp_10, dp_10, dp_10);
        return shapeDrawable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_letter2);
        letterBar = findViewById(R.id.letterBar);
        letterBar.setLetterTouchListener(new SingleLetterPopup().setDuration(2000)
                .setTextViewBuilder(new TextViewBuilder()
                        .setBackgroundDrawable(buildWeChatDrawable())
                        .setPadding(dp_10, dp_10, dp_10, dp_10)
                        .setTextSize(20f).setTextColor(Color.RED))
                .setPopupBehavior(PopupBehavior.createCenterBehavior())
                .createPopup());
        letterBar.addFirstDrawable(R.drawable.icon_search).refresh();
        recyclerView = findViewById(R.id.recyclerView);

        recyclerBeans = initData();
        Collections.sort(recyclerBeans);
        myAdapter = new MyAdapter(recyclerBeans);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
        recyclerView.setAdapter(myAdapter);

        View headView = getLayoutInflater().inflate(R.layout.recycler_header, (ViewGroup) recyclerView.getParent(), false);
        setViewClickListener(headView, R.id.tv_header1, v -> {
            ILetter letter = letterBar.getLetter(0);
            letter.setLetterTouchShow(!letter.isLetterTouchShow());
            Toast.makeText(this, "drawableLetter显示状态：" + letter.isLetterTouchShow(), Toast.LENGTH_SHORT).show();
        });
        setViewClickListener(headView, R.id.tv_header2, v -> {
            letterBar.setNoneShowLetter('A');
            Toast.makeText(this, "A显示状态：" + letterBar.getLetter(1).isLetterTouchShow(), Toast.LENGTH_SHORT).show();
        });

        myAdapter.addHeaderView(headView);
        letterBar.attachCharLetter(recyclerBeans);
        letterBar.setLetterScrollListener((letter, position) -> {
            if (letter instanceof CharLetter) {
                CharLetter charLetter = (CharLetter) letter;
                int i = calPosition(charLetter.getCharLetter());
                if (i != -1) {
                    recyclerView.scrollToPosition(i + 1);
                }
            } else {
                recyclerView.scrollToPosition(0);
            }
        });

    }

    private int calPosition(char ch) {
        for (int i = 0; i < recyclerBeans.size(); i++) {
            RecyclerBean recyclerBean = recyclerBeans.get(i);
            if (recyclerBean.getCharLetter() == ch) {
                return i;
            }
        }
        return -1;
    }

    private void setViewClickListener(View headView, @IdRes int id, View.OnClickListener clickListener) {
        headView.findViewById(id).setOnClickListener(clickListener);
    }

    private List<RecyclerBean> initData() {
        List<RecyclerBean> recyclerBeanList = new ArrayList<>();
        List<String> allNames = NameUtils.getAll();
        for (int i = 0; i < allNames.size(); i++) {
            String name = allNames.get(i);
            String[] split = name.split("-");
            MemberInfo memberInfo = new MemberInfo(split[1], TextUtils.equals("男", split[0]));
            RecyclerBean recyclerBean = new RecyclerBean(memberInfo);
            recyclerBeanList.add(recyclerBean);
        }
        return recyclerBeanList;
    }
}
