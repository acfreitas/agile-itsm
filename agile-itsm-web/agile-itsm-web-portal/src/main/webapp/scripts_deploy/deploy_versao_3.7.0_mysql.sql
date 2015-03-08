set sql_safe_updates = 0;

-- INICIO - david.silva 27/06/2014

ALTER TABLE rh_perspectivatecnicaformacaoacademica ADD COLUMN idformacaoacademica INT(11) NOT NULL;
ALTER TABLE rh_perspectivatecnicaformacaoacademica ADD CONSTRAINT fk_formacaoacademica FOREIGN KEY (idformacaoacademica) REFERENCES rh_formacaoacademica (idformacaoacademica);

ALTER TABLE rh_perspectivatecnicacertificacao ADD COLUMN idcertificacao INT(11) NOT NULL;
ALTER TABLE rh_perspectivatecnicacertificacao ADD CONSTRAINT fk_certificacao FOREIGN KEY (idcertificacao) REFERENCES rh_certificacao (idcertificacao);

ALTER TABLE rh_perspectivatecnicacurso ADD COLUMN idcurso INT(11) NOT NULL;
ALTER TABLE rh_perspectivatecnicacurso ADD CONSTRAINT fk_curso FOREIGN KEY (idcurso) REFERENCES rh_curso (idcurso);

ALTER TABLE rh_perspectivatecnicaidioma ADD COLUMN ididioma INT(11) NOT NULL;
ALTER TABLE rh_perspectivatecnicaidioma ADD CONSTRAINT fk_idioma FOREIGN KEY (ididioma) REFERENCES rh_idioma (ididioma);

ALTER TABLE rh_perspectivatecnicaexperiencia ADD COLUMN idconhecimento INT(11) NOT NULL;
ALTER TABLE rh_perspectivatecnicaexperiencia ADD CONSTRAINT fk_conhecimento FOREIGN KEY (idconhecimento) REFERENCES rh_conhecimento (idconhecimento);

ALTER TABLE rh_perspectivacomportamentalfuncao ADD COLUMN idatitudeindividual INT(11) NOT NULL;
ALTER TABLE rh_perspectivacomportamentalfuncao ADD CONSTRAINT fk_atitudeindividual FOREIGN KEY (idatitudeindividual) REFERENCES rh_atitudeindividual (idatitudeindividual);

-- FIM - david.silva 27/06/2014

-- INICIO - thiago.borges - 30/06/2014

ALTER TABLE integranteviagem ADD COLUMN idtarefa INT(11) NULL;

-- FIM - thiago.borges - 30/06/2014

-- INICIO - renato.jesus - 27/06/2014
ALTER TABLE rh_funcaoexperienciaprofissionalcurriculo CHANGE COLUMN descricaofuncao descricaofuncao VARCHAR(600) NULL DEFAULT NULL;
-- FIM - renato.jesus - 27/06/2014

-- INICIO - renato.jesus - 02/07/2014
alter table rh_experienciaprofissionalcurriculo drop column funcao;
alter table rh_experienciaprofissionalcurriculo drop column periodo;
alter table rh_funcaoexperienciaprofissionalcurriculo add column iniciofuncao date null;
alter table rh_funcaoexperienciaprofissionalcurriculo add column fimfuncao date null;
-- FIM - renato.jesus - 02/07/2014

-- INICIO - euler.ramos - 11/07/2014
delete from menu where link = '/relatorioEficaciaNaDocumentacao/relatorioEficaciaNaDocumentacao.load';
delete from menu where link = '/relatorioEficaciaDoSoftware/relatorioEficaciaDoSoftware.load';
-- FIM - euler.ramos - 11/07/2014
-- INICIO - david.silva 15/07/2014

ALTER TABLE tipomovimfinanceiraviagem CHANGE COLUMN exigejustificativa exigejustificativa CHAR(1) NOT NULL DEFAULT 'N';

-- FIM - david.silva 15/07/2014

-- INICIO - Mário Hayasaki Júnior - 22/07/2014
alter table servicocontrato add column expandir char(1);
-- FIM 

-- INICIO - renato.jesus 22/07/2014
ALTER TABLE rh_candidato ADD COLUMN idempregado INT(11) NULL DEFAULT NULL;
ALTER TABLE rh_candidato CHANGE COLUMN cpf cpf VARCHAR(14) NULL DEFAULT NULL;
-- FIM - renato.jesus 22/07/2014

-- INICIO - renato.jesus 29/07/2014
ALTER TABLE rh_competencia ADD COLUMN nivelcompetencia INT(1) NULL;
-- FIM - renato.jesus 29/07/2014

