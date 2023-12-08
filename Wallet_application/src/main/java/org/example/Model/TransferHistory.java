package org.example.Model;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TransferHistory {
    private int id;
    private int debitTransactionId;
    private int creditTransactionId;
    private LocalDate transferDate;
}
