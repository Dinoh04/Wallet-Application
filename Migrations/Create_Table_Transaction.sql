create table if not exists Transaction
(
    idTransaction
    serial
    primary
    key,
    label
    varchar
(
    250
) not null,
    amount DOUBLE PRECISION not null,
    transactionDate timestamp default current_timestamp,
    transactionType TransactionType,
    idAccounts int REFERENCES Accounts
(
    idAccounts
),
    constraint uniqueTransaction unique
(
    label,
    amount,
    transactionDate,
    transactionType,
    idAccounts
)
    );