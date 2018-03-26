import java.util.Hashtable;

public class LinkedListQuestions {
  public static void main(String args[]) {
    System.out.println("Removing duplicates");

    Node head = new Node(1);
    head.appendToTail(4);
    head.appendToTail(10);
    head.appendToTail(4);
    head.appendToTail(3);
    head.appendToTail(3);
    head.appendToTail(2);

    removeDuplicates(head);
    while (head.next != null) {
        System.out.println("data is "+head.data);
        head = head.next;
    }

    System.out.println("Finding the Kth last element using recursion");
  }

  private static void removeDuplicates(Node n) {
    Hashtable<Integer, Boolean> table = new Hashtable<Integer, Boolean>();
    Node previous = null;
    while(n != null) {
        if (table.containsKey(n.data)) {
            previous.next = n.next;
        } else {
            table.put(n.data, true);
            previous = n;
        }
        n = n.next;
    }
  }

  private static class Node {
    Node next = null;

    int data;

    public Node(int d) {
        data = d;
    }

    void appendToTail(int d) {
        Node end = new Node(d);
        Node n = this;
        while (n.next != null) {
            n = n.next;
        }
        n.next = end;
    }
  }
}