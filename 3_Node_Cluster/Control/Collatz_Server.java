import java.io.*;
import java.net.*;
public class Collatz_Server
{
	public static void main(String args[]) throws IOException
	{
		ServerSocket Control_Socket=null; //Declare ControlSocket and set to null, allows the control node to
		boolean listening=true; //Declares boolean listening as true, sets Server to default always listen on ports
		String Control_Name="ControlNode"; //Declares ControlName variable and sets equal to Control, this names the server
		int Control_Num=4545; //Declares ControlNum variable, this sets the socket number that the server will listen on
		int[][] Seed_Table=Seed_Array_Generator.Seed_Table_Engine();
		Collatz_Server_State State_Share = new Collatz_Server_State(Seed_Table);
		try
		{
			Control_Socket = new ServerSocket(Control_Num);
		}
		catch(IOException e)
		{
			System.err.println("Could not start Control_Node on port: "+Control_Num); 
			System.exit(1);
		}
		System.out.println(Control_Name+" started");
		while(listening)
		{
			new Collatz_Server_Thread(Control_Socket.accept(), "Control_Thread_01", State_Share).start();
			System.out.println("Control Thread 01 Started");
			new Collatz_Server_Thread(Control_Socket.accept(), "Control_Thread_02", State_Share).start();
			System.out.println("Control Thread 02 Started");
			new Collatz_Server_Thread(Control_Socket.accept(), "Control_Thread_03", State_Share).start();
			System.out.println("Control Thread 03 Started");
		}
		Control_Socket.close();
	}
}