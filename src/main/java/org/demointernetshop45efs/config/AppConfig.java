package org.demointernetshop45efs.config;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Builder;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import freemarker.cache.ClassTemplateLoader;
import freemarker.core.HTMLOutputFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public freemarker.template.Configuration freemakerConfiguration() {
        freemarker.template.Configuration configuration = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_21);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateLoader(new ClassTemplateLoader(AppConfig.class, "/mail/"));
        return configuration;
    }

    @Bean
    public AmazonS3 amazonS3(){
        // необходимо задать конфигурационные параметры для аутентификации нашего приложения на DigitalOcean

        String accessKey = "DO801NQZH36AYKEDAG2K";
        String secretKey = "eCABKUS+0GQj6CfonjmQXXX8DxSB2U5PtE/gQfIP5BA";
        String endpoint = "https://fra1.digitaloceanspaces.com";
        String region = "fra1";

        // создать экземпляр класса, который мы будем использовать для аутентификации

        AWSCredentials credentials = new BasicAWSCredentials(
                accessKey,
                secretKey
        );
        // настройка точки подключения к хранилищу

        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(
                endpoint,
                region
        );

        // создать клиент для загрузки файлов

        AmazonS3ClientBuilder amazonS3ClientBuilder = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials));

        amazonS3ClientBuilder.setEndpointConfiguration(endpointConfiguration);

        AmazonS3 amazonS3 = amazonS3ClientBuilder.build();
        //клиент необходим для amazon / digital ocean -
        // экземпляр класса, который содержит в определенном формате ВСЮ информацию о месте подключения и правах доступа

        return amazonS3;

    }

}
