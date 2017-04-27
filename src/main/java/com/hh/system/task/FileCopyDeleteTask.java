package com.hh.system.task;

import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.hh.system.service.impl.BeanFactoryHelper;
import com.hh.system.service.impl.SystemFileService;

public class FileCopyDeleteTask extends TimerTask {
	private SystemFileService service = BeanFactoryHelper.getBeanFactory().getBean(SystemFileService.class);

	@Override
	public void run() {
		service.fileCopyDeleteTask();
	}

}
