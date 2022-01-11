package com.course.server;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@Api(value = "/",description = "这是我全部的get方法")
public class MyGetMethod {

    @RequestMapping(value = "/getCookies",method = RequestMethod.GET)
    @ApiOperation(value = "通过这个方法科技获取到cookie值",httpMethod = "get")
    public String getCookies(HttpServletResponse response){
        //HttpServerletRequest
        //HttpServerletResponse
        Cookie cookie=new Cookie("login","true");
        response.addCookie(cookie);
        return "恭喜你获得cookie的信息";
    }

    /**
     * 要求客户端携带cookie访问
     * @param request
     * @return
     */
    @RequestMapping(value = "/get/with/cookie",method = RequestMethod.GET)
    @ApiOperation(value = "要求客户端携带cookie访问",httpMethod = "GET")
    public String getWithCookies(HttpServletRequest request){
        Cookie[] cookies=request.getCookies();
        if (Objects.isNull(cookies)){
            return "你必须携带cookies信息来1";
        }
        for (Cookie cookie:cookies){
            if (cookie.getName().equals("login") && cookie.getValue().equals("true")){
                return "这是一个携带cookie信息的get请求";
            }
        }
        return "你必须携带cookies信息来2";

    }

    /**
     * 开发一个需要携带参数才能访问的get请求
     * 第一种实现方式： url:key=value&key=value
     * 我们来模拟获取商品列表
     */

    @RequestMapping(value = "/get/with/param",method = RequestMethod.GET)
    @ApiOperation(value = "一个需要携带参数才能访问的get请求",httpMethod = "GET")
    public Map<String,Integer> getList(@RequestParam Integer start,
                                       @RequestParam Integer end){
        Map<String,Integer> myList=new HashMap<>();
        myList.put("鞋子",400);
        myList.put("方便面",1);
        myList.put("衬衫",69);

        return myList;
    }


    /**
     * 第二种需要携带参数访问的get请求
     * url: ip:port/get/with/param/10/20
     */

    @RequestMapping(value = "/get/with/param/{start}/{end}")
    @ApiOperation(value = "第二种需要携带参数才能访问的get请求",httpMethod = "GET")
    public Map myGetList(@PathVariable Integer start,
                         @PathVariable Integer end){
        Map<String,Integer> myList=new HashMap<>();
        myList.put("鞋子",400);
        myList.put("方便面",1);
        myList.put("衬衫",69);

        return myList;
    }
}
