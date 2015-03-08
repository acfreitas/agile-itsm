package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class CentreonLogDTO implements IDto {
	private Integer log_id;
	private Long ctime;
	private String host_name;
	private String service_description;
	private String status;
	private String output;
	private String notification_cmd;
	private String notification_contact;
	private String type;
	private Integer retry;
	private Integer msg_type;
	private Integer instance;

	public Integer getLog_id(){
		return this.log_id;
	}
	public void setLog_id(Integer parm){
		this.log_id = parm;
	}

	public String getHost_name(){
		return this.host_name;
	}
	public void setHost_name(String parm){
		this.host_name = parm;
	}

	public String getService_description(){
		return this.service_description;
	}
	public void setService_description(String parm){
		this.service_description = parm;
	}

	public String getStatus(){
		return this.status;
	}
	public void setStatus(String parm){
		this.status = parm;
	}

	public String getOutput(){
		return this.output;
	}
	public void setOutput(String parm){
		this.output = parm;
	}

	public String getNotification_cmd(){
		return this.notification_cmd;
	}
	public void setNotification_cmd(String parm){
		this.notification_cmd = parm;
	}

	public String getNotification_contact(){
		return this.notification_contact;
	}
	public void setNotification_contact(String parm){
		this.notification_contact = parm;
	}

	public String getType(){
		return this.type;
	}
	public void setType(String parm){
		this.type = parm;
	}

	public Integer getRetry(){
		return this.retry;
	}
	public void setRetry(Integer parm){
		this.retry = parm;
	}

	public Integer getMsg_type(){
		return this.msg_type;
	}
	public void setMsg_type(Integer parm){
		this.msg_type = parm;
	}

	public Integer getInstance(){
		return this.instance;
	}
	public void setInstance(Integer parm){
		this.instance = parm;
	}
	public Long getCtime() {
		return ctime;
	}
	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}

}
