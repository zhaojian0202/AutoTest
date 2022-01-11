package com.course.httpclient.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForPost {

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
        CloseableHttpClient closeableHttpClient= HttpClients.custom().setDefaultCookieStore(store).build();

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
    public void testPostMethod() throws IOException {
        String uri=bundle.getString("tset.post.with.cookie");
        //拼接最终的测试地址
        String testUrl=this.url+uri;

        //声明一个client对象，用来执行方法
        CloseableHttpClient closeableHttpClient=HttpClients.createDefault();
        //声明一个方法，post方法
        HttpPost post=new HttpPost(testUrl);
        //添加参数
        JSONObject params=new JSONObject();
        params.put("name","lisi");
        params.put("age","18");
        //设置请求头信息设置header
        post.setHeader("content-type","application/json");
        //将参数信息添加到方法中
        StringEntity entity=new StringEntity(params.toString(),"utf-8");
        post.setEntity(entity);
        //声明一个对象来存储相应结果
        String result;
        //设置cookies信息
        closeableHttpClient=HttpClients.custom().setDefaultCookieStore(this.store).build();
        //执行post方法
        HttpResponse response=closeableHttpClient.execute(post);
        //获取相应结果
        result=EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
        //处理结果，判断返回结果是否符合预期
        //将返回的相应结果转换为json对象
        JSONObject resultJson=new JSONObject(result);
        //具体判断返回结果的值
        //获取到结果值
        String success=(String) resultJson.get("lisi");
        String status=(String) resultJson.get("status");
        Assert.assertEquals("success",success);
        Assert.assertEquals("1",status);

    }
}
