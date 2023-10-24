package page.aaws.b01.controller.transformer;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class DeleteTodoOkTransformerImpl extends DeleteTodoOkTransformer {
    @Override
    public ResponseEntity<?> process(Object... data) {
        return ResponseEntity.ok().build();
    }
}
