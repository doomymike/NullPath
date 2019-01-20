/**
 * [Node.java]
 * Node object that stores a generic item, to be used in a data structure
 * Authors: Brian Li, James Liang, Michael Oren, Brian Zhang
 * January 21, 2019
 */

public class Node<T> {
  
  private T item; //Item held
  private Node<T> next; //Next node in data structure

  /**
   * Node
   * Constructor that makes a node with a given item held inside and next node in data structure
   * @param item, the generic object held in the node
   * @param next, the next node in the data structure
   */
  Node(T item, Node<T> next) {
    this.item = item;
    this.next = next;
  } //End of constructor

  /**
   * addNext
   * Adds the next node in the data structure
   * @param item, the generic object in the next node to be added
   * @param next, the node that is to be the node after the node to be added
   */
  public void addNext(T item, Node<T> next) {
    this.next = new Node<T>(item, next);
  } //End of addNext

  /**
   * addItem
   * Adds an item to the node
   * @param item, the generic object to be added
   */
  public void addItem(T item) {
    this.item = item;
  } //End of addItem

  /**
   * getNext
   * Returns the next node in the data structure
   * @return Node<T>, the next node
   */
  public Node<T> getNext() {
    return next;
  } //End of getNext

  /**
   * setNext
   * Sets the next node in the data structure
   * @param next, the node to be set as the next node
   */
  public void setNext(Node<T> next) {
    this.next = next;
  } //End of setNext

  /**
   * getItem
   * Returns the item held in the node
   * @return T, generic object in node
   */
  public T getItem() {
    return item;
  } //End of getItem
  
} //End of class