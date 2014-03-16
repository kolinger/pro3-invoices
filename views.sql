 CREATE VIEW v_invoices AS
   SELECT invoices.id,
      invoices.deleted,
      invoices.comment,
      invoices.createdate,
      invoices.enddate,
      invoices.type,
      invoices.client_id,
      invoices.company_id,
      COALESCE(( SELECT sum(p.price / 100::numeric * (100 + p.tax)::numeric * p.count::numeric) AS sum
             FROM invoice_products p
            WHERE p.invoice_id = invoices.id), 0::numeric) AS amount,
      COALESCE(( SELECT sum(p2.amount) AS sum
             FROM payments p2
            WHERE p2.invoice_id = invoices.id), 0::numeric) AS payed
     FROM invoices;