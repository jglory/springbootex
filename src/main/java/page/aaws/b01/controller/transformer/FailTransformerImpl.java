package page.aaws.b01.controller.transformer;

import lombok.Getter;

import org.springframework.http.HttpStatusCode;
import page.aaws.b01.transformer.Transformer;

@Getter
public abstract class FailTransformerImpl implements Transformer {
    private HttpStatusCode httpStatusCode;
    private Exception exception;

    public FailTransformerImpl(HttpStatusCode httpStatusCode, Exception exception) {
        this.httpStatusCode = httpStatusCode;
        this.exception = exception;
    }
}
