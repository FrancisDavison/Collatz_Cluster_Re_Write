import java.io.*;
import java.net.*;
public class Collatz_Compute_01
{
	public static void main(String args[]) throws IOException, InterruptedException
	{
		Socket Compute_Socket=null;
		PrintWriter out=null;
		BufferedReader in=null;
		int Control_Socket_Num=4545;
		String Control_Name="192.168.0.100";
		String Compute_Node_Id="Collatz_Compute_01"; //Change
		try
		{
			Compute_Socket=new Socket(Control_Name,Control_Socket_Num);
			out=new PrintWriter(Compute_Socket.getOutputStream(), true);
			in=new BufferedReader(new InputStreamReader(Compute_Socket.getInputStream()));
		}
		catch(UnknownHostException e)
		{
			System.err.println("Don't know about host: localhost");
			System.exit(1);
		}
		catch(IOException e)
		{
			System.err.println("Couldn't get the I/O connection to: "+Control_Socket_Num);
			System.exit(1);
		}
		
		BufferedReader stdIn=new BufferedReader(new InputStreamReader(System.in));
		String To_Compute=null;
		String From_Compute=null;
		String To_Server=null;
		String Exit=null;
		int Initial=1;
		boolean Update_To_Server=true;
		System.out.println("Initialised "+Compute_Node_Id+" I/O connections");
		System.out.println("Press enter to start");
		String Start=stdIn.readLine();
		Thread.sleep(15000);
		while(true)
		{
			if(Initial==1)
			{
				To_Server="901933900000000"; //Change
				Initial=0;
			}
			else
			{
				From_Compute=Compute_Engine_01.Engine_01(To_Compute); //Change
				while(From_Compute=="999"||From_Compute=="988")
				{
					//Final seed complete, ask for user input here and just wait
					if(From_Compute=="999")
					{
						System.out.println("Compute has finished, please terminate server process and then press enter to exit");
					}
					if(From_Compute=="988")
					{
						System.out.println("Unknown error occured, exit now?");
					}
					Exit=stdIn.readLine();
					System.exit(1);
				}
				if(From_Compute=="922")
				{
					Update_To_Server=false;
				}
				if(Update_To_Server)
				{
					To_Server=From_Compute;
				}
				Update_To_Server=true;
			}
			if(To_Server!=null)
			{
				//check formatting here
				System.out.println(Compute_Node_Id+" sending "+To_Server+" to Control Node");
				out.println(To_Server);
			}
			To_Compute=in.readLine();
			System.out.println(Compute_Node_Id+" recieved "+To_Compute+" from Control Node");
		}
	}
}