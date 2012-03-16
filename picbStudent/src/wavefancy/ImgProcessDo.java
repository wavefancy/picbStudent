package wavefancy;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.logicalcobwebs.proxool.ProxoolFacade;

/**
 * This class is used to process image related problem.
 * @author icorner
 *
 */
public class ImgProcessDo extends HttpServlet {

	private static final long serialVersionUID = 5141070249514221046L;

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
//		System.out.println(action);
		
		if (action.equalsIgnoreCase("cropImg")) {
			cropImg(req, resp);
			return;
		}
		
		if (action.equalsIgnoreCase("reset")) {
			reset(req, resp);
			return;
		}
		
		if (action.equalsIgnoreCase("getCurrentImg")) {
			getCurrentImg(req, resp);
			return;
		}

	}
	
	
	/**
	 * crop image.
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void cropImg(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		Map<String, String> reMap = new HashMap<String, String>();
		HttpSession httpSession = req.getSession(true);
		File tempImgFile = (File) httpSession.getAttribute("tempImg");
//		httpSession.removeAttribute("tempImg");
//		ImageInputStream imageInputStream = ImageIO.createImageInputStream(tempImgFile);
		String fileSuffix = tempImgFile.getName().substring(tempImgFile.getName().lastIndexOf('.')+1);
//		System.out.println(fileSuffix);
		
		Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName(fileSuffix);
		
		if (iterator.hasNext()) {
			ImageReader imageReader =  iterator.next();
			
			//image source
			ImageInputStream iis = ImageIO.createImageInputStream(tempImgFile);
			
			imageReader.setInput(iis, true);
			ImageReadParam imageReadParam = imageReader.getDefaultReadParam();
			
			//original image.
			BufferedImage original = ImageIO.read(tempImgFile);
			int width = original.getWidth();
//			int height = original.getHeight();
			
			//scale image
//			Image image = original.getScaledInstance(400,(int)( height * width/400.0), Image.SCALE_DEFAULT);
//			BufferedImage tag = new BufferedImage(400, (int)( height * width/400.0), BufferedImage.TYPE_INT_RGB);
//			Graphics graphics = tag.getGraphics();
//			graphics.drawImage(image, 0, 0,null); //scaled image.
//			graphics.dispose();
			

			//scale image.
			int x1 = (int) (Integer.parseInt(req.getParameter("x1"))*width/400.0);
			int x2 = (int) (Integer.parseInt(req.getParameter("x2"))*width/400.0);
			int y1 = (int) (Integer.parseInt(req.getParameter("y1"))*width/400.0);
			int y2 = (int) (Integer.parseInt(req.getParameter("y2"))*width/400.0);
			
			Rectangle rectangle = new Rectangle(x1,y1,x2-x1,y2-y1);
			imageReadParam.setSourceRegion(rectangle);
			BufferedImage bufferedImage = imageReader.read(0,imageReadParam);
			ImageIO.write(bufferedImage, fileSuffix, tempImgFile);
			
			reMap.put("message", "ok");
			reMap.put("file", tempImgFile.getName());
		}else {
			reMap.put("message", "fail");
		}
		
		resp.getWriter().write(Utilities.getRawGson().toJson(reMap));
		resp.getWriter().flush();
	}
	
	/**
	 * When user reset image, delete the temp file on server.
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void reset(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		HttpSession httpSession = req.getSession(true);
		File tempImgFile = (File) httpSession.getAttribute("tempImg");
		if (tempImgFile != null) {
			tempImgFile.delete();
		}
	}
	
	protected void getCurrentImg(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		Map<String, String> reMap = new HashMap<String, String>();
		HttpSession httpSession = req.getSession(true);
		File tempImgFile = (File) httpSession.getAttribute("tempImg");
		if (tempImgFile != null) {
			reMap.put("file", tempImgFile.getName());
			reMap.put("message", "ok");
		}else {
			reMap.put("message", "fail");
		}
		
		resp.getWriter().write(Utilities.getRawGson().toJson(reMap));
		resp.getWriter().flush();
	}

}
