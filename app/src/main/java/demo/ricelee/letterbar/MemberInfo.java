package demo.ricelee.letterbar;

public class MemberInfo {
    String name;
    String nickName;
    boolean boy;

    public MemberInfo(String name, boolean boy) {
        this.name = name;
        this.boy = boy;
    }

    public MemberInfo(String name, String nickName) {
        this.name = name;
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public boolean isBoy() {
        return boy;
    }

    public void setBoy(boolean boy) {
        this.boy = boy;
    }
}
