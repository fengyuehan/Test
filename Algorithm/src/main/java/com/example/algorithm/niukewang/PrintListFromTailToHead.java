package com.example.algorithm.niukewang;

import java.util.ArrayList;
import java.util.Stack;

public  class PrintListFromTailToHead {
    /**
     * 输入一个链表，按链表值从尾到头的顺序返回一个ArrayList。
     * @param node
     * @return
     */
    public static ArrayList<Integer> printListFromTailToHead(Node node){
        ArrayList<Integer> arrayList = new ArrayList<>();
        if (node == null){
            return arrayList;
        }
        Stack<Integer> stack = new Stack<>();
        while (node.next != null){
            stack.push(node.val);
            node = node.next;
        }
        stack.push(node.val);
        while (!stack.isEmpty()){
            arrayList.add(stack.pop());
        }
        return arrayList;
    }
}
