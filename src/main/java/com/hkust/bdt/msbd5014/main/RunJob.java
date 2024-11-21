package com.hkust.bdt.msbd5014.main;

import com.hkust.bdt.msbd5014.bean.Customer;
import com.hkust.bdt.msbd5014.bean.Lineitem;
import com.hkust.bdt.msbd5014.bean.Orders;
import com.hkust.bdt.msbd5014.map.MyCustomerFlatMapFunction;
import com.hkust.bdt.msbd5014.map.MyLineitemFlatMapFunction;
import com.hkust.bdt.msbd5014.map.MyOrdersFlatMapFunction;
import com.hkust.bdt.msbd5014.process.MyLineitemCoProcessFunction;
import com.hkust.bdt.msbd5014.process.MyOrdersCoProcessFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import static com.hkust.bdt.msbd5014.util.FileSourceUtil.getSourceFromFile;

public class RunJob {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        // TODO 1.1: Read DS from Customer, Orders, Lineitem tables
        //          Customer table is the LEAF RELATION, so all tuples are alive
        DataStreamSource<String> customerFileDS = getSourceFromFile(env, "Customer");
        DataStreamSource<String> orderFileDS = getSourceFromFile(env, "Order");
        DataStreamSource<String> lineitemFileDS = getSourceFromFile(env, "Lineitem");
        SingleOutputStreamOperator<Customer> customerDS = customerFileDS.flatMap(new MyCustomerFlatMapFunction());
        SingleOutputStreamOperator<Orders> ordersDS = orderFileDS.flatMap(new MyOrdersFlatMapFunction());
        SingleOutputStreamOperator<Lineitem> lineitemDS = lineitemFileDS.flatMap(new MyLineitemFlatMapFunction());

//        customerDS.print("CUSTOMER DS====================");
//        ordersDS.print("ORDERS DS======================");
        // TODO
        customerDS
                .connect(ordersDS)
                .keyBy(c -> c.c_custkey, o -> o.o_custkey)
                .process(new MyOrdersCoProcessFunction())
//                .connect(lineitemDS)
//                .keyBy(t -> t.f1, l -> l.l_orderkey)
//                .process(new MyLineitemCoProcessFunction())
                .print();
        env.execute();
    }
}
