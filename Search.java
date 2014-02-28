import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Set;


class CostComparator implements Comparator<Path>{
	
    @Override
    public int compare(Path p1, Path p2) 
    {
        if(p1.cost < p2.cost)
        	return -1;
        else if(p1.cost == p2.cost)
        	return 0;
        else
        	return 1;
    }
}

class AwardComparator implements Comparator<Path>{
	
    @Override
    public int compare(Path p1, Path p2) 
    {
        if(p1.award < p2.award)
        	return -1;
        else if(p1.award == p2.award)
        	return 0;
        else
        	return 1;
    }
}

class Path
{
	float cost, award;
	
	ArrayList<Integer> nodes;
	Graph graph;
	
	public Path(Graph graph)
	{
		cost = 0.0f;
		award = 0.0f;
		nodes = new ArrayList<Integer>();
		this.graph = graph;
	}
	
	public Path(Graph graph, Path another)
	{
		cost = another.cost;
		award = another.award;
		nodes = new ArrayList<Integer>();
		nodes.addAll(another.nodes);
		this.graph = graph;
	}
	
	public void addNode(int node)
	{
		if(nodes.size() > 0)
		{
    		int prev = nodes.get(nodes.size()-1);
    		
    		nodes.add(node);
    		
			Pair pair = graph.getEdge(prev, node);
			
			cost += pair.getCost();
			award += pair.getAward();
		}
		else
		{
    		nodes.add(node);
		}

	}
	
	public void removeLast()
	{
		//int cur = nodes.get(nodes.size()-1);
		int cur = nodes.remove(nodes.size()-1);
		
		if(nodes.size() > 0)
		{
    		int prev = nodes.get(nodes.size()-1);
			Pair pair = graph.getEdge(prev, cur);
    		
			cost -= pair.getCost();
			award -= pair.getAward();	
		}
	}
	
	public int getLast()
	{
		return nodes.get(nodes.size()-1);
	}
	
	public boolean contains(int node)
	{
		return nodes.contains(node);
	}
	
	@Override
	public String toString()
	{
		String path = "";
		path += (cost + "\t" + award + "\t");
		
		for(int node : nodes)
		{
			path += (node + " ");
		}
		return path;
	}
}


public class Search {

    private static final int START = 2;
    private static final int END = 5;
    private static final int Edges = 21;

    public static void main(String[] args) 
    {
        // this graph is directional
        Graph graph = new Graph();
        float [] cost = new float[Edges];
        float [] award = new float[Edges];
        
        java.util.Random r=new java.util.Random(10);
        for(int i=0; i<Edges; i++)
        {
        	cost[i] = r.nextFloat()*100;
        	award[i]= r.nextFloat()*100;
        	
        	//System.out.println(cost[i] + " " + award[i]);
        }
        
        graph.addTwoWayVertex(1, 2, cost[0], award[0]);
        graph.addTwoWayVertex(1, 4, cost[1], award[1]);       
        graph.addTwoWayVertex(1, 7, cost[2], award[2]);
        graph.addTwoWayVertex(2, 4, cost[3], award[3]);
        graph.addTwoWayVertex(2, 3, cost[4], award[4]);
        graph.addTwoWayVertex(3, 5, cost[5], award[5]);
        graph.addTwoWayVertex(3, 8, cost[6], award[6]);
        graph.addTwoWayVertex(4, 5, cost[7], award[7]);
        graph.addTwoWayVertex(4, 9, cost[8], award[8]);
        graph.addTwoWayVertex(5, 6, cost[9], award[9]);       
        graph.addTwoWayVertex(5, 7, cost[10], award[10]);         
        graph.addTwoWayVertex(5, 9, cost[11], award[11]);        
        graph.addTwoWayVertex(6, 8, cost[12], award[12]);        
        graph.addTwoWayVertex(6, 10,cost[13], award[13]);           
        graph.addTwoWayVertex(7, 8, cost[14], award[14]);       
        graph.addTwoWayVertex(8, 10,cost[15], award[15]);       
        graph.addTwoWayVertex(9, 10,cost[16], award[16]);
        graph.addTwoWayVertex(9, 11,cost[17], award[17]); 
        graph.addTwoWayVertex(10,11,cost[18], award[18]); 
        graph.addTwoWayVertex(8, 12,cost[19], award[19]);
        graph.addTwoWayVertex(10,12,cost[20], award[20]);
        
        
        ArrayList<Path> paths = new ArrayList<Path>();
        
        Path active = new Path(graph);
        //LinkedList<Integer> visited = new LinkedList();
        active.addNode(START);
     
        Search search =  new Search();
        
        search.DFSSearch(graph, active, paths);
        
        search.printAllPath(paths);
    }

    private void DFSSearch(Graph graph, Path active, ArrayList<Path> paths)
    {
    	LinkedHashMap<Integer, Pair> nodes = graph.adjacentNodes(active.getLast());
        
    	Integer []nodeset = new Integer[nodes.size()];
    	nodes.keySet().toArray(nodeset);
    	
        // examine adjacent nodes
    	for(int i=0; i<nodes.size(); i++)
    	{
            if (active.contains(nodeset[i]))
            {
                continue;
            }
            if (nodeset[i] == END)
            {
            	//active.addNode(nodeset[i]);
            	Path path = new Path(graph, active);
            	path.addNode(nodeset[i]);
            	
            	//if(path.cost < 300)
            	paths.add(path);
            	
                //active.removeLast();
                break;
            }
    	}
        
        // in breadth-first, recursion needs to come after visiting adjacent nodes
    	for(int i=0; i<nodes.size(); i++)
        {
            if (active.contains(nodeset[i]) || nodeset[i].equals(END)) {
                continue;
            }
            active.addNode(nodeset[i]);
            DFSSearch(graph, active, paths);
            active.removeLast();
        }
    }

    private void printAllPath(ArrayList<Path> paths)
    {
    	CostComparator costcomp = new CostComparator();
    	AwardComparator awardcomp = new AwardComparator();
    	
    	Collections.sort(paths, costcomp);
    	
    	int index = 1;
    	for(Path path: paths)
    	{
    		System.out.println((index++) + ":" + path.toString());
    	}
    }
}