package dementiev.jira.communicationlog.api;

/**
 * @author Dmitry Dementiev
 */
public class LogEntryOperationFailedException extends Exception {
    public LogEntryOperationFailedException(String message) {
        super(message);
    }

    public LogEntryOperationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
