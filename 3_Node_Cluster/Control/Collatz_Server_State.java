public class Collatz_Server_State
{
	private Collatz_Server_State State_Share;
	private String Thread_Name;
	private boolean Current_Access=false;
	private int Waiting=0;
	private int[][] Seed_Table;
	private int[] Live_Index=new int[16]; //use 16 as that is how many Pi's I have available
	//Constructor
	Collatz_Server_State(int[][] Seed_Table_In)
	{
		Seed_Table=Seed_Table_In;
	}
	//Attempt to acquire lock
	public synchronized void AcquireLock() throws InterruptedException
	{
		Thread  This_Thread = Thread.currentThread(); //====Need a better name than me====
		System.out.println(This_Thread.getName()+" is attempting to acquire a lock");
		Waiting+=Waiting;
		while(Current_Access)
		{
			System.out.println(This_Thread.getName()+" waiting to get a lock, someone else is accessing...");
			wait();
		}
		//nobody currently has a lock, so assign lock to current thread
		Waiting-=Waiting;
		Current_Access=true;
		System.out.println(This_Thread.getName()+" got a lock!");
	}
	//Release the lock when thread has finished
	public synchronized void ReleaseLock()
	{
		Current_Access=false;
		notifyAll();
		Thread me=Thread.currentThread();
		System.out.println(This_Thread.getName()+" released a lock");
	}
	//Issue seed to thread that currently has lock
	public synchronized String ProcessInput(String Input_From_Compute)
	{
		String Output_To_Compute="";
		String Raw_Node_Id="";
		String Raw_Seed_Status="";
		String Raw_Current_Seed="";
		int Node_Id=0;
		int Seed_Status=0;
		int Current_Seed=0;
		
		for(int a=0;a<=2;a++)
		{
			Raw_Node_Id+=Input_From_Compute.charAt(a);
		}
		Node_Id=Integer.valueOf(Raw_Node_Id)-900;
		
		for(int b=3;b<=5;b++)
		{
			Raw_Seed_Status+=Input_From_Compute.charAt(b);
		}
		Seed_Status=Integer.valueOf(Raw_Seed_Status)-900;
		
		for(int d=6;d<=14;d++)//Might not need this for input
		{
			Raw_Current_Seed+=Input_From_Compute.charAt(d);
		}
		Current_Seed=Integer.valueOf(Raw_Current_Seed)-900000000;
		
		if(Seed_Status==900)
		{
			//Previous compute failed, re-issue same seed again
			Output_To_Compute=Input_From_Compute;
			return Output_To_Compute;
		}
		
		if(Seed_Status==11||Seed_Status==33)
		{
			//Previous compute passed, issue next seed
			if(Seed_Status==33)
			{
				Seed_Status=11;
			}
			Current_Seed=Seed_Table[Node_Id-1][Live_Index[Node_Id-1]];
			if(Current_Seed==-1||Current_Seed==0)
			{
				//Compute for this node is complete, send message (code 999) to compute node to stop requesting seeds
				Output_To_Compute=(String.valueOf(Node_Id+900))+(String.valueOf(999))+(String.valueOf(900000000));
				return Output_To_Compute;
			}
			Live_Index[Node_Id-1]+=1;
			Output_To_Compute=(String.valueOf(Node_Id+900))+(String.valueOf(Seed_Status+900))+(String.valueOf(Current_Seed+900000000));
			return Output_To_Compute;
		}
		
		if(Seed_Status==22)
		{
			//Invalid message received, request same message again
			return Output_To_Compute;
		}
		
		else
		{
			//Unknown error, return error message to compute node
			System.err.println("Unknown error occured");
			Output_To_Compute="UnknownError";
			return Output_To_Compute;
		}
	}
}