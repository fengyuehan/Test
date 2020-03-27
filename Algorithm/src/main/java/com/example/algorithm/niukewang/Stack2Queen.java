package com.example.algorithm.niukewang;

import java.util.Stack;

public class Stack2Queen {
    /**
     * 用两个栈来实现一个队列，完成队列的Push和Pop操作。 队列中的元素为int类型。
     */
    Stack<Integer> stack = new Stack<>();
    Stack<Integer> stack1 = new Stack<>();

    public void push(int node){
        stack.push(node);
    }

    public int pop(){
        if (stack1 == null){
            while (!stack.isEmpty()){
                stack1.push(stack.pop());
            }
        }
        return stack1.pop();
    }
}
