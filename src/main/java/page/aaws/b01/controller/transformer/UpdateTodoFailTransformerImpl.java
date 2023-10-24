package page.aaws.b01.controller.transformer;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UpdateTodoFailTransformerImpl extends UpdateTodoFailTransformer {
    @Override
    public ResponseEntity<?> process(Object... data) {
        Exception exception = (Exception) data[0];
        HttpStatusCode httpStatusCode = (HttpStatusCode) data[1];

        Map<String, String> response = new HashMap<>();
        response.put("result", "fail");
        response.put("message", exception.getMessage());

        return new ResponseEntity<>(response, httpStatusCode);
    }
}
