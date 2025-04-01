package com.edu.pucpr;

public class Node {
    int x, y;
    Node previous;
    Node next;

    Node(int x, int y, Node previous, Node next) {
        this.x = x;
        this.y = y;
        this.previous = previous;
        this.next = next;
    }

    Node(int x, int y, Node previous) {
        this.x = x;
        this.y = y;
        this.previous = previous;
    }

}
