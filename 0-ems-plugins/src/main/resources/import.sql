INSERT INTO EMS_USER (ID, EMAIL, NAME, INTERNATIONAL_PHONE_CODE, PHONE_NUMBER) VALUES ('12345678-1234-1234-a123-123456789001', 'example-user-1@example.de', 'Example-User-1', 0, 123456789);
INSERT INTO EMS_USER (ID, EMAIL, NAME, INTERNATIONAL_PHONE_CODE, PHONE_NUMBER) VALUES ('12345678-1234-1234-a123-123456789002', 'example-user-2@example.de', 'Example-User-2', 0, 123456789);

INSERT INTO FINANCIAL_LEDGER (ID, TITLE) VALUES ('12345678-1234-1234-a123-123456789011', 'Example-Financial-Ledger');
INSERT INTO EMS_USER_TO_FINANCIAL_LEDGER (FINANCIAL_LEDGER_ID, EMS_USER_ID) VALUES ('12345678-1234-1234-a123-123456789011', '12345678-1234-1234-a123-123456789001');

INSERT INTO BOOKING_CATEGORY (ID, FINANCIAL_LEDGER_ID, TITLE) VALUES ('12345678-1234-1234-a123-123456789021', '12345678-1234-1234-a123-123456789011', 'Example-Category');

INSERT INTO BOOKING (ID, CREATOR_ID, FINANCIAL_LEDGER_ID, REFERENCED_CATEGORY_ID, CREATION_DATE, TITLE, AMOUNT, CURRENCY_TYPE) VALUES ('12345678-1234-1234-a123-123456789031', '12345678-1234-1234-a123-123456789001', '12345678-1234-1234-a123-123456789011', '12345678-1234-1234-a123-123456789021', '2023-01-27', 'Example-Booking', 19.99, 0);
INSERT INTO FINANCIAL_LEDGER_BOOKINGS (FINANCIAL_LEDGER_ID, BOOKINGS_ID) VALUES ('12345678-1234-1234-a123-123456789011', '12345678-1234-1234-a123-123456789031');
INSERT INTO EMS_USER_TO_BOOKING (BOOKING_ID, EMS_USER_ID) VALUES ('12345678-1234-1234-a123-123456789031', '12345678-1234-1234-a123-123456789001');
