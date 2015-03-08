<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.EmpregadoDTO"%>
<!doctype html public "">
<html>
<head>
<script type="text/javascript" src="${ctx}/fckeditor/fckeditor.js"></script>
<%
	String id = request.getParameter("id");
%>
	<%@include file="/include/header.jsp"%>
	<title><fmt:message key="citcorpore.comum.title"/></title>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	<script type="text/javascript" src="${ctx}/js/ValidacaoUtils.js"></script>
    <script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>
	<script type="text/javascript" src="${ctx}/js/UploadUtils.js"></script>
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
        addEvent(window, "load", load, false);
	    function load(){
	    	converteCpfParaPadraoIngles();
	          document.form.afterLoad = function () {
	          	  habilitarQtdeFilhos();
	          	  habilitarListaDeficiencias();
	          }
	    }
	    /*Bruno.Aquino : Foi retirado o class de mascaras do campo cpf e inserido ao carregar a página a mascara do jquery para as linguas: Português e espanhol, para inglês não haverá mascara */
		function converteCpfParaPadraoIngles(){
			if('<%=request.getSession().getAttribute("locale")%>'=="en"){
				$("#cpf").unmask();
			}else{
				$("#cpf").mask("999.999.999-99");
			}
	    }
     	habilitarListaDeficiencias = function()
		{
			if(document.form.portadorNecessidadeEspecial[1].checked)
				document.getElementById('divDeficiencias').style.display = 'block';
			else
				document.getElementById('divDeficiencias').style.display = 'none';
		}

		function habilitarQtdeFilhos()
		{
			if(document.form.filhos[1].checked)
				document.getElementById('divFilhos').style.display = 'block';
			else
				document.getElementById('divFilhos').style.display = 'none';
		}

     	marcaTelefone = function(row, obj){
		};

		marcaEmail = function(row, obj){
		};
		marcaFormacao = function(row, obj){
		};
		marcaCertificacao = function(row, obj){
		};
		marcaIdioma = function(row, obj){
		};

		marcaEndereco = function(row, obj){
		};

</script>

