/* HashTableChained.java */
package dict;
import list.*;

public class HashTable<T,V> {


  protected int prime;
  protected DList<DListNode<Entry<T,V>>>[] buckets;
  protected int size;
  protected int large_prime;
  protected DList<Entry<T,V>> entries;



  public HashTable(int sizeEstimate) {
    prime = next_prime(sizeEstimate);
    large_prime = 32452843;
    size = 0;
    buckets = new DList[prime];
    entries = new DList<Entry<T,V>>();
  }


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

  /** 
   *  Mod function for HashTable that doesn't return negative numbers.
   *
   *  @param num is an integer
   *  @param m is the mod space
   *  @return an integer in mod m
   **/

  public static int mod(int num, int m){
    int temp;
    if(m==0){
      temp = num;
    }
    else
      temp = num%(m);
    if(temp < 0){
      return m + temp;
    }
    return temp;
  }

  /** 
   *  Returns the next prime number
   *
   *  @param num is an integer
   *  @return The next prime integer after num
   **/

  public static int next_prime(int num){
    while(is_prime(num)==false){
      num++;
    }
    return num;
  }

  /** 
   *  Returns the next prime number
   *
   *  @param num is an integer
   *  @return boolean for if num is prime
   **/

  public static boolean is_prime(int num){
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
    return size;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    return size == 0;
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  If the key exists already, the value of the key is updated.
   *  The entry is also inserted into a DList of all the entries, and it is 
   *  this node that is stored as the value in the HashTable.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/
  

  public Entry<T,V> insert(T key, V value) {
    Entry<T,V> current = new Entry<T,V>();
    current.key = key;
    current.value = value;
    int hash = compFunction(key.hashCode());

    if(find(key)==null){
      entries.insertFront(current);
      DListNode<Entry<T,V>> node = entries.front();
      if(buckets[hash]==null){
        buckets[hash] = new DList<DListNode<Entry<T,V>>>();
      }
      buckets[hash].insertFront(node);
      size++;
      if(this.loadFactor()>0.9){
        this.resize();
      }
    }
    else{
      try{
        DListNode<DListNode<Entry<T,V>>> curr = buckets[hash].front();
        while(curr!=null && curr.item().item().key().equals(key)==false){
          curr = curr.next();
        }
        curr.item().item().setValue(value);
      }
      catch(InvalidNodeException e){
        return null;
      }
    }
    return current;
  }


  /** 
   *  Returns a DList of all the entries in this HashTable in O(1).
   *  @return DList containing Entry objects
   **/

  public DList<Entry<T,V>> entries(){
    return entries;
  }

  /** 
   *  Resizes HashTable to double the size of the previous in O(n) where n
   *  is the number of entries.
   **/

  public void resize(){
    DList<Entry<T,V>> all_items = this.entries();
    prime = next_prime(size*2);
    this.makeEmpty();
    for(Entry<T,V> k: all_items){
      this.insert(k.key(),k.value());
    }
    
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
      DListNode<DListNode<Entry<T,V>>> curr = buckets[hash].front();
      while(curr!=null && curr.item().item().key().equals(key)==false){
        curr = curr.next();
      }
      return curr.item().item();
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
      DListNode<DListNode<Entry<T,V>>> curr = buckets[hash].front();
      while(curr!=null && curr.item().item().key().equals(key)==false){
        curr = curr.next();
      }
      Entry<T,V> temp = curr.item().item();
      curr.item().remove();
      curr.remove();
      size--;
      return temp;
    }
    catch(NullPointerException e){
      return null;
    }
    catch(InvalidNodeException m){
      return null;
    }

  }

  /**
   *  Remove all entries from the dictionary by creating a new buckets array and entries DList.
   */
  public void makeEmpty() {
    buckets = new DList[prime];
    entries = new DList<Entry<T,V>>();
    size = 0;
  }

  public String toString(){
    String ret = "{  ";

    for(Entry<T,V> curr : entries){
        ret += curr.key().toString() + ":" + curr.value().toString() + "  ";
      }

    ret += "  }";
    return ret;
  }



  public static void main(String[] args){
    HashTable<String,Integer> test = new HashTable<String,Integer>(7);
    System.out.println(test.size());
    System.out.println(test.isEmpty());
    test.insert("test",0);
    test.insert("test1",1);
    test.insert("test2",2);
    test.insert("test3",3);
    test.insert("test4",4);
    test.insert("test5",5);
    test.insert("test6",6);
    test.insert("test7",7);
    test.insert("test8",8);
    test.insert("test9",9);
    test.insert("test10",10);
    test.insert("test5",15);
    System.out.println(test);
    System.out.println(test.size());
    System.out.println(test.entries());
    System.out.println(test.find("test"));
    System.out.println(test.find("test1"));
    System.out.println(test.find("test2"));
    System.out.println(test.find("test3"));
    System.out.println(test.find("test4"));
    System.out.println(test.find("test5"));
    System.out.println(test.find("test6"));
    System.out.println(test.find("test7"));
    System.out.println(test.find("test8"));
    System.out.println(test.find("test9"));
    System.out.println(test.find("test10"));
    System.out.println(test.remove("test"));
    System.out.println(test.remove("test1"));
    System.out.println(test.remove("test2"));
    System.out.println(test.remove("test3"));
    System.out.println(test.remove("test4"));
    System.out.println(test.remove("test5"));
    System.out.println(test.remove("test6"));
    System.out.println(test.remove("test7"));
    System.out.println(test.remove("test8"));
    System.out.println(test.remove("test9"));
    System.out.println(test.remove("test10"));
    System.out.println(test.entries());
    System.out.println(test);

  }
}
