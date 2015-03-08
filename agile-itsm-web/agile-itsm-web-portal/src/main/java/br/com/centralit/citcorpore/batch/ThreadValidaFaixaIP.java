package br.com.centralit.citcorpore.batch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import br.com.centralit.citcorpore.comm.server.IPAddress;

public class ThreadValidaFaixaIP extends Thread {
	public static int NUMERO_THREADS = 5;
	private IPAddress ip1 = null;
	private IPAddress ip2 = null;	
	private ExecutorService exService;
	private boolean caiFora = false;
	
	@Override
	public void run() {
		
		System.out.println("CITSMART - Discovery: ip1 = " + ip1 + " ao ip2 = " + ip2);
			
		while(true) {
			
			exService = Executors.newFixedThreadPool(NUMERO_THREADS);
			IPAddress ipAux = new IPAddress(ip1.getValue());
			
			int qtde = 0;
	        do {
	        	if (!MonitoraAtivosDiscovery.iniciouDiscovery){
	        		caiFora = true;
	        		break;
	        	}
	        	if (!MonitoraDiscoveryIP.hsmAddressDiscovery.containsKey(ipAux.toString())) {
	        		exService.submit(new RunnableThread(ipAux), "done");
	        		qtde++;
	        	}
	        	if (qtde > NUMERO_THREADS) {
	        		qtde = 0;
	        		try {
						Thread.sleep(6000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}	        		
	        	}
	        	ipAux = ipAux.next();
	
	    }
	    while (ipAux.getValue() <= ip2.getValue());

        	if (!MonitoraAtivosDiscovery.iniciouDiscovery){
        		caiFora = true;
        	}	        
    		try {
				Thread.sleep(7000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        	try {
				exService.awaitTermination(30, TimeUnit.SECONDS);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
        	try{
        		exService.shutdown();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	exService = null;
        	if (caiFora){
        		System.out.println("CITSMART - Abortando ThreadValidaFaixaIP... ip1 = " + ip1 + " ao ip2 = " + ip2);
        		return;
        	}        	
	        try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
			}
		}		
	}
	public IPAddress getIp1() {
		return ip1;
	}
	public void setIp1(IPAddress ip1) {
		this.ip1 = ip1;
	}
	public IPAddress getIp2() {
		return ip2;
	}
	public void setIp2(IPAddress ip2) {
		this.ip2 = ip2;
	}
	class RunnableThread implements Runnable {
		private IPAddress ipAux = null;
		public RunnableThread(IPAddress ipParm) {
			ipAux = ipParm;
		}
        @Override
        public void run() {
        	if (ipAux != null){
        		ipAux.pingAddListInventory();
        	}
        }
    }	
}
