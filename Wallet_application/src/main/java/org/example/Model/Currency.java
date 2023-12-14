package org.example.Model;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Currency {
  private Integer idCurrency;
  private String currencyName;
  private String currencyCode;
}
