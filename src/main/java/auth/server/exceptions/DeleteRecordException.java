package auth.server.exceptions;

import java.io.Serial;

public class DeleteRecordException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;
    public DeleteRecordException(String message) {
        super(message);
    }
}
