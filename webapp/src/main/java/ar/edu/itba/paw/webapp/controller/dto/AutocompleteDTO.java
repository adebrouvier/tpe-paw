package ar.edu.itba.paw.webapp.controller.dto;

import java.util.List;

public class AutocompleteDTO {

  public List<String> results;

  public AutocompleteDTO(){}

  public AutocompleteDTO(List<String> results){
    this.results = results;
  }

  public List<String> getResults() {
    return results;
  }

  public void setResults(List<String> results) {
    this.results = results;
  }
}
