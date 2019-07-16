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
        String s = Pinyin.toPinyin(memberInfo.name.charAt(0));
        Log.e(getClass().getSimpleName(), "toPinYin:" + s + "\tchar:" + s.charAt(0));
        return s.charAt(0);
    }

    @Override
    public int compareTo(RecyclerBean o) {
        String currentPinyin = Pinyin.toPinyin(memberInfo.name, "");
        String otherPinyin = Pinyin.toPinyin(o.memberInfo.name, "");
        if ((currentPinyin.charAt(0) == '#' || currentPinyin.charAt(0) == '$') &&
                (otherPinyin.charAt(0) != '#' && otherPinyin.charAt(0) != '$')) {
            return 1;
        } else if ((currentPinyin.charAt(0) != '#' && currentPinyin.charAt(0) != '$') &&
                (otherPinyin.charAt(0) == '#' || otherPinyin.charAt(0) == '$')) {
            return -1;
        } else if ((currentPinyin.charAt(0) == '#' || currentPinyin.charAt(0) == '$') &&
                (otherPinyin.charAt(0) == '#' || otherPinyin.charAt(0) == '$')) {
            return currentPinyin.compareTo(otherPinyin);
        }
        return currentPinyin.compareTo(otherPinyin);
    }

    @Override
    public String toString() {
        return "RecyclerBean{" +
                "memberInfo=" + memberInfo +
                ", firstName=" + firstName +
                '}';
    }
}
