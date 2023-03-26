import java.util.*;
public class Seed_Array_Generator
{
	public static int[][] Seed_Table_Engine()
	{
		Scanner Input_Nodes=new Scanner(System.in);
		Scanner Input_Start_Seed=new Scanner(System.in);
		Scanner Input_Seed_Num=new Scanner(System.in);
		System.out.println("Input number of compute nodes: ");
		int Compute_Nodes=Input_Nodes.nextInt();
		int Count=0;
		while(Compute_Nodes<=0||Compute_Nodes>=16)
		{
			System.out.println("Number of compute nodes must be an integer, at least 1 and at most 15. Please input number of ocmpute nodes:");
			Compute_Nodes=Input_Nodes.nextInt();
		}
		System.out.println("Input starting seed value: ");
		int Start_Seed=Input_Start_Seed.nextInt();
		while(Start_Seed<=1||Start_Seed>=1000000)
		{
			System.out.println("Start seed must be an integer value, at least 2 and at most 1,000,000. Please input starting seed value: ");
			Start_Seed=Input_Start_Seed.nextInt();
		}
		System.out.println("Input number of seeds: ");
		int Seed_Num=Input_Seed_Num.nextInt();
		while(Seed_Num<=1||Seed_Num>=90000001)
		{
			System.out.println("Number of seeds must be an integer value, at least 2 and at most 10,000,000. Please input number of seeds:");
			Seed_Num=Input_Seed_Num.nextInt();
		}
		int Rows=Compute_Nodes;
		double Seeds_Remainder=Seed_Num%Compute_Nodes;
		int Columns=(int)(((Seed_Num-Seeds_Remainder)/Compute_Nodes)+1);
		if(Seeds_Remainder>=1)
		{
			Columns+=1;
		}
		int[][] Seed_Table = new int[Rows][Columns]; //Create Seed_Table array of integers, this will store all seeds needed in the computation, will need to test maximum size
		//Backfill final row with -1
		for(int i=0;i<Rows;i++) //Counter
		{
			Seed_Table[i][Columns-1]=-1; //Setting each cell from Top row to bottom row, in final column to -1
		}
		int Current_Seed=Start_Seed; //Sets starting seed to 2, as using start seed of 1 will not produce any sequence
		for(int w=0;w<(Columns-1);w++) //Counts Columns
		{
			for(int x=0;x<Rows;x++) //Counts Rows
			{
				Seed_Table[x][w]=Current_Seed; //Sets cell to value of Current_Seed counter
				Count+=1;
				Current_Seed+=1; //Increments Current_Seed counter
				if(Count==Seed_Num) //breaks loop at Current_Seed=Seeds+1 as Current_Seed is increased at end of loop
				{
					break; //break loop if ^ true
				}
			}
		}
		for(int a=0;a<Rows;a++) //This snippet will just print the Seed_Table out in a more understandable format, only for testing
		{
			System.out.println(Arrays.toString(Seed_Table[a]));
		}
		Input_Nodes.close();
		Input_Start_Seed.close();
		Input_Seed_Num.close();
		return Seed_Table;
	}
}