package com.hkust.bdt.msbd5014.process;

import com.hkust.bdt.msbd5014.bean.Customer;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.api.java.tuple.Tuple8;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

public class MyAggregationProcessFunction extends KeyedProcessFunction<String, Tuple8<String, String, String, String, String, Double, Double, String>, Tuple4<String, String, String, Double>> {
    private ValueState<Double> revenueState;

    @Override
    public void open(Configuration parameters) throws Exception {
        revenueState = getRuntimeContext()
                .getState(new ValueStateDescriptor<Double>("revenue_value_state", Double.class));
    }

    @Override
    public void processElement(Tuple8<String, String, String, String, String, Double, Double, String> value, KeyedProcessFunction<String, Tuple8<String, String, String, String, String, Double, Double, String>, Tuple4<String, String, String, Double>>.Context ctx, Collector<Tuple4<String, String, String, Double>> out) throws Exception {
        // revenue = l_extendedprice * (1 - l_discount)
        double currentRevenue = value.f5 * (1 - value.f6);

        Double accumulatedRevenue = revenueState.value();
        if (accumulatedRevenue == null) {
            accumulatedRevenue = 0.0;
        }
        accumulatedRevenue += currentRevenue;
        revenueState.update(accumulatedRevenue);

        out.collect(new Tuple4<>(value.f1, value.f3, value.f4, accumulatedRevenue));
    }
}
