public class SimpleQueue<T> {

    Node<T> head;
    Node<T> tail;

    public SimpleQueue() {
    }

    public void offer(T item) {
        if (head == null) {
            head = new Node<>(item, null);
            tail = head;
        } else {
            tail.setNext(new Node<>(item, null));
            tail = tail.getNext();
        }
    }

    public T poll() {
        if (head == null) {
            return null;
        } else {
            T temp = head.getItem();
            head = head.getNext();
            return temp;
        }
    }

    public T peek() {
        if (head == null) {
            return null;
        } else {
            return head.getItem();
        }
    }

    public void clear() {
        head = null;
        tail = null;
    }

}
