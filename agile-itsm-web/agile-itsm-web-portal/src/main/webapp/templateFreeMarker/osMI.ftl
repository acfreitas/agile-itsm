<html>
<head>
</head>
<body>
<table width='100%' cellspacing="0" cellpadding="0">
	<tr>
		<td colspan='5' style='border:1px solid black; background-color:#F0F0F0; font-family: arial; font-size: 11px;'>1 - ESPECIFICAÇÃO DOS PRODUTOS/SERVIÇOS E VOLUMES</td>
	</tr>
	<tr>
		<td style='border:1px solid black;font-family: arial; font-size: 11px; background-color:#6600FF; color: white;text-align:center'>Atividades</td>
		<td style='border:1px solid black;font-family: arial; font-size: 11px; background-color:#6600FF; color: white;text-align:center'>Produtos</td>
		<td style='border:1px solid black;font-family: arial; font-size: 11px; background-color:#6600FF; color: white;text-align:center'>Métrica (HST)</td>
		<td style='border:1px solid black;font-family: arial; font-size: 11px; background-color:#6600FF; color: white;text-align:center'>Data Entrega</td>
		<td style='border:1px solid black;font-family: arial; font-size: 11px; background-color:#6600FF; color: white;text-align:center'>Total</td>
	</tr>
<#assign qtdeTotal = 0>
<#list tasks as task>
	<#if task.tarefaFase>
	<tr>
		<td colspan='5' style='border:1px solid black;font-family: arial; font-size: 11px;background-color:#6600FF; color: white'>${task.nomeTarefaNivelPonto}</td>		
	</tr>
	<#else>
	<tr>
		<td style='border:1px solid black;font-family: arial; font-size: 11px;'>${task.nomeTarefaNivelPonto}</td>
		<td style='border:1px solid black;font-family: arial; font-size: 11px;'>${task.nomesProdutos}</td>
		<td style='border:1px solid black;font-family: arial; font-size: 11px;'>
			<table width='100%' cellspacing="0" cellpadding="0">
				<#list task.colPerfis as role>
					<tr>
						<td style='font-family: arial; font-size: 11px;'>
							${role.nomePerfilContrato}:
						</td>
						<td style='font-family: arial; font-size: 11px;text-align:right'>
							${role.tempoAlocHorasTotalStr}
						</td>					
					</tr>
				</#list>
			</table>
		</td>
		<td style='border:1px solid black;font-family: arial; font-size: 11px;text-align:center'>${task.dataFim}</td>
		<td style='border:1px solid black;font-family: arial; font-size: 11px;text-align:right'>${task.custoPerfilStr}</td>
		<#assign qtdeTotal = qtdeTotal + task.custoPerfil>
	</tr>	
	</#if>	
</#list>
	<tr>
		<td colspan='2' style='border:1px solid black;font-family: arial; font-size: 11px;background-color:#F0F0F0;'>TOTAL</td>
		<td style='border:1px solid black;font-family: arial; font-size: 11px;background-color:#F0F0F0;'>
			<table width='100%' cellspacing="0" cellpadding="0">
				<#list rolesValues as roleValue>
					<tr>
						<td style='font-family: arial; font-size: 11px;'>${roleValue.nomePerfilContrato}:</td>
						<td style='font-family: arial; font-size: 11px;text-align:right'>${roleValue.tempoAlocHorasTotalStr}</td>
					</tr>
				</#list>
			</table>
		</td>
		<td style='border:1px solid black;font-family: arial; font-size: 11px;text-align:center;background-color:#F0F0F0;'>${end}</td>
		<td style='border:1px solid black;font-family: arial; font-size: 11px;text-align:right;background-color:#F0F0F0;'>${qtdeTotal?string("###,###,##0.00")}</td>
	</tr>
</table>
<br/>

<table width='100%' cellspacing="0" cellpadding="0">
	<tr>
		<td style='border:1px solid black;font-family: arial; font-size: 11px;background-color:#F0F0F0'>2 - INSTRUÇÕES COMPLEMENTARES</td>
	</tr>
	<tr>
		<td style='border:1px solid black;font-family: arial; font-size: 11px;'>${projectDTO.detalhamentoFmt}</td>
	</tr>	
</table>
<br/>

<table width='100%' cellspacing="0" cellpadding="0">
	<tr>
		<td colspan='4' style='border:1px solid black;font-family: arial; font-size: 11px;background-color:#F0F0F0'>3 - CRONOGRAMA FÍSICO/ FINANCEIRO</td>
	</tr>
	<tr>
		<td style='border:1px solid black;font-family: arial; font-size: 11px; background-color:#6600FF; color: white'>Mês de referência</td>
		<td style='border:1px solid black;font-family: arial; font-size: 11px; background-color:#6600FF; color: white'>Entregáveis</td>
		<td style='border:1px solid black;font-family: arial; font-size: 11px; background-color:#6600FF; color: white'>Quantidade por Perfil/HST</td>
		<td style='border:1px solid black;font-family: arial; font-size: 11px; background-color:#6600FF; color: white'>VALOR R$</td>
	</tr>
