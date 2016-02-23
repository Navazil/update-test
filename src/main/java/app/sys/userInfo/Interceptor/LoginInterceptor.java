package app.sys.userInfo.Interceptor;

import app.sys.userInfo.service.UserService;
import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by weishicong on 2016/1/15.
 */
public class LoginInterceptor extends MethodFilterInterceptor{
    private static final long serialVersionUID=314131591L;

    @Autowired
    UserService userService;

    @Override
    protected String doIntercept(ActionInvocation actionInvocation) throws Exception {
        ActionContext actionContext = actionInvocation.getInvocationContext();
        HttpServletRequest request= (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
        HttpServletResponse response= (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);
        int userId=Integer.parseInt(request.getParameter("userId"));
        String sessionId=request.getParameter("sessionId");
        String path = request.getRequestURI();//url
        String suffix = path.substring(path.lastIndexOf("."), path.length()).toLowerCase();
        if(suffix.equals("ppt"))
        {
            response.sendRedirect("index.jsp");
        }
        if(sessionId!=null&&userService.CheckOnlineStatus(userId,sessionId)) {
            return actionInvocation.invoke();
        }
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("status",103);
        writeJson(map);
        return Action.ERROR;
    }

    public void writeJson(Object object) {
        try {
            String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
            ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
            ServletActionContext.getResponse().addHeader("Access-Control-Allow-Origin", "*");
            ServletActionContext.getResponse().getWriter().write(json);
            ServletActionContext.getResponse().getWriter().flush();
            ServletActionContext.getResponse().getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
