--to create  the fonction
CREATE OR REPLACE FUNCTION get_sum_in_out(
    account_id int,
    "from" TIMESTAMP,
    "to" TIMESTAMP
) RETURNS DECIMAL(18,5) AS $$
DECLARE
result DECIMAL(18,5);
BEGIN
SELECT COALESCE(SUM(CASE WHEN transactiontype = 'CREDIT' THEN amount ELSE -amount END), 0)
INTO result
FROM "transaction"
WHERE "idaccounts" = account_id
  AND transactiondate BETWEEN "from" AND "to";

RETURN result;
END; $$ LANGUAGE plpgsql;

-- exemple for using the function
SELECT * FROM get_sum_in_out(1, '2023-12-01', '2023-12-02');