package app.sys.userInfo.Interceptor;

import app.util.FileHandler;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by weishicong on 2016/1/26.
 */
public class FileFilter implements Filter {

    ServletContext context;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        context  = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response=((HttpServletResponse) servletResponse);
        HttpServletRequest request=((HttpServletRequest) servletRequest);
        OutputStream out=null;
        FileInputStream in=null;
        String path=context.getRealPath("/")+"/"+request.getRequestURI();
        File file=new File(path);
        if(file.isFile()&&file.exists())
        {
            String fileName=file.getPath().substring(file.getPath().lastIndexOf("\\")+1);
            response.setContentType("bin");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename="+fileName);
            try {
                out=response.getOutputStream();
                in = new FileInputStream(file);
                //decrypt(in, out);
                FileHandler.decrypt(path,out);
            }catch (IOException e)
            {
                e.printStackTrace();
            }
            finally {
                if(out!=null)
                {
                    out.flush();
                    out.close();
                }
                if(in!=null)
                {
                    in.close();
                }
            }

        }
        //((HttpServletResponse) servletResponse).sendRedirect("../index.jsp");
    }

    @Override
    public void destroy() {

    }
}
