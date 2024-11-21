package com.hkust.bdt.msbd5014.process;

import com.hkust.bdt.msbd5014.bean.Customer;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

public class MyCustomerProcessFunction extends KeyedProcessFunction<String, Customer, Customer> {
    private ListState<Customer> aliveTuples;

    @Override
    public void open(Configuration parameters) throws Exception {
        ListStateDescriptor<Customer> stateDescriptor = new ListStateDescriptor<>("customerAliveTuples", Customer.class);
        aliveTuples = getRuntimeContext().getListState(stateDescriptor);
    }

    private boolean isValid(Customer c) {
        return "AUTOMOBILE".equals(c.c_mktsegment);
    }

    @Override
    public void processElement(Customer value, KeyedProcessFunction<String, Customer, Customer>.Context ctx, Collector<Customer> out) throws Exception {
        if (isValid(value)) {
            aliveTuples.add(value);
            System.out.println("Current aliveTuples state:");
            for (Customer customer : aliveTuples.get()) {
                System.out.println(customer);
            }
        }

        out.collect(value);
    }
}
