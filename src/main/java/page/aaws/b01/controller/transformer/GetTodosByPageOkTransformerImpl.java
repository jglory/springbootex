package page.aaws.b01.controller.transformer;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import page.aaws.b01.dto.TodoDto;
import page.aaws.b01.dto.PageDto;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GetTodosByPageOkTransformerImpl extends GetTodosByPageOkTransformer {
    @Override
    public ResponseEntity<?> process(Object... data) {
        return ResponseEntity.ok((PageDto<TodoDto>) data[0]);
    }
}
