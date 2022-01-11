package com.course.controller;

import com.course.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//Log4j
@RestController
@Api(value = "/v1",description = "这是我的第一个版本的demo")
@RequestMapping("/v1")
public class Demo {

    //首先获取一个执行SQL语句的对象
    @Autowired
    private SqlSessionTemplate template;

    @RequestMapping(value = "/getUserCount",method = RequestMethod.GET)
    @ApiOperation(value = "获取用户数量",httpMethod = "GET")
    public int getUserCount(){

        return template.selectOne("getUserCount");

    }

    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    @ApiOperation(value = "添加用户",httpMethod = "POST")
    public int addUser(@RequestBody User user){
        int result=template.insert("addUser",user);
        return result;
    }

    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    @ApiOperation(value = "更新用户信息",httpMethod = "POST")
    public int updateUser(@RequestBody User user){
        int result=template.insert("updateUser",user);
        return result;
    }

    @RequestMapping(value = "/deleteUser",method = RequestMethod.GET)
    @ApiOperation(value = "删除用户信息",httpMethod = "GET")
    public int delUser(@RequestParam Integer id){
        int result=template.delete("delUser",id);
        return result;
    }

}
