$(document).ready(function() {

    $('#step > div:first-child').addClass('current');

    $('#step > div').each(function(i) {
        i = i + 1;
        $(this).attr('id', 'step-' + i);
    });

    $('#step > div.current').show();

    $('.content').click(function() {
        if (!$(this).hasClass("fire")) {
            $('.current').removeAttr('class');
            $('#step > div').not('.current').hide();
            $('#' + $(this).attr('rel')).addClass("current").show();
        } else {
            $('#current').val($(this).attr('rel'));
        }
    });

    $('input.first').attr('disabled', true);

    $('#termos').change(function() {
        if (!$(this).is(':checked')) {
            $('input.first').attr('disabled', true);
        } else {
            $('input.first').attr('disabled', false);
        }
    });

    $('#testarLDAP').attr('disabled', true);

    $('#metodoAutenticacao').change(function() {
        if ($(this).val() == 1) {
            $('#testarLDAP').attr('disabled', true);
            $('#LDAP').hide();
        } else {
            $('#LDAP').show();
            $('#testarLDAP').attr('disabled', false);
        }
    });

    $('input[type=checkbox]').change(function() {
        if ($(this).is(':checked')) {
            $(this).val('S');
        } else {
            $(this).val('N');
        }
    });
});

function gerarCargaInicial(e) {
    document.frmConexao.current.value = $(e).attr('rel');
    $('#install').attr('disabled', true);
    document.getElementById('Throbber').style.visibility = 'visible';
    document.frmConexao.fireEvent("gerarCargaInicial");
}

function cadastraEmpresa(e) {
    document.frmEmpresa.current.value = $(e).attr('rel');
    document.frmEmpresa.fireEvent("cadastraEmpresa");
}

function setNext(current) {
    $('.current').removeAttr('class');
    $('#step > div').not('.current').hide();
    $('#' + current).addClass("current").show();
    document.getElementById('Throbber').style.visibility = 'hidden';
}

var countAtributo = 0;
function addLinhaTabelaAtributosLdap(id, atributoLdap, valorAtributoLdap) {
    var tbl = document.getElementById('tabelaAtributosLdap');
    $('#tabelaAtributosLdap').show();
    var lastRow = tbl.rows.length;
    countAtributo++;
    var row = tbl.insertRow(lastRow);
    var coluna = row.insertCell(0);
    coluna.innerHTML = '<input type="hidden" readonly="readonly" class="outputext" id="idAtributoLdap'
            + countAtributo
            + '" name="idAtributoLdap" value="'
            + id
            + '" />'
            + atributoLdap;
    coluna = row.insertCell(1);
    coluna.innerHTML = '<input type="text" maxlength="200" id="valorAtributoLdap'
            + countAtributo
            + '" name="valorAtributoLdap" value="'
            + valorAtributoLdap + '"/>';
}

var countEmail = 0;
function addLinhaEmail(id, atributo, valor, check) {
    var tbl = document.getElementById('tabelaAtributosEmail');
    var lastRow = tbl.rows.length;
    countEmail++;
    var row = tbl.insertRow(lastRow);
    var coluna = row.insertCell(0);
    var checked = '';
    if (check == 'N')
        coluna.innerHTML = '<input type="hidden" readonly="readonly" class="outputext" id="idAtributoEmail'
                + countEmail
                + '" name="idAtributoEmail" value="'
                + id
                + '" />'
                + atributo;
    else
        coluna.innerHTML = atributo
                + '<input type="hidden" readonly="readonly" class="outputext" id="idAtributoEmail'
                + countEmail + '" name="idAtributoEmail" value="' + id + '" />';

    coluna = row.insertCell(1);
    if (check == 'N') {
        coluna.innerHTML = '<input type="text" maxlength="200" id="valorAtributoEmail'
                + countEmail
                + '" size="40" name="valorAtributoEmail" value="'
                + valor + '"/>';
    } else {
        if (valor == 'S')
            checked = "checked";
        coluna.innerHTML = '<input type="checkbox" id="valorAtributoEmail'
                + countEmail + '"  ' + checked
                + ' name="valorAtributoEmail" value="' + valor + '"/>';
    }
}

