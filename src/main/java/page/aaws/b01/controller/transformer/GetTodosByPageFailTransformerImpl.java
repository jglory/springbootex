package page.aaws.b01.controller.transformer;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class GetTodosByPageFailTransformerImpl extends FailTransformerImpl {
    public GetTodosByPageFailTransformerImpl(HttpStatusCode httpStatusCode, Exception exception) {
        super(httpStatusCode, exception);
    }

    @Override
    public ResponseEntity<?> process() {
        Map<String, String> response = new HashMap<>();
        response.put("result", "fail");
        response.put("message", this.getException().getMessage());

        return new ResponseEntity<>(response, this.getHttpStatusCode());
    }
}
