<div class="span8">
	<div class="row-fluid">
		<div class="span8">
			<label class="campoObrigatorio strong"><fmt:message key="candidato.nomeCompleto" /></label>
			<input id="nome" name="nome" value="${nome}" style="width: 100% !important;" type='text' maxlength="150" class="Valid[Required] Description[citcorpore.comum.nome]" />
		</div>
	
		<div class="span4">
			<label class="campoObrigatorio strong"><fmt:message key="colaborador.cpf" /></label>
			<input id="cpf" type="text" name="cpf" value="${cpf}" style="width: 100% !important;" maxlength="14" class="Valid[Required] Description[colaborador.cpf]"  onclick="cpf()" onblur="validaCpf()"/>
		</div>
	</div>
	
	<div class="row-fluid">
		<div class="span8">
			<label class="campoObrigatorio strong"><fmt:message key="avaliacaoFonecedor.email" /></label>
			<input id="email" name="email" value="${email}" style="width: 100% !important;" type='text' maxlength="150" class="Valid[Required] Description[avaliacaoFonecedor.email]" onblur="validaEmail()"/>
		</div>
	</div>
	<div id="recebeDiv">
		<div id="divSenha">
			<div class="row-fluid">
				<div class="span3">
					<label class="campoObrigatorio strong"><fmt:message key="eventoItemConfiguracao.senha" /></label>
					<input id="senha" name="senha" type="password" maxlength="20" onblur="testaSenha()" />
				</div>
		
				<div class="span3">
					<label class="campoObrigatorio strong"><fmt:message key="trabalheConosco.confirmacaoSenha" /></label>
					<input id="senha2" name="senha2" type="password" maxlength="20" onblur="testaSenha()"/>
				</div>
			</div>
		</div>
	</div>
	<div id="divAlterarSenha" style="display: none;">
		<fieldset>
			<label onclick="alterarSenha()" style="cursor: pointer; margin-top: 5px; margin-bottom: 5px;"><img alt="" src="${ctx}/template_new/images/icons/small/util/alterarsenha.png"><fmt:message key="usuario.alterarSenha"/></label>
		</fieldset>
	</div>
</div>