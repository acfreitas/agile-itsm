
		var temporizador;

		$(window).load(function() {
							
			carregaPermissao();		
		});
				

		function carregaPermissao(){}	
		
		/*
		  Alterado por
		  desenvolvedor: rcs (Rafael C�sar Soyer)
		  data: 07/01/2015
		*/
		function imprimirRelatorioQuantitativo() {
			var str_nomeDoRelatorio = "imprimirRelatorioQuantitativo";
			imprimirRelatorioPeloNomeDoRelatorio(str_nomeDoRelatorio);
		}

	    /*
          Alterado por
          desenvolvedor: rcs (Rafael C�sar Soyer)
          data: 07/01/2015
        */
		function imprimirRelatorioQuantitativoXls() {
            var str_nomeDoRelatorio = "imprimirRelatorioQuantitativoXls";
            imprimirRelatorioPeloNomeDoRelatorio(str_nomeDoRelatorio);
		}


