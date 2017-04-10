package com.mark;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by hl4350hb on 3/29/2017.
 */
public class TicketManagerGUI extends JFrame {
    private JPanel rootPanel;
    private JList openTicketsList;
    private JButton newTicketButton;
    private JButton closeTicketButton;
    private JTextField idTextField;
    private JTextField descriptionTextField;
    private JComboBox priorityComboBox;
    private JTextField reportedByTextField;
    private JTextField openDateTextField;
    private JButton submitButton;
    private DefaultListModel<String> listModel;

    private DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

    // Creates new ticket manager object.
    private TicketManager manager = new TicketManager();

    public TicketManagerGUI() {
        super("Tickets R Us!");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        submitButton.setVisible(false);
        String[] urgencies = {"1 - minor", "2", "3", "4", "5 - urgent"};
        for (int i = 0; i < urgencies.length; i++) {
            priorityComboBox.addItem(urgencies[i]);
        }
        listModel = new DefaultListModel<String>();
        openTicketsList.setModel(listModel);
        openTicketsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        LinkedList<Ticket> openTickets = manager.getTicketQueue();
//        for (Ticket t : openTickets) { listModel.addElement(t.toString()); }
        refreshList();

        closeTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        newTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disableFields(false);
//                manager.addTicket();

//                listModel.addElement();
            }
        });
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

//TODO add some validation to inputs
                try {
                    String description = descriptionTextField.getText();
                    int priority = Integer.parseInt(priorityComboBox.getSelectedItem().toString().substring(0, 1));
                    String reporter = reportedByTextField.getText();
                    Date dateReported = formatter.parse(openDateTextField.getText());
                    manager.addTicket(description, priority, reporter, dateReported);
                    refreshList();
                }
                catch (ParseException err) {
                    System.out.println("An error exists with the date formatting.");
                }
                finally {
                    disableFields(true);
                }
            }
        });
    }
    private void disableFields(boolean disable) {
        newTicketButton.setVisible(disable);
        closeTicketButton.setVisible(disable);
        descriptionTextField.setEnabled(!disable);
        priorityComboBox.setEnabled(!disable);
        reportedByTextField.setEnabled(!disable);
        if (disable) {
            openDateTextField.setText("");
        } else {
            openDateTextField.setText(formatter.format(new Date()));
        }
        submitButton.setVisible(!disable);
        submitButton.setEnabled(!disable);
    }

    private void refreshList() {
        LinkedList<Ticket> openTickets = manager.getTicketQueue();
        for (Ticket t : openTickets) { listModel.addElement(t.toString()); }
    }


}
