package com.example.datastructures;

import android.app.IntentService;
import android.util.Log;

import java.util.Stack;

public class PtintList {

    /**
     * 输入个链表的头结点，从尾到头反过来打印出每个结点的值。
     */
    public class ListNode{
        int val;
        ListNode next;
    }

    public void printList(ListNode node){
        Stack<ListNode> stack = new Stack<>();
        while (node != null){
            stack.push(node);
            node = node.next;
        }
        ListNode temp;
        while (!stack.isEmpty()){
            temp = stack.pop();
            Log.e("zzf",temp.val+"");
        }
    }
}
