CREATE OR REPLACE FUNCTION get_sum_by_category(
  accountid INT,
  "from" TIMESTAMP,
  "to" TIMESTAMP
)
RETURNS TABLE(restaurant DOUBLE PRECISION, salaire DOUBLE PRECISION)
AS $$
BEGIN
RETURN QUERY SELECT
        COALESCE(SUM(CASE WHEN "categoryname" = 'Restaurant' THEN "amount" ELSE 0 END), 0) AS restaurant,
        COALESCE(SUM(CASE WHEN "categoryname" = 'Salaire' THEN "amount" ELSE 0 END), 0) AS salaire
    FROM "transaction"
    LEFT JOIN "transactioncategory" ON "transaction"."idcategory" = "transactioncategory"."idcategory"
    WHERE "idaccounts" = accountid AND "transactiondate" BETWEEN "from" AND "to";
END;
$$ LANGUAGE plpgsql;


SELECT * FROM get_sum_by_category(1, '2023-12-01', '2023-12-02');