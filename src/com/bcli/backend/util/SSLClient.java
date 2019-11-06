package com.bcli.backend.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class SSLClient  {
	 
   /* public SSLClient() throws Exception {
        super();
        //传输协议需要根据自己的判断
        SSLContext ctx = SSLContext.getInstance("TLS");
        X509TrustManager tm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }
 
            @Override
            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }
 
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        ctx.init(null, new TrustManager[]{tm}, null);
        SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        ClientConnectionManager ccm = this.getConnectionManager();
        SchemeRegistry sr = ccm.getSchemeRegistry();
        sr.register(new Scheme("https", 443, ssf));
    }*/
	
	
	//请求配置，设置链接超时和读取超时
		private static final RequestConfig config = RequestConfig.custom().setConnectTimeout(30000).setSocketTimeout(30000).build();
	 
		//https策略，绕过安全检查
		public static CloseableHttpClient getSingleSSLConnection() throws Exception {
			//CloseableHttpClient httpClient = null;
			try {
				SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
					public boolean isTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString) throws CertificateException {
						return true;
					}
				}).build();
				SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				//return HttpClients.custom().setDefaultRequestConfig(config).build();
				//2017 12 14修改，忘了绕过安全检查设置了，哈哈
				return HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultRequestConfig(config).build();
			} catch (Exception e) {
				throw e;
			}
	 
		}
	
}

