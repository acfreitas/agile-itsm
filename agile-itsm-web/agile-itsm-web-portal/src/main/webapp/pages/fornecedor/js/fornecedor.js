var objTab = null;

$(function() {
	document.form.afterRestore = function() {
		$('.tabs').tabs('select', 0);

		if ($('#comboTiposPessoa option:selected').val() == 'F' || $('#comboTiposPessoa option:selected').val() == 'J') {
			$('#cnpj').removeAttr('disabled');
		} else {
			$('#cnpj').attr('disabled', 'disabled');
		}

		$('#telefone').unmask();
		$('#telefone').mask('(99) 9999-99999').val($('#telefone').val() );

		$('#fax').unmask();
		$('#fax').mask('(99) 9999-9999').val($('#fax').val() );

		$('#cep').unmask();
		$('#cep').mask('99999-999').val($('#cep').val() );

		$('#comboPaises option[value=' + $('#idPais').val() + ']').prop('selected', true);
		$('#comboUfs option[value=' + $('#idUf').val() + ']').prop('selected', true);
		$('#comboCidades option[value=' + $('#idCidade').val() + ']').prop('selected', true);
	};

	$('#cnpj').attr('disabled', 'disabled');

	/* CONFIGURA O CAMPO ESCONDIDO idPais DE ACORDO COM A OPÇÃO SELECIONADA NA CAIXA DE SELEÇÃO DE PAISES */
	$('#comboPaises').change(function() {
        $('#idPais').val($('#comboPaises option:selected').val() );

        if ($('#idPais').val() == '') {
        	$('#idUf').val('');
        	$('#idCidade').val('');
        	document.form.fireEvent('preencherComboCidades');
        }
        document.form.fireEvent('preencherComboUfs');
    });

	/* CONFIGURA O CAMPO ESCONDIDO idUf DE ACORDO COM A OPÇÃO SELECIONADA NA CAIXA DE SELEÇÃO DE UFs*/
	$('#comboUfs').change(function() {
		$('#idUf').val($('#comboUfs option:selected').val() );

		if ($('#idUf').val() == '') {
			$('#idCidade').val('');
		}
		document.form.fireEvent('preencherComboCidades');
	});

	/* CONFIGURA O CAMPO ESCONDIDO idCidade DE ACORDO COM A OPÇÃO SELECIONADA NA CAIXA DE SELEÇÃO DE CIDADES*/
	$('#comboCidades').change(function() {
		$('#idCidade').val($('#comboCidades option:selected').val() );
	});

	/* 1 - CONFIGURANDO A M?CARA APROPRIADA PARA CADA CAMPO DO FORMUL?IO */

	/* 1.1 - PARA O CAMPO CPF/CNPJ DE ACORDO COM O TIPO DE PESSOA SELECIONADO */

	$('#comboTiposPessoa').change(function() {
		// Recuperando o tipo de pessoa selecionado.
		var tipoPessoa = $('#comboTiposPessoa option:selected').val();

		$('#tipoPessoa').val(tipoPessoa);

		// Limpando o campo de CPF/CNPJ
		$('#cnpj').val('');

		// Retirando a m?cara anterior
		$('#cnpj').unmask();

		if (tipoPessoa != '') {
			// Definindo a m?cara do campo de acordo com o tipo de pessoa.
			if(language == "en")
				var mascara = '99999999999';
			else
				var mascara = tipoPessoa == 'F' ? '999.999.999-99' : '99.999.999/9999-99';

			$('#cnpj').removeAttr('disabled');

			// Aplicando a nova m?cara ao campo.
			$('#cnpj').mask(mascara);
		} else {
			$('#cnpj').attr('disabled', 'disabled');
		}
	});

	/* 1.2 - PARA OS CAMPOS TELEFONE E FAX */
	$('#telefone').mask('(99) 9999-99999');
	$('#fax').mask('(99) 9999-9999');

	/* 1.3 - CEP */
	$('#cep').mask('99999-999');

	$('#email').focusout(function() {
		var email = $('#email').val();

		if (email != '') {
			// Avaliando a express? regular para validação do e-mail.
			if (!/\b[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}\b/.test(email) ) {
				alert(i18n_message("citcorpore.validacao.emailInvalido"));
			}
		}
	});
});

/* RECUPERA O FORNECEDOR A PARTIR DA ABA DE PESQUISA DE FORNECEDORES */
function LOOKUP_FORNECEDOR_select(id, desc) {
	document.form.restore({
		idFornecedor: id
	});
}

function excluir() {
	var idFornecedor = document.getElementById("idFornecedor");

	if (idFornecedor.value != "") {
		if (confirm(i18n_message("citcorpore.comum.deleta") ) ) {
			document.form.fireEvent("delete");
		}
	} else {
		alert(i18n_message("citcorpore.comum.necessarioSelecionarRegistro") );
		return false;
	}
}

/* 			function verificaTipPessoa(){
	alert(document.form.comboTiposPessoa.value);
	if (StringUtils.isBlank(document.form.comboTiposPessoa.value)){
        alert(i18n_message("fornecedor.tipoPessoa")+" "+i18n_message("citcorpore.comum.naoInformado"));
        document.form.comboTiposPessoa.focus();
        return false;
    }
} */

function verificaCpfCnpj(cpfcnpj) {
	var maskCPF = "___.___.___-__";
	var maskCNPJ = "__.___.___/____-__";
	var maskCPFAmericano = "___________";
	var valor = cpfcnpj.value;

	if (StringUtils.isBlank(cpfcnpj.value) || (valor == maskCPF) || (valor == maskCNPJ) || (valor == maskCPFAmericano))
        return true;
   /*  if (StringUtils.isBlank(document.form.comboTiposPessoa.value)){
        alert(i18n_message("fornecedor.tipoPessoa")+" "+i18n_message("citcorpore.comum.naoInformado"));
        document.form.comboTiposPessoa.focus();
        return false;
    }	 */
	if (document.form.comboTiposPessoa.value == 'J') {
       if (!ValidacaoUtils.validaCNPJ(cpfcnpj, 'CNPJ - ')){
			valor = "";
			return false;
       }
	}else if (document.form.comboTiposPessoa.value == 'F'){
       if (!ValidacaoUtils.validaCPF(cpfcnpj, 'CPF - ')){
    	   valor = "";
           return false;
       }
	}
	return true;
}

document.form.onValidate = function() {
    return verificaCpfCnpj(document.form.cnpj);
};