package nz.ac.auckland.se281.datastructures;

public class Stack<T> {
  private LinkedList<T> linkedList;

  public Stack() {
    linkedList = new LinkedList<T>();
  }

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

  public T pop() {
    T result = linkedList.fetch(linkedList.size() - 1);
    linkedList.remove(linkedList.size() - 1);
    return result;
  }

  public T peek() {
    return linkedList.fetch(linkedList.size() - 1);
  }
}
