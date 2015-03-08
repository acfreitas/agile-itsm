<script type="text/javascript" src="${ctx}/cit/objects/TreinamentoCurriculoDTO.js"></script>
<script type="text/javascript" src="${ctx}/cit/objects/TelefoneCurriculoDTO.js"></script>
<script type="text/javascript" src="${ctx}/cit/objects/EmailCurriculoDTO.js"></script>
<script type="text/javascript" src="${ctx}/cit/objects/EnderecoCurriculoDTO.js"></script>
<script type="text/javascript" src="${ctx}/cit/objects/FormacaoCurriculoDTO.js"></script>
<script type="text/javascript" src="${ctx}/cit/objects/ExperienciaProfissionalCurriculoDTO.js"></script>
<script type="text/javascript" src="${ctx}/cit/objects/FuncaoExperienciaProfissionalCurriculoDTO.js"></script>
<script type="text/javascript" src="${ctx}/cit/objects/CompetenciaCurriculoDTO.js"></script>
<script type="text/javascript" src="${ctx}/cit/objects/CertificacaoCurriculoDTO.js"></script>
<script type="text/javascript" src="${ctx}/cit/objects/IdiomaCurriculoDTO.js"></script>
<script type="text/javascript" src='${ctx}/js/UploadUtils.js'></script>
<script src="${ctx}/novoLayout/common/theme/scripts/plugins/forms/jquery-validation/demo/marketo/jquery.maskedinput.js" type="text/javascript"></script>
<script src="${ctx}/novoLayout/common/include/js/templateCurriculo.js"></script>
<script type="text/javascript">
/*bruno.aquino: foi retirado o trecho de código abaixo de templateCurriculo.js pois não era possivel capturar o locale, este é incarregado de retirar a mascara do cpf quando a linguagem for inglês */
	addEvent(window, "load", load, false);
	function load(){
		$("#cep").mask("99.999-999");

		$("#cpf").mask("999.999.999-99");

		$("#telefone").mask("9999-99999");
		$("#dataNascimento").mask("99/99/9999");
		jQuery(document).ready(function($) {
			$('#telefone').keypress(function(e) {
				var tecla = e.keyCode ? e.keyCode : e.which;
				if((tecla > 47 && tecla < 58) || (tecla > 34 && tecla < 41) || tecla == 8 || tecla == 46) {
					return true;
				}
				return false;
			});
		});

	$(document).ready(function(){
		$('#inserir').click(function(event){
		 event.preventDefault();
		$("#remover_foto").show();
		$("#adicionar_foto").hide();
		});
		$('#remover_foto').click(function(event){
			event.preventDefault();
		$("#remover_foto").hide();
		$("#adicionar_foto").show();
		});
	});

	/* Desenvolvedor: Gilberto Nery - Data: 25/11/2013 - Horário: 15:00 - ID Citsmart: 0
	 * Alterado por luiz.borges - 09/12/2013 - 09:30 hrs - Internacionalização do datePicker
	 * Motivo/Comentário: Formato o campo Data de Nascimento para o padrão brasileiro (dd/mm/yyyy)
	 * */
		$('#dataNascimento').datepicker({
			dateFormat: 'dd/mm/yy',
			language: 'pt-BR',
			dayNamesMin: [i18n_message("citcorpore.texto.abreviado.diaSemana.domingo"),
			              i18n_message("citcorpore.texto.abreviado.diaSemana.segundaFeira"),
			              i18n_message("citcorpore.texto.abreviado.diaSemana.tercaFeira"),
			              i18n_message("citcorpore.texto.abreviado.diaSemana.quartaFeira"),
			              i18n_message("citcorpore.texto.abreviado.diaSemana.quintaFeira"),
			              i18n_message("citcorpore.texto.abreviado.diaSemana.sextaFeira"),
			              i18n_message("citcorpore.texto.abreviado.diaSemana.sabado")],
			monthNames: [ i18n_message("citcorpore.texto.mes.janeiro"),
			              i18n_message("citcorpore.texto.mes.fevereiro"),
			              i18n_message("citcorpore.texto.mes.marco"),
			              i18n_message("citcorpore.texto.mes.abril"),
			              i18n_message("citcorpore.texto.mes.maio"),
			              i18n_message("citcorpore.texto.mes.junho"),
			              i18n_message("citcorpore.texto.mes.julho"),
			              i18n_message("citcorpore.texto.mes.agosto"),
			              i18n_message("citcorpore.texto.mes.setembro"),
			              i18n_message("citcorpore.texto.mes.outubro"),
			              i18n_message("citcorpore.texto.mes.novembro"),
			              i18n_message("citcorpore.texto.mes.dezembro") ]
		});
  	}
</script>