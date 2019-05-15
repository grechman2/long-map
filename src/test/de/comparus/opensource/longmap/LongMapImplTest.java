package de.comparus.opensource.longmap;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class LongMapImplTest {

    LongMap<String> map = null;

    @Before
    public void setUp() throws Exception {
        map = new LongMapImpl<>(String.class);
    }

    @Test
    public void testPut_GivenEmptyMap_whenPutValue_thenContainsOneElement() {
        // arrange
        String testValue = "TestValue";
        Long key = 100L;
        // act
        String actualValue = map.put(key, testValue);
        // asset
        assertThat(map.size()).isEqualTo(1);
        assertThat(actualValue).isEqualTo(testValue);
    }

    @Test
    public void testPut_GivenEmptyMap_whenPutValueWithNegativeKey_thenContainsOneElementAndIsAbleToGetThatValueBack() {
        // arrange
        String testValue = "TestValue";
        Long key = -100L;
        // act
        String actualValue = map.put(key, testValue);
        // asset
        assertThat(map.size()).isEqualTo(1);
        assertThat(map.get(key)).isEqualTo(testValue);
    }

    @Test
    public void testPut_GivenEmptyMap_whenPutTheSameValueTwice_thenContainsOneElement() {
        // arrange
        String testValue = "TestValue";
        Long key = 100L;
        // act
        String actualValue = map.put(key, testValue);
        String actualValue2 = map.put(key, testValue);
        // asset
        assertThat(map.size()).isEqualTo(1);
        assertThat(actualValue).isEqualTo(testValue);
        assertThat(actualValue2).isEqualTo(testValue);
    }

    @Test
    public void testPut_GivenMapWithKeyValuePair_whenPutTheValueWithTheSameKey_thenValueIsUpdated() {
        // arrange
        String testValue1 = "TestValue1";
        String testValue2 = "TestValue2";
        Long key = 100L;
        map.put(key, testValue1);

        // act
        map.put(key, testValue2);

        // assert
        assertThat(map.size()).isEqualTo(1);
        assertThat(map.get(key)).isEqualTo(testValue2);
    }

    @Test
    public void testPut_GivenMapWithKeyValuePair_whenPutTheValueWithTheZeroKey_thenGetValue() {
        // arrange
        String testValue1 = "TestValue1";
        Long key = 0L;

        // act
        map.put(key, testValue1);

        // assert
        assertThat(map.size()).isEqualTo(1);
        assertThat(map.get(key)).isEqualTo(testValue1);
    }

    @Test
    public void testPut_GivenKeyValuePairInMap_whenPutPairWithTheSameValueButDifferentKeys_thenContainsTwoElements() {
        // arrange
        String testValue = "TestValue";
        Long key1 = 100L;
        Long key2 = 101L;
        // act
        String actualValue = map.put(key1, testValue);
        String actualValue2 = map.put(key2, testValue);
        // asset
        assertThat(map.size()).isEqualTo(2);
        assertThat(actualValue).isEqualTo(testValue);
        assertThat(actualValue2).isEqualTo(testValue);
    }

    @Test
    public void testPut_GivenKeyValuePairInMap_whenPutPairWithTheSameKeyButDifferentValue_thenPreviousPairWillBeOverridenByNewOne() {
        // arrange
        String testValue1 = "TestValue";
        Long key = 100L;
        map.put(key, testValue1);
        // act
        String testValue2 = "TestValue2";
        String actualValue2 = map.put(key, testValue2);
        // asset
        assertThat(map.size()).isEqualTo(1);
        assertThat(actualValue2).isEqualTo(testValue2);
    }

    @Test
    public void testGet_GivenKeyValuePairInMap_whenGetPairByKey_thenGetKey() {
        // arrange
        String testValue = "TestValue";
        Long key = 100L;
        map.put(key, testValue);
        // act
        String actualValue = map.get(key);
        // asset
        assertThat(actualValue).isEqualTo(testValue);
    }

    @Test
    public void testGet_GivenMapIsEmpty_whenGetPairByKey_thenGetNull() {
        // arrange
        Long keyThatNotExist = 100L;
        // act
        String actualValue = map.get(keyThatNotExist);
        // asset
        assertThat(actualValue).isEqualTo(null);
    }

    @Test
    public void testRemove_GivenKeyValuePairInMap_whenRemoveByKey_thenElementIsRemoved(){
        // arrange
        String testValue = "TestValue";
        Long key = 100L;
        map.put(key, testValue);
        // act
        String actualValue = map.remove(key);
        // asset
        assertThat(map.size()).isEqualTo(0);
        assertThat(map.get(key)).isEqualTo(null);
        assertThat(actualValue).isEqualTo(testValue);
    }

    @Test
    public void testRemove_GivenEmptyMap_whenRemoveByKey_thenNullValueIsReturned(){
        // arrange
        String testValue = "TestValue";
        Long key = 100L;
        // act
        String actualValue = map.remove(key);
        // asset
        assertThat(map.size()).isEqualTo(0);
        assertThat(map.get(key)).isEqualTo(null);
        assertThat(actualValue).isEqualTo(null);
    }

    @Test
    public void testIsEmpty_GivenEmptyMap_whenInvokeTheMethod_thenGetTrue(){
        // act
        Boolean isEmpty = map.isEmpty();
        // asset
        assertThat(map.size()).isEqualTo(0);
        assertThat(isEmpty).isEqualTo(true);
    }

    @Test
    public void testIsEmpty_GivenMapIsNotempty_whenInvokeTheMethod_thenGetFalse(){
        // arrange
        String testValue = "TestValue";
        Long key = 100L;
        map.put(key, testValue);
        // act
        Boolean isEmpty = map.isEmpty();
        // asset
        assertThat(map.size()).isEqualTo(1);
        assertThat(isEmpty).isEqualTo(false);
    }

    @Test
    public void testIsEmpty_GivenMapIsNotEmpty_whenRemoveItemAndInvokeTheMethod_thenGetTrue(){
        // arrange
        String testValue = "TestValue";
        Long key = 100L;
        map.put(key, testValue);
        // act
        map.remove(key);
        Boolean isEmpty = map.isEmpty();
        // asset
        assertThat(map.size()).isEqualTo(0);
        assertThat(isEmpty).isEqualTo(true);
    }

    @Test
    public void testContainsKey_GivenMapIsNotEmpty_whenInvokeTheMethodWithKeyThatExist_thenGetTrue(){
        // arrange
        String testValue = "TestValue";
        Long key = 100L;
        map.put(key, testValue);
        // act
        Boolean containsKey = map.containsKey(key);
        // asset
        assertThat(map.size()).isEqualTo(1);
        assertThat(containsKey).isEqualTo(true);
    }

    @Test
    public void testContainsKey_GivenMapIsEmpty_whenInvokeTheMethodWithKeyThatDoentExist_thenGetFalse(){
        // arrange
        Long key = 100L;
        // act
        Boolean containsKey = map.containsKey(key);
        // asset
        assertThat(map.size()).isEqualTo(0);
        assertThat(containsKey).isEqualTo(false);
    }

    @Test
    public void testContainsValue_GivenMapIsNotEmpty_whenInvokeTheMethodWithValueThatExist_thenGetTrue(){
        // arrange
        String testValue = "TestValue";
        Long key = 100L;
        map.put(key, testValue);
        // act
        Boolean containsValue = map.containsValue(testValue);
        // asset
        assertThat(map.size()).isEqualTo(1);
        assertThat(containsValue).isEqualTo(true);
    }

    @Test
    public void testContainsValue_GivenMapIsNotEmptyAndContainsPairWithNullValue_whenInvokeTheMethodWithNullValue_thenGetFalse(){
        // arrange
        String testValue = null;
        Long key = 100L;
        map.put(key, testValue);
        // act
        Boolean containsValue = map.containsValue(testValue);
        // asset
        assertThat(map.size()).isEqualTo(1);
        assertThat(containsValue).isEqualTo(false);
    }

    @Test
    public void testContainsValue_GivenMapIsEmpty_whenInvokeTheMethodWithNonExistedValue_thenGetFalse(){
        // arrange
        String testValue = "SomeValue";
        Long key = 100L;
        map.put(key, testValue);
        // act
        String nonExistentValue = "NonExistent";
        Boolean containsValue = map.containsValue(nonExistentValue);
        // asset
        assertThat(map.size()).isEqualTo(1);
        assertThat(containsValue).isEqualTo(false);
    }

    @Test
    public void testValues_GivenMapIsNotEmpty_whenInvokeTheMethod_thenGetArrayOfValues(){
        // arrange
        String testValue1 = "SomeValue1";
        Long key1 = 100L;
        String testValue2 = "SomeValue2";
        Long key2 = 110L;
        String testValue3 = "SomeValue3";
        Long key3 = 120L;
        map.put(key1, testValue1);
        map.put(key2, testValue2);
        map.put(key3, testValue3);
        List<String> expectedValues
                = Arrays.asList(testValue1, testValue2, testValue3);
        // act
        String[] actualValues =  map.values();
        // asset
        assertThat(actualValues.length).isEqualTo(expectedValues.size());
        for(int i = 0; i < expectedValues.size(); i++){
            assertThat(actualValues[0]).isIn(expectedValues);
        }
    }

    @Test
    public void testValues_GivenMapIsEmpty_whenInvokeTheMethod_thenGetEmptyArray(){
        // act
        String[] actualValues =  map.values();
        // asset
        assertThat(actualValues.length).isEqualTo(0);
    }

    @Test
    public void testKeys_GivenMapIsNotEmpty_whenInvokeTheMethod_thenGetArrayOfKeys(){
        // arrange
        String testValue1 = "SomeValue1";
        Long key1 = 100L;
        String testValue2 = "SomeValue2";
        Long key2 = 110L;
        String testValue3 = "SomeValue3";
        Long key3 = 120L;
        map.put(key1, testValue1);
        map.put(key2, testValue2);
        map.put(key3, testValue3);
        long[] expectedKeys = {key1, key2, key3};
        // act
        long[] actualKeys =  map.keys();
        // asset
        assertThat(actualKeys.length).isEqualTo(expectedKeys.length);
        assertThat(actualKeys).containsExactlyInAnyOrder(expectedKeys);
    }

    @Test
    public void testKeys_GivenMapIsEmpty_whenInvokeTheMethod_thenGetEmptyArray(){
        // act
        long[] actualKeys =  map.keys();
        // asset
        assertThat(actualKeys.length).isEqualTo(0);
    }

    @Test
    public void testClearAll_GivenMapIsNotEmpty_whenInvokeTheMethod_thenMapIsCleared(){
        // arrange
        String testValue1 = "SomeValue1";
        Long key1 = 100L;
        String testValue2 = "SomeValue2";
        Long key2 = 110L;
        String testValue3 = "SomeValue3";
        Long key3 = 120L;
        map.put(key1, testValue1);
        map.put(key2, testValue2);
        map.put(key3, testValue3);
        // act
        map.clear();
        // asset
        assertThat(map.size()).isEqualTo(0);
        assertThat(map.keys().length).isEqualTo(0);
    }


}