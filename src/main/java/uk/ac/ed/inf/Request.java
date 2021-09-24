package uk.ac.ed.inf

/* The idea of this module is to handle file fetching from server to client
 * 1. send a "GET /x/x.json" to the server
 * 2. recieve the string obj
 * 3. turn it into json file
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
		this.port     = port
		this.location = location;
	}

	protect void socket_out()
	{
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
			getFileCommand.flush()
			getFileCommand.close
		}
		catch(IOExceptions)
			System.out.println(IOExceptions);
	}

	protect String getFile()
	{
		String file;
		try
		{
			
			InputStream inputFile = socket.getInputStream();
			DataInputStream inputFile = new DataInputStream(inputStream);

			String file = inputFile.readUTF(); 
			socket.close;
		}
		catch(IOExceptions)
			System.out.println(IOExceptions);
			
		return file;
	}

}
