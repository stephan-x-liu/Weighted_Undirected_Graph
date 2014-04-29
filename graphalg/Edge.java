/**
 * Edge container object used to make tasks such as sorting the linked list easier.
 *
 **/
public class Edge implements Comparable{
	protected Object vertex1;
	protected Object vertex2;
	protected int weight;
	public Edge(Object v1, Objectx v2, int w){
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

	public int compareTo(Edge e){
		if this.weight == e.weight(){
			return 0;
		}
		if this.weight > e.weight(){
			return 1;
		}
		if this.weight < e.weight(){
			return -1;
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
