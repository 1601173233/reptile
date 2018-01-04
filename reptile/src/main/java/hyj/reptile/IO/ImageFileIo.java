package hyj.reptile.IO;

import hyj.reptile.head.ImageHeader;
import hyj.reptile.util.HttpGet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.Set;

public class ImageFileIo {
	static Set<String> set = new HashSet<String>();
	
	/**
	 * 下载指定地址的图片
	 * @param url
	 * @param repType
	 * @param fileName
	 * @throws Exception
	 */
	public static void downloadImg(String url,String repType,String fileName) throws Exception{
		if(set.contains(url)){
			return;
		}
		
		set.add(url);

		String fileDir = fileName.substring(0,fileName.lastIndexOf('/'));
		File file = new File(fileDir);
		if(!file.isDirectory()){
			file.mkdirs();
		}
		
		ByteArrayOutputStream out = new ByteArrayOutputStream ();
		out.write(HttpGet.getImageByte(url,repType));
		out.writeTo(new FileOutputStream(fileName));
		System.out.println(fileName);
		out.flush();
		out.close();
	}
	

	/**
	 * 下载指定地址的图片
	 * @param url
	 * @param repType
	 * @param fileName
	 * @throws Exception
	 */
	public static void downloadImg(String url,String repType,String fileName,ImageHeader header) throws Exception{
		if(set.contains(url)){
			return;
		}
		
		set.add(url);
		
		String fileDir = fileName.substring(0,fileName.lastIndexOf('/'));
		File file = new File(fileDir);
		if(!file.isDirectory()){
			file.mkdirs();
		}
		
		ByteArrayOutputStream out = new ByteArrayOutputStream ();
		
		//读取数据并写到文件
		if(header != null){
			out.write(HttpGet.getImageByte(url,repType,header));
		}else{
			out.write(HttpGet.getImageByte(url,repType));
		}
		
		out.writeTo(new FileOutputStream(fileName));
		System.out.println(fileName);
		out.flush();
		out.close();
	}
}

