package cn.com.uploadAndDownload.fileUploadDemo.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * 文件上传下载控制类
 * 
 * @author alvin
 *
 */
@Controller
@RequestMapping("/file")
public class FileController {
	private static Log log = LogFactory.getLog(FileController.class);
	/**
	 * 文件上传路径，以后换成文件服务器路径
	 */
	private static final String fileUploadPath = "/Users/alvin/Downloads/ProjectUploadFolder/";

	/**
	 * 初始页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "index")
	public ModelAndView index() {
		return new ModelAndView("fileViews/filesViews_index");
	}

	/**
	 * 上传文件页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "uploadFilePage")
	public ModelAndView uploadFilePage() {
		return new ModelAndView("fileViews/fileUpload");
	}

	/**
	 * 单文件上传
	 * @param file
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("uploadFile")
	public ModelAndView uploadFile(@RequestParam("files") MultipartFile file, String username, String password) {
		// 判断文件是否为空
		if (!file.isEmpty()) {
			try {
				// 文件保存路径
				String filePath = fileUploadPath + file.getOriginalFilename();
				// 转存文件
				file.transferTo(new File(filePath));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("UserName:" + username);
		System.out.println("Password:" + password);
		log.info("UserName:" + username);
		log.info("Password:" + password);
		return new ModelAndView("fileViews/fileList");
	}

	/**
	 * 多文件上传
	 * @param map
	 * @param files
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("uploadFiles")
	public ModelAndView uploadFiles(ModelMap map, @RequestParam("files") MultipartFile[] files, String username, String password) {
		List<String> fileNames = new ArrayList<String>();
		// 判断file数组不能为空并且长度大于0
		if (files != null && files.length > 0) {
			// 循环获取file数组中得文件
			for (int i = 0; i < files.length; i++) {
				MultipartFile file = files[i];
				// 保存文件
				System.out.println("True&False : " + saveFile(fileUploadPath, file));
				fileNames.add(file.getOriginalFilename());
			}
		}
		log.info("2UserName:" + username);
		log.info("2Password:" + password);
		map.put("uploadPath", fileUploadPath);
		map.put("fileNames", fileNames);
		return new ModelAndView("fileViews/fileList");
	}

	/**
	 * 文件列表页面
	 * @param map
	 * @return
	 */
	@RequestMapping("filelist")
	public ModelAndView filelist(ModelMap map) {
		String[] fileNames = new File(fileUploadPath).list();
		List<String> filFullNames = new ArrayList<String>();
		for (String fileName : fileNames) {
			if (!fileName.startsWith(".")) {
				filFullNames.add(fileName);
			}
		}
		map.put("uploadPath", fileUploadPath);
		map.put("fileNames", filFullNames);
		return new ModelAndView("fileViews/filelist");
	}

	/**
	 * 文件下载
	 * 
	 * @param fileName
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("download")
	public void down(String[] fileNames, HttpServletResponse response) throws Exception {
		for (String fileName : fileNames) {
			log.info("fileName: " + fileUploadPath + fileName);
		}
		int fileNums = fileNames.length;
		if (fileNums > 1) {
			download(fileNames, fileUploadPath, response);// 多文件下载zip
		} else {
			download(fileNames[0], fileUploadPath, response);// 单文件下载
		}
	}

	/**
	 * 保存文件
	 * 
	 * @param fileUploadPath
	 * @param file
	 * @return
	 */
	private boolean saveFile(String fileUploadPath, MultipartFile file) {
		// 判断文件是否为空
		if (!file.isEmpty()) {
			try {
				// 文件保存路径
				String filePath = fileUploadPath + file.getOriginalFilename();
				// 转存文件
				file.transferTo(new File(filePath));
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 多文件压缩zip后下载
	 * @param fileNames
	 * @param filePath
	 * @param response
	 */
	private void download(String[] fileNames, String filePath, HttpServletResponse response) {
		// 设置最终输出zip文件的文件名
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
		String zipFileName = formatter.format(new Date()) + ".zip";
		String strZipPath = filePath + "/" + zipFileName;

		ZipOutputStream zipStream = null;
		FileInputStream zipSource = null;
		BufferedInputStream bufferStream = null;
		File zipFile = new File(strZipPath);
		try {
			// 构造最终压缩包的输出流
			zipStream = new ZipOutputStream(new FileOutputStream(zipFile));
			for (int i = 0; i < fileNames.length; i++) {
				// 解码获取真实路径与文件名
				String realFileName = URLDecoder.decode(fileNames[i], "UTF-8");
				File file = new File(filePath + fileNames[i]);
				// TODO:未对文件不存在时进行操作，后期优化。
				if (file.exists()) {
					zipSource = new FileInputStream(file);// 将需要压缩的文件格式化为输入流
					/**
					 * 压缩条目不是具体独立的文件，而是压缩包文件列表中的列表项，称为条目，就像索引一样这里的name就是文件名, 文件名和之前的重复就会导致文件被覆盖
					 */
					ZipEntry zipEntry = new ZipEntry(realFileName);// 在压缩目录中文件的名字
					zipStream.putNextEntry(zipEntry);// 定位该压缩条目位置，开始写入文件到压缩包中
					bufferStream = new BufferedInputStream(zipSource, 1024 * 10);
					int read = 0;
					byte[] buf = new byte[1024 * 10];
					while ((read = bufferStream.read(buf, 0, 1024 * 10)) != -1) {
						zipStream.write(buf, 0, read);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != bufferStream)
					bufferStream.close();
				if (null != zipStream) {
					zipStream.flush();
					zipStream.close();
					zipStream = null;
				}
				if (null != zipSource)
					zipSource.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 判断系统压缩文件是否存在：true-把该压缩文件通过流输出给客户端后删除该压缩文件 false-未处理
		if (zipFile.exists()) {
			try {
				download(zipFileName, filePath, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			zipFile.delete();
		}
	}

	/**
	 * 单文件下载文件
	 * 
	 * @param fileName
	 * @param filePath
	 * @param response
	 * @throws Exception
	 */
	private void download(String fileName, String filePath, HttpServletResponse response) throws Exception {
		if (null != fileName) {
			File file = new File(filePath, fileName);
			System.out.println("file路径:" + file);
			if (file.exists()) {
				// 设置强制下载不打开,也有设置成response.setContentType("application/octet-stream
				// charset=utf-8");
				response.setContentType("application/force-download charset=utf-8");
				// 设置文件名
				response.addHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("UTF-8"), "iso8859-1"));
				response.setContentLength((int) file.length());
				byte[] buffer = new byte[1024];
				FileInputStream fis = null;
				BufferedInputStream bis = null;

				try {
					fis = new FileInputStream(file);
					bis = new BufferedInputStream(fis);
					OutputStream os = response.getOutputStream();
					int i = bis.read(buffer);
					while (i != -1) {
						os.write(buffer, 0, i);
						i = bis.read(buffer);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (bis != null) {
						try {
							bis.close();
							bis = null;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (fis != null) {
						try {
							fis.close();
							fis = null;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				System.out.println("文件不存在");
			}
		} else {
			System.out.println("下载的文件名为空");
		}
	}

}