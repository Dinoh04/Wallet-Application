create database wallet_application;
\c wallet_application

create table if not exists Currency(
    idCurrency serial primary key,
    currencyName varchar(250) not null,
    currencyCode varchar(5) not null,
    CONSTRAINT uniqueCurrencyNameCode UNIQUE (currencyName, currencyCode)
);

create type AccountType as ENUM ('Bank','Cash','Mobile money');
create table  if not exists Accounts(
    idAccounts serial primary key,
    accountsName varchar(250) not null,
    accountsBalance DOUBLE PRECISION not null,
    lastUpdate Timestamp default Current_Timestamp,
    idCurrency int REFERENCES Currency(idCurrency),
    accountType AccountType,
    CONSTRAINT uniqueAccounts UNIQUE (AccountsName,lastUpdate ,idCurrency,AccountType)
);

create TYPE  TransactionType AS ENUM ('Debit','Credit');

create table if not exists Transaction(
    idTransaction serial primary key,
    label varchar(250) not null,
    amount DOUBLE PRECISION not null,
    transactionDate timestamp default current_timestamp,
    transactionType TransactionType,
    idAccounts int REFERENCES Accounts(idAccounts),
    constraint uniqueTransaction unique (label,amount,transactionDate,transactionType,idAccounts)
);
