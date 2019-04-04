package de.comparus.opensource.longmap;

public class LongMapImpl<V> implements LongMap<V> {
    private int currentArrayCapacity;
    private Bucket<V>[] buckets;
    private long numberOfItems;

    public LongMapImpl() {
        this(16);
    }

    public LongMapImpl(int currentArrayCapacity) {
        this.currentArrayCapacity = currentArrayCapacity;
        buckets = createNewBucketsArray(currentArrayCapacity);
    }

    public V put(long key, V value) {
        KeyValueNode<V> keyValueNode = new KeyValueNode<>(key, value);
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
        return null;
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
}
