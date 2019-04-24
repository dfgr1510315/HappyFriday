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

    private boolean isHave(String string,String[] strings){
        for (String p:strings){
            if (string.equals(p)) return true;
        }
        return false;
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
      /*  request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");*/
        String realPath = "/LJZ";
       /* String realPath = "";*/
        String path=request.getServletPath();
/*        System.out.println("path:"+path);
        System.out.println("paths:"+ Arrays.toString(paths));*/
        if (isHave(path,paths)){
            chain.doFilter(request, response);
            return;
        }
        HttpSession session=request.getSession();
        try {
            String username=(String) session.getAttribute("user_id");
            String usertype = (String) session.getAttribute("usertype");
            if(username==null){
              /*  Cookie[] cookies = request.getCookies();
                for (Cookie cookie: cookies){
                    System.out.println(cookie.getName());
                    if ("user".equals(cookie.getName())){
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                }*/
                PrintWriter out = response.getWriter();
                out.println ("<script type=\"text/javascript\">window.location='"+realPath+"/HTML_JSP/404.html'</script>");
                out.close();
                return;
            }
            if (isHave(path,teacher_paths)){
                String[] class_id = ((String) session.getAttribute("class_id")).split(",");
                String class_id1 = request.getParameter("class_id");
                if (usertype.equals("teacher")&&isHave(class_id1,class_id)) {
                    chain.doFilter(request, response);
                    return;
                }else {
                    PrintWriter out = response.getWriter();
                    out.println ("<script type=\"text/javascript\">window.location='"+realPath+"/HTML_JSP/404.html'</script>");
                    out.close();
                    return;
                }
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            //判断session是否为空，为空的话就页面重定向到登陆界面
            response.sendRedirect(request.getContextPath()+"/HTML_JSP/homepage.html");
            e.printStackTrace();
            return;
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config){
        paths = config.getInitParameter("excPath").split(",");
        teacher_paths = config.getInitParameter("teacherPath").split(",");
    }

}
