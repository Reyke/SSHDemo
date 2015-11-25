package com.qvc.cn.it.report.manager;

import java.util.List;
import java.util.Map;

import com.qvc.cn.it.report.model.ReportPOJO;

public interface ReportExportManager extends BaseManager {
	public List<ReportPOJO> getReportByTaskId(Long taskID);

	public List<Map<String, Object>> getLatestThreeTaskSummaryByTaskId(
			Long taskID);
}
