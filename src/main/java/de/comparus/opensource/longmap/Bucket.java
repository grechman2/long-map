package de.comparus.opensource.longmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Bucket<V> {

    private List<KeyValueNode<V>> nodes;

    public Bucket() {
        nodes = new ArrayList<>();
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

    public List<KeyValueNode<V>> getAllItems(){
        return nodes;
    }

    public List<V> getAllValue(){
        return nodes.stream().map(item -> item.getValue()).collect(Collectors.toList());
    }

    public long[] getAllKeys(){
        return nodes.stream().mapToLong(item -> item.getKey()).toArray();
    }

    public KeyValueNode<V> searchForItemByKey(long key){
        Optional<KeyValueNode<V>> node = nodes.stream().filter(item -> item.getKey() == key).findFirst();
        return node.isPresent() == true? node.get() : null;
    }

    public KeyValueNode<V> searchForItemByValue(V value){
        Optional<KeyValueNode<V>> node = nodes.stream().filter(item ->  item.getValue() != null && item.getValue().equals(value)).findFirst();
        return node.isPresent() == true? node.get() : null;
    }

    public void addItemToEmptyCell(KeyValueNode<V> item){
       nodes.add(item);
    }

    public KeyValueNode<V> removeItem(long key) {
        KeyValueNode<V> itemToRemove = searchForItemByKey(key);
        if(itemToRemove == null) return null;
        nodes.remove(itemToRemove);
        return itemToRemove;
    }
}