</head>
<body>
    <div class="box grid_16 tabs" style='margin: 0px 0px 0px 0px;'>
			<div class="columns clearfix">
				<form name='form' action='${ctx}/pages/curriculo/curriculo'>
					<input type='hidden' name='idCurriculo' id='idCurriculo'/>
				        	<div class="col_100">
				        		<div class="col_80">
				        	 		<div class="col_100">
						        	    <div class="col_15">
						        	         <fieldset style="height: 70px;">
						                         <label ><fmt:message key="CPF" /></label>
						                         <div>
						                              <input type='text' name='cpf' id='cpf' size='15' maxlength="14" readonly='readonly' />
						                          </div>
						                     </fieldset>
						        	    </div>
						        	    <div class="col_66">
						        	         <fieldset style="height: 70px;">
						                         <label ><fmt:message key="Nome" /></label>
						                         <div>
						                             <input type='text' name='nome' size="50" maxlength="100"   readonly='readonly' />
						                          </div>
						                     </fieldset>
						        	    </div>
							        	 <div class="col_15">
						        	         <fieldset style="height: 70px;">
						                         <label ><fmt:message key="Data de nascimento" /></label>
						                         <div>
						                             <input type='text' name='dataNascimento' size="10" maxlength="10"  readonly='readonly' class='Format[Date] Description[Data de Nascimento]' onblur='calculaIdade()'  />
						                         </div>
						                         <div>
						                             <span id='spnIdade' style='color: red; font-weight: bold' style='display:none'></span>
						                         </div>
						                     </fieldset>
						        	    </div>
					        	    </div>
				        	 		<div class="col_100">
						        	    <div class="col_15">
						        	    	<fieldset style="height: 70px;">
						                         <label ><fmt:message key="Sexo" /></label>
						                         <div>
						                             <input type='radio' name='sexo' value='M' disabled='disabled'/>Masculino
						                         </div>
						                         <div>
						                             <input type='radio' name='sexo' value='F' disabled='disabled'/>Feminino
						                          </div>
						                     </fieldset>
						        	    </div>
						        	    <div class="col_20">
						        	    	<fieldset style="height: 70px;">
						                         <label ><fmt:message key="Estado Civil" /></label>
						                         <div>
						                             <select name='estadoCivil' disabled='disabled'>
													    <option value="0" >-- Selecione --</option>
														<option value="1" >Solteiro(a)</option>
														<option value="2" >Casado(a)</option>
						                                <option value="3" >Companheiro(a)</option>
						                                <option value="4" >União estável</option>
														<option value="5" >Separado(a)</option>
						                                <option value="6" >Divorciado(a)</option>
														<option value="7" >Viuvo(a)</option>
														</select>
						                          </div>
						                     </fieldset>
						        	    </div>
						        	    <div class="col_16">
						        	    	<fieldset style="height: 70px;">
						                         <label ><fmt:message key="Possui filho(s)" /></label>
						                         <div>
						                             <input type="radio" name="filhos" value="N"
						                                    class="Valid[Required] Description[Possui filho(s)]"
						                                    onclick="habilitarQtdeFilhos()" disabled='disabled'/>Não
						                         </div>
						                         <div>
						                             <input type="radio" name="filhos" value="S"
						                                    class="Valid[Required] Description[Possui filho(s)]"
						                                    onclick="habilitarQtdeFilhos()" disabled='disabled'/>Sim
						                         </div>
						                     </fieldset>
						        	    </div>
						        	    <div class="col_15" id="divFilhos" style="display: none;">
						        	    	<fieldset style="height: 70px;">
						                         <label ><fmt:message key="Quantos" /></label>
						                         <div>
						                            <select name="qtdeFilhos" disabled='disabled'>
							                         <option value="">-- Selecione -- </option>
							                         <option value="1">1</option>
							                         <option value="2">2</option>
							                         <option value="3">3</option>
							                         <option value="4">4</option>
							                         <option value="5">5 ou mais</option>
							                        </select>
						                          </div>
						                     </fieldset>
						        	    </div>
						        	    <div class="col_30">
						        	    	<fieldset style="height: 70px;">
						                         <label ><fmt:message key="Naturalidade" /></label>
						                         <div>
						                              <select name='idNaturalidade' disabled='disabled'>
						  								</select>
						                          </div>
						                     </fieldset>
						        	    </div>
				        	    	</div>
						        	 <div class="col_100">
						        	    <div class="col_40">
						        	    	<fieldset style="height: 70px;">
						                         <label ><fmt:message key="Cidade Natal" /></label>
						                         <div>
						                              <input type='text' name='cidadeNatal' size="50" maxlength="80"  readonly='readonly'  />
						                          </div>
						                     </fieldset>
						        	    </div>
						        	    <div class="col_33">
						        	    	<fieldset style="height: 70px;">
						                         <label ><fmt:message key="Portador de necessidades especiais" /></label>
						                         <div>
						                             <input type="radio" name="portadorNecessidadeEspecial" value="N"  disabled='disabled'
						                                    class="Valid[Required] Description[Portador de necessidade especial]"
						                                    onclick="habilitarListaDeficiencias()"/>Não
						                         </div>
						                         <div>
						                             <input type="radio" name="portadorNecessidadeEspecial" value="S"  disabled='disabled'
						                                    class="Valid[Required] Description[Portador de necessidade especial]"
						                                    onclick="habilitarListaDeficiencias()"/>Sim
						                         </div>
						                     </fieldset>
						        	    </div>
						        	    <div class="col_20" id="divDeficiencias" style="display: none;">
						        	    	<fieldset style="height: 70px;">
						                         <label ><fmt:message key="Qual" /></label>
						                         <div>
						                            <select name="idItemListaTipoDeficiencia" disabled='disabled'>
						                             <option value="">-- Selecione -- </option>
						                             <option value="1">Auditiva</option>
						                             <option value="2">Física</option>
						                             <option value="3">Mental</option>
						                             <option value="4">Múltipla</option>
						                             <option value="5">Visual</option>
						                            </select>
						                          </div>
						                     </fieldset>
						        	    </div>
				        	    	</div>
				        		</div>
				        	  	<div id='divFoto' class="col_20">
									  <div id='divImgFoto' style='border: 1px solid black; width: 165px; height: 170px;'>
								      </div>
								</div>
							 </div>
				             <div class="col_50">
				               <fieldset style="height: 130px;">
				             	<label><fmt:message key="entrevista.observacoes" /></label>
				             	<textarea rows="5" cols="122" name='observacoesEntrevista' readonly='readonly'></textarea>
				               </fieldset>
				             </div>
				             <div class="col_100">
				        	 	<div class="col_33">
				        	 	  <fieldset>
				  					<label  style='font-size:16px'><fmt:message key="Emails" /></label>
		  								<div style='height: 100px; overflow: auto; border: 1px solid black'>
					  								<table id='tblEmail' class="table" width='100%'>
					  									<tr>
					  										<th width="1%">
					  											&nbsp;
					  										</th>
					  										<th width="5%">
					  											Principal
					  										</th>
					  										<td>
					  											E-mail
					  										</th>
					  									</tr>
					  								</table>
				  								</div>
				  				</fieldset>
				        	 	</div>
				        	 	<div class="col_33">
				        	 	  <fieldset>
				  					<label  style='font-size:16px'><fmt:message key="Telefones" /></label>
		  								<div style='height: 100px; overflow: auto; border: 1px solid black'>
			  								<table id='tblTelefones' class="table" width='100%'>
			  									<tr>
			  										<th width="1%">
			  											&nbsp;
			  										</th>
			  										<th width="43%">
			  											Tipo
			  										</th>
			  										<th width="10%">
			  											DDD
			  										</th>
			  										<th>
			  											Número
			  										</th>
			  									</tr>
			  								</table>
		  								</div>
				  				</fieldset>
								</div>

				        	 	<div class="col_33">
				        	 	  <fieldset>
				  					<label style='font-size:16px'><fmt:message key="Certificações" /></label>
					  								<div style='height: 100px; overflow: auto; border: 1px solid black'>
						  								<table id='tblCertificacao' class="table" width='100%'>

						  									<tr>
						  										<th width="1%">
						  											&nbsp;
						  										</th>
						  										<th>
						  											Descrição
						  										</th>
						  										<th width="30%">
						  											Versão
						  										</th>
						  										<th width="25%">
						  											Validade
						  										</th>
						  									</tr>
						  								</table>
					  								</div>
					  				</fieldset>

				        	 	  </div>

				        	 </div>

				        	 <div class="col_100">
				        	    <div class="col_33">
				        	       <fieldset>
				  					<label  style='font-size:16px'><fmt:message key="Endereços" /></label>
					  								<div style='height: 100px; overflow: auto; border: 1px solid black'>
				  								<table id='tblEnderecos' class="table" width='100%'>
				  									<tr>
				  										<th width="1%">
				  											&nbsp;
				  										</th>
				  										<th width="3%">
				  											Corresp.
				  										</th>
				  										<th width="15%">
				  											Tipo
				  										</th>
				  										<th >
				  											Endereço
				  										</th>
				  									</tr>
				  								</table>
				  								</div>
				 				</fieldset>
				        	    </div>

				    			<div class="col_33">
				    				<fieldset>
				  					<label  style='font-size:16px'><fmt:message key="Idiomas" /></label>
				  								<div style='height: 100px; overflow: auto; border: 1px solid black'>
					  								<table id='tblIdioma' class="table" width='100%'>
					  									<tr>
					  										<th width="1%">
					  											&nbsp;
					  										</th>
					  										<th width="50%">
					  											Descrição
					  										</th>
					  										<th>
					  											Níveis de conhecimento
					  										</th>
					  									</tr>
					  								</table>
					  							</div>
				  				</fieldset>
				    			</div>

				             	<div class="col_33">
				             		<fieldset>
				  					<label  style='font-size:16px'><fmt:message key="Formação" /></label>
					  								<div style='height: 100px; overflow: auto; border: 1px solid black'>
						  								<table id='tblFormacao' class="table" width='100%'>
						  									<tr>
						  										<th width="1%">
						  											&nbsp;
						  										</th>
						  										<th width="15%">
						  											Formação&nbsp;
						  										</th>
						  										<th width="30%">
						  											&nbsp;Instituição&nbsp;
						  										</th>
						  										<th width="15%">
						  											&nbsp;Situação&nbsp;
						  										</th>
						  										<th>
						  											&nbsp;Descrição
						  										</th>
						  									</tr>
						  								</table>
					  								</div>
					  				</fieldset>
				             	</div>
				             </div>
								<div class="col_100">
										<fieldset>
										    <label  style='font-size:16px'><fmt:message key="Currículo"/></label>
											<cit:uploadControl  style="height:50%;width:100%; border-bottom:1px solid #DDDDDD ; border-top:1px solid #DDDDDD "  title="<fmt:message key='citcorpore.comum.anexos' />" id="uploadAnexos" form="document.form" action="/pages/upload/upload.load" disabled="true" />
										</fieldset>
								</div>
				         </form>
		 </div>
    </div>
</body>

<%@include file="/include/footer.jsp"%>
</html>
