package page.aaws.b01.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import page.aaws.b01.controller.transformer.GetTodosByPageFailTransformer;
import page.aaws.b01.controller.transformer.GetTodosByPageFailTransformerImpl;
import page.aaws.b01.controller.transformer.GetTodosByPageOkTransformer;
import page.aaws.b01.controller.transformer.GetTodosByPageOkTransformerImpl;

@Configuration
public class RootConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public GetTodosByPageOkTransformer getTodosByPageOkTransformer() {
        return new GetTodosByPageOkTransformerImpl();
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public GetTodosByPageFailTransformer getTodosByPageFailTransformer() {
        return new GetTodosByPageFailTransformerImpl();
    }
}
