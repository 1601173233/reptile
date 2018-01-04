package hyj.reptile.head;

import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URLConnection;

/**
 * 图像数据请求头
 * @author yyy
 *
 */
public class ImageHeader extends Header {
	//内容的类型
	private String contentType = null;
	
	//请求位置
	private String referer = null;
	
	/**
	 * 把数据头的信息放置到请求连接上去
	 */
	public void putMsgToConnection(URLConnection connection){
		super.putMsgToConnection(connection);
		
		_putMsgToConnection(connection);
	}

	/**
	 * 把数据头的信息放置到请求连接上去
	 * @throws ProtocolException 
	 */
	public void putMsgToConnection(HttpURLConnection connection) throws ProtocolException{
		super.putMsgToConnection(connection);
		
		_putMsgToConnection(connection);
	}
	
	private void _putMsgToConnection(URLConnection connection){
		//内容类型
		if(contentType != null){
			connection.addRequestProperty("contentType", contentType);
		}
		
		//请求位置
		if(referer != null){
			connection.addRequestProperty("referer", referer);
		}
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}
	
	
}

