package com.dym.alarm.common;


import android.content.Context;
import android.os.Build;
import android.util.Log;


import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import java.io.InputStream;

import java.net.URL;


import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


/**
 * Created by dizhanbin on 16/12/28.
 */

public class Https {


    public static void get(String urlString, final ObjectCallBack<Integer, String, Boolean> callBack) {

        HttpsURLConnection urlConnection = null;
        try {

            // Create an SSLContext that uses our TrustManager
            SSLContext context = SSLContext.getInstance("TLSv1", "AndroidOpenSSL");
            context.init(null, new TrustManager[]{
                    new X509TrustManager() {

                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {

                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {

                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[0];
                        }
                    }
            }, null);

            URL url = new URL(urlString);
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Connection", "close");
            urlConnection.setConnectTimeout(30000);//
            urlConnection.setDoOutput(false);
            urlConnection.setSSLSocketFactory(context.getSocketFactory());
            urlConnection.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    if (hostname != null && hostname.toLowerCase().contains("fotoable.net")) {
                        return true;
                    } else {
                        return false;
                    }

                }
            });


            if (HttpsURLConnection.HTTP_OK == urlConnection.getResponseCode()) {

                InputStream in = urlConnection.getInputStream();

                ByteArrayOutputStream out = new ByteArrayOutputStream();

                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = in.read(buffer)) > 0) {

                    out.write(buffer, 0, len);

                }

                try {
                    in.close();
                } catch (Exception e) {
                }

                callBack.onCallBack(HttpsURLConnection.HTTP_OK, new String(out.toByteArray(), "utf-8"));

            } else {

                callBack.onCallBack(0, null);

            }


        } catch (Exception e) {
            NLog.e(e);
            callBack.onCallBack(0, null);
        } finally {
            if (urlConnection != null)
                try {
                    urlConnection.disconnect();
                } catch (Exception e) {

                    NLog.e(e);
                }
        }


    }


}


