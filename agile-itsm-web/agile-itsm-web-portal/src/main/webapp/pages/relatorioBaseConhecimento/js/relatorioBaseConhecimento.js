
    var temporizador;
    addEvent(window, "load", load, false);
    function load() {
        $("#POPUP_USUARIO").dialog({
            autoOpen : false,
            width : 600,
            height : 400,
            modal : true
        });
    }

    function LOOKUP_USUARIO_select(id, desc) {
        document.form.idUsuarioAcesso.value = id;
        document.form.nomeUsuarioAcesso.value = desc;
        $("#POPUP_USUARIO").dialog("close");
    }


    /*Alterado por
      desenvolvedor: rcs (Rafael C�sar Soyer)
      data: 06/01/2015
    */
    function imprimirRelatorioBaseConhecimento(){
        var str_nomeDoRelatorio = "imprimirRelatorioBaseConhecimento";
        imprimirRelatorioPeloNomeDoRelatorio (str_nomeDoRelatorio);
    }

     /*Alterado por
     desenvolvedor: rcs (Rafael C�sar Soyer)
     data: 06/01/2015
   */
    function imprimirRelatorioBaseConhecimentoXls(){
        var str_nomeDoRelatorio = "imprimirRelatorioBaseConhecimentoXls";
        imprimirRelatorioPeloNomeDoRelatorio (str_nomeDoRelatorio);
    }

     /*desenvolvedor: rcs (Rafael C�sar Soyer)
     data: 06/01/2015
   */
    var str_html_inputDtInicioDtFim =
        "<table>" +
        "<tr>" +
            "<td>" +
                "<input type='text' name='dataInicio' id='dataInicio' size='10' maxlength='10' class='Format[Date] Valid[Date] datepicker'/>" +
            "</td>" +
            "<td>" +
                i18n_message("citcorpore.comum.a") +
            "</td>" +
            "<td>" +
                "<input type='text' name='dataFim' id='dataFim' size='10' maxlength='10' class='Format[Date] Valid[Date] datepicker'/>" +
            "</td>" +
        "</tr>" +
     "</table>";


     /*Alterado por
     desenvolvedor: rcs (Rafael C�sar Soyer)
     data: 06/01/2015
   */
    function mudaDivPeriodo(tipoPeriodo){
        if (tipoPeriodo != null && tipoPeriodo != '') {
            // LIMPA CAMPOS DE PER�ODO
            document.getElementById('dataInicio').value = '';
            document.getElementById('dataFim').value = '';

            // MOSTRA O PERIODO ESCOLHIDO
            if (tipoPeriodo == 'criacao') {
                $( ".divPeriodoCriacao" ).html(str_html_inputDtInicioDtFim);
            } else if (tipoPeriodo == 'publicacao') {
                $( ".divPeriodoPublicacao" ).html(str_html_inputDtInicioDtFim);
            } else if (tipoPeriodo == 'expiracao') {
                $( ".divPeriodoExpiracao" ).html(str_html_inputDtInicioDtFim);
            } else if (tipoPeriodo == 'acesso') {
                $( ".divPeriodoAcesso" ).html(str_html_inputDtInicioDtFim);
            }
        }
    }

      $(function() {
            $("#addUsuario").click(function() {
                $("#POPUP_USUARIO").dialog("open");
            });
        });

     /*desenvolvedor: rcs (Rafael C�sar Soyer)
       data: 06/01/2015
     */
      //adiciona conte�do da string � div, ao carregar p�gina
      (function($) {
            $(function() {
                $( "#divPeriodoCriacao" ).html(str_html_inputDtInicioDtFim);
            });
      })(jQuery);


