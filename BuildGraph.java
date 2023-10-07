import java.io.*;
import java.util.*;
/**
 * Problem Set 4
 * @author Tayeb Mohammadi
 */
public class BuildGraph<V, E>{
    Graph<String, Set<String>> theGraph = new AdjacencyMapGraph<>();
    public BuildGraph(){
    }
    /**
     *  Reading from the actors file
     */
    public static Map<Integer, String> readActors() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("bacon/actorsTest.txt"));
        String line;
        Map<Integer, String> actorsToID = new HashMap<Integer, String>();
        while((line = reader.readLine()) != null){
            actorsToID.put(Integer.parseInt(line.split("\\|")[0]), line.split("\\|")[1]);
        }
        reader.close();
        return actorsToID;
    }
    /**
     *  Reading from the movies file
     */

    public static Map<Integer, String> readMovies() throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader("bacon/moviesTest.txt"));
        Map<Integer, String> movies = new HashMap<>();
        String line;
        while((line = reader.readLine()) != null){
            movies.put(Integer.parseInt(line.split("\\|")[0]), line.split("\\|")[1]);
        }
        reader.close();
        return movies;
    }
    /**
     *  Reading from the actorstomovies file
     */
    public static Map<String, ArrayList<String>> actorsToMovies() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("bacon/movie-actorsTest.txt"));
        Map<String, ArrayList<String>> moviesToActors = new HashMap<>();
        String line;

        while((line = reader.readLine()) != null){
            String movie =  readMovies().get(Integer.parseInt(line.split("\\|")[0]));
            String actor = readActors().get(Integer.parseInt(line.split("\\|")[1]));
            if(moviesToActors.containsKey(movie)){
                moviesToActors.get(movie).add(actor);
            } else{
                ArrayList<String> value = new ArrayList<>();
                value.add(actor);
                moviesToActors.put(movie, value);
            }
        }

        reader.close();
        return moviesToActors;
    }
    /**
     *  making the graph
     */
    public Graph<String, Set<String>> makeGraph() throws IOException {
        for(Map.Entry<Integer, String> actor: readActors().entrySet()){ // loop through the actors from actors file
            theGraph.insertVertex(actor.getValue()); //insert each actor into the graph as a vertex
        }
        for(Map.Entry<String, ArrayList<String>> Record: actorsToMovies().entrySet()){// loop through the movies from movies file
            String movie = Record.getKey();
            for(int i= 0; i<Record.getValue().size(); i++){
                for(int j = i+1; j<Record.getValue().size(); j++){
                    if(!theGraph.hasEdge(Record.getValue().get(i), Record.getValue().get(j))){
                        Set<String> values = new HashSet<>(); //if edge label is non-existent, add it
                        values.add(Record.getKey());
                        theGraph.insertUndirected(Record.getValue().get(i), Record.getValue().get(j), values);
                    }else{
                        Set<String> set = theGraph.getLabel(Record.getValue().get(i),Record.getValue().get(j));
                        set.add(movie); //if edge label is already there, update it
                        theGraph.insertUndirected(Record.getValue().get(i), Record.getValue().get(j), set);
                    }
                }
            }
        }
        return theGraph;
    }
    public static void main(String[] args) throws IOException {
        BuildGraph v = new BuildGraph();
        v.makeGraph();
    }

}


