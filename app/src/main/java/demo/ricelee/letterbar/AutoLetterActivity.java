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
import java.util.Random;

import ricelee.ui.letterbar.LetterBar;

public class AutoLetterActivity extends AppCompatActivity {
    private LetterBar letterBar;
    private RecyclerView recyclerView;
    List<RecyclerBean> recyclerBeans = new ArrayList<>();
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
        letterBar.addAutoCharLetter(recyclerBeans);
    }

    public void clickEvent(View view) {
        if (view.getId() == R.id.btn_add) {
            addChar(allSurnames);
        } else if (view.getId() == R.id.btn_add_other) {
            addChar(otherSurnames);
        }
    }

    private void addChar(String[] names) {
        String name = getName(names);
        RecyclerBean recyclerBean = transferData(name);
        myAdapter.addData(recyclerBean);
        addTv.setText(String.format("生成的姓名%s,%s", name, recyclerBean.getCharLetter()));
        letterBar.addAutoCharLetter(recyclerBean);
    }

    private RecyclerBean transferData(String name) {
        String[] split = name.split("-");
        MemberInfo memberInfo = new MemberInfo(split[1], TextUtils.equals("男", split[0]));
        return new RecyclerBean(memberInfo);
    }

    private List<RecyclerBean> initData() {
        List<RecyclerBean> recyclerBeanList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String name = getName(onlySurnames);
            String[] split = name.split("-");
            MemberInfo memberInfo = new MemberInfo(split[1], TextUtils.equals("男", split[0]));
            RecyclerBean recyclerBean = new RecyclerBean(memberInfo);
            recyclerBeanList.add(recyclerBean);
        }
        return recyclerBeanList;
    }

    private static String[] allSurnames = {"赵", "钱", "孙", "李", "周", "吴", "郑", "王", "冯", "陈", "褚", "卫", "蒋", "沈", "韩", "杨", "朱", "秦", "尤", "许",
            "何", "吕", "施", "张", "孔", "曹", "严", "华", "金", "魏", "陶", "姜", "戚", "谢", "邹", "喻", "柏", "水", "窦", "章", "云", "苏", "潘", "葛", "奚", "范", "彭", "郎",
            "鲁", "韦", "昌", "马", "苗", "凤", "花", "方", "俞", "任", "袁", "柳", "酆", "鲍", "史", "唐", "费", "廉", "岑", "薛", "雷", "贺", "倪", "汤", "滕", "殷",
            "罗", "毕", "郝", "邬", "安", "常", "乐", "于", "时", "傅", "皮", "卞", "齐", "康", "伍", "余", "元", "卜", "顾", "孟", "平", "黄", "和",
            "穆", "萧", "尹", "姚", "邵", "湛", "汪", "祁", "毛", "禹", "狄", "米", "贝", "明", "臧", "计", "伏", "成", "戴", "谈", "宋", "茅", "庞", "熊", "纪", "舒",
            "屈", "项", "祝", "董", "梁", "杜", "阮", "蓝", "闵", "席", "季"};

    private static String[] otherSurnames = {"$", "#", "*"};
    private static String[] generateSurnames = {"赵", "钱", "孙", "李", "周", "吴", "郑", "王", "明", "方", "许"};

    private static String[] onlySurnames = {"董", "孙",""};

    public String getName(String[] Surname) {
        Random random = new Random();
        String girl = "秀娟英华慧巧美娜静淑惠珠翠雅芝玉萍红娥玲芬芳燕彩春菊兰凤洁梅琳素云莲真环雪荣爱妹霞香月莺媛艳瑞凡佳嘉琼勤珍贞莉桂娣叶璧璐娅琦晶妍茜秋珊莎锦黛青倩婷姣婉娴瑾颖露瑶怡婵雁蓓纨仪荷丹蓉眉君琴蕊薇菁梦岚苑婕馨瑗琰韵融园艺咏卿聪澜纯毓悦昭冰爽琬茗羽希宁欣飘育滢馥筠柔竹霭凝晓欢霄枫芸菲寒伊亚宜可姬舒影荔枝思丽 ";
        String boy = "伟刚勇毅俊峰强军平保东文辉力明永健世广志义兴良海山仁波宁贵福生龙元全国胜学祥才发武新利清飞彬富顺信子杰涛昌成康星光天达安岩中茂进林有坚和彪博诚先敬震振壮会思群豪心邦承乐绍功松善厚庆磊民友裕河哲江超浩亮政谦亨奇固之轮翰朗伯宏言若鸣朋斌梁栋维启克伦翔旭鹏泽晨辰士以建家致树炎德行时泰盛雄琛钧冠策腾楠榕风航弘";
        int index = random.nextInt(Surname.length - 1);
        String name = Surname[index]; //获得一个随机的姓氏
        int i = random.nextInt(3);//可以根据这个数设置产生的男女比例
        if (i == 2) {
            int j = random.nextInt(girl.length() - 2);
            if (j % 2 == 0) {
                name = "女-" + name + girl.substring(j, j + 2);
            } else {
                name = "女-" + name + girl.substring(j, j + 1);
            }

        } else {
            int j = random.nextInt(girl.length() - 2);
            if (j % 2 == 0) {
                name = "男-" + name + boy.substring(j, j + 2);
            } else {
                name = "男-" + name + boy.substring(j, j + 1);
            }

        }
        return name;
    }

}
