package br.com.centralit.citcorpore.comm.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ThreadTrataComunicacao extends Thread {
	private Socket socketClient = null;
    private String dadoRecebido;
    private boolean inventory;
    private boolean evento;
    private boolean desligar;
    private boolean reiniciar;
    private boolean hibernar;
    private boolean comando;
    private String linhaDeComando;
    private String comandoEvento;
    private String[] diretorioRaiz;

    /**
     * Construtor.
     * 
     * @param sockClientParm
     */
    public ThreadTrataComunicacao(Socket sockClientParm) {
    	this.socketClient = sockClientParm;
    }
    
    public void run() {
    	// Verifica a exist�ncia do socket cliente
    	if (getSocketClient() != null) {
    		// Repete indefinidamente
    		while (true) {
    			// a tentativa de
    			try {
    				// verificar se o socket cliente n�o est� fechado e est� conectado
    				if ( !this.getSocketClient().isClosed() && this.getSocketClient().isConnected() ) {
    					BufferedReader entrada = null;
    					// Tenta
    					try {
        					// cria um buffer para armazenar os dados provenientes do fluxo de entrada do socket cliente
    						entrada = new BufferedReader(new InputStreamReader(getSocketClient().getInputStream() ) );
    					} catch (IOException e) {
    						break;
    					}
    					this.analisarParametroRecebido(entrada);
    				} else {
    					return;
    				}
    			} catch (IOException e) {
    				break;
    			}
    			// verifica se � UMA REALIZA��O DE INVENT�RIO
    			if (this.isInventory() ) {
    				// tenta
    				try {
    					// criar um fluxo de dados (com os dados do fluxo de sa�da do socket cliente) direcionado para a sa�da padr�o
    					PrintStream ps = new PrintStream(getSocketClient().getOutputStream() );
    					// cria uma refer�ncia para o diret�rio do usu�rio no sistema
    					File diretorio = new File(System.getProperty("user.dir") );
    					// itera atrav�s da cole��o de arquivos
    					for (int i = 0; i < diretorio.listFiles().length; i++) {
    						// e verifica se o arquivo atual n�o possui a extens�o .xml, .XML ou .ocs
    						if ( (!diretorio.listFiles()[i].getName().endsWith(".xml") ) && (!diretorio.listFiles()[i].getName().endsWith(".XML") )
    								&& (!diretorio.listFiles()[i].getName().endsWith(".ocs")))
        						// se verdade vai para a pr�xima itera��o
    							continue;
    						// sen�o obt�m o caminho completo at� o arquivo no sistema de arquivos
    						File f = new File(diretorio.listFiles()[i].getAbsolutePath());
    						// remove o arquivo (arquivo associado a um invent�rio anterior)
    						f.delete();
    					}
    					// tenta
    					try {
    						File diretorioCorrente = new File(System.getProperty("user.dir") + File.separator + "OCS");
    						this.setDiretorioRaiz(System.getProperty("user.dir").split("\\\\"));
    						Runtime.getRuntime().exec(diretorioCorrente.getAbsolutePath().replace('\\', '/') + "/OCSInventory.exe /local=" 
    						+ this.getDiretorioRaiz()[0] + File.separator + " /xml=" + this.getDiretorioRaiz()[0] + File.separator);
    					} catch (IOException localIOException1) {
    					}
    					File diretorioaux = null;
    					boolean x = true;
    					int contador = 0;
    					while (x) {
    						sleep(5000L);
    						diretorioaux = new File(this.getDiretorioRaiz()[0] + File.separator);
    						for (int i = 0; i < diretorioaux.listFiles().length; i++) {
    							if ( (diretorioaux.listFiles()[i].getName().endsWith(".xml") ) || 
    									(diretorioaux.listFiles()[i].getName().endsWith(".XML") ) ) {
    								String textoXML = getStringTextFromFileTxt(diretorioaux.listFiles()[i].getAbsolutePath());
    								textoXML = textoXML.replaceAll("\n", "");
    								textoXML = textoXML.replaceAll("\r", "");
    								ps.println(textoXML);
    								x = false;
    								break;
    							}
    						}
    						contador += 1;
    						if (contador == 10) {
    							x = false;
    						}
    					}
    				} catch (Exception e) {
    				}
    				try {
    					sleep(5000L);
    				} catch (InterruptedException e) {
    				}
    				break;
    			}
    			// verifica se � UM EVENTO DE INSTALA��O OU DESINSTALA��O
    			if (this.isEvento() ) {
				// tenta
    				try {
    					// executar o comando associado ao evento
    					Runtime.getRuntime().exec(this.getComandoEvento() );
    					// Cria um fluxo de impress�o com o fluxo de sa�da do socket cliente
    					PrintStream ps = new PrintStream(getSocketClient().getOutputStream());
    					ps.println(true);
    				} catch (IOException e) {
    					System.out.println(e);
    				}
    				try {
    					sleep(5000L);
    				} catch (InterruptedException e) {
    				}
    				break;
    			}
    			// DESLIGAR
    			if (this.isDesligar() ) {
    				try {
    					// shutdown -s
    					Runtime.getRuntime().exec("init 0");
    					sleep(5000L);
    				} catch (IOException e) {
    				} catch (InterruptedException e) {
    				}
    				break;
    			}
    			// REINICIAR
    			if (this.isReiniciar() ) {
    				try {
    					Runtime.getRuntime().exec("shutdown -r");
    					sleep(5000L);
    				} catch (IOException e) {
    				} catch (InterruptedException e) {
    				}
    				break;
    			}
    			// HIBERNAR
    			if (this.isHibernar() ) {
    				try {
    					Runtime.getRuntime().exec("shutdown -h");
    					sleep(5000L);
    				} catch (IOException e) {
    				} catch (InterruptedException e) {
    				}
    				break;
    			}
    			// LINHA DE COMANDO
    			if (this.isComando() ) {
    				try {
    					Runtime.getRuntime().exec(this.getLinhaDeComando());
    					sleep(5000L);
    				} catch (IOException e) {
    				} catch (InterruptedException e) {
    				}
    				break;
    			}
    			// LISTAR PROCESSOS DA M�QUINA EM ATIVIDADE
    			// LISTAR SERVI�OS DA M�QUINA EM ATIVIDADE
    		}
    	} else {
    		return;
    	}
    }

    /**
     * Analisa o Par�metro recebido: INVENTORY, INSTALAR/DESINSTALAR, DESLIGAR, REINICIAR, HIBERNAR, COMANDO
     * 
     * @param entrada
     * @throws IOException
     * @author valdoilo.damasceno
     */
    private void analisarParametroRecebido(BufferedReader entrada) throws IOException {
    	if(entrada != null){
    		String stringAux = entrada.readLine();
    		if(stringAux != null){
    			this.setDadoRecebido(new String(stringAux.getBytes()));
    		}
    	}
    	
    	while (true) {
    		if (this.getDadoRecebido().indexOf("INVENTORY") >= 0) {
    			this.setInventory(true);
    			break;
    		}
    		if (this.getDadoRecebido().indexOf("INSTALAR/DESINSTALAR") >= 0) {
    			String[] arrayDadoRecebido = this.getDadoRecebido().split(", ");
    			this.setComandoEvento(arrayDadoRecebido[1].replace("]", ""));
    			this.setEvento(true);
    			break;
    		}
    		if (this.getDadoRecebido().indexOf("DESLIGAR") >= 0) {
    			this.setDesligar(true);
    			break;
    		}
    		if (this.getDadoRecebido().indexOf("REINICIAR") >= 0) {
    			this.setReiniciar(true);
    			break;
    		}
    		if (this.getDadoRecebido().indexOf("HIBERNAR") >= 0) {
    			this.setHibernar(true);
    			break;
    		}
    		if (!this.isInventory() && !isEvento() && !isDesligar() && !isReiniciar() && !isHibernar() ) {
    			this.setLinhaDeComando(this.getDadoRecebido());
    			this.setComando(true);
    			break;
    		}
    	}
    }

    public static String getStringTextFromFileTxt(String arquivo) {
    	String retorno = "";
    	try {
    		FileInputStream arq = new FileInputStream(arquivo);
    		BufferedReader br = new BufferedReader(new InputStreamReader(arq, "ISO-8859-1") );
    		while (br.ready() ) {
    			retorno = retorno + br.readLine() + "\n";
    		}
    		br.close();
    		arq.close();
    	} catch (FileNotFoundException e) {
    	} catch (IOException e) {
    	}
    	return retorno;
    }

    /** 
     * @return valor do atributo socketClient.
     */
    public Socket getSocketClient() {
    	return this.socketClient;
    }

    /**
     * @return valor do atributo inventory.
     */
    public boolean isInventory() {
    	return inventory;
    }

    /**
     * Define valor do atributo inventory.
     * 
     * @param inventory
     */
    public void setInventory(boolean inventory) {
    	this.inventory = inventory;
    }

    /**
     * @return valor do atributo evento.
     */
    public boolean isEvento() {
    	return evento;
    }

    /**
     * Define valor do atributo evento.
     * 
     * @param evento
     */
    public void setEvento(boolean evento) {
    	this.evento = evento;
    }

    /**
     * @return valor do atributo dadoRecebido.
     */
    public String getDadoRecebido() {
    	return dadoRecebido;
    }

    /**
     * Define valor do atributo dadoRecebido.
     * 
     * @param dadoRecebido
     */
    public void setDadoRecebido(String dadoRecebido) {
    	this.dadoRecebido = dadoRecebido;
    }

    /**
     * @return valor do atributo diretorioRaiz.
     */
    public String[] getDiretorioRaiz() {
    	return diretorioRaiz;
    }

    /**
     * Define valor do atributo diretorioRaiz.
     * 
     * @param diretorioRaiz
     */
    public void setDiretorioRaiz(String[] diretorioRaiz) {
    	this.diretorioRaiz = diretorioRaiz;
    }

    /**
     * @return valor do atributo comandoEvento.
     */
    public String getComandoEvento() {
    	return comandoEvento;
    }

    /**
     * Define valor do atributo comandoEvento.
     * 
     * @param comandoEvento
     */
    public void setComandoEvento(String comandoEvento) {
    	this.comandoEvento = comandoEvento;
    }

    /**
     * @return valor do atributo desligar.
     */
    public boolean isDesligar() {
    	return desligar;
    }

    /**
     * Define valor do atributo desligar.
     * 
     * @param desligar
     */
    public void setDesligar(boolean desligar) {
    	this.desligar = desligar;
    }

    /**
     * @return valor do atributo reiniciar.
     */
    public boolean isReiniciar() {
    	return reiniciar;
    }

    /**
     * Define valor do atributo reiniciar.
     * 
     * @param reiniciar
     */
    public void setReiniciar(boolean reiniciar) {
    	this.reiniciar = reiniciar;
    }

    /**
     * @return valor do atributo hibernar.
     */
    public boolean isHibernar() {
    	return hibernar;
    }

    /**
     * Define valor do atributo hibernar.
     * 
     * @param hibernar
     */
    public void setHibernar(boolean hibernar) {
    	this.hibernar = hibernar;
    }

    /**
     * @return valor do atributo linhaDeComando.
     */
    public String getLinhaDeComando() {
    	return linhaDeComando;
    }

    /**
     * Define valor do atributo linhaDeComando.
     * 
     * @param linhaDeComando
     */
    public void setLinhaDeComando(String linhaDeComando) {
    	this.linhaDeComando = linhaDeComando;
    }

    /**
     * @return valor do atributo comando.
     */
    public boolean isComando() {
    	return comando;
    }

    /**
     * Define valor do atributo comando.
     * 
     * @param comando
     */
    public void setComando(boolean comando) {
    	this.comando = comando;
    }
}
