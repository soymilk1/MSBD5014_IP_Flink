package com.hkust.bdt.msbd5014.map;

import com.hkust.bdt.msbd5014.bean.Customer;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

public class MyCustomerFlatMapFunction implements FlatMapFunction<String, Customer> {


    @Override
    public void flatMap(String value, Collector<Customer> out) throws Exception {
        String[] split = value.split("\\|");
        if ("AUTOMOBILE".equals(split[6])) {
            out.collect(new Customer(split[0], split[6]));
        }
    }
}
