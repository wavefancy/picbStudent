package wavefancy;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.logicalcobwebs.proxool.ProxoolFacade;

/**
 * This class is not intended for this project, just a template.
 * @author icorner
 *
 */
public class UsersDo extends HttpServlet {

	private static final long serialVersionUID = 7336684815224754637L;
	
	@Override
	public void destroy() {
		ProxoolFacade.shutdown();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = req.getParameter("action");
		System.out.println(action);
		
		if (action.equalsIgnoreCase("login")) {
			login(req, resp);
			return;
		}
		
		if (action.equalsIgnoreCase("register")) {
			register(req, resp);
			return;
		}
		
		if (action.equalsIgnoreCase("checkEmail")) {
			checkEmail(req, resp);
			return;
		}
		
		if (action.equalsIgnoreCase("checkAdmin")) {
			checkAdmin(req, resp);
			return;
		}
		
		if (action.equalsIgnoreCase("logout")) {
			logout(req, resp);
			return;
		}
		
		if (action.equalsIgnoreCase("getUserInfo")) {
			getUserInfo(req, resp);
			return;
		}
		
		if (action.equalsIgnoreCase("loadUserList")){
			loadUserList(req,resp);
			return;
		}
	}
	
	protected void register(HttpServletRequest req, HttpServletResponse resp)
		throws IOException, ServletException{
//		System.out.println("register");
		Map<?, ?> parameterMap = req.getParameterMap();
//		System.out.print(parameterMap);
		Users user = Utilities.getRawGson().fromJson(Utilities.getRawGson().toJson(parameterMap), Users.class);
		int id = Integer.parseInt(req.getParameter("id"));
		
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
			if (id < 0) {
				
				session.save(user);
				HttpSession httpSession = req.getSession(true);
//				httpSession.setAttribute("username", user.getEmail());
				httpSession.setAttribute("userid", user.getId());
				
			}else {
				
				Session s2 = HibernateUtil.getSession();
				s2.beginTransaction();
				Users u = (Users) s2.get(Users.class, id);
				s2.getTransaction().commit();
				s2.close();
				
			
				session.update(user);
			}
		session.getTransaction().commit();
		session.close();
		
		Map<String, String> reMap = new HashMap<String, String>();
		reMap.put("id", Integer.toString(user.getId()));
		
		resp.getWriter().write(Utilities.getRawGson().toJson(reMap));
		resp.getWriter().flush();
	}
	
	protected void checkEmail(HttpServletRequest req, HttpServletResponse resp)
		throws IOException, ServletException{
		String email = req.getParameter("email").trim();
		Map<String, String> reMap = new HashMap<String, String>();
		
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		
		String hql = "select email from Users where email=?";
		Query query = session.createQuery(hql);
		query.setParameter(0, email);
		ScrollableResults scrollableResults = query.scroll();
		if (scrollableResults.next()) {
			reMap.put("message", "bad");
		}else{
			reMap.put("message", "ok"); 
		}
		
		session.getTransaction().commit();
		session.close();
//		session.close();
		
		resp.getWriter().write(Utilities.getRawGson().toJson(reMap));
		resp.getWriter().flush();
	}
	
	protected void login(HttpServletRequest req, HttpServletResponse resp)
		throws IOException, ServletException{
//		System.out.print("here");
		String username = req.getParameter("email").trim();
		String psw = req.getParameter("psw").trim();
//		System.err.println(username +"--"+psw);
		
		Map<String, String> reMap = new HashMap<String, String>();
		
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		String hql = "select id,email,oadmin from Users where email = ? and psw = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, username);
		query.setParameter(1, psw);
		
		ScrollableResults sResults = query.scroll();
		if (sResults.next()) {
			HttpSession httpSession = req.getSession(true);
			httpSession.setAttribute("userid", sResults.getInteger(0));
			
			reMap.put("oadmin", sResults.getInteger(2).toString());
			reMap.put("id", sResults.getInteger(0).toString());
			
		}else {
			reMap.put("error", "EMAIL and PASSWORD do not match, please check!");
		}
		
		session.getTransaction().commit();
		session.close();
		
		resp.getWriter().write(Utilities.getRawGson().toJson(reMap));
		resp.getWriter().flush();
	}
	
	protected void checkAdmin(HttpServletRequest req, HttpServletResponse resp)
		throws IOException, ServletException{
		int id = Integer.parseInt(req.getParameter("id").trim());
		
		Map<String,String> reMap = new HashMap<String, String>();
		
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		String hql = "select oadmin from Users where id=?";
		Query query = session.createQuery(hql);
		query.setParameter(0, id);
		
		ScrollableResults sr = query.scroll();
		if(sr.next()){
			int oadmin = sr.getInteger(0);
			if(oadmin == 1){
				reMap.put("error", "0");
			}else{
				reMap.put("error", "1");
			}
		}else{
			reMap.put("error", "1");
		}
		
		session.getTransaction().commit();
		session.close();
		
		resp.getWriter().write(Utilities.getRawGson().toJson(reMap));
		resp.getWriter().flush();
	}
	
	protected void logout(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException{
		HttpSession session = request.getSession(true);
		session.setAttribute("userid", null);
		
	}
	
	protected void getUserInfo(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException{

			int id = Integer.parseInt(request.getParameter("id").trim());
			
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			Users u = (Users) session.get(Users.class, id);
			session.getTransaction().commit();
			session.close();
			
			response.getWriter().write(Utilities.getRawGson().toJson(u));
//			System.out.println(Utilities.getRawGson().toJson(u));
			response.getWriter().flush();
		}
	
	protected void loadUserList(HttpServletRequest req, HttpServletResponse resp) 
		throws IOException, ServletException{
		Map<String, String> reMap = new HashMap<String, String>();
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
			String hql = "select id,fName,lName,email,institution, address,pcode,pay from Users where oadmin=0 order by id";
			Query query = session.createQuery(hql);
			ScrollableResults sr = query.scroll();
			StringBuffer sBuffer  = new StringBuffer();
			while(sr.next()){
				sBuffer.append("<tr>");
				sBuffer.append("<td>").append(sr.getInteger(0)).append("</td>");
				sBuffer.append("<td>").append(sr.getString(1)).append("</td>");
				sBuffer.append("<td>").append(sr.getString(2)).append("</td>");
				sBuffer.append("<td>").append(sr.getString(3)).append("</td>");
				sBuffer.append("<td>").append(sr.getString(4)).append("</td>");
				sBuffer.append("<td>").append(sr.getString(5)).append("</td>"); 
				sBuffer.append("<td>").append(sr.getString(6)).append("</td>");
				
				if (sr.getInteger(7) == 0) {
					sBuffer.append("<td>").append("NO").append("</td>");
				}else {
					sBuffer.append("<td>").append("Payed").append("</td>");
				}
				
				sBuffer.append("</tr>");
			}
		session.getTransaction().commit();
		session.close();
		
		reMap.put("content", sBuffer.toString());
		resp.getWriter().write(Utilities.getRawGson().toJson(reMap));
		resp.getWriter().flush();
	}

}
