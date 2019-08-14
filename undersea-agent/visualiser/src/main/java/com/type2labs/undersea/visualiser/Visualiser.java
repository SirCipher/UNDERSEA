package com.type2labs.undersea.visualiser;

import com.type2labs.undersea.common.logger.LogMessage;
import com.type2labs.undersea.common.visualiser.VisualiserData;
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
import java.net.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Visualiser {

    private static final Logger logger = LogManager.getLogger(Visualiser.class);

    // AgentName:Log
    private Map<String, String> logs = new HashMap<>();
    private JTextArea logArea;

    private JTable table;

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

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Accepted: " + clientSocket.getRemoteSocketAddress());
                    clientProcessingPool.execute(new ClientTask(clientSocket));
                }
            } catch (IOException e) {
                System.err.println("Unable to process client request");
                e.printStackTrace();
            }
        });
        serverThread.start();
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

        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        // TODO: Address should be the MOOS address
        String[] columnNames = {"Address", "Name", "Raft Role", "No. Tasks", "Pos (X,Y)"};
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
            }
        });

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


        logArea = new JTextArea("LOGS");
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
        bagConstraints.weighty = 1.0;
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

    private class ClientTask implements Runnable {
        private final Socket clientSocket;

        private ClientTask(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
                clientSocket.setSoTimeout(200);
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

                if (received instanceof VisualiserData) {
                    VisualiserData agentState = (VisualiserData) received;
                    String agentName = agentState.getName();
                    DefaultTableModel model = (DefaultTableModel) table.getModel();

                    if (!logs.containsKey(agentName)) {
                        logs.put(agentName, "");

                        model.addRow(new Object[]{
                                clientSocket.getRemoteSocketAddress(),
                                agentState.getName(),
                                agentState.getRaftRole(),
                                agentState.getNoTasks(),
                                Arrays.toString(agentState.getPos())});
                    } else {
                        // TODO: Update row contents
                    }
                } else if (received instanceof LogMessage) {
                    LogMessage logMessage = (LogMessage) received;

                    String currentValue = logs.get(logMessage.getAgentName());
                    currentValue += logMessage.getMessage();

                    logs.put(logMessage.getAgentName(), currentValue);
                }
            } catch (EOFException ignored) {
                // Connection is always kept open so this is expected
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
