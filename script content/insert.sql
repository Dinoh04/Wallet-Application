-- currency table  insert
INSERT INTO currency (currency_name, currency_symbole) VALUES ('Ariary', 'AR'),('Franc Malgache', 'FMG'),('Euro', '€ '),('Dollar', '$') ON CONFLICT (currency_name,currency_symbole) DO NOTHING;

-- acccounts table insert
INSERT INTO accounts (accounts_name, accounts_balance, id_currency) VALUES ('Witting Inc', 1061629, 1),('Homenick Inc', 6735773, 2),('Oberbrunner, Boyer and Rath', 3072552, 3),('Runte, Koepp and Reinger', 148743, 4) ON CONFLICT (accounts_name, id_currency) DO NOTHING;

-- transaction table insert 
INSERT INTO transaction (description, amount, transaction_type, id_accounts) VALUES ('Expense 1', 50.25, 'expenses', 1),('Recipe 1', 100.50, 'recipes', 2),('Transfer 1', 75.00, 'to transfer', 1),('Expense 2', 30.75, 'expenses', 3) ON CONFLICT (description,amount,transaction_type,id_accounts) DO NOTHING;