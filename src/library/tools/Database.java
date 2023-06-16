package library.tools;


import org.json.JSONObject;
import org.json.JSONArray;

import Item.item;

import java.util.*;

public class Database {

    private static Database instance = null;
    private static HashNode[] buckets;
    private static int numOfBuckets; // capacity
    private static int size;

    private item Item;

    private Database() {}

    public static Database getInstance() {
        if(instance == null) {
            numOfBuckets = 26;
            buckets = new HashNode[numOfBuckets];
            size = 0;
            instance = new Database();
        }
        return instance;

    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void add(HashNode node) {

        int firstDigit = 0;
        int temp = (int) Math.floor(node.getKey());
        firstDigit = temp/10000;
        int bucketIndex = getBucketIndex(firstDigit);
        if(buckets[bucketIndex] == null) {
            buckets[bucketIndex] = node;
            size++;
        } else {
            HashNode head = buckets[bucketIndex];
            if(searchTree(head, node.getKey()) != null) {
                HashNode inPlace = searchTree(head, node.getKey());

                int firstDigit1 = 0;
                int temp1 = (int) Math.floor(inPlace.getKey());
                firstDigit1 = temp/10000;
                int bucketIndex1 = getBucketIndex(firstDigit1);
                if(buckets[bucketIndex1] == inPlace) {
                    buckets[bucketIndex1].setValue(node.getValue());
                }
                else {
                    inPlace.setValue(node.getValue());
                }

            } else {
                BSTInsert(head, node);
            }
        }
    }

    private int getBucketIndex(int key) {
        int temp = key - 11;
        return temp % numOfBuckets;
    }

    public HashNode search(double key) {
        int firstDigit = 0;
        int temp = (int) Math.floor(key);
        firstDigit = temp/10000;
        int bucketIndex = getBucketIndex(firstDigit);
        HashNode head = buckets[bucketIndex];
        return searchTree(head, key);
    }

    public void remove(double key) {
        int firstDigit = 0;
        int temp = (int) Math.floor(key);
        firstDigit = temp/10000;
        int bucketIndex = getBucketIndex(firstDigit);
        HashNode head = buckets[bucketIndex];
        HashNode delete = Remove(head, key);
    }

    public ArrayList<HashNode> booksByAuthor(int firstDigit) {
        int bucketIndex = getBucketIndex(firstDigit);
        HashNode head = buckets[bucketIndex];
        ArrayList<HashNode> nodes = new ArrayList<>();
        return printTree(head, nodes);
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    private int Height(HashNode node)
    {
        if (node == null) {return 0;}
        else {return node.getHeight();}
    }

    // Balance computes the balance factor of the node
    private int Balance(HashNode node)
    {
        if (node == null) {return 0;}
        else { return (Height(node.getRight()) - Height(node.getLeft())); }
    }

    // updateHeight updates the height of the node
    private void updateHeight(HashNode node)
    {
        int l = Height(node.getLeft());
        int r = Height(node.getRight());

        node.setHeight(Math.max(l , r) + 1);
    }


    public ArrayList<HashNode> levelOrder(HashNode currPtr) {
        ArrayList<HashNode> nodes = new ArrayList<>();

        Queue<HashNode> queue = new LinkedList<HashNode>();
        queue.add(currPtr);

        while(!queue.isEmpty()) {
            HashNode node = queue.poll();
            nodes.add(node);
            if(node != null) {
                /*Enqueue left child */
                if (node.getLeft() != null) {
                    queue.add(node.getLeft());
                }

                /*Enqueue right child */
                if (node.getRight() != null) {
                    queue.add(node.getRight());
                }
            }
        }
        return nodes;
    }

    // search the tree for the key k
    // and return the corresponding node
    private HashNode searchTree(HashNode node, double key) {
        if (node == null || key == node.getKey()) {
            return node;
        }
        if (key < node.getKey()) {
            return searchTree(node.getLeft(), key);
        }
        return searchTree(node.getRight(), key);
    }


    private ArrayList<HashNode> printTree(HashNode currPtr, ArrayList<HashNode> nodes) {
        // print the tree structure on the screen
        if (currPtr != null) {
            printTree(currPtr.getLeft(), nodes);
            nodes.add(currPtr);
            printTree(currPtr.getRight(), nodes);
        }

        return nodes;
    }

    private HashNode rotateLeft(HashNode node)
    {
        HashNode right = node.getRight();
        HashNode rightLeft = right.getLeft();

        int firstDigit = 0;
        int temp = (int) Math.floor(right.getKey());
        firstDigit = temp/10000;
        int bucketIndex = getBucketIndex(firstDigit);

        if(buckets[bucketIndex] == right) {
            buckets[bucketIndex].setLeft(node);
        }

        else {
            right.setLeft(node);
        }

        int firstDigit1 = 0;
        int temp1 = (int) Math.floor(node.getKey());
        firstDigit1 = temp/10000;
        int bucketIndex1 = getBucketIndex(firstDigit1);

        if(buckets[bucketIndex] == node) {
            buckets[bucketIndex1].setRight(rightLeft);
        }

        else{
            node.setRight(rightLeft);
        }

        updateHeight(node);
        updateHeight(right);

        return right;
    }

    private HashNode rotateRight(HashNode node)
    {
        HashNode left = node.getLeft();
        HashNode leftRight = left.getRight();

        int firstDigit = 0;
        int temp = (int) Math.floor(left.getKey());
        firstDigit = temp/10000;
        int bucketIndex = getBucketIndex(firstDigit);

        if(buckets[bucketIndex] == left) {
            buckets[bucketIndex].setRight(node);
        }

        else {
            left.setRight(node);
        }

        int firstDigit1 = 0;
        int temp1 = (int) Math.floor(node.getKey());
        firstDigit1 = temp/10000;
        int bucketIndex1 = getBucketIndex(firstDigit1);

        if(buckets[bucketIndex] == node) {
            buckets[bucketIndex1].setLeft(leftRight);
        }

        else{
            node.setLeft(leftRight);
        }

        updateHeight(node);
        updateHeight(left);

        return left;
    }

    // balanceTree balances the tree using rotations after an insertion or deletion
    private HashNode balanceTree(HashNode node)
    {
        if(node == null) {return null;}

        updateHeight(node);
        int balance = Balance(node);

        if (balance > 1) //R
        {
            if (Balance(node.getRight()) < 0) // rightLeft rotation
            {
                int firstDigit = 0;
                int temp = (int) Math.floor(node.getKey());
                firstDigit = temp/10000;
                int bucketIndex = getBucketIndex(firstDigit);

                if(buckets[bucketIndex] == node) {
                    buckets[bucketIndex].setRight(rotateRight(node.getRight()));
                }

                else {
                    node.setRight(rotateRight(node.getRight()));
                }

                return rotateLeft(node);
            }

            else { return rotateLeft(node); } // left rotation
        }

        if (balance < -1)//L
        {
            if (Balance(node.getLeft()) > 0)//LR
            {

                int firstDigit = 0;
                int temp = (int) Math.floor(node.getKey());
                firstDigit = temp/10000;
                int bucketIndex = getBucketIndex(firstDigit);

                if(buckets[bucketIndex] == node) {
                    buckets[bucketIndex].setLeft(rotateLeft(node.getLeft()));
                }

                else {
                    node.setLeft(rotateLeft(node.getLeft()));
                }
                return rotateRight(node);
            }
            else//LL
                return rotateRight(node);
        }
        return node;
    }

    private HashNode BSTInsert(HashNode node, HashNode other)
    {
        // Performs normal BST insertion
        if (node == null) {return other;}

        else if (other.getKey() < node.getKey()) {

            int firstDigit = 0;
            int temp = (int) Math.floor(node.getKey());
            firstDigit = temp/10000;
            int bucketIndex = getBucketIndex(firstDigit);

            if(buckets[bucketIndex] == node) {
                buckets[bucketIndex].setLeft(BSTInsert(node.getLeft(), other));
            }

            else {
                node.setLeft(BSTInsert(node.getLeft(), other));
            }
        }

        else {
            int firstDigit = 0;
            int temp = (int) Math.floor(node.getKey());
            firstDigit = temp/10000;
            int bucketIndex = getBucketIndex(firstDigit);

            if(buckets[bucketIndex] == node) {
                buckets[bucketIndex].setRight(BSTInsert(node.getRight(), other));
            }

            else {
                node.setRight(BSTInsert(node.getRight(), other));
            }
        }

        // Balances the tree after BST Insertion
        return balanceTree(node);
    }

    // Successor returns the next largest node
    private HashNode NextLargest(HashNode node)
    {
        if (node.getLeft() != null)
            return NextLargest(node.getLeft());

        else {return node;}
    }

    private HashNode NextSmallest(HashNode node)
    {
        if (node.getRight() != null)
            return NextSmallest(node.getRight());

        else {return node;}
    }


    private HashNode Remove(HashNode node, double key)
    {
        // Performs standard BST Deletion
        if (node == null) {return node;}

        else if (key < node.getKey())
            node.setLeft(Remove(node.getLeft(), key));

        else if (key > node.getKey())
            node.setRight(Remove(node.getRight(), key));

        else
        {
            if (node.getRight() == null && node.getLeft() == null) {
                HashNode temp = node;
                node = null;

                int firstDigit = 0;
                int tempNum = (int) Math.floor(temp.getKey());
                firstDigit = tempNum/10000;
                int bucketIndex = getBucketIndex(firstDigit);

                HashNode head = null;
                if(levelOrder(buckets[bucketIndex]).size() == 1) {
                    buckets[bucketIndex] = null;
                    size--;
                }

                else {
                    return balanceTree(head);
                }
            }

            else if (node.getLeft() == null && node.getRight() != null) {

                int firstDigit = 0;
                int tempNum = (int) Math.floor(node.getKey());
                firstDigit = tempNum/10000;
                int bucketIndex = getBucketIndex(firstDigit);

                HashNode temp = NextLargest(node.getRight());
                node.setKey(temp.getKey());
                node.setValue(temp.getValue());
                node.setItem(temp.getItem());

                if(buckets[bucketIndex] == node) {
                    buckets[bucketIndex].setRight(Remove(node.getRight(), node.getKey()));
                }

                else {
                    node.setRight(Remove(node.getRight(), node.getKey()));
                }
            }
            else if(node.getRight() == null && node.getLeft() != null)
            {
                int firstDigit = 0;
                int tempNum = (int) Math.floor(node.getKey());
                firstDigit = tempNum/10000;
                int bucketIndex = getBucketIndex(firstDigit);

                HashNode temp =  NextSmallest(node.getLeft());
                node.setKey(temp.getKey());
                node.setValue(temp.getValue());
                node.setItem(temp.getItem());

                if(buckets[bucketIndex] == node) {
                    buckets[bucketIndex].setLeft(Remove(node.getLeft(), temp.getKey()));
                }

                else {
                    node.setLeft(Remove(node.getLeft(), temp.getKey()));
                }
            }
            else {
                int firstDigit = 0;
                int tempNum = (int) Math.floor(node.getKey());
                firstDigit = tempNum/10000;
                int bucketIndex = getBucketIndex(firstDigit);

                HashNode temp =  NextSmallest(node.getLeft());
                node.setKey(temp.getKey());
                node.setValue(temp.getValue());
                node.setItem(temp.getItem());

                if(buckets[bucketIndex] == node) {
                    buckets[bucketIndex].setLeft(Remove(node.getLeft(), temp.getKey()));
                }

                else {
                    node.setLeft(Remove(node.getLeft(), temp.getKey()));
                }
            }
        }
            // Balances the tree after deletion
        return balanceTree(node);
    }



// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------//


    @Override
    public String toString() {
        return "{" +
                "buckets=" + buckets.length +
                ", size=" + size +
                '}';
    }

    public void emptyList() {
        for(HashNode node : buckets) {
            for(HashNode node1 : levelOrder(node)) {
                if(node1 != null) {
                    remove(node1.getKey());
                }
            }
        }
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("nodes", listToJson());
        return json;
    }


    private JSONArray listToJson() {

        JSONArray jsonArray = new JSONArray();
     for(HashNode node : buckets) {
         for(HashNode node1 : levelOrder(node)) {
             if(node1 != null) {
                 jsonArray.put(node1.toJson());
             }
         }
     }
        return jsonArray;
    }
}
