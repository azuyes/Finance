package com.bjut.ssh.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Title: Lspass
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/4/11 10:01
 * @Version: 1.0
 */
@Entity
public class Lspass {
    private String userNo;
    private String userPass;
    private String userName;
    private String userId;
    private String userNote;
    private String department;
    private Short fSykm;
    private Short fSybb;
    private String functionAuthority;

    @Id
    @Column(name = "UserNo")
    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    @Basic
    @Column(name = "UserPass")
    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    @Basic
    @Column(name = "UserName")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "UserID")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "UserNote")
    public String getUserNote() {
        return userNote;
    }

    public void setUserNote(String userNote) {
        this.userNote = userNote;
    }

    @Basic
    @Column(name = "Department")
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Basic
    @Column(name = "F_SYKM")
    public Short getfSykm() {
        return fSykm;
    }

    public void setfSykm(Short fSykm) {
        this.fSykm = fSykm;
    }

    @Basic
    @Column(name = "F_SYBB")
    public Short getfSybb() {
        return fSybb;
    }

    public void setfSybb(Short fSybb) {
        this.fSybb = fSybb;
    }

    @Basic
    @Column(name = "FunctionAuthority")
    public String getFunctionAuthority() {
        return functionAuthority;
    }

    public void setFunctionAuthority(String functionAuthority) {
        this.functionAuthority = functionAuthority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lspass lspass = (Lspass) o;

        if (userNo != null ? !userNo.equals(lspass.userNo) : lspass.userNo != null) return false;
        if (userPass != null ? !userPass.equals(lspass.userPass) : lspass.userPass != null) return false;
        if (userName != null ? !userName.equals(lspass.userName) : lspass.userName != null) return false;
        if (userId != null ? !userId.equals(lspass.userId) : lspass.userId != null) return false;
        if (userNote != null ? !userNote.equals(lspass.userNote) : lspass.userNote != null) return false;
        if (department != null ? !department.equals(lspass.department) : lspass.department != null) return false;
        if (fSykm != null ? !fSykm.equals(lspass.fSykm) : lspass.fSykm != null) return false;
        if (fSybb != null ? !fSybb.equals(lspass.fSybb) : lspass.fSybb != null) return false;
        if (functionAuthority != null ? !functionAuthority.equals(lspass.functionAuthority) : lspass.functionAuthority != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userNo != null ? userNo.hashCode() : 0;
        result = 31 * result + (userPass != null ? userPass.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (userNote != null ? userNote.hashCode() : 0);
        result = 31 * result + (department != null ? department.hashCode() : 0);
        result = 31 * result + (fSykm != null ? fSykm.hashCode() : 0);
        result = 31 * result + (fSybb != null ? fSybb.hashCode() : 0);
        result = 31 * result + (functionAuthority != null ? functionAuthority.hashCode() : 0);
        return result;
    }
}
