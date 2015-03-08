-- MySQL dump 10.13  Distrib 5.5.29, for debian-linux-gnu (x86_64)
--
-- Host: 10.2.1.7    Database: citsmart_homologacao_implantacao
-- ------------------------------------------------------
-- Server version	5.1.61

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `acaoplanomelhoria`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acaoplanomelhoria` (
  `idacaoplanomelhoria` int(11) NOT NULL,
  `idplanomelhoria` int(11) NOT NULL,
  `idobjetivoplanomelhoria` int(11) NOT NULL,
  `tituloacao` varchar(255) NOT NULL,
  `detalhamentoacao` text,
  `datainicio` date DEFAULT NULL,
  `datafim` date DEFAULT NULL,
  `responsavel` varchar(255) DEFAULT NULL,
  `dataconclusao` date DEFAULT NULL,
  `criadopor` varchar(255) DEFAULT NULL,
  `modificadopor` varchar(255) DEFAULT NULL,
  `datacriacao` date DEFAULT NULL,
  `ultmodificacao` date DEFAULT NULL,
  PRIMARY KEY (`idacaoplanomelhoria`),
  KEY `ix_act_pm_id` (`idobjetivoplanomelhoria`),
  KEY `fk_ref_plmact` (`idplanomelhoria`),
  CONSTRAINT `fk_ref_objact` FOREIGN KEY (`idobjetivoplanomelhoria`) REFERENCES `objetivoplanomelhoria` (`idobjetivoplanomelhoria`),
  CONSTRAINT `fk_ref_plmact` FOREIGN KEY (`idplanomelhoria`) REFERENCES `planomelhoria` (`idplanomelhoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acaoplanomelhoria`
--

LOCK TABLES `acaoplanomelhoria` WRITE;
/*!40000 ALTER TABLE `acaoplanomelhoria` DISABLE KEYS */;
/*!40000 ALTER TABLE `acaoplanomelhoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acordonivelservico`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acordonivelservico` (
  `idacordonivelservico` bigint(20) NOT NULL,
  `idservicocontrato` bigint(20) DEFAULT NULL,
  `idprioridadepadrao` bigint(20) DEFAULT NULL,
  `situacao` char(1) NOT NULL,
  `titulosla` varchar(500) NOT NULL,
  `disponibilidade` decimal(15,3) DEFAULT NULL,
  `descricaosla` text,
  `escoposla` text,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  `avaliarem` date DEFAULT NULL,
  `tipo` char(1) DEFAULT NULL,
  `deleted` char(1) DEFAULT NULL,
  `valorLimite` decimal(15,3) DEFAULT NULL,
  `detalheGlosa` text,
  `detalheLimiteGlosa` text,
  `unidadeValorLimite` varchar(150) DEFAULT NULL,
  `impacto` char(1) DEFAULT NULL,
  `urgencia` char(1) DEFAULT NULL,
  `permiteMudarImpUrg` char(1) DEFAULT NULL,
  `idFormula` int(11) DEFAULT NULL,
  `contatos` text,
  `criadoEm` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `criadoPor` varchar(30) DEFAULT NULL,
  `modificadoEm` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modificadoPor` varchar(30) DEFAULT NULL,
  `tempoauto` decimal(15,3) DEFAULT NULL,
  `idprioridadeauto1` int(11) DEFAULT NULL,
  `idprioridadeauto2` int(11) DEFAULT NULL,
  `idprioridadeauto3` int(11) DEFAULT NULL,
  `idprioridadeauto4` int(11) DEFAULT NULL,
  `idprioridadeauto5` int(11) DEFAULT NULL,
  `idgrupo1` int(11) DEFAULT NULL,
  `idgrupo2` int(11) DEFAULT NULL,
  `idgrupo3` int(11) DEFAULT NULL,
  `idgrupo4` int(11) DEFAULT NULL,
  `idgrupo5` int(11) DEFAULT NULL,
  PRIMARY KEY (`idacordonivelservico`),
  KEY `fk_reference_102` (`idprioridadepadrao`),
  KEY `fk_reference_39` (`idservicocontrato`),
  KEY `fk_reference_572` (`idFormula`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acordonivelservico`
--

LOCK TABLES `acordonivelservico` WRITE;
/*!40000 ALTER TABLE `acordonivelservico` DISABLE KEYS */;
INSERT INTO `acordonivelservico` VALUES (1,NULL,NULL,'A','SLA 1',NULL,'Descrição do Acordo de Nível de Servico 1','','2013-04-01',NULL,'2014-12-31','T','N',NULL,'','','','B','B','S',NULL,'','2013-04-08 11:56:35',NULL,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,NULL,NULL,'A','SLA 2',NULL,'Descrição do Acordo de Nível de Servico 1','','2013-04-01',NULL,'2014-12-31','T','N',NULL,'','','','B','B','S',NULL,'','2013-04-08 11:56:45',NULL,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(3,NULL,NULL,'A','SLA 3',NULL,'Descrição do Acordo de Nível de Servico 3','','2013-04-01',NULL,'2014-12-31','T','N',NULL,'','','','B','B','S',NULL,'','2013-04-08 11:56:57',NULL,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `acordonivelservico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acordonivelservico_hist`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acordonivelservico_hist` (
  `idacordonivelservico_hist` bigint(20) NOT NULL,
  `idacordonivelservico` bigint(20) NOT NULL,
  `idservicocontrato` bigint(20) DEFAULT NULL,
  `idprioridadepadrao` int(11) DEFAULT NULL,
  `situacao` char(1) NOT NULL,
  `titulosla` varchar(500) NOT NULL,
  `disponibilidade` decimal(10,3) DEFAULT NULL,
  `descricaosla` text,
  `escoposla` text,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  `avaliarem` date DEFAULT NULL,
  `tipo` char(1) DEFAULT NULL,
  `valorlimite` decimal(15,3) DEFAULT NULL,
  `detalheglosa` text,
  `detalhelimiteglosa` text,
  `unidadevalorlimite` varchar(150) DEFAULT NULL,
  `impacto` char(1) DEFAULT NULL,
  `urgencia` char(1) DEFAULT NULL,
  `permiteMudarImpUrg` char(1) DEFAULT NULL,
  `deleted` char(1) DEFAULT NULL,
  `criadoem` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `criadopor` varchar(255) DEFAULT NULL,
  `modificadoem` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modificadopor` varchar(255) DEFAULT NULL,
  `conteudodados` text,
  `idformula` int(11) DEFAULT NULL,
  PRIMARY KEY (`idacordonivelservico_hist`),
  KEY `fk_reference_102` (`idprioridadepadrao`),
  KEY `fk_reference_91` (`idservicocontrato`),
  CONSTRAINT `fk_reference_102` FOREIGN KEY (`idprioridadepadrao`) REFERENCES `prioridade` (`idprioridade`),
  CONSTRAINT `fk_reference_91` FOREIGN KEY (`idservicocontrato`) REFERENCES `servicocontrato` (`idservicocontrato`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acordonivelservico_hist`
--

LOCK TABLES `acordonivelservico_hist` WRITE;
/*!40000 ALTER TABLE `acordonivelservico_hist` DISABLE KEYS */;
INSERT INTO `acordonivelservico_hist` VALUES (1,1,NULL,NULL,'A','Acordo de Nível de Servico 1',NULL,'Descrição do Acordo de Nível de Servico 1','','2013-04-01',NULL,'2014-12-31','T',NULL,'','','','B','B','S','N','2013-04-05 04:00:00','Administrador','2013-04-05 04:00:00','Administrador','JSONDATA: TRUE\nDETALHEGLOSA: \nURGENCIA: B\nDESCRICAORESULTADOS: \nHH-2-5: \nHH-2-4: \nHH-2-3: \nHH-2-2: \nHH-2-1: \nAVALIAREM: 31/12/2014\nHH-1-4: \nHH-1-3: \nACAOFLUXO: \nHH-1-2: \nHH-1-1: \nESCOPOSLA: \nHH-1-5: \nDINAMICVIEWSJSON_DATA: \nIDFLUXO: \nJSONMATRIZ: \nIMPACTO: B\nDINAMICVIEWSIDVISAO: 67\nIDGRUPO1: \nIDUNIDADE_1: \nTITULOSLA: Acordo de Nível de Servico 1\nDISPONIBILIDADE: \nLIMITEGLOSA: \nTEMPOAUTO: \nTIPO: T\nDETALHELIMITEGLOSA: \nNOCACHE: Fri Apr 05 2013 09:28:14 GMT-0300 (Hora oficial do Brasil)\nDINAMICVIEWSJSON_TEMPDATA: \nKEYCONTROL: \nMM-2-4: \nMM-2-5: \nMM-2-2: \nMM-2-3: \nMM-2-1: \nMM-1-3: \nMM-1-4: \nPERMITEMUDARIMPURG: S\nMM-1-1: \nMM-1-2: \nDINAMICVIEWSTABLESVINC: \"TABLE_SEARCH_62\",\"TABLE_SEARCH_63\"\nMM-1-5: \nCONTATOS: \nVALORAJUSTESERIALIZADO: idServicoContratoidAcordoNivelServicoserialversionuiddeletedquantidadeFalhasvalorAjusteidControleCITFrameworknull\nDINAMICVIEWSACAOPESQUISASELECIONADA: \nVALORAJUSTE: \nQUANTIDADEFALHAS: \nDATAFIM: \nMODOEXIBICAO: N\nVALORLIMITE: \nSESSION.NUMERO_CONTRATO_EDICAO: 1\nUNIDADEVALORLIMITE: \nGLOSA: \nDINAMICVIEWSIDVISAOPESQUISASELECIONADA: \nIDACORDONIVELSERVICO: 1\nDATAINICIO: 01/04/2013\nDESCRICAOSLA: Descrição do Acordo de Nível de Servico 1\nSESSION.DINAMICVIEWS_SAVEINFO: null\nIDPRIORIDADEAUTO1: \nIDTAREFA: \nSITUACAO: A\nRESULTADOSESPERADOSSERIALIZADO: idServicoContratoidAcordoNivelServicodeletedlimiteslimiteGlosaglosadescricaoResultadosidControleCITFrameworknull\nLIMITES: \nDINAMICVIEWSDADOSADICIONAIS: {}\n',NULL),(2,2,NULL,NULL,'A','Acordo de Nível de Servico 2',NULL,'Descrição do Acordo de Nível de Servico 1','','2013-04-01',NULL,'2014-12-31','T',NULL,'','','','B','B','S','N','2013-04-05 04:00:00','Administrador','2013-04-05 04:00:00','Administrador','JSONDATA: \nDETALHEGLOSA: \nURGENCIA: B\nDESCRICAORESULTADOS: \nHH-2-5: \nHH-2-4: \nHH-2-3: \nHH-2-2: \nHH-2-1: \nAVALIAREM: 31/12/2014\nHH-1-4: \nHH-1-3: \nACAOFLUXO: \nHH-1-2: \nHH-1-1: \nESCOPOSLA: \nHH-1-5: \nDINAMICVIEWSJSON_DATA: \nIDFLUXO: \nJSONMATRIZ: \nIMPACTO: B\nDINAMICVIEWSIDVISAO: 67\nIDGRUPO1: \nIDUNIDADE_1: \nTITULOSLA: Acordo de Nível de Servico 2\nDISPONIBILIDADE: \nLIMITEGLOSA: \nTEMPOAUTO: \nTIPO: T\nDETALHELIMITEGLOSA: \nNOCACHE: Fri Apr 05 2013 09:29:03 GMT-0300 (Hora oficial do Brasil)\nDINAMICVIEWSJSON_TEMPDATA: \nKEYCONTROL: \nMM-2-4: \nMM-2-5: \nMM-2-2: \nMM-2-3: \nMM-2-1: \nMM-1-3: \nMM-1-4: \nPERMITEMUDARIMPURG: S\nMM-1-1: \nMM-1-2: \nDINAMICVIEWSTABLESVINC: \nMM-1-5: \nCONTATOS: \nVALORAJUSTESERIALIZADO: idServicoContratoidAcordoNivelServicoserialversionuiddeletedquantidadeFalhasvalorAjusteidControleCITFrameworknull\nDINAMICVIEWSACAOPESQUISASELECIONADA: \nVALORAJUSTE: \nQUANTIDADEFALHAS: \nDATAFIM: \nMODOEXIBICAO: N\nVALORLIMITE: \nSESSION.NUMERO_CONTRATO_EDICAO: 1\nUNIDADEVALORLIMITE: \nGLOSA: \nDINAMICVIEWSIDVISAOPESQUISASELECIONADA: \nIDACORDONIVELSERVICO: 2\nDATAINICIO: 01/04/2013\nDESCRICAOSLA: Descrição do Acordo de Nível de Servico 1\nSESSION.DINAMICVIEWS_SAVEINFO: null\nIDPRIORIDADEAUTO1: \nIDTAREFA: \nSITUACAO: A\nRESULTADOSESPERADOSSERIALIZADO: idServicoContratoidAcordoNivelServicodeletedlimiteslimiteGlosaglosadescricaoResultadosidControleCITFrameworknull\nLIMITES: \nDINAMICVIEWSDADOSADICIONAIS: {}\n',NULL),(3,3,NULL,NULL,'A','Acordo de Nível de Servico 3',NULL,'Descrição do Acordo de Nível de Servico 3','','2013-04-01',NULL,'2014-12-31','T',NULL,'','','','B','B','S','N','2013-04-05 04:00:00','Administrador','2013-04-05 04:00:00','Administrador','JSONDATA: \nDETALHEGLOSA: \nURGENCIA: B\nDESCRICAORESULTADOS: \nHH-2-5: \nHH-2-4: \nHH-2-3: \nHH-2-2: \nHH-2-1: \nAVALIAREM: 31/12/2014\nHH-1-4: \nHH-1-3: \nACAOFLUXO: \nHH-1-2: \nHH-1-1: \nESCOPOSLA: \nHH-1-5: \nDINAMICVIEWSJSON_DATA: \nIDFLUXO: \nJSONMATRIZ: \nIMPACTO: B\nDINAMICVIEWSIDVISAO: 67\nIDGRUPO1: \nIDUNIDADE_1: \nTITULOSLA: Acordo de Nível de Servico 3\nDISPONIBILIDADE: \nLIMITEGLOSA: \nTEMPOAUTO: \nTIPO: T\nDETALHELIMITEGLOSA: \nNOCACHE: Fri Apr 05 2013 09:30:09 GMT-0300 (Hora oficial do Brasil)\nDINAMICVIEWSJSON_TEMPDATA: \nKEYCONTROL: \nMM-2-4: \nMM-2-5: \nMM-2-2: \nMM-2-3: \nMM-2-1: \nMM-1-3: \nMM-1-4: \nPERMITEMUDARIMPURG: S\nMM-1-1: \nMM-1-2: \nDINAMICVIEWSTABLESVINC: \nMM-1-5: \nCONTATOS: \nVALORAJUSTESERIALIZADO: idServicoContratoidAcordoNivelServicoserialversionuiddeletedquantidadeFalhasvalorAjusteidControleCITFrameworknull\nDINAMICVIEWSACAOPESQUISASELECIONADA: \nVALORAJUSTE: \nQUANTIDADEFALHAS: \nDATAFIM: \nMODOEXIBICAO: N\nVALORLIMITE: \nSESSION.NUMERO_CONTRATO_EDICAO: 1\nUNIDADEVALORLIMITE: \nGLOSA: \nDINAMICVIEWSIDVISAOPESQUISASELECIONADA: \nIDACORDONIVELSERVICO: 3\nDATAINICIO: 01/04/2013\nDESCRICAOSLA: Descrição do Acordo de Nível de Servico 3\nSESSION.DINAMICVIEWS_SAVEINFO: null\nIDPRIORIDADEAUTO1: \nIDTAREFA: \nSITUACAO: A\nRESULTADOSESPERADOSSERIALIZADO: idServicoContratoidAcordoNivelServicodeletedlimiteslimiteGlosaglosadescricaoResultadosidControleCITFrameworknull\nLIMITES: \nDINAMICVIEWSDADOSADICIONAIS: {}\n',NULL),(4,1,NULL,NULL,'A','SLA 1',NULL,'Descrição do Acordo de Nível de Servico 1','','2013-04-01',NULL,'2014-12-31','T',NULL,'','','','B','B','S','N','2013-04-08 04:00:00','Administrador','2013-04-08 04:00:00','Administrador','JSONDATA: TRUE\nDETALHEGLOSA: \nURGENCIA: B\nDESCRICAORESULTADOS: \nHH-2-5: 0\nHH-2-4: 0\nHH-2-3: 0\nHH-2-2: 0\nHH-2-1: 0\nAVALIAREM: 31/12/2014\nHH-1-4: 0\nHH-1-3: 0\nACAOFLUXO: \nHH-1-2: 0\nHH-1-1: 0\nESCOPOSLA: \nHH-1-5: 0\nDINAMICVIEWSJSON_DATA: \nIDFLUXO: \nJSONMATRIZ: \nIMPACTO: B\nDINAMICVIEWSIDVISAO: 67\nIDGRUPO1: \nIDUNIDADE_1: \nTITULOSLA: SLA 1\nDISPONIBILIDADE: \nLIMITEGLOSA: \nTEMPOAUTO: \nTIPO: T\nDETALHELIMITEGLOSA: \nREMOVED: false\nNOCACHE: Mon Apr 08 2013 09:02:27 GMT-0300 (Hora oficial do Brasil)\nDINAMICVIEWSJSON_TEMPDATA: \nKEYCONTROL: \nMM-2-4: 0\nMM-2-5: 0\nMM-2-2: 0\nMM-2-3: 0\nMM-2-1: 0\nMM-1-3: 0\nMM-1-4: 0\nPERMITEMUDARIMPURG: S\nMM-1-1: 0\nMM-1-2: 0\nDINAMICVIEWSTABLESVINC: \"TABLE_SEARCH_62\",\"TABLE_SEARCH_63\"\nMM-1-5: 0\nCONTATOS: \nVALORAJUSTESERIALIZADO: idServicoContratoidAcordoNivelServicoserialversionuiddeletedquantidadeFalhasvalorAjusteidControleCITFrameworknull\nDINAMICVIEWSACAOPESQUISASELECIONADA: \nVALORAJUSTE: \nQUANTIDADEFALHAS: \nDATAFIM: \nMODOEXIBICAO: N\nVALORLIMITE: \nSESSION.NUMERO_CONTRATO_EDICAO: null\nUNIDADEVALORLIMITE: \nGLOSA: \nDINAMICVIEWSIDVISAOPESQUISASELECIONADA: \nIDACORDONIVELSERVICO: 1\nDATAINICIO: 01/04/2013\nIDSERVICOCONTRATO: null\nDESCRICAOSLA: Descrição do Acordo de Nível de Servico 1\nSESSION.DINAMICVIEWS_SAVEINFO: null\nIDPRIORIDADEAUTO1: \nIDTAREFA: \nSITUACAO: A\nRESULTADOSESPERADOSSERIALIZADO: idServicoContratoidAcordoNivelServicodeletedlimiteslimiteGlosaglosadescricaoResultadosidControleCITFrameworknull\nLIMITES: \nDINAMICVIEWSDADOSADICIONAIS: {}\n',NULL),(5,2,NULL,NULL,'A','SLA 2',NULL,'Descrição do Acordo de Nível de Servico 1','','2013-04-01',NULL,'2014-12-31','T',NULL,'','','','B','B','S','N','2013-04-08 04:00:00','Administrador','2013-04-08 04:00:00','Administrador','JSONDATA: TRUE\nDETALHEGLOSA: \nURGENCIA: B\nDESCRICAORESULTADOS: \nHH-2-5: 0\nHH-2-4: 0\nHH-2-3: 0\nHH-2-2: 0\nHH-2-1: 0\nAVALIAREM: 31/12/2014\nHH-1-4: 0\nHH-1-3: 0\nACAOFLUXO: \nHH-1-2: 0\nHH-1-1: 0\nESCOPOSLA: \nHH-1-5: 0\nDINAMICVIEWSJSON_DATA: \nIDFLUXO: \nJSONMATRIZ: \nIMPACTO: B\nDINAMICVIEWSIDVISAO: 67\nIDGRUPO1: \nIDUNIDADE_1: \nTITULOSLA: SLA 2\nDISPONIBILIDADE: \nLIMITEGLOSA: \nTEMPOAUTO: \nTIPO: T\nDETALHELIMITEGLOSA: \nREMOVED: false\nNOCACHE: Mon Apr 08 2013 09:02:37 GMT-0300 (Hora oficial do Brasil)\nDINAMICVIEWSJSON_TEMPDATA: \nKEYCONTROL: \nMM-2-4: 0\nMM-2-5: 0\nMM-2-2: 0\nMM-2-3: 0\nMM-2-1: 0\nMM-1-3: 0\nMM-1-4: 0\nPERMITEMUDARIMPURG: S\nMM-1-1: 0\nMM-1-2: 0\nDINAMICVIEWSTABLESVINC: \"TABLE_SEARCH_62\",\"TABLE_SEARCH_63\"\nMM-1-5: 0\nCONTATOS: \nVALORAJUSTESERIALIZADO: idServicoContratoidAcordoNivelServicoserialversionuiddeletedquantidadeFalhasvalorAjusteidControleCITFrameworknull\nDINAMICVIEWSACAOPESQUISASELECIONADA: \nVALORAJUSTE: \nQUANTIDADEFALHAS: \nDATAFIM: \nMODOEXIBICAO: N\nVALORLIMITE: \nSESSION.NUMERO_CONTRATO_EDICAO: null\nUNIDADEVALORLIMITE: \nGLOSA: \nDINAMICVIEWSIDVISAOPESQUISASELECIONADA: \nIDACORDONIVELSERVICO: 2\nDATAINICIO: 01/04/2013\nIDSERVICOCONTRATO: null\nDESCRICAOSLA: Descrição do Acordo de Nível de Servico 1\nSESSION.DINAMICVIEWS_SAVEINFO: null\nIDPRIORIDADEAUTO1: \nIDTAREFA: \nSITUACAO: A\nRESULTADOSESPERADOSSERIALIZADO: idServicoContratoidAcordoNivelServicodeletedlimiteslimiteGlosaglosadescricaoResultadosidControleCITFrameworknull\nLIMITES: \nDINAMICVIEWSDADOSADICIONAIS: {}\n',NULL),(6,3,NULL,NULL,'A','SLA 3',NULL,'Descrição do Acordo de Nível de Servico 3','','2013-04-01',NULL,'2014-12-31','T',NULL,'','','','B','B','S','N','2013-04-08 04:00:00','Administrador','2013-04-08 04:00:00','Administrador','JSONDATA: TRUE\nDETALHEGLOSA: \nURGENCIA: B\nDESCRICAORESULTADOS: \nHH-2-5: 0\nHH-2-4: 0\nHH-2-3: 0\nHH-2-2: 0\nHH-2-1: 0\nAVALIAREM: 31/12/2014\nHH-1-4: 0\nHH-1-3: 0\nACAOFLUXO: \nHH-1-2: 0\nHH-1-1: 0\nESCOPOSLA: \nHH-1-5: 0\nDINAMICVIEWSJSON_DATA: \nIDFLUXO: \nJSONMATRIZ: \nIMPACTO: B\nDINAMICVIEWSIDVISAO: 67\nIDGRUPO1: \nIDUNIDADE_1: \nTITULOSLA: SLA 3\nDISPONIBILIDADE: \nLIMITEGLOSA: \nTEMPOAUTO: \nTIPO: T\nDETALHELIMITEGLOSA: \nREMOVED: false\nNOCACHE: Mon Apr 08 2013 09:02:48 GMT-0300 (Hora oficial do Brasil)\nDINAMICVIEWSJSON_TEMPDATA: \nKEYCONTROL: \nMM-2-4: 0\nMM-2-5: 0\nMM-2-2: 0\nMM-2-3: 0\nMM-2-1: 0\nMM-1-3: 0\nMM-1-4: 0\nPERMITEMUDARIMPURG: S\nMM-1-1: 0\nMM-1-2: 0\nDINAMICVIEWSTABLESVINC: \"TABLE_SEARCH_62\",\"TABLE_SEARCH_63\"\nMM-1-5: 0\nCONTATOS: \nVALORAJUSTESERIALIZADO: idServicoContratoidAcordoNivelServicoserialversionuiddeletedquantidadeFalhasvalorAjusteidControleCITFrameworknull\nDINAMICVIEWSACAOPESQUISASELECIONADA: \nVALORAJUSTE: \nQUANTIDADEFALHAS: \nDATAFIM: \nMODOEXIBICAO: N\nVALORLIMITE: \nSESSION.NUMERO_CONTRATO_EDICAO: null\nUNIDADEVALORLIMITE: \nGLOSA: \nDINAMICVIEWSIDVISAOPESQUISASELECIONADA: \nIDACORDONIVELSERVICO: 3\nDATAINICIO: 01/04/2013\nIDSERVICOCONTRATO: null\nDESCRICAOSLA: Descrição do Acordo de Nível de Servico 3\nSESSION.DINAMICVIEWS_SAVEINFO: null\nIDPRIORIDADEAUTO1: \nIDTAREFA: \nSITUACAO: A\nRESULTADOSESPERADOSSERIALIZADO: idServicoContratoidAcordoNivelServicodeletedlimiteslimiteGlosaglosadescricaoResultadosidControleCITFrameworknull\nLIMITES: \nDINAMICVIEWSDADOSADICIONAIS: {}\n',NULL);
/*!40000 ALTER TABLE `acordonivelservico_hist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acordonivelservicocontrato`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acordonivelservicocontrato` (
  `idacordonivelservicocontrato` int(11) NOT NULL,
  `idcontrato` int(11) NOT NULL,
  `descricaoacordo` varchar(200) NOT NULL,
  `detalhamentoacordo` text,
  `valorlimite` decimal(15,3) NOT NULL,
  `unidadevalorlimite` varchar(200) DEFAULT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  `descricaoglosa` text,
  `deleted` char(1) DEFAULT NULL,
  `idFormula` int(11) DEFAULT NULL,
  PRIMARY KEY (`idacordonivelservicocontrato`),
  KEY `fk_reference_509` (`idcontrato`),
  KEY `fk_reference_573` (`idFormula`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acordonivelservicocontrato`
--

LOCK TABLES `acordonivelservicocontrato` WRITE;
/*!40000 ALTER TABLE `acordonivelservicocontrato` DISABLE KEYS */;
/*!40000 ALTER TABLE `acordonivelservicocontrato` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acordoservicocontrato`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acordoservicocontrato` (
  `idacordoservicocontrato` bigint(20) NOT NULL,
  `idacordonivelservico` bigint(20) NOT NULL,
  `idservicocontrato` bigint(20) NOT NULL,
  `datacriacao` date NOT NULL,
  `datainicio` date DEFAULT NULL,
  `datafim` date DEFAULT NULL,
  `dataultatualiz` date DEFAULT NULL,
  `deleted` char(1) DEFAULT NULL,
  `idrecurso` int(11) DEFAULT NULL,
  PRIMARY KEY (`idacordoservicocontrato`),
  KEY `fk_reference_627` (`idacordonivelservico`),
  KEY `fk_reference_628` (`idservicocontrato`),
  KEY `fk_ref_asc_rec` (`idrecurso`),
  CONSTRAINT `fk_reference_627` FOREIGN KEY (`idacordonivelservico`) REFERENCES `acordonivelservico` (`idacordonivelservico`),
  CONSTRAINT `fk_reference_628` FOREIGN KEY (`idservicocontrato`) REFERENCES `servicocontrato` (`idservicocontrato`),
  CONSTRAINT `fk_ref_asc_rec` FOREIGN KEY (`idrecurso`) REFERENCES `recurso` (`idrecurso`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acordoservicocontrato`
--

LOCK TABLES `acordoservicocontrato` WRITE;
/*!40000 ALTER TABLE `acordoservicocontrato` DISABLE KEYS */;
INSERT INTO `acordoservicocontrato` VALUES (1,1,1,'2013-04-05','2013-04-01',NULL,'2013-04-05',NULL,NULL),(2,1,2,'2013-04-05','2013-04-01',NULL,'2013-04-05',NULL,NULL),(3,2,3,'2013-04-05','2013-04-01',NULL,'2013-04-05',NULL,NULL),(4,2,4,'2013-04-05','2013-04-01',NULL,'2013-04-05',NULL,NULL),(5,3,5,'2013-04-05','2013-04-01',NULL,'2013-04-05',NULL,NULL);
/*!40000 ALTER TABLE `acordoservicocontrato` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alcada`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alcada` (
  `idalcada` int(11) NOT NULL,
  `nomealcada` varchar(70) NOT NULL,
  `situacao` char(1) NOT NULL,
  `tipoalcada` varchar(40) DEFAULT NULL COMMENT 'C - Autorização de compras',
  PRIMARY KEY (`idalcada`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alcada`
--

LOCK TABLES `alcada` WRITE;
/*!40000 ALTER TABLE `alcada` DISABLE KEYS */;
/*!40000 ALTER TABLE `alcada` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alcadacentroresultado`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alcadacentroresultado` (
  `idalcadacentroresultado` int(11) NOT NULL,
  `idcentroresultado` int(11) NOT NULL,
  `idempregado` int(11) NOT NULL,
  `idalcada` int(11) NOT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  PRIMARY KEY (`idalcadacentroresultado`),
  KEY `fk_reference_662` (`idcentroresultado`),
  KEY `fk_reference_663` (`idempregado`),
  KEY `fk_reference_673` (`idalcada`),
  CONSTRAINT `fk_reference_662` FOREIGN KEY (`idcentroresultado`) REFERENCES `centroresultado` (`idcentroresultado`),
  CONSTRAINT `fk_reference_663` FOREIGN KEY (`idempregado`) REFERENCES `empregados` (`idempregado`),
  CONSTRAINT `fk_reference_673` FOREIGN KEY (`idalcada`) REFERENCES `alcada` (`idalcada`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alcadacentroresultado`
--

LOCK TABLES `alcadacentroresultado` WRITE;
/*!40000 ALTER TABLE `alcadacentroresultado` DISABLE KEYS */;
/*!40000 ALTER TABLE `alcadacentroresultado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `anexo`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `anexo` (
  `idanexo` int(11) NOT NULL,
  `idexecucaoatividade` int(11) DEFAULT NULL,
  `nome` varchar(256) NOT NULL,
  `descricao` text,
  `extensao` varchar(256) NOT NULL,
  `link` varchar(256) NOT NULL,
  `path` varchar(256) NOT NULL,
  PRIMARY KEY (`idanexo`),
  KEY `INDEX_ANEXOEXECUCAOATIVIDADE` (`idexecucaoatividade`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='anexo';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `anexo`
--

LOCK TABLES `anexo` WRITE;
/*!40000 ALTER TABLE `anexo` DISABLE KEYS */;
/*!40000 ALTER TABLE `anexo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `anexobaseconhecimento`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `anexobaseconhecimento` (
  `idanexobaseconhecimento` int(11) NOT NULL,
  `idbaseconhecimento` int(11) DEFAULT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  `nome` varchar(256) NOT NULL,
  `link` varchar(256) NOT NULL,
  `extensao` varchar(10) NOT NULL,
  `descricao` varchar(256) NOT NULL,
  PRIMARY KEY (`idanexobaseconhecimento`),
  KEY `INDEX_ANEXOBASECONHECIMENTO` (`idbaseconhecimento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='anexobaseconhecimento';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `anexobaseconhecimento`
--

LOCK TABLES `anexobaseconhecimento` WRITE;
/*!40000 ALTER TABLE `anexobaseconhecimento` DISABLE KEYS */;
/*!40000 ALTER TABLE `anexobaseconhecimento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `anexoincidente`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `anexoincidente` (
  `idanexoincidente` int(11) NOT NULL,
  `idincidente` int(11) NOT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  `nome` varchar(256) NOT NULL,
  `link` varchar(256) NOT NULL,
  `extensao` varchar(10) DEFAULT NULL,
  `descricao` varchar(256) NOT NULL,
  PRIMARY KEY (`idanexoincidente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Armazena as referências para arquivos anexados a incidentes.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `anexoincidente`
--

LOCK TABLES `anexoincidente` WRITE;
/*!40000 ALTER TABLE `anexoincidente` DISABLE KEYS */;
/*!40000 ALTER TABLE `anexoincidente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `anexomudanca`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `anexomudanca` (
  `idanexomudanca` int(11) NOT NULL,
  `idmudanca` int(11) DEFAULT NULL,
  `datainicio` date DEFAULT NULL,
  `datafim` date DEFAULT NULL,
  `nome` varchar(256) DEFAULT NULL,
  `link` varchar(256) DEFAULT NULL,
  `extensao` varchar(10) DEFAULT NULL,
  `descricao` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`idanexomudanca`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `anexomudanca`
--

LOCK TABLES `anexomudanca` WRITE;
/*!40000 ALTER TABLE `anexomudanca` DISABLE KEYS */;
/*!40000 ALTER TABLE `anexomudanca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `aprovacaomudanca`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `aprovacaomudanca` (
  `idaprovacaomudanca` int(11) NOT NULL,
  `idrequisicaomudanca` int(11) DEFAULT NULL,
  `idempregado` int(11) DEFAULT NULL,
  `nomeempregado` varchar(45) DEFAULT NULL,
  `voto` char(1) DEFAULT NULL,
  `comentario` varchar(200) DEFAULT NULL,
  `datahorainicio` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`idaprovacaomudanca`),
  KEY `fk_requisicaomudanca_idx` (`idrequisicaomudanca`),
  KEY `fk_empregado_idx` (`idempregado`),
  CONSTRAINT `fk_empregado` FOREIGN KEY (`idempregado`) REFERENCES `empregados` (`idempregado`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_requisicaomudanca` FOREIGN KEY (`idrequisicaomudanca`) REFERENCES `requisicaomudanca` (`idrequisicaomudanca`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aprovacaomudanca`
--

LOCK TABLES `aprovacaomudanca` WRITE;
/*!40000 ALTER TABLE `aprovacaomudanca` DISABLE KEYS */;
/*!40000 ALTER TABLE `aprovacaomudanca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `aprovacaosolicitacaoservico`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `aprovacaosolicitacaoservico` (
  `idaprovacaosolicitacaoservico` int(11) NOT NULL,
  `idsolicitacaoservico` bigint(20) NOT NULL,
  `idtarefa` bigint(20) DEFAULT NULL,
  `idresponsavel` int(11) NOT NULL,
  `datahora` timestamp NULL DEFAULT NULL,
  `idjustificativa` int(11) DEFAULT NULL,
  `complementojustificativa` text,
  `observacoes` text,
  `aprovacao` char(1) NOT NULL,
  PRIMARY KEY (`idaprovacaosolicitacaoservico`),
  KEY `fk_aprovacao_tarefa_idx` (`idtarefa`),
  KEY `fk_aprovacao_solicitacao_idx` (`idsolicitacaoservico`),
  KEY `fk_aprovacao_justificativa_idx` (`idjustificativa`),
  KEY `fk_aprovacao_responsavel_idx` (`idresponsavel`),
  CONSTRAINT `fk_aprovacao_justificativa` FOREIGN KEY (`idjustificativa`) REFERENCES `justificativasolicitacao` (`idjustificativa`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_aprovacao_responsavel` FOREIGN KEY (`idresponsavel`) REFERENCES `empregados` (`idempregado`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_aprovacao_solicitacao` FOREIGN KEY (`idsolicitacaoservico`) REFERENCES `solicitacaoservico` (`idsolicitacaoservico`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_aprovacao_tarefa` FOREIGN KEY (`idtarefa`) REFERENCES `bpm_itemtrabalhofluxo` (`iditemtrabalho`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aprovacaosolicitacaoservico`
--

LOCK TABLES `aprovacaosolicitacaoservico` WRITE;
/*!40000 ALTER TABLE `aprovacaosolicitacaoservico` DISABLE KEYS */;
/*!40000 ALTER TABLE `aprovacaosolicitacaoservico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `apuracaovaloresrecurso`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `apuracaovaloresrecurso` (
  `idapuracaovaloresrecurso` int(11) NOT NULL,
  `idrecurso` int(11) NOT NULL,
  `valorinicio` decimal(15,3) NOT NULL,
  `deleted` char(1) DEFAULT NULL,
  PRIMARY KEY (`idapuracaovaloresrecurso`),
  KEY `fk_reference_606` (`idrecurso`),
  CONSTRAINT `fk_reference_606` FOREIGN KEY (`idrecurso`) REFERENCES `recurso` (`idrecurso`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apuracaovaloresrecurso`
--

LOCK TABLES `apuracaovaloresrecurso` WRITE;
/*!40000 ALTER TABLE `apuracaovaloresrecurso` DISABLE KEYS */;
/*!40000 ALTER TABLE `apuracaovaloresrecurso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `atividadeperiodica`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `atividadeperiodica` (
  `idatividadeperiodica` int(11) NOT NULL,
  `idcontrato` int(11) DEFAULT NULL,
  `idprocedimentotecnico` int(11) DEFAULT NULL,
  `idgrupoatvperiodica` int(11) DEFAULT NULL,
  `tituloatividade` varchar(256) DEFAULT NULL,
  `descricao` text,
  `datainicio` date DEFAULT NULL,
  `orientacaotecnica` text,
  `datafim` date DEFAULT NULL,
  `criadopor` varchar(30) DEFAULT NULL,
  `datacriacao` date DEFAULT NULL,
  `alteradopor` varchar(30) DEFAULT NULL,
  `dataultalteracao` date DEFAULT NULL,
  `idsolicitacaoservico` int(11) DEFAULT NULL,
  `idrequisicaomudanca` int(11) DEFAULT NULL,
  `blackout` char(1) DEFAULT NULL,
  PRIMARY KEY (`idatividadeperiodica`),
  KEY `fk_reference_563` (`idsolicitacaoservico`),
  KEY `fk_ref_reqmud` (`idrequisicaomudanca`),
  CONSTRAINT `fk_ref_reqmud` FOREIGN KEY (`idrequisicaomudanca`) REFERENCES `requisicaomudanca` (`idrequisicaomudanca`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `atividadeperiodica`
--

LOCK TABLES `atividadeperiodica` WRITE;
/*!40000 ALTER TABLE `atividadeperiodica` DISABLE KEYS */;
/*!40000 ALTER TABLE `atividadeperiodica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `atividades`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `atividades` (
  `idatividade` int(11) NOT NULL,
  `idetapa` int(11) NOT NULL,
  `idtipoatividade` int(11) NOT NULL,
  `idatividadeproxima` int(11) DEFAULT NULL,
  `nomeatividade` varchar(100) NOT NULL,
  `ordem` smallint(6) NOT NULL,
  `grupoexecutor` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`idatividade`),
  KEY `INDEX_ATIVIDADESETAPA` (`idetapa`),
  KEY `INDEX_ATIVIDADESTIPOATIVIDADE` (`idtipoatividade`),
  KEY `INDEX_ATIVIDADEPROXIMA` (`idatividadeproxima`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='atividades';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `atividades`
--

LOCK TABLES `atividades` WRITE;
/*!40000 ALTER TABLE `atividades` DISABLE KEYS */;
/*!40000 ALTER TABLE `atividades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `atividadesfluxos`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `atividadesfluxos` (
  `idatividade` int(11) NOT NULL,
  `idfluxo` int(11) NOT NULL,
  PRIMARY KEY (`idatividade`,`idfluxo`),
  KEY `INDEX_ATIVIDADESFLUXO` (`idfluxo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='atividadesfluxos';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `atividadesfluxos`
--

LOCK TABLES `atividadesfluxos` WRITE;
/*!40000 ALTER TABLE `atividadesfluxos` DISABLE KEYS */;
/*!40000 ALTER TABLE `atividadesfluxos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `atividadesos`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `atividadesos` (
  `idatividadesos` bigint(20) NOT NULL,
  `idos` int(11) NOT NULL,
  `sequencia` smallint(6) DEFAULT NULL,
  `idatividadeservicocontrato` bigint(20) DEFAULT NULL,
  `descricaoatividade` text NOT NULL,
  `obsatividade` text,
  `custoatividade` decimal(18,3) DEFAULT NULL,
  `glosaatividade` decimal(18,3) DEFAULT NULL,
  `complexidade` char(5) DEFAULT NULL,
  `deleted` char(1) DEFAULT NULL,
  `qtdeexecutada` decimal(15,3) DEFAULT NULL,
  `formula` text,
  `contabilizar` char(1) DEFAULT NULL,
  `idservicocontratocontabil` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`idatividadesos`),
  KEY `fk_reference_128` (`idos`),
  KEY `fk_reference_129` (`idatividadeservicocontrato`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `atividadesos`
--

LOCK TABLES `atividadesos` WRITE;
/*!40000 ALTER TABLE `atividadesos` DISABLE KEYS */;
/*!40000 ALTER TABLE `atividadesos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `atividadesservicocontrato`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `atividadesservicocontrato` (
  `idatividadeservicocontrato` bigint(20) NOT NULL,
  `idservicocontrato` bigint(20) NOT NULL,
  `descricaoatividade` text NOT NULL,
  `obsatividade` text,
  `custoatividade` decimal(18,3) DEFAULT NULL,
  `complexidade` char(5) DEFAULT NULL,
  `deleted` char(1) DEFAULT NULL,
  `hora` decimal(10,2) DEFAULT NULL,
  `quantidade` int(11) DEFAULT NULL,
  `periodo` char(5) DEFAULT NULL,
  `tipocusto` char(1) DEFAULT NULL,
  `formula` text,
  `contabilizar` char(1) DEFAULT NULL,
  `idservicocontratocontabil` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`idatividadeservicocontrato`),
  KEY `fk_reference_127` (`idservicocontrato`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `atividadesservicocontrato`
--

LOCK TABLES `atividadesservicocontrato` WRITE;
/*!40000 ALTER TABLE `atividadesservicocontrato` DISABLE KEYS */;
/*!40000 ALTER TABLE `atividadesservicocontrato` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auditoria`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auditoria` (
  `idauditoria` int(11) NOT NULL,
  `iditemconfiguracao` int(11) DEFAULT NULL,
  `idusuario` int(11) DEFAULT NULL,
  `data` date NOT NULL,
  `hora` char(4) DEFAULT NULL,
  `ocorr` text,
  PRIMARY KEY (`idauditoria`),
  KEY `INDEX_AUDITORIAITEMCONFIGURACAO` (`iditemconfiguracao`),
  KEY `INDEX_AUDITORIAUSUARIO` (`idusuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='auditoria';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auditoria`
--

LOCK TABLES `auditoria` WRITE;
/*!40000 ALTER TABLE `auditoria` DISABLE KEYS */;
/*!40000 ALTER TABLE `auditoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auditoriaitemconfiguracao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auditoriaitemconfiguracao` (
  `idauditoria` int(11) NOT NULL,
  `iditemconfiguracao` int(11) DEFAULT NULL,
  `idempregado` int(11) DEFAULT NULL,
  `data` date NOT NULL,
  `hora` char(4) DEFAULT NULL,
  `ocorr` text,
  PRIMARY KEY (`idauditoria`),
  KEY `INDEX_AUDTITEMCONFIGURACAO` (`iditemconfiguracao`),
  KEY `INDEX_AUDITEMPREGADO` (`idempregado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='auditoriaitemconfiguracao';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auditoriaitemconfiguracao`
--

LOCK TABLES `auditoriaitemconfiguracao` WRITE;
/*!40000 ALTER TABLE `auditoriaitemconfiguracao` DISABLE KEYS */;
/*!40000 ALTER TABLE `auditoriaitemconfiguracao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `avaliacaocoletapreco`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `avaliacaocoletapreco` (
  `idcriterio` int(11) NOT NULL,
  `idcoletapreco` int(11) NOT NULL,
  `avaliacao` int(11) NOT NULL,
  KEY `fk_reference_36` (`idcriterio`),
  KEY `fk_reference_689` (`idcoletapreco`),
  CONSTRAINT `fk_reference_36` FOREIGN KEY (`idcriterio`) REFERENCES `criterioavaliacao` (`idcriterio`),
  CONSTRAINT `fk_reference_689` FOREIGN KEY (`idcoletapreco`) REFERENCES `coletapreco` (`idcoletapreco`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `avaliacaocoletapreco`
--

LOCK TABLES `avaliacaocoletapreco` WRITE;
/*!40000 ALTER TABLE `avaliacaocoletapreco` DISABLE KEYS */;
/*!40000 ALTER TABLE `avaliacaocoletapreco` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `avaliacaocotacao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `avaliacaocotacao` (
  `idcriterio` int(11) NOT NULL,
  `idfornecedor` int(11) NOT NULL,
  `iditemcotacao` int(11) NOT NULL,
  `avaliacao` int(11) NOT NULL,
  PRIMARY KEY (`idcriterio`,`idfornecedor`,`iditemcotacao`),
  CONSTRAINT `avaliacaocotacao_ibfk_1` FOREIGN KEY (`idcriterio`) REFERENCES `criterioavaliacao` (`idcriterio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `avaliacaocotacao`
--

LOCK TABLES `avaliacaocotacao` WRITE;
/*!40000 ALTER TABLE `avaliacaocotacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `avaliacaocotacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `avaliacaofornecedor`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `avaliacaofornecedor` (
  `idavaliacaofornecedor` int(11) NOT NULL,
  `idfornecedor` bigint(20) DEFAULT NULL,
  `idresponsavel` int(11) DEFAULT NULL,
  `dataavaliacao` date DEFAULT NULL,
  `decisaoqualificacao` char(1) DEFAULT NULL,
  `observacoes` text,
  `contato` varchar(245) DEFAULT NULL,
  PRIMARY KEY (`idavaliacaofornecedor`),
  KEY `fk_reference_678` (`idfornecedor`),
  KEY `fk_reference_683` (`idresponsavel`),
  CONSTRAINT `fk_reference_678` FOREIGN KEY (`idfornecedor`) REFERENCES `fornecedor` (`idfornecedor`),
  CONSTRAINT `fk_reference_683` FOREIGN KEY (`idresponsavel`) REFERENCES `empregados` (`idempregado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `avaliacaofornecedor`
--

LOCK TABLES `avaliacaofornecedor` WRITE;
/*!40000 ALTER TABLE `avaliacaofornecedor` DISABLE KEYS */;
/*!40000 ALTER TABLE `avaliacaofornecedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `avaliacaopedido`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `avaliacaopedido` (
  `idavaliacao` int(11) NOT NULL,
  `idcriterio` int(11) NOT NULL,
  `idpedido` int(11) DEFAULT NULL,
  `idcoletapreco` int(11) DEFAULT NULL,
  `iditemrequisicaoproduto` int(11) DEFAULT NULL,
  `tipoavaliacao` char(1) NOT NULL COMMENT 'P - Produto\n            C - Compra',
  `avaliacao` decimal(8,2) NOT NULL,
  `observacoes` longtext,
  PRIMARY KEY (`idavaliacao`),
  KEY `fk_reference_49` (`idcriterio`),
  KEY `fk_reference_701` (`idpedido`),
  KEY `fk_reference_702` (`idcoletapreco`,`iditemrequisicaoproduto`),
  CONSTRAINT `avaliacaopedido_ibfk_3` FOREIGN KEY (`idcoletapreco`, `iditemrequisicaoproduto`) REFERENCES `cotacaoitemrequisicao` (`idcoletapreco`, `iditemrequisicaoproduto`),
  CONSTRAINT `avaliacaopedido_ibfk_1` FOREIGN KEY (`idcriterio`) REFERENCES `criterioavaliacao` (`idcriterio`),
  CONSTRAINT `avaliacaopedido_ibfk_2` FOREIGN KEY (`idpedido`) REFERENCES `pedidocompra` (`idpedido`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `avaliacaopedido`
--

LOCK TABLES `avaliacaopedido` WRITE;
/*!40000 ALTER TABLE `avaliacaopedido` DISABLE KEYS */;
/*!40000 ALTER TABLE `avaliacaopedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `avaliacaoreferenciafornecedor`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `avaliacaoreferenciafornecedor` (
  `idavaliacaofornecedor` int(11) NOT NULL DEFAULT '0',
  `idempregado` int(11) NOT NULL DEFAULT '0',
  `decisao` char(1) DEFAULT NULL,
  `observacoes` text,
  PRIMARY KEY (`idavaliacaofornecedor`,`idempregado`),
  KEY `fk_reference_6896` (`idempregado`),
  CONSTRAINT `avaliacaoreferenciafornecedor_ibfk_1` FOREIGN KEY (`idempregado`) REFERENCES `empregados` (`idempregado`),
  CONSTRAINT `fk_reference_681` FOREIGN KEY (`idavaliacaofornecedor`) REFERENCES `avaliacaofornecedor` (`idavaliacaofornecedor`),
  CONSTRAINT `fk_reference_6896` FOREIGN KEY (`idempregado`) REFERENCES `empregados` (`idempregado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `avaliacaoreferenciafornecedor`
--

LOCK TABLES `avaliacaoreferenciafornecedor` WRITE;
/*!40000 ALTER TABLE `avaliacaoreferenciafornecedor` DISABLE KEYS */;
/*!40000 ALTER TABLE `avaliacaoreferenciafornecedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `baseconhecimento`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `baseconhecimento` (
  `idbaseconhecimento` int(11) NOT NULL,
  `idpasta` int(11) DEFAULT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  `titulo` varchar(256) NOT NULL,
  `conteudo` text,
  `status` char(1) NOT NULL,
  `idbaseconhecimentopai` int(11) DEFAULT NULL,
  `dataexpiracao` date DEFAULT NULL,
  `versao` varchar(45) DEFAULT NULL,
  `idnotificacao` int(11) DEFAULT NULL,
  `justificativaobservacao` varchar(500) DEFAULT NULL,
  `datapublicacao` date DEFAULT NULL,
  `fontereferencia` varchar(255) DEFAULT NULL,
  `faq` varchar(45) DEFAULT NULL,
  `arquivado` varchar(45) DEFAULT NULL,
  `idusuarioautor` int(11) DEFAULT NULL,
  `idusuarioaprovador` int(11) DEFAULT NULL,
  `idhistoricobaseconhecimento` int(11) DEFAULT NULL,
  `origem` char(1) NOT NULL,
  `privacidade` varchar(45) DEFAULT NULL,
  `situacao` varchar(45) DEFAULT NULL,
  `gerenciamentoDisponibilidade` char(1) DEFAULT NULL,
  `direitoAutoral` char(1) DEFAULT NULL,
  `legislacao` char(1) DEFAULT NULL,
  `conteudosemformatacao` text,
  PRIMARY KEY (`idbaseconhecimento`),
  KEY `INDEX_BASEPASTA` (`idpasta`),
  KEY `INDEX_BASECONHECIMENTOPAI` (`idbaseconhecimentopai`),
  KEY `idnotificacao` (`idnotificacao`),
  KEY `idhistoricobaseconhecimento` (`idhistoricobaseconhecimento`),
  CONSTRAINT `idhistoricobaseconhecimento` FOREIGN KEY (`idhistoricobaseconhecimento`) REFERENCES `historicobaseconhecimento` (`idhistoricobaseconhecimento`),
  CONSTRAINT `idnotificacao` FOREIGN KEY (`idnotificacao`) REFERENCES `notificacao` (`idnotificacao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='baseconhecimento';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `baseconhecimento`
--

LOCK TABLES `baseconhecimento` WRITE;
/*!40000 ALTER TABLE `baseconhecimento` DISABLE KEYS */;
INSERT INTO `baseconhecimento` VALUES (1,1,'2013-04-08',NULL,'Conhecimento 1','Descri&ccedil;&atilde;o Conhecimento 1<br />','S',NULL,'2014-12-31','1.0',NULL,'Justificativa Conhecimento 1','2013-04-08','Conhecimento 1',NULL,'N',1,1,1,'1','C','DS',NULL,NULL,NULL,'Descrição Conhecimento 1'),(2,2,'2013-04-08',NULL,'Conhecimento 2','Descri&ccedil;&atilde;o Conhecimento 2<br />','S',NULL,'2014-12-31','1.0',NULL,'Justificativa Conhecimento 2','2013-04-08','',NULL,'N',1,1,2,'1','C','DS',NULL,NULL,NULL,'Descrição Conhecimento 2'),(3,1,'2013-04-08',NULL,'FAQ 1','Resposta FAQ 1<br />','S',NULL,'2014-12-31','1.0',NULL,'Justificativa FAQ 1','2013-04-08','FAQ 1','S','N',1,1,3,'1','C','DS',NULL,NULL,NULL,'Resposta FAQ 1'),(4,2,'2013-04-08',NULL,'FAQ 2','Resposta FAQ 2','S',NULL,'2014-12-31','1.0',NULL,'Justificativa FAQ 2','2013-04-08','FAQ 2','S','N',1,1,4,'1','C','DS',NULL,NULL,NULL,'Resposta FAQ 2');
/*!40000 ALTER TABLE `baseconhecimento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `baseconhecimentorelacionado`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `baseconhecimentorelacionado` (
  `idbaseconhecimento` int(11) NOT NULL,
  `idbaseconhecimentorelacionado` int(11) NOT NULL,
  PRIMARY KEY (`idbaseconhecimento`,`idbaseconhecimentorelacionado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `baseconhecimentorelacionado`
--

LOCK TABLES `baseconhecimentorelacionado` WRITE;
/*!40000 ALTER TABLE `baseconhecimentorelacionado` DISABLE KEYS */;
/*!40000 ALTER TABLE `baseconhecimentorelacionado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `baseitemconfiguracao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `baseitemconfiguracao` (
  `idbaseitemconfiguracao` int(11) NOT NULL,
  `idtipoitemconfiguracao` int(11) DEFAULT NULL,
  `nomebaseitemconfiguracao` varchar(256) NOT NULL,
  `executavel` varchar(256) NOT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  `tipoexecucao` char(1) DEFAULT NULL,
  `comando` varchar(256) DEFAULT NULL COMMENT 'Comando de instalacao selenciosa.',
  PRIMARY KEY (`idbaseitemconfiguracao`),
  KEY `INDEX_BASETIPOITEMCONFIGURACAO` (`idtipoitemconfiguracao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='baseitemconfiguracao';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `baseitemconfiguracao`
--

LOCK TABLES `baseitemconfiguracao` WRITE;
/*!40000 ALTER TABLE `baseitemconfiguracao` DISABLE KEYS */;
/*!40000 ALTER TABLE `baseitemconfiguracao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bibliotecasexternas`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bibliotecasexternas` (
  `idbibliotecasexterna` bigint(20) NOT NULL,
  `nome` varchar(500) NOT NULL,
  `caminho` varchar(500) NOT NULL,
  PRIMARY KEY (`idbibliotecasexterna`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bibliotecasexternas`
--

LOCK TABLES `bibliotecasexternas` WRITE;
/*!40000 ALTER TABLE `bibliotecasexternas` DISABLE KEYS */;
/*!40000 ALTER TABLE `bibliotecasexternas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `botaoacaovisao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `botaoacaovisao` (
  `idbotaoacaovisao` bigint(20) NOT NULL,
  `idvisao` bigint(20) NOT NULL,
  `texto` varchar(120) NOT NULL,
  `acao` char(1) NOT NULL,
  `script` text,
  `hint` varchar(120) DEFAULT NULL,
  `icone` varchar(120) DEFAULT NULL,
  `ordem` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`idbotaoacaovisao`),
  KEY `fk_reference_97` (`idvisao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `botaoacaovisao`
--

LOCK TABLES `botaoacaovisao` WRITE;
/*!40000 ALTER TABLE `botaoacaovisao` DISABLE KEYS */;
/*!40000 ALTER TABLE `botaoacaovisao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bpm_atribuicaofluxo`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bpm_atribuicaofluxo` (
  `idatribuicao` bigint(20) NOT NULL,
  `iditemtrabalho` bigint(20) NOT NULL,
  `tipo` varchar(20) DEFAULT NULL,
  `datahora` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `idusuario` int(11) DEFAULT NULL,
  `idgrupo` int(11) DEFAULT NULL,
  PRIMARY KEY (`idatribuicao`),
  KEY `fk_reference_119` (`iditemtrabalho`),
  KEY `fk_reference_135` (`idusuario`),
  KEY `fk_reference_137` (`idgrupo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bpm_atribuicaofluxo`
--

LOCK TABLES `bpm_atribuicaofluxo` WRITE;
/*!40000 ALTER TABLE `bpm_atribuicaofluxo` DISABLE KEYS */;
INSERT INTO `bpm_atribuicaofluxo` VALUES (1,1,'Automatica','2013-04-05 13:40:01',NULL,2),(2,2,'Automatica','2013-04-05 13:43:39',NULL,2),(3,4,'Automatica','2013-04-05 14:13:18',NULL,3),(4,4,'Acompanhamento','2013-04-05 14:13:18',NULL,2);
/*!40000 ALTER TABLE `bpm_atribuicaofluxo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bpm_elementofluxo`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bpm_elementofluxo` (
  `idelemento` bigint(20) NOT NULL,
  `idfluxo` bigint(20) NOT NULL,
  `tipoelemento` varchar(20) NOT NULL,
  `subtipo` varchar(20) DEFAULT NULL,
  `nome` varchar(50) DEFAULT NULL,
  `documentacao` text,
  `tipointeracao` char(1) DEFAULT NULL,
  `url` varchar(150) DEFAULT NULL,
  `visao` varchar(150) DEFAULT NULL,
  `grupos` text,
  `usuarios` text,
  `acaoentrada` text,
  `acaosaida` text,
  `script` text,
  `textoemail` text,
  `nomefluxoencadeado` varchar(100) DEFAULT NULL,
  `posx` double DEFAULT NULL,
  `posy` double DEFAULT NULL,
  `altura` double DEFAULT NULL,
  `largura` double DEFAULT NULL,
  `modeloemail` varchar(20) DEFAULT NULL,
  `template` varchar(40) DEFAULT NULL,
  `intervalo` int(11) DEFAULT NULL,
  `condicaodisparo` text,
  `multiplasinstancias` char(1) DEFAULT NULL,
  `destinatariosemail` text,
  `contabilizasla` char(1) DEFAULT NULL,
  `percexecucao` double DEFAULT NULL,
  PRIMARY KEY (`idelemento`),
  KEY `fk_reference_126` (`idfluxo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bpm_elementofluxo`
--

LOCK TABLES `bpm_elementofluxo` WRITE;
/*!40000 ALTER TABLE `bpm_elementofluxo` DISABLE KEYS */;
INSERT INTO `bpm_elementofluxo` VALUES (1,1,'Inicio',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'S',NULL),(2,1,'Tarefa',NULL,'Escalar atendimento','Escalar atendimento','U','pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?escalar=S',NULL,'#{solicitacaoServico.grupoNivel1}',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'S',NULL),(3,1,'Tarefa',NULL,'Atender solicitacao','Atender solicitacao','U','pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?escalar=S&alterarSituacao=S',NULL,'#{solicitacaoServico.grupoAtual}',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'S',NULL),(4,1,'Script',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'#{execucaoFluxo}.encerra();',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'S',NULL),(5,1,'Porta','Decisao',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'S',NULL),(6,1,'Porta','Decisao',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'S',NULL),(7,1,'Porta','Decisao',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'S',NULL),(8,1,'Porta','Decisao',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'S',NULL),(9,1,'Finalizacao',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'S',NULL),(10,2,'Inicio',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'S',NULL),(11,2,'Tarefa',NULL,'Escalar atendimento','Direcionar atendimento','U','pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?escalar=S',NULL,'#{solicitacaoServico.grupoNivel1}',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'S',NULL),(12,2,'Tarefa',NULL,'Controle de Qualidade','Controle de qualidade','U','pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?escalar=S&alterarSituacao=S',NULL,'#{solicitacaoServico.grupoNivel1}',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'S',NULL),(13,2,'Tarefa',NULL,'Atender solicitacao','Atender solicitacao','U','pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?escalar=S&alterarSituacao=S',NULL,'#{solicitacaoServico.grupoAtual}',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'S',NULL),(14,2,'Script',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'#{execucaoFluxo}.encerra();',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'S',NULL),(15,2,'Porta','Decisao',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'S',NULL),(16,2,'Porta','Decisao',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'S',NULL),(17,2,'Porta','Decisao',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'S',NULL),(18,2,'Porta','Decisao',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'S',NULL),(19,2,'Porta','Decisao',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'S',NULL),(20,2,'Finalizacao',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'S',NULL),(21,3,'Inicio',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'S',NULL),(22,3,'Tarefa',NULL,'Escalar atendimento','Direcionar atendimento','U','pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?escalar=S',NULL,'#{solicitacaoServico.grupoNivel1}',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'S',NULL),(23,3,'Tarefa',NULL,'Atender solicitacao','Atender solicitacao','U','pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?escalar=S&alterarSituacao=S',NULL,'#{solicitacaoServico.grupoAtual}',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'S',NULL),(24,3,'Script',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'#{execucaoFluxo}.encerra();',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'S',NULL),(25,3,'Porta','Decisao',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'S',NULL),(26,3,'Porta','Decisao',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'S',NULL),(27,3,'Porta','Decisao',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'S',NULL),(28,3,'Porta','Decisao',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'S',NULL),(29,3,'Finalizacao',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'S',NULL),(30,4,'Inicio','','','','','','','','','','','','','',26,37,32,32,'',NULL,NULL,NULL,NULL,NULL,'S',NULL),(31,4,'Tarefa','','Direcionar atendimento','Direcionar atendimento ','U','pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?escalar=S','','#{solicitacaoServico.grupoNivel1}','','','','','','',289,223,65,140,'',NULL,NULL,NULL,NULL,NULL,'S',NULL),(32,4,'Tarefa','','Atender solicitação ','Atender solicitação ','U','pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?escalar=S&alterarSituacao=S','','#{solicitacaoServico.grupoAtual}','','','','','','',289,420,65,140,'',NULL,NULL,NULL,NULL,NULL,'S',NULL),(33,4,'Tarefa','','Controle de qualidade','Controle de qualidade','U','pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?escalar=S&alterarSituacao=S','','#{solicitacaoServico.grupoNivel1}','','','','','','',653,222,65,140,'',NULL,NULL,NULL,NULL,NULL,'S',NULL),(34,4,'Script','','Encerrar solicitação','','','','','','','','','#{execucaoFluxo}.encerra();','','',493,21,65,140,'',NULL,NULL,NULL,NULL,NULL,'S',NULL),(35,4,'Porta','','','','','','','','','','','','','',203,33,42,42,'',NULL,NULL,NULL,NULL,NULL,'S',NULL),(36,4,'Porta','','','','','','','','','','','','','',204,155,42,42,'',NULL,NULL,NULL,NULL,NULL,'S',NULL),(37,4,'Porta','','','','','','','','','','','','','',576,431,42,42,'',NULL,NULL,NULL,NULL,NULL,'S',NULL),(38,4,'Porta','','','','','','','','','','','','','',541,234,42,42,'',NULL,NULL,NULL,NULL,NULL,'S',NULL),(39,4,'Porta','','','','','','','','','','','','','',338,329,42,42,'',NULL,NULL,NULL,NULL,NULL,'S',NULL),(40,4,'Finalizacao','','','','','','','','','','','','','',765,38,32,32,'',NULL,NULL,NULL,NULL,NULL,'S',NULL),(41,5,'Inicio','','','','','','','','','','','','','',26,37,32,32,'',NULL,NULL,NULL,NULL,NULL,'S',NULL),(42,5,'Tarefa','','Direcionar atendimento','Direcionar atendimento ','U','pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?escalar=S','','#{solicitacaoServico.grupoNivel1}','','','','','','',289,223,65,140,'',NULL,NULL,NULL,NULL,NULL,'S',NULL),(43,5,'Tarefa','','Atender solicitação ','Atender solicitação ','U','pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?escalar=S&alterarSituacao=S','','#{solicitacaoServico.grupoAtual}','','','','','','',289,420,65,140,'',NULL,NULL,NULL,NULL,NULL,'S',NULL),(44,5,'Tarefa','','Controle de qualidade','Controle de qualidade','U','pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?escalar=S&alterarSituacao=S','','#{solicitacaoServico.grupoNivel1}','','','','','','',652,223,65,140,'',NULL,NULL,NULL,NULL,NULL,'S',NULL),(45,5,'Script','','Encerrar solicitação','','','','','','','','','#{execucaoFluxo}.encerra();','','',493,21,65,140,'',NULL,NULL,NULL,NULL,NULL,'S',NULL),(46,5,'Porta','','','','','','','','','','','','','',203,33,42,42,'',NULL,NULL,NULL,NULL,NULL,'S',NULL),(47,5,'Porta','','','','','','','','','','','','','',204,155,42,42,'',NULL,NULL,NULL,NULL,NULL,'S',NULL),(48,5,'Porta','','','','','','','','','','','','','',576,431,42,42,'',NULL,NULL,NULL,NULL,NULL,'S',NULL),(49,5,'Porta','','','','','','','','','','','','','',541,234,42,42,'',NULL,NULL,NULL,NULL,NULL,'S',NULL),(50,5,'Porta','','','','','','','','','','','','','',338,329,42,42,'',NULL,NULL,NULL,NULL,NULL,'S',NULL),(51,5,'Finalizacao','','','','','','','','','','','','','',765,38,32,32,'',NULL,NULL,NULL,NULL,NULL,'S',NULL),(52,8,'Inicio','','','','','','','','','','','','','',26,37,32,32,'','',NULL,'','','','S',NULL),(53,8,'Tarefa','','Avaliar/Destinar o chamado','Avaliar/Destinar o chamado','U','pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?escalar=S','','LideresGoiania','','','','','','',289,223,65,140,'','',NULL,'','','','S',NULL),(54,8,'Tarefa','','Desenvolvimento','Desenvolvimento','U','pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?escalar=S&alterarSituacao=S','','#{solicitacaoServico.grupoAtual}','','','','','','',289,420,65,140,'','',NULL,'','','','S',NULL),(55,8,'Tarefa','','Testes/Controle de qualidade','Testes/Controle de qualidade','U','pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?escalar=S&alterarSituacao=S','','TestesGoiania','','','','','','',652,223,65,140,'','',NULL,'','','','S',NULL),(56,8,'Script','','Encerrar solicitação','','','','','','','','','#{execucaoFluxo}.encerra();','','',493,21,65,140,'','',NULL,'','','','S',NULL),(57,8,'Porta','','','','','','','','','','','','','',203,33,42,42,'','',NULL,'','','','S',NULL),(58,8,'Porta','','','','','','','','','','','','','',204,155,42,42,'','',NULL,'','','','S',NULL),(59,8,'Porta','','','','','','','','','','','','','',576,431,42,42,'','',NULL,'','','','S',NULL),(60,8,'Porta','','','','','','','','','','','','','',541,234,42,42,'','',NULL,'','','','S',NULL),(61,8,'Porta','','','','','','','','','','','','','',338,329,42,42,'','',NULL,'','','','S',NULL),(62,8,'Finalizacao','','','','','','','','','','','','','',765,38,32,32,'','',NULL,'','','','S',NULL),(184,9,'Inicio','','','','','','','','','','','','','',94,56,32,32,'','',NULL,'','',NULL,'S',NULL),(185,9,'Tarefa','','Avaliar','Avaliar','U','pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Execucao&fase=Avaliacao','','#{requisicaoMudanca.nomeGrupoAtual}','','','','','','',703,168,65,140,'','',NULL,'','',NULL,'S',NULL),(186,9,'Tarefa','','Aprovar','Aprovar','U','/pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Aprovacao','','#{requisicaoMudanca.nomeGrupoAtual}','','','','','','',40,168,65,140,'','',NULL,'','',NULL,'S',NULL),(187,9,'Tarefa','','Planejar','Planejar','U','pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Planejamento','','#{requisicaoMudanca.nomeGrupoAtual}','','','','','','',255,168,65,140,'','',NULL,'','',NULL,'S',NULL),(188,9,'Tarefa','','Executar','Executar','U','pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Execucao','','#{requisicaoMudanca.nomeGrupoAtual}','','','','','','',482,168,65,140,'','',NULL,'','',NULL,'S',NULL),(189,9,'Finalizacao','','','','','','','','','','','','','',918,184,32,32,'','',NULL,'','',NULL,'S',NULL),(404,42,'Inicio','','','','','','','','','','','','','',163,161,32,32,'','',NULL,'','','',NULL,NULL),(405,42,'Tarefa','','Executar','Executar','U','pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Execucao','',' #{requisicaoMudanca.nomeGrupoAtual}','','','','','','',397,216,65,140,'','',NULL,'','','',NULL,NULL),(406,42,'Tarefa','','Avaliar','Avaliar','U',' pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Execucao&fase=Avaliacao','','#{requisicaoMudanca.nomeGrupoAtual}','','','','','','',719,260,65,140,'','',NULL,'','','',NULL,NULL),(407,42,'Script','','Encerra','','','','','','','','','#{execucaoFluxo}.encerra()','','',1014,246,65,140,'','',NULL,'','','',NULL,NULL),(408,42,'Finalizacao',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1326,305,32,32,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(409,43,'Inicio','','','','','','','','','','','','','',90,139,32,32,'','',NULL,'','','',NULL,NULL),(410,43,'Tarefa','','Executar','Executar','U','pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Execucao','','#{requisicaoMudanca.nomeGrupoAtual}','','','','','','',313,162,65,140,'','',NULL,'','','',NULL,NULL),(411,43,'Tarefa','','Avaliar','Avaliar','U',' pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Execucao&fase=Avaliacao','',' #{requisicaoMudanca.nomeGrupoAtual}','','','','','','',677,231,65,140,'','',NULL,'','','',NULL,NULL),(412,43,'Script',NULL,'Encerra',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'#{execucaoFluxo}.encerra();',NULL,NULL,1050,247,65,140,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(413,43,'Finalizacao',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1300,287,32,32,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(414,44,'Inicio','','','','','','','','','','','','','',94,56,32,32,'','',NULL,'','','',NULL,NULL),(415,44,'Tarefa','','Aprovar','Aprovar','U','/pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Aprovacao','','#{requisicaoMudanca.nomeGrupoAtual}','','','','','','',67,171,65,140,'','',NULL,'','','',NULL,NULL),(416,44,'Tarefa','','Planejar','Planejar','U','pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Planejamento','','#{requisicaoMudanca.nomeGrupoAtual}','','','','','','',255,168,65,140,'','',NULL,'','','',NULL,NULL),(417,44,'Tarefa','','Executar','Executar','U','pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Execucao','','#{requisicaoMudanca.nomeGrupoAtual}','','','','','','',482,168,65,140,'','',NULL,'','','',NULL,NULL),(418,44,'Tarefa','','Avaliar','Avaliar','U','pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Execucao&fase=Avaliacao','','#{requisicaoMudanca.nomeGrupoAtual}','','','','','','',783,169,65,140,'','',NULL,'','','',NULL,NULL),(419,44,'Script',NULL,'Encerra',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'#{execucaoFluxo}.encerra();',NULL,NULL,1045,173,65,140,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(420,44,'Finalizacao',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1135,425,32,32,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `bpm_elementofluxo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bpm_fluxo`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bpm_fluxo` (
  `idfluxo` bigint(20) NOT NULL,
  `versao` varchar(10) NOT NULL,
  `idtipofluxo` int(11) DEFAULT NULL,
  `variaveis` text,
  `conteudoxml` text,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  PRIMARY KEY (`idfluxo`),
  KEY `fk_reference_115` (`idtipofluxo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bpm_fluxo`
--

LOCK TABLES `bpm_fluxo` WRITE;
/*!40000 ALTER TABLE `bpm_fluxo` DISABLE KEYS */;
INSERT INTO `bpm_fluxo` VALUES (1,'1.0',1,'solicitacaoServico;solicitacaoServico.situacao;solicitacaoServico.grupoAtual;solicitacaoServico.grupoNivel1','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<bpmn2:definitions xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://www.omg.org/bpmn20\" xmlns:bpmn2=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:dc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:di=\"http://www.omg.org/spec/DD/20100524/DI\" xmlns:drools=\"http://www.jboss.org/drools\" id=\"_TAjZkcL1EeGlM7mRf4ZKFw\" xsi:schemaLocation=\"http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd\" targetNamespace=\"http://www.omg.org/bpmn20\">\n  <bpmn2:itemDefinition id=\"_solicitacaoServico;solicitacaoServico.situacao;solicitacaoServico.grupoAtual;solicitacaoServico.grupoNivel1Item\"/>\n  <bpmn2:process id=\".\" drools:version=\"1.0\" name=\"SolicitacaoServico\" isExecutable=\"true\">\n    <bpmn2:property id=\"solicitacaoServico;solicitacaoServico.situacao;solicitacaoServico.grupoAtual;solicitacaoServico.grupoNivel1\" itemSubjectRef=\"_solicitacaoServico;solicitacaoServico.situacao;solicitacaoServico.grupoAtual;solicitacaoServico.grupoNivel1Item\"/>\n    <bpmn2:laneSet id=\"_TAl10ML1EeGlM7mRf4ZKFw\">\n      <bpmn2:lane id=\"_7219C884-E6BB-4AD9-AEB6-2DB4D317C7DB\" name=\"#{solicitacaoServico.grupoNivel1}\">\n        <bpmn2:flowNodeRef>_BABF0B36-D704-4F3F-8995-4F5CC2958990</bpmn2:flowNodeRef>\n        <bpmn2:flowNodeRef>_09755BCA-51FD-46FC-88F5-0624812702CB</bpmn2:flowNodeRef>\n      </bpmn2:lane>\n      <bpmn2:lane id=\"_B991272D-E6A1-442B-88AC-28E914F160AA\" name=\"#{solicitacaoServico.grupoAtual}\">\n        <bpmn2:flowNodeRef>_4A1283E6-60C9-4352-B9D7-FAACF2030C3A</bpmn2:flowNodeRef>\n        <bpmn2:flowNodeRef>_C50C56D1-ACC9-4AEA-88C7-C3CD9591D1C2</bpmn2:flowNodeRef>\n      </bpmn2:lane>\n    </bpmn2:laneSet>\n    <bpmn2:startEvent id=\"_00A73A2E-1017-4631-9783-A63E40954DB2\" drools:bgcolor=\"#FFFFFF\" name=\"\">\n      <bpmn2:outgoing>_885640F7-1369-4531-9836-06995D0FBBCA</bpmn2:outgoing>\n    </bpmn2:startEvent>\n    <bpmn2:exclusiveGateway id=\"_15D2B14E-003A-446E-8E6A-5F5CA0AD8F24\" drools:bgcolor=\"#FFFFFF\" name=\"\" gatewayDirection=\"Diverging\">\n      <bpmn2:incoming>_885640F7-1369-4531-9836-06995D0FBBCA</bpmn2:incoming>\n      <bpmn2:outgoing>_1F2D7DFA-BA3F-4F0C-9B6F-FECE4F98CED0</bpmn2:outgoing>\n      <bpmn2:outgoing>_922B64D9-EB35-49F2-9CF5-3E0B4C60AE2C</bpmn2:outgoing>\n    </bpmn2:exclusiveGateway>\n    <bpmn2:sequenceFlow id=\"_885640F7-1369-4531-9836-06995D0FBBCA\" sourceRef=\"_00A73A2E-1017-4631-9783-A63E40954DB2\" targetRef=\"_15D2B14E-003A-446E-8E6A-5F5CA0AD8F24\"/>\n    <bpmn2:scriptTask id=\"_7EA8C356-A633-4DF5-BB9C-0132AEC21700\" drools:bgcolor=\"#FFFFFF\" name=\"Encerrar atendimento\" scriptFormat=\"http://www.java.com/java\">\n      <bpmn2:incoming>_F5B82048-7257-4443-A4E2-EE32337DE16A</bpmn2:incoming>\n      <bpmn2:incoming>_1F2D7DFA-BA3F-4F0C-9B6F-FECE4F98CED0</bpmn2:incoming>\n      <bpmn2:incoming>_08B904B6-C4D0-4FFD-8075-C292F6D864DE</bpmn2:incoming>\n      <bpmn2:outgoing>_D61154D2-4CAA-4110-B945-219DF2AEF5B9</bpmn2:outgoing>\n      <bpmn2:script>#{execucaoFluxo}.encerra();</bpmn2:script>\n    </bpmn2:scriptTask>\n    <bpmn2:endEvent id=\"_4F42AA2A-68C7-4835-B8CF-CB43BF9288AF\" drools:bgcolor=\"#FFFFFF\" name=\"\">\n      <bpmn2:incoming>_D61154D2-4CAA-4110-B945-219DF2AEF5B9</bpmn2:incoming>\n    </bpmn2:endEvent>\n    <bpmn2:sequenceFlow id=\"_1F2D7DFA-BA3F-4F0C-9B6F-FECE4F98CED0\" sourceRef=\"_15D2B14E-003A-446E-8E6A-5F5CA0AD8F24\" targetRef=\"_7EA8C356-A633-4DF5-BB9C-0132AEC21700\">\n      <bpmn2:conditionExpression xsi:type=\"bpmn2:tFormalExpression\" id=\"_TAoSEML1EeGlM7mRf4ZKFw\">#{solicitacaoServico}.atendida();</bpmn2:conditionExpression>\n    </bpmn2:sequenceFlow>\n    <bpmn2:sequenceFlow id=\"_D61154D2-4CAA-4110-B945-219DF2AEF5B9\" sourceRef=\"_7EA8C356-A633-4DF5-BB9C-0132AEC21700\" targetRef=\"_4F42AA2A-68C7-4835-B8CF-CB43BF9288AF\"/>\n    <bpmn2:exclusiveGateway id=\"_BBB06489-141D-4EF0-9433-C7531A83EE88\" drools:bgcolor=\"#FFFFFF\" name=\"\" gatewayDirection=\"Diverging\">\n      <bpmn2:incoming>_922B64D9-EB35-49F2-9CF5-3E0B4C60AE2C</bpmn2:incoming>\n      <bpmn2:outgoing>_66B784F9-406D-4AAF-B2C1-E0A04AB08510</bpmn2:outgoing>\n      <bpmn2:outgoing>_73EB57AD-0AA2-4270-82CA-93BC8C24EC05</bpmn2:outgoing>\n    </bpmn2:exclusiveGateway>\n    <bpmn2:sequenceFlow id=\"_922B64D9-EB35-49F2-9CF5-3E0B4C60AE2C\" sourceRef=\"_15D2B14E-003A-446E-8E6A-5F5CA0AD8F24\" targetRef=\"_BBB06489-141D-4EF0-9433-C7531A83EE88\">\n      <bpmn2:conditionExpression xsi:type=\"bpmn2:tFormalExpression\" id=\"_TApgMML1EeGlM7mRf4ZKFw\">!#{solicitacaoServico}.atendida();</bpmn2:conditionExpression>\n    </bpmn2:sequenceFlow>\n    <bpmn2:sequenceFlow id=\"_66B784F9-406D-4AAF-B2C1-E0A04AB08510\" sourceRef=\"_BBB06489-141D-4EF0-9433-C7531A83EE88\" targetRef=\"_BABF0B36-D704-4F3F-8995-4F5CC2958990\">\n      <bpmn2:conditionExpression xsi:type=\"bpmn2:tFormalExpression\" id=\"_TApgMcL1EeGlM7mRf4ZKFw\">!#{solicitacaoServico}.escalada();</bpmn2:conditionExpression>\n    </bpmn2:sequenceFlow>\n    <bpmn2:sequenceFlow id=\"_73EB57AD-0AA2-4270-82CA-93BC8C24EC05\" sourceRef=\"_BBB06489-141D-4EF0-9433-C7531A83EE88\" targetRef=\"_4A1283E6-60C9-4352-B9D7-FAACF2030C3A\">\n      <bpmn2:conditionExpression xsi:type=\"bpmn2:tFormalExpression\" id=\"_TAqHQML1EeGlM7mRf4ZKFw\">#{solicitacaoServico}.escalada();</bpmn2:conditionExpression>\n    </bpmn2:sequenceFlow>\n    <bpmn2:sequenceFlow id=\"_8A261F49-6767-405D-B6A8-6F0C4E8388D7\" sourceRef=\"_4A1283E6-60C9-4352-B9D7-FAACF2030C3A\" targetRef=\"_C50C56D1-ACC9-4AEA-88C7-C3CD9591D1C2\"/>\n    <bpmn2:sequenceFlow id=\"_E0ABE882-7BB3-4A1B-8A6F-06A5D506555F\" sourceRef=\"_C50C56D1-ACC9-4AEA-88C7-C3CD9591D1C2\" targetRef=\"_4A1283E6-60C9-4352-B9D7-FAACF2030C3A\">\n      <bpmn2:conditionExpression xsi:type=\"bpmn2:tFormalExpression\" id=\"_TAquUML1EeGlM7mRf4ZKFw\">#{solicitacaoServico}.emAtendimento();</bpmn2:conditionExpression>\n    </bpmn2:sequenceFlow>\n    <bpmn2:sequenceFlow id=\"_08B904B6-C4D0-4FFD-8075-C292F6D864DE\" sourceRef=\"_C50C56D1-ACC9-4AEA-88C7-C3CD9591D1C2\" targetRef=\"_7EA8C356-A633-4DF5-BB9C-0132AEC21700\">\n      <bpmn2:conditionExpression xsi:type=\"bpmn2:tFormalExpression\" id=\"_TAquUcL1EeGlM7mRf4ZKFw\">#{solicitacaoServico}.atendida();</bpmn2:conditionExpression>\n    </bpmn2:sequenceFlow>\n    <bpmn2:sequenceFlow id=\"_9C026CB9-A497-49D6-93C1-7AFFE63DC421\" sourceRef=\"_BABF0B36-D704-4F3F-8995-4F5CC2958990\" targetRef=\"_09755BCA-51FD-46FC-88F5-0624812702CB\"/>\n    <bpmn2:sequenceFlow id=\"_3938AC76-4CA8-4AB8-9D24-05797179140E\" sourceRef=\"_09755BCA-51FD-46FC-88F5-0624812702CB\" targetRef=\"_4A1283E6-60C9-4352-B9D7-FAACF2030C3A\">\n      <bpmn2:conditionExpression xsi:type=\"bpmn2:tFormalExpression\" id=\"_TArVYML1EeGlM7mRf4ZKFw\">!#{solicitacaoServico}.atendida();</bpmn2:conditionExpression>\n    </bpmn2:sequenceFlow>\n    <bpmn2:sequenceFlow id=\"_F5B82048-7257-4443-A4E2-EE32337DE16A\" sourceRef=\"_09755BCA-51FD-46FC-88F5-0624812702CB\" targetRef=\"_7EA8C356-A633-4DF5-BB9C-0132AEC21700\">\n      <bpmn2:conditionExpression xsi:type=\"bpmn2:tFormalExpression\" id=\"_TArVYcL1EeGlM7mRf4ZKFw\">#{solicitacaoServico}.atendida();</bpmn2:conditionExpression>\n    </bpmn2:sequenceFlow>\n    <bpmn2:task id=\"_BABF0B36-D704-4F3F-8995-4F5CC2958990\" drools:bgcolor=\"#FFFFFF\" drools:taskName=\"url:pages/solicitacaoServico/solicitacaoServico.load?escalar=S\" name=\"Escalar atendimento\">\n      <bpmn2:documentation id=\"_TAr8cML1EeGlM7mRf4ZKFw\">Escalar atendimento</bpmn2:documentation>\n      <bpmn2:incoming>_66B784F9-406D-4AAF-B2C1-E0A04AB08510</bpmn2:incoming>\n      <bpmn2:outgoing>_9C026CB9-A497-49D6-93C1-7AFFE63DC421</bpmn2:outgoing>\n      <bpmn2:ioSpecification id=\"_TAr8ccL1EeGlM7mRf4ZKFw\">\n        <bpmn2:dataInput id=\"_BABF0B36-D704-4F3F-8995-4F5CC2958990_TaskNameInput\" name=\"TaskName\"/>\n        <bpmn2:inputSet id=\"_TAr8csL1EeGlM7mRf4ZKFw\"/>\n        <bpmn2:outputSet id=\"_TAr8c8L1EeGlM7mRf4ZKFw\"/>\n      </bpmn2:ioSpecification>\n      <bpmn2:dataInputAssociation id=\"_TAr8dML1EeGlM7mRf4ZKFw\">\n        <bpmn2:targetRef>_BABF0B36-D704-4F3F-8995-4F5CC2958990_TaskNameInput</bpmn2:targetRef>\n        <bpmn2:assignment id=\"_TAr8dcL1EeGlM7mRf4ZKFw\">\n          <bpmn2:from xsi:type=\"bpmn2:tFormalExpression\" id=\"_TAr8dsL1EeGlM7mRf4ZKFw\">url:pages/solicitacaoServico/solicitacaoServico.load?escalar=S</bpmn2:from>\n          <bpmn2:to xsi:type=\"bpmn2:tFormalExpression\" id=\"_TAsjgML1EeGlM7mRf4ZKFw\">_BABF0B36-D704-4F3F-8995-4F5CC2958990_TaskNameInput</bpmn2:to>\n        </bpmn2:assignment>\n      </bpmn2:dataInputAssociation>\n    </bpmn2:task>\n    <bpmn2:exclusiveGateway id=\"_09755BCA-51FD-46FC-88F5-0624812702CB\" drools:bgcolor=\"#ffffff\" name=\"\" gatewayDirection=\"Diverging\">\n      <bpmn2:incoming>_9C026CB9-A497-49D6-93C1-7AFFE63DC421</bpmn2:incoming>\n      <bpmn2:outgoing>_3938AC76-4CA8-4AB8-9D24-05797179140E</bpmn2:outgoing>\n      <bpmn2:outgoing>_F5B82048-7257-4443-A4E2-EE32337DE16A</bpmn2:outgoing>\n    </bpmn2:exclusiveGateway>\n    <bpmn2:task id=\"_4A1283E6-60C9-4352-B9D7-FAACF2030C3A\" drools:bgcolor=\"#FFFFFF\" drools:taskName=\"url:pages/solicitacaoServico/solicitacaoServico.load\" name=\"Atender solicitacao\">\n      <bpmn2:documentation id=\"_TAtKkML1EeGlM7mRf4ZKFw\">Atender solicitacao</bpmn2:documentation>\n      <bpmn2:incoming>_3938AC76-4CA8-4AB8-9D24-05797179140E</bpmn2:incoming>\n      <bpmn2:incoming>_73EB57AD-0AA2-4270-82CA-93BC8C24EC05</bpmn2:incoming>\n      <bpmn2:incoming>_E0ABE882-7BB3-4A1B-8A6F-06A5D506555F</bpmn2:incoming>\n      <bpmn2:outgoing>_8A261F49-6767-405D-B6A8-6F0C4E8388D7</bpmn2:outgoing>\n      <bpmn2:ioSpecification id=\"_TAtKkcL1EeGlM7mRf4ZKFw\">\n        <bpmn2:dataInput id=\"_4A1283E6-60C9-4352-B9D7-FAACF2030C3A_TaskNameInput\" name=\"TaskName\"/>\n        <bpmn2:inputSet id=\"_TAtKksL1EeGlM7mRf4ZKFw\"/>\n        <bpmn2:outputSet id=\"_TAtKk8L1EeGlM7mRf4ZKFw\"/>\n      </bpmn2:ioSpecification>\n      <bpmn2:dataInputAssociation id=\"_TAtKlML1EeGlM7mRf4ZKFw\">\n        <bpmn2:targetRef>_4A1283E6-60C9-4352-B9D7-FAACF2030C3A_TaskNameInput</bpmn2:targetRef>\n        <bpmn2:assignment id=\"_TAtKlcL1EeGlM7mRf4ZKFw\">\n          <bpmn2:from xsi:type=\"bpmn2:tFormalExpression\" id=\"_TAtKlsL1EeGlM7mRf4ZKFw\">url:pages/solicitacaoServico/solicitacaoServico.load</bpmn2:from>\n          <bpmn2:to xsi:type=\"bpmn2:tFormalExpression\" id=\"_TAtKl8L1EeGlM7mRf4ZKFw\">_4A1283E6-60C9-4352-B9D7-FAACF2030C3A_TaskNameInput</bpmn2:to>\n        </bpmn2:assignment>\n      </bpmn2:dataInputAssociation>\n    </bpmn2:task>\n    <bpmn2:exclusiveGateway id=\"_C50C56D1-ACC9-4AEA-88C7-C3CD9591D1C2\" drools:bgcolor=\"#FFFFFF\" name=\"\" gatewayDirection=\"Diverging\">\n      <bpmn2:incoming>_8A261F49-6767-405D-B6A8-6F0C4E8388D7</bpmn2:incoming>\n      <bpmn2:outgoing>_E0ABE882-7BB3-4A1B-8A6F-06A5D506555F</bpmn2:outgoing>\n      <bpmn2:outgoing>_08B904B6-C4D0-4FFD-8075-C292F6D864DE</bpmn2:outgoing>\n    </bpmn2:exclusiveGateway>\n  </bpmn2:process>\n  <bpmndi:BPMNDiagram id=\"_TAtxoML1EeGlM7mRf4ZKFw\">\n    <bpmndi:BPMNPlane id=\"_TAtxocL1EeGlM7mRf4ZKFw\" bpmnElement=\".\">\n      <bpmndi:BPMNShape id=\"_TAuYsML1EeGlM7mRf4ZKFw\" bpmnElement=\"_00A73A2E-1017-4631-9783-A63E40954DB2\">\n        <dc:Bounds height=\"30.0\" width=\"30.0\" x=\"58.0\" y=\"28.0\"/>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"_TAuYscL1EeGlM7mRf4ZKFw\" bpmnElement=\"_15D2B14E-003A-446E-8E6A-5F5CA0AD8F24\">\n        <dc:Bounds height=\"40.0\" width=\"40.0\" x=\"210.0\" y=\"23.0\"/>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNEdge id=\"_TAuYssL1EeGlM7mRf4ZKFw\" bpmnElement=\"_885640F7-1369-4531-9836-06995D0FBBCA\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"73.0\" y=\"43.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"230.0\" y=\"43.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNShape id=\"_TAu_wML1EeGlM7mRf4ZKFw\" bpmnElement=\"_7EA8C356-A633-4DF5-BB9C-0132AEC21700\">\n        <dc:Bounds height=\"48.0\" width=\"209.0\" x=\"480.0\" y=\"19.0\"/>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"_TAu_wcL1EeGlM7mRf4ZKFw\" bpmnElement=\"_4F42AA2A-68C7-4835-B8CF-CB43BF9288AF\">\n        <dc:Bounds height=\"28.0\" width=\"28.0\" x=\"743.0\" y=\"26.0\"/>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNEdge id=\"_TAu_wsL1EeGlM7mRf4ZKFw\" bpmnElement=\"_1F2D7DFA-BA3F-4F0C-9B6F-FECE4F98CED0\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"230.0\" y=\"43.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"584.5\" y=\"43.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"_TAu_w8L1EeGlM7mRf4ZKFw\" bpmnElement=\"_D61154D2-4CAA-4110-B945-219DF2AEF5B9\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"584.5\" y=\"43.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"757.0\" y=\"40.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNShape id=\"_TAvm0ML1EeGlM7mRf4ZKFw\" bpmnElement=\"_BBB06489-141D-4EF0-9433-C7531A83EE88\">\n        <dc:Bounds height=\"40.0\" width=\"40.0\" x=\"210.0\" y=\"116.0\"/>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNEdge id=\"_TAvm0cL1EeGlM7mRf4ZKFw\" bpmnElement=\"_922B64D9-EB35-49F2-9CF5-3E0B4C60AE2C\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"230.0\" y=\"43.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"230.0\" y=\"136.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"_TAvm0sL1EeGlM7mRf4ZKFw\" bpmnElement=\"_66B784F9-406D-4AAF-B2C1-E0A04AB08510\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"230.0\" y=\"136.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"230.0\" y=\"265.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"320.0\" y=\"70.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"_TAwN4ML1EeGlM7mRf4ZKFw\" bpmnElement=\"_73EB57AD-0AA2-4270-82CA-93BC8C24EC05\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"230.0\" y=\"136.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"230.0\" y=\"557.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"320.0\" y=\"122.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"_TAwN4cL1EeGlM7mRf4ZKFw\" bpmnElement=\"_8A261F49-6767-405D-B6A8-6F0C4E8388D7\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"320.0\" y=\"122.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"350.0\" y=\"620.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"469.0\" y=\"185.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"_TAw08ML1EeGlM7mRf4ZKFw\" bpmnElement=\"_E0ABE882-7BB3-4A1B-8A6F-06A5D506555F\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"469.0\" y=\"185.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"499.0\" y=\"557.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"320.0\" y=\"122.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"_TAw08cL1EeGlM7mRf4ZKFw\" bpmnElement=\"_08B904B6-C4D0-4FFD-8075-C292F6D864DE\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"469.0\" y=\"185.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"584.0\" y=\"622.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"584.5\" y=\"43.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"_TAxcAML1EeGlM7mRf4ZKFw\" bpmnElement=\"_9C026CB9-A497-49D6-93C1-7AFFE63DC421\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"320.0\" y=\"70.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"320.0\" y=\"185.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"_TAxcAcL1EeGlM7mRf4ZKFw\" bpmnElement=\"_3938AC76-4CA8-4AB8-9D24-05797179140E\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"320.0\" y=\"185.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"320.0\" y=\"122.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"_TAyDEML1EeGlM7mRf4ZKFw\" bpmnElement=\"_F5B82048-7257-4443-A4E2-EE32337DE16A\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"320.0\" y=\"185.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"425.0\" y=\"380.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"425.0\" y=\"43.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"584.5\" y=\"43.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNShape id=\"_TAyDEcL1EeGlM7mRf4ZKFw\" bpmnElement=\"_BABF0B36-D704-4F3F-8995-4F5CC2958990\">\n        <dc:Bounds height=\"80.0\" width=\"100.0\" x=\"270.0\" y=\"30.0\"/>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"_TAyDEsL1EeGlM7mRf4ZKFw\" bpmnElement=\"_09755BCA-51FD-46FC-88F5-0624812702CB\">\n        <dc:Bounds height=\"40.0\" width=\"40.0\" x=\"300.0\" y=\"165.0\"/>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"_TAyqIML1EeGlM7mRf4ZKFw\" bpmnElement=\"_4A1283E6-60C9-4352-B9D7-FAACF2030C3A\">\n        <dc:Bounds height=\"80.0\" width=\"100.0\" x=\"270.0\" y=\"82.0\"/>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"_TAyqIcL1EeGlM7mRf4ZKFw\" bpmnElement=\"_C50C56D1-ACC9-4AEA-88C7-C3CD9591D1C2\">\n        <dc:Bounds height=\"40.0\" width=\"40.0\" x=\"449.0\" y=\"165.0\"/>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"_TAyqIsL1EeGlM7mRf4ZKFw\" bpmnElement=\"_7219C884-E6BB-4AD9-AEB6-2DB4D317C7DB\">\n        <dc:Bounds height=\"234.0\" width=\"800.0\" x=\"30.0\" y=\"195.0\"/>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"_TAyqI8L1EeGlM7mRf4ZKFw\" bpmnElement=\"_B991272D-E6A1-442B-88AC-28E914F160AA\">\n        <dc:Bounds height=\"244.0\" width=\"797.0\" x=\"30.0\" y=\"435.0\"/>\n      </bpmndi:BPMNShape>\n    </bpmndi:BPMNPlane>\n  </bpmndi:BPMNDiagram>\n</bpmn2:definitions>\n','2012-06-30','2012-07-30'),(2,'2.0',1,'solicitacaoServico;solicitacaoServico.situacao;solicitacaoServico.grupoAtual;solicitacaoServico.grupoNivel1','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<bpmn2:definitions xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://www.omg.org/bpmn20\" xmlns:bpmn2=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:dc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:di=\"http://www.omg.org/spec/DD/20100524/DI\" xmlns:drools=\"http://www.jboss.org/drools\" id=\"_gKtygM0hEeG3EMaQBy-8iQ\" xsi:schemaLocation=\"http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd\" targetNamespace=\"http://www.omg.org/bpmn20\">\n  <bpmn2:itemDefinition id=\"_solicitacaoServico;solicitacaoServico.situacao;solicitacaoServico.grupoAtual;solicitacaoServico.grupoNivel1Item\"/>\n  <bpmn2:process id=\".\" drools:version=\"1.0\" name=\"SolicitacaoServico\" isExecutable=\"true\">\n    <bpmn2:property id=\"solicitacaoServico;solicitacaoServico.situacao;solicitacaoServico.grupoAtual;solicitacaoServico.grupoNivel1\" itemSubjectRef=\"_solicitacaoServico;solicitacaoServico.situacao;solicitacaoServico.grupoAtual;solicitacaoServico.grupoNivel1Item\"/>\n    <bpmn2:laneSet id=\"_gKvAoM0hEeG3EMaQBy-8iQ\">\n      <bpmn2:lane id=\"_E4BE29F4-2CB0-4151-B78E-6F7CB9C964E2\" name=\"#{solicitacaoServico.grupoNivel1}\">\n        <bpmn2:flowNodeRef>_D1F0DC16-EDF1-4B15-9ED7-8ADE73B93528</bpmn2:flowNodeRef>\n        <bpmn2:flowNodeRef>_F63DBDCB-BB51-4F2E-B4C5-8ACC345D3075</bpmn2:flowNodeRef>\n        <bpmn2:flowNodeRef>_4EE3576B-B282-4ED0-A2F9-0BCCB4FE2F00</bpmn2:flowNodeRef>\n        <bpmn2:flowNodeRef>_597D1CD3-C204-45E4-AEB0-4075E2F576E6</bpmn2:flowNodeRef>\n      </bpmn2:lane>\n      <bpmn2:lane id=\"_66012055-0E90-4DAA-81D5-947E6CB85D75\" name=\"#{solicitacaoServico.grupoAtual}\">\n        <bpmn2:flowNodeRef>_BFC9602E-61ED-407B-B48E-C2BDBF35BB09</bpmn2:flowNodeRef>\n        <bpmn2:flowNodeRef>_2D4DB592-0EE1-4C7B-9A89-DB95F7A8B864</bpmn2:flowNodeRef>\n      </bpmn2:lane>\n    </bpmn2:laneSet>\n    <bpmn2:startEvent id=\"_9ECA28AE-4DD0-44A2-B5AB-348EC7AA52CB\" drools:bgcolor=\"#FFFFFF\" name=\"\">\n      <bpmn2:outgoing>_DB01681D-84A3-483A-A4B5-EBE4C154E33B</bpmn2:outgoing>\n    </bpmn2:startEvent>\n    <bpmn2:exclusiveGateway id=\"_A11AB028-DCA6-474E-9AF7-87324B07A62C\" drools:bgcolor=\"#FFFFFF\" name=\"\" gatewayDirection=\"Diverging\">\n      <bpmn2:incoming>_DB01681D-84A3-483A-A4B5-EBE4C154E33B</bpmn2:incoming>\n      <bpmn2:outgoing>_1D8B646D-8A0E-4377-9275-5E757153D3CB</bpmn2:outgoing>\n      <bpmn2:outgoing>_8A42F392-C7F2-447E-A52F-9E2B51A9C15A</bpmn2:outgoing>\n    </bpmn2:exclusiveGateway>\n    <bpmn2:sequenceFlow id=\"_DB01681D-84A3-483A-A4B5-EBE4C154E33B\" sourceRef=\"_9ECA28AE-4DD0-44A2-B5AB-348EC7AA52CB\" targetRef=\"_A11AB028-DCA6-474E-9AF7-87324B07A62C\"/>\n    <bpmn2:scriptTask id=\"_823F2422-DDCD-461F-B1E2-87324F5F5166\" drools:bgcolor=\"#FFFFFF\" name=\"Encerrar atendimento\" scriptFormat=\"http://www.java.com/java\">\n      <bpmn2:incoming>_1D8B646D-8A0E-4377-9275-5E757153D3CB</bpmn2:incoming>\n      <bpmn2:incoming>_6549E9BA-450B-4AAC-B7A3-3445706D0685</bpmn2:incoming>\n      <bpmn2:incoming>_37EFFCE2-6742-4B63-80C1-B4A775B41AAF</bpmn2:incoming>\n      <bpmn2:outgoing>_72B575F2-E153-4031-8F73-C99A03885A41</bpmn2:outgoing>\n      <bpmn2:script>#{execucaoFluxo}.encerra();</bpmn2:script>\n    </bpmn2:scriptTask>\n    <bpmn2:endEvent id=\"_6D81611E-F3B6-4408-AC0A-B69A13201DD3\" drools:bgcolor=\"#FFFFFF\" name=\"\">\n      <bpmn2:incoming>_72B575F2-E153-4031-8F73-C99A03885A41</bpmn2:incoming>\n    </bpmn2:endEvent>\n    <bpmn2:sequenceFlow id=\"_1D8B646D-8A0E-4377-9275-5E757153D3CB\" sourceRef=\"_A11AB028-DCA6-474E-9AF7-87324B07A62C\" targetRef=\"_823F2422-DDCD-461F-B1E2-87324F5F5166\">\n      <bpmn2:conditionExpression xsi:type=\"bpmn2:tFormalExpression\" id=\"_gKzSEM0hEeG3EMaQBy-8iQ\">#{solicitacaoServico}.atendida();</bpmn2:conditionExpression>\n    </bpmn2:sequenceFlow>\n    <bpmn2:sequenceFlow id=\"_72B575F2-E153-4031-8F73-C99A03885A41\" sourceRef=\"_823F2422-DDCD-461F-B1E2-87324F5F5166\" targetRef=\"_6D81611E-F3B6-4408-AC0A-B69A13201DD3\"/>\n    <bpmn2:exclusiveGateway id=\"_E9AC92E3-B050-4979-9A87-8AED9BBC66B1\" drools:bgcolor=\"#FFFFFF\" name=\"\" gatewayDirection=\"Diverging\">\n      <bpmn2:incoming>_8A42F392-C7F2-447E-A52F-9E2B51A9C15A</bpmn2:incoming>\n      <bpmn2:outgoing>_DBC25383-BFD2-4D41-9C0A-84E317CB2FE2</bpmn2:outgoing>\n      <bpmn2:outgoing>_27FA215B-0D2F-4237-A54A-0283997B3188</bpmn2:outgoing>\n    </bpmn2:exclusiveGateway>\n    <bpmn2:sequenceFlow id=\"_8A42F392-C7F2-447E-A52F-9E2B51A9C15A\" sourceRef=\"_A11AB028-DCA6-474E-9AF7-87324B07A62C\" targetRef=\"_E9AC92E3-B050-4979-9A87-8AED9BBC66B1\">\n      <bpmn2:conditionExpression xsi:type=\"bpmn2:tFormalExpression\" id=\"_gK0gMM0hEeG3EMaQBy-8iQ\">!#{solicitacaoServico}.atendida();</bpmn2:conditionExpression>\n    </bpmn2:sequenceFlow>\n    <bpmn2:sequenceFlow id=\"_DBC25383-BFD2-4D41-9C0A-84E317CB2FE2\" sourceRef=\"_E9AC92E3-B050-4979-9A87-8AED9BBC66B1\" targetRef=\"_D1F0DC16-EDF1-4B15-9ED7-8ADE73B93528\">\n      <bpmn2:conditionExpression xsi:type=\"bpmn2:tFormalExpression\" id=\"_gK1HQM0hEeG3EMaQBy-8iQ\">!#{solicitacaoServico}.escalada();</bpmn2:conditionExpression>\n    </bpmn2:sequenceFlow>\n    <bpmn2:sequenceFlow id=\"_27FA215B-0D2F-4237-A54A-0283997B3188\" sourceRef=\"_E9AC92E3-B050-4979-9A87-8AED9BBC66B1\" targetRef=\"_BFC9602E-61ED-407B-B48E-C2BDBF35BB09\">\n      <bpmn2:conditionExpression xsi:type=\"bpmn2:tFormalExpression\" id=\"_gK1uUM0hEeG3EMaQBy-8iQ\">#{solicitacaoServico}.escalada();</bpmn2:conditionExpression>\n    </bpmn2:sequenceFlow>\n    <bpmn2:sequenceFlow id=\"_5680EF4F-9DD3-4F3A-9279-0A42E20D68A6\" sourceRef=\"_BFC9602E-61ED-407B-B48E-C2BDBF35BB09\" targetRef=\"_2D4DB592-0EE1-4C7B-9A89-DB95F7A8B864\"/>\n    <bpmn2:sequenceFlow id=\"_177C6E04-C4AF-4AFF-87CA-1D8698CD8F83\" sourceRef=\"_2D4DB592-0EE1-4C7B-9A89-DB95F7A8B864\" targetRef=\"_BFC9602E-61ED-407B-B48E-C2BDBF35BB09\">\n      <bpmn2:conditionExpression xsi:type=\"bpmn2:tFormalExpression\" id=\"_gLIpQM0hEeG3EMaQBy-8iQ\">#{solicitacaoServico}.emAtendimento();</bpmn2:conditionExpression>\n    </bpmn2:sequenceFlow>\n    <bpmn2:sequenceFlow id=\"_132E3531-1175-4D7E-A881-3564EFEE550F\" sourceRef=\"_2D4DB592-0EE1-4C7B-9A89-DB95F7A8B864\" targetRef=\"_4EE3576B-B282-4ED0-A2F9-0BCCB4FE2F00\">\n      <bpmn2:conditionExpression xsi:type=\"bpmn2:tFormalExpression\" id=\"_gLIpQc0hEeG3EMaQBy-8iQ\">#{solicitacaoServico}.atendida();</bpmn2:conditionExpression>\n    </bpmn2:sequenceFlow>\n    <bpmn2:sequenceFlow id=\"_6DCE3644-A05A-428C-B5A9-03F5A56EE260\" sourceRef=\"_D1F0DC16-EDF1-4B15-9ED7-8ADE73B93528\" targetRef=\"_F63DBDCB-BB51-4F2E-B4C5-8ACC345D3075\"/>\n    <bpmn2:sequenceFlow id=\"_55BDC649-5F2F-48A2-B165-86DD8A6AA54F\" sourceRef=\"_F63DBDCB-BB51-4F2E-B4C5-8ACC345D3075\" targetRef=\"_BFC9602E-61ED-407B-B48E-C2BDBF35BB09\">\n      <bpmn2:conditionExpression xsi:type=\"bpmn2:tFormalExpression\" id=\"_gLJ3YM0hEeG3EMaQBy-8iQ\">!#{solicitacaoServico}.atendida();</bpmn2:conditionExpression>\n    </bpmn2:sequenceFlow>\n    <bpmn2:sequenceFlow id=\"_6549E9BA-450B-4AAC-B7A3-3445706D0685\" sourceRef=\"_F63DBDCB-BB51-4F2E-B4C5-8ACC345D3075\" targetRef=\"_823F2422-DDCD-461F-B1E2-87324F5F5166\">\n      <bpmn2:conditionExpression xsi:type=\"bpmn2:tFormalExpression\" id=\"_gLKecM0hEeG3EMaQBy-8iQ\">#{solicitacaoServico}.atendida();</bpmn2:conditionExpression>\n    </bpmn2:sequenceFlow>\n    <bpmn2:sequenceFlow id=\"_86FB570A-0D39-46A2-89A1-D3F94B2412CA\" sourceRef=\"_597D1CD3-C204-45E4-AEB0-4075E2F576E6\" targetRef=\"_D1F0DC16-EDF1-4B15-9ED7-8ADE73B93528\">\n      <bpmn2:conditionExpression xsi:type=\"bpmn2:tFormalExpression\" id=\"_gLKecc0hEeG3EMaQBy-8iQ\">!#{solicitacaoServico}.atendida();</bpmn2:conditionExpression>\n    </bpmn2:sequenceFlow>\n    <bpmn2:sequenceFlow id=\"_37EFFCE2-6742-4B63-80C1-B4A775B41AAF\" sourceRef=\"_597D1CD3-C204-45E4-AEB0-4075E2F576E6\" targetRef=\"_823F2422-DDCD-461F-B1E2-87324F5F5166\">\n      <bpmn2:conditionExpression xsi:type=\"bpmn2:tFormalExpression\" id=\"_gLLFgM0hEeG3EMaQBy-8iQ\">#{solicitacaoServico}.atendida();</bpmn2:conditionExpression>\n    </bpmn2:sequenceFlow>\n    <bpmn2:sequenceFlow id=\"_92E6B1B0-3C0A-47C0-A859-224224EDE9AE\" sourceRef=\"_4EE3576B-B282-4ED0-A2F9-0BCCB4FE2F00\" targetRef=\"_597D1CD3-C204-45E4-AEB0-4075E2F576E6\"/>\n    <bpmn2:task id=\"_D1F0DC16-EDF1-4B15-9ED7-8ADE73B93528\" drools:bgcolor=\"#FFFFFF\" drools:taskName=\"url:pages/solicitacaoServico/solicitacaoServico.load?escalar=S\" name=\"Escalar atendimento\">\n      <bpmn2:documentation id=\"_gLMToM0hEeG3EMaQBy-8iQ\">Direcionar atendimento</bpmn2:documentation>\n      <bpmn2:incoming>_86FB570A-0D39-46A2-89A1-D3F94B2412CA</bpmn2:incoming>\n      <bpmn2:incoming>_DBC25383-BFD2-4D41-9C0A-84E317CB2FE2</bpmn2:incoming>\n      <bpmn2:outgoing>_6DCE3644-A05A-428C-B5A9-03F5A56EE260</bpmn2:outgoing>\n      <bpmn2:ioSpecification id=\"_gLMToc0hEeG3EMaQBy-8iQ\">\n        <bpmn2:dataInput id=\"_D1F0DC16-EDF1-4B15-9ED7-8ADE73B93528_TaskNameInput\" name=\"TaskName\"/>\n        <bpmn2:inputSet id=\"_gLMTos0hEeG3EMaQBy-8iQ\"/>\n        <bpmn2:outputSet id=\"_gLMTo80hEeG3EMaQBy-8iQ\"/>\n      </bpmn2:ioSpecification>\n      <bpmn2:dataInputAssociation id=\"_gLMTpM0hEeG3EMaQBy-8iQ\">\n        <bpmn2:targetRef>_D1F0DC16-EDF1-4B15-9ED7-8ADE73B93528_TaskNameInput</bpmn2:targetRef>\n        <bpmn2:assignment id=\"_gLM6sM0hEeG3EMaQBy-8iQ\">\n          <bpmn2:from xsi:type=\"bpmn2:tFormalExpression\" id=\"_gLM6sc0hEeG3EMaQBy-8iQ\">url:pages/solicitacaoServico/solicitacaoServico.load?escalar=S</bpmn2:from>\n          <bpmn2:to xsi:type=\"bpmn2:tFormalExpression\" id=\"_gLM6ss0hEeG3EMaQBy-8iQ\">_D1F0DC16-EDF1-4B15-9ED7-8ADE73B93528_TaskNameInput</bpmn2:to>\n        </bpmn2:assignment>\n      </bpmn2:dataInputAssociation>\n    </bpmn2:task>\n    <bpmn2:exclusiveGateway id=\"_F63DBDCB-BB51-4F2E-B4C5-8ACC345D3075\" drools:bgcolor=\"#ffffff\" name=\"\" gatewayDirection=\"Diverging\">\n      <bpmn2:incoming>_6DCE3644-A05A-428C-B5A9-03F5A56EE260</bpmn2:incoming>\n      <bpmn2:outgoing>_55BDC649-5F2F-48A2-B165-86DD8A6AA54F</bpmn2:outgoing>\n      <bpmn2:outgoing>_6549E9BA-450B-4AAC-B7A3-3445706D0685</bpmn2:outgoing>\n    </bpmn2:exclusiveGateway>\n    <bpmn2:task id=\"_4EE3576B-B282-4ED0-A2F9-0BCCB4FE2F00\" drools:bgcolor=\"#b1c2d6\" drools:taskName=\"url:pages/solicitacaoServico/solicitacaoServico.load?escalar=S&amp;alterarSituacao=S\" name=\"Controle de Qualidade\">\n      <bpmn2:documentation id=\"_gLOI0M0hEeG3EMaQBy-8iQ\">Controle de qualidade</bpmn2:documentation>\n      <bpmn2:incoming>_132E3531-1175-4D7E-A881-3564EFEE550F</bpmn2:incoming>\n      <bpmn2:outgoing>_92E6B1B0-3C0A-47C0-A859-224224EDE9AE</bpmn2:outgoing>\n      <bpmn2:ioSpecification id=\"_gLOI0c0hEeG3EMaQBy-8iQ\">\n        <bpmn2:dataInput id=\"_4EE3576B-B282-4ED0-A2F9-0BCCB4FE2F00_TaskNameInput\" name=\"TaskName\"/>\n        <bpmn2:inputSet id=\"_gLOI0s0hEeG3EMaQBy-8iQ\"/>\n        <bpmn2:outputSet id=\"_gLOI080hEeG3EMaQBy-8iQ\"/>\n      </bpmn2:ioSpecification>\n      <bpmn2:dataInputAssociation id=\"_gLOI1M0hEeG3EMaQBy-8iQ\">\n        <bpmn2:targetRef>_4EE3576B-B282-4ED0-A2F9-0BCCB4FE2F00_TaskNameInput</bpmn2:targetRef>\n        <bpmn2:assignment id=\"_gLOv4M0hEeG3EMaQBy-8iQ\">\n          <bpmn2:from xsi:type=\"bpmn2:tFormalExpression\" id=\"_gLOv4c0hEeG3EMaQBy-8iQ\">url:pages/solicitacaoServico/solicitacaoServico.load?escalar=S&amp;alterarSituacao=S</bpmn2:from>\n          <bpmn2:to xsi:type=\"bpmn2:tFormalExpression\" id=\"_gLOv4s0hEeG3EMaQBy-8iQ\">_4EE3576B-B282-4ED0-A2F9-0BCCB4FE2F00_TaskNameInput</bpmn2:to>\n        </bpmn2:assignment>\n      </bpmn2:dataInputAssociation>\n    </bpmn2:task>\n    <bpmn2:exclusiveGateway id=\"_597D1CD3-C204-45E4-AEB0-4075E2F576E6\" drools:bgcolor=\"#ffffff\" name=\"\" gatewayDirection=\"Diverging\">\n      <bpmn2:incoming>_92E6B1B0-3C0A-47C0-A859-224224EDE9AE</bpmn2:incoming>\n      <bpmn2:outgoing>_86FB570A-0D39-46A2-89A1-D3F94B2412CA</bpmn2:outgoing>\n      <bpmn2:outgoing>_37EFFCE2-6742-4B63-80C1-B4A775B41AAF</bpmn2:outgoing>\n    </bpmn2:exclusiveGateway>\n    <bpmn2:task id=\"_BFC9602E-61ED-407B-B48E-C2BDBF35BB09\" drools:bgcolor=\"#FFFFFF\" drools:taskName=\"url:pages/solicitacaoServico/solicitacaoServico.load?escalar=S&amp;alterarSituacao=S\" name=\"Atender solicitacao\">\n      <bpmn2:documentation id=\"_gLQlEM0hEeG3EMaQBy-8iQ\">Atender solicitacao</bpmn2:documentation>\n      <bpmn2:incoming>_55BDC649-5F2F-48A2-B165-86DD8A6AA54F</bpmn2:incoming>\n      <bpmn2:incoming>_27FA215B-0D2F-4237-A54A-0283997B3188</bpmn2:incoming>\n      <bpmn2:incoming>_177C6E04-C4AF-4AFF-87CA-1D8698CD8F83</bpmn2:incoming>\n      <bpmn2:outgoing>_5680EF4F-9DD3-4F3A-9279-0A42E20D68A6</bpmn2:outgoing>\n      <bpmn2:ioSpecification id=\"_gLQlEc0hEeG3EMaQBy-8iQ\">\n        <bpmn2:dataInput id=\"_BFC9602E-61ED-407B-B48E-C2BDBF35BB09_TaskNameInput\" name=\"TaskName\"/>\n        <bpmn2:inputSet id=\"_gLQlEs0hEeG3EMaQBy-8iQ\"/>\n        <bpmn2:outputSet id=\"_gLQlE80hEeG3EMaQBy-8iQ\"/>\n      </bpmn2:ioSpecification>\n      <bpmn2:dataInputAssociation id=\"_gLQlFM0hEeG3EMaQBy-8iQ\">\n        <bpmn2:targetRef>_BFC9602E-61ED-407B-B48E-C2BDBF35BB09_TaskNameInput</bpmn2:targetRef>\n        <bpmn2:assignment id=\"_gLQlFc0hEeG3EMaQBy-8iQ\">\n          <bpmn2:from xsi:type=\"bpmn2:tFormalExpression\" id=\"_gLRMIM0hEeG3EMaQBy-8iQ\">url:pages/solicitacaoServico/solicitacaoServico.load?escalar=S&amp;alterarSituacao=S</bpmn2:from>\n          <bpmn2:to xsi:type=\"bpmn2:tFormalExpression\" id=\"_gLRMIc0hEeG3EMaQBy-8iQ\">_BFC9602E-61ED-407B-B48E-C2BDBF35BB09_TaskNameInput</bpmn2:to>\n        </bpmn2:assignment>\n      </bpmn2:dataInputAssociation>\n    </bpmn2:task>\n    <bpmn2:exclusiveGateway id=\"_2D4DB592-0EE1-4C7B-9A89-DB95F7A8B864\" drools:bgcolor=\"#FFFFFF\" name=\"\" gatewayDirection=\"Diverging\">\n      <bpmn2:incoming>_5680EF4F-9DD3-4F3A-9279-0A42E20D68A6</bpmn2:incoming>\n      <bpmn2:outgoing>_177C6E04-C4AF-4AFF-87CA-1D8698CD8F83</bpmn2:outgoing>\n      <bpmn2:outgoing>_132E3531-1175-4D7E-A881-3564EFEE550F</bpmn2:outgoing>\n    </bpmn2:exclusiveGateway>\n  </bpmn2:process>\n  <bpmndi:BPMNDiagram id=\"_gLRzMM0hEeG3EMaQBy-8iQ\">\n    <bpmndi:BPMNPlane id=\"_gLRzMc0hEeG3EMaQBy-8iQ\" bpmnElement=\".\">\n      <bpmndi:BPMNShape id=\"_gLRzMs0hEeG3EMaQBy-8iQ\" bpmnElement=\"_9ECA28AE-4DD0-44A2-B5AB-348EC7AA52CB\">\n        <dc:Bounds height=\"30.0\" width=\"30.0\" x=\"58.0\" y=\"28.0\"/>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"_gLSaQM0hEeG3EMaQBy-8iQ\" bpmnElement=\"_A11AB028-DCA6-474E-9AF7-87324B07A62C\">\n        <dc:Bounds height=\"40.0\" width=\"40.0\" x=\"210.0\" y=\"23.0\"/>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNEdge id=\"_gLSaQc0hEeG3EMaQBy-8iQ\" bpmnElement=\"_DB01681D-84A3-483A-A4B5-EBE4C154E33B\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"73.0\" y=\"43.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"230.0\" y=\"43.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNShape id=\"_gLTBUM0hEeG3EMaQBy-8iQ\" bpmnElement=\"_823F2422-DDCD-461F-B1E2-87324F5F5166\">\n        <dc:Bounds height=\"48.0\" width=\"209.0\" x=\"480.0\" y=\"19.0\"/>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"_gLTBUc0hEeG3EMaQBy-8iQ\" bpmnElement=\"_6D81611E-F3B6-4408-AC0A-B69A13201DD3\">\n        <dc:Bounds height=\"28.0\" width=\"28.0\" x=\"740.0\" y=\"23.0\"/>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNEdge id=\"_gLTBUs0hEeG3EMaQBy-8iQ\" bpmnElement=\"_1D8B646D-8A0E-4377-9275-5E757153D3CB\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"230.0\" y=\"43.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"584.5\" y=\"43.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"_gLToYM0hEeG3EMaQBy-8iQ\" bpmnElement=\"_72B575F2-E153-4031-8F73-C99A03885A41\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"584.5\" y=\"43.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"754.0\" y=\"37.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNShape id=\"_gLToYc0hEeG3EMaQBy-8iQ\" bpmnElement=\"_E9AC92E3-B050-4979-9A87-8AED9BBC66B1\">\n        <dc:Bounds height=\"40.0\" width=\"40.0\" x=\"210.0\" y=\"116.0\"/>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNEdge id=\"_gLUPcM0hEeG3EMaQBy-8iQ\" bpmnElement=\"_8A42F392-C7F2-447E-A52F-9E2B51A9C15A\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"230.0\" y=\"43.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"230.0\" y=\"136.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"_gLUPcc0hEeG3EMaQBy-8iQ\" bpmnElement=\"_DBC25383-BFD2-4D41-9C0A-84E317CB2FE2\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"230.0\" y=\"136.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"230.0\" y=\"265.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"305.0\" y=\"70.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"_gLU2gM0hEeG3EMaQBy-8iQ\" bpmnElement=\"_27FA215B-0D2F-4237-A54A-0283997B3188\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"230.0\" y=\"136.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"230.0\" y=\"557.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"320.0\" y=\"122.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"_gLU2gc0hEeG3EMaQBy-8iQ\" bpmnElement=\"_5680EF4F-9DD3-4F3A-9279-0A42E20D68A6\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"320.0\" y=\"122.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"350.0\" y=\"620.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"469.0\" y=\"185.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"_gLVdkM0hEeG3EMaQBy-8iQ\" bpmnElement=\"_177C6E04-C4AF-4AFF-87CA-1D8698CD8F83\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"469.0\" y=\"185.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"499.0\" y=\"557.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"320.0\" y=\"122.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"_gLVdkc0hEeG3EMaQBy-8iQ\" bpmnElement=\"_132E3531-1175-4D7E-A881-3564EFEE550F\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"469.0\" y=\"185.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"650.0\" y=\"620.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"631.0\" y=\"89.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"_gLWEoM0hEeG3EMaQBy-8iQ\" bpmnElement=\"_6DCE3644-A05A-428C-B5A9-03F5A56EE260\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"305.0\" y=\"70.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"335.0\" y=\"332.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"350.0\" y=\"332.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"320.0\" y=\"185.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"_gLWrsM0hEeG3EMaQBy-8iQ\" bpmnElement=\"_55BDC649-5F2F-48A2-B165-86DD8A6AA54F\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"320.0\" y=\"185.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"320.0\" y=\"122.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"_gLWrsc0hEeG3EMaQBy-8iQ\" bpmnElement=\"_6549E9BA-450B-4AAC-B7A3-3445706D0685\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"320.0\" y=\"185.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"425.0\" y=\"380.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"425.0\" y=\"43.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"584.5\" y=\"43.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"_gLXSwM0hEeG3EMaQBy-8iQ\" bpmnElement=\"_86FB570A-0D39-46A2-89A1-D3F94B2412CA\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"515.0\" y=\"70.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"455.0\" y=\"267.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"455.0\" y=\"265.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"305.0\" y=\"70.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"_gLX50M0hEeG3EMaQBy-8iQ\" bpmnElement=\"_37EFFCE2-6742-4B63-80C1-B4A775B41AAF\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"515.0\" y=\"70.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"545.0\" y=\"156.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"584.0\" y=\"156.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"584.5\" y=\"43.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"_gLZvAM0hEeG3EMaQBy-8iQ\" bpmnElement=\"_92E6B1B0-3C0A-47C0-A859-224224EDE9AE\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"631.0\" y=\"89.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"588.0\" y=\"284.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"588.0\" y=\"265.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"515.0\" y=\"70.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNShape id=\"_gLi48M0hEeG3EMaQBy-8iQ\" bpmnElement=\"_D1F0DC16-EDF1-4B15-9ED7-8ADE73B93528\">\n        <dc:Bounds height=\"80.0\" width=\"100.0\" x=\"255.0\" y=\"30.0\"/>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"_gLjgAM0hEeG3EMaQBy-8iQ\" bpmnElement=\"_F63DBDCB-BB51-4F2E-B4C5-8ACC345D3075\">\n        <dc:Bounds height=\"40.0\" width=\"40.0\" x=\"300.0\" y=\"165.0\"/>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"_gLjgAc0hEeG3EMaQBy-8iQ\" bpmnElement=\"_4EE3576B-B282-4ED0-A2F9-0BCCB4FE2F00\">\n        <dc:Bounds height=\"80.0\" width=\"100.0\" x=\"581.0\" y=\"49.0\"/>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"_gLkHEM0hEeG3EMaQBy-8iQ\" bpmnElement=\"_597D1CD3-C204-45E4-AEB0-4075E2F576E6\">\n        <dc:Bounds height=\"40.0\" width=\"40.0\" x=\"495.0\" y=\"50.0\"/>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"_gLkHEc0hEeG3EMaQBy-8iQ\" bpmnElement=\"_BFC9602E-61ED-407B-B48E-C2BDBF35BB09\">\n        <dc:Bounds height=\"80.0\" width=\"100.0\" x=\"270.0\" y=\"82.0\"/>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"_gLkHEs0hEeG3EMaQBy-8iQ\" bpmnElement=\"_2D4DB592-0EE1-4C7B-9A89-DB95F7A8B864\">\n        <dc:Bounds height=\"40.0\" width=\"40.0\" x=\"449.0\" y=\"165.0\"/>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"_gLkuIM0hEeG3EMaQBy-8iQ\" bpmnElement=\"_E4BE29F4-2CB0-4151-B78E-6F7CB9C964E2\">\n        <dc:Bounds height=\"234.0\" width=\"800.0\" x=\"30.0\" y=\"195.0\"/>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"_gLkuIc0hEeG3EMaQBy-8iQ\" bpmnElement=\"_66012055-0E90-4DAA-81D5-947E6CB85D75\">\n        <dc:Bounds height=\"244.0\" width=\"797.0\" x=\"30.0\" y=\"435.0\"/>\n      </bpmndi:BPMNShape>\n    </bpmndi:BPMNPlane>\n  </bpmndi:BPMNDiagram>\n</bpmn2:definitions>\n','2012-07-30','2012-09-04'),(3,'3.0',1,'solicitacaoServico;solicitacaoServico.situacao;solicitacaoServico.grupoAtual;solicitacaoServico.grupoNivel1','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<bpmn2:definitions xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://www.omg.org/bpmn20\" xmlns:bpmn2=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:dc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:di=\"http://www.omg.org/spec/DD/20100524/DI\" xmlns:drools=\"http://www.jboss.org/drools\" id=\"_E5QD4faLEeGVfvqwEY9LcA\" xsi:schemaLocation=\"http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd\" targetNamespace=\"http://www.omg.org/bpmn20\">\n  <bpmn2:itemDefinition id=\"_solicitacaoServico;solicitacaoServico.situacao;solicitacaoServico.grupoAtual;solicitacaoServico.grupoNivel1Item\"/>\n  <bpmn2:process id=\".\" drools:version=\"1.0\" name=\"SolicitacaoServico\" isExecutable=\"true\">\n    <bpmn2:property id=\"solicitacaoServico;solicitacaoServico.situacao;solicitacaoServico.grupoAtual;solicitacaoServico.grupoNivel1\" itemSubjectRef=\"_solicitacaoServico;solicitacaoServico.situacao;solicitacaoServico.grupoAtual;solicitacaoServico.grupoNivel1Item\"/>\n    <bpmn2:laneSet id=\"_E5SgIPaLEeGVfvqwEY9LcA\">\n      <bpmn2:lane id=\"_9C814F88-58E9-48E6-A44C-703553E0892C\" name=\"#{solicitacaoServico.grupoNivel1}\">\n        <bpmn2:flowNodeRef>_EDADDE41-D5B3-47A6-B1B1-515019CF0421</bpmn2:flowNodeRef>\n        <bpmn2:flowNodeRef>_1D5732FA-DED0-4F31-A479-1AB355B4E913</bpmn2:flowNodeRef>\n      </bpmn2:lane>\n      <bpmn2:lane id=\"_402836A1-A1AB-4E9F-BB4F-DC65F79A4F1E\" name=\"#{solicitacaoServico.grupoAtual}\">\n        <bpmn2:flowNodeRef>_FBFE2D05-4A0D-4F7A-BDE5-F146EEA70F9F</bpmn2:flowNodeRef>\n        <bpmn2:flowNodeRef>_FD9EA06D-7180-4854-B811-F97440508D54</bpmn2:flowNodeRef>\n      </bpmn2:lane>\n    </bpmn2:laneSet>\n    <bpmn2:startEvent id=\"_CEC7B587-6AC8-40B1-A6AE-07FDDFE55E27\" drools:bgcolor=\"#FFFFFF\" name=\"\">\n      <bpmn2:outgoing>_C9744931-B932-435B-BD3D-8FFD72C51FCE</bpmn2:outgoing>\n    </bpmn2:startEvent>\n    <bpmn2:exclusiveGateway id=\"_69BEA550-FDED-4A48-B322-4BC966A621C7\" drools:bgcolor=\"#FFFFFF\" name=\"\" gatewayDirection=\"Diverging\">\n      <bpmn2:incoming>_C9744931-B932-435B-BD3D-8FFD72C51FCE</bpmn2:incoming>\n      <bpmn2:outgoing>_F9CD0B7E-D238-4B81-B872-6DF7EB3220B3</bpmn2:outgoing>\n      <bpmn2:outgoing>_F7FAF05E-70B5-4D5E-9BBD-6744B69A83CD</bpmn2:outgoing>\n    </bpmn2:exclusiveGateway>\n    <bpmn2:sequenceFlow id=\"_C9744931-B932-435B-BD3D-8FFD72C51FCE\" sourceRef=\"_CEC7B587-6AC8-40B1-A6AE-07FDDFE55E27\" targetRef=\"_69BEA550-FDED-4A48-B322-4BC966A621C7\"/>\n    <bpmn2:scriptTask id=\"_1A8E9B76-73C3-480C-BB14-7BE7717B3D9B\" drools:bgcolor=\"#FFFFFF\" name=\"Encerrar atendimento\" scriptFormat=\"http://www.java.com/java\">\n      <bpmn2:incoming>_90C02145-451B-4DA7-90F4-3797B5367970</bpmn2:incoming>\n      <bpmn2:incoming>_DBD51470-B0E2-4E29-820E-E9BC267A551C</bpmn2:incoming>\n      <bpmn2:incoming>_F9CD0B7E-D238-4B81-B872-6DF7EB3220B3</bpmn2:incoming>\n      <bpmn2:outgoing>_2B9E7061-9E23-47DA-8292-17AECDBFBD93</bpmn2:outgoing>\n      <bpmn2:script>#{execucaoFluxo}.encerra();</bpmn2:script>\n    </bpmn2:scriptTask>\n    <bpmn2:endEvent id=\"_3735EC03-438F-4221-A141-D9442F554ACF\" drools:bgcolor=\"#FFFFFF\" name=\"\">\n      <bpmn2:incoming>_2B9E7061-9E23-47DA-8292-17AECDBFBD93</bpmn2:incoming>\n    </bpmn2:endEvent>\n    <bpmn2:sequenceFlow id=\"_F9CD0B7E-D238-4B81-B872-6DF7EB3220B3\" sourceRef=\"_69BEA550-FDED-4A48-B322-4BC966A621C7\" targetRef=\"_1A8E9B76-73C3-480C-BB14-7BE7717B3D9B\">\n      <bpmn2:conditionExpression xsi:type=\"bpmn2:tFormalExpression\" id=\"_E5UVUPaLEeGVfvqwEY9LcA\">#{solicitacaoServico}.atendida();</bpmn2:conditionExpression>\n    </bpmn2:sequenceFlow>\n    <bpmn2:sequenceFlow id=\"_2B9E7061-9E23-47DA-8292-17AECDBFBD93\" sourceRef=\"_1A8E9B76-73C3-480C-BB14-7BE7717B3D9B\" targetRef=\"_3735EC03-438F-4221-A141-D9442F554ACF\"/>\n    <bpmn2:exclusiveGateway id=\"_545FCB64-8543-40D3-83E1-4E09DF4D1ADB\" drools:bgcolor=\"#FFFFFF\" name=\"\" gatewayDirection=\"Diverging\">\n      <bpmn2:incoming>_F7FAF05E-70B5-4D5E-9BBD-6744B69A83CD</bpmn2:incoming>\n      <bpmn2:outgoing>_D02C5575-465E-4B15-8BDB-74AADA4B4A7C</bpmn2:outgoing>\n      <bpmn2:outgoing>_AC928604-C5DB-4424-BFD9-3D5E5E187455</bpmn2:outgoing>\n    </bpmn2:exclusiveGateway>\n    <bpmn2:sequenceFlow id=\"_F7FAF05E-70B5-4D5E-9BBD-6744B69A83CD\" sourceRef=\"_69BEA550-FDED-4A48-B322-4BC966A621C7\" targetRef=\"_545FCB64-8543-40D3-83E1-4E09DF4D1ADB\">\n      <bpmn2:conditionExpression xsi:type=\"bpmn2:tFormalExpression\" id=\"_E5U8YPaLEeGVfvqwEY9LcA\">!#{solicitacaoServico}.atendida();</bpmn2:conditionExpression>\n    </bpmn2:sequenceFlow>\n    <bpmn2:sequenceFlow id=\"_D02C5575-465E-4B15-8BDB-74AADA4B4A7C\" sourceRef=\"_545FCB64-8543-40D3-83E1-4E09DF4D1ADB\" targetRef=\"_EDADDE41-D5B3-47A6-B1B1-515019CF0421\">\n      <bpmn2:conditionExpression xsi:type=\"bpmn2:tFormalExpression\" id=\"_E5U8YfaLEeGVfvqwEY9LcA\">!#{solicitacaoServico}.escalada();</bpmn2:conditionExpression>\n    </bpmn2:sequenceFlow>\n    <bpmn2:sequenceFlow id=\"_AC928604-C5DB-4424-BFD9-3D5E5E187455\" sourceRef=\"_545FCB64-8543-40D3-83E1-4E09DF4D1ADB\" targetRef=\"_FBFE2D05-4A0D-4F7A-BDE5-F146EEA70F9F\">\n      <bpmn2:conditionExpression xsi:type=\"bpmn2:tFormalExpression\" id=\"_E5VjcPaLEeGVfvqwEY9LcA\">#{solicitacaoServico}.escalada();</bpmn2:conditionExpression>\n    </bpmn2:sequenceFlow>\n    <bpmn2:sequenceFlow id=\"_EBA2E8D7-C463-4904-9C63-9B9E710B446E\" sourceRef=\"_FBFE2D05-4A0D-4F7A-BDE5-F146EEA70F9F\" targetRef=\"_FD9EA06D-7180-4854-B811-F97440508D54\"/>\n    <bpmn2:sequenceFlow id=\"_048CD934-6618-40A6-BA0B-00C8ED75EE5E\" sourceRef=\"_FD9EA06D-7180-4854-B811-F97440508D54\" targetRef=\"_FBFE2D05-4A0D-4F7A-BDE5-F146EEA70F9F\">\n      <bpmn2:conditionExpression xsi:type=\"bpmn2:tFormalExpression\" id=\"_E5VjcfaLEeGVfvqwEY9LcA\">#{solicitacaoServico}.emAtendimento();</bpmn2:conditionExpression>\n    </bpmn2:sequenceFlow>\n    <bpmn2:sequenceFlow id=\"_DBD51470-B0E2-4E29-820E-E9BC267A551C\" sourceRef=\"_FD9EA06D-7180-4854-B811-F97440508D54\" targetRef=\"_1A8E9B76-73C3-480C-BB14-7BE7717B3D9B\">\n      <bpmn2:conditionExpression xsi:type=\"bpmn2:tFormalExpression\" id=\"_E5VjcvaLEeGVfvqwEY9LcA\">#{solicitacaoServico}.atendida();</bpmn2:conditionExpression>\n    </bpmn2:sequenceFlow>\n    <bpmn2:sequenceFlow id=\"_DC776005-B434-465F-A000-242CDEABCCD4\" sourceRef=\"_EDADDE41-D5B3-47A6-B1B1-515019CF0421\" targetRef=\"_1D5732FA-DED0-4F31-A479-1AB355B4E913\"/>\n    <bpmn2:sequenceFlow id=\"_24E70E21-820B-41DB-B7AE-7091889A6ECB\" sourceRef=\"_1D5732FA-DED0-4F31-A479-1AB355B4E913\" targetRef=\"_FBFE2D05-4A0D-4F7A-BDE5-F146EEA70F9F\">\n      <bpmn2:conditionExpression xsi:type=\"bpmn2:tFormalExpression\" id=\"_E5WKgPaLEeGVfvqwEY9LcA\">!#{solicitacaoServico}.atendida();</bpmn2:conditionExpression>\n    </bpmn2:sequenceFlow>\n    <bpmn2:sequenceFlow id=\"_90C02145-451B-4DA7-90F4-3797B5367970\" sourceRef=\"_1D5732FA-DED0-4F31-A479-1AB355B4E913\" targetRef=\"_1A8E9B76-73C3-480C-BB14-7BE7717B3D9B\">\n      <bpmn2:conditionExpression xsi:type=\"bpmn2:tFormalExpression\" id=\"_E5WKgfaLEeGVfvqwEY9LcA\">#{solicitacaoServico}.atendida();</bpmn2:conditionExpression>\n    </bpmn2:sequenceFlow>\n    <bpmn2:task id=\"_EDADDE41-D5B3-47A6-B1B1-515019CF0421\" drools:bgcolor=\"#FFFFFF\" drools:taskName=\"url:pages/solicitacaoServico/solicitacaoServico.load?escalar=S\" name=\"Escalar atendimento\">\n      <bpmn2:documentation id=\"_E5WxkPaLEeGVfvqwEY9LcA\">Direcionar atendimento</bpmn2:documentation>\n      <bpmn2:incoming>_D02C5575-465E-4B15-8BDB-74AADA4B4A7C</bpmn2:incoming>\n      <bpmn2:outgoing>_DC776005-B434-465F-A000-242CDEABCCD4</bpmn2:outgoing>\n      <bpmn2:ioSpecification id=\"_E5WxkfaLEeGVfvqwEY9LcA\">\n        <bpmn2:dataInput id=\"_EDADDE41-D5B3-47A6-B1B1-515019CF0421_TaskNameInput\" name=\"TaskName\"/>\n        <bpmn2:inputSet id=\"_E5WxkvaLEeGVfvqwEY9LcA\"/>\n        <bpmn2:outputSet id=\"_E5Wxk_aLEeGVfvqwEY9LcA\"/>\n      </bpmn2:ioSpecification>\n      <bpmn2:dataInputAssociation id=\"_E5WxlPaLEeGVfvqwEY9LcA\">\n        <bpmn2:targetRef>_EDADDE41-D5B3-47A6-B1B1-515019CF0421_TaskNameInput</bpmn2:targetRef>\n        <bpmn2:assignment id=\"_E5WxlfaLEeGVfvqwEY9LcA\">\n          <bpmn2:from xsi:type=\"bpmn2:tFormalExpression\" id=\"_E5WxlvaLEeGVfvqwEY9LcA\">url:pages/solicitacaoServico/solicitacaoServico.load?escalar=S</bpmn2:from>\n          <bpmn2:to xsi:type=\"bpmn2:tFormalExpression\" id=\"_E5Wxl_aLEeGVfvqwEY9LcA\">_EDADDE41-D5B3-47A6-B1B1-515019CF0421_TaskNameInput</bpmn2:to>\n        </bpmn2:assignment>\n      </bpmn2:dataInputAssociation>\n    </bpmn2:task>\n    <bpmn2:exclusiveGateway id=\"_1D5732FA-DED0-4F31-A479-1AB355B4E913\" drools:bgcolor=\"#ffffff\" name=\"\" gatewayDirection=\"Diverging\">\n      <bpmn2:incoming>_DC776005-B434-465F-A000-242CDEABCCD4</bpmn2:incoming>\n      <bpmn2:outgoing>_24E70E21-820B-41DB-B7AE-7091889A6ECB</bpmn2:outgoing>\n      <bpmn2:outgoing>_90C02145-451B-4DA7-90F4-3797B5367970</bpmn2:outgoing>\n    </bpmn2:exclusiveGateway>\n    <bpmn2:task id=\"_FBFE2D05-4A0D-4F7A-BDE5-F146EEA70F9F\" drools:bgcolor=\"#FFFFFF\" drools:taskName=\"url:pages/solicitacaoServico/solicitacaoServico.load?escalar=S&amp;alterarSituacao=S\" name=\"Atender solicitacao\">\n      <bpmn2:documentation id=\"_E5XYoPaLEeGVfvqwEY9LcA\">Atender solicitacao</bpmn2:documentation>\n      <bpmn2:incoming>_AC928604-C5DB-4424-BFD9-3D5E5E187455</bpmn2:incoming>\n      <bpmn2:incoming>_048CD934-6618-40A6-BA0B-00C8ED75EE5E</bpmn2:incoming>\n      <bpmn2:incoming>_24E70E21-820B-41DB-B7AE-7091889A6ECB</bpmn2:incoming>\n      <bpmn2:outgoing>_EBA2E8D7-C463-4904-9C63-9B9E710B446E</bpmn2:outgoing>\n      <bpmn2:ioSpecification id=\"_E5XYofaLEeGVfvqwEY9LcA\">\n        <bpmn2:dataInput id=\"_FBFE2D05-4A0D-4F7A-BDE5-F146EEA70F9F_TaskNameInput\" name=\"TaskName\"/>\n        <bpmn2:inputSet id=\"_E5XYovaLEeGVfvqwEY9LcA\"/>\n        <bpmn2:outputSet id=\"_E5XYo_aLEeGVfvqwEY9LcA\"/>\n      </bpmn2:ioSpecification>\n      <bpmn2:dataInputAssociation id=\"_E5XYpPaLEeGVfvqwEY9LcA\">\n        <bpmn2:targetRef>_FBFE2D05-4A0D-4F7A-BDE5-F146EEA70F9F_TaskNameInput</bpmn2:targetRef>\n        <bpmn2:assignment id=\"_E5XYpfaLEeGVfvqwEY9LcA\">\n          <bpmn2:from xsi:type=\"bpmn2:tFormalExpression\" id=\"_E5XYpvaLEeGVfvqwEY9LcA\">url:pages/solicitacaoServico/solicitacaoServico.load?escalar=S&amp;alterarSituacao=S</bpmn2:from>\n          <bpmn2:to xsi:type=\"bpmn2:tFormalExpression\" id=\"_E5XYp_aLEeGVfvqwEY9LcA\">_FBFE2D05-4A0D-4F7A-BDE5-F146EEA70F9F_TaskNameInput</bpmn2:to>\n        </bpmn2:assignment>\n      </bpmn2:dataInputAssociation>\n    </bpmn2:task>\n    <bpmn2:exclusiveGateway id=\"_FD9EA06D-7180-4854-B811-F97440508D54\" drools:bgcolor=\"#FFFFFF\" name=\"\" gatewayDirection=\"Diverging\">\n      <bpmn2:incoming>_EBA2E8D7-C463-4904-9C63-9B9E710B446E</bpmn2:incoming>\n      <bpmn2:outgoing>_048CD934-6618-40A6-BA0B-00C8ED75EE5E</bpmn2:outgoing>\n      <bpmn2:outgoing>_DBD51470-B0E2-4E29-820E-E9BC267A551C</bpmn2:outgoing>\n    </bpmn2:exclusiveGateway>\n  </bpmn2:process>\n  <bpmndi:BPMNDiagram id=\"_E5X_sPaLEeGVfvqwEY9LcA\">\n    <bpmndi:BPMNPlane id=\"_E5X_sfaLEeGVfvqwEY9LcA\" bpmnElement=\".\">\n      <bpmndi:BPMNShape id=\"_E5X_svaLEeGVfvqwEY9LcA\" bpmnElement=\"_CEC7B587-6AC8-40B1-A6AE-07FDDFE55E27\">\n        <dc:Bounds height=\"30.0\" width=\"30.0\" x=\"58.0\" y=\"28.0\"/>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"_E5X_s_aLEeGVfvqwEY9LcA\" bpmnElement=\"_69BEA550-FDED-4A48-B322-4BC966A621C7\">\n        <dc:Bounds height=\"40.0\" width=\"40.0\" x=\"210.0\" y=\"23.0\"/>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNEdge id=\"_E5X_tPaLEeGVfvqwEY9LcA\" bpmnElement=\"_C9744931-B932-435B-BD3D-8FFD72C51FCE\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"73.0\" y=\"43.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"230.0\" y=\"43.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNShape id=\"_E5YmwPaLEeGVfvqwEY9LcA\" bpmnElement=\"_1A8E9B76-73C3-480C-BB14-7BE7717B3D9B\">\n        <dc:Bounds height=\"48.0\" width=\"209.0\" x=\"480.0\" y=\"19.0\"/>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"_E5YmwfaLEeGVfvqwEY9LcA\" bpmnElement=\"_3735EC03-438F-4221-A141-D9442F554ACF\">\n        <dc:Bounds height=\"28.0\" width=\"28.0\" x=\"739.0\" y=\"22.0\"/>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNEdge id=\"_E5YmwvaLEeGVfvqwEY9LcA\" bpmnElement=\"_F9CD0B7E-D238-4B81-B872-6DF7EB3220B3\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"230.0\" y=\"43.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"584.5\" y=\"43.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"_E5Ymw_aLEeGVfvqwEY9LcA\" bpmnElement=\"_2B9E7061-9E23-47DA-8292-17AECDBFBD93\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"584.5\" y=\"43.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"753.0\" y=\"36.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNShape id=\"_E5YmxPaLEeGVfvqwEY9LcA\" bpmnElement=\"_545FCB64-8543-40D3-83E1-4E09DF4D1ADB\">\n        <dc:Bounds height=\"40.0\" width=\"40.0\" x=\"210.0\" y=\"116.0\"/>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNEdge id=\"_E5ZN0PaLEeGVfvqwEY9LcA\" bpmnElement=\"_F7FAF05E-70B5-4D5E-9BBD-6744B69A83CD\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"230.0\" y=\"43.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"230.0\" y=\"136.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"_E5ZN0faLEeGVfvqwEY9LcA\" bpmnElement=\"_D02C5575-465E-4B15-8BDB-74AADA4B4A7C\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"230.0\" y=\"136.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"230.0\" y=\"265.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"305.0\" y=\"70.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"_E5ZN0vaLEeGVfvqwEY9LcA\" bpmnElement=\"_AC928604-C5DB-4424-BFD9-3D5E5E187455\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"230.0\" y=\"136.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"230.0\" y=\"557.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"320.0\" y=\"122.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"_E5ZN0_aLEeGVfvqwEY9LcA\" bpmnElement=\"_EBA2E8D7-C463-4904-9C63-9B9E710B446E\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"320.0\" y=\"122.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"350.0\" y=\"620.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"469.0\" y=\"185.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"_E5Z04PaLEeGVfvqwEY9LcA\" bpmnElement=\"_048CD934-6618-40A6-BA0B-00C8ED75EE5E\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"469.0\" y=\"185.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"499.0\" y=\"557.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"320.0\" y=\"122.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"_E5Z04faLEeGVfvqwEY9LcA\" bpmnElement=\"_DBD51470-B0E2-4E29-820E-E9BC267A551C\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"469.0\" y=\"185.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"584.0\" y=\"620.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"584.5\" y=\"43.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"_E5Z04vaLEeGVfvqwEY9LcA\" bpmnElement=\"_DC776005-B434-465F-A000-242CDEABCCD4\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"305.0\" y=\"70.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"335.0\" y=\"332.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"350.0\" y=\"332.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"320.0\" y=\"185.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"_E5ab8PaLEeGVfvqwEY9LcA\" bpmnElement=\"_24E70E21-820B-41DB-B7AE-7091889A6ECB\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"320.0\" y=\"185.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"320.0\" y=\"122.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"_E5ab8faLEeGVfvqwEY9LcA\" bpmnElement=\"_90C02145-451B-4DA7-90F4-3797B5367970\">\n        <di:waypoint xsi:type=\"dc:Point\" x=\"320.0\" y=\"185.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"425.0\" y=\"380.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"425.0\" y=\"43.0\"/>\n        <di:waypoint xsi:type=\"dc:Point\" x=\"584.5\" y=\"43.0\"/>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNShape id=\"_E5ab8vaLEeGVfvqwEY9LcA\" bpmnElement=\"_EDADDE41-D5B3-47A6-B1B1-515019CF0421\">\n        <dc:Bounds height=\"80.0\" width=\"100.0\" x=\"255.0\" y=\"30.0\"/>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"_E5ab8_aLEeGVfvqwEY9LcA\" bpmnElement=\"_1D5732FA-DED0-4F31-A479-1AB355B4E913\">\n        <dc:Bounds height=\"40.0\" width=\"40.0\" x=\"300.0\" y=\"165.0\"/>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"_E5bDAPaLEeGVfvqwEY9LcA\" bpmnElement=\"_FBFE2D05-4A0D-4F7A-BDE5-F146EEA70F9F\">\n        <dc:Bounds height=\"80.0\" width=\"100.0\" x=\"270.0\" y=\"82.0\"/>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"_E5bDAfaLEeGVfvqwEY9LcA\" bpmnElement=\"_FD9EA06D-7180-4854-B811-F97440508D54\">\n        <dc:Bounds height=\"40.0\" width=\"40.0\" x=\"449.0\" y=\"165.0\"/>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"_E5bDAvaLEeGVfvqwEY9LcA\" bpmnElement=\"_9C814F88-58E9-48E6-A44C-703553E0892C\">\n        <dc:Bounds height=\"234.0\" width=\"800.0\" x=\"30.0\" y=\"195.0\"/>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"_E5bDA_aLEeGVfvqwEY9LcA\" bpmnElement=\"_402836A1-A1AB-4E9F-BB4F-DC65F79A4F1E\">\n        <dc:Bounds height=\"244.0\" width=\"797.0\" x=\"30.0\" y=\"435.0\"/>\n      </bpmndi:BPMNShape>\n    </bpmndi:BPMNPlane>\n  </bpmndi:BPMNDiagram>\n</bpmn2:definitions>\n','2012-09-04',NULL),(4,'1.0',2,'solicitacaoServico;solicitacaoServico.situacao;solicitacaoServico.grupoAtual;solicitacaoServico.grupoNivel1','','2012-09-14','2012-09-17'),(5,'2.0',2,'solicitacaoServico;solicitacaoServico.situacao;solicitacaoServico.grupoAtual;solicitacaoServico.grupoNivel1','','2012-09-17',NULL),(6,'1.0',4,'requisicaoMudanca;requisicaoMudanca.status;requisicaoMudanca.nomeGrupoAtual;requisicaoMudanca.grupoNivel1',NULL,'2012-11-12','2012-11-12'),(7,'2.0',4,'requisicaoMudanca;requisicaoMudanca.status;requisicaoMudanca.nomeGrupoAtual;requisicaoMudanca.grupoNivel1',NULL,'2012-11-12','2012-11-12'),(8,'1.0',4,'solicitacaoServico;solicitacaoServico.situacao;solicitacaoServico.grupoAtual;solicitacaoServico.grupoNivel1','','2013-02-19',NULL),(9,'3.0',5,'requisicaoMudanca;requisicaoMudanca.status;requisicaoMudanca.nomeGrupoAtual;requisicaoMudanca.grupoNivel1',NULL,'2013-01-09',NULL),(42,'1.0',6,'requisicaoMudanca;requisicaoMudanca.status;requisicaoMudanca.nomeGrupoAtual','','2013-03-26',NULL),(43,'1.0',7,'requisicaoMudanca;requisicaoMudanca.status;requisicaoMudanca.nomeGrupoAtual','','2013-03-26',NULL),(44,'1.0',8,'requisicaoMudanca;requisicaoMudanca.status;requisicaoMudanca.nomeGrupoAtual','','2013-03-26',NULL);
/*!40000 ALTER TABLE `bpm_fluxo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bpm_historicoitemtrabalho`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bpm_historicoitemtrabalho` (
  `idhistoricoitemtrabalho` bigint(20) NOT NULL,
  `iditemtrabalho` bigint(20) NOT NULL,
  `idresponsavel` int(11) DEFAULT NULL,
  `idusuario` int(11) DEFAULT NULL,
  `idgrupo` int(11) DEFAULT NULL,
  `datahora` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `acao` varchar(10) NOT NULL,
  PRIMARY KEY (`idhistoricoitemtrabalho`),
  KEY `fk_reference_52` (`iditemtrabalho`),
  KEY `fk_reference_53` (`idresponsavel`),
  KEY `fk_reference_54` (`idusuario`),
  KEY `fk_reference_55` (`idgrupo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bpm_historicoitemtrabalho`
--

LOCK TABLES `bpm_historicoitemtrabalho` WRITE;
/*!40000 ALTER TABLE `bpm_historicoitemtrabalho` DISABLE KEYS */;
/*!40000 ALTER TABLE `bpm_historicoitemtrabalho` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bpm_instanciafluxo`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bpm_instanciafluxo` (
  `idinstancia` bigint(20) NOT NULL,
  `idfluxo` bigint(20) NOT NULL,
  `datahoracriacao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `situacao` varchar(20) DEFAULT NULL,
  `datahorafinalizacao` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`idinstancia`),
  KEY `fk_reference_122` (`idfluxo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bpm_instanciafluxo`
--

LOCK TABLES `bpm_instanciafluxo` WRITE;
/*!40000 ALTER TABLE `bpm_instanciafluxo` DISABLE KEYS */;
INSERT INTO `bpm_instanciafluxo` VALUES (1,3,'2013-04-05 13:40:01','Aberta',NULL),(2,3,'2013-04-05 13:43:39','Aberta',NULL),(3,3,'2013-04-05 13:46:04','Aberta',NULL),(4,3,'2013-04-05 14:13:18','Aberta',NULL);
/*!40000 ALTER TABLE `bpm_instanciafluxo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bpm_itemtrabalhofluxo`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bpm_itemtrabalhofluxo` (
  `iditemtrabalho` bigint(20) NOT NULL,
  `idinstancia` bigint(20) NOT NULL,
  `idelemento` bigint(20) NOT NULL,
  `idresponsavelatual` int(11) DEFAULT NULL,
  `datahoracriacao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `datahorainicio` timestamp NULL DEFAULT NULL,
  `situacao` char(20) NOT NULL,
  `datahorafinalizacao` timestamp NULL DEFAULT NULL,
  `datahoraexecucao` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`iditemtrabalho`),
  KEY `fk_reference_107` (`idinstancia`),
  KEY `fk_reference_110` (`idresponsavelatual`),
  KEY `fk_reference_112` (`idelemento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bpm_itemtrabalhofluxo`
--

LOCK TABLES `bpm_itemtrabalhofluxo` WRITE;
/*!40000 ALTER TABLE `bpm_itemtrabalhofluxo` DISABLE KEYS */;
INSERT INTO `bpm_itemtrabalhofluxo` VALUES (1,1,23,NULL,'2013-04-05 13:40:01',NULL,'Disponivel',NULL,NULL),(2,2,23,NULL,'2013-04-05 13:43:39',NULL,'Disponivel',NULL,NULL),(3,3,24,NULL,'2013-04-05 13:46:04',NULL,'Executado',NULL,NULL),(4,4,23,NULL,'2013-04-05 14:13:18',NULL,'Disponivel',NULL,NULL);
/*!40000 ALTER TABLE `bpm_itemtrabalhofluxo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bpm_objetoinstanciafluxo`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bpm_objetoinstanciafluxo` (
  `idobjetoinstancia` bigint(20) NOT NULL,
  `iditemtrabalho` bigint(20) DEFAULT NULL,
  `idinstancia` bigint(20) NOT NULL,
  `idobjetonegocio` bigint(20) DEFAULT NULL,
  `nomeobjeto` varchar(100) DEFAULT NULL,
  `nomeclasse` varchar(100) DEFAULT NULL,
  `tipoassociacao` char(1) NOT NULL,
  `campochave` char(1) NOT NULL COMMENT 'S - Sim\n            N - Não',
  `objetoprincipal` char(1) NOT NULL,
  `nometabelabd` varchar(120) DEFAULT NULL,
  `nomecampobd` varchar(100) DEFAULT NULL,
  `tipocampobd` varchar(20) DEFAULT NULL,
  `valor` longtext,
  PRIMARY KEY (`idobjetoinstancia`),
  KEY `fk_reference_106` (`idobjetonegocio`),
  KEY `fk_reference_109` (`iditemtrabalho`),
  KEY `fk_reference_127` (`idinstancia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bpm_objetoinstanciafluxo`
--

LOCK TABLES `bpm_objetoinstanciafluxo` WRITE;
/*!40000 ALTER TABLE `bpm_objetoinstanciafluxo` DISABLE KEYS */;
INSERT INTO `bpm_objetoinstanciafluxo` VALUES (1,NULL,1,NULL,'solicitacaoServico','br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO','I','N','S',NULL,NULL,NULL,'{\"idSolicitacaoServico\":1,\"idContatoSolicitacaoServico\":1,\"idServicoContrato\":1,\"idSolicitante\":2,\"idOrigem\":1,\"idPrioridade\":5,\"filtroADPesq\":\"\",\"idUnidade\":1,\"idFaseAtual\":2,\"dataHoraSolicitacao\":\"Apr 5, 2013 9:40:01 AM\",\"prazoCapturaHH\":0,\"prazoCapturaMM\":0,\"prazoHH\":0,\"prazoMM\":0,\"descricao\":\"Descri\\u0026ccedil;\\u0026atilde;o do Incidente ou Requisi\\u0026ccedil;\\u0026atilde;o\\u003cbr /\\u003e\",\"resposta\":\"\",\"dataHoraInicio\":\"Apr 5, 2013 9:40:01 AM\",\"situacao\":\"EmAndamento\",\"seqReabertura\":0,\"enviaEmailCriacao\":\"S\",\"enviaEmailFinalizacao\":\"S\",\"slaACombinar\":\"S\",\"idCalendario\":1,\"tempoDecorridoHH\":0,\"tempoDecorridoMM\":0,\"situacaoSLA\":\"N\",\"descrSituacao\":\"Em andamento\",\"atrasoSLA\":0.0,\"idContrato\":1,\"solicitante\":\"Default\",\"dataHoraSolicitacaoStr\":\"05/04/2013 09:40\",\"usuarioDto\":{\"idUsuario\":3,\"idEmpregado\":3,\"idPerfilAcessoUsuario\":1,\"idEmpresa\":1,\"login\":\"consultor\",\"nomeUsuario\":\"Consultor\",\"status\":\"A\",\"grupos\":[\"SDNIVEL1\",\"SDNIVEL2\",\"SDNIVEL3Apli\",\"SDNIVEL3Infra\",\"SDNIVEL3Sist\",\"SDNIVEL3Telefonia\"],\"locale\":\"\",\"colGrupos\":[{\"idGrupo\":2,\"nome\":\"1º NÍVEL\",\"sigla\":\"SDNIVEL1\",\"serviceDesk\":\"S\"},{\"idGrupo\":3,\"nome\":\"2º NÍVEL\",\"sigla\":\"SDNIVEL2\",\"serviceDesk\":\"S\"},{\"idGrupo\":16,\"nome\":\"3º NÍVEL - Aplicação\",\"sigla\":\"SDNIVEL3Apli\",\"serviceDesk\":\"S\"},{\"idGrupo\":4,\"nome\":\"3º NÍVEL - Infraestrutura\",\"sigla\":\"SDNIVEL3Infra\",\"serviceDesk\":\"S\"},{\"idGrupo\":17,\"nome\":\"3º NÍVEL - Sistemas\",\"sigla\":\"SDNIVEL3Sist\",\"serviceDesk\":\"S\"},{\"idGrupo\":19,\"nome\":\"3º NÍVEL - Telefonia\",\"sigla\":\"SDNIVEL3Telefonia\",\"serviceDesk\":\"N\"}]},\"grupoAtual\":\"SDNIVEL1\",\"grupoNivel1\":\"SDNIVEL1\",\"acaoFluxo\":\"\",\"idTipoDemandaServico\":3,\"idServico\":1,\"itemConfiguracao\":\"\",\"messageId\":\"\",\"nomecontato\":\"Default\",\"telefonecontato\":\"3242-4433\",\"emailcontato\":\"citsmart_instalador_mysql@centralit.com.br\",\"observacao\":\"\",\"reclassificar\":\"\",\"escalar\":\"\",\"alterarSituacao\":\"\",\"detalhamentoCausa\":\"\",\"solucaoTemporaria\":\"N\",\"nomeServico\":\"Serviço 1\",\"ramal\":\"\",\"idAcordoNivelServico\":1,\"registradoPor\":\"Consultor\",\"registroexecucao\":\"\",\"impacto\":\"B\",\"urgencia\":\"B\",\"descricaoSemFormatacao\":\"Descrição do Incidente ou Requisição\",\"idResponsavel\":3,\"idGrupoAtual\":2,\"idGrupoNivel1\":2}'),(2,NULL,1,NULL,'solicitacaoServico.situacao','java.lang.String','I','N','S',NULL,NULL,NULL,'\"EmAndamento\"'),(3,NULL,1,NULL,'solicitacaoServico.grupoAtual','java.lang.String','I','N','S',NULL,NULL,NULL,'\"SDNIVEL1\"'),(4,NULL,1,NULL,'solicitacaoServico.grupoNivel1','java.lang.String','I','N','S',NULL,NULL,NULL,'\"SDNIVEL1\"'),(5,NULL,2,NULL,'solicitacaoServico','br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO','I','N','S',NULL,NULL,NULL,'{\"idSolicitacaoServico\":2,\"idContatoSolicitacaoServico\":2,\"idServicoContrato\":4,\"idSolicitante\":2,\"idOrigem\":1,\"idPrioridade\":5,\"filtroADPesq\":\"\",\"idUnidade\":1,\"idFaseAtual\":2,\"dataHoraSolicitacao\":\"Apr 5, 2013 9:43:39 AM\",\"prazoCapturaHH\":0,\"prazoCapturaMM\":0,\"prazoHH\":0,\"prazoMM\":0,\"descricao\":\"Descri\\u0026ccedil;\\u0026atilde;o do Incidente ou Requisi\\u0026ccedil;\\u0026atilde;o\",\"resposta\":\"\",\"dataHoraInicio\":\"Apr 5, 2013 9:43:39 AM\",\"situacao\":\"EmAndamento\",\"seqReabertura\":0,\"enviaEmailCriacao\":\"S\",\"enviaEmailFinalizacao\":\"S\",\"slaACombinar\":\"S\",\"idCalendario\":1,\"tempoDecorridoHH\":0,\"tempoDecorridoMM\":0,\"situacaoSLA\":\"N\",\"descrSituacao\":\"Em andamento\",\"atrasoSLA\":0.0,\"idContrato\":1,\"solicitante\":\"Default\",\"dataHoraSolicitacaoStr\":\"05/04/2013 09:43\",\"usuarioDto\":{\"idUsuario\":3,\"idEmpregado\":3,\"idPerfilAcessoUsuario\":1,\"idEmpresa\":1,\"login\":\"consultor\",\"nomeUsuario\":\"Consultor\",\"status\":\"A\",\"grupos\":[\"SDNIVEL1\",\"SDNIVEL2\",\"SDNIVEL3Apli\",\"SDNIVEL3Infra\",\"SDNIVEL3Sist\",\"SDNIVEL3Telefonia\"],\"locale\":\"\",\"colGrupos\":[{\"idGrupo\":2,\"nome\":\"1º NÍVEL\",\"sigla\":\"SDNIVEL1\",\"serviceDesk\":\"S\"},{\"idGrupo\":3,\"nome\":\"2º NÍVEL\",\"sigla\":\"SDNIVEL2\",\"serviceDesk\":\"S\"},{\"idGrupo\":16,\"nome\":\"3º NÍVEL - Aplicação\",\"sigla\":\"SDNIVEL3Apli\",\"serviceDesk\":\"S\"},{\"idGrupo\":4,\"nome\":\"3º NÍVEL - Infraestrutura\",\"sigla\":\"SDNIVEL3Infra\",\"serviceDesk\":\"S\"},{\"idGrupo\":17,\"nome\":\"3º NÍVEL - Sistemas\",\"sigla\":\"SDNIVEL3Sist\",\"serviceDesk\":\"S\"},{\"idGrupo\":19,\"nome\":\"3º NÍVEL - Telefonia\",\"sigla\":\"SDNIVEL3Telefonia\",\"serviceDesk\":\"N\"}]},\"grupoAtual\":\"SDNIVEL1\",\"grupoNivel1\":\"SDNIVEL1\",\"acaoFluxo\":\"\",\"idTipoDemandaServico\":1,\"idServico\":4,\"itemConfiguracao\":\"\",\"messageId\":\"\",\"nomecontato\":\"Default\",\"telefonecontato\":\"3242-4433\",\"emailcontato\":\"citsmart_instalador_mysql@centralit.com.br\",\"observacao\":\"\",\"reclassificar\":\"\",\"escalar\":\"\",\"alterarSituacao\":\"\",\"detalhamentoCausa\":\"\",\"solucaoTemporaria\":\"N\",\"nomeServico\":\"Serviço 4\",\"ramal\":\"\",\"idAcordoNivelServico\":2,\"registradoPor\":\"Consultor\",\"registroexecucao\":\"\",\"impacto\":\"B\",\"urgencia\":\"B\",\"descricaoSemFormatacao\":\"Descrição do Incidente ou Requisição\",\"idResponsavel\":3,\"idGrupoAtual\":2,\"idGrupoNivel1\":2}'),(6,NULL,2,NULL,'solicitacaoServico.situacao','java.lang.String','I','N','S',NULL,NULL,NULL,'\"EmAndamento\"'),(7,NULL,2,NULL,'solicitacaoServico.grupoAtual','java.lang.String','I','N','S',NULL,NULL,NULL,'\"SDNIVEL1\"'),(8,NULL,2,NULL,'solicitacaoServico.grupoNivel1','java.lang.String','I','N','S',NULL,NULL,NULL,'\"SDNIVEL1\"'),(9,NULL,3,NULL,'solicitacaoServico','br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO','I','N','S',NULL,NULL,NULL,'{\"idSolicitacaoServico\":3,\"idContatoSolicitacaoServico\":3,\"idServicoContrato\":5,\"idSolicitante\":3,\"idOrigem\":1,\"idPrioridade\":5,\"filtroADPesq\":\"\",\"idUnidade\":1,\"idFaseAtual\":2,\"dataHoraSolicitacao\":\"Apr 5, 2013 9:46:04 AM\",\"prazoCapturaHH\":0,\"prazoCapturaMM\":0,\"prazoHH\":0,\"prazoMM\":0,\"descricao\":\"Descri\\u0026ccedil;\\u0026atilde;o do Incidente ou Requisi\\u0026ccedil;\\u0026atilde;o\",\"resposta\":\"\",\"dataHoraInicio\":\"Apr 5, 2013 9:46:04 AM\",\"situacao\":\"Cancelada\",\"seqReabertura\":0,\"enviaEmailCriacao\":\"S\",\"enviaEmailFinalizacao\":\"S\",\"slaACombinar\":\"S\",\"idCalendario\":1,\"tempoDecorridoHH\":0,\"tempoDecorridoMM\":0,\"situacaoSLA\":\"N\",\"descrSituacao\":\"Cancelada\",\"atrasoSLA\":0.0,\"idContrato\":1,\"solicitante\":\"Consultor\",\"dataHoraSolicitacaoStr\":\"05/04/2013 09:46\",\"usuarioDto\":{\"idUsuario\":2,\"idEmpregado\":2,\"idPerfilAcessoUsuario\":2,\"idEmpresa\":1,\"login\":\"default\",\"nomeUsuario\":\"Default\",\"status\":\"A\",\"grupos\":[\"SDNIVEL1\",\"SDNIVEL2\",\"SDNIVEL3Apli\",\"SDNIVEL3Infra\",\"SDNIVEL3Sist\",\"SDNIVEL3Telefonia\"],\"locale\":\"\",\"colGrupos\":[{\"idGrupo\":2,\"nome\":\"1º NÍVEL\",\"sigla\":\"SDNIVEL1\",\"serviceDesk\":\"S\"},{\"idGrupo\":3,\"nome\":\"2º NÍVEL\",\"sigla\":\"SDNIVEL2\",\"serviceDesk\":\"S\"},{\"idGrupo\":16,\"nome\":\"3º NÍVEL - Aplicação\",\"sigla\":\"SDNIVEL3Apli\",\"serviceDesk\":\"S\"},{\"idGrupo\":4,\"nome\":\"3º NÍVEL - Infraestrutura\",\"sigla\":\"SDNIVEL3Infra\",\"serviceDesk\":\"S\"},{\"idGrupo\":17,\"nome\":\"3º NÍVEL - Sistemas\",\"sigla\":\"SDNIVEL3Sist\",\"serviceDesk\":\"S\"},{\"idGrupo\":19,\"nome\":\"3º NÍVEL - Telefonia\",\"sigla\":\"SDNIVEL3Telefonia\",\"serviceDesk\":\"N\"}]},\"grupoAtual\":\"SDNIVEL2\",\"grupoNivel1\":\"SDNIVEL1\",\"acaoFluxo\":\"\",\"idTipoDemandaServico\":1,\"idServico\":5,\"itemConfiguracao\":\"\",\"messageId\":\"\",\"nomecontato\":\"Consultor\",\"telefonecontato\":\"3242-4433\",\"emailcontato\":\"citsmart_instalador_mysql@centralit.com.br\",\"observacao\":\"\",\"reclassificar\":\"\",\"escalar\":\"\",\"alterarSituacao\":\"\",\"detalhamentoCausa\":\"\",\"solucaoTemporaria\":\"N\",\"nomeServico\":\"Serviço 5\",\"ramal\":\"\",\"idAcordoNivelServico\":3,\"registradoPor\":\"Default\",\"registroexecucao\":\"\",\"impacto\":\"B\",\"urgencia\":\"B\",\"descricaoSemFormatacao\":\"Descrição do Incidente ou Requisição\",\"idResponsavel\":2,\"idGrupoAtual\":3,\"idGrupoNivel1\":2}'),(10,NULL,3,NULL,'solicitacaoServico.situacao','java.lang.String','I','N','S',NULL,NULL,NULL,'\"Cancelada\"'),(11,NULL,3,NULL,'solicitacaoServico.grupoAtual','java.lang.String','I','N','S',NULL,NULL,NULL,'\"SDNIVEL2\"'),(12,NULL,3,NULL,'solicitacaoServico.grupoNivel1','java.lang.String','I','N','S',NULL,NULL,NULL,'\"SDNIVEL1\"'),(13,NULL,4,NULL,'solicitacaoServico','br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO','I','N','S',NULL,NULL,NULL,'{\"idSolicitacaoServico\":4,\"idContatoSolicitacaoServico\":4,\"idServicoContrato\":5,\"idSolicitante\":3,\"idOrigem\":1,\"idPrioridade\":5,\"filtroADPesq\":\"\",\"idUnidade\":1,\"idFaseAtual\":2,\"dataHoraSolicitacao\":\"Apr 5, 2013 10:13:17 AM\",\"prazoCapturaHH\":0,\"prazoCapturaMM\":0,\"prazoHH\":0,\"prazoMM\":0,\"descricao\":\"Descri\\u0026ccedil;\\u0026atilde;o do Incidente ou Requisi\\u0026ccedil;\\u0026atilde;o\\u003cbr /\\u003e\",\"resposta\":\"\",\"dataHoraInicio\":\"Apr 5, 2013 10:13:17 AM\",\"situacao\":\"EmAndamento\",\"seqReabertura\":0,\"enviaEmailCriacao\":\"S\",\"enviaEmailFinalizacao\":\"S\",\"slaACombinar\":\"S\",\"idCalendario\":1,\"tempoDecorridoHH\":0,\"tempoDecorridoMM\":0,\"situacaoSLA\":\"N\",\"descrSituacao\":\"Em andamento\",\"atrasoSLA\":0.0,\"idContrato\":1,\"solicitante\":\"Consultor\",\"dataHoraSolicitacaoStr\":\"05/04/2013 10:13\",\"usuarioDto\":{\"idUsuario\":2,\"idEmpregado\":2,\"idPerfilAcessoUsuario\":2,\"idEmpresa\":1,\"login\":\"default\",\"nomeUsuario\":\"Default\",\"status\":\"A\",\"grupos\":[\"SDNIVEL1\",\"SDNIVEL2\",\"SDNIVEL3Apli\",\"SDNIVEL3Infra\",\"SDNIVEL3Sist\",\"SDNIVEL3Telefonia\"],\"locale\":\"\",\"colGrupos\":[{\"idGrupo\":2,\"nome\":\"1º NÍVEL\",\"sigla\":\"SDNIVEL1\",\"serviceDesk\":\"S\"},{\"idGrupo\":3,\"nome\":\"2º NÍVEL\",\"sigla\":\"SDNIVEL2\",\"serviceDesk\":\"S\"},{\"idGrupo\":16,\"nome\":\"3º NÍVEL - Aplicação\",\"sigla\":\"SDNIVEL3Apli\",\"serviceDesk\":\"S\"},{\"idGrupo\":4,\"nome\":\"3º NÍVEL - Infraestrutura\",\"sigla\":\"SDNIVEL3Infra\",\"serviceDesk\":\"S\"},{\"idGrupo\":17,\"nome\":\"3º NÍVEL - Sistemas\",\"sigla\":\"SDNIVEL3Sist\",\"serviceDesk\":\"S\"},{\"idGrupo\":19,\"nome\":\"3º NÍVEL - Telefonia\",\"sigla\":\"SDNIVEL3Telefonia\",\"serviceDesk\":\"N\"}]},\"grupoAtual\":\"SDNIVEL2\",\"grupoNivel1\":\"SDNIVEL1\",\"acaoFluxo\":\"\",\"idTipoDemandaServico\":1,\"idServico\":5,\"itemConfiguracao\":\"\",\"messageId\":\"\",\"nomecontato\":\"Consultor\",\"telefonecontato\":\"3242-4433\",\"emailcontato\":\"citsmart_instalador_mysql@centralit.com.br\",\"observacao\":\"\",\"reclassificar\":\"\",\"escalar\":\"\",\"alterarSituacao\":\"\",\"detalhamentoCausa\":\"\",\"solucaoTemporaria\":\"N\",\"nomeServico\":\"Serviço 5\",\"ramal\":\"\",\"idAcordoNivelServico\":3,\"registradoPor\":\"Default\",\"registroexecucao\":\"\",\"impacto\":\"B\",\"urgencia\":\"B\",\"descricaoSemFormatacao\":\"Descrição do Incidente ou Requisição\",\"idResponsavel\":2,\"idGrupoAtual\":3,\"idGrupoNivel1\":2}'),(14,NULL,4,NULL,'solicitacaoServico.situacao','java.lang.String','I','N','S',NULL,NULL,NULL,'\"EmAndamento\"'),(15,NULL,4,NULL,'solicitacaoServico.grupoAtual','java.lang.String','I','N','S',NULL,NULL,NULL,'\"SDNIVEL2\"'),(16,NULL,4,NULL,'solicitacaoServico.grupoNivel1','java.lang.String','I','N','S',NULL,NULL,NULL,'\"SDNIVEL1\"');
/*!40000 ALTER TABLE `bpm_objetoinstanciafluxo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bpm_sequenciafluxo`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bpm_sequenciafluxo` (
  `idelementoorigem` bigint(20) NOT NULL,
  `idelementodestino` bigint(20) NOT NULL,
  `idfluxo` bigint(20) NOT NULL,
  `nomeclasseorigem` varchar(100) DEFAULT NULL,
  `nomeclassedestino` varchar(100) DEFAULT NULL,
  `condicao` text,
  `idconexaoorigem` smallint(6) DEFAULT NULL,
  `idconexaodestino` smallint(6) DEFAULT NULL,
  `bordax` double DEFAULT NULL,
  `borday` double DEFAULT NULL,
  `posicaoalterada` char(1) DEFAULT NULL,
  `nome` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idelementoorigem`,`idelementodestino`),
  KEY `fk_reference_133` (`idelementodestino`),
  KEY `fk_reference_134` (`idfluxo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bpm_sequenciafluxo`
--

LOCK TABLES `bpm_sequenciafluxo` WRITE;
/*!40000 ALTER TABLE `bpm_sequenciafluxo` DISABLE KEYS */;
INSERT INTO `bpm_sequenciafluxo` VALUES (1,5,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,7,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(3,8,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(4,9,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(5,4,1,NULL,NULL,'#{solicitacaoServico}.atendida();',NULL,NULL,NULL,NULL,NULL,NULL),(5,6,1,NULL,NULL,'!#{solicitacaoServico}.atendida();',NULL,NULL,NULL,NULL,NULL,NULL),(6,2,1,NULL,NULL,'!#{solicitacaoServico}.escalada();',NULL,NULL,NULL,NULL,NULL,NULL),(6,3,1,NULL,NULL,'#{solicitacaoServico}.escalada();',NULL,NULL,NULL,NULL,NULL,NULL),(7,3,1,NULL,NULL,'!#{solicitacaoServico}.atendida();',NULL,NULL,NULL,NULL,NULL,NULL),(7,4,1,NULL,NULL,'#{solicitacaoServico}.atendida();',NULL,NULL,NULL,NULL,NULL,NULL),(8,3,1,NULL,NULL,'#{solicitacaoServico}.emAtendimento();',NULL,NULL,NULL,NULL,NULL,NULL),(8,4,1,NULL,NULL,'#{solicitacaoServico}.atendida();',NULL,NULL,NULL,NULL,NULL,NULL),(10,15,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(11,17,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(12,18,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(13,19,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(14,20,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(15,14,2,NULL,NULL,'#{solicitacaoServico}.atendida();',NULL,NULL,NULL,NULL,NULL,NULL),(15,16,2,NULL,NULL,'!#{solicitacaoServico}.atendida();',NULL,NULL,NULL,NULL,NULL,NULL),(16,11,2,NULL,NULL,'!#{solicitacaoServico}.escalada();',NULL,NULL,NULL,NULL,NULL,NULL),(16,13,2,NULL,NULL,'#{solicitacaoServico}.escalada();',NULL,NULL,NULL,NULL,NULL,NULL),(17,13,2,NULL,NULL,'!#{solicitacaoServico}.atendida();',NULL,NULL,NULL,NULL,NULL,NULL),(17,14,2,NULL,NULL,'#{solicitacaoServico}.atendida();',NULL,NULL,NULL,NULL,NULL,NULL),(18,11,2,NULL,NULL,'!#{solicitacaoServico}.atendida();',NULL,NULL,NULL,NULL,NULL,NULL),(18,14,2,NULL,NULL,'#{solicitacaoServico}.atendida();',NULL,NULL,NULL,NULL,NULL,NULL),(19,12,2,NULL,NULL,'#{solicitacaoServico}.atendida();',NULL,NULL,NULL,NULL,NULL,NULL),(19,13,2,NULL,NULL,'#{solicitacaoServico}.emAtendimento();',NULL,NULL,NULL,NULL,NULL,NULL),(21,25,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(22,27,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(23,28,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(24,29,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(25,24,3,NULL,NULL,'#{solicitacaoServico}.atendida();',NULL,NULL,NULL,NULL,NULL,NULL),(25,26,3,NULL,NULL,'!#{solicitacaoServico}.atendida();',NULL,NULL,NULL,NULL,NULL,NULL),(26,22,3,NULL,NULL,'!#{solicitacaoServico}.escalada();',NULL,NULL,NULL,NULL,NULL,NULL),(26,23,3,NULL,NULL,'#{solicitacaoServico}.escalada();',NULL,NULL,NULL,NULL,NULL,NULL),(27,23,3,NULL,NULL,'!#{solicitacaoServico}.atendida();',NULL,NULL,NULL,NULL,NULL,NULL),(27,24,3,NULL,NULL,'#{solicitacaoServico}.atendida();',NULL,NULL,NULL,NULL,NULL,NULL),(28,23,3,NULL,NULL,'#{solicitacaoServico}.emAtendimento();',NULL,NULL,NULL,NULL,NULL,NULL),(28,24,3,NULL,NULL,'#{solicitacaoServico}.atendida();',NULL,NULL,NULL,NULL,NULL,NULL),(30,35,4,NULL,NULL,'',1,3,130.5,53.5,'N',''),(31,39,4,NULL,NULL,'',2,0,359,308.5,'N',''),(32,37,4,NULL,NULL,'',1,3,502.5,452.25,'N',''),(33,38,4,NULL,NULL,'',3,1,618,254.75,'N',''),(34,40,4,NULL,NULL,'',1,3,699,53.75,'N',''),(35,34,4,NULL,NULL,'#{solicitacaoServico}.atendida();',1,3,369,53.75,'N','atendida'),(35,36,4,NULL,NULL,'!#{solicitacaoServico}.atendida();',2,0,224.5,115,'N','não atendida'),(36,31,4,NULL,NULL,'!#{solicitacaoServico}.escalada();',1,0,359,177,'S','não direcionada'),(36,32,4,NULL,NULL,'#{solicitacaoServico}.escalada();',2,3,223,452,'S','direcionada'),(37,32,4,NULL,NULL,'#{solicitacaoServico}.emAtendimento();',2,2,597,498,'S','não atendida'),(37,33,4,NULL,NULL,'#{solicitacaoServico}.atendida();',1,2,723,452,'S','atendida'),(38,31,4,NULL,NULL,'!#{solicitacaoServico}.atendida();',3,1,485,255.25,'N','não atendida'),(38,34,4,NULL,NULL,'#{solicitacaoServico}.atendida();',0,2,562.5,160,'N','atendida'),(39,32,4,NULL,NULL,'#{solicitacaoServico}.emAtendimento();',2,0,359,397,'S','não atendida'),(39,34,4,NULL,NULL,'#{solicitacaoServico}.atendida();',1,3,482,351,'S','atendida'),(41,46,5,NULL,NULL,'',1,3,130.5,53.5,'N',''),(42,50,5,NULL,NULL,'',2,0,359,308.5,'N',''),(43,48,5,NULL,NULL,'',1,3,502.5,452.25,'N',''),(44,49,5,NULL,NULL,'',3,1,617.5,255.25,'N',''),(45,51,5,NULL,NULL,'',1,3,699,53.75,'N',''),(46,45,5,NULL,NULL,'#{solicitacaoServico}.atendida();',1,3,369,53.75,'N','atendida'),(46,47,5,NULL,NULL,'!#{solicitacaoServico}.atendida();',2,0,224.5,115,'N','não atendida'),(47,42,5,NULL,NULL,'!#{solicitacaoServico}.escalada();',1,0,359,177,'S','não direcionada'),(47,43,5,NULL,NULL,'#{solicitacaoServico}.escalada();',2,3,223,452,'S','direcionada'),(48,43,5,NULL,NULL,'#{solicitacaoServico}.emAtendimento();',2,2,597,498,'S','não atendida'),(48,44,5,NULL,NULL,'#{solicitacaoServico}.atendida();',1,2,723,452,'S','atendida'),(49,42,5,NULL,NULL,'!#{solicitacaoServico}.atendida();',3,1,485,255.25,'N','não atendida'),(49,45,5,NULL,NULL,'#{solicitacaoServico}.atendida();',0,2,562.5,160,'N','atendida'),(50,43,5,NULL,NULL,'#{solicitacaoServico}.emAtendimento();',2,0,359,397,'S','não atendida'),(50,45,5,NULL,NULL,'#{solicitacaoServico}.atendida();',1,3,482,351,'S','atendida'),(52,57,8,NULL,NULL,'',1,3,130.5,53.5,'N',''),(53,61,8,NULL,NULL,'',2,0,359,308.5,'N',''),(54,59,8,NULL,NULL,'',1,3,502.5,452.25,'N',''),(55,60,8,NULL,NULL,'',3,1,617.5,255.25,'N',''),(56,62,8,NULL,NULL,'',1,3,699,53.75,'N',''),(57,56,8,NULL,NULL,'#{solicitacaoServico}.atendida();',1,3,369,53.75,'N','atendida'),(57,58,8,NULL,NULL,'!#{solicitacaoServico}.atendida();',2,0,224.5,115,'N','não atendida'),(58,53,8,NULL,NULL,'!#{solicitacaoServico}.escalada();',1,0,359,177,'S','não direcionada'),(58,54,8,NULL,NULL,'#{solicitacaoServico}.escalada();',2,3,223,452,'S','direcionada'),(59,54,8,NULL,NULL,'#{solicitacaoServico}.emAtendimento();',2,2,597,498,'S','não atendida'),(59,55,8,NULL,NULL,'#{solicitacaoServico}.atendida();',1,2,723,452,'S','atendida'),(60,53,8,NULL,NULL,'!#{solicitacaoServico}.atendida();',3,1,485,255.25,'N','não atendida'),(60,56,8,NULL,NULL,'#{solicitacaoServico}.atendida();',0,2,562.5,160,'N','atendida'),(61,54,8,NULL,NULL,'#{solicitacaoServico}.emAtendimento();',2,0,359,397,'S','não atendida'),(61,56,8,NULL,NULL,'#{solicitacaoServico}.atendida();',1,3,482,351,'S','atendida'),(184,186,9,NULL,NULL,'',2,0,110,128,'N',NULL),(185,189,9,NULL,NULL,'',1,3,880.5,200.9,'N',NULL),(186,187,9,NULL,NULL,'',1,3,217.5,200.5,'N',NULL),(187,188,9,NULL,NULL,'',1,3,438.5,200.5,'N',NULL),(188,185,9,NULL,NULL,'',1,3,662.5,200.5,'N',NULL),(404,405,42,NULL,NULL,'',1,3,296,212.75,'N',''),(405,406,42,NULL,NULL,'',1,3,628,270.5,'N',''),(406,407,42,NULL,NULL,'',1,3,936.5,285.5,'N',NULL),(407,408,42,NULL,NULL,'',1,3,1240,299.75,'N',NULL),(409,410,43,NULL,NULL,'',1,3,296,212.75,'N',''),(410,411,43,NULL,NULL,'',1,3,628,270.5,'N',''),(411,412,43,NULL,NULL,'',1,3,936.5,285.5,'N',NULL),(412,413,43,NULL,NULL,'',1,3,1240,299.75,'N',NULL),(414,415,44,NULL,NULL,'',2,0,1143,341.5,'N',NULL),(415,416,44,NULL,NULL,'',1,3,994,213.5,'N',NULL),(416,417,44,NULL,NULL,'',1,3,702.5,201,'N',NULL),(417,418,44,NULL,NULL,'',1,3,438.5,200.5,'N',NULL),(418,419,44,NULL,NULL,'',1,3,231,202,'N',NULL),(419,420,44,NULL,NULL,'',2,0,123.5,129.5,'N',NULL);
/*!40000 ALTER TABLE `bpm_sequenciafluxo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bpm_tipofluxo`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bpm_tipofluxo` (
  `idtipofluxo` int(11) NOT NULL,
  `nomefluxo` varchar(70) NOT NULL,
  `descricao` text,
  `nomeclassefluxo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idtipofluxo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bpm_tipofluxo`
--

LOCK TABLES `bpm_tipofluxo` WRITE;
/*!40000 ALTER TABLE `bpm_tipofluxo` DISABLE KEYS */;
INSERT INTO `bpm_tipofluxo` VALUES (1,'SolicitacaoServico',NULL,NULL),(6,'RequisicaoMudancaPadrao','Requisição de Mudança Padrão','br.com.centralit.citcorpore.bpm.negocio.ExecucaoMudanca'),(7,'RequisicaoMudancaEmergencial','Requisição Mudança Emergencial','br.com.centralit.citcorpore.bpm.negocio.ExecucaoMudanca'),(8,'RequisicaodeMudancaNormal','Requisição de Mudança Normal','br.com.centralit.citcorpore.bpm.negocio.ExecucaoMudanca');
/*!40000 ALTER TABLE `bpm_tipofluxo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `calendario`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `calendario` (
  `idcalendario` int(11) NOT NULL,
  `descricao` varchar(70) DEFAULT NULL,
  `consideraferiados` char(1) DEFAULT NULL,
  `idjornadaseg` int(11) DEFAULT NULL,
  `idjornadater` int(11) DEFAULT NULL,
  `idjornadaqua` int(11) DEFAULT NULL,
  `idjornadaqui` int(11) DEFAULT NULL,
  `idjornadasex` int(11) DEFAULT NULL,
  `idjornadasab` int(11) DEFAULT NULL,
  `idjornadadom` int(11) DEFAULT NULL,
  PRIMARY KEY (`idcalendario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calendario`
--

LOCK TABLES `calendario` WRITE;
/*!40000 ALTER TABLE `calendario` DISABLE KEYS */;
INSERT INTO `calendario` VALUES (1,'Padrão','S',1,1,1,1,1,NULL,NULL);
/*!40000 ALTER TABLE `calendario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campoobjrelacionado`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campoobjrelacionado` (
  `idcampoobjrelacionado` bigint(20) NOT NULL,
  `idrelacobjetonegocio` bigint(20) NOT NULL,
  `idcamposobjetonegociopai` bigint(20) NOT NULL,
  `idcamposobjetonegociofilho` bigint(20) NOT NULL,
  `situacao` char(1) NOT NULL,
  PRIMARY KEY (`idcampoobjrelacionado`),
  KEY `fk_reference_80` (`idrelacobjetonegocio`),
  KEY `fk_reference_81` (`idcamposobjetonegociopai`),
  KEY `fk_reference_82` (`idcamposobjetonegociofilho`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campoobjrelacionado`
--

LOCK TABLES `campoobjrelacionado` WRITE;
/*!40000 ALTER TABLE `campoobjrelacionado` DISABLE KEYS */;
/*!40000 ALTER TABLE `campoobjrelacionado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `camposobjetonegocio`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `camposobjetonegocio` (
  `idcamposobjetonegocio` bigint(20) NOT NULL,
  `idobjetonegocio` bigint(20) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `nomedb` varchar(100) NOT NULL,
  `pk` char(1) NOT NULL,
  `sequence` char(1) NOT NULL,
  `unico` char(1) NOT NULL,
  `tipodb` char(20) NOT NULL,
  `obrigatorio` char(1) NOT NULL,
  `situacao` char(1) NOT NULL,
  `precisiondb` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idcamposobjetonegocio`),
  KEY `fk_reference_72` (`idobjetonegocio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `camposobjetonegocio`
--

LOCK TABLES `camposobjetonegocio` WRITE;
/*!40000 ALTER TABLE `camposobjetonegocio` DISABLE KEYS */;
/*!40000 ALTER TABLE `camposobjetonegocio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `caracteristica`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `caracteristica` (
  `idcaracteristica` int(11) NOT NULL,
  `idempresa` int(11) NOT NULL,
  `nomecaracteristica` varchar(255) NOT NULL,
  `tagcaracteristica` varchar(255) DEFAULT NULL,
  `descricao` varchar(4000) DEFAULT NULL,
  `tipo` char(2) DEFAULT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  `sistema` char(1) DEFAULT NULL,
  PRIMARY KEY (`idcaracteristica`),
  KEY `INDEX_CEMPRESA` (`idempresa`),
  KEY `INDEX_CNOMECARACTERISTICA` (`nomecaracteristica`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='caracteristica';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `caracteristica`
--

LOCK TABLES `caracteristica` WRITE;
/*!40000 ALTER TABLE `caracteristica` DISABLE KEYS */;
/*!40000 ALTER TABLE `caracteristica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cargos`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cargos` (
  `idcargo` int(11) NOT NULL,
  `nomecargo` varchar(256) NOT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  PRIMARY KEY (`idcargo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='{\rtf1ansiansicpg1252uc1deff0deflang1046deflangfe1046{';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cargos`
--

LOCK TABLES `cargos` WRITE;
/*!40000 ALTER TABLE `cargos` DISABLE KEYS */;
INSERT INTO `cargos` VALUES (1,'Administrador do Sistema','2012-01-01',NULL),(2,'Default','2012-01-01',NULL),(3,'Coordenador HelpDesk','2012-07-13',NULL);
/*!40000 ALTER TABLE `cargos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `catalogoservico`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `catalogoservico` (
  `idcatalogoservico` int(11) NOT NULL DEFAULT '0',
  `idcontrato` int(11) DEFAULT NULL,
  `datainicio` date DEFAULT NULL,
  `datafim` date DEFAULT NULL,
  `obs` text,
  `nomeservico` char(150) DEFAULT NULL,
  `titulocatalogoservico` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`idcatalogoservico`),
  UNIQUE KEY `ak_key_1_catalago` (`idcatalogoservico`),
  KEY `fk_infocata_reference_contratos` (`idcontrato`),
  CONSTRAINT `fk_infocata_reference_contratos` FOREIGN KEY (`idcontrato`) REFERENCES `contratos` (`idcontrato`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `catalogoservico`
--

LOCK TABLES `catalogoservico` WRITE;
/*!40000 ALTER TABLE `catalogoservico` DISABLE KEYS */;
/*!40000 ALTER TABLE `catalogoservico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoriagaleriaimagem`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categoriagaleriaimagem` (
  `idcategoriagaleriaimagem` int(11) NOT NULL,
  `nomecategoria` varchar(70) NOT NULL,
  PRIMARY KEY (`idcategoriagaleriaimagem`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='categoriagaleriaimagem';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoriagaleriaimagem`
--

LOCK TABLES `categoriagaleriaimagem` WRITE;
/*!40000 ALTER TABLE `categoriagaleriaimagem` DISABLE KEYS */;
/*!40000 ALTER TABLE `categoriagaleriaimagem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoriamudanca`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categoriamudanca` (
  `idcategoriamudanca` int(11) NOT NULL,
  `idtipofluxo` int(11) DEFAULT NULL,
  `idmodeloemailcriacao` int(11) DEFAULT NULL,
  `idmodeloemailfinalizacao` int(11) DEFAULT NULL,
  `idmodeloemailacoes` int(11) DEFAULT NULL,
  `idgruponivel1` int(11) DEFAULT NULL,
  `idgrupoexecutor` int(11) DEFAULT NULL,
  `idcalendario` int(11) DEFAULT NULL,
  `idcategoriamudancapai` int(11) DEFAULT NULL,
  `nomecategoria` varchar(100) DEFAULT NULL,
  `datainicio` date DEFAULT NULL,
  `datafim` date DEFAULT NULL,
  PRIMARY KEY (`idcategoriamudanca`),
  KEY `fk_categori_reference_bpm_tipo` (`idtipofluxo`),
  KEY `fk_categori_reference_modelose` (`idmodeloemailcriacao`),
  KEY `fk_categori_reference_modelose_01` (`idmodeloemailfinalizacao`),
  KEY `fk_categori_reference_modelose_02` (`idmodeloemailacoes`),
  KEY `fk_categori_reference_grupo` (`idgruponivel1`),
  KEY `fk_categori_reference_grupo_01` (`idgrupoexecutor`),
  KEY `fk_categori_reference_calendar` (`idcalendario`),
  CONSTRAINT `fk_categori_reference_bpm_tipo` FOREIGN KEY (`idtipofluxo`) REFERENCES `bpm_tipofluxo` (`idtipofluxo`),
  CONSTRAINT `fk_categori_reference_calendar` FOREIGN KEY (`idcalendario`) REFERENCES `calendario` (`idcalendario`),
  CONSTRAINT `fk_categori_reference_grupo` FOREIGN KEY (`idgruponivel1`) REFERENCES `grupo` (`idgrupo`),
  CONSTRAINT `fk_categori_reference_grupo_01` FOREIGN KEY (`idgrupoexecutor`) REFERENCES `grupo` (`idgrupo`),
  CONSTRAINT `fk_categori_reference_modelose` FOREIGN KEY (`idmodeloemailcriacao`) REFERENCES `modelosemails` (`idmodeloemail`),
  CONSTRAINT `fk_categori_reference_modelose_01` FOREIGN KEY (`idmodeloemailfinalizacao`) REFERENCES `modelosemails` (`idmodeloemail`),
  CONSTRAINT `fk_categori_reference_modelose_02` FOREIGN KEY (`idmodeloemailacoes`) REFERENCES `modelosemails` (`idmodeloemail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoriamudanca`
--

LOCK TABLES `categoriamudanca` WRITE;
/*!40000 ALTER TABLE `categoriamudanca` DISABLE KEYS */;
/*!40000 ALTER TABLE `categoriamudanca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoriaocorrencia`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categoriaocorrencia` (
  `idcategoriaocorrencia` int(11) NOT NULL,
  `nome` varchar(20) NOT NULL,
  `dataInicio` date NOT NULL,
  `dataFim` date DEFAULT NULL,
  PRIMARY KEY (`idcategoriaocorrencia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoriaocorrencia`
--

LOCK TABLES `categoriaocorrencia` WRITE;
/*!40000 ALTER TABLE `categoriaocorrencia` DISABLE KEYS */;
/*!40000 ALTER TABLE `categoriaocorrencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoriapost`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categoriapost` (
  `idcategoriapost` int(11) NOT NULL,
  `idcategoriapostPai` int(11) DEFAULT NULL,
  `nomecategoria` varchar(255) DEFAULT NULL,
  `datainicio` date DEFAULT NULL,
  `datafim` date DEFAULT NULL,
  PRIMARY KEY (`idcategoriapost`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoriapost`
--

LOCK TABLES `categoriapost` WRITE;
/*!40000 ALTER TABLE `categoriapost` DISABLE KEYS */;
/*!40000 ALTER TABLE `categoriapost` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoriaproblema`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categoriaproblema` (
  `idcategoriaproblema` int(11) NOT NULL,
  `cat_idcategoriaproblema` int(11) DEFAULT NULL,
  `idcategoriaproblemapai` int(11) NOT NULL,
  `nomecategoria` int(11) NOT NULL,
  PRIMARY KEY (`idcategoriaproblema`),
  KEY `fk_reference_617` (`cat_idcategoriaproblema`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoriaproblema`
--

LOCK TABLES `categoriaproblema` WRITE;
/*!40000 ALTER TABLE `categoriaproblema` DISABLE KEYS */;
/*!40000 ALTER TABLE `categoriaproblema` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoriaproduto`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categoriaproduto` (
  `idcategoria` int(11) NOT NULL,
  `idcategoriapai` int(11) DEFAULT NULL,
  `nomecategoria` varchar(100) NOT NULL,
  `situacao` char(1) NOT NULL,
  `pesocotacaopreco` int(11) DEFAULT NULL,
  `pesocotacaoprazoentrega` int(11) DEFAULT NULL,
  `pesocotacaoprazopagto` int(11) DEFAULT NULL,
  `pesocotacaotaxajuros` int(11) DEFAULT NULL,
  `pesocotacaoprazogarantia` int(11) DEFAULT NULL,
  PRIMARY KEY (`idcategoria`),
  KEY `fk_reference_670` (`idcategoriapai`),
  CONSTRAINT `fk_reference_670` FOREIGN KEY (`idcategoriapai`) REFERENCES `categoriaproduto` (`idcategoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoriaproduto`
--

LOCK TABLES `categoriaproduto` WRITE;
/*!40000 ALTER TABLE `categoriaproduto` DISABLE KEYS */;
/*!40000 ALTER TABLE `categoriaproduto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoriaquestionario`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categoriaquestionario` (
  `idcategoriaquestionario` int(11) NOT NULL,
  `nomecategoriaquestionario` varchar(50) NOT NULL,
  `idempresa` int(11) NOT NULL,
  `compartilhada` char(1) DEFAULT 'N',
  PRIMARY KEY (`idcategoriaquestionario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='categoriaquestionario';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoriaquestionario`
--

LOCK TABLES `categoriaquestionario` WRITE;
/*!40000 ALTER TABLE `categoriaquestionario` DISABLE KEYS */;
INSERT INTO `categoriaquestionario` VALUES (1,'CONTRATOS',1,'N');
/*!40000 ALTER TABLE `categoriaquestionario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoriaservico`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categoriaservico` (
  `idcategoriaservico` int(11) NOT NULL,
  `idcategoriaservicopai` int(11) DEFAULT NULL,
  `idempresa` int(11) NOT NULL,
  `nomecategoriaservico` varchar(100) NOT NULL,
  `datainicio` date DEFAULT '2012-04-02',
  `datafim` date DEFAULT NULL,
  `nomeCategoriaServicoConcatenado` varchar(1024) DEFAULT NULL,
  `nomeCatServicoConcatenado` varchar(520) DEFAULT NULL,
  PRIMARY KEY (`idcategoriaservico`),
  KEY `INDEX_CATEGORIASERVICOPAI` (`idcategoriaservicopai`),
  KEY `INDEX_CATEMPRESA` (`idempresa`),
  KEY `index_nomecateg` (`nomeCategoriaServicoConcatenado`(255))
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='categoriaservico';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoriaservico`
--

LOCK TABLES `categoriaservico` WRITE;
/*!40000 ALTER TABLE `categoriaservico` DISABLE KEYS */;
INSERT INTO `categoriaservico` VALUES (1,NULL,1,'Categoria de Serviço 1','2013-04-02',NULL,'Categoria de Serviço 1',NULL);
/*!40000 ALTER TABLE `categoriaservico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoriasolucao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categoriasolucao` (
  `idcategoriasolucao` int(11) NOT NULL,
  `idcategoriasolucaopai` int(11) DEFAULT NULL,
  `descricaocategoriasolucao` varchar(100) NOT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  `deleted` char(1) DEFAULT NULL,
  PRIMARY KEY (`idcategoriasolucao`),
  KEY `fk_reference_57` (`idcategoriasolucaopai`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoriasolucao`
--

LOCK TABLES `categoriasolucao` WRITE;
/*!40000 ALTER TABLE `categoriasolucao` DISABLE KEYS */;
INSERT INTO `categoriasolucao` VALUES (1,NULL,'Software','2012-01-01',NULL,'n'),(2,1,'Ajuste de configuração de software','2012-01-01',NULL,'n'),(3,1,'Nova Instalação','2012-01-01',NULL,NULL),(4,1,'Remoção de arquivos temporários','2012-01-01',NULL,'n'),(5,1,'Ajuste no sistema operacional','2012-01-01',NULL,'n'),(6,NULL,'Hardware','2012-01-01',NULL,'y'),(7,6,'Reparo no hardware','2012-01-01',NULL,'n'),(8,6,'Substituição de hardware','2012-01-01',NULL,'n'),(9,6,'Manutenção preventiva','2012-01-01',NULL,'n'),(10,NULL,'Ajuste na configuração do aplicativo','2012-01-01',NULL,NULL),(11,NULL,'Ajustes nas configurações do sistema','2012-01-01',NULL,NULL),(12,NULL,'Atualização no sistema operacional','2012-01-01',NULL,NULL),(13,NULL,'Aplicação de service pack','2012-01-01',NULL,NULL),(14,NULL,'Ajuste na configuração do browser','2012-01-01',NULL,NULL),(15,NULL,'Novas parametrizações','2012-01-01',NULL,NULL),(16,NULL,'Reconfiguração de parâmetros de rede','2012-01-01',NULL,NULL),(17,NULL,'Liberação de regras no firewall','2012-01-01',NULL,NULL),(18,NULL,'Alteração de regras no firewall','2012-01-01',NULL,NULL),(19,NULL,'Exclusão de regras no firewall','2012-01-01',NULL,NULL),(20,NULL,'Liberação de página no proxy','2012-01-01',NULL,NULL),(21,NULL,'Criação de usuários','2012-01-01',NULL,NULL),(22,NULL,'Mudança de perfil','2012-01-01',NULL,NULL),(23,NULL,'Backup e Restore','2012-01-01',NULL,NULL),(24,NULL,'Software','2012-01-01',NULL,'y');
/*!40000 ALTER TABLE `categoriasolucao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `causaincidente`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `causaincidente` (
  `idcausaincidente` int(11) NOT NULL,
  `idcausaincidentepai` int(11) DEFAULT NULL,
  `descricaocausa` varchar(100) NOT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  `deleted` char(1) DEFAULT NULL,
  PRIMARY KEY (`idcausaincidente`),
  KEY `fk_reference_56` (`idcausaincidentepai`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `causaincidente`
--

LOCK TABLES `causaincidente` WRITE;
/*!40000 ALTER TABLE `causaincidente` DISABLE KEYS */;
INSERT INTO `causaincidente` VALUES (1,NULL,'Software','2012-01-01',NULL,NULL),(2,1,'Instalação ineficaz','2012-01-01',NULL,NULL),(3,1,'Configuração','2012-01-01',NULL,NULL),(4,1,'Falta de Treinamento','2012-01-01',NULL,NULL),(5,NULL,'Hardware','2012-01-01',NULL,NULL),(6,5,'Defeito','2012-01-01',NULL,NULL),(7,5,'Configuração','2012-01-01',NULL,NULL),(8,5,'Uso Indevido','2012-01-01',NULL,NULL),(9,5,'Outros','2012-01-01',NULL,'n'),(10,1,'Erro no aplicativo','2012-01-01',NULL,NULL),(11,1,'Lentidão','2012-01-01',NULL,NULL),(12,1,'Erro na configuração','2012-01-01',NULL,NULL),(13,5,'Erro na configuração','2012-01-01',NULL,'y'),(14,9,'Mudança não planejada','2012-01-01',NULL,NULL);
/*!40000 ALTER TABLE `causaincidente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `centroresultado`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `centroresultado` (
  `idcentroresultado` int(11) NOT NULL,
  `codigocentroresultado` varchar(25) NOT NULL,
  `nomecentroresultado` varchar(200) NOT NULL,
  `idcentroresultadopai` int(11) DEFAULT NULL,
  `permiterequisicaoproduto` char(1) DEFAULT NULL,
  `situacao` char(1) NOT NULL,
  PRIMARY KEY (`idcentroresultado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `centroresultado`
--

LOCK TABLES `centroresultado` WRITE;
/*!40000 ALTER TABLE `centroresultado` DISABLE KEYS */;
/*!40000 ALTER TABLE `centroresultado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cidades`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cidades` (
  `idcidade` int(11) NOT NULL,
  `nomecidade` varchar(45) NOT NULL,
  `iduf` int(11) NOT NULL,
  PRIMARY KEY (`idcidade`),
  KEY `fk_cidades_1` (`iduf`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cidades`
--

LOCK TABLES `cidades` WRITE;
/*!40000 ALTER TABLE `cidades` DISABLE KEYS */;
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0001, 'Acrelândia', 09);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0002, 'Assis Brasil', 09);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0003, 'Brasiléia' , 09);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0004, 'Bujari', 09 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0005, 'Capixaba', 09 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0006, 'Cruzeiro do Sul', 09 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0007, 'Epitaciolândia' , 09);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0008, 'Feijó', 09);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0009, 'Jordão', 09 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0010, 'Mâncio Lima' , 09 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0011, 'Manoel Urbano', 09 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0012, 'Marechal Thaumaturgo' , 09);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0013, 'Plácido de Castro', 09);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0014, 'Porto Acre', 09);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0015, 'Porto Walter', 09);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0016, 'Rio Branco', 09);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0017, 'Rodrigues Alves', 09 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0018, 'Santa Rosa do Purus', 09 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0019, 'Sena Madureira' , 09);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0020, 'Senador Guiomard' , 09);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0021, 'Tarauacá', 09 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0022, 'Xapuri', 09 );

INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0023, 'Água Branca' , 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0024, 'Anadia', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0025, 'Arapiraca' , 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0026, 'Atalaia' , 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0027, 'Barra de Santo Antônio' , 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0028, 'Barra de São Miguel', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0029, 'Batalha' , 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0030, 'Belém', 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0031, 'Belo Monte', 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0032, 'Boca da Mata', 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0033, 'Branquinha', 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0034, 'Cacimbinhas' , 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0035, 'Cajueiro', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0036, 'Campestre' , 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0037, 'Campo Alegre', 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0038, 'Campo Grande', 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0039, 'Canapi', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0040, 'Capela', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0041, 'Carneiros' , 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0042, 'Chã Preta' , 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0043, 'Coité do Nóia', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0044, 'Colônia Leopoldina', 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0045, 'Coqueiro Seco', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0046, 'Coruripe', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0047, 'Craíbas' , 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0048, 'Delmiro Gouveia', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0049, 'Dois Riachos', 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0050, 'Estrela de Alagoas', 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0051, 'Feira Grande', 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0052, 'Feliz Deserto', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0053, 'Flexeiras' , 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0054, 'Girau do Ponciano', 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0055, 'Ibateguara', 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0056, 'Igaci', 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0057, 'Igreja Nova' , 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0058, 'Inhapi', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0059, 'Jacaré dos Homens', 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0060, 'Jacuípe' , 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0061, 'Japaratinga' , 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0062, 'Jaramataia', 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0063, 'Joaquim Gomes', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0064, 'Jundiá', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0065, 'Junqueiro' , 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0066, 'Lagoa da Canoa' , 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0067, 'Limoeiro de Anadia', 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0068, 'Maceió', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0069, 'Major Isidoro', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0070, 'Mar Vermelho', 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0071, 'Maragogi', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0072, 'Maravilha' , 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0073, 'Marechal Deodoro' , 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0074, 'Maribondo' , 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0075, 'Mata Grande' , 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0076, 'Matriz de Camaragibe' , 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0077, 'Messias' , 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0078, 'Minador do Negrão', 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0079, 'Monteirópolis', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0080, 'Murici', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0081, 'Novo Lino' , 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0082, 'Olho d`Água das Flores' , 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0083, 'Olho d`Água do Casado', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0084, 'Olho d`Água Grande', 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0085, 'Olivença', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0086, 'Ouro Branco' , 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0087, 'Palestina' , 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0088, 'Palmeira dos Índios', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0089, 'Pão de Açúcar', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0090, 'Pariconha' , 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0091, 'Paripueira', 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0092, 'Passo de Camaragibe', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0093, 'Paulo Jacinto', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0094, 'Penedo', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0095, 'Piaçabuçu' , 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0096, 'Pilar', 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0097, 'Pindoba' , 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0098, 'Piranhas', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0099, 'Poço das Trincheiras' , 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0100, 'Porto Calvo' , 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0101, 'Porto de Pedras', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0102, 'Porto Real do Colégio', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0103, 'Quebrangulo' , 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0104, 'Rio Largo' , 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0105, 'Roteiro' , 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0106, 'Santa Luzia do Norte' , 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0107, 'Santana do Ipanema', 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0108, 'Santana do Mundaú', 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0109, 'São Brás', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0110, 'São José da Laje' , 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0111, 'São José da Tapera', 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0112, 'São Luís do Quitunde' , 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0113, 'São Miguel dos Campos', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0114, 'São Miguel dos Milagres', 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0115, 'São Sebastião', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0116, 'Satuba', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0117, 'Senador Rui Palmeira' , 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0118, 'Tanque d`Arca', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0119, 'Taquarana' , 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0120, 'Teotônio Vilela', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0121, 'Traipu', 10 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0122, 'União dos Palmares', 10);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0123, 'Viçosa', 10 );

INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0124, 'Amapá', 11);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0125, 'Calçoene', 11 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0126, 'Cutias', 11 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0127, 'Ferreira Gomes' , 11);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0128, 'Itaubal' , 11);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0129, 'Laranjal do Jari' , 11);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0130, 'Macapá', 11 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0131, 'Mazagão' , 11);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0132, 'Oiapoque', 11 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0133, 'Pedra Branca do Amaparí', 11);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0134, 'Porto Grande', 11);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0135, 'Pracuúba', 11 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0136, 'Santana' , 11);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0137, 'Serra do Navio' , 11);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0138, 'Tartarugalzinho', 11 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0139, 'Vitória do Jari', 11 );

INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0140, 'Alvarães', 12 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0141, 'Amaturá' , 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0142, 'Anamã' , 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0143, 'Anori', 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0144, 'Apuí' , 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0145, 'Atalaia do Norte' , 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0146, 'Autazes' , 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0147, 'Barcelos', 12 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0148, 'Barreirinha' , 12 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0149, 'Benjamin Constant', 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0150, 'Beruri', 12 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0151, 'Boa Vista do Ramos', 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0152, 'Boca do Acre', 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0153, 'Borba', 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0154, 'Caapiranga', 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0155, 'Canutama', 12 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0156, 'Carauari', 12 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0157, 'Careiro' , 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0158, 'Careiro da Várzea', 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0159, 'Coari', 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0160, 'Codajás' , 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0161, 'Eirunepé', 12 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0162, 'Envira', 12 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0163, 'Fonte Boa' , 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0164, 'Guajará' , 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0165, 'Humaitá' , 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0166, 'Ipixuna' , 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0167, 'Iranduba', 12 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0168, 'Itacoatiara' , 12 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0169, 'Itamarati' , 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0170, 'Itapiranga', 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0171, 'Japurá', 12 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0172, 'Juruá', 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0173, 'Jutaí', 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0174, 'Lábrea', 12 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0175, 'Manacapuru', 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0176, 'Manaquiri' , 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0177, 'Manaus', 12 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0178, 'Manicoré', 12 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0179, 'Maraã', 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0180, 'Maués', 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0181, 'Nhamundá', 12 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0182, 'Nova Olinda do Norte' , 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0183, 'Novo Airão', 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0184, 'Novo Aripuanã', 12 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0185, 'Parintins' , 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0186, 'Pauini', 12 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0187, 'Presidente Figueiredo', 12 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0188, 'Rio Preto da Eva' , 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0189, 'Santa Isabel do Rio Negro', 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0190, 'Santo Antônio do Içá' , 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0191, 'São Gabriel da Cachoeira' , 12 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0192, 'São Paulo de Olivença', 12 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0193, 'São Sebastião do Uatumã', 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0194, 'Silves', 12 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0195, 'Tabatinga' , 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0196, 'Tapauá', 12 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0197, 'Tefé' , 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0198, 'Tonantins' , 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0199, 'Uarini', 12 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0200, 'Urucará' , 12);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0201, 'Urucurituba' , 12 );

INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0202, 'Abaíra', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0203, 'Abaré', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0204, 'Acajutiba' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0205, 'Adustina', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0206, 'Água Fria' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0207, 'Aiquara' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0208, 'Alagoinhas', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0209, 'Alcobaça', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0210, 'Almadina', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0211, 'Amargosa', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0212, 'Amélia Rodrigues' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0213, 'América Dourada', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0214, 'Anagé', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0215, 'Andaraí' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0216, 'Andorinha' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0217, 'Angical' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0218, 'Anguera' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0219, 'Antas', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0220, 'Antônio Cardoso', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0221, 'Antônio Gonçalves', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0222, 'Aporá', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0223, 'Apuarema', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0224, 'Araças', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0225, 'Aracatu' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0226, 'Araci', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0227, 'Aramari' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0228, 'Arataca' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0229, 'Aratuípe', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0230, 'Aurelino Leal', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0231, 'Baianópolis' , 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0232, 'Baixa Grande', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0233, 'Banzaê', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0234, 'Barra', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0235, 'Barra da Estiva', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0236, 'Barra do Choça' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0237, 'Barra do Mendes', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0238, 'Barra do Rocha' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0239, 'Barreiras' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0240, 'Barro Alto', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0241, 'Belmonte', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0242, 'Belo Campo', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0243, 'Biritinga' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0244, 'Boa Nova', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0245, 'Boa Vista do Tupim', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0246, 'Bom Jesus da Lapa', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0247, 'Bom Jesus da Serra', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0248, 'Boninal' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0249, 'Bonito', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0250, 'Boquira' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0251, 'Botuporã', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0252, 'Brejões' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0253, 'Brejolândia' , 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0254, 'Brotas de Macaúbas', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0255, 'Brumado' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0256, 'Buerarema' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0257, 'Buritirama', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0258, 'Caatiba' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0259, 'Cabaceiras do Paraguaçu', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0260, 'Cachoeira' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0261, 'Caculé', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0262, 'Caém' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0263, 'Caetanos', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0264, 'Caetité' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0265, 'Cafarnaum' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0266, 'Cairu', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0267, 'Caldeirão Grande' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0268, 'Camacan' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0269, 'Camaçari', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0270, 'Camamu', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0271, 'Campo Alegre de Lourdes', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0272, 'Campo Formoso', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0273, 'Canápolis' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0274, 'Canarana', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0275, 'Canavieiras' , 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0276, 'Candeal' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0277, 'Candeias', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0278, 'Candiba' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0279, 'Cândido Sales', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0280, 'Cansanção' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0281, 'Canudos' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0282, 'Capela do Alto Alegre', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0283, 'Capim Grosso', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0284, 'Caraíbas', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0285, 'Caravelas' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0286, 'Cardeal da Silva' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0287, 'Carinhanha', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0288, 'Casa Nova' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0289, 'Castro Alves', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0290, 'Catolândia', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0291, 'Catu' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0292, 'Caturama', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0293, 'Central' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0294, 'Chorrochó' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0295, 'Cícero Dantas', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0296, 'Cipó' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0297, 'Coaraci' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0298, 'Cocos', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0299, 'Conceição da Feira', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0300, 'Conceição do Almeida' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0301, 'Conceição do Coité', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0302, 'Conceição do Jacuípe' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0303, 'Conde', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0304, 'Condeúba', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0305, 'Contendas do Sincorá' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0306, 'Coração de Maria' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0307, 'Cordeiros' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0308, 'Coribe', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0309, 'Coronel João Sá', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0310, 'Correntina', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0311, 'Cotegipe', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0312, 'Cravolândia' , 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0313, 'Crisópolis', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0314, 'Cristópolis' , 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0315, 'Cruz das Almas' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0316, 'Curaçá', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0317, 'Dário Meira' , 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0318, 'Dias d`Ávila', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0319, 'Dom Basílio' , 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0320, 'Dom Macedo Costa' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0321, 'Elísio Medrado' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0322, 'Encruzilhada', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0323, 'Entre Rios', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0324, 'Érico Cardoso', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0325, 'Esplanada' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0326, 'Euclides da Cunha', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0327, 'Eunápolis' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0328, 'Fátima', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0329, 'Feira da Mata', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0330, 'Feira de Santana' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0331, 'Filadélfia', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0332, 'Firmino Alves', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0333, 'Floresta Azul', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0334, 'Formosa do Rio Preto' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0335, 'Gandu', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0336, 'Gavião', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0337, 'Gentio do Ouro' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0338, 'Glória', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0339, 'Gongogi' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0340, 'Governador Lomanto Júnior', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0341, 'Governador Mangabeira', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0342, 'Guajeru' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0343, 'Guanambi', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0344, 'Guaratinga', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0345, 'Heliópolis', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0346, 'Iaçu' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0347, 'Ibiassucê' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0348, 'Ibicaraí', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0349, 'Ibicoara', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0350, 'Ibicuí', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0351, 'Ibipeba' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0352, 'Ibipitanga', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0353, 'Ibiquera', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0354, 'Ibirapitanga', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0355, 'Ibirapuã', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0356, 'Ibirataia' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0357, 'Ibitiara', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0358, 'Ibititá' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0359, 'Ibotirama' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0360, 'Ichu' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0361, 'Igaporã' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0362, 'Igrapiúna' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0363, 'Iguaí', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0364, 'Ilhéus', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0365, 'Inhambupe' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0366, 'Ipecaetá', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0367, 'Ipiaú', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0368, 'Ipirá', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0369, 'Ipupiara', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0370, 'Irajuba' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0371, 'Iramaia' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0372, 'Iraquara', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0373, 'Irará', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0374, 'Irecê', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0375, 'Itabela' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0376, 'Itaberaba' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0377, 'Itabuna' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0378, 'Itacaré' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0379, 'Itaeté', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0380, 'Itagi', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0381, 'Itagibá' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0382, 'Itagimirim', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0383, 'Itaguaçu da Bahia', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0384, 'Itaju do Colônia' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0385, 'Itajuípe', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0386, 'Itamaraju' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0387, 'Itamari' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0388, 'Itambé', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0389, 'Itanagra', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0390, 'Itanhém' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0391, 'Itaparica' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0392, 'Itapé', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0393, 'Itapebi' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0394, 'Itapetinga', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0395, 'Itapicuru' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0396, 'Itapitanga', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0397, 'Itaquara', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0398, 'Itarantim' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0399, 'Itatim', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0400, 'Itiruçu' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0401, 'Itiúba', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0402, 'Itororó' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0403, 'Ituaçu', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0404, 'Ituberá' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0405, 'Iuiú' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0406, 'Jaborandi' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0407, 'Jacaraci', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0408, 'Jacobina', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0409, 'Jaguaquara', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0410, 'Jaguarari' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0411, 'Jaguaripe' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0412, 'Jandaíra', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0413, 'Jequié', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0414, 'Jeremoabo' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0415, 'Jiquiriçá' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0416, 'Jitaúna' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0417, 'João Dourado', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0418, 'Juazeiro', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0419, 'Jucuruçu', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0420, 'Jussara' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0421, 'Jussari' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0422, 'Jussiape', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0423, 'Lafaiete Coutinho', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0424, 'Lagoa Real', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0425, 'Laje' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0426, 'Lajedão' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0427, 'Lajedinho' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0428, 'Lajedo do Tabocal', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0429, 'Lamarão' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0430, 'Lapão', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0431, 'Lauro de Freitas' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0432, 'Lençóis' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0433, 'Licínio de Almeida', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0434, 'Livramento de Nossa Senhora' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0435, 'Macajuba', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0436, 'Macarani', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0437, 'Macaúbas', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0438, 'Macururé', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0439, 'Madre de Deus', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0440, 'Maetinga', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0441, 'Maiquinique' , 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0442, 'Mairi', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0443, 'Malhada' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0444, 'Malhada de Pedras', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0445, 'Manoel Vitorino', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0446, 'Mansidão', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0447, 'Maracás' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0448, 'Maragogipe', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0449, 'Maraú', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0450, 'Marcionílio Souza', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0451, 'Mascote' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0452, 'Mata de São João' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0453, 'Matina', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0454, 'Medeiros Neto', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0455, 'Miguel Calmon', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0456, 'Milagres', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0457, 'Mirangaba' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0458, 'Mirante' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0459, 'Monte Santo' , 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0460, 'Morpará' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0461, 'Morro do Chapéu', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0462, 'Mortugaba' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0463, 'Mucugê', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0464, 'Mucuri', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0465, 'Mulungu do Morro' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0466, 'Mundo Novo', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0467, 'Muniz Ferreira' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0468, 'Muquém de São Francisco', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0469, 'Muritiba', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0470, 'Mutuípe' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0471, 'Nazaré', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0472, 'Nilo Peçanha', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0473, 'Nordestina', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0474, 'Nova Canaã', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0475, 'Nova Fátima' , 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0476, 'Nova Ibiá' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0477, 'Nova Itarana', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0478, 'Nova Redenção', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0479, 'Nova Soure', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0480, 'Nova Viçosa' , 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0481, 'Novo Horizonte' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0482, 'Novo Triunfo', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0483, 'Olindina', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0484, 'Oliveira dos Brejinhos' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0485, 'Ouriçangas', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0486, 'Ourolândia', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0487, 'Palmas de Monte Alto' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0488, 'Palmeiras' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0489, 'Paramirim' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0490, 'Paratinga' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0491, 'Paripiranga' , 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0492, 'Pau Brasil', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0493, 'Paulo Afonso', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0494, 'Pé de Serra' , 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0495, 'Pedrão', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0496, 'Pedro Alexandre', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0497, 'Piatã', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0498, 'Pilão Arcado', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0499, 'Pindaí', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0500, 'Pindobaçu' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0501, 'Pintadas', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0502, 'Piraí do Norte' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0503, 'Piripá', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0504, 'Piritiba', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0505, 'Planaltino', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0506, 'Planalto', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0507, 'Poções', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0508, 'Pojuca', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0509, 'Ponto Novo', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0510, 'Porto Seguro', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0511, 'Potiraguá' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0512, 'Prado', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0513, 'Presidente Dutra' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0514, 'Presidente Jânio Quadros' , 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0515, 'Presidente Tancredo Neves', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0516, 'Queimadas' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0517, 'Quijingue' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0518, 'Quixabeira', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0519, 'Rafael Jambeiro', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0520, 'Remanso' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0521, 'Retirolândia', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0522, 'Riachão das Neves', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0523, 'Riachão do Jacuípe', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0524, 'Riacho de Santana', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0525, 'Ribeira do Amparo', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0526, 'Ribeira do Pombal', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0527, 'Ribeirão do Largo', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0528, 'Rio de Contas', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0529, 'Rio do Antônio' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0530, 'Rio do Pires', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0531, 'Rio Real', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0532, 'Rodelas' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0533, 'Ruy Barbosa' , 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0534, 'Salinas da Margarida' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0535, 'Salvador', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0536, 'Santa Bárbara', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0537, 'Santa Brígida', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0538, 'Santa Cruz Cabrália', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0539, 'Santa Cruz da Vitória', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0540, 'Santa Inês', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0541, 'Santa Luzia' , 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0542, 'Santa Maria da Vitória' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0543, 'Santa Rita de Cássia' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0544, 'Santa Teresinha', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0545, 'Santaluz', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0546, 'Santana' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0547, 'Santanópolis', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0548, 'Santo Amaro' , 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0549, 'Santo Antônio de Jesus' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0550, 'Santo Estêvão', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0551, 'São Desidério', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0552, 'São Domingos', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0553, 'São Felipe', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0554, 'São Félix' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0555, 'São Félix do Coribe', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0556, 'São Francisco do Conde' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0557, 'São Gabriel' , 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0558, 'São Gonçalo dos Campos' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0559, 'São José da Vitória', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0560, 'São José do Jacuípe', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0561, 'São Miguel das Matas' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0562, 'São Sebastião do Passé' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0563, 'Sapeaçu' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0564, 'Sátiro Dias' , 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0565, 'Saubara' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0566, 'Saúde', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0567, 'Seabra', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0568, 'Sebastião Laranjeiras', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0569, 'Senhor do Bonfim' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0570, 'Sento Sé', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0571, 'Serra do Ramalho' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0572, 'Serra Dourada', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0573, 'Serra Preta' , 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0574, 'Serrinha', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0575, 'Serrolândia' , 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0576, 'Simões Filho', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0577, 'Sítio do Mato', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0578, 'Sítio do Quinto', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0579, 'Sobradinho', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0580, 'Souto Soares', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0581, 'Tabocas do Brejo Velho' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0582, 'Tanhaçu' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0583, 'Tanque Novo' , 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0584, 'Tanquinho' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0585, 'Taperoá' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0586, 'Tapiramutá', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0587, 'Teixeira de Freitas', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0588, 'Teodoro Sampaio', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0589, 'Teofilândia' , 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0590, 'Teolândia' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0591, 'Terra Nova', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0592, 'Tremedal', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0593, 'Tucano', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0594, 'Uauá' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0595, 'Ubaíra', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0596, 'Ubaitaba', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0597, 'Ubatã', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0598, 'Uibaí', 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0599, 'Umburanas' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0600, 'Una', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0601, 'Urandi', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0602, 'Uruçuca' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0603, 'Utinga', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0604, 'Valença' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0605, 'Valente' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0606, 'Várzea da Roça' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0607, 'Várzea do Poço' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0608, 'Várzea Nova' , 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0609, 'Varzedo' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0610, 'Vera Cruz' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0611, 'Vereda', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0612, 'Vitória da Conquista' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0613, 'Wagner', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0614, 'Wanderley' , 13);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0615, 'Wenceslau Guimarães', 13 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0616, 'Xique-Xique' , 13 );

INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0617, 'Abaiara' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0618, 'Acarapé' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0619, 'Acaraú', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0620, 'Acopiara', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0621, 'Aiuaba', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0622, 'Alcântaras', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0623, 'Altaneira' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0624, 'Alto Santo', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0625, 'Amontada', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0626, 'Antonina do Norte', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0627, 'Apuiarés', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0628, 'Aquiraz' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0629, 'Aracati' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0630, 'Aracoiaba' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0631, 'Ararendá', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0632, 'Araripe' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0633, 'Aratuba' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0634, 'Arneiroz', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0635, 'Assaré', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0636, 'Aurora', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0637, 'Baixio', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0638, 'Banabuiú', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0639, 'Barbalha', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0640, 'Barreira', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0641, 'Barro', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0642, 'Barroquinha' , 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0643, 'Baturité', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0644, 'Beberibe', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0645, 'Bela Cruz' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0646, 'Boa Viagem', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0647, 'Brejo Santo' , 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0648, 'Camocim' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0649, 'Campos Sales', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0650, 'Canindé' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0651, 'Capistrano', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0652, 'Caridade', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0653, 'Cariré', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0654, 'Caririaçu' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0655, 'Cariús', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0656, 'Carnaubal' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0657, 'Cascavel', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0658, 'Catarina', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0659, 'Catunda' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0660, 'Caucaia' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0661, 'Cedro', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0662, 'Chaval', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0663, 'Choró', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0664, 'Chorozinho', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0665, 'Coreaú', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0666, 'Crateús' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0667, 'Crato', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0668, 'Croatá', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0669, 'Cruz' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0670, 'Deputado Irapuan Pinheiro', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0671, 'Ererê', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0672, 'Eusébio' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0673, 'Farias Brito', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0674, 'Forquilha' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0675, 'Fortaleza' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0676, 'Fortim', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0677, 'Frecheirinha', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0678, 'General Sampaio', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0679, 'Graça', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0680, 'Granja', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0681, 'Granjeiro' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0682, 'Groaíras', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0683, 'Guaiúba' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0684, 'Guaraciaba do Norte', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0685, 'Guaramiranga', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0686, 'Hidrolândia' , 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0687, 'Horizonte' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0688, 'Ibaretama' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0689, 'Ibiapina', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0690, 'Ibicuitinga' , 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0691, 'Icapuí', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0692, 'Icó', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0693, 'Iguatu', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0694, 'Independência', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0695, 'Ipaporanga', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0696, 'Ipaumirim' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0697, 'Ipu', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0698, 'Ipueiras', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0699, 'Iracema' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0700, 'Irauçuba', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0701, 'Itaiçaba', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0702, 'Itaitinga' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0703, 'Itapagé' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0704, 'Itapipoca' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0705, 'Itapiúna', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0706, 'Itarema' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0707, 'Itatira' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0708, 'Jaguaretama' , 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0709, 'Jaguaribara' , 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0710, 'Jaguaribe' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0711, 'Jaguaruana', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0712, 'Jardim', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0713, 'Jati' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0714, 'Jijoca de Jericoacoara' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0715, 'Juazeiro do Norte', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0716, 'Jucás', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0717, 'Lavras da Mangabeira' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0718, 'Limoeiro do Norte', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0719, 'Madalena', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0720, 'Maracanaú' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0721, 'Maranguape', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0722, 'Marco', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0723, 'Martinópole' , 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0724, 'Massapê' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0725, 'Mauriti' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0726, 'Meruoca' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0727, 'Milagres', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0728, 'Milhã', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0729, 'Miraíma' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0730, 'Missão Velha', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0731, 'Mombaça' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0732, 'Monsenhor Tabosa' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0733, 'Morada Nova' , 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0734, 'Moraújo' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0735, 'Morrinhos' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0736, 'Mucambo' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0737, 'Mulungu' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0738, 'Nova Olinda' , 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0739, 'Nova Russas' , 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0740, 'Novo Oriente', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0741, 'Ocara', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0742, 'Orós' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0743, 'Pacajus' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0744, 'Pacatuba', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0745, 'Pacoti', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0746, 'Pacujá', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0747, 'Palhano' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0748, 'Palmácia', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0749, 'Paracuru', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0750, 'Paraipaba' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0751, 'Parambu' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0752, 'Paramoti', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0753, 'Pedra Branca', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0754, 'Penaforte' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0755, 'Pentecoste', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0756, 'Pereiro' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0757, 'Pindoretama' , 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0758, 'Piquet Carneiro', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0759, 'Pires Ferreira' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0760, 'Poranga' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0761, 'Porteiras' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0762, 'Potengi' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0763, 'Potiretama', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0764, 'Quiterianópolis', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0765, 'Quixadá' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0766, 'Quixelô' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0767, 'Quixeramobim', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0768, 'Quixeré' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0769, 'Redenção', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0770, 'Reriutaba' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0771, 'Russas', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0772, 'Saboeiro', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0773, 'Salitre' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0774, 'Santa Quitéria' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0775, 'Santana do Acaraú', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0776, 'Santana do Cariri', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0777, 'São Benedito', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0778, 'São Gonçalo do Amarante', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0779, 'São João do Jaguaribe', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0780, 'São Luís do Curu' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0781, 'Senador Pompeu' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0782, 'Senador Sá', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0783, 'Sobral', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0784, 'Solonópole', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0785, 'Tabuleiro do Norte', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0786, 'Tamboril', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0787, 'Tarrafas', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0788, 'Tauá' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0789, 'Tejuçuoca' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0790, 'Tianguá' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0791, 'Trairi', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0792, 'Tururu', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0793, 'Ubajara' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0794, 'Umari', 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0795, 'Umirim', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0796, 'Uruburetama' , 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0797, 'Uruoca', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0798, 'Varjota' , 14);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0799, 'Várzea Alegre', 14 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0800, 'Viçosa do Ceará', 14 );

INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0801, 'Brasília', 01 );

INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0802, 'Afonso Cláudio' , 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0803, 'Água Doce do Norte', 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0804, 'Águia Branca', 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0805, 'Alegre', 08 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0806, 'Alfredo Chaves' , 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0807, 'Alto Rio Novo', 08 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0808, 'Anchieta', 08 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0809, 'Apiacá', 08 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0810, 'Aracruz' , 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0811, 'Atilio Vivacqua', 08 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0812, 'Baixo Guandu', 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0813, 'Barra de São Francisco' , 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0814, 'Boa Esperança', 08 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0815, 'Bom Jesus do Norte', 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0816, 'Brejetuba' , 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0817, 'Cachoeiro de Itapemirim', 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0818, 'Cariacica' , 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0819, 'Castelo' , 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0820, 'Colatina', 08 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0821, 'Conceição da Barra', 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0822, 'Conceição do Castelo' , 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0823, 'Divino de São Lourenço' , 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0824, 'Domingos Martins' , 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0825, 'Dores do Rio Preto', 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0826, 'Ecoporanga', 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0827, 'Fundão', 08 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0828, 'Guaçuí', 08 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0829, 'Guarapari' , 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0830, 'Ibatiba' , 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0831, 'Ibiraçu' , 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0832, 'Ibitirama' , 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0833, 'Iconha', 08 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0834, 'Irupi', 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0835, 'Itaguaçu', 08 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0836, 'Itapemirim', 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0837, 'Itarana' , 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0838, 'Iúna' , 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0839, 'Jaguaré' , 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0840, 'Jerônimo Monteiro', 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0841, 'João Neiva', 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0842, 'Laranja da Terra' , 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0843, 'Linhares', 08 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0844, 'Mantenópolis', 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0845, 'Marataízes', 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0846, 'Marechal Floriano', 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0847, 'Marilândia', 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0848, 'Mimoso do Sul', 08 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0849, 'Montanha', 08 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0850, 'Mucurici', 08 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0851, 'Muniz Freire', 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0852, 'Muqui', 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0853, 'Nova Venécia', 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0854, 'Pancas', 08 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0855, 'Pedro Canário', 08 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0856, 'Pinheiros' , 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0857, 'Piúma', 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0858, 'Ponto Belo', 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0859, 'Presidente Kennedy', 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0860, 'Rio Bananal' , 08 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0861, 'Rio Novo do Sul', 08 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0862, 'Santa Leopoldina' , 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0863, 'Santa Maria de Jetibá', 08 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0864, 'Santa Teresa', 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0865, 'São Domingos do Norte', 08 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0866, 'São Gabriel da Palha' , 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0867, 'São José do Calçado', 08 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0868, 'São Mateus', 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0869, 'São Roque do Canaã', 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0870, 'Serra', 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0871, 'Sooretama' , 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0872, 'Vargem Alta' , 08 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0873, 'Venda Nova do Imigrante', 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0874, 'Viana', 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0875, 'Vila Pavão', 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0876, 'Vila Valério', 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0877, 'Vila Velha', 08);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0878, 'Vitória' , 08);

INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0879, 'Abadia de Goiás', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0880, 'Abadiânia' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0881, 'Acreúna' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0882, 'Adelândia' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0883, 'Água Fria de Goiás', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0884, 'Água Limpa', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0885, 'Águas Lindas de Goiás', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0886, 'Alexânia', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0887, 'Aloândia', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0888, 'Alto Horizonte' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0889, 'Alto Paraíso de Goiás', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0890, 'Alvorada do Norte', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0891, 'Amaralina' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0892, 'Americano do Brasil', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0893, 'Amorinópolis', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0894, 'Anápolis', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0895, 'Anhanguera', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0896, 'Anicuns' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0897, 'Aparecida de Goiânia' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0898, 'Aparecida do Rio Doce', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0899, 'Aporé', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0900, 'Araçu', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0901, 'Aragarças' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0902, 'Aragoiânia', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0903, 'Araguapaz' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0904, 'Arenópolis', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0905, 'Aruanã', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0906, 'Aurilândia', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0907, 'Avelinópolis', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0908, 'Baliza', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0909, 'Barro Alto', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0910, 'Bela Vista de Goiás', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0911, 'Bom Jardim de Goiás', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0912, 'Bom Jesus de Goiás', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0913, 'Bonfinópolis', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0914, 'Bonópolis' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0915, 'Brazabrantes', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0916, 'Britânia', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0917, 'Buriti Alegre', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0918, 'Buriti de Goiás', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0919, 'Buritinópolis', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0920, 'Cabeceiras', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0921, 'Cachoeira Alta' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0922, 'Cachoeira de Goiás', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0923, 'Cachoeira Dourada', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0924, 'Caçu' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0925, 'Caiapônia' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0926, 'Caldas Novas', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0927, 'Caldazinha', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0928, 'Campestre de Goiás', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0929, 'Campinaçu' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0930, 'Campinorte', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0931, 'Campo Alegre de Goiás', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0932, 'Campos Belos', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0933, 'Campos Verdes', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0934, 'Carmo do Rio Verde', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0935, 'Castelândia' , 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0936, 'Catalão' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0937, 'Caturaí' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0938, 'Cavalcante', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0939, 'Ceres', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0940, 'Cezarina', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0941, 'Chapadão do Céu', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0942, '`cidades` Ocidental', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0943, 'Cocalzinho de Goiás', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0944, 'Colinas do Sul' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0945, 'Córrego do Ouro', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0946, 'Corumbá de Goiás' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0947, 'Corumbaíba', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0948, 'Cristalina', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0949, 'Cristianópolis' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0950, 'Crixás', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0951, 'Cromínia', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0952, 'Cumari', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0953, 'Damianópolis', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0954, 'Damolândia', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0955, 'Davinópolis' , 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0956, 'Diorama' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0957, 'Divinópolis de Goiás' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0958, 'Doverlândia' , 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0959, 'Edealina', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0960, 'Edéia', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0961, 'Estrela do Norte' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0962, 'Faina', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0963, 'Fazenda Nova', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0964, 'Firminópolis', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0965, 'Flores de Goiás', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0966, 'Formosa' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0967, 'Formoso' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0968, 'Goianápolis' , 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0969, 'Goiandira' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0970, 'Goianésia' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0971, 'Goiânia' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0972, 'Goianira', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0973, 'Goiás', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0974, 'Goiatuba', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0975, 'Gouvelândia' , 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0976, 'Guapó', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0977, 'Guaraíta', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0978, 'Guarani de Goiás' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0979, 'Guarinos', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0980, 'Heitoraí', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0981, 'Hidrolândia' , 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0982, 'Hidrolina' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0983, 'Iaciara' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0984, 'Inaciolândia', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0985, 'Indiara' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0986, 'Inhumas' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0987, 'Ipameri' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0988, 'Iporá', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0989, 'Israelândia' , 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0990, 'Itaberaí', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0991, 'Itaguari', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0992, 'Itaguaru', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0993, 'Itajá', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0994, 'Itapaci' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0995, 'Itapirapuã', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0996, 'Itapuranga', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0997, 'Itarumã' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0998, 'Itauçu', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (0999, 'Itumbiara' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1000, 'Ivolândia' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1001, 'Jandaia' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1002, 'Jaraguá' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1003, 'Jataí', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1004, 'Jaupaci' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1005, 'Jesúpolis' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1006, 'Joviânia', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1007, 'Jussara' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1008, 'Leopoldo de Bulhões', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1009, 'Luziânia', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1010, 'Mairipotaba' , 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1011, 'Mambaí', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1012, 'Mara Rosa' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1013, 'Marzagão', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1014, 'Matrinchã' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1015, 'Maurilândia' , 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1016, 'Mimoso de Goiás', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1017, 'Minaçu', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1018, 'Mineiros', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1019, 'Moiporá' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1020, 'Monte Alegre de Goiás', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1021, 'Montes Claros de Goiás' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1022, 'Montividiu', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1023, 'Montividiu do Norte', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1024, 'Morrinhos' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1025, 'Morro Agudo de Goiás' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1026, 'Mossâmedes', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1027, 'Mozarlândia' , 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1028, 'Mundo Novo', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1029, 'Mutunópolis' , 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1030, 'Nazário' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1031, 'Nerópolis' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1032, 'Niquelândia' , 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1033, 'Nova América', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1034, 'Nova Aurora' , 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1035, 'Nova Crixás' , 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1036, 'Nova Glória' , 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1037, 'Nova Iguaçu de Goiás' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1038, 'Nova Roma' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1039, 'Nova Veneza' , 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1040, 'Novo Brasil' , 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1041, 'Novo Gama' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1042, 'Novo Planalto', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1043, 'Orizona' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1044, 'Ouro Verde de Goiás', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1045, 'Ouvidor' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1046, 'Padre Bernardo' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1047, 'Palestina de Goiás', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1048, 'Palmeiras de Goiás', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1049, 'Palmelo' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1050, 'Palminópolis', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1051, 'Panamá', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1052, 'Paranaiguara', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1053, 'Paraúna' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1054, 'Perolândia', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1055, 'Petrolina de Goiás', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1056, 'Pilar de Goiás' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1057, 'Piracanjuba' , 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1058, 'Piranhas', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1059, 'Pirenópolis' , 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1060, 'Pires do Rio', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1061, 'Planaltina', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1062, 'Pontalina' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1063, 'Porangatu' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1064, 'Porteirão' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1065, 'Portelândia' , 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1066, 'Posse', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1067, 'Professor Jamil', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1068, 'Quirinópolis', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1069, 'Rialma', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1070, 'Rianápolis', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1071, 'Rio Quente', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1072, 'Rio Verde' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1073, 'Rubiataba' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1074, 'Sanclerlândia', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1075, 'Santa Bárbara de Goiás' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1076, 'Santa Cruz de Goiás', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1077, 'Santa Fé de Goiás', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1078, 'Santa Helena de Goiás', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1079, 'Santa Isabel', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1080, 'Santa Rita do Araguaia' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1081, 'Santa Rita do Novo Destino', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1082, 'Santa Rosa de Goiás', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1083, 'Santa Tereza de Goiás', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1084, 'Santa Terezinha de Goiás' , 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1085, 'Santo Antônio da Barra' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1086, 'Santo Antônio de Goiás' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1087, 'Santo Antônio do Descoberto' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1088, 'São Domingos', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1089, 'São Francisco de Goiás' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1090, 'São João d`Aliança', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1091, 'São João da Paraúna', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1092, 'São Luís de Montes Belos' , 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1093, 'São Luíz do Norte', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1094, 'São Miguel do Araguaia' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1095, 'São Miguel do Passa Quatro', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1096, 'São Patrício', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1097, 'São Simão' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1098, 'Senador Canedo' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1099, 'Serranópolis', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1100, 'Silvânia', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1101, 'Simolândia', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1102, 'Sítio d`Abadia' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1103, 'Taquaral de Goiás', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1104, 'Teresina de Goiás', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1105, 'Terezópolis de Goiás' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1106, 'Três Ranchos', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1107, 'Trindade', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1108, 'Trombas' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1109, 'Turvânia', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1110, 'Turvelândia' , 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1111, 'Uirapuru', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1112, 'Uruaçu', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1113, 'Uruana', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1114, 'Urutaí', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1115, 'Valparaíso de Goiás', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1116, 'Varjão', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1117, 'Vianópolis', 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1118, 'Vicentinópolis' , 04);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1119, 'Vila Boa', 04 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1120, 'Vila Propício', 04 );

INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1121, 'Açailândia', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1122, 'Afonso Cunha', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1123, 'Água Doce do Maranhão', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1124, 'Alcântara' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1125, 'Aldeias Altas', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1126, 'Altamira do Maranhão' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1127, 'Alto Alegre do Maranhão', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1128, 'Alto Alegre do Pindaré' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1129, 'Alto Parnaíba', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1130, 'Amapá do Maranhão', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1131, 'Amarante do Maranhão' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1132, 'Anajatuba' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1133, 'Anapurus', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1134, 'Apicum-Açu', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1135, 'Araguanã', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1136, 'Araioses', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1137, 'Arame', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1138, 'Arari', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1139, 'Axixá', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1140, 'Bacabal' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1141, 'Bacabeira' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1142, 'Bacuri', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1143, 'Bacurituba', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1144, 'Balsas', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1145, 'Barão de Grajaú', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1146, 'Barra do Corda' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1147, 'Barreirinhas', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1148, 'Bela Vista do Maranhão' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1149, 'Belágua' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1150, 'Benedito Leite' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1151, 'Bequimão', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1152, 'Bernardo do Mearim', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1153, 'Boa Vista do Gurupi', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1154, 'Bom Jardim', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1155, 'Bom Jesus das Selvas' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1156, 'Bom Lugar' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1157, 'Brejo', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1158, 'Brejo de Areia' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1159, 'Buriti', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1160, 'Buriti Bravo', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1161, 'Buriticupu', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1162, 'Buritirana', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1163, 'Cachoeira Grande' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1164, 'Cajapió' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1165, 'Cajari', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1166, 'Campestre do Maranhão', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1167, 'Cândido Mendes' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1168, 'Cantanhede', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1169, 'Capinzal do Norte', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1170, 'Carolina', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1171, 'Carutapera', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1172, 'Caxias', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1173, 'Cedral', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1174, 'Central do Maranhão', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1175, 'Centro do Guilherme', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1176, 'Centro Novo do Maranhão', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1177, 'Chapadinha', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1178, 'Cidelândia', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1179, 'Codó' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1180, 'Coelho Neto' , 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1181, 'Colinas' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1182, 'Conceição do Lago-Açu', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1183, 'Coroatá' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1184, 'Cururupu', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1185, 'Davinópolis' , 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1186, 'Dom Pedro' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1187, 'Duque Bacelar', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1188, 'Esperantinópolis' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1189, 'Estreito', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1190, 'Feira Nova do Maranhão' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1191, 'Fernando Falcão', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1192, 'Formosa da Serra Negra' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1193, 'Fortaleza dos Nogueiras', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1194, 'Fortuna' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1195, 'Godofredo Viana', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1196, 'Gonçalves Dias' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1197, 'Governador Archer', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1198, 'Governador Edison Lobão', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1199, 'Governador Eugênio Barros', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1200, 'Governador Luiz Rocha', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1201, 'Governador Newton Bello', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1202, 'Governador Nunes Freire', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1203, 'Graça Aranha', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1204, 'Grajaú', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1205, 'Guimarães' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1206, 'Humberto de Campos', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1207, 'Icatu', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1208, 'Igarapé do Meio', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1209, 'Igarapé Grande' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1210, 'Imperatriz', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1211, 'Itaipava do Grajaú', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1212, 'Itapecuru Mirim', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1213, 'Itinga do Maranhão', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1214, 'Jatobá', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1215, 'Jenipapo dos Vieiras' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1216, 'João Lisboa' , 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1217, 'Joselândia', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1218, 'Junco do Maranhão', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1219, 'Lago da Pedra', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1220, 'Lago do Junco', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1221, 'Lago dos Rodrigues', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1222, 'Lago Verde', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1223, 'Lagoa do Mato', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1224, 'Lagoa Grande do Maranhão' , 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1225, 'Lajeado Novo', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1226, 'Lima Campos' , 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1227, 'Loreto', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1228, 'Luís Domingues' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1229, 'Magalhães de Almeida' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1230, 'Maracaçumé', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1231, 'Marajá do Sena' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1232, 'Maranhãozinho', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1233, 'Mata Roma' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1234, 'Matinha' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1235, 'Matões', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1236, 'Matões do Norte', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1237, 'Milagres do Maranhão' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1238, 'Mirador' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1239, 'Miranda do Norte' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1240, 'Mirinzal', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1241, 'Monção', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1242, 'Montes Altos', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1243, 'Morros', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1244, 'Nina Rodrigues' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1245, 'Nova Colinas', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1246, 'Nova Iorque' , 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1247, 'Nova Olinda do Maranhão', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1248, 'Olho d`Água das Cunhãs' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1249, 'Olinda Nova do Maranhão', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1250, 'Paço do Lumiar' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1251, 'Palmeirândia', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1252, 'Paraibano' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1253, 'Parnarama' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1254, 'Passagem Franca', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1255, 'Pastos Bons' , 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1256, 'Paulino Neves', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1257, 'Paulo Ramos' , 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1258, 'Pedreiras' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1259, 'Pedro do Rosário' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1260, 'Penalva' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1261, 'Peri Mirim', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1262, 'Peritoró', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1263, 'Pindaré-Mirim', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1264, 'Pinheiro', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1265, 'Pio XII' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1266, 'Pirapemas' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1267, 'Poção de Pedras', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1268, 'Porto Franco', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1269, 'Porto Rico do Maranhão' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1270, 'Presidente Dutra' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1271, 'Presidente Juscelino' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1272, 'Presidente Médici', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1273, 'Presidente Sarney', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1274, 'Presidente Vargas', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1275, 'Primeira Cruz', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1276, 'Raposa', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1277, 'Riachão' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1278, 'Ribamar Fiquene', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1279, 'Rosário' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1280, 'Sambaíba', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1281, 'Santa Filomena do Maranhão', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1282, 'Santa Helena', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1283, 'Santa Inês', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1284, 'Santa Luzia' , 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1285, 'Santa Luzia do Paruá' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1286, 'Santa Quitéria do Maranhão', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1287, 'Santa Rita', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1288, 'Santana do Maranhão', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1289, 'Santo Amaro do Maranhão', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1290, 'Santo Antônio dos Lopes', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1291, 'São Benedito do Rio Preto', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1292, 'São Bento' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1293, 'São Bernardo', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1294, 'São Domingos do Azeitão', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1295, 'São Domingos do Maranhão' , 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1296, 'São Félix de Balsas', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1297, 'São Francisco do Brejão', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1298, 'São Francisco do Maranhão', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1299, 'São João Batista' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1300, 'São João do Carú' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1301, 'São João do Paraíso', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1302, 'São João do Soter', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1303, 'São João dos Patos', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1304, 'São José de Ribamar', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1305, 'São José dos Basílios', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1306, 'São Luís', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1307, 'São Luís Gonzaga do Maranhão', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1308, 'São Mateus do Maranhão' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1309, 'São Pedro da Água Branca' , 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1310, 'São Pedro dos Crentes', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1311, 'São Raimundo das Mangabeiras', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1312, 'São Raimundo do Doca Bezerra', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1313, 'São Roberto' , 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1314, 'São Vicente Ferrer', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1315, 'Satubinha' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1316, 'Senador Alexandre Costa', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1317, 'Senador La Rocque', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1318, 'Serrano do Maranhão', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1319, 'Sítio Novo', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1320, 'Sucupira do Norte', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1321, 'Sucupira do Riachão', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1322, 'Tasso Fragoso', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1323, 'Timbiras', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1324, 'Timon', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1325, 'Trizidela do Vale', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1326, 'Tufilândia', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1327, 'Tuntum', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1328, 'Turiaçu' , 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1329, 'Turilândia', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1330, 'Tutóia', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1331, 'Urbano Santos', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1332, 'Vargem Grande', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1333, 'Viana', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1334, 'Vila Nova dos Martírios', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1335, 'Vitória do Mearim', 15);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1336, 'Vitorino Freire', 15 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1337, 'Zé Doca' , 15);

INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1338, 'Acorizal', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1339, 'Água Boa', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1340, 'Alta Floresta', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1341, 'Alto Araguaia', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1342, 'Alto Boa Vista' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1343, 'Alto Garças' , 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1344, 'Alto Paraguai', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1345, 'Alto Taquari', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1346, 'Apiacás' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1347, 'Araguaiana', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1348, 'Araguainha', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1349, 'Araputanga', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1350, 'Arenápolis', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1351, 'Aripuanã', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1352, 'Barão de Melgaço' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1353, 'Barra do Bugres', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1354, 'Barra do Garças', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1355, 'Brasnorte' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1356, 'Cáceres' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1357, 'Campinápolis', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1358, 'Campo Novo do Parecis', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1359, 'Campo Verde' , 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1360, 'Campos de Júlio', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1361, 'Canabrava do Norte', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1362, 'Canarana', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1363, 'Carlinda', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1364, 'Castanheira' , 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1365, 'Chapada dos Guimarães', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1366, 'Cláudia' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1367, 'Cocalinho' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1368, 'Colíder' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1369, 'Comodoro', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1370, 'Confresa', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1371, 'Cotriguaçu', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1372, 'Cuiabá', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1373, 'Denise', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1374, 'Diamantino', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1375, 'Dom Aquino', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1376, 'Feliz Natal' , 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1377, 'Figueirópolis d`Oeste', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1378, 'Gaúcha do Norte', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1379, 'General Carneiro' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1380, 'Glória d`Oeste' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1381, 'Guarantã do Norte', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1382, 'Guiratinga', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1383, 'Indiavaí', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1384, 'Itaúba', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1385, 'Itiquira', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1386, 'Jaciara' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1387, 'Jangada' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1388, 'Jauru', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1389, 'Juara', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1390, 'Juína', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1391, 'Juruena' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1392, 'Juscimeira', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1393, 'Lambari d`Oeste', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1394, 'Lucas do Rio Verde', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1395, 'Luciára' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1396, 'Marcelândia' , 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1397, 'Matupá', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1398, 'Mirassol d`Oeste' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1399, 'Nobres', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1400, 'Nortelândia' , 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1401, 'Nossa Senhora do Livramento' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1402, 'Nova Bandeirantes', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1403, 'Nova Brasilândia' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1404, 'Nova Canaã do Norte', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1405, 'Nova Guarita', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1406, 'Nova Lacerda', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1407, 'Nova Marilândia', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1408, 'Nova Maringá', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1409, 'Nova Monte Verde' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1410, 'Nova Mutum', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1411, 'Nova Olímpia', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1412, 'Nova Ubiratã', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1413, 'Nova Xavantina' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1414, 'Novo Horizonte do Norte', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1415, 'Novo Mundo', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1416, 'Novo São Joaquim' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1417, 'Paranaíta' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1418, 'Paranatinga' , 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1419, 'Pedra Preta' , 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1420, 'Peixoto de Azevedo', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1421, 'Planalto da Serra', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1422, 'Poconé', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1423, 'Pontal do Araguaia', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1424, 'Ponte Branca', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1425, 'Pontes e Lacerda' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1426, 'Porto Alegre do Norte', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1427, 'Porto dos Gaúchos', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1428, 'Porto Esperidião' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1429, 'Porto Estrela', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1430, 'Poxoréo' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1431, 'Primavera do Leste', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1432, 'Querência' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1433, 'Reserva do Cabaçal', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1434, 'Ribeirão Cascalheira' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1435, 'Ribeirãozinho', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1436, 'Rio Branco', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1437, 'Rondonópolis', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1438, 'Rosário Oeste', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1439, 'Salto do Céu', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1440, 'Santa Carmem', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1441, 'Santa Terezinha', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1442, 'Santo Afonso', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1443, 'Santo Antônio do Leverger', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1444, 'São Félix do Araguaia', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1445, 'São José do Povo' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1446, 'São José do Rio Claro', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1447, 'São José do Xingu', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1448, 'São José dos Quatro Marcos', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1449, 'São Pedro da Cipa', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1450, 'Sapezal' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1451, 'Sinop', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1452, 'Sorriso' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1453, 'Tabaporã', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1454, 'Tangará da Serra' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1455, 'Tapurah' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1456, 'Terra Nova do Norte', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1457, 'Tesouro' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1458, 'Torixoréu' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1459, 'União do Sul', 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1460, 'Várzea Grande', 16 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1461, 'Vera' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1462, 'Vila Bela da Santíssima Trindade' , 16);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1463, 'Vila Rica' , 16);

INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1464, 'Água Clara', 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1465, 'Alcinópolis' , 17 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1466, 'Amambaí' , 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1467, 'Anastácio' , 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1468, 'Anaurilândia', 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1469, 'Angélica', 17 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1470, 'Antônio João', 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1471, 'Aparecida do Taboado' , 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1472, 'Aquidauana', 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1473, 'Aral Moreira', 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1474, 'Bandeirantes', 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1475, 'Bataguassu', 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1476, 'Bataiporã' , 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1477, 'Bela Vista', 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1478, 'Bodoquena' , 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1479, 'Bonito', 17 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1480, 'Brasilândia' , 17 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1481, 'Caarapó' , 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1482, 'Camapuã' , 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1483, 'Campo Grande', 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1484, 'Caracol' , 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1485, 'Cassilândia' , 17 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1486, 'Chapadão do Sul', 17 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1487, 'Corguinho' , 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1488, 'Coronel Sapucaia' , 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1489, 'Corumbá' , 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1490, 'Costa Rica', 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1491, 'Coxim', 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1492, 'Deodápolis', 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1493, 'Dois Irmãos do Buriti', 17 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1494, 'Douradina' , 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1495, 'Dourados', 17 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1496, 'Eldorado', 17 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1497, 'Fátima do Sul', 17 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1498, 'Glória de Dourados', 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1499, 'Guia Lopes da Laguna' , 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1500, 'Iguatemi', 17 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1501, 'Inocência' , 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1502, 'Itaporã' , 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1503, 'Itaquiraí' , 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1504, 'Ivinhema', 17 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1505, 'Japorã', 17 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1506, 'Jaraguari' , 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1507, 'Jardim', 17 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1508, 'Jateí', 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1509, 'Juti' , 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1510, 'Ladário' , 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1511, 'Laguna Carapã', 17 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1512, 'Maracaju', 17 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1513, 'Miranda' , 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1514, 'Mundo Novo', 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1515, 'Naviraí' , 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1516, 'Nioaque' , 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1517, 'Nova Alvorada do Sul' , 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1518, 'Nova Andradina' , 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1519, 'Novo Horizonte do Sul', 17 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1520, 'Paranaíba' , 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1521, 'Paranhos', 17 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1522, 'Pedro Gomes' , 17 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1523, 'Ponta Porã', 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1524, 'Porto Murtinho' , 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1525, 'Ribas do Rio Pardo', 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1526, 'Rio Brilhante', 17 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1527, 'Rio Negro' , 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1528, 'Rio Verde de Mato Grosso' , 17 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1529, 'Rochedo' , 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1530, 'Santa Rita do Pardo', 17 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1531, 'São Gabriel do Oeste' , 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1532, 'Selvíria', 17 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1533, 'Sete Quedas' , 17 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1534, 'Sidrolândia' , 17 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1535, 'Sonora', 17 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1536, 'Tacuru', 17 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1537, 'Taquarussu', 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1538, 'Terenos' , 17);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1539, 'Três Lagoas' , 17 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1540, 'Vicentina' , 17);

INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1541, 'Abadia dos Dourados', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1542, 'Abaeté', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1543, 'Abre Campo', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1544, 'Acaiaca' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1545, 'Açucena' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1546, 'Água Boa', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1547, 'Água Comprida', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1548, 'Aguanil' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1549, 'Águas Formosas' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1550, 'Águas Vermelhas', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1551, 'Aimorés' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1552, 'Aiuruoca', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1553, 'Alagoa', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1554, 'Albertina' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1555, 'Além Paraíba', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1556, 'Alfenas' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1557, 'Alfredo Vasconcelos', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1558, 'Almenara', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1559, 'Alpercata' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1560, 'Alpinópolis' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1561, 'Alterosa', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1562, 'Alto Caparaó', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1563, 'Alto Jequitibá' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1564, 'Alto Rio Doce', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1565, 'Alvarenga' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1566, 'Alvinópolis' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1567, 'Alvorada de Minas', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1568, 'Amparo do Serra', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1569, 'Andradas', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1570, 'Andrelândia' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1571, 'Angelândia', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1572, 'Antônio Carlos' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1573, 'Antônio Dias', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1574, 'Antônio Prado de Minas' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1575, 'Araçaí', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1576, 'Aracitaba' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1577, 'Araçuaí' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1578, 'Araguari', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1579, 'Arantina', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1580, 'Araponga', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1581, 'Araporã' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1582, 'Arapuá', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1583, 'Araújos' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1584, 'Araxá', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1585, 'Arceburgo' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1586, 'Arcos', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1587, 'Areado', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1588, 'Argirita', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1589, 'Aricanduva', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1590, 'Arinos', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1591, 'Astolfo Dutra', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1592, 'Ataléia' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1593, 'Augusto de Lima', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1594, 'Baependi', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1595, 'Baldim', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1596, 'Bambuí', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1597, 'Bandeira', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1598, 'Bandeira do Sul', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1599, 'Barão de Cocais', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1600, 'Barão de Monte Alto', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1601, 'Barbacena' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1602, 'Barra Longa' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1603, 'Barroso' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1604, 'Bela Vista de Minas', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1605, 'Belmiro Braga', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1606, 'Belo Horizonte' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1607, 'Belo Oriente', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1608, 'Belo Vale' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1609, 'Berilo', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1610, 'Berizal' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1611, 'Bertópolis', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1612, 'Betim', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1613, 'Bias Fortes' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1614, 'Bicas', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1615, 'Biquinhas' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1616, 'Boa Esperança', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1617, 'Bocaina de Minas' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1618, 'Bocaiúva', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1619, 'Bom Despacho', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1620, 'Bom Jardim de Minas', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1621, 'Bom Jesus da Penha', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1622, 'Bom Jesus do Amparo', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1623, 'Bom Jesus do Galho', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1624, 'Bom Repouso' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1625, 'Bom Sucesso' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1626, 'Bonfim', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1627, 'Bonfinópolis de Minas', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1628, 'Bonito de Minas', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1629, 'Borda da Mata', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1630, 'Botelhos', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1631, 'Botumirim' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1632, 'Brás Pires', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1633, 'Brasilândia de Minas' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1634, 'Brasília de Minas', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1635, 'Brasópolis', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1636, 'Braúnas' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1637, 'Brumadinho', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1638, 'Bueno Brandão', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1639, 'Buenópolis', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1640, 'Bugre', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1641, 'Buritis' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1642, 'Buritizeiro' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1643, 'Cabeceira Grande' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1644, 'Cabo Verde', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1645, 'Cachoeira da Prata', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1646, 'Cachoeira de Minas', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1647, 'Cachoeira de Pajeú', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1648, 'Cachoeira Dourada', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1649, 'Caetanópolis', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1650, 'Caeté', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1651, 'Caiana', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1652, 'Cajuri', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1653, 'Caldas', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1654, 'Camacho' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1655, 'Camanducaia' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1656, 'Cambuí', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1657, 'Cambuquira', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1658, 'Campanário', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1659, 'Campanha', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1660, 'Campestre' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1661, 'Campina Verde', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1662, 'Campo Azul', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1663, 'Campo Belo', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1664, 'Campo do Meio', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1665, 'Campo Florido', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1666, 'Campos Altos', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1667, 'Campos Gerais', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1668, 'Cana Verde', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1669, 'Canaã', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1670, 'Canápolis' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1671, 'Candeias', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1672, 'Cantagalo' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1673, 'Caparaó' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1674, 'Capela Nova' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1675, 'Capelinha' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1676, 'Capetinga' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1677, 'Capim Branco', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1678, 'Capinópolis' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1679, 'Capitão Andrade', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1680, 'Capitão Enéas', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1681, 'Capitólio' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1682, 'Caputira', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1683, 'Caraí', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1684, 'Caranaíba' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1685, 'Carandaí', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1686, 'Carangola' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1687, 'Caratinga' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1688, 'Carbonita' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1689, 'Careaçu' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1690, 'Carlos Chagas', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1691, 'Carmésia', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1692, 'Carmo da Cachoeira', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1693, 'Carmo da Mata', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1694, 'Carmo de Minas' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1695, 'Carmo do Cajuru', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1696, 'Carmo do Paranaíba', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1697, 'Carmo do Rio Claro', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1698, 'Carmópolis de Minas', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1699, 'Carneirinho' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1700, 'Carrancas' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1701, 'Carvalhópolis', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1702, 'Carvalhos' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1703, 'Casa Grande' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1704, 'Cascalho Rico', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1705, 'Cássia', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1706, 'Cataguases', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1707, 'Catas Altas' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1708, 'Catas Altas da Noruega' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1709, 'Catuji', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1710, 'Catuti', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1711, 'Caxambu' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1712, 'Cedro do Abaeté', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1713, 'Central de Minas' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1714, 'Centralina', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1715, 'Chácara' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1716, 'Chalé', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1717, 'Chapada do Norte' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1718, 'Chapada Gaúcha' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1719, 'Chiador' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1720, 'Cipotânea' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1721, 'Claraval', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1722, 'Claro dos Poções' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1723, 'Cláudio' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1724, 'Coimbra' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1725, 'Coluna', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1726, 'Comendador Gomes' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1727, 'Comercinho', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1728, 'Conceição da Aparecida' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1729, 'Conceição da Barra de Minas' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1730, 'Conceição das Alagoas', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1731, 'Conceição das Pedras' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1732, 'Conceição de Ipanema' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1733, 'Conceição do Mato Dentro' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1734, 'Conceição do Pará', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1735, 'Conceição do Rio Verde' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1736, 'Conceição dos Ouros', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1737, 'Cônego Marinho' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1738, 'Confins' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1739, 'Congonhal' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1740, 'Congonhas' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1741, 'Congonhas do Norte', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1742, 'Conquista' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1743, 'Conselheiro Lafaiete' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1744, 'Conselheiro Pena' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1745, 'Consolação', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1746, 'Contagem', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1747, 'Coqueiral' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1748, 'Coração de Jesus' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1749, 'Cordisburgo' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1750, 'Cordislândia', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1751, 'Corinto' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1752, 'Coroaci' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1753, 'Coromandel', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1754, 'Coronel Fabriciano', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1755, 'Coronel Murta', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1756, 'Coronel Pacheco', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1757, 'Coronel Xavier Chaves', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1758, 'Córrego Danta', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1759, 'Córrego do Bom Jesus' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1760, 'Córrego Fundo', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1761, 'Córrego Novo', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1762, 'Couto de Magalhães de Minas' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1763, 'Crisólita' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1764, 'Cristais', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1765, 'Cristália' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1766, 'Cristiano Otoni', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1767, 'Cristina', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1768, 'Crucilândia' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1769, 'Cruzeiro da Fortaleza', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1770, 'Cruzília', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1771, 'Cuparaque' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1772, 'Curral de Dentro' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1773, 'Curvelo' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1774, 'Datas', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1775, 'Delfim Moreira' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1776, 'Delfinópolis', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1777, 'Delta', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1778, 'Descoberto', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1779, 'Desterro de Entre Rios' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1780, 'Desterro do Melo' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1781, 'Diamantina', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1782, 'Diogo de Vasconcelos' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1783, 'Dionísio', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1784, 'Divinésia' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1785, 'Divino', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1786, 'Divino das Laranjeiras' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1787, 'Divinolândia de Minas', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1788, 'Divinópolis' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1789, 'Divisa Alegre', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1790, 'Divisa Nova' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1791, 'Divisópolis' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1792, 'Dom Bosco' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1793, 'Dom Cavati', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1794, 'Dom Joaquim' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1795, 'Dom Silvério', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1796, 'Dom Viçoso', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1797, 'Dona Eusébia', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1798, 'Dores de Campos', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1799, 'Dores de Guanhães', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1800, 'Dores do Indaiá', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1801, 'Dores do Turvo' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1802, 'Doresópolis' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1803, 'Douradoquara', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1804, 'Durandé' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1805, 'Elói Mendes' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1806, 'Engenheiro Caldas', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1807, 'Engenheiro Navarro', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1808, 'Entre Folhas', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1809, 'Entre Rios de Minas', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1810, 'Ervália' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1811, 'Esmeraldas', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1812, 'Espera Feliz', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1813, 'Espinosa', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1814, 'Espírito Santo do Dourado', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1815, 'Estiva', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1816, 'Estrela Dalva', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1817, 'Estrela do Indaiá', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1818, 'Estrela do Sul' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1819, 'Eugenópolis' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1820, 'Ewbank da Câmara' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1821, 'Extrema' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1822, 'Fama' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1823, 'Faria Lemos' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1824, 'Felício dos Santos', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1825, 'Felisburgo', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1826, 'Felixlândia' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1827, 'Fernandes Tourinho', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1828, 'Ferros', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1829, 'Fervedouro', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1830, 'Florestal' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1831, 'Formiga' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1832, 'Formoso' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1833, 'Fortaleza de Minas', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1834, 'Fortuna de Minas' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1835, 'Francisco Badaró' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1836, 'Francisco Dumont' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1837, 'Francisco Sá', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1838, 'Franciscópolis' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1839, 'Frei Gaspar' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1840, 'Frei Inocêncio' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1841, 'Frei Lagonegro' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1842, 'Fronteira' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1843, 'Fronteira dos Vales', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1844, 'Fruta de Leite' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1845, 'Frutal', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1846, 'Funilândia', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1847, 'Galiléia', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1848, 'Gameleiras', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1849, 'Glaucilândia', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1850, 'Goiabeira' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1851, 'Goianá', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1852, 'Gonçalves' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1853, 'Gonzaga' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1854, 'Gouveia' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1855, 'Governador Valadares' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1856, 'Grão Mogol', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1857, 'Grupiara', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1858, 'Guanhães', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1859, 'Guapé', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1860, 'Guaraciaba', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1861, 'Guaraciama', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1862, 'Guaranésia', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1863, 'Guarani' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1864, 'Guarará' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1865, 'Guarda-Mor', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1866, 'Guaxupé' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1867, 'Guidoval', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1868, 'Guimarânia', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1869, 'Guiricema' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1870, 'Gurinhatã' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1871, 'Heliodora' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1872, 'Iapu' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1873, 'Ibertioga' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1874, 'Ibiá' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1875, 'Ibiaí', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1876, 'Ibiracatu' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1877, 'Ibiraci' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1878, 'Ibirité' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1879, 'Ibitiúra de Minas', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1880, 'Ibituruna' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1881, 'Icaraí de Minas', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1882, 'Igarapé' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1883, 'Igaratinga', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1884, 'Iguatama', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1885, 'Ijaci', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1886, 'Ilicínea', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1887, 'Imbé de Minas', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1888, 'Inconfidentes', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1889, 'Indaiabira', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1890, 'Indianópolis', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1891, 'Ingaí', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1892, 'Inhapim' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1893, 'Inhaúma' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1894, 'Inimutaba' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1895, 'Ipaba', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1896, 'Ipanema' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1897, 'Ipatinga', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1898, 'Ipiaçu', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1899, 'Ipuiúna' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1900, 'Iraí de Minas', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1901, 'Itabira' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1902, 'Itabirinha de Mantena', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1903, 'Itabirito' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1904, 'Itacambira', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1905, 'Itacarambi', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1906, 'Itaguara', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1907, 'Itaipé', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1908, 'Itajubá' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1909, 'Itamarandiba', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1910, 'Itamarati de Minas', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1911, 'Itambacuri', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1912, 'Itambé do Mato Dentro', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1913, 'Itamogi' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1914, 'Itamonte', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1915, 'Itanhandu' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1916, 'Itanhomi', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1917, 'Itaobim' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1918, 'Itapagipe' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1919, 'Itapecerica' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1920, 'Itapeva' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1921, 'Itatiaiuçu', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1922, 'Itaú de Minas', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1923, 'Itaúna', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1924, 'Itaverava' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1925, 'Itinga', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1926, 'Itueta', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1927, 'Ituiutaba' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1928, 'Itumirim', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1929, 'Iturama' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1930, 'Itutinga', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1931, 'Jaboticatubas', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1932, 'Jacinto' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1933, 'Jacuí', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1934, 'Jacutinga' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1935, 'Jaguaraçu' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1936, 'Jaíba', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1937, 'Jampruca', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1938, 'Janaúba' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1939, 'Januária', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1940, 'Japaraíba' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1941, 'Japonvar', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1942, 'Jeceaba' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1943, 'Jenipapo de Minas', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1944, 'Jequeri' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1945, 'Jequitaí', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1946, 'Jequitibá' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1947, 'Jequitinhonha', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1948, 'Jesuânia', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1949, 'Joaíma', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1950, 'Joanésia', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1951, 'João Monlevade' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1952, 'João Pinheiro', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1953, 'Joaquim Felício', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1954, 'Jordânia', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1955, 'José Gonçalves de Minas', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1956, 'José Raydan' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1957, 'Josenópolis' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1958, 'Juatuba' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1959, 'Juiz de Fora', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1960, 'Juramento' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1961, 'Juruaia' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1962, 'Juvenília' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1963, 'Ladainha', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1964, 'Lagamar' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1965, 'Lagoa da Prata' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1966, 'Lagoa dos Patos', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1967, 'Lagoa Dourada', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1968, 'Lagoa Formosa', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1969, 'Lagoa Grande', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1970, 'Lagoa Santa' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1971, 'Lajinha' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1972, 'Lambari' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1973, 'Lamim', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1974, 'Laranjal', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1975, 'Lassance', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1976, 'Lavras', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1977, 'Leandro Ferreira' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1978, 'Leme do Prado', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1979, 'Leopoldina', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1980, 'Liberdade' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1981, 'Lima Duarte' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1982, 'Limeira do Oeste' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1983, 'Lontra', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1984, 'Luisburgo' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1985, 'Luislândia', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1986, 'Luminárias', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1987, 'Luz', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1988, 'Machacalis', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1989, 'Machado' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1990, 'Madre de Deus de Minas' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1991, 'Malacacheta' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1992, 'Mamonas' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1993, 'Manga', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1994, 'Manhuaçu', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1995, 'Manhumirim', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1996, 'Mantena' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1997, 'Mar de Espanha' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1998, 'Maravilhas', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (1999, 'Maria da Fé' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2000, 'Mariana' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2001, 'Marilac' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2002, 'Mário Campos', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2003, 'Maripá de Minas', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2004, 'Marliéria' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2005, 'Marmelópolis', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2006, 'Martinho Campos', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2007, 'Martins Soares' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2008, 'Mata Verde', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2009, 'Materlândia' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2010, 'Mateus Leme' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2011, 'Mathias Lobato' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2012, 'Matias Barbosa' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2013, 'Matias Cardoso' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2014, 'Matipó', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2015, 'Mato Verde', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2016, 'Matozinhos', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2017, 'Matutina', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2018, 'Medeiros', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2019, 'Medina', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2020, 'Mendes Pimentel', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2021, 'Mercês', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2022, 'Mesquita', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2023, 'Minas Novas' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2024, 'Minduri' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2025, 'Mirabela', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2026, 'Miradouro' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2027, 'Miraí', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2028, 'Miravânia' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2029, 'Moeda', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2030, 'Moema', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2031, 'Monjolos', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2032, 'Monsenhor Paulo', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2033, 'Montalvânia' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2034, 'Monte Alegre de Minas', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2035, 'Monte Azul', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2036, 'Monte Belo', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2037, 'Monte Carmelo', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2038, 'Monte Formoso', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2039, 'Monte Santo de Minas' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2040, 'Monte Sião', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2041, 'Montes Claros', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2042, 'Montezuma' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2043, 'Morada Nova de Minas' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2044, 'Morro da Garça' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2045, 'Morro do Pilar' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2046, 'Munhoz', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2047, 'Muriaé', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2048, 'Mutum', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2049, 'Muzambinho', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2050, 'Nacip Raydan', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2051, 'Nanuque' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2052, 'Naque', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2053, 'Natalândia', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2054, 'Natércia', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2055, 'Nazareno', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2056, 'Nepomuceno', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2057, 'Ninheira', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2058, 'Nova Belém', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2059, 'Nova Era', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2060, 'Nova Lima' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2061, 'Nova Módica' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2062, 'Nova Ponte', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2063, 'Nova Porteirinha' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2064, 'Nova Resende', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2065, 'Nova Serrana', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2066, 'Nova União', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2067, 'Novo Cruzeiro', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2068, 'Novo Oriente de Minas', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2069, 'Novorizonte' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2070, 'Olaria', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2071, 'Olhos-d`Água', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2072, 'Olímpio Noronha', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2073, 'Oliveira', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2074, 'Oliveira Fortes', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2075, 'Onça de Pitangui' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2076, 'Oratórios' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2077, 'Orizânia', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2078, 'Ouro Branco' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2079, 'Ouro Fino' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2080, 'Ouro Preto', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2081, 'Ouro Verde de Minas', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2082, 'Padre Carvalho' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2083, 'Padre Paraíso', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2084, 'Pai Pedro' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2085, 'Paineiras' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2086, 'Pains', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2087, 'Paiva', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2088, 'Palma', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2089, 'Palmópolis', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2090, 'Papagaios' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2091, 'Pará de Minas', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2092, 'Paracatu', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2093, 'Paraguaçu' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2094, 'Paraisópolis', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2095, 'Paraopeba' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2096, 'Passa Quatro', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2097, 'Passa Tempo' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2098, 'Passa-Vinte' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2099, 'Passabém', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2100, 'Passos', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2101, 'Patis', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2102, 'Patos de Minas' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2103, 'Patrocínio', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2104, 'Patrocínio do Muriaé' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2105, 'Paula Cândido', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2106, 'Paulistas' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2107, 'Pavão', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2108, 'Peçanha' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2109, 'Pedra Azul', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2110, 'Pedra Bonita', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2111, 'Pedra do Anta', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2112, 'Pedra do Indaiá', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2113, 'Pedra Dourada', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2114, 'Pedralva', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2115, 'Pedras de Maria da Cruz', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2116, 'Pedrinópolis', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2117, 'Pedro Leopoldo' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2118, 'Pedro Teixeira' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2119, 'Pequeri' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2120, 'Pequi', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2121, 'Perdigão', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2122, 'Perdizes', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2123, 'Perdões' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2124, 'Periquito' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2125, 'Pescador', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2126, 'Piau' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2127, 'Piedade de Caratinga' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2128, 'Piedade de Ponte Nova', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2129, 'Piedade do Rio Grande', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2130, 'Piedade dos Gerais', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2131, 'Pimenta' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2132, 'Pingo-d`Água', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2133, 'Pintópolis', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2134, 'Piracema', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2135, 'Pirajuba', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2136, 'Piranga' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2137, 'Piranguçu' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2138, 'Piranguinho' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2139, 'Pirapetinga' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2140, 'Pirapora', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2141, 'Piraúba' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2142, 'Pitangui', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2143, 'Piumhi', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2144, 'Planura' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2145, 'Poço Fundo', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2146, 'Poços de Caldas', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2147, 'Pocrane' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2148, 'Pompéu', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2149, 'Ponte Nova', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2150, 'Ponto Chique', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2151, 'Ponto dos Volantes', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2152, 'Porteirinha' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2153, 'Porto Firme' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2154, 'Poté' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2155, 'Pouso Alegre', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2156, 'Pouso Alto', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2157, 'Prados', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2158, 'Prata', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2159, 'Pratápolis', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2160, 'Pratinha', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2161, 'Presidente Bernardes' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2162, 'Presidente Juscelino' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2163, 'Presidente Kubitschek', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2164, 'Presidente Olegário', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2165, 'Prudente de Morais', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2166, 'Quartel Geral', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2167, 'Queluzito' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2168, 'Raposos' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2169, 'Raul Soares' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2170, 'Recreio' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2171, 'Reduto', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2172, 'Resende Costa', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2173, 'Resplendor', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2174, 'Ressaquinha' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2175, 'Riachinho' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2176, 'Riacho dos Machados', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2177, 'Ribeirão das Neves', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2178, 'Ribeirão Vermelho', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2179, 'Rio Acima' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2180, 'Rio Casca' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2181, 'Rio do Prado', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2182, 'Rio Doce', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2183, 'Rio Espera', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2184, 'Rio Manso' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2185, 'Rio Novo', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2186, 'Rio Paranaíba', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2187, 'Rio Pardo de Minas', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2188, 'Rio Piracicaba' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2189, 'Rio Pomba' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2190, 'Rio Preto' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2191, 'Rio Vermelho', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2192, 'Ritápolis' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2193, 'Rochedo de Minas' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2194, 'Rodeiro' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2195, 'Romaria' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2196, 'Rosário da Limeira', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2197, 'Rubelita', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2198, 'Rubim', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2199, 'Sabará', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2200, 'Sabinópolis' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2201, 'Sacramento', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2202, 'Salinas' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2203, 'Salto da Divisa', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2204, 'Santa Bárbara', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2205, 'Santa Bárbara do Leste' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2206, 'Santa Bárbara do Monte Verde', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2207, 'Santa Bárbara do Tugúrio' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2208, 'Santa Cruz de Minas', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2209, 'Santa Cruz de Salinas', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2210, 'Santa Cruz do Escalvado', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2211, 'Santa Efigênia de Minas', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2212, 'Santa Fé de Minas', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2213, 'Santa Helena de Minas', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2214, 'Santa Juliana', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2215, 'Santa Luzia' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2216, 'Santa Margarida', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2217, 'Santa Maria de Itabira' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2218, 'Santa Maria do Salto' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2219, 'Santa Maria do Suaçuí', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2220, 'Santa Rita de Caldas' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2221, 'Santa Rita de Ibitipoca', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2222, 'Santa Rita de Jacutinga', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2223, 'Santa Rita de Minas', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2224, 'Santa Rita do Itueto' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2225, 'Santa Rita do Sapucaí', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2226, 'Santa Rosa da Serra', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2227, 'Santa Vitória', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2228, 'Santana da Vargem', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2229, 'Santana de Cataguases', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2230, 'Santana de Pirapama', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2231, 'Santana do Deserto', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2232, 'Santana do Garambéu', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2233, 'Santana do Jacaré', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2234, 'Santana do Manhuaçu', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2235, 'Santana do Paraíso', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2236, 'Santana do Riacho', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2237, 'Santana dos Montes', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2238, 'Santo Antônio do Amparo', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2239, 'Santo Antônio do Aventureiro', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2240, 'Santo Antônio do Grama' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2241, 'Santo Antônio do Itambé', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2242, 'Santo Antônio do Jacinto' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2243, 'Santo Antônio do Monte' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2244, 'Santo Antônio do Retiro', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2245, 'Santo Antônio do Rio Abaixo' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2246, 'Santo Hipólito' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2247, 'Santos Dumont', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2248, 'São Bento Abade', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2249, 'São Brás do Suaçuí', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2250, 'São Domingos das Dores' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2251, 'São Domingos do Prata', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2252, 'São Félix de Minas', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2253, 'São Francisco', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2254, 'São Francisco de Paula' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2255, 'São Francisco de Sales' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2256, 'São Francisco do Glória', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2257, 'São Geraldo' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2258, 'São Geraldo da Piedade' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2259, 'São Geraldo do Baixio', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2260, 'São Gonçalo do Abaeté', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2261, 'São Gonçalo do Pará', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2262, 'São Gonçalo do Rio Abaixo', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2263, 'São Gonçalo do Rio Preto' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2264, 'São Gonçalo do Sapucaí' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2265, 'São Gotardo' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2266, 'São João Batista do Glória', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2267, 'São João da Lagoa', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2268, 'São João da Mata' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2269, 'São João da Ponte', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2270, 'São João das Missões' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2271, 'São João del Rei' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2272, 'São João do Manhuaçu' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2273, 'São João do Manteninha' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2274, 'São João do Oriente', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2275, 'São João do Pacuí', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2276, 'São João do Paraíso', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2277, 'São João Evangelista' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2278, 'São João Nepomuceno', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2279, 'São Joaquim de Bicas' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2280, 'São José da Barra', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2281, 'São José da Lapa' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2282, 'São José da Safira', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2283, 'São José da Varginha' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2284, 'São José do Alegre', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2285, 'São José do Divino', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2286, 'São José do Goiabal', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2287, 'São José do Jacuri', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2288, 'São José do Mantimento' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2289, 'São Lourenço', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2290, 'São Miguel do Anta', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2291, 'São Pedro da União', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2292, 'São Pedro do Suaçuí', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2293, 'São Pedro dos Ferros' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2294, 'São Romão' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2295, 'São Roque de Minas', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2296, 'São Sebastião da Bela Vista' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2297, 'São Sebastião da Vargem Alegre', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2298, 'São Sebastião do Anta', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2299, 'São Sebastião do Maranhão', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2300, 'São Sebastião do Oeste' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2301, 'São Sebastião do Paraíso' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2302, 'São Sebastião do Rio Preto', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2303, 'São Sebastião do Rio Verde', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2304, 'São Thomé das Letras' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2305, 'São Tiago' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2306, 'São Tomás de Aquino', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2307, 'São Vicente de Minas' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2308, 'Sapucaí-Mirim', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2309, 'Sardoá', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2310, 'Sarzedo' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2311, 'Sem-Peixe' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2312, 'Senador Amaral' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2313, 'Senador Cortes' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2314, 'Senador Firmino', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2315, 'Senador José Bento', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2316, 'Senador Modestino Gonçalves' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2317, 'Senhora de Oliveira', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2318, 'Senhora do Porto' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2319, 'Senhora dos Remédios' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2320, 'Sericita', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2321, 'Seritinga' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2322, 'Serra Azul de Minas', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2323, 'Serra da Saudade' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2324, 'Serra do Salitre' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2325, 'Serra dos Aimorés', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2326, 'Serrania', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2327, 'Serranópolis de Minas', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2328, 'Serranos', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2329, 'Serro', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2330, 'Sete Lagoas' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2331, 'Setubinha' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2332, 'Silveirânia' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2333, 'Silvianópolis', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2334, 'Simão Pereira', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2335, 'Simonésia' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2336, 'Sobrália', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2337, 'Soledade de Minas', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2338, 'Tabuleiro' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2339, 'Taiobeiras', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2340, 'Taparuba', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2341, 'Tapira', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2342, 'Tapiraí' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2343, 'Taquaraçu de Minas', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2344, 'Tarumirim' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2345, 'Teixeiras' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2346, 'Teófilo Otoni', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2347, 'Timóteo' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2348, 'Tiradentes', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2349, 'Tiros', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2350, 'Tocantins' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2351, 'Tocos do Moji', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2352, 'Toledo', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2353, 'Tombos', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2354, 'Três Corações', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2355, 'Três Marias' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2356, 'Três Pontas' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2357, 'Tumiritinga' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2358, 'Tupaciguara' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2359, 'Turmalina' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2360, 'Turvolândia' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2361, 'Ubá', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2362, 'Ubaí' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2363, 'Ubaporanga', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2364, 'Uberaba' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2365, 'Uberlândia', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2366, 'Umburatiba', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2367, 'Unaí' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2368, 'União de Minas' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2369, 'Uruana de Minas', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2370, 'Urucânia', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2371, 'Urucuia' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2372, 'Vargem Alegre', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2373, 'Vargem Bonita', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2374, 'Vargem Grande do Rio Pardo', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2375, 'Varginha', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2376, 'Varjão de Minas', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2377, 'Várzea da Palma', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2378, 'Varzelândia' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2379, 'Vazante' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2380, 'Verdelândia' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2381, 'Veredinha' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2382, 'Veríssimo' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2383, 'Vermelho Novo', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2384, 'Vespasiano', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2385, 'Viçosa', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2386, 'Vieiras' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2387, 'Virgem da Lapa' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2388, 'Virgínia', 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2389, 'Virginópolis', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2390, 'Virgolândia' , 06 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2391, 'Visconde do Rio Branco' , 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2392, 'Volta Grande', 06);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2393, 'Wenceslau Braz' , 06);

INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2394, 'Abaetetuba', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2395, 'Abel Figueiredo', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2396, 'Acará', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2397, 'Afuá' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2398, 'Água Azul do Norte', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2399, 'Alenquer', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2400, 'Almeirim', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2401, 'Altamira', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2402, 'Anajás', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2403, 'Ananindeua', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2404, 'Anapu', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2405, 'Augusto Corrêa' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2406, 'Aurora do Pará' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2407, 'Aveiro', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2408, 'Bagre', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2409, 'Baião', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2410, 'Bannach' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2411, 'Barcarena' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2412, 'Belém', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2413, 'Belterra', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2414, 'Benevides' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2415, 'Bom Jesus do Tocantins' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2416, 'Bonito', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2417, 'Bragança', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2418, 'Brasil Novo' , 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2419, 'Brejo Grande do Araguaia' , 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2420, 'Breu Branco' , 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2421, 'Breves', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2422, 'Bujaru', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2423, 'Cachoeira do Arari', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2424, 'Cachoeira do Piriá', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2425, 'Cametá', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2426, 'Canaã dos Carajás', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2427, 'Capanema', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2428, 'Capitão Poço', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2429, 'Castanhal' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2430, 'Chaves', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2431, 'Colares' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2432, 'Conceição do Araguaia', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2433, 'Concórdia do Pará', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2434, 'Cumaru do Norte', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2435, 'Curionópolis', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2436, 'Curralinho', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2437, 'Curuá', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2438, 'Curuçá', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2439, 'Dom Eliseu', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2440, 'Eldorado dos Carajás' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2441, 'Faro' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2442, 'Floresta do Araguaia' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2443, 'Garrafão do Norte', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2444, 'Goianésia do Pará', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2445, 'Gurupá', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2446, 'Igarapé-Açu' , 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2447, 'Igarapé-Miri', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2448, 'Inhangapi' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2449, 'Ipixuna do Pará', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2450, 'Irituia' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2451, 'Itaituba', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2452, 'Itupiranga', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2453, 'Jacareacanga', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2454, 'Jacundá' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2455, 'Juruti', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2456, 'Limoeiro do Ajuru', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2457, 'Mãe do Rio', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2458, 'Magalhães Barata' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2459, 'Marabá', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2460, 'Maracanã', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2461, 'Marapanim' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2462, 'Marituba', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2463, 'Medicilândia', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2464, 'Melgaço' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2465, 'Mocajuba', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2466, 'Moju' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2467, 'Monte Alegre', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2468, 'Muaná', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2469, 'Nova Esperança do Piriá', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2470, 'Nova Ipixuna', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2471, 'Nova Timboteua' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2472, 'Novo Progresso' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2473, 'Novo Repartimento', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2474, 'Óbidos', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2475, 'Oeiras do Pará' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2476, 'Oriximiná' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2477, 'Ourém', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2478, 'Ourilândia do Norte', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2479, 'Pacajá', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2480, 'Palestina do Pará', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2481, 'Paragominas' , 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2482, 'Parauapebas' , 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2483, 'Pau d`Arco', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2484, 'Peixe-Boi' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2485, 'Piçarra' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2486, 'Placas', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2487, 'Ponta de Pedras', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2488, 'Portel', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2489, 'Porto de Moz', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2490, 'Prainha' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2491, 'Primavera' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2492, 'Quatipuru' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2493, 'Redenção', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2494, 'Rio Maria' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2495, 'Rondon do Pará' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2496, 'Rurópolis' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2497, 'Salinópolis' , 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2498, 'Salvaterra', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2499, 'Santa Bárbara do Pará', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2500, 'Santa Cruz do Arari', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2501, 'Santa Isabel do Pará' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2502, 'Santa Luzia do Pará', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2503, 'Santa Maria das Barreiras', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2504, 'Santa Maria do Pará', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2505, 'Santana do Araguaia', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2506, 'Santarém', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2507, 'Santarém Novo', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2508, 'Santo Antônio do Tauá', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2509, 'São Caetano de Odivelas', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2510, 'São Domingos do Araguaia' , 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2511, 'São Domingos do Capim', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2512, 'São Félix do Xingu', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2513, 'São Francisco do Pará', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2514, 'São Geraldo do Araguaia', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2515, 'São João da Ponta', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2516, 'São João de Pirabas', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2517, 'São João do Araguaia' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2518, 'São Miguel do Guamá', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2519, 'São Sebastião da Boa Vista', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2520, 'Sapucaia', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2521, 'Senador José Porfírio', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2522, 'Soure', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2523, 'Tailândia' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2524, 'Terra Alta', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2525, 'Terra Santa' , 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2526, 'Tomé-Açu', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2527, 'Tracuateua', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2528, 'Trairão' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2529, 'Tucumã', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2530, 'Tucuruí' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2531, 'Ulianópolis' , 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2532, 'Uruará', 19 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2533, 'Vigia', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2534, 'Viseu', 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2535, 'Vitória do Xingu' , 19);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2536, 'Xinguara', 19 );

INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2537, 'Água Branca' , 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2538, 'Aguiar', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2539, 'Alagoa Grande', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2540, 'Alagoa Nova' , 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2541, 'Alagoinha' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2542, 'Alcantil', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2543, 'Algodão de Jandaíra', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2544, 'Alhandra', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2545, 'Amparo', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2546, 'Aparecida' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2547, 'Araçagi' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2548, 'Arara', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2549, 'Araruna' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2550, 'Areia', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2551, 'Areia de Baraúnas', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2552, 'Areial', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2553, 'Aroeiras', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2554, 'Assunção', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2555, 'Baía da Traição', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2556, 'Bananeiras', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2557, 'Baraúna' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2558, 'Barra de Santa Rosa', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2559, 'Barra de Santana' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2560, 'Barra de São Miguel', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2561, 'Bayeux', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2562, 'Belém', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2563, 'Belém do Brejo do Cruz' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2564, 'Bernardino Batista', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2565, 'Boa Ventura' , 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2566, 'Boa Vista' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2567, 'Bom Jesus' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2568, 'Bom Sucesso' , 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2569, 'Bonito de Santa Fé', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2570, 'Boqueirão' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2571, 'Borborema' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2572, 'Brejo do Cruz', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2573, 'Brejo dos Santos' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2574, 'Caaporã' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2575, 'Cabaceiras', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2576, 'Cabedelo', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2577, 'Cachoeira dos Índios' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2578, 'Cacimba de Areia' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2579, 'Cacimba de Dentro', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2580, 'Cacimbas', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2581, 'Caiçara' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2582, 'Cajazeiras', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2583, 'Cajazeirinhas', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2584, 'Caldas Brandão' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2585, 'Camalaú' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2586, 'Campina Grande' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2587, 'Campo de Santana' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2588, 'Capim', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2589, 'Caraúbas', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2590, 'Carrapateira', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2591, 'Casserengue' , 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2592, 'Catingueira' , 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2593, 'Catolé do Rocha', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2594, 'Caturité', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2595, 'Conceição' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2596, 'Condado' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2597, 'Conde', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2598, 'Congo', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2599, 'Coremas' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2600, 'Coxixola', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2601, 'Cruz do Espírito Santo' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2602, 'Cubati', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2603, 'Cuité', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2604, 'Cuité de Mamanguape', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2605, 'Cuitegi' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2606, 'Curral de Cima' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2607, 'Curral Velho', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2608, 'Damião', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2609, 'Desterro', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2610, 'Diamante', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2611, 'Dona Inês' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2612, 'Duas Estradas', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2613, 'Emas' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2614, 'Esperança' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2615, 'Fagundes', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2616, 'Frei Martinho', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2617, 'Gado Bravo', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2618, 'Guarabira' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2619, 'Gurinhém', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2620, 'Gurjão', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2621, 'Ibiara', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2622, 'Igaracy' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2623, 'Imaculada' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2624, 'Ingá' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2625, 'Itabaiana' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2626, 'Itaporanga', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2627, 'Itapororoca' , 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2628, 'Itatuba' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2629, 'Jacaraú' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2630, 'Jericó', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2631, 'João Pessoa' , 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2632, 'Juarez Távora', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2633, 'Juazeirinho' , 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2634, 'Junco do Seridó', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2635, 'Juripiranga' , 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2636, 'Juru' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2637, 'Lagoa', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2638, 'Lagoa de Dentro', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2639, 'Lagoa Seca', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2640, 'Lastro', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2641, 'Livramento', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2642, 'Logradouro', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2643, 'Lucena', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2644, 'Mãe d`Água', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2645, 'Malta', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2646, 'Mamanguape', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2647, 'Manaíra' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2648, 'Marcação', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2649, 'Mari' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2650, 'Marizópolis' , 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2651, 'Massaranduba', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2652, 'Mataraca', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2653, 'Matinhas', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2654, 'Mato Grosso' , 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2655, 'Maturéia', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2656, 'Mogeiro' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2657, 'Montadas', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2658, 'Monte Horebe', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2659, 'Monteiro', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2660, 'Mulungu' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2661, 'Natuba', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2662, 'Nazarezinho' , 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2663, 'Nova Floresta', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2664, 'Nova Olinda' , 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2665, 'Nova Palmeira', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2666, 'Olho d`Água' , 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2667, 'Olivedos', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2668, 'Ouro Velho', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2669, 'Parari', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2670, 'Passagem', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2671, 'Patos', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2672, 'Paulista', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2673, 'Pedra Branca', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2674, 'Pedra Lavrada', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2675, 'Pedras de Fogo' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2676, 'Pedro Régis' , 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2677, 'Piancó', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2678, 'Picuí', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2679, 'Pilar', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2680, 'Pilões', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2681, 'Pilõezinhos' , 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2682, 'Pirpirituba' , 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2683, 'Pitimbu' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2684, 'Pocinhos', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2685, 'Poço Dantas' , 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2686, 'Poço de José de Moura', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2687, 'Pombal', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2688, 'Prata', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2689, 'Princesa Isabel', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2690, 'Puxinanã', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2691, 'Queimadas' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2692, 'Quixabá' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2693, 'Remígio' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2694, 'Riachão' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2695, 'Riachão do Bacamarte' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2696, 'Riachão do Poço', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2697, 'Riacho de Santo Antônio', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2698, 'Riacho dos Cavalos', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2699, 'Rio Tinto' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2700, 'Salgadinho', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2701, 'Salgado de São Félix' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2702, 'Santa Cecília', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2703, 'Santa Cruz', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2704, 'Santa Helena', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2705, 'Santa Inês', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2706, 'Santa Luzia' , 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2707, 'Santa Rita', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2708, 'Santa Teresinha', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2709, 'Santana de Mangueira' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2710, 'Santana dos Garrotes' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2711, 'Santarém', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2712, 'Santo André' , 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2713, 'São Bentinho', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2714, 'São Bento' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2715, 'São Domingos de Pombal' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2716, 'São Domingos do Cariri' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2717, 'São Francisco', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2718, 'São João do Cariri', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2719, 'São João do Rio do Peixe' , 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2720, 'São João do Tigre', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2721, 'São José da Lagoa Tapada' , 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2722, 'São José de Caiana', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2723, 'São José de Espinharas' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2724, 'São José de Piranhas' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2725, 'São José de Princesa' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2726, 'São José do Bonfim', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2727, 'São José do Brejo do Cruz', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2728, 'São José do Sabugi', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2729, 'São José dos Cordeiros' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2730, 'São José dos Ramos', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2731, 'São Mamede', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2732, 'São Miguel de Taipu', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2733, 'São Sebastião de Lagoa de Roça', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2734, 'São Sebastião do Umbuzeiro', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2735, 'Sapé' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2736, 'Seridó', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2737, 'Serra Branca', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2738, 'Serra da Raiz', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2739, 'Serra Grande', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2740, 'Serra Redonda', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2741, 'Serraria', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2742, 'Sertãozinho' , 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2743, 'Sobrado' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2744, 'Solânea' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2745, 'Soledade', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2746, 'Sossêgo' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2747, 'Sousa', 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2748, 'Sumé' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2749, 'Taperoá' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2750, 'Tavares' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2751, 'Teixeira', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2752, 'Tenório' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2753, 'Triunfo' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2754, 'Uiraúna' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2755, 'Umbuzeiro' , 18);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2756, 'Várzea', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2757, 'Vieirópolis' , 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2758, 'Vista Serrana', 18 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2759, 'Zabelê', 18 );

INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2760, 'Abatiá', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2761, 'Adrianópolis', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2762, 'Agudos do Sul', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2763, 'Almirante Tamandaré', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2764, 'Altamira do Paraná', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2765, 'Alto Paraná' , 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2766, 'Alto Piquiri', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2767, 'Altônia' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2768, 'Alvorada do Sul', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2769, 'Amaporã' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2770, 'Ampére', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2771, 'Anahy', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2772, 'Andirá', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2773, 'Ângulo', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2774, 'Antonina', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2775, 'Antônio Olinto' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2776, 'Apucarana' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2777, 'Arapongas' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2778, 'Arapoti' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2779, 'Arapuã', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2780, 'Araruna' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2781, 'Araucária' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2782, 'Ariranha do Ivaí' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2783, 'Assaí', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2784, 'Assis Chateaubriand', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2785, 'Astorga' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2786, 'Atalaia' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2787, 'Balsa Nova', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2788, 'Bandeirantes', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2789, 'Barbosa Ferraz' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2790, 'Barra do Jacaré', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2791, 'Barracão', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2792, 'Bela Vista da Caroba' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2793, 'Bela Vista do Paraíso', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2794, 'Bituruna', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2795, 'Boa Esperança', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2796, 'Boa Esperança do Iguaçu', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2797, 'Boa Ventura de São Roque' , 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2798, 'Boa Vista da Aparecida' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2799, 'Bocaiúva do Sul', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2800, 'Bom Jesus do Sul' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2801, 'Bom Sucesso' , 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2802, 'Bom Sucesso do Sul', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2803, 'Borrazópolis', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2804, 'Braganey', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2805, 'Brasilândia do Sul', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2806, 'Cafeara' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2807, 'Cafelândia', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2808, 'Cafezal do Sul' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2809, 'Califórnia', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2810, 'Cambará' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2811, 'Cambé', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2812, 'Cambira' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2813, 'Campina da Lagoa' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2814, 'Campina do Simão' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2815, 'Campina Grande do Sul', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2816, 'Campo Bonito', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2817, 'Campo do Tenente' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2818, 'Campo Largo' , 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2819, 'Campo Magro' , 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2820, 'Campo Mourão', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2821, 'Cândido de Abreu' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2822, 'Candói', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2823, 'Cantagalo' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2824, 'Capanema', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2825, 'Capitão Leônidas Marques' , 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2826, 'Carambeí', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2827, 'Carlópolis', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2828, 'Cascavel', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2829, 'Castro', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2830, 'Catanduvas', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2831, 'Centenário do Sul', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2832, 'Cerro Azul', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2833, 'Céu Azul', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2834, 'Chopinzinho' , 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2835, 'Cianorte', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2836, '`cidades` Gaúcha' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2837, 'Clevelândia' , 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2838, 'Colombo' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2839, 'Colorado', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2840, 'Congonhinhas', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2841, 'Conselheiro Mairinck' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2842, 'Contenda', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2843, 'Corbélia', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2844, 'Cornélio Procópio', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2845, 'Coronel Domingos Soares', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2846, 'Coronel Vivida' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2847, 'Corumbataí do Sul', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2848, 'Cruz Machado', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2849, 'Cruzeiro do Iguaçu', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2850, 'Cruzeiro do Oeste', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2851, 'Cruzeiro do Sul', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2852, 'Cruzmaltina' , 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2853, 'Curitiba', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2854, 'Curiúva' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2855, 'Diamante d`Oeste' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2856, 'Diamante do Norte', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2857, 'Diamante do Sul', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2858, 'Dois Vizinhos', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2859, 'Douradina' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2860, 'Doutor Camargo' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2861, 'Doutor Ulysses' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2862, 'Enéas Marques', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2863, 'Engenheiro Beltrão', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2864, 'Entre Rios do Oeste', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2865, 'Esperança Nova' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2866, 'Espigão Alto do Iguaçu' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2867, 'Farol', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2868, 'Faxinal' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2869, 'Fazenda Rio Grande', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2870, 'Fênix', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2871, 'Fernandes Pinheiro', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2872, 'Figueira', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2873, 'Flor da Serra do Sul' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2874, 'Floraí', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2875, 'Floresta', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2876, 'Florestópolis', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2877, 'Flórida' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2878, 'Formosa do Oeste' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2879, 'Foz do Iguaçu', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2880, 'Foz do Jordão', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2881, 'Francisco Alves', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2882, 'Francisco Beltrão', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2883, 'General Carneiro' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2884, 'Godoy Moreira', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2885, 'Goioerê' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2886, 'Goioxim' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2887, 'Grandes Rios', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2888, 'Guaíra', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2889, 'Guairaçá', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2890, 'Guamiranga', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2891, 'Guapirama' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2892, 'Guaporema' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2893, 'Guaraci' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2894, 'Guaraniaçu', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2895, 'Guarapuava', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2896, 'Guaraqueçaba', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2897, 'Guaratuba' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2898, 'Honório Serpa', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2899, 'Ibaiti', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2900, 'Ibema', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2901, 'Ibiporã' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2902, 'Icaraíma', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2903, 'Iguaraçu', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2904, 'Iguatu', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2905, 'Imbaú', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2906, 'Imbituva', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2907, 'Inácio Martins' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2908, 'Inajá', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2909, 'Indianópolis', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2910, 'Ipiranga', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2911, 'Iporã', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2912, 'Iracema do Oeste' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2913, 'Irati', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2914, 'Iretama' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2915, 'Itaguajé', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2916, 'Itaipulândia', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2917, 'Itambaracá', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2918, 'Itambé', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2919, 'Itapejara d`Oeste', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2920, 'Itaperuçu' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2921, 'Itaúna do Sul', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2922, 'Ivaí' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2923, 'Ivaiporã', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2924, 'Ivaté', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2925, 'Ivatuba' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2926, 'Jaboti', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2927, 'Jacarezinho' , 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2928, 'Jaguapitã' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2929, 'Jaguariaíva' , 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2930, 'Jandaia do Sul' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2931, 'Janiópolis', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2932, 'Japira', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2933, 'Japurá', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2934, 'Jardim Alegre', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2935, 'Jardim Olinda', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2936, 'Jataizinho', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2937, 'Jesuítas', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2938, 'Joaquim Távora' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2939, 'Jundiaí do Sul' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2940, 'Juranda' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2941, 'Jussara' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2942, 'Kaloré', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2943, 'Lapa' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2944, 'Laranjal', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2945, 'Laranjeiras do Sul', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2946, 'Leópolis', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2947, 'Lidianópolis', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2948, 'Lindoeste' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2949, 'Loanda', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2950, 'Lobato', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2951, 'Londrina', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2952, 'Luiziana', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2953, 'Lunardelli', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2954, 'Lupionópolis', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2955, 'Mallet', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2956, 'Mamborê' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2957, 'Mandaguaçu', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2958, 'Mandaguari', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2959, 'Mandirituba' , 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2960, 'Manfrinópolis', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2961, 'Mangueirinha', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2962, 'Manoel Ribas', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2963, 'Marechal Cândido Rondon', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2964, 'Maria Helena', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2965, 'Marialva', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2966, 'Marilândia do Sul', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2967, 'Marilena', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2968, 'Mariluz' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2969, 'Maringá' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2970, 'Mariópolis', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2971, 'Maripá', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2972, 'Marmeleiro', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2973, 'Marquinho' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2974, 'Marumbi' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2975, 'Matelândia', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2976, 'Matinhos', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2977, 'Mato Rico' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2978, 'Mauá da Serra', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2979, 'Medianeira', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2980, 'Mercedes', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2981, 'Mirador' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2982, 'Miraselva' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2983, 'Missal', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2984, 'Moreira Sales', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2985, 'Morretes', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2986, 'Munhoz de Melo' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2987, 'Nossa Senhora das Graças' , 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2988, 'Nova Aliança do Ivaí' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2989, 'Nova América da Colina' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2990, 'Nova Aurora' , 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2991, 'Nova Cantu', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2992, 'Nova Esperança' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2993, 'Nova Esperança do Sudoeste', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2994, 'Nova Fátima' , 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2995, 'Nova Laranjeiras' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2996, 'Nova Londrina', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2997, 'Nova Olímpia', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2998, 'Nova Prata do Iguaçu' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (2999, 'Nova Santa Bárbara', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3000, 'Nova Santa Rosa', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3001, 'Nova Tebas', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3002, 'Novo Itacolomi' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3003, 'Ortigueira', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3004, 'Ourizona', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3005, 'Ouro Verde do Oeste', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3006, 'Paiçandu', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3007, 'Palmas', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3008, 'Palmeira', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3009, 'Palmital', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3010, 'Palotina', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3011, 'Paraíso do Norte' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3012, 'Paranacity', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3013, 'Paranaguá' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3014, 'Paranapoema' , 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3015, 'Paranavaí' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3016, 'Pato Bragado', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3017, 'Pato Branco' , 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3018, 'Paula Freitas', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3019, 'Paulo Frontin', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3020, 'Peabiru' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3021, 'Perobal' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3022, 'Pérola', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3023, 'Pérola d`Oeste' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3024, 'Piên' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3025, 'Pinhais' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3026, 'Pinhal de São Bento', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3027, 'Pinhalão', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3028, 'Pinhão', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3029, 'Piraí do Sul', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3030, 'Piraquara' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3031, 'Pitanga' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3032, 'Pitangueiras', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3033, 'Planaltina do Paraná' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3034, 'Planalto', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3035, 'Ponta Grossa', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3036, 'Pontal do Paraná' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3037, 'Porecatu', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3038, 'Porto Amazonas' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3039, 'Porto Barreiro' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3040, 'Porto Rico', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3041, 'Porto Vitória', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3042, 'Prado Ferreira' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3043, 'Pranchita' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3044, 'Presidente Castelo Branco', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3045, 'Primeiro de Maio' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3046, 'Prudentópolis', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3047, 'Quarto Centenário', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3048, 'Quatiguá', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3049, 'Quatro Barras', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3050, 'Quatro Pontes', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3051, 'Quedas do Iguaçu' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3052, 'Querência do Norte', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3053, 'Quinta do Sol', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3054, 'Quitandinha' , 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3055, 'Ramilândia', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3056, 'Rancho Alegre', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3057, 'Rancho Alegre d`Oeste', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3058, 'Realeza' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3059, 'Rebouças', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3060, 'Renascença', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3061, 'Reserva' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3062, 'Reserva do Iguaçu', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3063, 'Ribeirão Claro' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3064, 'Ribeirão do Pinhal', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3065, 'Rio Azul', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3066, 'Rio Bom' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3067, 'Rio Bonito do Iguaçu' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3068, 'Rio Branco do Ivaí', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3069, 'Rio Branco do Sul', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3070, 'Rio Negro' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3071, 'Rolândia', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3072, 'Roncador', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3073, 'Rondon', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3074, 'Rosário do Ivaí', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3075, 'Sabáudia', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3076, 'Salgado Filho', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3077, 'Salto do Itararé' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3078, 'Salto do Lontra', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3079, 'Santa Amélia', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3080, 'Santa Cecília do Pavão' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3081, 'Santa Cruz de Monte Castelo' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3082, 'Santa Fé', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3083, 'Santa Helena', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3084, 'Santa Inês', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3085, 'Santa Isabel do Ivaí' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3086, 'Santa Izabel do Oeste', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3087, 'Santa Lúcia' , 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3088, 'Santa Maria do Oeste' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3089, 'Santa Mariana', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3090, 'Santa Mônica', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3091, 'Santa Tereza do Oeste', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3092, 'Santa Terezinha de Itaipu', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3093, 'Santana do Itararé', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3094, 'Santo Antônio da Platina' , 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3095, 'Santo Antônio do Caiuá' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3096, 'Santo Antônio do Paraíso' , 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3097, 'Santo Antônio do Sudoeste', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3098, 'Santo Inácio', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3099, 'São Carlos do Ivaí', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3100, 'São Jerônimo da Serra', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3101, 'São João', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3102, 'São João do Caiuá', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3103, 'São João do Ivaí' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3104, 'São João do Triunfo', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3105, 'São Jorge d`Oeste', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3106, 'São Jorge do Ivaí', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3107, 'São Jorge do Patrocínio', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3108, 'São José da Boa Vista', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3109, 'São José das Palmeiras' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3110, 'São José dos Pinhais' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3111, 'São Manoel do Paraná' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3112, 'São Mateus do Sul', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3113, 'São Miguel do Iguaçu' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3114, 'São Pedro do Iguaçu', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3115, 'São Pedro do Ivaí', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3116, 'São Pedro do Paraná', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3117, 'São Sebastião da Amoreira', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3118, 'São Tomé', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3119, 'Sapopema', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3120, 'Sarandi' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3121, 'Saudade do Iguaçu', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3122, 'Sengés', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3123, 'Serranópolis do Iguaçu' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3124, 'Sertaneja' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3125, 'Sertanópolis', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3126, 'Siqueira Campos', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3127, 'Sulina', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3128, 'Tamarana', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3129, 'Tamboara', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3130, 'Tapejara', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3131, 'Tapira', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3132, 'Teixeira Soares', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3133, 'Telêmaco Borba' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3134, 'Terra Boa' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3135, 'Terra Rica', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3136, 'Terra Roxa', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3137, 'Tibagi', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3138, 'Tijucas do Sul' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3139, 'Toledo', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3140, 'Tomazina', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3141, 'Três Barras do Paraná', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3142, 'Tunas do Paraná', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3143, 'Tuneiras do Oeste', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3144, 'Tupãssi' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3145, 'Turvo', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3146, 'Ubiratã' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3147, 'Umuarama', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3148, 'União da Vitória' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3149, 'Uniflor' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3150, 'Uraí' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3151, 'Ventania', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3152, 'Vera Cruz do Oeste', 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3153, 'Verê' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3154, 'Vila Alta' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3155, 'Virmond' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3156, 'Vitorino', 07 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3157, 'Wenceslau Braz' , 07);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3158, 'Xambrê', 07 );

INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3159, 'Abreu e Lima', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3160, 'Afogados da Ingazeira', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3161, 'Afrânio' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3162, 'Agrestina' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3163, 'Água Preta', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3164, 'Águas Belas' , 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3165, 'Alagoinha' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3166, 'Aliança' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3167, 'Altinho' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3168, 'Amaraji' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3169, 'Angelim' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3170, 'Araçoiaba' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3171, 'Araripina' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3172, 'Arcoverde' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3173, 'Barra de Guabiraba', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3174, 'Barreiros' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3175, 'Belém de Maria' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3176, 'Belém de São Francisco' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3177, 'Belo Jardim' , 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3178, 'Betânia' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3179, 'Bezerros', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3180, 'Bodocó', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3181, 'Bom Conselho', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3182, 'Bom Jardim', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3183, 'Bonito', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3184, 'Brejão', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3185, 'Brejinho', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3186, 'Brejo da Madre de Deus' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3187, 'Buenos Aires', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3188, 'Buíque', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3189, 'Cabo de Santo Agostinho', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3190, 'Cabrobó' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3191, 'Cachoeirinha', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3192, 'Caetés', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3193, 'Calçado' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3194, 'Calumbi' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3195, 'Camaragibe', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3196, 'Camocim de São Félix' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3197, 'Camutanga' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3198, 'Canhotinho', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3199, 'Capoeiras' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3200, 'Carnaíba', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3201, 'Carnaubeira da Penha' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3202, 'Carpina' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3203, 'Caruaru' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3204, 'Casinhas', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3205, 'Catende' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3206, 'Cedro', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3207, 'Chã de Alegria' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3208, 'Chã Grande', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3209, 'Condado' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3210, 'Correntes' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3211, 'Cortês', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3212, 'Cumaru', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3213, 'Cupira', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3214, 'Custódia', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3215, 'Dormentes' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3216, 'Escada', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3217, 'Exu', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3218, 'Feira Nova', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3219, 'Fernando de Noronha', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3220, 'Ferreiros' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3221, 'Flores', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3222, 'Floresta', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3223, 'Frei Miguelinho', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3224, 'Gameleira' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3225, 'Garanhuns' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3226, 'Glória do Goitá', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3227, 'Goiana', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3228, 'Granito' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3229, 'Gravatá' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3230, 'Iati' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3231, 'Ibimirim', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3232, 'Ibirajuba' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3233, 'Igarassu', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3234, 'Iguaraci', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3235, 'Inajá', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3236, 'Ingazeira' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3237, 'Ipojuca' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3238, 'Ipubi', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3239, 'Itacuruba' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3240, 'Itaíba', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3241, 'Itamaracá' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3242, 'Itambé', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3243, 'Itapetim', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3244, 'Itapissuma', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3245, 'Itaquitinga' , 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3246, 'Jaboatão dos Guararapes', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3247, 'Jaqueira', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3248, 'Jataúba' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3249, 'Jatobá', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3250, 'João Alfredo', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3251, 'Joaquim Nabuco' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3252, 'Jucati', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3253, 'Jupi' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3254, 'Jurema', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3255, 'Lagoa do Carro' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3256, 'Lagoa do Itaenga' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3257, 'Lagoa do Ouro', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3258, 'Lagoa dos Gatos', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3259, 'Lagoa Grande', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3260, 'Lajedo', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3261, 'Limoeiro', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3262, 'Macaparana', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3263, 'Machados', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3264, 'Manari', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3265, 'Maraial' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3266, 'Mirandiba' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3267, 'Moreilândia' , 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3268, 'Moreno', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3269, 'Nazaré da Mata' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3270, 'Olinda', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3271, 'Orobó', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3272, 'Orocó', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3273, 'Ouricuri', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3274, 'Palmares', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3275, 'Palmeirina', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3276, 'Panelas' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3277, 'Paranatama', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3278, 'Parnamirim', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3279, 'Passira' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3280, 'Paudalho', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3281, 'Paulista', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3282, 'Pedra', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3283, 'Pesqueira' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3284, 'Petrolândia' , 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3285, 'Petrolina' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3286, 'Poção', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3287, 'Pombos', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3288, 'Primavera' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3289, 'Quipapá' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3290, 'Quixaba' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3291, 'Recife', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3292, 'Riacho das Almas' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3293, 'Ribeirão', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3294, 'Rio Formoso' , 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3295, 'Sairé', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3296, 'Salgadinho', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3297, 'Salgueiro' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3298, 'Saloá', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3299, 'Sanharó' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3300, 'Santa Cruz', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3301, 'Santa Cruz da Baixa Verde', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3302, 'Santa Cruz do Capibaribe' , 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3303, 'Santa Filomena' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3304, 'Santa Maria da Boa Vista' , 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3305, 'Santa Maria do Cambucá' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3306, 'Santa Terezinha', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3307, 'São Benedito do Sul', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3308, 'São Bento do Una' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3309, 'São Caitano' , 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3310, 'São João', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3311, 'São Joaquim do Monte' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3312, 'São José da Coroa Grande' , 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3313, 'São José do Belmonte' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3314, 'São José do Egito', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3315, 'São Lourenço da Mata' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3316, 'São Vicente Ferrer', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3317, 'Serra Talhada', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3318, 'Serrita' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3319, 'Sertânia', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3320, 'Sirinhaém' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3321, 'Solidão' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3322, 'Surubim' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3323, 'Tabira', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3324, 'Tacaimbó', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3325, 'Tacaratu', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3326, 'Tamandaré' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3327, 'Taquaritinga do Norte', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3328, 'Terezinha' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3329, 'Terra Nova', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3330, 'Timbaúba', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3331, 'Toritama', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3332, 'Tracunhaém', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3333, 'Trindade', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3334, 'Triunfo' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3335, 'Tupanatinga' , 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3336, 'Tuparetama', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3337, 'Venturosa' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3338, 'Verdejante', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3339, 'Vertente do Lério', 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3340, 'Vertentes' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3341, 'Vicência', 20 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3342, 'Vitória de Santo Antão' , 20);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3343, 'Xexéu', 20);

INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3344, 'Acauã', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3345, 'Agricolândia', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3346, 'Água Branca' , 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3347, 'Alagoinha do Piauí', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3348, 'Alegrete do Piauí', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3349, 'Alto Longá', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3350, 'Altos', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3351, 'Alvorada do Gurguéia' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3352, 'Amarante', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3353, 'Angical do Piauí' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3354, 'Anísio de Abreu', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3355, 'Antônio Almeida', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3356, 'Aroazes' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3357, 'Arraial' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3358, 'Assunção do Piauí', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3359, 'Avelino Lopes', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3360, 'Baixa Grande do Ribeiro', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3361, 'Barra d`Alcântara', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3362, 'Barras', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3363, 'Barreiras do Piauí', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3364, 'Barro Duro', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3365, 'Batalha' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3366, 'Bela Vista do Piauí', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3367, 'Belém do Piauí' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3368, 'Beneditinos' , 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3369, 'Bertolínia', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3370, 'Betânia do Piauí' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3371, 'Boa Hora', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3372, 'Bocaina' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3373, 'Bom Jesus' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3374, 'Bom Princípio do Piauí' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3375, 'Bonfim do Piauí', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3376, 'Boqueirão do Piauí', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3377, 'Brasileira', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3378, 'Brejo do Piauí' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3379, 'Buriti dos Lopes' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3380, 'Buriti dos Montes', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3381, 'Cabeceiras do Piauí', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3382, 'Cajazeiras do Piauí', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3383, 'Cajueiro da Praia', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3384, 'Caldeirão Grande do Piauí', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3385, 'Campinas do Piauí', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3386, 'Campo Alegre do Fidalgo', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3387, 'Campo Grande do Piauí', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3388, 'Campo Largo do Piauí' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3389, 'Campo Maior' , 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3390, 'Canavieira', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3391, 'Canto do Buriti', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3392, 'Capitão de Campos', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3393, 'Capitão Gervásio Oliveira', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3394, 'Caracol' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3395, 'Caraúbas do Piauí', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3396, 'Caridade do Piauí', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3397, 'Castelo do Piauí' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3398, 'Caxingó' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3399, 'Cocal', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3400, 'Cocal de Telha' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3401, 'Cocal dos Alves', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3402, 'Coivaras', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3403, 'Colônia do Gurguéia', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3404, 'Colônia do Piauí' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3405, 'Conceição do Canindé' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3406, 'Coronel José Dias', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3407, 'Corrente', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3408, 'Cristalândia do Piauí', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3409, 'Cristino Castro', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3410, 'Curimatá', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3411, 'Currais' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3412, 'Curral Novo do Piauí' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3413, 'Curralinhos' , 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3414, 'Demerval Lobão' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3415, 'Dirceu Arcoverde' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3416, 'Dom Expedito Lopes', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3417, 'Dom Inocêncio', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3418, 'Domingos Mourão', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3419, 'Elesbão Veloso' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3420, 'Eliseu Martins' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3421, 'Esperantina' , 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3422, 'Fartura do Piauí' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3423, 'Flores do Piauí', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3424, 'Floresta do Piauí', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3425, 'Floriano', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3426, 'Francinópolis', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3427, 'Francisco Ayres', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3428, 'Francisco Macedo' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3429, 'Francisco Santos' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3430, 'Fronteiras', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3431, 'Geminiano' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3432, 'Gilbués' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3433, 'Guadalupe' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3434, 'Guaribas', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3435, 'Hugo Napoleão', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3436, 'Ilha Grande' , 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3437, 'Inhuma', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3438, 'Ipiranga do Piauí', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3439, 'Isaías Coelho', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3440, 'Itainópolis' , 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3441, 'Itaueira', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3442, 'Jacobina do Piauí', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3443, 'Jaicós', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3444, 'Jardim do Mulato' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3445, 'Jatobá do Piauí', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3446, 'Jerumenha' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3447, 'João Costa', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3448, 'Joaquim Pires', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3449, 'Joca Marques', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3450, 'José de Freitas', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3451, 'Juazeiro do Piauí', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3452, 'Júlio Borges', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3453, 'Jurema', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3454, 'Lagoa Alegre', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3455, 'Lagoa de São Francisco' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3456, 'Lagoa do Barro do Piauí', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3457, 'Lagoa do Piauí' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3458, 'Lagoa do Sítio' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3459, 'Lagoinha do Piauí', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3460, 'Landri Sales', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3461, 'Luís Correia', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3462, 'Luzilândia', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3463, 'Madeiro' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3464, 'Manoel Emídio', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3465, 'Marcolândia' , 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3466, 'Marcos Parente' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3467, 'Massapê do Piauí' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3468, 'Matias Olímpio' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3469, 'Miguel Alves', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3470, 'Miguel Leão' , 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3471, 'Milton Brandão' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3472, 'Monsenhor Gil', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3473, 'Monsenhor Hipólito', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3474, 'Monte Alegre do Piauí', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3475, 'Morro Cabeça no Tempo', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3476, 'Morro do Chapéu do Piauí' , 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3477, 'Murici dos Portelas', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3478, 'Nazaré do Piauí', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3479, 'Nossa Senhora de Nazaré', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3480, 'Nossa Senhora dos Remédios', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3481, 'Nova Santa Rita', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3482, 'Novo Oriente do Piauí', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3483, 'Novo Santo Antônio', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3484, 'Oeiras', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3485, 'Olho d`Água do Piauí' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3486, 'Padre Marcos', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3487, 'Paes Landim' , 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3488, 'Pajeú do Piauí' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3489, 'Palmeira do Piauí', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3490, 'Palmeirais', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3491, 'Paquetá' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3492, 'Parnaguá', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3493, 'Parnaíba', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3494, 'Passagem Franca do Piauí' , 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3495, 'Patos do Piauí' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3496, 'Paulistana', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3497, 'Pavussu' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3498, 'Pedro II', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3499, 'Pedro Laurentino' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3500, 'Picos', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3501, 'Pimenteiras' , 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3502, 'Pio IX', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3503, 'Piracuruca', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3504, 'Piripiri', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3505, 'Porto', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3506, 'Porto Alegre do Piauí', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3507, 'Prata do Piauí' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3508, 'Queimada Nova', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3509, 'Redenção do Gurguéia' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3510, 'Regeneração' , 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3511, 'Riacho Frio' , 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3512, 'Ribeira do Piauí' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3513, 'Ribeiro Gonçalves', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3514, 'Rio Grande do Piauí', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3515, 'Santa Cruz do Piauí', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3516, 'Santa Cruz dos Milagres', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3517, 'Santa Filomena' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3518, 'Santa Luz' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3519, 'Santa Rosa do Piauí', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3520, 'Santana do Piauí' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3521, 'Santo Antônio de Lisboa', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3522, 'Santo Antônio dos Milagres', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3523, 'Santo Inácio do Piauí', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3524, 'São Braz do Piauí', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3525, 'São Félix do Piauí', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3526, 'São Francisco de Assis do Piauí', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3527, 'São Francisco do Piauí' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3528, 'São Gonçalo do Gurguéia', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3529, 'São Gonçalo do Piauí' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3530, 'São João da Canabrava', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3531, 'São João da Fronteira', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3532, 'São João da Serra', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3533, 'São João da Varjota', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3534, 'São João do Arraial', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3535, 'São João do Piauí', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3536, 'São José do Divino', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3537, 'São José do Peixe', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3538, 'São José do Piauí', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3539, 'São Julião', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3540, 'São Lourenço do Piauí', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3541, 'São Luis do Piauí', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3542, 'São Miguel da Baixa Grande', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3543, 'São Miguel do Fidalgo', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3544, 'São Miguel do Tapuio' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3545, 'São Pedro do Piauí', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3546, 'São Raimundo Nonato', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3547, 'Sebastião Barros' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3548, 'Sebastião Leal' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3549, 'Sigefredo Pacheco', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3550, 'Simões', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3551, 'Simplício Mendes' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3552, 'Socorro do Piauí' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3553, 'Sussuapara', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3554, 'Tamboril do Piauí', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3555, 'Tanque do Piauí', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3556, 'Teresina', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3557, 'União', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3558, 'Uruçuí', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3559, 'Valença do Piauí' , 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3560, 'Várzea Branca', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3561, 'Várzea Grande', 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3562, 'Vera Mendes' , 21 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3563, 'Vila Nova do Piauí', 21);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3564, 'Wall Ferraz' , 21 );

INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3565, 'Angra dos Reis' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3566, 'Aperibé' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3567, 'Araruama', 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3568, 'Areal', 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3569, 'Armação dos Búzios', 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3570, 'Arraial do Cabo', 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3571, 'Barra do Piraí' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3572, 'Barra Mansa' , 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3573, 'Belford Roxo', 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3574, 'Bom Jardim', 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3575, 'Bom Jesus do Itabapoana', 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3576, 'Cabo Frio' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3577, 'Cachoeiras de Macacu' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3578, 'Cambuci' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3579, 'Campos dos Goytacazes', 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3580, 'Cantagalo' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3581, 'Carapebus' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3582, 'Cardoso Moreira', 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3583, 'Carmo', 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3584, 'Casimiro de Abreu', 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3585, 'Comendador Levy Gasparian', 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3586, 'Conceição de Macabu', 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3587, 'Cordeiro', 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3588, 'Duas Barras' , 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3589, 'Duque de Caxias', 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3590, 'Engenheiro Paulo de Frontin' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3591, 'Guapimirim', 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3592, 'Iguaba Grande', 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3593, 'Itaboraí', 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3594, 'Itaguaí' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3595, 'Italva', 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3596, 'Itaocara', 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3597, 'Itaperuna' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3598, 'Itatiaia', 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3599, 'Japeri', 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3600, 'Laje do Muriaé' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3601, 'Macaé', 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3602, 'Macuco', 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3603, 'Magé' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3604, 'Mangaratiba' , 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3605, 'Maricá', 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3606, 'Mendes', 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3607, 'Miguel Pereira' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3608, 'Miracema', 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3609, 'Natividade', 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3610, 'Nilópolis' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3611, 'Niterói' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3612, 'Nova Friburgo', 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3613, 'Nova Iguaçu' , 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3614, 'Paracambi' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3615, 'Paraíba do Sul' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3616, 'Parati', 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3617, 'Paty do Alferes', 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3618, 'Petrópolis', 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3619, 'Pinheiral' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3620, 'Piraí', 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3621, 'Porciúncula' , 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3622, 'Porto Real', 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3623, 'Quatis', 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3624, 'Queimados' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3625, 'Quissamã', 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3626, 'Resende' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3627, 'Rio Bonito', 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3628, 'Rio Claro' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3629, 'Rio das Flores' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3630, 'Rio das Ostras' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3631, 'Rio de Janeiro' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3632, 'Santa Maria Madalena' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3633, 'Santo Antônio de Pádua' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3634, 'São Fidélis' , 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3635, 'São Francisco de Itabapoana' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3636, 'São Gonçalo' , 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3637, 'São João da Barra', 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3638, 'São João de Meriti', 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3639, 'São José de Ubá', 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3640, 'São José do Vale do Rio Preto' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3641, 'São Pedro da Aldeia', 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3642, 'São Sebastião do Alto', 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3643, 'Sapucaia', 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3644, 'Saquarema' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3645, 'Seropédica', 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3646, 'Silva Jardim', 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3647, 'Sumidouro' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3648, 'Tanguá', 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3649, 'Teresópolis' , 03 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3650, 'Trajano de Morais', 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3651, 'Três Rios' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3652, 'Valença' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3653, 'Varre-Sai' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3654, 'Vassouras' , 03);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3655, 'Volta Redonda', 03 );

INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3656, 'Acari', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3657, 'Açu', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3658, 'Afonso Bezerra' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3659, 'Água Nova' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3660, 'Alexandria', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3661, 'Almino Afonso', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3662, 'Alto do Rodrigues', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3663, 'Angicos' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3664, 'Antônio Martins', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3665, 'Apodi', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3666, 'Areia Branca', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3667, 'Arês' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3668, 'Augusto Severo' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3669, 'Baía Formosa', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3670, 'Baraúna' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3671, 'Barcelona' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3672, 'Bento Fernandes', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3673, 'Bodó' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3674, 'Bom Jesus' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3675, 'Brejinho', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3676, 'Caiçara do Norte' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3677, 'Caiçara do Rio do Vento', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3678, 'Caicó', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3679, 'Campo Redondo', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3680, 'Canguaretama', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3681, 'Caraúbas', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3682, 'Carnaúba dos Dantas', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3683, 'Carnaubais', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3684, 'Ceará-Mirim' , 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3685, 'Cerro Corá', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3686, 'Coronel Ezequiel' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3687, 'Coronel João Pessoa', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3688, 'Cruzeta' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3689, 'Currais Novos', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3690, 'Doutor Severiano' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3691, 'Encanto' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3692, 'Equador' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3693, 'Espírito Santo' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3694, 'Extremoz', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3695, 'Felipe Guerra', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3696, 'Fernando Pedroza' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3697, 'Florânia', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3698, 'Francisco Dantas' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3699, 'Frutuoso Gomes' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3700, 'Galinhos', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3701, 'Goianinha' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3702, 'Governador Dix-Sept Rosado', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3703, 'Grossos' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3704, 'Guamaré' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3705, 'Ielmo Marinho', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3706, 'Ipanguaçu' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3707, 'Ipueira' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3708, 'Itajá', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3709, 'Itaú' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3710, 'Jaçanã', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3711, 'Jandaíra', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3712, 'Janduís' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3713, 'Januário Cicco' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3714, 'Japi' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3715, 'Jardim de Angicos', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3716, 'Jardim de Piranhas', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3717, 'Jardim do Seridó' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3718, 'João Câmara' , 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3719, 'João Dias' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3720, 'José da Penha', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3721, 'Jucurutu', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3722, 'Lagoa d`Anta', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3723, 'Lagoa de Pedras', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3724, 'Lagoa de Velhos', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3725, 'Lagoa Nova', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3726, 'Lagoa Salgada', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3727, 'Lajes', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3728, 'Lajes Pintadas' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3729, 'Lucrécia', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3730, 'Luís Gomes', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3731, 'Macaíba' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3732, 'Macau', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3733, 'Major Sales' , 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3734, 'Marcelino Vieira' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3735, 'Martins' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3736, 'Maxaranguape', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3737, 'Messias Targino', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3738, 'Montanhas' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3739, 'Monte Alegre', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3740, 'Monte das Gameleiras' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3741, 'Mossoró' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3742, 'Natal', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3743, 'Nísia Floresta' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3744, 'Nova Cruz' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3745, 'Olho-d`Água do Borges', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3746, 'Ouro Branco' , 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3747, 'Paraná', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3748, 'Paraú', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3749, 'Parazinho' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3750, 'Parelhas', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3751, 'Parnamirim', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3752, 'Passa e Fica', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3753, 'Passagem', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3754, 'Patu' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3755, 'Pau dos Ferros' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3756, 'Pedra Grande', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3757, 'Pedra Preta' , 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3758, 'Pedro Avelino', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3759, 'Pedro Velho' , 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3760, 'Pendências', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3761, 'Pilões', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3762, 'Poço Branco' , 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3763, 'Portalegre', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3764, 'Porto do Mangue', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3765, 'Presidente Juscelino' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3766, 'Pureza', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3767, 'Rafael Fernandes' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3768, 'Rafael Godeiro' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3769, 'Riacho da Cruz' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3770, 'Riacho de Santana', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3771, 'Riachuelo' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3772, 'Rio do Fogo' , 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3773, 'Rodolfo Fernandes', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3774, 'Ruy Barbosa' , 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3775, 'Santa Cruz', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3776, 'Santa Maria' , 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3777, 'Santana do Matos' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3778, 'Santana do Seridó', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3779, 'Santo Antônio', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3780, 'São Bento do Norte', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3781, 'São Bento do Trairí', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3782, 'São Fernando', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3783, 'São Francisco do Oeste' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3784, 'São Gonçalo do Amarante', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3785, 'São João do Sabugi', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3786, 'São José de Mipibu', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3787, 'São José do Campestre', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3788, 'São José do Seridó', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3789, 'São Miguel', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3790, 'São Miguel de Touros' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3791, 'São Paulo do Potengi' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3792, 'São Pedro' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3793, 'São Rafael', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3794, 'São Tomé', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3795, 'São Vicente' , 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3796, 'Senador Elói de Souza', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3797, 'Senador Georgino Avelino' , 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3798, 'Serra de São Bento', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3799, 'Serra do Mel', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3800, 'Serra Negra do Norte' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3801, 'Serrinha', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3802, 'Serrinha dos Pintos', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3803, 'Severiano Melo' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3804, 'Sítio Novo', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3805, 'Taboleiro Grande' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3806, 'Taipu', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3807, 'Tangará' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3808, 'Tenente Ananias', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3809, 'Tenente Laurentino Cruz', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3810, 'Tibau', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3811, 'Tibau do Sul', 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3812, 'Timbaúba dos Batistas', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3813, 'Touros', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3814, 'Triunfo Potiguar' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3815, 'Umarizal', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3816, 'Upanema' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3817, 'Várzea', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3818, 'Venha-Ver' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3819, 'Vera Cruz' , 22);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3820, 'Viçosa', 22 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3821, 'Vila Flor' , 22);

INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3822, 'Água Santa', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3823, 'Agudo', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3824, 'Ajuricaba' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3825, 'Alecrim' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3826, 'Alegrete', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3827, 'Alegria' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3828, 'Alpestre', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3829, 'Alto Alegre' , 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3830, 'Alto Feliz', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3831, 'Alvorada', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3832, 'Amaral Ferrador', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3833, 'Ametista do Sul', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3834, 'André da Rocha' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3835, 'Anta Gorda', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3836, 'Antônio Prado', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3837, 'Arambaré', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3838, 'Araricá' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3839, 'Aratiba' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3840, 'Arroio do Meio' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3841, 'Arroio do Sal', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3842, 'Arroio do Tigre', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3843, 'Arroio dos Ratos' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3844, 'Arroio Grande', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3845, 'Arvorezinha' , 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3846, 'Augusto Pestana', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3847, 'Áurea', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3848, 'Bagé' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3849, 'Balneário Pinhal' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3850, 'Barão', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3851, 'Barão de Cotegipe', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3852, 'Barão do Triunfo' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3853, 'Barra do Guarita' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3854, 'Barra do Quaraí', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3855, 'Barra do Ribeiro' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3856, 'Barra do Rio Azul', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3857, 'Barra Funda' , 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3858, 'Barracão', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3859, 'Barros Cassal', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3860, 'Benjamin Constant do Sul' , 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3861, 'Bento Gonçalves', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3862, 'Boa Vista das Missões', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3863, 'Boa Vista do Buricá', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3864, 'Boa Vista do Sul' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3865, 'Bom Jesus' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3866, 'Bom Princípio', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3867, 'Bom Progresso', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3868, 'Bom Retiro do Sul', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3869, 'Boqueirão do Leão', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3870, 'Bossoroca' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3871, 'Braga', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3872, 'Brochier', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3873, 'Butiá', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3874, 'Caçapava do Sul', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3875, 'Cacequi' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3876, 'Cachoeira do Sul' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3877, 'Cachoeirinha', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3878, 'Cacique Doble', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3879, 'Caibaté' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3880, 'Caiçara' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3881, 'Camaquã' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3882, 'Camargo' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3883, 'Cambará do Sul' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3884, 'Campestre da Serra', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3885, 'Campina das Missões', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3886, 'Campinas do Sul', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3887, 'Campo Bom' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3888, 'Campo Novo', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3889, 'Campos Borges', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3890, 'Candelária', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3891, 'Cândido Godói', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3892, 'Candiota', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3893, 'Canela', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3894, 'Canguçu' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3895, 'Canoas', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3896, 'Capão da Canoa' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3897, 'Capão do Leão', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3898, 'Capela de Santana', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3899, 'Capitão' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3900, 'Capivari do Sul', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3901, 'Caraá', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3902, 'Carazinho' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3903, 'Carlos Barbosa' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3904, 'Carlos Gomes', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3905, 'Casca', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3906, 'Caseiros', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3907, 'Catuípe' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3908, 'Caxias do Sul', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3909, 'Centenário', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3910, 'Cerrito' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3911, 'Cerro Branco', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3912, 'Cerro Grande', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3913, 'Cerro Grande do Sul', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3914, 'Cerro Largo' , 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3915, 'Chapada' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3916, 'Charqueadas' , 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3917, 'Charrua' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3918, 'Chiapeta', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3919, 'Chuí' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3920, 'Chuvisca', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3921, 'Cidreira', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3922, 'Ciríaco' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3923, 'Colinas' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3924, 'Colorado', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3925, 'Condor', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3926, 'Constantina' , 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3927, 'Coqueiros do Sul' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3928, 'Coronel Barros' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3929, 'Coronel Bicaco' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3930, 'Cotiporã', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3931, 'Coxilha' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3932, 'Crissiumal', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3933, 'Cristal' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3934, 'Cristal do Sul' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3935, 'Cruz Alta' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3936, 'Cruzeiro do Sul', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3937, 'David Canabarro', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3938, 'Derrubadas', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3939, 'Dezesseis de Novembro', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3940, 'Dilermando de Aguiar' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3941, 'Dois Irmãos' , 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3942, 'Dois Irmãos das Missões', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3943, 'Dois Lajeados', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3944, 'Dom Feliciano', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3945, 'Dom Pedrito' , 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3946, 'Dom Pedro de Alcântara' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3947, 'Dona Francisca' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3948, 'Doutor Maurício Cardoso', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3949, 'Doutor Ricardo' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3950, 'Eldorado do Sul', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3951, 'Encantado' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3952, 'Encruzilhada do Sul', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3953, 'Engenho Velho', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3954, 'Entre Rios do Sul', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3955, 'Entre-Ijuís' , 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3956, 'Erebango', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3957, 'Erechim' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3958, 'Ernestina' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3959, 'Erval Grande', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3960, 'Erval Seco', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3961, 'Esmeralda' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3962, 'Esperança do Sul' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3963, 'Espumoso', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3964, 'Estação' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3965, 'Estância Velha' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3966, 'Esteio', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3967, 'Estrela' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3968, 'Estrela Velha', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3969, 'Eugênio de Castro', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3970, 'Fagundes Varela', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3971, 'Farroupilha' , 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3972, 'Faxinal do Soturno', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3973, 'Faxinalzinho', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3974, 'Fazenda Vilanova' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3975, 'Feliz', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3976, 'Flores da Cunha', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3977, 'Floriano Peixoto' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3978, 'Fontoura Xavier', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3979, 'Formigueiro' , 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3980, 'Fortaleza dos Valos', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3981, 'Frederico Westphalen' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3982, 'Garibaldi' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3983, 'Garruchos' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3984, 'Gaurama' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3985, 'General Câmara' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3986, 'Gentil', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3987, 'Getúlio Vargas' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3988, 'Giruá', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3989, 'Glorinha', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3990, 'Gramado' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3991, 'Gramado dos Loureiros', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3992, 'Gramado Xavier' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3993, 'Gravataí', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3994, 'Guabiju' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3995, 'Guaíba', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3996, 'Guaporé' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3997, 'Guarani das Missões', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3998, 'Harmonia', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (3999, 'Herval', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4000, 'Herveiras' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4001, 'Horizontina' , 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4002, 'Hulha Negra' , 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4003, 'Humaitá' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4004, 'Ibarama' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4005, 'Ibiaçá', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4006, 'Ibiraiaras', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4007, 'Ibirapuitã', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4008, 'Ibirubá' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4009, 'Igrejinha' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4010, 'Ijuí' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4011, 'Ilópolis', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4012, 'Imbé' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4013, 'Imigrante' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4014, 'Independência', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4015, 'Inhacorá', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4016, 'Ipê', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4017, 'Ipiranga do Sul', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4018, 'Iraí' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4019, 'Itaara', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4020, 'Itacurubi' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4021, 'Itapuca' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4022, 'Itaqui', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4023, 'Itatiba do Sul' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4024, 'Ivorá', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4025, 'Ivoti', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4026, 'Jaboticaba', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4027, 'Jacutinga' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4028, 'Jaguarão', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4029, 'Jaguari' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4030, 'Jaquirana' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4031, 'Jari' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4032, 'Jóia' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4033, 'Júlio de Castilhos', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4034, 'Lagoa dos Três Cantos', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4035, 'Lagoa Vermelha' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4036, 'Lagoão', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4037, 'Lajeado' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4038, 'Lajeado do Bugre' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4039, 'Lavras do Sul', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4040, 'Liberato Salzano' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4041, 'Lindolfo Collor', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4042, 'Linha Nova', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4043, 'Maçambara' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4044, 'Machadinho', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4045, 'Mampituba' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4046, 'Manoel Viana', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4047, 'Maquiné' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4048, 'Maratá', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4049, 'Marau', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4050, 'Marcelino Ramos', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4051, 'Mariana Pimentel' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4052, 'Mariano Moro', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4053, 'Marques de Souza' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4054, 'Mata' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4055, 'Mato Castelhano', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4056, 'Mato Leitão' , 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4057, 'Maximiliano de Almeida' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4058, 'Minas do Leão', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4059, 'Miraguaí', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4060, 'Montauri', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4061, 'Monte Alegre dos Campos', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4062, 'Monte Belo do Sul', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4063, 'Montenegro', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4064, 'Mormaço' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4065, 'Morrinhos do Sul' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4066, 'Morro Redondo', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4067, 'Morro Reuter', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4068, 'Mostardas' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4069, 'Muçum', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4070, 'Muitos Capões', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4071, 'Muliterno' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4072, 'Não-Me-Toque', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4073, 'Nicolau Vergueiro', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4074, 'Nonoai', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4075, 'Nova Alvorada', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4076, 'Nova Araçá', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4077, 'Nova Bassano', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4078, 'Nova Boa Vista' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4079, 'Nova Bréscia', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4080, 'Nova Candelária', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4081, 'Nova Esperança do Sul', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4082, 'Nova Hartz', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4083, 'Nova Pádua', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4084, 'Nova Palma', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4085, 'Nova Petrópolis', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4086, 'Nova Prata', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4087, 'Nova Ramada' , 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4088, 'Nova Roma do Sul' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4089, 'Nova Santa Rita', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4090, 'Novo Barreiro', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4091, 'Novo Cabrais', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4092, 'Novo Hamburgo', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4093, 'Novo Machado', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4094, 'Novo Tiradentes', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4095, 'Osório', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4096, 'Paim Filho', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4097, 'Palmares do Sul', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4098, 'Palmeira das Missões' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4099, 'Palmitinho', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4100, 'Panambi' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4101, 'Pantano Grande' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4102, 'Paraí', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4103, 'Paraíso do Sul' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4104, 'Pareci Novo' , 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4105, 'Parobé', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4106, 'Passa Sete', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4107, 'Passo do Sobrado' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4108, 'Passo Fundo' , 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4109, 'Paverama', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4110, 'Pedro Osório', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4111, 'Pejuçara', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4112, 'Pelotas' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4113, 'Picada Café' , 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4114, 'Pinhal', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4115, 'Pinhal Grande', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4116, 'Pinheirinho do Vale', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4117, 'Pinheiro Machado' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4118, 'Pirapó', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4119, 'Piratini', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4120, 'Planalto', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4121, 'Poço das Antas' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4122, 'Pontão', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4123, 'Ponte Preta' , 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4124, 'Portão', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4125, 'Porto Alegre', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4126, 'Porto Lucena', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4127, 'Porto Mauá', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4128, 'Porto Vera Cruz', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4129, 'Porto Xavier', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4130, 'Pouso Novo', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4131, 'Presidente Lucena', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4132, 'Progresso' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4133, 'Protásio Alves' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4134, 'Putinga' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4135, 'Quaraí', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4136, 'Quevedos', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4137, 'Quinze de Novembro', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4138, 'Redentora' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4139, 'Relvado' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4140, 'Restinga Seca', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4141, 'Rio dos Índios' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4142, 'Rio Grande', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4143, 'Rio Pardo' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4144, 'Riozinho', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4145, 'Roca Sales', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4146, 'Rodeio Bonito', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4147, 'Rolante' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4148, 'Ronda Alta', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4149, 'Rondinha', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4150, 'Roque Gonzales' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4151, 'Rosário do Sul' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4152, 'Sagrada Família', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4153, 'Saldanha Marinho' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4154, 'Salto do Jacuí' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4155, 'Salvador das Missões' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4156, 'Salvador do Sul', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4157, 'Sananduva' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4158, 'Santa Bárbara do Sul' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4159, 'Santa Clara do Sul', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4160, 'Santa Cruz do Sul', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4161, 'Santa Maria' , 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4162, 'Santa Maria do Herval', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4163, 'Santa Rosa', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4164, 'Santa Tereza', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4165, 'Santa Vitória do Palmar', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4166, 'Santana da Boa Vista' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4167, 'Santana do Livramento', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4168, 'Santiago', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4169, 'Santo Ângelo', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4170, 'Santo Antônio da Patrulha', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4171, 'Santo Antônio das Missões', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4172, 'Santo Antônio do Palma' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4173, 'Santo Antônio do Planalto', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4174, 'Santo Augusto', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4175, 'Santo Cristo', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4176, 'Santo Expedito do Sul', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4177, 'São Borja' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4178, 'São Domingos do Sul', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4179, 'São Francisco de Assis' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4180, 'São Francisco de Paula' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4181, 'São Gabriel' , 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4182, 'São Jerônimo', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4183, 'São João da Urtiga', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4184, 'São João do Polêsine' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4185, 'São Jorge' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4186, 'São José das Missões' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4187, 'São José do Herval', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4188, 'São José do Hortêncio', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4189, 'São José do Inhacorá' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4190, 'São José do Norte', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4191, 'São José do Ouro' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4192, 'São José dos Ausentes', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4193, 'São Leopoldo', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4194, 'São Lourenço do Sul', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4195, 'São Luiz Gonzaga' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4196, 'São Marcos', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4197, 'São Martinho', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4198, 'São Martinho da Serra', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4199, 'São Miguel das Missões' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4200, 'São Nicolau' , 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4201, 'São Paulo das Missões', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4202, 'São Pedro da Serra', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4203, 'São Pedro do Butiá', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4204, 'São Pedro do Sul' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4205, 'São Sebastião do Caí' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4206, 'São Sepé', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4207, 'São Valentim', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4208, 'São Valentim do Sul', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4209, 'São Valério do Sul', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4210, 'São Vendelino', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4211, 'São Vicente do Sul', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4212, 'Sapiranga' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4213, 'Sapucaia do Sul', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4214, 'Sarandi' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4215, 'Seberi', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4216, 'Sede Nova' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4217, 'Segredo' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4218, 'Selbach' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4219, 'Senador Salgado Filho', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4220, 'Sentinela do Sul' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4221, 'Serafina Corrêa', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4222, 'Sério', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4223, 'Sertão', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4224, 'Sertão Santana' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4225, 'Sete de Setembro' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4226, 'Severiano de Almeida' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4227, 'Silveira Martins' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4228, 'Sinimbu' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4229, 'Sobradinho', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4230, 'Soledade', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4231, 'Tabaí', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4232, 'Tapejara', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4233, 'Tapera', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4234, 'Tapes', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4235, 'Taquara' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4236, 'Taquari' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4237, 'Taquaruçu do Sul' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4238, 'Tavares' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4239, 'Tenente Portela', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4240, 'Terra de Areia' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4241, 'Teutônia', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4242, 'Tiradentes do Sul', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4243, 'Toropi', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4244, 'Torres', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4245, 'Tramandaí' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4246, 'Travesseiro' , 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4247, 'Três Arroios', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4248, 'Três Cachoeiras', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4249, 'Três Coroas' , 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4250, 'Três de Maio', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4251, 'Três Forquilhas', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4252, 'Três Palmeiras' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4253, 'Três Passos' , 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4254, 'Trindade do Sul', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4255, 'Triunfo' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4256, 'Tucunduva' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4257, 'Tunas', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4258, 'Tupanci do Sul' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4259, 'Tupanciretã' , 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4260, 'Tupandi' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4261, 'Tuparendi' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4262, 'Turuçu', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4263, 'Ubiretama' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4264, 'União da Serra' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4265, 'Unistalda' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4266, 'Uruguaiana', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4267, 'Vacaria' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4268, 'Vale do Sol' , 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4269, 'Vale Real' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4270, 'Vale Verde', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4271, 'Vanini', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4272, 'Venâncio Aires' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4273, 'Vera Cruz' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4274, 'Veranópolis' , 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4275, 'Vespasiano Correa', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4276, 'Viadutos', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4277, 'Viamão', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4278, 'Vicente Dutra', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4279, 'Victor Graeff', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4280, 'Vila Flores' , 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4281, 'Vila Lângaro', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4282, 'Vila Maria', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4283, 'Vila Nova do Sul' , 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4284, 'Vista Alegre', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4285, 'Vista Alegre do Prata', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4286, 'Vista Gaúcha', 23);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4287, 'Vitória das Missões', 23 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4288, 'Xangri-lá' , 23);
									
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4289, 'Alta Floresta d`Oeste', 24 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4290, 'Alto Alegre dos Parecis', 24);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4291, 'Alto Paraíso', 24);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4292, 'Alvorada d`Oeste' , 24);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4293, 'Ariquemes' , 24);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4294, 'Buritis' , 24);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4295, 'Cabixi', 24 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4296, 'Cacaulândia' , 24 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4297, 'Cacoal', 24 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4298, 'Campo Novo de Rondônia' , 24);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4299, 'Candeias do Jamari', 24);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4300, 'Castanheiras', 24);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4301, 'Cerejeiras', 24);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4302, 'Chupinguaia' , 24 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4303, 'Colorado do Oeste', 24);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4304, 'Corumbiara', 24);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4305, 'Costa Marques', 24 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4306, 'Cujubim' , 24);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4307, 'Espigão d`Oeste', 24 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4308, 'Governador Jorge Teixeira', 24);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4309, 'Guajará-Mirim', 24 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4310, 'Itapuã do Oeste', 24 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4311, 'Jaru' , 24);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4312, 'Ji-Paraná' , 24);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4313, 'Machadinho d`Oeste', 24);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4314, 'Ministro Andreazza', 24);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4315, 'Mirante da Serra' , 24);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4316, 'Monte Negro' , 24 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4317, 'Nova Brasilândia d`Oeste' , 24 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4318, 'Nova Mamoré' , 24 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4319, 'Nova União', 24);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4320, 'Novo Horizonte do Oeste', 24);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4321, 'Ouro Preto do Oeste', 24 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4322, 'Parecis' , 24);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4323, 'Pimenta Bueno', 24 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4324, 'Pimenteiras do Oeste' , 24);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4325, 'Porto Velho' , 24 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4326, 'Presidente Médici', 24);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4327, 'Primavera de Rondônia', 24 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4328, 'Rio Crespo', 24);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4329, 'Rolim de Moura' , 24);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4330, 'Santa Luzia d`Oeste', 24 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4331, 'São Felipe d`Oeste', 24);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4332, 'São Francisco do Guaporé' , 24 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4333, 'São Miguel do Guaporé', 24 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4334, 'Seringueiras', 24);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4335, 'Teixeirópolis', 24 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4336, 'Theobroma' , 24);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4337, 'Urupá', 24);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4338, 'Vale do Anari', 24 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4339, 'Vale do Paraíso', 24 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4340, 'Vilhena' , 24);

INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4341, 'Alto Alegre' , 25 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4342, 'Amajari' , 25);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4343, 'Boa Vista' , 25);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4344, 'Bonfim', 25 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4345, 'Cantá', 25);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4346, 'Caracaraí' , 25);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4347, 'Caroebe' , 25);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4348, 'Iracema' , 25);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4349, 'Mucajaí' , 25);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4350, 'Normandia' , 25);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4351, 'Pacaraima' , 25);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4352, 'Rorainópolis', 25);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4353, 'São João da Baliza', 25);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4354, 'São Luiz', 25 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4355, 'Uiramutã', 25 );

INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4356, 'Abdon Batista', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4357, 'Abelardo Luz', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4358, 'Agrolândia', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4359, 'Agronômica', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4360, 'Água Doce' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4361, 'Águas de Chapecó' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4362, 'Águas Frias' , 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4363, 'Águas Mornas', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4364, 'Alfredo Wagner' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4365, 'Alto Bela Vista', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4366, 'Anchieta', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4367, 'Angelina', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4368, 'Anita Garibaldi', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4369, 'Anitápolis', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4370, 'Antônio Carlos' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4371, 'Apiúna', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4372, 'Arabutã' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4373, 'Araquari', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4374, 'Araranguá' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4375, 'Armazém' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4376, 'Arroio Trinta', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4377, 'Arvoredo', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4378, 'Ascurra' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4379, 'Atalanta', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4380, 'Aurora', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4381, 'Balneário Arroio do Silva', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4382, 'Balneário Barra do Sul' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4383, 'Balneário Camboriú', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4384, 'Balneário Gaivota', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4385, 'Bandeirante' , 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4386, 'Barra Bonita', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4387, 'Barra Velha' , 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4388, 'Bela Vista do Toldo', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4389, 'Belmonte', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4390, 'Benedito Novo', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4391, 'Biguaçu' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4392, 'Blumenau', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4393, 'Bocaina do Sul' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4394, 'Bom Jardim da Serra', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4395, 'Bom Jesus' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4396, 'Bom Jesus do Oeste', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4397, 'Bom Retiro', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4398, 'Bombinhas' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4399, 'Botuverá', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4400, 'Braço do Norte' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4401, 'Braço do Trombudo', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4402, 'Brunópolis', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4403, 'Brusque' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4404, 'Caçador' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4405, 'Caibi', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4406, 'Calmon', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4407, 'Camboriú', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4408, 'Campo Alegre', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4409, 'Campo Belo do Sul', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4410, 'Campo Erê' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4411, 'Campos Novos', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4412, 'Canelinha' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4413, 'Canoinhas' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4414, 'Capão Alto', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4415, 'Capinzal', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4416, 'Capivari de Baixo', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4417, 'Catanduvas', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4418, 'Caxambu do Sul' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4419, 'Celso Ramos' , 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4420, 'Cerro Negro' , 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4421, 'Chapadão do Lageado', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4422, 'Chapecó' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4423, 'Cocal do Sul', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4424, 'Concórdia' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4425, 'Cordilheira Alta' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4426, 'Coronel Freitas', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4427, 'Coronel Martins', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4428, 'Correia Pinto', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4429, 'Corupá', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4430, 'Criciúma', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4431, 'Cunha Porã', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4432, 'Cunhataí', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4433, 'Curitibanos' , 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4434, 'Descanso', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4435, 'Dionísio Cerqueira', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4436, 'Dona Emma' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4437, 'Doutor Pedrinho', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4438, 'Entre Rios', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4439, 'Ermo' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4440, 'Erval Velho' , 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4441, 'Faxinal dos Guedes', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4442, 'Flor do Sertão' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4443, 'Florianópolis', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4444, 'Formosa do Sul' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4445, 'Forquilhinha', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4446, 'Fraiburgo' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4447, 'Frei Rogério', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4448, 'Galvão', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4449, 'Garopaba', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4450, 'Garuva', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4451, 'Gaspar', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4452, 'Governador Celso Ramos' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4453, 'Grão Pará' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4454, 'Gravatal', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4455, 'Guabiruba' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4456, 'Guaraciaba', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4457, 'Guaramirim', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4458, 'Guarujá do Sul' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4459, 'Guatambú', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4460, 'Herval d`Oeste' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4461, 'Ibiam', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4462, 'Ibicaré' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4463, 'Ibirama' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4464, 'Içara', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4465, 'Ilhota', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4466, 'Imaruí', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4467, 'Imbituba', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4468, 'Imbuia', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4469, 'Indaial' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4470, 'Iomerê', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4471, 'Ipira', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4472, 'Iporã do Oeste' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4473, 'Ipuaçu', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4474, 'Ipumirim', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4475, 'Iraceminha', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4476, 'Irani', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4477, 'Irati', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4478, 'Irineópolis' , 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4479, 'Itá', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4480, 'Itaiópolis', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4481, 'Itajaí', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4482, 'Itapema' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4483, 'Itapiranga', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4484, 'Itapoá', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4485, 'Ituporanga', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4486, 'Jaborá', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4487, 'Jacinto Machado', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4488, 'Jaguaruna' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4489, 'Jaraguá do Sul' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4490, 'Jardinópolis', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4491, 'Joaçaba' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4492, 'Joinville' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4493, 'José Boiteux', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4494, 'Jupiá', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4495, 'Lacerdópolis', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4496, 'Lages', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4497, 'Laguna', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4498, 'Lajeado Grande' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4499, 'Laurentino', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4500, 'Lauro Muller', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4501, 'Lebon Régis' , 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4502, 'Leoberto Leal', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4503, 'Lindóia do Sul' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4504, 'Lontras' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4505, 'Luiz Alves', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4506, 'Luzerna' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4507, 'Macieira', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4508, 'Mafra', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4509, 'Major Gercino', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4510, 'Major Vieira', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4511, 'Maracajá', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4512, 'Maravilha' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4513, 'Marema', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4514, 'Massaranduba', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4515, 'Matos Costa' , 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4516, 'Meleiro' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4517, 'Mirim Doce', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4518, 'Modelo', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4519, 'Mondaí', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4520, 'Monte Carlo' , 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4521, 'Monte Castelo', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4522, 'Morro da Fumaça', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4523, 'Morro Grande', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4524, 'Navegantes', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4525, 'Nova Erechim', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4526, 'Nova Itaberaba' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4527, 'Nova Trento' , 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4528, 'Nova Veneza' , 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4529, 'Novo Horizonte' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4530, 'Orleans' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4531, 'Otacílio Costa' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4532, 'Ouro' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4533, 'Ouro Verde', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4534, 'Paial', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4535, 'Painel', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4536, 'Palhoça' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4537, 'Palma Sola', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4538, 'Palmeira', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4539, 'Palmitos', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4540, 'Papanduva' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4541, 'Paraíso' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4542, 'Passo de Torres', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4543, 'Passos Maia' , 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4544, 'Paulo Lopes' , 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4545, 'Pedras Grandes' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4546, 'Penha', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4547, 'Peritiba', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4548, 'Petrolândia' , 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4549, 'Piçarras', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4550, 'Pinhalzinho' , 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4551, 'Pinheiro Preto' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4552, 'Piratuba', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4553, 'Planalto Alegre', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4554, 'Pomerode', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4555, 'Ponte Alta', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4556, 'Ponte Alta do Norte', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4557, 'Ponte Serrada', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4558, 'Porto Belo', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4559, 'Porto União' , 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4560, 'Pouso Redondo', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4561, 'Praia Grande', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4562, 'Presidente Castelo Branco', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4563, 'Presidente Getúlio', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4564, 'Presidente Nereu' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4565, 'Princesa', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4566, 'Quilombo', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4567, 'Rancho Queimado', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4568, 'Rio das Antas', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4569, 'Rio do Campo', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4570, 'Rio do Oeste', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4571, 'Rio do Sul', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4572, 'Rio dos Cedros' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4573, 'Rio Fortuna' , 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4574, 'Rio Negrinho', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4575, 'Rio Rufino', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4576, 'Riqueza' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4577, 'Rodeio', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4578, 'Romelândia', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4579, 'Salete', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4580, 'Saltinho', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4581, 'Salto Veloso', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4582, 'Sangão', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4583, 'Santa Cecília', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4584, 'Santa Helena', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4585, 'Santa Rosa de Lima', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4586, 'Santa Rosa do Sul', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4587, 'Santa Terezinha', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4588, 'Santa Terezinha do Progresso', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4589, 'Santiago do Sul', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4590, 'Santo Amaro da Imperatriz', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4591, 'São Bento do Sul' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4592, 'São Bernardino' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4593, 'São Bonifácio', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4594, 'São Carlos', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4595, 'São Cristovão do Sul' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4596, 'São Domingos', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4597, 'São Francisco do Sul' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4598, 'São João Batista' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4599, 'São João do Itaperiú' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4600, 'São João do Oeste', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4601, 'São João do Sul', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4602, 'São Joaquim' , 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4603, 'São José', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4604, 'São José do Cedro', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4605, 'São José do Cerrito', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4606, 'São Lourenço do Oeste', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4607, 'São Ludgero' , 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4608, 'São Martinho', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4609, 'São Miguel da Boa Vista', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4610, 'São Miguel do Oeste', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4611, 'São Pedro de Alcântara' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4612, 'Saudades', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4613, 'Schroeder' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4614, 'Seara', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4615, 'Serra Alta', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4616, 'Siderópolis' , 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4617, 'Sombrio' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4618, 'Sul Brasil', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4619, 'Taió' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4620, 'Tangará' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4621, 'Tigrinhos' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4622, 'Tijucas' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4623, 'Timbé do Sul', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4624, 'Timbó', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4625, 'Timbó Grande', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4626, 'Três Barras' , 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4627, 'Treviso' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4628, 'Treze de Maio', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4629, 'Treze Tílias', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4630, 'Trombudo Central' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4631, 'Tubarão' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4632, 'Tunápolis' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4633, 'Turvo', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4634, 'União do Oeste' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4635, 'Urubici' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4636, 'Urupema' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4637, 'Urussanga' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4638, 'Vargeão' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4639, 'Vargem', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4640, 'Vargem Bonita', 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4641, 'Vidal Ramos' , 26 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4642, 'Videira' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4643, 'Vitor Meireles' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4644, 'Witmarsum' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4645, 'Xanxerê' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4646, 'Xavantina' , 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4647, 'Xaxim', 26);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4648, 'Zortéa', 26 );

INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4649, 'Adamantina', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4650, 'Adolfo', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4651, 'Aguaí', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4652, 'Águas da Prata' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4653, 'Águas de Lindóia' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4654, 'Águas de Santa Bárbara' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4655, 'Águas de São Pedro', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4656, 'Agudos', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4657, 'Alambari', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4658, 'Alfredo Marcondes', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4659, 'Altair', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4660, 'Altinópolis' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4661, 'Alto Alegre' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4662, 'Alumínio', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4663, 'Álvares Florence' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4664, 'Álvares Machado', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4665, 'Álvaro de Carvalho', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4666, 'Alvinlândia' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4667, 'Americana' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4668, 'Américo Brasiliense', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4669, 'Américo de Campos', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4670, 'Amparo', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4671, 'Analândia' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4672, 'Andradina' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4673, 'Angatuba', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4674, 'Anhembi' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4675, 'Anhumas' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4676, 'Aparecida' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4677, 'Aparecida d`Oeste', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4678, 'Apiaí', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4679, 'Araçariguama', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4680, 'Araçatuba' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4681, 'Araçoiaba da Serra', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4682, 'Aramina' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4683, 'Arandu', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4684, 'Arapeí', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4685, 'Araraquara', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4686, 'Araras', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4687, 'Arco-Íris' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4688, 'Arealva' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4689, 'Areias', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4690, 'Areiópolis', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4691, 'Ariranha', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4692, 'Artur Nogueira' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4693, 'Arujá', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4694, 'Aspásia' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4695, 'Assis', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4696, 'Atibaia' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4697, 'Auriflama' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4698, 'Avaí' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4699, 'Avanhandava' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4700, 'Avaré', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4701, 'Bady Bassitt', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4702, 'Balbinos', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4703, 'Bálsamo' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4704, 'Bananal' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4705, 'Barão de Antonina', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4706, 'Barbosa' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4707, 'Bariri', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4708, 'Barra Bonita', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4709, 'Barra do Chapéu', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4710, 'Barra do Turvo' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4711, 'Barretos', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4712, 'Barrinha', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4713, 'Barueri' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4714, 'Bastos', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4715, 'Batatais', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4716, 'Bauru', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4717, 'Bebedouro' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4718, 'Bento de Abreu' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4719, 'Bernardino de Campos' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4720, 'Bertioga', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4721, 'Bilac', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4722, 'Birigui' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4723, 'Biritiba-Mirim' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4724, 'Boa Esperança do Sul' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4725, 'Bocaina' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4726, 'Bofete', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4727, 'Boituva' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4728, 'Bom Jesus dos Perdões', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4729, 'Bom Sucesso de Itararé' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4730, 'Borá' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4731, 'Boracéia', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4732, 'Borborema' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4733, 'Borebi', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4734, 'Botucatu', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4735, 'Bragança Paulista', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4736, 'Braúna', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4737, 'Brejo Alegre', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4738, 'Brodowski' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4739, 'Brotas', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4740, 'Buri' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4741, 'Buritama', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4742, 'Buritizal' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4743, 'Cabrália Paulista', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4744, 'Cabreúva', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4745, 'Caçapava', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4746, 'Cachoeira Paulista', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4747, 'Caconde' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4748, 'Cafelândia', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4749, 'Caiabu', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4750, 'Caieiras', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4751, 'Caiuá', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4752, 'Cajamar' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4753, 'Cajati', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4754, 'Cajobi', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4755, 'Cajuru', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4756, 'Campina do Monte Alegre', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4757, 'Campinas', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4758, 'Campo Limpo Paulista' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4759, 'Campos do Jordão' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4760, 'Campos Novos Paulista', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4761, 'Cananéia', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4762, 'Canas', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4763, 'Cândido Mota', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4764, 'Cândido Rodrigues', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4765, 'Canitar' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4766, 'Capão Bonito', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4767, 'Capela do Alto' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4768, 'Capivari', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4769, 'Caraguatatuba', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4770, 'Carapicuíba' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4771, 'Cardoso' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4772, 'Casa Branca' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4773, 'Cássia dos Coqueiros' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4774, 'Castilho', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4775, 'Catanduva' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4776, 'Catiguá' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4777, 'Cedral', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4778, 'Cerqueira César', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4779, 'Cerquilho' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4780, 'Cesário Lange', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4781, 'Charqueada', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4782, 'Chavantes' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4783, 'Clementina', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4784, 'Colina', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4785, 'Colômbia', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4786, 'Conchal' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4787, 'Conchas' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4788, 'Cordeirópolis', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4789, 'Coroados', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4790, 'Coronel Macedo' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4791, 'Corumbataí', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4792, 'Cosmópolis', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4793, 'Cosmorama' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4794, 'Cotia', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4795, 'Cravinhos' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4796, 'Cristais Paulista', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4797, 'Cruzália', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4798, 'Cruzeiro', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4799, 'Cubatão' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4800, 'Cunha', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4801, 'Descalvado', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4802, 'Diadema' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4803, 'Dirce Reis', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4804, 'Divinolândia', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4805, 'Dobrada' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4806, 'Dois Córregos', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4807, 'Dolcinópolis', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4808, 'Dourado' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4809, 'Dracena' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4810, 'Duartina', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4811, 'Dumont', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4812, 'Echaporã', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4813, 'Eldorado', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4814, 'Elias Fausto', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4815, 'Elisiário' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4816, 'Embaúba' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4817, 'Embu' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4818, 'Embu-Guaçu', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4819, 'Emilianópolis', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4820, 'Engenheiro Coelho', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4821, 'Espírito Santo do Pinhal' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4822, 'Espírito Santo do Turvo', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4823, 'Estiva Gerbi', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4824, 'Estrela d`Oeste', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4825, 'Estrela do Norte' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4826, 'Euclides da Cunha Paulista', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4827, 'Fartura' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4828, 'Fernando Prestes' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4829, 'Fernandópolis', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4830, 'Fernão', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4831, 'Ferraz de Vasconcelos', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4832, 'Flora Rica', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4833, 'Floreal' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4834, 'Florínia', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4835, 'Flórida Paulista' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4836, 'Franca', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4837, 'Francisco Morato' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4838, 'Franco da Rocha', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4839, 'Gabriel Monteiro' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4840, 'Gália', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4841, 'Garça', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4842, 'Gastão Vidigal' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4843, 'Gavião Peixoto' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4844, 'General Salgado', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4845, 'Getulina', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4846, 'Glicério', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4847, 'Guaiçara', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4848, 'Guaimbê' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4849, 'Guaíra', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4850, 'Guapiaçu', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4851, 'Guapiara', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4852, 'Guará', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4853, 'Guaraçaí', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4854, 'Guaraci' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4855, 'Guarani d`Oeste', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4856, 'Guarantã', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4857, 'Guararapes', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4858, 'Guararema' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4859, 'Guaratinguetá', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4860, 'Guareí', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4861, 'Guariba' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4862, 'Guarujá' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4863, 'Guarulhos' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4864, 'Guatapará' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4865, 'Guzolândia', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4866, 'Herculândia' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4867, 'Holambra', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4868, 'Hortolândia' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4869, 'Iacanga' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4870, 'Iacri', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4871, 'Iaras', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4872, 'Ibaté', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4873, 'Ibirá', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4874, 'Ibirarema' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4875, 'Ibitinga', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4876, 'Ibiúna', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4877, 'Icém' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4878, 'Iepê' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4879, 'Igaraçu do Tietê' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4880, 'Igarapava' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4881, 'Igaratá' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4882, 'Iguape', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4883, 'Ilha Comprida', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4884, 'Ilha Solteira', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4885, 'Ilhabela', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4886, 'Indaiatuba', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4887, 'Indiana' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4888, 'Indiaporã' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4889, 'Inúbia Paulista', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4890, 'Ipauçu', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4891, 'Iperó', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4892, 'Ipeúna', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4893, 'Ipiguá', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4894, 'Iporanga', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4895, 'Ipuã' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4896, 'Iracemápolis', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4897, 'Irapuã', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4898, 'Irapuru' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4899, 'Itaberá' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4900, 'Itaí' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4901, 'Itajobi' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4902, 'Itaju', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4903, 'Itanhaém', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4904, 'Itaóca', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4905, 'Itapecerica da Serra' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4906, 'Itapetininga', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4907, 'Itapeva' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4908, 'Itapevi' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4909, 'Itapira' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4910, 'Itapirapuã Paulista', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4911, 'Itápolis', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4912, 'Itaporanga', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4913, 'Itapuí', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4914, 'Itapura' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4915, 'Itaquaquecetuba', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4916, 'Itararé' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4917, 'Itariri' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4918, 'Itatiba' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4919, 'Itatinga', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4920, 'Itirapina' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4921, 'Itirapuã', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4922, 'Itobi', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4923, 'Itu', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4924, 'Itupeva' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4925, 'Ituverava' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4926, 'Jaborandi' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4927, 'Jaboticabal' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4928, 'Jacareí' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4929, 'Jaci' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4930, 'Jacupiranga' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4931, 'Jaguariúna', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4932, 'Jales', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4933, 'Jambeiro', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4934, 'Jandira' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4935, 'Jardinópolis', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4936, 'Jarinu', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4937, 'Jaú', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4938, 'Jeriquara' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4939, 'Joanópolis', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4940, 'João Ramalho', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4941, 'José Bonifácio' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4942, 'Júlio Mesquita' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4943, 'Jumirim' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4944, 'Jundiaí' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4945, 'Junqueirópolis' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4946, 'Juquiá', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4947, 'Juquitiba' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4948, 'Lagoinha', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4949, 'Laranjal Paulista', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4950, 'Lavínia' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4951, 'Lavrinhas' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4952, 'Leme' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4953, 'Lençóis Paulista' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4954, 'Limeira' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4955, 'Lindóia' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4956, 'Lins' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4957, 'Lorena', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4958, 'Lourdes' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4959, 'Louveira', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4960, 'Lucélia' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4961, 'Lucianópolis', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4962, 'Luís Antônio', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4963, 'Luiziânia' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4964, 'Lupércio', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4965, 'Lutécia' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4966, 'Macatuba', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4967, 'Macaubal', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4968, 'Macedônia' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4969, 'Magda', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4970, 'Mairinque' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4971, 'Mairiporã' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4972, 'Manduri' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4973, 'Marabá Paulista', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4974, 'Maracaí' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4975, 'Marapoama' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4976, 'Mariápolis', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4977, 'Marília' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4978, 'Marinópolis' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4979, 'Martinópolis', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4980, 'Matão', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4981, 'Mauá' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4982, 'Mendonça', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4983, 'Meridiano' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4984, 'Mesópolis' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4985, 'Miguelópolis', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4986, 'Mineiros do Tietê', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4987, 'Mira Estrela', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4988, 'Miracatu', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4989, 'Mirandópolis', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4990, 'Mirante do Paranapanema', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4991, 'Mirassol', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4992, 'Mirassolândia', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4993, 'Mococa', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4994, 'Mogi das Cruzes', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4995, 'Mogi Guaçu', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4996, 'Mogi-Mirim', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4997, 'Mombuca' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4998, 'Monções' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (4999, 'Mongaguá', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5000, 'Monte Alegre do Sul', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5001, 'Monte Alto', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5002, 'Monte Aprazível', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5003, 'Monte Azul Paulista', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5004, 'Monte Castelo', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5005, 'Monte Mor' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5006, 'Monteiro Lobato', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5007, 'Morro Agudo' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5008, 'Morungaba' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5009, 'Motuca', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5010, 'Murutinga do Sul' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5011, 'Nantes', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5012, 'Narandiba' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5013, 'Natividade da Serra', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5014, 'Nazaré Paulista', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5015, 'Neves Paulista' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5016, 'Nhandeara' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5017, 'Nipoã', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5018, 'Nova Aliança', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5019, 'Nova Campina', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5020, 'Nova Canaã Paulista', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5021, 'Nova Castilho', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5022, 'Nova Europa' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5023, 'Nova Granada', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5024, 'Nova Guataporanga', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5025, 'Nova Independência', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5026, 'Nova Luzitânia' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5027, 'Nova Odessa' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5028, 'Novais', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5029, 'Novo Horizonte' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5030, 'Nuporanga' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5031, 'Ocauçu', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5032, 'Óleo' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5033, 'Olímpia' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5034, 'Onda Verde', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5035, 'Oriente' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5036, 'Orindiúva' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5037, 'Orlândia', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5038, 'Osasco', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5039, 'Oscar Bressane' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5040, 'Osvaldo Cruz', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5041, 'Ourinhos', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5042, 'Ouro Verde', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5043, 'Ouroeste', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5044, 'Pacaembu', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5045, 'Palestina' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5046, 'Palmares Paulista', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5047, 'Palmeira d`Oeste' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5048, 'Palmital', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5049, 'Panorama', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5050, 'Paraguaçu Paulista', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5051, 'Paraibuna' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5052, 'Paraíso' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5053, 'Paranapanema', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5054, 'Paranapuã' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5055, 'Parapuã' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5056, 'Pardinho', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5057, 'Pariquera-Açu', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5058, 'Parisi', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5059, 'Patrocínio Paulista', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5060, 'Paulicéia' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5061, 'Paulínia', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5062, 'Paulistânia' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5063, 'Paulo de Faria' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5064, 'Pederneiras' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5065, 'Pedra Bela', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5066, 'Pedranópolis', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5067, 'Pedregulho', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5068, 'Pedreira', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5069, 'Pedrinhas Paulista', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5070, 'Pedro de Toledo', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5071, 'Penápolis' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5072, 'Pereira Barreto', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5073, 'Pereiras', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5074, 'Peruíbe' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5075, 'Piacatu' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5076, 'Piedade' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5077, 'Pilar do Sul', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5078, 'Pindamonhangaba', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5079, 'Pindorama' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5080, 'Pinhalzinho' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5081, 'Piquerobi' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5082, 'Piquete' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5083, 'Piracaia', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5084, 'Piracicaba', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5085, 'Piraju', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5086, 'Pirajuí' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5087, 'Pirangi' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5088, 'Pirapora do Bom Jesus', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5089, 'Pirapozinho' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5090, 'Pirassununga', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5091, 'Piratininga' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5092, 'Pitangueiras', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5093, 'Planalto', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5094, 'Platina' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5095, 'Poá', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5096, 'Poloni', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5097, 'Pompéia' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5098, 'Pongaí', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5099, 'Pontal', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5100, 'Pontalinda', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5101, 'Pontes Gestal', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5102, 'Populina', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5103, 'Porangaba' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5104, 'Porto Feliz' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5105, 'Porto Ferreira' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5106, 'Potim', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5107, 'Potirendaba' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5108, 'Pracinha', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5109, 'Pradópolis', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5110, 'Praia Grande', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5111, 'Pratânia', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5112, 'Presidente Alves' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5113, 'Presidente Bernardes' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5114, 'Presidente Epitácio', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5115, 'Presidente Prudente', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5116, 'Presidente Venceslau' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5117, 'Promissão' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5118, 'Quadra', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5119, 'Quatá', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5120, 'Queiroz' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5121, 'Queluz', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5122, 'Quintana', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5123, 'Rafard', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5124, 'Rancharia' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5125, 'Redenção da Serra', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5126, 'Regente Feijó', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5127, 'Reginópolis' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5128, 'Registro', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5129, 'Restinga', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5130, 'Ribeira' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5131, 'Ribeirão Bonito', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5132, 'Ribeirão Branco', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5133, 'Ribeirão Corrente', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5134, 'Ribeirão do Sul', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5135, 'Ribeirão dos Índios', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5136, 'Ribeirão Grande', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5137, 'Ribeirão Pires' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5138, 'Ribeirão Preto' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5139, 'Rifaina' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5140, 'Rincão', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5141, 'Rinópolis' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5142, 'Rio Claro' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5143, 'Rio das Pedras' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5144, 'Rio Grande da Serra', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5145, 'Riolândia' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5146, 'Riversul', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5147, 'Rosana', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5148, 'Roseira' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5149, 'Rubiácea', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5150, 'Rubinéia', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5151, 'Sabino', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5152, 'Sagres', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5153, 'Sales', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5154, 'Sales Oliveira' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5155, 'Salesópolis' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5156, 'Salmourão' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5157, 'Saltinho', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5158, 'Salto', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5159, 'Salto de Pirapora', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5160, 'Salto Grande', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5161, 'Sandovalina' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5162, 'Santa Adélia', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5163, 'Santa Albertina', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5164, 'Santa Bárbara d`Oeste', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5165, 'Santa Branca', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5166, 'Santa Clara d`Oeste', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5167, 'Santa Cruz da Conceição', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5168, 'Santa Cruz da Esperança', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5169, 'Santa Cruz das Palmeiras' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5170, 'Santa Cruz do Rio Pardo', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5171, 'Santa Ernestina', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5172, 'Santa Fé do Sul', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5173, 'Santa Gertrudes', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5174, 'Santa Isabel', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5175, 'Santa Lúcia' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5176, 'Santa Maria da Serra' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5177, 'Santa Mercedes' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5178, 'Santa Rita d`Oeste', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5179, 'Santa Rita do Passa Quatro', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5180, 'Santa Rosa de Viterbo', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5181, 'Santa Salete', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5182, 'Santana da Ponte Pensa' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5183, 'Santana de Parnaíba', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5184, 'Santo Anastácio', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5185, 'Santo André' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5186, 'Santo Antônio da Alegria' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5187, 'Santo Antônio de Posse' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5188, 'Santo Antônio do Aracanguá', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5189, 'Santo Antônio do Jardim', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5190, 'Santo Antônio do Pinhal', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5191, 'Santo Expedito' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5192, 'Santópolis do Aguapeí', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5193, 'Santos', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5194, 'São Bento do Sapucaí' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5195, 'São Bernardo do Campo', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5196, 'São Caetano do Sul', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5197, 'São Carlos', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5198, 'São Francisco', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5199, 'São João da Boa Vista', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5200, 'São João das Duas Pontes' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5201, 'São João de Iracema', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5202, 'São João do Pau d`Alho' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5203, 'São Joaquim da Barra' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5204, 'São José da Bela Vista' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5205, 'São José do Barreiro' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5206, 'São José do Rio Pardo', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5207, 'São José do Rio Preto', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5208, 'São José dos Campos', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5209, 'São Lourenço da Serra', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5210, 'São Luís do Paraitinga' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5211, 'São Manuel', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5212, 'São Miguel Arcanjo', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5213, 'São Paulo' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5214, 'São Pedro' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5215, 'São Pedro do Turvo', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5216, 'São Roque' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5217, 'São Sebastião', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5218, 'São Sebastião da Grama' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5219, 'São Simão' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5220, 'São Vicente' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5221, 'Sarapuí' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5222, 'Sarutaiá', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5223, 'Sebastianópolis do Sul' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5224, 'Serra Azul', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5225, 'Serra Negra' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5226, 'Serrana' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5227, 'Sertãozinho' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5228, 'Sete Barras' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5229, 'Severínia' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5230, 'Silveiras' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5231, 'Socorro' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5232, 'Sorocaba', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5233, 'Sud Mennucci', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5234, 'Sumaré', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5235, 'Suzanápolis' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5236, 'Suzano', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5237, 'Tabapuã' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5238, 'Tabatinga' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5239, 'Taboão da Serra', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5240, 'Taciba', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5241, 'Taguaí', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5242, 'Taiaçu', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5243, 'Taiúva', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5244, 'Tambaú', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5245, 'Tanabi', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5246, 'Tapiraí' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5247, 'Tapiratiba', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5248, 'Taquaral', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5249, 'Taquaritinga', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5250, 'Taquarituba' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5251, 'Taquarivaí', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5252, 'Tarabai' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5253, 'Tarumã', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5254, 'Tatuí', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5255, 'Taubaté' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5256, 'Tejupá', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5257, 'Teodoro Sampaio', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5258, 'Terra Roxa', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5259, 'Tietê', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5260, 'Timburi' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5261, 'Torre de Pedra' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5262, 'Torrinha', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5263, 'Trabiju' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5264, 'Tremembé', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5265, 'Três Fronteiras', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5266, 'Tuiuti', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5267, 'Tupã' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5268, 'Tupi Paulista', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5269, 'Turiúba' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5270, 'Turmalina' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5271, 'Ubarana' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5272, 'Ubatuba' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5273, 'Ubirajara' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5274, 'Uchoa', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5275, 'União Paulista' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5276, 'Urânia', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5277, 'Uru', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5278, 'Urupês', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5279, 'Valentim Gentil', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5280, 'Valinhos', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5281, 'Valparaíso', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5282, 'Vargem', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5283, 'Vargem Grande do Sul' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5284, 'Vargem Grande Paulista' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5285, 'Várzea Paulista', 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5286, 'Vera Cruz' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5287, 'Vinhedo' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5288, 'Viradouro' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5289, 'Vista Alegre do Alto' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5290, 'Vitória Brasil' , 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5291, 'Votorantim', 02);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5292, 'Votuporanga' , 02 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5293, 'Zacarias', 02 );

INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5294, 'Amparo de São Francisco', 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5295, 'Aquidabã', 27 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5296, 'Aracaju' , 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5297, 'Arauá', 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5298, 'Areia Branca', 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5299, 'Barra dos Coqueiros', 27 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5300, 'Boquim', 27 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5301, 'Brejo Grande', 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5302, 'Campo do Brito' , 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5303, 'Canhoba' , 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5304, 'Canindé de São Francisco' , 27 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5305, 'Capela', 27 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5306, 'Carira', 27 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5307, 'Carmópolis', 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5308, 'Cedro de São João', 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5309, 'Cristinápolis', 27 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5310, 'Cumbe', 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5311, 'Divina Pastora' , 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5312, 'Estância', 27 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5313, 'Feira Nova', 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5314, 'Frei Paulo', 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5315, 'Gararu', 27 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5316, 'General Maynard', 27 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5317, 'Gracho Cardoso' , 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5318, 'Ilha das Flores', 27 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5319, 'Indiaroba' , 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5320, 'Itabaiana' , 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5321, 'Itabaianinha', 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5322, 'Itabi', 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5323, 'Itaporanga d`Ajuda', 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5324, 'Japaratuba', 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5325, 'Japoatã' , 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5326, 'Lagarto' , 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5327, 'Laranjeiras' , 27 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5328, 'Macambira' , 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5329, 'Malhada dos Bois' , 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5330, 'Malhador', 27 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5331, 'Maruim', 27 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5332, 'Moita Bonita', 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5333, 'Monte Alegre de Sergipe', 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5334, 'Muribeca', 27 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5335, 'Neópolis', 27 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5336, 'Nossa Senhora Aparecida', 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5337, 'Nossa Senhora da Glória', 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5338, 'Nossa Senhora das Dores', 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5339, 'Nossa Senhora de Lourdes' , 27 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5340, 'Nossa Senhora do Socorro' , 27 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5341, 'Pacatuba', 27 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5342, 'Pedra Mole', 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5343, 'Pedrinhas' , 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5344, 'Pinhão', 27 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5345, 'Pirambu' , 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5346, 'Poço Redondo', 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5347, 'Poço Verde', 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5348, 'Porto da Folha' , 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5349, 'Propriá' , 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5350, 'Riachão do Dantas', 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5351, 'Riachuelo' , 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5352, 'Ribeirópolis', 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5353, 'Rosário do Catete', 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5354, 'Salgado' , 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5355, 'Santa Luzia do Itanhy', 27 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5356, 'Santa Rosa de Lima', 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5357, 'Santana do São Francisco' , 27 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5358, 'Santo Amaro das Brotas' , 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5359, 'São Cristóvão', 27 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5360, 'São Domingos', 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5361, 'São Francisco', 27 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5362, 'São Miguel do Aleixo' , 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5363, 'Simão Dias', 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5364, 'Siriri', 27 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5365, 'Telha', 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5366, 'Tobias Barreto' , 27);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5367, 'Tomar do Geru', 27 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5368, 'Umbaúba' , 27);

INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5369, 'Abreulândia' , 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5370, 'Aguiarnópolis', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5371, 'Aliança do Tocantins' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5372, 'Almas', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5373, 'Alvorada', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5374, 'Ananás', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5375, 'Angico', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5376, 'Aparecida do Rio Negro' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5377, 'Aragominas', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5378, 'Araguacema', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5379, 'Araguaçu', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5380, 'Araguaína' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5381, 'Araguanã', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5382, 'Araguatins', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5383, 'Arapoema', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5384, 'Arraias' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5385, 'Augustinópolis' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5386, 'Aurora do Tocantins', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5387, 'Axixá do Tocantins', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5388, 'Babaçulândia', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5389, 'Bandeirantes do Tocantins', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5390, 'Barra do Ouro', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5391, 'Barrolândia' , 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5392, 'Bernardo Sayão' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5393, 'Bom Jesus do Tocantins' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5394, 'Brasilândia do Tocantins' , 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5395, 'Brejinho de Nazaré', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5396, 'Buriti do Tocantins', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5397, 'Cachoeirinha', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5398, 'Campos Lindos', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5399, 'Cariri do Tocantins', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5400, 'Carmolândia' , 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5401, 'Carrasco Bonito', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5402, 'Caseara' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5403, 'Centenário', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5404, 'Chapada da Natividade', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5405, 'Chapada de Areia' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5406, 'Colinas do Tocantins' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5407, 'Colméia' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5408, 'Combinado' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5409, 'Conceição do Tocantins' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5410, 'Couto de Magalhães', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5411, 'Cristalândia', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5412, 'Crixás do Tocantins', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5413, 'Darcinópolis', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5414, 'Dianópolis', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5415, 'Divinópolis do Tocantins' , 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5416, 'Dois Irmãos do Tocantins' , 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5417, 'Dueré', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5418, 'Esperantina' , 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5419, 'Fátima', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5420, 'Figueirópolis', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5421, 'Filadélfia', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5422, 'Formoso do Araguaia', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5423, 'Fortaleza do Tabocão' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5424, 'Goianorte' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5425, 'Goiatins', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5426, 'Guaraí', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5427, 'Gurupi', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5428, 'Ipueiras', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5429, 'Itacajá' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5430, 'Itaguatins', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5431, 'Itapiratins' , 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5432, 'Itaporã do Tocantins' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5433, 'Jaú do Tocantins' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5434, 'Juarina' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5435, 'Lagoa da Confusão', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5436, 'Lagoa do Tocantins', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5437, 'Lajeado' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5438, 'Lavandeira', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5439, 'Lizarda' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5440, 'Luzinópolis' , 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5441, 'Marianópolis do Tocantins', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5442, 'Mateiros', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5443, 'Maurilândia do Tocantins' , 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5444, 'Miracema do Tocantins', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5445, 'Miranorte' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5446, 'Monte do Carmo' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5447, 'Monte Santo do Tocantins' , 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5448, 'Muricilândia', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5449, 'Natividade', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5450, 'Nazaré', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5451, 'Nova Olinda' , 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5452, 'Nova Rosalândia', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5453, 'Novo Acordo' , 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5454, 'Novo Alegre' , 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5455, 'Novo Jardim' , 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5456, 'Oliveira de Fátima', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5457, 'Palmas', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5458, 'Palmeirante' , 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5459, 'Palmeiras do Tocantins' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5460, 'Palmeirópolis', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5461, 'Paraíso do Tocantins' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5462, 'Paranã', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5463, 'Pau d`Arco', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5464, 'Pedro Afonso', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5465, 'Peixe', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5466, 'Pequizeiro', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5467, 'Pindorama do Tocantins' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5468, 'Piraquê' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5469, 'Pium' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5470, 'Ponte Alta do Bom Jesus', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5471, 'Ponte Alta do Tocantins', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5472, 'Porto Alegre do Tocantins', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5473, 'Porto Nacional' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5474, 'Praia Norte' , 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5475, 'Presidente Kennedy', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5476, 'Pugmil', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5477, 'Recursolândia', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5478, 'Riachinho' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5479, 'Rio da Conceição' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5480, 'Rio dos Bois', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5481, 'Rio Sono', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5482, 'Sampaio' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5483, 'Sandolândia' , 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5484, 'Santa Fé do Araguaia' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5485, 'Santa Maria do Tocantins' , 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5486, 'Santa Rita do Tocantins', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5487, 'Santa Rosa do Tocantins', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5488, 'Santa Tereza do Tocantins', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5489, 'Santa Terezinha do Tocantins', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5490, 'São Bento do Tocantins' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5491, 'São Félix do Tocantins' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5492, 'São Miguel do Tocantins', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5493, 'São Salvador do Tocantins', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5494, 'São Sebastião do Tocantins', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5495, 'São Valério da Natividade', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5496, 'Silvanópolis', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5497, 'Sítio Novo do Tocantins', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5498, 'Sucupira', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5499, 'Taguatinga', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5500, 'Taipas do Tocantins', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5501, 'Talismã' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5502, 'Tocantínia', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5503, 'Tocantinópolis' , 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5504, 'Tupirama', 05 );
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5505, 'Tupiratins', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5506, 'Wanderlândia', 05);
INSERT INTO `cidades` (idcidade, nomecidade, iduf) VALUES (5507, 'Xambioá' , 05);
INSERT INTO `cidades` (idcidade,nomecidade,iduf) VALUES (5508,'Brazlandia',1);
INSERT INTO `cidades` (idcidade,nomecidade,iduf) VALUES (5509,'Candangolandia',1);
INSERT INTO `cidades` (idcidade,nomecidade,iduf) VALUES (5510,'Ceilandia',1);
INSERT INTO `cidades` (idcidade,nomecidade,iduf) VALUES (5511,'Cruzeiro',1);
INSERT INTO `cidades` (idcidade,nomecidade,iduf) VALUES (5512,'Gama',1);
INSERT INTO `cidades` (idcidade,nomecidade,iduf) VALUES (5513,'Guara',1);
INSERT INTO `cidades` (idcidade,nomecidade,iduf) VALUES (5514,'Lago Norte',1);
INSERT INTO `cidades` (idcidade,nomecidade,iduf) VALUES (5515,'Lago Sul',1);
INSERT INTO `cidades` (idcidade,nomecidade,iduf) VALUES (5516,'Nucleo Bandeirante',1);
INSERT INTO `cidades` (idcidade,nomecidade,iduf) VALUES (5517,'Paranoa',1);
INSERT INTO `cidades` (idcidade,nomecidade,iduf) VALUES (5518,'Planaltina',1);
INSERT INTO `cidades` (idcidade,nomecidade,iduf) VALUES (5519,'Recanto das Emas',1);
INSERT INTO `cidades` (idcidade,nomecidade,iduf) VALUES (5520,'Riacho Fundo',1);
INSERT INTO `cidades` (idcidade,nomecidade,iduf) VALUES (5521,'Samambaia',1);
INSERT INTO `cidades` (idcidade,nomecidade,iduf) VALUES (5522,'Santa Maria',1);
INSERT INTO `cidades` (idcidade,nomecidade,iduf) VALUES (5523,'Sao Sebastiao',1);
INSERT INTO `cidades` (idcidade,nomecidade,iduf) VALUES (5524,'Sobradinho',1);
INSERT INTO `cidades` (idcidade,nomecidade,iduf) VALUES (5525,'Taguatinga',1);
INSERT INTO `cidades` (idcidade,nomecidade,iduf) VALUES (5526,'Abadia de Goiás',4);
/*!40000 ALTER TABLE `cidades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientes`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clientes` (
  `idcliente` int(11) NOT NULL,
  `nomerazaosocial` varchar(70) NOT NULL,
  `nomefantasia` varchar(70) DEFAULT NULL,
  `cpfcnpj` varchar(14) DEFAULT NULL,
  `observacoes` text,
  `situacao` char(1) NOT NULL,
  `deleted` char(1) DEFAULT NULL,
  PRIMARY KEY (`idcliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='clientes';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (1,'Cliente 1','Cliente 1','','','A',NULL);
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `colecao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `colecao` (
  `idcolecao` int(11) NOT NULL,
  `nome` varchar(256) DEFAULT NULL,
  `idteste` int(11) DEFAULT NULL,
  PRIMARY KEY (`idcolecao`),
  KEY `fk_idteste` (`idteste`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='colecao';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `colecao`
--

LOCK TABLES `colecao` WRITE;
/*!40000 ALTER TABLE `colecao` DISABLE KEYS */;
/*!40000 ALTER TABLE `colecao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coletapreco`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coletapreco` (
  `idcoletapreco` int(11) NOT NULL,
  `idfornecedor` bigint(20) NOT NULL,
  `iditemcotacao` int(11) NOT NULL,
  `idresponsavel` int(11) NOT NULL,
  `idrespresultado` int(11) DEFAULT NULL,
  `idjustifresultado` int(11) DEFAULT NULL,
  `datacoleta` date NOT NULL,
  `datavalidade` date DEFAULT NULL,
  `especificacoes` text,
  `preco` decimal(8,2) NOT NULL,
  `valoracrescimo` decimal(8,2) NOT NULL,
  `valordesconto` decimal(8,2) NOT NULL,
  `valorfrete` decimal(8,2) NOT NULL,
  `prazoentrega` int(11) NOT NULL,
  `prazomediopagto` decimal(4,2) NOT NULL,
  `taxajuros` decimal(4,2) NOT NULL,
  `prazogarantia` int(11) NOT NULL,
  `quantidadecotada` decimal(8,2) NOT NULL,
  `pontuacao` decimal(8,4) DEFAULT NULL,
  `resultadocalculo` char(1) DEFAULT NULL COMMENT 'M - Melhor cotação\n            E - Empate\n            N - Não vencedora',
  `quantidadecalculo` decimal(8,2) DEFAULT NULL,
  `resultadofinal` char(1) DEFAULT NULL COMMENT 'M - Melhor cotação\n            N - Não vencedora',
  `quantidadecompra` decimal(8,2) DEFAULT NULL,
  `complemjustifresultado` text,
  `quantidadeaprovada` decimal(8,2) DEFAULT NULL,
  `quantidadepedido` decimal(8,2) DEFAULT NULL,
  PRIMARY KEY (`idcoletapreco`),
  KEY `fk_reference_28` (`iditemcotacao`),
  KEY `fk_reference_635` (`idresponsavel`),
  KEY `fk_reference_636` (`idfornecedor`),
  KEY `fk_reference_695` (`idrespresultado`),
  KEY `fk_reference_698` (`idjustifresultado`),
  CONSTRAINT `fk_reference_28` FOREIGN KEY (`iditemcotacao`) REFERENCES `itemcotacao` (`iditemcotacao`),
  CONSTRAINT `fk_reference_635` FOREIGN KEY (`idresponsavel`) REFERENCES `empregados` (`idempregado`),
  CONSTRAINT `fk_reference_636` FOREIGN KEY (`idfornecedor`) REFERENCES `fornecedor` (`idfornecedor`),
  CONSTRAINT `fk_reference_695` FOREIGN KEY (`idrespresultado`) REFERENCES `empregados` (`idempregado`),
  CONSTRAINT `fk_reference_698` FOREIGN KEY (`idjustifresultado`) REFERENCES `justificativaparecer` (`idjustificativa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coletapreco`
--

LOCK TABLES `coletapreco` WRITE;
/*!40000 ALTER TABLE `coletapreco` DISABLE KEYS */;
/*!40000 ALTER TABLE `coletapreco` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comando`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comando` (
  `id` int(11) NOT NULL,
  `descricao` varchar(400) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='comando';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comando`
--

LOCK TABLES `comando` WRITE;
/*!40000 ALTER TABLE `comando` DISABLE KEYS */;
/*!40000 ALTER TABLE `comando` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comandosistemaoperacional`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comandosistemaoperacional` (
  `id` int(11) NOT NULL,
  `idcomando` int(11) NOT NULL,
  `idsistemaoperacional` int(11) NOT NULL,
  `comando` varchar(255) NOT NULL,
  `sistemaoperacional` varchar(255) DEFAULT NULL,
  `comandosistemaoperacional` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `INDEX_COMANDOSISTEMAOPERACIONAL` (`idsistemaoperacional`),
  KEY `fk_comando` (`idcomando`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='comandosistemaoperacional';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comandosistemaoperacional`
--

LOCK TABLES `comandosistemaoperacional` WRITE;
/*!40000 ALTER TABLE `comandosistemaoperacional` DISABLE KEYS */;
/*!40000 ALTER TABLE `comandosistemaoperacional` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comentarios`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comentarios` (
  `idcomentario` int(11) NOT NULL,
  `idbaseconhecimento` int(11) NOT NULL,
  `comentario` text NOT NULL,
  `nome` varchar(255) NOT NULL,
  `nota` varchar(45) NOT NULL,
  `email` varchar(70) NOT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  PRIMARY KEY (`idcomentario`),
  KEY `fk_comentario_baseconhecimento` (`idbaseconhecimento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comentarios`
--

LOCK TABLES `comentarios` WRITE;
/*!40000 ALTER TABLE `comentarios` DISABLE KEYS */;
/*!40000 ALTER TABLE `comentarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `complexidade`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `complexidade` (
  `idcontrato` int(11) NOT NULL DEFAULT '0',
  `complexidade` char(1) NOT NULL,
  `valorcomplexidade` decimal(10,2) DEFAULT NULL,
  `deleted` char(1) DEFAULT NULL,
  PRIMARY KEY (`idcontrato`,`complexidade`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `complexidade`
--

LOCK TABLES `complexidade` WRITE;
/*!40000 ALTER TABLE `complexidade` DISABLE KEYS */;
/*!40000 ALTER TABLE `complexidade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `condicaooperacao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `condicaooperacao` (
  `idcondicaooperacao` int(11) NOT NULL,
  `idempresa` int(11) NOT NULL,
  `nomecondicaooperacao` varchar(150) NOT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  PRIMARY KEY (`idcondicaooperacao`),
  KEY `INDEX_CONDEMPRESA` (`idempresa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='condicaooperacao';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `condicaooperacao`
--

LOCK TABLES `condicaooperacao` WRITE;
/*!40000 ALTER TABLE `condicaooperacao` DISABLE KEYS */;
INSERT INTO `condicaooperacao` VALUES (1,1,'24 X 7','2012-01-01',NULL),(2,1,'Horário Comercial','2012-01-01',NULL);
/*!40000 ALTER TABLE `condicaooperacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conhecimentoic`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `conhecimentoic` (
  `iditemconfiguracao` int(11) NOT NULL,
  `idbaseconhecimento` int(11) NOT NULL,
  PRIMARY KEY (`iditemconfiguracao`,`idbaseconhecimento`),
  KEY `fk_ref_conhic_bc` (`idbaseconhecimento`),
  CONSTRAINT `fk_ref_conhic_bc` FOREIGN KEY (`idbaseconhecimento`) REFERENCES `baseconhecimento` (`idbaseconhecimento`),
  CONSTRAINT `fk_ref_conhic_icc` FOREIGN KEY (`iditemconfiguracao`) REFERENCES `itemconfiguracao` (`iditemconfiguracao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conhecimentoic`
--

LOCK TABLES `conhecimentoic` WRITE;
/*!40000 ALTER TABLE `conhecimentoic` DISABLE KEYS */;
/*!40000 ALTER TABLE `conhecimentoic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conhecimentomudanca`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `conhecimentomudanca` (
  `idrequisicaomudanca` int(11) NOT NULL,
  `idbaseconhecimento` int(11) NOT NULL,
  PRIMARY KEY (`idrequisicaomudanca`,`idbaseconhecimento`),
  KEY `fk_ref_conhmud_bc` (`idbaseconhecimento`),
  CONSTRAINT `fk_ref_conhmd_icc` FOREIGN KEY (`idrequisicaomudanca`) REFERENCES `requisicaomudanca` (`idrequisicaomudanca`),
  CONSTRAINT `fk_ref_conhmud_bc` FOREIGN KEY (`idbaseconhecimento`) REFERENCES `baseconhecimento` (`idbaseconhecimento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conhecimentomudanca`
--

LOCK TABLES `conhecimentomudanca` WRITE;
/*!40000 ALTER TABLE `conhecimentomudanca` DISABLE KEYS */;
/*!40000 ALTER TABLE `conhecimentomudanca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conhecimentoproblema`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `conhecimentoproblema` (
  `idproblema` int(11) NOT NULL,
  `idbaseconhecimento` int(11) NOT NULL,
  PRIMARY KEY (`idproblema`,`idbaseconhecimento`),
  KEY `fk_ref_conhpb_bc` (`idbaseconhecimento`),
  CONSTRAINT `fk_ref_conhpb_bc` FOREIGN KEY (`idbaseconhecimento`) REFERENCES `baseconhecimento` (`idbaseconhecimento`),
  CONSTRAINT `fk_ref_conhpb_icc` FOREIGN KEY (`idproblema`) REFERENCES `problema` (`idproblema`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conhecimentoproblema`
--

LOCK TABLES `conhecimentoproblema` WRITE;
/*!40000 ALTER TABLE `conhecimentoproblema` DISABLE KEYS */;
/*!40000 ALTER TABLE `conhecimentoproblema` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conhecimentosolicitacaoservico`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `conhecimentosolicitacaoservico` (
  `idsolicitacaoservico` bigint(20) NOT NULL,
  `idbaseconhecimento` int(11) NOT NULL,
  PRIMARY KEY (`idsolicitacaoservico`,`idbaseconhecimento`),
  KEY `fk_ref_conhss_bc` (`idbaseconhecimento`),
  CONSTRAINT `fk_ref_conhss_bc` FOREIGN KEY (`idbaseconhecimento`) REFERENCES `baseconhecimento` (`idbaseconhecimento`),
  CONSTRAINT `fk_ref_conhss_icc` FOREIGN KEY (`idsolicitacaoservico`) REFERENCES `solicitacaoservico` (`idsolicitacaoservico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conhecimentosolicitacaoservico`
--

LOCK TABLES `conhecimentosolicitacaoservico` WRITE;
/*!40000 ALTER TABLE `conhecimentosolicitacaoservico` DISABLE KEYS */;
/*!40000 ALTER TABLE `conhecimentosolicitacaoservico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contadoracesso`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contadoracesso` (
  `idcontadoracesso` int(11) NOT NULL,
  `idusuario` int(11) DEFAULT NULL,
  `idbaseconhecimento` int(11) DEFAULT NULL,
  `datahoraacesso` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `contadoracesso` int(11) NOT NULL,
  PRIMARY KEY (`idcontadoracesso`),
  KEY `fk_reference_608` (`idusuario`),
  CONSTRAINT `fk_reference_608` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`idusuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contadoracesso`
--

LOCK TABLES `contadoracesso` WRITE;
/*!40000 ALTER TABLE `contadoracesso` DISABLE KEYS */;
/*!40000 ALTER TABLE `contadoracesso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contatocliente`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contatocliente` (
  `idcontatocliente` int(11) NOT NULL,
  `idcliente` int(11) NOT NULL,
  `nome` varchar(80) NOT NULL,
  `telefones` varchar(100) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `departamento` varchar(100) DEFAULT NULL,
  `observacoes` text,
  `datainicio` date DEFAULT NULL,
  `datafim` date DEFAULT NULL,
  PRIMARY KEY (`idcontatocliente`),
  KEY `INDEX_CONTATOCLIENTE` (`idcliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='contatocliente';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contatocliente`
--

LOCK TABLES `contatocliente` WRITE;
/*!40000 ALTER TABLE `contatocliente` DISABLE KEYS */;
/*!40000 ALTER TABLE `contatocliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contatorequisicaomudanca`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contatorequisicaomudanca` (
  `idcontatorequisicaomudanca` int(10) NOT NULL,
  `nomecontato` varchar(100) DEFAULT NULL,
  `telefonecontato` varchar(100) DEFAULT NULL,
  `emailcontato` varchar(200) DEFAULT NULL,
  `observacao` text,
  `idlocalidade` int(11) DEFAULT NULL,
  `ramal` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`idcontatorequisicaomudanca`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contatorequisicaomudanca`
--

LOCK TABLES `contatorequisicaomudanca` WRITE;
/*!40000 ALTER TABLE `contatorequisicaomudanca` DISABLE KEYS */;
/*!40000 ALTER TABLE `contatorequisicaomudanca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contatosolicitacaoservico`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contatosolicitacaoservico` (
  `idcontatosolicitacaoservico` int(11) NOT NULL,
  `nomecontato` varchar(70) NOT NULL,
  `telefonecontato` varchar(70) DEFAULT NULL,
  `emailcontato` varchar(120) NOT NULL,
  `localizacaofisica` text,
  `idlocalidade` int(11) DEFAULT NULL,
  `ramal` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`idcontatosolicitacaoservico`),
  KEY `fk_reference_622` (`idlocalidade`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contatosolicitacaoservico`
--

LOCK TABLES `contatosolicitacaoservico` WRITE;
/*!40000 ALTER TABLE `contatosolicitacaoservico` DISABLE KEYS */;
INSERT INTO `contatosolicitacaoservico` VALUES (1,'Default','3242-4433','citsmart_instalador_mysql@centralit.com.br','',NULL,NULL),(2,'Default','3242-4433','citsmart_instalador_mysql@centralit.com.br','',NULL,NULL),(3,'Consultor','3242-4433','citsmart_instalador_mysql@centralit.com.br','',NULL,NULL),(4,'Consultor','3242-4433','citsmart_instalador_mysql@centralit.com.br','',NULL,NULL);
/*!40000 ALTER TABLE `contatosolicitacaoservico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contratoquestionarios`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contratoquestionarios` (
  `idcontratoquestionario` int(11) NOT NULL,
  `idquestionario` int(11) NOT NULL,
  `idcontrato` int(11) NOT NULL,
  `dataquestionario` date NOT NULL,
  `idprofissional` int(11) DEFAULT NULL,
  `idempresa` int(11) DEFAULT NULL,
  `aba` varchar(30) DEFAULT NULL,
  `situacao` char(1) DEFAULT NULL,
  `situacaocomplemento` char(1) DEFAULT NULL,
  `datahoragrav` date DEFAULT NULL,
  `migrado` char(1) DEFAULT NULL,
  `conteudoimpresso` text,
  `idmigracao` int(11) DEFAULT NULL,
  PRIMARY KEY (`idcontratoquestionario`),
  KEY `INDEX_CONTRATOQUESTIONARIO` (`idquestionario`),
  KEY `INDEX_CONTRATOCONTRATO` (`idcontrato`),
  KEY `INDEX_CONTRATOPROFISSIONAL` (`idprofissional`),
  KEY `INDEX_CONTRATOEMPRESA` (`idempresa`),
  KEY `INDEX_CONTRATOMIGRACAO` (`idmigracao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='contratoquestionarios';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contratoquestionarios`
--

LOCK TABLES `contratoquestionarios` WRITE;
/*!40000 ALTER TABLE `contratoquestionarios` DISABLE KEYS */;
/*!40000 ALTER TABLE `contratoquestionarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contratos`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contratos` (
  `idcontrato` int(11) NOT NULL,
  `idcliente` int(11) NOT NULL,
  `idmoeda` int(11) DEFAULT NULL,
  `idfornecedor` bigint(20) NOT NULL,
  `numero` varchar(30) NOT NULL,
  `objeto` text NOT NULL,
  `datacontrato` date NOT NULL,
  `valorestimado` decimal(18,3) DEFAULT NULL,
  `tipotempoestimado` char(1) DEFAULT NULL,
  `tempoestimado` int(11) DEFAULT NULL,
  `tipo` char(1) NOT NULL,
  `situacao` char(1) NOT NULL,
  `cotacaomoeda` decimal(18,3) DEFAULT NULL,
  `cadastromanualusuario` char(1) DEFAULT NULL,
  `deleted` char(1) DEFAULT NULL,
  `idgruposolicitante` int(11) DEFAULT NULL,
  `datafimcontrato` date DEFAULT NULL,
  PRIMARY KEY (`idcontrato`),
  KEY `fk_reference_26` (`idmoeda`),
  KEY `fk_reference_3` (`idcliente`),
  KEY `fk_reference_60` (`idfornecedor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contratos`
--

LOCK TABLES `contratos` WRITE;
/*!40000 ALTER TABLE `contratos` DISABLE KEYS */;
INSERT INTO `contratos` VALUES (1,1,1,1,'1','Descrição do Contrato','2013-04-01',1000000.000,NULL,NULL,'C','A',1.000,'N',NULL,2,'2014-12-31');
/*!40000 ALTER TABLE `contratos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contratos_hist`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contratos_hist` (
  `idcontrato_hist` int(11) NOT NULL,
  `idcontrato` int(11) NOT NULL,
  `idcliente` int(11) NOT NULL,
  `idmoeda` int(11) DEFAULT NULL,
  `idfornecedor` bigint(20) NOT NULL,
  `numero` varchar(30) NOT NULL,
  `objeto` text NOT NULL,
  `datacontrato` date NOT NULL,
  `valorestimado` decimal(18,3) DEFAULT NULL,
  `tipotempoestimado` char(1) DEFAULT NULL,
  `tempoestimado` int(11) DEFAULT NULL,
  `tipo` char(1) NOT NULL,
  `situacao` char(1) NOT NULL,
  `cotacaomoeda` decimal(18,3) DEFAULT NULL,
  `cadastromanualusuario` char(1) DEFAULT NULL,
  `deleted` char(1) DEFAULT NULL,
  `idgruposolicitante` int(11) DEFAULT NULL,
  `datafimcontrato` date DEFAULT NULL,
  `criadoem` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `criadopor` varchar(256) DEFAULT NULL,
  `modificadoem` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modificadopor` varchar(256) DEFAULT NULL,
  `conteudodados` text,
  PRIMARY KEY (`idcontrato_hist`),
  KEY `fk_reference_26_2` (`idmoeda`),
  KEY `fk_reference_3_2` (`idcliente`),
  KEY `fk_reference_60_2` (`idfornecedor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contratos_hist`
--

LOCK TABLES `contratos_hist` WRITE;
/*!40000 ALTER TABLE `contratos_hist` DISABLE KEYS */;
INSERT INTO `contratos_hist` VALUES (1,1,1,1,1,'1','Descrição do Contrato','2013-04-01',1000000.000,NULL,NULL,'C','A',1.000,NULL,NULL,2,'2014-12-31','2013-04-02 04:00:00','Administrador','2013-04-02 04:00:00','Administrador','JSONDATA: TRUE\nTIPO: C\nNUMERO: 1\nNOCACHE: Tue Apr 02 2013 13:26:45 GMT-0300 (Hora oficial do Brasil)\nIDFORNECEDOR: 1\nKEYCONTROL: \nDINAMICVIEWSJSON_TEMPDATA: \nVALORESTIMADO: 1.000.000,00\nIDCLIENTE: 1\nCADASTROMANUALUSUARIO: N\nCOTACAOMOEDA: 1,00\nIDCONTRATO: 1\nDINAMICVIEWSTABLESVINC: \"TABLE_SEARCH_28\",\"TABLE_SEARCH_64\"\nDINAMICVIEWSACAOPESQUISASELECIONADA: \nDATAFIMCONTRATO: 31/12/2014\nOBJETO: Descrição do Contrato\nIDMOEDA: 1\nDATACONTRATO: 01/04/2013\nACAOFLUXO: \nMODOEXIBICAO: N\nIDGRUPOSOLICITANTE: 2\nSESSION.NUMERO_CONTRATO_EDICAO: null\nDINAMICVIEWSIDVISAOPESQUISASELECIONADA: \nDINAMICVIEWSJSON_DATA: \nIDFLUXO: \nJSONMATRIZ: \nDINAMICVIEWSIDVISAO: 20\nSESSION.DINAMICVIEWS_SAVEINFO: null\nIDTAREFA: \nSITUACAO: A\nDINAMICVIEWSDADOSADICIONAIS: {}\n');
/*!40000 ALTER TABLE `contratos_hist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contratoscolaboradores`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contratoscolaboradores` (
  `idempregado` int(11) NOT NULL,
  `idcontrato` int(11) NOT NULL,
  PRIMARY KEY (`idempregado`,`idcontrato`),
  KEY `fk_reference_569` (`idcontrato`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contratoscolaboradores`
--

LOCK TABLES `contratoscolaboradores` WRITE;
/*!40000 ALTER TABLE `contratoscolaboradores` DISABLE KEYS */;
/*!40000 ALTER TABLE `contratoscolaboradores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contratosgrupos`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contratosgrupos` (
  `idgrupo` int(10) unsigned NOT NULL,
  `idcontrato` int(10) unsigned NOT NULL,
  PRIMARY KEY (`idgrupo`,`idcontrato`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contratosgrupos`
--

LOCK TABLES `contratosgrupos` WRITE;
/*!40000 ALTER TABLE `contratosgrupos` DISABLE KEYS */;
INSERT INTO `contratosgrupos` VALUES (2,1),(3,1),(4,1),(16,1),(17,1),(19,1);
/*!40000 ALTER TABLE `contratosgrupos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contratosunidades`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contratosunidades` (
  `idunidade` int(11) NOT NULL,
  `idcontrato` int(11) NOT NULL DEFAULT '0',
  `contratosunidadescol` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idunidade`,`idcontrato`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contratosunidades`
--

LOCK TABLES `contratosunidades` WRITE;
/*!40000 ALTER TABLE `contratosunidades` DISABLE KEYS */;
/*!40000 ALTER TABLE `contratosunidades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `controleged`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `controleged` (
  `idcontroleged` int(11) NOT NULL,
  `idtabela` smallint(6) NOT NULL,
  `id` int(11) NOT NULL,
  `nomearquivo` varchar(255) NOT NULL,
  `descricaoarquivo` varchar(255) DEFAULT NULL,
  `extensaoarquivo` varchar(5) DEFAULT NULL,
  `datahora` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `pasta` varchar(255) DEFAULT NULL,
  `conteudoarquivo` blob,
  PRIMARY KEY (`idcontroleged`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `controleged`
--

LOCK TABLES `controleged` WRITE;
/*!40000 ALTER TABLE `controleged` DISABLE KEYS */;
/*!40000 ALTER TABLE `controleged` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `controlequestionarios`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `controlequestionarios` (
  `idcontrolequestionario` int(11) NOT NULL,
  PRIMARY KEY (`idcontrolequestionario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='controlequestionarios';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `controlequestionarios`
--

LOCK TABLES `controlequestionarios` WRITE;
/*!40000 ALTER TABLE `controlequestionarios` DISABLE KEYS */;
/*!40000 ALTER TABLE `controlequestionarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cotacao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cotacao` (
  `idcotacao` int(11) NOT NULL,
  `identificacao` varchar(100) DEFAULT NULL,
  `idempresa` int(11) NOT NULL,
  `idresponsavel` int(11) NOT NULL,
  `situacao` varchar(25) NOT NULL,
  `datahoracadastro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `observacoes` text,
  `datafinalprevista` date DEFAULT NULL,
  PRIMARY KEY (`idcotacao`),
  KEY `fk_reference_633` (`idempresa`),
  CONSTRAINT `fk_reference_633` FOREIGN KEY (`idempresa`) REFERENCES `empresa` (`idempresa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cotacao`
--

LOCK TABLES `cotacao` WRITE;
/*!40000 ALTER TABLE `cotacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `cotacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cotacaoitemrequisicao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cotacaoitemrequisicao` (
  `idcoletapreco` int(11) NOT NULL,
  `iditemrequisicaoproduto` int(11) NOT NULL,
  `idparecer` int(11) DEFAULT NULL,
  `iditemtrabalhoaprovacao` bigint(20) DEFAULT NULL,
  `idsolicitacaoservico` bigint(20) DEFAULT NULL,
  `iditemtrabalhoinspecao` bigint(20) DEFAULT NULL,
  `idcotacao` int(11) DEFAULT NULL,
  `quantidade` decimal(8,2) NOT NULL,
  `situacao` char(25) DEFAULT NULL COMMENT 'Aguardando - Aguardando aprovação\n            PreAprovado - Pré aprovado\n            Aprovado - Aprovado\n            NaoAprovado - Não aprovado',
  `quantidadeentregue` decimal(8,2) DEFAULT NULL,
  `iditemtrabalho` int(11) DEFAULT NULL,
  PRIMARY KEY (`idcoletapreco`,`iditemrequisicaoproduto`),
  KEY `fk_reference_688` (`idcotacao`),
  KEY `iditemrequisicaoproduto` (`iditemrequisicaoproduto`),
  KEY `fk_reference_694` (`idparecer`),
  KEY `idsolicitacaoservico` (`idsolicitacaoservico`),
  KEY `iditemtrabalhoinspecao` (`iditemtrabalhoinspecao`),
  KEY `fk_reference_699` (`iditemtrabalhoaprovacao`),
  CONSTRAINT `cotacaoitemrequisicao_ibfk_1` FOREIGN KEY (`iditemrequisicaoproduto`) REFERENCES `itemrequisicaoproduto` (`iditemrequisicaoproduto`),
  CONSTRAINT `cotacaoitemrequisicao_ibfk_2` FOREIGN KEY (`iditemtrabalhoaprovacao`) REFERENCES `bpm_itemtrabalhofluxo` (`iditemtrabalho`),
  CONSTRAINT `cotacaoitemrequisicao_ibfk_3` FOREIGN KEY (`idsolicitacaoservico`) REFERENCES `solicitacaoservico` (`idsolicitacaoservico`),
  CONSTRAINT `cotacaoitemrequisicao_ibfk_4` FOREIGN KEY (`iditemtrabalhoinspecao`) REFERENCES `bpm_itemtrabalhofluxo` (`iditemtrabalho`),
  CONSTRAINT `cotacaoitemrequisicao_ibfk_5` FOREIGN KEY (`iditemtrabalhoaprovacao`) REFERENCES `bpm_itemtrabalhofluxo` (`iditemtrabalho`),
  CONSTRAINT `fk_reference_687` FOREIGN KEY (`idcoletapreco`) REFERENCES `coletapreco` (`idcoletapreco`),
  CONSTRAINT `fk_reference_688` FOREIGN KEY (`idcotacao`) REFERENCES `cotacao` (`idcotacao`),
  CONSTRAINT `fk_reference_694` FOREIGN KEY (`idparecer`) REFERENCES `parecer` (`idparecer`),
  CONSTRAINT `fk_reference_699` FOREIGN KEY (`iditemtrabalhoaprovacao`) REFERENCES `bpm_itemtrabalhofluxo` (`iditemtrabalho`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cotacaoitemrequisicao`
--

LOCK TABLES `cotacaoitemrequisicao` WRITE;
/*!40000 ALTER TABLE `cotacaoitemrequisicao` DISABLE KEYS */;
/*!40000 ALTER TABLE `cotacaoitemrequisicao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `criterioavaliacao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `criterioavaliacao` (
  `idcriterio` int(11) NOT NULL,
  `descricao` varchar(100) NOT NULL,
  `aplicavelcotacao` char(1) NOT NULL,
  `aplicavelavaliacaosolicitante` char(1) NOT NULL,
  `aplicavelavaliacaocomprador` char(1) NOT NULL,
  `aplicavelqualificacaofornecedor` char(1) NOT NULL,
  `tipoavaliacao` char(1) NOT NULL COMMENT 'S - Sim/No\n            A - Aceito/Nao Aceito\n            C - Conforme/Nao Conforme\n            N - Numero - 0 a 10',
  PRIMARY KEY (`idcriterio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `criterioavaliacao`
--

LOCK TABLES `criterioavaliacao` WRITE;
/*!40000 ALTER TABLE `criterioavaliacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `criterioavaliacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `criterioavaliacaofornecedor`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `criterioavaliacaofornecedor` (
  `idavaliacaofornecedor` int(11) NOT NULL DEFAULT '0',
  `idcriterio` int(11) NOT NULL DEFAULT '0',
  `valor` int(11) DEFAULT NULL,
  `observacoes` text,
  PRIMARY KEY (`idavaliacaofornecedor`,`idcriterio`),
  KEY `fk_reference_680` (`idcriterio`),
  CONSTRAINT `fk_reference_679` FOREIGN KEY (`idavaliacaofornecedor`) REFERENCES `avaliacaofornecedor` (`idavaliacaofornecedor`),
  CONSTRAINT `fk_reference_680` FOREIGN KEY (`idcriterio`) REFERENCES `criterioavaliacao` (`idcriterio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `criterioavaliacaofornecedor`
--

LOCK TABLES `criterioavaliacaofornecedor` WRITE;
/*!40000 ALTER TABLE `criterioavaliacaofornecedor` DISABLE KEYS */;
/*!40000 ALTER TABLE `criterioavaliacaofornecedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `criteriocotacao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `criteriocotacao` (
  `idcotacao` int(11) NOT NULL,
  `idcriterio` int(11) NOT NULL,
  `peso` int(11) NOT NULL,
  PRIMARY KEY (`idcotacao`,`idcriterio`),
  KEY `fk_reference_33` (`idcriterio`),
  CONSTRAINT `criteriocotacao_ibfk_2` FOREIGN KEY (`idcriterio`) REFERENCES `criterioavaliacao` (`idcriterio`),
  CONSTRAINT `criteriocotacao_ibfk_1` FOREIGN KEY (`idcotacao`) REFERENCES `cotacao` (`idcotacao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `criteriocotacao`
--

LOCK TABLES `criteriocotacao` WRITE;
/*!40000 ALTER TABLE `criteriocotacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `criteriocotacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `criteriocotacaocategoria`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `criteriocotacaocategoria` (
  `idcategoria` int(11) NOT NULL,
  `idcriterio` int(11) NOT NULL,
  `pesocotacao` int(11) NOT NULL,
  PRIMARY KEY (`idcategoria`,`idcriterio`),
  KEY `idcriterio` (`idcriterio`),
  CONSTRAINT `criteriocotacaocategoria_ibfk_1` FOREIGN KEY (`idcategoria`) REFERENCES `categoriaproduto` (`idcategoria`),
  CONSTRAINT `criteriocotacaocategoria_ibfk_2` FOREIGN KEY (`idcriterio`) REFERENCES `criterioavaliacao` (`idcriterio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `criteriocotacaocategoria`
--

LOCK TABLES `criteriocotacaocategoria` WRITE;
/*!40000 ALTER TABLE `criteriocotacaocategoria` DISABLE KEYS */;
/*!40000 ALTER TABLE `criteriocotacaocategoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `criterioitemcotacao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `criterioitemcotacao` (
  `iditemcotacao` int(11) NOT NULL,
  `idcriterio` int(11) NOT NULL,
  `peso` int(11) NOT NULL,
  PRIMARY KEY (`idcriterio`,`iditemcotacao`),
  KEY `fk_reference_724` (`iditemcotacao`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `criterioitemcotacao`
--

LOCK TABLES `criterioitemcotacao` WRITE;
/*!40000 ALTER TABLE `criterioitemcotacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `criterioitemcotacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `demandas`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `demandas` (
  `iddemanda` int(11) NOT NULL,
  `idcontrato` int(11) DEFAULT NULL,
  `idsituacaodemanda` int(11) NOT NULL,
  `idtipodemanda` int(11) NOT NULL,
  `idprojeto` int(11) DEFAULT NULL,
  `iddemandapai` int(11) DEFAULT NULL,
  `idfluxo` int(11) NOT NULL,
  `previsaoinicio` date DEFAULT NULL,
  `datainicio` date DEFAULT NULL,
  `previsaofim` date DEFAULT NULL,
  `datafim` date DEFAULT NULL,
  `detalhamento` text,
  `prioridade` char(1) NOT NULL,
  `expectativafim` date DEFAULT NULL,
  `datacadastro` date DEFAULT NULL,
  `complexidade` int(11) DEFAULT NULL,
  `custototal` decimal(18,3) DEFAULT NULL,
  `observacao` text,
  `idos` int(11) DEFAULT NULL,
  `glosa` decimal(15,3) DEFAULT NULL,
  PRIMARY KEY (`iddemanda`),
  KEY `INDEX_DEMANDACONTRATO` (`idcontrato`),
  KEY `INDEX_DEMANDASITUACAODEMANDA` (`idsituacaodemanda`),
  KEY `INDEX_DEMANDATIPODEMANDA` (`idtipodemanda`),
  KEY `INDEX_DEMANDAPROJETO` (`idprojeto`),
  KEY `INDEX_DEMANDADEMANDAPAI` (`iddemandapai`),
  KEY `INDEX_DEMANDAFLUXO` (`idfluxo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='demandas';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `demandas`
--

LOCK TABLES `demandas` WRITE;
/*!40000 ALTER TABLE `demandas` DISABLE KEYS */;
/*!40000 ALTER TABLE `demandas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dicionario`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dicionario` (
  `iddicionario` int(11) NOT NULL,
  `nome` varchar(245) DEFAULT NULL,
  `valor` varchar(500) DEFAULT NULL,
  `idlingua` int(11) DEFAULT NULL,
  `personalizado` char(1) DEFAULT 'N',
  PRIMARY KEY (`iddicionario`),
  KEY `idlingua` (`idlingua`),
  KEY `index_dic_nome` (`nome`),
  KEY `index_dic_valor` (`valor`),
  KEY `index_dic_lingua` (`idlingua`),
  CONSTRAINT `idlingua` FOREIGN KEY (`idlingua`) REFERENCES `lingua` (`idlingua`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dicionario`
--

LOCK TABLES `dicionario` WRITE;
/*!40000 ALTER TABLE `dicionario` DISABLE KEYS */;
/*!40000 ALTER TABLE `dicionario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emailsolicitacaoservico`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `emailsolicitacaoservico` (
  `messageid` varchar(500) NOT NULL,
  `situacao` varchar(25) NOT NULL,
  `idemailsolicitacaoservico` int(11) NOT NULL,
  PRIMARY KEY (`idemailsolicitacaoservico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emailsolicitacaoservico`
--

LOCK TABLES `emailsolicitacaoservico` WRITE;
/*!40000 ALTER TABLE `emailsolicitacaoservico` DISABLE KEYS */;
/*!40000 ALTER TABLE `emailsolicitacaoservico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empregadoitemconfiguracao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `empregadoitemconfiguracao` (
  `idusuario` int(11) NOT NULL,
  `iditemconfiguracao` int(11) NOT NULL,
  `idempregado` int(11) DEFAULT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  PRIMARY KEY (`idusuario`,`iditemconfiguracao`),
  KEY `INDEX_EMPREGADOITEMCONFIGURACAO` (`iditemconfiguracao`),
  KEY `INDEX_EMPREGADO` (`idempregado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='empregadoitemconfiguracao';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empregadoitemconfiguracao`
--

LOCK TABLES `empregadoitemconfiguracao` WRITE;
/*!40000 ALTER TABLE `empregadoitemconfiguracao` DISABLE KEYS */;
/*!40000 ALTER TABLE `empregadoitemconfiguracao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empregados`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `empregados` (
  `idempregado` int(11) NOT NULL,
  `nome` varchar(80) NOT NULL,
  `nomeprocura` varchar(80) DEFAULT NULL,
  `datanascimento` date DEFAULT NULL,
  `sexo` char(1) DEFAULT NULL,
  `cpf` varchar(14) DEFAULT NULL,
  `rg` varchar(15) DEFAULT NULL,
  `dataemissaorg` date DEFAULT NULL,
  `idtiposangue` int(11) DEFAULT '0',
  `orgexpedidor` varchar(15) DEFAULT NULL,
  `iduforgexpedidor` int(11) DEFAULT NULL,
  `pai` varchar(50) DEFAULT NULL,
  `mae` varchar(50) DEFAULT NULL,
  `conjuge` varchar(50) DEFAULT NULL,
  `observacoes` text,
  `estadocivil` smallint(6) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `datacadastro` date DEFAULT NULL,
  `fumante` char(1) DEFAULT 'N',
  `ctpsnumero` varchar(15) DEFAULT NULL,
  `ctpsserie` varchar(10) DEFAULT NULL,
  `ctpsiduf` int(11) DEFAULT NULL,
  `ctpsdataemissao` date DEFAULT NULL,
  `nit` varchar(20) DEFAULT NULL,
  `dataadmissao` date DEFAULT NULL,
  `datademissao` date DEFAULT NULL,
  `tipo` char(1) DEFAULT NULL,
  `idsituacaofuncional` int(11) NOT NULL,
  `custoporhora` double DEFAULT NULL,
  `custototalmes` double DEFAULT NULL,
  `valorsalario` double DEFAULT NULL,
  `valorProdutividadeMedia` double DEFAULT NULL,
  `valorPlanoSaudeMedia` double DEFAULT NULL,
  `valorVTraMedia` double DEFAULT NULL,
  `valorVRefMedia` double DEFAULT NULL,
  `agencia` varchar(10) DEFAULT NULL,
  `contasalario` varchar(20) DEFAULT NULL,
  `idunidade` int(11) DEFAULT NULL,
  `datafim` date DEFAULT NULL,
  `telefone` varchar(45) DEFAULT NULL,
  `idcargo` int(11) DEFAULT NULL,
  `vinculaAContratos` char(1) DEFAULT NULL,
  `ramal` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`idempregado`),
  KEY `INDEX_EMPUNIDADE` (`idunidade`),
  KEY `fk_cargo_funcionario` (`idcargo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='empregados';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empregados`
--

LOCK TABLES `empregados` WRITE;
/*!40000 ALTER TABLE `empregados` DISABLE KEYS */;
INSERT INTO `empregados` VALUES (1,'Administrador','Administrador','2012-01-01','M','','',NULL,0,'',NULL,'','','','',NULL,'citsmart_instalador_mysql@centralit.com.br',NULL,NULL,'','',NULL,NULL,'','2012-01-01',NULL,'E',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'','',1,NULL,'3242-4433',1,NULL,NULL),(2,'Default','Default','2012-01-01','M','','',NULL,0,'',NULL,'','','','',NULL,'citsmart_instalador_mysql@centralit.com.br',NULL,NULL,'','',NULL,NULL,'','2012-01-01',NULL,'E',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'','',1,NULL,'3242-4433',2,NULL,NULL),(3,'Consultor','Consultor','2012-01-01','M','','',NULL,0,'',NULL,'','','','',NULL,'contact@citsmart.com',NULL,NULL,'','',NULL,NULL,'','2012-01-01',NULL,'E',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'','',1,NULL,'3242-4433',1,NULL,NULL);
/*!40000 ALTER TABLE `empregados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empresa`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `empresa` (
  `idempresa` int(11) NOT NULL,
  `nomeempresa` varchar(150) NOT NULL,
  `detalhamento` text,
  `datainicio` date DEFAULT '2012-04-02',
  `datafim` date DEFAULT NULL,
  PRIMARY KEY (`idempresa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='empresa';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empresa`
--

LOCK TABLES `empresa` WRITE;
/*!40000 ALTER TABLE `empresa` DISABLE KEYS */;
INSERT INTO `empresa` VALUES (1,'Default','','2013-03-18',NULL);
/*!40000 ALTER TABLE `empresa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `endereco`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `endereco` (
  `idendereco` int(11) NOT NULL,
  `logradouro` varchar(200) DEFAULT NULL,
  `numero` varchar(20) DEFAULT NULL,
  `complemento` varchar(200) DEFAULT NULL,
  `bairro` varchar(200) DEFAULT NULL,
  `idcidade` int(11) DEFAULT NULL,
  `idpais` int(11) DEFAULT NULL,
  `cep` varchar(8) DEFAULT NULL,
  `iduf` int(11) DEFAULT NULL,
  PRIMARY KEY (`idendereco`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `endereco`
--

LOCK TABLES `endereco` WRITE;
/*!40000 ALTER TABLE `endereco` DISABLE KEYS */;
INSERT INTO `endereco` VALUES (1,NULL,NULL,NULL,NULL,971,1,NULL,4);
/*!40000 ALTER TABLE `endereco` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entregaitemrequisicao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `entregaitemrequisicao` (
  `identrega` int(11) NOT NULL,
  `idpedido` int(11) NOT NULL,
  `idcoletapreco` int(11) NOT NULL,
  `iditemrequisicaoproduto` int(11) NOT NULL,
  `idsolicitacaoservico` bigint(20) DEFAULT NULL,
  `quantidadeentregue` decimal(8,2) NOT NULL,
  `iditemtrabalho` bigint(20) DEFAULT NULL,
  `idparecer` int(11) DEFAULT NULL,
  `situacao` varchar(25) NOT NULL,
  `observacoes` text,
  PRIMARY KEY (`identrega`),
  KEY `fk_reference_701` (`idpedido`),
  KEY `fk_reference_702` (`idcoletapreco`,`iditemrequisicaoproduto`),
  KEY `fk_reference_711` (`idsolicitacaoservico`),
  KEY `fk_reference_712` (`iditemtrabalho`),
  KEY `fk_reference_719` (`idparecer`),
  CONSTRAINT `fk_reference_701` FOREIGN KEY (`idpedido`) REFERENCES `pedidocompra` (`idpedido`),
  CONSTRAINT `fk_reference_702` FOREIGN KEY (`idcoletapreco`, `iditemrequisicaoproduto`) REFERENCES `cotacaoitemrequisicao` (`idcoletapreco`, `iditemrequisicaoproduto`),
  CONSTRAINT `fk_reference_711` FOREIGN KEY (`idsolicitacaoservico`) REFERENCES `solicitacaoservico` (`idsolicitacaoservico`),
  CONSTRAINT `fk_reference_712` FOREIGN KEY (`iditemtrabalho`) REFERENCES `bpm_itemtrabalhofluxo` (`iditemtrabalho`),
  CONSTRAINT `fk_reference_719` FOREIGN KEY (`idparecer`) REFERENCES `parecer` (`idparecer`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entregaitemrequisicao`
--

LOCK TABLES `entregaitemrequisicao` WRITE;
/*!40000 ALTER TABLE `entregaitemrequisicao` DISABLE KEYS */;
/*!40000 ALTER TABLE `entregaitemrequisicao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `etapas`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `etapas` (
  `idetapa` int(11) NOT NULL,
  `idfluxo` int(11) NOT NULL,
  `idetapapai` int(11) DEFAULT NULL,
  `nomeetapa` varchar(70) NOT NULL,
  `ordem` smallint(6) NOT NULL,
  PRIMARY KEY (`idetapa`),
  KEY `INDEX_ETAPAFLUXO` (`idfluxo`),
  KEY `INDEX_ETAPASETAPAPAI` (`idetapapai`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='etapas';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `etapas`
--

LOCK TABLES `etapas` WRITE;
/*!40000 ALTER TABLE `etapas` DISABLE KEYS */;
/*!40000 ALTER TABLE `etapas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evento`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evento` (
  `idevento` int(11) NOT NULL,
  `idempresa` int(11) NOT NULL,
  `descricao` varchar(255) NOT NULL,
  `ligarcasodesl` char(1) NOT NULL,
  `datainicio` date DEFAULT NULL,
  `datafim` date DEFAULT NULL,
  `usuario` varchar(256) DEFAULT NULL,
  `senha` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`idevento`),
  KEY `INDEX_EVENTOEMPRESA` (`idempresa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='evento';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evento`
--

LOCK TABLES `evento` WRITE;
/*!40000 ALTER TABLE `evento` DISABLE KEYS */;
/*!40000 ALTER TABLE `evento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eventoempregado`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `eventoempregado` (
  `idevento` int(11) NOT NULL,
  `idempregado` int(11) NOT NULL,
  `idgrupo` int(11) DEFAULT NULL,
  `idunidade` int(11) DEFAULT NULL,
  `iditemconfiguracaopai` int(11) DEFAULT NULL,
  PRIMARY KEY (`idevento`,`idempregado`),
  KEY `INDEX_EVENTOEMPREGADO` (`idempregado`),
  KEY `INDEX_EVENTOGRUPO` (`idgrupo`),
  KEY `INDEX_EVENTOUNIDADE` (`idunidade`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='eventoempregado';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eventoempregado`
--

LOCK TABLES `eventoempregado` WRITE;
/*!40000 ALTER TABLE `eventoempregado` DISABLE KEYS */;
/*!40000 ALTER TABLE `eventoempregado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eventogrupo`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `eventogrupo` (
  `idevento` int(11) DEFAULT NULL,
  `idgrupo` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='eventogrupo';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eventogrupo`
--

LOCK TABLES `eventogrupo` WRITE;
/*!40000 ALTER TABLE `eventogrupo` DISABLE KEYS */;
/*!40000 ALTER TABLE `eventogrupo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eventoitemconfiguracao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `eventoitemconfiguracao` (
  `idevento` int(11) NOT NULL DEFAULT '0',
  `iditemconfiguracao` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idevento`,`iditemconfiguracao`),
  KEY `iditemconfiguracao` (`iditemconfiguracao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eventoitemconfiguracao`
--

LOCK TABLES `eventoitemconfiguracao` WRITE;
/*!40000 ALTER TABLE `eventoitemconfiguracao` DISABLE KEYS */;
/*!40000 ALTER TABLE `eventoitemconfiguracao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eventomonitconhecimento`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `eventomonitconhecimento` (
  `ideventomonitoramento` int(11) NOT NULL,
  `idbaseconhecimento` int(11) NOT NULL,
  PRIMARY KEY (`ideventomonitoramento`,`idbaseconhecimento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eventomonitconhecimento`
--

LOCK TABLES `eventomonitconhecimento` WRITE;
/*!40000 ALTER TABLE `eventomonitconhecimento` DISABLE KEYS */;
/*!40000 ALTER TABLE `eventomonitconhecimento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eventomonitoramento`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `eventomonitoramento` (
  `ideventomonitoramento` int(11) NOT NULL,
  `nomeevento` varchar(255) NOT NULL,
  `detalhamento` text,
  `criadopor` varchar(255) DEFAULT NULL,
  `modificadopor` varchar(255) DEFAULT NULL,
  `datacriacao` date DEFAULT NULL,
  `ultmodificacao` date DEFAULT NULL,
  PRIMARY KEY (`ideventomonitoramento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eventomonitoramento`
--

LOCK TABLES `eventomonitoramento` WRITE;
/*!40000 ALTER TABLE `eventomonitoramento` DISABLE KEYS */;
/*!40000 ALTER TABLE `eventomonitoramento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eventounidade`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `eventounidade` (
  `idunidade` int(11) DEFAULT NULL,
  `idevento` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='eventounidade';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eventounidade`
--

LOCK TABLES `eventounidade` WRITE;
/*!40000 ALTER TABLE `eventounidade` DISABLE KEYS */;
/*!40000 ALTER TABLE `eventounidade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `excecaocalendario`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `excecaocalendario` (
  `idexcecaocalendario` int(11) NOT NULL,
  `idcalendario` int(11) NOT NULL,
  `idjornada` int(11) DEFAULT NULL,
  `tipo` char(1) NOT NULL COMMENT 'F=Folga             T=Trabalho',
  `datainicio` date NOT NULL,
  `datatermino` date NOT NULL,
  PRIMARY KEY (`idexcecaocalendario`),
  KEY `fk_reference_540` (`idcalendario`),
  KEY `fk_reference_541` (`idjornada`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `excecaocalendario`
--

LOCK TABLES `excecaocalendario` WRITE;
/*!40000 ALTER TABLE `excecaocalendario` DISABLE KEYS */;
/*!40000 ALTER TABLE `excecaocalendario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `excecaoempregado`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `excecaoempregado` (
  `idevento` int(11) NOT NULL,
  `idempregado` int(11) NOT NULL,
  `idgrupo` int(11) DEFAULT NULL,
  `idunidade` int(11) DEFAULT NULL,
  PRIMARY KEY (`idevento`,`idempregado`),
  KEY `INDEX_EXCECAOEMPREGADO` (`idempregado`),
  KEY `INDEX_EXCECAOGRUPO` (`idgrupo`),
  KEY `INDEX_EXCECAOUNIDADE` (`idunidade`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='excecaoempregado';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `excecaoempregado`
--

LOCK TABLES `excecaoempregado` WRITE;
/*!40000 ALTER TABLE `excecaoempregado` DISABLE KEYS */;
/*!40000 ALTER TABLE `excecaoempregado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `execucaoatividadeperiodica`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `execucaoatividadeperiodica` (
  `idexecucaoatividadeperiodica` int(11) NOT NULL,
  `idatividadeperiodica` int(11) NOT NULL,
  `dataprogramada` date NOT NULL,
  `horaprogramada` char(5) DEFAULT NULL,
  `situacao` char(1) NOT NULL,
  `detalhamento` text,
  `usuario` char(1) DEFAULT NULL,
  `idempregado` int(11) DEFAULT NULL,
  `dataexecucao` date DEFAULT NULL,
  `horaexecucao` char(5) DEFAULT NULL,
  `dataregistro` date DEFAULT NULL,
  `horaregistro` char(5) DEFAULT NULL,
  `idprogramacaoatividade` int(11) DEFAULT NULL,
  `idmotivosuspensao` int(11) DEFAULT NULL,
  `complementomotivosuspensao` text,
  PRIMARY KEY (`idexecucaoatividadeperiodica`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `execucaoatividadeperiodica`
--

LOCK TABLES `execucaoatividadeperiodica` WRITE;
/*!40000 ALTER TABLE `execucaoatividadeperiodica` DISABLE KEYS */;
/*!40000 ALTER TABLE `execucaoatividadeperiodica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `execucaobatch`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `execucaobatch` (
  `idexecucaobatch` int(11) NOT NULL,
  `idprocessamentobatch` int(11) NOT NULL,
  `conteudo` text NOT NULL,
  `datahora` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idexecucaobatch`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `execucaobatch`
--

LOCK TABLES `execucaobatch` WRITE;
/*!40000 ALTER TABLE `execucaobatch` DISABLE KEYS */;
/*!40000 ALTER TABLE `execucaobatch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `execucaodemanda`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `execucaodemanda` (
  `idexecucao` int(11) NOT NULL,
  `iddemanda` int(11) NOT NULL,
  `idatividade` int(11) NOT NULL,
  `idempregadoexecutor` int(11) DEFAULT NULL,
  `idempregadoreceptor` int(11) DEFAULT NULL,
  `relato` text,
  `qtdehoras` decimal(18,3) DEFAULT NULL,
  `situacao` char(1) NOT NULL,
  `grupoexecutor` varchar(20) DEFAULT NULL,
  `terminoprevisto` date DEFAULT NULL,
  `terminoreal` date DEFAULT NULL,
  PRIMARY KEY (`idexecucao`),
  KEY `INDEX_EXECUCAOATIVIDADE` (`idatividade`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='execucaodemanda';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `execucaodemanda`
--

LOCK TABLES `execucaodemanda` WRITE;
/*!40000 ALTER TABLE `execucaodemanda` DISABLE KEYS */;
/*!40000 ALTER TABLE `execucaodemanda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `execucaomudanca`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `execucaomudanca` (
  `idexecucao` int(11) NOT NULL,
  `idinstanciafluxo` bigint(20) NOT NULL,
  `idrequisicaomudanca` int(11) NOT NULL,
  `idfluxo` bigint(20) NOT NULL,
  `seqreabertura` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`idexecucao`),
  KEY `fk_execucao_reference_bpm_inst` (`idinstanciafluxo`),
  KEY `fk_execucao_reference_requisic` (`idrequisicaomudanca`),
  KEY `fk_execucao_reference_bpm_flux` (`idfluxo`),
  CONSTRAINT `fk_execucao_reference_bpm_flux` FOREIGN KEY (`idfluxo`) REFERENCES `bpm_fluxo` (`idfluxo`),
  CONSTRAINT `fk_execucao_reference_bpm_inst` FOREIGN KEY (`idinstanciafluxo`) REFERENCES `bpm_instanciafluxo` (`idinstancia`),
  CONSTRAINT `fk_execucao_reference_requisic` FOREIGN KEY (`idrequisicaomudanca`) REFERENCES `requisicaomudanca` (`idrequisicaomudanca`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `execucaomudanca`
--

LOCK TABLES `execucaomudanca` WRITE;
/*!40000 ALTER TABLE `execucaomudanca` DISABLE KEYS */;
/*!40000 ALTER TABLE `execucaomudanca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `execucaosolicitacao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `execucaosolicitacao` (
  `idexecucao` bigint(20) NOT NULL,
  `idsolicitacaoservico` bigint(20) DEFAULT NULL,
  `idfase` bigint(20) DEFAULT NULL,
  `idinstanciafluxo` bigint(20) DEFAULT NULL,
  `idfluxo` bigint(20) DEFAULT NULL,
  `prazohh` smallint(6) DEFAULT NULL,
  `prazomm` smallint(6) DEFAULT NULL,
  `seqreabertura` int(11) DEFAULT NULL,
  PRIMARY KEY (`idexecucao`),
  KEY `fk_reference_116` (`idsolicitacaoservico`),
  KEY `fk_reference_118` (`idfase`),
  KEY `fk_reference_128` (`idfluxo`),
  KEY `fk_reference_130` (`idinstanciafluxo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `execucaosolicitacao`
--

LOCK TABLES `execucaosolicitacao` WRITE;
/*!40000 ALTER TABLE `execucaosolicitacao` DISABLE KEYS */;
INSERT INTO `execucaosolicitacao` VALUES (1,1,2,1,3,0,0,NULL),(2,2,2,2,3,0,0,NULL),(3,3,2,3,3,0,0,NULL),(4,4,2,4,3,0,0,NULL);
/*!40000 ALTER TABLE `execucaosolicitacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `externalconnection`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `externalconnection` (
  `idexternalconnection` int(11) NOT NULL,
  `nome` varchar(80) NOT NULL,
  `tipo` char(1) NOT NULL,
  `urljdbc` varchar(255) DEFAULT NULL,
  `jdbcdbname` varchar(255) DEFAULT NULL,
  `jdbcdriver` varchar(255) DEFAULT NULL,
  `jdbcuser` varchar(255) DEFAULT NULL,
  `jdbcpassword` varchar(255) DEFAULT NULL,
  `filename` varchar(500) DEFAULT NULL,
  `schemadb` varchar(255) DEFAULT NULL,
  `deleted` char(1) DEFAULT NULL,
  PRIMARY KEY (`idexternalconnection`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `externalconnection`
--

LOCK TABLES `externalconnection` WRITE;
/*!40000 ALTER TABLE `externalconnection` DISABLE KEYS */;
/*!40000 ALTER TABLE `externalconnection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faixavaloresrecurso`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `faixavaloresrecurso` (
  `idfaixavaloresrecurso` int(11) NOT NULL,
  `idrecurso` int(11) NOT NULL,
  `valorinicio` decimal(15,3) DEFAULT NULL,
  `valorfim` decimal(15,3) DEFAULT NULL,
  `cor` varchar(20) DEFAULT NULL,
  `descricao` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`idfaixavaloresrecurso`),
  KEY `fk_reference_604` (`idrecurso`),
  CONSTRAINT `faixavaloresrecurso_ibfk_1` FOREIGN KEY (`idrecurso`) REFERENCES `recurso` (`idrecurso`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faixavaloresrecurso`
--

LOCK TABLES `faixavaloresrecurso` WRITE;
/*!40000 ALTER TABLE `faixavaloresrecurso` DISABLE KEYS */;
/*!40000 ALTER TABLE `faixavaloresrecurso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faseservico`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `faseservico` (
  `idfase` bigint(20) NOT NULL,
  `nomefase` varchar(70) NOT NULL,
  `fasecaptura` char(1) NOT NULL,
  PRIMARY KEY (`idfase`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faseservico`
--

LOCK TABLES `faseservico` WRITE;
/*!40000 ALTER TABLE `faseservico` DISABLE KEYS */;
INSERT INTO `faseservico` VALUES (1,'Captura','S'),(2,'Execução','N');
/*!40000 ALTER TABLE `faseservico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fatura`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fatura` (
  `idfatura` int(11) NOT NULL,
  `idcontrato` int(11) NOT NULL,
  `datainicial` date NOT NULL,
  `datafinal` date NOT NULL,
  `descricaofatura` varchar(150) DEFAULT NULL,
  `valorcotacaomoeda` decimal(15,3) NOT NULL,
  `datacriacao` date NOT NULL,
  `dataultmodificacao` date NOT NULL,
  `valorprevistosomaos` decimal(15,3) DEFAULT NULL,
  `valorsomaglosasos` decimal(15,3) DEFAULT NULL,
  `valorexecutadosomaos` decimal(15,3) DEFAULT NULL,
  `observacao` text,
  `aprovacaogestor` text,
  `aprovacaofiscal` text,
  `saldoprevisto` decimal(15,3) DEFAULT NULL,
  `situacaofatura` char(1) NOT NULL,
  PRIMARY KEY (`idfatura`),
  KEY `fk_reference_505` (`idcontrato`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fatura`
--

LOCK TABLES `fatura` WRITE;
/*!40000 ALTER TABLE `fatura` DISABLE KEYS */;
/*!40000 ALTER TABLE `fatura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faturaapuracaoans`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `faturaapuracaoans` (
  `idfaturaapuracaoans` int(11) NOT NULL,
  `idfatura` int(11) NOT NULL,
  `idacordonivelservicocontrato` int(11) NOT NULL,
  `valorapurado` decimal(15,3) DEFAULT NULL,
  `detalhamento` text,
  `percentualglosa` decimal(15,3) DEFAULT NULL,
  `valorglosa` decimal(15,3) DEFAULT NULL,
  `dataapuracao` date DEFAULT NULL,
  PRIMARY KEY (`idfaturaapuracaoans`),
  KEY `fk_reference_510` (`idfatura`),
  KEY `fk_reference_511` (`idacordonivelservicocontrato`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faturaapuracaoans`
--

LOCK TABLES `faturaapuracaoans` WRITE;
/*!40000 ALTER TABLE `faturaapuracaoans` DISABLE KEYS */;
/*!40000 ALTER TABLE `faturaapuracaoans` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faturaos`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `faturaos` (
  `idfatura` int(11) NOT NULL,
  `idos` int(11) NOT NULL,
  PRIMARY KEY (`idfatura`,`idos`),
  KEY `fk_reference_508` (`idos`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faturaos`
--

LOCK TABLES `faturaos` WRITE;
/*!40000 ALTER TABLE `faturaos` DISABLE KEYS */;
/*!40000 ALTER TABLE `faturaos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feriado`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `feriado` (
  `idferiado` int(11) NOT NULL,
  `data` date NOT NULL,
  `descricao` varchar(100) DEFAULT NULL,
  `abrangencia` char(1) DEFAULT NULL,
  `iduf` int(11) DEFAULT NULL,
  `idcidade` int(11) DEFAULT NULL,
  `recorrente` char(1) DEFAULT NULL,
  PRIMARY KEY (`idferiado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='feriado';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feriado`
--

LOCK TABLES `feriado` WRITE;
/*!40000 ALTER TABLE `feriado` DISABLE KEYS */;
/*!40000 ALTER TABLE `feriado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fluxo`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fluxo` (
  `idfluxo` int(11) NOT NULL,
  `nomefluxo` varchar(70) NOT NULL,
  `descricao` text,
  PRIMARY KEY (`idfluxo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fluxo`
--

LOCK TABLES `fluxo` WRITE;
/*!40000 ALTER TABLE `fluxo` DISABLE KEYS */;
/*!40000 ALTER TABLE `fluxo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fluxos`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fluxos` (
  `idfluxo` int(11) NOT NULL,
  `nomefluxo` varchar(70) NOT NULL,
  PRIMARY KEY (`idfluxo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='fluxos';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fluxos`
--

LOCK TABLES `fluxos` WRITE;
/*!40000 ALTER TABLE `fluxos` DISABLE KEYS */;
/*!40000 ALTER TABLE `fluxos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fluxoservico`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fluxoservico` (
  `idservicocontrato` bigint(20) NOT NULL,
  `idtipofluxo` int(11) NOT NULL,
  `idfase` bigint(20) NOT NULL,
  `principal` char(1) NOT NULL DEFAULT 'N',
  `deleted` char(1) DEFAULT NULL,
  PRIMARY KEY (`idservicocontrato`,`idtipofluxo`,`idfase`),
  KEY `fk_reference_117` (`idfase`),
  KEY `fk_reference_93` (`idtipofluxo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fluxoservico`
--

LOCK TABLES `fluxoservico` WRITE;
/*!40000 ALTER TABLE `fluxoservico` DISABLE KEYS */;
/*!40000 ALTER TABLE `fluxoservico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `formula`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `formula` (
  `idformula` int(11) NOT NULL,
  `identificador` varchar(120) NOT NULL,
  `nome` varchar(120) NOT NULL,
  `conteudo` text,
  `datacriacao` date DEFAULT NULL,
  PRIMARY KEY (`idformula`),
  UNIQUE KEY `ix_identificador` (`identificador`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `formula`
--

LOCK TABLES `formula` WRITE;
/*!40000 ALTER TABLE `formula` DISABLE KEYS */;
INSERT INTO `formula` VALUES (1,'Calculo de ANS Padrão','Calculo de ANS Padrão','if (faturaDTO.getFieldANS() == \"valorGlosa\"){   var percGlosa = (FormulasUtil.getDoubleValueFromGrid(request,   faturaDTO.getSeqANS(),  \"valorGlosa\") / faturaDTO.getValorExecutadoSomaOS()) * 100;    FormulasUtil.setDoubleValueFromGrid(document,  faturaDTO.getSeqANS(),  \"percentualGlosa\", percGlosa );}else{   var valorGlosa = faturaDTO.getValorExecutadoSomaOS() * (FormulasUtil.getDoubleValueFromGrid(request,   faturaDTO.getSeqANS(),  \"percentualGlosa\") / 100);   FormulasUtil.setDoubleValueFromGrid(document,  faturaDTO.getSeqANS(),  \"valorGlosa\", valorGlosa);}',NULL),(2,'CalculoGLOSAOS','Calculo Glosa OS','if (osDTO.getFieldANS() == \"custoGlosa\"){   var percGlosa = (FormulasUtil.getDoubleValueFromGrid(request,   osDTO.getSeqANS(),  \"custoGlosa\") / osDTO.getExecutadoOS()) * 100;    FormulasUtil.setDoubleValueFromGrid(document,  osDTO.getSeqANS(),  \"percAplicado\", percGlosa ); }else{    var valorGlosa = osDTO.getExecutadoOS() * (FormulasUtil.getDoubleValueFromGrid(request,   osDTO.getSeqANS(),  \"percAplicado\") / 100);    FormulasUtil.setDoubleValueFromGrid(document,  osDTO.getSeqANS(),  \"custoGlosa\", valorGlosa);}',NULL);
/*!40000 ALTER TABLE `formula` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fornecedor`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fornecedor` (
  `idfornecedor` bigint(20) NOT NULL,
  `razaosocial` varchar(100) NOT NULL,
  `nomefantasia` varchar(70) DEFAULT NULL,
  `cnpj` varchar(14) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `observacao` text,
  `deleted` char(1) DEFAULT NULL,
  `telefone` varchar(20) DEFAULT NULL,
  `fax` varchar(20) DEFAULT NULL,
  `nomeContato` varchar(100) DEFAULT NULL,
  `inscricaoEstadual` varchar(25) DEFAULT NULL,
  `inscricaoMunicipal` varchar(25) DEFAULT NULL,
  `idendereco` int(11) DEFAULT NULL,
  `tipopessoa` char(1) DEFAULT NULL,
  PRIMARY KEY (`idfornecedor`),
  KEY `fk_forn_end` (`idendereco`),
  CONSTRAINT `fk_forn_end` FOREIGN KEY (`idendereco`) REFERENCES `endereco` (`idendereco`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fornecedor`
--

LOCK TABLES `fornecedor` WRITE;
/*!40000 ALTER TABLE `fornecedor` DISABLE KEYS */;
INSERT INTO `fornecedor` VALUES (1,'Fornecedor 1','Seu Fornecedor',NULL,'','',NULL,NULL,NULL,'','','',1,'');
/*!40000 ALTER TABLE `fornecedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fornecedorcotacao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fornecedorcotacao` (
  `idcotacao` int(11) NOT NULL,
  `idfornecedor` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idcotacao`,`idfornecedor`),
  KEY `fk_reference_685` (`idfornecedor`),
  CONSTRAINT `fk_reference_684` FOREIGN KEY (`idcotacao`) REFERENCES `cotacao` (`idcotacao`),
  CONSTRAINT `fk_reference_685` FOREIGN KEY (`idfornecedor`) REFERENCES `fornecedor` (`idfornecedor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fornecedorcotacao`
--

LOCK TABLES `fornecedorcotacao` WRITE;
/*!40000 ALTER TABLE `fornecedorcotacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `fornecedorcotacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fornecedorproduto`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fornecedorproduto` (
  `idfornecedorproduto` int(11) NOT NULL,
  `idfornecedor` bigint(20) DEFAULT NULL,
  `idtipoproduto` int(11) NOT NULL,
  `idmarca` int(11) DEFAULT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  PRIMARY KEY (`idfornecedorproduto`),
  KEY `fk_reference_660` (`idfornecedor`),
  KEY `idtipoproduto` (`idtipoproduto`),
  KEY `fk_reference_675` (`idmarca`),
  CONSTRAINT `fk_reference_660` FOREIGN KEY (`idfornecedor`) REFERENCES `fornecedor` (`idfornecedor`),
  CONSTRAINT `fk_reference_675` FOREIGN KEY (`idmarca`) REFERENCES `marca` (`idmarca`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fornecedorproduto`
--

LOCK TABLES `fornecedorproduto` WRITE;
/*!40000 ALTER TABLE `fornecedorproduto` DISABLE KEYS */;
/*!40000 ALTER TABLE `fornecedorproduto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `galeriaimagens`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `galeriaimagens` (
  `idimagem` int(11) NOT NULL,
  `idcategoriagaleriaimagem` int(11) NOT NULL,
  `nomeimagem` varchar(255) NOT NULL,
  `descricaoimagem` varchar(70) DEFAULT NULL,
  `detalhamento` text,
  `extensao` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`idimagem`),
  KEY `INDEX_GALERIACATEGORIAGALERIAIMAGEM` (`idcategoriagaleriaimagem`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='galeriaimagens';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `galeriaimagens`
--

LOCK TABLES `galeriaimagens` WRITE;
/*!40000 ALTER TABLE `galeriaimagens` DISABLE KEYS */;
/*!40000 ALTER TABLE `galeriaimagens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `glosaos`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `glosaos` (
  `idglosaos` int(11) NOT NULL,
  `idos` int(11) NOT NULL,
  `datacriacao` date NOT NULL,
  `dataultmodificacao` date NOT NULL,
  `descricaoglosa` text NOT NULL,
  `ocorrencias` text,
  `percaplicado` decimal(15,3) NOT NULL,
  `custoglosa` decimal(15,3) NOT NULL,
  `numeroOcorrencias` decimal(15,3) DEFAULT NULL,
  `idacordonivelservico` int(11) DEFAULT NULL,
  PRIMARY KEY (`idglosaos`),
  KEY `fk_reference_504` (`idos`),
  KEY `fk_reference_618` (`idacordonivelservico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `glosaos`
--

LOCK TABLES `glosaos` WRITE;
/*!40000 ALTER TABLE `glosaos` DISABLE KEYS */;
/*!40000 ALTER TABLE `glosaos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `glosaservicocontrato`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `glosaservicocontrato` (
  `idglosaservicocontrato` bigint(20) NOT NULL,
  `idservicocontrato` bigint(20) NOT NULL,
  `quantidadeglosa` int(11) DEFAULT NULL,
  `datafim` date DEFAULT NULL,
  PRIMARY KEY (`idglosaservicocontrato`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `glosaservicocontrato`
--

LOCK TABLES `glosaservicocontrato` WRITE;
/*!40000 ALTER TABLE `glosaservicocontrato` DISABLE KEYS */;
/*!40000 ALTER TABLE `glosaservicocontrato` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grupo`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grupo` (
  `idgrupo` int(11) NOT NULL,
  `idempresa` int(11) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  `descricao` text,
  `servicedesk` char(1) DEFAULT NULL,
  `sigla` varchar(20) DEFAULT NULL,
  `abertura` varchar(45) DEFAULT NULL,
  `encerramento` varchar(45) DEFAULT NULL,
  `andamento` varchar(45) DEFAULT NULL,
  `comiteconsultivomudanca` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`idgrupo`),
  KEY `INDEX_GRUPOEMPRESA` (`idempresa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='grupo';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grupo`
--

LOCK TABLES `grupo` WRITE;
/*!40000 ALTER TABLE `grupo` DISABLE KEYS */;
INSERT INTO `grupo` VALUES (1,1,'Desenvolvimento','2012-01-01',NULL,'Esse grupo possui perfil de acesso Supervisão.','N','DES',NULL,NULL,NULL,''),(2,1,'1º NÍVEL','2012-01-01',NULL,'Atendimento de 1º Nível.','S','SDNIVEL1',NULL,NULL,NULL,''),(3,1,'2º NÍVEL','2012-01-01',NULL,'Atendimento de 2º Nível.','S','SDNIVEL2',NULL,NULL,NULL,''),(4,1,'3º NÍVEL - Infraestrutura','2012-01-01',NULL,'Atendimento de 3º Nível - Infraestrutura','S','SDNIVEL3Infra',NULL,NULL,NULL,''),(5,1,'Qualidade','2012-01-01',NULL,'Alterado por valdoílo para teste.','S','QUALIDADE',NULL,NULL,NULL,''),(6,1,'Gestores Internos','2012-01-01',NULL,'Gestores Internos','S','GESTORES',NULL,NULL,NULL,''),(7,1,'Coordenadores Externos','2012-01-01',NULL,'Coordenadores Externos','N','COORD_EXT',NULL,NULL,NULL,''),(16,1,'3º NÍVEL - Aplicação','2012-01-01',NULL,'3º NÍVEL - Aplicação','S','SDNIVEL3Apli',NULL,NULL,NULL,''),(17,1,'3º NÍVEL - Sistemas','2012-01-01',NULL,'3º NÍVEL - Sistemas','S','SDNIVEL3Sist',NULL,NULL,NULL,''),(18,1,'Coordenador','2012-01-01',NULL,'Coordenador de Infraestrutura','S','COORDENAÇÃO',NULL,NULL,NULL,''),(19,1,'3º NÍVEL - Telefonia','2012-01-01',NULL,'3º NÍVEL - Telefonia','N','SDNIVEL3Telefonia',NULL,NULL,NULL,''),(20,1,'Gerentes','2012-01-01',NULL,'','S','GER',NULL,NULL,NULL,'');
/*!40000 ALTER TABLE `grupo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grupoatvperiodica`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grupoatvperiodica` (
  `idgrupoatvperiodica` int(11) NOT NULL,
  `nomegrupoatvperiodica` varchar(256) NOT NULL,
  `descgrupoatvperiodica` text,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  `deleted` char(1) DEFAULT NULL,
  PRIMARY KEY (`idgrupoatvperiodica`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='grupoatvperiodica';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grupoatvperiodica`
--

LOCK TABLES `grupoatvperiodica` WRITE;
/*!40000 ALTER TABLE `grupoatvperiodica` DISABLE KEYS */;
/*!40000 ALTER TABLE `grupoatvperiodica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grupoitemconfiguracao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grupoitemconfiguracao` (
  `idgrupoitemconfiguracao` int(11) NOT NULL,
  `nomegrupoitemconfiguracao` varchar(100) NOT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `emailgrupoitemconfiguracao` varchar(256) DEFAULT NULL,
  `idGrupoItemConfiguracaoPai` int(11) DEFAULT NULL,
  PRIMARY KEY (`idgrupoitemconfiguracao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grupoitemconfiguracao`
--

LOCK TABLES `grupoitemconfiguracao` WRITE;
/*!40000 ALTER TABLE `grupoitemconfiguracao` DISABLE KEYS */;
/*!40000 ALTER TABLE `grupoitemconfiguracao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grupoquestionario`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grupoquestionario` (
  `idgrupoquestionario` int(11) NOT NULL,
  `idquestionario` int(11) NOT NULL,
  `nomegrupoquestionario` varchar(80) NOT NULL,
  `ordem` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`idgrupoquestionario`),
  KEY `INDEX_GRUPOQUESTQUESTIONARIO` (`idquestionario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='grupoquestionario';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grupoquestionario`
--

LOCK TABLES `grupoquestionario` WRITE;
/*!40000 ALTER TABLE `grupoquestionario` DISABLE KEYS */;
/*!40000 ALTER TABLE `grupoquestionario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gruporecursos`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gruporecursos` (
  `idgruporecurso` int(11) NOT NULL,
  `nomegruporecurso` varchar(70) NOT NULL,
  `situacao` char(1) NOT NULL,
  `deleted` char(1) DEFAULT NULL,
  PRIMARY KEY (`idgruporecurso`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gruporecursos`
--

LOCK TABLES `gruporecursos` WRITE;
/*!40000 ALTER TABLE `gruporecursos` DISABLE KEYS */;
/*!40000 ALTER TABLE `gruporecursos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gruposemails`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gruposemails` (
  `idgrupo` int(11) NOT NULL,
  `idempregado` int(11) DEFAULT NULL,
  `nome` varchar(80) DEFAULT NULL,
  `email` varchar(200) NOT NULL,
  PRIMARY KEY (`idgrupo`,`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gruposemails`
--

LOCK TABLES `gruposemails` WRITE;
/*!40000 ALTER TABLE `gruposemails` DISABLE KEYS */;
/*!40000 ALTER TABLE `gruposemails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gruposempregados`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gruposempregados` (
  `idgrupo` int(11) NOT NULL,
  `idempregado` int(11) NOT NULL,
  `enviaemail` char(1) DEFAULT NULL,
  PRIMARY KEY (`idgrupo`,`idempregado`),
  KEY `INDEX_GRUPOSEMPREGADO` (`idempregado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='gruposempregados';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gruposempregados`
--

LOCK TABLES `gruposempregados` WRITE;
/*!40000 ALTER TABLE `gruposempregados` DISABLE KEYS */;
INSERT INTO `gruposempregados` VALUES (2,1,'N'),(2,2,'N'),(2,3,'N'),(3,1,'N'),(3,3,'N'),(4,1,'N'),(4,3,'N'),(16,3,'N'),(17,1,'N'),(17,3,'N'),(18,1,'N'),(19,1,'N'),(19,3,'N');
/*!40000 ALTER TABLE `gruposempregados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grupovisao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grupovisao` (
  `idgrupovisao` bigint(20) NOT NULL,
  `idvisao` bigint(20) NOT NULL,
  `descricaogrupovisao` varchar(500) NOT NULL,
  `forma` char(1) NOT NULL,
  `ordem` int(11) NOT NULL,
  `situacao` char(1) NOT NULL,
  PRIMARY KEY (`idgrupovisao`),
  KEY `fk_reference_75` (`idvisao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grupovisao`
--

LOCK TABLES `grupovisao` WRITE;
/*!40000 ALTER TABLE `grupovisao` DISABLE KEYS */;
INSERT INTO `grupovisao` VALUES (3,7,'Visão de Empresa','1',0,'A'),(7,8,'Listagem de Empregados','1',0,'A'),(8,9,'Endereços','1',0,'A'),(12,10,'Grupos de Segurança do Empregado','1',0,'A'),(15,5,'Visão Empregados','1',0,'A'),(19,13,'Listagem de Fluxos','1',0,'A'),(114,39,'Pesquisa Causa de Incidentes','1',0,'A'),(120,45,'$grupo.recursos.lista','1',0,'A'),(121,46,'$nagios.conexao.listagem','1',0,'A'),(122,47,'$requisitosla.pesquisasla','1',0,'A'),(123,48,'$visao.listagemAcordoNivelServico','1',0,'A'),(124,22,'$visao.listagemClientes','1',0,'A'),(125,49,'$visao.listagemConexoesExternas','1',0,'A'),(126,25,'$visao.listagemContratos','1',0,'A'),(127,50,'$visao.listagemEmpregados','1',0,'A'),(128,51,'$visao.listagemFluxos','1',0,'A'),(129,52,'$visao.listagemFluxoTrabalho','1',0,'A'),(130,24,'$visao.listagemFornecedores','1',0,'A'),(131,35,'$visao.listagemLocalExecucaoServico','1',0,'A'),(132,18,'$visao.listagemServicosCriacao','1',0,'A'),(133,31,'$visao.listagemTipoEventoServico','1',0,'A'),(134,53,'$visao.pesquisaCategoriaSolucao','1',0,'A'),(135,41,'$visao.pesquisaCausaIncidentes','1',0,'A'),(136,43,'$visao.pesquisaGrupoAtividadePeriodica','1',0,'A'),(137,37,'$visao.pesquisaJustificativaSolicitacao','1',0,'A'),(138,33,'$visao.pesquisaTipoSolicitacaoServico','1',0,'A'),(139,54,'$contrato.historicoauditoria','1',0,'A'),(140,55,'$grupo.recursos','1',0,'A'),(141,32,'$menu.nome.tipoSolicitacaoServico','1',0,'A'),(142,56,'$nagios.conexao.titulo','1',0,'A'),(143,57,'$requisitosla.sla','1',0,'A'),(144,58,'$sla.historicoauditoria','1',0,'A'),(145,59,'$sla.listacontratosvinculadosano','1',0,'A'),(146,60,'$sla.listacontratosvinculadoscliente','1',0,'A'),(147,61,'$sla.listacontratosvinculadosterceiro','1',0,'A'),(148,62,'$slarequisitosla.titulo','1',0,'A'),(149,63,'$sla.revisar.revisar','1',0,'A'),(150,28,'$visao.acordoNivelServicoContrato','1',0,'A'),(151,14,'$visao.cadastroFluxos','1',0,'A'),(152,64,'$visao.complexidades','1',0,'A'),(153,29,'$visao.fluxoServico','1',0,'A'),(154,65,'$visao.historicoAuditoriaServico','1',0,'A'),(155,66,'$visao.vincularSlaServico','1',0,'A'),(156,67,'$visao.acordoGeral','1',0,'A'),(157,26,'$visao.acordoNivelServico','1',0,'A'),(158,15,'$visao.analiseIncidente','1',0,'A'),(159,16,'$visao.atendimento','1',0,'A'),(160,27,'$visao.atividadesServico','1',0,'A'),(161,30,'$visao.CadastroTipoEventoServico','1',0,'A'),(162,40,'$visao.categoriaSolucao','1',0,'A'),(163,38,'$visao.causaIncidentes','1',0,'A'),(164,21,'$visao.clientes','1',0,'A'),(165,68,'$visao.conexoesExternas','1',0,'A'),(166,20,'$visao.contratos','1',0,'A'),(167,17,'$visao.criacaoServicos','1',0,'A'),(168,69,'$visao.enderecos','1',0,'A'),(169,23,'$visao.fornecedores','1',0,'A'),(170,42,'$visao.grupoAtividadePeriodica','1',0,'A'),(171,70,'$visao.gruposSegurancaEmpregados','1',0,'A'),(172,12,'$visao.incidente','1',0,'A'),(173,36,'$visao.justificativaSolicitacao','1',0,'A'),(174,34,'$visao.localExecucaoServico','1',0,'A'),(175,19,'$visao.servicoContrato','1',0,'A'),(176,44,'$visao.servicoContratoMulti','1',0,'A'),(177,71,'$visao.visaoEmpregados','1',0,'A'),(178,72,'$visao.visaoEmpresa','1',0,'A');
/*!40000 ALTER TABLE `grupovisao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grupovisaocamposnegocio`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grupovisaocamposnegocio` (
  `idgrupovisao` bigint(20) NOT NULL,
  `idcamposobjetonegocio` bigint(20) NOT NULL,
  `descricaonegocio` varchar(500) NOT NULL,
  `tiponegocio` varchar(20) NOT NULL,
  `ordem` int(11) NOT NULL,
  `situacao` char(1) NOT NULL,
  `obrigatorio` char(1) NOT NULL,
  `tamanho` smallint(6) NOT NULL,
  `decimais` smallint(6) NOT NULL,
  `tipoligacao` char(1) DEFAULT NULL,
  `textosql` text,
  `tamanhoparapesq` smallint(6) DEFAULT NULL,
  `formula` text,
  `visivel` char(1) NOT NULL,
  `htmlcode` text,
  PRIMARY KEY (`idgrupovisao`,`idcamposobjetonegocio`),
  KEY `fk_reference_77` (`idcamposobjetonegocio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grupovisaocamposnegocio`
--

LOCK TABLES `grupovisaocamposnegocio` WRITE;
/*!40000 ALTER TABLE `grupovisaocamposnegocio` DISABLE KEYS */;
/*!40000 ALTER TABLE `grupovisaocamposnegocio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grupovisaocamposnegocioinfosql`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grupovisaocamposnegocioinfosql` (
  `idgrupovisaocamposnegocioinfosql` bigint(20) NOT NULL,
  `idgrupovisao` bigint(20) NOT NULL,
  `idcamposobjetonegocio` bigint(20) NOT NULL,
  `campo` varchar(150) NOT NULL,
  `tipoligacao` char(1) NOT NULL,
  `filtro` text,
  `descricao` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`idgrupovisaocamposnegocioinfosql`),
  KEY `fk_reference_86` (`idgrupovisao`,`idcamposobjetonegocio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grupovisaocamposnegocioinfosql`
--

LOCK TABLES `grupovisaocamposnegocioinfosql` WRITE;
/*!40000 ALTER TABLE `grupovisaocamposnegocioinfosql` DISABLE KEYS */;
/*!40000 ALTER TABLE `grupovisaocamposnegocioinfosql` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grupovisaocamposnegocioligacao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grupovisaocamposnegocioligacao` (
  `idgrupovisaocamposnegocioligaca` bigint(20) NOT NULL,
  `idgrupovisao` bigint(20) NOT NULL,
  `idcamposobjetonegocio` bigint(20) NOT NULL,
  `idcamposobjetonegocioligacao` bigint(20) DEFAULT NULL,
  `tipoligacao` char(1) NOT NULL,
  `filtro` text,
  `descricao` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`idgrupovisaocamposnegocioligaca`),
  KEY `fk_reference_84` (`idgrupovisao`,`idcamposobjetonegocio`),
  KEY `fk_reference_85` (`idcamposobjetonegocioligacao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grupovisaocamposnegocioligacao`
--

LOCK TABLES `grupovisaocamposnegocioligacao` WRITE;
/*!40000 ALTER TABLE `grupovisaocamposnegocioligacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `grupovisaocamposnegocioligacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hilosequences`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hilosequences` (
  `SEQUENCENAME` varchar(50) NOT NULL,
  `HIGHVALUES` int(11) NOT NULL,
  PRIMARY KEY (`SEQUENCENAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hilosequences`
--

LOCK TABLES `hilosequences` WRITE;
/*!40000 ALTER TABLE `hilosequences` DISABLE KEYS */;
/*!40000 ALTER TABLE `hilosequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historicobaseconhecimento`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `historicobaseconhecimento` (
  `idhistoricobaseconhecimento` int(11) NOT NULL,
  `idbaseconhecimento` int(11) NOT NULL,
  `idpasta` int(11) DEFAULT NULL,
  `datainicio` date DEFAULT NULL,
  `datafim` date DEFAULT NULL,
  `titulo` varchar(256) DEFAULT NULL,
  `conteudo` text,
  `status` char(1) DEFAULT NULL,
  `idbaseconhecimentopai` int(11) DEFAULT NULL,
  `dataexpiracao` date DEFAULT NULL,
  `versao` varchar(45) DEFAULT NULL,
  `idusuarioautor` int(11) DEFAULT NULL,
  `idusuarioaprovador` int(11) DEFAULT NULL,
  `fontereferencia` varchar(255) DEFAULT NULL,
  `idnotificacao` int(11) DEFAULT NULL,
  `datapublicacao` date DEFAULT NULL,
  `justificativaobservacao` varchar(500) DEFAULT NULL,
  `faq` varchar(45) DEFAULT NULL,
  `origem` char(1) DEFAULT NULL,
  `arquivado` varchar(45) DEFAULT NULL,
  `idusuarioalteracao` int(11) DEFAULT NULL,
  `datahoraalteracao` timestamp NULL DEFAULT NULL,
  `conteudosemformatacao` text,
  PRIMARY KEY (`idhistoricobaseconhecimento`,`idbaseconhecimento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historicobaseconhecimento`
--

LOCK TABLES `historicobaseconhecimento` WRITE;
/*!40000 ALTER TABLE `historicobaseconhecimento` DISABLE KEYS */;
INSERT INTO `historicobaseconhecimento` VALUES (1,1,1,'2013-04-08',NULL,'Conhecimento 1','Descri&ccedil;&atilde;o Conhecimento 1<br />','S',NULL,'2014-12-31','1.0',1,1,'Conhecimento 1',NULL,'2013-04-08','Justificativa Conhecimento 1',NULL,'1','N',1,'2013-04-08 13:12:20',NULL),(2,2,2,'2013-04-08',NULL,'Conhecimento 2','Descri&ccedil;&atilde;o Conhecimento 2<br />','S',NULL,'2014-12-31','1.0',1,1,'',NULL,'2013-04-08','Justificativa Conhecimento 2',NULL,'1','N',1,'2013-04-08 13:15:22',NULL),(3,3,1,'2013-04-08',NULL,'FAQ 1','Resposta FAQ 1<br />','S',NULL,'2014-12-31','1.0',1,1,'FAQ 1',NULL,'2013-04-08','Justificativa FAQ 1','S','1','N',1,'2013-04-08 13:16:54',NULL),(4,4,2,'2013-04-08',NULL,'FAQ 2','Resposta FAQ 2','S',NULL,'2014-12-31','1.0',1,1,'FAQ 2',NULL,'2013-04-08','Justificativa FAQ 2','S','1','N',1,'2013-04-08 13:19:35',NULL);
/*!40000 ALTER TABLE `historicobaseconhecimento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historicoexecucao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `historicoexecucao` (
  `idhistorico` int(11) NOT NULL,
  `idexecucao` int(11) NOT NULL,
  `data` date NOT NULL,
  `situacao` char(1) NOT NULL,
  `idempregadoexecutor` int(11) NOT NULL,
  `detalhamento` text,
  `hora` int(11) DEFAULT NULL,
  PRIMARY KEY (`idhistorico`),
  KEY `fk_historic_reference_execucao` (`idexecucao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='historicoexecucao';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historicoexecucao`
--

LOCK TABLES `historicoexecucao` WRITE;
/*!40000 ALTER TABLE `historicoexecucao` DISABLE KEYS */;
/*!40000 ALTER TABLE `historicoexecucao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historicoic`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `historicoic` (
  `idhistoricoic` int(11) NOT NULL,
  `iditemconfiguracao` int(11) NOT NULL,
  `identificacao` varchar(400) NOT NULL,
  `iditemconfiguracaopai` int(11) DEFAULT NULL,
  `idtipoitemconfiguracao` int(11) DEFAULT NULL,
  `idgrupoitemconfiguracao` int(11) DEFAULT NULL,
  `idproprietario` int(11) DEFAULT NULL,
  `versao` varchar(250) DEFAULT NULL,
  `familia` varchar(250) DEFAULT NULL,
  `idfamiliaitemconfiguracao` int(11) DEFAULT NULL,
  `classe` varchar(250) DEFAULT NULL,
  `idclasseitemconfiguracao` int(11) DEFAULT NULL,
  `localidade` varchar(250) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `criticidade` int(11) DEFAULT NULL,
  `numeroSerie` varchar(45) DEFAULT NULL,
  `dataExpiracao` date DEFAULT NULL,
  `idMudanca` int(11) DEFAULT NULL,
  `idProblema` int(11) DEFAULT NULL,
  `IdIncidente` int(11) DEFAULT NULL,
  `idautoralteracao` int(11) NOT NULL,
  `datahoraalteracao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `baseline` varchar(30) DEFAULT NULL,
  `restauracao` int(11) DEFAULT NULL,
  `idMidiaSoftware` int(11) DEFAULT NULL,
  `impacto` varchar(255) DEFAULT NULL,
  `urgencia` varchar(255) DEFAULT NULL,
  `historicoVersao` double(11,1) DEFAULT NULL,
  PRIMARY KEY (`idhistoricoic`),
  KEY `fk_itemconfiguracao` (`iditemconfiguracao`),
  KEY `fk_autoralteracao` (`idautoralteracao`),
  KEY `fk_familiaitemconfiguracao` (`idfamiliaitemconfiguracao`),
  KEY `fk_classeitemconfiguracao` (`idclasseitemconfiguracao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historicoic`
--

LOCK TABLES `historicoic` WRITE;
/*!40000 ALTER TABLE `historicoic` DISABLE KEYS */;
/*!40000 ALTER TABLE `historicoic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historicosituacaocotacao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `historicosituacaocotacao` (
  `idhistorico` int(11) NOT NULL,
  `idcotacao` int(11) NOT NULL,
  `idresponsavel` int(11) NOT NULL,
  `datahora` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `situacao` varchar(25) NOT NULL,
  PRIMARY KEY (`idhistorico`),
  KEY `idcotacao` (`idcotacao`),
  KEY `idresponsavel` (`idresponsavel`),
  CONSTRAINT `historicosituacaocotacao_ibfk_1` FOREIGN KEY (`idcotacao`) REFERENCES `cotacao` (`idcotacao`),
  CONSTRAINT `historicosituacaocotacao_ibfk_2` FOREIGN KEY (`idresponsavel`) REFERENCES `empregados` (`idempregado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historicosituacaocotacao`
--

LOCK TABLES `historicosituacaocotacao` WRITE;
/*!40000 ALTER TABLE `historicosituacaocotacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `historicosituacaocotacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historicotentativa`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `historicotentativa` (
  `idhistoricotentativa` int(11) NOT NULL,
  `iditemconfiguracao` int(11) DEFAULT NULL,
  `idbaseitemconfiguracao` int(11) DEFAULT NULL,
  `idevento` int(11) DEFAULT NULL,
  `idempregado` int(11) DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `data` date DEFAULT NULL,
  `hora` char(4) DEFAULT NULL,
  PRIMARY KEY (`idhistoricotentativa`),
  KEY `ITEMCONFIGURACAO` (`idhistoricotentativa`,`iditemconfiguracao`),
  KEY `BASEITEMCONFIGURACAO` (`idhistoricotentativa`,`idbaseitemconfiguracao`),
  KEY `EVENTO` (`idhistoricotentativa`,`idevento`),
  KEY `EMPREGADO` (`idhistoricotentativa`,`idempregado`),
  KEY `FK_Reference_130` (`iditemconfiguracao`),
  KEY `FK_Reference_131` (`idbaseitemconfiguracao`),
  KEY `FK_Reference_133` (`idevento`),
  KEY `FK_Reference_136` (`idempregado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='historicotentativa';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historicotentativa`
--

LOCK TABLES `historicotentativa` WRITE;
/*!40000 ALTER TABLE `historicotentativa` DISABLE KEYS */;
/*!40000 ALTER TABLE `historicotentativa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historicovalor`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `historicovalor` (
  `idhistoricovalor` int(11) NOT NULL,
  `idvalor` int(11) NOT NULL,
  `iditemconfiguracao` int(11) DEFAULT NULL,
  `idcaracteristica` int(11) DEFAULT NULL,
  `valorstr` varchar(4000) DEFAULT NULL,
  `valorlongo` text,
  `valordecimal` decimal(18,4) DEFAULT NULL,
  `valordate` date DEFAULT NULL,
  `idbaseitemconfiguracao` int(11) DEFAULT NULL,
  `datahoraalteracao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `idautoralteracao` int(11) NOT NULL,
  `baseline` varchar(30) DEFAULT NULL,
  `idHistoricoIC` int(11) DEFAULT NULL,
  PRIMARY KEY (`idhistoricovalor`),
  KEY `fk_valor` (`idvalor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historicovalor`
--

LOCK TABLES `historicovalor` WRITE;
/*!40000 ALTER TABLE `historicovalor` DISABLE KEYS */;
/*!40000 ALTER TABLE `historicovalor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `htmlcodevisao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `htmlcodevisao` (
  `idhtmlcodevisao` bigint(20) NOT NULL,
  `idvisao` bigint(20) NOT NULL,
  `htmlcodetype` char(30) NOT NULL,
  `htmlcode` text NOT NULL,
  PRIMARY KEY (`idhtmlcodevisao`),
  KEY `fk_reference_121` (`idvisao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `htmlcodevisao`
--

LOCK TABLES `htmlcodevisao` WRITE;
/*!40000 ALTER TABLE `htmlcodevisao` DISABLE KEYS */;
/*!40000 ALTER TABLE `htmlcodevisao` ENABLE KEYS */;
UNLOCK TABLES;

CREATE  TABLE `instalacao` (   
	`idinstalacao` INT NOT NULL ,   
	`sucesso` CHAR(1) NULL ,   
	`passo` VARCHAR(255) NULL ,   
	PRIMARY KEY (`idinstalacao`) 
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `imagemhistorico`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `imagemhistorico` (
  `idimagem` int(11) NOT NULL,
  `data` date NOT NULL,
  `nomearquivo` varchar(255) NOT NULL,
  `observacao` text,
  `idcontrato` int(11) NOT NULL,
  `idprofissional` int(11) DEFAULT NULL,
  `idempresa` int(11) DEFAULT NULL,
  `aba` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`idimagem`),
  KEY `ix_imagemhistorico` (`idcontrato`,`data`),
  KEY `ix_imagemhiistorico_data` (`data`,`idcontrato`,`idprofissional`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `imagemhistorico`
--

LOCK TABLES `imagemhistorico` WRITE;
/*!40000 ALTER TABLE `imagemhistorico` DISABLE KEYS */;
/*!40000 ALTER TABLE `imagemhistorico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `imagemitemconfiguracao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `imagemitemconfiguracao` (
  `idimagemitemconfiguracao` int(11) NOT NULL,
  `idservico` int(11) DEFAULT NULL,
  `iditemconfiguracao` int(11) DEFAULT NULL,
  `posx` int(11) DEFAULT NULL,
  `posy` int(11) DEFAULT NULL,
  `descricao` varchar(256) DEFAULT NULL,
  `caminhoimagem` varchar(256) DEFAULT NULL,
  `idimagemitemconfiguracaopai` int(11) DEFAULT NULL,
  PRIMARY KEY (`idimagemitemconfiguracao`),
  KEY `INDEX_IMAGEMITEMSERVICO` (`idservico`),
  KEY `INDEX_IMAGEMITEMCONFIGURACAO` (`iditemconfiguracao`),
  KEY `INDEX_IMAGEMITEMCONFIGURACAOPAI` (`idimagemitemconfiguracaopai`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='imagemitemconfiguracao';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `imagemitemconfiguracao`
--

LOCK TABLES `imagemitemconfiguracao` WRITE;
/*!40000 ALTER TABLE `imagemitemconfiguracao` DISABLE KEYS */;
/*!40000 ALTER TABLE `imagemitemconfiguracao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `imagemservicorelacionado`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `imagemservicorelacionado` (
  `idimagemservicorelacionado` int(11) NOT NULL,
  `idservico` int(11) DEFAULT NULL,
  `idservicorelacionado` int(11) DEFAULT NULL,
  `posx` int(11) DEFAULT NULL,
  `posy` int(11) DEFAULT NULL,
  `descricao` varchar(256) DEFAULT NULL,
  `caminhoimagem` varchar(256) DEFAULT NULL,
  `idimagemservicorelacionadopai` int(11) DEFAULT NULL,
  PRIMARY KEY (`idimagemservicorelacionado`),
  KEY `INDEX_IMAGEMITEMSERVICO` (`idservico`),
  KEY `INDEX_IMAGEMSERVICORELACIONADO` (`idservicorelacionado`),
  KEY `INDEX_IMAGEMSERVICORELACIONADOPAI` (`idimagemservicorelacionadopai`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `imagemservicorelacionado`
--

LOCK TABLES `imagemservicorelacionado` WRITE;
/*!40000 ALTER TABLE `imagemservicorelacionado` DISABLE KEYS */;
/*!40000 ALTER TABLE `imagemservicorelacionado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `impacto`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `impacto` (
  `idImpacto` int(11) NOT NULL,
  `nivelImpacto` varchar(100) NOT NULL,
  `siglaImpacto` char(2) DEFAULT NULL,
  PRIMARY KEY (`idImpacto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `impacto`
--

LOCK TABLES `impacto` WRITE;
/*!40000 ALTER TABLE `impacto` DISABLE KEYS */;
INSERT INTO `impacto` VALUES (1,'Altíssimo','AL'),(2,'Alto','A'),(3,'Elevado','E'),(4,'Médio','M'),(5,'Baixo','B');
/*!40000 ALTER TABLE `impacto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `importanciaconhecimentogrupo`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `importanciaconhecimentogrupo` (
  `idbaseconhecimento` int(11) NOT NULL,
  `idgrupo` int(11) NOT NULL,
  `grauimportanciagrupo` varchar(45) NOT NULL,
  PRIMARY KEY (`idbaseconhecimento`,`idgrupo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `importanciaconhecimentogrupo`
--

LOCK TABLES `importanciaconhecimentogrupo` WRITE;
/*!40000 ALTER TABLE `importanciaconhecimentogrupo` DISABLE KEYS */;
/*!40000 ALTER TABLE `importanciaconhecimentogrupo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `importanciaconhecimentousuario`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `importanciaconhecimentousuario` (
  `idbaseconhecimento` int(11) NOT NULL,
  `idusuario` int(11) NOT NULL,
  `grauimportanciausuario` varchar(45) NOT NULL,
  PRIMARY KEY (`idbaseconhecimento`,`idusuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `importanciaconhecimentousuario`
--

LOCK TABLES `importanciaconhecimentousuario` WRITE;
/*!40000 ALTER TABLE `importanciaconhecimentousuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `importanciaconhecimentousuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `importancianegocio`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `importancianegocio` (
  `idimportancianegocio` int(11) NOT NULL,
  `idempresa` int(11) NOT NULL,
  `nomeimportancianegocio` varchar(100) NOT NULL,
  `situacao` char(1) NOT NULL,
  PRIMARY KEY (`idimportancianegocio`),
  KEY `INDEX_IMPORTANCIAEMPRESA` (`idempresa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='importancianegocio';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `importancianegocio`
--

LOCK TABLES `importancianegocio` WRITE;
/*!40000 ALTER TABLE `importancianegocio` DISABLE KEYS */;
INSERT INTO `importancianegocio` VALUES (1,1,'Baixa','A'),(3,1,'Média','A'),(4,1,'Alta','A'),(10,1,'A combinar','A'),(11,1,'Normal','A');
/*!40000 ALTER TABLE `importancianegocio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `importconfig`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `importconfig` (
  `idimportconfig` int(11) NOT NULL,
  `tipo` char(1) NOT NULL,
  `idexternalconnection` int(11) DEFAULT NULL,
  `tabelaorigem` varchar(255) DEFAULT NULL,
  `tabeladestino` varchar(255) DEFAULT NULL,
  `filtroorigem` text,
  `nome` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idimportconfig`),
  KEY `fk_ref_impconx` (`idexternalconnection`),
  CONSTRAINT `fk_ref_impconx` FOREIGN KEY (`idexternalconnection`) REFERENCES `externalconnection` (`idexternalconnection`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `importconfig`
--

LOCK TABLES `importconfig` WRITE;
/*!40000 ALTER TABLE `importconfig` DISABLE KEYS */;
/*!40000 ALTER TABLE `importconfig` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `importconfigcampos`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `importconfigcampos` (
  `idimportconfigcampo` int(11) NOT NULL,
  `idimportconfig` int(11) NOT NULL,
  `origem` varchar(255) DEFAULT NULL,
  `destino` varchar(255) DEFAULT NULL,
  `script` text,
  PRIMARY KEY (`idimportconfigcampo`),
  KEY `fk_ref_impcp_imp` (`idimportconfig`),
  CONSTRAINT `fk_ref_impcp_imp` FOREIGN KEY (`idimportconfig`) REFERENCES `importconfig` (`idimportconfig`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `importconfigcampos`
--

LOCK TABLES `importconfigcampos` WRITE;
/*!40000 ALTER TABLE `importconfigcampos` DISABLE KEYS */;
/*!40000 ALTER TABLE `importconfigcampos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `infocatalogoservico`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `infocatalogoservico` (
  `idinfocatalogoservico` int(11) NOT NULL,
  `idcatalogoservico` int(11) DEFAULT NULL,
  `descinfocatalogoservico` text,
  `nomeinfocatalogoservico` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`idinfocatalogoservico`),
  KEY `fk_infocata_reference_catalago` (`idcatalogoservico`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `infocatalogoservico`
--

LOCK TABLES `infocatalogoservico` WRITE;
/*!40000 ALTER TABLE `infocatalogoservico` DISABLE KEYS */;
/*!40000 ALTER TABLE `infocatalogoservico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `informacaoservico`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `informacaoservico` (
  `idInformacaoServico` bigint(20) NOT NULL AUTO_INCREMENT,
  `idServico` bigint(20) NOT NULL,
  `usuario` varchar(255) DEFAULT NULL,
  `titulo` varchar(255) DEFAULT NULL,
  `texto` varchar(255) DEFAULT NULL,
  `situacao` varchar(255) DEFAULT NULL,
  `deleted` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idInformacaoServico`),
  KEY `FK3107D1C6B3CD587F` (`idServico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `informacaoservico`
--

LOCK TABLES `informacaoservico` WRITE;
/*!40000 ALTER TABLE `informacaoservico` DISABLE KEYS */;
/*!40000 ALTER TABLE `informacaoservico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `informacoescontratoconfig`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `informacoescontratoconfig` (
  `idinformacoescontratoconfig` int(11) NOT NULL,
  `idinformacoescontratoconfigpai` int(11) DEFAULT NULL,
  `nome` varchar(50) NOT NULL,
  `descricao` varchar(70) NOT NULL,
  `funcionalidadepath` varchar(255) DEFAULT NULL,
  `funcitem` char(1) NOT NULL,
  `idquestionario` int(11) DEFAULT NULL,
  `idempresa` int(11) NOT NULL,
  `situacao` char(1) NOT NULL,
  `ordem` smallint(6) DEFAULT NULL,
  `infoadicional` text,
  `funcadicionalaposgravacao` varchar(255) DEFAULT NULL,
  `chamarfuncaddaposgravar` char(1) DEFAULT NULL,
  `chamarfuncaddhistorico` char(1) DEFAULT NULL,
  `iconefunchistorico` varchar(255) DEFAULT NULL,
  `iconefunchistoricofinal` varchar(255) DEFAULT NULL,
  `validacoes` text,
  `segurancaunidade` char(1) DEFAULT NULL,
  `segurancaunidadepcmso` char(1) DEFAULT NULL,
  `segurancaunidadeenferm` char(1) DEFAULT NULL,
  PRIMARY KEY (`idinformacoescontratoconfig`),
  KEY `INDEX_INFORMACOESCONTRATOCONFIGPAI` (`idinformacoescontratoconfigpai`),
  KEY `INDEX_INFORMACOESQUESTIONARIO` (`idquestionario`),
  KEY `INDEX_INFORMACOESEMPRESA` (`idempresa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='informacoescontratoconfig';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `informacoescontratoconfig`
--

LOCK TABLES `informacoescontratoconfig` WRITE;
/*!40000 ALTER TABLE `informacoescontratoconfig` DISABLE KEYS */;
/*!40000 ALTER TABLE `informacoescontratoconfig` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `informacoescontratoperfseg`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `informacoescontratoperfseg` (
  `idinformacoescontratoconfig` int(11) NOT NULL,
  `idperfilseguranca` int(11) NOT NULL,
  PRIMARY KEY (`idinformacoescontratoconfig`,`idperfilseguranca`),
  KEY `INDEX_INFORMACOESPERFILSEGURANCA` (`idperfilseguranca`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='informacoescontratoperfseg';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `informacoescontratoperfseg`
--

LOCK TABLES `informacoescontratoperfseg` WRITE;
/*!40000 ALTER TABLE `informacoescontratoperfseg` DISABLE KEYS */;
/*!40000 ALTER TABLE `informacoescontratoperfseg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inspecaoentregaitem`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inspecaoentregaitem` (
  `identrega` int(11) NOT NULL,
  `idcriterio` int(11) NOT NULL,
  `idresponsavel` int(11) DEFAULT NULL,
  `datahorainspecao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `avaliacao` varchar(25) DEFAULT NULL,
  `observacoes` longtext,
  PRIMARY KEY (`identrega`,`idcriterio`),
  KEY `fk_reference_713` (`idresponsavel`),
  KEY `fk_reference_714` (`idcriterio`),
  CONSTRAINT `fk_reference_713` FOREIGN KEY (`idresponsavel`) REFERENCES `empregados` (`idempregado`),
  CONSTRAINT `fk_reference_714` FOREIGN KEY (`idcriterio`) REFERENCES `criterioavaliacao` (`idcriterio`),
  CONSTRAINT `fk_reference_715` FOREIGN KEY (`identrega`) REFERENCES `entregaitemrequisicao` (`identrega`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inspecaoentregaitem`
--

LOCK TABLES `inspecaoentregaitem` WRITE;
/*!40000 ALTER TABLE `inspecaoentregaitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `inspecaoentregaitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inspecaopedidocompra`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inspecaopedidocompra` (
  `idpedido` int(11) NOT NULL,
  `idcriterio` int(11) NOT NULL,
  `idresponsavel` int(11) NOT NULL,
  `datahorainspecao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `avaliacao` varchar(25) DEFAULT NULL,
  `observacoes` longtext,
  PRIMARY KEY (`idpedido`,`idcriterio`),
  KEY `fk_reference_717` (`idcriterio`),
  KEY `fk_reference_718` (`idresponsavel`),
  CONSTRAINT `fk_reference_716` FOREIGN KEY (`idpedido`) REFERENCES `pedidocompra` (`idpedido`),
  CONSTRAINT `fk_reference_717` FOREIGN KEY (`idcriterio`) REFERENCES `criterioavaliacao` (`idcriterio`),
  CONSTRAINT `fk_reference_718` FOREIGN KEY (`idresponsavel`) REFERENCES `empregados` (`idempregado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inspecaopedidocompra`
--

LOCK TABLES `inspecaopedidocompra` WRITE;
/*!40000 ALTER TABLE `inspecaopedidocompra` DISABLE KEYS */;
/*!40000 ALTER TABLE `inspecaopedidocompra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventarioxml`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inventarioxml` (
  `idinventarioxml` int(11) NOT NULL,
  `conteudo` mediumtext,
  `idnetmap` int(11) DEFAULT NULL,
  `datainicial` date DEFAULT NULL,
  `datafinal` date DEFAULT NULL,
  `nome` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`idinventarioxml`),
  KEY `INDEX_INVENTARIONETMAP` (`idnetmap`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='inventarioxml';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventarioxml`
--

LOCK TABLES `inventarioxml` WRITE;
/*!40000 ALTER TABLE `inventarioxml` DISABLE KEYS */;
/*!40000 ALTER TABLE `inventarioxml` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itemcfgsolicitacaoserv`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `itemcfgsolicitacaoserv` (
  `iditemconfiguracao` int(11) DEFAULT NULL,
  `idsolicitacaoservico` bigint(20) DEFAULT NULL,
  `datainicio` date DEFAULT NULL,
  `datafim` date DEFAULT NULL,
  KEY `fk_itemcfgs_reference_itemconf` (`iditemconfiguracao`),
  KEY `fk_itemcfgs_reference_solicita` (`idsolicitacaoservico`),
  CONSTRAINT `fk_itemcfgs_reference_itemconf` FOREIGN KEY (`iditemconfiguracao`) REFERENCES `itemconfiguracao` (`iditemconfiguracao`),
  CONSTRAINT `fk_itemcfgs_reference_solicita` FOREIGN KEY (`idsolicitacaoservico`) REFERENCES `solicitacaoservico` (`idsolicitacaoservico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itemcfgsolicitacaoserv`
--

LOCK TABLES `itemcfgsolicitacaoserv` WRITE;
/*!40000 ALTER TABLE `itemcfgsolicitacaoserv` DISABLE KEYS */;
/*!40000 ALTER TABLE `itemcfgsolicitacaoserv` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itemconfiguracao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `itemconfiguracao` (
  `iditemconfiguracao` int(11) NOT NULL,
  `identificacao` varchar(400) NOT NULL,
  `iditemconfiguracaopai` int(11) DEFAULT NULL,
  `idtipoitemconfiguracao` int(11) DEFAULT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  `idgrupoitemconfiguracao` int(11) DEFAULT NULL,
  `idproprietario` int(11) DEFAULT NULL,
  `dataexpiracao` date DEFAULT NULL,
  `versao` varchar(50) DEFAULT NULL,
  `familia` varchar(250) DEFAULT NULL,
  `classe` varchar(250) DEFAULT NULL,
  `localidade` varchar(250) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `criticidade` int(11) DEFAULT NULL,
  `numeroSerie` varchar(45) DEFAULT NULL,
  `idMudanca` int(11) DEFAULT NULL,
  `idProblema` int(11) DEFAULT NULL,
  `IdIncidente` int(11) DEFAULT NULL,
  `idMidiaSoftware` int(11) DEFAULT NULL,
  `impacto` varchar(255) DEFAULT NULL,
  `urgencia` varchar(255) DEFAULT NULL,
  `idbaseconhecimento` int(11) DEFAULT NULL,
  PRIMARY KEY (`iditemconfiguracao`),
  KEY `INDEX_ICPAI` (`identificacao`(255),`iditemconfiguracaopai`),
  KEY `INDEX_ITEMCONF` (`iditemconfiguracao`,`iditemconfiguracaopai`,`idtipoitemconfiguracao`,`datafim`),
  KEY `fk_itemconf_associati_itemconf` (`iditemconfiguracaopai`),
  KEY `fk_itemconf_reference_tipoitem` (`idtipoitemconfiguracao`),
  KEY `idgrupoitemconfiguracao` (`idgrupoitemconfiguracao`),
  KEY `fk_idproprietario` (`idproprietario`),
  KEY `fk_idbaseconhecimento_itemconfiguracao` (`idbaseconhecimento`),
  CONSTRAINT `fk_idbaseconhecimento_itemconfiguracao` FOREIGN KEY (`idbaseconhecimento`) REFERENCES `baseconhecimento` (`idbaseconhecimento`),
  CONSTRAINT `fk_idproprietario` FOREIGN KEY (`idproprietario`) REFERENCES `empregados` (`idempregado`),
  CONSTRAINT `itemconfiguracao_ibfk_2` FOREIGN KEY (`idgrupoitemconfiguracao`) REFERENCES `grupoitemconfiguracao` (`idgrupoitemconfiguracao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='itemconfiguracao';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itemconfiguracao`
--

LOCK TABLES `itemconfiguracao` WRITE;
/*!40000 ALTER TABLE `itemconfiguracao` DISABLE KEYS */;
/*!40000 ALTER TABLE `itemconfiguracao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itemconfiguracaoevento`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `itemconfiguracaoevento` (
  `iditemconfiguracaoevento` int(11) NOT NULL,
  `idbaseitemconfiguracao` int(11) DEFAULT NULL,
  `iditemconfiguracao` int(11) DEFAULT NULL,
  `idevento` int(11) NOT NULL,
  `tipoexecucao` char(1) DEFAULT NULL,
  `gerarquando` char(1) NOT NULL,
  `data` date NOT NULL,
  `hora` char(4) DEFAULT NULL,
  `linhacomando` varchar(255) DEFAULT NULL,
  `linhacomandolinux` varchar(255) DEFAULT NULL,
  KEY `INDEX_ITEMBASEITEMCONFIGURACAO` (`idbaseitemconfiguracao`),
  KEY `INDEX_ITEMCONFIGURACAO` (`iditemconfiguracao`),
  KEY `INDEX_ITEMEVENTO` (`idevento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='itemconfiguracaoevento';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itemconfiguracaoevento`
--

LOCK TABLES `itemconfiguracaoevento` WRITE;
/*!40000 ALTER TABLE `itemconfiguracaoevento` DISABLE KEYS */;
/*!40000 ALTER TABLE `itemconfiguracaoevento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itemcotacao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `itemcotacao` (
  `iditemcotacao` int(11) NOT NULL,
  `idcotacao` int(11) DEFAULT NULL,
  `idproduto` int(11) DEFAULT NULL,
  `tipoidentificacao` char(1) NOT NULL,
  `quantidade` decimal(8,2) DEFAULT NULL,
  `situacao` varchar(20) DEFAULT NULL,
  `datahoralimite` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `idcategoriaproduto` int(11) DEFAULT NULL,
  `idunidademedida` int(11) DEFAULT NULL,
  `descricaoitem` varchar(200) NOT NULL,
  `especificacoes` longtext,
  `marcapreferencial` varchar(100) DEFAULT NULL,
  `precoaproximado` decimal(8,2) DEFAULT NULL,
  `solicitacoesatendidas` text,
  `resultadovalidacao` char(1) DEFAULT NULL,
  `mensagensvalidacao` text,
  `pesopreco` int(11) DEFAULT NULL,
  `pesoprazoentrega` int(11) DEFAULT NULL,
  `pesoprazopagto` int(11) DEFAULT NULL,
  `pesotaxajuros` int(11) DEFAULT NULL,
  `pesoprazogarantia` int(11) DEFAULT NULL,
  `exigefornecedorqualificado` char(1) DEFAULT NULL,
  PRIMARY KEY (`iditemcotacao`),
  KEY `fk_reference_25` (`idcotacao`),
  KEY `fk_reference_674` (`idproduto`),
  KEY `fk_reference_690` (`idunidademedida`),
  KEY `fk_reference_691` (`idcategoriaproduto`),
  CONSTRAINT `fk_reference_25` FOREIGN KEY (`idcotacao`) REFERENCES `cotacao` (`idcotacao`),
  CONSTRAINT `fk_reference_674` FOREIGN KEY (`idproduto`) REFERENCES `produto` (`idproduto`),
  CONSTRAINT `fk_reference_690` FOREIGN KEY (`idunidademedida`) REFERENCES `unidademedida` (`idunidademedida`),
  CONSTRAINT `fk_reference_691` FOREIGN KEY (`idcategoriaproduto`) REFERENCES `categoriaproduto` (`idcategoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itemcotacao`
--

LOCK TABLES `itemcotacao` WRITE;
/*!40000 ALTER TABLE `itemcotacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `itemcotacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itempedidocompra`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `itempedidocompra` (
  `iditempedido` int(11) NOT NULL,
  `idpedido` int(11) NOT NULL,
  `idproduto` int(11) NOT NULL,
  `idcoletapreco` int(11) DEFAULT NULL,
  `quantidade` decimal(8,2) NOT NULL,
  `valortotal` decimal(8,2) NOT NULL,
  `valordesconto` decimal(8,2) DEFAULT NULL,
  `valoracrescimo` decimal(8,2) DEFAULT NULL,
  `basecalculoicms` decimal(8,2) DEFAULT NULL,
  `aliquotaicms` decimal(8,2) DEFAULT NULL,
  `aliquotaipi` decimal(8,2) DEFAULT NULL,
  PRIMARY KEY (`iditempedido`),
  KEY `fk_reference_703` (`idcoletapreco`),
  KEY `fk_reference_704` (`idpedido`),
  KEY `fk_reference_705` (`idproduto`),
  CONSTRAINT `fk_reference_703` FOREIGN KEY (`idcoletapreco`) REFERENCES `coletapreco` (`idcoletapreco`),
  CONSTRAINT `fk_reference_704` FOREIGN KEY (`idpedido`) REFERENCES `pedidocompra` (`idpedido`),
  CONSTRAINT `fk_reference_705` FOREIGN KEY (`idproduto`) REFERENCES `produto` (`idproduto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itempedidocompra`
--

LOCK TABLES `itempedidocompra` WRITE;
/*!40000 ALTER TABLE `itempedidocompra` DISABLE KEYS */;
/*!40000 ALTER TABLE `itempedidocompra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itemrequisicaoproduto`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `itemrequisicaoproduto` (
  `iditemrequisicaoproduto` int(11) NOT NULL,
  `idsolicitacaoservico` int(11) NOT NULL,
  `idparecervalidacao` int(11) DEFAULT NULL,
  `idparecerautorizacao` int(11) DEFAULT NULL,
  `idcategoriaproduto` int(11) DEFAULT NULL,
  `idunidademedida` int(11) DEFAULT NULL,
  `idproduto` int(11) DEFAULT NULL,
  `iditemcotacao` int(11) DEFAULT NULL,
  `descricaoitem` varchar(200) NOT NULL,
  `especificacoes` longtext,
  `quantidade` decimal(8,2) NOT NULL,
  `marcapreferencial` varchar(100) DEFAULT NULL,
  `precoaproximado` decimal(8,2) DEFAULT NULL,
  `situacao` varchar(20) DEFAULT NULL,
  `percvariacaopreco` decimal(8,2) DEFAULT NULL,
  `qtdeaprovada` decimal(8,2) DEFAULT NULL,
  `tipoatendimento` char(1) NOT NULL COMMENT 'C - Cotação\n            E - Estoque',
  `tipoidentificacao` char(1) NOT NULL,
  `aprovacotacao` char(1) DEFAULT NULL,
  `qtdecotada` decimal(8,2) DEFAULT NULL,
  `valoraprovado` decimal(8,2) DEFAULT NULL,
  PRIMARY KEY (`iditemrequisicaoproduto`),
  KEY `fk_reference_625` (`idsolicitacaoservico`),
  KEY `fk_reference_643` (`idparecervalidacao`),
  KEY `fk_reference_644` (`idparecerautorizacao`),
  KEY `fk_reference_669` (`idproduto`),
  KEY `fk_reference_676` (`idunidademedida`),
  KEY `fk_reference_677` (`idcategoriaproduto`),
  KEY `fk_reference_692` (`iditemcotacao`),
  CONSTRAINT `fk_reference_625` FOREIGN KEY (`idsolicitacaoservico`) REFERENCES `requisicaoproduto` (`idsolicitacaoservico`),
  CONSTRAINT `fk_reference_643` FOREIGN KEY (`idparecervalidacao`) REFERENCES `parecer` (`idparecer`),
  CONSTRAINT `fk_reference_644` FOREIGN KEY (`idparecerautorizacao`) REFERENCES `parecer` (`idparecer`),
  CONSTRAINT `fk_reference_669` FOREIGN KEY (`idproduto`) REFERENCES `produto` (`idproduto`),
  CONSTRAINT `fk_reference_676` FOREIGN KEY (`idunidademedida`) REFERENCES `unidademedida` (`idunidademedida`),
  CONSTRAINT `fk_reference_677` FOREIGN KEY (`idcategoriaproduto`) REFERENCES `categoriaproduto` (`idcategoria`),
  CONSTRAINT `fk_reference_692` FOREIGN KEY (`iditemcotacao`) REFERENCES `itemcotacao` (`iditemcotacao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itemrequisicaoproduto`
--

LOCK TABLES `itemrequisicaoproduto` WRITE;
/*!40000 ALTER TABLE `itemrequisicaoproduto` DISABLE KEYS */;
/*!40000 ALTER TABLE `itemrequisicaoproduto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jornadatrabalho`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jornadatrabalho` (
  `idjornada` int(11) NOT NULL,
  `descricao` varchar(70) NOT NULL,
  `inicio1` char(5) DEFAULT NULL,
  `termino1` char(5) DEFAULT NULL,
  `inicio2` char(5) DEFAULT NULL,
  `termino2` char(5) DEFAULT NULL,
  `inicio3` char(5) DEFAULT NULL,
  `termino3` char(5) DEFAULT NULL,
  `inicio4` char(5) DEFAULT NULL,
  `termino4` char(5) DEFAULT NULL,
  `inicio5` char(5) DEFAULT NULL,
  `termino5` char(5) DEFAULT NULL,
  `cargahoraria` char(5) DEFAULT NULL,
  `datainicio` date DEFAULT NULL,
  `datafim` date DEFAULT NULL,
  PRIMARY KEY (`idjornada`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jornadatrabalho`
--

LOCK TABLES `jornadatrabalho` WRITE;
/*!40000 ALTER TABLE `jornadatrabalho` DISABLE KEYS */;
INSERT INTO `jornadatrabalho` VALUES (1,'Service Desk (Semana)','08:00','20:00','','','','','','','','','12:00',NULL,NULL),(2,'Service Desk (Fim de semana)','08:00','15:00','','','','','','','','','07:00',NULL,NULL);
/*!40000 ALTER TABLE `jornadatrabalho` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `justificacaofalha`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `justificacaofalha` (
  `idjustificacaofalha` int(11) NOT NULL,
  `iditemconfiguracao` int(11) DEFAULT NULL,
  `idbaseitemconfiguracao` int(11) DEFAULT NULL,
  `idevento` int(11) DEFAULT NULL,
  `idempregado` int(11) DEFAULT NULL,
  `idhistoricotentativa` int(11) DEFAULT NULL,
  `descricao` text,
  `data` date DEFAULT NULL,
  `hora` char(4) DEFAULT NULL,
  PRIMARY KEY (`idjustificacaofalha`),
  KEY `INDEX_JUSTIFICACAOITEMCONFIGURACAO` (`iditemconfiguracao`),
  KEY `INDEX_JUSTIFICACAOBASEITEMCONFIGURACAO` (`idbaseitemconfiguracao`),
  KEY `INDEX_JUSTIFICACAOEVENTO` (`idevento`),
  KEY `INDEX_JUSTIFICACAOEMPREGADO` (`idempregado`),
  KEY `FK_Reference_137` (`idhistoricotentativa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='justificacaofalha';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `justificacaofalha`
--

LOCK TABLES `justificacaofalha` WRITE;
/*!40000 ALTER TABLE `justificacaofalha` DISABLE KEYS */;
/*!40000 ALTER TABLE `justificacaofalha` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `justificativamudanca`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `justificativamudanca` (
  `idjustificativamudanca` int(11) NOT NULL,
  `descricaojustificativa` varchar(100) NOT NULL,
  `suspensao` char(1) NOT NULL,
  `situacao` char(1) NOT NULL,
  `aprovacao` char(1) DEFAULT NULL,
  `deleted` char(1) DEFAULT NULL,
  PRIMARY KEY (`idjustificativamudanca`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `justificativamudanca`
--

LOCK TABLES `justificativamudanca` WRITE;
/*!40000 ALTER TABLE `justificativamudanca` DISABLE KEYS */;
/*!40000 ALTER TABLE `justificativamudanca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `justificativaparecer`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `justificativaparecer` (
  `idjustificativa` int(11) NOT NULL,
  `descricaojustificativa` varchar(100) NOT NULL,
  `situacao` char(1) DEFAULT NULL,
  `aplicavelrequisicao` char(1) DEFAULT NULL,
  `aplicavelcotacao` char(1) DEFAULT NULL,
  `aplicavelinspecao` char(1) DEFAULT NULL,
  PRIMARY KEY (`idjustificativa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `justificativaparecer`
--

LOCK TABLES `justificativaparecer` WRITE;
/*!40000 ALTER TABLE `justificativaparecer` DISABLE KEYS */;
/*!40000 ALTER TABLE `justificativaparecer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `justificativasolicitacao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `justificativasolicitacao` (
  `idjustificativa` int(11) NOT NULL,
  `descricaojustificativa` varchar(100) NOT NULL,
  `suspensao` char(1) NOT NULL,
  `situacao` char(1) NOT NULL,
  `deleted` char(1) DEFAULT NULL,
  `aprovacao` char(1) DEFAULT NULL,
  PRIMARY KEY (`idjustificativa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `justificativasolicitacao`
--

LOCK TABLES `justificativasolicitacao` WRITE;
/*!40000 ALTER TABLE `justificativasolicitacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `justificativasolicitacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `liberacao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `liberacao` (
  `idliberacao` int(11) NOT NULL,
  `idsolicitante` int(11) NOT NULL,
  `idresponsavel` int(11) DEFAULT NULL,
  `titulo` varchar(100) NOT NULL,
  `descricao` text NOT NULL,
  `datainicial` date NOT NULL,
  `datafinal` date NOT NULL,
  `dataliberacao` date DEFAULT NULL,
  `situacao` char(1) NOT NULL COMMENT 'A - Aceita\n            E - Em execução\n            F - Finalizada\n            X - Cancelada',
  `risco` char(1) NOT NULL COMMENT 'B - Baixo\n            M - Médio\n            A - Alto',
  `versao` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`idliberacao`),
  KEY `fk_reference_720` (`idsolicitante`),
  KEY `fk_reference_721` (`idresponsavel`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `liberacao`
--

LOCK TABLES `liberacao` WRITE;
/*!40000 ALTER TABLE `liberacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `liberacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `liberacaomudanca`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `liberacaomudanca` (
  `idliberacao` int(11) NOT NULL,
  `idrequisicaomudanca` int(11) NOT NULL,
  PRIMARY KEY (`idliberacao`,`idrequisicaomudanca`),
  KEY `fk_reference_710` (`idrequisicaomudanca`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `liberacaomudanca`
--

LOCK TABLES `liberacaomudanca` WRITE;
/*!40000 ALTER TABLE `liberacaomudanca` DISABLE KEYS */;
/*!40000 ALTER TABLE `liberacaomudanca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `limitealcada`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `limitealcada` (
  `idlimitealcada` int(11) NOT NULL,
  `idalcada` int(11) NOT NULL,
  `idgrupo` int(11) NOT NULL,
  `tipolimite` char(1) DEFAULT NULL COMMENT 'F - Por faixa de valores\n            Q - Qualquer valor\n            ',
  `abrangenciacentrocusto` varchar(20) NOT NULL COMMENT 'T - Todos\n            R - Somente o responsável',
  `limitevaloritem` decimal(8,2) DEFAULT NULL,
  `limitevalormensal` decimal(8,2) DEFAULT NULL,
  `situacao` char(1) NOT NULL,
  PRIMARY KEY (`idlimitealcada`),
  KEY `fk_reference_647` (`idalcada`),
  KEY `fk_reference_649` (`idgrupo`),
  CONSTRAINT `fk_reference_647` FOREIGN KEY (`idalcada`) REFERENCES `alcada` (`idalcada`),
  CONSTRAINT `fk_reference_649` FOREIGN KEY (`idgrupo`) REFERENCES `grupo` (`idgrupo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `limitealcada`
--

LOCK TABLES `limitealcada` WRITE;
/*!40000 ALTER TABLE `limitealcada` DISABLE KEYS */;
/*!40000 ALTER TABLE `limitealcada` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lingua`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lingua` (
  `idlingua` int(11) NOT NULL,
  `nome` varchar(245) DEFAULT NULL,
  `sigla` varchar(245) DEFAULT NULL,
  `datainicio` date DEFAULT NULL,
  `datafim` date DEFAULT NULL,
  PRIMARY KEY (`idlingua`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lingua`
--

LOCK TABLES `lingua` WRITE;
/*!40000 ALTER TABLE `lingua` DISABLE KEYS */;
INSERT INTO `lingua` VALUES (1,'Português','PT','2013-01-30',NULL),(2,'Inglês','EN','2013-01-30',NULL),(3,'Espanhol','ES','2013-01-30',NULL);
/*!40000 ALTER TABLE `lingua` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `localexecucaoservico`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `localexecucaoservico` (
  `idlocalexecucaoservico` int(11) NOT NULL,
  `nomelocalexecucaoservico` varchar(70) NOT NULL,
  `deleted` char(1) DEFAULT NULL,
  PRIMARY KEY (`idlocalexecucaoservico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `localexecucaoservico`
--

LOCK TABLES `localexecucaoservico` WRITE;
/*!40000 ALTER TABLE `localexecucaoservico` DISABLE KEYS */;
INSERT INTO `localexecucaoservico` VALUES (1,'Interno','N'),(2,'Externo','N'),(4,'Interno/Externo',NULL);
/*!40000 ALTER TABLE `localexecucaoservico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `localidade`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `localidade` (
  `idlocalidade` int(11) NOT NULL,
  `nomelocalidade` varchar(255) DEFAULT NULL,
  `datainicio` date DEFAULT NULL,
  `datafim` date DEFAULT NULL,
  PRIMARY KEY (`idlocalidade`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `localidade`
--

LOCK TABLES `localidade` WRITE;
/*!40000 ALTER TABLE `localidade` DISABLE KEYS */;
/*!40000 ALTER TABLE `localidade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `localidadeunidade`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `localidadeunidade` (
  `idlocalidadeunidade` int(11) NOT NULL,
  `idunidade` int(11) NOT NULL,
  `idlocalidade` int(11) NOT NULL,
  `datainicio` date DEFAULT NULL,
  `datafim` date DEFAULT NULL,
  PRIMARY KEY (`idlocalidadeunidade`),
  KEY `fk_localida_reference_localida` (`idlocalidade`),
  CONSTRAINT `fk_localida_reference_localida` FOREIGN KEY (`idlocalidade`) REFERENCES `localidade` (`idlocalidade`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `localidadeunidade`
--

LOCK TABLES `localidadeunidade` WRITE;
/*!40000 ALTER TABLE `localidadeunidade` DISABLE KEYS */;
/*!40000 ALTER TABLE `localidadeunidade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logdados`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `logdados` (
  `idlog` int(255) NOT NULL AUTO_INCREMENT,
  `dtatualizacao` date DEFAULT NULL,
  `operacao` varchar(100) DEFAULT NULL,
  `dados` text,
  `idusuario` int(11) DEFAULT NULL,
  `localorigem` varchar(255) DEFAULT NULL,
  `nometabela` varchar(255) DEFAULT NULL,
  `logdadoscol` varchar(45) DEFAULT NULL,
  `datalog` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idlog`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logdados`
--

LOCK TABLES `logdados` WRITE;
/*!40000 ALTER TABLE `logdados` DISABLE KEYS */;
/*!40000 ALTER TABLE `logdados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `marca`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `marca` (
  `idmarca` int(11) NOT NULL,
  `idfabricante` bigint(20) DEFAULT NULL,
  `nomemarca` varchar(100) NOT NULL,
  `situacao` char(1) NOT NULL,
  PRIMARY KEY (`idmarca`),
  KEY `fk_reference_661` (`idfabricante`),
  CONSTRAINT `fk_reference_661` FOREIGN KEY (`idfabricante`) REFERENCES `fornecedor` (`idfornecedor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `marca`
--

LOCK TABLES `marca` WRITE;
/*!40000 ALTER TABLE `marca` DISABLE KEYS */;
/*!40000 ALTER TABLE `marca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `matrizprioridade`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `matrizprioridade` (
  `idMatrizPrioridade` int(11) NOT NULL,
  `siglaImpacto` char(2) NOT NULL,
  `siglaUrgencia` char(2) NOT NULL,
  `valorPrioridade` int(11) NOT NULL,
  `idcontrato` int(11) DEFAULT NULL,
  `deleted` char(1) DEFAULT NULL,
  PRIMARY KEY (`idMatrizPrioridade`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `matrizprioridade`
--

LOCK TABLES `matrizprioridade` WRITE;
/*!40000 ALTER TABLE `matrizprioridade` DISABLE KEYS */;
INSERT INTO `matrizprioridade` VALUES (1,'AL','B',2,NULL,''),(2,'AL','M',2,NULL,''),(3,'AL','A',1,NULL,''),(4,'AL','C',1,NULL,''),(5,'A','B',3,NULL,''),(6,'A','M',2,NULL,''),(7,'A','A',2,NULL,''),(8,'A','C',1,NULL,''),(9,'E','B',3,NULL,''),(10,'E','M',3,NULL,''),(11,'E','A',2,NULL,''),(12,'E','C',2,NULL,''),(13,'M','B',4,NULL,''),(14,'M','M',3,NULL,''),(15,'M','A',3,NULL,''),(16,'M','C',2,NULL,''),(17,'B','B',5,NULL,''),(18,'B','M',4,NULL,''),(19,'B','A',3,NULL,''),(20,'B','C',3,NULL,'');
/*!40000 ALTER TABLE `matrizprioridade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `matrizvisao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `matrizvisao` (
  `idmatriz` bigint(20) NOT NULL,
  `idvisao` bigint(20) DEFAULT NULL,
  `idobjetonegocio` bigint(20) DEFAULT NULL,
  `idcamposobjetonegocio1` bigint(20) DEFAULT NULL,
  `idcamposobjetonegocio2` bigint(20) DEFAULT NULL,
  `idcamposobjetonegocio3` bigint(20) DEFAULT NULL,
  `strinfo` text,
  `nomecampo1` varchar(255) DEFAULT NULL,
  `nomecampo2` varchar(255) DEFAULT NULL,
  `nomecampo3` varchar(255) DEFAULT NULL,
  `descricaocampo1` varchar(255) DEFAULT NULL,
  `descricaocampo2` varchar(255) DEFAULT NULL,
  `descricaocampo3` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idmatriz`),
  KEY `fk_reference_620` (`idvisao`),
  KEY `fk_reference_621` (`idobjetonegocio`),
  KEY `fk_reference_622` (`idcamposobjetonegocio1`),
  KEY `fk_reference_623` (`idcamposobjetonegocio2`),
  KEY `fk_reference_624` (`idcamposobjetonegocio3`),
  CONSTRAINT `fk_reference_620` FOREIGN KEY (`idvisao`) REFERENCES `visao` (`idvisao`),
  CONSTRAINT `fk_reference_621` FOREIGN KEY (`idobjetonegocio`) REFERENCES `objetonegocio` (`idobjetonegocio`),
  CONSTRAINT `fk_reference_622` FOREIGN KEY (`idcamposobjetonegocio1`) REFERENCES `camposobjetonegocio` (`idcamposobjetonegocio`),
  CONSTRAINT `fk_reference_623` FOREIGN KEY (`idcamposobjetonegocio2`) REFERENCES `camposobjetonegocio` (`idcamposobjetonegocio`),
  CONSTRAINT `fk_reference_624` FOREIGN KEY (`idcamposobjetonegocio3`) REFERENCES `camposobjetonegocio` (`idcamposobjetonegocio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `matrizvisao`
--

LOCK TABLES `matrizvisao` WRITE;
/*!40000 ALTER TABLE `matrizvisao` DISABLE KEYS */;
/*!40000 ALTER TABLE `matrizvisao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu` (
  `idmenu` int(11) NOT NULL,
  `idmenupai` int(11) DEFAULT NULL,
  `nome` varchar(256) NOT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  `descricao` varchar(256) DEFAULT NULL,
  `ordem` int(11) DEFAULT NULL,
  `link` varchar(256) DEFAULT NULL,
  `imagem` varchar(256) DEFAULT NULL,
  `horizontal` varchar(10) DEFAULT NULL,
  `menurapido` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idmenu`),
  KEY `INDEX_MENUPAI` (`idmenupai`),
  KEY `index_nome` (`nome`(255),`ordem`),
  KEY `index_link` (`link`(255)),
  KEY `index_rapido` (`menurapido`),
  KEY `index_menu_nome` (`nome`(255),`ordem`),
  KEY `index_menu_link` (`link`(255)),
  KEY `index_menu_rapido` (`menurapido`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='menu';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meucatalogo`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `meucatalogo` (
  `idusuario` int(11) NOT NULL,
  `idservico` int(11) NOT NULL,
  PRIMARY KEY (`idusuario`,`idservico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meucatalogo`
--

LOCK TABLES `meucatalogo` WRITE;
/*!40000 ALTER TABLE `meucatalogo` DISABLE KEYS */;
/*!40000 ALTER TABLE `meucatalogo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `midia`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `midia` (
  `idmidia` int(11) NOT NULL,
  `nome` varchar(200) DEFAULT NULL,
  `midiacol` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idmidia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `midia`
--

LOCK TABLES `midia` WRITE;
/*!40000 ALTER TABLE `midia` DISABLE KEYS */;
INSERT INTO `midia` (idmidia, nome) VALUES (1, 'Blu-ray'), (2, 'Cartão de Memória'), (3, 'CD'), (4, 'Disquete'), (5, 'DVD'), (6, 'Fita Magnética'), (7, 'HD'), (8, 'Pen drive'), (9, 'Outros');
/*!40000 ALTER TABLE `midia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `midiasoftware`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `midiasoftware` (
  `idmidiasoftware` int(11) NOT NULL,
  `nome` varchar(200) NOT NULL,
  `endfisico` varchar(500) DEFAULT NULL,
  `versao` varchar(20) DEFAULT NULL,
  `endlogico` varchar(200) DEFAULT NULL,
  `licencas` int(11) DEFAULT NULL,
  `idmidia` int(11) DEFAULT NULL,
  `idtiposoftware` int(11) DEFAULT NULL,
  `datainicio` date DEFAULT NULL,
  `datafim` date DEFAULT NULL,
  PRIMARY KEY (`idmidiasoftware`),
  KEY `fk_midia` (`idmidia`),
  KEY `fk_tiposoftware` (`idtiposoftware`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `midiasoftware`
--

LOCK TABLES `midiasoftware` WRITE;
/*!40000 ALTER TABLE `midiasoftware` DISABLE KEYS */;
/*!40000 ALTER TABLE `midiasoftware` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `modelosemails`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `modelosemails` (
  `idmodeloemail` int(11) NOT NULL,
  `titulo` varchar(100) NOT NULL,
  `texto` text NOT NULL,
  `situacao` char(1) NOT NULL,
  `identificador` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`idmodeloemail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `modelosemails`
--

LOCK TABLES `modelosemails` WRITE;
/*!40000 ALTER TABLE `modelosemails` DISABLE KEYS */;
INSERT INTO `modelosemails` VALUES (1,'Solicitação registrada -  ${IDSOLICITACAOSERVICO}','Senhor(a) ${NOMECONTATO},<br /><br />Informamos que a sua solicita&ccedil;&atilde;o foi registrada em ${DATAHORASOLICITACAOSTR}, conforme os dados abaixo:<br /><br /><strong>N&uacute;mero:</strong> ${IDSOLICITACAOSERVICO}<br /><strong>Tipo:</strong> ${DEMANDA}<br /><strong>Servi&ccedil;o:</strong> ${SERVICO}<br /><br /><strong>Descri&ccedil;&atilde;o:</strong> <br />${DESCRICAO}<br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda<br /><br /><br /><br /><br /><br />','A',NULL),(2,'Solicitação atendida - ${IDSOLICITACAOSERVICO}','Senhor(a) ${NOMECONTATO},<br /><br />Informamos que a sua solicita&ccedil;&atilde;o foi atendida em ${DATAHORAFIM}, conforme os dados abaixo:<br /><br /><strong>N&uacute;mero:</strong> ${IDSOLICITACAOSERVICO}<br /><strong>Tipo:</strong> ${DEMANDA}<br /><strong>Servi&ccedil;o:</strong> ${SERVICO}<br /><br /><strong>Descri&ccedil;&atilde;o:</strong> <br />${DESCRICAO}<br /><br /><strong>Resposta do grupo solucionador:</strong><br />${RESPOSTA}<br /><br />${LINKPESQUISASATISFACAO}<br />Caso a solu&ccedil;&atilde;o n&atilde;o lhe atenda, a solicita&ccedil;&atilde;o poder&aacute; ser reaberta.<br /><br />Atenciosamente,<br /><br />Central IT&nbsp;Tecnologia da Informa&ccedil;&atilde;o Ltda<br />','A',NULL),(3,'Solicitação em atendimento - ${IDSOLICITACAOSERVICO}','Senhor(a) ${NOMECONTATO},<br /><br />Informamos que a sua solicita&ccedil;&atilde;o registrada ${DATAHORASOLICITACAOSTR} est&aacute; em atendimento, conforme os dados abaixo:<br /><br /><strong>N&uacute;mero:</strong> ${IDSOLICITACAOSERVICO}<br /><strong>Tipo:</strong> ${DEMANDA}<br /><strong>Servi&ccedil;o:</strong> ${SERVICO}<br /><br /><strong>Descri&ccedil;&atilde;o:</strong> <br />${DESCRICAO}<br /><strong><br />Tarefa executada:</strong> ${NOMETAREFA}<br /><strong>Grupo de atendimento:</strong> ${GRUPOATUAL}<br /><br />Atenciosamente,<br /><br />Central IT&nbsp;Tecnologia da Informa&ccedil;&atilde;o Ltda<br />','A',NULL),(6,'Validade do Item configuração - ${IDENTIFICACAO}','Informamos que o item de configura&ccedil;&atilde;o identificado como ${IDENTIFICACAO} expirar&aacute; no dia&nbsp;${DATAEXPIRACAO}.<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.<br />','A',''),(7,'Validade do documento - ${TITULO}','Informamos que o documento titulado como ${TITULO} expirar&aacute; no dia&nbsp;${DATAEXPIRACAO}.<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.<br />','A','bc'),(8,'Criação da Pasta - ${NOME}','Informamos que foi criado a pasta ${NOME}.<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.<br />','A','cp'),(9,'Alteração da Pasta - ${NOME}','Informamos que a pasta ${NOME} foi alterada.<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.<br />','A','ap'),(10,'Exclusão da pasta - ${NOME}','Informamos que a pasta ${NOME} foi exclu&iacute;da.<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.','A','ep'),(11,'Criação do documento - ${TITULO}','Informamos que foi criado o documento ${TITULO}.<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.<br />','A','cc'),(12,'Alteração do documento - ${TITULO}','Informamos que o documento ${TITULO} foi alterado.<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.<br />','A','ac'),(13,'Exclusão do documento - ${TITULO}','Informamos que o documento ${TITULO} foi exclu&iacute;do.<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.<br />','A','ec'),(14,'Alteração do Item configuração - ${IDENTIFICACAO}','Informamos que o item de configura&ccedil;&atilde;o identificado como ${IDENTIFICACAO} sofreu altera&ccedil;&atilde;o.<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.','A','alteracaoIC'),(15,'Alteração do Item configuração para Grupo- ${IDENTIFICACAO}','Informamos que o item de configura&ccedil;&atilde;o identificado como ${IDENTIFICACAO} foi alterado para o Grupo ${NOMEGRUPOITEMCONFIGURACAO}<br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.','A','alteracaoICGrupo'),(16,'Criação do Item configuração - ${IDENTIFICACAO}','Informamos a cria&ccedil;&atilde;o do item de configura&ccedil;&atilde;o identificado como ${IDENTIFICACAO} .<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.','A','CriacaoIC'),(17,'Alteração Serviço - ${NOMESERVICO}','Informamos que o servi&ccedil;o identificado como ${NOMESERVICO} sofreu altera&ccedil;&atilde;o.<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.','A','alteracaoServico'),(26,'Requisição de Mudança em andamento - ${IDREQUISICAOMUDANCA}','Senhor(a) ${NOMESOLICITANTE}, <br /><br />Informamos que a requisi&ccedil;&atilde;o de mudan&ccedil;a registrada em ${DATAHORAINICIOSTR} est&aacute; em atendimento, conforme os dados abaixo:<br /><br /><strong>N&uacute;mero:</strong>&nbsp;${IDREQUISICAOMUDANCA}<br /><strong>T&iacute;tulo:</strong>&nbsp;${TITULO}<br /><br /><strong>Descri&ccedil;&atilde;o:</strong>&nbsp;<br />${TITULO}<br /><strong><br /></strong>${DESCRICAO}<br /><br /><strong>Grupo de atendimento:</strong>&nbsp;${NOMEGRUPOATUAL}<br /><br />Atenciosamente, <br /><br />Central IT&nbsp;Tecnologia da Informa&ccedil;&atilde;o Ltda','A','AndamentoReqMud'),(27,'Requisição de mudança registrada - ${IDREQUISICAOMUDANCA}','Senhor(a) ${NOMESOLICITANTE}, <br /><br />Informamos que a sua Requisi&ccedil;&atilde;o de mudan&ccedil;a foi registrada em ${DATAHORAINICIOSTR}, conforme os dados abaixo:<br /><strong><br />N&uacute;mero:</strong> ${IDREQUISICAOMUDANCA}<br /><strong>Tipo:</strong> ${TIPO}<br /><strong>T&iacute;tulo:</strong> ${TITULO}<br /><br /><strong>Descri&ccedil;&atilde;o:</strong> <br />${DESCRICAO}<br /><br />Atenciosamente, <br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.','A','AberturaReqMud'),(28,'Requisição de mudança finalizada - ${IDREQUISICAOMUDANCA}','Senhor(a) ${NOMESOLICITANTE}, <br /><br />Informamos que a sua Requisi&ccedil;&atilde;o de mudan&ccedil;a foi finalizada em ${DATAHORACONCLUSAO}, conforme os dados abaixo:<br /><strong><br />N&uacute;mero:</strong> ${IDREQUISICAOMUDANCA}<br /><strong>Tipo:</strong> ${TIPO}<br /><strong>T&iacute;tulo:</strong> ${TITULO}<br /><br /><strong>Status:</strong>${STATUS}<br /><strong>Descri&ccedil;&atilde;o:</strong> <br />${DESCRICAO}<br /><br /><br />Atenciosamente, <br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.','A','FinalizadaReqMud'),(29,'Requisição encaminhada para o COMITÊ CONSULTIVO DE MUDANÇA ','&nbsp;A Requisi&ccedil;&atilde;o de Mudan&ccedil;a abaixo foi encaminhada para o Comit&ecirc; Consultivo de Mudan&ccedil;a do qual o Senhor(a) faz parte:<div>&nbsp;</div><div><strong>N&uacute;mero</strong>: ${IDREQUISICAOMUDANCA}</div><div><strong>Tipo</strong>: ${TIPO}</div><div><strong>T&iacute;tulo</strong>: ${TITULO}</div><div>&nbsp;</div><div><strong>Descri&ccedil;&atilde;o</strong>:&nbsp;</div><div>${DESCRICAO}</div><div>&nbsp;</div><div>Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.</div>','A','emailCCM'),(30,'Requisição Mudança encaminhada para seu GRUPO DE TRABALHO','<div>&nbsp; A &nbsp;requisi&ccedil;&atilde;o mudan&ccedil;a abaixo foi encaminhada para seu Grupo de Trabalho:</div><div>&nbsp;</div><div>N&uacute;mero: ${IDREQUISICAOMUDANCA}</div><div>Tipo: ${TIPO}</div><div>Titulo: ${TITULO}</div><div>&nbsp;</div><div>Descri&ccedil;&atilde;o:&nbsp;</div><div>${DESCRICAO}</div><div>&nbsp;</div><div>Atenciosamente, <br /><br />Central IT&nbsp;Tecnologia da Informa&ccedil;&atilde;o Ltda.</div>','A','ReqMudancaGrupo');
/*!40000 ALTER TABLE `modelosemails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `moedas`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `moedas` (
  `idmoeda` int(11) NOT NULL,
  `nomemoeda` varchar(50) NOT NULL,
  PRIMARY KEY (`idmoeda`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='moedas';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `moedas`
--

LOCK TABLES `moedas` WRITE;
/*!40000 ALTER TABLE `moedas` DISABLE KEYS */;
INSERT INTO `moedas` VALUES (1,'Real'),(2,'UST');
/*!40000 ALTER TABLE `moedas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `motivosuspensaoativid`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `motivosuspensaoativid` (
  `idmotivo` int(11) NOT NULL,
  `descricao` varchar(100) NOT NULL,
  PRIMARY KEY (`idmotivo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='motivosuspensaoativid';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `motivosuspensaoativid`
--

LOCK TABLES `motivosuspensaoativid` WRITE;
/*!40000 ALTER TABLE `motivosuspensaoativid` DISABLE KEYS */;
/*!40000 ALTER TABLE `motivosuspensaoativid` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nagiosconexao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `nagiosconexao` (
  `idnagiosconexao` int(11) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `nomejndi` varchar(255) DEFAULT NULL,
  `criadopor` varchar(255) DEFAULT NULL,
  `modificadopor` varchar(255) DEFAULT NULL,
  `datacriacao` date DEFAULT NULL,
  `ultmodificacao` date DEFAULT NULL,
  `deleted` char(1) DEFAULT NULL,
  PRIMARY KEY (`idnagiosconexao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nagiosconexao`
--

LOCK TABLES `nagiosconexao` WRITE;
/*!40000 ALTER TABLE `nagiosconexao` DISABLE KEYS */;
/*!40000 ALTER TABLE `nagiosconexao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `netmap`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `netmap` (
  `idnetmap` int(11) NOT NULL,
  `ip` varchar(50) DEFAULT NULL,
  `mask` varchar(50) DEFAULT NULL,
  `mac` varchar(50) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `nome` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`idnetmap`),
  KEY `INDEX_NETMAPIP` (`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='netmap';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `netmap`
--

LOCK TABLES `netmap` WRITE;
/*!40000 ALTER TABLE `netmap` DISABLE KEYS */;
/*!40000 ALTER TABLE `netmap` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notificacao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notificacao` (
  `idnotificacao` int(11) NOT NULL,
  `titulo` varchar(255) DEFAULT NULL,
  `tiponotificacao` char(1) DEFAULT NULL,
  `datainicio` date DEFAULT NULL,
  `datafim` date DEFAULT NULL,
  `origemnotificacao` char(1) NOT NULL,
  `idContrato` int(11) DEFAULT NULL,
  PRIMARY KEY (`idnotificacao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notificacao`
--

LOCK TABLES `notificacao` WRITE;
/*!40000 ALTER TABLE `notificacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `notificacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notificacaogrupo`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notificacaogrupo` (
  `idnotificacao` int(11) DEFAULT NULL,
  `idgrupo` int(11) DEFAULT NULL,
  KEY `fk_alertagr_reference_alerta` (`idnotificacao`),
  KEY `fk_alertagr_reference_grupo` (`idgrupo`),
  CONSTRAINT `fk_alertagr_reference_alerta` FOREIGN KEY (`idnotificacao`) REFERENCES `notificacao` (`idnotificacao`),
  CONSTRAINT `fk_alertagr_reference_grupo` FOREIGN KEY (`idgrupo`) REFERENCES `grupo` (`idgrupo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notificacaogrupo`
--

LOCK TABLES `notificacaogrupo` WRITE;
/*!40000 ALTER TABLE `notificacaogrupo` DISABLE KEYS */;
/*!40000 ALTER TABLE `notificacaogrupo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notificacaoservico`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notificacaoservico` (
  `idNotificacao` int(11) DEFAULT NULL,
  `idServico` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notificacaoservico`
--

LOCK TABLES `notificacaoservico` WRITE;
/*!40000 ALTER TABLE `notificacaoservico` DISABLE KEYS */;
/*!40000 ALTER TABLE `notificacaoservico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notificacaousuario`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notificacaousuario` (
  `idnotificacao` int(11) DEFAULT NULL,
  `idusuario` int(11) DEFAULT NULL,
  KEY `fk_alertaus_reference_alerta` (`idnotificacao`),
  KEY `fk_alertaus_reference_usuario` (`idusuario`),
  CONSTRAINT `fk_alertaus_reference_alerta` FOREIGN KEY (`idnotificacao`) REFERENCES `notificacao` (`idnotificacao`),
  CONSTRAINT `fk_alertaus_reference_usuario` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`idusuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notificacaousuario`
--

LOCK TABLES `notificacaousuario` WRITE;
/*!40000 ALTER TABLE `notificacaousuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `notificacaousuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `objetivomonitoramento`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `objetivomonitoramento` (
  `idobjetivomonitoramento` int(11) NOT NULL,
  `idobjetivoplanomelhoria` int(11) NOT NULL,
  `titulomonitoramento` varchar(255) NOT NULL,
  `fatorcriticosucesso` varchar(255) DEFAULT NULL,
  `kpi` varchar(255) DEFAULT NULL,
  `metrica` text,
  `medicao` text,
  `relatorios` text,
  `responsavel` varchar(255) DEFAULT NULL,
  `criadopor` varchar(255) DEFAULT NULL,
  `modificadopor` varchar(255) DEFAULT NULL,
  `datacriacao` date DEFAULT NULL,
  `ultmodificacao` date DEFAULT NULL,
  PRIMARY KEY (`idobjetivomonitoramento`),
  KEY `fk_ref_objmn` (`idobjetivoplanomelhoria`),
  CONSTRAINT `fk_ref_objmn` FOREIGN KEY (`idobjetivoplanomelhoria`) REFERENCES `objetivoplanomelhoria` (`idobjetivoplanomelhoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `objetivomonitoramento`
--

LOCK TABLES `objetivomonitoramento` WRITE;
/*!40000 ALTER TABLE `objetivomonitoramento` DISABLE KEYS */;
/*!40000 ALTER TABLE `objetivomonitoramento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `objetivoplanomelhoria`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `objetivoplanomelhoria` (
  `idobjetivoplanomelhoria` int(11) NOT NULL,
  `idplanomelhoria` int(11) NOT NULL,
  `tituloobjetivo` varchar(255) NOT NULL,
  `detalhamento` text,
  `resultadoesperado` text,
  `medicao` varchar(255) DEFAULT NULL,
  `responsavel` varchar(255) DEFAULT NULL,
  `criadopor` varchar(255) DEFAULT NULL,
  `modificadopor` varchar(255) DEFAULT NULL,
  `datacriacao` date DEFAULT NULL,
  `ultmodificacao` date DEFAULT NULL,
  PRIMARY KEY (`idobjetivoplanomelhoria`),
  KEY `ix_obj_pm_id` (`idplanomelhoria`),
  CONSTRAINT `fk_ref_objplm` FOREIGN KEY (`idplanomelhoria`) REFERENCES `planomelhoria` (`idplanomelhoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `objetivoplanomelhoria`
--

LOCK TABLES `objetivoplanomelhoria` WRITE;
/*!40000 ALTER TABLE `objetivoplanomelhoria` DISABLE KEYS */;
/*!40000 ALTER TABLE `objetivoplanomelhoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `objetonegocio`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `objetonegocio` (
  `idobjetonegocio` bigint(20) NOT NULL,
  `nomeobjetonegocio` varchar(500) NOT NULL,
  `nometabeladb` varchar(120) NOT NULL,
  `situacao` char(1) NOT NULL,
  PRIMARY KEY (`idobjetonegocio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `objetonegocio`
--

LOCK TABLES `objetonegocio` WRITE;
/*!40000 ALTER TABLE `objetonegocio` DISABLE KEYS */;
/*!40000 ALTER TABLE `objetonegocio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ocorrenciamudanca`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ocorrenciamudanca` (
  `idocorrencia` int(11) NOT NULL,
  `iditemtrabalho` bigint(20) DEFAULT NULL,
  `idjustificativa` int(11) DEFAULT NULL,
  `idrequisicaomudanca` int(11) DEFAULT NULL,
  `dataregistro` date DEFAULT NULL,
  `horaregistro` varchar(5) DEFAULT NULL,
  `registradopor` varchar(100) DEFAULT NULL,
  `descricao` varchar(200) DEFAULT NULL,
  `datainicio` date DEFAULT NULL,
  `datafim` date DEFAULT NULL,
  `complementojustificativa` longtext,
  `dadosmudanca` longtext,
  `informacoescontato` longtext,
  `categoria` varchar(20) DEFAULT NULL,
  `origem` char(1) DEFAULT NULL,
  `tempogasto` smallint(6) DEFAULT NULL,
  `ocorrencia` longtext,
  `idcategoriaocorrencia` int(11) DEFAULT NULL,
  `idorigemocorrencia` int(11) DEFAULT NULL,
  PRIMARY KEY (`idocorrencia`),
  KEY `fk_ocorrenc_reference_bpm_item` (`iditemtrabalho`),
  KEY `fk_ocorrenc_reference_justific` (`idjustificativa`),
  KEY `fk_ocorrenc_reference_requisic` (`idrequisicaomudanca`),
  CONSTRAINT `fk_ocorrenc_reference_bpm_item` FOREIGN KEY (`iditemtrabalho`) REFERENCES `bpm_itemtrabalhofluxo` (`iditemtrabalho`),
  CONSTRAINT `fk_ocorrenc_reference_justific` FOREIGN KEY (`idjustificativa`) REFERENCES `justificativasolicitacao` (`idjustificativa`),
  CONSTRAINT `fk_ocorrenc_reference_requisic` FOREIGN KEY (`idrequisicaomudanca`) REFERENCES `requisicaomudanca` (`idrequisicaomudanca`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ocorrenciamudanca`
--

LOCK TABLES `ocorrenciamudanca` WRITE;
/*!40000 ALTER TABLE `ocorrenciamudanca` DISABLE KEYS */;
/*!40000 ALTER TABLE `ocorrenciamudanca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ocorrencias`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ocorrencias` (
  `idocorrencia` int(11) NOT NULL,
  `iddemanda` int(11) NOT NULL,
  `ocorrencia` text,
  `tipoocorrencia` char(1) NOT NULL,
  `respostaocorrencia` text,
  `data` date NOT NULL,
  `idempregado` int(11) DEFAULT NULL,
  PRIMARY KEY (`idocorrencia`),
  KEY `INDEX_OCORRENCIASDEMANDA` (`iddemanda`),
  KEY `INDEX_OCORRENCIASEMPREGADO` (`idempregado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ocorrencias';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ocorrencias`
--

LOCK TABLES `ocorrencias` WRITE;
/*!40000 ALTER TABLE `ocorrencias` DISABLE KEYS */;
/*!40000 ALTER TABLE `ocorrencias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ocorrenciasolicitacao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ocorrenciasolicitacao` (
  `idocorrencia` int(11) NOT NULL,
  `idjustificativa` int(11) DEFAULT NULL,
  `idsolicitacaoservico` bigint(20) DEFAULT NULL,
  `iditemtrabalho` bigint(20) DEFAULT NULL,
  `dataregistro` date DEFAULT NULL,
  `horaregistro` varchar(5) DEFAULT NULL,
  `registradopor` varchar(100) DEFAULT NULL,
  `descricao` varchar(500) DEFAULT NULL,
  `datainicio` date DEFAULT NULL,
  `datafim` date DEFAULT NULL,
  `complementojustificativa` text,
  `dadossolicitacao` longtext,
  `informacoescontato` text,
  `categoria` varchar(20) DEFAULT NULL,
  `origem` char(1) DEFAULT NULL,
  `tempogasto` smallint(6) DEFAULT NULL,
  `ocorrencia` longtext,
  `idcategoriaocorrencia` int(11) DEFAULT NULL,
  `idorigemocorrencia` int(11) DEFAULT NULL,
  PRIMARY KEY (`idocorrencia`),
  KEY `fk_reference_562` (`idjustificativa`),
  KEY `fk_reference_564` (`idsolicitacaoservico`),
  KEY `fk_reference_565` (`iditemtrabalho`),
  KEY `idcategoriaocorrencia` (`idcategoriaocorrencia`),
  KEY `idorigemocorrencia` (`idorigemocorrencia`),
  CONSTRAINT `ocorrenciasolicitacao_ibfk_1` FOREIGN KEY (`idcategoriaocorrencia`) REFERENCES `categoriaocorrencia` (`idcategoriaocorrencia`),
  CONSTRAINT `ocorrenciasolicitacao_ibfk_2` FOREIGN KEY (`idcategoriaocorrencia`) REFERENCES `categoriaocorrencia` (`idcategoriaocorrencia`),
  CONSTRAINT `ocorrenciasolicitacao_ibfk_3` FOREIGN KEY (`idcategoriaocorrencia`) REFERENCES `categoriaocorrencia` (`idcategoriaocorrencia`),
  CONSTRAINT `ocorrenciasolicitacao_ibfk_4` FOREIGN KEY (`idorigemocorrencia`) REFERENCES `origemocorrencia` (`idorigemocorrencia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ocorrenciasolicitacao`
--

LOCK TABLES `ocorrenciasolicitacao` WRITE;
/*!40000 ALTER TABLE `ocorrenciasolicitacao` DISABLE KEYS */;
INSERT INTO `ocorrenciasolicitacao` VALUES (1,NULL,1,NULL,'2013-04-05','09:40','Consultor','Registro da Solicitação','2013-04-05','2013-04-05',NULL,'{\"idSolicitacaoServico\":1,\"idContatoSolicitacaoServico\":1,\"idServicoContrato\":1,\"idSolicitante\":2,\"idOrigem\":1,\"idPrioridade\":5,\"filtroADPesq\":\"\",\"idUnidade\":1,\"dataHoraSolicitacao\":\"Apr 5, 2013 9:40:01 AM\",\"prazoCapturaHH\":0,\"prazoCapturaMM\":0,\"prazoHH\":0,\"prazoMM\":0,\"descricao\":\"Descri\\u0026ccedil;\\u0026atilde;o do Incidente ou Requisi\\u0026ccedil;\\u0026atilde;o\\u003cbr /\\u003e\",\"resposta\":\"\",\"dataHoraInicio\":\"Apr 5, 2013 9:40:01 AM\",\"situacao\":\"EmAndamento\",\"seqReabertura\":0,\"enviaEmailCriacao\":\"S\",\"enviaEmailFinalizacao\":\"S\",\"slaACombinar\":\"S\",\"idCalendario\":1,\"tempoDecorridoHH\":0,\"tempoDecorridoMM\":0,\"situacaoSLA\":\"N\",\"descrSituacao\":\"Em andamento\",\"atrasoSLA\":0.0,\"idContrato\":1,\"solicitante\":\"Default\",\"dataHoraSolicitacaoStr\":\"05/04/2013 09:40\",\"usuarioDto\":{\"idUsuario\":3,\"idEmpregado\":3,\"idPerfilAcessoUsuario\":1,\"idEmpresa\":1,\"login\":\"consultor\",\"nomeUsuario\":\"Consultor\",\"status\":\"A\",\"grupos\":[\"SDNIVEL1\",\"SDNIVEL2\",\"SDNIVEL3Apli\",\"SDNIVEL3Infra\",\"SDNIVEL3Sist\",\"SDNIVEL3Telefonia\"],\"locale\":\"\",\"colGrupos\":[{\"idGrupo\":2,\"nome\":\"1º NÍVEL\",\"sigla\":\"SDNIVEL1\",\"serviceDesk\":\"S\"},{\"idGrupo\":3,\"nome\":\"2º NÍVEL\",\"sigla\":\"SDNIVEL2\",\"serviceDesk\":\"S\"},{\"idGrupo\":16,\"nome\":\"3º NÍVEL - Aplicação\",\"sigla\":\"SDNIVEL3Apli\",\"serviceDesk\":\"S\"},{\"idGrupo\":4,\"nome\":\"3º NÍVEL - Infraestrutura\",\"sigla\":\"SDNIVEL3Infra\",\"serviceDesk\":\"S\"},{\"idGrupo\":17,\"nome\":\"3º NÍVEL - Sistemas\",\"sigla\":\"SDNIVEL3Sist\",\"serviceDesk\":\"S\"},{\"idGrupo\":19,\"nome\":\"3º NÍVEL - Telefonia\",\"sigla\":\"SDNIVEL3Telefonia\",\"serviceDesk\":\"N\"}]},\"acaoFluxo\":\"\",\"idTipoDemandaServico\":3,\"idServico\":1,\"itemConfiguracao\":\"\",\"messageId\":\"\",\"nomecontato\":\"Default\",\"telefonecontato\":\"3242-4433\",\"emailcontato\":\"citsmart_instalador_mysql@centralit.com.br\",\"observacao\":\"\",\"reclassificar\":\"\",\"escalar\":\"\",\"alterarSituacao\":\"\",\"detalhamentoCausa\":\"\",\"solucaoTemporaria\":\"N\",\"nomeServico\":\"Serviço 1\",\"ramal\":\"\",\"idAcordoNivelServico\":1,\"registradoPor\":\"Consultor\",\"registroexecucao\":\"\",\"impacto\":\"B\",\"urgencia\":\"B\",\"descricaoSemFormatacao\":\"Descrição do Incidente ou Requisição\",\"idResponsavel\":3,\"idGrupoAtual\":2,\"idGrupoNivel1\":2}','{\"idcontatosolicitacaoservico\":1,\"nomecontato\":\"Default\",\"telefonecontato\":\"3242-4433\",\"emailcontato\":\"citsmart_instalador_mysql@centralit.com.br\",\"observacao\":\"\"}','Criacao','O',0,NULL,NULL,NULL),(2,NULL,1,NULL,'2013-04-05','09:40','Automático','Inicio do SLA','2013-04-05','2013-04-05',NULL,'{\"idSolicitacaoServico\":1,\"idContatoSolicitacaoServico\":1,\"idServicoContrato\":1,\"idSolicitante\":2,\"idOrigem\":1,\"idPrioridade\":5,\"filtroADPesq\":\"\",\"idUnidade\":1,\"idFaseAtual\":2,\"dataHoraSolicitacao\":\"Apr 5, 2013 9:40:01 AM\",\"prazoCapturaHH\":0,\"prazoCapturaMM\":0,\"prazoHH\":0,\"prazoMM\":0,\"descricao\":\"Descri\\u0026ccedil;\\u0026atilde;o do Incidente ou Requisi\\u0026ccedil;\\u0026atilde;o\\u003cbr /\\u003e\",\"resposta\":\"\",\"dataHoraInicio\":\"Apr 5, 2013 9:40:01 AM\",\"situacao\":\"EmAndamento\",\"seqReabertura\":0,\"enviaEmailCriacao\":\"S\",\"enviaEmailFinalizacao\":\"S\",\"slaACombinar\":\"S\",\"idCalendario\":1,\"tempoDecorridoHH\":0,\"tempoDecorridoMM\":0,\"situacaoSLA\":\"A\",\"dataHoraInicioSLA\":\"Apr 5, 2013 9:40:01 AM\",\"descrSituacao\":\"Em andamento\",\"dataHoraLimite\":\"Apr 5, 2013 9:40:00 AM\",\"dataHoraLimiteStr\":\"05/04/2013 09:40\",\"dataHoraInicioSLAStr\":\"05/04/2013 09:40:01\",\"atrasoSLA\":0.0,\"idContrato\":1,\"solicitante\":\"Default\",\"dataHoraSolicitacaoStr\":\"05/04/2013 09:40\",\"usuarioDto\":{\"idUsuario\":3,\"idEmpregado\":3,\"idPerfilAcessoUsuario\":1,\"idEmpresa\":1,\"login\":\"consultor\",\"nomeUsuario\":\"Consultor\",\"status\":\"A\",\"grupos\":[\"SDNIVEL1\",\"SDNIVEL2\",\"SDNIVEL3Apli\",\"SDNIVEL3Infra\",\"SDNIVEL3Sist\",\"SDNIVEL3Telefonia\"],\"locale\":\"\",\"colGrupos\":[{\"idGrupo\":2,\"nome\":\"1º NÍVEL\",\"sigla\":\"SDNIVEL1\",\"serviceDesk\":\"S\"},{\"idGrupo\":3,\"nome\":\"2º NÍVEL\",\"sigla\":\"SDNIVEL2\",\"serviceDesk\":\"S\"},{\"idGrupo\":16,\"nome\":\"3º NÍVEL - Aplicação\",\"sigla\":\"SDNIVEL3Apli\",\"serviceDesk\":\"S\"},{\"idGrupo\":4,\"nome\":\"3º NÍVEL - Infraestrutura\",\"sigla\":\"SDNIVEL3Infra\",\"serviceDesk\":\"S\"},{\"idGrupo\":17,\"nome\":\"3º NÍVEL - Sistemas\",\"sigla\":\"SDNIVEL3Sist\",\"serviceDesk\":\"S\"},{\"idGrupo\":19,\"nome\":\"3º NÍVEL - Telefonia\",\"sigla\":\"SDNIVEL3Telefonia\",\"serviceDesk\":\"N\"}]},\"grupoAtual\":\"SDNIVEL1\",\"grupoNivel1\":\"SDNIVEL1\",\"acaoFluxo\":\"\",\"idTipoDemandaServico\":3,\"idServico\":1,\"itemConfiguracao\":\"\",\"messageId\":\"\",\"nomecontato\":\"Default\",\"telefonecontato\":\"3242-4433\",\"emailcontato\":\"citsmart_instalador_mysql@centralit.com.br\",\"observacao\":\"\",\"reclassificar\":\"\",\"escalar\":\"\",\"alterarSituacao\":\"\",\"detalhamentoCausa\":\"\",\"solucaoTemporaria\":\"N\",\"nomeServico\":\"Serviço 1\",\"ramal\":\"\",\"idAcordoNivelServico\":1,\"registradoPor\":\"Consultor\",\"registroexecucao\":\"\",\"impacto\":\"B\",\"urgencia\":\"B\",\"descricaoSemFormatacao\":\"Descrição do Incidente ou Requisição\",\"idResponsavel\":3,\"idGrupoAtual\":2,\"idGrupoNivel1\":2}',NULL,'InicioSLA','O',0,NULL,NULL,NULL),(3,NULL,2,NULL,'2013-04-05','09:43','Consultor','Registro da Solicitação','2013-04-05','2013-04-05',NULL,'{\"idSolicitacaoServico\":2,\"idContatoSolicitacaoServico\":2,\"idServicoContrato\":4,\"idSolicitante\":2,\"idOrigem\":1,\"idPrioridade\":5,\"filtroADPesq\":\"\",\"idUnidade\":1,\"dataHoraSolicitacao\":\"Apr 5, 2013 9:43:39 AM\",\"prazoCapturaHH\":0,\"prazoCapturaMM\":0,\"prazoHH\":0,\"prazoMM\":0,\"descricao\":\"Descri\\u0026ccedil;\\u0026atilde;o do Incidente ou Requisi\\u0026ccedil;\\u0026atilde;o\",\"resposta\":\"\",\"dataHoraInicio\":\"Apr 5, 2013 9:43:39 AM\",\"situacao\":\"EmAndamento\",\"seqReabertura\":0,\"enviaEmailCriacao\":\"S\",\"enviaEmailFinalizacao\":\"S\",\"slaACombinar\":\"S\",\"idCalendario\":1,\"tempoDecorridoHH\":0,\"tempoDecorridoMM\":0,\"situacaoSLA\":\"N\",\"descrSituacao\":\"Em andamento\",\"atrasoSLA\":0.0,\"idContrato\":1,\"solicitante\":\"Default\",\"dataHoraSolicitacaoStr\":\"05/04/2013 09:43\",\"usuarioDto\":{\"idUsuario\":3,\"idEmpregado\":3,\"idPerfilAcessoUsuario\":1,\"idEmpresa\":1,\"login\":\"consultor\",\"nomeUsuario\":\"Consultor\",\"status\":\"A\",\"grupos\":[\"SDNIVEL1\",\"SDNIVEL2\",\"SDNIVEL3Apli\",\"SDNIVEL3Infra\",\"SDNIVEL3Sist\",\"SDNIVEL3Telefonia\"],\"locale\":\"\",\"colGrupos\":[{\"idGrupo\":2,\"nome\":\"1º NÍVEL\",\"sigla\":\"SDNIVEL1\",\"serviceDesk\":\"S\"},{\"idGrupo\":3,\"nome\":\"2º NÍVEL\",\"sigla\":\"SDNIVEL2\",\"serviceDesk\":\"S\"},{\"idGrupo\":16,\"nome\":\"3º NÍVEL - Aplicação\",\"sigla\":\"SDNIVEL3Apli\",\"serviceDesk\":\"S\"},{\"idGrupo\":4,\"nome\":\"3º NÍVEL - Infraestrutura\",\"sigla\":\"SDNIVEL3Infra\",\"serviceDesk\":\"S\"},{\"idGrupo\":17,\"nome\":\"3º NÍVEL - Sistemas\",\"sigla\":\"SDNIVEL3Sist\",\"serviceDesk\":\"S\"},{\"idGrupo\":19,\"nome\":\"3º NÍVEL - Telefonia\",\"sigla\":\"SDNIVEL3Telefonia\",\"serviceDesk\":\"N\"}]},\"acaoFluxo\":\"\",\"idTipoDemandaServico\":1,\"idServico\":4,\"itemConfiguracao\":\"\",\"messageId\":\"\",\"nomecontato\":\"Default\",\"telefonecontato\":\"3242-4433\",\"emailcontato\":\"citsmart_instalador_mysql@centralit.com.br\",\"observacao\":\"\",\"reclassificar\":\"\",\"escalar\":\"\",\"alterarSituacao\":\"\",\"detalhamentoCausa\":\"\",\"solucaoTemporaria\":\"N\",\"nomeServico\":\"Serviço 4\",\"ramal\":\"\",\"idAcordoNivelServico\":2,\"registradoPor\":\"Consultor\",\"registroexecucao\":\"\",\"impacto\":\"B\",\"urgencia\":\"B\",\"descricaoSemFormatacao\":\"Descrição do Incidente ou Requisição\",\"idResponsavel\":3,\"idGrupoAtual\":2,\"idGrupoNivel1\":2}','{\"idcontatosolicitacaoservico\":2,\"nomecontato\":\"Default\",\"telefonecontato\":\"3242-4433\",\"emailcontato\":\"citsmart_instalador_mysql@centralit.com.br\",\"observacao\":\"\"}','Criacao','O',0,NULL,NULL,NULL),(4,NULL,2,NULL,'2013-04-05','09:43','Automático','Inicio do SLA','2013-04-05','2013-04-05',NULL,'{\"idSolicitacaoServico\":2,\"idContatoSolicitacaoServico\":2,\"idServicoContrato\":4,\"idSolicitante\":2,\"idOrigem\":1,\"idPrioridade\":5,\"filtroADPesq\":\"\",\"idUnidade\":1,\"idFaseAtual\":2,\"dataHoraSolicitacao\":\"Apr 5, 2013 9:43:39 AM\",\"prazoCapturaHH\":0,\"prazoCapturaMM\":0,\"prazoHH\":0,\"prazoMM\":0,\"descricao\":\"Descri\\u0026ccedil;\\u0026atilde;o do Incidente ou Requisi\\u0026ccedil;\\u0026atilde;o\",\"resposta\":\"\",\"dataHoraInicio\":\"Apr 5, 2013 9:43:39 AM\",\"situacao\":\"EmAndamento\",\"seqReabertura\":0,\"enviaEmailCriacao\":\"S\",\"enviaEmailFinalizacao\":\"S\",\"slaACombinar\":\"S\",\"idCalendario\":1,\"tempoDecorridoHH\":0,\"tempoDecorridoMM\":0,\"situacaoSLA\":\"A\",\"dataHoraInicioSLA\":\"Apr 5, 2013 9:43:39 AM\",\"descrSituacao\":\"Em andamento\",\"dataHoraLimite\":\"Apr 5, 2013 9:43:00 AM\",\"dataHoraLimiteStr\":\"05/04/2013 09:43\",\"dataHoraInicioSLAStr\":\"05/04/2013 09:43:39\",\"atrasoSLA\":0.0,\"idContrato\":1,\"solicitante\":\"Default\",\"dataHoraSolicitacaoStr\":\"05/04/2013 09:43\",\"usuarioDto\":{\"idUsuario\":3,\"idEmpregado\":3,\"idPerfilAcessoUsuario\":1,\"idEmpresa\":1,\"login\":\"consultor\",\"nomeUsuario\":\"Consultor\",\"status\":\"A\",\"grupos\":[\"SDNIVEL1\",\"SDNIVEL2\",\"SDNIVEL3Apli\",\"SDNIVEL3Infra\",\"SDNIVEL3Sist\",\"SDNIVEL3Telefonia\"],\"locale\":\"\",\"colGrupos\":[{\"idGrupo\":2,\"nome\":\"1º NÍVEL\",\"sigla\":\"SDNIVEL1\",\"serviceDesk\":\"S\"},{\"idGrupo\":3,\"nome\":\"2º NÍVEL\",\"sigla\":\"SDNIVEL2\",\"serviceDesk\":\"S\"},{\"idGrupo\":16,\"nome\":\"3º NÍVEL - Aplicação\",\"sigla\":\"SDNIVEL3Apli\",\"serviceDesk\":\"S\"},{\"idGrupo\":4,\"nome\":\"3º NÍVEL - Infraestrutura\",\"sigla\":\"SDNIVEL3Infra\",\"serviceDesk\":\"S\"},{\"idGrupo\":17,\"nome\":\"3º NÍVEL - Sistemas\",\"sigla\":\"SDNIVEL3Sist\",\"serviceDesk\":\"S\"},{\"idGrupo\":19,\"nome\":\"3º NÍVEL - Telefonia\",\"sigla\":\"SDNIVEL3Telefonia\",\"serviceDesk\":\"N\"}]},\"grupoAtual\":\"SDNIVEL1\",\"grupoNivel1\":\"SDNIVEL1\",\"acaoFluxo\":\"\",\"idTipoDemandaServico\":1,\"idServico\":4,\"itemConfiguracao\":\"\",\"messageId\":\"\",\"nomecontato\":\"Default\",\"telefonecontato\":\"3242-4433\",\"emailcontato\":\"citsmart_instalador_mysql@centralit.com.br\",\"observacao\":\"\",\"reclassificar\":\"\",\"escalar\":\"\",\"alterarSituacao\":\"\",\"detalhamentoCausa\":\"\",\"solucaoTemporaria\":\"N\",\"nomeServico\":\"Serviço 4\",\"ramal\":\"\",\"idAcordoNivelServico\":2,\"registradoPor\":\"Consultor\",\"registroexecucao\":\"\",\"impacto\":\"B\",\"urgencia\":\"B\",\"descricaoSemFormatacao\":\"Descrição do Incidente ou Requisição\",\"idResponsavel\":3,\"idGrupoAtual\":2,\"idGrupoNivel1\":2}',NULL,'InicioSLA','O',0,NULL,NULL,NULL),(5,NULL,3,NULL,'2013-04-05','09:46','Default','Registro da Solicitação','2013-04-05','2013-04-05',NULL,'{\"idSolicitacaoServico\":3,\"idContatoSolicitacaoServico\":3,\"idServicoContrato\":5,\"idSolicitante\":3,\"idOrigem\":1,\"idPrioridade\":5,\"filtroADPesq\":\"\",\"idUnidade\":1,\"dataHoraSolicitacao\":\"Apr 5, 2013 9:46:04 AM\",\"prazoCapturaHH\":0,\"prazoCapturaMM\":0,\"prazoHH\":0,\"prazoMM\":0,\"descricao\":\"Descri\\u0026ccedil;\\u0026atilde;o do Incidente ou Requisi\\u0026ccedil;\\u0026atilde;o\",\"resposta\":\"\",\"dataHoraInicio\":\"Apr 5, 2013 9:46:04 AM\",\"situacao\":\"Cancelada\",\"seqReabertura\":0,\"enviaEmailCriacao\":\"S\",\"enviaEmailFinalizacao\":\"S\",\"slaACombinar\":\"S\",\"idCalendario\":1,\"tempoDecorridoHH\":0,\"tempoDecorridoMM\":0,\"situacaoSLA\":\"N\",\"descrSituacao\":\"Cancelada\",\"atrasoSLA\":0.0,\"idContrato\":1,\"solicitante\":\"Consultor\",\"dataHoraSolicitacaoStr\":\"05/04/2013 09:46\",\"usuarioDto\":{\"idUsuario\":2,\"idEmpregado\":2,\"idPerfilAcessoUsuario\":2,\"idEmpresa\":1,\"login\":\"default\",\"nomeUsuario\":\"Default\",\"status\":\"A\",\"grupos\":[\"SDNIVEL1\",\"SDNIVEL2\",\"SDNIVEL3Apli\",\"SDNIVEL3Infra\",\"SDNIVEL3Sist\",\"SDNIVEL3Telefonia\"],\"locale\":\"\",\"colGrupos\":[{\"idGrupo\":2,\"nome\":\"1º NÍVEL\",\"sigla\":\"SDNIVEL1\",\"serviceDesk\":\"S\"},{\"idGrupo\":3,\"nome\":\"2º NÍVEL\",\"sigla\":\"SDNIVEL2\",\"serviceDesk\":\"S\"},{\"idGrupo\":16,\"nome\":\"3º NÍVEL - Aplicação\",\"sigla\":\"SDNIVEL3Apli\",\"serviceDesk\":\"S\"},{\"idGrupo\":4,\"nome\":\"3º NÍVEL - Infraestrutura\",\"sigla\":\"SDNIVEL3Infra\",\"serviceDesk\":\"S\"},{\"idGrupo\":17,\"nome\":\"3º NÍVEL - Sistemas\",\"sigla\":\"SDNIVEL3Sist\",\"serviceDesk\":\"S\"},{\"idGrupo\":19,\"nome\":\"3º NÍVEL - Telefonia\",\"sigla\":\"SDNIVEL3Telefonia\",\"serviceDesk\":\"N\"}]},\"acaoFluxo\":\"\",\"idTipoDemandaServico\":1,\"idServico\":5,\"itemConfiguracao\":\"\",\"messageId\":\"\",\"nomecontato\":\"Consultor\",\"telefonecontato\":\"3242-4433\",\"emailcontato\":\"citsmart_instalador_mysql@centralit.com.br\",\"observacao\":\"\",\"reclassificar\":\"\",\"escalar\":\"\",\"alterarSituacao\":\"\",\"detalhamentoCausa\":\"\",\"solucaoTemporaria\":\"N\",\"nomeServico\":\"Serviço 5\",\"ramal\":\"\",\"idAcordoNivelServico\":3,\"registradoPor\":\"Default\",\"registroexecucao\":\"\",\"impacto\":\"B\",\"urgencia\":\"B\",\"descricaoSemFormatacao\":\"Descrição do Incidente ou Requisição\",\"idResponsavel\":2,\"idGrupoAtual\":3,\"idGrupoNivel1\":2}','{\"idcontatosolicitacaoservico\":3,\"nomecontato\":\"Consultor\",\"telefonecontato\":\"3242-4433\",\"emailcontato\":\"citsmart_instalador_mysql@centralit.com.br\",\"observacao\":\"\"}','Criacao','O',0,NULL,NULL,NULL),(6,NULL,3,NULL,'2013-04-05','09:46','Automático','Encerramento da Solicitação','2013-04-05','2013-04-05',NULL,'{\"idSolicitacaoServico\":3,\"idContatoSolicitacaoServico\":3,\"idServicoContrato\":5,\"idSolicitante\":3,\"idOrigem\":1,\"idPrioridade\":5,\"filtroADPesq\":\"\",\"idUnidade\":1,\"idFaseAtual\":2,\"dataHoraSolicitacao\":\"Apr 5, 2013 9:46:04 AM\",\"prazoCapturaHH\":0,\"prazoCapturaMM\":0,\"prazoHH\":0,\"prazoMM\":0,\"descricao\":\"Descri\\u0026ccedil;\\u0026atilde;o do Incidente ou Requisi\\u0026ccedil;\\u0026atilde;o\",\"resposta\":\"\",\"dataHoraInicio\":\"Apr 5, 2013 9:46:04 AM\",\"dataHoraFim\":\"Apr 5, 2013 9:46:04 AM\",\"situacao\":\"Cancelada\",\"seqReabertura\":0,\"enviaEmailCriacao\":\"S\",\"enviaEmailFinalizacao\":\"S\",\"slaACombinar\":\"S\",\"idCalendario\":1,\"tempoDecorridoHH\":0,\"tempoDecorridoMM\":0,\"situacaoSLA\":\"N\",\"descrSituacao\":\"Cancelada\",\"atrasoSLA\":0.0,\"idContrato\":1,\"solicitante\":\"Consultor\",\"dataHoraSolicitacaoStr\":\"05/04/2013 09:46\",\"usuarioDto\":{\"idUsuario\":2,\"idEmpregado\":2,\"idPerfilAcessoUsuario\":2,\"idEmpresa\":1,\"login\":\"default\",\"nomeUsuario\":\"Default\",\"status\":\"A\",\"grupos\":[\"SDNIVEL1\",\"SDNIVEL2\",\"SDNIVEL3Apli\",\"SDNIVEL3Infra\",\"SDNIVEL3Sist\",\"SDNIVEL3Telefonia\"],\"locale\":\"\",\"colGrupos\":[{\"idGrupo\":2,\"nome\":\"1º NÍVEL\",\"sigla\":\"SDNIVEL1\",\"serviceDesk\":\"S\"},{\"idGrupo\":3,\"nome\":\"2º NÍVEL\",\"sigla\":\"SDNIVEL2\",\"serviceDesk\":\"S\"},{\"idGrupo\":16,\"nome\":\"3º NÍVEL - Aplicação\",\"sigla\":\"SDNIVEL3Apli\",\"serviceDesk\":\"S\"},{\"idGrupo\":4,\"nome\":\"3º NÍVEL - Infraestrutura\",\"sigla\":\"SDNIVEL3Infra\",\"serviceDesk\":\"S\"},{\"idGrupo\":17,\"nome\":\"3º NÍVEL - Sistemas\",\"sigla\":\"SDNIVEL3Sist\",\"serviceDesk\":\"S\"},{\"idGrupo\":19,\"nome\":\"3º NÍVEL - Telefonia\",\"sigla\":\"SDNIVEL3Telefonia\",\"serviceDesk\":\"N\"}]},\"grupoAtual\":\"SDNIVEL2\",\"grupoNivel1\":\"SDNIVEL1\",\"acaoFluxo\":\"\",\"idTipoDemandaServico\":1,\"idServico\":5,\"itemConfiguracao\":\"\",\"messageId\":\"\",\"nomecontato\":\"Consultor\",\"telefonecontato\":\"3242-4433\",\"emailcontato\":\"citsmart_instalador_mysql@centralit.com.br\",\"observacao\":\"\",\"reclassificar\":\"\",\"escalar\":\"\",\"alterarSituacao\":\"\",\"detalhamentoCausa\":\"\",\"solucaoTemporaria\":\"N\",\"nomeServico\":\"Serviço 5\",\"ramal\":\"\",\"idAcordoNivelServico\":3,\"registradoPor\":\"Default\",\"registroexecucao\":\"\",\"impacto\":\"B\",\"urgencia\":\"B\",\"tempoCapturaHH\":0,\"tempoCapturaMM\":0,\"tempoAtrasoHH\":0,\"tempoAtrasoMM\":0,\"descricaoSemFormatacao\":\"Descrição do Incidente ou Requisição\",\"idResponsavel\":2,\"idGrupoAtual\":3,\"idGrupoNivel1\":2}',NULL,'Encerramento','O',0,NULL,NULL,NULL),(7,NULL,3,NULL,'2013-04-05','09:46','Automático','Inicio do SLA','2013-04-05','2013-04-05',NULL,'{\"idSolicitacaoServico\":3,\"idContatoSolicitacaoServico\":3,\"idServicoContrato\":5,\"idSolicitante\":3,\"idOrigem\":1,\"idPrioridade\":5,\"filtroADPesq\":\"\",\"idUnidade\":1,\"idFaseAtual\":2,\"dataHoraSolicitacao\":\"Apr 5, 2013 9:46:04 AM\",\"prazoCapturaHH\":0,\"prazoCapturaMM\":0,\"prazoHH\":0,\"prazoMM\":0,\"descricao\":\"Descri\\u0026ccedil;\\u0026atilde;o do Incidente ou Requisi\\u0026ccedil;\\u0026atilde;o\",\"resposta\":\"\",\"dataHoraInicio\":\"Apr 5, 2013 9:46:04 AM\",\"dataHoraFim\":\"Apr 5, 2013 9:46:04 AM\",\"situacao\":\"Cancelada\",\"seqReabertura\":0,\"enviaEmailCriacao\":\"S\",\"enviaEmailFinalizacao\":\"S\",\"slaACombinar\":\"S\",\"idCalendario\":1,\"tempoDecorridoHH\":0,\"tempoDecorridoMM\":0,\"situacaoSLA\":\"A\",\"dataHoraInicioSLA\":\"Apr 5, 2013 9:46:04 AM\",\"descrSituacao\":\"Cancelada\",\"dataHoraLimite\":\"Apr 5, 2013 9:46:00 AM\",\"dataHoraLimiteStr\":\"05/04/2013 09:46\",\"dataHoraInicioSLAStr\":\"05/04/2013 09:46:04\",\"atrasoSLA\":0.0,\"idContrato\":1,\"solicitante\":\"Consultor\",\"dataHoraSolicitacaoStr\":\"05/04/2013 09:46\",\"usuarioDto\":{\"idUsuario\":2,\"idEmpregado\":2,\"idPerfilAcessoUsuario\":2,\"idEmpresa\":1,\"login\":\"default\",\"nomeUsuario\":\"Default\",\"status\":\"A\",\"grupos\":[\"SDNIVEL1\",\"SDNIVEL2\",\"SDNIVEL3Apli\",\"SDNIVEL3Infra\",\"SDNIVEL3Sist\",\"SDNIVEL3Telefonia\"],\"locale\":\"\",\"colGrupos\":[{\"idGrupo\":2,\"nome\":\"1º NÍVEL\",\"sigla\":\"SDNIVEL1\",\"serviceDesk\":\"S\"},{\"idGrupo\":3,\"nome\":\"2º NÍVEL\",\"sigla\":\"SDNIVEL2\",\"serviceDesk\":\"S\"},{\"idGrupo\":16,\"nome\":\"3º NÍVEL - Aplicação\",\"sigla\":\"SDNIVEL3Apli\",\"serviceDesk\":\"S\"},{\"idGrupo\":4,\"nome\":\"3º NÍVEL - Infraestrutura\",\"sigla\":\"SDNIVEL3Infra\",\"serviceDesk\":\"S\"},{\"idGrupo\":17,\"nome\":\"3º NÍVEL - Sistemas\",\"sigla\":\"SDNIVEL3Sist\",\"serviceDesk\":\"S\"},{\"idGrupo\":19,\"nome\":\"3º NÍVEL - Telefonia\",\"sigla\":\"SDNIVEL3Telefonia\",\"serviceDesk\":\"N\"}]},\"grupoAtual\":\"SDNIVEL2\",\"grupoNivel1\":\"SDNIVEL1\",\"acaoFluxo\":\"\",\"idTipoDemandaServico\":1,\"idServico\":5,\"itemConfiguracao\":\"\",\"messageId\":\"\",\"nomecontato\":\"Consultor\",\"telefonecontato\":\"3242-4433\",\"emailcontato\":\"citsmart_instalador_mysql@centralit.com.br\",\"observacao\":\"\",\"reclassificar\":\"\",\"escalar\":\"\",\"alterarSituacao\":\"\",\"detalhamentoCausa\":\"\",\"solucaoTemporaria\":\"N\",\"nomeServico\":\"Serviço 5\",\"ramal\":\"\",\"idAcordoNivelServico\":3,\"registradoPor\":\"Default\",\"registroexecucao\":\"\",\"impacto\":\"B\",\"urgencia\":\"B\",\"tempoCapturaHH\":0,\"tempoCapturaMM\":0,\"tempoAtrasoHH\":0,\"tempoAtrasoMM\":0,\"descricaoSemFormatacao\":\"Descrição do Incidente ou Requisição\",\"idResponsavel\":2,\"idGrupoAtual\":3,\"idGrupoNivel1\":2}',NULL,'InicioSLA','O',0,NULL,NULL,NULL),(8,NULL,3,NULL,'2013-04-05','09:46','Automático','Encerramento da Solicitação','2013-04-05','2013-04-05',NULL,'{\"idSolicitacaoServico\":3,\"idContatoSolicitacaoServico\":3,\"idServicoContrato\":5,\"idSolicitante\":3,\"idOrigem\":1,\"idPrioridade\":5,\"filtroADPesq\":\"\",\"idUnidade\":1,\"idFaseAtual\":2,\"dataHoraSolicitacao\":\"Apr 5, 2013 9:46:04 AM\",\"prazoCapturaHH\":0,\"prazoCapturaMM\":0,\"prazoHH\":0,\"prazoMM\":0,\"descricao\":\"Descri\\u0026ccedil;\\u0026atilde;o do Incidente ou Requisi\\u0026ccedil;\\u0026atilde;o\",\"resposta\":\"\",\"dataHoraInicio\":\"Apr 5, 2013 9:46:04 AM\",\"dataHoraFim\":\"Apr 5, 2013 9:46:04 AM\",\"situacao\":\"Cancelada\",\"seqReabertura\":0,\"enviaEmailCriacao\":\"S\",\"enviaEmailFinalizacao\":\"S\",\"slaACombinar\":\"S\",\"idCalendario\":1,\"tempoDecorridoHH\":0,\"tempoDecorridoMM\":0,\"situacaoSLA\":\"A\",\"dataHoraInicioSLA\":\"Apr 5, 2013 9:46:04 AM\",\"descrSituacao\":\"Cancelada\",\"dataHoraLimite\":\"Apr 5, 2013 9:46:00 AM\",\"dataHoraLimiteStr\":\"05/04/2013 09:46\",\"dataHoraInicioSLAStr\":\"05/04/2013 09:46:04\",\"atrasoSLA\":0.0,\"idContrato\":1,\"solicitante\":\"Consultor\",\"dataHoraSolicitacaoStr\":\"05/04/2013 09:46\",\"usuarioDto\":{\"idUsuario\":2,\"idEmpregado\":2,\"idPerfilAcessoUsuario\":2,\"idEmpresa\":1,\"login\":\"default\",\"nomeUsuario\":\"Default\",\"status\":\"A\",\"grupos\":[\"SDNIVEL1\",\"SDNIVEL2\",\"SDNIVEL3Apli\",\"SDNIVEL3Infra\",\"SDNIVEL3Sist\",\"SDNIVEL3Telefonia\"],\"locale\":\"\",\"colGrupos\":[{\"idGrupo\":2,\"nome\":\"1º NÍVEL\",\"sigla\":\"SDNIVEL1\",\"serviceDesk\":\"S\"},{\"idGrupo\":3,\"nome\":\"2º NÍVEL\",\"sigla\":\"SDNIVEL2\",\"serviceDesk\":\"S\"},{\"idGrupo\":16,\"nome\":\"3º NÍVEL - Aplicação\",\"sigla\":\"SDNIVEL3Apli\",\"serviceDesk\":\"S\"},{\"idGrupo\":4,\"nome\":\"3º NÍVEL - Infraestrutura\",\"sigla\":\"SDNIVEL3Infra\",\"serviceDesk\":\"S\"},{\"idGrupo\":17,\"nome\":\"3º NÍVEL - Sistemas\",\"sigla\":\"SDNIVEL3Sist\",\"serviceDesk\":\"S\"},{\"idGrupo\":19,\"nome\":\"3º NÍVEL - Telefonia\",\"sigla\":\"SDNIVEL3Telefonia\",\"serviceDesk\":\"N\"}]},\"grupoAtual\":\"SDNIVEL2\",\"grupoNivel1\":\"SDNIVEL1\",\"acaoFluxo\":\"\",\"idTipoDemandaServico\":1,\"idServico\":5,\"itemConfiguracao\":\"\",\"messageId\":\"\",\"nomecontato\":\"Consultor\",\"telefonecontato\":\"3242-4433\",\"emailcontato\":\"citsmart_instalador_mysql@centralit.com.br\",\"observacao\":\"\",\"reclassificar\":\"\",\"escalar\":\"\",\"alterarSituacao\":\"\",\"detalhamentoCausa\":\"\",\"solucaoTemporaria\":\"N\",\"nomeServico\":\"Serviço 5\",\"ramal\":\"\",\"idAcordoNivelServico\":3,\"registradoPor\":\"Default\",\"registroexecucao\":\"\",\"impacto\":\"B\",\"urgencia\":\"B\",\"tempoCapturaHH\":0,\"tempoCapturaMM\":0,\"tempoAtrasoHH\":0,\"tempoAtrasoMM\":0,\"tempoAtendimentoHH\":0,\"tempoAtendimentoMM\":0,\"descricaoSemFormatacao\":\"Descrição do Incidente ou Requisição\",\"idResponsavel\":2,\"idGrupoAtual\":3,\"idGrupoNivel1\":2}',NULL,'Encerramento','O',0,NULL,NULL,NULL),(9,NULL,4,NULL,'2013-04-05','10:13','Default','Registro da Solicitação','2013-04-05','2013-04-05',NULL,'{\"idSolicitacaoServico\":4,\"idContatoSolicitacaoServico\":4,\"idServicoContrato\":5,\"idSolicitante\":3,\"idOrigem\":1,\"idPrioridade\":5,\"filtroADPesq\":\"\",\"idUnidade\":1,\"dataHoraSolicitacao\":\"Apr 5, 2013 10:13:17 AM\",\"prazoCapturaHH\":0,\"prazoCapturaMM\":0,\"prazoHH\":0,\"prazoMM\":0,\"descricao\":\"Descri\\u0026ccedil;\\u0026atilde;o do Incidente ou Requisi\\u0026ccedil;\\u0026atilde;o\\u003cbr /\\u003e\",\"resposta\":\"\",\"dataHoraInicio\":\"Apr 5, 2013 10:13:17 AM\",\"situacao\":\"EmAndamento\",\"seqReabertura\":0,\"enviaEmailCriacao\":\"S\",\"enviaEmailFinalizacao\":\"S\",\"slaACombinar\":\"S\",\"idCalendario\":1,\"tempoDecorridoHH\":0,\"tempoDecorridoMM\":0,\"situacaoSLA\":\"N\",\"descrSituacao\":\"Em andamento\",\"atrasoSLA\":0.0,\"idContrato\":1,\"solicitante\":\"Consultor\",\"dataHoraSolicitacaoStr\":\"05/04/2013 10:13\",\"usuarioDto\":{\"idUsuario\":2,\"idEmpregado\":2,\"idPerfilAcessoUsuario\":2,\"idEmpresa\":1,\"login\":\"default\",\"nomeUsuario\":\"Default\",\"status\":\"A\",\"grupos\":[\"SDNIVEL1\",\"SDNIVEL2\",\"SDNIVEL3Apli\",\"SDNIVEL3Infra\",\"SDNIVEL3Sist\",\"SDNIVEL3Telefonia\"],\"locale\":\"\",\"colGrupos\":[{\"idGrupo\":2,\"nome\":\"1º NÍVEL\",\"sigla\":\"SDNIVEL1\",\"serviceDesk\":\"S\"},{\"idGrupo\":3,\"nome\":\"2º NÍVEL\",\"sigla\":\"SDNIVEL2\",\"serviceDesk\":\"S\"},{\"idGrupo\":16,\"nome\":\"3º NÍVEL - Aplicação\",\"sigla\":\"SDNIVEL3Apli\",\"serviceDesk\":\"S\"},{\"idGrupo\":4,\"nome\":\"3º NÍVEL - Infraestrutura\",\"sigla\":\"SDNIVEL3Infra\",\"serviceDesk\":\"S\"},{\"idGrupo\":17,\"nome\":\"3º NÍVEL - Sistemas\",\"sigla\":\"SDNIVEL3Sist\",\"serviceDesk\":\"S\"},{\"idGrupo\":19,\"nome\":\"3º NÍVEL - Telefonia\",\"sigla\":\"SDNIVEL3Telefonia\",\"serviceDesk\":\"N\"}]},\"acaoFluxo\":\"\",\"idTipoDemandaServico\":1,\"idServico\":5,\"itemConfiguracao\":\"\",\"messageId\":\"\",\"nomecontato\":\"Consultor\",\"telefonecontato\":\"3242-4433\",\"emailcontato\":\"citsmart_instalador_mysql@centralit.com.br\",\"observacao\":\"\",\"reclassificar\":\"\",\"escalar\":\"\",\"alterarSituacao\":\"\",\"detalhamentoCausa\":\"\",\"solucaoTemporaria\":\"N\",\"nomeServico\":\"Serviço 5\",\"ramal\":\"\",\"idAcordoNivelServico\":3,\"registradoPor\":\"Default\",\"registroexecucao\":\"\",\"impacto\":\"B\",\"urgencia\":\"B\",\"descricaoSemFormatacao\":\"Descrição do Incidente ou Requisição\",\"idResponsavel\":2,\"idGrupoAtual\":3,\"idGrupoNivel1\":2}','{\"idcontatosolicitacaoservico\":4,\"nomecontato\":\"Consultor\",\"telefonecontato\":\"3242-4433\",\"emailcontato\":\"citsmart_instalador_mysql@centralit.com.br\",\"observacao\":\"\"}','Criacao','O',0,NULL,NULL,NULL),(10,NULL,4,NULL,'2013-04-05','10:13','Automático','Inicio do SLA','2013-04-05','2013-04-05',NULL,'{\"idSolicitacaoServico\":4,\"idContatoSolicitacaoServico\":4,\"idServicoContrato\":5,\"idSolicitante\":3,\"idOrigem\":1,\"idPrioridade\":5,\"filtroADPesq\":\"\",\"idUnidade\":1,\"idFaseAtual\":2,\"dataHoraSolicitacao\":\"Apr 5, 2013 10:13:17 AM\",\"prazoCapturaHH\":0,\"prazoCapturaMM\":0,\"prazoHH\":0,\"prazoMM\":0,\"descricao\":\"Descri\\u0026ccedil;\\u0026atilde;o do Incidente ou Requisi\\u0026ccedil;\\u0026atilde;o\\u003cbr /\\u003e\",\"resposta\":\"\",\"dataHoraInicio\":\"Apr 5, 2013 10:13:17 AM\",\"situacao\":\"EmAndamento\",\"seqReabertura\":0,\"enviaEmailCriacao\":\"S\",\"enviaEmailFinalizacao\":\"S\",\"slaACombinar\":\"S\",\"idCalendario\":1,\"tempoDecorridoHH\":0,\"tempoDecorridoMM\":0,\"situacaoSLA\":\"A\",\"dataHoraInicioSLA\":\"Apr 5, 2013 10:13:18 AM\",\"descrSituacao\":\"Em andamento\",\"dataHoraLimite\":\"Apr 5, 2013 10:13:00 AM\",\"dataHoraLimiteStr\":\"05/04/2013 10:13\",\"dataHoraInicioSLAStr\":\"05/04/2013 10:13:18\",\"atrasoSLA\":0.0,\"idContrato\":1,\"solicitante\":\"Consultor\",\"dataHoraSolicitacaoStr\":\"05/04/2013 10:13\",\"usuarioDto\":{\"idUsuario\":2,\"idEmpregado\":2,\"idPerfilAcessoUsuario\":2,\"idEmpresa\":1,\"login\":\"default\",\"nomeUsuario\":\"Default\",\"status\":\"A\",\"grupos\":[\"SDNIVEL1\",\"SDNIVEL2\",\"SDNIVEL3Apli\",\"SDNIVEL3Infra\",\"SDNIVEL3Sist\",\"SDNIVEL3Telefonia\"],\"locale\":\"\",\"colGrupos\":[{\"idGrupo\":2,\"nome\":\"1º NÍVEL\",\"sigla\":\"SDNIVEL1\",\"serviceDesk\":\"S\"},{\"idGrupo\":3,\"nome\":\"2º NÍVEL\",\"sigla\":\"SDNIVEL2\",\"serviceDesk\":\"S\"},{\"idGrupo\":16,\"nome\":\"3º NÍVEL - Aplicação\",\"sigla\":\"SDNIVEL3Apli\",\"serviceDesk\":\"S\"},{\"idGrupo\":4,\"nome\":\"3º NÍVEL - Infraestrutura\",\"sigla\":\"SDNIVEL3Infra\",\"serviceDesk\":\"S\"},{\"idGrupo\":17,\"nome\":\"3º NÍVEL - Sistemas\",\"sigla\":\"SDNIVEL3Sist\",\"serviceDesk\":\"S\"},{\"idGrupo\":19,\"nome\":\"3º NÍVEL - Telefonia\",\"sigla\":\"SDNIVEL3Telefonia\",\"serviceDesk\":\"N\"}]},\"grupoAtual\":\"SDNIVEL2\",\"grupoNivel1\":\"SDNIVEL1\",\"acaoFluxo\":\"\",\"idTipoDemandaServico\":1,\"idServico\":5,\"itemConfiguracao\":\"\",\"messageId\":\"\",\"nomecontato\":\"Consultor\",\"telefonecontato\":\"3242-4433\",\"emailcontato\":\"citsmart_instalador_mysql@centralit.com.br\",\"observacao\":\"\",\"reclassificar\":\"\",\"escalar\":\"\",\"alterarSituacao\":\"\",\"detalhamentoCausa\":\"\",\"solucaoTemporaria\":\"N\",\"nomeServico\":\"Serviço 5\",\"ramal\":\"\",\"idAcordoNivelServico\":3,\"registradoPor\":\"Default\",\"registroexecucao\":\"\",\"impacto\":\"B\",\"urgencia\":\"B\",\"descricaoSemFormatacao\":\"Descrição do Incidente ou Requisição\",\"idResponsavel\":2,\"idGrupoAtual\":3,\"idGrupoNivel1\":2}',NULL,'InicioSLA','O',0,NULL,NULL,NULL);
/*!40000 ALTER TABLE `ocorrenciasolicitacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `opcaorespostaquestionario`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `opcaorespostaquestionario` (
  `idopcaorespostaquestionario` int(11) NOT NULL,
  `idquestaoquestionario` int(11) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `peso` int(11) DEFAULT NULL,
  `valor` varchar(50) DEFAULT NULL,
  `geraalerta` char(1) DEFAULT NULL,
  `exibecomplemento` char(1) DEFAULT NULL,
  `idquestaocomplemento` int(11) DEFAULT NULL,
  PRIMARY KEY (`idopcaorespostaquestionario`),
  KEY `INDEX_OPCAOQUESTAOQUESTIONARIO` (`idquestaoquestionario`),
  KEY `INDEX_OPCOESQUESTAOCOMPLEMENTO` (`idquestaocomplemento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='opcaorespostaquestionario';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `opcaorespostaquestionario`
--

LOCK TABLES `opcaorespostaquestionario` WRITE;
/*!40000 ALTER TABLE `opcaorespostaquestionario` DISABLE KEYS */;
/*!40000 ALTER TABLE `opcaorespostaquestionario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `opiniao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `opiniao` (
  `idopiniao` int(11) NOT NULL AUTO_INCREMENT,
  `idusuario` int(11) DEFAULT NULL,
  `idsolicitacao` int(11) DEFAULT NULL,
  `tipo` varchar(50) DEFAULT NULL,
  `observacoes` varchar(250) DEFAULT NULL,
  `data` date DEFAULT NULL,
  `hora` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idopiniao`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `opiniao`
--

LOCK TABLES `opiniao` WRITE;
/*!40000 ALTER TABLE `opiniao` DISABLE KEYS */;
/*!40000 ALTER TABLE `opiniao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `origematendimento`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `origematendimento` (
  `idorigem` bigint(20) NOT NULL,
  `descricao` varchar(100) NOT NULL,
  PRIMARY KEY (`idorigem`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `origematendimento`
--

LOCK TABLES `origematendimento` WRITE;
/*!40000 ALTER TABLE `origematendimento` DISABLE KEYS */;
INSERT INTO `origematendimento` VALUES (1,'Help Desk'),(2,'Internet'),(3,'E-mail'),(4,'Operador'),(5,'3º Nivel');
/*!40000 ALTER TABLE `origematendimento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `origemocorrencia`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `origemocorrencia` (
  `idorigemocorrencia` int(11) NOT NULL,
  `nome` varchar(256) NOT NULL,
  `dataInicio` date NOT NULL,
  `dataFim` date DEFAULT NULL,
  PRIMARY KEY (`idorigemocorrencia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `origemocorrencia`
--

LOCK TABLES `origemocorrencia` WRITE;
/*!40000 ALTER TABLE `origemocorrencia` DISABLE KEYS */;
/*!40000 ALTER TABLE `origemocorrencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `os`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `os` (
  `idos` int(11) NOT NULL,
  `idcontrato` int(11) NOT NULL,
  `idclassificacaoos` int(11) DEFAULT NULL,
  `idservicocontrato` bigint(20) DEFAULT NULL,
  `numero` varchar(20) DEFAULT NULL,
  `ano` int(11) DEFAULT NULL,
  `datainicio` date NOT NULL,
  `datafim` date NOT NULL,
  `demanda` text NOT NULL,
  `objetivo` text,
  `situacaoos` int(11) DEFAULT NULL,
  `nomearearequisitante` varchar(150) DEFAULT NULL,
  `obsfinalizacao` text,
  `quantidadeglosasanterior` int(11) DEFAULT NULL,
  `quantidade` int(11) DEFAULT NULL,
  `idospai` int(11) DEFAULT NULL,
  KEY `fk_reference_22` (`idcontrato`),
  KEY `fk_reference_24` (`idclassificacaoos`),
  KEY `fk_reference_27` (`idservicocontrato`),
  KEY `fk_reference_619` (`idospai`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `os`
--

LOCK TABLES `os` WRITE;
/*!40000 ALTER TABLE `os` DISABLE KEYS */;
/*!40000 ALTER TABLE `os` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `osatividadeperiodica`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `osatividadeperiodica` (
  `idatividadeperiodica` int(11) DEFAULT NULL,
  `idos` int(11) DEFAULT NULL,
  KEY `fk_reference_579` (`idatividadeperiodica`),
  KEY `fk_reference_580` (`idos`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `osatividadeperiodica`
--

LOCK TABLES `osatividadeperiodica` WRITE;
/*!40000 ALTER TABLE `osatividadeperiodica` DISABLE KEYS */;
/*!40000 ALTER TABLE `osatividadeperiodica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pais`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pais` (
  `idpais` int(11) NOT NULL,
  `nomepais` varchar(200) NOT NULL,
  PRIMARY KEY (`idpais`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pais`
--

LOCK TABLES `pais` WRITE;
/*!40000 ALTER TABLE `pais` DISABLE KEYS */;
INSERT INTO `pais` VALUES (1,'Brasil');
/*!40000 ALTER TABLE `pais` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `palavragemea`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `palavragemea` (
  `idpalavragemea` int(11) NOT NULL,
  `palavra` varchar(256) NOT NULL,
  `palavracorrespondente` varchar(256) NOT NULL,
  PRIMARY KEY (`idpalavragemea`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `palavragemea`
--

LOCK TABLES `palavragemea` WRITE;
/*!40000 ALTER TABLE `palavragemea` DISABLE KEYS */;
/*!40000 ALTER TABLE `palavragemea` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parametrocorpore`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parametrocorpore` (
  `idparametrocorpore` int(11) NOT NULL,
  `nomeparametrocorpore` varchar(200) NOT NULL,
  `valor` varchar(200) NOT NULL,
  `idempresa` int(11) NOT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  `tipodado` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idparametrocorpore`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='parametrocorpore';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parametrocorpore`
--

LOCK TABLES `parametrocorpore` WRITE;
/*!40000 ALTER TABLE `parametrocorpore` DISABLE KEYS */;
INSERT INTO `parametrocorpore` VALUES (0,'ORIGEM - Configuração de Origem de Informações (para federação de dados)',' ',1,'2013-04-08',NULL,NULL),(1,'Diretorio Arquivo NetMap',' ',1,'2013-04-08',NULL,NULL),(2,'Faixa de Ip',' ',1,'2013-04-08',NULL,NULL),(3,'No Pesquisa',' ',1,'2013-04-08',NULL,NULL),(4,'Atributo Pesquisa',' ',1,'2013-04-08',NULL,NULL),(5,'CAMINHO_INSTALADORES',' ',1,'2013-04-08',NULL,NULL),(6,'Diretorio XML Agente',' ',1,'2013-04-08',NULL,NULL),(7,'Periodo para fazer inventario dias',' ',1,'2013-04-08',NULL,NULL),(8,'Caminho Nmap',' ',1,'2013-04-08',NULL,NULL),(9,'ID Grupo Nível 1',' ',1,'2013-04-08',NULL,NULL),(10,'Email origem das notificações de solicitações de serviço',' ',1,'2013-04-08',NULL,NULL),(11,'Exige autenticação para envio de email',' ',1,'2013-04-08',NULL,NULL),(12,'Usuário para autenticação de email',' ',1,'2013-04-08',NULL,NULL),(13,'Senha para autenticação de email',' ',1,'2013-04-08',NULL,NULL),(14,'SMTP para envio de email',' ',1,'2013-04-08',NULL,NULL),(15,'GED Interno (S/N)',' ',1,'2013-04-08',NULL,NULL),(16,'GED Interno em Banco de Dados (S/N)',' ',1,'2013-04-08',NULL,NULL),(17,'GED Externo - Classe de tratamento',' ',1,'2013-04-08',NULL,NULL),(18,'GED Diretorio',' ',1,'2013-04-08',NULL,NULL),(19,'Nome da Empresa',' ',1,'2013-04-08',NULL,NULL),(20,'BASE CONHECIMENTO - Pasta de armazenamento',' ',1,'2013-04-08',NULL,NULL),(21,'BASECONHECIMENTO - Pasta de armazenamento arquivos LUCENE',' ',1,'2013-04-08',NULL,NULL),(22,'MÉTODO DE AUTENTICAÇÃO (1 - Próprio, 2 - LDAP)',' ',1,'2013-04-08',NULL,NULL),(23,'SMTP LEITURA - Servidor de entrada de emails do Service Desk',' ',1,'2013-04-08',NULL,NULL),(24,'SMTP LEITURA - Caixa de entrada de emails do Service Desk',' ',1,'2013-04-08',NULL,NULL),(25,'SMTP LEITURA - Senha da Caixa de entrada de emails do Service Desk',' ',1,'2013-04-08',NULL,NULL),(26,'SMTP LEITURA - Provider do servidor de emails do Service Desk (imaps, pops, imap, pop, etc)',' ',1,'2013-04-08',NULL,NULL),(27,'SMTP LEITURA - Porta do servidor de emails do Service Desk',' ',1,'2013-04-08',NULL,NULL),(28,'SMTP LEITURA - Pasta da caixa de entrada de emails do Service Desk',' ',1,'2013-04-08',NULL,NULL),(29,'Nome do fluxo padrão para serviços',' ',1,'2013-04-08',NULL,NULL),(30,'ID da fase padrão para execução de serviços',' ',1,'2013-04-08',NULL,NULL),(31,'Envia email na execução dos fluxos de solicitações/incidentes',' ',1,'2013-04-08',NULL,NULL),(32,'DB - nome do SCHEMA do Banco de dados',' ',1,'2013-04-08',NULL,NULL),(33,'URL de acesso ao sistema',' ',1,'2013-04-08',NULL,NULL),(34,'LDAP - URL de acesso ao ActiveDirectory. Exemplo: ldap://10.2.1.2:389',' ',1,'2013-04-08',NULL,NULL),(35,'LDAP - String com dominio do AD. Exemplo: dc=empresa,dc=com,dc=br',' ',1,'2013-04-08',NULL,NULL),(36,'Domínio de e-mail. (@empresa.com.br)',' ',1,'2013-04-08',NULL,NULL),(37,'LDAP - Login para consultar informações de usuários do LDAP(deve-se cadastrar também a senha).',' ',1,'2013-04-08',NULL,NULL),(38,'LDAP - Senha para consultar informações de usuários do LDAP(deve-se cadastrar também o login).',' ',1,'2013-04-08',NULL,NULL),(39,'LDAP - Id do perfil de acesso que será setado automaticamente caso o usuário não possua nenhum.',' ',1,'2013-04-08',NULL,NULL),(40,'Faz o controle de acessos do catalogo de serviço por unidade (S/N) ?',' ',1,'2013-04-08',NULL,NULL),(41,'Faz o controle de vinculo de colaboradores aos contratos (S/N) ?',' ',1,'2013-04-08',NULL,NULL),(42,'Pagina para cadastro de solicitações de serviço (será usado o padrão caso não seja informado)',' ',1,'2013-04-08',NULL,NULL),(43,'LDAP - Sufixo dominio',' ',1,'2013-04-08',NULL,NULL),(44,'Diretório Upload repositório path',' ',1,'2013-04-08',NULL,NULL),(45,'LDAP - ID Grupo Padrão.',' ',1,'2013-04-08',NULL,NULL),(46,'Página de Login usando o Portal',' ',1,'2013-04-08',NULL,NULL),(47,'Nome do fluxo padrão para mudanças',' ',1,'2013-04-08',NULL,NULL),(48,'Validar permissões de botões (gravar, excluir e pesquisar) (S/N).',' ',1,'2013-04-08',NULL,NULL),(49,'Gravar nome Usuário e Nome Empregado a partir do Last Name? (S/N).',' ',1,'2013-04-08',NULL,NULL),(50,'Aceitar Valor 0 (Zero) para quantidade e custo da atividade?',' ',1,'2013-04-08',NULL,NULL),(51,'FORMULA GLOSA OS - Código Padrão',' ',1,'2013-04-08',NULL,NULL),(52,'Ativar log no sistema (\'true\' ou \'false\')',' ',1,'2013-04-08',NULL,NULL),(53,'Tipos: \'CIT_LOG\' (arquivo de log), \'DB_LOG\' (grava no banco)',' ',1,'2013-04-08',NULL,NULL),(54,'Caminho da pasta que ficará o arquivo de LOG',' ',1,'2013-04-08',NULL,NULL),(55,'Nome do arquivo de log',' ',1,'2013-04-08',NULL,NULL),(56,'Extensão do arquivo de log',' ',1,'2013-04-08',NULL,NULL),(57,'ID do modelo de e-mail que será enviado para o grupo de destino ao escalar uma solicitação.',' ',1,'2013-04-08',NULL,NULL),(58,'Notificar o grupo ao receber uma escalação de solicitação de serviço (S e N).',' ',1,'2013-04-08',NULL,NULL),(59,'PATRIMÔNIOP - ID Tipo Item Configuração.',' ',1,'2013-04-08',NULL,NULL),(60,'Grupo de novos itens de configuração.',' ',1,'2013-04-08',NULL,NULL),(61,'Vincula contratos a unidade',' ',1,'2013-04-08',NULL,NULL),(62,'Identifica o id do serviço para solicitações de serviços e incidentes (Ex: A Ser Classificado )',' ',1,'2013-04-08',NULL,NULL),(63,'[Parametro Depreciado] Pagina para cadastro de solicitações de serviço pelo Portal (Default: \'/pages/solicitacaoServicoPortal/solicitacaoServicoPortal.load\')',' ',1,'2013-04-08',NULL,NULL),(64,'LDAP - Atributo LDAP para obter NOME DO COLABORADOR? (DisplayName, CN, SN ...).',' ',1,'2013-04-08',NULL,NULL),(65,'Identifica o id de origem do chamado padrão da solicitação de serviço ',' ',1,'2013-04-08',NULL,NULL),(66,'Idioma padrão do sistema(Ex: EN=Inglês)?',' ',1,'2013-04-08',NULL,NULL),(67,'LDAP - Filtro LDAP na busca? (Default: (&(objectCategory=person)(objectClass=user))).',' ',1,'2013-04-08',NULL,NULL),(68,'LDAP - Mostrar botão de buscar no AD na tela de incidentes.(Default: \'N\')',' ',1,'2013-04-08',NULL,NULL),(69,'Validar campos Causa e Categoria da Solução da página solicitação serviço obrigatorios(Default: N)',' ',1,'2013-04-08',NULL,NULL),(70,'Mostrar botões de importação e exportação do xml no cadastro de menus. (Default: S)',' ',1,'2013-04-08',NULL,NULL),(71,'Parâmetro para ler o arquivo XML padrão de menus. (Default: N)',' ',1,'2013-04-08',NULL,NULL),(72,'SMTP LEITURA - Limite de emails carregados em Solicitação Serviço',' ',1,'2013-04-08',NULL,NULL),(73,'Avisar, com antecedência, a quantidade de dias que restam para a expiração da licença. (Default: 90 dias)',' ',1,'2013-04-08',NULL,NULL),(74,'Enviar e-mail para grupo ou proprietário do item configuração. (1: Grupo 2: proprietário).',' ',1,'2013-04-08',NULL,NULL),(75,'ID do modelo de e-mail para envio de notificação de licença próximo da expiração',' ',1,'2013-04-08',NULL,NULL),(76,'Nome do Domínio da Rede (exemplo: EMPRESA, CORPORACAO)',' ',1,'2013-04-08',NULL,NULL),(78,'Avisar, com antecedência, a quantidade de dias que restam para a expiração do conhecimento.  (Default: 90 dias)',' ',1,'2013-04-08',NULL,NULL),(79,'ID do modelo de e-mail para envio de notificação de criação de pasta',' ',1,'2013-04-08',NULL,NULL),(80,'ID do modelo de e-mail para envio de notificação de atualização de pasta',' ',1,'2013-04-08',NULL,NULL),(81,'ID do modelo de e-mail para envio de notificação de exclusão de pasta',' ',1,'2013-04-08',NULL,NULL),(82,'ID do modelo de e-mail para envio de notificação de criação de conhecimento',' ',1,'2013-04-08',NULL,NULL),(83,'ID do modelo de e-mail para envio de notificação de atualização de conhecimento',' ',1,'2013-04-08',NULL,NULL),(84,'ID do modelo de e-mail para envio de notificação de exclusão de conhecimento',' ',1,'2013-04-08',NULL,NULL),(85,'Verificação de vinculo de mudança relacionada ao Item de configuração (Default: S)',' ',1,'2013-04-08',NULL,NULL),(86,'Mostrar Categoria de serviço em Incidente default(S)',' ',1,'2013-04-08',NULL,NULL),(87,'Modelo de criação de IC',' ',1,'2013-04-08',NULL,NULL),(88,'Modelo de alteração de IC)',' ',1,'2013-04-08',NULL,NULL),(89,'Modelo de alteração de IC de grupo',' ',1,'2013-04-08',NULL,NULL),(90,'Envio de e-mails de Notificação de ICs (1-Grupo, 2-Proprietario, 3-Todos) (Default: 1)',' ',1,'2013-04-08',NULL,NULL),(91,'SMTP GMAIL - Envio e recebimento de e-mails utilizando GMAIL? (S e N)',' ',1,'2013-04-08',NULL,NULL),(92,'Nome fase ciclo de vida desenvolvimento Item Configuração (Default: ICs em Desenvolvimento)',' ',1,'2013-04-08',NULL,NULL),(93,'Nome fase ciclo de vida produção Item Configuração (Default: ICs em Produção',' ',1,'2013-04-08',NULL,NULL),(94,'Nome fase ciclo de vida homologação Item Configuração (Default: ICs em Homologação)',' ',1,'2013-04-08',NULL,NULL),(95,'Nome Inventário (Default: Inventário)',' ',1,'2013-04-08',NULL,NULL),(96,'ID do grupo de desenvolvimento Item Configuração (Ex: 1)',' ',1,'2013-04-08',NULL,NULL),(97,'ID do grupo de produção Item Configuração (Ex: 2)',' ',1,'2013-04-08',NULL,NULL),(98,'ID do grupo de homologação Item Configuração (Ex: 3)',' ',1,'2013-04-08',NULL,NULL),(99,'ID do grupo de inventario Item Configuração (Ex: 4)',' ',1,'2013-04-08',NULL,NULL),(100,'ID do grupo padrão para atendimento de requisições de produtos',' ',1,'2013-04-08',NULL,NULL),(101,'Percentual máximo de variação de preço para cotação',' ',1,'2013-04-08',NULL,NULL),(102,'LDAP - Número máximo de colaboradores retornados na rotina de sincronização com AD? ',' ',1,'2013-04-08',NULL,NULL),(103,'ID do modelo de e-mail para envio de notificação de alteracao de serviço',' ',1,'2013-04-08',NULL,NULL),(104,'Calcular a prioridade da solicitação dinamicamente usando a fórmula cadastrada. (Default: \'N\')',' ',1,'2013-04-08',NULL,NULL),(105,'ID da origem que será setado como padrão ao criar um novo incidente',' ',1,'2013-04-08',NULL,NULL),(106,'Determina automaticamente a urgência e impacto das requisições de produto',' ',1,'2013-04-08',NULL,NULL),(107,'Peso padrão para critério PREÇO na cotação',' ',1,'2013-04-08',NULL,NULL),(108,'Peso padrão para critério PRAZO DE ENTREGA na cotação',' ',1,'2013-04-08',NULL,NULL),(109,'Peso padrão para critério PRAZO DE PAGAMENTO na cotação',' ',1,'2013-04-08',NULL,NULL),(110,'Peso padrão para critério PRAZO DE GARANTIA na cotação',' ',1,'2013-04-08',NULL,NULL),(111,'Peso padrão para critério TAXA DE JUROS na cotação',' ',1,'2013-04-08',NULL,NULL),(112,'Path do Arquivo de Status do Nagios',' ',1,'2013-04-08',NULL,NULL),(113,'ID do modelo de e-mail para escalação automática',' ',1,'2013-04-08',NULL,NULL),(114,'Permite que um empregado da área de compras faça requisição de produto',' ',1,'2013-04-08',NULL,NULL),(115,'Tirar vinculo de localidade física com unidade ?(S/N)(Default: N)',' ',1,'2013-04-08',NULL,NULL),(116,'ID do modelo de e-mail para alteração da senha de acesso ao CITSmart',' ',1,'2013-04-08',NULL,NULL),(117,'Informar o caminho para salvar a tabela LOGDADOS',' ',1,'2013-04-08',NULL,NULL),(118,'[Portal] ID do Contrato padrão para abertura de chamados.',' ',1,'2013-04-08',NULL,NULL),(119,'Tipo de captura de solicitações/Incidentes (1-Na execução, 2-No direcionamento) (Default: 1)',' ',1,'2013-04-08',NULL,NULL),(120,'ID do modelo de e-mail para envio de notificação para o comitê de consultivo de mudanças',' ',1,'2013-04-08',NULL,NULL),(121,'Faz o controle de Configuração para o openLDAP (S/N)(Default: N)',' ',1,'2013-04-08',NULL,NULL),(122,'ID do modelo de e-mail que será enviado para o grupo de destino ao escalar uma requisição mudança',' ',1,'2013-04-08',NULL,NULL);
/*!40000 ALTER TABLE `parametrocorpore` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parametros`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parametros` (
  `nomeparametro` varchar(70) NOT NULL,
  `idempresa` int(11) NOT NULL,
  `modulo` varchar(200) DEFAULT NULL,
  `valor` varchar(200) DEFAULT NULL,
  `detalhamento` varchar(200) DEFAULT NULL,
  KEY `INDEX_PARAMETROSEMPRESA` (`idempresa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='parametros';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parametros`
--

LOCK TABLES `parametros` WRITE;
/*!40000 ALTER TABLE `parametros` DISABLE KEYS */;
/*!40000 ALTER TABLE `parametros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parametroscontrato`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parametroscontrato` (
  `idcontrato` int(11) NOT NULL,
  `nomeparametro` varchar(100) NOT NULL,
  `valorparametro` text,
  PRIMARY KEY (`idcontrato`,`nomeparametro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parametroscontrato`
--

LOCK TABLES `parametroscontrato` WRITE;
/*!40000 ALTER TABLE `parametroscontrato` DISABLE KEYS */;
/*!40000 ALTER TABLE `parametroscontrato` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parecer`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parecer` (
  `idparecer` int(11) NOT NULL,
  `idjustificativa` int(11) DEFAULT NULL,
  `idalcada` int(11) DEFAULT NULL,
  `idresponsavel` int(11) NOT NULL,
  `datahoraparecer` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `complementojustificativa` text,
  `aprovado` char(1) NOT NULL,
  `observacoes` text,
  PRIMARY KEY (`idparecer`),
  KEY `fk_reference_35` (`idjustificativa`),
  KEY `fk_reference_650` (`idalcada`),
  CONSTRAINT `fk_reference_35` FOREIGN KEY (`idjustificativa`) REFERENCES `justificativaparecer` (`idjustificativa`),
  CONSTRAINT `fk_reference_650` FOREIGN KEY (`idalcada`) REFERENCES `alcada` (`idalcada`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parecer`
--

LOCK TABLES `parecer` WRITE;
/*!40000 ALTER TABLE `parecer` DISABLE KEYS */;
/*!40000 ALTER TABLE `parecer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pasta`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pasta` (
  `idpasta` int(11) NOT NULL,
  `nome` varchar(256) DEFAULT NULL,
  `datainicio` date DEFAULT NULL,
  `datafim` date DEFAULT NULL,
  `idpastapai` int(11) DEFAULT NULL,
  `idnotificacao` int(11) DEFAULT NULL,
  `herdapermissoes` char(1) DEFAULT NULL,
  PRIMARY KEY (`idpasta`),
  KEY `INDEX_PASTAPAI` (`idpastapai`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='pasta';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pasta`
--

LOCK TABLES `pasta` WRITE;
/*!40000 ALTER TABLE `pasta` DISABLE KEYS */;
INSERT INTO `pasta` VALUES (1,'Pasta 1',NULL,NULL,NULL,NULL,NULL),(2,'Pasta 2',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `pasta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedidocompra`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pedidocompra` (
  `idpedido` int(11) NOT NULL,
  `idempresa` int(11) NOT NULL,
  `idfornecedor` bigint(20) NOT NULL,
  `datapedido` date NOT NULL,
  `dataprevistaentrega` date DEFAULT NULL,
  `numeropedido` varchar(25) NOT NULL,
  `identificacaoentrega` varchar(25) DEFAULT NULL,
  `valorfrete` decimal(8,2) DEFAULT NULL,
  `valorseguro` decimal(8,2) DEFAULT NULL,
  `numeronf` varchar(25) DEFAULT NULL,
  `outrasdespesas` decimal(8,2) DEFAULT NULL,
  `situacao` varchar(20) NOT NULL,
  `idcotacao` int(11) DEFAULT NULL,
  `idenderecoentrega` int(11) DEFAULT NULL,
  `dataentrega` date DEFAULT NULL,
  `observacoes` text,
  PRIMARY KEY (`idpedido`),
  KEY `fk_reference_638` (`idfornecedor`),
  KEY `fk_reference_706` (`idcotacao`),
  KEY `fk_reference_707` (`idenderecoentrega`),
  CONSTRAINT `fk_reference_638` FOREIGN KEY (`idfornecedor`) REFERENCES `fornecedor` (`idfornecedor`),
  CONSTRAINT `fk_reference_706` FOREIGN KEY (`idcotacao`) REFERENCES `cotacao` (`idcotacao`),
  CONSTRAINT `fk_reference_707` FOREIGN KEY (`idenderecoentrega`) REFERENCES `endereco` (`idendereco`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedidocompra`
--

LOCK TABLES `pedidocompra` WRITE;
/*!40000 ALTER TABLE `pedidocompra` DISABLE KEYS */;
/*!40000 ALTER TABLE `pedidocompra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `perfilacesso`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `perfilacesso` (
  `idperfil` int(11) NOT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  `nome` varchar(256) NOT NULL,
  `acessosistemacitsmart` char(1 char)  DEFAULT 'S',
  PRIMARY KEY (`idperfil`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='perfilacesso';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfilacesso`
--

LOCK TABLES `perfilacesso` WRITE;
/*!40000 ALTER TABLE `perfilacesso` DISABLE KEYS */;
INSERT INTO `perfilacesso` VALUES (1,'2012-01-01',NULL,'Administrador'),(2,'2013-03-18',NULL,'Atendimento 1º NÍVEL'),(3,'2012-01-01',NULL,'Supervisão'),(4,'2012-01-01',NULL,'Qualidade'),(5,'2012-01-01',NULL,'Coordenação'),(6,'2012-01-01',NULL,'Usuário'),(7,'2012-01-01',NULL,'Desenvolvimento'),(15,'2012-01-01',NULL,'Atendimento 3º NÍVEL'),(18,'2012-01-01',NULL,'Atendimento 2º NÍVEL');
/*!40000 ALTER TABLE `perfilacesso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `perfilacessogrupo`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `perfilacessogrupo` (
  `idperfil` int(11) NOT NULL,
  `idgrupo` int(11) NOT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  PRIMARY KEY (`idperfil`,`idgrupo`,`datainicio`),
  KEY `INDEX_PERFILGRUPO` (`idgrupo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='perfilacessogrupo';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfilacessogrupo`
--

LOCK TABLES `perfilacessogrupo` WRITE;
/*!40000 ALTER TABLE `perfilacessogrupo` DISABLE KEYS */;
INSERT INTO `perfilacessogrupo` VALUES (1,2,'2012-01-01',NULL),(4,5,'2012-01-01',NULL),(5,6,'2012-01-01',NULL),(5,7,'2012-01-01',NULL),(5,18,'2012-01-01',NULL),(5,20,'2012-01-01',NULL),(7,1,'2012-01-01',NULL),(15,4,'2012-01-01',NULL),(15,16,'2012-01-01',NULL),(15,17,'2012-01-01',NULL),(15,19,'2012-01-01',NULL),(18,3,'2012-01-01',NULL);
/*!40000 ALTER TABLE `perfilacessogrupo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `perfilacessomenu`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `perfilacessomenu` (
  `idperfilacesso` int(11) DEFAULT NULL,
  `idmenu` int(11) DEFAULT NULL,
  `pesquisa` char(1) NOT NULL,
  `grava` char(1) NOT NULL,
  `deleta` char(1) NOT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  KEY `INDEX_ACESSOMENU2` (`datafim`),
  KEY `FK_Reference_138` (`idperfilacesso`),
  KEY `FK_Reference_139` (`idmenu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='perfilacessomenu';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfilacessomenu`
--

LOCK TABLES `perfilacessomenu` WRITE;
/*!40000 ALTER TABLE `perfilacessomenu` DISABLE KEYS */;
/*!40000 ALTER TABLE `perfilacessomenu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `perfilacessopasta`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `perfilacessopasta` (
  `idperfil` int(11) NOT NULL,
  `idpasta` int(11) NOT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  `aprovabaseconhecimento` char(1) DEFAULT NULL,
  `permiteleitura` char(1) DEFAULT NULL,
  `permiteleituragravacao` char(1) DEFAULT NULL,
  PRIMARY KEY (`idperfil`,`idpasta`),
  KEY `INDEX_PERFILPASTA` (`idpasta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='perfilacessopasta';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfilacessopasta`
--

LOCK TABLES `perfilacessopasta` WRITE;
/*!40000 ALTER TABLE `perfilacessopasta` DISABLE KEYS */;
INSERT INTO `perfilacessopasta` VALUES (1,1,'2013-04-05',NULL,'S','N','S'),(1,2,'2013-04-05',NULL,'S','N','S'),(1,3,'2013-04-05',NULL,'S','N','S'),(1,4,'2013-04-05',NULL,'S','N','S'),(2,1,'2013-04-05',NULL,'N','S','N'),(2,2,'2013-04-05',NULL,'N','S','N'),(2,3,'2013-04-05',NULL,'N','S','N'),(2,4,'2013-04-05',NULL,'N','S','N'),(3,1,'2013-04-05',NULL,'S','N','S'),(3,2,'2013-04-05',NULL,'S','N','S'),(3,3,'2013-04-05',NULL,'S','N','S'),(3,4,'2013-04-05',NULL,'S','N','S'),(4,1,'2013-04-05',NULL,'N','S','N'),(4,2,'2013-04-05',NULL,'N','S','N'),(4,3,'2013-04-05',NULL,'N','S','N'),(4,4,'2013-04-05',NULL,'N','S','N'),(5,1,'2013-04-05',NULL,'S','N','S'),(5,2,'2013-04-05',NULL,'S','N','S'),(5,3,'2013-04-05',NULL,'S','N','S'),(5,4,'2013-04-05',NULL,'S','N','S'),(6,1,'2013-04-05',NULL,'N','S','N'),(6,2,'2013-04-05',NULL,'N','S','N'),(6,3,'2013-04-05',NULL,'N','S','N'),(6,4,'2013-04-05',NULL,'N','S','N'),(7,1,'2013-04-05',NULL,'N','S','N'),(7,2,'2013-04-05',NULL,'N','S','N'),(7,3,'2013-04-05',NULL,'N','S','N'),(7,4,'2013-04-05',NULL,'N','S','N'),(15,1,'2013-04-05',NULL,'N','S','N'),(15,2,'2013-04-05',NULL,'N','S','N'),(15,3,'2013-04-05',NULL,'N','S','N'),(15,4,'2013-04-05',NULL,'N','S','N'),(18,1,'2013-04-05',NULL,'N','S','N'),(18,2,'2013-04-05',NULL,'N','S','N'),(18,3,'2013-04-05',NULL,'N','S','N'),(18,4,'2013-04-05',NULL,'N','S','N');
/*!40000 ALTER TABLE `perfilacessopasta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `perfilacessosituacaofatura`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `perfilacessosituacaofatura` (
  `idperfil` int(11) NOT NULL,
  `situacaofatura` char(1) NOT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  PRIMARY KEY (`idperfil`,`situacaofatura`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfilacessosituacaofatura`
--

LOCK TABLES `perfilacessosituacaofatura` WRITE;
/*!40000 ALTER TABLE `perfilacessosituacaofatura` DISABLE KEYS */;
INSERT INTO `perfilacessosituacaofatura` (`idperfil`,`situacaofatura`,`datainicio`,`datafim`) VALUES (1,1,'2012-09-04',NULL);
INSERT INTO `perfilacessosituacaofatura` (`idperfil`,`situacaofatura`,`datainicio`,`datafim`) VALUES (1,2,'2012-09-04',NULL);
INSERT INTO `perfilacessosituacaofatura` (`idperfil`,`situacaofatura`,`datainicio`,`datafim`) VALUES (1,3,'2012-09-04',NULL);
INSERT INTO `perfilacessosituacaofatura` (`idperfil`,`situacaofatura`,`datainicio`,`datafim`) VALUES (1,4,'2012-09-04',NULL);
INSERT INTO `perfilacessosituacaofatura` (`idperfil`,`situacaofatura`,`datainicio`,`datafim`) VALUES (1,5,'2012-09-04',NULL);
INSERT INTO `perfilacessosituacaofatura` (`idperfil`,`situacaofatura`,`datainicio`,`datafim`) VALUES (1,6,'2012-09-04',NULL);
INSERT INTO `perfilacessosituacaofatura` (`idperfil`,`situacaofatura`,`datainicio`,`datafim`) VALUES (1,7,'2012-09-04',NULL);
/*!40000 ALTER TABLE `perfilacessosituacaofatura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `perfilacessosituacaoos`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `perfilacessosituacaoos` (
  `idperfil` int(11) NOT NULL,
  `situacaoos` int(11) NOT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  PRIMARY KEY (`idperfil`,`situacaoos`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfilacessosituacaoos`
--

LOCK TABLES `perfilacessosituacaoos` WRITE;
/*!40000 ALTER TABLE `perfilacessosituacaoos` DISABLE KEYS */;
INSERT INTO `perfilacessosituacaoos` (`idperfil`,`situacaoos`,`datainicio`,`datafim`) VALUES (1,1,'2012-09-04',NULL);
INSERT INTO `perfilacessosituacaoos` (`idperfil`,`situacaoos`,`datainicio`,`datafim`) VALUES (1,2,'2012-09-04',NULL);
INSERT INTO `perfilacessosituacaoos` (`idperfil`,`situacaoos`,`datainicio`,`datafim`) VALUES (1,3,'2012-09-04',NULL);
INSERT INTO `perfilacessosituacaoos` (`idperfil`,`situacaoos`,`datainicio`,`datafim`) VALUES (1,4,'2012-09-04',NULL);
INSERT INTO `perfilacessosituacaoos` (`idperfil`,`situacaoos`,`datainicio`,`datafim`) VALUES (1,5,'2012-09-04',NULL);
INSERT INTO `perfilacessosituacaoos` (`idperfil`,`situacaoos`,`datainicio`,`datafim`) VALUES (1,6,'2012-09-04',NULL);
INSERT INTO `perfilacessosituacaoos` (`idperfil`,`situacaoos`,`datainicio`,`datafim`) VALUES (1,7,'2012-09-04',NULL);
/*!40000 ALTER TABLE `perfilacessosituacaoos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `perfilacessousuario`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `perfilacessousuario` (
  `datainicio` date NOT NULL,
  `idusuario` int(11) NOT NULL,
  `idperfil` int(11) NOT NULL,
  `datafim` date DEFAULT NULL,
  PRIMARY KEY (`idusuario`,`idperfil`),
  KEY `INDEX_PERFILUSUARIO` (`idusuario`),
  KEY `INDEX_PERFIL` (`idperfil`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='perfilacessousuario';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfilacessousuario`
--

LOCK TABLES `perfilacessousuario` WRITE;
/*!40000 ALTER TABLE `perfilacessousuario` DISABLE KEYS */;
INSERT INTO `perfilacessousuario` VALUES ('2012-01-01',1,1,NULL),('2012-01-01',2,2,NULL),('2012-01-01',3,1,NULL);
/*!40000 ALTER TABLE `perfilacessousuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `perfilseguranca`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `perfilseguranca` (
  `idperfilseguranca` int(11) NOT NULL,
  `nomeperfilseguranca` varchar(15) NOT NULL,
  `descperfilseguranca` varchar(70) DEFAULT NULL,
  PRIMARY KEY (`idperfilseguranca`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='perfilseguranca';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfilseguranca`
--

LOCK TABLES `perfilseguranca` WRITE;
/*!40000 ALTER TABLE `perfilseguranca` DISABLE KEYS */;
/*!40000 ALTER TABLE `perfilseguranca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permissoesfluxo`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permissoesfluxo` (
  `idgrupo` int(11) NOT NULL,
  `idtipofluxo` int(11) NOT NULL,
  `criar` char(1) NOT NULL,
  `executar` char(1) NOT NULL,
  `delegar` char(1) NOT NULL,
  `suspender` char(1) NOT NULL,
  `reativar` char(1) DEFAULT NULL,
  `alterarsla` char(1) DEFAULT NULL,
  `reabrir` char(1) DEFAULT NULL,
  PRIMARY KEY (`idgrupo`,`idtipofluxo`),
  KEY `fk_reference_108` (`idtipofluxo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissoesfluxo`
--

LOCK TABLES `permissoesfluxo` WRITE;
/*!40000 ALTER TABLE `permissoesfluxo` DISABLE KEYS */;
INSERT INTO `permissoesfluxo` VALUES (1,1,'N','N','N','N','N','N','N'),(1,6,'N','N','N','N','N','N','N'),(1,7,'N','N','N','N','N','N','N'),(1,8,'N','N','N','N','N','N','N'),(2,1,'S','S','S','N','N','N','S'),(2,6,'N','N','N','N','N','N','N'),(2,7,'N','N','N','N','N','N','N'),(2,8,'N','N','N','N','N','N','N'),(3,1,'S','S','S','S','S','S','S'),(3,6,'N','N','N','N','N','N','N'),(3,7,'N','N','N','N','N','N','N'),(3,8,'N','N','N','N','N','N','N'),(4,1,'N','N','N','N','N','N','N'),(4,6,'N','N','N','N','N','N','N'),(4,7,'N','N','N','N','N','N','N'),(4,8,'N','N','N','N','N','N','N'),(5,1,'N','N','N','N','N','N','N'),(5,6,'N','N','N','N','N','N','N'),(5,7,'N','N','N','N','N','N','N'),(5,8,'N','N','N','N','N','N','N'),(6,1,'N','N','N','N','N','N','N'),(6,6,'N','N','N','N','N','N','N'),(6,7,'N','N','N','N','N','N','N'),(6,8,'N','N','N','N','N','N','N'),(7,1,'N','N','N','N','N','N','N'),(7,6,'N','N','N','N','N','N','N'),(7,7,'N','N','N','N','N','N','N'),(7,8,'N','N','N','N','N','N','N'),(16,1,'S','S','S','S','S','N','S'),(16,6,'N','N','N','N','N','N','N'),(16,7,'N','N','N','N','N','N','N'),(16,8,'N','N','N','N','N','N','N'),(17,1,'S','S','S','S','S','N','S'),(17,6,'N','N','N','N','N','N','N'),(17,7,'N','N','N','N','N','N','N'),(17,8,'N','N','N','N','N','N','N'),(18,1,'N','N','N','N','N','N','N'),(18,6,'N','N','N','N','N','N','N'),(18,7,'N','N','N','N','N','N','N'),(18,8,'N','N','N','N','N','N','N'),(19,1,'N','N','N','N','N','N','N'),(19,6,'N','N','N','N','N','N','N'),(19,7,'N','N','N','N','N','N','N'),(19,8,'N','N','N','N','N','N','N'),(20,1,'N','N','N','N','N','N','N'),(20,6,'N','N','N','N','N','N','N'),(20,7,'N','N','N','N','N','N','N'),(20,8,'N','N','N','N','N','N','N');
/*!40000 ALTER TABLE `permissoesfluxo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pesquisasatisfacao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pesquisasatisfacao` (
  `idpesquisasatisfacao` int(11) NOT NULL,
  `idsolicitacaoservico` bigint(20) NOT NULL,
  `nota` int(11) NOT NULL,
  `comentario` text,
  PRIMARY KEY (`idpesquisasatisfacao`),
  KEY `fk_reference_542` (`idsolicitacaoservico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='latin1_swedish_ci';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pesquisasatisfacao`
--

LOCK TABLES `pesquisasatisfacao` WRITE;
/*!40000 ALTER TABLE `pesquisasatisfacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `pesquisasatisfacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pessoa`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pessoa` (
  `tokenidentificacao` varchar(20) NOT NULL,
  `nome` varchar(200) DEFAULT NULL,
  `cpf` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`tokenidentificacao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pessoa`
--

LOCK TABLES `pessoa` WRITE;
/*!40000 ALTER TABLE `pessoa` DISABLE KEYS */;
/*!40000 ALTER TABLE `pessoa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `planomelhoria`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `planomelhoria` (
  `idplanomelhoria` int(11) NOT NULL,
  `idfornecedor` int(11) DEFAULT NULL,
  `idcontrato` int(11) DEFAULT NULL,
  `titulo` varchar(100) NOT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  `objetivo` text,
  `visaogeral` text,
  `escopo` text,
  `visao` text,
  `missao` text,
  `datacriacao` date DEFAULT NULL,
  `notas` text,
  `criadopor` varchar(40) DEFAULT NULL,
  `modificadopor` varchar(40) DEFAULT NULL,
  `ultmodificacao` date DEFAULT NULL,
  `situacao` char(1) DEFAULT NULL,
  PRIMARY KEY (`idplanomelhoria`),
  KEY `ix_pm_forn` (`idfornecedor`),
  KEY `ix_pm_contrato` (`idcontrato`,`idfornecedor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `planomelhoria`
--

LOCK TABLES `planomelhoria` WRITE;
/*!40000 ALTER TABLE `planomelhoria` DISABLE KEYS */;
/*!40000 ALTER TABLE `planomelhoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `portal`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `portal` (
  `idportal` int(11) NOT NULL,
  `iditem` int(11) DEFAULT NULL,
  `posicaox` double DEFAULT NULL,
  `posicaoy` double DEFAULT NULL,
  `idusuario` int(11) NOT NULL,
  `largura` double DEFAULT NULL,
  `altura` double DEFAULT NULL,
  `data` date DEFAULT NULL,
  `hora` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `coluna` int(11) DEFAULT NULL,
  PRIMARY KEY (`idportal`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `portal`
--

LOCK TABLES `portal` WRITE;
/*!40000 ALTER TABLE `portal` DISABLE KEYS */;
/*!40000 ALTER TABLE `portal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post` (
  `idPost` int(11) NOT NULL,
  `titulo` varchar(255) DEFAULT NULL,
  `descricao` text,
  `conteudo` text,
  `imagem` varchar(255) DEFAULT NULL,
  `idCategoriaPost` int(11) DEFAULT NULL,
  `dataInicio` date DEFAULT NULL,
  `dataFim` date DEFAULT NULL,
  PRIMARY KEY (`idPost`),
  KEY `fk_post_1_idx` (`idCategoriaPost`),
  CONSTRAINT `fk_post_1` FOREIGN KEY (`idCategoriaPost`) REFERENCES `categoriapost` (`idcategoriapost`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prioridade`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prioridade` (
  `idprioridade` int(11) NOT NULL,
  `idempresa` int(11) DEFAULT NULL,
  `nomeprioridade` varchar(100) NOT NULL,
  `situacao` char(1) NOT NULL,
  `grupoprioridade` char(3) NOT NULL,
  PRIMARY KEY (`idprioridade`),
  KEY `INDEX_PRIORIDADEEMPRESA` (`idempresa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='prioridade';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prioridade`
--

LOCK TABLES `prioridade` WRITE;
/*!40000 ALTER TABLE `prioridade` DISABLE KEYS */;
INSERT INTO `prioridade` VALUES (1,1,'1','A',''),(2,1,'2','A',''),(3,1,'3','A',''),(4,1,'4','A',''),(5,1,'5','A','');
/*!40000 ALTER TABLE `prioridade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prioridadeacordonivelservico`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prioridadeacordonivelservico` (
  `idunidade` int(11) NOT NULL,
  `idacordonivelservico` bigint(20) NOT NULL,
  `idprioridade` bigint(20) DEFAULT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  PRIMARY KEY (`idunidade`,`idacordonivelservico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prioridadeacordonivelservico`
--

LOCK TABLES `prioridadeacordonivelservico` WRITE;
/*!40000 ALTER TABLE `prioridadeacordonivelservico` DISABLE KEYS */;
/*!40000 ALTER TABLE `prioridadeacordonivelservico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prioridadeservicounidade`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prioridadeservicounidade` (
  `idunidade` int(11) NOT NULL,
  `idservicocontrato` bigint(20) NOT NULL,
  `idprioridade` bigint(20) DEFAULT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  PRIMARY KEY (`idunidade`,`idservicocontrato`),
  KEY `fk_reference_101` (`idservicocontrato`),
  KEY `fk_reference_99` (`idprioridade`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prioridadeservicounidade`
--

LOCK TABLES `prioridadeservicounidade` WRITE;
/*!40000 ALTER TABLE `prioridadeservicounidade` DISABLE KEYS */;
/*!40000 ALTER TABLE `prioridadeservicounidade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `problema`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `problema` (
  `idproblema` int(11) NOT NULL,
  `status` varchar(40) DEFAULT NULL,
  `prioridade` int(1) DEFAULT NULL,
  `idcriador` int(11) DEFAULT NULL,
  `idproprietario` int(11) DEFAULT NULL,
  `titulo` varchar(255) DEFAULT NULL,
  `descricao` varchar(1000) DEFAULT NULL,
  `idcategoriaproblema` int(11) DEFAULT NULL,
  `impacto` varchar(10) DEFAULT NULL,
  `urgencia` varchar(10) DEFAULT NULL,
  `proativoreativo` varchar(15) DEFAULT NULL,
  `datahoralimitesolucionar` date DEFAULT NULL,
  `msgerroassociada` varchar(1000) DEFAULT NULL,
  `idproblemaitemconfiguracao` int(11) DEFAULT NULL,
  `idproblemamudanca` int(11) DEFAULT NULL,
  `idproblemaincidente` int(11) DEFAULT NULL,
  `datahorainicio` date DEFAULT NULL,
  `datahorafim` date DEFAULT NULL,
  `solucaodefinitiva` varchar(4000) DEFAULT NULL,
  `adicionarbdce` varchar(1) DEFAULT NULL,
  `statusbaseconhecimento` varchar(1) DEFAULT NULL,
  `idpastabaseconhecimento` int(11) DEFAULT NULL,
  `causaraiz` varchar(4000) DEFAULT NULL,
  `solucaocontorno` varchar(4000) DEFAULT NULL,
  `idbaseconhecimento` int(11) DEFAULT NULL,
  `severidade` varchar(15) DEFAULT NULL,
  `idgrupo` int(11) DEFAULT NULL,
  `idservico` int(11) DEFAULT NULL,
  `idcontrato` int(11) DEFAULT NULL,
  `idservicocontrato` int(11) DEFAULT NULL,
  `idprioridade` int(11) DEFAULT NULL,
  `datahoralimite` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `datahorasolicitacao` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `prazoHH` int(11) DEFAULT NULL,
  `prazoMM` int(11) DEFAULT NULL,
  `slaACombinar` char(1) DEFAULT NULL,
  PRIMARY KEY (`idproblema`),
  KEY `fk_reference_612` (`idproblemaitemconfiguracao`),
  KEY `fk_reference_614` (`idproblemamudanca`),
  KEY `fk_reference_616` (`idcategoriaproblema`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problema`
--

LOCK TABLES `problema` WRITE;
/*!40000 ALTER TABLE `problema` DISABLE KEYS */;
/*!40000 ALTER TABLE `problema` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `problemaitemconfiguracao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `problemaitemconfiguracao` (
  `idproblemaitemconfiguracao` int(11) NOT NULL,
  `idproblema` int(11) NOT NULL,
  `iditemconfiguracao` int(11) NOT NULL,
  `descricaoproblema` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idproblemaitemconfiguracao`),
  KEY `fk_reference_610` (`iditemconfiguracao`),
  KEY `fk_reference_613` (`idproblema`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problemaitemconfiguracao`
--

LOCK TABLES `problemaitemconfiguracao` WRITE;
/*!40000 ALTER TABLE `problemaitemconfiguracao` DISABLE KEYS */;
/*!40000 ALTER TABLE `problemaitemconfiguracao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `problemamudanca`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `problemamudanca` (
  `idproblemamudanca` int(11) NOT NULL,
  `idproblema` int(11) NOT NULL,
  `idrequisicaomudanca` int(11) NOT NULL,
  PRIMARY KEY (`idproblemamudanca`),
  KEY `fk_reference_611` (`idrequisicaomudanca`),
  KEY `fk_reference_615` (`idproblema`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problemamudanca`
--

LOCK TABLES `problemamudanca` WRITE;
/*!40000 ALTER TABLE `problemamudanca` DISABLE KEYS */;
/*!40000 ALTER TABLE `problemamudanca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `procedimentotecnico`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `procedimentotecnico` (
  `idprocedimentotecnico` int(11) NOT NULL,
  `nomeprocedimentotecnico` varchar(255) NOT NULL,
  `textoprocedimentotecnico` text,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  PRIMARY KEY (`idprocedimentotecnico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='procedimentotecnico';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `procedimentotecnico`
--

LOCK TABLES `procedimentotecnico` WRITE;
/*!40000 ALTER TABLE `procedimentotecnico` DISABLE KEYS */;
/*!40000 ALTER TABLE `procedimentotecnico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `processamentobatch`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `processamentobatch` (
  `idprocessamentobatch` int(11) NOT NULL,
  `descricao` varchar(256) DEFAULT NULL,
  `expressaocron` varchar(40) NOT NULL,
  `conteudo` text,
  `tipo` char(1) NOT NULL,
  `situacao` char(1) NOT NULL,
  PRIMARY KEY (`idprocessamentobatch`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `processamentobatch`
--

LOCK TABLES `processamentobatch` WRITE;
/*!40000 ALTER TABLE `processamentobatch` DISABLE KEYS */;
INSERT INTO `processamentobatch` VALUES (1,'Execução da Rotina de Inventario','','br.com.centralit.citcorpore.quartz.job.DisparaInventario','C','A'),(2,'Verifica e avisa a data de expiração de um item de configuração','','br.com.centralit.citcorpore.quartz.job.VerificaValidadeLicenca','C','A'),(3,'Backup LogDados','','br.com.centralit.citcorpore.quartz.job.ExecutaBackupLogDados','C','A'),(4,'Replicar o conteúdo do campo descricao da tabela solicitacaoservico para o campo descricaosemformatacao','','br.com.centralit.citcorpore.quartz.job.EventoPopulaDescricaoSolicitacao','C','A'),(5,'Replicar o conteudo do campo conteudo da  tabela base conhecimento para o campo conteudosemformatcao','','br.com.centralit.citcorpore.quartz.job.EventoPopulaConteudoBaseConhecimento','C','A');
/*!40000 ALTER TABLE `processamentobatch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produto`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `produto` (
  `idproduto` int(11) NOT NULL,
  `idtipoproduto` int(11) NOT NULL,
  `idmarca` int(11) DEFAULT NULL,
  `modelo` varchar(25) DEFAULT NULL,
  `precomercado` decimal(8,2) DEFAULT NULL,
  `detalhes` text,
  `codigoproduto` varchar(25) DEFAULT NULL,
  `situacao` char(1) NOT NULL,
  `complemento` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idproduto`),
  KEY `fk_reference_671` (`idtipoproduto`),
  KEY `fk_reference_672` (`idmarca`),
  CONSTRAINT `fk_reference_671` FOREIGN KEY (`idtipoproduto`) REFERENCES `tipoproduto` (`idtipoproduto`),
  CONSTRAINT `fk_reference_672` FOREIGN KEY (`idmarca`) REFERENCES `marca` (`idmarca`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produto`
--

LOCK TABLES `produto` WRITE;
/*!40000 ALTER TABLE `produto` DISABLE KEYS */;
/*!40000 ALTER TABLE `produto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `programacaoatividade`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `programacaoatividade` (
  `idprogramacaoatividade` int(11) NOT NULL,
  `idatividadeperiodica` int(11) NOT NULL,
  `tipoagendamento` char(1) NOT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  `duracaoestimada` int(11) DEFAULT NULL,
  `periodicidadediaria` int(11) DEFAULT NULL,
  `periodicidadesemanal` int(11) DEFAULT NULL,
  `periodicidademensal` int(11) DEFAULT NULL,
  `dia` int(11) DEFAULT NULL,
  `diautil` int(11) DEFAULT NULL,
  `diasemana` int(11) DEFAULT NULL,
  `seqdiasemana` int(11) DEFAULT NULL,
  `seg` char(1) DEFAULT NULL,
  `ter` char(1) DEFAULT NULL,
  `qua` char(1) DEFAULT NULL,
  `qui` char(1) DEFAULT NULL,
  `sex` char(1) DEFAULT NULL,
  `sab` char(1) DEFAULT NULL,
  `dom` char(1) DEFAULT NULL,
  `jan` char(1) DEFAULT NULL,
  `fev` char(1) DEFAULT NULL,
  `mar` char(1) DEFAULT NULL,
  `abr` char(1) DEFAULT NULL,
  `mai` char(1) DEFAULT NULL,
  `jun` char(1) DEFAULT NULL,
  `jul` char(1) DEFAULT NULL,
  `ago` char(1) DEFAULT NULL,
  `setem` char(1) DEFAULT NULL,
  `outub` char(1) DEFAULT NULL,
  `nov` char(1) DEFAULT NULL,
  `dez` char(1) DEFAULT NULL,
  `repeticao` char(1) DEFAULT NULL,
  `repeticaointervalo` int(11) DEFAULT NULL,
  `repeticaotipointervalo` char(1) DEFAULT NULL,
  `horainicio` char(4) DEFAULT NULL,
  `horafim` char(4) DEFAULT NULL,
  `idatividadesos` int(11) DEFAULT NULL,
  PRIMARY KEY (`idprogramacaoatividade`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `programacaoatividade`
--

LOCK TABLES `programacaoatividade` WRITE;
/*!40000 ALTER TABLE `programacaoatividade` DISABLE KEYS */;
/*!40000 ALTER TABLE `programacaoatividade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projetos`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `projetos` (
  `idprojeto` int(11) NOT NULL,
  `idcliente` int(11) NOT NULL,
  `idcontrato` int(11) DEFAULT NULL,
  `nomeprojeto` varchar(70) NOT NULL,
  `detalhamento` text,
  `situacao` char(1) NOT NULL,
  `valorestimado` decimal(18,3) NOT NULL,
  PRIMARY KEY (`idprojeto`),
  KEY `INDEX_PROJETOSCLIENTE` (`idcliente`),
  KEY `INDEX_PROJETOCONTRATO` (`idcontrato`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='projetos';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projetos`
--

LOCK TABLES `projetos` WRITE;
/*!40000 ALTER TABLE `projetos` DISABLE KEYS */;
/*!40000 ALTER TABLE `projetos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questaoquestionario`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questaoquestionario` (
  `idquestaoquestionario` int(11) NOT NULL,
  `idgrupoquestionario` int(11) DEFAULT NULL,
  `idquestaoagrupadora` int(11) DEFAULT NULL,
  `idquestaocompartilhada` int(11) DEFAULT NULL,
  `idquestaoorigem` int(11) DEFAULT NULL,
  `tipo` char(1) NOT NULL,
  `tituloquestaoquestionario` text,
  `tipoquestao` char(1) NOT NULL,
  `sexoquestao` char(1) NOT NULL,
  `sequenciaquestao` int(11) NOT NULL,
  `valordefault` text,
  `textoinicial` text,
  `tamanho` int(11) DEFAULT NULL,
  `decimais` int(11) DEFAULT NULL,
  `inforesposta` char(1) DEFAULT NULL,
  `valoresreferencia` text,
  `unidade` text,
  `obrigatoria` char(1) NOT NULL,
  `ponderada` char(1) DEFAULT NULL,
  `qtdelinhas` int(11) DEFAULT NULL,
  `qtdecolunas` int(11) DEFAULT NULL,
  `cabecalholinhas` char(1) DEFAULT NULL,
  `cabecalhocolunas` char(1) DEFAULT NULL,
  `nomelistagem` varchar(30) DEFAULT NULL,
  `ultimovalor` char(1) DEFAULT NULL,
  `idsubquestionario` int(11) DEFAULT NULL,
  `abaresultsubform` varchar(200) DEFAULT NULL,
  `sigla` varchar(100) DEFAULT NULL,
  `imprime` char(1) DEFAULT NULL,
  `calculada` char(1) DEFAULT NULL,
  `editavel` char(1) DEFAULT NULL,
  `valorpermitido1` decimal(15,5) DEFAULT NULL,
  `valorpermitido2` decimal(15,5) DEFAULT NULL,
  `aplicavelcrianca` char(1) DEFAULT NULL,
  `aplicavelidoso` char(1) DEFAULT NULL,
  `aplicaveladulto` char(1) DEFAULT NULL,
  `aplicaveladolescente` char(1) DEFAULT NULL,
  `idimagem` int(11) DEFAULT NULL,
  PRIMARY KEY (`idquestaoquestionario`),
  KEY `INDEX_QUESTAOGRUPOQUESTIONARIO` (`idgrupoquestionario`),
  KEY `INDEX_QUESTAOAGRUPADORA` (`idquestaoagrupadora`),
  KEY `INDEX_QUESTAOCOMPARTILHADA` (`idquestaocompartilhada`),
  KEY `INDEX_QUESTAOORIGEM` (`idquestaoorigem`),
  KEY `INDEX_SUBQUESTIONARIO` (`idsubquestionario`),
  KEY `INDEX_QUESTAOIMAGEM` (`idimagem`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='questaoquestionario';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questaoquestionario`
--

LOCK TABLES `questaoquestionario` WRITE;
/*!40000 ALTER TABLE `questaoquestionario` DISABLE KEYS */;
/*!40000 ALTER TABLE `questaoquestionario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questionario`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questionario` (
  `idquestionario` int(11) NOT NULL,
  `idquestionarioorigem` int(11) DEFAULT NULL,
  `idcategoriaquestionario` int(11) NOT NULL,
  `nomequestionario` varchar(50) NOT NULL,
  `idempresa` int(11) NOT NULL,
  `ativo` char(1) DEFAULT 'S',
  PRIMARY KEY (`idquestionario`),
  KEY `INDEX_QUESTIONARIOORIGEM` (`idquestionarioorigem`),
  KEY `INDEX_QUESTIONARIOCATEGORIAQUESTIONARIO` (`idcategoriaquestionario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='questionario';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questionario`
--

LOCK TABLES `questionario` WRITE;
/*!40000 ALTER TABLE `questionario` DISABLE KEYS */;
/*!40000 ALTER TABLE `questionario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reaberturasolicitacao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reaberturasolicitacao` (
  `idsolicitacaoservico` bigint(20) NOT NULL,
  `seqreabertura` int(11) NOT NULL,
  `idresponsavel` int(11) DEFAULT NULL,
  `datahora` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `observacoes` text,
  PRIMARY KEY (`idsolicitacaoservico`,`seqreabertura`),
  KEY `fk_reference_542` (`idresponsavel`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reaberturasolicitacao`
--

LOCK TABLES `reaberturasolicitacao` WRITE;
/*!40000 ALTER TABLE `reaberturasolicitacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `reaberturasolicitacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recurso`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recurso` (
  `idrecurso` int(11) NOT NULL,
  `idgruporecurso` int(11) NOT NULL,
  `idrecursopai` int(11) DEFAULT NULL,
  `nomerecurso` varchar(150) NOT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  `tipoatualizacao` char(1) NOT NULL,
  `deleted` char(1) DEFAULT NULL,
  `idnagiosconexao` int(11) DEFAULT NULL,
  `hostName` varchar(255) DEFAULT NULL,
  `serviceName` varchar(255) DEFAULT NULL,
  `horaInicioFunc` varchar(5) DEFAULT NULL,
  `horaFimFunc` varchar(5) DEFAULT NULL,
  `idCalendario` int(11) DEFAULT NULL,
  `statusaberturainc` varchar(255) DEFAULT NULL,
  `idsolicitante` int(11) DEFAULT NULL,
  `emailaberturainc` varchar(255) DEFAULT NULL,
  `descricaoabertinc` text,
  `impacto` char(1) DEFAULT NULL,
  `urgencia` char(1) DEFAULT NULL,
  `idgrupo` int(11) DEFAULT NULL,
  `idorigem` bigint(20) DEFAULT NULL,
  `idservicocontrato` bigint(20) DEFAULT NULL,
  `ideventomonitoramento` int(11) DEFAULT NULL,
  `iditemconfiguracao` int(11) DEFAULT NULL,
  `statusalerta` varchar(255) DEFAULT NULL,
  `emailsalerta` text,
  `descricaoalerta` text,
  PRIMARY KEY (`idrecurso`),
  KEY `fk_reference_605` (`idrecursopai`),
  KEY `fk_ref_rec_nagios` (`idnagiosconexao`),
  KEY `fk_ref_rec_calend` (`idCalendario`),
  KEY `fk_ref_solic_rec` (`idsolicitante`),
  KEY `fk_ref_grp_rec` (`idgrupo`),
  KEY `fk_ref_orig_rec` (`idorigem`),
  KEY `fk_ref_sc_rec` (`idservicocontrato`),
  KEY `fk_ref_evt_rec` (`ideventomonitoramento`),
  KEY `fk_ref_ic_rec` (`iditemconfiguracao`),
  KEY `fk_ref_grprec` (`idgruporecurso`),
  CONSTRAINT `fk_reference_605` FOREIGN KEY (`idrecursopai`) REFERENCES `recurso` (`idrecurso`),
  CONSTRAINT `fk_ref_evt_rec` FOREIGN KEY (`ideventomonitoramento`) REFERENCES `eventomonitoramento` (`ideventomonitoramento`),
  CONSTRAINT `fk_ref_grprec` FOREIGN KEY (`idgruporecurso`) REFERENCES `gruporecursos` (`idgruporecurso`),
  CONSTRAINT `fk_ref_grp_rec` FOREIGN KEY (`idgrupo`) REFERENCES `grupo` (`idgrupo`),
  CONSTRAINT `fk_ref_ic_rec` FOREIGN KEY (`iditemconfiguracao`) REFERENCES `itemconfiguracao` (`iditemconfiguracao`),
  CONSTRAINT `fk_ref_orig_rec` FOREIGN KEY (`idorigem`) REFERENCES `origematendimento` (`idorigem`),
  CONSTRAINT `fk_ref_rec_calend` FOREIGN KEY (`idCalendario`) REFERENCES `calendario` (`idcalendario`),
  CONSTRAINT `fk_ref_rec_nagios` FOREIGN KEY (`idnagiosconexao`) REFERENCES `nagiosconexao` (`idnagiosconexao`),
  CONSTRAINT `fk_ref_sc_rec` FOREIGN KEY (`idservicocontrato`) REFERENCES `servicocontrato` (`idservicocontrato`),
  CONSTRAINT `fk_ref_solic_rec` FOREIGN KEY (`idsolicitante`) REFERENCES `empregados` (`idempregado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recurso`
--

LOCK TABLES `recurso` WRITE;
/*!40000 ALTER TABLE `recurso` DISABLE KEYS */;
/*!40000 ALTER TABLE `recurso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `regioes`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `regioes` (
  `idregioes` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL,
  PRIMARY KEY (`idregioes`),
  UNIQUE KEY `nome_UNIQUE` (`nome`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regioes`
--

LOCK TABLES `regioes` WRITE;
/*!40000 ALTER TABLE `regioes` DISABLE KEYS */;
/*!40000 ALTER TABLE `regioes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registro`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `registro` (
  `idregistro` int(11) NOT NULL,
  `tokenidentificacao` varchar(20) NOT NULL,
  `datahora` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idregistro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registro`
--

LOCK TABLES `registro` WRITE;
/*!40000 ALTER TABLE `registro` DISABLE KEYS */;
/*!40000 ALTER TABLE `registro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `relacionamentoproduto`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `relacionamentoproduto` (
  `idtipoproduto` int(11) NOT NULL,
  `idtipoprodutorelacionado` int(11) NOT NULL,
  `tiporelacionamento` char(1) DEFAULT NULL COMMENT 'A - Acessório\n            S - Produto semelhante',
  PRIMARY KEY (`idtipoproduto`,`idtipoprodutorelacionado`),
  KEY `fk_reference_655` (`idtipoprodutorelacionado`),
  CONSTRAINT `fk_reference_654` FOREIGN KEY (`idtipoproduto`) REFERENCES `tipoproduto` (`idtipoproduto`),
  CONSTRAINT `fk_reference_655` FOREIGN KEY (`idtipoprodutorelacionado`) REFERENCES `tipoproduto` (`idtipoproduto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `relacionamentoproduto`
--

LOCK TABLES `relacionamentoproduto` WRITE;
/*!40000 ALTER TABLE `relacionamentoproduto` DISABLE KEYS */;
/*!40000 ALTER TABLE `relacionamentoproduto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `relacobjetonegocio`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `relacobjetonegocio` (
  `idrelacobjetonegocio` bigint(20) NOT NULL,
  `idobjetonegociopai` bigint(20) NOT NULL,
  `idobjetonegociofilho` bigint(20) NOT NULL,
  `situacao` char(1) NOT NULL,
  PRIMARY KEY (`idrelacobjetonegocio`),
  KEY `fk_reference_73` (`idobjetonegociopai`),
  KEY `fk_reference_74` (`idobjetonegociofilho`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `relacobjetonegocio`
--

LOCK TABLES `relacobjetonegocio` WRITE;
/*!40000 ALTER TABLE `relacobjetonegocio` DISABLE KEYS */;
/*!40000 ALTER TABLE `relacobjetonegocio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requisicaomudanca`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `requisicaomudanca` (
  `idrequisicaomudanca` int(11) NOT NULL,
  `idproprietario` int(11) NOT NULL,
  `idsolicitante` int(11) NOT NULL,
  `idtipomudanca` int(11) DEFAULT NULL,
  `idgruponivel1` int(11) DEFAULT NULL,
  `idgrupoatual` int(11) DEFAULT NULL,
  `idcalendario` int(11) DEFAULT NULL,
  `motivo` varchar(255) DEFAULT NULL,
  `nivelimportancianegocio` varchar(255) DEFAULT NULL,
  `classificacao` varchar(255) DEFAULT NULL,
  `nivelimpacto` varchar(255) DEFAULT NULL,
  `analiseimpacto` varchar(3000) DEFAULT NULL,
  `datahoraconclusao` timestamp NULL DEFAULT NULL,
  `dataaceitacao` date DEFAULT NULL,
  `datavotacao` date DEFAULT NULL,
  `datahorainicio` timestamp NULL DEFAULT NULL,
  `datahoratermino` timestamp NULL DEFAULT NULL,
  `titulo` varchar(255) DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `risco` varchar(255) DEFAULT NULL,
  `estimativacusto` double DEFAULT NULL,
  `planoreversao` varchar(3000) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `prioridade` int(11) DEFAULT NULL,
  `enviaemailcriacao` varchar(1) DEFAULT NULL,
  `enviaemailfinalizacao` varchar(1) DEFAULT NULL,
  `enviaemailacoes` varchar(1) DEFAULT NULL,
  `exibirquadromudancas` varchar(1) DEFAULT NULL,
  `seqreabertura` smallint(6) DEFAULT NULL,
  `datahoracaptura` timestamp NULL DEFAULT NULL,
  `datahorareativacao` timestamp NULL DEFAULT NULL,
  `datahorasuspensao` timestamp NULL DEFAULT NULL,
  `tempodecorridohh` smallint(6) DEFAULT NULL,
  `tempodecorridomm` smallint(6) DEFAULT NULL,
  `prazohh` smallint(6) DEFAULT NULL,
  `prazomm` smallint(6) DEFAULT NULL,
  `tempoatendimentohh` smallint(6) DEFAULT NULL,
  `tempoatendimentomm` smallint(6) DEFAULT NULL,
  `tempoatrasohh` smallint(6) DEFAULT NULL,
  `tempoatrasomm` smallint(6) DEFAULT NULL,
  `tempocapturahh` smallint(6) DEFAULT NULL,
  `tempocapturamm` smallint(6) DEFAULT NULL,
  `fase` varchar(20) DEFAULT NULL,
  `nivelurgencia` varchar(255) DEFAULT NULL,
  `idbaseconhecimento` int(11) DEFAULT NULL,
  `nomecategoriamudanca` varchar(15) DEFAULT NULL,
  `idcontrato` int(11) NOT NULL,
  `idunidade` int(11) DEFAULT NULL,
  `idcontatorequisicaomudanca` int(10) DEFAULT NULL,
  `idgrupocomite` int(11) DEFAULT NULL,
  `enviaemailgrupocomite` varchar(1) DEFAULT NULL,
  `datahorainicioagendada` timestamp NULL DEFAULT NULL,
  `datahoraterminoagendada` timestamp NULL DEFAULT NULL,
  `idlocalidade` int(11) DEFAULT NULL,
  `fechamento` text,
  `tipo` VARCHAR(255) NULL ,
  PRIMARY KEY (`idrequisicaomudanca`),
  KEY `fk_requisic_reference_cat` (`idtipomudanca`),
  KEY `fk_requisic_reference_grupo` (`idgruponivel1`),
  KEY `fk_requisic_reference_grupo_01` (`idgrupoatual`),
  KEY `fk_requisic_reference_calendar` (`idcalendario`),
  KEY `fk_idbaseconhecimento` (`idbaseconhecimento`),
  KEY `fk_requisic_reference_grupo_comite` (`idgrupocomite`),
  CONSTRAINT `fk_idbaseconhecimento` FOREIGN KEY (`idbaseconhecimento`) REFERENCES `baseconhecimento` (`idbaseconhecimento`),
  CONSTRAINT `fk_requisic_reference_calendar` FOREIGN KEY (`idcalendario`) REFERENCES `calendario` (`idcalendario`),
  CONSTRAINT `fk_requisic_reference_grupo` FOREIGN KEY (`idgruponivel1`) REFERENCES `grupo` (`idgrupo`),
  CONSTRAINT `fk_requisic_reference_grupo_01` FOREIGN KEY (`idgrupoatual`) REFERENCES `grupo` (`idgrupo`),
  CONSTRAINT `fk_requisic_reference_grupo_comite` FOREIGN KEY (`idgrupocomite`) REFERENCES `grupo` (`idgrupo`),
  CONSTRAINT `fk_requisic_reference_tipomudanca` FOREIGN KEY (`idtipomudanca`) REFERENCES `tipomudanca` (`idtipomudanca`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requisicaomudanca`
--

LOCK TABLES `requisicaomudanca` WRITE;
/*!40000 ALTER TABLE `requisicaomudanca` DISABLE KEYS */;
/*!40000 ALTER TABLE `requisicaomudanca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requisicaomudancaitemconfiguracao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `requisicaomudancaitemconfiguracao` (
  `idrequisicaomudancaitemconfiguracao` int(11) NOT NULL,
  `idrequisicaomudanca` int(11) DEFAULT NULL,
  `iditemconfiguracao` int(11) DEFAULT NULL,
  `descricao` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idrequisicaomudancaitemconfiguracao`),
  KEY `fk_requisic_reference_req` (`idrequisicaomudanca`),
  KEY `fk_requisic_reference_itemconf` (`iditemconfiguracao`),
  CONSTRAINT `fk_requisic_reference_itemconf` FOREIGN KEY (`iditemconfiguracao`) REFERENCES `itemconfiguracao` (`iditemconfiguracao`),
  CONSTRAINT `fk_requisic_reference_req` FOREIGN KEY (`idrequisicaomudanca`) REFERENCES `requisicaomudanca` (`idrequisicaomudanca`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requisicaomudancaitemconfiguracao`
--

LOCK TABLES `requisicaomudancaitemconfiguracao` WRITE;
/*!40000 ALTER TABLE `requisicaomudancaitemconfiguracao` DISABLE KEYS */;
/*!40000 ALTER TABLE `requisicaomudancaitemconfiguracao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requisicaomudancaservico`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `requisicaomudancaservico` (
  `idrequisicaomudancaservico` int(11) NOT NULL,
  `idrequisicaomudanca` int(11) NOT NULL,
  `idservico` bigint(20) NOT NULL,
  PRIMARY KEY (`idrequisicaomudancaservico`),
  KEY `fk_requisic_referenc_req` (`idrequisicaomudanca`),
  KEY `fk_requisic_reference_servico` (`idservico`),
  CONSTRAINT `fk_requisic_reference_servico` FOREIGN KEY (`idservico`) REFERENCES `servico` (`idservico`),
  CONSTRAINT `fk_requisic_referenc_req` FOREIGN KEY (`idrequisicaomudanca`) REFERENCES `requisicaomudanca` (`idrequisicaomudanca`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requisicaomudancaservico`
--

LOCK TABLES `requisicaomudancaservico` WRITE;
/*!40000 ALTER TABLE `requisicaomudancaservico` DISABLE KEYS */;
/*!40000 ALTER TABLE `requisicaomudancaservico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requisicaoproduto`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `requisicaoproduto` (
  `idsolicitacaoservico` int(11) NOT NULL,
  `idprojeto` int(11) DEFAULT NULL,
  `idcentrocusto` int(11) DEFAULT NULL,
  `idenderecoentrega` int(11) DEFAULT NULL,
  `finalidade` char(1) NOT NULL COMMENT 'C - Atendimento ao cliente\n            I - Uso interno',
  `rejeitada` char(1) NOT NULL,
  PRIMARY KEY (`idsolicitacaoservico`),
  KEY `fk_reference_626` (`idprojeto`),
  KEY `fk_reference_646` (`idcentrocusto`),
  KEY `fk_reference_668` (`idenderecoentrega`),
  CONSTRAINT `fk_reference_626` FOREIGN KEY (`idprojeto`) REFERENCES `projetos` (`idprojeto`),
  CONSTRAINT `fk_reference_646` FOREIGN KEY (`idcentrocusto`) REFERENCES `centroresultado` (`idcentroresultado`),
  CONSTRAINT `fk_reference_668` FOREIGN KEY (`idenderecoentrega`) REFERENCES `endereco` (`idendereco`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requisicaoproduto`
--

LOCK TABLES `requisicaoproduto` WRITE;
/*!40000 ALTER TABLE `requisicaoproduto` DISABLE KEYS */;
/*!40000 ALTER TABLE `requisicaoproduto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requisitosla`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `requisitosla` (
  `idrequisitosla` int(11) NOT NULL,
  `idempregado` int(11) DEFAULT NULL,
  `requisitadoem` date DEFAULT NULL,
  `assunto` varchar(200) NOT NULL,
  `detalhamento` text,
  `situacao` char(1) NOT NULL,
  `criadopor` varchar(255) DEFAULT NULL,
  `criadoem` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modificadopor` varchar(255) DEFAULT NULL,
  `modificadoem` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `deleted` char(1) DEFAULT NULL,
  PRIMARY KEY (`idrequisitosla`),
  KEY `fk_reference_552` (`idempregado`),
  CONSTRAINT `fk_reference_552` FOREIGN KEY (`idempregado`) REFERENCES `empregados` (`idempregado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requisitosla`
--

LOCK TABLES `requisitosla` WRITE;
/*!40000 ALTER TABLE `requisitosla` DISABLE KEYS */;
/*!40000 ALTER TABLE `requisitosla` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `responsabilidade`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `responsabilidade` (
  `idresponsabilidade` int(11) NOT NULL,
  `idempregado` int(11) DEFAULT NULL,
  `idcontrato` int(11) NOT NULL,
  `idcontatocliente` int(11) DEFAULT NULL,
  `idservicocontrato` int(11) DEFAULT NULL,
  `produtoatv` varchar(400) NOT NULL,
  `detalhamento` text,
  `participacao` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`idresponsabilidade`),
  KEY `INDEX_RESPONSABILIDADEEMPREGADO` (`idempregado`),
  KEY `INDEX_RESPONSABILIDADECONTRATO` (`idcontrato`),
  KEY `INDEX_RESPONSABILIDADECONTATOCLIENTE` (`idcontatocliente`),
  KEY `INDEX_RESPONSABILIDADESERVICOCONTRATO` (`idservicocontrato`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='responsabilidade';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `responsabilidade`
--

LOCK TABLES `responsabilidade` WRITE;
/*!40000 ALTER TABLE `responsabilidade` DISABLE KEYS */;
/*!40000 ALTER TABLE `responsabilidade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `respostaitemquestionario`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `respostaitemquestionario` (
  `idrespostaitemquestionario` int(11) NOT NULL,
  `ididentificadorresposta` int(11) NOT NULL,
  `idquestaoquestionario` int(11) NOT NULL,
  `sequencialresposta` int(11) DEFAULT NULL,
  `respostatextual` text,
  `respostapercentual` decimal(15,5) DEFAULT NULL,
  `respostavalor` decimal(15,5) DEFAULT NULL,
  `respostavalor2` decimal(15,5) DEFAULT NULL,
  `respostanumero` decimal(13,0) DEFAULT NULL,
  `respostanumero2` decimal(13,0) DEFAULT NULL,
  `respostadata` date DEFAULT NULL,
  `respostahora` varchar(4) DEFAULT NULL,
  `respostapressaosistolica` smallint(6) DEFAULT NULL,
  `respostapressaodiastolica` smallint(6) DEFAULT NULL,
  `respostames` smallint(6) DEFAULT NULL,
  `respostaano` smallint(6) DEFAULT NULL,
  `respostapeso` decimal(15,5) DEFAULT NULL,
  `respostaaltura` decimal(15,5) DEFAULT NULL,
  `respostaimc` decimal(15,5) DEFAULT NULL,
  `respostadum` date DEFAULT NULL,
  `respostaciclomenstrual` smallint(6) DEFAULT NULL,
  `respostamediafaselutea` smallint(6) DEFAULT NULL,
  `respostadpp` date DEFAULT NULL,
  `respostaidlistagem` varchar(10) DEFAULT NULL,
  `idatestado` int(11) DEFAULT NULL,
  `respostadpppeladum` date DEFAULT NULL,
  `respostadia` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`idrespostaitemquestionario`),
  KEY `INDEX_IDENTIFICADORRESPOSTA` (`ididentificadorresposta`),
  KEY `INDEX_RESPOSTAQUESTAOQUESTIONARIO` (`idquestaoquestionario`),
  KEY `INDEX_REPOSTAATESTADO` (`idatestado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='respostaitemquestionario';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `respostaitemquestionario`
--

LOCK TABLES `respostaitemquestionario` WRITE;
/*!40000 ALTER TABLE `respostaitemquestionario` DISABLE KEYS */;
/*!40000 ALTER TABLE `respostaitemquestionario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `respostaitemquestionarioanexos`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `respostaitemquestionarioanexos` (
  `idrespostaitmquestionarioanexo` int(11) NOT NULL,
  `idrespostaitemquestionario` int(11) NOT NULL,
  `caminhoanexo` varchar(255) NOT NULL,
  `observacao` text,
  PRIMARY KEY (`idrespostaitmquestionarioanexo`),
  KEY `INDEX_RESPOSTAITEMQUESTIONARIO` (`idrespostaitemquestionario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='respostaitemquestionarioanexos';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `respostaitemquestionarioanexos`
--

LOCK TABLES `respostaitemquestionarioanexos` WRITE;
/*!40000 ALTER TABLE `respostaitemquestionarioanexos` DISABLE KEYS */;
/*!40000 ALTER TABLE `respostaitemquestionarioanexos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `respostaitemquestionariocids`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `respostaitemquestionariocids` (
  `idrespostaitemquestionariocid` int(11) NOT NULL,
  `idrespostaitemquestionario` int(11) NOT NULL,
  `idcid` int(11) NOT NULL,
  PRIMARY KEY (`idrespostaitemquestionariocid`),
  KEY `INDEX_RESPOSTAITEMQUESTIONARIO` (`idrespostaitemquestionario`),
  KEY `INDEX_REPOSTACID` (`idcid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='respostaitemquestionariocids';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `respostaitemquestionariocids`
--

LOCK TABLES `respostaitemquestionariocids` WRITE;
/*!40000 ALTER TABLE `respostaitemquestionariocids` DISABLE KEYS */;
/*!40000 ALTER TABLE `respostaitemquestionariocids` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `respostaitemquestionarioopcoes`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `respostaitemquestionarioopcoes` (
  `idrespostaitemquestionario` int(11) NOT NULL,
  `idopcaorespostaquestionario` int(11) NOT NULL,
  PRIMARY KEY (`idrespostaitemquestionario`,`idopcaorespostaquestionario`),
  KEY `INDEX_REPOSTAOPCAORESPOSTAQUESTIONARIO` (`idopcaorespostaquestionario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='respostaitemquestionarioopcoes';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `respostaitemquestionarioopcoes`
--

LOCK TABLES `respostaitemquestionarioopcoes` WRITE;
/*!40000 ALTER TABLE `respostaitemquestionarioopcoes` DISABLE KEYS */;
/*!40000 ALTER TABLE `respostaitemquestionarioopcoes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resultadosesperados`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resultadosesperados` (
  `idservicocontrato` bigint(20) NOT NULL,
  `idacordonivelservico` bigint(20) NOT NULL,
  `descricaoresultados` varchar(1000) DEFAULT NULL,
  `limites` varchar(200) DEFAULT NULL,
  `glosa` varchar(200) DEFAULT NULL,
  `limiteglosa` varchar(200) DEFAULT NULL,
  `deleted` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resultadosesperados`
--

LOCK TABLES `resultadosesperados` WRITE;
/*!40000 ALTER TABLE `resultadosesperados` DISABLE KEYS */;
/*!40000 ALTER TABLE `resultadosesperados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `revisarsla`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `revisarsla` (
  `idrevisarsla` bigint(20) NOT NULL,
  `idacordonivelservico` bigint(20) NOT NULL,
  `datarevisao` date NOT NULL,
  `detalherevisao` text,
  `observacao` varchar(200) DEFAULT NULL,
  `deleted` char(1) DEFAULT NULL,
  PRIMARY KEY (`idrevisarsla`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `revisarsla`
--

LOCK TABLES `revisarsla` WRITE;
/*!40000 ALTER TABLE `revisarsla` DISABLE KEYS */;
/*!40000 ALTER TABLE `revisarsla` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scripts`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scripts` (
  `descricao` text,
  `datafim` date DEFAULT NULL,
  `datainicio` date NOT NULL,
  `historico` text,
  `idscript` int(11) NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `sqlquery` text,
  `tipo` char(10) DEFAULT NULL,
  `idversao` int(11) DEFAULT NULL,
  PRIMARY KEY (`idscript`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scripts`
--

LOCK TABLES `scripts` WRITE;
/*!40000 ALTER TABLE `scripts` DISABLE KEYS */;
/*!40000 ALTER TABLE `scripts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scriptsvisao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scriptsvisao` (
  `idscriptsvisao` bigint(20) NOT NULL,
  `idvisao` bigint(20) NOT NULL,
  `typeexecute` char(1) NOT NULL,
  `scrypttype` char(30) NOT NULL,
  `script` text,
  `scriptlanguage` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`idscriptsvisao`),
  KEY `fk_reference_96` (`idvisao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scriptsvisao`
--

LOCK TABLES `scriptsvisao` WRITE;
/*!40000 ALTER TABLE `scriptsvisao` DISABLE KEYS */;
/*!40000 ALTER TABLE `scriptsvisao` ENABLE KEYS */;
UNLOCK TABLES;


CREATE TABLE `sequence_block_controller` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `sequence_name` VARCHAR(80) NOT NULL,
  `last_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `sequence_name_UNIQUE` (`sequence_name` ASC),
  INDEX `sequence_name_idx` (`sequence_name` ASC));
  
  
--
-- Table structure for table `servcontratocatalogoserv`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `servcontratocatalogoserv` (
  `idservicocontrato` bigint(20) DEFAULT NULL,
  `idcatalogoservico` int(11) DEFAULT NULL,
  KEY `fk_servcontr_reference_servico` (`idservicocontrato`),
  KEY `fk_servcontr_reference_catalogoservico` (`idcatalogoservico`),
  CONSTRAINT `fk_servcontr_reference_catalogoservico` FOREIGN KEY (`idcatalogoservico`) REFERENCES `catalogoservico` (`idcatalogoservico`),
  CONSTRAINT `fk_servcontr_reference_servico` FOREIGN KEY (`idservicocontrato`) REFERENCES `servico` (`idservico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servcontratocatalogoserv`
--

LOCK TABLES `servcontratocatalogoserv` WRITE;
/*!40000 ALTER TABLE `servcontratocatalogoserv` DISABLE KEYS */;
/*!40000 ALTER TABLE `servcontratocatalogoserv` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servico`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `servico` (
  `idservico` bigint(20) NOT NULL,
  `idcategoriaservico` int(11) NOT NULL,
  `idsituacaoservico` int(11) NOT NULL,
  `idtiposervico` int(11) DEFAULT NULL,
  `idimportancianegocio` int(11) DEFAULT NULL,
  `idempresa` bigint(20) NOT NULL,
  `idtipoeventoservico` int(11) DEFAULT NULL,
  `idtipodemandaservico` int(11) DEFAULT NULL,
  `idlocalexecucaoservico` int(11) DEFAULT NULL,
  `nomeservico` varchar(500) NOT NULL,
  `detalheservico` text,
  `objetivo` text,
  `passosservico` text,
  `datainicio` date DEFAULT NULL,
  `linkprocesso` varchar(500) DEFAULT NULL,
  `descricaoprocesso` text,
  `tipodescprocess` char(1) DEFAULT NULL,
  `dispportal` char(1) DEFAULT NULL,
  `quadroorientportal` text,
  `deleted` char(1) DEFAULT NULL,
  `detalhesServico` varchar(255) DEFAULT NULL,
  `siglaAbrev` varchar(150) DEFAULT NULL,
  `idbaseconhecimento` int(11) DEFAULT NULL,
  `idtemplatesolicitacao` int(11) DEFAULT NULL,
  `idtemplateacompanhamento` int(11) DEFAULT NULL,
  PRIMARY KEY (`idservico`),
  KEY `fk_reference_124` (`idtipoeventoservico`),
  KEY `fk_reference_125` (`idtipodemandaservico`),
  KEY `fk_reference_126` (`idlocalexecucaoservico`),
  KEY `fk_reference_34` (`idcategoriaservico`),
  KEY `fk_reference_35` (`idsituacaoservico`),
  KEY `fk_reference_36` (`idtiposervico`),
  KEY `fk_reference_37` (`idimportancianegocio`),
  KEY `fk_reference_42` (`idempresa`),
  KEY `FK7643C6BFC86EB082` (`idcategoriaservico`),
  KEY `FK7643C6BFC4080A57` (`idcategoriaservico`),
  KEY `fk_reference_567` (`idbaseconhecimento`),
  KEY `index_nome` (`nomeservico`(255)),
  KEY `idtemplatesolicitacao` (`idtemplatesolicitacao`),
  KEY `fk_reference_642` (`idtemplateacompanhamento`),
  KEY `index_servico_idcatservico` (`idcategoriaservico`),
  KEY `index_servico_idsitservico` (`idsituacaoservico`),
  KEY `index_servico_idtiposervico` (`idtiposervico`),
  KEY `index_servico_nomeservico` (`nomeservico`(255)),
  CONSTRAINT `fk_reference_642` FOREIGN KEY (`idtemplateacompanhamento`) REFERENCES `templatesolicitacaoservico` (`idtemplate`),
  CONSTRAINT `servico_ibfk_1` FOREIGN KEY (`idtemplatesolicitacao`) REFERENCES `templatesolicitacaoservico` (`idtemplate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servico`
--

LOCK TABLES `servico` WRITE;
/*!40000 ALTER TABLE `servico` DISABLE KEYS */;
INSERT INTO `servico` VALUES (1,1,1,1,10,1,33,3,4,'Serviço 1','Descrição do Serviço 1','',NULL,'2013-04-01','','',NULL,'S',NULL,NULL,NULL,'',NULL,NULL,NULL),(2,1,1,1,10,1,33,3,4,'Serviço 2','Descrição do Serviço 2','',NULL,'2013-04-01','','',NULL,'S',NULL,NULL,NULL,'',NULL,NULL,NULL),(3,1,1,1,10,1,33,3,4,'Serviço 3','Descrição do Serviço 3','',NULL,'2013-04-01','','',NULL,'S',NULL,NULL,NULL,'',NULL,NULL,NULL),(4,1,1,1,10,1,33,1,4,'Serviço 4','Descrição do Serviço 4','',NULL,'2013-04-01','','',NULL,'S',NULL,NULL,NULL,'',NULL,NULL,NULL),(5,1,1,1,10,1,33,1,4,'Serviço 5','Descrição do Serviço 5','',NULL,'2013-04-01','','',NULL,'S',NULL,NULL,NULL,'',NULL,NULL,NULL);
/*!40000 ALTER TABLE `servico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servico_hist`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `servico_hist` (
  `idhistoricoservico` bigint(20) NOT NULL,
  `idservico` bigint(20) NOT NULL,
  `idcategoriaservico` int(11) NOT NULL,
  `idsituacaoservico` int(11) NOT NULL,
  `idtiposervico` int(11) DEFAULT NULL,
  `idimportancianegocio` int(11) DEFAULT NULL,
  `idempresa` bigint(20) NOT NULL,
  `idtipoeventoservico` int(11) DEFAULT NULL,
  `idtipodemandaservico` int(11) DEFAULT NULL,
  `idlocalexecucaoservico` int(11) DEFAULT NULL,
  `nomeservico` varchar(500) NOT NULL,
  `detalheservico` text,
  `objetivo` text,
  `passosservico` text,
  `datainicio` date DEFAULT NULL,
  `linkprocesso` varchar(500) DEFAULT NULL,
  `descricaoprocesso` text,
  `tipodescprocess` char(1) DEFAULT NULL,
  `dispportal` char(1) DEFAULT NULL,
  `quadroorientportal` text,
  `deleted` char(1) DEFAULT NULL,
  `detalhesServico` varchar(255) DEFAULT NULL,
  `siglaAbrev` varchar(150) DEFAULT NULL,
  `idbaseconhecimento` int(11) DEFAULT NULL,
  `idtemplatesolicitacao` int(11) DEFAULT NULL,
  `idtemplateacompanhamento` int(11) DEFAULT NULL,
  `criadoEm` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `criadoPor` varchar(255) DEFAULT NULL,
  `modificadoEm` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modificadoPor` varchar(255) DEFAULT NULL,
  `conteudodados` text,
  PRIMARY KEY (`idhistoricoservico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servico_hist`
--

LOCK TABLES `servico_hist` WRITE;
/*!40000 ALTER TABLE `servico_hist` DISABLE KEYS */;
INSERT INTO `servico_hist` VALUES (1,1,1,1,1,10,1,19,3,4,'Seu Serviço','Descrição do Serviço','',NULL,'2013-04-01','','',NULL,'S',NULL,NULL,NULL,'',NULL,NULL,NULL,'2013-04-02 04:00:00','Administrador','2013-04-02 04:00:00','Administrador','JSONDATA: TRUE\nNOCACHE: Tue Apr 02 2013 13:24:15 GMT-0300 (Hora oficial do Brasil)\nIDCATEGORIASERVICO: 1\nKEYCONTROL: \nDINAMICVIEWSJSON_TEMPDATA: \nIDSITUACAOSERVICO: 1\nIDTIPODEMANDASERVICO: 3\nSIGLAABREV: \nLINKPROCESSO: \nIDBASECONHECIMENTO: \nIDLOCALEXECUCAOSERVICO: 4\nDINAMICVIEWSTABLESVINC: \nDINAMICVIEWSACAOPESQUISASELECIONADA: \nIDSERVICO: 1\nACAOFLUXO: \nMODOEXIBICAO: N\nDETALHESERVICO: Descrição do Serviço\nSESSION.NUMERO_CONTRATO_EDICAO: null\nNOMESERVICO: Seu Serviço\nDINAMICVIEWSJSON_DATA: \nIDEMPRESA: 1\nDINAMICVIEWSIDVISAOPESQUISASELECIONADA: \nIDFLUXO: \nDESCRICAOPROCESSO: \nJSONMATRIZ: \nDATAINICIO: 01/04/2013\nIDIMPORTANCIANEGOCIO: 10\nDINAMICVIEWSIDVISAO: 17\nIDTIPOSERVICO: 1\nSESSION.DINAMICVIEWS_SAVEINFO: null\nOBJETIVO: \nIDTAREFA: \nDINAMICVIEWSDADOSADICIONAIS: \nDISPPORTAL: S\nIDTIPOEVENTOSERVICO: 19\n'),(2,1,1,1,1,10,1,19,3,4,'Serviço 1','Descrição do Serviço 1','',NULL,'2013-04-01','','',NULL,'S',NULL,NULL,NULL,'',NULL,NULL,NULL,'2013-04-03 04:00:00','Administrador','2013-04-03 04:00:00','Administrador','JSONDATA: \nREMOVED: false\nNOCACHE: Wed Apr 03 2013 08:54:34 GMT-0300 (Hora oficial do Brasil)\nIDCATEGORIASERVICO: 1\nDINAMICVIEWSJSON_TEMPDATA: \nIDSITUACAOSERVICO: 1\nKEYCONTROL: \nIDTIPODEMANDASERVICO: 3\nSIGLAABREV: \nLINKPROCESSO: \nIDBASECONHECIMENTO: \nIDLOCALEXECUCAOSERVICO: 4\nDINAMICVIEWSTABLESVINC: \nDINAMICVIEWSACAOPESQUISASELECIONADA: \nIDSERVICO: 1\nACAOFLUXO: \nDETALHESERVICO: Descrição do Serviço 1\nMODOEXIBICAO: N\nSESSION.NUMERO_CONTRATO_EDICAO: null\nDINAMICVIEWSJSON_DATA: \nNOMESERVICO: Serviço 1\nIDFLUXO: \nIDEMPRESA: 1\nDINAMICVIEWSIDVISAOPESQUISASELECIONADA: \nJSONMATRIZ: \nDESCRICAOPROCESSO: \nIDIMPORTANCIANEGOCIO: 10\nDATAINICIO: 01/04/2013\nDINAMICVIEWSIDVISAO: 17\nIDTIPOSERVICO: 1\nSESSION.DINAMICVIEWS_SAVEINFO: null\nIDTAREFA: \nOBJETIVO: \nDINAMICVIEWSDADOSADICIONAIS: \nDISPPORTAL: S\nIDTIPOEVENTOSERVICO: 19\n'),(3,2,1,1,1,10,1,33,3,4,'Serviço 2','Descrição do Serviço 2','',NULL,'2013-04-01','','',NULL,'S',NULL,NULL,NULL,'',NULL,NULL,NULL,'2013-04-03 04:00:00','Administrador','2013-04-03 04:00:00','Administrador','JSONDATA: \nNOCACHE: Wed Apr 03 2013 08:55:48 GMT-0300 (Hora oficial do Brasil)\nIDCATEGORIASERVICO: 1\nKEYCONTROL: \nDINAMICVIEWSJSON_TEMPDATA: \nIDSITUACAOSERVICO: 1\nIDTIPODEMANDASERVICO: 3\nSIGLAABREV: \nLINKPROCESSO: \nIDBASECONHECIMENTO: \nIDLOCALEXECUCAOSERVICO: 4\nDINAMICVIEWSTABLESVINC: \nDINAMICVIEWSACAOPESQUISASELECIONADA: \nIDSERVICO: 2\nACAOFLUXO: \nMODOEXIBICAO: N\nDETALHESERVICO: Descrição do Serviço 2\nSESSION.NUMERO_CONTRATO_EDICAO: null\nNOMESERVICO: Serviço 2\nDINAMICVIEWSJSON_DATA: \nIDEMPRESA: 1\nDINAMICVIEWSIDVISAOPESQUISASELECIONADA: \nIDFLUXO: \nDESCRICAOPROCESSO: \nJSONMATRIZ: \nDATAINICIO: 01/04/2013\nIDIMPORTANCIANEGOCIO: 10\nDINAMICVIEWSIDVISAO: 17\nIDTIPOSERVICO: 1\nSESSION.DINAMICVIEWS_SAVEINFO: null\nOBJETIVO: \nIDTAREFA: \nDINAMICVIEWSDADOSADICIONAIS: \nDISPPORTAL: S\nIDTIPOEVENTOSERVICO: 33\n'),(4,1,1,1,1,10,1,33,3,4,'Serviço 1','Descrição do Serviço 1','',NULL,'2013-04-01','','',NULL,'S',NULL,NULL,NULL,'',NULL,NULL,NULL,'2013-04-03 04:00:00','Administrador','2013-04-03 04:00:00','Administrador','JSONDATA: \nREMOVED: false\nNOCACHE: Wed Apr 03 2013 08:56:03 GMT-0300 (Hora oficial do Brasil)\nIDCATEGORIASERVICO: 1\nDINAMICVIEWSJSON_TEMPDATA: \nIDSITUACAOSERVICO: 1\nKEYCONTROL: \nIDTIPODEMANDASERVICO: 3\nSIGLAABREV: \nLINKPROCESSO: \nIDBASECONHECIMENTO: \nIDLOCALEXECUCAOSERVICO: 4\nDINAMICVIEWSTABLESVINC: \nDINAMICVIEWSACAOPESQUISASELECIONADA: \nIDSERVICO: 1\nACAOFLUXO: \nDETALHESERVICO: Descrição do Serviço 1\nMODOEXIBICAO: N\nSESSION.NUMERO_CONTRATO_EDICAO: null\nDINAMICVIEWSJSON_DATA: \nNOMESERVICO: Serviço 1\nIDFLUXO: \nIDEMPRESA: 1\nDINAMICVIEWSIDVISAOPESQUISASELECIONADA: \nJSONMATRIZ: \nDESCRICAOPROCESSO: \nIDIMPORTANCIANEGOCIO: 10\nDATAINICIO: 01/04/2013\nDINAMICVIEWSIDVISAO: 17\nIDTIPOSERVICO: 1\nSESSION.DINAMICVIEWS_SAVEINFO: null\nIDTAREFA: \nOBJETIVO: \nDINAMICVIEWSDADOSADICIONAIS: \nDISPPORTAL: S\nIDTIPOEVENTOSERVICO: 33\n'),(5,3,1,1,1,10,1,33,3,4,'Serviço 3','Descrição do Serviço 3','',NULL,'2013-04-01','','',NULL,'S',NULL,NULL,NULL,'',NULL,NULL,NULL,'2013-04-03 04:00:00','Administrador','2013-04-03 04:00:00','Administrador','JSONDATA: \nNOCACHE: Wed Apr 03 2013 08:56:56 GMT-0300 (Hora oficial do Brasil)\nIDCATEGORIASERVICO: 1\nKEYCONTROL: \nDINAMICVIEWSJSON_TEMPDATA: \nIDSITUACAOSERVICO: 1\nIDTIPODEMANDASERVICO: 3\nSIGLAABREV: \nLINKPROCESSO: \nIDBASECONHECIMENTO: \nIDLOCALEXECUCAOSERVICO: 4\nDINAMICVIEWSTABLESVINC: \nDINAMICVIEWSACAOPESQUISASELECIONADA: \nIDSERVICO: 3\nACAOFLUXO: \nMODOEXIBICAO: N\nDETALHESERVICO: Descrição do Serviço 3\nSESSION.NUMERO_CONTRATO_EDICAO: null\nNOMESERVICO: Serviço 3\nDINAMICVIEWSJSON_DATA: \nIDEMPRESA: 1\nDINAMICVIEWSIDVISAOPESQUISASELECIONADA: \nIDFLUXO: \nDESCRICAOPROCESSO: \nJSONMATRIZ: \nDATAINICIO: 01/04/2013\nIDIMPORTANCIANEGOCIO: 10\nDINAMICVIEWSIDVISAO: 17\nIDTIPOSERVICO: 1\nSESSION.DINAMICVIEWS_SAVEINFO: null\nOBJETIVO: \nIDTAREFA: \nDINAMICVIEWSDADOSADICIONAIS: \nDISPPORTAL: S\nIDTIPOEVENTOSERVICO: 33\n'),(6,4,1,1,1,10,1,33,1,4,'Serviço 4','Descrição do Serviço 4','',NULL,'2013-04-01','','',NULL,'S',NULL,NULL,NULL,'',NULL,NULL,NULL,'2013-04-03 04:00:00','Administrador','2013-04-03 04:00:00','Administrador','JSONDATA: \nNOCACHE: Wed Apr 03 2013 08:57:58 GMT-0300 (Hora oficial do Brasil)\nIDCATEGORIASERVICO: 1\nKEYCONTROL: \nDINAMICVIEWSJSON_TEMPDATA: \nIDSITUACAOSERVICO: 1\nIDTIPODEMANDASERVICO: 1\nSIGLAABREV: \nLINKPROCESSO: \nIDBASECONHECIMENTO: \nIDLOCALEXECUCAOSERVICO: 4\nDINAMICVIEWSTABLESVINC: \nDINAMICVIEWSACAOPESQUISASELECIONADA: \nIDSERVICO: 4\nACAOFLUXO: \nMODOEXIBICAO: N\nDETALHESERVICO: Descrição do Serviço 4\nSESSION.NUMERO_CONTRATO_EDICAO: null\nNOMESERVICO: Serviço 4\nDINAMICVIEWSJSON_DATA: \nIDEMPRESA: 1\nDINAMICVIEWSIDVISAOPESQUISASELECIONADA: \nIDFLUXO: \nDESCRICAOPROCESSO: \nJSONMATRIZ: \nDATAINICIO: 01/04/2013\nIDIMPORTANCIANEGOCIO: 10\nDINAMICVIEWSIDVISAO: 17\nIDTIPOSERVICO: 1\nSESSION.DINAMICVIEWS_SAVEINFO: null\nOBJETIVO: \nIDTAREFA: \nDINAMICVIEWSDADOSADICIONAIS: \nDISPPORTAL: S\nIDTIPOEVENTOSERVICO: 33\n'),(7,5,1,1,1,10,1,33,1,4,'Serviço 5','Descrição do Serviço 5','',NULL,'2013-04-01','','',NULL,'S',NULL,NULL,NULL,'',NULL,NULL,NULL,'2013-04-03 04:00:00','Administrador','2013-04-03 04:00:00','Administrador','JSONDATA: \nNOCACHE: Wed Apr 03 2013 09:01:22 GMT-0300 (Hora oficial do Brasil)\nIDCATEGORIASERVICO: 1\nKEYCONTROL: \nDINAMICVIEWSJSON_TEMPDATA: \nIDSITUACAOSERVICO: 1\nIDTIPODEMANDASERVICO: 1\nSIGLAABREV: \nLINKPROCESSO: \nIDBASECONHECIMENTO: \nIDLOCALEXECUCAOSERVICO: 4\nDINAMICVIEWSTABLESVINC: \nDINAMICVIEWSACAOPESQUISASELECIONADA: \nIDSERVICO: 5\nACAOFLUXO: \nMODOEXIBICAO: N\nDETALHESERVICO: Descrição do Serviço 5\nSESSION.NUMERO_CONTRATO_EDICAO: null\nNOMESERVICO: Serviço 5\nDINAMICVIEWSJSON_DATA: \nIDEMPRESA: 1\nDINAMICVIEWSIDVISAOPESQUISASELECIONADA: \nIDFLUXO: \nDESCRICAOPROCESSO: \nJSONMATRIZ: \nDATAINICIO: 01/04/2013\nIDIMPORTANCIANEGOCIO: 10\nDINAMICVIEWSIDVISAO: 17\nIDTIPOSERVICO: 1\nSESSION.DINAMICVIEWS_SAVEINFO: null\nOBJETIVO: \nIDTAREFA: \nDINAMICVIEWSDADOSADICIONAIS: \nDISPPORTAL: S\nIDTIPOEVENTOSERVICO: 33\n');
/*!40000 ALTER TABLE `servico_hist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servicocontrato`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `servicocontrato` (
  `idservicocontrato` bigint(20) NOT NULL,
  `idservico` bigint(20) NOT NULL,
  `idcontrato` int(11) NOT NULL,
  `idcondicaooperacao` int(11) DEFAULT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  `observacao` text,
  `custo` decimal(18,3) DEFAULT NULL,
  `restricoespressup` text,
  `objetivo` text,
  `permiteslanocadinc` char(1) DEFAULT NULL,
  `linkprocesso` varchar(500) DEFAULT NULL,
  `descricaoprocesso` text,
  `tipodescprocess` char(1) DEFAULT NULL,
  `deleted` char(1) DEFAULT NULL,
  `arearequisitante` varchar(150) DEFAULT NULL,
  `idgruponivel1` int(11) DEFAULT NULL,
  `idModeloEmailCriacao` int(11) DEFAULT NULL,
  `idModeloEmailFinalizacao` int(11) DEFAULT NULL,
  `idModeloEmailAcoes` int(11) DEFAULT NULL,
  `idgrupoexecutor` int(11) DEFAULT NULL,
  `idcalendario` int(11) DEFAULT NULL,
  `permSLATempoACombinar` char(1) DEFAULT NULL,
  `permMudancaSLA` char(1) DEFAULT NULL,
  `permMudancaCalendario` char(1) DEFAULT NULL,
  `idgrupoaprovador` int(11) DEFAULT NULL,
  PRIMARY KEY (`idservicocontrato`),
  KEY `fk_reference_38` (`idcondicaooperacao`),
  KEY `fk_reference_40` (`idservico`),
  KEY `fk_reference_41` (`idcontrato`),
  KEY `fk_reference_546` (`idgruponivel1`),
  KEY `fk_reference_547` (`idModeloEmailCriacao`),
  KEY `fk_reference_548` (`idModeloEmailFinalizacao`),
  KEY `fk_reference_549` (`idModeloEmailAcoes`),
  KEY `fk_reference_559` (`idgrupoexecutor`),
  KEY `fk_reference_560` (`idcalendario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicocontrato`
--

LOCK TABLES `servicocontrato` WRITE;
/*!40000 ALTER TABLE `servicocontrato` DISABLE KEYS */;
INSERT INTO `servicocontrato` VALUES (1,1,1,2,'2013-04-01',NULL,'',NULL,'','',NULL,'','',NULL,NULL,'',NULL,17,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL),(2,2,1,2,'2013-04-01',NULL,'',NULL,'','',NULL,'','',NULL,NULL,'',2,17,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL),(3,3,1,2,'2013-04-01',NULL,'',NULL,'','',NULL,'','',NULL,NULL,'',2,17,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL),(4,4,1,2,'2013-04-01',NULL,'',NULL,'','',NULL,'','',NULL,NULL,'',2,17,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL),(5,5,1,2,'2013-04-01',NULL,'',NULL,'','',NULL,'','',NULL,NULL,'',2,17,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `servicocontrato` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servicos`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `servicos` (
  `idServico` bigint(20) NOT NULL AUTO_INCREMENT,
  `nomeServico` varchar(255) DEFAULT NULL,
  `detalhesServico` varchar(255) DEFAULT NULL,
  `objetivo` varchar(255) DEFAULT NULL,
  `passosServico` varchar(255) DEFAULT NULL,
  `dataInicio` varchar(255) DEFAULT NULL,
  `linkProcesso` varchar(255) DEFAULT NULL,
  `descricaoProcesso` varchar(255) DEFAULT NULL,
  `tipoDescProcess` varchar(255) DEFAULT NULL,
  `dispPortal` varchar(255) DEFAULT NULL,
  `quadroOrientPortal` varchar(255) DEFAULT NULL,
  `deleted` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idServico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicos`
--

LOCK TABLES `servicos` WRITE;
/*!40000 ALTER TABLE `servicos` DISABLE KEYS */;
/*!40000 ALTER TABLE `servicos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sistemaoperacional`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sistemaoperacional` (
  `id` int(11) NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='sistemaoperacional';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sistemaoperacional`
--

LOCK TABLES `sistemaoperacional` WRITE;
/*!40000 ALTER TABLE `sistemaoperacional` DISABLE KEYS */;
/*!40000 ALTER TABLE `sistemaoperacional` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `situacaodemanda`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `situacaodemanda` (
  `idsituacaodemanda` int(11) NOT NULL,
  `nomesituacao` varchar(40) NOT NULL,
  PRIMARY KEY (`idsituacaodemanda`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='situacaodemanda';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `situacaodemanda`
--

LOCK TABLES `situacaodemanda` WRITE;
/*!40000 ALTER TABLE `situacaodemanda` DISABLE KEYS */;
INSERT INTO `situacaodemanda` VALUES (1,'Ativo');
/*!40000 ALTER TABLE `situacaodemanda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `situacaoservico`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `situacaoservico` (
  `idsituacaoservico` int(11) NOT NULL,
  `idempresa` int(11) NOT NULL,
  `nomesituacaoservico` varchar(100) NOT NULL,
  `datainicio` date DEFAULT NULL,
  `datafim` date DEFAULT NULL,
  PRIMARY KEY (`idsituacaoservico`),
  KEY `INDEX_SITUACAOEMPRESA` (`idempresa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='situacaoservico';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `situacaoservico`
--

LOCK TABLES `situacaoservico` WRITE;
/*!40000 ALTER TABLE `situacaoservico` DISABLE KEYS */;
INSERT INTO `situacaoservico` VALUES (-999,1,'Em Análise',NULL,NULL),(1,1,'Ativo',NULL,NULL),(2,1,'Inativo',NULL,NULL),(3,1,'Em criação',NULL,NULL),(4,1,'Em desenho',NULL,NULL);
/*!40000 ALTER TABLE `situacaoservico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `slarequisitosla`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `slarequisitosla` (
  `idrequisitosla` int(11) NOT NULL,
  `idacordonivelservico` bigint(20) NOT NULL,
  `datavinculacao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `dataultmodificacao` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `deleted` char(1) DEFAULT NULL,
  PRIMARY KEY (`idrequisitosla`,`idacordonivelservico`),
  KEY `fk_reference_554` (`idacordonivelservico`),
  CONSTRAINT `fk_reference_553` FOREIGN KEY (`idrequisitosla`) REFERENCES `requisitosla` (`idrequisitosla`),
  CONSTRAINT `fk_reference_554` FOREIGN KEY (`idacordonivelservico`) REFERENCES `acordonivelservico` (`idacordonivelservico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `slarequisitosla`
--

LOCK TABLES `slarequisitosla` WRITE;
/*!40000 ALTER TABLE `slarequisitosla` DISABLE KEYS */;
/*!40000 ALTER TABLE `slarequisitosla` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `solicitacaoservico`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `solicitacaoservico` (
  `idsolicitacaoservico` bigint(20) NOT NULL,
  `idfaseatual` bigint(20) DEFAULT NULL,
  `idtipoproblema` bigint(20) DEFAULT NULL,
  `idgrupoatual` int(11) DEFAULT NULL,
  `idprioridade` bigint(20) DEFAULT NULL,
  `idorigem` bigint(20) DEFAULT NULL,
  `idresponsavel` int(11) DEFAULT NULL,
  `idsolicitante` int(11) DEFAULT NULL,
  `atendimentopresencial` char(1) DEFAULT NULL,
  `datahorasolicitacao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `datahoralimite` timestamp NULL DEFAULT NULL,
  `prazohh` smallint(6) DEFAULT NULL,
  `prazomm` smallint(6) DEFAULT NULL,
  `descricao` text NOT NULL,
  `resposta` text,
  `datahorainicio` timestamp NULL DEFAULT NULL,
  `datahorafim` timestamp NULL DEFAULT NULL,
  `situacao` varchar(20) NOT NULL,
  `deleted` char(1) DEFAULT NULL,
  `idcontatosolicitacaoservico` int(11) DEFAULT NULL,
  `idservicocontrato` bigint(20) DEFAULT NULL,
  `idunidade` int(11) DEFAULT NULL,
  `idcausaincidente` int(11) DEFAULT NULL,
  `idcategoriasolucao` int(11) DEFAULT NULL,
  `idtipodemandaservico` int(11) DEFAULT NULL,
  `iditemconfiguracao` int(11) DEFAULT NULL,
  `iditemconfiguracaofilho` int(11) DEFAULT NULL,
  `idsolicitacaopai` int(11) DEFAULT NULL,
  `detalhamentocausa____` text,
  `seqreabertura` int(11) DEFAULT NULL,
  `detalhamentocausa` text,
  `enviaEmailCriacao` char(1) DEFAULT NULL,
  `enviaEmailFinalizacao` char(1) DEFAULT NULL,
  `enviaEmailAcoes` char(1) DEFAULT NULL,
  `idgruponivel1` int(11) DEFAULT NULL,
  `solucaoTemporaria` char(1) DEFAULT NULL,
  `idsolicitacaoservicoorigem` bigint(20) DEFAULT NULL,
  `idCalendario` int(11) DEFAULT NULL,
  `houveMudanca` char(1) DEFAULT NULL,
  `slaACombinar` char(1) DEFAULT NULL,
  `prazohhAnterior` smallint(6) DEFAULT NULL,
  `prazommAnterior` smallint(6) DEFAULT NULL,
  `tempodecorridohh` smallint(6) DEFAULT NULL,
  `tempodecorridomm` smallint(6) DEFAULT NULL,
  `datahorasuspensao` timestamp NULL DEFAULT NULL,
  `datahorareativacao` timestamp NULL DEFAULT NULL,
  `impacto` char(1) DEFAULT NULL,
  `urgencia` char(1) DEFAULT NULL,
  `tempoCapturaHH` smallint(6) DEFAULT NULL,
  `tempoCapturaMM` smallint(6) DEFAULT NULL,
  `tempoAtrasoHH` smallint(6) DEFAULT NULL,
  `tempoAtrasoMM` smallint(6) DEFAULT NULL,
  `tempoAtendimentoHH` smallint(6) DEFAULT NULL,
  `tempoAtendimentoMM` smallint(6) DEFAULT NULL,
  `dataHoraCaptura` timestamp NULL DEFAULT NULL,
  `idbaseconhecimento` int(11) DEFAULT NULL,
  `idacordonivelservico` int(11) DEFAULT NULL,
  `idSolicitacaoRelacionada` int(11) DEFAULT NULL,
  `descricaosemformatacao` text,
  `datahorainiciosla` timestamp NULL DEFAULT NULL,
  `idultimaaprovacao` int(11) DEFAULT NULL,
  `situacaosla` char(1) DEFAULT NULL,
  `datahorasuspensaosla` timestamp NULL DEFAULT NULL,
  `datahorareativacaosla` timestamp NULL DEFAULT NULL,
  `prazocapturahh` int(11) DEFAULT NULL,
  `prazocapturamm` int(11) DEFAULT NULL,
  PRIMARY KEY (`idsolicitacaoservico`),
  KEY `fk_reference_100` (`idtipoproblema`),
  KEY `fk_reference_103` (`idservicocontrato`),
  KEY `fk_reference_104` (`idorigem`),
  KEY `fk_reference_105` (`idresponsavel`),
  KEY `fk_reference_111` (`idsolicitante`),
  KEY `fk_reference_120` (`idfaseatual`),
  KEY `fk_reference_123` (`idunidade`),
  KEY `fk_reference_138` (`idgrupoatual`),
  KEY `fk_reference_95` (`idprioridade`),
  KEY `fk_reference_550` (`idgruponivel1`),
  KEY `fk_reference_531` (`idcausaincidente`),
  KEY `fk_reference_532` (`idcategoriasolucao`),
  KEY `fk_solicitacaoserv_contato` (`idcontatosolicitacaoservico`),
  KEY `fk_reference_561` (`idCalendario`),
  KEY `fk_ref_sol_servData` (`datahorasolicitacao`,`idsolicitante`),
  KEY `fk_ref_sol_servCTRData` (`datahorasolicitacao`,`idservicocontrato`),
  KEY `idbaseconhecimento` (`idbaseconhecimento`),
  KEY `idx_idtipodemandaservico` (`idtipodemandaservico`),
  KEY `idx_iditemconfiguracao` (`iditemconfiguracao`),
  KEY `idx_iditemconfiguracaofilho` (`iditemconfiguracaofilho`),
  KEY `idx_idsolicitacaopai` (`idsolicitacaopai`),
  KEY `idx_idsolicitacaoservicoorigem` (`idsolicitacaoservicoorigem`),
  KEY `idx_idsolicitacaorelacionada` (`idSolicitacaoRelacionada`),
  KEY `idx_idacordonivelservico` (`idacordonivelservico`),
  KEY `fk_solicitacao_aprovacao_idx` (`idultimaaprovacao`),
  CONSTRAINT `fk_solicitacao_aprovacao` FOREIGN KEY (`idultimaaprovacao`) REFERENCES `aprovacaosolicitacaoservico` (`idaprovacaosolicitacaoservico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solicitacaoservico`
--

LOCK TABLES `solicitacaoservico` WRITE;
/*!40000 ALTER TABLE `solicitacaoservico` DISABLE KEYS */;
INSERT INTO `solicitacaoservico` VALUES (1,2,NULL,2,5,1,3,2,NULL,'2013-04-05 13:40:01','2013-04-05 13:40:00',0,0,'Descri&ccedil;&atilde;o do Incidente ou Requisi&ccedil;&atilde;o<br />','','2013-04-05 13:40:01',NULL,'EmAndamento',NULL,1,1,1,NULL,NULL,3,NULL,NULL,NULL,NULL,0,'','S','S',NULL,2,'N',NULL,1,NULL,'S',NULL,NULL,0,0,NULL,NULL,'B','B',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,'Descri\\u00E7\\u00E3o do Incidente ou Requisi\\u00E7\\u00E3o','2013-04-05 13:40:01',NULL,'A',NULL,NULL,0,0),(2,2,NULL,2,5,1,3,2,NULL,'2013-04-05 13:43:39','2013-04-05 13:43:00',0,0,'Descri&ccedil;&atilde;o do Incidente ou Requisi&ccedil;&atilde;o','','2013-04-05 13:43:39',NULL,'EmAndamento',NULL,2,4,1,NULL,NULL,1,NULL,NULL,NULL,NULL,0,'','S','S',NULL,2,'N',NULL,1,NULL,'S',NULL,NULL,0,0,NULL,NULL,'B','B',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,'Descri\\u00E7\\u00E3o do Incidente ou Requisi\\u00E7\\u00E3o','2013-04-05 13:43:39',NULL,'A',NULL,NULL,0,0),(3,2,NULL,3,5,1,2,3,NULL,'2013-04-05 13:46:04','2013-04-05 13:46:00',0,0,'Descri&ccedil;&atilde;o do Incidente ou Requisi&ccedil;&atilde;o','','2013-04-05 13:46:04','2013-04-05 13:46:04','Cancelada',NULL,3,5,1,NULL,NULL,1,NULL,NULL,NULL,NULL,0,'','S','S',NULL,2,'N',NULL,1,NULL,'S',NULL,NULL,0,0,NULL,NULL,'B','B',0,0,0,0,0,0,NULL,NULL,3,NULL,'Descri\\u00E7\\u00E3o do Incidente ou Requisi\\u00E7\\u00E3o','2013-04-05 13:46:04',NULL,'A',NULL,NULL,0,0),(4,2,NULL,3,5,1,2,3,NULL,'2013-04-05 14:13:17','2013-04-05 14:13:00',0,0,'Descri&ccedil;&atilde;o do Incidente ou Requisi&ccedil;&atilde;o<br />','','2013-04-05 14:13:17',NULL,'EmAndamento',NULL,4,5,1,NULL,NULL,1,NULL,NULL,NULL,NULL,0,'','S','S',NULL,2,'N',NULL,1,NULL,'S',NULL,NULL,0,0,NULL,NULL,'B','B',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,'Descri\\u00E7\\u00E3o do Incidente ou Requisi\\u00E7\\u00E3o','2013-04-05 14:13:18',NULL,'A',NULL,NULL,0,0);
/*!40000 ALTER TABLE `solicitacaoservico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `solicitacaoservicoevtmon`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `solicitacaoservicoevtmon` (
  `idsolicitacaoservico` bigint(20) NOT NULL,
  `ideventomonitoramento` int(11) NOT NULL,
  `idrecurso` int(11) DEFAULT NULL,
  `nomehost` varchar(255) DEFAULT NULL,
  `nomeservice` varchar(255) DEFAULT NULL,
  `infoadd` text,
  PRIMARY KEY (`idsolicitacaoservico`,`ideventomonitoramento`),
  KEY `fk_ref_evtmon` (`ideventomonitoramento`),
  KEY `fk_ref_recevtmon` (`idrecurso`),
  CONSTRAINT `fk_ref_evtmon` FOREIGN KEY (`ideventomonitoramento`) REFERENCES `eventomonitoramento` (`ideventomonitoramento`),
  CONSTRAINT `fk_ref_recevtmon` FOREIGN KEY (`idrecurso`) REFERENCES `recurso` (`idrecurso`),
  CONSTRAINT `fk_ref_ssevtmon` FOREIGN KEY (`idsolicitacaoservico`) REFERENCES `solicitacaoservico` (`idsolicitacaoservico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solicitacaoservicoevtmon`
--

LOCK TABLES `solicitacaoservicoevtmon` WRITE;
/*!40000 ALTER TABLE `solicitacaoservicoevtmon` DISABLE KEYS */;
/*!40000 ALTER TABLE `solicitacaoservicoevtmon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `solicitacaoservicomudanca`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `solicitacaoservicomudanca` (
  `idrequisicaomudanca` int(11) DEFAULT NULL,
  `idsolicitacaoservico` bigint(20) DEFAULT NULL,
  KEY `fk_solicita_reference_requisic` (`idrequisicaomudanca`),
  KEY `fk_solicita_reference_solmud` (`idsolicitacaoservico`),
  CONSTRAINT `fk_solicita_reference_requisic` FOREIGN KEY (`idrequisicaomudanca`) REFERENCES `requisicaomudanca` (`idrequisicaomudanca`),
  CONSTRAINT `fk_solicita_reference_solmud` FOREIGN KEY (`idsolicitacaoservico`) REFERENCES `solicitacaoservico` (`idsolicitacaoservico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solicitacaoservicomudanca`
--

LOCK TABLES `solicitacaoservicomudanca` WRITE;
/*!40000 ALTER TABLE `solicitacaoservicomudanca` DISABLE KEYS */;
/*!40000 ALTER TABLE `solicitacaoservicomudanca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `solicitacaoservicoproblema`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `solicitacaoservicoproblema` (
  `idproblema` int(11) DEFAULT NULL,
  `idsolicitacaoservico` int(11) DEFAULT NULL,
  KEY `fk_solicita_reference_solicita` (`idsolicitacaoservico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solicitacaoservicoproblema`
--

LOCK TABLES `solicitacaoservicoproblema` WRITE;
/*!40000 ALTER TABLE `solicitacaoservicoproblema` DISABLE KEYS */;
/*!40000 ALTER TABLE `solicitacaoservicoproblema` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tabfederacaodados`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tabfederacaodados` (
  `nometabela` varchar(255) NOT NULL,
  `chavefinal` varchar(255) NOT NULL,
  `chaveoriginal` varchar(255) NOT NULL,
  `origem` varchar(255) NOT NULL,
  `criacao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ultatualiz` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`nometabela`,`chavefinal`),
  KEY `ix_tab_orig_chvorig` (`origem`,`nometabela`,`chaveoriginal`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tabfederacaodados`
--

LOCK TABLES `tabfederacaodados` WRITE;
/*!40000 ALTER TABLE `tabfederacaodados` DISABLE KEYS */;
/*!40000 ALTER TABLE `tabfederacaodados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `templatesolicitacaoservico`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `templatesolicitacaoservico` (
  `idtemplate` int(11) NOT NULL,
  `identificacao` varchar(40) NOT NULL,
  `nometemplate` varchar(200) NOT NULL,
  `nomeclassedto` varchar(255) NOT NULL,
  `nomeclasseaction` varchar(255) NOT NULL,
  `nomeclasseservico` varchar(255) NOT NULL,
  `urlrecuperacao` varchar(255) NOT NULL,
  `scriptaposrecuperacao` text,
  `habilitadirecionamento` char(1) DEFAULT NULL,
  `habilitasituacao` char(1) DEFAULT NULL,
  `habilitasolucao` char(1) DEFAULT NULL,
  `alturadiv` int(11) DEFAULT NULL,
  `habilitaurgenciaimpacto` char(1) DEFAULT NULL,
  `habilitanotificacaoemail` char(1) DEFAULT NULL,
  `habilitaproblema` char(1) DEFAULT NULL,
  `habilitamudanca` char(1) DEFAULT NULL,
  `habilitaitemconfiguracao` char(1) DEFAULT NULL,
  `habilitasolicitacaorelacionada` char(1) DEFAULT NULL,
  `habilitagravarecontinuar` char(1) DEFAULT NULL,
  PRIMARY KEY (`idtemplate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `templatesolicitacaoservico`
--

LOCK TABLES `templatesolicitacaoservico` WRITE;
/*!40000 ALTER TABLE `templatesolicitacaoservico` DISABLE KEYS */;
INSERT INTO `templatesolicitacaoservico` VALUES (10,'AprovacaoSolicitacaoServico','Aprovaçãoo da Solicitaçãoo de Serviço','br.com.centralit.citcorpore.bean.AprovacaoSolicitacaoServicoDTO','br.com.centralit.citcorpore.ajaxForms.AprovacaoSolicitacaoServico','br.com.centralit.citcorpore.negocio.AprovacaoSolicitacaoServicoServiceEjb','/pages/aprovacaoSolicitacaoServico/aprovacaoSolicitacaoServico.load',NULL,'N','N','N',250,'N','N','N','N','N','N','S');
/*!40000 ALTER TABLE `templatesolicitacaoservico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tempoacordonivelservico`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tempoacordonivelservico` (
  `idacordonivelservico` bigint(20) NOT NULL,
  `idprioridade` bigint(20) NOT NULL,
  `idfase` bigint(20) NOT NULL,
  `tempohh` int(11) NOT NULL,
  `tempomm` int(11) NOT NULL,
  PRIMARY KEY (`idacordonivelservico`,`idprioridade`,`idfase`),
  KEY `fk_reference_50` (`idprioridade`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tempoacordonivelservico`
--

LOCK TABLES `tempoacordonivelservico` WRITE;
/*!40000 ALTER TABLE `tempoacordonivelservico` DISABLE KEYS */;
INSERT INTO `tempoacordonivelservico` VALUES (1,1,1,0,0),(1,1,2,0,0),(1,2,1,0,0),(1,2,2,0,0),(1,3,1,0,0),(1,3,2,0,0),(1,4,1,0,0),(1,4,2,0,0),(1,5,1,0,0),(1,5,2,0,0),(2,1,1,0,0),(2,1,2,0,0),(2,2,1,0,0),(2,2,2,0,0),(2,3,1,0,0),(2,3,2,0,0),(2,4,1,0,0),(2,4,2,0,0),(2,5,1,0,0),(2,5,2,0,0),(3,1,1,0,0),(3,1,2,0,0),(3,2,1,0,0),(3,2,2,0,0),(3,3,1,0,0),(3,3,2,0,0),(3,4,1,0,0),(3,4,2,0,0),(3,5,1,0,0),(3,5,2,0,0);
/*!40000 ALTER TABLE `tempoacordonivelservico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `timers`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `timers` (
  `TIMERID` varchar(80) NOT NULL,
  `TARGETID` varchar(250) NOT NULL,
  `INITIALDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `TIMERINTERVAL` bigint(20) DEFAULT NULL,
  `INSTANCEPK` longblob,
  `INFO` longblob,
  PRIMARY KEY (`TIMERID`,`TARGETID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `timers`
--

LOCK TABLES `timers` WRITE;
/*!40000 ALTER TABLE `timers` DISABLE KEYS */;
/*!40000 ALTER TABLE `timers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `timesheet`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `timesheet` (
  `idtimesheet` int(11) NOT NULL,
  `iddemanda` int(11) DEFAULT NULL,
  `idempregado` int(11) NOT NULL,
  `idprojeto` int(11) DEFAULT NULL,
  `qtdehoras` decimal(18,3) NOT NULL,
  `data` date NOT NULL,
  `custoporhora` decimal(18,3) NOT NULL,
  `detalhamento` text,
  PRIMARY KEY (`idtimesheet`),
  KEY `INDEX_TIMESHEETDEMANDA` (`iddemanda`),
  KEY `INDEX_TIMESHEETEMPREGADO` (`idempregado`),
  KEY `INDEX_TIMESHEETPROJETO` (`idprojeto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='timesheet';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `timesheet`
--

LOCK TABLES `timesheet` WRITE;
/*!40000 ALTER TABLE `timesheet` DISABLE KEYS */;
/*!40000 ALTER TABLE `timesheet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipocomplexidade`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipocomplexidade` (
  `complexidade` char(1) NOT NULL,
  `desctipocomplexidade` varchar(50) NOT NULL,
  PRIMARY KEY (`complexidade`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipocomplexidade`
--

LOCK TABLES `tipocomplexidade` WRITE;
/*!40000 ALTER TABLE `tipocomplexidade` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipocomplexidade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipodemanda`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipodemanda` (
  `idtipodemanda` int(11) NOT NULL,
  `nometipodemanda` varchar(40) NOT NULL,
  PRIMARY KEY (`idtipodemanda`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='tipodemanda';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipodemanda`
--

LOCK TABLES `tipodemanda` WRITE;
/*!40000 ALTER TABLE `tipodemanda` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipodemanda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipodemandaservico`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipodemandaservico` (
  `idtipodemandaservico` int(11) NOT NULL,
  `nometipodemandaservico` varchar(70) NOT NULL,
  `classificacao` char(1) DEFAULT NULL,
  `deleted` char(1) DEFAULT NULL,
  PRIMARY KEY (`idtipodemandaservico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipodemandaservico`
--

LOCK TABLES `tipodemandaservico` WRITE;
/*!40000 ALTER TABLE `tipodemandaservico` DISABLE KEYS */;
INSERT INTO `tipodemandaservico` VALUES (1,'Requisição','R',NULL),(2,'O.S. (Ordem de Serviço)','O',NULL),(3,'Incidente','I',NULL);
/*!40000 ALTER TABLE `tipodemandaservico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipoeventoservico`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipoeventoservico` (
  `idtipoeventoservico` int(11) NOT NULL,
  `nometipoeventoservico` varchar(70) NOT NULL,
  `deleted` char(1) DEFAULT NULL,
  PRIMARY KEY (`idtipoeventoservico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipoeventoservico`
--

LOCK TABLES `tipoeventoservico` WRITE;
/*!40000 ALTER TABLE `tipoeventoservico` DISABLE KEYS */;
INSERT INTO `tipoeventoservico` VALUES (1,'EVENTUAL',NULL),(19,'DIÁRIO',NULL),(30,'SEMANAL',NULL),(31,'MENSAL',NULL),(32,'SEMESTRAL',NULL),(33,'ANUAL',NULL);
/*!40000 ALTER TABLE `tipoeventoservico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipoitemcfgcaracteristica`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipoitemcfgcaracteristica` (
  `id` int(11) NOT NULL,
  `idtipoitemconfiguracao` int(11) NOT NULL,
  `idcaracteristica` int(11) NOT NULL,
  `datainicio` date DEFAULT NULL,
  `datafim` date DEFAULT NULL,
  `nameinfoagente` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `INDEX_TIPOITEMCONFIGURACAO` (`idtipoitemconfiguracao`),
  KEY `INDEX_TIPOCARACTERISTICA` (`idcaracteristica`),
  KEY `INDEX_TIPOCARC` (`idtipoitemconfiguracao`,`idcaracteristica`,`nameinfoagente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='tipoitemcfgcaracteristica';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipoitemcfgcaracteristica`
--

LOCK TABLES `tipoitemcfgcaracteristica` WRITE;
/*!40000 ALTER TABLE `tipoitemcfgcaracteristica` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipoitemcfgcaracteristica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipoitemconfiguracao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipoitemconfiguracao` (
  `idtipoitemconfiguracao` int(11) NOT NULL,
  `idempresa` int(11) NOT NULL,
  `nometipoitemconfiguracao` varchar(255) NOT NULL,
  `tagtipoitemconfiguracao` varchar(50) DEFAULT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  `sistema` char(1) DEFAULT NULL,
  `categoria` int(11) DEFAULT NULL,
  PRIMARY KEY (`idtipoitemconfiguracao`),
  KEY `INDEX_TIPOEMPRESA` (`idempresa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='tipoitemconfiguracao';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipoitemconfiguracao`
--

LOCK TABLES `tipoitemconfiguracao` WRITE;
/*!40000 ALTER TABLE `tipoitemconfiguracao` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipoitemconfiguracao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipomudanca`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipomudanca` (
  `idtipomudanca` int(11) NOT NULL,
  `idtipofluxo` int(11) DEFAULT NULL,
  `idmodeloemailcriacao` int(11) DEFAULT NULL,
  `idmodeloemailfinalizacao` int(11) DEFAULT NULL,
  `idmodeloemailacoes` int(11) DEFAULT NULL,
  `idgrupoexecutor` int(11) DEFAULT NULL,
  `idcalendario` int(11) DEFAULT NULL,
  `nometipomudanca` varchar(100) DEFAULT NULL,
  `datainicio` date DEFAULT NULL,
  `datafim` date DEFAULT NULL,
  PRIMARY KEY (`idtipomudanca`),
  KEY `idtipofluxo` (`idtipofluxo`),
  KEY `idgrupoexecutor` (`idgrupoexecutor`),
  KEY `idcalendario` (`idcalendario`),
  KEY `tipomudanca_ibfk_2` (`idmodeloemailcriacao`),
  KEY `tipomudanca_ibfk_3` (`idmodeloemailfinalizacao`),
  KEY `tipomudanca_ibfk_4` (`idmodeloemailacoes`),
  CONSTRAINT `tipomudanca_ibfk_4` FOREIGN KEY (`idmodeloemailacoes`) REFERENCES `modelosemails` (`idmodeloemail`),
  CONSTRAINT `tipomudanca_ibfk_1` FOREIGN KEY (`idtipofluxo`) REFERENCES `bpm_tipofluxo` (`idtipofluxo`),
  CONSTRAINT `tipomudanca_ibfk_2` FOREIGN KEY (`idmodeloemailcriacao`) REFERENCES `modelosemails` (`idmodeloemail`),
  CONSTRAINT `tipomudanca_ibfk_3` FOREIGN KEY (`idmodeloemailfinalizacao`) REFERENCES `modelosemails` (`idmodeloemail`),
  CONSTRAINT `tipomudanca_ibfk_5` FOREIGN KEY (`idgrupoexecutor`) REFERENCES `grupo` (`idgrupo`),
  CONSTRAINT `tipomudanca_ibfk_6` FOREIGN KEY (`idcalendario`) REFERENCES `calendario` (`idcalendario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipomudanca`
--

LOCK TABLES `tipomudanca` WRITE;
/*!40000 ALTER TABLE `tipomudanca` DISABLE KEYS */;
INSERT INTO `tipomudanca` VALUES (1,7,27,28,26,1,1,'Emergencial','2013-03-14',NULL),(2,6,27,28,26,1,1,'Padrão','2013-04-05',NULL),(3,8,27,28,26,1,1,'Normal','2013-03-15',NULL);
/*!40000 ALTER TABLE `tipomudanca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipoos`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipoos` (
  `idclassificacaoos` int(11) NOT NULL,
  `idcontrato` int(11) DEFAULT NULL,
  `descricao` varchar(150) NOT NULL,
  `detalhamento` text,
  PRIMARY KEY (`idclassificacaoos`),
  KEY `INDEX_TIPOOSCONTRATO` (`idcontrato`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='tipoos';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipoos`
--

LOCK TABLES `tipoos` WRITE;
/*!40000 ALTER TABLE `tipoos` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipoos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipoproblemaatendimento`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipoproblemaatendimento` (
  `idtipoproblema` bigint(20) NOT NULL,
  `idtipoproblemapai` bigint(20) DEFAULT NULL,
  `descricaoproblema` text NOT NULL,
  `situacao` char(1) NOT NULL,
  PRIMARY KEY (`idtipoproblema`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipoproblemaatendimento`
--

LOCK TABLES `tipoproblemaatendimento` WRITE;
/*!40000 ALTER TABLE `tipoproblemaatendimento` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipoproblemaatendimento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipoproduto`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipoproduto` (
  `idtipoproduto` int(11) NOT NULL,
  `idcategoria` int(11) DEFAULT NULL,
  `idunidademedida` int(11) DEFAULT NULL,
  `nomeproduto` varchar(100) NOT NULL,
  `situacao` char(1) NOT NULL,
  `aceitarequisicao` char(1) NOT NULL,
  PRIMARY KEY (`idtipoproduto`),
  KEY `fk_reference_658` (`idcategoria`),
  KEY `fk_reference_664` (`idunidademedida`),
  CONSTRAINT `fk_reference_658` FOREIGN KEY (`idcategoria`) REFERENCES `categoriaproduto` (`idcategoria`),
  CONSTRAINT `fk_reference_664` FOREIGN KEY (`idunidademedida`) REFERENCES `unidademedida` (`idunidademedida`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipoproduto`
--

LOCK TABLES `tipoproduto` WRITE;
/*!40000 ALTER TABLE `tipoproduto` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipoproduto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tiposatividades`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tiposatividades` (
  `idtipoatividade` int(11) NOT NULL,
  `descricaotipoatividade` varchar(50) NOT NULL,
  PRIMARY KEY (`idtipoatividade`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='tiposatividades';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tiposatividades`
--

LOCK TABLES `tiposatividades` WRITE;
/*!40000 ALTER TABLE `tiposatividades` DISABLE KEYS */;
/*!40000 ALTER TABLE `tiposatividades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tiposervico`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tiposervico` (
  `idtiposervico` int(11) NOT NULL,
  `idempresa` int(11) NOT NULL,
  `nometiposervico` varchar(300) NOT NULL,
  `situacao` char(1) NOT NULL,
  PRIMARY KEY (`idtiposervico`),
  KEY `INDEX_TIPOSERVICOEMPRESA` (`idempresa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='tiposervico';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tiposervico`
--

LOCK TABLES `tiposervico` WRITE;
/*!40000 ALTER TABLE `tiposervico` DISABLE KEYS */;
INSERT INTO `tiposervico` VALUES (1,1,'A DEFINIR','A');
/*!40000 ALTER TABLE `tiposervico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tiposoftware`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tiposoftware` (
  `idtiposoftware` int(11) NOT NULL,
  `nome` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`idtiposoftware`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tiposoftware`
--

LOCK TABLES `tiposoftware` WRITE;
/*!40000 ALTER TABLE `tiposoftware` DISABLE KEYS */;
INSERT INTO `tiposoftware` (idtiposoftware, nome) VALUES (1, 'Anti-virus'), (2, 'Auxiliar de escritório'), (3, 'Comunicador Instantâneo'), (4, 'Editor de Imagem'), (5, 'Editor de Texto'), (6, 'Navegador'), (7, 'Sistema Operacional'), (8, 'Outros');
/*!40000 ALTER TABLE `tiposoftware` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipounidade`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipounidade` (
  `idtipounidade` int(11) NOT NULL,
  `idempresa` int(11) NOT NULL,
  `nometipounidade` varchar(80) NOT NULL,
  `datainicio` date DEFAULT NULL,
  `datafim` date DEFAULT NULL,
  PRIMARY KEY (`idtipounidade`),
  KEY `INDEX_TIPOUNIDADEEMPRESA` (`idempresa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='tipounidade';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipounidade`
--

LOCK TABLES `tipounidade` WRITE;
/*!40000 ALTER TABLE `tipounidade` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipounidade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ufs`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ufs` (
  `iduf` int(11) NOT NULL,
  `nomeuf` varchar(50) NOT NULL,
  `siglauf` varchar(2) NOT NULL,
  `idregioes` varchar(45) DEFAULT NULL,
  `idpais` int(11) DEFAULT NULL,
  PRIMARY KEY (`iduf`),
  KEY `fk_ufs` (`idregioes`),
  KEY `fk_uf_pais` (`idpais`),
  CONSTRAINT `fk_uf_pais` FOREIGN KEY (`idpais`) REFERENCES `pais` (`idpais`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ufs';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ufs`
--

LOCK TABLES `ufs` WRITE;
/*!40000 ALTER TABLE `ufs` DISABLE KEYS */;
INSERT INTO `ufs` VALUES (0,' -- Indefinida -- ','--',NULL,1),(1,'Distrito Federal','DF',NULL,1),(2,'São Paulo','SP',NULL,1),(3,'Rio de Janeiro','RJ',NULL,1),(4,'Goiás','GO',NULL,1),(5,'Tocantins','TO',NULL,1),(6,'Minas Gerais','MG',NULL,1),(7,'Paraná','PR',NULL,1),(8,'Espirito Santo','ES',NULL,1),(9,'Acre','AC',NULL,1),(10,'Alagoas','AL',NULL,1),(11,'Amapá','AP',NULL,1),(12,'Amazonas','AM',NULL,1),(13,'Bahia','BA',NULL,1),(14,'Ceará','CE',NULL,1),(15,'Maranhão','MA',NULL,1),(16,'Mato Grosso','MT',NULL,1),(17,'Mato Grosso do Sul','MS',NULL,1),(18,'Paraíba','PB',NULL,1),(19,'Pará','PA',NULL,1),(20,'Pernambuco','PE',NULL,1),(21,'Piauí','PI',NULL,1),(22,'Rio Grande do Norte','RN',NULL,1),(23,'Rio Grande do Sul','RS',NULL,1),(24,'Rondônia','RO',NULL,1),(25,'Roraima','RR',NULL,1),(26,'Santa Catarina','SC',NULL,1),(27,'Sergipe','SE',NULL,1);
/*!40000 ALTER TABLE `ufs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unidade`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unidade` (
  `idunidade` int(11) NOT NULL,
  `idunidadepai` int(11) DEFAULT NULL,
  `idtipounidade` int(11) DEFAULT NULL,
  `idempresa` int(11) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  `descricao` text,
  `email` varchar(40) DEFAULT NULL,
  `idendereco` int(11) DEFAULT NULL,
  `aceitaentregaproduto` char(1) DEFAULT NULL,
  PRIMARY KEY (`idunidade`),
  KEY `INDEX_UNUNIDADEPAI` (`idunidadepai`),
  KEY `INDEX_UNTIPOUNIDADE` (`idtipounidade`),
  KEY `INDEX_UNEMPRESA` (`idempresa`),
  KEY `fk_unid_end` (`idendereco`),
  CONSTRAINT `fk_unid_end` FOREIGN KEY (`idendereco`) REFERENCES `endereco` (`idendereco`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='unidade';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unidade`
--

LOCK TABLES `unidade` WRITE;
/*!40000 ALTER TABLE `unidade` DISABLE KEYS */;
INSERT INTO `unidade` VALUES (1,NULL,NULL,1,'Default','2012-01-01',NULL,'','',NULL,NULL);
/*!40000 ALTER TABLE `unidade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unidadebaseitemconfiguracao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unidadebaseitemconfiguracao` (
  `idbaseitemconfiguracao` int(11) NOT NULL,
  `idunidade` int(11) DEFAULT NULL,
  PRIMARY KEY (`idbaseitemconfiguracao`),
  KEY `INDEX_UNIDADE` (`idunidade`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='unidadebaseitemconfiguracao';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unidadebaseitemconfiguracao`
--

LOCK TABLES `unidadebaseitemconfiguracao` WRITE;
/*!40000 ALTER TABLE `unidadebaseitemconfiguracao` DISABLE KEYS */;
/*!40000 ALTER TABLE `unidadebaseitemconfiguracao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unidademedida`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unidademedida` (
  `idunidademedida` int(11) NOT NULL,
  `nomeunidademedida` varchar(100) NOT NULL,
  `siglaunidademedida` varchar(10) NOT NULL,
  `situacao` char(1) NOT NULL,
  PRIMARY KEY (`idunidademedida`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unidademedida`
--

LOCK TABLES `unidademedida` WRITE;
/*!40000 ALTER TABLE `unidademedida` DISABLE KEYS */;
/*!40000 ALTER TABLE `unidademedida` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unidadesaccservicos`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unidadesaccservicos` (
  `idunidade` int(11) NOT NULL,
  `idservico` bigint(20) NOT NULL,
  PRIMARY KEY (`idunidade`,`idservico`),
  KEY `fk_reference_571` (`idservico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unidadesaccservicos`
--

LOCK TABLES `unidadesaccservicos` WRITE;
/*!40000 ALTER TABLE `unidadesaccservicos` DISABLE KEYS */;
/*!40000 ALTER TABLE `unidadesaccservicos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `urgencia`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `urgencia` (
  `idUrgencia` int(11) NOT NULL,
  `nivelUrgencia` varchar(100) NOT NULL,
  `siglaUrgencia` char(2) DEFAULT NULL,
  PRIMARY KEY (`idUrgencia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `urgencia`
--

LOCK TABLES `urgencia` WRITE;
/*!40000 ALTER TABLE `urgencia` DISABLE KEYS */;
INSERT INTO `urgencia` VALUES (1,'Crítica','C'),(2,'Alta','A'),(3,'Média','M'),(4,'Baixa','B');
/*!40000 ALTER TABLE `urgencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `idusuario` int(11) NOT NULL,
  `idunidade` int(11) DEFAULT NULL,
  `idempregado` int(11) NOT NULL,
  `idempresa` int(11) NOT NULL,
  `login` varchar(256) DEFAULT NULL,
  `nome` varchar(256) DEFAULT NULL,
  `senha` varchar(300) DEFAULT NULL,
  `status` char(1) CHARACTER SET latin1 DEFAULT NULL,
  `ldap` char(1) DEFAULT NULL,
  `ultimoacessoportal` date DEFAULT NULL,
  PRIMARY KEY (`idusuario`),
  KEY `INDEX_USEMPREGADO` (`idempregado`),
  KEY `INDEX_USUNIDADE` (`idunidade`),
  KEY `INDEX_USEMPRESA` (`idempresa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='usuario';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,NULL,1,1,'admin','Administrador','511574e821fa42fb7d31796ab74c1c721098a0df','A',NULL,NULL),(2,NULL,2,1,'default','Default','40bd001563085fc35165329ea1ff5c5ecbdbbeef','A',NULL,NULL),(3,NULL,3,1,'consultor','Consultor','04a991894ac6cdd7ef06d049feb9f0b9cc6c9004','A',NULL,NULL);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarioitemconfiguracao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarioitemconfiguracao` (
  `idusuario` int(11) NOT NULL,
  `iditemconfiguracao` int(11) NOT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  PRIMARY KEY (`idusuario`,`iditemconfiguracao`),
  KEY `INDEX_USUARIOITEMCONFIGURACAO` (`iditemconfiguracao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='usuarioitemconfiguracao';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarioitemconfiguracao`
--

LOCK TABLES `usuarioitemconfiguracao` WRITE;
/*!40000 ALTER TABLE `usuarioitemconfiguracao` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuarioitemconfiguracao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `valor`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `valor` (
  `idvalor` int(11) NOT NULL,
  `iditemconfiguracao` int(11) DEFAULT NULL,
  `idcaracteristica` int(11) DEFAULT NULL,
  `valorstr` varchar(4000) DEFAULT NULL,
  `valorlongo` text,
  `valordecimal` decimal(18,4) DEFAULT NULL,
  `valordate` date DEFAULT NULL,
  `idbaseitemconfiguracao` int(11) DEFAULT NULL,
  PRIMARY KEY (`idvalor`),
  KEY `INDEX_VITEMCONFIGURACAO` (`iditemconfiguracao`),
  KEY `INDEX_VCARACTERISTICA` (`idcaracteristica`),
  KEY `INDEX_VBASEITEMCONFIG` (`idbaseitemconfiguracao`),
  KEY `INDEX_VCONSULTA3` (`iditemconfiguracao`,`idcaracteristica`,`idbaseitemconfiguracao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='valor';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `valor`
--

LOCK TABLES `valor` WRITE;
/*!40000 ALTER TABLE `valor` DISABLE KEYS */;
/*!40000 ALTER TABLE `valor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `valorajusteglosa`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `valorajusteglosa` (
  `idservicocontrato` bigint(20) NOT NULL,
  `idacordonivelservico` bigint(20) NOT NULL,
  `quantidadefalhas` int(11) DEFAULT NULL,
  `valorajuste` decimal(10,2) DEFAULT NULL,
  `deleted` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `valorajusteglosa`
--

LOCK TABLES `valorajusteglosa` WRITE;
/*!40000 ALTER TABLE `valorajusteglosa` DISABLE KEYS */;
/*!40000 ALTER TABLE `valorajusteglosa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `valorservicocontrato`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `valorservicocontrato` (
  `idservicocontrato` bigint(20) DEFAULT NULL,
  `idservico` int(11) DEFAULT NULL,
  `valorServico` decimal(10,2) DEFAULT NULL,
  `datainicio` date DEFAULT NULL,
  `datafim` date DEFAULT NULL,
  `idvalorservicocontrato` int(11) NOT NULL,
  PRIMARY KEY (`idvalorservicocontrato`),
  KEY `fk_reference_665` (`idservicocontrato`),
  CONSTRAINT `fk_reference_665` FOREIGN KEY (`idservicocontrato`) REFERENCES `servicocontrato` (`idservicocontrato`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `valorservicocontrato`
--

LOCK TABLES `valorservicocontrato` WRITE;
/*!40000 ALTER TABLE `valorservicocontrato` DISABLE KEYS */;
/*!40000 ALTER TABLE `valorservicocontrato` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `valorvisaocamposnegocio`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `valorvisaocamposnegocio` (
  `idvalorvisaocamposnegocio` bigint(20) NOT NULL,
  `idgrupovisao` bigint(20) NOT NULL,
  `idcamposobjetonegocio` bigint(20) NOT NULL,
  `valor` varchar(500) NOT NULL,
  `situacao` char(1) NOT NULL,
  `descricao` varchar(500) NOT NULL,
  PRIMARY KEY (`idvalorvisaocamposnegocio`),
  KEY `fk_reference_83` (`idgrupovisao`,`idcamposobjetonegocio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `valorvisaocamposnegocio`
--

LOCK TABLES `valorvisaocamposnegocio` WRITE;
/*!40000 ALTER TABLE `valorvisaocamposnegocio` DISABLE KEYS */;
/*!40000 ALTER TABLE `valorvisaocamposnegocio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `versao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `versao` (
  `idversao` int(11) NOT NULL,
  `nomeversao` varchar(100) NOT NULL,
  `idusuario` int(11) DEFAULT NULL,
  PRIMARY KEY (`idversao`),
  UNIQUE KEY `idversao` (`idversao`),
  UNIQUE KEY `nomeversao` (`nomeversao`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `versao`
--

LOCK TABLES `versao` WRITE;
/*!40000 ALTER TABLE `versao` DISABLE KEYS */;
INSERT INTO `versao` VALUES (1,'2.0.5');
/*!40000 ALTER TABLE `versao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vinculaosincidente`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vinculaosincidente` (
  `idos` int(11) NOT NULL,
  `idsolicitacaoservico` bigint(20) NOT NULL,
  `idatividadesos` bigint(20) NOT NULL,
  PRIMARY KEY (`idos`,`idsolicitacaoservico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vinculaosincidente`
--

LOCK TABLES `vinculaosincidente` WRITE;
/*!40000 ALTER TABLE `vinculaosincidente` DISABLE KEYS */;
/*!40000 ALTER TABLE `vinculaosincidente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vinculovisao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vinculovisao` (
  `idvisaorelacionada` bigint(20) NOT NULL,
  `seq` int(11) NOT NULL,
  `tipovinculo` char(1) NOT NULL,
  `idgrupovisaopai` bigint(20) DEFAULT NULL,
  `idcamposobjetonegociopai` bigint(20) DEFAULT NULL,
  `idgrupovisaofilho` bigint(20) DEFAULT NULL,
  `idcamposobjetonegociofilho` bigint(20) DEFAULT NULL,
  `idcamposobjetonegociopainn` bigint(20) DEFAULT NULL,
  `idcamposobjetonegociofilhonn` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`idvisaorelacionada`,`seq`),
  KEY `fk_vinculov_ref2_grupovis` (`idgrupovisaopai`,`idcamposobjetonegociopai`),
  KEY `fk_vinculov_ref1_grupovis` (`idgrupovisaofilho`,`idcamposobjetonegociofilho`),
  KEY `fk_vinculov_ref1_camposob` (`idcamposobjetonegociopainn`),
  KEY `fk_vinculov_ref2_camposob` (`idcamposobjetonegociofilhonn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vinculovisao`
--

LOCK TABLES `vinculovisao` WRITE;
/*!40000 ALTER TABLE `vinculovisao` DISABLE KEYS */;
/*!40000 ALTER TABLE `vinculovisao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `visao`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `visao` (
  `idvisao` bigint(20) NOT NULL,
  `descricao` varchar(120) NOT NULL,
  `tipovisao` char(2) NOT NULL,
  `situacao` char(1) NOT NULL,
  `classename` varchar(500) DEFAULT NULL,
  `identificador` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idvisao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visao`
--

LOCK TABLES `visao` WRITE;
/*!40000 ALTER TABLE `visao` DISABLE KEYS */;
/*!40000 ALTER TABLE `visao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `visaorelacionada`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `visaorelacionada` (
  `idvisaorelacionada` bigint(20) NOT NULL,
  `idvisaopai` bigint(20) NOT NULL,
  `idvisaofilha` bigint(20) NOT NULL,
  `idobjetonegocionn` bigint(20) DEFAULT NULL,
  `ordem` int(11) NOT NULL,
  `titulo` varchar(500) NOT NULL,
  `situacao` char(1) NOT NULL,
  `tipovinculacao` char(1) NOT NULL,
  `acaoemselecaopesquisa` char(2) DEFAULT NULL,
  PRIMARY KEY (`idvisaorelacionada`),
  KEY `fk_reference_78` (`idvisaopai`),
  KEY `fk_visaorel_ref2_visao` (`idvisaofilha`),
  KEY `fk_reference_90` (`idobjetonegocionn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visaorelacionada`
--

LOCK TABLES `visaorelacionada` WRITE;
/*!40000 ALTER TABLE `visaorelacionada` DISABLE KEYS */;
/*!40000 ALTER TABLE `visaorelacionada` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Trigger DDL Statements

CREATE
TRIGGER `add_current_date_to_slarequisitosla_insert`
BEFORE INSERT ON `slarequisitosla`
FOR EACH ROW
BEGIN  
        SET NEW.dataultmodificacao = CURRENT_TIMESTAMP;   
END;

CREATE
TRIGGER `add_current_date_to_slarequisitosla_update`
BEFORE UPDATE ON `slarequisitosla`
FOR EACH ROW
BEGIN  
        SET NEW.dataultmodificacao = CURRENT_TIMESTAMP;   
END;

-- INDEXACAO ITEM DE CONFIGURACAO

ALTER TABLE historicovalor
ADD INDEX fk_rel_idhistoricoic_idx (idHistoricoIC ASC);

ALTER TABLE historicovalor
ADD CONSTRAINT fk_rel_idhistoricoic
  FOREIGN KEY (idHistoricoIC)
  REFERENCES historicoic (idhistoricoic)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

-- Dump completed on 2013-04-08 17:11:12