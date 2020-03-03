package io.dfjinxin.modules.outsider.entity;

import java.util.Collection;

import io.dfjinxin.modules.outsider.excel.Pager;
import io.dfjinxin.modules.outsider.excel.model.Record;

public class MyPager implements Pager{

	/**
	 * @param title
	 * @param headers
	 * @param body
	 */
	public MyPager(String title, Record header, Collection<? extends Record> body) {
		super();
		this.title = title;
		this.headers = header;
		this.body = body;
	}

	private String title;
	
	private Record headers;
	
	private Collection<? extends Record> body;
	
	public String getTitle() {
		return title;
	}

	public int getPageNum() {
		return 0;
	}

	public int getPageColumn(){
		try {
			return headers.getCells().length;
		} catch (IllegalAccessException e) {
			return 0;
		}
	}

	public Record getHeader() {
		return headers;
	}

	public Collection<? extends Record> getBody() {
		return body;
	}

}