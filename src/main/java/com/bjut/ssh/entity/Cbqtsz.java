package com.bjut.ssh.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Cbqtsz {
    private String itemNo;
    private String itemName;
    private String itemData1;
    private String itemDataType1;
    private String itemDataFunction1;
    private String itemData2;
    private String itemDataType2;
    private String itemDataFunction2;
    private String itemType;

    @Id
    @Column(name = "ItemNo")
    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    @Basic
    @Column(name = "ItemName")
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Basic
    @Column(name = "ItemData1")
    public String getItemData1() {
        return itemData1;
    }

    public void setItemData1(String itemData1) {
        this.itemData1 = itemData1;
    }

    @Basic
    @Column(name = "ItemDataType1")
    public String getItemDataType1() {
        return itemDataType1;
    }

    public void setItemDataType1(String itemDataType1) {
        this.itemDataType1 = itemDataType1;
    }

    @Basic
    @Column(name = "ItemDataFunction1")
    public String getItemDataFunction1() {
        return itemDataFunction1;
    }

    public void setItemDataFunction1(String itemDataFunction1) {
        this.itemDataFunction1 = itemDataFunction1;
    }

    @Basic
    @Column(name = "ItemData2")
    public String getItemData2() {
        return itemData2;
    }

    public void setItemData2(String itemData2) {
        this.itemData2 = itemData2;
    }

    @Basic
    @Column(name = "ItemDataType2")
    public String getItemDataType2() {
        return itemDataType2;
    }

    public void setItemDataType2(String itemDataType2) {
        this.itemDataType2 = itemDataType2;
    }

    @Basic
    @Column(name = "ItemDataFunction2")
    public String getItemDataFunction2() {
        return itemDataFunction2;
    }

    public void setItemDataFunction2(String itemDataFunction2) {
        this.itemDataFunction2 = itemDataFunction2;
    }

    @Basic
    @Column(name = "ItemType")
    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cbqtsz cbqtsz = (Cbqtsz) o;

        if (itemNo != null ? !itemNo.equals(cbqtsz.itemNo) : cbqtsz.itemNo != null) return false;
        if (itemName != null ? !itemName.equals(cbqtsz.itemName) : cbqtsz.itemName != null) return false;
        if (itemData1 != null ? !itemData1.equals(cbqtsz.itemData1) : cbqtsz.itemData1 != null) return false;
        if (itemDataType1 != null ? !itemDataType1.equals(cbqtsz.itemDataType1) : cbqtsz.itemDataType1 != null)
            return false;
        if (itemDataFunction1 != null ? !itemDataFunction1.equals(cbqtsz.itemDataFunction1) : cbqtsz.itemDataFunction1 != null)
            return false;
        if (itemData2 != null ? !itemData2.equals(cbqtsz.itemData2) : cbqtsz.itemData2 != null) return false;
        if (itemDataType2 != null ? !itemDataType2.equals(cbqtsz.itemDataType2) : cbqtsz.itemDataType2 != null)
            return false;
        if (itemDataFunction2 != null ? !itemDataFunction2.equals(cbqtsz.itemDataFunction2) : cbqtsz.itemDataFunction2 != null)
            return false;
        if (itemType != null ? !itemType.equals(cbqtsz.itemType) : cbqtsz.itemType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = itemNo != null ? itemNo.hashCode() : 0;
        result = 31 * result + (itemName != null ? itemName.hashCode() : 0);
        result = 31 * result + (itemData1 != null ? itemData1.hashCode() : 0);
        result = 31 * result + (itemDataType1 != null ? itemDataType1.hashCode() : 0);
        result = 31 * result + (itemDataFunction1 != null ? itemDataFunction1.hashCode() : 0);
        result = 31 * result + (itemData2 != null ? itemData2.hashCode() : 0);
        result = 31 * result + (itemDataType2 != null ? itemDataType2.hashCode() : 0);
        result = 31 * result + (itemDataFunction2 != null ? itemDataFunction2.hashCode() : 0);
        result = 31 * result + (itemType != null ? itemType.hashCode() : 0);
        return result;
    }
}
