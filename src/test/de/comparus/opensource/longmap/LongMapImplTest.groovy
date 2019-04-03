package de.comparus.opensource.longmap

import org.junit.Before
import org.junit.Test

class LongMapImplTest {

    LongMap<String> map
    
    @Before
    public void setUp() throws Exception {
       map = new LongMapImpl<>();
    }

    @Test
    public void givenEmptyMap_whenPutValue_thenContainsOneElement() {
        // arrange
        String testValue = "TestValue";
        Long key = 100;
        // act
        String actualValue = map.put(key, testValue);
        // asset
        assertThat(map.size()).is(1);
        assertThat(map.size()).is(1);

    }
}
