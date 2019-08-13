package com.type2labs.undersea.visualiser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@SuppressWarnings("DuplicatedCode")
public class VisualiserRunner {

    private static final Logger logger = LogManager.getLogger(VisualiserRunner.class);

    private JFrame frame;
    private JTable table;
    private int[] ports;


    public VisualiserRunner(String properties) {
        loadConfig(properties);
        initGui();
    }

    private void initGui() {
        JFrame frame = new JFrame("UNDERSEA");
        frame.setLayout(new BorderLayout());

        JPanel pane = new JPanel();
        frame.add(pane);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) (screenSize.width * 0.75), (int) (screenSize.height * 0.75));
        frame.setVisible(true);

        MenuBar menu = new MenuBar();
        menu.add(new Menu("File"));
        frame.setMenuBar(menu);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton button;
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        String[] columnNames = {"Connected", "Port", "Name", "Raft Status", "No. Tasks", "Pos (X,Y)"};
        DefaultTableModel model = new DefaultTableModel(new Object[0][0], columnNames);
        table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (int i = 0; i < 50; i++) {
            model.addRow(new Object[]{false, 0, "Alpha", "Leader", 0, "0,0"});
        }

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        GridBagConstraints west = new GridBagConstraints();
        west.anchor = GridBagConstraints.WEST;
        west.fill = GridBagConstraints.BOTH;
        west.weighty = 1;
        west.weightx = 1;
        west.gridx = 0;
        west.gridy = 0;

        pane.add(scrollPane, west);

        JPanel agentOptions = new JPanel();
        agentOptions.setLayout(new GridLayout(3, 2, 10, 50));
        agentOptions.setBorder(new BevelBorder(BevelBorder.LOWERED));

        JTextField agentName = new JTextField("Name: ");
        agentOptions.add(agentName);

        JButton agentOptionsButton = new JButton("Kill agent");
        agentOptions.add(agentOptionsButton);

        agentOptionsButton = new JButton("Promote");
        agentOptions.add(agentOptionsButton);

        agentOptionsButton = new JButton("Demote");
        agentOptions.add(agentOptionsButton);

        agentOptionsButton = new JButton("Disable");
        agentOptions.add(agentOptionsButton);

        GridBagConstraints east = new GridBagConstraints();
        east.anchor = GridBagConstraints.EAST;
        east.fill = GridBagConstraints.BOTH;
        east.weighty = 1;
        east.weightx = 0.1;
        east.gridx = 1;
        east.gridy = 0;

        pane.add(agentOptions, east);

        JTextArea logArea = new JTextArea("LOGS");
        logArea.setEditable(false);
        logArea.setWrapStyleWord(true);
        logArea.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));

        Border border = BorderFactory.createLineBorder(Color.BLACK);
        logArea.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 0, 10)));

        GridBagConstraints bagConstraints = new GridBagConstraints();
        bagConstraints.fill = GridBagConstraints.BOTH;
        bagConstraints.ipady = 40;
        bagConstraints.weightx = 1.0;
        bagConstraints.weighty = 1.0;
        bagConstraints.gridwidth = 3;
        bagConstraints.gridx = 0;
        bagConstraints.gridy = 1;


        pane.add(logArea, bagConstraints);

        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        frame.add(statusPanel, BorderLayout.SOUTH);
        statusPanel.setPreferredSize(new Dimension(frame.getWidth(), 16));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        JLabel statusLabel = new JLabel("status");
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusPanel.add(statusLabel);

        frame.setVisible(true);
    }

    private void loadConfig(String properties) {
        List<String> stringPorts;

        try {
            stringPorts = Files.readAllLines(Paths.get(properties), Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException("Unable to load port configuration file: " + properties);
        }

        ports = new int[stringPorts.size()];

        for (int i = 0; i < ports.length; i++) {
            logger.info("Registering for port: " + stringPorts.get(i));
            ports[i] = Integer.parseInt(stringPorts.get(i));
        }

    }

    private void connectToAgent(int port) {

    }

}
