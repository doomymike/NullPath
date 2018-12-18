public class SimpleLinkedList<T> {
  
  private Node<T> head = null;
  
  SimpleLinkedList() {
  }
  
  public void add(T item) {
    
    Node<T> tempNode = head;
    
    if (head == null) {
      head = new Node<T>(item, null);
    } else {
      while (tempNode.getNext() != null) {
        tempNode = tempNode.getNext();
      }
      tempNode.addNext(item, null);
    }
    
  }
  
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
    
  }
  
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
    
  }
  
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
  }
  
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
  }
  
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
  }
  
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
    
  }
  
  public void clear() {
    head = null;
  }
  
}