package com.scheduling.app.constants;

public class ErrorCodes {

	public static final int GENERIC_ERROR = 1000;
	public static final String GENERIC_ERROR_MESSAGE = "Application error occured!!";

	public static final int OK_STATUS = 0;
	public static final String OK_STATUS_MESSAGE = "Ok";

	public static final int GENERIC_NOT_FOUND = 1001;
	public static final String GENERIC_NOT_FOUND_MESSAGE = "Resource not found!!";

	public static final int NULL_OR_EMPTY = 1002;
	public static final String NULL_OR_EMPTY_MESSAGE = "Null, empty or invalid field value for ";

	public static final int DATE_NOT_PARSABLE = 1003;
	public static final String DATE_NOT_PARSABLE_MESSAGE = "Unable to parse date ";

	public static final int TO_DATE_BEFORE_FROM_DATE = 1004;
	public static final String TO_DATE_BEFORE_FROM_DATE_MESSAGE = "To Date cannot be before from date";

	public static final int CLIENT_ERROR = 404;
}
