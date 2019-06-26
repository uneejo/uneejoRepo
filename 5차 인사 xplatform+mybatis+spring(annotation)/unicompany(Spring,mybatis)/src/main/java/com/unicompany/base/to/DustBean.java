package com.unicompany.base.to;

import com.unicompany.common.annotation.Dataset;

@Dataset(name="gds_dust")
public class DustBean{
   String stationName,pmValue,pmGrade;

public String getStationName() {
	return stationName;
}

public void setStationName(String stationName) {
	this.stationName = stationName;
}

public String getPmValue() {
	return pmValue;
}

public void setPmValue(String pmValue) {
	this.pmValue = pmValue;
}

public String getPmGrade() {
	return pmGrade;
}

public void setPmGrade(String pmGrade) {
	this.pmGrade = pmGrade;
}

}