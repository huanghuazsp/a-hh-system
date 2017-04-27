package com.hh.system.service.impl;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hh.system.bean.SystemFile;
import com.hh.system.service.inf.IFileOper;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.FileUtil;
import com.hh.system.util.LogUtil;
import com.hh.system.util.StaticVar;
import com.hh.system.util.dto.PageRange;
import com.hh.system.util.dto.PagingData;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.dto.ParamInf;
import com.hh.system.util.request.Request;
import com.hh.usersystem.LoginUserServiceInf;

@Service
public class SystemFileService extends BaseService<SystemFile> {

	@Autowired
	private LoginUserServiceInf loginUserService;

	@Override
	public PagingData<SystemFile> queryPagingData(SystemFile entity,
			PageRange pageRange) {

		ParamInf paramInf = ParamFactory.getParamHb()
				.like("text", entity.getText())
				.is("createUser", loginUserService.findUserId())
				.orderDesc(StaticVar.order);
		if (Check.isNoEmpty(entity.getFileType())) {
			paramInf.in("fileType", Convert.strToList(entity.getFileType()));
		}
		PagingData<SystemFile> pageList = queryPagingData(pageRange, paramInf);
		for (SystemFile systemFile : pageList.getItems()) {
			systemFile.setBaseHttpFilePath("/"
					+ StaticVar.filebasefolder);
		}
		return pageList;
	}

	@Override
	public void deleteByIds(List<String> deleteIds) {
		List<SystemFile> systemFiles = queryListByIds(deleteIds);
		super.deleteByIds(deleteIds);
		for (SystemFile systemFile : systemFiles) {
			FileUtil.delFile(StaticVar.filepath + systemFile.getPath());
		}
	}

	public void showAllFiles(File dir) {
		String filePath = StaticVar.filepath.replaceAll("//", "/");
		File[] fs = dir.listFiles();
		if (fs.length == 0) {
			dir.delete();
		}
		for (int i = 0; i < fs.length; i++) {
			if (fs[i].isDirectory()) {
				try {
					showAllFiles(fs[i]);
				} catch (Exception e) {
					LogUtil.error("遍历附件文件夹出错！" + e.getClass().getName() + "："
							+ e.getMessage());
				}
			} else {
				String path = fs[i].getAbsolutePath().replaceAll("\\\\", "/");
				String path2 = ("/" + path.replaceAll("\\\\", "/")).replace(
						"/" + filePath, "").replace(filePath, "");
				int count = findCount(ParamFactory.getParamHb().is("path",
						"/" + path2));
				if (count == 0) {
					String newpath = StaticVar.deletefilepath + path2;
					FileUtil.moveFile(path, newpath);
				}
			}
		}
	}

	@Transactional
	public void fileCopyDeleteTask() {

		int count = dao.findCount(SystemFile.class, ParamFactory.getParamHb());
		for (int i = 0; i < count; i = i + 1000) {
			List<SystemFile> systemFileList = dao.queryList(SystemFile.class,
					ParamFactory.getParamHb(), new PageRange(i, 1000));
			for (SystemFile systemFile : systemFileList) {
				File file = new File(StaticVar.filepath + systemFile.getPath());
				if (!file.exists()) {
					systemFile.setStatus(2);
				} else {
					IFileOper fileOper = StaticVar.fileOperMap.get(systemFile
							.getType());
					if (fileOper != null) {
						fileOper.fileOper(systemFile);
					}
				}
				if (systemFile.getStatus() == 1
						|| systemFile.getStatus() == 2) {
					dao.updateEntity(systemFile);
				}
			}
		}

		String filePath = StaticVar.filepath.replaceAll("//", "/");
		File file = new File(filePath);
		showAllFiles(file);
		/*
		 * for (String path : allFilePathList) { String path2 = path.replaceAll(
		 * "\\\\", "/").replace(filePath, ""); String[] pathArr =
		 * path2.split("/"); String[] tableNames = pathArr[1].split("#"); String
		 * column = pathArr[2];
		 * 
		 * boolean as = true; for (String tableName : tableNames) { try { if
		 * (!"default_table".equals(tableName)) { String sql =
		 * "select count(*) from " + tableName + " where " + column + " like " +
		 * "'%" + pathArr[pathArr.length - 1] + "%'"; BigInteger count =
		 * (BigInteger) dao.findObjectBySql(sql); if (count.intValue() > 0) { as
		 * = false; break; } } else { as = false;
		 * LogUtil.info("过滤（default_table）。。。。"); break; } } catch (Exception e)
		 * { LogUtil.error("表名：" + tableName + "；字段名：" + column + "----查询失败！！");
		 * }
		 * 
		 * }
		 * 
		 * if (as) { String createBasePath = StaticVar.filepath + "/deletebak/";
		 * List<String> createFileList = new ArrayList<String>();
		 * createFileList.add(createBasePath);
		 * 
		 * for (int i = 0; i < pathArr.length - 1; i++) { createBasePath += "/"
		 * + pathArr[i]; createFileList.add(createBasePath); }
		 * FileUtil.isExist(createFileList); String newpath = StaticVar.filepath
		 * + "/deletebak/" + path2; FileUtil.moveFile(path, newpath);
		 * LogUtil.info(path + "-移动->" + newpath); }
		 * 
		 * }
		 */

	}

}
