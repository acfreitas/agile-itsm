/**
 *  @author breno.guimaraes
 */

function Servico(_id, _nome){
	var id = _id;
	var nome = _nome;
	
	this.setNome = function(value){
		nome = value;
	};
	
	this.getNome = function(){
		return nome;
	};
}