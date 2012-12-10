package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import play.modules.morphia.Model.MorphiaQuery;
import play.mvc.Controller;
import play.mvc.Util;
import utils.Utils;
/** 健康档案管理 */
public class HealthArchives extends Controller {
	public static final int PAGE_SIZE = 20;
	/** 管理入口 .*/
	public static void index(String keyword, String department, int page, int size) {
		List<models.Department> divisions = Secure.getDepartments();
		render(keyword, department, divisions);
	}
	
	/** 导出excel. */
	public static void report(String keyword, String department, String id) {
		String fileName = "健康档案-" + Secure.getAdmin().department.name + ".xls";
		File f = new File("./excels/" + fileName);
		models.HealthArchives data = models.HealthArchives.findById(id);
	    try {
	    	if (f.exists())
	    		f.createNewFile();
	    	int index = 0;
	        WritableWorkbook wbook = Workbook.createWorkbook(f);
	        WritableSheet ws = wbook.createSheet("体检报告", index);
	        Label label0_0 = new Label(0, 0, "单位");
	        ws.addCell(label0_0);
	        Label field0_0 = new Label(1, 0, data.department.name);
	        ws.addCell(field0_0);
	        
	        Label label0_1 = new Label(2, 0, "单位编码");
	        ws.addCell(label0_1);
	        Label field0_1 = new Label(3, 0, data.department.code);
	        ws.addCell(field0_1);
	        
	        Label label1_0 = new Label(0, 1, "姓名");
	        ws.addCell(label1_0);
	        Label field1_0 = new Label(1, 1, data.name);
	        ws.addCell(field1_0);
	        
	        Label label1_1 = new Label(2, 1, "体检编号");
	        ws.addCell(label1_1);
	        Label field1_1 = new Label(3, 1, data.code);
	        ws.addCell(field1_1);
	        
	        Label label2_0 = new Label(0, 2, "性别");
	        ws.addCell(label2_0);
	        Label field2_0 = new Label(1, 2, data.gender);
	        ws.addCell(field2_0);
	        
	        Label label2_1 = new Label(2, 2, "年龄");
	        ws.addCell(label2_1);
	        jxl.write.Number field2_1 = new jxl.write.Number(3, 2, data.age);
	        ws.addCell(field2_1);
	        
	        Label label3_0 = new Label(0, 3, "家庭地址");
	        ws.addCell(label3_0);
	        Label field3_0 = new Label(1, 3, data.home);
	        ws.addCell(field3_0);
	        
	        Label label3_1 = new Label(2, 3, "联系电话");
	        ws.addCell(label3_1);
	        Label field3_1 = new Label(4, 3, data.tel);
	        ws.addCell(field3_1);
	        
	        Label label4_0 = new Label(0, 4, "体检日期");
	        ws.addCell(label4_0);
	        Label field4_0 = new Label(4, 4, data.tel);
	        ws.addCell(field4_0);
	        
	        Label label4_1 = new Label(2, 4, "报告日期");
	        ws.addCell(label4_1);
	        Label field4_1 = new Label(4, 4, data.tel);
	        ws.addCell(field4_1);
	        
	        Label label5_0 = new Label(0, 5, "体检地址");
	        ws.addCell(label5_0);
	        Label field5_0 = new Label(1, 5, data.dealthAddress);
	        ws.addCell(field5_0);
	        
	        Label label6_0 = new Label(0, 6, "既往病史");
	        ws.addCell(label6_0);
	        Label field6_0 = new Label(1, 6, data.history);
	        ws.addCell(field6_0);
	        
	        for (models.Physical physical : data.physicals) {
	        	WritableSheet wsp = wbook.createSheet("检查项-" + physical.name, index);
	        	int contentIndex = 0;
	        	Label plabel0_0 = new Label(0, contentIndex, "检查项");
	        	wsp.addCell(plabel0_0);
		        Label pfield0_0 = new Label(1, contentIndex, physical.name);
		        wsp.addCell(pfield0_0);
		        contentIndex ++;
		        
		        Label plabel1_0 = new Label(0, contentIndex, "检查项内容");
		        wsp.addCell(plabel1_0);
		        contentIndex ++;
		        
		        for (models.PhysicalInfo info : physical.physicalInfo) {
			        Label pfield2_0 = new Label(0, contentIndex, info.content);
			        wsp.addCell(pfield2_0);
			        contentIndex ++;
		        }
		        
		        if (physical.results != null) {
			        Label plabel3_0 = new Label(0, contentIndex, "结果");
			        wsp.addCell(plabel3_0);
			        Label pfield3_0 = new Label(1, contentIndex, physical.results.result);
			        wsp.addCell(pfield3_0);
			        contentIndex ++;
			        Label plabel3_1 = new Label(0, contentIndex, "参考值");
			        wsp.addCell(plabel3_1);
			        Label pfield3_1 = new Label(1, contentIndex, physical.results.reference);
			        wsp.addCell(pfield3_1);
			        contentIndex ++;
			        Label plabel3_2 = new Label(0, contentIndex, "检查医生");
			        wsp.addCell(plabel3_2);
			        Label pfield3_2 = new Label(1, contentIndex, physical.results.doctor);
			        wsp.addCell(pfield3_2);
			        contentIndex ++;
		        }
		        
		        if (physical.treatment != null) {
			        Label plabel4_0 = new Label(0, contentIndex, "检查异常结果汇总");
			        wsp.addCell(plabel4_0);
			        Label pfield4_0 = new Label(1, contentIndex, physical.treatment.summary);
			        wsp.addCell(pfield4_0);
			        contentIndex ++;
			        Label plabel4_1 = new Label(0, contentIndex, "结论");
			        wsp.addCell(plabel4_1);
			        Label pfield4_1 = new Label(1, contentIndex, physical.treatment.conclusion);
			        wsp.addCell(pfield4_1);
			        contentIndex ++;
			        Label plabel4_2 = new Label(0, contentIndex, "治疗意见或建议");
			        wsp.addCell(plabel4_2);
			        Label pfield4_2 = new Label(1, contentIndex, physical.treatment.treatment);
			        wsp.addCell(pfield4_2);
			        contentIndex ++;
			        Label plabel4_3 = new Label(0, contentIndex, "健康咨询地址");
			        wsp.addCell(plabel4_3);
			        Label pfield4_3 = new Label(1, contentIndex, physical.treatment.address);
			        wsp.addCell(pfield4_3);
			        contentIndex ++;
			        Label plabel4_4 = new Label(0, contentIndex, "咨询电话");
			        wsp.addCell(plabel4_4);
			        Label pfield4_4 = new Label(1, contentIndex, physical.treatment.tel);
			        wsp.addCell(pfield4_4);
			        Label plabel4_5 = new Label(2, contentIndex, "主检医生");
			        wsp.addCell(plabel4_5);
			        Label pfield4_5 = new Label(3, contentIndex, physical.treatment.treatmentDoctor);
			        wsp.addCell(pfield4_5);
		        }
	        }
	        wbook.write();
	        wbook.close();
	        renderBinary(f, fileName);
	    } catch (Exception exception) {  
	        // TODO Auto-generated catch block  
	    	exception.printStackTrace();
	        render("/errors/500.html", exception);
	    }
	}
	
