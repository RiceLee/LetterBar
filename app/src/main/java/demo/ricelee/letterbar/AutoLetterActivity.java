package demo.ricelee.letterbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ricelee.ui.letterbar.LetterBar;

public class AutoLetterActivity extends AppCompatActivity {
    private LetterBar letterBar;
    private RecyclerView recyclerView;
    private List<RecyclerBean> recyclerBeans = new ArrayList<>();
    private TextView addTv;

    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_letter);
        addTv = findViewById(R.id.tv_add);
        letterBar = findViewById(R.id.bar4);
        letterBar.addFirstDrawable(R.drawable.icon_search);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerBeans = initData();
        Collections.sort(recyclerBeans);
        myAdapter = new MyAdapter(recyclerBeans);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
        letterBar.addAutoSpecialLetter('#', 200)
                .addAutoSpecialLetter('$', 10);
        letterBar.attachCharLetter(recyclerBeans);
    }

    public void clickEvent(View view) {
        if (view.getId() == R.id.btn_add) {
            addChar(NameUtils.allSurnames);
        } else if (view.getId() == R.id.btn_add_other) {
            addChar(NameUtils.otherSurnames);
        }
    }

    private void addChar(String[] names) {
        String name = NameUtils.getName(names);
        RecyclerBean recyclerBean = transferData(name);
        myAdapter.addData(recyclerBean);
        addTv.setText(String.format("生成的姓名%s,%s", name, recyclerBean.getCharLetter()));
        letterBar.attachCharLetter(recyclerBean);
    }

    private RecyclerBean transferData(String name) {
        String[] split = name.split("-");
        MemberInfo memberInfo = new MemberInfo(split[1], TextUtils.equals("男", split[0]));
        return new RecyclerBean(memberInfo);
    }

    private List<RecyclerBean> initData() {
        List<RecyclerBean> recyclerBeanList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String name = NameUtils.getName(NameUtils.onlySurnames);
            String[] split = name.split("-");
            MemberInfo memberInfo = new MemberInfo(split[1], TextUtils.equals("男", split[0]));
            RecyclerBean recyclerBean = new RecyclerBean(memberInfo);
            recyclerBeanList.add(recyclerBean);
        }
        return recyclerBeanList;
    }


}
