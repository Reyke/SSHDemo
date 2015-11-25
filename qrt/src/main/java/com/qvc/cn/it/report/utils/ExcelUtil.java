package com.qvc.cn.it.report.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

import com.qvc.cn.it.report.model.IssueComponent;
import com.qvc.cn.it.report.model.ReportPOJO;
import com.qvc.cn.it.report.model.TemplateDetail;
import com.qvc.cn.it.report.model.TemplateHead;

public class ExcelUtil {
	private final static int DEFAULT_START_X = 1;
	private final static int DEFAULT_START_Y = 1;

	private static final String[] ISSUE_TYPE_TITLES = new String[] {"Test Group", "Data Issue", 
        "Device Issue", "Known Issue", "Other Issue", "Possible Defect", "Script Issue", "Total"};
	private static final String[] EXECUTE_RESULT_TITLES = new String[] {"Test Group", "Passed", "Manual Passed", "Re-Run Passed", "Failed", "Total"};
	
	private static Map<String, String> COLUMN_PROPERTY_MAP = new HashMap<String, String>();
	public static final String[] EXECUTION_SUMMARY_TITLES = new String[]{"Execution Date", "Release",
        "US Test Group","Passed","Failed","Total","Coverage"};
	private static HSSFFont FONT_BOLDER;
	private static HSSFFont FONT_RED;
	private static HSSFFont FONT_GREEN;
	
	private static HSSFCellStyle headerStyle;
	private static HSSFCellStyle cellStyle;
	private static HSSFCellStyle percentageCellStyle;
	private static Map<Short, HSSFCellStyle> COLOR_STYLE = new HashMap<Short, HSSFCellStyle>();
	
	/**
	 * generate a excel file for selected task.
	 * @param list	all test cases in this task.
	 * @param template	the template selected to export.
	 * @param components	all components, used to generate the columns title of the report.
	 * @return	the excel file created.
	 */
	public static HSSFWorkbook generateQAReport(
			List<ReportPOJO> list,
			TemplateHead template, 
			List<IssueComponent> components,
			List<Map<String, Object>> hisSummary ) {
		
		initComponentInfo(components);
		
		HSSFWorkbook report = new HSSFWorkbook();
		
		init(report);
		
		Map<String, List<ReportPOJO>> formattedData = formatList(list);

		createSummarySheet(report, formattedData, hisSummary);
		createDetailSheet(report, formattedData, template);
		
		return report;
	}

	private static void initComponentInfo(List<IssueComponent> components) {
		
		COLUMN_PROPERTY_MAP.clear();
		for (IssueComponent component : components) {
			COLUMN_PROPERTY_MAP.put(component.getFieldName(), component.getTitle());
		}
	}
	
	private static void init(HSSFWorkbook workbook) {
		FONT_BOLDER = workbook.createFont();
		FONT_BOLDER.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		FONT_RED = workbook.createFont();
		FONT_RED.setColor(HSSFFont.COLOR_RED);
		
		FONT_GREEN = workbook.createFont();
		FONT_GREEN.setColor(HSSFColor.GREEN.index);
		
		cellStyle = null;
		percentageCellStyle = null;
		headerStyle = null;
		COLOR_STYLE.clear();
	}

	private static HSSFCellStyle createCellStyle(HSSFWorkbook workbook) {
		if (cellStyle == null) {
			cellStyle = workbook.createCellStyle();
			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			cellStyle.setWrapText(true);
		} 
		return cellStyle;
	}
	
