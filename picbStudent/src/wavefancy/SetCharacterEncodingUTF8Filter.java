package wavefancy;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class SetCharacterEncodingUTF8Filter implements Filter {
	@SuppressWarnings("unused")
	private FilterConfig config;

	public void destroy() {
		config = null;  
	}

	/**
	 * set character encoding utf-8;
	 */
	public void doFilter(ServletRequest rep, ServletResponse resp,
			FilterChain filterChain) throws IOException, ServletException {
//		System.out.println("utf-8");
		rep.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
//		System.out.println(rep.getParameter("id"));
		filterChain.doFilter(rep, resp);
	}

	public void init(FilterConfig arg0) throws ServletException {
		this.config = arg0;
	}

}
