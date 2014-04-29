/* Kruskal.java */

package graphalg;

import graph.*;
import set.*;
import list.*;
import dict.*;

/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {

  /**
   * minSpanTree() returns a WUGraph that represents the minimum spanning tree
   * of the WUGraph g.  The original WUGraph g is NOT changed.
   *
   * @param g The weighted, undirected graph whose MST we want to compute.
   * @author Sebastian Merz
   * @return A newly constructed WUGraph representing the MST of g.
   */
  public static WUGraph minSpanTree(WUGraph g) {
    WUGraph output = new WUGraph();
    Object[] origVertices = g.getVertices();
    DList<Edge> edgeList = new DList();
    HashTable<Edge,Integer> edgeTable = new HashTable(g.edgeCount());
    HashTable<Object,Integer> vertexTable = new HashTable(origVertices.length);

    int j=0; //We keep (:) loop for elegance, but we need a counter for later.
    for(Object item:origVertices){
      vertexTable.insert(item, j);
      j++;
      output.addVertex(item);
      Neighbors neighbors = g.getNeighbors(item);
      int i;
      for(i=0;i<neighbors.neighborList.length;i++){
        //This is going to give us every edge twice. The alternative is fancy detection though.
        //Specifically via a hashtable. If we need one anyway we'll make it here.
        //Oh fuck it, lets do it.
        Edge e = new Edge(item, neighbors.neighborList[i], neighbors.weightList[i]);

        if(edgeTable.find(e) == null){
          edgeList.insertFront(e);
          edgeTable.insert(e, 1);
        }
      }
    }

    edgeList.sort();

    DisjointSets linkedSet = new DisjointSets(origVertices.length);

    for(Edge edge:edgeList){
      int v1Int = vertexTable.find(edge.vertex1).value();
      int v2Int = vertexTable.find(edge.vertex2).value();

      if(linkedSet.find(v1Int) != linkedSet.find(v2Int)){
        //In this case, these two vertices are not connected yet.
        linkedSet.union(v1Int, v2Int);
        output.addEdge(edge.vertex1, edge.vertex2, edge.weight);
      }
    }

    return output;
  }

}
