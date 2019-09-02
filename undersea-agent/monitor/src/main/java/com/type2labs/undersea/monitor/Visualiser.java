package com.type2labs.undersea.monitor;

import com.type2labs.undersea.common.logger.LogMessage;
import com.type2labs.undersea.common.monitor.VisualiserData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import java.awt.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Visualiser {

    private static final Logger logger = LogManager.getLogger(Visualiser.class);

    // AgentName:Log
    private Map<String, String> logs = new HashMap<>();
    private JFrame frame;
    private JTextArea logArea;
    private JTable table;
    private String currentLog;
    private boolean shutdown = false;

    public Visualiser() {
        startServer();
        initGui();
    }

    public static void main(String[] args) {
        new Visualiser();
    }

    private void startServer() {
        final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);

        Thread serverThread = new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(5050);
                logger.info("Waiting for clients to connect...");

                while (!shutdown) {
                    Socket clientSocket = serverSocket.accept();
                    logger.info("Processing request from: " + clientSocket.getPort());
                    clientProcessingPool.execute(new ClientTask(clientSocket));
                }

                logger.info("Shutting down visualiser");
            } catch (IOException e) {
                logger.error("Unable to process client request", e);
                e.printStackTrace();
            }
        });
        serverThread.start();
    }

    private void initGui() {
        frame = new JFrame("UNDERSEA cluster monitor");
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

        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        // TODO: Address should be the MOOS address
        String[] columnNames = {"Address", "Raft Peer ID", "Name", "Multi-Role status", "Raft Role", "No. Tasks", "No. Completed Tasks"};
        DefaultTableModel model = new DefaultTableModel(new Object[0][0], columnNames);
        table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.getSelectionModel().addListSelectionListener(event -> {
            if (table.getSelectedRow() > -1) {
                String selected = (String) table.getValueAt(table.getSelectedRow(), 1);
                logArea.setText(logs.get(selected));
                currentLog = selected;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        GridBagConstraints west = new GridBagConstraints();
        west.anchor = GridBagConstraints.WEST;
        west.fill = GridBagConstraints.BOTH;
        west.weighty = 0.5;
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
        east.weighty = 0.5;
        east.weightx = 0.1;
        east.gridx = 1;
        east.gridy = 0;

        pane.add(agentOptions, east);

        logArea = new JTextArea("Select an agent to view its logs");
        JScrollPane logScrollPane = new JScrollPane(logArea);

        logArea.setMaximumSize(new Dimension(frame.getWidth(), frame.getHeight() / 2));

        int maximumLines = 100;

        AbstractDocument abstractDocument = (AbstractDocument) logArea.getDocument();
        abstractDocument.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                SwingUtilities.invokeLater(() -> {
                    // Tail log
                    logArea.setCaretPosition(logArea.getDocument().getLength());

                    Document document = documentEvent.getDocument();
                    Element root = document.getDefaultRootElement();

                    Element line = root.getElement(0);
                    int end = line.getEndOffset();

                    while (root.getElementCount() > maximumLines) {
                        try {
                            document.remove(0, end);
                        } catch (BadLocationException e) {
                            logger.error("Couldn't find start location to remove line");
                        }
                    }
                });
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {

            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {

            }
        });

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
        bagConstraints.weighty = 0.5;
        bagConstraints.gridwidth = 3;
        bagConstraints.gridx = 0;
        bagConstraints.gridy = 1;

        pane.add(logScrollPane, bagConstraints);

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

    private int getRowByAgentName(String name) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        for (int i = model.getRowCount() - 1; i >= 0; --i) {
            for (int j = model.getColumnCount() - 1; j >= 0; --j) {
                if (model.getValueAt(i, j).equals(name)) {
                    return i;
                }
            }
        }

        logger.error("Agent " + name + " does not exist in the data model");
        return -1;
    }

    public void shutdown() {
        shutdown = true;
        frame.dispose();
    }

    private class ClientTask implements Runnable {
        private final Socket clientSocket;

        private ClientTask(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
                clientSocket.setSoTimeout(10000);
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            InputStream inputStream;
            ObjectInputStream ois;

            try {
                inputStream = clientSocket.getInputStream();
                ois = new ObjectInputStream(inputStream);
                handleData(ois);
            } catch (SocketTimeoutException ignored) {
                ignored.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void handleData(ObjectInputStream ois) {
            try {
                Object received = ois.readObject();
                DefaultTableModel model = (DefaultTableModel) table.getModel();

                if (received instanceof VisualiserData) {
                    VisualiserData agentState = (VisualiserData) received;
                    String agentName = agentState.getName();

                    if (!logs.containsKey(agentName)) {
                        logs.put(agentName, "");

                        model.addRow(new Object[]{
                                clientSocket.getRemoteSocketAddress(),
                                agentState.getRaftPeerId(),
                                agentState.getName(),
                                agentState.getMultiRoleStatus(),
                                agentState.getRaftRole(),
                                agentState.getNoTasks(),
                                agentState.getCompletedTasks()});
                    } else {
                        int rowId = getRowByAgentName(agentName);

                        if (rowId != -1) {
                            model.setValueAt(clientSocket.getRemoteSocketAddress(), rowId, 0);
                            model.setValueAt(agentState.getRaftPeerId(), rowId, 1);
                            model.setValueAt(agentState.getName(), rowId, 2);
                            model.setValueAt(agentState.getMultiRoleStatus(), rowId, 3);
                            model.setValueAt(agentState.getRaftRole(), rowId, 4);
                            model.setValueAt(agentState.getNoTasks(), rowId, 5);
                            model.setValueAt(agentState.getCompletedTasks(), rowId, 6);
                        }
                    }
                } else if (received instanceof LogMessage) {
                    LogMessage logMessage = (LogMessage) received;

                    String currentValue = logs.get(logMessage.getPeerId());
                    if (currentValue == null) {
                        currentValue = "";
                    }

                    currentValue += logMessage.getMessage();

                    logs.put(logMessage.getPeerId(), currentValue);

                    if (currentLog != null && currentLog.equals(logMessage.getPeerId())) {
                        logArea.setText(currentValue);
                    }
                }
            } catch (EOFException ignored) {
                // Connection is always kept open so this is expected
                ignored.printStackTrace();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
