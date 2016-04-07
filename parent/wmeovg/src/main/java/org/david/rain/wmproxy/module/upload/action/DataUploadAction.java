package org.david.rain.wmproxy.module.upload.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.david.rain.wmproxy.common.base.struts2.BaseAction;
import org.david.rain.wmproxy.module.sys.entity.User;
import org.david.rain.wmproxy.module.util.ContentUtils;
import org.david.rain.wmproxy.module.util.FileUploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.opensymphony.xwork2.Action;

public class DataUploadAction extends BaseAction implements Action {

	public String execute() throws IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = response.getWriter();

		//System.out.println(uploadContentType + " :" + uploadFileName);
		
		if(!ContentUtils.isContentTypeAllow(uploadContentType)){
			
			out.write("{success:false, msg:'文件格式不对，请上传excel，cvs或txt文件。'}");
			return null;
		}
		
		String loginName = (String) request.getSession().getAttribute(
				User.LOGIN_NAME_KEY);
		String rootPath = request.getSession().getServletContext().getRealPath(
				"/")
				+ "upload"
				+ FileUploadUtil.FILE_SEPARATOR
				+ "whitelist"
				+ FileUploadUtil.FILE_SEPARATOR 
				+ loginName
				+ FileUploadUtil.FILE_SEPARATOR ;
		//System.out.println(rootPath);

		String newFileName = System.currentTimeMillis()
				+ FileUploadUtil.getFileExtention(uploadFileName);

		try {
			FileUploadUtil.upload(upload, rootPath, newFileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			out.write("{success:false,msg:'上传失败，请重试。'"+e.getMessage()+"}");
			return null;
		}
		
		logger.info(loginName + "上传文件："+ uploadFileName + " 文件类型：" + uploadContentType + " 保存路径：" + rootPath + newFileName );
		out.write("{success:true, filename:'" + newFileName + "'}");
		return null;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	public File upload;
	public String uploadFileName;
	public String uploadContentType;
}
