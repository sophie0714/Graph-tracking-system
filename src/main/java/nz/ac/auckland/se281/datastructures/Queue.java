package nz.ac.auckland.se281.datastructures;

public class Queue<T> {
    private LinkedList<T> linkedList;
    private int length;

    public Queue(){
        linkedList= new LinkedList<T>();
        length = 0;
    }

    public void enQueue(T data){
        linkedList.append(data);
    }

    public void deQueue(){
        linkedList.remove(0);
    }

    public T Peek(){
        return linkedList.fetch(0);
    }

    public boolean isEmpty(){
        return length == 0;
    }
    
}
