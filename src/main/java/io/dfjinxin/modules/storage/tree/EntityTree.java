package io.dfjinxin.modules.storage.tree;

import java.util.List;

/**
 * 生成树数据的接收类
 *
 */
public class EntityTree implements ITree<EntityTree> {

	private String id;
	
	private String parentId;
	
	private String label;
	
	private List<EntityTree> children ;

	private Integer num;


	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<EntityTree> getChildren() {
		return children;
	}

	@Override
	public void setChildren(List<EntityTree> childList) {
		this.children = childList;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
}
