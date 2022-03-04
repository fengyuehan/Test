package com.example.algorithm.niukewang;

public class CalculateMaxTreeDistance {
    /**
     * 二叉树距离 指的是两个 叶结点之间边的个数，计算一个二叉树的最大距离有两种情况，最终的目的就是要获得这两种情况的值，并取其最大值作为该结点的最大距离：
     *
     * 路径经过左子树的最深结点，通过根结点，再到右子树的最深结点，在这种情况下，最大距离等于左子树的高度加上右子树的高度在加上2。
     * 路径不穿过根结点，而是左子树或右子树的最大距离路径，在这种情况下，最大距离等于这两者的最大值。
     *
     * @param node
     */
    public void calculateMaxTreeDistance(Node node){
        if (node == null){
            return;
        }

        if (node.left == null && node.right == null){
            return;
        }

        int leftDistance = 0;
        int leftDepth = -1;
        if (node.left != null){
            calculateMaxTreeDistance(node.left);
            leftDistance = node.left.maxDistance;
            leftDepth = node.left.maxDepth;
        }
        int rightDistance = 0;
        int rightDepth = -1;
        if (node.right != null){
            calculateMaxTreeDistance(node.right);
            rightDistance = node.right.maxDistance;
            rightDepth = node.right.maxDepth;
        }

        node.maxDepth = leftDepth > rightDepth? leftDepth + 1 : rightDepth + 1;
        int throughRoot = leftDepth + rightDepth + 2;
        int notThroughRoot = leftDistance > rightDistance ? leftDistance : rightDistance;
        node.maxDistance = throughRoot > notThroughRoot ? throughRoot : notThroughRoot;
    }
}
