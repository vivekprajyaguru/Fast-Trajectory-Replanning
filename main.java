package yea;
import java.util.*;
public class main 
{
	static int reach=0;//becomes one if agent can reach target
	public static void main(String[]args)
	{
		
		
		List<cord[][]>ma = new ArrayList<>();//List that stores 50 maps
		while(ma.size()<50)
		{
			cord[][] map= new cord[101][101];
			boolean[][]visted= new boolean[101][101];//for dfs search
			for(int i=0;i<map.length;i++)
			{
				for(int j=0;j<map[i].length;j++)
				{
					map[i][j]= new cord(i,j);
				}
				
			}
			
			dfs(map,0,0,map[100][100],visted);
			if(reach==1)//added to the list if map is reachable
			ma.add(map);
			reach=0;
		}
		Scanner scan= new Scanner(System.in);
		String input="";
		do {
			System.out.println("which function to test?");
			input=scan.nextLine();
			if(input.equals("adapt"))
				{
				long startTime1 = System.nanoTime();
					adapt(ma.get(0),0,0,100,100);
					adapt(ma.get(0),20,20,100,100);
				long endTime1 = System.nanoTime();
					System.out.println("adapt Took "+(endTime1 - startTime1) + " ns"); 
				}
			else if(input.equals("astar"))
			{
				long startTime1 = System.nanoTime();
				Astar(ma.get(0),0,0,100,100);
				Astar(ma.get(0),20,20,100,100);
			long endTime1 = System.nanoTime();
				System.out.println("astar Took "+(endTime1 - startTime1) + " ns"); 
			}
			
		}while(!input.equals("end"));
		
		
		   
	}
	
