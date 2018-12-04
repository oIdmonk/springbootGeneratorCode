package com.xxx.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 作者xujingyang. E-mail:xjy06@foxmail.com
 * @version 创建时间：2018年3月30日 下午7:50:25
 * @类说明: http请求工具类
 */
public class HttpClientUtil {

    /**
     * @param url 请求路径
     * @return 返回值 字符串格式
     * @throws ClientProtocolException
     * @throws IOException
     * @descript 封装HTTP GET方法 无参数的Get请求
     * @author xujingyang
     * @time 2018年3月30日
     * @return_type String
     */
    public static String get(String url) throws ClientProtocolException, IOException {
        // 首先需要先创建一个DefaultHttpClient的实例
        HttpClient httpClient = new DefaultHttpClient();
        // 先创建一个HttpGet对象,传入目标的网络地址,然后调用HttpClient的execute()方法即可:
        HttpGet httpGet = new HttpGet();
        httpGet.setURI(URI.create(url));
        HttpResponse response = httpClient.execute(httpGet);
        String httpEntityContent = getHttpEntityContent(response);
        httpGet.abort();
        return httpEntityContent;
    }

    /**
     * @param url      请求地址
     * @param paramMap 请求参数
     * @return 返回值 字符串格式
     * @throws ClientProtocolException
     * @throws IOException
     * @descript 封装HTTP GET方法 有参数的Get请求
     * @author xujingyang
     * @time 2018年4月1日
     * @return_type String
     */
    public static String get(String url, Map<String, Object> paramMap) throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet();
        List<NameValuePair> formparams = setHttpParams(paramMap);
        String param = URLEncodedUtils.format(formparams, "UTF-8");
        httpGet.setURI(URI.create(url + "?" + param));
        HttpResponse response = httpClient.execute(httpGet);
        String httpEntityContent = getHttpEntityContent(response);
        httpGet.abort();
        return httpEntityContent;
    }

    /**
     * @param url
     * @param headers  设置头信息
     * @param paramMap 请求参数
     * @return 返回值 字符串格式
     * @throws ClientProtocolException
     * @throws IOException
     * @descript 封装HTTP GET方法 有参数和header的Get请求
     * @author xujingyang
     * @time 2018年4月1日
     * @return_type String
     */
    public static String get(String url, Map<String, Object> headers, Map<String, Object> paramMap)
            throws ClientProtocolException, IOException {

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet();

        // 设置请求头
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, Object> param : headers.entrySet()) {
                if (param.getValue() != null)
                    httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
            }

        }

        List<NameValuePair> formparams = setHttpParams(paramMap);
        String param = URLEncodedUtils.format(formparams, "UTF-8");
        httpGet.setURI(URI.create(url + "?" + param));
        HttpResponse response = httpClient.execute(httpGet);
        String httpEntityContent = getHttpEntityContent(response);
        httpGet.abort();
        return httpEntityContent;
    }

    /**
     * @param url      请求地址
     * @param paramMap 请求参数
     * @return 返回值 字符串格式
     * @throws ClientProtocolException
     * @throws IOException
     * @descript 封装HTTP POST方法
     * @author xujingyang
     * @time 2018年4月1日
     * @return_type String
     */
    public static String post(String url, Map<String, Object> paramMap) throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
        httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 6000);
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> formparams = setHttpParams(paramMap);
        UrlEncodedFormEntity param = new UrlEncodedFormEntity(formparams, "UTF-8");
        // 通过setEntity()设置参数给post
        httpPost.setEntity(param);
        // 利用httpClient的execute()方法发送请求并且获取返回参数
        HttpResponse response = httpClient.execute(httpPost);
        String httpEntityContent = getHttpEntityContent(response);
        httpPost.abort();
        return httpEntityContent;
    }

    /**
     * @param url      请求地址
     * @param headers  请求头可有可无，无得话传null
     * @param paramMap 请求参数
     * @return 返回值 字符串格式
     * @throws ClientProtocolException
     * @throws IOException
     * @descript 封装HTTP POST方法 可设置请求头
     * @author xujingyang
     * @time 2018年4月1日
     * @return_type String
     */
    public static String post(String url, Map<String, Object> headers, Map<String, Object> paramMap)
            throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
        httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 6000);
        HttpPost httpPost = new HttpPost(url);

        // 设置请求头
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, Object> param : headers.entrySet()) {
                if (param.getValue() != null)
                    httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
            }

        }

        List<NameValuePair> formparams = setHttpParams(paramMap);
        UrlEncodedFormEntity param = new UrlEncodedFormEntity(formparams, "UTF-8");
        // 通过setEntity()设置参数给post
        httpPost.setEntity(param);
        // 利用httpClient的execute()方法发送请求并且获取返回参数
        HttpResponse response = httpClient.execute(httpPost);
        String httpEntityContent = getHttpEntityContent(response);
        httpPost.abort();
        return httpEntityContent;
    }

    /**
     * @param url       请求地址
     * @param headers   请求头可有可无，无得话传null
     * @param paramJson json参数
     * @return 返回值 字符串格式
     * @throws ClientProtocolException
     * @throws IOException
     * @descript 封装HTTP POST方法 参数传json数据 请求头可有可无，无得话传null
     * @author xujingyang
     * @time 2018年4月3日
     * @return_type String
     */
    public static String post(String url, Map<String, Object> headers, String paramJson)
            throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
        httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 6000);
        HttpPost httpPost = new HttpPost(url);

        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");

        // 设置请求头
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, Object> param : headers.entrySet()) {
                if (param.getValue() != null)
                    httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
            }

        }

        StringEntity entity = new StringEntity(paramJson, "UTF-8");
        entity.setContentType("text/json");
        // 通过setEntity()设置参数给post
        httpPost.setEntity(entity);
        // 利用httpClient的execute()方法发送请求并且获取返回参数
        HttpResponse response = httpClient.execute(httpPost);
        String httpEntityContent = getHttpEntityContent(response);
        httpPost.abort();
        return httpEntityContent;
    }

    /**
     * @param paramMap 请求参数
     * @return
     * @descript 设置请求参数
     * @author xujingyang
     * @time 2018年3月30日
     * @return_type List<NameValuePair>
     */
    private static List<NameValuePair> setHttpParams(Map<String, Object> paramMap) {
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        Set<Map.Entry<String, Object>> set = paramMap.entrySet();
        for (Map.Entry<String, Object> entry : set) {
            if (entry.getValue() != null)
                formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
        }
        return formparams;
    }

    /**
     * @param response 相应response
     * @return 返回相应内容，字符串格式
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @descript 获得响应HTTP实体内容, 内部调用
     * @author xujingyang
     * @time 2018年3月30日
     * @return_type String
     */
    private static String getHttpEntityContent(HttpResponse response) throws IOException, UnsupportedEncodingException {
        // 通过HttpResponse 的getEntity()方法获取返回信息
        HttpEntity entity = response.getEntity();

        if (response.getStatusLine().getStatusCode() != 200) {
            return "远程请求出错，状态码：" + response.getStatusLine().getStatusCode();
        }

        if (entity != null) {
            InputStream is = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = br.readLine();
            StringBuilder sb = new StringBuilder();
            while (line != null) {
                sb.append(line + "\n");
                line = br.readLine();
            }
            br.close();
            is.close();
            return sb.toString();
        }
        return "";
    }

}
