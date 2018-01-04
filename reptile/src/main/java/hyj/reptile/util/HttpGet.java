package hyj.reptile.util;

import hyj.reptile.head.DefaultHeader;
import hyj.reptile.head.Header;
import hyj.reptile.head.ImageHeader;
import hyj.reptile.regex.Regex;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * 网络连接类
 * @author huangyujie
 *
 */
public class HttpGet {
	
	public static Map<String,String> getStringAndCookies(String url) throws UnsupportedEncodingException { // 定义一个字符串用来存储网页内容
		return getStringAndCookies(url,null);
	}
	
	public static String getString(String url) throws UnsupportedEncodingException { // 定义一个字符串用来存储网页内容
		return getString(url,null);
	}
	
	public static String getString(String url,Header header) throws UnsupportedEncodingException { // 定义一个字符串用来存储网页内容
		return getStringAndCookies(url,null).get("DATA");
	}
		
	public static Map<String,String> getStringAndCookies(String url,Header header) throws UnsupportedEncodingException { // 定义一个字符串用来存储网页内容
		StringBuffer buffer = new StringBuffer();
		// 定义一个缓冲字符输入流
		BufferedReader in = null;

		String charset = "GBK",cookie,line;
		Map<String,String> map = new HashMap<String,String>();
		
		try {
			// 将string转成url对象
			URL realUrl = new URL(url);
			
			// 初始化一个链接到那个url的连接
			HttpURLConnection  connection = connect(realUrl,header);
			
			// 初始化 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			
			//获取数据集的类型
			charset = getCharaSet(in);
			
			in.close();

			// 初始化一个链接到那个url的连接
			connection = connect(realUrl,header);
			
			// 初始化 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream(),charset));
			
			// 用来临时存储抓取到的每一行的数据
			while ((line = in.readLine()) != null) {
				// 遍历抓取到的每一行并将其存储到result里面
				buffer.append(line);
			}
			
			cookie = connection.getHeaderField("set-cookie");
			
			map.put("DATA", buffer.toString());
			map.put("COOKIES", cookie);
			
			connection.disconnect();
			
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return map;
	}

	/**
	 * get方法提交
	 * 
	 * @param url
	 *            String 访问的URL
	 * @param param
	 *            String 提交的内容
	 * @param repType
	 *            返回类型
	 * @return String
	 * */
	public static byte[] getByte(String url, String repType) {
		String result = "";
		byte[] resByt = null;
		try {
			URL urlObj = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) urlObj
					.openConnection();

			// 连接超时
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setConnectTimeout(25000);

			// 读取超时 --服务器响应比较慢,增大时间
			conn.setReadTimeout(25000);
			conn.setRequestMethod("GET");
			conn.addRequestProperty("Accept-Language", "zh-cn");
			conn.addRequestProperty("Content-type", repType);
			conn.addRequestProperty(
					"User-Agent",
					"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727)");
			
			conn.connect();

			PrintWriter out = new PrintWriter(new OutputStreamWriter(
					conn.getOutputStream(), "UTF-8"), true);

			if ("image/jpeg".equals(repType)) {
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				BufferedImage bufImg = ImageIO.read(conn.getInputStream());
				ImageIO.write(bufImg, "jpg", outputStream);
				resByt = outputStream.toByteArray();
				outputStream.close();

			} else {
				// 取得输入流，并使用Reader读取
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(conn.getInputStream()));

				System.out.println("=============================");
				System.out.println("Contents of get request");
				System.out.println("=============================");
				String lines = null;
				while ((lines = reader.readLine()) != null) {
					System.out.println(lines);
					result += lines;
					result += "\r";
				}
				resByt = result.getBytes();
				reader.close();
			}
			out.print(resByt);
			out.flush();
			out.close();
			// 断开连接
			conn.disconnect();
			System.out.println("=============================");
			System.out.println("Contents of get request ends");
			System.out.println("=============================");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resByt;
	}
	

	public static byte[] getImageByte(String url, String repType) {
		ImageHeader header = DefaultHeader.defaultImageHeader(repType);
		
		return getImageByte(url, repType, header);
	}

	/**
	 * get方法提交
	 * 
	 * @param url
	 *            String 访问的URL
	 * @param param
	 *            String 提交的内容
	 * @param repType
	 *            返回类型
	 * @return String
	 * */
	public static byte[] getImageByte(String url, String repType, ImageHeader header) {
		byte[] resByt = null;
		try {
			URL urlObj = new URL(url);
			
			//创建数据连接
			HttpURLConnection conn = connect(urlObj,header);

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BufferedImage bufImg = ImageIO.read(conn.getInputStream());
			ImageIO.write(bufImg, repType, outputStream);
			resByt = outputStream.toByteArray();
			outputStream.close();
			
			// 断开连接
			conn.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resByt;
	}

	/**
	 * 获取数据连接
	 * @param in
	 * @return
	 * @throws IOException 
	 */
	private static HttpURLConnection connect(URL url,Header head) throws IOException{
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		
		//加载对应的请求头
		if(head != null){
			head.putMsgToConnection(connection);
		}
		
		connection.connect();
		
		return connection;
	}
	
	
	
	/**
	 * 获取字符集的类型
	 * @param in
	 * @return
	 * @throws IOException 
	 */
	private static String getCharaSet(BufferedReader in) throws IOException{
		String charset = "GBK";
		String line;

		while ((line = in.readLine()) != null) {				
			if(!(Regex.regexString(line, "charset=.+?>").equals("Nothing"))){
				charset = Regex.regexString(line, "charset=(.+?)>",1);
				charset = charset.substring(
						0,charset.indexOf("\"") > 0 ? charset.indexOf("\""):charset.length() - 1).replaceAll("\"", "");
				break;
			}
		}
		
		return charset.replaceAll(" ", "");
	}
}

