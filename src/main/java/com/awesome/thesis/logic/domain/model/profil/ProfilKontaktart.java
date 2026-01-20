package com.awesome.thesis.logic.domain.model.profil;

import com.awesome.thesis.annotations.AggregateValue;

/**
 * Dieses Enum dient der Speicherung einer Kontaktart.
 */
@AggregateValue
public enum ProfilKontaktart {
  EMAIL("Email", "mailto:%s"),
  TEL("Telefon", "tel:%s");
  
  private final String label;
  private final String href;
  
  ProfilKontaktart(String label, String href) {
    this.label = label;
    this.href = href;
  }
  
  public String getLabel() {
    return label;
  }
  
  public String getHref() {
    return href;
  }
}
