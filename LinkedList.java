package edu.app.listas;

import java.util.NoSuchElementException;

public class LinkedList<T> {

    public enum ListType { SINGLY, DOUBLY, CIRCULAR }

    private Node<T> head;
    private Node<T> tail;
    private int size;
    private final ListType type;

    public LinkedList(ListType type) {
        this.type = type;
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    private boolean isCircular() { return type == ListType.CIRCULAR; }
    private boolean isDoubly() { return type == ListType.DOUBLY; }

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }
    public void clear() { head = tail = null; size = 0; }

    // Inserciones
    public void addFirst(T data) {
        Node<T> newNode = isDoubly() ? new DoublyNode<>(data) : new Node<>(data);

        if (isEmpty()) {
            head = tail = newNode;
            if (isCircular()) newNode.next = newNode;
        } else {
            newNode.next = head;
            if (isDoubly()) ((DoublyNode<T>) head).prev = (DoublyNode<T>) newNode;
            head = newNode;
            if (isCircular()) {
                if (isDoubly()) {
                    ((DoublyNode<T>) tail).next = (DoublyNode<T>) head;
                    ((DoublyNode<T>) head).prev = (DoublyNode<T>) tail;
                } else {
                    tail.next = head;
                }
            }
        }
        size++;
    }

    public void addLast(T data) {
        if (isEmpty()) { addFirst(data); return; }

        Node<T> newNode = isDoubly() ? new DoublyNode<>(data) : new Node<>(data);

        if (isCircular()) {
            newNode.next = head;
            if (isDoubly()) {
                ((DoublyNode<T>) newNode).prev = (DoublyNode<T>) tail;
                ((DoublyNode<T>) head).prev = (DoublyNode<T>) newNode;
            }
        }

        tail.next = newNode;
        if (isDoubly()) ((DoublyNode<T>) newNode).prev = (DoublyNode<T>) tail;

        tail = newNode;
        size++;
    }

    public void addAt(int index, T data) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("Índice inválido: " + index);
        if (index == 0) { addFirst(data); return; }
        if (index == size) { addLast(data); return; }

        Node<T> current = head;
        for (int i = 0; i < index - 1; i++) current = current.next;

        Node<T> newNode = isDoubly() ? new DoublyNode<>(data) : new Node<>(data);
        newNode.next = current.next;
        current.next = newNode;
        if (isDoubly()) {
            ((DoublyNode<T>) newNode.next).prev = (DoublyNode<T>) newNode;
            ((DoublyNode<T>) newNode).prev = (DoublyNode<T>) current;
        }
        size++;
    }

    // Eliminaciones
    public T removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Lista vacía");

        T data = head.data;
        if (size == 1) { clear(); return data; }

        head = head.next;
        if (isDoubly()) ((DoublyNode<T>) head).prev = null;
        if (isCircular()) {
            if (isDoubly()) {
                ((DoublyNode<T>) tail).next = (DoublyNode<T>) head;
                ((DoublyNode<T>) head).prev = (DoublyNode<T>) tail;
            } else {
                tail.next = head;
            }
        }
        size--;
        return data;
    }

    public T removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Lista vacía");
        if (size == 1) return removeFirst();

        T data = tail.data;
        if (isDoubly()) {
            tail = ((DoublyNode<T>) tail).prev;
            ((DoublyNode<T>) tail).next = isCircular() ? (DoublyNode<T>) head : null;
            if (isCircular()) ((DoublyNode<T>) head).prev = (DoublyNode<T>) tail;
        } else {
            Node<T> current = head;
            while (current.next != tail) current = current.next;
            tail = current;
            tail.next = isCircular() ? head : null;
        }
        size--;
        return data;
    }

    public T removeAt(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Índice inválido: " + index);
        if (index == 0) return removeFirst();
        if (index == size - 1) return removeLast();

        Node<T> current = head;
        for (int i = 0; i < index - 1; i++) current = current.next;

        T data = current.next.data;
        current.next = current.next.next;
        if (isDoubly() && current.next != null) ((DoublyNode<T>) current.next).prev = (DoublyNode<T>) current;
        size--;
        return data;
    }

    public boolean removeValue(T value) {
        if (isEmpty()) return false;
        if (head.data.equals(value)) { removeFirst(); return true; }

        Node<T> current = head;
        Node<T> previous = null;

        do {
            if (current.data.equals(value)) {
                if (current == tail) { removeLast(); return true; }
                previous.next = current.next;
                if (isDoubly()) ((DoublyNode<T>) current.next).prev = (DoublyNode<T>) previous;
                size--;
                return true;
            }
            previous = current;
            current = current.next;
        } while (current != null && (!isCircular() || current != head));

        return false;
    }

    public int indexOf(T value) {
        if (isEmpty()) return -1;
        int index = 0;
        Node<T> current = head;

        do {
            if (current.data.equals(value)) return index;
            current = current.next;
            index++;
        } while (current != null && (!isCircular() || current != head));

        return -1;
    }

    public boolean contains(T value) { return indexOf(value) >= 0; }

    // Acceso seguro al datos
    public T getDataAt(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Índice inválido: " + index);
        Node<T> current = head;
        for (int i = 0; i < index; i++) current = current.next;
        return current.data;
    }

    // Mostrar contenido
    public String toForwardString() {
        if (isEmpty()) return "[]";

        StringBuilder sb = new StringBuilder("[");
        Node<T> current = head;

        do {
            sb.append(current.data);
            if (current.next != null && (!isCircular() || current.next != head)) sb.append(", ");
            current = current.next;
        } while (current != null && (!isCircular() || current != head));

        sb.append("]");
        return sb.toString();
    }

    // Clases internas
    private static class Node<T> {
        T data;
        Node<T> next;
        Node(T data) { this.data = data; this.next = null; }
    }

    private static class DoublyNode<T> extends Node<T> {
        DoublyNode<T> prev;
        DoublyNode<T> next;
        DoublyNode(T data) { super(data); this.prev = null; this.next = null; }
    }
}
