package app.sys.userInfo.action;

import app.common.action.GenericActionSupport;
import app.sys.userInfo.model.UserEntity;
import app.sys.userInfo.service.UserService;
import app.sys.userInfo.structs.SimUserInfo;
import app.sys.userInfo.structs.UserInfo;
import app.util.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by weishicong on 2016/1/13.
 */
public class userMgrAction extends GenericActionSupport {
    private int userId;
    private int targetUserId;
    private String userName;
    private String password;
    private String newPassword;
    private String nickName;
    private String avatar;
    private String tel;
    private String signature;
    private byte gender;
    private byte enable_share;
    private byte status;
    private String sessionID=super.getRequest().getSession().getId();
    private File image;
    private String imageFileName;

    private FileOutputStream fos;

    public FileOutputStream getFos()
    {
        try {
            fos=new FileOutputStream(new File("F:\\Images for Firefox\\35921781.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return this.fos;
    }


    @Autowired
    UserService userService;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }
    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }

    public byte getEnable_share() {
        return enable_share;
    }

    public void setEnable_share(byte enable_share) {
        this.enable_share = enable_share;
    }

    public byte getStatus() {
        return status;
    }

    public String getSessionID() {
        return sessionID;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public int getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(int targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    @Override
    public String execute() throws Exception
    {
        return SUCCESS;
    }

    public String register()
    {
        if(userName==null||password==null||nickName==null)
        {
            super.writeJson(generateReturnData("status",100));
            return ERROR;
        }
        UserEntity userInfo=new UserEntity();
        userInfo.setUsername(userName);
        userInfo.setPassword(password);
        userInfo.setNickname(nickName);
        userInfo.setEnableShare(enable_share);
        userInfo.setAvatar(avatar);
        userInfo.setTel(tel);
        userInfo.setGender(gender);
        userInfo.setSignature(signature);
        if(userService.checkUserExist(userName))
        {
            super.writeJson(generateReturnData("status",101));
            return ERROR;
        }
        if(userService.saveUserEntity(userInfo)) {
            int userId=userService.getIdByName(userName);
            super.writeJson(generateReturnData("status&userId",200,userId));
            return SUCCESS;
        } else {
            super.writeJson(generateReturnData("status",100));
            return ERROR;
        }
    }

    public String updateUserInfo()
    {

        UserEntity userInfo;
        userInfo=userService.queryUserById(userId);
        userInfo.setNickname(nickName);
        userInfo.setTel(tel);
        userInfo.setGender(gender);
        userInfo.setSignature(signature);
        userInfo.setEnableShare(enable_share);
        if(password!=null&&password!=""&&userService.checkPassword(userId,password)&&checkPasswordAvailable(newPassword))
        {
            userInfo.setPassword(newPassword);
        }
        userService.upDateUserInfo(userInfo);               //使用服务更新用户信息
        super.writeJson(generateReturnData("status", 200));
        return SUCCESS;
    }

    public String querySimpleUserInfo()
    {
        /*if(userId!=targetUserId&&(userService.queryUserById(targetUserId).getEnableShare()==0))
        {
            super.writeJson(generateReturnData("status",401));
            return ERROR;
        }*/
        SimUserInfo userInfo=convertToSimpleInfo(userService.queryUserById(targetUserId));
        if(userInfo==null)
        {
            super.writeJson(generateReturnData("status", 402));
            return ERROR;
        }
        super.writeJson(generateReturnData("status&userInfo",200,userInfo));
        return SUCCESS;

    }

    public String login()
    {
        UserEntity userEntity=userService.loginCheck(userName,password);
        if(userEntity==null)
        {
            super.writeJson(generateReturnData("status",102));
            return ERROR;
        }
        userEntity.setSessionId(super.getSession().getId());
        userService.upDateUserInfo(userEntity);
        UserInfo userInfo=convertToUserInfo(userEntity);
        super.writeJson(generateReturnData("status&userInfo",200,userInfo));
        return SUCCESS;
    }

    public String uploadImage(){
        InputStream is = null;
        OutputStream ops = null;
        try{
            if(null != this.image) {
                String folderPath=super.getRealyPath("/")+"/";
                String suffix = this.imageFileName.substring(imageFileName.lastIndexOf("."), imageFileName.length()).toLowerCase();	// 取文件后缀
                String saveFileName = "userImage/"+java.util.UUID.randomUUID().toString() + suffix;
                File path = new File(folderPath+"/userImage/");
                if(!path.exists()){
                    path.mkdirs();
                }
                if (image.exists()) {        //判断文件不存在，上传的附件大小为0视为不存在。
                    is = new FileInputStream(image);
                    // 将文件名称放入的根目录下的upload文件下
                    //FileUtil.createDir(new File(rootPath));
                    File destFile = new File(folderPath + saveFileName);
                    ops = new FileOutputStream(destFile);
                    FileHandler.encrypt(image.getPath(),folderPath+saveFileName);
                    //encrypt(is,ops);
                    UserEntity userEntity = userService.queryUserById(userId);   //如果之前的头像已存在则进行删除
                    deleteFile(folderPath + userEntity.getAvatar());
                    userEntity.setAvatar(saveFileName);
                    userService.upDateUserInfo(userEntity);
                    super.writeJson(generateReturnData("status&imagePath",200,userEntity.getAvatar()));

                    return SUCCESS;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(is!=null){
                    is.close();
                }
                if(ops!=null){
                    ops.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        super.writeJson(generateReturnData("status",100));
        return ERROR;
    }
    public String logout()
    {
        UserEntity userEntity=userService.queryUserById(userId);
        userEntity.setSessionId("");
        userService.upDateUserInfo(userEntity);
        super.writeJson(generateReturnData("status", 200));
        return SUCCESS;
    }


    private boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 创建要返回的Map类型数据
     * @param keys Map中参数的键，以'&'来进行分隔
     * @param args Object类型的数据，可以放多个
     *
     * 例如generateReturnData("key1&key2",object1,object2);
     *
     * @return Map<String,Object>类型的数据,即一个数据Map
     *
     * *******/
    private Map<String,Object> generateReturnData(String keys,Object...args)
    {
        Map<String,Object> data=new HashMap<String,Object>();
        String[] keyArray=keys.split("&");
        for(int i=0,j=0;((i<keyArray.length)&&j<args.length);i++,j++)
        {
            data.put(keyArray[i],args[j]);
        }
        return data;
    }

    private SimUserInfo convertToSimpleInfo(UserEntity userEntity)
    {
        if(userEntity==null)
            return null;
        SimUserInfo userInfo=new SimUserInfo();
        userInfo.setUserId(userEntity.getUserId());
        userInfo.setUserName(userEntity.getUsername());
        userInfo.setNickName(userEntity.getNickname());
        userInfo.setImagePath(userEntity.getAvatar());
        userInfo.setPhoneNum(userEntity.getTel());
        userInfo.setGender(userEntity.getGender());
        userInfo.setSignature(userEntity.getSignature());
        userInfo.setIs_shared(userEntity.getEnableShare());
        return userInfo;
    }

    private UserInfo convertToUserInfo(UserEntity userEntity)
    {
        if(userEntity==null)
            return null;
        UserInfo userInfo=new UserInfo();
        userInfo.setUserId(userEntity.getUserId());
        userInfo.setUserName(userEntity.getUsername());
        userInfo.setNickName(userEntity.getNickname());
        userInfo.setImagePath(userEntity.getAvatar());
        userInfo.setPhoneNum(userEntity.getTel());
        userInfo.setGender(userEntity.getGender());
        userInfo.setSignature(userEntity.getSignature());
        userInfo.setIs_shared(userEntity.getEnableShare());
        userInfo.setSessionId(userEntity.getSessionId());
        return userInfo;
    }

    private boolean checkPasswordAvailable(String password)
    {
        if(password==null||password.length()<6)
        {
            return false;
        }
        return true;
    }

    public String teste()
    {
        System.out.print("123");
        return SUCCESS;
    }
}
