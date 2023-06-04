package nz.ac.auckland.se281.datastructures;

public class Node<T> {
    // Field
	private T data;
    private Node<T> next;
    private Node<T> prev;

    // Constructor
    public Node(T data){
        this.data = data;
    }

    public T getData(){
        return data;
    }

    public Node<T> getNext(){
        return next;
    }

    public Node<T> getPrev(){
        return prev;
    }

    public void setData(T data){
        this.data = data;
    }

    public void setNext(Node<T> node){
        this.next = node;
    }

    public void setPrev(Node<T> node){
        this.prev = node;
    }


}
