package nz.ac.auckland.se281.datastructures;

/**
 * A class which uses self-made linked list.
 *
 * @author Sophie Park
 */
public class Queue<T> {
  private LinkedList<T> linkedList;

  public Queue() {
    linkedList = new LinkedList<T>();
  }

  /**
   * A method adds a node at the end of the linked list.
   *
   * @param data a data that the new node has
   */
  public void enQueue(T data) {
    linkedList.append(data);
  }

  /** A method removes a ndoe at front in the linked list. */
  public void deQueue() {
    linkedList.remove(0);
  }

  /**
   * A method finds a value at the front of the linked list.
   *
   * @return the value at the front
   */
  public T peek() {
    return linkedList.fetch(0);
  }

  /**
   * A method determines if the linked list is empty or not.
   *
   * @return boolean of if the linked list is empty or not
   */
  public boolean isEmpty() {
    return linkedList.size() == 0;
  }
}
