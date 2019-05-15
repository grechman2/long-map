package de.comparus.opensource.longmap;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class LongMapImpl<V> implements LongMap<V> {

    private static final float REHASH_THRESHHOLD = 0.7f;
    private int bucketsArraySize;
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

    public LongMapImpl(int bucketsArraySize, Class<V> clazzOfValue) {
        this.valuesClazz = clazzOfValue;
        this.bucketsArraySize = bucketsArraySize;
        buckets = createNewBucketsArray(bucketsArraySize);
    }

    public V put(long key, V value) {
        KeyValueNode<V> keyValueNode = new KeyValueNode<>(key, value);
        if(currenHashCapacity() >= REHASH_THRESHHOLD ){
            rehash();
        }

        Bucket<V> bucket = getBucket(key);
        if(!bucket.isBucketContainsItemByKey(keyValueNode)){
            bucket.addItem(keyValueNode);
            numberOfItems++;
        }else{
            KeyValueNode<V> node = bucket.getItem(key);
            node.setValue(value);
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
        long[] allKeys = new long[numberOfItems];
        int numOfAddedKeys = 0;
        for(int i = 0; i < buckets.length ; i++){
            Bucket<V> bucket = buckets[i];
            if(bucket == null) continue;
            long[] bucketKeys = bucket.getAllKeys();

            for(int j = numOfAddedKeys, k = 0; k < bucketKeys.length; j++ , k++){
                allKeys[j] = bucketKeys[k];
            }
            numOfAddedKeys += bucketKeys.length;
        }
        return allKeys;
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
        if(size() == 0) return;
        for(int i = 0; i < buckets.length ; i++){
            buckets[i] = null;
        }
        numberOfItems = 0;
    }

    private int calculateBucketAddress(long key){
       return calculateBucketAddressForSpecificSizeOfBucketArray(key, bucketsArraySize);
    }

    private int calculateBucketAddressForSpecificSizeOfBucketArray(long key, int bucketsArrayLength){
        return (int) Math.abs(key) % bucketsArrayLength;
    }

    @SuppressWarnings({"rawtypes","unchecked"})
    private Bucket<V>[] createNewBucketsArray(int size){
        return (Bucket<V>[]) new Bucket[size];
    }

    private Bucket<V> getBucket(Long key){
       return getBucket(key, buckets);
    }

    private Bucket<V> getBucket(Long key, Bucket<V>[] bucketsArray){
        Bucket<V> bucket = bucketsArray[calculateBucketAddressForSpecificSizeOfBucketArray(key, bucketsArray.length)];
        if(bucket == null) {
            bucket = new Bucket<>();
            bucketsArray[calculateBucketAddressForSpecificSizeOfBucketArray(key, bucketsArray.length)] = bucket;
        }
        return bucket;
    }

    private float currenHashCapacity(){
        return (float) numberOfItems / buckets.length;
    }

    private void rehash() {
        int newArrayCapacity = bucketsArraySize * 2;
        Bucket<V>[] newBuckets = createNewBucketsArray(newArrayCapacity);
        for(int i = 0; i < buckets.length ; i++){
            Bucket<V> bucket = buckets[i];
            if(bucket == null) continue;
            bucket.getAllItems().forEach( item -> moveKeyValuePairToNewBucketsArray(newBuckets, item));
        }
        bucketsArraySize = newArrayCapacity;
        buckets = newBuckets;
    };

    private void moveKeyValuePairToNewBucketsArray(Bucket<V>[] newBucketsArray, KeyValueNode<V> pair){
        Bucket<V> bucket = getBucket(pair.getKey(), newBucketsArray);
        bucket.addItem(pair);
    }

    protected int getBucketsArraySize(){
        return buckets.length;
    }
}
