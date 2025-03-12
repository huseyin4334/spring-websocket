package org.example.springwebsocket.config;

import org.apache.catalina.Container;
import org.apache.catalina.Wrapper;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/jsp/", ".jsp");
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix("/WEB-INF/jsp/");
        internalResourceViewResolver.setSuffix(".jsp");
        registry.viewResolver(internalResourceViewResolver);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> servletContainerCustomizer() {
        return new WebServerFactoryCustomizer<>() {

            @Override
            public void customize(ConfigurableServletWebServerFactory container) {
                if (container != null) {
                    customizeTomcat((TomcatServletWebServerFactory) container);
                }
            }

            private void customizeTomcat(TomcatServletWebServerFactory tomcatFactory) {
                tomcatFactory.addContextCustomizers(context -> {
                    Container jsp = context.findChild("jsp");
                    if (jsp instanceof Wrapper wrapper) {
                        wrapper.addInitParameter("development", "true");
                    }
                });
            }
        };
    }
}
