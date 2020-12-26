package CrackingTheCodingInterview;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class C2_Linked_Lists {

    // single linked list. But can be doubly linked list
    class Node {
        Node next;
        int data;
        Node(int data) {
            this.data = data;
        }
        void appendTail(int data) {
            Node node = this;
            while (node.next != null) node = node.next;
            node.next = new Node(data);
        }
    }

    // remove first occurrence of node with given data
    Node removeNode(final Node head, int data) {
        if (head.data == data) return head.next;
        Node n = head;
        while(n.next != null) {
            if (n.next.data == data) {
                n.next = n.next.next;
                break;
            }
            n = n.next;
        }
        return head;
    }

    Node getList() {
        Random random = new Random();
        final Node head = new Node(random.nextInt(20));
        Node node = head;
        for (int i = 0; i < 10 + random.nextInt(10); i++) {
            node.next = new Node(random.nextInt(20));
            node = node.next;
        }
        return head;
    }

    int size(Node head) {
        int n = 0;
        while (head.next != null) {
            head = head.next;
            n++;
        }
        return n;
    }

    void print(Node head) {
        System.out.print(head.data);
        while (head.next != null) {
            head = head.next;
            System.out.print(" -> " + head.data);
        }
        System.out.println();
    }

    /**
     * Write code to remove duplicates from an unsorted linked list.
     * How would you solve this problem if a temporary buffer is not allowed?
     */
    @Test
    public void _2_1_remove_duplicates_list() {
        assertTrue(false);
    }

    /**
     * Implement an algorithm to find the nth to last element of a singly linked list.
     */
    @Test
    public void _2_2_nth_to_last() {
        // I need 2 elements: current and nth back from current, when current will be last I'll stop and return nth
        Node head = getList();
        print(head);
        // 4-th:
        int nth = 4;
        Node nthN = null;
        while(head.next != null) {
            if (nth != 0) nth--;
            else {
                nthN = head;
                nth = -1;
            }
            

        }
        System.out.println(nthN != null ? nthN.data : null);
        assertTrue(false);
    }

    /**
     * Implement an algorithm to delete a node in the middle of a single linked list, given only access to that node.
     * Input: the node 'c' from the linked list a->b->c->d->e
     * Result: nothing is returned, but the new linked list looks like a->b->d->e
     */
    @Test
    public void _2_3_delete_node_restricted_access() {
        Node head = getList();
        print(head);
        int element = size(head)/2;
        Node n = head;
        while(element-- != 0) n = n.next;
        System.out.println(n.data);
        System.out.println(removeNode(n));
        print(head);
    }

    // returns false if removal was unsuccessful. Unsuccessful when n is the last node.
    boolean removeNode(Node n) {
        if (n == null || n.next == null) return false;
        n.data = n.next.data;
        n.next = n.next.next;
        return true;
    }

    /**
     * You have two numbers represented by a linked list, where each node contains a single digit. The digits are
     * stored in reverse order, such that the 1's digit is at the head of the list. Write a function that adds the
     * two numbers and returns the sum as a linked list.
     * Input: (3 -> 1 -> 5) + (5 -> 9 -> 2)
     * Output: 8 -> 0 -> 8
     * 513+295=808
     */
    @Test
    public void _2_4_list_number_sum() {
        assertTrue(false);
    }

    /**
     * Given a circular linked list, implement an algorithm which returns node at the beginning of the loop.
     * input: A B C D E C[the same C as earlier]
     * output: C
     */
    @Test
    public void _2_5_circular_beginning() {
        assertTrue(false);
    }
    
}
