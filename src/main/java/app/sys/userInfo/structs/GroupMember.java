package app.sys.userInfo.structs;

/**
 * Created by weishicong on 2016/1/19.
 */
public class GroupMember {
    private int userId;
    private String nickName;
    private String signature;
    private String avatar;
    private byte gender;
    private byte is_shared;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public byte getGender() {
        return gender;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }

    public byte getIs_shared() {
        return is_shared;
    }

    public void setIs_shared(byte is_shared) {
        this.is_shared = is_shared;
    }

}
