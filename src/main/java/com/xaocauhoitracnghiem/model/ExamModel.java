package com.xaocauhoitracnghiem.model;

import java.util.ArrayList;
import java.util.List;

public class ExamModel {
	public List<QuestionGroupModel> groupList = new ArrayList<QuestionGroupModel>();
	
	// exam information
	public String tenSo_PhongGD; // tên sở / phòng giáo dục
	public List<QuestionGroupModel> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<QuestionGroupModel> groupList) {
		this.groupList = groupList;
	}
	public String getTenSo_PhongGD() {
		return tenSo_PhongGD;
	}
	public void setTenSo_PhongGD(String tenSo_PhongGD) {
		this.tenSo_PhongGD = tenSo_PhongGD;
	}
	public String getKiKiemTra() {
		return kiKiemTra;
	}
	public void setKiKiemTra(String kiKiemTra) {
		this.kiKiemTra = kiKiemTra;
	}
	public String getHocKy() {
		return hocKy;
	}
	public void setHocKy(String hocKy) {
		this.hocKy = hocKy;
	}
	public String getNamHoc() {
		return namHoc;
	}
	public void setNamHoc(String namHoc) {
		this.namHoc = namHoc;
	}
	public String getMonHoc() {
		return monHoc;
	}
	public void setMonHoc(String monHoc) {
		this.monHoc = monHoc;
	}
	public String getThoiGianLamBai() {
		return thoiGianLamBai;
	}
	public void setThoiGianLamBai(String thoiGianLamBai) {
		this.thoiGianLamBai = thoiGianLamBai;
	}
	
	public String kiKiemTra; // kì kiểm tra
	public String hocKy;
	public String namHoc; 
	public String monHoc; // mon hoc
	public String thoiGianLamBai; // thoi gian lam bai
}
