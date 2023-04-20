package com.carparking.application.ultis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GetDateTimeCommon {
	private static Date dateTime = new Date();
	private static SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	public static String getDateStartToParams(String tuNgay) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateTime);
		String fromDate = null;
		if (tuNgay == null || tuNgay.equals("")) {
			cal.add(Calendar.DATE, -7);
			String dateNVSL = formattedDate.format(cal.getTime());
			Date tuNgayQuery = formattedDate.parse(dateNVSL);
			fromDate = formatter.format(tuNgayQuery);
		} else {
			fromDate = tuNgay.concat(" 00:00:00");
		}
		return fromDate;
	};

	public static String getDateEndToParams(String denNgay) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateTime);
		String toDate;
		if (denNgay == null || denNgay.equals("")) {
			cal.add(Calendar.DATE, 14);
			String dateNVSF = formattedDate.format(cal.getTime());
			Date denNgayQuery = formattedDate.parse(dateNVSF);
			toDate = formatter.format(denNgayQuery);
		} else {
			toDate = denNgay.concat(" 23:59:59");
		}
		return toDate;
	};
}
