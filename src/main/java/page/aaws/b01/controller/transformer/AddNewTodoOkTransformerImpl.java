package page.aaws.b01.controller.transformer;

import org.springframework.http.ResponseEntity;
import page.aaws.b01.dto.TodoDto;

public class AddNewTodoOkTransformerImpl extends AddNewTodoOkTransformer {
    @Override
    public ResponseEntity<?> process(Object... data) {
        return ResponseEntity.ok((TodoDto) data[0]);
    }
}
