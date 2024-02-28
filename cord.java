package yea;
import java.util.Random;
public class cord implements Comparable<cord>
{
	int x;
	int y;
	boolean blocked;
	int f;
	int g;
	int h;
	public cord(int x, int y)
	{
		this.x=x;
		this.y=y;
		h=-1;
		g=Integer.MAX_VALUE;
		Random randI = new Random();
	    int myRandInt = randI.nextInt(100)+1;
	    if(myRandInt>30)// if rand is greater than 30 set cordinate to unblocked
	    {
	    	blocked=false;
	    }
	    else
	    	blocked=true;
	    
	}
	public void cal(int h, int g)//get the h value
	{
		this.h=h;
		this.g=g;
		f=100*h+90*g;
	}
	public String toString1()
	{
		if(blocked)
		{
			return "1";
		}
		else
			return "0";
	}
	public String toString()
	{
		return "("+x+" "+y+")";
	}
	@Override
	public int compareTo(cord o) {
		// TODO Auto-generated method stub
		return this.f - o.f;
	}
	
	
}
