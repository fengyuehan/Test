package com.example.algorithm.niukewang;

public class DeleteNode {
    /**
     * 在给定的单向列表的头指针和一个结点指针，定义在一个函数在O(1)时间删除该结点。
     */
    public void deleteNode(Node pHead, Node pDelNode){
        if (pHead == null || pDelNode == null){
            return;
        }

        //只有一个节点
        if (pHead == pDelNode){
            pHead = null;
            pDelNode = null;
        }else if (pDelNode.next != null){
            //删除的不是尾节点
            Node node = pDelNode.next;
            pDelNode.val = node.val;
            pDelNode.next = node.next;
            node = null;
        }else {
            //多个节点，删除尾节点
            Node node = pHead;
            while (node.next != pDelNode){
                node = node.next;
            }
            //循环是找到删除节点的前一个，所以直接将node.next置为null，相当于删除了最后一个
            node.next = null;
            pDelNode = null;
        }
    }
}
