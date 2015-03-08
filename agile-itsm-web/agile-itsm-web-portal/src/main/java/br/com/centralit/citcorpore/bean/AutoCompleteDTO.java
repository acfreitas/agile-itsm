package br.com.centralit.citcorpore.bean;

import java.util.List;

public class AutoCompleteDTO {
	
	private String query;
	private List suggestions;
	private List data;
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public List getSuggestions() {
		return suggestions;
	}
	public void setSuggestions(List suggestions) {
		this.suggestions = suggestions;
	}
	public List getData() {
		return data;
	}
	public void setData(List data) {
		this.data = data;
	}
}