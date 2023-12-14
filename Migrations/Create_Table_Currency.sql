create table if not exists Currency
(
    idCurrency
    serial
    primary
    key,
    currencyName
    varchar
(
    250
) not null,
    currencyCode varchar
(
    5
) not null,
    CONSTRAINT uniqueCurrencyNameCode UNIQUE
(
    currencyName,
    currencyCode
)
    );