package by.mnp;

import by.mnp.model.CustomPriorityQueue;
import by.mnp.model.CustomPriorityQueueImpl;

import java.util.Comparator;

public class Main {
    public static void main(String[] args) {

        CustomPriorityQueue<Integer> queue = new CustomPriorityQueueImpl<>(Comparator.reverseOrder());

        queue.add(10);
        queue.add(20);

        System.out.println(queue.peek());
    }
}