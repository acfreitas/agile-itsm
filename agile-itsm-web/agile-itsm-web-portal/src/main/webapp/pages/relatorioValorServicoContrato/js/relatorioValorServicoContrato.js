
    var temporizador;
    
	$(window).load(function() {
		
		
	});
   

    /*Alterado por
      desenvolvedor: rcs (Rafael C�sar Soyer)
      data: 07/01/2015
    */
    function imprimirRelatorioValorServicoContrato(){
        var str_nomeDoRelatorio = "imprimirRelatorioValorServicoContrato";
        imprimirRelatorioPeloNomeDoRelatorio(str_nomeDoRelatorio);
    }

    /*Alterado por
      desenvolvedor: rcs (Rafael C�sar Soyer)
      data: 07/01/2015
    */
    function imprimirRelatorioValorServicoContratoXls(){
        var str_nomeDoRelatorio = "imprimirRelatorioValorServicoContratoXls";
        imprimirRelatorioPeloNomeDoRelatorio(str_nomeDoRelatorio);
    }

    /*Alterado por
      desenvolvedor: rcs (Rafael C�sar Soyer)
      data: 07/01/2015
    */
    function meuLimpar(){

        limpar();

        /*
        * � necess�rio, j� que ao limpar o contrato muda
        * sendo necess�rio carregar os seus servi�os
        */
        document.form.fireEvent("preencherComboboxServico");
    }

    $(function() {
        $("#idContrato").change(function() {
            JANELA_AGUARDE_MENU.show();
            document.form.fireEvent("preencherComboboxServico");
        });

    });


