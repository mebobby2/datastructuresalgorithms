import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class CheckRouteExists {

    public enum State {
        Unvisited, Visited, Visiting;
    }

  public static void main(String args[]) {
    GraphNode node1 = new GraphNode();
    GraphNode node2 = new GraphNode();
    GraphNode node3 = new GraphNode();
    GraphNode node4 = new GraphNode();
    GraphNode node5 = new GraphNode();
    GraphNode node6 = new GraphNode();

    node1.addNeighbor(node2);
    node1.addNeighbor(node3);

    node2.addNeighbor(node1);
    node2.addNeighbor(node6);

    node3.addNeighbor(node1);
    node3.addNeighbor(node4);
    node3.addNeighbor(node6);

    node4.addNeighbor(node3);
    node4.addNeighbor(node5);

    node5.addNeighbor(node4);

    node6.addNeighbor(node2);
    node6.addNeighbor(node3);

    CheckRouteExists c = new CheckRouteExists();
    System.out.println(c.routeExists(node1, node5));
  }

    public boolean routeExists(GraphNode start, GraphNode end) {
        LinkedList<GraphNode> q = new LinkedList<GraphNode>();

        start.state = State.Visiting;
        q.add(start);
        GraphNode u;
        while(!q.isEmpty()) {
            u = q.removeFirst();
            if (u != null) {
                for (GraphNode v: u.nodeNeighbours ) {
                    if (v.state == State.Unvisited) {
                        if (v == end) {
                            return true;
                        } else {
                            v.state = State.Visiting;
                            q.add(v);
                        }
                    }
                }
                u.state = State.Visited;
            }
        }
        return false;
    }

    public static class GraphNode {
        State state;
        List<GraphNode> nodeNeighbours;

        public GraphNode() {
            this.nodeNeighbours = new ArrayList<GraphNode>();  // store neighbors
            this.state = State.Unvisited;

        }

        public void addNeighbor(GraphNode target) {
            this.nodeNeighbours.add(target);    //This will add target to the neighbours of this given node.
        }
    }
}