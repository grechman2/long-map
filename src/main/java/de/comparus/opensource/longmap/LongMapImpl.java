package de.comparus.opensource.longmap;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LongMapImpl<V> implements LongMap<V> {

    private static final float REHASH_THRESHHOLD = 0.7f;
    private int currentArrayCapacity;
    private Bucket<V>[] buckets;
    private int numberOfItems;
    private Class<V> valuesClazz;

    /**
     *  Class of value type should be passed as a parameter, as
     *  it is not possible to create array with generic type, as
     *  types are erased at runtime.
     * */
    public LongMapImpl(Class<V> clazzOfValue) {
        this(16, clazzOfValue);
    }

    public LongMapImpl(int currentArrayCapacity, Class<V> clazzOfValue) {
        this.valuesClazz = clazzOfValue;
        this.currentArrayCapacity = currentArrayCapacity;
        buckets = createNewBucketsArray(currentArrayCapacity);
    }

    public V put(long key, V value) {
        KeyValueNode<V> keyValueNode = new KeyValueNode<>(key, value);
        if(currenHashLoadIndicator() >= REHASH_THRESHHOLD ){
            rehash();
        }

        Bucket<V> bucket = getBucket(key);
        if(!bucket.isBucketContainsItemByKey(keyValueNode)){
            bucket.addItem(keyValueNode);
            numberOfItems++;
        }
        return value;
    }

    public V get(long key) {
       Bucket<V> bucket = getBucket(key);
       KeyValueNode<V> keyValueNode = bucket.getItem(key);
       if(keyValueNode == null) return null;
       return keyValueNode.getValue();
    }

    public V remove(long key) {
        Bucket<V> bucket = getBucket(key);
        KeyValueNode<V> item = bucket.removeItem(key);
        if(item != null) {
            numberOfItems--;
            return item.getValue();
        }
        return null;
    }

    public boolean isEmpty() {
        return numberOfItems == 0;
    }

    public boolean containsKey(long key) {
        for(int i = 0; i < buckets.length; i++ ){
            Bucket<V> bucket = buckets[i];
            if(bucket == null ) continue;
            if(bucket.isBucketContainsItemByKey(key)){
                return true;
            }
        }
        return false;
    }

    public boolean containsValue(V value) {
        for(int i = 0; i < buckets.length; i++ ){
            Bucket<V> bucket = buckets[i];
            if(bucket == null ) continue;
            if(bucket.isBucketContainsItemByValue(value)){
                return true;
            }
        }
        return false;
    }

    public long[] keys() {
        return null;
    }

    public V[] values() {
        List<V> allValues = new ArrayList<>();
        for(int i = 0; i < buckets.length ; i++){
            Bucket<V> bucket = buckets[i];
            if(bucket == null) continue;
            allValues.addAll(bucket.getAllValue());
        }
        V[] result = (V[]) Array.newInstance(valuesClazz, allValues.size());
        allValues.toArray(result);
        return result;
    }

    public long size() {
        return numberOfItems;
    }

    public void clear() {
    }

    private int calculateBucketAddress(long key){
        return (int) key % currentArrayCapacity;
    }

    @SuppressWarnings({"rawtypes","unchecked"})
    private Bucket<V>[] createNewBucketsArray(int size){
        return (Bucket<V>[]) new Bucket[size];
    }

    private Bucket<V> getBucket(Long key){
        Bucket<V> bucket = buckets[calculateBucketAddress(key)];
        if(bucket == null) {
            bucket = new Bucket<>();
            buckets[calculateBucketAddress(key)] = bucket;
        }
        return bucket;
    }

    private float currenHashLoadIndicator(){
        return numberOfItems / buckets.length;
    }

    private void rehash() {
        int newArrayCapacity = currentArrayCapacity * 2;
        Bucket<V>[] newBuckets = createNewBucketsArray(newArrayCapacity);


    }
}
