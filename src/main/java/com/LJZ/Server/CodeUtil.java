package com.LJZ.Server;

import java.util.UUID;

class CodeUtil {
    //生成唯一的激活码
    static String generateUniqueCode(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}