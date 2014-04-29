package graphalg;
/**
 * Edge container object used to make tasks such as sorting the linked list easier.
 * 
 * Note that this only works with undirected lists, since it assumes 2 edges with 
 * opposite start points are equal.
 **/
public class Edge implements Comparable{
	protected Object vertex1;
	protected Object vertex2;
	protected int weight;
	public Edge(Object v1, Object v2, int w){
		vertex1 = v1;
		vertex2 = v2;
		weight = w;
	}
	public Object[] vertices(){
		Object[] temp = {vertex1,vertex2};
		return temp;
	}

	public int weight(){
		return weight;
	}

	public int compareTo(Object o){
    if(o instanceof Edge){
      Edge e = (Edge) o;
      if(this.weight == e.weight()){
        return 0;
      }
      if(this.weight > e.weight()){
        return 1;
      }
      if(this.weight < e.weight()){
        return -1;
      }
    }else{
      return 0;
    }
    return 0;
	}

  public boolean equals(Object o){
    if(o instanceof Edge){
      Edge e = (Edge) o;
      return (((vertex1 == e.vertex1 && vertex2 == e.vertex2) || 
            (vertex1 == e.vertex2 && vertex2 == e.vertex1)) &&
            weight == e.weight);
    }else{
      return false;
    }
  }

	public int hashCode() {
    if (vertex1.equals(vertex2)) {
      return vertex1.hashCode() + 1;
    } else {
      return vertex1.hashCode() + vertex2.hashCode();
    }
  }
}