-- INICIO - renato.jesus 04/08/2014
ALTER TABLE rh_certificacaocurriculo ADD CONSTRAINT fk_rh_certificacao_curriculo FOREIGN KEY (idcurriculo) REFERENCES rh_curriculo ( idcurriculo ) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE rh_competencia ADD CONSTRAINT fk_rh_competencia_curriculo FOREIGN KEY (idcurriculo) REFERENCES rh_curriculo ( idcurriculo ) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE rh_emailcurriculo ADD CONSTRAINT fk_rh_email_curriculo FOREIGN KEY (idcurriculo) REFERENCES rh_curriculo ( idcurriculo ) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE rh_enderecocurriculo ADD CONSTRAINT fk_rh_endereco_curriculo FOREIGN KEY (idcurriculo) REFERENCES rh_curriculo ( idcurriculo ) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE rh_formacaocurriculo ADD CONSTRAINT fk_rh_formacao_curriculo FOREIGN KEY (idcurriculo) REFERENCES rh_curriculo ( idcurriculo ) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE rh_idiomacurriculo ADD CONSTRAINT fk_rh_idioma_curriculo FOREIGN KEY (idcurriculo) REFERENCES rh_curriculo ( idcurriculo ) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE rh_telefonecurriculo ADD CONSTRAINT fk_rh_telefone_curriculo FOREIGN KEY (idcurriculo) REFERENCES rh_curriculo ( idcurriculo ) ON DELETE NO ACTION ON UPDATE NO ACTION;
-- FIM - renato.jesus 04/08/2014

-- INICIO - david.silva 08/08/2014
update menu set datafim = '2014-08-08' where link = '/atitudeIndividual/atitudeIndividual.load' and nome = '$menu.nome.atitudeIndividual';
update menu set datafim = '2014-08-08' where link = '/atitudeIndividual/atitudeIndividual.load' and nome = '$menu.nome.comportamento';
update menu set datafim = '2014-08-08' where link = '/atitudeIndividual/atitudeIndividual.load' and nome = '$menu.nome.comportamentoAtitude';
update menu set datafim = '2014-08-08' where link = '/atribuicaoRequisicaoFuncao/atribuicaoRequisicaoFuncao.load' and nome = '$menu.nome.atribuicaoRequisicaoFuncao';
update menu set datafim = '2014-08-08' where link = '/certificacao/certificacao.load' and nome = '$menu.nome.certificacao';
update menu set datafim = '2014-08-08' where link = '/conhecimento/conhecimento.load' and nome = '$menu.nome.conhecimento';
update menu set datafim = '2014-08-08' where link = '/templateCurriculo/templateCurriculo.load' and nome = '$menu.nome.curriculo';
update menu set datafim = '2014-08-08' where link = '/curso/curso.load' and nome = '$menu.nome.curso';
update menu set datafim = '2014-08-08' where link = '/conhecimento/conhecimento.load' and nome = '$menu.nome.experiencia';
update menu set datafim = '2014-08-08' where link = '/experienciaInformatica/experienciaInformatica.load' and nome = '$menu.nome.experienciaInformatica';
update menu set datafim = '2014-08-08' where link = '/formacaoAcademica/formacaoAcademica.load' and nome = '$menu.nome.formacaoAcademica';
update menu set datafim = '2014-08-08' where link = '/habilidade/habilidade.load' and nome = '$menu.nome.habilidade';
update menu set datafim = '2014-08-08' where link = '/jornadaEmpregado/jornadaEmpregado.load' and nome = '$menu.nome.horarioTrabalho';
update menu set datafim = '2014-08-08' where link = '/justificativaListaNegra/justificativaListaNegra.load' and nome = '$rh.justificativaListaNegra';
update menu set datafim = '2014-08-08' where link = '/justificativaRequisicaoFuncao/justificativaRequisicaoFuncao.load' and nome = '$menu.nome.justificativaRequisicaoFuncao';
update menu set datafim = '2014-08-08' where link = '/manualFuncao/manualFuncao.load' and nome = '$ManualFuncao.Titulo';
update menu set datafim = '2014-08-08' where link = '/historicoFuncional/historicoFuncional.load' and nome = '$rh.historicoFuncional';
-- FIM - david.silva 08/08/2014

-- INICIO - david.silva 12/08/2014
UPDATE perfilacessomenu 
       INNER JOIN menu 
               ON menu.idmenu = perfilacessomenu.idmenu 
       INNER JOIN perfilacessogrupo 
               ON perfilacessogrupo.idperfil = perfilacessomenu.idperfilacesso 
       INNER JOIN grupo 
               ON grupo.idgrupo = perfilacessogrupo.idgrupo 
SET    perfilacessomenu.pesquisa = ( CASE 
                                       WHEN 
              grupo.nome LIKE 'CENTRAL IT - SISTEMAS INTERNOS - RH' THEN 'S' 
                                       ELSE 'N' 
                                     END ), 
       perfilacessomenu.grava = ( CASE 
                                    WHEN 
       grupo.nome LIKE 'CENTRAL IT - SISTEMAS INTERNOS - RH' THEN 'S' 
                                    ELSE 'N' 
                                  END ), 
       perfilacessomenu.deleta = ( CASE 
                                     WHEN 
       grupo.nome LIKE 'CENTRAL IT - SISTEMAS INTERNOS - RH' THEN 'S' 
                                     ELSE 'N' 
                                   END ) 
WHERE  menu.nome IN ( '$menu.nome.recursosHumanos', '$menu.nome.cadastrosRH', 
                                          '$menu.nome.cadastrosRH.idioma', 
                             '$menu.nome.pesquisa', 
                                          '$menu.nome.pesquisaCurrriculo' );
-- FIM - david.silva 12/08/2014
-- INICIO - renato.jesus 14/08/2014
ALTER TABLE rh_enderecocurriculo CHANGE COLUMN logradouro logradouro VARCHAR(100) NULL;
-- FIM - renato.jesus 14/08/2014

set sql_safe_updates = 1;