	/** 获取数据列表. */
	public static void list(String keyword, String code, String department, int page, int rows) {
		rows = rows != 0 ? rows : PAGE_SIZE;
		List<models.HealthArchives> lists = null;
		if (department == null || "".equals(department)) {
			lists = findAll(keyword, code, page, rows);
		} else {
			models.Department dep = models.Department.findById(department);
			if (dep != null) {
				MorphiaQuery query = models.HealthArchives.find();
				if (keyword != null && !"".equals(keyword))
					query.filter("name", keyword);
				if (code != null)
					query.filter("code", code);
				query.filter("department", dep);
				lists = query.limit(rows).offset(page * rows - rows).asList();
			} else {
				lists = findAll(keyword, code, page, rows);
			}
		}
		List<Object> datas = new ArrayList<Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		for (models.HealthArchives mode : lists) {
			datas.add(mode.serialize());
		}
		result.put("page", page);
		result.put("rows", datas);
		result.put("total", models.HealthArchives.count());
		result.put("size", rows);
		renderJSON(result);
	}
	
	/** 获取当前操作者的全部数据. */
	private static List<models.HealthArchives> findAll(String keyword, String code, int page, int rows) {
		List<models.Department> departments = Secure.getDepartments();
		List<models.HealthArchives> result = new ArrayList<models.HealthArchives>();
		int size = 0, offset = page * rows - rows, end = offset + rows;
		for (models.Department department : departments) {
			MorphiaQuery query = models.HealthArchives.find("byDepartment", department);
			if (keyword != null && !"".equals(keyword))
				query.filter("name", keyword);
			if (code != null)
				query.filter("code", code);
			List<models.HealthArchives> data = query.asList();
			if (size >= offset && size < end) {
				for (models.HealthArchives flup : data) {
					result.add(flup);
					size ++;
				}
			} else {
				size += data.size();
			}
			if (size >= end) {
				break;
			}
		}
		return result;
	}
	