var countLog = 0;
function addLinhaLog(id, atributo, valor, check, required, func, valorFunc) {
    var tbl = document.getElementById('tabelaAtributosLog');
    var lastRow = tbl.rows.length;
    countLog++;
    var row = tbl.insertRow(lastRow);
    var coluna = row.insertCell(0);
    var require = '';
    if (required == 'true')
        require = 'required';
    var f;
    if (func > '' && valorFunc > '')
        f = func + '=' + valorFunc;
    var checked = '';
    if (check == 'N')
        coluna.innerHTML = '<input type="hidden" readonly="readonly" class="outputext" id="idAtributoLog'
                + countLog
                + '" name="idAtributoLog" value="'
                + id
                + '" />'
                + atributo;
    else
        coluna.innerHTML = atributo
                + '<input type="hidden" readonly="readonly" class="outputext" id="idAtributoLog'
                + countLog + '" name="idAtributoLog" value="' + id + '" />';

    coluna = row.insertCell(1);
    if (check == 'N') {
        coluna.innerHTML = '<input type="text" maxlength="200" id="valorAtributoLog'
                + countLog
                + '" '
                + func
                + '='
                + valorFunc
                + ' '
                + require
                + ' size="40" name="valorAtributoLog" value="' + valor + '"/>';
    } else {
        if (valor == 'true')
            checked = "checked";
        coluna.innerHTML = '<input type="checkbox" id="valorAtributoLog'
                + countLog + '"  ' + checked + ' ' + f
                + ' name="valorAtributoLog" value="' + valor
                + '"  class="not" />';
    }
}

var countGed = 0;
function addLinhaGed(id, atributo, valor, check, required, func, valorFunc) {
    var tbl = document.getElementById('tabelaAtributosGed');
    var lastRow = tbl.rows.length;
    countGed++;
    var row = tbl.insertRow(lastRow);
    var coluna = row.insertCell(0);
    var require = '';
    if (required == 'true')
        require = 'required';
    var checked = '';
    if (check == 'N')
        coluna.innerHTML = '<input type="hidden" readonly="readonly" class="outputext" id="idAtributoGed'
                + countGed
                + '" name="idAtributoGed" value="'
                + id
                + '" />'
                + atributo;
    else
        coluna.innerHTML = atributo
                + '<input type="hidden" readonly="readonly" class="outputext" id="idAtributoGed'
                + countGed + '" name="idAtributoGed" value="' + id + '" />';

    coluna = row.insertCell(1);
    if (check == 'N') {
        coluna.innerHTML = '<input type="text" maxlength="200" id="valorAtributoGed'
                + countGed
                + '" '
                + func
                + '='
                + valorFunc
                + ' '
                + require
                + '  size="40" name="valorAtributoGed" value="' + valor + '"/>';
    } else {
        if (valor == 'S')
            checked = "checked";
        coluna.innerHTML = '<input type="checkbox" id="valorAtributoGed'
                + countGed + '"  ' + checked + ' ' + func + '=' + valorFunc
                + ' name="valorAtributoGed" value="' + valor + '"/>';
    }
}

var countSMTP = 0;
function addLinhaSMTP(id, atributo, valor, check, arrClass) {
    var tbl = document.getElementById('tabelaAtributosSMTP');
    var lastRow = tbl.rows.length;
    countSMTP++;
    var row = tbl.insertRow(lastRow);
    var coluna = row.insertCell(0);
    var checked = '';
    if (check == 'N')
        coluna.innerHTML = '<input type="hidden" readonly="readonly" class="outputext" id="idAtributoSMTP'
                + countSMTP
                + '" name="idAtributoSMTP" value="'
                + id
                + '" />'
                + atributo;
    else
        coluna.innerHTML = atributo
                + '<input type="hidden" readonly="readonly" class="outputext" id="idAtributoSMTP'
                + countSMTP + '" name="idAtributoSMTP" value="' + id + '" />';

    coluna = row.insertCell(1);
    var classes = setValid(arrClass);
    if (check == 'N') {
        coluna.innerHTML = '<input type="text" maxlength="200" id="valorAtributoSMTP'
                + countSMTP
                + '" size="40" class="'
                + classes
                + '" name="valorAtributoSMTP" value="' + valor + '"/>';
    } else {
        if (valor == 'S')
            checked = "checked";
        coluna.innerHTML = '<input type="checkbox" id="valorAtributoSMTP'
                + countSMTP + '"  ' + checked
                + ' name="valorAtributoSMTP" value="' + valor + '"/>';
    }
}

