package by.mnp.model;

public interface CustomPriorityQueue<E> {

    boolean add(E e);

    E peek();

    E poll();

    int size();

    boolean isEmpty();
}
