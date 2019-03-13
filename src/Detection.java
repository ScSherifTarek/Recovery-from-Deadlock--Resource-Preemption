
/**
 * 
 * @author Sherif Tarek 20160115
 * @author Ayman Gamal  20160075
 * @author Usama Fouad  20160047
 *
 */

public class Detection {

	static int n = 5,m = 3;
	public static int alloc[][] = new int[n][m];
	public static int req[][] = new int[n][m];
	public static int avail[] = new int[m];
	public static Boolean finish[] = new Boolean[n];
	public static Boolean terminated[] = new Boolean[n];
	public static String seq="";
	
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
	
	public static Boolean isFinished()
	{
		for(int i=0;i<n;i++)
			if(finish[i] == false)
				return false;
		return true;
	}
	
	public static Boolean isSmaller(int arr1[], int arr2[])
	{
		for(int i=0;i<arr1.length;i++)
			if(arr1[i] > arr2[i])
				return false;
		return true;
	}
	
	
	public static void algo()
	{
		for(int j=0;j<n;j++)
		{
			for(int i=0;i<n;i++)
			{
				if(!finish[i] && isSmaller(req[i],avail)) // request of process i is smaller than avail or not
				{
					for(int k=0;k<m;k++)
						avail[k] += alloc[i][k];
					finish[i] = true;
					seq += "P"+i+", ";
				}
			}
		}
	}
	
	
	public static void deadLock()
	{
		if(isFinished())
		{
			System.out.println("Safe");
			System.out.println(seq);
		}
		else
		{
			System.out.println("Dead Lock");
			algo();
			int count=1;
			while(!isFinished() && count <=n)
			{
				release();
				algo();
				count++;
			}
			if(!isFinished())
				System.out.println("DeadLock");
			else
				System.out.println("Safe");
			System.out.println(seq);
		}
	}
	
	public static void release()
	{
		int vic = getMin();
		System.out.println("Victim is P"+vic);
		for(int i =0; i<m;i++)
		{
			avail[i] += alloc[vic][i];
			req[vic][i] += alloc[vic][i];
			alloc[vic][i] = 0;
			terminated[vic] = true;
		}
	}
	
	public static int sum(int arr[])
	{
		int sum=0;
		for(int i=0;i<arr.length;i++)
			sum += arr[i];
		return sum;
	}
	
	public static int getMin()
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
	
	public static void main(String[] args)
	{
		fill();
		algo();
		deadLock();
	}
}
