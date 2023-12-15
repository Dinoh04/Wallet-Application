ALTER TABLE Transaction ADD COLUMN idCategory INTEGER REFERENCES TransactionCategory(idTransactionCategorie);
