package com.example.bookmarkapp;
	
		import java.io.BufferedReader;
		import java.io.InputStreamReader;
		import java.net.URI;
		import java.util.ArrayList;
	
		import org.apache.http.HttpResponse;
		import org.apache.http.NameValuePair;
		import org.apache.http.client.HttpClient;
		import org.apache.http.client.entity.UrlEncodedFormEntity;
		import org.apache.http.client.methods.HttpGet;
		import org.apache.http.client.methods.HttpPost;
		import org.apache.http.conn.params.ConnManagerParams;
		import org.apache.http.impl.client.DefaultHttpClient;
		import org.apache.http.params.HttpConnectionParams;
		import org.apache.http.params.HttpParams;
	
		public class ClientToServer {
		 public static final int HTTP_TIMEOUT = 30 * 1000;
		 private static HttpClient client;
		 private static HttpClient getHttpClient() {
		  if (client == null) {
			client = new DefaultHttpClient();
		 final HttpParams parameterHttp = client.getParams();
		 HttpConnectionParams.setConnectionTimeout(parameterHttp,
							HTTP_TIMEOUT);
		 ConnManagerParams.setTimeout(parameterHttp, HTTP_TIMEOUT);
			}
		return client;
		}
	
		public static String eksekusiHttpPost(String url,
		ArrayList<NameValuePair> postParameter) throws Exception {
		BufferedReader in = null;
		try {
		 HttpClient klien = getHttpClient();
		 HttpPost req = new HttpPost(url);
		 UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
			postParameter);
		 req.setEntity(formEntity);
		 HttpResponse jawaban = klien.execute(req);
		 in = new BufferedReader(new
	     InputStreamReader(jawaban.getEntity().getContent()));
		 StringBuffer sb = new StringBuffer("");
		 String line = "";
		 String NL = System.getProperty("line.separator");
		 while ((line = in.readLine()) != null) {
			sb.append(line + NL);
			}
		 in.close();
		 String hasil = sb.toString();
		return hasil;
		} finally {
			if (in != null) {
				in.close();
				}
			}
	
		}
	
		public static String eksekusiHttpGet(String url) throws Exception {
		 BufferedReader in = null;
		 try {
		   HttpClient hc = getHttpClient();
		   HttpGet req = new HttpGet();
		   req.setURI(new URI(url));
		   HttpResponse resp = hc.execute(req);
		   in = new BufferedReader(new
	        InputStreamReader(resp.getEntity().getContent()));
		   StringBuffer sb = new StringBuffer("");
		   String line = "";
		   String NL = System.getProperty("line.separator");
		   while ((line = in.readLine()) != null) {
			sb.append(line + NL);
			}
			in.close();
			String hasil = sb.toString();
			return hasil;
		} finally {
			if (in != null) {
		in.close();
}
}
}
	}

