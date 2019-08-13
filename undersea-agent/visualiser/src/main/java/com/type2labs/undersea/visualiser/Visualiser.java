package com.type2labs.undersea.visualiser;

import com.type2labs.undersea.common.visualiser.VisualiserData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Visualiser {

    private static final Logger logger = LogManager.getLogger(Visualiser.class);

    private final List<Socket> clients = new ArrayList<>();

    private JTable table;

    public static void main(String[] args) {
        new Visualiser();
    }

    public Visualiser() {
        startServer();
        initGui();
    }


    private void addClient(Socket address) {
        if (!clients.contains(address)) {
            clients.add(address);
            logger.info("Registered client: " + address);
        }
    }

    private class ClientTask implements Runnable {
        private final Socket clientSocket;

        private ClientTask(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            System.out.println("Got a client !");

            try {
                ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                VisualiserData agentState = (VisualiserData) ois.readObject();

                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.addRow(new Object[]{
                        clientSocket.getLocalPort(),
                        agentState.getName(),
                        agentState.getRaftRole(),
                        agentState.getNoTasks(),
                        Arrays.toString(agentState.getPos())});

                clientSocket.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    private void startServer() {
        final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);

        Runnable serverTask = () -> {
            try {
                ServerSocket serverSocket = new ServerSocket(5050);
                System.out.println("Waiting for clients to connect...");

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    addClient(clientSocket);
                    clientProcessingPool.submit(new ClientTask(clientSocket));
                }
            } catch (IOException e) {
                System.err.println("Unable to process client request");
                e.printStackTrace();
            }
        };

        Thread serverThread = new Thread(serverTask);
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

        String[] columnNames = {"Port", "Name", "Raft Role", "No. Tasks", "Pos (X,Y)"};
        DefaultTableModel model = new DefaultTableModel(new Object[0][0], columnNames);
        table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

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


    private void connectToAgent(int port) {

    }

}
