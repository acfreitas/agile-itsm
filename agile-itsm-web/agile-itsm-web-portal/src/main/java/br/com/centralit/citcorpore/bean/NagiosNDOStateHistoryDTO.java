package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class NagiosNDOStateHistoryDTO implements IDto {
	private Integer statehistory_id;
	private Integer instance_id;
	private java.sql.Timestamp state_time;
	private Integer state_time_usec;
	private Integer object_id;
	private Integer state_change;
	private Integer state;
	private Integer state_type;
	private Integer current_check_attempt;
	private Integer max_check_attempts;
	private Integer last_state;
	private Integer last_hard_state;
	private String output;
	private String long_output;

	public Integer getStatehistory_id(){
		return this.statehistory_id;
	}
	public void setStatehistory_id(Integer parm){
		this.statehistory_id = parm;
	}

	public Integer getInstance_id(){
		return this.instance_id;
	}
	public void setInstance_id(Integer parm){
		this.instance_id = parm;
	}

	public java.sql.Timestamp getState_time(){
		return this.state_time;
	}
	public void setState_time(java.sql.Timestamp parm){
		this.state_time = parm;
	}

	public Integer getState_time_usec(){
		return this.state_time_usec;
	}
	public void setState_time_usec(Integer parm){
		this.state_time_usec = parm;
	}

	public Integer getObject_id(){
		return this.object_id;
	}
	public void setObject_id(Integer parm){
		this.object_id = parm;
	}

	public Integer getState_change(){
		return this.state_change;
	}
	public void setState_change(Integer parm){
		this.state_change = parm;
	}

	public Integer getState(){
		return this.state;
	}
	public void setState(Integer parm){
		this.state = parm;
	}

	public Integer getState_type(){
		return this.state_type;
	}
	public void setState_type(Integer parm){
		this.state_type = parm;
	}

	public Integer getCurrent_check_attempt(){
		return this.current_check_attempt;
	}
	public void setCurrent_check_attempt(Integer parm){
		this.current_check_attempt = parm;
	}

	public Integer getMax_check_attempts(){
		return this.max_check_attempts;
	}
	public void setMax_check_attempts(Integer parm){
		this.max_check_attempts = parm;
	}

	public Integer getLast_state(){
		return this.last_state;
	}
	public void setLast_state(Integer parm){
		this.last_state = parm;
	}

	public Integer getLast_hard_state(){
		return this.last_hard_state;
	}
	public void setLast_hard_state(Integer parm){
		this.last_hard_state = parm;
	}

	public String getOutput(){
		return this.output;
	}
	public void setOutput(String parm){
		this.output = parm;
	}

	public String getLong_output(){
		return this.long_output;
	}
	public void setLong_output(String parm){
		this.long_output = parm;
	}
	public String getStatus(){
		if (this.getState() != null){
			if (this.getState().intValue() == 2){ //CRITICAL
				return "DOWN";
			}else {
				return "UP";
			}
		}
		return "";
	}	
	
}
