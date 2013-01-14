package dementiev.jira.communicationlog.service;

import dementiev.jira.communicationlog.api.LogEntry;
import dementiev.jira.communicationlog.api.LogEntryManager;
import dementiev.jira.communicationlog.api.LogEntryOperationFailedException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A sample implementation of the LogEntryManager for testing purposes.
 *
 * @author Dmitry Dementiev
 */
public class SampleLogEntryManager implements LogEntryManager {
    // one list for all issues
    private final List<LogEntry> list = new ArrayList<LogEntry>();
    private long sequence;

    public SampleLogEntryManager() {
        try {
            LogEntry e = new LogEntry();

            e.setIssueId(324L);
            e.setDirection(LogEntry.Direction.IN);
            e.setType(LogEntry.Type.WEB);
            e.setNoteType(LogEntry.NoteType.EXTERNAL);
            e.setTimestamp(new Date(System.currentTimeMillis() - 86400000L));
            e.setSummary("Wikipedia");
            e.setNotes("Wikipedia is a free,[3] web-based, collaborative, multilingual encyclopedia project supported by the non-profit Wikimedia Foundation. Its 16 million articles (over 3.4 million in English) have been written collaboratively by volunteers around the world, and almost all of its articles can be edited by anyone with access to the site.[4] Wikipedia was launched in 2001 by Jimmy Wales and Larry Sanger[5] and is currently the largest and most popular general reference work on the Internet,[2][6][7][8] ranked 7th among all websites on Alexa.[9]");
            addLogEntry(e);

            e.setIssueId(178135L);
            e.setType(LogEntry.Type.EMAIL);
            e.setNoteType(LogEntry.NoteType.INTERNAL);
            e.setTimestamp(new Date(System.currentTimeMillis() - 43200000L));
            e.setSummary("Please implement the plugin");
            e.setNotes("We will use the already known external mySQL-Database (product selection plugin) to add the needed database tables.\n\n" +
                "Currently, we've set up the following database tables with sample data:\n" +
                "CREATE TABLE 'HdtActivity' (\n" +
                "  'ActivityId' int(11) NOT NULL auto_increment,");
            ArrayList<LogEntry.Attachment> atts = new ArrayList<LogEntry.Attachment>();
            LogEntry.Attachment att= new LogEntry.Attachment();
            att.setId(1L);
            att.setContact("http://dl.dropbox.com/u/2171206/testmessage.msg");
            atts.add(att);
            LogEntry.Attachment att1= new LogEntry.Attachment();
            att1.setId(134L);
            att1.setContact("attachemnt");
            atts.add(att1);
            e.setAttachments(atts);
            addLogEntry(e);
        } catch (LogEntryOperationFailedException e1) {
            // ignore
        }
    }

    public synchronized final void addLogEntry(LogEntry entry) throws LogEntryOperationFailedException {
        if (entry.getId() != null) throw new LogEntryOperationFailedException("new entry should not have id");
        if (entry.getIssueId() == null) throw new LogEntryOperationFailedException("new entry is not linked to any issue");
        LogEntry e = entry.clone();
        e.setId(++sequence);
        list.add(e);
    }

    public synchronized List<LogEntry> getLogEntries(Long issueId) {
        return new ArrayList<LogEntry>(list);
    }
}
