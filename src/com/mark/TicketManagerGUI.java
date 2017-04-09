package com.mark;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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



        closeTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        newTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newTicketButton.setVisible(false);
                closeTicketButton.setVisible(false);
                descriptionTextField.setEnabled(true);
                priorityComboBox.setEnabled(true);
                reportedByTextField.setEnabled(true);
                openDateTextField.setText(formatter.format(new Date()));
                submitButton.setVisible(true);
                submitButton.setEnabled(true);


//                listModel.addElement();
            }
        });
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }




}
