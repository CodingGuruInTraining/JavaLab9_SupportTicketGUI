package com.mark;

import java.util.Date;

/**
 * This class creates a new object containing multiple attributes about a
 * specific issue.
 */
public class Ticket {
    // Defines attributes.
    private String description;
    private String reportedBy;
    private Date openedDate;
    private int urgency;        // 1 = minor --- 5 = server on fire
    private boolean isOpen;
    private Date closedDate;
    private String resolution;
    protected int ticketID;



    // Creates a static counter for all of this class' objects.
    // A static number is currently set as the starting point.
    private static int staticTicketIDCounter = 70001;

    // Constructor for new Tickets.
    public Ticket(String desc, int p, String rep, Date repDate) {
        this.description = desc;
        this.urgency = p;
        this.reportedBy = rep;
        this.openedDate = repDate;
        this.ticketID = staticTicketIDCounter;
        staticTicketIDCounter++;
        this.isOpen = true;
    }

    // Constructor for recreating Tickets when program starts.
    public Ticket(int id, String desc, int p, String rep, Date repDate, String res, Date close) {
        this.ticketID = id;
        this.description = desc;
        this.urgency = p;
        this.reportedBy = rep;
        this.openedDate = repDate;
        this.resolution = res;
        this.closedDate = close;
        // Adjusts static counter so that it is larger than all open Tickets.
        if (id >= staticTicketIDCounter) {
            staticTicketIDCounter = id + 1;
        }
    }

    // Getters.
    protected String getDescription() { return this.description; }
    protected int getUrgency() {
        return this.urgency;
    }
    protected int getTicketID() {
        return this.ticketID;
    }
    protected String getReportedBy() { return this.reportedBy; }
    protected Date getOpenedDate() { return this.openedDate; }
    protected String getResolution() { return this.resolution; }
    protected Date getClosedDate() { return this.closedDate; }
    protected static int getStaticTicketIDCounter() { return staticTicketIDCounter; }
    // Setters.
    protected void setResolution(String res) { this.resolution = res; }
    protected void setClosedDate(Date closed) { this.closedDate = closed; }
    protected void setIsOpen(boolean status) { this.isOpen = status; }

    @Override
    public String toString() {
        // Custom toString return value.
        return "Ticket ID: " + this.ticketID +
                " Issue: " + this.description +
                " Priority: " + this.urgency +
                " Reported by: " + this.reportedBy +
                " Reported on: " + this.openedDate;
    }
}
