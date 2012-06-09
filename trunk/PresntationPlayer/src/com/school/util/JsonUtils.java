package com.school.util;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

public class JsonUtils {
	public static final String PARAM_MESSAGE="message";
	public static final String PARAM_PRESENTATION="presentation";
	public static final String PARAM_PP_LIST="ppList";
	public static final String PARAM_PAGE_NO="pageNo";
	public static final String PARAM_HAS_RESULTS="hasResults";
	public static final String SUCCESS = "success", ERROR_MESSAGE = "errorMessage";
	public static Map<String, Object> successWithParams(Map<String, Object> params) {
		return getJsonWithParams(params, STATUS_SUCCESS);
	}

	public static Map<String, Object> failureWithParams(Map<String, Object> params) {
		return getJsonWithParams(params, STATUS_FAILURE);
	}

	public static Map<String, Object> successJson() {
		return getJson(STATUS_SUCCESS, null);
	}

	public static Map<String, Object> failureJson(String errorMessage) {
		return getJson(STATUS_FAILURE, errorMessage);
	}

	public static <T extends Object> Map<String, Object> successWithParameter(String paramName, T paramValue) {
		Map<String, Object> map = new TreeMap<>();
		map.put(paramName, paramValue);
		return getJsonWithParams(map, STATUS_SUCCESS);
	}

	public static Map<String, Object> failureJson() {
		return getJson(STATUS_FAILURE, null);
	}

	private static Map<String, Object> getJson(boolean statusValue, String errorMessage) {
		Map<String, Object> response = new TreeMap<>();
		response.put(SUCCESS, statusValue);
		if (StringUtils.isNotBlank(errorMessage)) {
			response.put(ERROR_MESSAGE, errorMessage);
		}
		return response;
	}

	public static Map<String, Object> getJsonWithParams(Map<String, Object> params, boolean statusValue) {
		params.put(SUCCESS, statusValue);
		return params;
	}

	
	private static final boolean STATUS_FAILURE = false, STATUS_SUCCESS = true;
}