var countIC = 0;
function addLinhaParametrosIC(id, atributo, valor, check, required, func,
        valorFunc, arrClass) {
    var tbl = document.getElementById('tabelaAtributosIC');
    var lastRow = tbl.rows.length;
    countIC++;
    var row = tbl.insertRow(lastRow);
    var coluna = row.insertCell(0);
    var classes = setValid(arrClass);
    var require = '';
    if (required == 'true')
        require = 'required';
    var checked = '';
    if (check == 'N')
        coluna.innerHTML = '<input type="hidden" readonly="readonly" class="outputext" id="idAtributoIC'
                + countIC
                + '" name="idAtributoIC" value="'
                + id
                + '" />'
                + atributo;
    else
        coluna.innerHTML = atributo
                + '<input type="hidden" readonly="readonly" class="outputext" id="idAtributoIC'
                + countIC + '" name="idAtributoIC" value="' + id + '" />';

    coluna = row.insertCell(1);
    if (check == 'N') {
        coluna.innerHTML = '<input type="text" maxlength="200" class="'
                + classes + '" id="valorAtributoIC' + countIC + '" ' + func
                + '=' + valorFunc + ' ' + require
                + '  size="40" name="valorAtributoIC" value="' + valor + '"/>';
    } else {
        if (valor == 'S')
            checked = "checked";
        coluna.innerHTML = '<input type="checkbox" id="valorAtributoIC'
                + countIC + '" ' + func + '=' + valorFunc + ' ' + checked
                + ' name="valorAtributoIC" value="' + valor + '"/>';
    }
}

var countBase = 0;
function addLinhaParametrosBase(id, atributo, valor, check, required, func,
        valorFunc, arrClass) {
    var tbl = document.getElementById('tabelaAtributosBase');
    var lastRow = tbl.rows.length;
    countBase++;
    var row = tbl.insertRow(lastRow);
    var coluna = row.insertCell(0);
    var classes = setValid(arrClass);
    var require = '';
    if (required == 'true')
        require = 'required';
    var checked = '';
    if (check == 'N')
        coluna.innerHTML = '<input type="hidden" readonly="readonly" class="outputext" id="idAtributoBase'
                + countBase
                + '" name="idAtributoBase" value="'
                + id
                + '" />'
                + atributo;
    else
        coluna.innerHTML = atributo
                + '<input type="hidden" readonly="readonly" class="outputext" id="idAtributoBase'
                + countBase + '" name="idAtributoBase" value="' + id + '" />';

    coluna = row.insertCell(1);
    if (check == 'N') {
        coluna.innerHTML = '<input type="text"  maxlength="200" class="'
                + classes + '"  id="valorAtributoBase' + countBase + '" '
                + func + '=' + valorFunc + ' ' + require
                + ' size="40" name="valorAtributoBase" value="' + valor + '"/>';
    } else {
        if (valor == 'S')
            checked = "checked";
        coluna.innerHTML = '<input type="checkbox" id="valorAtributoBase'
                + countBase + '" ' + func + '=' + valorFunc + ' ' + checked
                + ' name="valorAtributoBase" value="' + valor + '"/>';
    }
}

var countGeral = 0;
function addLinhaParametrosGerais(id, atributo, valor, check, required) {
    var tbl = document.getElementById('tabelaAtributosGerais');
    var lastRow = tbl.rows.length;
    countGeral++;
    var row = tbl.insertRow(lastRow);
    var coluna = row.insertCell(0);
    var checked = '';
    var require = '';
    if (required == 'true')
        require = 'required';
    if (check == 'N')
        coluna.innerHTML = '<input type="hidden" readonly="readonly" class="outputext" id="idAtributoGeral'
                + countGeral
                + '" '
                + require
                + ' name="idAtributoGeral" value="' + id + '" />' + atributo;
    else
        coluna.innerHTML = atributo
                + '<input type="hidden" readonly="readonly" class="outputext" id="idAtributoGeral'
                + countGeral + '" ' + require
                + ' name="idAtributoGeral" value="' + id + '" />';

    coluna = row.insertCell(1);
    if (check == 'N') {
        coluna.innerHTML = '<input type="text" maxlength="200" id="valorAtributoGeral'
                + countGeral
                + '" '
                + require
                + ' size="40" name="valorAtributoGeral" value="'
                + valor
                + '"/>';
    } else {
        if (valor == 'S')
            checked = "checked";
        coluna.innerHTML = '<input type="checkbox" id="valorAtributoGeral'
                + countGeral + '"  ' + checked + ' ' + require
                + ' name="valorAtributoGeral" value="' + valor + '"/>';
    }
}

