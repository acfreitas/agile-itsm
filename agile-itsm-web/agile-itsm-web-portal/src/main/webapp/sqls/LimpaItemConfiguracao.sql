update recurso set iditemconfiguracao = null where iditemconfiguracao is not null ;
delete from empregadoitemconfiguracao;
delete from eventoitemconfiguracao;
delete from historicotentativa;
delete from justificacaofalha;
delete from usuarioitemconfiguracao;
delete from requisicaomudancaitemconfiguracao;
delete from requisicaoliberacaoitemconfiguracao;
delete from problemaitemconfiguracao;
delete from conhecimentoic;
delete from softwareslistanegraencontrados;
delete from itemconfiguracaoevento;
delete from itemcfgsolicitacaoserv;
delete from imagemitemconfiguracao;
delete from auditoriaitemconfig;
delete from historicovalor;
delete from historicoic;
delete from valor;
delete from itemconfiguracao;