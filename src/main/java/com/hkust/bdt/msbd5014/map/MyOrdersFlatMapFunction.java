package com.hkust.bdt.msbd5014.map;

import com.hkust.bdt.msbd5014.bean.Orders;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MyOrdersFlatMapFunction implements FlatMapFunction<String, Orders> {

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void flatMap(String value, Collector<Orders> out) throws Exception {
        String[] split = value.split("\\|");
        Date limitDate = sdf.parse("1995-03-13");
        Date orderDate = sdf.parse(split[4]);
        if (orderDate.compareTo(limitDate) < 0) {
            // orderDate < limitDate
            out.collect(new Orders(split[0], split[1], split[4], split[7]));
        }
    }
}