function deleteAllRowsTabelaAtributosLdap() {
    var tabela = document.getElementById('tabelaAtributosLdap');
    var count = tabela.rows.length;
    while (count > 1) {
        tabela.deleteRow(count - 1);
        count--;
    }
}

function deleteAllRowsTabelaAtributosEmail() {
    var tabela = document.getElementById('tabelaAtributosEmail');
    var count = tabela.rows.length;
    while (count > 1) {
        tabela.deleteRow(count - 1);
        count--;
    }
}

function deleteAllRowsTabelaAtributosLog() {
    var tabela = document.getElementById('tabelaAtributosLog');
    var count = tabela.rows.length;
    while (count > 1) {
        tabela.deleteRow(count - 1);
        count--;
    }
}

function deleteAllRowsTabelaAtributosGed() {
    var tabela = document.getElementById('tabelaAtributosGed');
    var count = tabela.rows.length;
    while (count > 1) {
        tabela.deleteRow(count - 1);
        count--;
    }
}

function deleteAllRowsTabelaAtributosSMTP() {
    var tabela = document.getElementById('tabelaAtributosSMTP');
    var count = tabela.rows.length;
    while (count > 1) {
        tabela.deleteRow(count - 1);
        count--;
    }
}

function deleteAllRowsTabelaAtributosIC() {
    var tabela = document.getElementById('tabelaAtributosIC');
    var count = tabela.rows.length;
    while (count > 1) {
        tabela.deleteRow(count - 1);
        count--;
    }
}

function deleteAllRowsTabelaAtributosBase() {
    var tabela = document.getElementById('tabelaAtributosBase');
    var count = tabela.rows.length;
    while (count > 1) {
        tabela.deleteRow(count - 1);
        count--;
    }
}

function deleteAllRowsTabelaAtributosGerais() {
    var tabela = document.getElementById('tabelaAtributosGerais');
    var count = tabela.rows.length;
    while (count > 1) {
        tabela.deleteRow(count - 1);
        count--;
    }
}

function serializaAtributosLdap() {
    var tabela = document.getElementById('tabelaAtributosLdap');
    var count = countAtributo + 1;
    var listAtributos = [];

    for (var i = 1; i < count; i++) {
        if (document.getElementById('idAtributoLdap' + i) != ""
                && document.getElementById('idAtributoLdap' + i) != null) {
            var idAtributo = document.getElementById('idAtributoLdap' + i).value;

            var valorAtributo = $('#valorAtributoLdap' + i).val()

            var ldapDto = new LdapDTO(idAtributo, valorAtributo);

            listAtributos.push(ldapDto);
        }
    }
    document.frmLDAP.listAtributoLdapSerializado.value = ObjectUtils
            .serializeObjects(listAtributos);
}

function serializaAtributosEmail() {
    var tabela = document.getElementById('tabelaAtributosEmail');
    var count = countEmail + 1;
    var listAtributos = [];

    for (var i = 1; i < count; i++) {
        if (document.getElementById('idAtributoEmail' + i) != ""
                && document.getElementById('idAtributoEmail' + i) != null) {
            var idAtributo = document.getElementById('idAtributoEmail' + i).value;

            var valorAtributo = $('#valorAtributoEmail' + i).val()
            var emailDto = new EmailDTO(idAtributo, valorAtributo);

            listAtributos.push(emailDto);
        }
    }
    document.frmEmail.listAtributoEmailSerializado.value = ObjectUtils
            .serializeObjects(listAtributos);
}

