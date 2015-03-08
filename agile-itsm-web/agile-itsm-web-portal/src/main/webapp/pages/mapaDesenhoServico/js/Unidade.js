/**
 * @author breno.guimaraes
 */

function Unidade(_id, _nome){
	var id = _id;
	var nome = _nome;
	
	this.getNome = function(){
		return nome;
	};
	
	this.getId = function(){
		return id;
	};
}