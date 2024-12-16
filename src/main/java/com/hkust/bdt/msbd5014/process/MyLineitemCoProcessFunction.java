package com.hkust.bdt.msbd5014.process;

import com.hkust.bdt.msbd5014.bean.Lineitem;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.api.java.tuple.Tuple8;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.co.CoProcessFunction;
import org.apache.flink.util.Collector;

/**
 * 多对多连接逻辑
 * 输入流1: Tuple5(custkey, orderkey, mktsegment, orderdate, sp)
 * 输入流2: Lineitem(l_orderkey, l_ep, l_dis, l_shipdate)
 * 输出流: Tuple8(custkey, orderkey, mktsegment, orderdate, sp, ep, disct, shipdate)
 */
public class MyLineitemCoProcessFunction extends CoProcessFunction<Tuple5<String, String, String, String, String>, Lineitem, Tuple8<String, String, String, String, String, Double, Double, String>> {

    private ListState<Tuple5> ordersState;
    private ListState<Lineitem> lineitemState;

    @Override
    public void open(Configuration parameters) throws Exception {
        ordersState = getRuntimeContext().getListState(
                new ListStateDescriptor<>("ordersState", Types.GENERIC(Tuple5.class)));
        lineitemState = getRuntimeContext().getListState(
                new ListStateDescriptor<>("lineitemState", Types.GENERIC(Lineitem.class)));
    }

    @Override
    public void processElement1(Tuple5<String, String, String, String, String> order,
                                Context ctx,
                                Collector<Tuple8<String, String, String, String, String, Double, Double, String>> out) throws Exception {
        // 将当前 order 加入状态
        ordersState.add(order);

        // 检查状态中是否存在匹配的 Lineitem 数据
        for (Lineitem lineitem : lineitemState.get()) {
            if (order.f1.equals(lineitem.l_orderkey)) {
                out.collect(new Tuple8<>(
                        order.f0, order.f1, order.f2, order.f3, order.f4,
                        lineitem.l_ep, lineitem.l_dis, lineitem.l_shipdate));
            }
        }
    }

    @Override
    public void processElement2(Lineitem lineitem,
                                Context ctx,
                                Collector<Tuple8<String, String, String, String, String, Double, Double, String>> out) throws Exception {
        // 将当前 Lineitem 加入状态
        lineitemState.add(lineitem);

        // 检查状态中是否存在匹配的 Order 数据
        for (Tuple5<String, String, String, String, String> order : ordersState.get()) {
            if (lineitem.l_orderkey.equals(order.f1)) {
                out.collect(new Tuple8<>(
                        order.f0, order.f1, order.f2, order.f3, order.f4,
                        lineitem.l_ep, lineitem.l_dis, lineitem.l_shipdate));
            }
        }
    }
}
