package com.wd.cloud.docdelivery;


import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * DocDeliveryApplication class
 *
 * @author hezhigang
 * @date 2018/04/08
 */
@EnableScheduling
@EnableJpaAuditing
@EnableSwagger2Doc
@EnableRedisHttpSession
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.wd.cloud.apifeign"})
public class DocDeliveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocDeliveryApplication.class, args);
        System.out.println("ヾ(◍°∇°◍)ﾉﾞ    -文献传递服务启动成功      ヾ(◍°∇°◍)ﾉﾞ\n" +
                " *                             _ooOoo_\n" +
                " *                            o8888888o\n" +
                " *                            88\" . \"88\n" +
                " *                            (| -_- |)\n" +
                " *                            O\\  =  /O\n" +
                " *                         ____/`---'\\____\n" +
                " *                       .'  \\\\|     |//  `.\n" +
                " *                      /  \\\\|||  :  |||//  \\\n" +
                " *                     /  _||||| -:- |||||-  \\\n" +
                " *                     |   | \\\\\\  -  /// |   |\n" +
                " *                     | \\_|  ''\\---/''  |   |\n" +
                " *                     \\  .-\\__  `-`  ___/-. /\n" +
                " *                   ___`. .'  /--.--\\  `. . __\n" +
                " *                .\"\" '<  `.___\\_<|>_/___.'  >'\"\".\n" +
                " *               | | :  `- \\`.;`\\ _ /`;.`/ - ` : | |\n" +
                " *               \\  \\ `-.   \\_ __\\ /__ _/   .-` /  /\n" +
                " *          ======`-.____`-.___\\_____/___.-`____.-'======\n" +
                " *                             `=---='\n" +
                " *          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n" +
                " *                           佛祖保佑,永无BUG\n"+
                " *          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n" +
                " *                     不知道此生，我将创造多少BUG\n" +
                " *                     也不知道今世，我会手刃bug几何\n" +
                " *                  但是，我知道,你用等待，守候我晚归的夜");
    }

}
