package com.hkust.bdt.msbd5014.bean;


public class Lineitem {
    public String l_orderkey;
    public double l_ep;
    public double l_dis;
    public String l_shipdate;

    public Lineitem() {
    }

    public Lineitem(String l_orderkey, double l_ep, double l_dis, String l_shipdate) {
        this.l_orderkey = l_orderkey;
        this.l_ep = l_ep;
        this.l_dis = l_dis;
        this.l_shipdate = l_shipdate;
    }

    public String getL_orderkey() {
        return l_orderkey;
    }

    public void setL_orderkey(String l_orderkey) {
        this.l_orderkey = l_orderkey;
    }

    public double getL_ep() {
        return l_ep;
    }

    public void setL_ep(double l_ep) {
        this.l_ep = l_ep;
    }

    public double getL_dis() {
        return l_dis;
    }

    public void setL_dis(double l_dis) {
        this.l_dis = l_dis;
    }

    public String getL_shipdate() {
        return l_shipdate;
    }

    public void setL_shipdate(String l_shipdate) {
        this.l_shipdate = l_shipdate;
    }

    @Override
    public String toString() {
        return "Lineitem{" +
                "l_orderkey='" + l_orderkey + '\'' +
                ", l_ep=" + l_ep +
                ", l_dis=" + l_dis +
                ", l_shipdate='" + l_shipdate + '\'' +
                '}';
    }
}
