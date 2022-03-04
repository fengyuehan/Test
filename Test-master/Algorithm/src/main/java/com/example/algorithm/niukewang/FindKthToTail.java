package com.example.algorithm.niukewang;

public class FindKthToTail {
    /**
     * 输入一个链表，输出该链表中倒数第k个结点。
     * @param head
     * @param k
     * @return
     */
    public static Node findKthTail(Node head,int k){
        if (head == null || k < 0){
            return null;
        }
        int n = 0;
        while (head != null){
            n++;
            head = head.next;
        }
        //先用循环找出链表的长度
        if (k > n){
            return null;
        }
        //然后循环找出第n-k个
        for (int i = 0; i < n-k;i++){
            head = head.next;
        }
        return head;
    }
}
