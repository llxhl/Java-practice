package cn.lxh.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@SpringBootConfiguration
//@EnableAutoConfiguration
//@ComponentScan
@EnableScheduling//开启定时任务
//引导类
public class Application {
    public static void main(String[] args) {
        //自己              主函数传进来的参数
        SpringApplication.run(Application.class, args);
    }
}
