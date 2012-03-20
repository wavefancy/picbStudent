package cn.ac.picb.young;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import wavefancy.HibernateUtil;
import wavefancy.Users;

/**
 *
 * @author young
 */
public class Registration extends HttpServlet {

    private Session session;
    Transaction tx;
    //private Query query;
    //private String hql;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String username = request.getParameter("username");
            String psw = request.getParameter("psw");
            String pswd = new MD5().getMD5(psw);
            String email = request.getParameter("email");
            String uimg = request.getParameter("uimg");

            session = HibernateUtil.getCurrentSession();
            tx = session.beginTransaction();

            Users user = new Users();
            user.setUsername(username);
            user.setPsw(pswd);
            user.setEmail(email);
            //user.setPgroupid(0);
            //user.setPadmin(0);
            user.setUimg(uimg);

            session.save(user);
            tx.commit();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Registration</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<script type=\"text/javascript\"> alert(\"Successfully registered!\");location.href=\"index.html\";</script>");
            out.println("</body>");
            out.println("</html>");
            //response.sendRedirect("index.html");
        } finally {
            //session.close();
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
