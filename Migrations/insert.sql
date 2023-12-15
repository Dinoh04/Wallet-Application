-- Currency table insert
INSERT INTO Currency (currencyName, currencyCode) VALUES ('Ariary', 'AR'), ('Euro', 'EUR'), ('Euro', 'EUR'), ('Ariary', 'AR') ON CONFLICT (currencyName, currencyCode) DO NOTHING;

-- Accounts table insert
INSERT INTO Accounts (accountsName, accountsBalance, lastUpdate, idCurrency, accountType) VALUES ('compte courant', 1061629, CURRENT_TIMESTAMP, 1, 'BANK'),('compte épargne', 6735773, CURRENT_TIMESTAMP, 2, 'CASH'),('compte courant', 3072552, CURRENT_TIMESTAMP, 2, 'MOBILE_MONEY'),('compte épargne', 148743, CURRENT_TIMESTAMP, 1, 'BANK') ON CONFLICT (AccountsName,accountsBalance,idCurrency,AccountType) DO NOTHING;

-- Transaction table insert
INSERT INTO Transaction (label, amount, transactionDate, transactionType, idAccounts) VALUES ('Expense 1', 50.25, CURRENT_TIMESTAMP, 'DEBIT', 1),('Recipe 1', 100.50, CURRENT_TIMESTAMP, 'CREDIT', 2),('Expense 2', 30.75, CURRENT_TIMESTAMP, 'DEBIT', 3),('Recipe 2', 75.00, CURRENT_TIMESTAMP, 'CREDIT', 1) ON CONFLICT (label,amount,transactionDate,transactionType,idAccounts) DO NOTHING;

--
