package com.hkust.bdt.msbd5014.bean;

/**
 * Object for Orders table
 * Table structure: o_orderkey, o_custkey, o_orderdate, o_sp
 */
public class Orders {
    public String o_orderkey;
    public String o_custkey;
    public String o_orderdate;
    public String o_sp;

    public Orders() {
    }

    public Orders(String o_orderkey, String o_custkey, String o_orderdate, String o_sp) {
        this.o_orderkey = o_orderkey;
        this.o_custkey = o_custkey;
        this.o_orderdate = o_orderdate;
        this.o_sp = o_sp;
    }

    public String getO_orderkey() {
        return o_orderkey;
    }

    public void setO_orderkey(String o_orderkey) {
        this.o_orderkey = o_orderkey;
    }

    public String getO_custkey() {
        return o_custkey;
    }

    public void setO_custkey(String o_custkey) {
        this.o_custkey = o_custkey;
    }

    public String getO_orderdate() {
        return o_orderdate;
    }

    public void setO_orderdate(String o_orderdate) {
        this.o_orderdate = o_orderdate;
    }

    public String getO_sp() {
        return o_sp;
    }

    public void setO_sp(String o_sp) {
        this.o_sp = o_sp;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "o_orderkey='" + o_orderkey + '\'' +
                ", o_custkey='" + o_custkey + '\'' +
                ", o_orderdate='" + o_orderdate + '\'' +
                ", o_sp='" + o_sp + '\'' +
                '}';
    }
}
