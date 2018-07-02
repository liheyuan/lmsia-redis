package com.coder4.sbmvt.redis;

import com.coder4.sbmvt.redis.transfomer.value.JsonCacheValueTransformer;

/**
 * @author coder4
 */
public class TestValueTransformer extends JsonCacheValueTransformer<TestValue> {

    public TestValueTransformer() {
        super(TestValue.class);
    }
}