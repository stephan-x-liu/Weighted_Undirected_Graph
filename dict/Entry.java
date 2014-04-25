/* Entry.java */
package dict;

public class Entry<T,V> {

  protected T key;
  protected V value;

  public T key() {
    return key;
  }

  public V value() {
    return value;
  }

  public void setValue(V obj){
    value = obj;
  }

  public String toString(){
  	return key.toString()+":"+value.toString();
  }

}
