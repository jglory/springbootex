package page.aaws.b01.cqrs;

public class CommandAndQueryCreationException extends RuntimeException {
    public CommandAndQueryCreationException() {
    }

    public CommandAndQueryCreationException(String message) {
        super(message);
    }

    public CommandAndQueryCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandAndQueryCreationException(Throwable cause) {
        super(cause);
    }

    public CommandAndQueryCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
