package cn.com.goldwind.convert.pdf2txt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PdfToTxt {

	public static void main(String[] args) {
		try {
			// 取得F盘下的pdf的内容
			readPdf("/Users/alvinsun/Documents/GoldWind/英汉电力技术词典.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 传入一个.pdf文件
	 * 
	 * @param file 绝对路径
	 * @throws Exception
	 */
	public static void readPdf(String file) throws Exception {
		boolean sort = false;// 是否排序
		String pdfFile = file;// pdf文件名
		String textFile = null;// 输入文本文件名称
		String encoding = "UTF-8";// 编码方式
		int startPage = 1;// 开始提取页数
		int endPage = Integer.MAX_VALUE;// 结束提取页数
		Writer output = null;// 文件输入流，生成文本文件
		PDDocument document = null;// 内存中存储的PDF Document
		try {
			try {
				URL url = new URL(pdfFile);// 首先当作一个URL来装载文件，如果得到异常再从本地文件系统//去装载文件
				document = PDDocument.load(new File(pdfFile));// 注意参数已不是以前版本中的URL,而是File
				String fileName = url.getFile();// 获取PDF的文件名
				// 以原来PDF的名称来命名新产生的txt文件
				if (fileName.length() > 4) {
					File outputFile = new File(fileName.substring(0, fileName.length() - 4) + ".txt");
					textFile = "/Users/alvinsun/Documents/GoldWind/" + outputFile.getName();
				}
			} catch (MalformedURLException e) {
				// 如果作为URL装载得到异常则从文件系统装载
				// 注意参数已不是以前版本中的URL.而是File。
				document = PDDocument.load(new File(pdfFile));
				if (pdfFile.length() > 4) {
					textFile = pdfFile.substring(0, pdfFile.length() - 4) + ".txt";
				}
			}
			output = new OutputStreamWriter(new FileOutputStream(textFile), encoding);// 文件输入流，写入文件倒textFile
			PDFTextStripper stripper = null;// PDFTextStripper来提取文本
			stripper = new PDFTextStripper();
			stripper.setSortByPosition(sort);// 设置是否排序
			stripper.setStartPage(startPage);// 设置起始页
			stripper.setEndPage(endPage);// 设置结束页
			stripper.writeText(document, output);// 调用PDFTextStripper的writeText提取并输出文本

			System.out.println(textFile + " 输出成功！");
		} finally {
			if (output != null) {
				output.close();// 关闭输出流
				output=null;
			}
			if (document != null) {
				document.close();// 关闭PDF Document
				document=null;
			}
		}
	}
}
