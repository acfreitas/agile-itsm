package br.com.centralit.citcorpore.comm.server;

import br.com.centralit.citcorpore.batch.MonitoraDiscoveryIP;
import br.com.centralit.citcorpore.bean.NetMapDTO;

public class IPAddress {

	public static int PING_TIMEOUT = 6000;
	public static boolean NATIVE_PING = true;
    private final int value;
    public static final boolean isUnix = !System.getProperty("os.name").startsWith("Windows");

    public IPAddress(int value) {
        this.value = value;
    }

    public IPAddress(String stringValue) {
    	if (stringValue == null){
    		stringValue = "";
    	}
    	stringValue = stringValue.trim();
        String[] parts = stringValue.split("\\.");
        if( parts.length != 4 ) {
            throw new IllegalArgumentException();
        }
        value = 
                (Integer.parseInt(parts[0], 10) << (8*3)) & 0xFF000000 | 
                (Integer.parseInt(parts[1], 10) << (8*2)) & 0x00FF0000 |
                (Integer.parseInt(parts[2], 10) << (8*1)) & 0x0000FF00 |
                (Integer.parseInt(parts[3], 10) << (8*0)) & 0x000000FF;
    }

    public int getOctet(int i) {

        if( i<0 || i>=4 ) throw new IndexOutOfBoundsException();

        return (value >> (i*8)) & 0x000000FF;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(int i=3; i>=0; --i) {
            sb.append(getOctet(i));
            if( i!= 0) sb.append(".");
        }

        return sb.toString();

    }

    @Override
    public boolean equals(Object obj) {
        if( obj instanceof IPAddress ) {
            return value==((IPAddress)obj).value;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return value;
    }

    public int getValue() {
        return value;
    }

    public IPAddress next() {
        return new IPAddress(value+1);
    }
    
	public boolean ping(){
		/*
		 * Emauri - 06/12/2013 - Inicio, Retirar o isReachable, pois não tem o efeito desejado e onera com abertura de sockets.
		 * 
	    InetAddress inet;
		try {
			inet = InetAddress.getByName(toString());
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		}
		
		boolean bRet = false;
		try {
			bRet = inet.isReachable(PING_TIMEOUT);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
    	if (!bRet){
    		if (NATIVE_PING){
    			bRet = this.nativePing();
    		}
    	}		
		return bRet;
		*
		* Emauri - 06/12/2013 - Fim.
		*/
		return this.nativePing();
	}
	public boolean nativePing(){
		Process process = null;
		try{
			String command = (isUnix)?"ping -c 4 -w 5": "ping -4 -w 1250";
			process = Runtime.getRuntime().exec(command +" "+ toString());
			return (process.waitFor() == 0)?true:false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean pingAddListInventory(){
    	boolean pingOK = this.ping();
    	if (!pingOK){
    		if (NATIVE_PING){
	    		pingOK = this.nativePing();
    		}
    	}
    	if (pingOK){
    		synchronized (MonitoraDiscoveryIP.lstAddressDiscovery) {
    			NetMapDTO netMapDTO = new NetMapDTO();
    			netMapDTO.setIp(this.toString());
    			netMapDTO.setNovoIC(true);
    			netMapDTO.setStatusPing(NetMapDTO.ATIVO);
    			if (!MonitoraDiscoveryIP.hsmAddressDiscovery.containsKey(this.toString())){
    				MonitoraDiscoveryIP.hsmAddressDiscovery.put(this.toString(), this.toString());
    				MonitoraDiscoveryIP.lstAddressDiscovery.add(netMapDTO);
    			}
    		}
		}
    	return pingOK;
	}
	
    public static void main(String[] args) {


        IPAddress ip1 = new IPAddress("10.0.0.1");

        System.out.println("ip1 = " + ip1);

        IPAddress ip2 = new IPAddress("10.255.255.255");

        System.out.println("ip2 = " + ip2);

        System.out.println("Looping:");

        do {
        	
        	try {
        		System.out.println(ip1 + " >>>: " + ip1.ping());
                ip1 = ip1.next();
			} catch (Exception e) {
				System.out.println("Problema de Permissão da Rede para dar Ping");
			}
        	

        } while(!ip1.equals(ip2));


    }

}
