/*
package Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


@WebFilter(filterName = "Login_Filter")
public class Login_Filter implements Filter {
    private String[] paths;
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        //设置字符集为utf-8
        request.setCharacterEncoding("UTF-8");
        String path=request.getServletPath();
        for (String p : paths) {
            if (path.equals(p)) {
                chain.doFilter(request, response);
                return;
            }
        }
        HttpSession session=request.getSession();
        try {
            String username=(String) session.getAttribute("username");
            if(username==null){
                //判断session是否为空，为空的话就页面重定向到登陆界面
                response.sendRedirect(request.getContextPath()+"/HTML_JSP/homepage.jsp");
                return;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //判断session是否为空，为空的话就页面重定向到登陆界面
            response.sendRedirect(request.getContextPath()+"/HTML_JSP/homepage.jsp");
            e.printStackTrace();
            return;
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        paths = config.getInitParameter("excPath").split(",");
    }

}
*/
