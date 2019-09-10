package com.type2labs.undersea.monitor;

import com.type2labs.undersea.common.logger.VisualiserMessage;
import com.type2labs.undersea.common.monitor.VisualiserData;
import com.type2labs.undersea.common.service.transaction.LifecycleEvent;
import com.type2labs.undersea.utilities.networking.SimpleServer;
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
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Visualiser {

    private static final Logger logger = LogManager.getLogger(Visualiser.class);

    // AgentName:Log
    private Map<String, AgentInfo> agents = new ConcurrentHashMap<>();
    private final Object LOCK = new Object();
    private JFrame frame;
    private JTextArea logArea;
    private JTable table;
    private String currentLog;
    private boolean shutdown = false;

    private static class AgentInfo {
        String log = "";
        int port;
    }

    public Visualiser() {
        startServer();
        initGui();
    }

    public static void main(String[] args) {
        new Visualiser();
    }

    private void startServer() {
        SimpleServer server = new SimpleServer(5050, (clientSocket) -> {
            try {
                clientSocket.setSoTimeout(10000);
            } catch (SocketException e) {
                e.printStackTrace();
            }

            InputStream inputStream;
            ObjectInputStream ois;

            try {
                inputStream = clientSocket.getInputStream();
                ois = new ObjectInputStream(inputStream);
                handleData(ois);
            } catch (SocketTimeoutException ignored) {
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }, logger);
        server.runServer();
    }

    private void initGui() {
        frame = new JFrame("UNDERSEA cluster monitor");
        frame.setLayout(new BorderLayout());

        JPanel pane = new JPanel();
        frame.add(pane);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) (screenSize.width * 0.75), (int) (screenSize.height * 0.75));
        frame.setVisible(true);

        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Agent Options");

        MenuItem killAgentItem = new MenuItem("Kill Agent");
        killAgentItem.addActionListener(this::killAgentAction);
        menu.add(killAgentItem);

        menuBar.add(menu);

        frame.setMenuBar(menuBar);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        String[] columnNames = {"Raft Peer ID", "Name", "Multi-Role status", "Service Manager Status",
                "Raft Role", "Leader Peer ID", "No. Tasks", "No. Completed Tasks", "No. Peers"};
        DefaultTableModel model = new DefaultTableModel(new Object[0][0], columnNames);
        table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.getSelectionModel().addListSelectionListener(event -> {
            if (table.getSelectedRow() > -1) {
                String selected = (String) table.getValueAt(table.getSelectedRow(), 0);
                AgentInfo agentInfo = agents.get(selected);

                logArea.setText(agentInfo.log);
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

    private void sendToAgent(AgentInfo agentInfo, String message) {
        PrintStream ps;

        try (Socket socket = new Socket("localhost", agentInfo.port);) {
            OutputStream os = socket.getOutputStream();
            ps = new PrintStream(os);

            ps.println(message);
            ps.flush();
        } catch (IOException e) {
            logger.error("Failed to open connection to agent on port: " + agentInfo.port, e);
        }
    }

    private void killAgentAction(ActionEvent e) {
        if (table.getSelectedRow() > -1) {
            String selected = (String) table.getValueAt(table.getSelectedRow(), 0);
            AgentInfo agentInfo = agents.get(selected);

            if (agentInfo == null) {
                return;
            }

            sendToAgent(agentInfo, LifecycleEvent.SHUTDOWN.toString());
        }
    }

    private synchronized int getRowByPeerId(String name) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        for (int i = model.getRowCount() - 1; i >= 0; --i) {
            if (model.getValueAt(i, 0).equals(name)) {
                return i;
            }
        }

        logger.error("Agent " + name + " does not exist in the data model");
        return -1;
    }

    public void shutdown() {
        shutdown = true;
        frame.dispose();
    }

    private synchronized void handleData(ObjectInputStream ois) {
        try {
            Object received = ois.readObject();
            DefaultTableModel model = (DefaultTableModel) table.getModel();

            if (received instanceof VisualiserData) {
                final VisualiserData agentState = (VisualiserData) received;
                final String peerId = agentState.getRaftPeerId();

                AgentInfo agentInfo = agents.get(peerId);

                // If we haven't seen this agent before
                if (agentInfo == null) {
                    agentInfo = new AgentInfo();
                    agents.put(peerId, agentInfo);

                    agentInfo.port = agentState.getPort();

                    model.addRow(new Object[]{
                            agentState.getRaftPeerId(),
                            agentState.getName(),
                            agentState.getMultiRoleStatus(),
                            agentState.getServiceManagerStatus(),
                            agentState.getRaftRole(),
                            agentState.getLeaderPeerId(),
                            agentState.getNoAssignedTasks(),
                            agentState.getCompletedTasks(),
                            agentState.getNoPeers()
                    });
                } else {
                    int rowId = getRowByPeerId(peerId);

                    if (rowId != -1) {
                        model.setValueAt(agentState.getRaftPeerId(), rowId, 0);
                        model.setValueAt(agentState.getName(), rowId, 1);
                        model.setValueAt(agentState.getMultiRoleStatus(), rowId, 2);
                        model.setValueAt(agentState.getServiceManagerStatus(), rowId, 3);
                        model.setValueAt(agentState.getRaftRole(), rowId, 4);
                        model.setValueAt(agentState.getLeaderPeerId(), rowId, 5);
                        model.setValueAt(agentState.getNoAssignedTasks(), rowId, 6);
                        model.setValueAt(agentState.getCompletedTasks(), rowId, 7);
                        model.setValueAt(agentState.getNoPeers(), rowId, 8);
                    }
                }
            } else if (received instanceof VisualiserMessage) {
                VisualiserMessage visualiserMessage = (VisualiserMessage) received;

                AgentInfo agentInfo = agents.get(visualiserMessage.getPeerId());
                if (agentInfo == null) {
                    agentInfo = new AgentInfo();
                }

                agentInfo.log += visualiserMessage.getMessage();

                agents.put(visualiserMessage.getPeerId(), agentInfo);

                if (currentLog != null && currentLog.equals(visualiserMessage.getPeerId())) {
                    logArea.setText(agentInfo.log);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
