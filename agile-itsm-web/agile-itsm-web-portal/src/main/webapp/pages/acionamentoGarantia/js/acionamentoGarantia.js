/**
 * 
 */

        addEvent(window, "load", load, false);
        function load(){        
            document.form.afterLoad = function () {
            	parent.escondeJanelaAguarde(); 
            }    
        }
    
        $(function() {
            $("#POPUP_ITEM_REQUISICAO").dialog({
                autoOpen : false,
                width : 580,
                height : 550,
                modal : true
            });
        }); 
                
 
        function gerarImg (row, obj){
            row.cells[0].innerHTML = '<img style="cursor: pointer;" src=src/>';
        };
            
        function editarItem(row, obj) {
            document.formItemRequisicao.clear();
            HTMLUtils.setValues(document.formItemRequisicao,'item',obj);
            document.getElementById('item#index').value = row.rowIndex;
            document.getElementById('item#dataEntrega').value = obj.dataEntrega;
            $('#POPUP_ITEM_REQUISICAO').dialog('open');
        } 
        
        function getObjetoSerializado() {
            var obj = new CIT_RequisicaoProdutoDTO();
            HTMLUtils.setValuesObject(document.form, obj);
            var itensRequisicao = HTMLUtils.getObjectsByTableId('tblItensRequisicao');
            obj.itensEntrega_serialize = ObjectUtils.serializeObjects(itensRequisicao);
            return ObjectUtils.serializeObject(obj);
        }