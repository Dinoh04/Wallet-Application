create table if not exists Accounts
(
    idAccounts
    serial
    primary
    key,
    accountsName
    varchar
(
    250
) not null,
    accountsBalance DOUBLE PRECISION not null,
    lastUpdate Timestamp default Current_Timestamp,
    idCurrency int REFERENCES Currency
(
    idCurrency
),
    accountType AccountType,
    CONSTRAINT uniqueAccounts UNIQUE
(
    accountsName,
    accountsBalance,
    idCurrency,
    AccountType
)
    );