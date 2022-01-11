package com.course.cases;

import com.course.model.User;
import com.course.config.TestConfig;
import com.course.model.UpdateUserInfoCase;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class UpdateUserInfoTest {

    @Test(dependsOnGroups = "loginTrue",description = "更新用户信息接口测试")
    public void updateUserInfo() throws IOException {
        SqlSession session= DatabaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase=session.selectOne("updateUserInfoCase",2);
        System.out.println(updateUserInfoCase.toString());
        System.out.println(TestConfig.updateUserInfoUrl);

        //发送请求
        int result=getResult(updateUserInfoCase);

        User user=session.selectOne("updateUserInfo",updateUserInfoCase);

        //验证结果
        Assert.assertNotNull(user);
        Assert.assertNotNull(result);


    }

    private int getResult(UpdateUserInfoCase updateUserInfoCase) throws IOException {

        HttpPost post=new HttpPost(TestConfig.updateUserInfoUrl);
        JSONObject param=new JSONObject();
        param.put("id",updateUserInfoCase.getUserId());
        param.put("userName",updateUserInfoCase.getUserName());
        param.put("sex",updateUserInfoCase.getSex());
        param.put("password",updateUserInfoCase.getPassword());
        param.put("age",updateUserInfoCase.getAge());
        param.put("permission",updateUserInfoCase.getPermission());
        param.put("isDelete",updateUserInfoCase.getIsDelete());

        post.setHeader("content-type","application/json");
        StringEntity entity=new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        TestConfig.defaultHttpClient.setCookieStore(TestConfig.cookieStore);
        HttpResponse response=TestConfig.defaultHttpClient.execute(post);
        String result;
        result= EntityUtils.toString(response.getEntity());

        return Integer.parseInt(result);


    }

    @Test(dependsOnGroups = "loginTrue",description = "删除用户信息")
    public void deleteUser() throws IOException {
        SqlSession session= DatabaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase=session.selectOne("updateUserInfoCase",1);
        System.out.println(updateUserInfoCase.toString());
        System.out.println(TestConfig.updateUserInfoUrl);
    }

}
