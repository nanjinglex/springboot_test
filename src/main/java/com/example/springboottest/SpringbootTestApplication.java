package com.example.springboottest;


import com.example.springboottest.springboot2.Dog;
import com.example.springboottest.springboot2.MyCongfig;
import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.helpers.LogLog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

@SpringBootApplication
@EnableConfigurationProperties
//@ConditionalOnClass(SpringfoxWebMvcConfiguration.class)
//@EnableAspectJAutoProxy(proxyTargetClass = true)
//@ComponentScan(basePackages={"com.example"}) // 扫描该包路径下的所有spring组件
//@Import(SpringUtil.class)
@Slf4j
public class SpringbootTestApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext run = SpringApplication.run(SpringbootTestApplication.class, args);

        String[] beanDefinitionNames = run.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
        //@Configuration(proxyBeanMethods = false)
        Object name1 = run.getBean("dogName");
        Object name2 = run.getBean("dogName");
        System.out.println(name1 == name2);//true

        Object myPet1 = run.getBean("myDog");
        Object myPet2 = run.getBean("myDog");
        System.out.println(myPet1==myPet2);//true

        MyCongfig bean = run.getBean(MyCongfig.class);
        String dogName1 = bean.dogName();
        String dogName2 = bean.dogName();
        System.out.println(dogName1==dogName2);//false

        Dog dog1 = bean.myDog();
        Dog dog2 = bean.myDog();
        System.out.println(dog1==dog2);//false


        //Import
        String[] beanNamesForType = run.getBeanNamesForType(Dog.class);
        Arrays.stream(beanNamesForType).forEach(System.out::println);

        System.out.println(run.getBean(LogLog.class));

        //@Conditional
        System.out.println("是否包含myCat："+run.containsBean("myCat"));

        boolean catName = run.containsBean("catName");
        System.out.println("是否包含bean catName:"+catName);

        //@ImportResource("classpath:beans.xml")
        System.out.println("是否包含bean haha:"+run.containsBean("haha"));



        Environment env = run.getEnvironment();
        log.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\thttp://localhost:{}\n\t" +
                        "External: \thttp://{}:{}\n\t" +
                        "Doc: \thttp://{}:{}/doc.html\n" +
                        "Doc: \thttp://{}:{}/swagger-ui.html#\n" +
                        "----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"));
    }

//    @Bean
//    public SpringUtil getSpringUtil() {
//        return new SpringUtil();
//    }

}
