-- Iniciação e execução de tarefas
select s.idsolicitacaoservico as numero, 
       e.seqreabertura, 
       h.datahora, 
       u.login responsavel, 
       h.acao, 
       l.documentacao as tarefa
from citsmart.bpm_historicoitemtrabalho h 
    inner join citsmart.bpm_itemtrabalhofluxo i
       on h.iditemtrabalho = i.iditemtrabalho
    inner join citsmart.bpm_elementofluxo l
       on l.idelemento = i.idelemento
    inner join citsmart.bpm_instanciafluxo f
       on f.idinstancia = i.idinstancia
    inner join citsmart.execucaosolicitacao e
       on e.idinstanciafluxo = i.idinstancia
    inner join citsmart.solicitacaoservico s
       on s.idsolicitacaoservico = i.idinstancia
    inner join citsmart.usuario u
       on u.idusuario = h.idresponsavel
  where h.acao in ('Iniciar', 'Executar')
  order by s.idsolicitacaoservico, h.datahora, u.login;

-- Delegação de tarefas a usuários
select s.idsolicitacaoservico as numero, 
       e.seqreabertura, 
       h.datahora, 
       u.login responsavel, 
       h.acao, 
       l.documentacao as tarefa,
       ud.login as 'atribuido a'
from citsmart.bpm_historicoitemtrabalho h 
    inner join citsmart.bpm_itemtrabalhofluxo i
       on h.iditemtrabalho = i.iditemtrabalho
    inner join citsmart.bpm_elementofluxo l
       on l.idelemento = i.idelemento
    inner join citsmart.bpm_instanciafluxo f
       on f.idinstancia = i.idinstancia
    inner join citsmart.execucaosolicitacao e
       on e.idinstanciafluxo = i.idinstancia
    inner join citsmart.solicitacaoservico s
       on s.idsolicitacaoservico = i.idinstancia
    inner join citsmart.usuario u
       on u.idusuario = h.idresponsavel
    inner join citsmart.usuario ud
       on ud.idusuario = h.idusuario
  where h.acao in ('Delegar') 
  order by s.idsolicitacaoservico, h.datahora, u.login;

-- Delegação de tarefas a grupos
select s.idsolicitacaoservico as numero, 
       e.seqreabertura, 
       h.datahora, 
       u.login responsavel, 
       h.acao, 
       l.documentacao as tarefa,
       gd.sigla as 'atribuido a'
from citsmart.bpm_historicoitemtrabalho h 
    inner join citsmart.bpm_itemtrabalhofluxo i
       on h.iditemtrabalho = i.iditemtrabalho
    inner join citsmart.bpm_elementofluxo l
       on l.idelemento = i.idelemento
    inner join citsmart.bpm_instanciafluxo f
       on f.idinstancia = i.idinstancia
    inner join citsmart.execucaosolicitacao e
       on e.idinstanciafluxo = i.idinstancia
    inner join citsmart.solicitacaoservico s
       on s.idsolicitacaoservico = i.idinstancia
    inner join citsmart.usuario u
       on u.idusuario = h.idresponsavel
    inner join citsmart.gruposseguranca gd
       on gd.idgrupo = h.idgrupo
  where h.acao in ('Delegar') 
  order by s.idsolicitacaoservico, h.datahora, u.login;

-- Delegação de tarefas a grupos
select s.idsolicitacaoservico as numero, 
       e.seqreabertura, 
       r.datahora, 
       u.login responsavel
from citsmart.reaberturasolicitacao r
    inner join citsmart.solicitacaoservico s
       on s.idsolicitacaoservico = r.idsolicitacaoservico
    inner join citsmart.execucaosolicitacao e
       on e.idsolicitacaoservico = s.idsolicitacaoservico
      and e.seqreabertura = r.seqreabertura
    inner join citsmart.bpm_instanciafluxo f
       on f.idinstancia = e.idinstanciafluxo
    inner join citsmart.usuario u
       on u.idusuario = r.idresponsavel
  order by s.idsolicitacaoservico, r.datahora, u.login;

  
  
-- Juntado:
select s.idsolicitacaoservico as numero, 
       e.seqreabertura, 
       h.datahora, 
       u.login responsavel, 
       h.acao, 
       l.documentacao as tarefa,
       gd.sigla as atribuido_grupo,
       ud.login as atribuido_usuario
from citsmart.bpm_historicoitemtrabalho h 
    inner join citsmart.bpm_itemtrabalhofluxo i
       on h.iditemtrabalho = i.iditemtrabalho
    inner join citsmart.bpm_elementofluxo l
       on l.idelemento = i.idelemento
    inner join citsmart.bpm_instanciafluxo f
       on f.idinstancia = i.idinstancia
    inner join citsmart.execucaosolicitacao e
       on e.idinstanciafluxo = i.idinstancia
    inner join citsmart.solicitacaoservico s
       on s.idsolicitacaoservico = i.idinstancia
    left outer join citsmart.usuario u
       on u.idusuario = h.idresponsavel
    left outer join citsmart.usuario ud
       on ud.idusuario = h.idusuario       
    left outer join citsmart.gruposseguranca gd
       on gd.idgrupo = h.idgrupo       
  where h.acao in ('Iniciar', 'Executar', 'Delegar')
  and s.idsolicitacaoservico = 6
  order by s.idsolicitacaoservico, h.datahora, u.login;