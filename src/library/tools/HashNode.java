package library.tools;
import Item.item;
import org.json.JSONObject;

public class HashNode {
        private double key;
        private String value;

        private item Item;

        HashNode left; // pointer to left child
        HashNode right; // pointer to right child

        int height; // balance factor

    public HashNode(double key, String value) {
        this.key = key;
        this.value = value;
        this.left = null;
        this.right = null;
        this.height = 1;
        this.Item = null;
    }

    public item getItem() {
        return Item;
    }

    public void setItem(item Item) {
        if(this.Item != Item) {
            this.Item = Item;
        }
    }
    public double getKey() {
        return key;
    }

    public void setKey(double key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public HashNode getLeft() {
        return left;
    }

    public void setLeft(HashNode left) {
        this.left = left;
    }

    public HashNode getRight() {
        return right;
    }

    public void setRight(HashNode right) {
        this.right = right;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "HashNode{" +
                "key=" + key +
                ", value='" + value + '\'' +
                ", Item=" + Item +
                ", left=" + left +
                ", right=" + right +
                ", height=" + height +
                '}';
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("keyBox", key);
        json.put("value", value);
        json.put("Item", Item.toJson());

        return json;
    }
}
