package com.java.threadpool;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
@Slf4j
public class TimeControlService {

    private static final ConcurrentHashMap<String, Double> TOTAL_EXECUTION_TIME = new ConcurrentHashMap<String, Double>();

    public void addTotalExecutionTime(long time) {
        var totalExecutionTime = TOTAL_EXECUTION_TIME.get("time");
        if (totalExecutionTime != null) {
            totalExecutionTime = totalExecutionTime + time;
        } else {
            totalExecutionTime = Double.parseDouble(time + "");
        }
        TOTAL_EXECUTION_TIME.put("time", totalExecutionTime/1000);
    }

    public Double getTotalExecutionTime() {
        return TOTAL_EXECUTION_TIME.get("time");
    }
}
