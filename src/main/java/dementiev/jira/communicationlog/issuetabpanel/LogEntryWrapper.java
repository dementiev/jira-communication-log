package dementiev.jira.communicationlog.issuetabpanel;

import dementiev.jira.communicationlog.api.LogEntry;
import dementiev.jira.communicationlog.util.LogEntryUtil;
import com.atlassian.jira.issue.IssueManager;
import com.atlassian.jira.plugin.issuetabpanel.AbstractIssueAction;
import com.atlassian.jira.plugin.issuetabpanel.IssueTabPanelModuleDescriptor;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * @author dmitry dementiev
 */

/**
 * This class is the wrapper around the log entry object and is used when displaying log entries in the View Issue page,
 * on the 'Customer communication log' issue tab panel.
 */
public class LogEntryWrapper extends AbstractIssueAction {
    private IssueManager issueManager;
    private LogEntry logEntry;
    private boolean canBeReplied;
    private String attachmentReplyHref;
    private String convertedDate;
    private static final Logger log = Logger.getLogger(LogEntryWrapper.class);

    public LogEntryWrapper(IssueTabPanelModuleDescriptor descriptor, LogEntry logEntry, boolean canBeReplied, String attachmentReplyHref) {
        super(descriptor);
        this.logEntry = logEntry;
        Date entryDate = logEntry.getTimestamp();
        if (entryDate != null) {
            this.convertedDate = convertDate(entryDate);
        }
        this.canBeReplied = canBeReplied;
        this.attachmentReplyHref = attachmentReplyHref;
    }

    @Override
    public Date getTimePerformed() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void populateVelocityParams(Map params) {
        params.put("logEntryWrapper", this);
    }

    public LogEntry getLogEntry() {
        return logEntry;
    }

    public void setLogEntry(LogEntry logEntry) {
        this.logEntry = logEntry;
    }

    public boolean isCanBeReplied() {
        return canBeReplied;
    }

    public void setCanBeReplied(boolean canBeReplied) {
        this.canBeReplied = canBeReplied;
    }


    public IssueManager getIssueManager() {
        return issueManager;
    }

    public void setIssueManager(IssueManager issueManager) {
        this.issueManager = issueManager;
    }

    public String getAttachmentReplyHref() {
        return attachmentReplyHref;
    }

    public void setAttachmentReplyHref(String attachmentReplyHref) {
        this.attachmentReplyHref = attachmentReplyHref;
    }

    public String convertDate(Date oldDate) {
        return LogEntryUtil.LOG_DATE_FORMAT.format(oldDate);
    }

    public String getConvertedDate(){
        return convertedDate;
    }

    @Override
    public String toString() {
        return "LogEntryWrapper{" +
                "logEntry=" + logEntry +
                ", canBeReplied=" + canBeReplied +
                '}';
    }
}
