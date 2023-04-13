INSERT INTO EMS_USER (ID, MAIL_ADDRESS, NAME, INTERNATIONAL_PHONE_CODE, NUMBER) VALUES ('12345678-1234-1234-a123-123456789001', 'example-user-1@example.de', 'Example-User-1', 0, 123456789);
INSERT INTO EMS_USER (ID, MAIL_ADDRESS, NAME, INTERNATIONAL_PHONE_CODE, NUMBER) VALUES ('12345678-1234-1234-a123-123456789002', 'example-user-2@example.de', 'Example-User-2', 0, 123456789);

INSERT INTO FINANCIAL_LEDGER (ID, TITLE) VALUES ('12345678-1234-1234-a123-123456789011', 'Example-Financial-Ledger');
INSERT INTO EMS_USER_TO_FINANCIAL_LEDGER (FINANCIAL_LEDGER_ID, EMS_USER_ID) VALUES ('12345678-1234-1234-a123-123456789011', '12345678-1234-1234-a123-123456789001');
INSERT INTO EMS_USER_TO_FINANCIAL_LEDGER (FINANCIAL_LEDGER_ID, EMS_USER_ID) VALUES ('12345678-1234-1234-a123-123456789011', '12345678-1234-1234-a123-123456789002');

INSERT INTO BOOKING_CATEGORY_AGGREGATE (ID, TITLE, FINANCIAL_LEDGER_ID) VALUES ('12345678-1234-1234-a123-123456789021', 'Example-Category', '12345678-1234-1234-a123-123456789011');

INSERT INTO BOOKING_AGGREGATE (ID, TITLE, AMOUNT, CURRENCY_TYPE, CREATOR_ID, FINANCIAL_LEDGER_ID, AGGREGATE_CATEGORY_ID, CREATION_DATE) VALUES ('12345678-1234-1234-a123-123456789031', 'Example-Booking', 19.99, 0, '12345678-1234-1234-a123-123456789001', '12345678-1234-1234-a123-123456789011', '12345678-1234-1234-a123-123456789021', '2023-01-27');
INSERT INTO BOOKING_REFERENCE (USER_ID, BOOKING_AGGREGATE_ID) VALUES ('12345678-1234-1234-a123-123456789001', '12345678-1234-1234-a123-123456789031');
INSERT INTO BOOKING_REFERENCE (USER_ID, BOOKING_AGGREGATE_ID) VALUES ('12345678-1234-1234-a123-123456789002', '12345678-1234-1234-a123-123456789031');