package hyj.reptile.head;

import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URLConnection;

/**
 * 数据请求头
 * @author yyy
 *
 */
public class Header {
	//版本号
	private String version = null;
	
	//请求报头.希望接受的文本类型
	private String accept = null;

	//客户端接受的字符集
	private String acceptCharset = null;

	//客户端接受的文本格式
	private String acceptEncoding = null;

	//客户端接受的语言
	private String acceptLanguage = null;

	//用于证明客户端有权查看某个资源
	private String authorization = null;

	//被请求资源的Internet主机和端口号
	private String host = null;

	//主机信息
	private String userAgent = null;
	
	//请求方式
	private String requestMethod = null;

	//储存方式
	private String cacheControl = null;
	
	//连接方式
	private String connection = null;

	//连接超时时间
	private Integer connectTimeout = null;

	//读超时时间
	private Integer readTimeout = null;
	
	//是否可读
	private Boolean doInput = null;

	//是否可写
	private Boolean doOutput = null;
	
	private String cookie;
	
	/**
	 * 把数据头的信息放置到请求连接上去
	 */
	public void putMsgToConnection(URLConnection connection){
		//版本号
		if(version != null){
			connection.addRequestProperty("Version", version);
		}

		//请求报头.希望接受的文本类型
		if(accept != null){
			connection.addRequestProperty("Accept", accept);
		}

		//客户端接受的字符集
		if(acceptCharset != null){
			connection.addRequestProperty("Accept_Charset", acceptCharset);
		}

		//客户端接受的文本格式
		if(acceptEncoding != null){
			connection.addRequestProperty("Accept_Encoding", acceptEncoding);
		}

		//客户端接受的语言
		if(acceptLanguage != null){
			connection.addRequestProperty("Accept_Language", acceptLanguage);
		}

		//用于证明客户端有权查看某个资源
		if(authorization != null){
			connection.addRequestProperty("Authorization", authorization);
		}

		//被请求资源的Internet主机和端口号
		if(host != null){
			connection.addRequestProperty("Host", host);
		}

		//主机信息
		if(userAgent != null){
			connection.addRequestProperty("User_Agent", userAgent);
		}

		//储存方式
		if(userAgent != null){
			connection.addRequestProperty("Cache_Control", cacheControl);
		}

		//连接方式
		if(this.connection != null){
			connection.addRequestProperty("Connection", this.connection);
		}
		
		//连接超时时间
		if(connectTimeout != null){
			connection.setConnectTimeout(connectTimeout);
		}
		
		//读超时时间
		if(readTimeout != null){
			connection.setReadTimeout(readTimeout);
		}

		//是否可读
		if(doInput != null) {
			connection.setDoInput(doInput);
		}

		//是否可写 
		if(doOutput != null) {
			connection.setDoOutput(doOutput);
		}

		//是否可写 
		if(cookie != null) {
			connection.addRequestProperty("Cookie", cookie);
		}
	}
	
	/**
	 * 把数据头的信息放置到请求连接上去
	 * @throws ProtocolException 
	 */
	public void putMsgToConnection(HttpURLConnection connection) throws ProtocolException{
		putMsgToConnection((URLConnection) connection);

		//设置请求方式
		if(requestMethod != null) {
			connection.setRequestMethod(requestMethod);
		}
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public String getAcceptCharset() {
		return acceptCharset;
	}

	public void setAcceptCharset(String acceptCharset) {
		this.acceptCharset = acceptCharset;
	}

	public String getAcceptEncoding() {
		return acceptEncoding;
	}

	public void setAcceptEncoding(String acceptEncoding) {
		this.acceptEncoding = acceptEncoding;
	}

	public String getAcceptLanguage() {
		return acceptLanguage;
	}

	public void setAcceptLanguage(String acceptLanguage) {
		this.acceptLanguage = acceptLanguage;
	}

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public Integer getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public Integer getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(Integer readTimeout) {
		this.readTimeout = readTimeout;
	}

	public Boolean getDoInput() {
		return doInput;
	}

	public void setDoInput(Boolean doInput) {
		this.doInput = doInput;
	}

	public Boolean getDoOutput() {
		return doOutput;
	}

	public void setDoOutput(Boolean doOutput) {
		this.doOutput = doOutput;
	}

	public String getCacheControl() {
		return cacheControl;
	}

	public void setCacheControl(String cacheControl) {
		this.cacheControl = cacheControl;
	}

	public String getConnection() {
		return connection;
	}

	public void setConnection(String connection) {
		this.connection = connection;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	
}