<#assign qtdeTotal = 0>
<#list finMilestones as fin>
	<tr>
		<td style='border:1px solid black;font-family: arial; font-size: 11px;'>${fin.nomeMarcoPag}</td>
		<td style='border:1px solid black;font-family: arial; font-size: 11px;'>
			<table width='100%' cellspacing="0" cellpadding="0">
				<#list fin.colProdutosByMarcosFin as product>
					<tr>
						<td style='font-family: arial; font-size: 11px;'>
							${product.nomeProduto}
						</td>					
					</tr>
				</#list>
			</table>		
		</td>
		<#assign qtdeTotalMarco = 0>
		<td style='border:1px solid black;font-family: arial; font-size: 11px;'>
			<table width='100%' cellspacing="0" cellpadding="0">
				<#list fin.colPerfisByMarcosFin as role>
					<tr>
						<td style='font-family: arial; font-size: 11px;'>
							${role.nomePerfilContrato}:
						</td>
						<td style='font-family: arial; font-size: 11px;text-align:right'>
							${role.tempoAlocHorasTotalStr}
						</td>					
					</tr>
					<#assign qtdeTotalMarco = qtdeTotalMarco + role.custoTotalPerfil>
				</#list>
				<tr>
					<td style='font-family: arial; font-size: 11px;'>
						TOTAL:
					</td>
					<td style='font-family: arial; font-size: 11px;text-align:right'>
						${fin.tempoAlocHorasTotalStr}
					</td>					
				</tr>				
			</table>		
		</td>
		<td style='border:1px solid black;font-family: arial; font-size: 11px;text-align:right'>${qtdeTotalMarco?string("###,###,##0.00")}</td>
		<#assign qtdeTotal = qtdeTotal + fin.custoPerfil>
	</tr>
</#list>
	<tr>
		<td colspan='2' style='border:1px solid black;font-family: arial; font-size: 11px;background-color:#CCFFFF;'>TOTAL</td>
		<td style='border:1px solid black;font-family: arial; font-size: 11px;background-color:#CCFFFF;'>
		<table width='100%' cellspacing="0" cellpadding="0">
		<#list rolesValues as roleValue>
			<tr>
				<td style='font-family: arial; font-size: 11px;'>${roleValue.nomePerfilContrato}:</td>
				<td style='font-family: arial; font-size: 11px;text-align:right'>${roleValue.tempoAlocHorasTotalStr}</td>
			</tr>
		</#list>
			<tr>
				<td style='font-family: arial; font-size: 11px;'>TOTAL:</td>
				<td style='font-family: arial; font-size: 11px;text-align:right'>${totalTempoAloc}</td>
			</tr>		
		</table>		
		</td>
		<td style='border:1px solid black;font-family: arial; font-size: 11px;text-align:right;background-color:#CCFFFF;'>${qtdeTotal?string("###,###,##0.00")}</td>
	</tr>
</table>
<br/>

<table width='100%' cellspacing="0" cellpadding="0">
	<tr>
		<td colspan='5' style='border:1px solid black;font-family: arial; font-size: 11px;background-color:#F0F0F0'>4 - QUADRO RESUMO DOS RECURSOS INVESTIDOS</td>
	</tr>
	<tr>
		<td style='border:1px solid black;font-family: arial; font-size: 11px;background-color:#CCFFFF;'><b>Perfil de Serviços</b></td>
		<td style='border:1px solid black;font-family: arial; font-size: 11px;text-align:right;background-color:#CCFFFF;'><b>HST</b></td>
		<td style='border:1px solid black;font-family: arial; font-size: 11px;text-align:right;background-color:#CCFFFF;'><b>VALOR R$</b></td>
	</tr>	
	<#assign qtdeTotal = 0>
	<#assign qtdeTotalMin = 0>
	<#list rolesValues as roleValue>
		<tr>
			<td style='border:1px solid black;font-family: arial; font-size: 11px;'>${roleValue.nomePerfilContrato}</td>
			<td style='border:1px solid black;font-family: arial; font-size: 11px;text-align:right'>${roleValue.tempoAlocHorasTotalStr}</td>
			<td style='border:1px solid black;font-family: arial; font-size: 11px;text-align:right'>${roleValue.custoTotalPerfilStr}</td>
		</tr>
		<#assign qtdeTotal = qtdeTotal + roleValue.custoTotalPerfil>
		<#assign qtdeTotalMin = qtdeTotalMin + roleValue.tempoAlocMinutosTotal>
	</#list>
	<#assign qtdeTotalMin = qtdeTotalMin / 60>
	<tr>
		<td style='border:1px solid black;font-family: arial; font-size: 11px;background-color:#CCFFFF;'><b>TOTAL</b></td>
		<td style='border:1px solid black;font-family: arial; font-size: 11px;text-align:right;background-color:#CCFFFF;'><b>${qtdeTotalMin?string("###,###,##0.00")}</b></td>
		<td style='border:1px solid black;font-family: arial; font-size: 11px;text-align:right;background-color:#CCFFFF;'><b>${qtdeTotal?string("###,###,##0.00")}</b></td>
	</tr>	
