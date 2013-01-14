package dementiev.jira.communicationlog.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author dmitry dementiev
 */
public class LogEntryUtil {
    public static final String PLUGIN_KEY = "dementiev.jira.communication-log";
    public static final String WEBRESOURCE_KEY = "logentry-resources";
    public static final String OUTLOOK_MIME_TYPE = "application/vnd.ms-outlook";//http://www.w3schools.com/media/media_mimeref.asp
    public static final String OCTET_MIME_TYPE = "application/octet-stream";
    public static final DateFormat LOG_DATE_FORMAT = new SimpleDateFormat("MMMMM dd - HH:mm a", Locale.US);
    public static final String ATTACHMENT_PREFIX = "/secure/attachment/";// should be ended with attachment.id/attachment.Filename

}
