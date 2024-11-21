package com.hkust.bdt.msbd5014.process;

import com.hkust.bdt.msbd5014.bean.Lineitem;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.api.java.tuple.Tuple8;
import org.apache.flink.streaming.api.functions.co.CoProcessFunction;
import org.apache.flink.util.Collector;

/**
 * In1: Tuple5
 * In2: Lineitem
 * Out: Tuple8(custkey, orderkey, mktsegment, orderdate, sp, ep, disct, shipdate)
 */
public class MyLineitemCoProcessFunction extends CoProcessFunction<Tuple5<String, String, String, String, String>, Lineitem, Tuple8<String, String, String, String, String, Double, Double, String>> {

    @Override
    public void processElement1(Tuple5<String, String, String, String, String> value, CoProcessFunction<Tuple5<String, String, String, String, String>, Lineitem, Tuple8<String, String, String, String, String, Double, Double, String>>.Context ctx, Collector<Tuple8<String, String, String, String, String, Double, Double, String>> out) throws Exception {

    }

    @Override
    public void processElement2(Lineitem value, CoProcessFunction<Tuple5<String, String, String, String, String>, Lineitem, Tuple8<String, String, String, String, String, Double, Double, String>>.Context ctx, Collector<Tuple8<String, String, String, String, String, Double, Double, String>> out) throws Exception {

    }
}
