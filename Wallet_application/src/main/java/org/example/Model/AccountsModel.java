package org.example.Model;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AccountsModel {
    private int id_accounts;
    private String accounts_name;
    private Float accounts_balance;
    private  int id_currency;
}
