/* This handleMoreClients Class inheritance the Thread Class. 
 * This Class handles all the clients that are connected to the Server Class.
 * The run method overrides the run method in the Thread class.
 * The run method creates input\output objects for communication from the client to the server.
 * Then it enters a do while loop sending requests and receiving reply until the user says quit.
 *  
 *  Name: Joshua Johnston
 *  Date: 10/24/2015
 */

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class handleMoreClients extends Thread { 
	
	private Socket socket = null;    
	
//-----------------------------------------------------------
	public handleMoreClients(Socket socket){        // constructor
		this.socket = socket;                       //got a socket
	}
	
//-----------------------------------------------------------
	public void run(){
		boolean run = true;   //flag to terminate the do while loop
		try{
			
			DataInputStream input = new DataInputStream(socket.getInputStream());   //created DataInputStream object to receive requests from a client				
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);      //created PrintWriter object to reply to the client
			
			do{     //started do while loop
				
				int option = input.readInt();  //reading the request from the client
				
				switch(option){                //each case has a system command, displays a message to the server 
											   //and then sends a command to the client
					
					case 1:	String systemCommand1 = "date";
							sendCommand(out, systemCommand1);						
							System.out.println("Responding to date request from the "+ socket);
							break;
					
					case 2: String systemCommand2 = "uptime";
							sendCommand(out, systemCommand2);
							System.out.println("Responding to uptime request from the "+ socket);
							break;
							
					case 3: String systemCommand3 = "free -m";
							sendCommand(out, systemCommand3);
							System.out.println("Responding to memory use request from the "+ socket);
							break;
							
					case 4: String systemCommand4 = "netstat";
							sendCommand(out, systemCommand4);
							out.println("");
							System.out.println("Responding to Netstat request from the "+ socket);						
							break;
							
					case 5: String systemCommand5 = "who";
							sendCommand(out, systemCommand5);
							System.out.println("Responding to current users request from the "+ socket);
							break;
							
					case 6: String systemCommand6 = "ps ax";
							sendCommand(out, systemCommand6);
							System.out.println("Responding to running processes request from the "+ socket);
							break;
							
					case 7: System.out.println("Responding to exit request from the "+ socket);							
							System.out.println("connection terminated from " + socket);
							run = false; //do while loop ends
							break;
				} //end of switch
						
				}while(run == true); //do while loop ends when the client sends the quit request. 
			socket.close();          //terminated the connection 
			} catch(IOException e){
				e.printStackTrace();
			}
		} //end of run()
//-----------------------------------------------------------	
public static void sendCommand(PrintWriter output, String command){
		
	try {
		Process process = Runtime.getRuntime().exec(command); //created a process object to run a system command
		    	
		BufferedReader reader = new BufferedReader(           //created a BufferedReader object to catch the process 
		    					new InputStreamReader(process.getInputStream()));		    	
		    	
		String line;		    	
		while((line = reader.readLine()) != null){     //reading line by line from the BufferedReader object until it's null.
		    	output.println(line);                  //sending each line to the client
		    } //end of while
		    
		output.println("ServerDone");     //ServerDone is an end token to let the client 
		                                  //know it's done sending lines for a system command
		
		reader.close();      //closing the BufferedReader object
		process.destroy();	//kills the process	    	
		 
		} catch (IOException e) {
		    e.printStackTrace();
			}		
	} //end of sendCommand()
//-----------------------------------------------------------
} //end of handleMoreClients Class
/////////////////////////////////////////////////////////////