	/** 添加操作 */
	public static void add(String name, String code, String gender, int age,
			String home, String dealthAddress, String history, String department, String tel,
			String dealthDate, String reportDate, String physicals) {
		Map<String, String> result = new HashMap<String, String>();
		if (department == null || "".equals(department)) {
			result.put("error", "记录添加失败，请选择该所属部门。<br>如果还未创建部门，请先创建部门后进行添加");
			renderText(result);
		}
		
		models.HealthArchives existHealth = isExist(code, department, null);
		if (existHealth != null) {
			result.put("error", "人员添加失败，" + existHealth.name + "(" + existHealth.code + ")已经存在！");
		} else {
			models.HealthArchives newData = new models.HealthArchives();
			Date newDate = new Date();
			models.Department curDep = models.Department.findById(department);
			
			newData.name = name;
			newData.code = code;
			newData.gender = gender;
			newData.age = age;
			newData.dealthDate = Utils.parseDate(dealthDate);
			newData.reportDate = Utils.parseDate(reportDate);
			newData.home = home;
			newData.dealthAddress = dealthAddress;
			newData.history = history;
			newData.tel = tel;
			newData.createAt = newDate;
			newData.modifyAt = newDate;
			newData.department = curDep;
			newData = newData.save();
			
			if (physicals != null && !"".equals(physicals)) {
				List<String> physicalArr = Utils.toStringArray(physicals, "#S#");
				List<models.Physical> childrenList = new ArrayList<models.Physical>();
				for (int i = 0; i < physicalArr.size(); i ++) {
					// data format: name,content,result,reference,doctor,summary,conclusion,treatment,address,tel,treatmentDoctor,id|... 
					String[] contentAt = physicalArr.get(i).split("#,#", 12);
					models.Physical newPhy = new models.Physical();
					if (contentAt.length > 0)
						newPhy.name = contentAt[0];
					newPhy.health = newData;
					newPhy.createAt = newDate;
					newPhy.modifyAt = newDate;
					newPhy = newPhy.save();
					
					// 添加检查项内容
					if (contentAt.length > 1 && Utils.checkString(contentAt[1])) {
						List<models.PhysicalInfo> infoList = new ArrayList<models.PhysicalInfo>();
						List<String> infoArr = Utils.toStringArray(contentAt[1], "#T#");
						for (int j = 0; j < infoArr.size(); j ++) {
							models.PhysicalInfo newInfo = new models.PhysicalInfo();
							newInfo.content = infoArr.get(j);
							newInfo.physical = newPhy;
							newInfo.createAt = newDate;
							newInfo.modifyAt = newDate;
							newInfo = newInfo.save();
							infoList.add(newInfo);
						}
						newPhy.physicalInfo = infoList;
					}
					// 检查结果
					if ((contentAt.length > 2 && Utils.checkString(contentAt[2]))
							|| (contentAt.length > 4 && Utils.checkString(contentAt[4]))) {
						models.PhysicalResult newResult = new models.PhysicalResult();
						newResult.health = newData;
						newResult.physical = newPhy;
						if (contentAt.length > 2)
							newResult.result = contentAt[2];
						if (contentAt.length > 3)
							newResult.reference = contentAt[3];
						if (contentAt.length > 4)
							newResult.doctor = contentAt[4];
						newResult.department = curDep;
						newResult.createAt = newDate;
						newResult.modifyAt = newDate;
						newResult = newResult.save();
						newPhy.results = newResult;
					}
					// 结论以及建议
					if ((contentAt.length > 5 && Utils.checkString(contentAt[5]))
							|| (contentAt.length > 6 && Utils.checkString(contentAt[6]))
							|| (contentAt.length > 7 && Utils.checkString(contentAt[7]))
							|| (contentAt.length > 8 && Utils.checkString(contentAt[8]))
							|| (contentAt.length > 9 && Utils.checkString(contentAt[9]))
							|| (contentAt.length > 10 && Utils.checkString(contentAt[10]))) {
						models.PhysicalTreatment newTra = new models.PhysicalTreatment();
						if (contentAt.length > 5)
							newTra.summary = contentAt[5];
						if (contentAt.length > 6)
							newTra.conclusion = contentAt[6];
						if (contentAt.length > 7)
							newTra.treatment = contentAt[7];
						if (contentAt.length > 8)
							newTra.address = contentAt[8];
						if (contentAt.length > 9)
							newTra.tel = contentAt[9];
						if (contentAt.length > 10)
							newTra.treatmentDoctor = contentAt[10];
						newTra.department = curDep;
						newTra.createAt = newDate;
						newTra.modifyAt = newDate;
						newTra.physical = newPhy;
						newTra.health = newData;
						newTra = newTra.save();
						newPhy.treatment = newTra;
					}
					newPhy = newPhy.save();
					childrenList.add(newPhy);
				}
				newData.physicals = childrenList;
				newData.save();
			}
		}
		renderText(result);
	}
	
