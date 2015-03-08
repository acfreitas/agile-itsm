    <ul class="topnav">  
        <li>
        	<a href="#">Cadastros</a>
        	<ul class="subnav">
                <li><a href="../../pages/empregado/empregado.load">Colaborador</a></li>
                <li><a href="../../pages/cliente/cliente.load">Cliente</a></li>
                <li><a href="../../pages/contratos/contratos.load">Contrato</a></li>
                <li><a href="../../pages/funcionalidade/funcionalidade.load">Funcionalidade</a></li>
                <li><a href="../../pages/grupoAcesso/grupoAcesso.load">Grupo de Acesso</a></li>
                <li><a href="../../pages/atividades/atividades.load">Atividades</a></li>
                <li><a href="../../pages/painelAtividades/painelAtividades.load">Painel de Atividades</a></li>
                <li><a href="../../pages/projeto/projeto.load">Projeto</a></li>
                <li><a href="../../pages/usuario/usuario.load">Usuário</a></li>
        	</ul>
        </li>
        <li><a href="#">&nbsp;&nbsp;O.S.&nbsp;&nbsp;</a></li>
        <li><a href="#">Fluxo de Trabalho</a></li>
        <li><a href="#">Financeiro</a></li>
        <li><a href="#">Gerência do Projeto</a></li>
        <li><a href="#">Consultas</a></li>
        <li>
        	<a href="#">Relatórios</a>
        	<ul class="subnav">
        		<li><a href="../../pages/relatorioAtividades/relatorioAtividades.load">Relatório de Atividades</a></li>		
        	</ul>
        </li>
    </ul>
<link type="text/css" rel="stylesheet" href="../../include/menu2011/menuNovo.css" />
 <script type="text/javascript">
	$(document).ready(function() {

		$("ul.topnav li a").hover(function() {
			$(this).parent().find("ul.subnav").slideDown('fast').show();
			$(this).parent().hover(function() {
			}, function() {
				$(this).parent().find("ul.subnav").slideUp('slow');
			});

		}).hover(function() {
			$(this).addClass("subhover");
		}, function() {
			$(this).removeClass("subhover");
		});

	});
</script>