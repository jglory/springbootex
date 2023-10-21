package page.aaws.b01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
public class HttpTransformerConfig {
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
}
