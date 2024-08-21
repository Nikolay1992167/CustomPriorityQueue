package by.mnp.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

import static by.mnp.util.Constants.CAPACITY_ERROR_MESSAGE;
import static by.mnp.util.Constants.IMPLEMENTATION_ERROR;
import static by.mnp.util.Constants.NOTIFICATION_NOT_NULL;

@SuppressWarnings("unchecked")
public class CustomPriorityQueueImpl<E> implements CustomPriorityQueue<E> {

    private E[] queue;
    private int size;
    private static final int capacity_default = 8;
    private Comparator<? super E> comparator;

    public CustomPriorityQueueImpl() {
        queue = (E[]) new Object[capacity_default];
        size = 0;
    }

    public CustomPriorityQueueImpl(Comparator<? super E> comparator) {
        queue = (E[]) new Object[capacity_default];
        size = 0;
        this.comparator = comparator;
    }

    public CustomPriorityQueueImpl(int capacity) {
        Optional.of(capacity)
                .filter(integer -> integer > 0)
                .ifPresentOrElse(integer -> {
                    queue = (E[]) new Object[integer];
                    size = 0;
                }, () -> {
                    throw new IllegalArgumentException(CAPACITY_ERROR_MESSAGE);
                });
    }

    public CustomPriorityQueueImpl(int capacity, Comparator<? super E> comparator) {
        Optional.of(capacity)
                .filter(integer -> integer > 0)
                .ifPresentOrElse(integer -> {
                    queue = (E[]) new Object[integer];
                    size = 0;
                    this.comparator = comparator;
                }, () -> {
                    throw new IllegalArgumentException(CAPACITY_ERROR_MESSAGE);
                });
    }

    @Override
    public boolean add(E value) {
        Optional.ofNullable(value)
                .ifPresentOrElse(e -> {
                    if (size == queue.length) {
                        queue = Arrays.copyOf(queue, queue.length * 2);
                    }
                    queue[size] = e;
                    size++;
                    siftUp(size - 1);
                }, () -> {
                    throw new NullPointerException(NOTIFICATION_NOT_NULL);
                });
        return true;
    }

    @Override
    public E peek() {
        return queue[0];
    }

    @Override
    public E poll() {
        E element = queue[0];
        if (element != null) {
            queue[0] = queue[size - 1];
            queue[size - 1] = null;
            size--;
            siftDown();
        }
        return element;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void siftUp(int i) {
        while (i > 0 && compare(i, parent(i)) < 0) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    private void siftDown() {
        int i = 0;
        while (goLeft(i) < size) {
            int min = goLeft(i);
            if (goRight(i) < size && compare(goRight(i), goLeft(i)) < 0) {
                min = goRight(i);
            }
            if (compare(i, min) <= 0) {
                break;
            }
            swap(i, min);
            i = min;
        }
    }

    private int goLeft(int i) {
        return i * 2 + 1;
    }

    private int goRight(int i) {
        return i * 2 + 2;
    }

    private int compare(int i, int j) {
        return Optional.ofNullable(comparator)
                .orElseGet(() -> (e1, e2) -> {
                    if (!(e1 instanceof Comparable)) {
                        throw new ClassCastException(IMPLEMENTATION_ERROR + e1);
                    }
                    return ((Comparable<? super E>) e1).compareTo((E) e2);
                })
                .compare(queue[i], queue[j]);
    }

    private void swap(int i, int j) {
        E temp = queue[i];
        queue[i] = queue[j];
        queue[j] = temp;
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    @Override
    public String toString() {
        return Arrays.stream(queue)
                .filter(Objects::nonNull)
                .toList()
                .toString();
    }
}
