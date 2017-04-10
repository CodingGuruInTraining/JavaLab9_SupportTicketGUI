package com.mark;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by hl4350hb on 3/29/2017.
 */
public class TicketManagerGUI extends JFrame {
    private JPanel rootPanel;
    private JList<Ticket> openTicketsList;
    private JButton newTicketButton;
    private JButton closeTicketButton;
    private JTextField idTextField;
    private JTextField descriptionTextField;
    private JComboBox priorityComboBox;
    private JTextField reportedByTextField;
    private JTextField openDateTextField;
    private JButton submitButton;
    private JButton resolveButton;
    private DefaultListModel<Ticket> listModel;

    private DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

    // Creates new ticket manager object.
    private TicketManager manager = new TicketManager();

    public TicketManagerGUI() {
        super("Tickets R Us!");
        setContentPane(rootPanel);
        setPreferredSize(new Dimension(700,450));
        pack();
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.showConfirmDialog(rootPanel, "Do you really want to exit?", "Confirm Close", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    manager.exitEvent();
                    System.out.println("Closing...");
                    System.exit(0);
                }
            }
        });


        setVisible(true);
        submitButton.setVisible(false);
        resolveButton.setVisible(false);
        String[] urgencies = {"1 - minor", "2", "3", "4", "5 - urgent"};
        for (int i = 0; i < urgencies.length; i++) {
            priorityComboBox.addItem(urgencies[i]);
        }
        listModel = new DefaultListModel<Ticket>();
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
                int availableID = Ticket.getStaticTicketIDCounter();
                idTextField.setText(availableID + "");
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
                clearFields();
            }
        });
        resolveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int listindex = openTicketsList.getSelectedIndex();
                if (listindex >= 0) {

                    Ticket t = (Ticket)openTicketsList.getSelectedValue();
//                    Ticket t = (Ticket)listModel.getElementAt(listindex);
                    String resolution = JOptionPane.showInputDialog("Enter Ticket's resolution:");

                    while (resolution.equals("")) {
                        resolution = JOptionPane.showInputDialog("Enter Ticket's resolution:");

                    }
//                    System.exit(0);
                    manager.closeTicket(t, resolution);
                    refreshList();
                    clearFields();
                }
                else {
                    System.out.println("Need to select a Ticket to close.");
//TODO replace with pop-up
                }
            }
        });
    }

//    @Override
//    public int getDefaultCloseOperation() {
//        System.out.println("close opp: " + super.getDefaultCloseOperation());
//        return super.getDefaultCloseOperation();
//    }

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
        for (Ticket t : openTickets) { listModel.addElement(t); }
    }

    private void clearFields() {
        idTextField.setText("");
        descriptionTextField.setText("");
        priorityComboBox.setSelectedIndex(0);
        reportedByTextField.setText("");
        openDateTextField.setText("");
    }

}
//helpful sites:
//        http://www.homeandlearn.co.uk/java/java_option_panes.html
//http://stackoverflow.com/questions/9093448/do-something-when-the-close-button-is-clicked-on-a-jframe