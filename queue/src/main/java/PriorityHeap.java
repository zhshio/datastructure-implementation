import java.util.Arrays;
import java.util.Random;

/**
 * @description:
 * @author: zs
 * @time: 2024/3/25 14:29
 */

public class PriorityHeap<E extends Comparable<E>>{
    private E[] data;

    private int size;

    private final static double DILATANCY_FACTOR = 0.75;
    private final static double REDUCTION_FACTOR = 0.125;
    private final static int MAX_GAP = 10000;

    public PriorityHeap(int cap) {
        this.data = (E[]) new Comparable[cap];
        this.size = 0;
    }

    public void insert(E e) {
        if (needExpand()) {
            expandSize();
        }
        data[size] = e;
        siftUp(size++);
    }

    public E peekTop() {
        if (size == 0) {
            throw new IllegalStateException();
        }
        return data[0];
    }

    public E popTop() {
        if (size == 0) {
            throw new IllegalStateException();
        }
        if (needReduce()) {
            reduceSize();
        }
        E min = data[0];
        data[0] = data[--size];
        siftDown(0);
        return min;
    }

    public void siftUp(int index) {
        while (index > 0 && data[getParent(index)].compareTo(data[index]) > 0) {
            swap(index, getParent(index));
            index = getParent(index);
        }
    }

    public void siftDown(int index) {
        while (getLeft(index) < size) {
            int left = getLeft(index);
            int right = getRight(index);
            int compare = left;
            if (left + 1 < size && data[left].compareTo(data[right]) > 0) {
                compare = right;
            }
            if (data[index].compareTo(data[compare]) <= 0) {
                break;
            } else {
                swap(index, compare);
                index = compare;
            }
        }
    }

    private boolean needExpand() {
        return size >= data.length * DILATANCY_FACTOR;
    }

    private boolean needReduce() {
        return data.length - size > MAX_GAP && data.length * REDUCTION_FACTOR > size;
    }

    private void reduceSize() {
        int newCapacity = data.length >> 1;
        E[] newData = (E[]) new Comparable[newCapacity];
        System.arraycopy(data, 0, newData, 0, size);
        data = newData;
    }
    private void expandSize() {
        int newCapacity = data.length << 1;
        E[] newData = (E[]) new Comparable[newCapacity];
        System.arraycopy(data, 0, newData, 0, size);
        data = newData;
    }
    int getLeft(int index) {
        return index * 2 + 1;
    }

    int getRight(int index) {
        return index * 2 + 2;
    }

    int getParent(int index) {
        return (index - 1) / 2;
    }

    void swap(int i, int j) {
        E temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    public static void main(String[] args) {
        PriorityHeap<Integer> heap = new PriorityHeap<>(8);
        int[] d = new int[8];
        for (int i = 0; i < 8; i++) {
            d[i] = new Random().nextInt(8);
        }
        System.out.println(Arrays.toString(d));
        for (int i = 0; i < 8; i++) {
            heap.insert(d[i]);
        }
        for (int i = 0; i < 8; i++) {
            System.out.println(heap.popTop());
        }
    }
}
