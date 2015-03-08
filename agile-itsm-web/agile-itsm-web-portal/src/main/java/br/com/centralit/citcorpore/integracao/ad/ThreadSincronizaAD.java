package br.com.centralit.citcorpore.integracao.ad;

public class ThreadSincronizaAD implements Runnable {

	public ThreadSincronizaAD() {
	}

	@Override
	public void run() {
		try {
			LDAPUtils.sincronizaUsuariosAD();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