function serializaAtributosLog() {
    var tabela = document.getElementById('tabelaAtributosLog');
    var count = countLog + 1;
    var listAtributos = [];

    for (var i = 1; i < count; i++) {
        if (document.getElementById('idAtributoLog' + i) != ""
                && document.getElementById('idAtributoLog' + i) != null) {
            var idAtributo = document.getElementById('idAtributoLog' + i).value;

            var valorAtributo = $('#valorAtributoLog' + i).val()
            var log = new LogDTO(idAtributo, valorAtributo);

            listAtributos.push(log);
        }
    }
    document.frmLog.listAtributoLogSerializado.value = ObjectUtils
            .serializeObjects(listAtributos);
}

function serializaAtributosGed() {
    var tabela = document.getElementById('tabelaAtributosGed');
    var count = countGed + 1;
    var listAtributos = [];

    for (var i = 1; i < count; i++) {
        if (document.getElementById('idAtributoGed' + i) != ""
                && document.getElementById('idAtributoGed' + i) != null) {
            var idAtributo = document.getElementById('idAtributoGed' + i).value;

            var valorAtributo = $('#valorAtributoGed' + i).val()
            var Ged = new GedDTO(idAtributo, valorAtributo);

            listAtributos.push(Ged);
        }
    }
    document.frmGed.listAtributoGedSerializado.value = ObjectUtils
            .serializeObjects(listAtributos);
}

function serializaAtributosSMTP() {
    var tabela = document.getElementById('tabelaAtributosSMTP');
    var count = countSMTP + 1;
    var listAtributos = [];

    for (var i = 1; i < count; i++) {
        if (document.getElementById('idAtributoSMTP' + i) != ""
                && document.getElementById('idAtributoSMTP' + i) != null) {
            var idAtributo = document.getElementById('idAtributoSMTP' + i).value;

            var valorAtributo = $('#valorAtributoSMTP' + i).val()
            var SMTP = new SMTPDTO(idAtributo, valorAtributo);

            listAtributos.push(SMTP);
        }
    }
    document.frmSMTP.listAtributoSMTPSerializado.value = ObjectUtils
            .serializeObjects(listAtributos);
}

function serializaAtributosIC() {
    var tabela = document.getElementById('tabelaAtributosIC');
    var count = countIC + 1;
    var listAtributos = [];

    for (var i = 1; i < count; i++) {
        if (document.getElementById('idAtributoIC' + i) != ""
                && document.getElementById('idAtributoIC' + i) != null) {
            var idAtributo = document.getElementById('idAtributoIC' + i).value;

            var valorAtributo = $('#valorAtributoIC' + i).val()
            var ic = new ICDTO(idAtributo, valorAtributo);

            listAtributos.push(ic);
        }
    }
    document.frmIC.listAtributoICSerializado.value = ObjectUtils
            .serializeObjects(listAtributos);
}

function serializaAtributosBase() {
    var tabela = document.getElementById('tabelaAtributosBase');
    var count = countBase + 1;
    var listAtributos = [];

    for (var i = 1; i < count; i++) {
        if (document.getElementById('idAtributoBase' + i) != ""
                && document.getElementById('idAtributoBase' + i) != null) {
            var idAtributo = document.getElementById('idAtributoBase' + i).value;

            var valorAtributo = $('#valorAtributoBase' + i).val()
            var base = new BaseDTO(idAtributo, valorAtributo);

            listAtributos.push(base);
        }
    }
    document.frmBase.listAtributoBaseSerializado.value = ObjectUtils
            .serializeObjects(listAtributos);
}

function serializaAtributosGerais() {
    var tabela = document.getElementById('tabelaAtributosGerais');
    var count = countGeral + 1;
    var listAtributos = [];

    for (var i = 1; i < count; i++) {
        if (document.getElementById('idAtributoGeral' + i) != ""
                && document.getElementById('idAtributoGeral' + i) != null) {
            var idAtributo = document.getElementById('idAtributoGeral' + i).value;

            var valorAtributo = $('#valorAtributoGeral' + i).val()
            var geral = new GeralDTO(idAtributo, valorAtributo);

            listAtributos.push(geral);
        }
    }
    document.frmGeral.listAtributoGeraisSerializado.value = ObjectUtils
            .serializeObjects(listAtributos);
}

