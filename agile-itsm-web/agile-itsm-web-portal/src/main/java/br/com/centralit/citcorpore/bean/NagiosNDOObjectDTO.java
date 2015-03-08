package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class NagiosNDOObjectDTO implements IDto {
	private Integer object_id;
	private Integer instance_id;
	private Integer objecttype_id;
	private String name1;
	private String name2;
	private Integer is_active;

	public Integer getObject_id(){
		return this.object_id;
	}
	public void setObject_id(Integer parm){
		this.object_id = parm;
	}

	public Integer getInstance_id(){
		return this.instance_id;
	}
	public void setInstance_id(Integer parm){
		this.instance_id = parm;
	}

	public Integer getObjecttype_id(){
		return this.objecttype_id;
	}
	public void setObjecttype_id(Integer parm){
		this.objecttype_id = parm;
	}

	public String getName1(){
		return this.name1;
	}
	public void setName1(String parm){
		this.name1 = parm;
	}

	public String getName2(){
		return this.name2;
	}
	public void setName2(String parm){
		this.name2 = parm;
	}

	public Integer getIs_active(){
		return this.is_active;
	}
	public void setIs_active(Integer parm){
		this.is_active = parm;
	}

}
