package br.com.citframework.util;

    import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.centralit.citcorpore.util.CITCorporeUtil;

    /**
     * Tool to run database scripts
     */
    public class ScriptRunner {

        private static final String DEFAULT_DELIMITER = ";";

        private Connection connection;

        private boolean stopOnError;
        private boolean autoCommit;

        private PrintWriter logWriter = new PrintWriter(System.out);
        private PrintWriter errorLogWriter = new PrintWriter(System.err);

        private String delimiter = DEFAULT_DELIMITER;
        private boolean fullLineDelimiter = false;

        /**
         * Default constructor
         */
        public ScriptRunner(Connection connection, boolean autoCommit,
                boolean stopOnError) {
            this.connection = connection;
            this.autoCommit = autoCommit;
            this.stopOnError = stopOnError;
        }

        public void setDelimiter(String delimiter, boolean fullLineDelimiter) {
            this.delimiter = delimiter;
            this.fullLineDelimiter = fullLineDelimiter;
        }

        public void setLogWriter(PrintWriter logWriter) {
            this.logWriter = logWriter;
        }

        public void setErrorLogWriter(PrintWriter errorLogWriter) {
            this.errorLogWriter = errorLogWriter;
        }

        public void runScript(File reader) throws IOException, SQLException {
            try {
                boolean originalAutoCommit = connection.getAutoCommit();
                try {
                    if (originalAutoCommit != this.autoCommit) {
                        connection.setAutoCommit(this.autoCommit);
                    }
                    runScript(connection, reader);
                } finally {
                    connection.setAutoCommit(originalAutoCommit);
                }
            } catch (IOException e) {
                throw e;
            } catch (SQLException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException("Error running script.  Cause: " + e, e);
            }
        }
        
        public List<String> readScript(File inputFile) throws IOException, SQLException {
        	// Create scanner  
    	    Scanner scanner;  
    	    List<String> strings = new ArrayList<String>();
    	    StringBuilder command = null;
    	    try {  
	    		scanner = new Scanner(inputFile,"UTF-8").useDelimiter(this.getDelimiter());  
    	        while(scanner.hasNext()) {
		        	  if (command == null) {
	                      command = new StringBuilder();
	                  }
    	        	String str = scanner.next();
    	        	/*Validação para o PostGreSQL - delimiter de função*/
    	        	if(str.startsWith("\nCREATE FUNCTION")) {
    	        		command.append(str);
    	        		scanner.useDelimiter("(')");
    	        	} else {
    	        		if(command.toString().equals("")){   
    	        			
    	        			/*
    	        			 * Foi adicionando um tratamento especifico para criação de triggers, a trigger é criada apenas no Oracle
    	        			 * devido a algumas valiações é removido o ultimo caracter ';' da ultima sentença 'END', apos a função de leitura
    	        			 * de arquivo montar o bloco da trigger de forma correta é adicionado um ';' 
    	        			 * 
    	        			 * Foi tratado nesse local, devido ao mecanismo está em funcionando estavel, e uma alteração no mecanismo geral pode
    	        			 * causar impactos.
    	        			 * 
    	        			 *   @author Ezequiel
    	        			 * 
    	        			 */
    	        			if (str.contains("CREATE OR REPLACE TRIGGER") || str.contains("CREATE TRIGGER")){
    	        				
    	        				str = str + ";";
        	        			
        	        			strings.add(str);
        	        			
    	        			}else{
    	        				
    	        				strings.add(str);
    	        			}
    	        			
    	        		}
    	        		else
    	        			strings.add(command.toString() + str + "'");
    	        		scanner.useDelimiter("(;(\r)?\n)|(--\n)");
    	        		command = null;
    	        	} 
    	        }
    	    } catch (FileNotFoundException e1) {  
    	        e1.printStackTrace();  
    	    }  
    	   
    	    return strings;
    	  
        }

        private void runScript(Connection conn, File inputFile) throws IOException, SQLException {
            StringBuilder command = null;
            Statement statement = null;
            final int batchSize = 1000;
            int count = 0;
            try {
            	statement = conn.createStatement();
            	
            	if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.MYSQL)) 
            		statement.execute("SET FOREIGN_KEY_CHECKS=0");
            	else if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL))
            		statement.execute("SET CONSTRAINTS ALL DEFERRED");
            	
            	List<String> lines = readScript(inputFile);
                for (String line : lines) {
					
                    if (command == null) {
                        command = new StringBuilder();
                    }
                    String trimmedLine = line.trim();

            		if (trimmedLine.startsWith("/*!") && trimmedLine.endsWith("*/")) {
                        println(trimmedLine);
                    } else if (trimmedLine.length() < 1  || trimmedLine.startsWith("//")) {
                    	println(trimmedLine);
                    } else if (trimmedLine.startsWith("\\n\\n")) {
                    	println(trimmedLine);
                    } else if (trimmedLine.startsWith("'")) {
                    	println(trimmedLine);
                    } else if(CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE) &&
            				(trimmedLine.startsWith("--") || trimmedLine.startsWith("\n/\n--") || trimmedLine.startsWith("\n\n/\n--"))&& (trimmedLine.endsWith("--") || trimmedLine.endsWith(""))) {
            			println(trimmedLine);            			
            		} else if(trimmedLine.startsWith("REM INSERTING into") || trimmedLine.startsWith("SET DEFINE")) {
	                	println(trimmedLine);
	                } else { 
                    	command.append(" ");
                        command.append(line);
                        command.append(" ");

                        println(command);
                	   if((CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL) && command.toString().contains("ALTER TABLE"))
                			   || (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE) && command.toString().contains("NOT NULL ENABLE"))
                			   || (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER) && command.toString().contains("CREATE TRIGGER"))) {
                		   Statement st = null;
                		   try{
                			   st = conn.createStatement();
                			   st.execute(command.toString());
                		   }catch(Exception e) {
                			   e.printStackTrace();
                		   }finally {
                			   if (st != null) {  
               	                try {  
               	                	st.close();  
               	                } catch (SQLException e) {  
               	                    e.printStackTrace();  
               	                }  
               	            }  
                			   st = null;               			   
                		   }
                		   
                	   } else {
                    	   statement.addBatch(command.toString());
                	   }
                       
                        if(++count % batchSize == 0) {
                        	try {
                        		statement.executeBatch();
                        		
                        		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.MYSQL)) 
                        			statement.execute("SET FOREIGN_KEY_CHECKS=1");
                        		statement.close(); 
                        		statement = conn.createStatement();
                        		
                        		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.MYSQL)) 
                            		statement.execute("SET FOREIGN_KEY_CHECKS=0");
                        		else if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL))
                        			statement.execute("SET CONSTRAINTS ALL DEFERRED");
                                 
                        		if (autoCommit && !conn.getAutoCommit()) {
                                    conn.commit();
                                }
	                        } catch (Exception s) {
                        		s.fillInStackTrace();
                        		printlnError("Error executing: " + command);
                        		printlnError(s);
	                        }	
                        }
                        command = null;
                    } 
                }
                statement.executeBatch();
                if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.MYSQL)) 
            		statement.execute("SET FOREIGN_KEY_CHECKS=1");
                //limpa o objeto stmt
                statement.clearBatch();

                if (!autoCommit) {
                    conn.commit();
                }
            } catch ( Exception e) {
                e.fillInStackTrace();
                printlnError("Error executing: " + command);
                printlnError(e);
            } finally {
            	  // Release resources  
	            if (statement != null) {  
	                try {  
	                	statement.close();  
	                } catch (SQLException e) {  
	                    e.printStackTrace();  
	                }  
	            }  
	            statement = null;  
	            conn.close();
	            conn = null;
                flush();
            }
        }

        private String getDelimiter() {
            return delimiter;
        }

        private void println(Object o) {
            if (logWriter != null) {
                logWriter.println(o);
            }
        }

        private void printlnError(Object o) {
            if (errorLogWriter != null) {
                errorLogWriter.println(o);
            }
        }

        private void flush() {
            if (logWriter != null) {
                logWriter.flush();
            }
            if (errorLogWriter != null) {
                errorLogWriter.flush();
            }
        }
        
    }
