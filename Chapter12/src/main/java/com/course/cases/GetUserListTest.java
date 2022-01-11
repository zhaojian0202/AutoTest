package com.course.cases;


import com.course.model.User;
import com.course.config.TestConfig;
import com.course.model.GetUserListCase;
import com.course.utils.DatabaseUtil;
import com.mongodb.util.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GetUserListTest {

    @Test(dependsOnGroups = "loginTrue" ,description = "获取用户列表接口测试")
    public void getUserList() throws IOException {
        SqlSession session= DatabaseUtil.getSqlSession();
        GetUserListCase getUserListCase=session.selectOne("getUserListCase",1);
        System.out.println(getUserListCase.toString());
        System.out.println(TestConfig.getUserListUrl);

        //获取结果
        JSONArray resultJson=getJsonResult(getUserListCase);
        //验证
        List<User> userList=session.selectList("getUserList",getUserListCase);
        for (User u:userList){
            System.out.println("获取的user："+u.toString());
        }
        JSONArray userListJson= new JSONArray(resultJson.toString());
        Assert.assertEquals(userListJson.length(),resultJson.length());
//        for (int i=0;i<resultJson.size();i++){
//            JSONObject expect=(JSONObject) resultJson.get(i);
//            JSONObject actual=userListJson.getJSONObject(i);
//            Assert.assertEquals(expect.toString(),actual.toString());
//        }
    }

    private JSONArray getJsonResult(GetUserListCase getUserListCase) throws IOException {

        HttpPost post=new HttpPost(TestConfig.getUserListUrl);
        JSONObject param=new JSONObject();
        param.put("userName",getUserListCase.getUserName());
        param.put("sex",getUserListCase.getSex());
        param.put("age",getUserListCase.getAge());

        post.setHeader("content-type","application/json");
        StringEntity entity=new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        TestConfig.defaultHttpClient.setCookieStore(TestConfig.cookieStore);
        String result;
        HttpResponse response=TestConfig.defaultHttpClient.execute(post);
        result= EntityUtils.toString(response.getEntity(),"utf-8");
        List resultList= Arrays.asList(result);
        JSONArray jsonArray=new JSONArray(resultList);
        return jsonArray;
    }
}
