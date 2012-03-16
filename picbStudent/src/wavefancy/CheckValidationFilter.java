package wavefancy;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class CheckValidationFilter implements Filter {
	@SuppressWarnings("unused")
	private FilterConfig filterConfig; 
		
	public void destroy() { 
		this.filterConfig = null; 
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException { 
//		System.out.println("CheckValidationFilter");
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) req;
		// get the client request URI
		String requestURI = httpServletRequest.getRequestURI();
//		System.out.println(requestURI);
		//get content path
		String contextPath = httpServletRequest.getContextPath();
//		System.out.println(contextPath);
		
		String uri = requestURI.substring(contextPath.length()+1);
		System.out.println(uri);
		
	
		
		Set<String> permitActions = new HashSet<String>();
		permitActions.add("checkEmail");
		permitActions.add("register");
		permitActions.add("login");
		permitActions.add("logout");
		
		//test
		if (!permitActions.contains(req.getParameter("action"))) { //the request from login page
			chain.doFilter(req, resp); 
			return;
		}else if (uri.endsWith("logout.do")) {
			chain.doFilter(req, resp);
			return;
		}else{
			HttpSession httpSession = httpServletRequest.getSession(true);
			Object userid = httpSession.getAttribute("userid");
			if (null == userid) { //don't login
				Map<String,String> reMap = new HashMap<String,String>();
				reMap.put("error", "timeOut");
				resp.getWriter().write(Utilities.getRawGson().toJson(reMap));
				resp.getWriter().flush();
				
				return;
			}else {
				//check other privilege
//				System.out.println("here!");
				
				chain.doFilter(req, resp);
				return;
			}
		}
		
//		chain.doFilter(req, resp);
	}

	public void init(FilterConfig arg0) throws ServletException {
		this.filterConfig = arg0;
	}

}
