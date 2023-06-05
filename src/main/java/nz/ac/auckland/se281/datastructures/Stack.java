package nz.ac.auckland.se281.datastructures;

/**
 * A stack class which contains a self-made linked list. 
 * 
 *@author Sophie Park
 */
public class Stack<T> {
  private LinkedList<T> linkedList;

  public Stack() {
    linkedList = new LinkedList<T>();
  }

  /**
   * A method determines if the stack is empty or not.
   * 
   *@return boolean of if the stack is empty or not
   */
  public boolean isEmpty() {
    if (linkedList.size() == 0) {
      return true;
    } else {
      return false;
    }
  }

  public void push(T data) {
    linkedList.append(data);
  }

  /**
   * A method gives a value at the specific position and removes it.
   * 
   *@return the value at the specific position
   */
  public T pop() {
    T result = linkedList.fetch(linkedList.size() - 1);
    linkedList.remove(linkedList.size() - 1);
    return result;
  }

  /**
   * A method gives the value at the end of the linked-list.
   *@return a value that a node at the end contains
   */
  public T peek() {
    return linkedList.fetch(linkedList.size() - 1);
  }
}
