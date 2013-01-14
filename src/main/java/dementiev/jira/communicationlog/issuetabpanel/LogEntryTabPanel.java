package dementiev.jira.communicationlog.issuetabpanel;

import dementiev.jira.communicationlog.api.LogEntry;
import dementiev.jira.communicationlog.api.LogEntryManager;
import dementiev.jira.communicationlog.util.LogEntryUtil;
import com.atlassian.jira.issue.AttachmentManager;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.IssueManager;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.issue.attachment.Attachment;
import com.atlassian.jira.issue.managers.DefaultAttachmentManager;
import com.atlassian.jira.plugin.issuetabpanel.AbstractIssueTabPanel;
import com.atlassian.jira.security.PermissionManager;
import com.atlassian.jira.security.Permissions;
import com.atlassian.plugin.webresource.WebResourceManager;
import com.opensymphony.user.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author dmitry dementiev
 */
public class LogEntryTabPanel extends AbstractIssueTabPanel {
    private LogEntryManager logEntryManager;
    private PermissionManager permissionManager;
    private WebResourceManager webResourceManager;
    private final AttachmentManager attachmentManager;
    private final IssueManager issueManager;

    public LogEntryTabPanel(LogEntryManager logEntryManager, PermissionManager permissionManager, WebResourceManager webResourceManager, DefaultAttachmentManager attachmentManager, IssueManager issueManager) {
        this.logEntryManager = logEntryManager;
        this.permissionManager = permissionManager;
        this.webResourceManager = webResourceManager;
        this.attachmentManager = attachmentManager;
        this.issueManager = issueManager;
    }

    public List<LogEntry> getActions(Issue issue, User remoteUser) {
        if (issue == null) {
            throw new IllegalArgumentException("Issue cannot be null.");
        }
        webResourceManager.requireResource(LogEntryUtil.PLUGIN_KEY + ":" + LogEntryUtil.WEBRESOURCE_KEY);//needed css and js files for expanding-collapsing single log
        List logEntryWrappers = new ArrayList();
        for (Iterator iter = logEntryManager.getLogEntries(issue.getId()).iterator(); iter.hasNext();) {
            LogEntry logEntry = (LogEntry) iter.next();
            boolean canBeReplied = false;
            Attachment reply;
            String attachmentReplyHref = "";
            if (logEntry.getType().equals(LogEntry.Type.EMAIL)) {
                List<LogEntry.Attachment> entryAttachments = logEntry.getAttachments();
                if (entryAttachments != null) {
                    for (LogEntry.Attachment attach : entryAttachments) {
                        if (attach.getContact().toLowerCase().endsWith(".msg")) {
                            canBeReplied = true;
                            attachmentReplyHref = attach.getContact();
                        }
                    }
                }
                /*       commented for test implementation
           List<LogEntry.Attachment> entryAttachments = logEntry.getAttachments();
                if (entryAttachments != null) {
                    for (LogEntry.Attachment attach : entryAttachments) {
                        reply = getRealJiraAttachment(attach, logEntry.getIssueId());
                        if (reply != null) {
                            attachmentReplyHref = LogEntryUtil.ATTACHMENT_PREFIX + reply.getId() + "/" + reply.getFilename();
                            canBeReplied = true;
                        }
                    }
                }*/
            }
            logEntryWrappers.add(new LogEntryWrapper(descriptor, logEntry, canBeReplied, attachmentReplyHref));
        }
        return logEntryWrappers;
    }

    public boolean showPanel(Issue issue, User remoteUser) {
        return hasPermission(issue, remoteUser);
    }

    private boolean hasPermission(Issue issue, User remoteUser) {
        return permissionManager.hasPermission(Permissions.VIEW_VERSION_CONTROL, issue, remoteUser);
    }

    public Attachment getRealJiraAttachment(LogEntry.Attachment attachment, Long issueId) {
        Attachment result = null;
        if (attachment != null) {
            //check MIME-TYPE, .msg, 
            MutableIssue issue = issueManager.getIssueObject(issueId);
            if (issue != null) {
                ArrayList<Attachment> list = (ArrayList<Attachment>) attachmentManager.getAttachments(issue);
                if (list != null) {
                    for (Attachment att : list) {
                        if (att != null) {
                            if (att.getId().equals(attachment.getId())
                                    /*&& (att.getMimetype().equalsIgnoreCase(LogEntryUtil.OUTLOOK_MIME_TYPE)
                                    || att.getMimetype().equalsIgnoreCase(LogEntryUtil.OCTET_MIME_TYPE))*/   //check MIME-TYPE
                                    && att.getFilename().toLowerCase().endsWith(".msg")) {
                                /*(viewable by).
                                  I didn`t find such a method in JIRA neither in @link Attacment nor in @link AttachmentManager.
                                */
                                return att;
                            }
                        }
                    }
                }
            }
        }
        return result;
    }
}
