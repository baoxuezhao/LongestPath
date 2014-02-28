import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

class Pair
{
	float cost;
	float award;
	
	public Pair(float cost, float award)
	{
		this.cost = cost;
		this.award = award;
	}
	
	public float getCost()
	{
		return cost;
	}
	
	public float getAward()
	{
		return award;
	}
}

public class Graph 
{
	private	boolean directed = false;
	
    private Map<Integer, LinkedHashMap<Integer, Pair>> map = new HashMap();

    public void addEdge(int node1, int node2, float cost, float award) 
    {
        LinkedHashMap<Integer, Pair> adjacent = map.get(node1);
        
        if(adjacent==null) 
        {
            adjacent = new LinkedHashMap<Integer, Pair>();
            map.put(node1, adjacent);
        }
        adjacent.put(node2, new Pair(cost, award));
    }

    public void addTwoWayVertex(int node1, int node2, float cost, float award) 
    {
        addEdge(node1, node2, cost, award);
        addEdge(node2, node1, cost, award);
    }
    
    public void addTwoWayVertex(int node1, int node2) 
    {
        addEdge(node1, node2, 0, 0);
        addEdge(node2, node1, 0, 0);
    }

    public boolean isConnected(int node1, int node2) 
    {
    	LinkedHashMap<Integer, Pair> adjacent = map.get(node1);
    	
        if(adjacent==null) {
            return false;
        }
        return adjacent.containsKey(node2);
    }
    
    public Pair getEdge(int node1, int node2)
    {
    	LinkedHashMap<Integer, Pair> adjacent = map.get(node1);
    	
    	if(adjacent == null)
    		return null;
    	
    	return adjacent.get(node2);
    }

    public LinkedHashMap<Integer, Pair> adjacentNodes(int last) 
    {
        LinkedHashMap<Integer, Pair> adjacent = map.get(last);
        
        return adjacent;
    }
}