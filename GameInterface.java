import java.util.*;
import java.util.Arrays;
/**
 * Problem Set 4
 * @author Tayeb Mohammadi
 */

public class GameInterface{


    public static void main(String[] args) throws Exception {

        //Creating instances of the Graphlib class and the BuildGraph class
        BuildGraph<String, Set<String>> theMap  = new BuildGraph();
        GraphLib Testing = new GraphLib();      //instance of the GraphLib Class
        BuildGraph bGraph = new BuildGraph();  //instance of the BuildGraph Class
        Boolean gameStatus = true;
        Graph<String, Set<String>> theGraph= theMap.makeGraph();
        while (gameStatus){
        System.out.println("" +
                "Hey Welcome to the Bacon game. Here are some Commands to follow :\n" +
                "c <#>: list top (positive number) or bottom (negative) <#> centers of the universe, sorted by average separation\n" +
                "d <low> <high>: list actors sorted by degree, with degree between low and high\n" +
                "i: list actors with infinite separation from the current center\n" +
                "p <name>: find path from <name> to current center of the universe\n" +
                "s <low> <high>: list actors sorted by non-infinite separation from the current center, with separation between low and high\n" +
                "u <name>: make <name> the center of the universe\n" +
                "q: quit game");
        Scanner in = new Scanner(System.in);
        String line;
        System.out.println("Enter your command:  ");
        line = in.nextLine();
        System.out.println(theMap.theGraph);
        for(int i = 0; i<line.length(); i++){
            if(line.charAt(i) == 'p'){
                String name =  line.split(" ")[1];
                System.out.println(name);
                if(!theMap.theGraph.hasVertex(name)){
                    System.out.println("No such peron in the stars 😤");
                } else{
                    System.out.println("The path from " + name + " to the current center of the universe:  ");
                    System.out.println(GraphLib.getPath(GraphLib.bfs(theGraph, "Kevin Bacon"), name));
                }
            } else if(line.charAt(i) == 'd') {
                ArrayList<String> actorsByDegree = new ArrayList<>();
                Integer low = Integer.parseInt(line.split(" ")[1]);
                Integer high = Integer.parseInt(line.split(" ")[2]);

                for (String actorNode: theMap.theGraph.vertices()){
                    if(low<Testing.numOfCostars(theMap.makeGraph(), actorNode) && Testing.numOfCostars(theMap.theGraph, actorNode) < high){
                        actorsByDegree.add(actorNode);
                    }
                }
                actorsByDegree.sort((String s1, String s2) ->  theMap.theGraph.outDegree(s1) - theMap.theGraph.outDegree(s2));
                System.out.println(actorsByDegree);

            } else if(line.charAt(i) == 's'){
                Integer low = Integer.parseInt(line.split(" ")[1]);
                Integer high = Integer.parseInt(line.split(" ")[1]);
                ArrayList<Set<String>> toCompare = new ArrayList();
                for(String Vertex: theMap.makeGraph().vertices()){
                    Integer size  = GraphLib.getPath(theMap.theGraph, Vertex).size();
                    if( size > low && size < high){
                        Set<String> theSet = new HashSet<>();
                        theSet.add(Vertex);
                        toCompare.add(theSet);
                    }
                }
            }else if(line.charAt(i) == 'i'){
                ArrayList<String> infiniteActors = new ArrayList<>();
                for (String actorNode: theMap.theGraph.vertices()){
                    if(GraphLib.getPath(theMap.theGraph, actorNode) == null){
                        infiniteActors.add(actorNode);
                    }
                }
                System.out.println(infiniteActors);
            }else if(line.charAt(i) == 'u'){
                try {
                    if(theMap.theGraph.hasVertex(line.split(" ")[1])){
                        Testing.changeCenter(theMap.theGraph, line.split(" ")[1]);
                    }
                } catch (Exception error){
                    System.out.println("There is no such person in the universe");
                }
            }else if (line.charAt(i) == 'c'){
                ArrayList<String> sorted = new ArrayList<>();
                ArrayList<Integer> sortedInt = new ArrayList<>();
                Map<Integer, String> theSep = new HashMap<>();
                for(String vertex : theMap.theGraph.vertices()){
                    int separation = (int)GraphLib.averageSeparation(theMap.theGraph , vertex);
                    theSep.put(separation, vertex);
                    sortedInt.add(separation);
                }
                // bubble sort
                for(int k = 0; k< sortedInt.size(); k++){
                    for(int j = 0; j<sortedInt.size();j++){
                        if(sortedInt.get(k) > sortedInt.get(j)){
                            int temp = sortedInt.get(k);
                            sortedInt.set(k, sortedInt.get(j));
                            sortedInt.set(j, temp);
                        }
                    }
                }
                for(int m = 0; m<sortedInt.size(); m++){
                    sorted.add(theSep.get(sortedInt.get(m)));
                }

                System.out.println(sorted);
            }
            else if(line.charAt(i) == 'q'){
                gameStatus = false;
            }
        }
    }}

}
