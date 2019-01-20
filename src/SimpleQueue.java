/**
 * [SimpleQueue.java]
 * Queue object that stores nodes containing generic items
 * Authors: Brian Li, James Liang, Michael Oren, Brian Zhang
 * Date: January 21, 2019
 */

public class SimpleQueue<T> {

    Node<T> head; //head node (node to be dequeued)
    Node<T> tail; // tail node (node last enqueued)

    /**
     * SimpleQueue
     * Constructor that makes an empty queue
     */
    public SimpleQueue() {
        head = null;
        tail = null;
    } // End of constructor

    /**
     * offer
     * Enqueues an item (into new tail node)
     * @param item, the generic item to be enqueued
     */
    public void offer(T item) {
        if (tail == null) {
            head = new Node<>(item, null);
            tail = head;
        } else {
            tail.setNext(new Node<>(item, null));
            tail = tail.getNext();
        }
    } // End of offer

    /**
     * poll
     * Dequeues an item (item in head node)
     * @return generic item in head node
     */
    public T poll() {
        if (head == null) {
            return null;
        } else {
            T temp = head.getItem();
            head = head.getNext();
            return temp;
        }
    } // End of poll

    /**
     * peek
     * Returns the item held in the head node
     * @return generic item held in head node
     */
    public T peek() {
        if (head == null) {
            return null;
        } else {
            return head.getItem();
        }
    } // End of peek

    /**
     * clear
     * Clears the queue of all items
     */
    public void clear() {
        head = null;
        tail = null;
    } // End of clear

} // End of class
