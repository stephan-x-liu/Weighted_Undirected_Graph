/* WUGraph.java */

package graph;
import list.*;
import dict.*;
/**
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */

public class WUGraph {

  HashTable<Object,HashTable<Object,VertexPair>> vertices;
  HashTable<VertexPair,Integer> edges;

  /**
   * WUGraph() constructs a graph having no vertices or edges.
   *
   * Running time:  O(1).
   */
  public WUGraph(){
    vertices = new HashTable<Object,HashTable<Object,VertexPair>>(50);
    edges = new HashTable<VertexPair,Integer>(50);
  }

  /**
   * vertexCount() returns the number of vertices in the graph.
   *
   * Running time:  O(1).
   */
  public int vertexCount(){
    return vertices.size();
  }

  /**
   * edgeCount() returns the total number of edges in the graph.
   *
   * Running time:  O(1).
   */
  public int edgeCount(){
    return edges.size();
  }

  /**
   * getVertices() returns an array containing all the objects that serve
   * as vertices of the graph.  The array's length is exactly equal to the
   * number of vertices.  If the graph has no vertices, the array has length
   * zero.
   *
   * (NOTE:  Do not return any internal data structure you use to represent
   * vertices!  Return only the same objects that were provided by the
   * calling application in calls to addVertex().)
   *
   * Running time:  O(|V|).
   */
  public Object[] getVertices(){
    DList<Entry<Object,HashTable<Object,VertexPair>>> entries = vertices.entries();
    Object[] getverts = new Object[entries.length()];
    int count = 0;
    for(Entry k: entries){
      getverts[count] = k.key();
      count++; 
    }
    return getverts;
  }

  /**
   * addVertex() adds a vertex (with no incident edges) to the graph.
   * The vertex's "name" is the object provided as the parameter "vertex".
   * If this object is already a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(1).
   */
  public void addVertex(Object vertex){
    if(vertices.find(vertex)==null)
      vertices.insert(vertex,new HashTable<Object,VertexPair>(vertexCount()+10));
  }

  /**
   * removeVertex() removes a vertex from the graph.  All edges incident on the
   * deleted vertex are removed as well.  If the parameter "vertex" does not
   * represent a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public void removeVertex(Object vertex){
    Entry<Object,HashTable<Object,VertexPair>> vert = vertices.remove(vertex);
    if(vert!=null){
      DList<Entry<Object,VertexPair>> connected = vert.value().entries();
      for(Entry<Object,VertexPair> v: connected){
        edges.remove(v.value());
        if(vertices.find(v.key())!=null)
          vertices.find(v.key()).value().remove(vertex);
      }
    }
  }

  /**
   * isVertex() returns true if the parameter "vertex" represents a vertex of
   * the graph.
   *
   * Running time:  O(1).
   */
  public boolean isVertex(Object vertex){
    return vertices.find(vertex)!=null;
  }

  /**
   * degree() returns the degree of a vertex.  Self-edges add only one to the
   * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
   * of the graph, zero is returned.
   *
   * Running time:  O(1).
   */
  public int degree(Object vertex){
    if(vertices.find(vertex)!=null)
      return vertices.find(vertex).value().size();
    return 0;
  }

  /**
   * getNeighbors() returns a new Neighbors object referencing two arrays.  The
   * Neighbors.neighborList array contains each object that is connected to the
   * input object by an edge.  The Neighbors.weightList array contains the
   * weights of the corresponding edges.  The length of both arrays is equal to
   * the number of edges incident on the input vertex.  If the vertex has
   * degree zero, or if the parameter "vertex" does not represent a vertex of
   * the graph, null is returned (instead of a Neighbors object).
   *
   * The returned Neighbors object, and the two arrays, are both newly created.
   * No previously existing Neighbors object or array is changed.
   *
   * (NOTE:  In the neighborList array, do not return any internal data
   * structure you use to represent vertices!  Return only the same objects
   * that were provided by the calling application in calls to addVertex().)
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public Neighbors getNeighbors(Object vertex){
    if(vertices.find(vertex)==null || vertices.find(vertex).value().size()==0){
      return null;
    }
    HashTable<Object,VertexPair> n = vertices.find(vertex).value();
    DList<Entry<Object,VertexPair>> n_entries = n.entries();
    Neighbors all = new Neighbors(n.size());
    int count = 0;
    for(Entry<Object,VertexPair> k : n_entries){
      all.weightList[count] = edges.find(k.value()).value();
      all.neighborList[count] = k.key();
      count++;
    }
    return all;
  }

  /**
   * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
   * u and v does not represent a vertex of the graph, the graph is unchanged.
   * The edge is assigned a weight of "weight".  If the graph already contains
   * edge (u, v), the weight is updated to reflect the new value.  Self-edges
   * (where u == v) are allowed.
   *
   * Running time:  O(1).
   */
  public void addEdge(Object u, Object v, int weight){
    if(vertices.find(u)!=null && vertices.find(v)!=null){
      VertexPair temp = new VertexPair(u,v);
      if(edges.find(temp)!=null){
        edges.find(temp).setValue(weight);
      }
      else{
        edges.insert(temp,weight);
        vertices.find(u).value().insert(v,temp);
        if(!u.equals(v))
          vertices.find(v).value().insert(u,temp);
      }
    }
  }

  /**
   * removeEdge() removes an edge (u, v) from the graph.  If either of the
   * parameters u and v does not represent a vertex of the graph, the graph
   * is unchanged.  If (u, v) is not an edge of the graph, the graph is
   * unchanged.
   *
   * Running time:  O(1).
   */
  public void removeEdge(Object u, Object v){
    VertexPair temp = new VertexPair(u,v);
    if(vertices.find(u)!=null && vertices.find(v)!=null&&edges.find(temp)!=null){
      edges.remove(temp);
      vertices.find(u).value().remove(v);
      if(!u.equals(v))
        vertices.find(v).value().remove(u);
    }
  }

  /**
   * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
   * if (u, v) is not an edge (including the case where either of the
   * parameters u and v does not represent a vertex of the graph).
   *
   * Running time:  O(1).
   */
  public boolean isEdge(Object u, Object v){
    if(vertices.find(u)!=null && vertices.find(v)!=null && edges.find(new VertexPair(u,v))!=null){
      return true;
    }
    return false;
  }
  /**
   * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
   * an edge (including the case where either of the parameters u and v does
   * not represent a vertex of the graph).
   *
   * (NOTE:  A well-behaved application should try to avoid calling this
   * method for an edge that is not in the graph, and should certainly not
   * treat the result as if it actually represents an edge with weight zero.
   * However, some sort of default response is necessary for missing edges,
   * so we return zero.  An exception would be more appropriate, but also more
   * annoying.)
   *
   * Running time:  O(1).
   */
  public int weight(Object u, Object v){
    if(vertices.find(u)!=null && vertices.find(v)!=null){
      Entry<VertexPair,Integer> temp = edges.find(new VertexPair(u,v));
      if (temp!= null)
        return temp.value();
    }
    return 0;
  }

}