function LdapDTO(idAtributo, valorAtributo) {
    this.idAtributoLdap = idAtributo;
    this.valorAtributoLdap = valorAtributo;
}

function EmailDTO(idAtributo, valorAtributo) {
    this.idAtributoEmail = idAtributo;
    this.valorAtributoEmail = valorAtributo;
}

function LogDTO(idAtributo, valorAtributo) {
    this.idAtributoLog = idAtributo;
    this.valorAtributoLog = valorAtributo;
}

function GedDTO(idAtributo, valorAtributo) {
    this.idAtributoGed = idAtributo;
    this.valorAtributoGed = valorAtributo;
}

function SMTPDTO(idAtributo, valorAtributo) {
    this.idAtributoSMTP = idAtributo;
    this.valorAtributoSMTP = valorAtributo;
}

function ICDTO(idAtributo, valorAtributo) {
    this.idAtributoIC = idAtributo;
    this.valorAtributoIC = valorAtributo;
}

function BaseDTO(idAtributo, valorAtributo) {
    this.idAtributoBase = idAtributo;
    this.valorAtributoBase = valorAtributo;
}

function GeralDTO(idAtributo, valorAtributo) {
    this.idAtributoGeral = idAtributo;
    this.valorAtributoGeral = valorAtributo;
}

function configuraLDAP(e) {
    serializaAtributosLdap();
    document.frmLDAP.current.value = $(e).attr('rel');
    document.frmLDAP.fireEvent("configuraLDAP");
}

function testaLDAP() {
    document.frmLDAP.fireEvent("testaLDAP");
}

function configuraAplicacao(e) {
    serializaAtributosGerais();
    document.frmGeral.current.value = $(e).attr('rel');
    desabilita();
    document.frmGeral.fireEvent("configuraParametrosGerais");
}

function configuraEmail(e) {
    serializaAtributosEmail();
    document.frmEmail.current.value = $(e).attr('rel');
    document.frmEmail.fireEvent("configuraEmail");
}

function configuraLog(e) {
    serializaAtributosLog();
    document.frmLog.current.value = $(e).attr('rel');
    document.frmLog.fireEvent("configuraLog");
}

function configuraGed(e) {
    serializaAtributosGed();
    document.frmGed.current.value = $(e).attr('rel');
    document.frmGed.fireEvent("configuraGed");
}

function configuraSMTP(e) {
    serializaAtributosSMTP();
    document.frmSMTP.current.value = $(e).attr('rel');
    document.frmSMTP.fireEvent("configuraSMTP");
}

function configuraParametrosIC(e) {
    serializaAtributosIC();
    document.frmIC.current.value = $(e).attr('rel');
    document.frmIC.fireEvent("configuraParametrosIC");
}

function configuraParametrosBase(e) {
    serializaAtributosBase();
    document.frmBase.current.value = $(e).attr('rel');
    document.frmBase.fireEvent("configuraParametrosBase");

}

function changeCheck() {
    $('input[type=checkbox]').not('.not').change(function() {
        if ($(this).is(':checked')) {
            $(this).val('S');
        } else {
            $(this).val('N');
        }
    });
    $('input[type=checkbox].not').change(function() {
        if ($(this).is(':checked')) {
            $(this).val('true');
        } else {
            $(this).val('false');
        }
    });
    /* recarrega as configura��es do defines */
    DEFINEALLPAGES_generateConfiguracaoCampos();
}

function validaDiretorio(e) {
    document.getElementById('diretorio').value = e.value;
    document.getElementById('campoDiretorio').value = e.id;
    if (document.getElementById('diretorio').value != '')
        document.frmGed.fireEvent("validaDiretorio");
}

function setValid(arrClass) {
    var s = arrClass.split(',');
    var retorno = '';
    for (var i = 0; i < s.length; i++) {
        retorno += s[i] + ' ';
    }
    return retorno;
}

