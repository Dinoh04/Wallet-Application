package org.example.Model;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class BalanceHistoryEntry {
  private LocalDateTime updateDateTime;
  private Double balance;
}
