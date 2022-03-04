package com.example.algorithm.niukewang;

public class MergeTwoLists {
    /**
     * 输入两个递增排序的链表，合并这两个链表并使新链表中的结点仍然是按照递增排序的
     * @param node1
     * @param node2
     * @return
     */
    public Node mergeTwoLists(Node node1,Node node2){
        if (node1 == null){
            return node2;
        }
        if (node2 == null){
            return node1;
        }
        Node node = new Node();
        while (node1 != null && node2 != null){
            if (node1.val < node2.val){
                node.next = node1;
                node1 = node1.next;
            }else {
                node.next = node2;
                node2 = node2.next;
            }
            node = node.next;
        }
        //下面的判断是当两个node的长度不一致的时候
        if (node1 == null){
            node.next = node2;
        }else {
            node.next = node1;
        }
        return node.next;
    }

    public Node merge(Node node1,Node node2){
        if (node1 == null){
            return node2;
        }
        if (node2 == null){
            return node1;
        }
        if(node1.val<node2.val) {
            node1.next=merge(node1.next, node2);
            return node1;
        }else {
            node2.next=merge(node1, node2.next);
            return node2;
        }
    }
}
