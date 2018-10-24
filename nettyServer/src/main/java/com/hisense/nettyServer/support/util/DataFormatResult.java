package com.hisense.nettyServer.support.util;
/**
 * <p>Title: DataFormatResult<Result,Data,Info></p>
 * <p>Description:数据标准化结果</p>
 * <p>Company:青岛海信网络科技有限公司</p>
 * @author liangshan
 */

public class DataFormatResult<Result,Data,Info,Topic> {
	Result result;
	Data data;
	Info info;
	Topic topic;
	public DataFormatResult(Result result,Data data,Info info,Topic topic) {
		super();
		this.result=result;
		this.data=data;
		this.info=info;
		this.topic=topic;
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	public Info getInfo() {
		return info;
	}
	public void setInfo(Info info) {
		this.info = info;
	}
	public Topic getTopic() {
		return topic;
	}
	public void setTopic(Topic topic) {
		this.topic = topic;
	}

}
