package nz.ac.auckland.se281.datastructures;

public class LinkedList<T> {

  private Node<T> head;
  private Node<T> tail;
  private int length;

  public LinkedList() {
    length = 0;
  }

  public void append(T data) {
    Node<T> newNode = new Node<T>(data);
    if (length == 0) {
      head = newNode;
    } else if (length == 1) {
      tail = newNode;
      head.setNext(tail);
      tail.setPrev(head);
    } else {
      tail.setNext(newNode);
      newNode.setPrev(tail);
      tail = newNode;
    }
    length++;
  }

  public void prepend(T data) {
    Node<T> newNode = new Node<T>(data);
    if (length == 0) {
      head = newNode;
    } else if (length == 1) {
      newNode.setNext(head);
      head.setPrev(newNode);
      tail = head;
      head = newNode;
    } else {
      newNode.setNext(head);
      head.setPrev(newNode);
      head = newNode;
    }
    length++;
  }

  public T fetch(int pos) {
    Node<T> temp = head;
    for (int i = 0; i < pos; i++) {
      temp = temp.getNext();
    }
    T result = temp.getData();
    return result;
  }

  public void insert(int pos, T data) {
    if (pos == 0) {
      prepend(data);
    } else if (pos == length - 1) {
      append(data);
    } else {
      Node<T> newNode = new Node<T>(data);
      Node<T> temp = head;
      for (int i = 0; i < pos; i++) {
        temp = temp.getNext();
      }
      Node<T> tempPrev = temp.getPrev();
      temp.setPrev(newNode);
      newNode.setNext(temp);
      tempPrev.setNext(newNode);
      newNode.setPrev(tempPrev);
    }
    length++;
  }

  public void remove(int pos) {
    if (size()== 1){
      head = null;
      tail = null;
      length--;
      return;
    } 

    if (pos == 0) {
      Node<T> headNext = head.getNext();
      head = headNext;
      head.setPrev(null);
    } else if (pos == length - 1) {
      Node<T> tailPrev = tail.getPrev();
      tail = tailPrev;
      tail.setNext(null);
    } else {
      Node<T> temp = head;
      for (int i = 0; i < pos; i++) {
        temp = temp.getNext();
      }
      Node<T> tempNext = temp.getNext();
      Node<T> tempPrev = temp.getPrev();
      tempNext.setPrev(tempPrev);
      tempPrev.setNext(tempNext);
    }
    length--;
  }

  public int size() {
    return length;
  }
}
