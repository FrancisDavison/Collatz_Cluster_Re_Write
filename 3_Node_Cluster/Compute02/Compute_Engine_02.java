import java.io.*;
import org.apache.commons.csv.*;
public class Compute_Engine_02
{
	public static String Engine_02(String Message_In) throws InterruptedException, IOException
	{
		Thread.sleep(5);
		boolean Final_Seed=false;
		int Node_Id=0;
		int Seed_Status=0;
		int Current_Seed=0;
		int User_In=0;
		String Message_Out="";
		String Raw_User_In;
		String Raw_Node_Id="";
		String Raw_Seed_Status="";
		String Raw_Current_Seed="";
		for(int a=0;a<=2;a++)
		{
			Raw_Node_Id+=Message_In.charAt(a);
		}
		Node_Id=Integer.valueOf(Raw_Node_Id)-900;
		for(int b=3;b<=5;b++)
		{
			Raw_Seed_Status+=Message_In.charAt(b);
		}
		Seed_Status=Integer.valueOf(Raw_Seed_Status)-900;
		for(int d=6;d<=14;d++)
		{
			Raw_Current_Seed+=Message_In.charAt(d);
		}
		Current_Seed=Integer.valueOf(Raw_Current_Seed)-900000000;
		if(Node_Id!=2) //Change
		{
			Message_Out=(String.valueOf(Node_Id+900))+(String.valueOf(944))+(String.valueOf(Current_Seed+900000000));
			return Message_Out;
		}
		//Check if Current seed is positive integer
		if(Integer.signum(Current_Seed)!=1&&Integer.signum(Current_Seed)!=0)
		{
			//Current Seed error, request same seed again
			Message_Out=(String.valueOf(Node_Id+900))+(String.valueOf(922))+(String.valueOf(Current_Seed+900000000));
			return Message_Out;
		}
		if(Seed_Status==00||Seed_Status==11)
		{
			//Compute here
			CSVPrinter printer;
			String File_Path=String.valueOf(Current_Seed)+"_02.csv";
	        FileWriter writer = new FileWriter(File_Path);
	        printer = new CSVPrinter(writer, CSVFormat.DEFAULT);
			try
			{
				long This_Term=Current_Seed; //Create and initialise This_Term variable for tracking current seed number
				printer.printRecord(Current_Seed,System.nanoTime());
				while(This_Term!=1)
				{
					if(This_Term%2!=0) //If This_Term is odd:
					{
						This_Term=(3*This_Term)+1; //Multiply This_Term by 3 and add 1
						printer.printRecord(This_Term,System.nanoTime());
					}
					else //Else (If This_Term is even)
					{
						This_Term=This_Term/2; //Divide This_Term by 2
						printer.printRecord(This_Term,System.nanoTime());
					}
					printer.flush();
				}
				printer.close();
				//Re-construct message here
				Message_Out=(String.valueOf(Node_Id+900))+(String.valueOf(Seed_Status+900))+(String.valueOf(Current_Seed+900000000));
				return Message_Out;
			}
			catch(IOException e)
			{
				e.printStackTrace();
				//Re-construct message here
				Message_Out=(String.valueOf(Node_Id+900))+(String.valueOf(Seed_Status+900))+(String.valueOf(Current_Seed+900000000));
				return Message_Out;
			}
		}
		if(Seed_Status==22)
		{
			//return 922, node will send old message
			Message_Out="922";
			return Message_Out;
		}
		if(Seed_Status==99)
		{
			Message_Out="999";
			return Message_Out;
		}
		else
		{
			System.err.println("Unknown error occured");
			Message_Out="988";
			return Message_Out;
		}
	}
}