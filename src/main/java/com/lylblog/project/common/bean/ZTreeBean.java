/**
 * 
 */
package com.lylblog.project.common.bean;

import java.io.Serializable;
import java.util.List;

/**
 * layui树结构参数模型
 * @author xym
 * @date 2018-8-7
 * @varsion 1.0
 * 
 * 结构：[{ //节点
 *   name: '父节点1'
 *  ,children: [{
 *     name: '子节点11'
 *  },{
 *     name: '子节点12'
 *   }]
 * },{
 *   name: '父节点2'
 *   ,children: [{
 *     name: '子节点21'
 *     ,children: [{
 *       name: '子节点211'
 *     }]
 */
public class ZTreeBean implements Serializable {

	private static final long serialVersionUID = 947194807503059417L;

	private int id;//分类id
	private String name;//分类名称
	private int parentId;//父分类id,顶级分类是0
	private String permType;//类型
	private String permName;
	private boolean open;//
	private boolean nocheck;
	private List<ZTreeBean> children;//子分类集合

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public boolean getOpen() { return open; }

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getPermType() { return permType; }

	public void setPermType(String permType) { this.permType = permType; }

	public String getPermName() {
		return permName;
	}

	public void setPermName(String permName) {
		this.permName = permName;
	}

	public boolean getNocheck() {
		return nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}

	public List<ZTreeBean> getChildren() {
		return children;
	}

	public void setChildren(List<ZTreeBean> children) {
		this.children = children;
	}
}
