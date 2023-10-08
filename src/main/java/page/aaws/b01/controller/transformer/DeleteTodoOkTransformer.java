package page.aaws.b01.controller.transformer;

import org.springframework.http.ResponseEntity;

public class DeleteTodoOkTransformer extends OkTransformer {
    @Override
    public ResponseEntity<?> process() {
        return ResponseEntity.ok().build();
    }
}
