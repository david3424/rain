package org.david.rain.utils.vo;

/**
 * DataElements
 * <p>
 * elements of object for reflecting
 * 
 * @author pazzini
 *
 */
public class DataElements {
	
	public enum Type {
		Numeric, Integer, PostiveInteger, Percent, Varchar, Date, Time, Timestamp, Month
	}

	/** get the the value by reflecting using the name*/
	private String name;
	
	/** the name of title of Excel*/
	private String gbkName;
	
	/** get the the value by reflecting using the category for chart */
	private String categoray;
	
	/** the prefix of grid data for common showing*/
	private String gridPrefix = "";
	
	/** the suffix of grid data for common showing*/
	private String gridSuffix = "";
	
	/** the width of excel column */
	private int excelColWidth;
	
	/** the type of this element */
	private Type type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGbkName() {
		return gbkName;
	}

	public void setGbkName(String gbkName) {
		this.gbkName = gbkName;
	}

	public String getCategoray() {
		return categoray;
	}

	public void setCategoray(String categoray) {
		this.categoray = categoray;
	}

	public String getGridPrefix() {
		return gridPrefix;
	}

	public void setGridPrefix(String gridPrefix) {
		this.gridPrefix = gridPrefix;
	}

	public String getGridSuffix() {
		return gridSuffix;
	}

	public void setGridSuffix(String gridSuffix) {
		this.gridSuffix = gridSuffix;
	}

	public int getExcelColWidth() {
		return excelColWidth;
	}

	public void setExcelColWidth(int excelColWidth) {
		this.excelColWidth = excelColWidth;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}
