
-- INICIO - david.silva 27/06/2014

ALTER TABLE rh_perspectivatecnicaformacaoacademica ADD idformacaoacademica INTEGER NOT NULL;

ALTER TABLE rh_perspectivatecnicacertificacao ADD idcertificacao INTEGER NOT NULL;
ALTER TABLE rh_perspectivatecnicacertificacao ADD CONSTRAINT fk_certificacao FOREIGN KEY (idcertificacao) REFERENCES rh_certificacao (idcertificacao);

ALTER TABLE rh_perspectivatecnicacurso ADD idcurso INTEGER NOT NULL;
ALTER TABLE rh_perspectivatecnicacurso ADD CONSTRAINT fk_curso FOREIGN KEY (idcurso) REFERENCES rh_curso (idcurso);

ALTER TABLE rh_perspectivatecnicaidioma ADD ididioma INTEGER NOT NULL;
ALTER TABLE rh_perspectivatecnicaidioma ADD CONSTRAINT fk_idioma FOREIGN KEY (ididioma) REFERENCES rh_idioma (ididioma);

ALTER TABLE rh_perspectivatecnicaexperiencia ADD idconhecimento INTEGER NOT NULL;
ALTER TABLE rh_perspectivatecnicaexperiencia ADD CONSTRAINT fk_conhecimento FOREIGN KEY (idconhecimento) REFERENCES rh_conhecimento (idconhecimento);

ALTER TABLE rh_perspectivacomportamentalfuncao ADD idatitudeindividual INTEGER NOT NULL;
ALTER TABLE rh_perspectivacomportamentalfuncao ADD CONSTRAINT fk_atitudeindividual FOREIGN KEY (idatitudeindividual) REFERENCES rh_atitudeindividual (idatitudeindividual);

-- FIM - david.silva 27/06/2014

-- INICIO - thiago.borges - 30/06/2014

ALTER TABLE integranteviagem ADD COLUMN idtarefa int NULL;

-- FIM - thiago.borges - 30/06/2014

-- INICIO - euler.ramos - 11/07/2014
delete from menu where link = '/relatorioEficaciaNaDocumentacao/relatorioEficaciaNaDocumentacao.load';
delete from menu where link = '/relatorioEficaciaDoSoftware/relatorioEficaciaDoSoftware.load';
-- FIM - euler.ramos - 11/07/2014

-- INICIO - david.silva 15/07/2014

ALTER TABLE tipomovimfinanceiraviagem ALTER COLUMN exigejustificativa SET DEFAULT 'N';

-- FIM - david.silva 15/07/2014

-- INICIO - Mário Hayasaki Júnior - 22/07/2014
alter table servicocontrato add expandir char(1);
--Fim
-- FIM - david.silva 15/07/2014

-- INICIO - renato.jesus 22/07/2014
ALTER TABLE rh_candidato ADD idempregado INTEGER NULL DEFAULT NULL;
ALTER TABLE rh_candidato ALTER COLUMN cpf DROP NOT NULL;
-- FIM - renato.jesus 22/07/2014

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
SET    pesquisa = 'N', 
       grava = 'N', 
       deleta = 'N' 
WHERE  idmenu = (SELECT menu.idmenu 
                 FROM   menu  
                 WHERE  menu.nome LIKE '$menu.nome.recursosHumanos'); 
-- FIM - david.silva 12/08/2014-- FIM - renato.jesus 04/08/2014
