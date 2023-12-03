create database wallet_apply;
\c wallet_apply


create table if not exists currency(
    id_currency serial primary key,
    currency_name varchar(250) not null,
    currency_symbole varchar(250) not null
);

create table  if not exists acccounts(
    id_accounts serial primary key,
    accounts_name varchar(250) not null,
    accounts_balance float default 0 not null,
    id_currency int REFERENCES currency(id_currency)
);

create TYPE  Transaction_type AS ENUM ('expenses', 'recipes','to transfer');

create table if not exists transaction(
    id_transaction serial primary key,
    description varchar(250),
    amount float not null,
    transaction_type Transaction_type
    id_accounts int REFERENCES acccounts(id_accounts)
);
