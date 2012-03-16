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
public class GroupsDo extends HttpServlet {

	private static final long serialVersionUID = -3497314602257508489L;

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
		
		if (action.equalsIgnoreCase("addGroup")) {
			
		}
		
		
		
		
	}
	
	protected void addGroup(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
	}

}
