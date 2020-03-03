package io.dfjinxin.modules.outsider.excel;

import java.util.Collection;

import io.dfjinxin.modules.outsider.excel.model.Record;

/**
 * @author zhujiazhou
 *
 */
public interface Pager {
	public String getTitle();
	
	public int getPageNum();

	public int getPageColumn();
	
	public Record getHeader();
	
	public Collection<? extends Record> getBody();
	
}
