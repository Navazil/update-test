package app.sys.userInfo.structs;

/**
 * Created by weishicong on 2016/1/19.
 */
public class SimUserInfo {
    private int userId;
    private String userName;
    private String nickName;
    private String imagePath;
    private String phoneNum;
    private byte gender;
    private String signature;

    private byte is_shared;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public byte getGender() {
        return gender;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public byte getIs_shared() {
        return is_shared;
    }

    public void setIs_shared(byte is_shared) {
        this.is_shared = is_shared;
    }
}
