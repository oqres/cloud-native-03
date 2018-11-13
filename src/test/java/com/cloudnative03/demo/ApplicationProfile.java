package com.cloudnative03.demo;


import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;


@Configuration
public class ApplicationProfile {

    private final Log log = LogFactory.getLog(getClass());

    @Bean
    static PropertySourcesPlaceholderConfigurer pspc(){
        return new PropertySourcesPlaceholderConfigurer();
    }


    @Configuration
    @Profile("prod")
    @PropertySource("some-prod.properties")
    public static class ProdConfiguration{
        @Bean
        InitializingBean init(){
            return () -> LogFactory.getLog(getClass()).info("prod InitializingBean");
        }
    }

    @Configuration
    @Profile({"default","dev"})
    @PropertySource("some.properties")
    public static class DefaultConfiguration{
        @Bean
        InitializingBean init(){
            return () -> LogFactory.getLog(getClass()).info("default InitializingBean");
        }
    }


    @Bean
    InitializingBean both(Environment env, @Value("${configuration.projectName}") String projectName){
        return () -> {
            log.info("activeProfiles : " + StringUtils.arrayToCommaDelimitedString(env.getActiveProfiles())+"'");
            log.info("configuration.projectName : "+projectName);
        };
    }

    public static void main(String[] args) {
       AnnotationConfigApplicationContext ac =  new AnnotationConfigApplicationContext();
       ac.getEnvironment().setActiveProfiles("prod");
       ac.register(ApplicationProfile.class);
       ac.refresh();
    }





}