function desabilita() {
    $('#concluir').attr('disabled', true);
    $('#Throbber_').css('visibility', 'visible');
}
function habilita() {
    $('#concluir').attr('disabled', false);
    $('#Throbber_').css('visibility', 'hidden');
}
function habilitaInstall() {
    $('#install').attr('disabled', false);
    document.getElementById('Throbber').style.visibility = 'hidden'
}
/**
 * Autor: Pedro Lino Data: 19/04/2013 Filtra todos os dados contidos na
 * tabela(filtro feito por TR) deve ser chamada no input via onkeyup campoBusca:
 * valor digitado no campo de filtro table: Id da tabela onde ser� feito a busca
 */
function filtroTableJs(campoBusca, table) {
    // Recupera value do campo de busca
    var term = campoBusca.value.toLowerCase();
    if (term != "") {
        // Mostra os TR's que contem o value digitado no campoBusca
        if (table != "") {
            $("#" + table + " tbody>tr").hide();
            $("#" + table + " td").filter(function() {
                return $(this).text().toLowerCase().indexOf(term) > -1
            }).parent("tr").show();
        }
    } else {
        // Quando n�o h� nada digitado, mostra a tabela com todos os dados
        $("#" + table + " tbody>tr").show();
    }
}

/**
 * Autor: Thiago Matias Data: 16/08/2013 Filtra todos os dados contidos na lista
 * deve ser chamada no input via onkeyup campoBusca: valor digitado no campo de
 * filtro lista: Id da div onde ser� feito a busca
 */
function filtroListaJs(campoBusca, lista) {
    // Recupera value do campo de busca
    var term = campoBusca.value.toLowerCase();
    if (term != "") {
        var searchText = term;

        $('#' + lista + ' ul > li ')
                .each(
                        function() {

                            var currentLiText = $(this).text(), showCurrentLi = currentLiText
                                    .toLowerCase().indexOf(searchText) !== -1;

                            $(this).toggle(showCurrentLi);

                        });
    } else {
        // Quando n�o h� nada digitado, mostra a tabela com todos os dados
        $('#' + lista + ' ul > li')
                .each(
                        function() {

                            var currentLiText = $(this).text(), showCurrentLi = currentLiText
                                    .toLowerCase().indexOf(searchText) == -1;

                            $(this).toggle(showCurrentLi);

                        });
    }
}

/*******************************************************************************
 * Autor: Thiago Matias Data: 30/08/2013 Filtra todos os dados contidos na div
 * deve ser chamada no input via onkeyup campoBusca: valor digitado no campo de
 * filtro
 *
 ******************************************************************************/
function filtroDivsJs(campoBusca, lista) {
    // Recupera value do campo de busca
    var term = campoBusca.value.toLowerCase();
    if (term != "") {
        var searchText = term;

        $('#' + lista + ' div')
                .each(
                        function() {

                            var currentLiText = $(this).text(), showCurrentLi = currentLiText
                                    .toLowerCase().indexOf(searchText) !== -1;

                            $(this).toggle(showCurrentLi);

                        });
    } else {
        // Quando n�o h� nada digitado, mostra a tabela com todos os dados
        $('#' + lista + ' div')
                .each(
                        function() {

                            var currentLiText = $(this).text(), showCurrentLi = currentLiText
                                    .toLowerCase().indexOf(searchText) == -1;

                            $(this).toggle(showCurrentLi);

                        });
    }
}
/*******************************************************************************
 * Autor: Thiago Matias Data: 09/09/2013 Contador para textarea, informando e
 * limitando a quantidade de caracteres digitados. Exemplo: catalogoServico.jsp
 ******************************************************************************/

function contCaracteres(valor) {
    quant = 255;
    total = valor.length;
    if (total <= quant) {
        resto = quant - total;
        document.getElementById('cont').innerHTML = resto;
    } else {
        document.getElementById('descCatalogoServico').value = valor.substr(0,
                quant);
    }
}

/*******************************************************************************
 * Autor: Thiago Matias Data: 11/09/2013 Limpando textos salvos no banco com
 * tags html. id = id do elemento a ser tratado texto = o texto que contem tags
 * para ser retirados.
 *
 ******************************************************************************/

function retirandoTagsHtml(id, texto) {
    var re = /(<([^>]+)>)/gi;
    for (i = 0; i < texto.length; i++)
        $(id).val($(id).val().replace(re, ""));
}
