CREATE OR REPLACE FUNCTION get_sum_by_category(
  accountid INT,
  "from" TIMESTAMP,
  "to" TIMESTAMP
)
RETURNS TABLE (categoryname varchar, total_amount numeric)
AS $$
BEGIN
RETURN QUERY
SELECT
    c.categoryname AS categoryname,
    COALESCE(SUM(CASE WHEN t.transactiontype = 'CREDIT' THEN t.amount ELSE -t.amount END)::numeric, 0) AS total_amount
FROM
    transactioncategory c
        LEFT JOIN
    transaction t ON c.idtransactioncategorie = t.idcategory AND t.idaccounts = idaccounts
        AND t.transactiondate BETWEEN "from" AND "to"
GROUP BY
    c.categoryname;
END;
$$ LANGUAGE plpgsql;


SELECT * FROM get_sum_by_category(1, '2023-12-01', '2023-12-02');