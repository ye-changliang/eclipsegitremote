package com.tydic.bigdata.ssh;

import java.util.Random;

public class Test {
    public static void main(String[] args) {
        int radom= (int) ((Math.random()*9+1)*10000);
        System.out.println(radom);
        SSHClient  client = SSHClient.getInstance("134.225.72.14","tydk","1qaz#EDC");
        //System.out.println(client.execCmdWithBack("ps -ef | grep apache-tomcat-7.0.52-test|grep -v grep|awk '{print \"kill -9 \"$2}'|sh"));
        System.out.println(client.execCmdWithBack("/hadoop/cloud_platform/cloud_basic_service/tomcat/apache-tomcat-7.0.52-test/bin/startup.sh"));
        System.out.println(client.execCmdWithBack("cd /hadoop/cloud_platform/cloud_basic_service/tomcat/apache-tomcat-7.0.52-test/bin \n ./startup.sh"));
    }
}
