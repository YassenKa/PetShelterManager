package code;

import java.util.ArrayList;

public class GenericTree<T> {
    private T data;
    private ArrayList<GenericTree<T>> children;

    public GenericTree(T data) {
        this.data = data;
        this.children = new ArrayList<>();
    }

    public void addChild(GenericTree<T> child) {
        children.add(child);
    }

    public T getData() {
        return data;
    }

    public ArrayList<GenericTree<T>> getChildren() {
        return children;
    }
}
