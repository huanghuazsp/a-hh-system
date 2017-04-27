package com.hh.system.util.base;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class BaseServletAction extends ActionSupport implements
		ServletResponseAware, ServletRequestAware, SessionAware {
	protected HttpServletRequest request;

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		request = arg0;
	}

	protected Map<String, Object> session;

	public void setSession(Map<String, Object> arg0) {
		session = arg0;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	protected HttpServletResponse response;

	public void setServletResponse(HttpServletResponse arg0) {
		response = arg0;
	}

	public HttpServletResponse getResponse() {
		return response;
	}
}
