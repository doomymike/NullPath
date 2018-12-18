public class Node<T> {
  
  private T item;
  private Node<T> next;
  
  Node(T item, Node<T> next) {
    this.item = item;
    this.next = next;
  }
  
  public void addNext(T item, Node<T> next) {
    this.next = new Node<T>(item, next);
  }
  
  public void addItem(T item) {
    this.item = item;
  }
  
  public Node<T> getNext() {
    return next;
  }
  
  public void setNext(Node<T> next) {
    this.next = next;
  }
  
  public T getItem() {
    return item;
  }
  
}