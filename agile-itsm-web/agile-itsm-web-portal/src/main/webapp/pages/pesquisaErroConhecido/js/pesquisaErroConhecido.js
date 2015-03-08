var popup;
var controle = 0;
addEvent(window, "load", load, false);

function load() {
    document.form.afterRestore = function() {
        $('.tabs').tabs('select', 0);
    }
}

function tituloBaseConhecimentoView(idItem) {

    document.form2.idBaseConhecimento.value = idItem;
    document.form2.fireEvent("restore");

    $('#conhecimento').show();
}

function verificarPermissaoDeAcesso(idPasta, idBaseConhecimento) {

    document.form2.idPasta.value = idPasta;
    document.form2.idBaseConhecimento.value = idBaseConhecimento;
    document.form2.fireEvent("verificarPermissaoDeAcesso");

}

function corTitulo(idItem) {
    var browser = document.getElementById("browser");
    var lista = browser.getElementsByTagName("a");
    for (var i = 0; i < lista.length; i++) {
        if (lista[i] == null) {
            continue;
        }
        if (lista[i].id != "idTitulo" + idItem) {
            lista[i].style.backgroundColor = "#FFF";
        } else {
            lista[i].style.backgroundColor = "#B0C4DE";
            lista[i].style.color = "#000";
        }
    }

}

function tree(id) {
    $(id).treeview();
}

function limpar() {
    document.formPesquisa.clear();
}

var seqSelecionada = '';

function fecharPesquisa() {
    $("#resultPesquisaPai").dialog("close");
}

function mostraPesquisaBaseConhecimento() {
    document.getElementById("resultPesquisaPai").style.display = "block";
}

$(function() {
    $("#resultPesquisaPai").dialog({
        title: i18n_message("citcorpore.comum.resultadopesquisa"),
        autoOpen: false,
        width: 600,
        height: 400,
        modal: true
    });

    $('#conhecimento').hide();
});


pesquisarErroConhecido = function() {
    var auxiliar = document.formPesquisa.termoPesquisa.value;

    for (var i = 1; i <= document.formPesquisa.termoPesquisa.value.length; i++) {
        auxiliar = auxiliar.replace(" ", "");
    }

    $('#conhecimento').hide();
    document.formPesquisa.fireEvent('pesquisaBaseConhecimentoTipoErroConhecimento');

};

function fecharBaseConhecimentoView() {
    parent.fechaPopupIframe();
}

function voltar() {
    verificarAbandonoSistema = false;
    window.location = retorno;
}

function contadorClicks(idBaseConhecimento) {
    document.formPesquisa.idBaseConhecimento.value = idBaseConhecimento;
    document.formPesquisa.fireEvent('contadorDeClicks');
}

$(function() {
    var myLayout;
    myLayout = $('body').layout({
        north__resizable: false // OVERRIDE the pane-default of 'resizable=true'
            ,
        north__spacing_open: 0 // no resizer-bar when open (zero height)
            ,
        north__spacing_closed: 350,
        north__minSize: 110,
        west: {
            minSize: 310,
            onclose_end: function() {
                $('#baseconhecimento').css('width', '98%');
            },
            onopen_end: function() {
                $('#baseconhecimento').css('width', '98%');
            }
        }
    });

    jQuery.fn.toggleText = function(a, b) {
        return this.html(this.html().replace(new RegExp("(" + a + "|" + b + ")"), function(x) {
            return (x == a) ? b : a;
        }));
    }

    $('#divpesquisa').css('display', 'block');

    $('.manipulaDiv', '#tabs-1').click(function() {
        $(this).next().slideToggle('slow').siblings('#divpesquisa:visible').slideToggle('fast');
        $(this).toggleText(i18n_message("baseConhecimento.esconder"), i18n_message("baseConhecimento.mostrar")).siblings('span').next('#divpesquisa:visible').prev()
            .toggleText(i18n_message("baseConhecimento.esconder"), i18n_message("baseConhecimento.mostrar"));
    });
});

function habilitaDivPesquisa() {
    $('#divpesquisa').css('display', 'block');
    $('#spanPesq').text(i18n_message("baseConhecimento.esconderPesquisa"), i18n_message("baseConhecimento.mostrarPesquisa"));
}

function desabilitaDivPesquisa() {
    $('#divpesquisa').css('display', 'none');
    $('#spanPesq').text(i18n_message("baseConhecimento.mostrarPesquisa"), i18n_message("baseConhecimento.esconderPesquisa"));
}
