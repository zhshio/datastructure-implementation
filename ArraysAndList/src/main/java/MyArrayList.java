/**
 * @Auther: 张帅
 * @Date: 2024/1/29 - 01 - 29 - 20:53
 * @Description: PACKAGE_NAME
 * @version: 1.0
 */

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @description:
 * @author: zs
 * @time: 2024/1/29 20:53
 */

public class MyArrayList<E> implements Iterable<E> {
    // 存储数据
    private E[] data;

    // 记录数组长度
    private int size;

    // 默认初始容量
    private static final int INIT_CAP = 1;

    public MyArrayList() {
        this(INIT_CAP);
    }
    public MyArrayList(int initCapacity) {
        data = (E[]) new Object[initCapacity];
        size = 0;
    }

    /***** 增 *****/
    public void addLast(E e) {
        if (data.length == size) {
            // 扩容
            resize(data.length << 1);
        }

        data[size] = e;
        size++;
    }

    public void addFirst(E e) {
        if (data.length == size) {
            // 扩容
            resize(data.length << 1);
        }

        System.arraycopy(data, 0,
                data, 1,
                size);

        data[0] = e;
        size++;
    }

    public void add(int index, E e) {
        checkPositionIndex(index);

        if (data.length == size) {
            // 扩容
            resize(data.length << 1);
        }

        // data[index..] ---> data[index + 1..];
        // 数据迁移
        System.arraycopy(data, index,
                data, index + 1,
                size - index);
        // 赋值
        data[index] = e;
        size ++;
    }

    /***** 删 *****/

    public E removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        if (size < data.length >> 2) {
            resize(data.length >> 1);
        }

        // 删除位置置为空, 垃圾回收, 防止内存泄漏
        E deleteVal = data[size - 1];
        data[size - 1] = null;
        size--;

        return deleteVal;
    }

    public E removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        if (size < data.length >> 2) {
            resize(data.length >> 1);
        }

        // 删除位置置为空, 垃圾回收, 防止内存泄漏
        E deleteVal = data[0];

        System.arraycopy(data, 1,
                data, 0,
                size - 1);

        data[0] = null;
        size --;
        return deleteVal;
    }

    public E remove(int index) {
        checkElementIndex(index);

        if (size < data.length >> 2) {
            resize(data.length >> 1);
        }

        E deleteVal = data[index];
        // data[index+1..] -> data[index..]
        // 数据迁移
        System.arraycopy(data, index + 1,
                data, index,
                size - index - 1);
        // 删除
        data[size - 1] = null;
        size --;
        return  deleteVal;
    }

    /***** 查 *****/
    public E get(int index) {
        checkElementIndex(index);
        return data[index];
    }

    /***** 改 *****/
    public E set(int index, E e) {
        checkElementIndex(index);

        E oldVal = data[index];
        data[index] = e;

        return oldVal;
    }

    /***** 工具函数 *****/

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /***** 私有函数 *****/

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    // 检查 index 索引位置是否可以存在元素
    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    // 检查 index 索引位置是否可以添加元素
    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    // 将数组大小扩容或缩容成 newCap
    private void resize(int newCap) {
        E[] temp = (E[]) new Object[newCap];
        System.arraycopy(data, 0, temp, 0, size);
        data = temp;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int p = 0;

            @Override
            public boolean hasNext() {
                return p != size;
            }

            @Override
            public E next() {
                return data[p++];
            }
        };
    }

    private void display() {
        System.out.println("size = " + size + " cap = " + data.length);
        System.out.println(Arrays.toString(data));
    }


    public static void main(String[] args) {
        // 初始容量设置为 3
        MyArrayList<Integer> arr = new MyArrayList<>(3);

        // 添加 5 个元素
        for (int i = 1; i <= 5; i++) {
            arr.addLast(i);
        }

        arr.remove(3);
        arr.add(1, 9);
        arr.addFirst(100);
        int val = arr.removeLast();

        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.get(i));
        }
    }
}
