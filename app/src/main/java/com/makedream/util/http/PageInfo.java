package com.makedream.util.http;

import java.io.Serializable;


/**
 * pageInfo
 * 
 * @author cfb
 * 
 */
public class PageInfo implements Serializable{

	private static final long serialVersionUID = -3687753242127348682L;

	/**当前页码*/
    public int currentPageNum = 1;

    /**总页数*/
    public int totalPages = 1;

    /**总记录数  搜索结果需要用到*/
    public int totalRecordNums = 0;
    
    /**页面大小**/
    public int pagesize = 0;
    
    public PageInfo() {
    }

	public int getNextPageIndex() {
		if (currentPageNum < totalPages)
			return (++currentPageNum);
		else
			return -1;
	}

	public boolean isLastPage(){
		
		return currentPageNum>=totalPages;
	}
	
	@Override
	public String toString(){
		return "currentPageNum="+currentPageNum+", totalPages="+totalPages+", totalRecordNums="+totalRecordNums;
	}

}
