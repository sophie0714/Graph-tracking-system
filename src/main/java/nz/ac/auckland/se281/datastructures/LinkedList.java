package nz.ac.auckland.se281.datastructures;

public class LinkedList<T> {

  private Node<T> head;
  private Node<T> tail;
  private int length;

  public LinkedList() {
    length = 0;
  }

  // Add a node at the end
  public void append(T data) {
    Node<T> newNode = new Node<T>(data);
    // if the newnode is the first node, make it head
    if (length == 0) {
      head = newNode;
      // if the newndoe is the second node, make it tail
    } else if (length == 1) {
      tail = newNode;
      head.setNext(tail);
      tail.setPrev(head);
      // In other cases, add at the end
    } else {
      tail.setNext(newNode);
      newNode.setPrev(tail);
      tail = newNode;
    }
    length++;
  }

  // Add a node at front
  public void prepend(T data) {
    Node<T> newNode = new Node<T>(data);
    // if the node is the first node, make it head
    if (length == 0) {
      head = newNode;
      // if the node is the second node, make it tail and link with the head
    } else if (length == 1) {
      newNode.setNext(head);
      head.setPrev(newNode);
      tail = head;
      head = newNode;
      // In other cases, add at front
    } else {
      newNode.setNext(head);
      head.setPrev(newNode);
      head = newNode;
    }
    length++;
  }

  // Find a value at the specific position
  public T fetch(int pos) {
    Node<T> temp = head;
    // Iteratively find the node at the specific position
    for (int i = 0; i < pos; i++) {
      temp = temp.getNext();
    }
    T result = temp.getData();
    return result;
  }

  // Put a value in the specific position
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

  // Remove a value at a specific position
  public void remove(int pos) {
    if (size() == 1) {
      head = null;
      tail = null;
      length--;
      return;
    }

    // When remove the first node, make the second node as head
    if (pos == 0) {
      Node<T> headNext = head.getNext();
      head = headNext;
      head.setPrev(null);
      // When remove the last node, make the latest ndoe as tail
    } else if (pos == length - 1) {
      Node<T> tailPrev = tail.getPrev();
      tail = tailPrev;
      tail.setNext(null);
      // In the middle, connect the nodes before and after the removed node linked
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

  // Get the size of value
  public int size() {
    return length;
  }
}