</table>
<br/>

<table width='100%' cellspacing="0" cellpadding="0">
	<tr>
		<td colspan='2' style='border:1px solid black;font-family: arial; font-size: 11px;background-color:#F0F0F0'>5 - DOCUMENTOS ENTREGUES</td>
	</tr>
	<tr>
		<td style='border:1px solid black;font-family: arial; font-size: 11px;'>
			(&nbsp;&nbsp;&nbsp;)___________________________________________________________<br>
			(&nbsp;&nbsp;&nbsp;)___________________________________________________________<br>
			(&nbsp;&nbsp;&nbsp;)___________________________________________________________<br>
		</td>
		<td style='border:1px solid black;font-family: arial; font-size: 11px;'>
			(&nbsp;&nbsp;&nbsp;)___________________________________________________________<br>
			(&nbsp;&nbsp;&nbsp;)___________________________________________________________<br>
			(&nbsp;&nbsp;&nbsp;)___________________________________________________________<br>
		</td>		
	</tr>
</table>
<br/>

<table width='100%' cellspacing="0" cellpadding="0">
	<tr>
		<td colspan='3' style='border:1px solid black;font-family: arial; font-size: 11px;background-color:#F0F0F0'>6 - DATAS E PRAZOS</td>
	</tr>
	<tr>
		<td style='border:1px solid black;font-family: arial; font-size: 11px;background-color:#CCFFFF;text-align:center;'>Data Prevista para Início dos Produtos / Serviços</td>
		<td style='border:1px solid black;font-family: arial; font-size: 11px;background-color:#CCFFFF;text-align:center;'>Data Prevista para Entrega dos Produtos / Serviços</td>
		<td style='border:1px solid black;font-family: arial; font-size: 11px;background-color:#CCFFFF;text-align:center;'>Prazo Total do Contrato (com a Garantia)</td>
	</tr>	
	<tr>
		<td style='border:1px solid black;font-family: arial; font-size: 11px;text-align:center;'>${init}</td>
		<td style='border:1px solid black;font-family: arial; font-size: 11px;text-align:center;'>${end}</td>
		<td style='border:1px solid black;font-family: arial; font-size: 11px;text-align:center;'>${duration?string("###,###,##0")}</td>
	</tr>	
</table>
<br/>

<table width='100%' cellspacing="0" cellpadding="0">
	<tr>
		<td colspan='2' style='border:1px solid black;font-family: arial; font-size: 11px;background-color:#F0F0F0'>7 - CIÊNCIA</td>
	</tr>
	<tr>
		<td colspan='2' style='border:1px solid black;font-family: arial; font-size: 11px;background-color:#CCFFFF;text-align:center;'>CONTRATANTE</td>
	</tr>
	<tr>
		<td width='50%' style='border:1px solid black;font-family: arial; font-size: 11px;background-color:#CCFFFF;text-align:center;'>Área Requisitante da Solução</td>
		<td width='50%' style='border:1px solid black;font-family: arial; font-size: 11px;background-color:#CCFFFF;text-align:center;'>Gestor do Contrato</td>
	</tr>
	<tr>
		<td width='50%' style='border:1px solid black;font-family: arial; font-size: 11px;text-align:center;'><br/><br/><br/>
			JOÃO MARCELO ALVES DE ANDRADE<br/>
			Matrícula:154613		
		</td>
		<td width='50%' style='border:1px solid black;font-family: arial; font-size: 11px;text-align:center;'><br/><br/><br/>
			FLÁVIO FERREIRA DOS SANTOS<br/>
			Matrícula: 1777179		
		</td>
	</tr>
	<tr>
		<td colspan='2' style='border:1px solid black;font-family: arial; font-size: 11px;background-color:#CCFFFF;text-align:center;'>Gestor de Tecnologia da Informação</td>
	</tr>	
	<tr>
		<td colspan='2' style='border:1px solid black;font-family: arial; font-size: 11px;text-align:center;'><br/><br/><br/>
			EDUARDO MENDONÇA DA SILVA<br/>
            Matrícula:1780203
		</td>
	</tr>	
	<tr>
		<td colspan='2' style='border:1px solid black;font-family: arial; font-size: 11px;background-color:#CCFFFF;text-align:center;'>CONTRATADA</td>
	</tr>	
	<tr>
		<td colspan='2' style='border:1px solid black;font-family: arial; font-size: 11px;background-color:#CCFFFF;text-align:center;'>Preposto</td>
	</tr>	
	<tr>
		<td colspan='2' style='border:1px solid black;font-family: arial; font-size: 11px;text-align:center;'><br/><br/><br/>
			JOÃO RAFAEL DE SOUZA PUGLIA</br>
			CTIS 
		</td>
	</tr>
	<tr>
		<td colspan='2' style='border:1px solid black;font-family: arial; font-size: 11px;'><br/>
			Brasilia, ${.now?date}. 
		</td>
	</tr>	
</table>
	
</body>
</html>