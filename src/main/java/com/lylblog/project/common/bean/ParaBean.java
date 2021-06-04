/**   
* @Title: BaseParaBean.java
* @Package com.cshg.common.bean
* @Description: 
* @author yuanshuai
* @date Jan 4, 2017 10:45:43 AM
* @version V1.0
*/
package com.lylblog.project.common.bean;


import lombok.Data;

/**
 * @author YShuai
 *
 */
@Data
public class ParaBean {

	//分页选项
	protected int page = 1; //页码
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
		return (page - 1) * limit;
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
}
