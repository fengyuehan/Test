package com.example.algorithm.niukewang;

public class Reverse {
    /**
     * 单链表的反转
     * @param node
     * @return
     */
    public Node reverse(Node node){
        if (node == null){
            return node;
        }

        Node pre = null;//用于保存前一个节点
        Node next = null;//用于保存临时变量
        while (node.next != null){
            next = node.next;
            node.next = pre;//核心代码
            pre = node;
            node = next;
        }

        return pre;
    }

    //采用递归
    public Node reverse1(Node node){
        if (node == null || node.next == null){
            return node;
        }

        Node temp = node.next;
        Node newNode = reverse(node.next);
        temp.next = node;//不是应该是node.next
        node.next = null;
        return newNode;
    }
}
