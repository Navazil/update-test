package app.util;

import org.aspectj.util.FileUtil;

import java.io.*;

/**
 * Created by weishicong on 2016/1/29.
 */
public class FileHandler {

    /**
     * 对文件进行加密，可以替换从临时文件到目标文件的存储过程
     * @param srcFileName   源文件地址 (可以将临时文件的地址作为参数传入)
     * @param destFileName  目标文件地址
     *
     * @return 成功时返回true，出现错误时返回false
     * @throws  IOException
     * ******/
    public static boolean encrypt(String srcFileName,String destFileName) throws IOException
    {
        if(srcFileName.equals(destFileName))
            encrypt(srcFileName);
        InputStream in=null;
        OutputStream out=null;
        File srcFile=new File(srcFileName);
        File destFile=new File(destFileName);
        if(!srcFile.exists()||!srcFile.isFile())
        {
            boolean y=srcFile.exists();
            boolean x=srcFile.isFile();
            return false;
        }
        try {
            in = new FileInputStream(srcFile);
            out=new FileOutputStream(destFile);
            if(srcFile.length()>8192)
                encrypt1(in,out);
            else
                encrypt2(in,out);
        }
        catch (Exception e)
        {
            return false;
        }
        finally {
            if(out!=null)
            {
                out.flush();
                out.close();
            }
            if(in!=null)
                in.close();
        }
        return true;
    }
    public static boolean encrypt(String srcFileName) throws  IOException
    {
        File srcFile=new File(srcFileName);
        if(!srcFile.exists()||!srcFile.isFile())
        {
            return false;
        }
        OutputStream out=null;
        InputStream in=null;
        int i=srcFileName.lastIndexOf("\\");
        String folderPath=srcFileName.substring(0, srcFileName.lastIndexOf("\\") + 1);
        String fileName=srcFileName.substring(srcFileName.lastIndexOf("\\") + 1, srcFileName.lastIndexOf("."));
        String fileType=srcFileName.substring(srcFileName.lastIndexOf("."));
        String tempFileName=folderPath+fileName+"-temp.tmp";
        File tempFile=new File(tempFileName);

        out=new FileOutputStream(tempFile);
        in=new FileInputStream(srcFile);
        byte[] buffer = new byte[8192];
        int length = 0;
        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }
        in.close();
        out.flush();
        out.close();
        in=new FileInputStream(tempFileName);
        out=new FileOutputStream(srcFileName);
        encrypt(tempFileName,srcFileName);
        if(out!=null) {
            out.flush();
            out.close();
        }
        if(in!=null)
            in.close();
        try {
            System.out.print(tempFile.delete());
        }catch(Exception e)
        {e.printStackTrace();}
        return true;
    }

    /**
     * 对文件进行解密，替换服务器文件的传输过程
     * @param srcFileName   源文件地址
     * @param out           输出流
     *
     * @return 成功时返回true，出现错误时返回false
     * @throws  IOException
     * ******/
    public static boolean decrypt(String srcFileName,OutputStream out) throws IOException
    {
        InputStream in=null;
        File srcFile=new File(srcFileName);
        if(!srcFile.exists()||!srcFile.isFile())
        {
            boolean y=srcFile.exists();
            boolean x=srcFile.isFile();
            return false;
        }
        try {
            in = new FileInputStream(srcFile);
            if(srcFile.length()>8192)
                decrypt1(in, out);
            else
                decrypt2(in,out);
        }
        catch (Exception e)
        {
            return false;
        }
        finally {
            if(out!=null)
            {
                out.flush();
                out.close();
            }
            if(in!=null)
                in.close();
        }
        return true;
    }
    private static void encrypt1(InputStream in,OutputStream out) throws IOException
    {
        byte[] buffer = new byte[8192];
        in.read(buffer);
        byte[] seed = new byte[1];
        seed[0] = (byte) (Math.random() * 90);
        int[] order = {1, 2, 3, 4, 5, 6, 7, 8};
        for (int i = 7; i != 0; i--) {
            int assist = seed[0] % i;
            int temp = order[0];
            order[0] = order[assist];
            order[assist] = temp;
            seed[0] += i;
        }
        for (int i = 0; i != 8; i++) {
            out.write(buffer, 1024 * (order[i] - 1), 1024);
        }
        out.write(seed);
        int length = 0;
        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }
    }
    private static void encrypt2(InputStream in,OutputStream out) throws IOException
    {
        byte[] buffer = new byte[64];
        int length=in.read(buffer);
        for(int i=0;i<length;i++)
        {
            buffer[i]+=128;
        }
        out.write(buffer);
        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }
    }
    private static void decrypt1(InputStream in,OutputStream out) throws IOException
    {
        byte[] buffer=new byte[8192];
        byte[] seed=new byte[1];
        int assist=0;
        in.read(buffer);
        in.read(seed);
        int[] order={1,2,3,4,5,6,7,8};
        for(int i=1;i<8;i++)
        {
            seed[0]-=i;
            assist=seed[0]%i;
            int temp=order[0];
            order[0]=order[assist];
            order[assist]=temp;
        }
        for(int i=0;i!=8;i++)
        {
            out.write(buffer,1024*(order[i]-1),1024);
        }
        int length=0;
        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }
    }
    private static void decrypt2(InputStream in,OutputStream out) throws IOException
    {
        byte[] buffer = new byte[64];
        int length=in.read(buffer);
        for(int i=0;i<length;i++)
        {
            buffer[i]+=128;
        }
        out.write(buffer);
        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }
    }
}
