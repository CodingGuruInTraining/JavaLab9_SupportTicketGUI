package com.mark;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 * This class creates a file manager object for writing to and
 * reading text files.
 */
public class TicketFileManager {
    // Creates initial variables.
    protected LinkedList<Ticket> openTickets;
    protected LinkedList<Ticket> closedTickets;
    protected String openFile = "openTickets.txt";
    protected String closeFile = "Resolved_tickets_as_of_";

    // Getters.
    public LinkedList<Ticket> getOpenTickets() {
        return openTickets;
    }
    public LinkedList<Ticket> getClosedTickets() {
        return closedTickets;
    }

    // Constructor.
    public TicketFileManager() {
        openTickets = fileReader(this.openFile);
    }

    // Reader method that should work with either file (open or closed).
    protected LinkedList<Ticket> fileReader(String filename) {
        // Creates new list object.
        LinkedList<Ticket> tickets = new LinkedList<Ticket>();
        // Adds reader within exception handler.
        try (BufferedReader buffReader = new BufferedReader(new FileReader(filename))) {
            // Reads first line of file.
            String ticket_line = buffReader.readLine();
            // Creates a date format object with a specific format.
            DateFormat formatter = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy");

            // Loops until the end of the file is reached.
            while (ticket_line != null) {
                // Splits the string into an array.
                String[] ticket_info = ticket_line.split(";");
                // Separates array items into variables and parses specific ones.
                int id = Integer.parseInt(ticket_info[0]);
                String desc = ticket_info[1];
                int p = Integer.parseInt(ticket_info[2]);
                String rep = ticket_info[3];
                Date repDate = formatter.parse(ticket_info[4]);
                // Sets null values to start.
                String res = null;
                Date close = null;
                // Checks if a null value was saved with Ticket and changes
                // variables to the array item's value if not null.
                if (!ticket_info[5].equalsIgnoreCase("null")) {
                    res = ticket_info[5];
                    close = formatter.parse(ticket_info[6]);
                }
                // Creates new Ticket with values and adds object to list.
                Ticket t = new Ticket(id, desc, p, rep, repDate, res, close);
                tickets.add(t);
                // Reads next line to continue through file.
                ticket_line = buffReader.readLine();
            }
            // Closes reader object and returns list if no errors.
            buffReader.close();
            return tickets;
        }
        // Catch for file reading errors.
        catch (IOException err) {
            System.out.println("One or more files could not be found.");
        }
        // Catch for parsing errors.
        catch (ParseException err) {
            System.out.println("An error exists with the saved dates.");
        }
        // Returns list even if empty.
        return tickets;
    }

    // Writer method that uses the provided variables to create text file.
    protected void fileWriter(LinkedList<Ticket> tickets, String filename, boolean appendTo) {
        // Adds writer object within exception handler.
        try (BufferedWriter buffWriter = new BufferedWriter(new FileWriter(filename, appendTo))) {
            // Loops through all Tickets in provided list.
            for (Ticket t : tickets) {
                // Writes a string of the Ticket's attributes with separators.
                buffWriter.write(t.getTicketID() + ";" + t.getDescription() + ";" + t.getUrgency() +
                ";" + t.getReportedBy() + ";" + t.getOpenedDate() + ";" + t.getResolution() +
                ";" + t.getClosedDate() + "\n");
            }
            // Closes writer object.
            buffWriter.close();
        }
        // Catch for filename or location errors.
        catch (IOException err) {
            System.out.println("There was an issue saving the data.");
        }
    }
}

//helpful site:
//        http://stackoverflow.com/questions/4496359/how-to-parse-date-string-to-date