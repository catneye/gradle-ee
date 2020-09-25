package com.catneye.util;

import org.junit.Assert;
import org.junit.Test;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtilTest {
    LocalDate dat = LocalDate.now();
    Date date = new Date();

    Date dateNull = null;
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    ZoneId zone = ZoneId.of("Europe/Moscow");
    long millis = date.getTime();
    java.sql.Timestamp time = new java.sql.Timestamp(millis);
    String dateString = date.toString();
    GregorianCalendar gc = new GregorianCalendar();

    @Test
    public void asLocalDate() {
        java.sql.Date date2 = new java.sql.Date(millis);
        Assert.assertEquals(dateNull, DateUtil.asLocalDate(dateNull, zone));
        Assert.assertEquals(dat, DateUtil.asLocalDate(date2, zone));
    }

    /*
    Expected :2020-09-25 16:29:44.755
    Actual   :2020-09-25T16:29:44.755
    What is 'T' after 2020-09-25
     */
    @Test
    public void asLocalDateTime() {

        Assert.assertEquals(dateNull, DateUtil.asLocalDateTime(dateNull, zone));
        Assert.assertEquals(timestamp, DateUtil.asLocalDateTime(time, zone));
    }

    @Test
    public void asUtilDate() {
        Assert.assertEquals(dateNull, DateUtil.asUtilDate(dateNull, zone));
        Assert.assertEquals(date, DateUtil.asUtilDate(date, zone));
        Assert.assertEquals(date, DateUtil.asUtilDate(time, zone));
    }

    /*
    Expected :2020-09-25 17:13:14.735
    Actual   :2020-09-25T14:13:14.735Z
     */
    @Test
    public void asInstant() {
        Assert.assertEquals(dateNull, DateUtil.asInstant(dateNull));
        Assert.assertEquals(timestamp, DateUtil.asInstant(date));
    }

    @Test
    public void asZonedDateTime() {
        Assert.assertEquals(dateNull, DateUtil.asZonedDateTime(dateNull, zone));
        //Assert.assertEquals(zone, DateUtil.asZonedDateTime(date, zone));
    }

    @Test
    public void asUtilGregorianCalendar() {
        gc.setTime(date);
        Assert.assertEquals(gc, DateUtil.asUtilGregorianCalendar(date));
    }

    //This is desperation, don't take it lightly
    @Test
    public void asXMLGregorianCalendar() throws DatatypeConfigurationException {
        gc.setTimeInMillis(millis);
        Assert.assertEquals(dateNull, DateUtil.asXMLGregorianCalendar(dateNull));
        Assert.assertEquals(DatatypeFactory.newInstance().newXMLGregorianCalendar(gc), DateUtil.asXMLGregorianCalendar(date));
    }

    @Test
    public void determineDate() {
        Assert.assertEquals(dateNull, DateUtil.determineDate(""));
    }

    //What wrong?
    @Test
    public void parseDate() {
        Assert.assertEquals(date, DateUtil.parseDate(dateString));
    }
}