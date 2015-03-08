/*
 * Função responsável por selecionar todos checkbox ou desmarcar todos
 * @author: Marco Nascimento
 * @date: 23/05/2008
 * marcotulio.nascimento@gmail.com / knox872003@yahoo.com.br
 */

/*
 * Para utilizar esta função;
 * Utilize o modelo:
 * CheckBox de checkAll
 * <input type="checkbox" id="chAll" name="chAll" onclick="checkAll('formControleLotes');" >
 * O Atributo name deve ser identico ao deste modelo e o formulário deve ser relativo  
 */
 
/* Check ALL true/false */
function checkAll(objForm) {
 	var qtdEl = document.forms(objForm).elements.length;
	var el = document.forms(objForm).elements;
	var go = false;
	for(i=0; qtdEl > i; i++){
		if(el[i].type == 'checkbox' && !el[i].disabled){
			if(el[i].name == 'chAll' && !el[i].checked){
				go = true;
			}
			if(go==false){
				el[i].checked = true;
			} else {
				el[i].checked = false;
			}
		}
	}
}