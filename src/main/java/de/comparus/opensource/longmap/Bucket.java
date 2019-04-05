package de.comparus.opensource.longmap;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Bucket<V> {

    private int bucketSize = 6;
    private int numberOfElements = 0;
    private KeyValueNode<V>[] nodes;

    public Bucket() {
        nodes = createNewValueNodeArray(bucketSize);
    }

    public Bucket(int bucketSize) {
        this.bucketSize = bucketSize;
    }

    public void addItem(KeyValueNode<V> item){
           addItemToEmptyCell(item);
    }

    boolean isBucketContainsItemByKey(KeyValueNode<V> item){
        return isBucketContainsItemByKey(item.getKey());
    }

    boolean isBucketContainsItemByValue(V value){
        return searchForItemByValue(value) != null;
    }

    boolean isBucketContainsItemByKey(long key){
        return searchForItemByKey(key) != null;
    }

    public KeyValueNode<V> getItem(long key) {
       KeyValueNode<V> item = searchForItemByKey(key);
       return item;
    }

    public KeyValueNode<V>[] getAllItems(){
        KeyValueNode<V>[] tmp = createNewValueNodeArray(nodes.length);
        int nextCellIndex = 0;
        for(int i = 0; i < nodes.length; i++){
           if(nodes[i] != null){
               tmp[nextCellIndex] = nodes[i];
               nextCellIndex++;
           }
       }
       return Arrays.copyOfRange(tmp, 0, nextCellIndex);
    }

    public V[] getAllValue(){
        @SuppressWarnings({"rawtypes","unchecked"})
        V[] tmp = (V[]) new Object[nodes.length];
        int nextCellIndex = 0;
        for(int i = 0; i < nodes.length; i++){
            if(nodes[i] != null){
                tmp[nextCellIndex] = nodes[i].getValue();
                nextCellIndex++;
            }
        }
        return Arrays.copyOfRange(tmp, 0, nextCellIndex);
    }

    public long[] getAllKeys(){
        long[] tmp = new long[nodes.length];
        int nextCellIndex = 0;
        for(int i = 0; i < nodes.length; i++){
            if(nodes[i] != null){
                tmp[nextCellIndex] = nodes[i].getKey();
                nextCellIndex++;
            }
        }
        return Arrays.copyOfRange(tmp, 0, nextCellIndex);
    }

    public KeyValueNode<V> searchForItemByKey(long key){
        for(int i = 0; i < nodes.length; i++){
            KeyValueNode<V> keyValueNode = nodes[i];
            if (keyValueNode != null && keyValueNode.getKey() == key){
                return keyValueNode;
            }
        }
        return null;
    }

    public KeyValueNode<V> searchForItemByValue(V value){
        for(int i = 0; i < nodes.length; i++){
            KeyValueNode<V> keyValueNode = nodes[i];
            if (keyValueNode != null && keyValueNode.getValue() != null && keyValueNode.getValue().equals(value)){
                return keyValueNode;
            }
        }
        return null;
    }

    public void addItemToEmptyCell(KeyValueNode<V> item){
        for(int i = 0; i < nodes.length; i++){
            KeyValueNode<V> keyValueNode = nodes[i];
            if(keyValueNode == null){
                nodes[i] = item;
                return;
            }
        }
    }

    @SuppressWarnings({"rawtypes","unchecked"})
    private KeyValueNode<V>[] createNewValueNodeArray(int size){
        return (KeyValueNode<V>[]) new KeyValueNode[size];
    }

    public KeyValueNode<V> removeItem(long key) {
        for(int i = 0; i < nodes.length; i++){
            KeyValueNode<V> keyValueNode = nodes[i];
            if (keyValueNode != null && keyValueNode.getKey() == key){
                nodes[i] = null;
                return keyValueNode;
            }
        }
        return null;
    }
}
