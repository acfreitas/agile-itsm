package br.com.citframework.log;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.dto.LogDados;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTratamentoArquivos;

/**
 * @author ronnie.lopes
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class DbLogArquivo implements Log {
	
	private String pathArqBancoLog;
	private String fileArqBancoLog;
	private String extArqBancoLog;
	
	public DbLogArquivo(){
		inicializarParametros();
	}
	
	/**Inicializa o DbLogArquivo com o caminho da gravação do arquivo, nome do arquivo, e extensão do arquivo
	 * @author ronnie.lopes
	 */
	private void inicializarParametros(){
		try {
			
			pathArqBancoLog = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.PATH_ARQ_BANCO_LOG, "C:/Program Files/jboss/server/default/deploy/CitCorpore.war/logBancoDados");
			fileArqBancoLog = "/log_DB_citsmart";
			extArqBancoLog = "txt";

		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**Monta a estrutura do log(nome do arquivo e mensagem) e envia para registrar no arquivo TXT
	 * @author ronnie.lopes
	 */
	public void registraLog(String mensagem, Class classe, String tipoMensagem) throws Exception {
		Date dataAtual = UtilDatas.getDataAtual();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nomeArquivo = pathArqBancoLog + fileArqBancoLog + "_" + sdf.format(dataAtual) + "." + extArqBancoLog;
		manterPastaArqUltimoCincoDias(pathArqBancoLog);
		synchronized (nomeArquivo) {
			List lista = new ArrayList();
			lista.add("["+tipoMensagem+"] - "+classe.getName()+" - "+mensagem);
			
			UtilTratamentoArquivos.geraFileTXT(nomeArquivo, lista, true);
		}
	}
	
	/**Monta a estrutura do log com tratamento de excessão
	 * @author ronnie.lopes
	 */
	public void registraLog(Exception e, Class classe, String tipoMensagem) throws Exception {
		Date dataAtual = UtilDatas.getDataAtual();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nomeArquivo = pathArqBancoLog + fileArqBancoLog + "_" + sdf.format(dataAtual) + "." + extArqBancoLog;
		manterPastaArqUltimoCincoDias(pathArqBancoLog);
		List lista = new ArrayList();
		lista.add("["+tipoMensagem+"] - "+sdf.format(dataAtual)+" - "+classe.getName()+" - Exception:");
		synchronized (nomeArquivo) {
			FileOutputStream fos = new FileOutputStream(nomeArquivo, true);
			PrintStream out = new PrintStream(fos);
			UtilTratamentoArquivos.geraFileTXT(nomeArquivo, lista, out);		
			e.printStackTrace(out);
			try{
				fos.close();
			}catch(Exception e1){
				System.out.println("Erro ao fechar arquivo de log: "+nomeArquivo); 
				e1.printStackTrace();
			}
		}
	}
	
	/**Verifica se o arquivo é do DbLogArquivo e Mantém apenas os arquivos dos últimos 5 dias,
	 * excluindo o arquivo que foi criado a 6 dias atrás referente a data atual
	 * @author ronnie.lopes
	 */
	public void manterPastaArqUltimoCincoDias(String localArquivo) {
		File localArq = new File(localArquivo);
		File[] arquivosPasta = localArq.listFiles(new FileFilter() {  
            public boolean accept(File pathname) {  
                return pathname.getName().toLowerCase().contains("log_db_citsmart");
            }
		});
		
		if(arquivosPasta != null && arquivosPasta.length > 0 && arquivosPasta.length > 6 && arquivosPasta != null) {
			Date dataAtual = UtilDatas.getDataAtual();
			
			for (File arquivo : arquivosPasta) {
				File[] arquivosPastaCont = localArq.listFiles(new FileFilter() {  
		            public boolean accept(File pathname) {  
		                return pathname.getName().toLowerCase().contains("log_db_citsmart");
		            }
				});
 				if(arquivosPastaCont.length > 6) {
					long milesegDataModifArq = arquivo.lastModified();
					Date dataModifArq = new Date(milesegDataModifArq);
					Integer difDiasDatas = UtilDatas.dataDiff(dataModifArq, dataAtual);
			
					if(difDiasDatas > 5) {
						arquivo.delete();
					}
				}else{
					return;
				}
			}
		}
	}
	
	/**Formata em string o objeto logDados
	 * @author ronnie.lopes
	 * @throws Exception 
	 */
	public String formatarStringLogDados(LogDados logDados) throws Exception {
		StringBuilder texto = new StringBuilder();
		
		if(logDados != null) {
			
			texto.append(" Data Hora Atualização: " + logDados.getDtAtualizacao() + " | "
			+			 " Operação: " + logDados.getOperacao() + " | "
			+			 " Dados: " + logDados.getDados() + " | "
			+			 " Id Usuário: " + logDados.getIdUsuario() + " | "
			+			 " Nome Usuário: " + logDados.getNomeUsuario() + " | "
			+			 " Local Origem: " + logDados.getLocalOrigem() + " | "
			+			 " Nome Tabela: " + logDados.getNomeTabela());
			return texto.toString();
			
		}else{
			return null;
		}
	}
}
