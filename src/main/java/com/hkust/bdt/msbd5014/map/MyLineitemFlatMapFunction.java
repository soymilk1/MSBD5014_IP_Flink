package com.hkust.bdt.msbd5014.map;

import com.hkust.bdt.msbd5014.bean.Lineitem;
import com.hkust.bdt.msbd5014.bean.Orders;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyLineitemFlatMapFunction implements FlatMapFunction<String, Lineitem> {

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void flatMap(String value, Collector<Lineitem> out) throws Exception {
        String[] split = value.split("\\|");
        Date limitDate = sdf.parse("1995-03-13");
        Date shipDate = sdf.parse(split[10]);
        if (shipDate.compareTo(limitDate) < 0) {
            // orderDate < limitDate
            out.collect(new Lineitem(split[0], Double.parseDouble(split[5]), Double.parseDouble(split[6]), split[10]));
        }
    }
}
