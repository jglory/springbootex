package page.aaws.b01;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import page.aaws.b01.controller.transformer.*;
import page.aaws.b01.cqrs.CommandAndQueryFactory;
import page.aaws.b01.controller.cqrs.HttpServletRequestToCommandAndQueryFactoryImpl;
import page.aaws.b01.util.SnowflakeIdGenerator;

@TestConfiguration
public class TestConfig {
    @Bean
    public CommandAndQueryFactory commandAndQueryFactory() {
        return new HttpServletRequestToCommandAndQueryFactoryImpl();
    }

    @Bean
    public AddNewTodoOkTransformer addNewTodoOkTransformer() {
        return new AddNewTodoOkTransformerImpl();
    }
    @Bean
    public AddNewTodoFailTransformer addNewTodoFailTransformer() {
        return new AddNewTodoFailTransformerImpl();
    }

    @Bean
    public DeleteTodoOkTransformer deleteTodoOkTransformer() {
        return new DeleteTodoOkTransformerImpl();
    }
    @Bean
    public DeleteTodoFailTransformer deleteTodoFailTransformer() {
        return new DeleteTodoFailTransformerImpl();
    }

    @Bean
    public GetTodoOkTransformer getTodoOkTransformer() {
        return new GetTodoOkTransformerImpl();
    }
    @Bean
    public GetTodoFailTransformer getTodoFailTransformer() {
        return new GetTodoFailTransformerImpl();
    }

    @Bean
    public GetTodosByPageOkTransformer getTodosByPageOkTransformer() {
        return new GetTodosByPageOkTransformerImpl();
    }
    @Bean
    public GetTodosByPageFailTransformer getTodosByPageFailTransformer() {
        return new GetTodosByPageFailTransformerImpl();
    }

    @Bean
    public UpdateTodoOkTransformer updateTodoOkTransformer() {
        return new UpdateTodoOkTransformerImpl();
    }
    @Bean
    public UpdateTodoFailTransformer updateTodoFailTransformer() {
        return new UpdateTodoFailTransformerImpl();
    }

    @Bean
    public SnowflakeIdGenerator snowflakeIdGenerator() {
        return new SnowflakeIdGenerator();
    }
}
