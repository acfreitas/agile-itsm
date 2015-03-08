select e.nome, a.*
from bpm_elementofluxo e 
inner join bpm_itemtrabalhofluxo i on i.idelemento = e.idelemento
inner join bpm_atribuicaofluxo a on a.iditemtrabalho = i.iditemtrabalho
inner join execucaosolicitacao x on x.idinstanciafluxo = i.idinstancia
where x.idsolicitacaoservico = 533 order by iditemtrabalho, datahora;