	private static HSSFCellStyle createPercentageCellStyle(HSSFWorkbook workbook) {
		if (percentageCellStyle == null) {
			percentageCellStyle = workbook.createCellStyle();
			percentageCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			percentageCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			percentageCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			percentageCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			percentageCellStyle.setWrapText(true);
			percentageCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));;
		} 
		return percentageCellStyle;
	}
	
	private static HSSFCellStyle createColorFontStyle(HSSFWorkbook workbook, short color) {
		HSSFCellStyle style = COLOR_STYLE.get(color);
		if (style == null) {
			style = workbook.createCellStyle();
			HSSFFont font = workbook.createFont();
			font.setColor(color);
			style.setFont(font);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setWrapText(true);
			
			COLOR_STYLE.put(color, style);
		} 
		return style;
	}
	private static HSSFCellStyle createHeaderStyle(HSSFWorkbook workbook) {
		if (headerStyle == null) {
			headerStyle = workbook.createCellStyle();
			HSSFFont font = workbook.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			headerStyle.setFont(font);
			headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			headerStyle.setWrapText(true);
		} 
		return headerStyle;
	}
	
	private static void createDetailSheet(HSSFWorkbook report,
			Map<String, List<ReportPOJO>> data, TemplateHead template) {
		HSSFSheet detailSheet = report.createSheet(" Failed Detail");
		
		// hide all grid lines.
		detailSheet.setDisplayGridlines(false);
		
		detailSheet.setColumnWidth(0, 10);
		
		Position position = new ExcelUtil.Position(1, 1);
		
		Map<String, String> templates = formatTemplate(template.getTemplateDetails());
		
		String[] titles = new String[templates.size()];
		templates.keySet().toArray(titles);
		
		createTitle(report, detailSheet, position, titles);
		
		boolean needMergeTestSetField = "[set_name]".equals(template.getTemplateDetails().iterator().next().getExpression());
		
		for (String testSet : data.keySet()) {
			Position startPosition = position.clone();			
			
			for (ReportPOJO pojo : data.get(testSet)) {
				//20140606  don't show test case in detail sheet if it match below
				// 1. Automation status = "Passed"
				// 2. Issue Type = 'Re-run Passed'
				if(Constants.TASK_STATE_PASSED.equalsIgnoreCase(pojo.getStatus()) ||
						Constants.ISSUE_TYPE_RERUNPASSED.equalsIgnoreCase(pojo.getIssueType())) {
					continue;
				}
				createDetailLine(report, detailSheet, position, pojo, templates);
			}
			
			// we need to merge the TestSet filed, we just check the first field.
			if (needMergeTestSetField) {
				detailSheet.addMergedRegion(new CellRangeAddress(startPosition.x, position.x - 1, startPosition.y, position.y));
				detailSheet.getRow(startPosition.x).getCell(startPosition.y).getCellStyle().setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			}
		}
		
		setRegionBorderToThick(detailSheet, new Position(1, 1), position.move(-1, template.getTemplateDetails().size()));
		
		for(int i = 1; i <= templates.size(); i++) {
			detailSheet.autoSizeColumn(i);
		}
	}
	
	private static void createDetailLine(
			HSSFWorkbook report,
			HSSFSheet detailSheet, 
			Position position, 
			ReportPOJO pojo,
			Map<String, String> templates) {
		
		HSSFRow row = detailSheet.createRow(position.x);
		
		for (String expression : templates.values()) {
			// we need reflect the properties in the POJO object.
			appendCell(report, row, position, getValue(pojo, expression));
		}
		position.nextLineStart();
	}
	
	private static HSSFRichTextString getValue (ReportPOJO pojo, String expression) {
		HSSFRichTextString result = new HSSFRichTextString();
		
		Pattern pattern = Pattern.compile("\\[([^\\[]*)\\]");
		Matcher match = pattern.matcher(expression);
		boolean moreThanOneProperties = expression.indexOf("+") > -1;
		String resultWithStyle = "";
		List<Integer[]> propertyIndex = new ArrayList<Integer[]>();
		
		while (match.find()) {
			String fieldName = match.group(1);
			String title = COLUMN_PROPERTY_MAP.get(fieldName);
			String value = null;
			if (title == null) {
				throw new RuntimeException ("Invalid column name " + match.group(1));
			}
			String property =  "get" + title.replace(" ", "");
			
			value = reflectGetStringMethod(pojo, property);
			if (value != null && !"null".equals(value)) {
				if (moreThanOneProperties) {
					propertyIndex.add(new Integer[] {resultWithStyle.length(), title.length()});
					resultWithStyle += (title + " : " + value + "\r\n");
				} else {
					result = new HSSFRichTextString(value);
					if ("getStatus".equals(property)) {
						// apply color to status field.
						if ("Passed".equalsIgnoreCase(value)) {
							// green
							result.applyFont(FONT_GREEN);
						} else if (Constants.TASK_STATE_PASSED.equalsIgnoreCase(value)) {
							// red
							result.applyFont(FONT_RED);
						} else {
							// do nothing
						}
						
					}
				}
			}
		}
		
		if (moreThanOneProperties) {
			result = new HSSFRichTextString(resultWithStyle);
			for (Integer[] index : propertyIndex) {
				result.applyFont(index[0], index[0] + index[1], FONT_BOLDER);
			}
		}
		
		return result;
	}

	private static String reflectGetStringMethod(ReportPOJO pojo, String property) {
		String result = "";
		try {
			Method method = pojo.getClass().getDeclaredMethod(property);
			result += (String)method.invoke(pojo);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private static Map<String, String> formatTemplate(
			Set<TemplateDetail> templateDetails) {
		Map<String, String> templates = new LinkedHashMap<String, String>();
		for (TemplateDetail td : templateDetails) {
			templates.put(td.getColumnLable(), td.getExpression());
		}
		return templates;
	}

	private static void createSummarySheet(
			HSSFWorkbook report,
			Map<String, List<ReportPOJO>> data, 
			List<Map<String, Object>> hisSummary) {
		
		HSSFSheet summarySheet = report.createSheet("Summary");
		
		// hide all grid lines.
		summarySheet.setDisplayGridlines(false);
		
		Position executeResultPosition = new ExcelUtil.Position(DEFAULT_START_X, DEFAULT_START_Y);
		Position issueTypePosition = new ExcelUtil.Position(executeResultPosition.x + data.size() + 5, executeResultPosition.y);
		Position orginalExecuteResultPosition = executeResultPosition.clone();
		Position orginalIssueTypePosition = issueTypePosition.clone();
		
		// write the test set result area.
		// write the test set result header.
		createTitle(report, summarySheet, executeResultPosition, EXECUTE_RESULT_TITLES);
		// write the test set issue type header. 
		createTitle(report, summarySheet, issueTypePosition, ISSUE_TYPE_TITLES);
		
		
		Map<String, Integer> totalIssueTypeCalculate = new HashMap<String, Integer>();
		Map<String, Integer> totalStatusCalculate = new HashMap<String, Integer>();
		for (String testSet : data.keySet()) {
			Map<String, Integer> statusCalculate = new HashMap<String, Integer>();
			Map<String, Integer> issueTypeCalculate = new HashMap<String, Integer>();
			
			calculateByIssueTypeAndResult(
					data.get(testSet), 
					statusCalculate,
					issueTypeCalculate
					);
			
			fillToTotalCalculate(totalIssueTypeCalculate, issueTypeCalculate);
			fillToTotalCalculate(totalStatusCalculate, statusCalculate);
			
			insertStatusRow(
					report, 
					data.get(testSet).size(),
					summarySheet, 
					executeResultPosition,
					testSet, 
					statusCalculate);
						
			insertIssueTypeRow(
					report, 
					summarySheet, 
					issueTypePosition,
					testSet, 
					issueTypeCalculate);
			issueTypePosition.nextLineStart();
		}
		
		// add the total row
		insertStatusRow(report, sumAllMap(totalStatusCalculate), summarySheet, executeResultPosition,
				"Total", totalStatusCalculate);
		
		insertIssueTypeRow(report, summarySheet, issueTypePosition,
				"Total", totalIssueTypeCalculate);

		// add the rate row 
		insertStatusRate(report, sumAllMap(totalStatusCalculate), summarySheet, executeResultPosition,
				"Rate", totalStatusCalculate);
		
		// let's set the border for these 3 regions.
		setRegionBorderToThick(summarySheet, orginalExecuteResultPosition, executeResultPosition);
		setRegionBorderToThick(summarySheet, orginalIssueTypePosition, issueTypePosition);
		
		executeResultPosition.nextLineStart();
		issueTypePosition.nextLineStart();
		
		// add the issue type summary area.
		issueTypePosition.down(2);
		orginalIssueTypePosition = issueTypePosition.clone();
		createTitle(report, summarySheet, issueTypePosition, new String[] {"Type", "Total"});
		
		for (String issueType : totalIssueTypeCalculate.keySet()) {
			// display the issue type
			HSSFRow issueTypeRow = summarySheet.createRow(issueTypePosition.x);
			appendCell(report, issueTypeRow, issueTypePosition, issueType, HSSFCell.CELL_TYPE_STRING, true);
			// display the amount
			appendCell(report, issueTypeRow, issueTypePosition, String.valueOf(totalIssueTypeCalculate.get(issueType)), HSSFCell.CELL_TYPE_NUMERIC, true);
			
			issueTypePosition.nextLineStart();
		}
		setRegionBorderToThick(summarySheet, orginalIssueTypePosition, issueTypePosition.clone().move(-1, 2));
		
		/**
		 * generate task history summary list
		 */
		Position executeSummaryHisPosition = issueTypePosition.down(2);
        //new ExcelUtil.Position(issueTypePosition.x + totalIssueTypeCalculate.size(), executeResultPosition.y);
		Position orginalExecuteSummaryHisPosition = executeSummaryHisPosition.clone();
		createTitle(report, summarySheet, executeSummaryHisPosition, EXECUTION_SUMMARY_TITLES);
		boolean isLast = false;
		for(int i = hisSummary.size() - 1; i >= 0 ; i--){
			if(i == 0 ){
				isLast = true;
			}
			insertSummaryRow(
					report,
					isLast, 
					summarySheet,
					executeSummaryHisPosition, 
					hisSummary.get(i)
				);
		}
		setRegionBorderToThick(summarySheet, orginalExecuteSummaryHisPosition, executeSummaryHisPosition);
		/*****************************************/
		
		
		// auto size all columns
		for (int j = 0; j < 12; j++) {
			summarySheet.autoSizeColumn(j);
		}
	}

	private static void setRegionBorderToThick(HSSFSheet sheet,
			Position startPosition, Position endPosition) {
		CellRangeAddress region = new CellRangeAddress(startPosition.x, endPosition.x, startPosition.y, endPosition.y - 1);
		RegionUtil.setBorderTop(CellStyle.BORDER_THICK, region, sheet, sheet.getWorkbook());
		RegionUtil.setBorderRight(CellStyle.BORDER_THICK, region, sheet, sheet.getWorkbook());
		RegionUtil.setBorderBottom(CellStyle.BORDER_THICK, region, sheet, sheet.getWorkbook());
		RegionUtil.setBorderLeft(CellStyle.BORDER_THICK, region, sheet, sheet.getWorkbook());
	}

	private static void insertIssueTypeRow(HSSFWorkbook report,
			HSSFSheet summarySheet, Position issueTypePosition, String testSet,
			Map<String, Integer> issueTypeCalculate) {
		// create a new row for each test set.
		HSSFRow issueTypeRow = summarySheet.createRow(issueTypePosition.x);
		// write the test set name
		appendCell(report, issueTypeRow, issueTypePosition, testSet, HSSFCell.CELL_TYPE_STRING, true);
		
		appendTestSetIssueType(report, issueTypePosition, issueTypeCalculate, issueTypeRow);
	}

	private static void insertStatusRate(HSSFWorkbook report,
			int total, HSSFSheet summarySheet,
			Position executeResultPosition, String testSet, Map<String, Integer> statusCalculate) {
		// create a new row for each test set.
		HSSFRow executeResultRow = summarySheet.createRow(executeResultPosition.x);
		// write the test set name
		appendCell(report, executeResultRow, executeResultPosition, testSet, HSSFCell.CELL_TYPE_STRING, true);
		
		appendTestSetStatusRate(report, total, executeResultPosition, testSet, statusCalculate, executeResultRow);
		
//		executeResultPosition.nextLineStart();
	}
	
	private static void insertStatusRow(
			HSSFWorkbook report,
			int total, 
			HSSFSheet summarySheet,
			Position executeResultPosition, 
			String testSet, 
			Map<String, Integer> statusCalculate) {
		
		// create a new row for each test set.
		HSSFRow executeResultRow = summarySheet.createRow(executeResultPosition.x);
		// write the test set name
		appendCell(report, executeResultRow, executeResultPosition, testSet, HSSFCell.CELL_TYPE_STRING, true);
		
		appendTestSetStatus(report, total, executeResultPosition, testSet, statusCalculate, executeResultRow);
		
		executeResultPosition.nextLineStart();
	}
	private static void insertSummaryRow(
			HSSFWorkbook report,
			boolean isLast, 
			HSSFSheet summarySheet,
			Position executeSummaryHisPosition, 
			Map<String, Object> summary
			) {
		// create a new row for each test set.
		HSSFRow executeSummaryHisRow = summarySheet.createRow(executeSummaryHisPosition.x);
		// write the test set name
		for(String key : EXECUTION_SUMMARY_TITLES){
			short type = 1, color=0;
			if(summary.get(key) instanceof String){
				type = HSSFCell.CELL_TYPE_STRING;
			}else{
				type = HSSFCell.CELL_TYPE_NUMERIC;
			}
			if(key == EXECUTION_SUMMARY_TITLES[3]){
				color = HSSFColor.GREEN.index;
				appendCell(report, executeSummaryHisRow, executeSummaryHisPosition,summary.get(key)+"" , type, false,color);
			}else if(key == EXECUTION_SUMMARY_TITLES[4]){
				color = HSSFColor.RED.index;
				appendCell(report, executeSummaryHisRow, executeSummaryHisPosition,summary.get(key)+"" , type, false,color);
			}else{
				appendCell(report, executeSummaryHisRow, executeSummaryHisPosition,summary.get(key)+"" , type, false);
			}
			
			
		}
		if(isLast) return;
		executeSummaryHisPosition.nextLineStart();
	}

	private static void appendTestSetStatusRate(HSSFWorkbook report, int total, Position executeResultPosition,
			String testSet, Map<String, Integer> statusCalculate, HSSFRow executeResultRow) {
		// Passed
		int value = statusCalculate.containsKey(Constants.TASK_STATE_PASSED) 
				? statusCalculate.get(Constants.TASK_STATE_PASSED) 
				: 0;
		appendPercentageCell(
				report, 
				executeResultRow, 
				executeResultPosition, 
				(value*1.0)/total, 
				HSSFCell.CELL_TYPE_NUMERIC, 
				false);
		
		//Mannual Passed
		value = statusCalculate.containsKey(Constants.MANUAL_PASSED) 
				? statusCalculate.get(Constants.MANUAL_PASSED) 
				: 0;
		appendPercentageCell(
				report, 
				executeResultRow, 
				executeResultPosition, 
				(value*1.0)/total, 
				HSSFCell.CELL_TYPE_NUMERIC, 
				false);

        //Re-run Passed
        value = statusCalculate.containsKey(Constants.ISSUE_TYPE_RERUNPASSED) 
            ? statusCalculate.get(Constants.ISSUE_TYPE_RERUNPASSED) 
            : 0;
        appendPercentageCell(
        		report, 
        		executeResultRow, 
        		executeResultPosition, 
        		(value*1.0)/total, 
        		HSSFCell.CELL_TYPE_NUMERIC, 
        		false);
		
		// Failed
		value = statusCalculate.containsKey(Constants.TASK_STATE_FAILED) 
				? statusCalculate.get(Constants.TASK_STATE_FAILED) 
				: 0;
		appendPercentageCell(report, executeResultRow, executeResultPosition, (value*1.0)/total, HSSFCell.CELL_TYPE_NUMERIC, false);
		
		// Total
		value = total;
		appendPercentageCell(report, executeResultRow, executeResultPosition, 1d, HSSFCell.CELL_TYPE_NUMERIC, false);
	}
	
	private static void appendTestSetStatus(
			HSSFWorkbook report, 
			int total, 
			Position executeResultPosition,
			String testSet, 
			Map<String, Integer> statusCalculate, 
			HSSFRow executeResultRow
			) {
		// Passed
		int value = statusCalculate.containsKey(Constants.TASK_STATE_PASSED) 
				? statusCalculate.get(Constants.TASK_STATE_PASSED) 
				: 0;
		appendCell(
				report, 
				executeResultRow, 
				executeResultPosition, 
				String.valueOf(value), 
				HSSFCell.CELL_TYPE_NUMERIC, 
				false,
				HSSFColor.GREEN.index
				);
				
		//Manual Passed
		value = statusCalculate.containsKey(Constants.MANUAL_PASSED) 
				? statusCalculate.get(Constants.MANUAL_PASSED) 
				: 0;
		appendCell(
				report, 
				executeResultRow, 
				executeResultPosition, 
				String.valueOf(value), 
				HSSFCell.CELL_TYPE_NUMERIC, 
				false,
				HSSFColor.GREEN.index
				);
		
        //Re-run Passed
        value = statusCalculate.containsKey(Constants.ISSUE_TYPE_RERUNPASSED) 
            ? statusCalculate.get(Constants.ISSUE_TYPE_RERUNPASSED) 
            : 0;
        appendCell(
        		report, 
        		executeResultRow, 
        		executeResultPosition, 
        		String.valueOf(value), 
        		HSSFCell.CELL_TYPE_NUMERIC, 
        		false,
				HSSFColor.GREEN.index
        		);

		// Failed
		value = statusCalculate.containsKey(Constants.TASK_STATE_FAILED) 
				? statusCalculate.get(Constants.TASK_STATE_FAILED) 
				: 0;
		appendCell(
				report, 
				executeResultRow, 
				executeResultPosition, 
				String.valueOf(value), 
				HSSFCell.CELL_TYPE_NUMERIC, 
				false,HSSFColor.RED.index
				);

		// Total
		value = total;
		appendCell(
				report, 
				executeResultRow, 
				executeResultPosition, 
				String.valueOf(value), 
				HSSFCell.CELL_TYPE_NUMERIC, 
				false
				);
	}

	private static void appendTestSetIssueType(HSSFWorkbook report,
			Position issueTypePosition,
			Map<String, Integer> issueTypeCalculate, HSSFRow issueTypeRow) {
		int value;

		// Data Issue
		value = issueTypeCalculate.containsKey("Data Issue") ? issueTypeCalculate.get("Data Issue") : 0;
		appendCell(report, issueTypeRow, issueTypePosition, String.valueOf(value), HSSFCell.CELL_TYPE_NUMERIC, false);
		
		// Device Issue
		value = issueTypeCalculate.containsKey("Device Issue") ? issueTypeCalculate.get("Device Issue") : 0;
		appendCell(report, issueTypeRow, issueTypePosition, String.valueOf(value), HSSFCell.CELL_TYPE_NUMERIC, false);
		
		// Known Issue
		value = issueTypeCalculate.containsKey("Known Issue") ? issueTypeCalculate.get("Known Issue") : 0;
		appendCell(report, issueTypeRow, issueTypePosition, String.valueOf(value), HSSFCell.CELL_TYPE_NUMERIC, false);
		
		// Object Change
	//	value = issueTypeCalculate.containsKey("Object Change") ? issueTypeCalculate.get("Object Change") : 0;
	//	appendCell(report, issueTypeRow, issueTypePosition, String.valueOf(value), HSSFCell.CELL_TYPE_NUMERIC, false);
		
		// Other Issue
		value = issueTypeCalculate.containsKey("Other Issue") ? issueTypeCalculate.get("Other Issue") : 0;
		appendCell(report, issueTypeRow, issueTypePosition, String.valueOf(value), HSSFCell.CELL_TYPE_NUMERIC, false);
		
		// Performance Issue
//		value = issueTypeCalculate.containsKey("Performance Issue") ? issueTypeCalculate.get("Performance Issue") : 0;
//		appendCell(report, issueTypeRow, issueTypePosition, String.valueOf(value), HSSFCell.CELL_TYPE_NUMERIC, false);
		
		// Possible Defect
		value = issueTypeCalculate.containsKey("Possible Defect") ? issueTypeCalculate.get("Possible Defect") : 0;
		appendCell(report, issueTypeRow, issueTypePosition, String.valueOf(value), HSSFCell.CELL_TYPE_NUMERIC, false);
		
		// Re-run Passed
//		value = issueTypeCalculate.containsKey("Re-run Passed") ? issueTypeCalculate.get("Re-run Passed") : 0;
//		appendCell(report, issueTypeRow, issueTypePosition, String.valueOf(value), HSSFCell.CELL_TYPE_NUMERIC, false);
		
		// Script Issue
		value = issueTypeCalculate.containsKey("Script Issue") ? issueTypeCalculate.get("Script Issue") : 0;
		appendCell(report, issueTypeRow, issueTypePosition, String.valueOf(value), HSSFCell.CELL_TYPE_NUMERIC, false);
		
		// Total
		value = sumAllMap(issueTypeCalculate);
		appendCell(report, issueTypeRow, issueTypePosition, String.valueOf(value), HSSFCell.CELL_TYPE_NUMERIC, false);
	}

	private static int sumAllMap(Map<String, Integer> issueTypeCalculate) {
		int value = 0;
		for (String issueType : issueTypeCalculate.keySet()) {
			if(!Constants.MANUAL_PASSED.equals(issueType)){
				value += issueTypeCalculate.get(issueType);
			}
		}
		return value;
	}
	
	private static void createTitle (HSSFWorkbook workbook, HSSFSheet sheet, Position position, String[] titles) {
		HSSFRow headerRow = sheet.createRow(position.x);
		for (String title : titles) {
			appendCell(workbook, headerRow, position, title, HSSFCell.CELL_TYPE_STRING, true);
		}
		position.nextLineStart();		
	}
	
	private static void fillToTotalCalculate(
			Map<String, Integer> total,
			Map<String, Integer> subTotal
			){
		for (String issueType : subTotal.keySet()) {
			if (total.containsKey(issueType)) {
				total.put(issueType, total.get(issueType) + subTotal.get(issueType));
			} else {
				total.put(issueType, subTotal.get(issueType));
			}
		}
	}

	private static void calculateByIssueTypeAndResult(
			List<ReportPOJO> list,
			Map<String, Integer> statusCalculate,
			Map<String, Integer> issueTypeCalculate
			) {
		for (ReportPOJO pojo : list) {
			
			// execute status
			if(pojo.getStatus().equals(Constants.TASK_STATE_PASSED)){
                _changeStatus(Constants.TASK_STATE_PASSED,statusCalculate);
			}else if(!Constants.ISSUE_TYPE_RERUNPASSED.equals(pojo.getIssueType())){
				if ( statusCalculate.containsKey(Constants.TASK_STATE_FAILED) ){
					statusCalculate.put(
							Constants.TASK_STATE_FAILED,
                            statusCalculate.get(Constants.TASK_STATE_FAILED) + 1
                            );
				} else {
					statusCalculate.put(Constants.TASK_STATE_FAILED, 1);
				}
			}
			
            // issue types.
            if (pojo.getIssueType() != null && !Constants.ISSUE_TYPE_UNDETERMINED.equals(pojo.getIssueType())) {
	
                if(Constants.ISSUE_TYPE_RERUNPASSED.equals(pojo.getIssueType())){
                    _changeStatus(Constants.ISSUE_TYPE_RERUNPASSED,statusCalculate);
                }else{
					if("Y".equals(pojo.getManualPassedInd())){
						_changeStatus(Constants.MANUAL_PASSED,statusCalculate);
					}
                    _changeStatus(pojo.getIssueType(),issueTypeCalculate);
                }
            }
        }
    }

    private static void _changeStatus(String key, Map<String,Integer> map){
        if (map.containsKey(key)) {
            map.put(key,map.get(key) + 1);
        } else {
            map.put(key, 1);
        }
    }

	private static Map<String, List<ReportPOJO>> formatList(
			List<ReportPOJO> list) {
		Map<String, List<ReportPOJO>> result = new HashMap<String, List<ReportPOJO>>();
		for (ReportPOJO pojo : list) {
			String testSetName = pojo.getTestSetName();

			if (result.containsKey(testSetName)) {
				result.get(testSetName).add(pojo);
			} else {
				List<ReportPOJO> tmp = new ArrayList<ReportPOJO>();
				tmp.add(pojo);
				result.put(testSetName, tmp);
			}
		}
		return result;
	}

	private static HSSFCell appendPercentageCell(HSSFWorkbook workbook, HSSFRow row, Position position,
			double value, int type, boolean isHeader) {
		HSSFCell cell = appendCell(workbook, row, position, Double.toString(value), type, isHeader);
		cell.setCellStyle(createPercentageCellStyle(workbook));
		return cell;
	}
	
	private static HSSFCell appendCell(HSSFWorkbook workbook, HSSFRow row, Position position,
			HSSFRichTextString value) {
		HSSFCell cell = row.createCell(position.y);

		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(createCellStyle(workbook));
		cell.setCellValue(value);
		
		position.right();	
		return cell;
	}
	private static HSSFCell appendCell(HSSFWorkbook workbook, HSSFRow row, Position position,
			String value, int type, boolean isHeader, short color) {
		HSSFCell cell = appendCell(workbook, row, position, value, type, isHeader);
		cell.setCellStyle(createColorFontStyle(workbook, color));
		return cell;
	}
	private static HSSFCell appendCell(HSSFWorkbook workbook, HSSFRow row, Position position,
			String value, int type, boolean isHeader) {
		HSSFCell cell = row.createCell(position.y);
		if (isHeader) {
			cell.setCellStyle(createHeaderStyle(workbook));
		} else {
			cell.setCellStyle(createCellStyle(workbook));
		}
		cell.setCellType(type);
		
		switch (type) {
		case HSSFCell.CELL_TYPE_NUMERIC:
			cell.setCellValue(Double.parseDouble(value));
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			cell.setCellValue(Boolean.parseBoolean(value));
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			cell.setCellFormula(value);
			break;
		default:
			cell.setCellValue(value);
		}
		position.right();
		return cell;
	}

	static class Position implements Cloneable{
		// (0, 0) (0, 1) (0, 2)
		// (1, 0) (1, 1) (1, 2)
		// (2, 0) (2, 1) (2, 2)

		int x;
		int y;

		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public Position clone () {
			return new Position(x, y);
		}

		public Position down() {
			// move down.
			x++;
			return this;
		}

		public Position up() {
			x--;
			return this;
		}

		public Position left() {
			y--;
			return this;
		}

		public Position right() {
			y++;
			return this;
		}

		public Position down(int count) {
			x += count;
			return this;
		}

		public Position update(int count) {
			x -= count;
			return this;
		}

		public Position left(int count) {
			y -= count;
			return this;
		}

		public Position right(int count) {
			y += count;
			return this;
		}

		public Position move(int xDelta, int yDelta) {
			x += xDelta;
			y += yDelta;
			return this;
		}

		public Position moveTo(int x, int y) {
			this.x = x;
			this.y = y;
			return this;
		}

		public Position nextLineStart() {
			x++;
			y = DEFAULT_START_Y;
			return this;
		}
	}
}
