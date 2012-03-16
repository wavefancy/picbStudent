package wavefancy;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.logicalcobwebs.proxool.ProxoolFacade;


/**
 * Upload Files through FileUpload.
 * @author icorner
 *
 */
public class FileUploadDo extends HttpServlet {
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
		
		try {
			uploadSigleImg(req, resp);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String action = req.getParameter("action");
		System.out.println("here!");
		System.out.println(action);
		
//		if (action.equalsIgnoreCase("uploadSigleImg")) {
//			try {
//				uploadSigleImg(req, resp);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		
		
		
		
	}
	
	protected void uploadSigleImg(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		System.out.println("here!");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(4096); //set file size
		factory.setRepository(new File("/tmp")); //set temp location
		
		//upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		HashMap<String, String> reMap = new HashMap<String, String>();
		
		@SuppressWarnings("unchecked")
		java.util.List<FileItem> items = upload.parseRequest(req);
		Iterator<FileItem> iterator = items.iterator();
		if (iterator.hasNext()) {
			FileItem fi = iterator.next();
			
			String fileName = fi.getName();
			
			fi.write(new File("/userImg" + fileName));
			reMap.put("img", "/userImg/"+fileName);
		}
		
		
		resp.getWriter().write(Utilities.getRawGson().toJson(reMap));
		resp.getWriter().flush();
		System.out.println("end"); 
	}
}
