package page.aaws.b01.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import page.aaws.b01.controller.transformer.AddNewTodoFailTransformer;
import page.aaws.b01.controller.transformer.AddNewTodoFailTransformerImpl;
import page.aaws.b01.controller.transformer.AddNewTodoOkTransformer;
import page.aaws.b01.controller.transformer.AddNewTodoOkTransformerImpl;
import page.aaws.b01.controller.transformer.DeleteTodoFailTransformer;
import page.aaws.b01.controller.transformer.DeleteTodoFailTransformerImpl;
import page.aaws.b01.controller.transformer.DeleteTodoOkTransformer;
import page.aaws.b01.controller.transformer.DeleteTodoOkTransformerImpl;
import page.aaws.b01.controller.transformer.GetTodoFailTransformer;
import page.aaws.b01.controller.transformer.GetTodoFailTransformerImpl;
import page.aaws.b01.controller.transformer.GetTodoOkTransformer;
import page.aaws.b01.controller.transformer.GetTodoOkTransformerImpl;
import page.aaws.b01.controller.transformer.GetTodosByPageFailTransformer;
import page.aaws.b01.controller.transformer.GetTodosByPageFailTransformerImpl;
import page.aaws.b01.controller.transformer.GetTodosByPageOkTransformer;
import page.aaws.b01.controller.transformer.GetTodosByPageOkTransformerImpl;
import page.aaws.b01.controller.transformer.UpdateTodoFailTransformer;
import page.aaws.b01.controller.transformer.UpdateTodoFailTransformerImpl;
import page.aaws.b01.controller.transformer.UpdateTodoOkTransformer;
import page.aaws.b01.controller.transformer.UpdateTodoOkTransformerImpl;

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
    public AddNewTodoOkTransformer addNewTodoOkTransformer() {
        return new AddNewTodoOkTransformerImpl();
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public AddNewTodoFailTransformer addNewTodoFailTransformer() {
        return new AddNewTodoFailTransformerImpl();
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public DeleteTodoOkTransformer deleteTodoOkTransformer() {
        return new DeleteTodoOkTransformerImpl();
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public DeleteTodoFailTransformer deleteTodoFailTransformer() {
        return new DeleteTodoFailTransformerImpl();
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public GetTodoOkTransformer getTodoOkTransformer() {
        return new GetTodoOkTransformerImpl();
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public GetTodoFailTransformer getTodoFailTransformer() {
        return new GetTodoFailTransformerImpl();
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

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public UpdateTodoOkTransformer updateTodoOkTransformer() {
        return new UpdateTodoOkTransformerImpl();
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public UpdateTodoFailTransformer updateTodoFailTransformer() {
        return new UpdateTodoFailTransformerImpl();
    }
}
