/**   
* @Title: BaseParaBean.java
* @Package com.cshg.common.bean
* @Description: 
* @author yuanshuai
* @date Jan 4, 2017 10:45:43 AM
* @version V1.0
*/
package com.lylblog.project.common.bean;


/**
 * @author YShuai
 *
 */
public class ParaBean {
	//分页选项
	protected int page=1; //页码
	protected int limit=10; //每页记录数
	//操作选项
	protected int rk;             //    序号
	protected String nickName;    //    用户昵称
	protected String orderBy;     //	排序
	protected String createBy;    //	创建者
	protected String createTime;  //	创建时间
	protected String updateBy;    //	更新者
	protected String updateTime;  //	更新时间
	protected String t1; //参数1
	protected String t2; //参数2
	protected String t3; //参数3
	protected String t4; //参数4

	protected String userId; //用户编码

	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getRk() {
		return rk;
	}

	public void setRk(int rk) {
		this.rk = rk;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getT1() {
		return t1;
	}
	public void setT1(String t1) {
		this.t1 = t1;
	}
	public String getT2() {
		return t2;
	}
	public void setT2(String t2) {
		this.t2 = t2;
	}
	public String getT3() {
		return t3;
	}
	public void setT3(String t3) {
		this.t3 = t3;
	}
	public String getT4() {
		return t4;
	}
	public void setT4(String t4) {
		this.t4 = t4;
	}
	
	//分页参数
	public int getOffset() {
		return (page - 1) * limit;
	}
	public int getRows() {
		return limit;
	}
	public int getBegin() {
		return (page - 1) * limit + 1;
	}
	public int getEnd() {
		return page * limit;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
