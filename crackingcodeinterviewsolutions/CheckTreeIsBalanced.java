import java.util.Hashtable;

public class CheckTreeIsBalanced {
    public int pIndex = 0;    

  public static void main(String args[]) {
    int[] arr = new int[]{50,30,10,40,70,60,90};

    CheckTreeIsBalanced q = new CheckTreeIsBalanced();
    TreeNode tree = q.constructTreeUsingPreorderTraversal(arr, arr[0], Integer.MIN_VALUE, Integer.MAX_VALUE);
    q.displayTree(tree);

    System.out.println("is balanced is:");
    System.out.println(q.isBalanced(tree));
  }

  public boolean isBalanced(TreeNode root) {
    if (root == null) return true; //Base case

    System.out.println("looking at "+root.data);

    System.out.println("left tree height is "+getHeight(root.left));
    System.out.println("right tree height is "+getHeight(root.right));

    int heightDiff = getHeight(root.left) - getHeight(root.right);
    if (Math.abs(heightDiff) > 1) {
        return false;
    } else {
        return isBalanced(root.left) && isBalanced(root.right);
    }
  }

  private int getHeight(TreeNode root) {
    if (root == null) return 0;
    System.out.println("getting height for "+root.data);
    return Math.max(getHeight(root.left), getHeight(root.right)) + 1;
  }

  public TreeNode constructTreeUsingPreorderTraversal(int[] arr, int data, int min, int max) {
    if (pIndex < arr.length) {
        if (arr[pIndex] > min && arr[pIndex] < max) {
            TreeNode root = new TreeNode(data);
            pIndex++;
            if (pIndex < arr.length) {
                root.left = constructTreeUsingPreorderTraversal(arr, arr[pIndex], min, data);
                root.right = constructTreeUsingPreorderTraversal(arr, arr[pIndex], data, max);
            }
            return root;
        }
    }
    return null;
  }

  public void displayTree(TreeNode root) {
        if (root != null) {
            displayTree(root.left);
            System.out.print(" " + root.data);
            displayTree(root.right);
            System.out.println("");
        }
    }

  private static class TreeNode {
    TreeNode left = null;
    TreeNode right = null;

    int data;

    public TreeNode(int d) {
        data = d;
    }

    void appendToLeft(int d) {
        this.left = new TreeNode(d);
    }

    void appendToRight(int d) {
        this.right = new TreeNode(d);
    }
  }
}