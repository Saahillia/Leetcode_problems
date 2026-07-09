/*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
};
*/

class Solution {
    public Node connect(Node root) {
        // Base case: If the root is null or a leaf node (i.e., no children),
        // there are no children to connect, so return.
        if (root == null || root.left == null) {
            return root;
        }

        // 1. Connect the left child to the right child of the current node.
        // In a perfect binary tree, if root.left exists, root.right also exists.
        root.left.next = root.right;

        // 2. Connect the right child of the current node to the left child of its next sibling.
        // This connection spans across different parent nodes but stays on the same level.
        // This only applies if the current node has a next sibling.
        if (root.next != null) {
            root.right.next = root.next.left;
        }

        // 3. Recursively call connect for the left and right subtrees.
        // The connections for the current level's children are now set up,
        // allowing the recursive calls to use these 'next' pointers for deeper levels.
        connect(root.left);
        connect(root.right);

        return root;
    }
}