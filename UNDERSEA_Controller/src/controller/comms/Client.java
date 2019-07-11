package controller.comms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

	/** Communication handles*/
    private Socket socket;		
    private PrintWriter out;			
    private BufferedReader in;
    
    /** Shut down string*/
    private String SHUT_DOWN_STR = "###";
    
    /**
     * Constructor: create a new client instance
     * @param host
     * @param port
     */
	public Client(String host, int port) {
		try {
			socket	= new Socket(host, port);
			out 	= new PrintWriter(socket.getOutputStream(),true);
			in		= new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} 
		catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}		
	}
	
	
	public PrintWriter getPrintWriter(){
		return this.out;
	}
	
	
	public BufferedReader getBufferedReader(){
		return this.in;
	}
	
	
	public String read() throws IOException{
		return in.readLine();
	}
	
	
	public String write(String outputStr) throws IOException{
		out.println(outputStr);
		out.flush();
		return read();
	}

	
	public boolean shutDown() throws IOException{
		String inputStr = write(SHUT_DOWN_STR);
		System.out.println(inputStr);
		if (inputStr.equals(SHUT_DOWN_STR))
			return true;
		return false;
	}
}
