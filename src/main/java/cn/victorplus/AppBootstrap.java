package cn.victorplus;

import cn.victorplus.service.CodeGenService;
import cn.victorplus.util.SpringBeanUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppBootstrap {

    public static void main(String[] args) throws Exception {
        SpringBeanUtil.context = SpringApplication.run(AppBootstrap.class, args);
        CodeGenService codeGenService = SpringBeanUtil.getBean(CodeGenService.class);

        codeGenService.genCode();

    }
}
