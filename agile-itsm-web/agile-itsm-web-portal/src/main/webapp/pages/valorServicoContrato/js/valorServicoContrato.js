		var objTab = null;

		$(window).load(function(){
			
			setTimeout(function(){
				if (idServicoContrato != '' ) {
					document.form.fireEvent("recupera");
				}
			}, 3000);
			
		});	
		
		
		function excluir() {
			document.getElementById("valorServico").value = document.getElementById("valorServico").value.replace(",", ".");
			if (document.getElementById("idValorServicoContrato").value != "") {
				if (confirm(i18n_message("citcorpore.comum.deleta"))) {
					document.form.fireEvent("delete");
				}
			}
		}

		function closePopup(idServicoContrato) {
			parent.fecharVisaoPainel(idServicoContrato,"ValorServico");
		}

		function salvar(){
			document.getElementById("valorServico").value = document.getElementById("valorServico").value.replace(",", ".");
			document.form.save();
		}