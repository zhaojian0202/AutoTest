package com.course.model;

import lombok.Data;

@Data
public class User {

    private int id;
    private String userName;
    private String age;
    private String sex;
    private String permission;
    private String isDelete;
    private String password;

    @Override
    public String toString(){
        return (
                "{id:"+id+","+
                "userName:"+userName+","+
                "password:"+password+","+
                "age:"+age+","+
                "sex:"+sex+","+
                "permission:"+permission+","+
                "idDelete:"+isDelete+"}"
                );
    }
}
