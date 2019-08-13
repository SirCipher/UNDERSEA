package com.type2labs.undersea.visualiser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Visualiser {

    private static final Logger logger = LogManager.getLogger(Visualiser.class);

    private JFrame frame;
    private JTable table;
    private int[] ports;

    public static void main(String[] args) {
        new Visualiser();
    }

    public Visualiser() {
        startServer();
        initGui();
    }

    private void startServer() {
        try {
            final AsynchronousServerSocketChannel listener = AsynchronousServerSocketChannel
                    .open()
                    .bind(new InetSocketAddress("localhost", 5050));

            // Listen for a new request
            listener.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
                @Override
                public void completed(AsynchronousSocketChannel ch, Void att) {
                    // Accept the next connection
                    listener.accept(null, this);

                    // Greet the client
                    ch.write(ByteBuffer.wrap("Hello, I am Echo Server 2020, let's have an engaging conversation!\n".getBytes()));

                    // Allocate a byte buffer (4K) to read from the client
                    ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
                    try {
                        // Read the first line
                        int bytesRead = ch.read(byteBuffer).get(20, TimeUnit.SECONDS);

                        boolean running = true;
                        while (bytesRead != -1 && running) {
                            System.out.println("bytes read: " + bytesRead);

                            // Make sure that we have data to read
                            if (byteBuffer.position() > 2) {
                                // Make the buffer ready to read
                                byteBuffer.flip();

                                // Convert the buffer into a line
                                byte[] lineBytes = new byte[bytesRead];
                                byteBuffer.get(lineBytes, 0, bytesRead);
                                String line = new String(lineBytes);

                                // Debug
                                System.out.println("Message: " + line);

                                // Echo back to the caller
                                ch.write(ByteBuffer.wrap(line.getBytes()));

                                // Make the buffer ready to write
                                byteBuffer.clear();

                                // Read the next line
                                bytesRead = ch.read(byteBuffer).get(20, TimeUnit.SECONDS);
                            } else {
                                // An empty line signifies the end of the conversation in our protocol
                                running = false;
                            }
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        // The user exceeded the 20 second timeout, so close the connection
                        ch.write(ByteBuffer.wrap("Good Bye\n".getBytes()));
                        System.out.println("Connection timed out, closing connection");
                    }

//                    System.out.println("End of conversation");
                    try {
                        // Close the connection if we need to
                        if (ch.isOpen()) {
                            ch.close();
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

                @Override
                public void failed(Throwable exc, Void att) {
                    exc.printStackTrace();
                }

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
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


    private void connectToAgent(int port) {

    }

}
