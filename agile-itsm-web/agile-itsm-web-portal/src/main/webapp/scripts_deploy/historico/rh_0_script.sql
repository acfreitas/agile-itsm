delimiter $$

CREATE TABLE `rh_conhecimento` (
  `idConhecimento` int(11) NOT NULL,
  `descricao` char(100) NOT NULL,
  `detalhe` char(100) NOT NULL,
  PRIMARY KEY (`idConhecimento`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `rh_experienciainformatica` (
  `idExperienciaInformatica` int(11) NOT NULL,
  `descricao` char(100) NOT NULL,
  `detalhe` char(100) NOT NULL,
  PRIMARY KEY (`idExperienciaInformatica`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `rh_habilidade` (
  `idHabilidade` int(11) NOT NULL,
  `descricao` char(100) NOT NULL,
  `detalhe` char(100) NOT NULL,
  PRIMARY KEY (`idHabilidade`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$

delimiter $$

CREATE TABLE `rh_cargoatitudeindividual` (
  `idatitudeindividual` int(11) NOT NULL,
  `iddescricaocargo` int(11) NOT NULL,
  `obrigatorio` char(1) NOT NULL,
  PRIMARY KEY (`idatitudeindividual`,`iddescricaocargo`),
  KEY `fk_reference_722` (`iddescricaocargo`),
  CONSTRAINT `fk_reference_722` FOREIGN KEY (`iddescricaocargo`) REFERENCES `rh_descricaocargo` (`iddescricaocargo`),
  CONSTRAINT `fk_reference_723` FOREIGN KEY (`idatitudeindividual`) REFERENCES `rh_atitudeindividual` (`idAtitudeIndividual`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `rh_atitudecandidato` (
  `identrevista` int(11) NOT NULL,
  `idatitudeorganizacional` int(11) NOT NULL,
  `avaliacao` char(1) DEFAULT NULL,
  PRIMARY KEY (`identrevista`,`idatitudeorganizacional`),
  KEY `fk_reference_atitudorg` (`idatitudeorganizacional`),
  CONSTRAINT `fk_reference_entrevista` FOREIGN KEY (`identrevista`) REFERENCES `rh_entrevistacandidato` (`identrevista`),
  CONSTRAINT `fk_reference_atitudorg` FOREIGN KEY (`idatitudeorganizacional`) REFERENCES `rh_atitudeorganizacional` (`idatitudeorganizacional`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$
CREATE TABLE `rh_atitudeorganizacional` (
  `idatitudeorganizacional` int(11) NOT NULL,
  `descricao` varchar(200) NOT NULL,
  `detalhe` text,
  PRIMARY KEY (`idatitudeorganizacional`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `rh_atitudeindividual` (
  `idAtitudeIndividual` int(11) NOT NULL,
  `descricao` char(100) NOT NULL,
  `detalhe` char(100) NOT NULL,
  PRIMARY KEY (`idAtitudeIndividual`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$


CREATE TABLE `rh_cargocertificacao` (
  `iddescricaocargo` int(11) NOT NULL,
  `idcertificacao` int(11) NOT NULL,
  `obrigatorio` char(1) NOT NULL,
  PRIMARY KEY (`iddescricaocargo`,`idcertificacao`),
  KEY `fk_reference_727` (`idcertificacao`),
  CONSTRAINT `fk_reference_726` FOREIGN KEY (`iddescricaocargo`) REFERENCES `rh_descricaocargo` (`iddescricaocargo`),
  CONSTRAINT `fk_reference_727` FOREIGN KEY (`idcertificacao`) REFERENCES `rh_certificacao` (`idCertificacao`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `rh_cargoconhecimento` (
  `iddescricaocargo` int(11) NOT NULL,
  `idconhecimento` int(11) NOT NULL,
  `obrigatorio` char(1) NOT NULL,
  PRIMARY KEY (`iddescricaocargo`,`idconhecimento`),
  KEY `fk_reference_725` (`idconhecimento`),
  CONSTRAINT `fk_reference_724` FOREIGN KEY (`iddescricaocargo`) REFERENCES `rh_descricaocargo` (`iddescricaocargo`),
  CONSTRAINT `fk_reference_725` FOREIGN KEY (`idconhecimento`) REFERENCES `rh_conhecimento` (`idConhecimento`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `rh_cargocurso` (
  `iddescricaocargo` int(11) NOT NULL,
  `idcurso` int(11) NOT NULL,
  `obrigatorio` char(1) NOT NULL,
  PRIMARY KEY (`iddescricaocargo`,`idcurso`),
  KEY `fk_reference_729` (`idcurso`),
  CONSTRAINT `fk_reference_728` FOREIGN KEY (`iddescricaocargo`) REFERENCES `rh_descricaocargo` (`iddescricaocargo`),
  CONSTRAINT `fk_reference_729` FOREIGN KEY (`idcurso`) REFERENCES `rh_curso` (`idCurso`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `rh_cargoexperienciaanterior` (
  `iddescricaocargo` int(11) NOT NULL,
  `idconhecimento` int(11) NOT NULL,
  `obrigatorio` char(1) NOT NULL,
  PRIMARY KEY (`iddescricaocargo`,`idconhecimento`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `rh_cargoexperienciainformatica` (
  `idexperienciainformatica` int(11) NOT NULL,
  `iddescricaocargo` int(11) NOT NULL,
  `obrigatorio` char(1) NOT NULL,
  PRIMARY KEY (`idexperienciainformatica`,`iddescricaocargo`),
  KEY `fk_reference_733` (`iddescricaocargo`),
  CONSTRAINT `fk_reference_732` FOREIGN KEY (`idexperienciainformatica`) REFERENCES `rh_experienciainformatica` (`idExperienciaInformatica`),
  CONSTRAINT `fk_reference_733` FOREIGN KEY (`iddescricaocargo`) REFERENCES `rh_descricaocargo` (`iddescricaocargo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `rh_cargoformacaoacademica` (
  `idformacaoacademica` int(11) NOT NULL,
  `iddescricaocargo` int(11) NOT NULL,
  `obrigatorio` char(1) NOT NULL,
  PRIMARY KEY (`idformacaoacademica`,`iddescricaocargo`),
  KEY `fk_reference_735` (`iddescricaocargo`),
  CONSTRAINT `fk_reference_734` FOREIGN KEY (`idformacaoacademica`) REFERENCES `rh_formacaoacademica` (`idFormacaoAcademica`),
  CONSTRAINT `fk_reference_735` FOREIGN KEY (`iddescricaocargo`) REFERENCES `rh_descricaocargo` (`iddescricaocargo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `rh_cargohabilidade` (
  `idhabilidade` int(11) NOT NULL,
  `iddescricaocargo` int(11) NOT NULL,
  `obrigatorio` char(1) NOT NULL,
  PRIMARY KEY (`idhabilidade`,`iddescricaocargo`),
  KEY `fk_reference_737` (`iddescricaocargo`),
  CONSTRAINT `fk_reference_736` FOREIGN KEY (`idhabilidade`) REFERENCES `rh_habilidade` (`idHabilidade`),
  CONSTRAINT `fk_reference_737` FOREIGN KEY (`iddescricaocargo`) REFERENCES `rh_descricaocargo` (`iddescricaocargo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `rh_cargoidioma` (
  `iddescricaocargo` int(11) NOT NULL,
  `ididioma` int(11) NOT NULL,
  `obrigatorio` char(1) NOT NULL,
  PRIMARY KEY (`iddescricaocargo`,`ididioma`),
  KEY `fk_reference_731` (`ididioma`),
  CONSTRAINT `fk_reference_730` FOREIGN KEY (`iddescricaocargo`) REFERENCES `rh_descricaocargo` (`iddescricaocargo`),
  CONSTRAINT `fk_reference_731` FOREIGN KEY (`ididioma`) REFERENCES `rh_idioma` (`idIdioma`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `rh_certificacao` (
  `idCertificacao` int(11) NOT NULL,
  `descricao` char(100) NOT NULL,
  `detalhe` char(100) NOT NULL,
  PRIMARY KEY (`idCertificacao`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `rh_certificacaocurriculo` (
  `idcertificacao` int(11) NOT NULL,
  `idcurriculo` int(11) NOT NULL,
  `versao` char(100) NOT NULL,
  `validade` int(11) NOT NULL,
  `descricao` char(100) NOT NULL,
  PRIMARY KEY (`idcertificacao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$


delimiter $$

CREATE TABLE `rh_certificacaorequisicaopessoal` (
  `idcertificacao` int(11) NOT NULL,
  `versaocertificacao` char(100) NOT NULL,
  `validadecertificacao` char(100) NOT NULL,
  `descricaocertificacao` char(100) NOT NULL,
  `idcurriculo` int(11) NOT NULL,
  PRIMARY KEY (`idcertificacao`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `rh_curriculo` (
  `idcurriculo` int(11) NOT NULL,
  `portadorNecessidadeEspecial` char(1) NOT NULL,
  `iditemlistatipodeficiencia` int(11) DEFAULT NULL,
  `qtdefilhos` int(11) DEFAULT NULL,
  `nome` char(100) NOT NULL,
  `sexo` char(1) NOT NULL,
  `cpf` char(15) NOT NULL,
  `estadoCivil` smallint(6) NOT NULL,
  `dataNascimento` date NOT NULL,
  `filhos` char(1) NOT NULL,
  `cidadeNatal` varchar(100) NOT NULL,
  `idNaturalidade` int(11) NOT NULL,
  `observacoesEntrevista` char(100) DEFAULT NULL,
  PRIMARY KEY (`idcurriculo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `rh_curso` (
  `idCurso` int(11) NOT NULL,
  `descricao` char(100) NOT NULL,
  `detalhe` char(100) NOT NULL,
  PRIMARY KEY (`idCurso`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `rh_descricaocargo` (
  `iddescricaocargo` int(11) NOT NULL,
  `nomecargo` char(100) NOT NULL,
  `idcbo` int(11) DEFAULT NULL,
  `atividades` char(100) NOT NULL,
  `situacao` char(1) NOT NULL,
  `idsolicitacaoservico` int(11) DEFAULT NULL,
  `observacoes` text,
  `idParecerValidacao` int(11) DEFAULT NULL,
  PRIMARY KEY (`iddescricaocargo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `rh_emailcurriculo` (
  `idemail` int(11) NOT NULL,
  `idcurriculo` int(11) NOT NULL,
  `principal` char(1) NOT NULL,
  `descricaoemail` varchar(100) NOT NULL,
  PRIMARY KEY (`idemail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$


delimiter $$

CREATE TABLE `rh_enderecocurriculo` (
  `idendereco` int(11) NOT NULL,
  `idbairro` int(11) NOT NULL,
  `idcidade` int(11) NOT NULL,
  `iduf` int(11) NOT NULL,
  `idcurriculo` int(11) NOT NULL,
  `idtipoendereco` int(11) NOT NULL,
  `cep` varchar(20) NOT NULL,
  `complemento` varchar(100) NOT NULL,
  `correspondencia` char(1) NOT NULL,
  `nomecidade` varchar(100) NOT NULL,
  `nomebairro` varchar(100) NOT NULL,
  `logradouro` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idendereco`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$


delimiter $$

CREATE TABLE `rh_entrevistacandidato` (
  `identrevista` int(11) NOT NULL,
  `idcurriculo` int(11) NOT NULL,
  `identrevistador` int(11) NOT NULL,
  `tipoentrevista` varchar(20) NOT NULL,
  `datahora` timestamp NOT NULL,
  `caracteristicas` text,
  `possuioutraatividade` char(1) NOT NULL,
  `outraatividade` text,
  `concordaexclusividade` char(1) NOT NULL,
  `salarioatual` decimal(6,2) DEFAULT NULL,
  `pretensaosalarial` decimal(6,2) DEFAULT NULL,
  `datadisponibilidade` date DEFAULT NULL,
  `competencias` text,
  `observacoes` text,
  `resultado` char(1) NOT NULL ,
  `idtriagem` int(11) DEFAULT NULL,
  `trabalhoemequipe` text,
  `cargoPretendido` char(100) DEFAULT NULL,
  `planoCarreira` char(100) NOT NULL,
  PRIMARY KEY (`identrevista`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `rh_formacaoacademica` (
  `idFormacaoAcademica` int(11) NOT NULL,
  `descricao` char(100) NOT NULL,
  `detalhe` char(100) NOT NULL,
  PRIMARY KEY (`idFormacaoAcademica`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `rh_formacaocurriculo` (
  `idformacao` int(11) NOT NULL,
  `idtipoformacao` int(11) NOT NULL,
  `idsituacao` int(11) NOT NULL,
  `idcurriculo` int(11) NOT NULL,
  `instituicao` varchar(100) NOT NULL,
  `descricao` varchar(100) NOT NULL,
  PRIMARY KEY (`idformacao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$


delimiter $$

CREATE TABLE `rh_jornadadetrabalho` (
  `idjornada` int(11) NOT NULL,
  `descricao` char(100) NOT NULL,
  `escala` char(1) NOT NULL,
  `considerarferiados` char(1) NOT NULL,
  PRIMARY KEY (`idjornada`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$


delimiter $$

CREATE TABLE `rh_idioma` (
  `idIdioma` int(11) NOT NULL,
  `descricao` char(100) NOT NULL,
  `detalhe` char(100) NOT NULL,
  PRIMARY KEY (`idIdioma`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `rh_requisicaoatitudeindividual` (
  `idatitudeindividual` int(11) NOT NULL,
  `idsolicitacaoservico` int(11) NOT NULL,
  `obrigatorio` char(1) NOT NULL,
  PRIMARY KEY (`idatitudeindividual`,`idsolicitacaoservico`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `rh_requisicaocertificacao` (
  `idcertificacao` int(11) NOT NULL,
  `idsolicitacaoservico` int(11) NOT NULL,
  `obrigatorio` char(1) DEFAULT NULL,
  PRIMARY KEY (`idcertificacao`,`idsolicitacaoservico`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `rh_requisicaoconhecimento` (
  `idconhecimento` int(11) NOT NULL,
  `idsolicitacaoservico` int(11) NOT NULL,
  `obrigatorio` char(1) DEFAULT NULL,
  PRIMARY KEY (`idconhecimento`,`idsolicitacaoservico`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `rh_requisicaocurso` (
  `idsolicitacaoservico` int(11) NOT NULL,
  `idcurso` int(11) NOT NULL,
  `obrigatorio` char(1) DEFAULT NULL,
  PRIMARY KEY (`idsolicitacaoservico`,`idcurso`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `rh_requisicaoexperienciaanterior` (
  `idconhecimento` int(11) NOT NULL,
  `idsolicitacaoservico` int(11) NOT NULL,
  `obrigatorio` char(1) DEFAULT NULL,
  PRIMARY KEY (`idconhecimento`,`idsolicitacaoservico`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `rh_requisicaoexperienciainformatica` (
  `idexperienciainformatica` int(11) NOT NULL,
  `idsolicitacaoservico` int(11) NOT NULL,
  `obrigatorio` char(1) DEFAULT NULL,
  PRIMARY KEY (`idexperienciainformatica`,`idsolicitacaoservico`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `rh_requisicaoformacaoacademica` (
  `idformacaoacademica` int(11) NOT NULL,
  `idsolicitacaoservico` int(11) NOT NULL,
  `obrigatorio` char(1) DEFAULT NULL,
  PRIMARY KEY (`idformacaoacademica`,`idsolicitacaoservico`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `rh_requisicaohabilidade` (
  `idhabilidade` int(11) NOT NULL,
  `idsolicitacaoservico` int(11) NOT NULL,
  `obrigatorio` char(1) DEFAULT NULL,
  PRIMARY KEY (`idhabilidade`,`idsolicitacaoservico`),
  KEY `fk_reference_739` (`idsolicitacaoservico`),
  CONSTRAINT `fk_reference_738` FOREIGN KEY (`idhabilidade`) REFERENCES `rh_habilidade` (`idHabilidade`),
  CONSTRAINT `fk_reference_739` FOREIGN KEY (`idsolicitacaoservico`) REFERENCES `rh_requisicaopessoal` (`idsolicitacaoservico`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `rh_requisicaoidioma` (
  `ididioma` int(11) NOT NULL,
  `idsolicitacaoservico` int(11) NOT NULL,
  `obrigatorio` char(1) DEFAULT NULL,
  PRIMARY KEY (`ididioma`,`idsolicitacaoservico`),
  KEY `fk_reference_741` (`idsolicitacaoservico`),
  CONSTRAINT `fk_reference_740` FOREIGN KEY (`ididioma`) REFERENCES `rh_idioma` (`idIdioma`),
  CONSTRAINT `fk_reference_741` FOREIGN KEY (`idsolicitacaoservico`) REFERENCES `rh_requisicaopessoal` (`idsolicitacaoservico`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `rh_requisicaopessoal` (
  `idsolicitacaoservico` int(11) NOT NULL,
  `idCargo` char(100) NOT NULL,
  `vagas` int(11) NOT NULL,
  `tipoContratacao` char(1) DEFAULT NULL,
  `motivoContratacao` char(1) DEFAULT NULL,
  `salario` double NOT NULL,
  `idCentroCusto` int(11) NOT NULL,
  `idProjeto` int(11) NOT NULL,
  `rejeitada` char(1) DEFAULT NULL,
  `idParecerValidacao` int(11) DEFAULT NULL,
  `situacao` char(1) DEFAULT NULL,
  `confidencial` char(1) NOT NULL,
  `dataAbertura` date NOT NULL,
  `beneficios` char(100) DEFAULT NULL,
  `folgas` char(100) DEFAULT NULL,
  `horario` char(100) DEFAULT NULL,
  PRIMARY KEY (`idsolicitacaoservico`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `rh_telefonecurriculo` (
  `idtelefone` int(11) NOT NULL,
  `idtipotelefone` int(11) NOT NULL,
  `ddd` int(3) NOT NULL,
  `numerotelefone` varchar(15) NOT NULL,
  `idcurriculo` int(11) NOT NULL,
  PRIMARY KEY (`idtelefone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$


delimiter $$

CREATE TABLE `rh_triagemrequisicaopessoal` (
  `idtriagem` int(11) NOT NULL,
  `idsolicitacaoservico` int(11) NOT NULL,
  `idcurriculo` int(11) NOT NULL,
  `iditemtrabalhoentrevistarh` int(11) DEFAULT NULL,
  `iditemtrabalhoentrevistagestor` int(11) DEFAULT NULL,
  PRIMARY KEY (`idtriagem`),
  KEY `fk_reference_743` (`idsolicitacaoservico`),
  KEY `fk_reference_744` (`idcurriculo`),
  CONSTRAINT `fk_reference_743` FOREIGN KEY (`idsolicitacaoservico`) REFERENCES `rh_requisicaopessoal` (`idsolicitacaoservico`),
  CONSTRAINT `fk_reference_744` FOREIGN KEY (`idcurriculo`) REFERENCES `rh_curriculo` (`idcurriculo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$
