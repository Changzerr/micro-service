package cn.changzer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.service.Contact;

@Configuration
@EnableSwagger2
public class SwaggerAutoConfiguration {
    @Bean
    public Docket createRestApi1() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())//.groupName("用户接口组")
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("cn.changzer.controller"))
                .build();
        return docket;
    }

    //@Bean
    //public Docket createRestApi2() {
    //    Docket docket = new Docket(DocumentationType.SWAGGER_2)
    //            .apiInfo(apiInfo()).groupName("菜单接口组")
    //            .select()
    //            //为当前包路径
    //            .apis(RequestHandlerSelectors.basePackage("cn.changzer.controller.menu"))
    //            .build();
    //    return docket;
    //}

    //构建 api文档的详细信息
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("API接口文档")
                //创建人
                .contact(new Contact("长泽r", "https://changzer.gitee.io/", ""))
                //版本号
                .version("1.0")
                //描述
                .description("API 描述")
                .build();
    }
}
