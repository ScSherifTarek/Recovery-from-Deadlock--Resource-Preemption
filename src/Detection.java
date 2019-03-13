
/**
 * 
 * @author Sherif Tarek 20160115
 * @author Ayman Gamal  20160075
 * @author Usama Fouad  20160047
 *
 */

public class Detection {

	private static int n = 5,m = 3;
	private static int alloc[][] = new int[n][m];
	private static int req[][] = new int[n][m];
	private static int avail[] = new int[m];
	private static Boolean finish[] = new Boolean[n];
	private static Boolean terminated[] = new Boolean[n];
	private static String seq="";
	
	public static void fill()
	{
		alloc[0][0] = 0;
		alloc[0][1] = 1;
		alloc[0][2] = 0;
		
		alloc[1][0] = 2;
		alloc[1][1] = 0;
		alloc[1][2] = 0;
		
		alloc[2][0] = 3;
		alloc[2][1] = 0;
		alloc[2][2] = 3;
		
		alloc[3][0] = 2;
		alloc[3][1] = 1;
		alloc[3][2] = 1;
		
		alloc[4][0] = 0;
		alloc[4][1] = 0;
		alloc[4][2] = 2;
		
		
		req[0][0] = 0;
		req[0][1] = 0;
		req[0][2] = 0;
		
		req[1][0] = 2;
		req[1][1] = 0;
		req[1][2] = 2;
		
		req[2][0] = 0;
		req[2][1] = 0;
		req[2][2] = 1;
		
		req[3][0] = 1;
		req[3][1] = 0;
		req[3][2] = 1;
		
		req[4][0] = 0;
		req[4][1] = 0;
		req[4][2] = 2;
		
		avail[0]=0;
		avail[1]=0;
		avail[2]=0;
		
		for(int i=0;i<n;i++)
			finish[i] = false;
		
		for(int i=0;i<n;i++)
			terminated[i] = false;
	}
	
	
	public static void algo()
	{
		Boolean thereIsaAChange;
		do
		{
			thereIsaAChange = false;
			for(int i=0;i<n;i++)
			{
				if(!finish[i] && isSmaller(req[i],avail)) // request of process i is smaller than avail or not
				{
					for(int k=0;k<m;k++)
						avail[k] += alloc[i][k];
					finish[i] = true;
					seq += "P"+i+", ";
					thereIsaAChange = true;
				}
			}
		}while(thereIsaAChange);
	}
	
	
	public static void deadLockRecovery()
	{
		if(isFinished())
		{
			System.out.println("Safe");
			System.out.println(seq);
		}
		else
		{
			System.out.println("Dead Lock happened");
			System.out.println("Recovering from the deadlock ...");
			
			while(!isFinished())
			{
				if(!release()) // if there's no victims to choose from
					break;
				algo();
			}
			if(!isFinished())
				System.out.println("We can't recover from this deadlock - deadlock state");
			else
				System.out.println("recovered from the deadlock - safe state");
			System.out.println(seq);
		}
	}
	
	private static Boolean release()
	{
		int vic = getMin();
		if(vic == -1) // can't get the min (all processes have been terminated)
			return false;
		
		System.out.println("Victim is P"+vic);
		for(int i =0; i<m;i++)
		{
			avail[i] += alloc[vic][i];
			req[vic][i] += alloc[vic][i];
			alloc[vic][i] = 0;
			terminated[vic] = true;
		}
		return true;
	}
	
	private static int getMin()
	{
		int min = (int) 1e9;
		int minIndex = -1;
		int temp;
		for(int i=0;i<n;i++)
		{
			if(!finish[i] && !terminated[i] )
			{
				temp = sum(alloc[i]);
				if(temp < min)
				{
					min = temp;
					minIndex = i;
				}
			}
		}
		return minIndex;
	}
	
	private static Boolean isFinished()
	{
		for(int i=0;i<n;i++)
			if(finish[i] == false)
				return false;
		return true;
	}
	
	private static Boolean isSmaller(int arr1[], int arr2[])
	{
		for(int i=0;i<arr1.length;i++)
			if(arr1[i] > arr2[i])
				return false;
		return true;
	}
	
	
	
	private static int sum(int arr[])
	{
		int sum=0;
		for(int i=0;i<arr.length;i++)
			sum += arr[i];
		return sum;
	}
	
	
	public static void main(String[] args)
	{
		fill();
		algo();
		deadLockRecovery();
	}
}
