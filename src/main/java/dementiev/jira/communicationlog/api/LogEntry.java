package dementiev.jira.communicationlog.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class represents an entry of the communications log, shown in the Communications tab.
 * 
 *
 * @author Dmitry Dementiev
 */
public class LogEntry implements Cloneable {
    /**
     * The unique ID of the entry.
     */
    private Long id;

    /**
     * The ID of the issue the entry is attached to
     */
    private Long issueId;

    /**
     * Type of the communication
     */
    private Type type;

    /**
     * Direction of the communication
     */
    private Direction direction;

    /**
     * Summary line 
     */
    private String summary;

    /**
     * Date/time of the communication
     */
    private Date timestamp;

    /**
     * Possibly long text with notes
     */
    private String notes;

    /**
     * Internal or External?
     */
    private NoteType noteType;

    /**
     * A list of JIRA attachments that are related to this entry
     */
    private List<Attachment> attachments;


    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public NoteType getNoteType() {
        return noteType;
    }

    public void setNoteType(NoteType noteType) {
        this.noteType = noteType;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public LogEntry clone() {
        try {
            LogEntry r = (LogEntry) super.clone();
            if (attachments != null) {
                r.attachments = new ArrayList<Attachment>(attachments.size());
                for (Attachment attachment : attachments) {
                    r.attachments.add(attachment.clone());
                }
            }
            return r;
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }

    public static enum Type {
        EMAIL,
        WEB,
        PHONE
    }

    public static enum Direction {
        IN,
        OUT
    }

    public static enum NoteType {
        INTERNAL,
        EXTERNAL
    }

    public static class Attachment implements Cloneable {
        /**
         * The ID of the attachment
         */
        private Long id;

        /**
         * Attachment contact
         */
        private String contact;

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        @Override
        public Attachment clone() {
            try {
                return (Attachment) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new Error(e);
            }
        }
    }
}
