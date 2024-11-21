package com.hkust.bdt.msbd5014.bean;

/**
 * Object for Customer table
 * Table structure: c_custkey, c_mktsegment
 */
public class Customer {
    public String c_custkey;
    public String c_mktsegment;

    public Customer() {
    }

    public Customer(String c_custkey, String c_mktsegment) {
        this.c_custkey = c_custkey;
        this.c_mktsegment = c_mktsegment;
    }

    public String getC_custkey() {
        return c_custkey;
    }

    public void setC_custkey(String c_custkey) {
        this.c_custkey = c_custkey;
    }

    public String getC_mktsegment() {
        return c_mktsegment;
    }

    public void setC_mktsegment(String c_mktsegment) {
        this.c_mktsegment = c_mktsegment;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "c_custkey='" + c_custkey + '\'' +
                ", c_mktsegment='" + c_mktsegment + '\'' +
                '}';
    }
}
