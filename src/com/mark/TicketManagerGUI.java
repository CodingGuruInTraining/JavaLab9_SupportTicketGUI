package com.mark;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
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
    private JButton resolveButton;
    private DefaultListModel<String> listModel;

    private DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

    // Creates new ticket manager object.
    private TicketManager manager = new TicketManager();

    public TicketManagerGUI() {
        super("Tickets R Us!");
        setContentPane(rootPanel);
        setPreferredSize(new Dimension(700,450));
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        submitButton.setVisible(false);
        resolveButton.setVisible(false);
        String[] urgencies = {"1 - minor", "2", "3", "4", "5 - urgent"};
        for (int i = 0; i < urgencies.length; i++) {
            priorityComboBox.addItem(urgencies[i]);
        }
        listModel = new DefaultListModel<String>();
        openTicketsList.setModel(listModel);
        openTicketsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        refreshList();

        closeTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newTicketButton.setVisible(false);
                closeTicketButton.setVisible(false);
                resolveButton.setVisible(true);
            }
        });
        newTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disableFields(false);
            }
        });
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

//TODO add some validation to inputs

                String description = descriptionTextField.getText();
                int priority = Integer.parseInt(priorityComboBox.getSelectedItem().toString().substring(0, 1));
                String reporter = reportedByTextField.getText();
                Date dateReported = new Date();
                manager.addTicket(description, priority, reporter, dateReported);
                refreshList();
                disableFields(true);
            }
        });
        resolveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
        listModel.clear();
        for (Ticket t : openTickets) { listModel.addElement(t.toString()); }
    }


}
