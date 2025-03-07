package com.zyw.blockchainverification.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
public class Result {
    private boolean equal;
    private List<Integer> blockNumbers;
    private long runningTime;
    private String reports;

    public Result(String report) {
        this.equal = true;
        this.blockNumbers = new ArrayList<>();
        this.runningTime = 0;
        this.reports = "运行"+report+"\n检验结果如下所示：\n";
    }

    public void addResult(int blockNumber) {
        blockNumbers.add(blockNumber);
    }

    public void refreshReports(String note){
        this.reports += note;
    }

    public boolean getEqual() {
        return this.equal;
    }

    @Override
    public String toString() {
        if (this.equal) {
            this.refreshReports("检验通过！\n");
        }
        else {
            this.refreshReports("检验不通过！\n");
            if (!this.blockNumbers.isEmpty()) {
                this.refreshReports("信息不一致的区块blockNumber如下：\n");
                for (Integer blockNumber : this.blockNumbers) {
                    this.refreshReports("blockNumber: "+String.valueOf(blockNumber)+"\n");
                }
            }
        }
        this.refreshReports("运行时间为: "+String.valueOf(runningTime)+"ms\n");
        this.refreshReports("检验结束！");
        return this.reports;
    }
}