	/** 删除操作 */
	public static void del(String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			models.HealthArchives.findById(id).delete();
			result.put("success", true);
		} catch (Exception exc) {
			result.put("error", "数据库异常，可能其他人正在操作，请刷新后重试。");
		}
		renderText(result);
	}
	
	/** 修改操作 */
	public static void update(String id, String name, String code, String gender, int age,
			String home, String dealthAddress, String history, String department, String tel,
			String dealthDate, String reportDate, String physicals) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (department == null || "".equals(department)) {
			result.put("error", "记录添加失败，请选择该所属部门。<br>如果还未创建部门，请先创建部门后进行添加");
			renderText(result);
		}
		try {
			models.HealthArchives cur = models.HealthArchives.findById(id);
			models.Department curDep = models.Department.findById(department);
			Date modifyDate = new Date();
			cur.name = name;
			cur.code = code;
			cur.gender = gender;
			cur.age = age;
			cur.dealthDate = Utils.parseDate(dealthDate);
			cur.reportDate = Utils.parseDate(reportDate);
			cur.home = home;
			cur.dealthAddress = dealthAddress;
			cur.history = history;
			cur.tel = tel;
			cur.modifyAt = modifyDate;
			cur.department = curDep;
			
			if (physicals != null && !"".equals(physicals)) {
				List<String> physicalArr = Utils.toStringArray(physicals, "#S#");
				List<models.Physical> childrenList = new ArrayList<models.Physical>();
				for (int i = 0; i < physicalArr.size(); i ++) {
					// data format: name,content,result,reference,doctor,summary,conclusion,treatment,address,tel,treatmentDoctor,id|... 
					String[] contentAt = physicalArr.get(i).split("#,#", 12);
					models.Physical newPhy = null;
					if (contentAt.length > 11 && Utils.checkString(contentAt[11])) {
						newPhy = models.Physical.findById(contentAt[11]);
					} else {
						newPhy = new models.Physical();
						newPhy.createAt = modifyDate;
					}
					if (contentAt.length > 0)
						newPhy.name = contentAt[0];
					newPhy.health = cur;
					newPhy.modifyAt = modifyDate;
					newPhy = newPhy.save();
					
					// 清除之前的检查详情
					if (newPhy.physicalInfo != null && newPhy.physicalInfo.size() > 0) {
						for (models.PhysicalInfo delInfo : newPhy.physicalInfo)
							delInfo.delete();
						newPhy.physicalInfo = null;
					}
					
					// 添加检查项内容
					if (contentAt.length > 1 && Utils.checkString(contentAt[1])) {
						List<models.PhysicalInfo> infoList = new ArrayList<models.PhysicalInfo>();
						List<String> infoArr = Utils.toStringArray(contentAt[1], "#T#");
						for (int j = 0; j < infoArr.size(); j ++) {
							models.PhysicalInfo newInfo = new models.PhysicalInfo();
							newInfo.content = infoArr.get(j);
							newInfo.physical = newPhy;
							newInfo.createAt = modifyDate;
							newInfo.modifyAt = modifyDate;
							newInfo = newInfo.save();
							infoList.add(newInfo);
						}
						newPhy.physicalInfo = infoList;
					}
					// 检查结果
					if ((contentAt.length > 2 && Utils.checkString(contentAt[2]) ) || 
							(contentAt.length > 4 && Utils.checkString(contentAt[4]))) {
						models.PhysicalResult newResult = null;
						if (newPhy.results != null) {
							newResult = newPhy.results;
						} else {
							newResult = new models.PhysicalResult();
							newResult.createAt = modifyDate;
						}
						newResult.health = cur;
						newResult.physical = newPhy;
						if (contentAt.length > 2)
							newResult.result = contentAt[2];
						if (contentAt.length > 3)
							newResult.reference = contentAt[3];
						if (contentAt.length > 4)
							newResult.doctor = contentAt[4];
						newResult.department = curDep;
						newResult.modifyAt = modifyDate;
						newResult = newResult.save();
						newPhy.results = newResult;
					}
					// 结论以及建议
					if ((contentAt.length > 5 && Utils.checkString(contentAt[5]))
							|| (contentAt.length > 6 && Utils.checkString(contentAt[6]))
							|| (contentAt.length > 7 && Utils.checkString(contentAt[7]))
							|| (contentAt.length > 8 && Utils.checkString(contentAt[8]))
							|| (contentAt.length > 9 && Utils.checkString(contentAt[9]))
							|| (contentAt.length > 10 && Utils.checkString(contentAt[10]))) {
						models.PhysicalTreatment newTra = null;
						if (newPhy.treatment != null) {
							newTra = newPhy.treatment;
						} else {
							newTra = new models.PhysicalTreatment();
							newTra.createAt = modifyDate;
						}
						if (contentAt.length > 5)
							newTra.summary = contentAt[5];
						if (contentAt.length > 6)
							newTra.conclusion = contentAt[6];
						if (contentAt.length > 7)
							newTra.treatment = contentAt[7];
						if (contentAt.length > 8)
							newTra.address = contentAt[8];
						if (contentAt.length > 9)
							newTra.tel = contentAt[9];
						if (contentAt.length > 10)
							newTra.treatmentDoctor = contentAt[10];
						newTra.department = curDep;
						newTra.modifyAt = modifyDate;
						newTra.physical = newPhy;
						newTra.health = cur;
						newTra = newTra.save();
						newPhy.treatment = newTra;
					}
					newPhy = newPhy.save();
					childrenList.add(newPhy);
				}
				cur.physicals = childrenList;
			} else {
				if (cur.physicals != null) {
					for (models.Physical phy: cur.physicals)
						phy.delete();
				}
				cur.physicals = null;
			}
			cur.save();
			result.put("success", true);
		} catch (Exception exc) {
			exc.printStackTrace();
			result.put("error", "数据库异常，可能其他人正在操作，请刷新后重试。");
		}
		renderText(result);
	}
	@Util
	public static models.HealthArchives isExist(final String code, final String department, final String id) {
		models.Department dep = models.Department.findById(department);
		MorphiaQuery q = models.HealthArchives.find("byDepartment", dep).filter("code", code);
		if (id != null && !"".equals(id)) {
			List<models.HealthArchives> exists = q.asList();
			for (models.HealthArchives exist : exists) {
				if (!id.equals(exist.getId().toString()))
					return exist;
			}
			return null;
		} else
			return q.get();
	}
}