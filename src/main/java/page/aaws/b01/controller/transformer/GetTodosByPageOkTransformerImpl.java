package page.aaws.b01.controller.transformer;

import org.springframework.http.ResponseEntity;
import page.aaws.b01.dto.TodoDto;
import page.aaws.b01.dto.PageDto;

public class GetTodosByPageOkTransformerImpl extends GetTodosByPageOkTransformer {
    @Override
    public ResponseEntity<?> process(Object... data) {
        return ResponseEntity.ok((PageDto<TodoDto>) data[0]);
    }
}
