package com.course.server;

import com.course.bean.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Api(value = "/")
@RequestMapping("/v1")
public class MyPostMethod {

    //存储cooKie的变量
    private static Cookie cookie;

    //用户登录成功获取cookie,然后再访问其他接口获取列表

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value = "登录接口，成功后返回cookie信息",httpMethod = "POST")
    public String login(HttpServletResponse response,
                        @RequestParam(value = "userName",required = true) String userName,
                        @RequestParam(value = "password",required = true)String password){
        if (userName.equals("zhangsan") && password.equals("123456")){
            cookie=new Cookie("login","true");
            response.addCookie(cookie);
            return "恭喜你登录成功了";
        }
        return "用户名或密码错误";


    }

    //获取列表接口,携带cookie,并验证
    @RequestMapping(value = "/getUserList",method = RequestMethod.POST)
    @ApiOperation(value = "获取用户列表",httpMethod = "POST")
    public String getUserList(HttpServletRequest request,
                            @RequestBody User u){
        //获取cookies
        Cookie[] cookies=request.getCookies();
        //验证cookie是否合法
        for(Cookie cookie:cookies){
            if (cookie.getName().equals("login")
                    && cookie.getValue().equals("true")
                    && u.getUserName().equals("zhangsan")
                    && u.getPassword().equals("123456")){
                User user=new User();
                user.setName("lisi");
                user.setAge("19");
                user.setSex("man");
                return user.toString();

            }
        }

        return "参数不合法";

    }


}
