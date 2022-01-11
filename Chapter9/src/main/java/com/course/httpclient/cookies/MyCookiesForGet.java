package com.course.httpclient.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForGet {

    private String url;
    private String uri;
    private ResourceBundle bundle;
    //用来存储cookies字段
    private CookieStore store;
    @BeforeTest
    public void beforeTest(){
        bundle=ResourceBundle.getBundle("application", Locale.CHINA);
        url=bundle.getString("test.url");
        uri=bundle.getString("getCookies.uri");
    }

    @Test
    public void testGetCookies() throws IOException {
        String result;
        String testUrl=this.url+this.uri;

        //获取cookies信息
        this.store=new BasicCookieStore();
        CloseableHttpClient closeableHttpClient=HttpClients.custom().setDefaultCookieStore(store).build();

        HttpGet get=new HttpGet(testUrl);
        //CloseableHttpClient client= HttpClients.createDefault();
        HttpResponse response=closeableHttpClient.execute(get);
        result= EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);



        List<Cookie> cookieList=store.getCookies();
        for (Cookie cookie:cookieList){
            String name=cookie.getName();
            String value=cookie.getValue();
            System.out.println("Cookie name ="+name+";Cookie value ="+value);
        }
    }
    @Test(dependsOnMethods = {"testGetCookies"})
    public void testGetWithCookies() throws IOException {
        String uri=bundle.getString("test.get.with.cookie");
        String testUrl=this.url+uri;
        HttpGet get=new HttpGet(testUrl);
        CloseableHttpClient closeableHttpClient=HttpClients.custom().setDefaultCookieStore(store).build();

        HttpResponse response=closeableHttpClient.execute(get);

        int statusCode=response.getStatusLine().getStatusCode();
        System.out.println("statusCode="+statusCode);
        if (statusCode==200){
            String result= EntityUtils.toString(response.getEntity(),"utf-8");
            System.out.println(result);
        }

    }
}
