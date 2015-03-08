/* Released under the GPL2. See license.txt for details. */
package br.com.centralit.nagios;

/**
 * Class Totals is only used to return counts.
 *
 * @author	Folkert van Heusden
 * @version	%I%, %G%
 * @since	0.1 
 */
public class Totals {
	int nCritical, nWarning, nOk;
	int nUp, nDown, nUnreachable, nPending;
	int nHosts, nServices;
	int nStateUnknownHost, nStateUnknownService;
	int nAcked, nFlapping;

	public Totals(int nCritical, int nWarning, int nOk, int nUp, int nDown, int nUnreachable, int nPending, int nHosts, int nServices, int nStateUnknownHost, int nStateUnknownService, int nAcked, int nFlapping) {
		this.nCritical = nCritical;
		this.nWarning = nWarning;
		this.nOk = nOk;
		this.nUp = nUp;
		this.nDown = nDown;
		this.nUnreachable = nUnreachable;
		this.nPending = nPending;
		this.nHosts = nHosts;
		this.nServices = nServices;
		this.nStateUnknownHost = nStateUnknownHost;
		this.nStateUnknownService = nStateUnknownService;
		this.nAcked = nAcked;
		this.nFlapping = nFlapping;
	}

	public int getNCritical() {
		return nCritical;
	}

	public int getNWarning() {
		return nWarning;
	}

	public int getNOk() {
		return nOk;
	}

	public int getNUp() {
		return nUp;
	}

	public int getNDown() {
		return nDown;
	}

	public int getNUnreachable() {
		return nUnreachable;
	}

	public int getNPending() {
		return nPending;
	}

	public int getNHosts() {
		return nHosts;
	}

	public int getNServices() {
		return nServices;
	}

	public int getNStateUnknownHost() {
		return nStateUnknownHost;
	}

	public int getNStateUnknownService() {
		return nStateUnknownService;
	}

	public int getNAcked() {
		return nAcked;
	}

	public int getNFlapping() {
		return nFlapping;
	}
}
