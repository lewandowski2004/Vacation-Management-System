package lewandowski.demo.Configuration;



import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.ws.rs.BeanParam;


@Configuration
public class WebConfig implements WebMvcConfigurer{

    private static final String RESOURCE_HANDLER_WEBJARS = "/webjars/**";
    private static final String[] RESOURCE_LOCATIONS_WEBJARS = {
            "classpath:/META-INF/resources/webjars/"};

    private static final String RESOURCE_HANDLER_OTHERS = "/*";
    private static final String[] RESOURCE_LOCATIONS_OTHERS = {
            "classpath:/resources/",
            "classpath:/static/"};

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler(RESOURCE_HANDLER_WEBJARS)
                .addResourceLocations(RESOURCE_LOCATIONS_WEBJARS);
        registry
                .addResourceHandler(RESOURCE_HANDLER_OTHERS)
                .addResourceLocations(RESOURCE_LOCATIONS_OTHERS);

    }

   @Bean
    public BCryptPasswordEncoder pwdEncrypt() {
        BCryptPasswordEncoder bcp = new BCryptPasswordEncoder();
        return bcp;
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("messages");
        source.setDefaultEncoding("utf-8");
        return source;
    }

    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource());
        return validator;
    }


}