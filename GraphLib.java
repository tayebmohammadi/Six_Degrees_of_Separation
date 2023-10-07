import java.io.IOException;
import java.util.*;

/**
 * Problem Set 4
 * @author  Tayeb Mohammadi
 */
public class GraphLib<V, E>{
	public static <V, E> Graph<V, E> bfs(Graph<V, E> g, V source) {
		Graph<V, E> pathTree = new AdjacencyMapGraph<>();
		Set<V> visited = new HashSet<V>(); //Set to track which vertices have already been visited
		Queue<V> queue = new LinkedList<V>(); //queue to implement BFS
		queue.add(source); //enqueue start vertex
		visited.add(source); //add start to visited Set
		while (!queue.isEmpty()) { //loop until no more vertices
			V u = queue.remove(); //dequeue
			pathTree.insertVertex(u);
			for (V v : g.inNeighbors(u)) { //loop over out neighbors
				if (!visited.contains(v)) { //if neighbor not visited, then neighbor is discovered from this vertex
					visited.add(v); //add neighbor to visited Set
					queue.add(v); //enqueue neighbor
					pathTree.insertVertex(v);
					pathTree.insertDirected(v, u, g.getLabel(u, v));
				}
			}
		}
		return pathTree;
	}
	/**
	 * get the path from a node to the current center
	 */

		public static <V, E> List<V> getPath(Graph<V, E> tree, V v) {
		if(!tree.hasVertex(v)) return null;
		List<V> pathList = new ArrayList<>();
		pathList.add(v);
		V curr = v;
		while (tree.outDegree(curr) != 0){
			for (V value : tree.outNeighbors(curr)) {
				curr = value;
			}
			pathList.add(curr);
		}
		return pathList;
	}

	/**
	 * compare main graph and our subGraph looking for missing vertices and return them
	 */
	public static <V, E> Set<V> missingVertices(Graph<V, E> graph, Graph<V, E> subgraph) {
		Set<V> mVertices = new HashSet<>();
		for(V v : graph.vertices()){
			if (!subgraph.hasVertex(v)){ //if the vertex isnt common to both, add it
				mVertices.add(v);
			}
		}
		return mVertices;
	}

	/**
	 * find the average separation between nodes using recursion
	 */

	public static <V, E> double averageSeparation(Graph<V, E> tree, V Start) throws Exception {
		return totalSeparation(tree, Start, 0) / tree.numVertices(); //call helper func
	}

	/**
	 * helper function/the recursive function
	 */
	public static <V, E> double totalSeparation(Graph<V, E> tree, V Start, int total) throws Exception {
		int num = total;
		if(tree.inDegree(Start) == 0) return num; // base case
		else{
			for(V node: tree.inNeighbors(Start)){
				totalSeparation(tree, node, num + 1); //recurse
			}
		}
		return num;
	}
	/**
	 * change the center of the shortest tree graph to another actor
	 */
	public Graph<V, E>  changeCenter(Graph<V, E> newPathTree, V root){
		return bfs(newPathTree, root); // chane the center to root
	}
	/**
	 * number of actors with certain number of steps to the center of the universe
	 */
	public int numOfActors(Graph<V, E> tree, int numberOfSteps){
		int numOfActs = 0;
		for (V vertex: tree.vertices()){
			if(getPath(tree, vertex).equals(numberOfSteps)){
				numOfActs += 1;
			}
		}
		return numOfActs;
	}
	/**
	 * find the coStars of a certain actor
	 */

	public int numOfCostars(Graph<V, E> graph, V v){
			return graph.inDegree(v);
	}
	public static void main(String[] args) throws IOException {
		BuildGraph<String, Set<String>> theMap  = new BuildGraph();
		GraphLib Testing = new GraphLib();
		Graph<String, Set<String>> theGraph= theMap.makeGraph();
		System.out.println("-------------------");
		System.out.println(missingVertices(theGraph, bfs(theGraph, "Kevin Bacon")));
	}
}

