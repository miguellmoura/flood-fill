package com.edu.pucpr;

public class Queue {

    private Node first;
    private Node top;

    void enqueue(int x, int y) {
        Node node = new Node(x, y, null);
        if (top == null) {
            first = node;
            top = node;
        } else {
            top.previous = node;
            top = node;
        }
    }

    Node dequeue() {
        if (first == null) {
            return null;
        }
        Node node = first;
        first = first.previous;

        if (first == null) {
            top = null;
        }
        return node;
    }

    boolean isEmpty() {
        return first == null;
    }

}