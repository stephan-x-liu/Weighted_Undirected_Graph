/* HashTableChained.java */
package dict;
import list.*;

public class HashTable<T,V> {

  /**
   *  Place any data fields here.
   **/
  protected int prime;
  protected DList<Entry>[] buckets;
  protected int size;
  protected int large_prime;



  public HashTable(int sizeEstimate) {
    prime = next_prime(sizeEstimate);
    large_prime = 32452843;
    size = 0;
    buckets = new DList[prime];
  }

  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public int prime(){
    return prime;
  }

  public double loadFactor(){
    return ((double) size)/((double) prime);
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  protected int compFunction(int code) {
    int a = 123456;
    int b = 234567;
    return mod(mod(a*code+b,large_prime),prime);

    
  }

  static int mod(int num, int m){
    int temp = num%m;
    if(temp < 0){
      return m + temp;
    }
    return temp;
  }

  static int next_prime(int num){
    while(is_prime(num)==false){
      num++;
    }
    return num;
  }

  static boolean is_prime(int num){
    for(int i = 2; i*i <= num; i+=2){
      if(num%i==0){
        return false;
      }
    }
    return true;
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
    // Replace the following line with your solution.
    return size;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    // Replace the following line with your solution.
    return size == 0;
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/
  public DList[] entries(){
    return buckets;
  }

  public Entry insert(T key, V value) {
    // Replace the following line with your solution.
    Entry<T,V> current = new Entry<T,V>();
    current.key = key;
    current.value = value;
    int hash = compFunction(key.hashCode());
    if(buckets[hash]==null){
      buckets[hash] = new DList<Entry>();
    }
    buckets[hash].insertFront(current);
    size++;
    if(this.loadFactor()>0.9){
      this.resize();
    }
    return current;
  }

  public void resize(){
    HashTable<T,V> larger = new HashTable<T,V>(next_prime(size*2));
    for(DList<Entry> k: buckets){
      for(Entry<T,V> j: k){
        larger.insert(j.key(),j.value);
      }
    }
    buckets = larger.entries();
    prime = buckets.length;

  }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public Entry<T,V> find(T key) {
    try{
      int hash = compFunction(key.hashCode());
      DListNode<Entry> curr = buckets[hash].front();
      while(curr!=null && curr.item().key().equals(key)==false){
        curr = curr.next();
      }
      return curr.item();
    }
    catch(NullPointerException e){
      return null;
    }
    catch(InvalidNodeException m){
      return null;
    }
  }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */

  public Entry<T,V> remove(T key) {
    try{
      int hash = compFunction(key.hashCode());
      DListNode<Entry> curr = buckets[hash].front();
      while(curr!=null && curr.item().key().equals(key)==false){
        curr = curr.next();
      }
      curr.remove();
      size--;
      return curr.item();
    }
    catch(NullPointerException e){
      return null;
    }
    catch(InvalidNodeException m){
      return null;
    }

  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
    buckets = new DList[prime];
    size = 0;
  }

  public String toString(){
    String ret = "{  ";
    try{
      for(int i = 0; i < buckets.length; i++){
        if(buckets[i]!=null){
          DListNode<Entry> curr = buckets[i].front();
          while(curr!=null){
            ret += curr.item().key().toString() + ":" + curr.item().value().toString() + "  ";
            curr = curr.next();
          }
        }
      }
      ret += "  }";
      return ret;
    }
    catch(InvalidNodeException m){
      return null;
    }
  }
}
