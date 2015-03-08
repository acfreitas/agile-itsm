<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>

<!doctype html public "">
<html>
<head>
    <script type="text/javascript" src="${ctx}/fckeditor/fckeditor.js"></script>
    <%
        String id = request.getParameter("id");
    %>
	<%@include file="/include/header.jsp"%>

    <title><fmt:message key="citcorpore.comum.title"/></title>
    <%@include file="/include/menu/menuConfig.jsp" %>

    <%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
    <script type="text/javascript" src="${ctx}/js/ValidacaoUtils.js"></script>
    <script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>
    <script type="text/javascript" src="${ctx}/cit/objects/RequisicaoPessoalDTO.js"></script>
    <script type="text/javascript" src="${ctx}/cit/objects/TriagemRequisicaoPessoalDTO.js"></script>

    <style type="text/css">
        .destacado {
            border:1px solid #ddd;
            padding:4px 10px;
            border-left:none;
            background:#eee;
            font-size: 14px;
        }
        .table {
            border-left:1px solid #ddd;
            width: 100%;
        }

        .table th {
            border:1px solid #ddd;
            padding:4px 10px;
            border-left:none;
            background:#eee;
        }

        .table td {
            border:1px solid #ddd;
            padding:4px 10px;
            border-top:none;
            border-left:none;
        }

        .table1 {
        }

        .table1 th {
            border:1px solid #ddd;
            padding:4px 10px;
            background:#eee;
        }

        .table1 td {
        }

         div#main_container {
            margin: 0px 0px 0px 0px;
        }

        .container_16
        {
            width: 100%;
            margin: 0px 0px 0px 0px;

            letter-spacing: -4px;
        }
    </style>

    <script>
        function limpar(){
	    	window.location = '${ctx}/movimentacaoPessoal/movimentacaoPessoal.load';
	    }

    	      function desabilitarTela() {
	          var f = document.form;
	          for(i=0;i<f.length;i++){
	              var el =  f.elements[i];
	              if (el.type != 'hidden') {
	                  if (el.disabled != null && el.disabled != undefined) {
	                      el.disabled = 'disabled';
	                  }
	              }
	          }
	      }


	      addEvent(window, "load", load, false);
	      function load(){
	          document.form.afterLoad = function () {
	        	  dataAtual();
	          }
	      }

	     $(function() {
	        $("#POPUP_EMPREGADOS").dialog({
	            autoOpen : false,
	            width : 1000,
	            height : 600,
	            modal : true
	        });
	    });
	     function abrePopup() {
	 		$("#POPUP_EMPREGADOS").dialog("open");
	 	}

	 	function LOOKUP_EMPREGADOS_select(id,desc){
	 		document.form.restore({idEmpregado:id});
	 		$("#POPUP_EMPREGADOS").dialog("close");
	 	}

	    $(function() {
	        $("#POPUP_CURRICULO").dialog({
	            autoOpen : false,
	            width : 1000,
	            height : 600,
	            modal : true
	        });
	    });

	    function configuraPainelMovimentacao() {
				if(document.form.tipoMovimentacao[0].checked) {
		    		document.getElementById('divAlteracao').style.display = 'block';
		    		document.getElementById('divDemissao').style.display = 'none';
				}
				else {
		    		document.getElementById('divAlteracao').style.display = 'none';
		    		document.getElementById('divDemissao').style.display = 'block';
				}
	    }

	    function validar() {
        }

	    function getObjetoSerializado() {
            var obj = new CIT_RequisicaoPessoalDTO();
            HTMLUtils.setValuesObject(document.form, obj);
            return ObjectUtils.serializeObject(obj);
     	}

	    function dataAtual() {
	    	hoje = new Date();
	    	dia = hoje.getDate();
	    	mes = hoje.getMonth();
	    	ano = hoje.getFullYear();
	    	if (dia < 10)
	    	 dia = "0" + dia;
	    	if (mes < 10)
		     mes = "0" + (mes+1);
	    	if (ano < 2000)
	    	 ano = "19" + ano;

	    	document.getElementById('data').value = (dia+"/"+ mes +"/"+ano);
	    }

	    function restoreCargo() {
	 	   document.form.fireEvent("restoreCargo");
	 	}


</script>
</head>

