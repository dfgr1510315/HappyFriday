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
    private String[] teacher_paths;
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        //设置字符集为utf-8
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String realPath = request.getSession().getServletContext().getRealPath("/");
        String path=request.getServletPath();
        //System.out.println(path);
        for (String p : paths) {
            //System.out.println(p);
            if (path.equals(p)) {
                chain.doFilter(request, response);
                return;
            }
        }
        HttpSession session=request.getSession();
        try {
            String username=(String) session.getAttribute("user_id");
            String usertype = (String) session.getAttribute("usertype");
            //System.out.println(username);
            if(username==null){
                //判断session是否为空，为空的话就页面重定向到登陆界面
               /* response.sendRedirect(request.getContextPath()+"/HTML_JSP/homepage.jsp");
                PrintWriter pw=response.getWriter();
                pw.write("<script language='javascript'>alert('请登录')</script>");
                pw.close();*/
                PrintWriter out = response.getWriter();
                out.println ("<script language=javascript>alert('请登录');window.location='"+realPath+"/HTML_JSP/homepage.jsp'</script>");
                out.close();
                return;
            }
            for (String p : teacher_paths) {
                if (path.equals(p)) {
                    if (usertype.equals("student")) {
                        PrintWriter out = response.getWriter();
                        out.println ("<script language=javascript>alert('你的用户权限不足以访问此页面');window.location='"+realPath+"/HTML_JSP/homepage.jsp'</script>");
                        out.close();
                        return;
                    }
                }
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            //判断session是否为空，为空的话就页面重定向到登陆界面
            response.sendRedirect(request.getContextPath()+"/HTML_JSP/homepage.jsp");
            e.printStackTrace();
            return;
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {
        paths = config.getInitParameter("excPath").split(",");
        teacher_paths = config.getInitParameter("teacherPath").split(",");
    }

}
