/**
 * AStar search (and Dijkstra search) uses a priority queue of partial paths
 * that the search is building.
 * Each partial path needs several pieces of information, to specify
 * the path to that point, its cost so far, and its estimated total cost
 */

public class PathItem implements Comparable<PathItem> {

    // TODO
    public Stop stop = null;
    public Edge edge = null;
    public double length = 0;
    public double estimate = 0;

    //Astar
    public PathItem(Stop stop, Edge edge, double length, double estimate){
        this.stop = stop;
        this.edge = edge;
        this.length = length;
        this.estimate = estimate;
    }

    @Override
    public int compareTo(PathItem o) {
        if(this.estimate == o.estimate){
            return 0;
        }
        else if(this.estimate > o.estimate){
            return 1;
        }
        else{
            return -1;
        }
    }

    public double getEstimate(){
        return this.estimate;
    }
    public Stop getStop(){
        return this.stop;
    }
    public double getLengthSoFar(){
        return length;
    }
    public Edge getEdge(){
        return this.edge;
    }
}
