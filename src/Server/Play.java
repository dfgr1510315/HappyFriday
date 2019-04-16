package Server;

import DAOlmpl.classDAOlmpl;
import net.sf.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Play")
public class Play extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        get_address(response,request);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request,response);
    }

    private void get_address(HttpServletResponse response,HttpServletRequest request)throws IOException {
        int No = Integer.parseInt(request.getParameter("No"));
        String class_No = request.getParameter("class_No");
        HttpSession session=request.getSession();
        String username = (String)session.getAttribute("user_id");
        JSONObject jsonObject = new JSONObject();
        classDAOlmpl cl = new classDAOlmpl();
        jsonObject.put("material", cl.get_material(username,No,class_No));
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
        out.close();
    }
}
