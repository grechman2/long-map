package de.comparus.opensource.longmap;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RehashLongManImplTest extends LongMapImpl {

    private static final int INITIAL_NUMBER_OF_BUCKETS = 4;

    public RehashLongManImplTest() {
        super(INITIAL_NUMBER_OF_BUCKETS,String.class);
    }

    LongMapImpl<String> map;

    @Before
    public void setUp() throws Exception {
        map = this;
    }

    @Test
    public void testRehashOfMap_GivenMapWithCapacityOf2Buckets_whenAdd4Items_MapIncreasesNumberOfBucketsAndRearangeItemsInNewBuckets() {
        // arrange
        String testValue1 = "SomeValue1";
        Long key1 = 100L;
        String testValue2 = "SomeValue2";
        Long key2 = 110L;
        String testValue3 = "SomeValue3";
        Long key3 = 120L;
        String testValue4 = "SomeValue4";
        Long key4 = 130L;
        String testValue5 = "SomeValue5";
        Long key5 = 140L;

        assertThat(map.getBucketsArraySize()).isEqualTo(INITIAL_NUMBER_OF_BUCKETS);

        map.put(key1, testValue1);
        assertThat(map.getBucketsArraySize()).isEqualTo(INITIAL_NUMBER_OF_BUCKETS);

        // add first element, number of bucket is still the same  as current map capacity (numberOfItems/numberOfBukets) = 1/4 = 0.25 < 0.7 (REHASH_THRESHHOLD)
        map.put(key2, testValue2);
        assertThat(map.getBucketsArraySize()).isEqualTo(INITIAL_NUMBER_OF_BUCKETS);

        // add second element, number of bucket is still the same as current map capacity (numberOfItems/numberOfBukets) = 2/4 = 0.5 < 0.7 (REHASH_THRESHHOLD)
        map.put(key3, testValue3);
        assertThat(map.getBucketsArraySize()).isEqualTo(INITIAL_NUMBER_OF_BUCKETS);

        // add third element, number of bucket is changed because current map capacity (numberOfItems/numberOfBukets) = 3/4 = 0.75 > 0.7 (REHASH_THRESHHOLD)
        map.put(key4, testValue4);
        assertThat(map.getBucketsArraySize()).isEqualTo(INITIAL_NUMBER_OF_BUCKETS*2);

        map.put(key5, testValue5);
        assertThat(map.getBucketsArraySize()).isEqualTo(INITIAL_NUMBER_OF_BUCKETS*2);

        //assert
        assertThat(map.size()).isEqualTo(5);
        assertThat(map.get(key1)).isEqualTo(testValue1);
        assertThat(map.get(key2)).isEqualTo(testValue2);
        assertThat(map.get(key3)).isEqualTo(testValue3);
        assertThat(map.get(key4)).isEqualTo(testValue4);
        assertThat(map.get(key5)).isEqualTo(testValue5);
    }
}
