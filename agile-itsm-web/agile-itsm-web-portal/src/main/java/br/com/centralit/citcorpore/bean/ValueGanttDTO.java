/**
 * 
 */
package br.com.centralit.citcorpore.bean;

/**
 * @author valdoilo.damasceno
 * 
 */
public class ValueGanttDTO {

    private String from;

    private String to;

    private String label;

    private String customClass;

    /**
     * @return valor do atributo from.
     */
    public String getFrom() {
	return from;
    }

    /**
     * Define valor do atributo from.
     * 
     * @param from
     */
    public void setFrom(String from) {
	this.from = from;
    }

    /**
     * @return valor do atributo to.
     */
    public String getTo() {
	return to;
    }

    /**
     * Define valor do atributo to.
     * 
     * @param to
     */
    public void setTo(String to) {
	this.to = to;
    }

    /**
     * @return valor do atributo label.
     */
    public String getLabel() {
	return label;
    }

    /**
     * Define valor do atributo label.
     * 
     * @param label
     */
    public void setLabel(String label) {
	this.label = label;
    }

    /**
     * @return valor do atributo customClass.
     */
    public String getCustomClass() {
	return customClass;
    }

    /**
     * Define valor do atributo customClass.
     * 
     * @param customClass
     */
    public void setCustomClass(String customClass) {
	this.customClass = customClass;
    }

}
