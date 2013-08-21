package org.openxava.demoapp.model.testcase;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.openxava.annotations.Editor;

@Entity
@Table(name="TEST_DateTime")
public class DateTimeTestCase {
    @TableGenerator(
    		name = "DateTimeTestCase", table = "ID_GEN",
    		pkColumnName = "GEN_NAME", pkColumnValue = "DateTimeTestCase",
            valueColumnName = "GEN_VAL", allocationSize = 1
    )  
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="DateTimeTestCase")  
    private Integer id;
    
    private Date defaultJavaDate;
    private Time defalutSqlTime;
    private Timestamp defaultSqlTimestamp;
    
    //BP: Use the customized Date/Time editor
    @Editor("TestApp_ToMinute_DateCalendar")
    private Date showMinuteJavaDate;
    
    @Editor("TestApp_ToMonth_DateCalendar")
    private java.sql.Date showMonthSqlDate;
    
    @Editor("TestApp_ToHour_DateTimeCalendar")
    private Timestamp showHourOnlyTimestamp;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDefaultJavaDate() {
		return defaultJavaDate;
	}

	public void setDefaultJavaDate(Date defaultJavaDate) {
		this.defaultJavaDate = defaultJavaDate;
	}

	public Time getDefalutSqlTime() {
		return defalutSqlTime;
	}

	public void setDefalutSqlTime(Time defalutSqlTime) {
		this.defalutSqlTime = defalutSqlTime;
	}

	public Timestamp getDefaultSqlTimestamp() {
		return defaultSqlTimestamp;
	}

	public void setDefaultSqlTimestamp(Timestamp defaultSqlTimestamp) {
		this.defaultSqlTimestamp = defaultSqlTimestamp;
	}

	public Date getShowMinuteJavaDate() {
		return showMinuteJavaDate;
	}

	public void setShowMinuteJavaDate(Date showMinuteJavaDate) {
		this.showMinuteJavaDate = showMinuteJavaDate;
	}

	public java.sql.Date getShowMonthSqlDate() {
		return showMonthSqlDate;
	}

	public void setShowMonthSqlDate(java.sql.Date showMonthSqlDate) {
		this.showMonthSqlDate = showMonthSqlDate;
	}

	public Timestamp getShowHourOnlyTimestamp() {
		return showHourOnlyTimestamp;
	}

	public void setShowHourOnlyTimestamp(Timestamp showHourOnlyTimestamp) {
		this.showHourOnlyTimestamp = showHourOnlyTimestamp;
	}
}
