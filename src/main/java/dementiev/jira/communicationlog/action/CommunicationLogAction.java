package dementiev.jira.communicationlog.action;

import dementiev.jira.communicationlog.api.LogEntry;
import dementiev.jira.communicationlog.api.LogEntryManager;
import dementiev.jira.communicationlog.api.LogEntryOperationFailedException;
import dementiev.jira.communicationlog.util.I18n;
import dementiev.jira.communicationlog.util.LogEntryUtil;
import com.atlassian.jira.exception.IssueNotFoundException;
import com.atlassian.jira.exception.IssuePermissionException;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.security.Permissions;
import com.atlassian.jira.util.I18nHelper;
import com.atlassian.jira.web.action.issue.AbstractIssueSelectAction;
import com.atlassian.plugin.webresource.WebResourceManager;
import com.opensymphony.util.TextUtils;

import java.util.Date;
import java.util.Locale;

/**
 * @author dmitry dementiev
 */
public class CommunicationLogAction extends AbstractIssueSelectAction implements I18n {
    private final static String BUNDLE_NAME = "dementiev.communicationlog.TextResource";
    private final I18nHelper i18Helper;
    private final LogEntryManager logEntryManager;
    //Log entry fields
    private String summary;
    private String direction;
    private String type;
    private String noteType;
    private String notes;

    public CommunicationLogAction(JiraAuthenticationContext authenticationContext, LogEntryManager logEntryManager, WebResourceManager webResourceManager) {
        this.logEntryManager = logEntryManager;
        webResourceManager.requireResource(LogEntryUtil.PLUGIN_KEY + ":" + LogEntryUtil.WEBRESOURCE_KEY);
        i18Helper = authenticationContext.getI18nHelper(BUNDLE_NAME);
    }

    public String doDefault() throws Exception {
        try {
            getIssueObject();
        }
        catch (IssueNotFoundException e) {
            // Error is added above
            return ERROR;
        }
        catch (IssuePermissionException e) {
            return ERROR;
        }

        return super.doDefault();
    }

    public String doExecute() {
        try {
            LogEntry logEntry = new LogEntry();
            logEntry.setSummary(getSummary());
            logEntry.setDirection(LogEntry.Direction.valueOf(getDirection().toUpperCase()));
            logEntry.setType(LogEntry.Type.valueOf(getType().toUpperCase()));
            logEntry.setNoteType(LogEntry.NoteType.valueOf(getNoteType().toUpperCase()));
            logEntry.setNotes(getNotes());
            logEntry.setTimestamp(new Date());
            logEntry.setIssueId(getIssueObject().getId());
            logEntryManager.addLogEntry(logEntry);
        } catch (LogEntryOperationFailedException e) {
            log.error(e);
        }
        return returnComplete("/browse/" + getIssue().getString("key"));
    }

    protected void doValidation() {
        if (!isHasIssuePermission(Permissions.BROWSE, getIssueObject().getGenericValue())) {
            addErrorMessage(getText("addlogEntry.error.nopermission"));
        }
        if (!(TextUtils.stringSet(getSummary().trim()) &&
                TextUtils.stringSet(getDirection().trim()) &&
                TextUtils.stringSet(getType().trim()) &&
                TextUtils.stringSet(getNotes().trim()) &&
                TextUtils.stringSet(getNoteType().trim())
        )) {
            addErrorMessage("Please, fill all the fields.");
        }
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNoteType() {
        return noteType;
    }

    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }

    public String getNotes() {
        return notes;     
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * This method returns Html Encoded Text from recource
     *
     * @param key
     * @return
     */
    public String getHtmlEncodedText(String key) {
        String txt = getText(key);
        if (TextUtils.stringSet(txt))
            return "";
        else
            return TextUtils.htmlEncode(txt).replace("'", "&#39;").replace(System.getProperty("line.separator"), "&#10;");
    }

    /**
     * @param key
     * @param locale
     * @return text from resource
     */
    public String getText(String key, Locale locale) {
        return i18Helper.getText(key, locale);
    }
}
