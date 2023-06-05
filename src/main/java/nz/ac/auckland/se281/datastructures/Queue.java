package nz.ac.auckland.se281.datastructures;

public class Queue<T> {
  private LinkedList<T> linkedList;

  public Queue() {
    linkedList = new LinkedList<T>();
  }

  public void enQueue(T data) {
    linkedList.append(data);
  }

  public void deQueue() {
    linkedList.remove(0);
  }

  public T peek() {
    return linkedList.fetch(0);
  }

  public boolean isEmpty() {
    return linkedList.size() == 0;
  }
}
