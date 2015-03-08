package br.com.centralit.lucene;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.lucene.queryParser.ParseException;

import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.citframework.excecao.LogicException;

public class TesteLucene {

	/**
	 * @param args
	 */
	private static Date formataData(String data) throws Exception {
		if (data == null || data.equals(""))
			return null;

		Date date = null;
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		date = (java.util.Date) formatter.parse(data);
		return date;
	}

	public static void main(String[] args) throws Exception {
		Lucene lucene = new Lucene();
		try {
			BaseConhecimentoDTO baseConhecimentoDTO = new BaseConhecimentoDTO();
			ArrayList<BaseConhecimentoDTO> pesquisa;
			try {
				
				//baseConhecimentoDTO.setIdBaseConhecimento(775); 
				//lucene.excluirBaseConhecimento(baseConhecimentoDTO);
				
				//baseConhecimentoDTO.setTermoPesquisa("Vincular Serviço");
				//baseConhecimentoDTO.setTermoPesquisa("SGRSI Vincular Trabalhador");
				//baseConhecimentoDTO.setTermoPesquisa("Ajuste servidor Web");
				//baseConhecimentoDTO.setTermoPesquisa("SGF SAPF Atualização Dados Empresa");
				//baseConhecimentoDTO.setTermoPesquisa("Outlook não funciona");
				//baseConhecimentoDTO.setTermoPesquisa("este som desenvolveu-se");
				//baseConhecimentoDTO.setTermoPesquisa("teste3333");
				//baseConhecimentoDTO.setTermoPesquisa("teste3333 + v1.2");
				//baseConhecimentoDTO.setTermoPesquisa("Proxy configuração CentralIT");
				//baseConhecimentoDTO.setTermoPesquisa("E-DOC- Não Consta a regional que deseja peticionar - v1.5");
				//baseConhecimentoDTO.setTermoPesquisa("@#$%¨?*?KMNJBHJB?_???`?? >< `?_?_??°ºªÇ??º?*???? ¬¢£³²¹§?=°ºª????");
				//baseConhecimentoDTO.setTermoPesquisa("Não Consta a regional que deseja peticionar");
				//baseConhecimentoDTO.setTermoPesquisa("erro87");
				//baseConhecimentoDTO.setTermoPesquisa("o usuario informa que nao esta conseguindo realizar a assinatura e nem o envio do peticionamento ele preenche todos os campos do documento do edoc mas quando ele clica em assinar documento o sistema fica com o botao pressionado e nao sai desta tela pelo o internet explorer, foi realizado a limpeza do estado ssl, redefinição do internet explorer, a desinstalação da versao mais atual do java e assim mesmo nao foi possivel realizar a assinatura do documento, foi também tentado fazer o petcionamento e mesmo assim o assinador trava, a versao utilizada foi a 7 update 13 \"!@#$%¨&*()_+{}`^?:><,,.;/;^}`{_+_)(°ºªÇ~]º^*-/-+.+¬¢£³²¹§-=°ºª\\\\\\\\");
				baseConhecimentoDTO.setTermoPesquisa("Som não funciona");
				
				//baseConhecimentoDTO.setMedia("0");
				//baseConhecimentoDTO.setGerenciamentoDisponibilidade("N");
				//baseConhecimentoDTO.setIdUsuarioAutorPesquisa(20);
				//baseConhecimentoDTO.setIdUsuarioAprovadorPesquisa(0);
				//baseConhecimentoDTO.setDataInicioPesquisa(new java.sql.Date(formataData("07/01/2013").getTime()));
				//baseConhecimentoDTO.setDataFimPesquisa(new java.sql.Date(formataData("08/01/2013").getTime()));
				//baseConhecimentoDTO.setDataInicioPublicacao(new java.sql.Date(formataData("30/12/1969").getTime()));
				//baseConhecimentoDTO.setDataFimPublicacao(new java.sql.Date(formataData("01/01/1970").getTime()));
				//baseConhecimentoDTO.setDataInicioExpiracao(new java.sql.Date(formataData("31/05/2013").getTime()));
				//baseConhecimentoDTO.setDataFimExpiracao(new java.sql.Date(formataData("01/06/2013").getTime()));
				//baseConhecimentoDTO.setIdPasta(81);
		
				pesquisa = lucene.pesquisaBaseConhecimento(baseConhecimentoDTO);
				
				System.out.println("INICIO");
				for (BaseConhecimentoDTO baseConhecimento : pesquisa) {
					System.out.println("------------------------------------------------------------------------------------------");
					System.out.println("id: " + baseConhecimento.getIdBaseConhecimento());
					System.out.println("titulo: " + baseConhecimento.getTitulo());
					System.out.println("conteudo: " + baseConhecimento.getConteudo());
					System.out.println("avaliacao: " + baseConhecimento.getMedia());
					System.out.println("gerdisponibilidade: " + baseConhecimento.getGerenciamentoDisponibilidade());
					System.out.println("idusuarioautor: " + baseConhecimento.getIdUsuarioAutor());
					System.out.println("idusuarioaprovador: " + baseConhecimento.getIdUsuarioAprovador());
					System.out.println("datainicio: " + baseConhecimento.getDataInicio());
					System.out.println("datapublicacao: " + baseConhecimento.getDataPublicacao());
					System.out.println("dataexpiracao: " + baseConhecimento.getDataExpiracao());
					System.out.println("idpasta: " + baseConhecimento.getIdPasta());
					System.out.println("cliques: " + baseConhecimento.getContadorCliques());
				}
				System.out.println("FIM.");
				/*
				 * ArrayList<String> pesquisa = lucene.pesquisaPalavrasGemeas("backup"); for (String palavra : pesquisa) { System.out.println(palavra); }
				 */
			} catch (LogicException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
