/**
 * SimpleLinkedList<T>
 * Simple Linked List object that holds nodes with generic objects
 * @author Brian Li, James Liang, Michael Oren, Brian Zhang
 * January 22, 2019
 */

public class SimpleLinkedList<T> {
  
  private Node<T> head = null; //Head node in list
  
  /**
   * SimpleLinkedList
   * Constructor that makes an empty list
   */
  SimpleLinkedList() {
  } //End of constructor
  
  /**
   * add
   * Adds an item to the list
   * @param item, a generic object T representing item added
   */
  public void add(T item) {
    
    Node<T> tempNode = head;
    
    if (head == null) {
      head = new Node<T>(item, null); //Set in head if list is empty
    } else {
      while (tempNode.getNext() != null) {
        tempNode = tempNode.getNext();
      }
      tempNode.addNext(item, null); //Otherwise set to new node at the end
    }
    
  } //End of add
  
  /**
   * add
   * Adds an item at the specific index in the list
   * @param item, a generic object T representing item added
   * @param index, an int representing index wanted in list
   */
  public void add(T item, int index) {
    
    int counter = 0;
    Node<T> tempNode = head;
    
    if (head == null) {
      head = new Node<T>(item, null);
    } else if (index == 0) {
      head = new Node<T>(item, head);
    } else {
      while ((counter < (index-1)) && (tempNode.getNext() != null)) {
        tempNode = tempNode.getNext();
        counter++;
      }
      if (tempNode.getNext() == null) {
        tempNode.setNext(new Node<T>(item, null));
      } else {
        Node<T> tempNode2;
        tempNode2 = tempNode.getNext();
        tempNode.setNext(new Node<T>(item, tempNode2));
      }
    }
    
  } //End of add
  
  /**
   * get
   * Returns item held at specified index
   * @param index, int representing index of item wanted
   * @return T item, Generic item representing item returned
   */
  public T get(int index) {
    
    int counter = 0;
    Node<T> tempNode = head;
    
    if (head == null) {
      return null;
    } else {
      while ((counter < index) && (tempNode.getNext() != null)) {
        tempNode = tempNode.getNext();
        counter++;
      }
    }
    
    if (counter == index) {
      return tempNode.getItem();
    } else {
      return null;
    }
    
  } //End of get
  
  /**
   * indexOf
   * Returns index of a certain item in list
   * @param item, generic item T representing item searched for
   * @return int counter, index of the item in list
   */
  public int indexOf(T item) {
    
    int counter = 0;
    Node<T> tempNode = head;
    
    if (head == null) {
      return -1;
    } else {
      while (tempNode.getNext() != null) {
        if (tempNode.getItem() == item) {
          return counter;
        }
        tempNode = tempNode.getNext();
        counter++;
      }
    }
    
    if (tempNode.getItem() == item) {
      return counter;
    }
    
    return -1;
  } //End of indexOf
  
  /**
   * remove
   * Removes a specified item from the list
   * @param item, Generic item T to be removed
   * @return boolean, true if removed, false if item not found in list
   */
  public boolean remove(T item) {
    
    Node<T> tempNode = head;
    
    if (head.getItem() == item) {
      head = head.getNext();
      return true;
    }
    
    while (tempNode.getNext() != null) {
      if (tempNode.getNext().getItem() == item) {
        if (tempNode.getNext().getNext() != null) {
          Node<T> tempNode2;
          tempNode2 = tempNode.getNext();
          tempNode.setNext(tempNode.getNext().getNext());
          tempNode2.setNext(null);
        } else {
          tempNode.setNext(null);
        }
        return true;
      } else {
        tempNode = tempNode.getNext();
      }
    }
    
    return false;
  } //End of remove
  
  /**
   * remove
   * Removes an item at a specified index
   * @param index, int representing index of item to be removed
   * @return T item, Generic object representing item removed from list
   */
  public T remove(int index) {
    
    int counter = 0;
    Node<T> tempNode = head;
    
    if (index == 0) {
      if (head != null) {
        tempNode = head;
        head = head.getNext();
        return tempNode.getItem();
      }
    } else {
      while ((counter < (index-1)) && (tempNode.getNext() != null)) {
        tempNode = tempNode.getNext();
        counter++;
      }
      if ((counter == (index-1)) && (tempNode.getNext() != null)) {
        Node<T> tempNode2;
        if (tempNode.getNext().getNext() == null) {
          tempNode2 = tempNode.getNext();
          tempNode.setNext(null);
          return tempNode2.getItem();
        } else {
          tempNode2 = tempNode.getNext();
          tempNode.setNext(tempNode.getNext().getNext());
          return tempNode2.getItem();
        }
      }
    }
    
    return null;
  } //End of remove
  
  /**
   * size
   * Returns number of items in list
   * @return int counter, representing size of list
   */
  public int size() {
    
    int counter = 0;
    Node<T> tempNode = head;
    
    if (head != null) {
      counter++;
      while (tempNode.getNext() != null) {
        tempNode = tempNode.getNext();
        counter++;
      }
    }
    
    return counter;
    
  } //End of size
  
  /**
   * clear
   * Clears the list
   */
  public void clear() {
    head = null;
  } //End of clear
  
} //End of class
