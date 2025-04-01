package com.edu.pucpr;

public class Stack {
    private Node top;

    void push(int x, int y) {
        top = new Node(x, y, top);
    }

    Node pop() {
        if (top == null){
            return null;
        }

        Node node = top;
        top = top.previous;
        return node;
    }

    boolean isEmpty() {
        return top == null;
    }
}
