create database wallet_apply;
\c wallet_apply


create table if not exists currency(
    id_currency serial primary key,
    currency_name varchar(250) not null,
    currency_symbole varchar(250) not null,
    CONSTRAINT unique_currency_name_symbole UNIQUE (currency_name, currency_symbole)
);

create table  if not exists accounts(
    id_accounts serial primary key,
    accounts_name varchar(250) not null,
    accounts_balance float default 0 not null,
    id_currency int REFERENCES currency(id_currency),
    CONSTRAINT unique_accounts_name_currency UNIQUE (accounts_name, id_currency)
);

create TYPE  Transaction_type AS ENUM ('expenses', 'recipes','to transfer');

create table if not exists transaction(
    id_transaction serial primary key,
    description varchar(250),
    amount float not null,
    transaction_type Transaction_type,
    id_accounts int REFERENCES accounts(id_accounts),
    constraint unique_transaction unique (description,amount,transaction_type,id_accounts)
);