<body>
 <div class="box grid_16 tabs" style='width:100%;margin: 0px 0px 0px 0px;'>
    <form name='form'
        action='${ctx}/pages/movimentacaoPessoal/movimentacaoPessoal'>
        <div class="columns clearfix">
        	<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico'/>
 			<input type='hidden' name='serializeTriagem' id='serializeTriagem'/>
        		<div class="col_100">
                   <div class="col_25">
                        <fieldset style="height: 70px;">
                            <label class="campoObrigatorio"><fmt:message key="Gestor" /></label>
                            <div>
                                <input type='text' name='responsavel' size='10' maxlength="100"/>
                            </div>
                        </fieldset>
                   </div>

                   <div class="col_25">
                        <fieldset style="height: 70px;">
                            <label class="campoObrigatorio"><fmt:message key="Nome Colaborador" /></label>
                            <div>
                                <input type='text' name='colaborador' size='10' style="cursor: pointer;" maxlength="100" onclick="abrePopup()"/>
                            </div>
                        </fieldset>
                   </div>
                   <div class="col_25">
                        <fieldset style="height: 70px;">
                            <label class="campoObrigatorio"><fmt:message key="Número Requisição Pessoal" /></label>
                            <div>
                                <input type='text' name='numero' size='10' maxlength="100"/>
                            </div>
                        </fieldset>
                   </div>
                   <div class="col_25">
	        	         <fieldset style="height: 70px;">
	                         <label class="campoObrigatorio"><fmt:message key="Data" /></label>
	                         <div>
	                             <input type='text' name='data' size="10" maxlength="10" class='Format[Date] Description[Data de Início]'/>
	                         </div>
	                     </fieldset>
	        	    </div>
                </div>

                <!--  --------------------------------------- ALTERAÇÃO  --------------------------------------- -->
                <div class="col_100" style="background:#F5F5F5">
                	<fieldset style="height: 50px;">
                		<div class="col_33">
                			<input type="radio" name="tipoMovimentacao" value="M" onclick="configuraPainelMovimentacao()"/>
                		</div>
                		<h2><fmt:message key="ALTERAÇÃO"/></h2>
                	</fieldset>
                </div>
                <div id="divAlteracao" style="display: none;">
        	    	        	  <div class="col_100">
	        	    <div class="col_33">
	        	         <fieldset style="height: 70px;">
	                         <label class="campoObrigatorio"><fmt:message key="Cargo" /></label>
	                         <div>
	                              <select name='idCargo'></select>
	                          </div>
	                     </fieldset>
	        	    </div>
	        	    <div class="col_33">
	        	         <fieldset style="height: 70px;">
	                         <label class="campoObrigatorio"><fmt:message key="Lotação" /></label>
	                         <div>
	                              <select name='idLotacao'></select>
	                          </div>
	                     </fieldset>
	        	    </div>
		        	 <div class="col_33">
	        	         <fieldset style="height: 70px;">
	                         <label class="campoObrigatorio"><fmt:message key="Centro de Resultado" /></label>
	                         <div>
	                              <select name='idCentroResultado'></select>
	                          </div>
	                     </fieldset>
	        	    </div>
	        	   </div>
	        	   <div class="col_100">
	        	    <div class="col_33">
	        	         <fieldset style="height: 70px;">
	                         <label class="campoObrigatorio"><fmt:message key="Projeto" /></label>
	                         <div>
	                              <select name='idProjeto'></select>
	                          </div>
	                     </fieldset>
	        	    </div>
		        	 <div class="col_33">
	        	         <fieldset style="height: 70px;">
	                         <label class="campoObrigatorio"><fmt:message key="Salário" /></label>
	                         <div>
	                              <input type='text' name='salario' size='10' maxlength="100" class='Format[Moeda]'/>
	                          </div>
	                     </fieldset>
	        	    </div>
	        	   </div>
	        	   <div class="col_100">
	        	     <fieldset style="height: 20px;">
	        	       <label class="campoObrigatorio"><fmt:message key="Justificativas" /></label>
	        	     </fieldset>
	        	   </div>
	        	   <div class="col_100">
					  <fieldset>
						  <div class="col_20">
							<fmt:message key="citcorporeRelatorio.comum.iniciativa" /><textarea rows="5" cols="122" name='iniciativa'></textarea>
						  </div>
						  <div class="col_20">
							<fmt:message key="citcorporeRelatorio.comum.aperfeicoamento" /><textarea rows="5" cols="122" name='aperfeicoamento'></textarea>
						  </div>
						  <div class="col_20">
							 <fmt:message key="citcorporeRelatorio.comum.comprometimento" /><textarea rows="5" cols="122" name='comprometimento'></textarea>
						  </div>
						  <div class="col_20">
							 <fmt:message key="citcorporeRelatorio.comum.integridade" /><textarea rows="5" cols="122" name='integridade'></textarea>
						  </div>
					  </fieldset>
					</div>
        	    </div>


        	     <!--  --------------------------------------- DEMISSÃO  --------------------------------------- -->
                <div class="col_100" style="background:#F5F5F5">
                    <fieldset style="height: 50px;">
                	    <div class="col_33">
                			<input type="radio" name="tipoMovimentacao" value="D" onclick="configuraPainelMovimentacao()"/>
                		</div>
                	    <h2><fmt:message key="DEMISSÃO"/></h2>
                	</fieldset>
                </div>
				<div id="divDemissao" style="display: none;">
        	      <div class="col_100">
	        	      	<div class="col_33">
		        	    	<fieldset style="height: 70px;">
		                         <input type="radio" name="tipoDemissao" value="I"/>
		                         <label class="campoNaoObrigatorio"><fmt:message key="Aviso Indenizado" /></label>
		                    </fieldset>
		               </div>
		               <div class="col_33">
		                    <fieldset style="height: 70px;">
		                         <input type="radio" name="tipoDemissao" value="T"/>
		                         <label class="campoNaoObrigatorio"><fmt:message key="Aviso Trabalhado" /></label>
		                    </fieldset>
		               </div>
		               <div class="col_33">
		                    <fieldset style="height: 70px;">
		                         <input type="radio" name="tipoDemissao" value="C"/>
		                         <label class="campoNaoObrigatorio"><fmt:message key="Término de Contrato" /></label>
		                     </fieldset>
		      			</div>
                   </div>
                   <div class="col_100">
					  <fieldset>
						  <label class="campoNaoObrigatorio"><fmt:message key="Observações" /></label>
						  <div>
							 <textarea rows="5" cols="122" name='observacoes'></textarea>
						  </div>
					  </fieldset>
				   </div>
        	    </div>
        </div>

    </form>


<div id="POPUP_EMPREGADOS" title="<fmt:message key="citcorpore.comum.pesquisa" />">
        <div class="box grid_16 tabs">
            <div class="toggle_container">
                <div id="tabs-2" class="block">
                    <div class="section">
                        <form name='formEmpregado' style="width: 540px">
                            <cit:findField formName='formEmpregado'
                                lockupName='LOOKUP_EMPREGADOS' id='LOOKUP_EMPREGADOS' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
                        </form>
                    </div>
                </div>
            </div>
        </div>
</div>
</div>
</body>

</html>
