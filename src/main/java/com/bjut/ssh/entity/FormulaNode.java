package com.bjut.ssh.entity;
import java.util.List;

public class FormulaNode {
    private int start;//公式原子开始位置
    private int end;//公式原子结束位置（包括end）
    private String nodeStr;//公式原子字符串
    private List<Object> resultList;//公式原子查询结果链表
    private String[][] resultArray;//公式原子查询结果数组
    public FormulaNode() {
        super();
        // TODO Auto-generated constructor stub
    }
    public FormulaNode(int start, int end, String nodeStr) {
        super();
        this.start = start;
        this.end = end;
        this.nodeStr = nodeStr;
    }
    public int getStart() {
        return start;
    }
    public void setStart(int start) {
        this.start = start;
    }
    public int getEnd() {
        return end;
    }
    public void setEnd(int end) {
        this.end = end;
    }
    public String getNodeStr() {
        return nodeStr;
    }
    public void setNodeStr(String nodeStr) {
        this.nodeStr = nodeStr;
    }
    public List<Object> getResultList() {
        return resultList;
    }
    public void setResultList(List<Object> resultList) {
        this.resultList = resultList;
    }

    public String[][] getResultArray() {
        return resultArray;
    }

    public void setResultArray(String[][] resultArray) {
        this.resultArray = resultArray;
    }
}
