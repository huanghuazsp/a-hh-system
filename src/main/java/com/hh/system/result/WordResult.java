package com.hh.system.result;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.hh.system.inf.IFileAction;
import com.hh.system.util.date.DateFormat;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;

public class WordResult implements Result {

	public void execute(ActionInvocation invocation) throws Exception {
		IFileAction action = (IFileAction) invocation.getAction();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setHeader("Cash", "no cash");
		response.setContentType("application/msword");
		if (action.getBytes() != null) {
			response.setContentLength(action.getBytes().length);
		}

		String fileName = new String(
				(action.getTitle() + DateFormat.getDate()+".doc").getBytes("gbk"),
				"ISO8859-1");
		response.addHeader("Content-Disposition", "attachment;filename="
				+ fileName);

		OutputStream output = response.getOutputStream();
		output.write(action.getBytes());
		output.flush();
		output.close();
	}

}
