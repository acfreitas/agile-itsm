<table width='100%' cellspacing="0" cellpadding="0">
	<tr>
		<td style='text-align:center; font-family: arial; font-size: 11px;'>
<b>MINISTÉRIO DA INTEGRAÇÃO NACIONAL</b>
		</td>
	</tr>		
	<tr>	
		<td style='text-align:center; font-family: arial; font-size: 11px;'>		
Secretaria-Executiva
		</td>
	</tr>		
	<tr>	
		<td style='text-align:center; font-family: arial; font-size: 11px;'>		
Coordenação-Geral de Tecnologia da Informação
		</td>
	</tr>		
	<tr>	
		<td style='text-align:center; font-family: arial; font-size: 11px;'>		
<b>ORDEM DE SERVIÇO</b>
		</td>
	</tr>
</table>
<table width='100%' cellspacing="0" cellpadding="0">
	<tr>
		<td colspan='8' style='border:1px solid black; background-color:#F0F0F0; font-family: arial; font-size: 11px;'>IDENTIFICAÇÃO</td>
	</tr>
	<tr>
		<td style='border:1px solid black; background-color:#CCFFFF; font-family: arial; font-size: 11px;'>OS nº:</td>
		<td style='border:1px solid black; font-family: arial; font-size: 11px;'>${projectDTO.numeroStr}</td>
		<td style='border:1px solid black; background-color:#CCFFFF; font-family: arial; font-size: 11px;'>Requisitante:</td>
		<td colspan='2' style='border:1px solid black; font-family: arial; font-size: 11px;'>${projectDTO.nomeAreaRequisitante}</td>
		<td style='border:1px solid black; background-color:#CCFFFF; font-family: arial; font-size: 11px;'>Data de Emissão:</td>
		<td style='border:1px solid black; font-family: arial; font-size: 11px;'>${projectDTO.dataEmissaoStr}</td>
	</tr>	
	<tr>
		<td style='border:1px solid black; background-color:#CCFFFF; font-family: arial; font-size: 11px;'>Nome do Projeto:</td>
		<td colspan='2' style='border:1px solid black; font-family: arial; font-size: 11px;'>${projectDTO.nomeProjeto}</td>
		<td style='border:1px solid black; background-color:#CCFFFF; font-family: arial; font-size: 11px;'>Sigla:</td>
		<td style='border:1px solid black; font-family: arial; font-size: 11px;'>${projectDTO.sigla}</td>
		<td style='border:1px solid black; background-color:#CCFFFF; font-family: arial; font-size: 11px;'>Emergencial:</td>
		<td style='border:1px solid black; font-family: arial; font-size: 11px;'>${projectDTO.emergencialStr}</td>
	</tr>
	<tr>
		<td style='border:1px solid black; background-color:#CCFFFF; font-family: arial; font-size: 11px;'>Contratada:</td>
		<td colspan='2' style='border:1px solid black; font-family: arial; font-size: 11px;'>${providerDto.razaoSocial}</td>
		<td style='border:1px solid black; background-color:#CCFFFF; font-family: arial; font-size: 11px;'>Contrato:</td>
		<td style='border:1px solid black; font-family: arial; font-size: 11px;'>${contractDto.numero}</td>
		<td style='border:1px solid black; background-color:#CCFFFF; font-family: arial; font-size: 11px;'>Severidade:</td>
		<td style='border:1px solid black; font-family: arial; font-size: 11px;'>${projectDTO.severidade}</td>
	</tr>	
	<tr>
		<td style='border:1px solid black; background-color:#CCFFFF; font-family: arial; font-size: 11px;'>Nome Gestor:</td>
		<td colspan='2' style='border:1px solid black; font-family: arial; font-size: 11px;'>${projectDTO.nomeGestor}</td>
		<td style='border:1px solid black; background-color:#CCFFFF; font-family: arial; font-size: 11px;'>Tipo OS:</td>
		<td colspan='3' style='border:1px solid black; font-family: arial; font-size: 11px;'>${projectDTO.nomeServicoOS}</td>
	</tr>	
</table>