	public static void adapt(cord[][]map,int tx,int ty,int sx, int sy)//adaptive Astar algo
	{
		int c=0;
		
		 int path[][]= new int[map.length][map.length];// see how the algothrim goes in which direction to search
		 PriorityQueue<cord> pq = new PriorityQueue<cord>();
		 int[][]visited= new int[map.length][map.length];
		 for(int i=0;i<map.length;i++)
		 Arrays.fill(visited[i],Integer.MAX_VALUE);
		 map[sx][sy].g=0;
		 map[sx][sy].cal(200,0);
		 pq.add(map[sx][sy]);//adding starting node need to change the 0 value as right now assuming every grid it start from 0 0
		 int[][] dir = new int[][]{{0, -1},{1,0},{-1,0},{0,1}};//directions
		 while(!pq.isEmpty())
		 {
			 cord cur= pq.remove();
			 
			 int x=cur.x;
			 int y=cur.y;
			// System.out.println("x:"+x +" y:"+y);									
			 if(x==tx&&y==ty)
			 {
				 for(int i=0;i<path.length;i++)
				 {
					// System.out.println();
					 for(int j=0;j<path.length;j++)
					 {
						 //System.out.print("|"+visited[i][j]+"|");
					 }
					 
				 }
				// System.out.println();
				 for(int i=0;i<path.length;i++)
				 {
					 //System.out.println();
					 for(int j=0;j<path.length;j++)
					 {
						 if(map[i][j].h<map[ty][tx].g-map[i][j].g)
						 map[i][j].h=map[ty][tx].g-map[i][j].g;
						// System.out.print("|"+map[i][j].h+"|");
					 }
					 
				 }
				// System.out.println();
				 for(int i=0;i<path.length;i++)
				 {
					// System.out.println();
					 for(int j=0;j<path.length;j++)
					 {
						// System.out.print("|"+path[i][j]+"|");
					 }
					 
				 }
				 //System.out.println("found:"+cur.g);
				 return;//found
			 }
			 if(visited[y][x]<=cur.g)
			 {
				
				 continue;
			 }
			 fillpath(path,x,y,c);
			 c++;
			//  System.out.println("c: "+c);
			 
			 visited[y][x]=cur.g;
			 int h=calh(new cord(x,y),new cord(x+1,y));
			 
			 for(int [] d:dir)
			 {
				 if (x+d[0] < 0 || x+d[0] >= map.length || y+d[1] < 0 || y+d[1] >= map.length || map[y+d[1]][x+d[0]].blocked)
				  {
				   
				   continue;
				  }
				 int g= cur.g+1;
				 int he=calh(cur,new cord(tx,ty));
				 if(map[y+d[1]][x+d[0]].h==-1)//A* Have not reached before
				 map[y+d[1]][x+d[0]].h=he;
				 else if(map[ty][tx].g!=Integer.MAX_VALUE)
				 he=map[ty][tx].g-map[y+d[1]][x+d[0]].g;
				 map[y+d[1]][x+d[0]].g=cur.g+1;
				 cord place= new cord(x+d[0],y+d[1]);
				 place.cal(he, g);
				 
				 pq.add(place);
				//System.out.print("added: ("+ (x+d[0])+" "+(y+d[1])+") ");
			 }
			// System.out.println(Arrays.toString(pq.toArray()));
			 
		 }
		
		 
		 System.out.println("cannot reach path");
	}
	public static void Astar(cord[][]map,int tx,int ty,int sx, int sy)//Astar search algo
	{
		
		int c=0;
		
		 int path[][]= new int[map.length][map.length];
		 PriorityQueue<cord> pq = new PriorityQueue<cord>();
		 int[][]visited= new int[map.length][map.length];
		 for(int i=0;i<map.length;i++)
		 Arrays.fill(visited[i],Integer.MAX_VALUE);
		 map[sx][sy].g=0;// need to change for start
		 map[sx][sy].cal(200,0);//need to change for start
		 pq.add(map[sx][sy]);//adding starting node need to change the 0 value as right now assuming every grid it start from 0 0
		 int[][] dir = new int[][]{{0, -1},{1,0},{-1,0},{0,1}};//directions
		 while(!pq.isEmpty())
		 {
			 cord cur= pq.remove();
			 
			 int x=cur.x;
			 int y=cur.y;
			 //System.out.println("x:"+x +" y:"+y);									
			 if(x==tx&&y==ty)
			 {
				 for(int i=0;i<path.length;i++)
				 {
					// System.out.println();
					 for(int j=0;j<path.length;j++)
					 {
						// System.out.print("|"+visited[i][j]+"|");
					 }
					 
				 }
				// System.out.println();
				 for(int i=0;i<path.length;i++)
				 {
					 //System.out.println();
					 for(int j=0;j<path.length;j++)
					 {
						// System.out.print("|"+map[i][j].h+"|");
					 }
					 
				 }
				// System.out.println();
				 for(int i=0;i<path.length;i++)
				 {
					 //System.out.println();
					 for(int j=0;j<path.length;j++)
					 {
						 //System.out.print("|"+path[i][j]+"|");
					 }
					 
				 }
				// System.out.println("wowo:"+cur.g);
				 return;//found
			 }
			 if(visited[y][x]<=cur.g)
			 {
				
				 continue;
			 }
			 fillpath(path,x,y,c);
			 c++;
			 // System.out.println("c: "+c);
			 
			 visited[y][x]=cur.g;
			 int h=calh(new cord(x,y),new cord(x+1,y));
			 
			 for(int [] d:dir)
			 {
				 if (x+d[0] < 0 || x+d[0] >= map.length || y+d[1] < 0 || y+d[1] >= map.length || map[y+d[1]][x+d[0]].blocked)
				  {
	
				   continue;
				  }
				 int g= cur.g+1;
				 int he=calh(cur,new cord(tx,ty));
				 map[y+d[1]][x+d[0]].h=he;
				 map[y+d[1]][x+d[0]].g=cur.g+1;
				 cord place= new cord(x+d[0],y+d[1]);
				 place.cal(he, g);
				 
				 pq.add(place);
				//System.out.print("added: ("+ (x+d[0])+" "+(y+d[1])+") ");
			 }
			// System.out.println(Arrays.toString(pq.toArray()));
			 
		 }
		
		 
		// System.out.println("cannot reach path");
		 
		 
	}
	public static void fillpath(int[][]map,int x,int y,int val)//help method to fill path 
	{
		if (x < 0 || x >= map.length || y < 0 || y >= map.length)
		{
			return;
		}
		map[y][x]=val;
	}
	
	public static void printmap(cord[][] map)//printout the map 0 means the block is open 1 means it is blocked
	{
		for(int i=0;i<map.length;i++)
		{
			System.out.print("|");
			for(int j=0;j<map[i].length;j++)
			{
				System.out.print(map[i][j].toString1());
			}
			System.out.println("|");
		}
	}
	public static void dfs(cord[][]map,int cx,int cy, cord target,boolean[][]visted)//dfs  to check wether if a path exist for the gridworld
	{
		if(map[target.y][target.x].blocked)
			return;
		if(reach!=0||cx>=map.length||cx<0||cy>=map.length||cy<0)
			return;
		if(visted[cy][cx]==true||map[cy][cx].blocked)
			return;
		
		if(cx==target.x&&cy==target.y)
		{
			
			reach=1;
			return;
		}
		visted[cy][cx]=true;
		dfs(map,cx+1,cy,target, visted);
		dfs(map,cx,cy+1,target, visted);
		dfs(map,cx-1,cy,target, visted);
		dfs(map,cx,cy-1,target, visted);
	}
	public static int calh(cord a, cord b)//calculate the heruistic between 2 cordinate which uses the manhanttan formula
	{
		int h= (int)(Math.abs(a.x-b.x)+Math.abs(a.y-b.y));
		return h;
	}
	

}
