package dementiev.jira.communicationlog.api;

import java.util.List;

/**
 * @author Dmitry Dementiev
 */
public interface LogEntryManager {
    /**
     * Retrieves all log entries for the given issue.
     * <p>
     * The list is returned in no particular order.
     *
     * @param issueId the issue's ID, may be null
     * @return a list of log entries, or empty list, not null 
     */
    List<LogEntry> getLogEntries(Long issueId);

    /**
     * Creates a new log entry. The new log entry must have issueId set, but id must be null.
     *
     * @param entry new entry record
     * @throws LogEntryOperationFailedException if there's a problem adding entry
     */
    void addLogEntry(LogEntry entry) throws LogEntryOperationFailedException;
}
