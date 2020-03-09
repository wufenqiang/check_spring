package com.weather.bigdata.it.app.check_spring.utils.send;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Map;

public final class HttpUtil {
	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	public static final int connectionTimeout = 3000;
	public static final int soTimeout = 5000;
	public static final int soLinger = 60;

	public static String postParams(String url, Map<String, Object> params) {
		return postMethod( url,  getPostValue2(params),  connectionTimeout , soTimeout, soLinger, "");
	}
	/**
	 * 使用post方法获取远程网页信息,支持301，302等跳转<br />
	 *
	 * List<BasicNameValuePair> list=new ArrayList<BasicNameValuePair>(); <br />
	 * list.add(new BasicNameValuePair("UserName","sxdxdx")); <br />
	 * list.add(new BasicNameValuePair("PassWord","sxdxdx"));<br />
	 * UrlEncodedFormEntity httpParameterEntity = new UrlEncodedFormEntity(list,"UTF-8");<br />
	 *
	 * 如果网页信息有编码信息按编码信息将返回的内容编码，如果没有则使用ISO-8859-1来进行编码
	 *
	 * @param url
	 * @param httpParameterEntity StringEntity,UrlEncodedFormEntity等类
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String postMethod(String url, HttpEntity httpParameterEntity, int connectionTimeout , int soTimeout, int soLinger, String cookie) {
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, connectionTimeout);
		HttpConnectionParams.setSoTimeout(httpParameters, soTimeout);
		HttpConnectionParams.setLinger(httpParameters, soLinger);
		HttpProtocolParams.setUserAgent(httpParameters, "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; WOW64; Trident/6.0; MAMIJS)");
		DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
		String html = "";
		try {
			HttpPost httpPost = new HttpPost(url);
			if (httpParameterEntity != null) {
				httpPost.setEntity(httpParameterEntity);
			}
			if(StringUtils.isNotBlank(cookie))
				httpPost.setHeader("Cookie", cookie);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					html = EntityUtils.toString(httpEntity);
					EntityUtils.consume(httpEntity);
				}
				return html;
			}
			httpPost.abort();

			if (statusCode == HttpStatus.SC_MOVED_TEMPORARILY || statusCode == HttpStatus.SC_MOVED_PERMANENTLY
					|| statusCode == HttpStatus.SC_SEE_OTHER || statusCode == HttpStatus.SC_TEMPORARY_REDIRECT) {
				Header header = httpResponse.getFirstHeader("location");
				if (header == null) {
					return html;
				}
				String newuri = header.getValue();
				if ((newuri == null) || (newuri.equals(""))) {
					newuri = "/";
				}
				HttpGet httpGet = new HttpGet(newuri);
				httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					html = EntityUtils.toString(httpEntity);
					EntityUtils.consume(httpEntity);
				}
				return html;
			}
		} catch (Exception e) {
			logger.info("httpclient post error: "+url+e.getMessage());
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return html;
	}
	@SuppressWarnings("deprecation")
	public static MultipartEntity getPostValue2(Map<String, Object> map){
		MultipartEntity multipartEntity = null;
		try {
			Charset charset = Charset.forName("utf-8");
			multipartEntity = new MultipartEntity(HttpMultipartMode.STRICT, null, charset);
			for(Map.Entry<String, Object> kv : map.entrySet()){
				multipartEntity.addPart(kv.getKey(), new StringBody(String.valueOf(kv.getValue()), charset));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return multipartEntity;
	}

	public static void main(String[] args){
		String url="http://172.16.185.238:8080/ReceiveMessages/SaveReceiveMessages";
		java.util.HashMap<String,Object> hashMap = new java.util.HashMap();
		hashMap.put("messageurl","http://way.weatherdt.com/apimall/basic/grid12h.htm?key=82942d356539081d6c4bb3f16c78e59c");
		hashMap.put("sign","036b5d9205b1804ae671ea0abf679814");
		hashMap.put("name","[商务接口监控][百度][12h预报]");
		hashMap.put("message","检验结果:检验正常\n数据时间:12-23,08:00:00\n更新时间:12-23,15:57:00\n数据阈值:12小时0分钟0秒\n更新阈值:2小时0分钟0秒");
		hashMap.put("pushtype",4);
		hashMap.put("contacts","吴奋强");

		String result=postParams(url,hashMap);
		System.out.println(result);
	}
}
