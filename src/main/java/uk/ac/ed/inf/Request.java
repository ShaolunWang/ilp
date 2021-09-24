package uk.ac.ed.inf;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.DataInputStream;
import java.net.Socket;

/* The idea of this module is to handle file fetching from server to client
 * 1. send a "GET /x/x.json" to the server
 * 2. recieve the string
*/

public class Request
{
	// hostname and port
	//
	String hostname;
	int port;
	String location;

	public Request(String hostname, int port, String location)
	{
		this.hostname = hostname;
		this.port     = port;
		this.location = location;
	}

	public String socketOut()
	{
		String file="";
		try
		{
			// opening the socket	
			Socket socket = new Socket(hostname, port);
			System.out.println("connected to the server");
			
			// sending request
			OutputStream output = socket.getOutputStream();
			DataOutputStream getFileCommand = new DataOutputStream(output); 
	        System.out.println("Sending requests to the server");



			getFileCommand.writeUTF("GET " + location);
			getFileCommand.flush();
			getFileCommand.close();


			InputStream input = socket.getInputStream();
			DataInputStream inputFile = new DataInputStream(input);

			file = inputFile.readUTF();
			inputFile.close();
			socket.close();
		}
		catch(Exception IOExceptions)
		{
			System.out.println(IOExceptions);
		}
		return file;
	}

}
