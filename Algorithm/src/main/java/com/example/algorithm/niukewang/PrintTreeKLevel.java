package com.example.algorithm.niukewang;

import java.util.LinkedList;

public class PrintTreeKLevel {
    public void printTreeKLevel(Node node,int k){
        if (node == null || node.root == null){
            return;
        }

        Node root = node.root;
        LinkedList<Node> queue = new LinkedList<>();
        queue.offer(root);
        Node end = root;
        int curLevel = 1;
        while (! queue.isEmpty()){
            Node node1 = queue.getFirst();
            if (curLevel == k){
                System.out.print("" + node1.value);
            }

            if(node1.left != null){
                queue.offer(node1.left);
            }
            if (node1.right != null){
                queue.offer(node1.right);
            }

            if (node1 == end){
                curLevel++;
                if (curLevel > k){
                    break;
                }
                end = queue.getLast();

            }
            queue.pop();
        }

    }
}
