package org.example.Model;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Currency {
    private  int id_currency;
    private String currency_name;
    private  String currency_symbole;
}
