$(function(){
    $('.slim-scroll').slimScroll({ scrollTo: '0' });
});
   
function filtroListaJs(campoBusca, lista){
		// Recupera value do campo de busca
   var term=campoBusca.value.toLowerCase();
   	if( term != "")	{
	 var searchText = term;
        $('#' + lista + ' ul > li ').each(function(){
            var currentLiText = $(this).text(),
                showCurrentLi = currentLiText.toLowerCase().indexOf(searchText) !== -1;
            $(this).toggle(showCurrentLi);
        });  
   	}else{
   		// Quando não há nada digitado, mostra a tabela com todos os dados
   		$('#' + lista + ' ul > li').each(function(){
   	            var currentLiText = $(this).text(),
   	                showCurrentLi = currentLiText.toLowerCase().indexOf(searchText) == -1;
   	            $(this).toggle(showCurrentLi);
        }); 
   	}
}