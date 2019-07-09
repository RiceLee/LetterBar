package demo.ricelee.letterbar;

import android.util.Log;

import com.github.promeg.pinyinhelper.Pinyin;

import ricelee.ui.letterbar.CharLetter;

public class RecyclerBean implements CharLetter, Comparable<RecyclerBean> {

    MemberInfo memberInfo;
    Character firstName;

    public RecyclerBean(MemberInfo memberInfo) {
        this.memberInfo = memberInfo;
    }

    public MemberInfo getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(MemberInfo memberInfo) {
        this.memberInfo = memberInfo;
    }

    public Character getFirstName() {
        return Character.toUpperCase(memberInfo.getName().charAt(0));
    }

    @Override
    public char getCharLetter() {
//        if (Pinyin.isChinese(memberInfo.name.charAt(0))) {
//
//        } else {
//            String s = Pinyin.toPinyin(memberInfo.name.charAt(0));
//            Log.e(getClass().getSimpleName(), "toPinYin:" + s + "\tchar:" + s.charAt(0));
//            return s.charAt(0);
//        }
        String s = Pinyin.toPinyin(memberInfo.name.charAt(0));
        Log.e(getClass().getSimpleName(), "toPinYin:" + s + "\tchar:" + s.charAt(0));
        return s.charAt(0);
    }

    @Override
    public int compareTo(RecyclerBean o) {
        return Pinyin.toPinyin(memberInfo.name, "").compareTo(Pinyin.toPinyin(o.memberInfo.name, ""));
    }
}
