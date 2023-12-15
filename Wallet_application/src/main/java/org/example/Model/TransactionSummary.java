package org.example.Model;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TransactionSummary {
private String category;
private double amount;
}
