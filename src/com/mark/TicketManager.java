package com.mark;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.LinkedList;

/**
 * This class creates a issue tracking program that saves entries
 * to text files.
 */
public class TicketManager {
    // Creates file manager object.
    private static TicketFileManager filer = new TicketFileManager();
    // Reads text file and generates a LinkedList with objects.
    private static LinkedList<Ticket> ticketQueue = filer.fileReader(filer.openFile);
    // Creates new list for resolved tickets.
    private static LinkedList<Ticket> resolvedTickets = new LinkedList<>();

    // Coordinating function.
    protected void mainMenu() {
        // Continues till told to stop.
        while (true) {
            // Displays menu options and their selection code.
            System.out.println("1) Enter New Ticket\n2) Delete Ticket by ID\n3) Delete " +
                    "Ticket by Issue\n4) Search by Issue\n5) Display Open Tickets\n6) Quit");
            // Grabs User's input.
            int task = Input.getPositiveIntInput("Enter your selection from list:");
            // Decides what action to take based on input.
            switch (task) {
                case 1:     // Entering new Ticket.
                    addTicket();
                    break;
                case 2:     // Deleting Ticket by its ID.
                    deleteByID();
                    break;
                case 3:     // Deleting Ticket by its Description.
                    deleteByIssue();
                    break;
                case 4:     // Searches for Tickets with a phrase in its Description.
                    searchByIssue();
                    break;
                case 5:     // Displays all open Tickets.
                    printAllTickets(ticketQueue);
                    break;
                case 6:     // Exits program.
                    System.out.println("Quitting program...goodbye");
                    // Runs exit procedures.
                    exitEvent();
                    return;
                default:
                    break;
            }
        }
    }

    protected static void addTicket() {
        // Sets initial variables.
        boolean moreProblems = true;
        String description;
        String reporter;
        Date dateReported = new Date();
        int priority;

        // Adds exception handler.
        try {
            // Loops until User no longer wants to enter new Tickets.
            while (moreProblems) {
                // Receives input from User.
                description = Input.getStringInput("Enter problem:");
                reporter = Input.getStringInput("Who reported this issue?:");
                priority = Input.getPositiveIntInput("Enter priority of this issue (1 minor - 5 urgent:");
                // Creates new Ticket with input values.
                Ticket t = new Ticket(description, priority, reporter, dateReported);
                // Adds new Ticket to list with all other open Tickets.
                addTicketByPriority(t);

                // Prompts User to continue or not.
                String more = Input.getStringInput("More tickets? (Y/N)");
                // Exits loop if N is entered.
                if (more.equalsIgnoreCase("N")) {
                    moreProblems = false;
                }
            }
        }
        // Catch for wrong data type entered.
        catch (NumberFormatException err) {
            System.out.println("Please enter a number for the priority.");
        }
    }

    protected static void addTicketByPriority(Ticket newTicket) {
        // Checks if list is empty and adds Ticket if so.
        if (ticketQueue.size() == 0) {
            ticketQueue.add(newTicket);
            return;
        }
        // Determines the priority value of new Ticket.
        int newTicketPriority = newTicket.getUrgency();
        // Loops through list and adds the new Ticket to a certain index.
        for (int x = 0; x < ticketQueue.size(); x++) {
            if (newTicketPriority >= ticketQueue.get(x).getUrgency()) {
                ticketQueue.add(x, newTicket);
                return;
            }
        }
        // Adds Ticket to end if priority is the lowest already.
        ticketQueue.addLast(newTicket);
    }

    protected void deleteByID() {
        // Displays all open Tickets.
        printAllTickets(ticketQueue);
        // Checks if list is empty before continuing.
        if (ticketQueue.size() == 0) {
            System.out.println("There are no tickets to delete.");
            return;
        }

        // Sets a loop indicator variable.
        boolean foundID = false;
        // Adds exception handler.
        try {
            // Loops until specific Ticket is found.
            while (foundID == false) {
                // Receives input from User.
                int input = Input.getPositiveIntInput("Enter ID of Ticket to delete:");

                // Loops through list and compares input to each Ticket's ID.
                for (Ticket t : ticketQueue) {
                    if (t.getTicketID() == input) {
                        foundID = true;
                        // Sends Ticket to deletion function and displays message afterwards.
                        closeTicket(t);
                        System.out.println(String.format("Ticket %d has been deleted.", input));
                        break;
                    }
                }
                // Displays message if ID was not found.
                if (foundID == false) {
                    System.out.println(String.format("Ticket %d was not found. Try again.", input));
                }
            }
            // Displays all open Tickets again.
            printAllTickets(ticketQueue);
        }
        catch (InputMismatchException err) {
            // Catch for entering a non-integer for ID.
            System.out.println("That is not a correct number format.");
        }
    }

    protected void deleteByIssue () {
        // Receives input from User.
        String searchString = Input.getStringInput("Enter description of Issue to delete:");
        // Sends input to function that searches list for partial matches and returns
        // a separate list.
        LinkedList<Ticket> results = searchDescription(searchString);
        // Checks if more than one Ticket exists in new list.
        if (results.size() > 1) {
            System.out.println("Your search term is not specific enough to identify Ticket.");
        }
        else {
            // Sends Ticket to deletion function and displays message afterwards.
            closeTicket(results.get(0));
            System.out.println("Ticket removed.");
        }
    }

    protected void searchByIssue() {
        // Receives input from User.
        String searchString = Input.getStringInput("Enter term to search descriptions for:");
        // Sends input to function that searches list for partial matches and returns
        // a separate list.
        LinkedList<Ticket> results = searchDescription(searchString);
        // Displays all results found in open Tickets list.
        printAllTickets(results);
    }

    protected static void printAllTickets(LinkedList<Ticket> tickets) {
        // Loops through open Tickets and displays their toString result.
        System.out.println(" ------- All Tickets ------- ");
        for (Ticket t : tickets) {
            System.out.println(t);
        }
        System.out.println(" ----- End of Tickets ------ ");
    }

    // General function that searches all Tickets for partial matches.
    protected LinkedList<Ticket> searchDescription (String searchString) {
        // Creates a new list to hold partial matches.
        LinkedList<Ticket> resultsList = new LinkedList<>();
        // Loops through open tickets and compares the provided search term to
        // each Ticket's description.
        for (Ticket t : ticketQueue) {
            if (t.getDescription().contains(searchString)) {
                // Partial matches are added to new list
                resultsList.add(t);
            }
        }
        // Returns new list.
        return resultsList;
    }

    protected void closeTicket(Ticket t) {
        // Receives input from User.
        String resolution = Input.getStringInput("How was the Ticket resolved?");
        // Generates a new Date object with the current date.
        Date closeDate = new Date();
        // Uses the Ticket's setters to update attributes.
        t.setIsOpen(false);
        t.setClosedDate(closeDate);
        t.setResolution(resolution);
        // Adds Ticket to resolved list and removes it from open list.
        resolvedTickets.add(t);
        ticketQueue.remove(t);
    }

    protected void exitEvent() {
        // Uses file manager's writing method to save all open Tickets to a text file.
        filer.fileWriter(ticketQueue, filer.openFile);
        // Creates a current date string and appends to resolved Ticket's text filename.
        String currentDate = new SimpleDateFormat("MMMM_dd_yyyy").format(new Date());
        filer.fileWriter(resolvedTickets, filer.closeFile + currentDate + ".txt");
    }

//    public static void main(String[] args) {
//        // Creates new object and run its main method.
//        TicketManager manager = new TicketManager();
//        manager.mainMenu();
//    }

}
