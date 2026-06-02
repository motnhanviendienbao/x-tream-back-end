-- fund
INSERT INTO funds (code, name, status, created_at, updated_at)
VALUES
    ('FUND_EQ', 'Equity Fund', 'ACTIVE', GETDATE(), GETDATE()),
    ('FUND_FIX', 'Fixed Income Fund', 'ACTIVE', GETDATE(), GETDATE());
-- product group
INSERT INTO product_group (code, name, status, fund_code, created_at, updated_at)
VALUES
    ('PG_EQ_1', 'Large Cap Equity', 'ACTIVE', 'FUND_EQ', GETDATE(), GETDATE()),
    ('PG_EQ_2', 'Tech Equity', 'ACTIVE', 'FUND_EQ', GETDATE(), GETDATE()),
    ('PG_FIX_1', 'Bond Portfolio', 'ACTIVE', 'FUND_FIX', GETDATE(), GETDATE());
-- product
INSERT INTO products (code, name, type, status, product_group_code, created_at, updated_at)
VALUES
    ('PROD_A', 'Blue Chip Growth', 'EQUITY', 'ACTIVE', 'PG_EQ_1', GETDATE(), GETDATE()),
    ('PROD_B', 'Tech Innovators', 'EQUITY', 'ACTIVE', 'PG_EQ_2', GETDATE(), GETDATE()),
    ('PROD_C', 'Government Bonds', 'FIXED', 'ACTIVE', 'PG_FIX_1', GETDATE(), GETDATE());
-- investment
INSERT INTO investments (code, name, status, product_code, created_at, updated_at)
VALUES
    ('INV_A1', 'Apple-like Growth', 'ACTIVE', 'PROD_A', GETDATE(), GETDATE()),
    ('INV_A2', 'Stable Bluechip Basket', 'ACTIVE', 'PROD_A', GETDATE(), GETDATE()),

    ('INV_B1', 'AI & Tech Portfolio', 'ACTIVE', 'PROD_B', GETDATE(), GETDATE()),
    ('INV_B2', 'Cloud & SaaS Basket', 'ACTIVE', 'PROD_B', GETDATE(), GETDATE()),

    ('INV_C1', 'Short Term Bonds', 'ACTIVE', 'PROD_C', GETDATE(), GETDATE());
-- investor account
INSERT INTO investor_accounts (status, investor_id, product_code,start_date, created_at, updated_at)
VALUES
-- USER 1
('ACTIVE', 1, 'PROD_A',GETDATE(), GETDATE(), GETDATE()),
('ACTIVE', 1, 'PROD_B',GETDATE(), GETDATE(), GETDATE()),

-- USER 2
('ACTIVE', 2, 'PROD_B',GETDATE(), GETDATE(), GETDATE()),
('ACTIVE', 2, 'PROD_C',GETDATE(), GETDATE(), GETDATE()),

-- USER 3
('ACTIVE', 3, 'PROD_A',GETDATE(), GETDATE(), GETDATE()),
('ACTIVE', 3, 'PROD_C',GETDATE(), GETDATE(), GETDATE());
-- investor account investment
INSERT INTO investor_account_investment (strategy, investment_code, investor_account_id, created_at, updated_at)
VALUES
-- account 1 (user 1)
(1, 'INV_A1', 1, GETDATE(), GETDATE()),
(2, 'INV_A2', 1, GETDATE(), GETDATE()),

-- account 2 (user 1)
(1, 'INV_B1', 2, GETDATE(), GETDATE()),

-- account 3 (user 2)
(1, 'INV_B1', 3, GETDATE(), GETDATE()),
(2, 'INV_C1', 3, GETDATE(), GETDATE()),

-- account 4 (user 2)
(1, 'INV_C1', 4, GETDATE(), GETDATE()),

-- account 5 (user 3)
(1, 'INV_A1', 5, GETDATE(), GETDATE()),
(2, 'INV_C1', 5, GETDATE(), GETDATE()),

-- account 6 (user 3)
(1, 'INV_B2', 6, GETDATE(), GETDATE());

-- investors
INSERT INTO investors (
    created_at, updated_at,
    best_contact_method, next_contact_method,
    dbo, email, gender,
    given_names, surname, title,
    city, district, post_code,
    property_name, street_name_1, street_name_2, street_number,
    mobile, primary_phone, secondary_phone,
    retirement_age, status, tfn
)
VALUES
-- INVESTOR 1 (map user 1)
(GETDATE(), GETDATE(),
 'EMAIL', 'PHONE',
 '1995-01-01', 'user1@gmail.com', 'MALE',
 'Nguyen', 'An', 'Mr',
 'HCM', 'Q1', '70000',
 NULL, 'Le Loi', NULL, '12',
 '0901111111', '0901111111', NULL,
 60, 'ACTIVE', 'TFN001'),

-- INVESTOR 2 (map user 2)
(GETDATE(), GETDATE(),
 'PHONE', 'EMAIL',
 '1993-05-10', 'user2@gmail.com', 'FEMALE',
 'Tran', 'Binh', 'Ms',
 'HCM', 'Q3', '70000',
 NULL, 'Nguyen Trai', NULL, '88',
 '0902222222', '0902222222', NULL,
 60, 'ACTIVE', 'TFN002'),

-- INVESTOR 3 (map user 3)
(GETDATE(), GETDATE(),
 'EMAIL', 'PHONE',
 '1990-09-20', 'user3@gmail.com', 'MALE',
 'Le', 'Cuong', 'Mr',
 'HCM', 'Q7', '70000',
 NULL, 'Pham Van Dong', NULL, '101',
 '0903333333', '0903333333', NULL,
 60, 'ACTIVE', 'TFN003');