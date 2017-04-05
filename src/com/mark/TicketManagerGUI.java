package com.mark;

import javax.swing.*;

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

    // Creates new ticket manager object.
    private TicketManager manager = new TicketManager();

    public TicketManagerGUI() {
        super("Tickets R Us!");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        String[] urgencies = {"1 - minor", "2", "3", "4", "5 - urgent"};
        for (int i = 0; i < urgencies.length; i++) {
            priorityComboBox.addItem(urgencies[i]);
        }



    }




}
