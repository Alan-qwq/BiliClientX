package com.Alan.BiliClientX.model;

public class CoinLog {
    public String time;
    public int delta;
    public String reason;

    public CoinLog(String time, int delta, String reason) {
        this.time = time;
        this.delta = delta;
        this.reason = reason;
    }
}

