package com.hkust.bdt.msbd5014.process;

import com.hkust.bdt.msbd5014.bean.Customer;
import com.hkust.bdt.msbd5014.bean.Orders;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.co.CoProcessFunction;
import org.apache.flink.util.Collector;

/**
 * Join Customer with Orders
 * A customer could have multiple orders but an order must belong to exactly 1 customer
 * 1 to M Join
 * Customer[c_custkey, c_mktsegment]
 * Orders[o_orderkey, o_custkey, o_orderdate, o_sp]
 * Output: Tuple(custkey, orderkey, mktsegment, orderdate, sp)
 */
public class MyOrdersCoProcessFunction extends CoProcessFunction<Customer, Orders, Tuple5<String, String, String, String, String>> {

    private ValueState<Customer> customerValueState;
    private ListState<Orders> ordersListState;

    @Override
    public void open(Configuration parameters) throws Exception {
        customerValueState = getRuntimeContext()
                .getState(new ValueStateDescriptor<Customer>("customer_value_state", Customer.class));
        ordersListState = getRuntimeContext()
                .getListState(new ListStateDescriptor<Orders>("orders_list_state", Orders.class));
    }

    @Override
    public void processElement1(Customer value, CoProcessFunction<Customer, Orders, Tuple5<String, String, String, String, String>>.Context ctx, Collector<Tuple5<String, String, String, String, String>> out) throws Exception {
        customerValueState.update(value);
        for (Orders order : ordersListState.get()) {
            Tuple5<String, String, String, String, String> result =
                    new Tuple5<>(value.c_custkey, order.o_orderkey, value.c_mktsegment, order.o_orderdate, order.o_sp);
            out.collect(result);
        }
    }

    @Override
    public void processElement2(Orders value, CoProcessFunction<Customer, Orders, Tuple5<String, String, String, String, String>>.Context ctx, Collector<Tuple5<String, String, String, String, String>> out) throws Exception {
        ordersListState.add(value);
        Customer customer = customerValueState.value();
        if (customer != null) {
            Tuple5<String, String, String, String, String> result =
                    new Tuple5<>(customer.c_custkey, value.o_orderkey, customer.c_mktsegment, value.o_orderdate, value.o_sp);
            out.collect(result);
        }
    }
}
