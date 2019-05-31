package com.bjut.ssh.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Cbtbsz {
    private String graphNo;
    private String graphName;
    private String graphTheme;
    private String graphData1;
    private String dataType1;
    private String dataFunction1;
    private String graphData2;
    private String dataType2;
    private String dataFunction2;
    private String graphData3;
    private String dataType3;
    private String dataFunction3;
    private Integer setNo;
    private String showStatus;

    @Id
    @Column(name = "GraphNo")
    public String getGraphNo() {
        return graphNo;
    }

    public void setGraphNo(String graphNo) {
        this.graphNo = graphNo;
    }

    @Basic
    @Column(name = "GraphName")
    public String getGraphName() {
        return graphName;
    }

    public void setGraphName(String graphName) {
        this.graphName = graphName;
    }

    @Basic
    @Column(name = "GraphTheme")
    public String getGraphTheme() {
        return graphTheme;
    }

    public void setGraphTheme(String graphTheme) {
        this.graphTheme = graphTheme;
    }

    @Basic
    @Column(name = "GraphData1")
    public String getGraphData1() {
        return graphData1;
    }

    public void setGraphData1(String graphData1) {
        this.graphData1 = graphData1;
    }

    @Basic
    @Column(name = "DataType1")
    public String getDataType1() {
        return dataType1;
    }

    public void setDataType1(String dataType1) {
        this.dataType1 = dataType1;
    }

    @Basic
    @Column(name = "DataFunction1")
    public String getDataFunction1() {
        return dataFunction1;
    }

    public void setDataFunction1(String dataFunction1) {
        this.dataFunction1 = dataFunction1;
    }

    @Basic
    @Column(name = "GraphData2")
    public String getGraphData2() {
        return graphData2;
    }

    public void setGraphData2(String graphData2) {
        this.graphData2 = graphData2;
    }

    @Basic
    @Column(name = "DataType2")
    public String getDataType2() {
        return dataType2;
    }

    public void setDataType2(String dataType2) {
        this.dataType2 = dataType2;
    }

    @Basic
    @Column(name = "DataFunction2")
    public String getDataFunction2() {
        return dataFunction2;
    }

    public void setDataFunction2(String dataFunction2) {
        this.dataFunction2 = dataFunction2;
    }

    @Basic
    @Column(name = "GraphData3")
    public String getGraphData3() {
        return graphData3;
    }

    public void setGraphData3(String graphData3) {
        this.graphData3 = graphData3;
    }

    @Basic
    @Column(name = "DataType3")
    public String getDataType3() {
        return dataType3;
    }

    public void setDataType3(String dataType3) {
        this.dataType3 = dataType3;
    }

    @Basic
    @Column(name = "DataFunction3")
    public String getDataFunction3() {
        return dataFunction3;
    }

    public void setDataFunction3(String dataFunction3) {
        this.dataFunction3 = dataFunction3;
    }

    @Basic
    @Column(name = "SetNo")
    public Integer getSetNo() {
        return setNo;
    }

    public void setSetNo(Integer setNo) {
        this.setNo = setNo;
    }

    @Basic
    @Column(name = "ShowStatus")
    public String getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(String showStatus) {
        this.showStatus = showStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cbtbsz cbtbsz = (Cbtbsz) o;

        if (graphNo != null ? !graphNo.equals(cbtbsz.graphNo) : cbtbsz.graphNo != null) return false;
        if (graphName != null ? !graphName.equals(cbtbsz.graphName) : cbtbsz.graphName != null) return false;
        if (graphTheme != null ? !graphTheme.equals(cbtbsz.graphTheme) : cbtbsz.graphTheme != null) return false;
        if (graphData1 != null ? !graphData1.equals(cbtbsz.graphData1) : cbtbsz.graphData1 != null) return false;
        if (dataType1 != null ? !dataType1.equals(cbtbsz.dataType1) : cbtbsz.dataType1 != null) return false;
        if (dataFunction1 != null ? !dataFunction1.equals(cbtbsz.dataFunction1) : cbtbsz.dataFunction1 != null)
            return false;
        if (graphData2 != null ? !graphData2.equals(cbtbsz.graphData2) : cbtbsz.graphData2 != null) return false;
        if (dataType2 != null ? !dataType2.equals(cbtbsz.dataType2) : cbtbsz.dataType2 != null) return false;
        if (dataFunction2 != null ? !dataFunction2.equals(cbtbsz.dataFunction2) : cbtbsz.dataFunction2 != null)
            return false;
        if (graphData3 != null ? !graphData3.equals(cbtbsz.graphData3) : cbtbsz.graphData3 != null) return false;
        if (dataType3 != null ? !dataType3.equals(cbtbsz.dataType3) : cbtbsz.dataType3 != null) return false;
        if (dataFunction3 != null ? !dataFunction3.equals(cbtbsz.dataFunction3) : cbtbsz.dataFunction3 != null)
            return false;
        if (setNo != null ? !setNo.equals(cbtbsz.setNo) : cbtbsz.setNo != null) return false;
        if (showStatus != null ? !showStatus.equals(cbtbsz.showStatus) : cbtbsz.showStatus != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = graphNo != null ? graphNo.hashCode() : 0;
        result = 31 * result + (graphName != null ? graphName.hashCode() : 0);
        result = 31 * result + (graphTheme != null ? graphTheme.hashCode() : 0);
        result = 31 * result + (graphData1 != null ? graphData1.hashCode() : 0);
        result = 31 * result + (dataType1 != null ? dataType1.hashCode() : 0);
        result = 31 * result + (dataFunction1 != null ? dataFunction1.hashCode() : 0);
        result = 31 * result + (graphData2 != null ? graphData2.hashCode() : 0);
        result = 31 * result + (dataType2 != null ? dataType2.hashCode() : 0);
        result = 31 * result + (dataFunction2 != null ? dataFunction2.hashCode() : 0);
        result = 31 * result + (graphData3 != null ? graphData3.hashCode() : 0);
        result = 31 * result + (dataType3 != null ? dataType3.hashCode() : 0);
        result = 31 * result + (dataFunction3 != null ? dataFunction3.hashCode() : 0);
        result = 31 * result + (setNo != null ? setNo.hashCode() : 0);
        result = 31 * result + (showStatus != null ? showStatus.hashCode() : 0);
        return result;
    }
}
