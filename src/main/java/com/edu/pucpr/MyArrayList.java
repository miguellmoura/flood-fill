package com.edu.pucpr;

import java.util.Arrays;
import java.util.Objects;

public class MyArrayList<E> {
    private Object[] data;
    private int capacity = 10;
    private int freePos = 0;

    public MyArrayList() {
        data = new Object[capacity];
    }

    public MyArrayList(int capacity) { 
        this.capacity = capacity;
        data = new Object[capacity];
    }

    public boolean add(E e) {
        if (isFull()) {
            grow();
        }
        data[freePos] = e;
        freePos++;
        return true;
    }

    public boolean add(int index, E e) {
        checkIndexOutOfBounds(index);
        if (isFull())
            grow();

        if (!indexIsAvailable(index)) {
            Object[] subArray = Arrays.copyOfRange(data, index, freePos);
            data[index] = e;
            System.arraycopy(subArray, 0, data, index + 1, subArray.length);
            freePos++;
            return true;
            // 9, 5, 2, 1, 4, null
            // 10 pos 1
            // subarray = 5, 2, 1, 4
            // 9, 10, 2, 1, 4, null
            // 9, 10, 5, 2, 1, 4, null
        }
        data[freePos] = e;
        freePos++;
        return true;
    }

    public Object remove(int index) {
        checkIndexOutOfBounds(index);

        Object element = data[index];
        Object[] subArray = Arrays.copyOfRange(data, index + 1, freePos);
        System.arraycopy(subArray, 0, data, index, subArray.length);
        freePos--;
        data[freePos] = null;

        // 9, 5, 2, 1, 4, null
        // element = 2
        // subarray = 1, 4
        // 9, 5, 1, 4, null
        return element;
    }

    public boolean remove(E e) {
        if (contains(e)) {
            remove(indexOf(e));
            return true;
        }
        return false;
    }

    public boolean set(int index, E e) {
        checkIndexOutOfBounds(index);
        data[index] = e;
        return true;
    }

    public E get(int index) {
        checkIndexOutOfBounds(index);
        return (E) data[index];
    }

    public int indexOf(E e) {
        for (int i = 0; i < capacity; i++) {
            if (data[i] == e)
                return i;
        }
        return -1;
    }

    public boolean contains(E e) {
        return indexOf(e) >= 0;
    }

    public Object[] toArray() {
        return Arrays.copyOf(data, freePos);
    }

    private void checkIndexOutOfBounds(int index) {
        if (index > capacity || index < 0) {
            throw new IndexOutOfBoundsException("Index:" + index + "Capacity: " + capacity);
        }
    }

    private boolean indexIsAvailable(int index) {
        return data[index] == null;
    }

    private void grow() {
        capacity *= 2;
        data = Arrays.copyOf(data, capacity);
    }

    private boolean isFull() {
        return data[capacity - 1] != null;
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.stream(data)
                .filter(Objects::nonNull)
                .toArray());
    }
}
