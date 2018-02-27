/* This program is a server that accepts connections then creates threads for the clients. The server starts up and 
 * displays a start up message with day and time. Then it enters an infinite loop listening for clients. 
 * When a client connects to the server a accepted message is displayed and a new thread is created.
 * 
 *  Name: Joshua Johnston
 *  Date: 10/24/2015
 * 
 */

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;


public class Server {

	public static void main(String[] args) throws Exception{	
		
		int port = 4142;                                            //port number	
		
		@SuppressWarnings("resource")
		ServerSocket serverS = new ServerSocket(port);            //creating a new server object with port number			    
		
		System.out.println("Server starting on " + new Date());  //displaying start up message			    
			
		while(true){                                             //starting an infinite loop
			
			Socket clientS = serverS.accept();		              //listening for clients	   
		    
		    if(clientS != null){                                    //if the server connected to a client 
		    	System.out.println("Accepted Client connection");   //display a message
		    }
		    
		    System.out.println("Started thread for client " + clientS + " at " + new Date());  //display new client message with date
		    new Thread(new handleMoreClients(clientS)).start();  //created a new thread
			
			} //end of while loop
	} //end of main()
//-----------------------------------------------------------
	
} //end of Server Class
/////////////////////////////////////////////////////////////

