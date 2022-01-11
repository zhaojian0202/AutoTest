package com.course.testng.parameter;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class DataProviderTest {

    @Test(dataProvider = "data")
    public void testDataProvider(String name,int age){
        System.out.println("name ="+name+";"+"age ="+age);
    }

    @DataProvider(name="data")
    public Object[][] provideData() {
        Object[][] o = new Object[][]{
                {"zhangsan",10},
                {"lisi",12},
                {"xiaoming",18}

        };
        return o;
    }

    @Test(dataProvider = "methodData")
    public void test1(String name,int age){
        System.out.println("test1方法 name="+name+";age="+age);

    }
    @Test(dataProvider = "methodData")
    public void test2(String name,int age){
        System.out.println("test2方法 name="+name+";age="+age);

    }

    @DataProvider(name ="methodData")
    public Object[][] methodDateTest(Method method){
        Object[][] result=null;
        if (method.getName().equals("test1")){
            result=new Object[][]{
                    {"haha",1},
                    {"meimei",2}
            };
        }
        else if (method.getName().equals("test2")){
            result=new Object[][]{
                    {"ee",3},
                    {"kkkk",4}
            };
        }
        return result;
    }
}
