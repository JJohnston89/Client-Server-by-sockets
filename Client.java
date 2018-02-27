/* This client program connects to a server and sends request for system commands.
 * The user will enter the server hostname as a command line argument.
 * The program creates a socket to connect to the hostname that user gave.
 * Then the program enters a loop until the user says quit.
 * 
 * Name: Joshua Johnston
 * Date: 10/25/2015
 */

import java.io.*;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Client {
	
	public static void main(String[] args) throws ClassNotFoundException{
		
		int port = 4142;             //port number
		
		if(args.length == 0){       //if no command line argument then print error message and exit.
			System.out.println("ERROR: No Host Name!");
			System.out.println("Program will now exit...");
			System.exit(0);
		}			
		
		try {
			Socket socket = new Socket(args[0], port);                                   //created a socket
			DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());	//created DataOutputStream object 
																						//to send requests to the server				
			int option = 0;
				
			do{                          //started do while loop
				menu();                 //display menu
				option = getData();     //get user input 
				    
				switch(option){     //printing out a message for the user option
				    
			            case 1: System.out.println("Requesting system time");
				    		break;
				    case 2: System.out.println("Requesting uptime");
				    		break;
				    case 3: System.out.println("Requesting memory use");
				    		break;
				    case 4: System.out.println("Requesting Netstat");
				    		break;
				    case 5: System.out.println("Requestiong current users");
				    		break;
				    case 6: System.out.println("Requesting running processes");
				    		break;
				    case 7: System.out.println("exiting...");
				    		break;
				    } //end of switch
				    
				toServer.writeInt(option);  //send a request to the server
				toServer.flush();
				
				if(option == 7)  //if user says quit then break
					break;
					
				DisplayResponse(socket);   //display the server's response
				}while(option != 7);      //end of do while			
			
		} catch (IOException e) {
			
			e.printStackTrace();			
			
		}
		
	} //end of main()
	
//-----------------------------------------------------------
	public static void menu(){  //displays a menu
		
		System.out.println(1 +": Host current Date and Time");
		System.out.println(2 +": Host uptime");
		System.out.println(3 +": Host memory use");
		System.out.println(4 +": Host Netstat");
		System.out.println(5 +": Host current users");
		System.out.println(6 +": Host running processes");
		System.out.println(7 +": Quit");
	} //end of menu()
//-----------------------------------------------------------	
	public static int getData(){
		boolean notValid = true;   //flag to terminate the do while loop
		int option = 0;
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);   //created a scanner object to read user input		
		
		do{		   //started do while
			try{
				System.out.println("Enter an option: ");  //prompt the user for input
				option = input.nextInt();                 //reading in the user input
			    
				if(option < 1 || option > 7){            //validating user input
					System.out.println("ERROR: Incorrect input!" + " Enter a value between 1-7!");  //display error message
					System.out.println(" ");
					menu();		//display menu		
				}
				else
					notValid = false;   //user input is valid
				
			}
			catch(InputMismatchException ex){
				   System.out.println("ERROR: Incorrect input!\n" + "An integer is required!"); //display input mismatch error
				   System.out.println(" ");
				   menu();	        //display menu				   
				   input.nextLine();
			
			}
		}while(notValid == true);    //loop ends when user input is valid		
		
		return option;		
	} //end of getData()
//-----------------------------------------------------------
	public static void DisplayResponse(Socket socket) throws IOException{
		
		BufferedReader fromServer = new BufferedReader
					(new InputStreamReader(socket.getInputStream())); //created BufferedReader object
																					  //to receive the server's reply		
		String userInput;
		
		while (!(userInput = fromServer.readLine()).equals("ServerDone")){ //reading line by line until end token
			System.out.println(userInput);                                 //printing line by line to the screen
        } //end of while
		System.out.println(""); 
		return;		
	} //end of DisplayResponse()
//-----------------------------------------------------------

} //end of Client Class
/////////////////////////////////////////////////////////////
