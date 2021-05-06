SELECT pg_catalog.setval(pg_get_serial_sequence('pets', 'id'), (SELECT MAX(id) FROM pets));
SELECT pg_catalog.setval(pg_get_serial_sequence('product_types', 'id'), (SELECT MAX(id) FROM product_types));
SELECT pg_catalog.setval(pg_get_serial_sequence('product_types_detailed', 'id'), (SELECT MAX(id) FROM product_types_detailed));
SELECT pg_catalog.setval(pg_get_serial_sequence('products', 'id'), (SELECT MAX(id) FROM products));
