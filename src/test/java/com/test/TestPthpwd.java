package com.test;

import org.apache.commons.codec.digest.Md5Crypt;

public class TestPthpwd {

    public static void main(String[] args) {
        String pwd = Md5Crypt.apr1Crypt("123456");
        
        System.out.println(pwd);
    }
}
