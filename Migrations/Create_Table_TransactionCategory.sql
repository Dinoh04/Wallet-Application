create table if not exists TransactionCategory
(
    idTransactionCategorie
    serial
    primary
    key,
    categoryName
    varchar
(
    100
) UNIQUE NOT NULL,
    transactionType TransactionType,
    CONSTRAINT transactionType_check CHECK
(
    transactionType
    IN
(
    'DEBIT',
    'CREDIT'
))
    );
