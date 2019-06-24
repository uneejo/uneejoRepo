package com.unicompany.hr.circumstance.to;

import com.unicompany.common.annotation.Dataset;
import com.unicompany.common.to.BaseBean;

@Dataset(name="dsHoliday")
public class HolidayBean extends BaseBean{

	private String basicDay;
	private String holidayName;
	private String note;
	private String holidayType;

	public String getBasicDay() {
		return basicDay;
	}
	public void setBasicDay(String basicDay) {
		this.basicDay = basicDay;
	}
	public String getHolidayName() {
		return holidayName;
	}
	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getHolidayType() {
		return holidayType;
	}
	public void setHolidayType(String holidayType) {
		this.holidayType = holidayType;
	}
}
