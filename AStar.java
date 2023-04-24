/**
 * Implements the A* search algorithm to find the shortest path
 * in a graph between a start node and a goal node.
 * It returns a Path consisting of a list of Edges that will
 * connect the start node to the goal node.
 */

import java.util.Collections;
import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;


public class AStar {


    private static String tOrD = "distance";    // way of calculating cost: "time" or "distance"
    

    // find the shortest path between two stops
    public static List<Edge> findShortestPath(Stop start, Stop goal, String timeOrDistance) {
        if (start == null || goal == null) {return null;}
        if(start.getId()==goal.getId()){
            return null;
        }
        timeOrDistance= (tOrD.equals("time"))?"time":"distance";
        double lengthSoFar = 0;
        //Comparator<PathItem> compare = new 
        PriorityQueue<PathItem> fringe = new PriorityQueue<PathItem>();
        Set<Stop> vistited = new HashSet<Stop>();
        //ArrayList<PathItem> backPointers = new ArrayList<PathItem>();
        Map<Stop, Edge> backPointers = new HashMap<Stop, Edge>();

        fringe.add(new PathItem(start, null, 0, heuristic(start, goal)));
        Edge correctEdge = null;
        //Stop neighbour = null;
        

        while(!fringe.isEmpty()){

            PathItem head = fringe.poll();
            
            lengthSoFar = head.getLengthSoFar();
            if(!vistited.contains(head.getStop())){
                //mark visited
                vistited.add(head.getStop());
                
                //put node and edge in backpointers
                if(head.getStop().getId()==goal.getId()){
                    //backPointers.add(null, correctEdge, null, null);
                    return ReconstructPath(start, goal, backPointers);
                }
                Collection<Stop> neighbours = head.getStop().getNeighbours();
                correctEdge = null;
                for(Stop neighbour : neighbours){
                    if(!vistited.contains(neighbour)){
                        for(Edge edge: head.getStop().getForwardEdges()){
                            if(edge.toStop().getId()==neighbour.getId()){
                                correctEdge = edge;
                                break;
                            }
                        }
                        if(correctEdge == null){
                            System.out.println("edge not found");
                        }
                        PathItem item = new PathItem(neighbour, correctEdge, lengthSoFar+head.getStop().distanceTo(neighbour), lengthSoFar+head.getStop().distanceTo(neighbour)+heuristic(neighbour, goal));
                        backPointers.put(neighbour, correctEdge);
                        fringe.add(item);
                        
                    }
                }
            }



        }

        return null;  
    }

    private static ArrayList<Edge> ReconstructPath(Stop start, Stop goal, Map<Stop, Edge> backPointers){
        ArrayList<Edge> line = new ArrayList<Edge>();
        if(backPointers.size()<=0){
            System.out.println("Back pointers unpopulated!!!!!");
            return null;
        }
        Stop current = goal;
        //Edge edge = backPointers.get(backPointers.size()-1).getEdge();
        while(current!= start){
            Edge edge = backPointers.get(current);

            if(edge == null){
                System.out.println("Edge is null");
                return null;
            }

            
            line.add(edge);
            current = edge.fromStop();
            
            

        }



        Collections.reverse(line);
        return line;
    }





    /** Return the heuristic estimate of the cost to get from a stop to the goal */
    public static double heuristic(Stop current, Stop goal) {
        if (tOrD=="distance"){ return current.distanceTo(goal);}
        else if (tOrD=="time"){return current.distanceTo(goal) / Transport.TRAIN_SPEED_MPS;}
        else {return 0;}
    }

    /** Return the cost of traversing an edge in the graph */
    public static double edgeCost(Edge edge){
        if (tOrD=="distance"){ return edge.distance();}
        else if (tOrD=="time"){return edge.time();}
        else {return 1;}
    }




}
