-- =============================================================================
-- XTREAM SEED DATA (V1.2)
-- Tham chiếu: funds → product_group → products → investments
-- Chuỗi investor: investors → investor_accounts → investor_account_investment
-- (Bảng users không được seed — dùng riêng cho auth)
-- =============================================================================

-- -----------------------------------------------------------------------------
-- 1. FUNDS
-- -----------------------------------------------------------------------------
INSERT INTO funds (code, name, status, created_at, updated_at)
VALUES
    ('FUND_EQ',  'Equity Growth Fund',    'ACTIVE', GETDATE(), GETDATE()),
    ('FUND_FIX', 'Fixed Income Fund',     'ACTIVE', GETDATE(), GETDATE()),
    ('FUND_BAL', 'Balanced Multi-Asset',  'ACTIVE', GETDATE(), GETDATE());

-- -----------------------------------------------------------------------------
-- 2. PRODUCT GROUP
-- -----------------------------------------------------------------------------
INSERT INTO product_group (code, name, status, fund_code, created_at, updated_at)
VALUES
    ('PG_EQ_1',  'Large Cap Equity',      'ACTIVE', 'FUND_EQ',  GETDATE(), GETDATE()),
    ('PG_EQ_2',  'Technology Equity',     'ACTIVE', 'FUND_EQ',  GETDATE(), GETDATE()),
    ('PG_FIX_1', 'Government Bonds',      'ACTIVE', 'FUND_FIX', GETDATE(), GETDATE()),
    ('PG_BAL_1', 'Balanced Portfolio',    'ACTIVE', 'FUND_BAL', GETDATE(), GETDATE());

-- -----------------------------------------------------------------------------
-- 3. PRODUCTS
-- -----------------------------------------------------------------------------
INSERT INTO products (code, name, type, status, product_group_code, created_at, updated_at)
VALUES
    ('PROD_A', 'Blue Chip Growth',      'EQUITY', 'ACTIVE', 'PG_EQ_1',  GETDATE(), GETDATE()),
    ('PROD_B', 'Tech Innovators',       'EQUITY', 'ACTIVE', 'PG_EQ_2',  GETDATE(), GETDATE()),
    ('PROD_C', 'Government Bonds',      'FIXED',  'ACTIVE', 'PG_FIX_1', GETDATE(), GETDATE()),
    ('PROD_D', 'Balanced Growth',       'MIXED',  'ACTIVE', 'PG_BAL_1', GETDATE(), GETDATE());

-- -----------------------------------------------------------------------------
-- 4. INVESTMENTS
-- -----------------------------------------------------------------------------
INSERT INTO investments (code, name, status, product_code, created_at, updated_at)
VALUES
    ('INV_A1', 'Large Cap Growth Basket',   'ACTIVE', 'PROD_A', GETDATE(), GETDATE()),
    ('INV_A2', 'Dividend Bluechip Basket',  'ACTIVE', 'PROD_A', GETDATE(), GETDATE()),

    ('INV_B1', 'AI & Tech Portfolio',       'ACTIVE', 'PROD_B', GETDATE(), GETDATE()),
    ('INV_B2', 'Cloud & SaaS Basket',       'ACTIVE', 'PROD_B', GETDATE(), GETDATE()),

    ('INV_C1', 'Short Term Bonds',          'ACTIVE', 'PROD_C', GETDATE(), GETDATE()),
    ('INV_C2', 'Long Term Bonds',           'ACTIVE', 'PROD_C', GETDATE(), GETDATE()),

    ('INV_D1', 'Equity Component',          'ACTIVE', 'PROD_D', GETDATE(), GETDATE()),
    ('INV_D2', 'Bond Component',            'ACTIVE', 'PROD_D', GETDATE(), GETDATE());

-- =============================================================================
-- INVESTOR CHAIN (thứ tự bắt buộc: investors → accounts → account_investment)
-- =============================================================================

-- -----------------------------------------------------------------------------
-- 5. INVESTORS
--     id=1 Nguyen Van An   | 2 equity accounts, đa dạng chiến lược
--     id=2 Tran Thi Binh   | equity + fixed income
--     id=3 Le Hoang Cuong  | thiên về trái phiếu
--     id=4 Pham Minh Duc   | 1 tài khoản balanced, investor mới
--     id=5 Vo Thi Em       | INACTIVE — test filter theo status
-- -----------------------------------------------------------------------------
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
    -- Investor 1: nam, trẻ, 2 tài khoản cổ phiếu
    (GETDATE(), GETDATE(),
     'EMAIL', 'PHONE',
     '1995-03-15', 'nguyen.van.an@example.com', 'MALE',
     'Van An', 'Nguyen', 'Mr',
     'Ho Chi Minh', 'District 1', '700000',
     NULL, 'Le Loi', 'Floor 5', '12',
     '0901234567', '0901234567', '02838234567',
     60, 'ACTIVE', '1234567890'),

    -- Investor 2: nữ, đa tài sản
    (GETDATE(), GETDATE(),
     'PHONE', 'EMAIL',
     '1993-07-22', 'tran.thi.binh@example.com', 'FEMALE',
     'Thi Binh', 'Tran', 'Ms',
     'Ho Chi Minh', 'District 3', '700000',
     'Saigon Tower', 'Nguyen Trai', NULL, '88',
     '0912345678', '0912345678', NULL,
     58, 'ACTIVE', '2345678901'),

    -- Investor 3: nam, bảo thủ
    (GETDATE(), GETDATE(),
     'EMAIL', 'EMAIL',
     '1988-11-08', 'le.hoang.cuong@example.com', 'MALE',
     'Hoang Cuong', 'Le', 'Mr',
     'Ho Chi Minh', 'District 7', '700000',
     NULL, 'Pham Van Dong', 'Apt 12B', '101',
     '0923456789', '0923456789', '02837778899',
     55, 'ACTIVE', '3456789012-001'),

    -- Investor 4: nam, balanced portfolio
    (GETDATE(), GETDATE(),
     'PHONE', 'PHONE',
     '1997-01-30', 'pham.minh.duc@example.com', 'MALE',
     'Minh Duc', 'Pham', 'Mr',
     'Ha Noi', 'Cau Giay', '100000',
     'Landmark 72', 'Pham Hung', NULL, '45',
     '0934567890', '0934567890', NULL,
     62, 'ACTIVE', '4567890123'),

    -- Investor 5: inactive — test search/filter
    (GETDATE(), GETDATE(),
     'EMAIL', 'PHONE',
     '1991-05-18', 'vo.thi.em@example.com', 'FEMALE',
     'Thi Em', 'Vo', 'Ms',
     'Da Nang', 'Hai Chau', '550000',
     NULL, 'Tran Phu', NULL, '200',
     '0945678901', '0945678901', NULL,
     60, 'INACTIVE', '5678901234');

-- -----------------------------------------------------------------------------
-- 6. INVESTOR ACCOUNTS
--     investor_id → product_code (investment phải thuộc cùng product)
-- -----------------------------------------------------------------------------
INSERT INTO investor_accounts (status, investor_id, product_code, start_date, created_at, updated_at)
VALUES
    -- Investor 1 (Nguyen Van An): 2 tài khoản equity
    ('ACTIVE', 1, 'PROD_A', '2023-01-10', GETDATE(), GETDATE()),   -- account id=1
    ('ACTIVE', 1, 'PROD_B', '2023-06-01', GETDATE(), GETDATE()),   -- account id=2

    -- Investor 2 (Tran Thi Binh): equity + fixed
    ('ACTIVE', 2, 'PROD_B', '2022-09-15', GETDATE(), GETDATE()),   -- account id=3
    ('ACTIVE', 2, 'PROD_C', '2024-02-20', GETDATE(), GETDATE()),   -- account id=4

    -- Investor 3 (Le Hoang Cuong): thiên trái phiếu
    ('ACTIVE', 3, 'PROD_A', '2021-04-01', GETDATE(), GETDATE()),   -- account id=5
    ('ACTIVE', 3, 'PROD_C', '2023-11-11', GETDATE(), GETDATE()),   -- account id=6

    -- Investor 4 (Pham Minh Duc): balanced
    ('ACTIVE', 4, 'PROD_D', '2024-08-01', GETDATE(), GETDATE()),   -- account id=7

    -- Investor 5 (Vo Thi Em): inactive account
    ('INACTIVE', 5, 'PROD_C', '2020-01-01', GETDATE(), GETDATE()); -- account id=8

-- -----------------------------------------------------------------------------
-- 7. INVESTOR ACCOUNT INVESTMENT
--     investment_code phải thuộc product của investor_account tương ứng
--     strategy: 1–100 (theo InvestorInvestmentRequestDTO)
-- -----------------------------------------------------------------------------
INSERT INTO investor_account_investment (strategy, investment_code, investor_account_id, created_at, updated_at)
VALUES
    -- Account 1 | investor 1 | PROD_A
    (60, 'INV_A1', 1, GETDATE(), GETDATE()),
    (40, 'INV_A2', 1, GETDATE(), GETDATE()),

    -- Account 2 | investor 1 | PROD_B
    (70, 'INV_B1', 2, GETDATE(), GETDATE()),
    (30, 'INV_B2', 2, GETDATE(), GETDATE()),

    -- Account 3 | investor 2 | PROD_B
    (50, 'INV_B1', 3, GETDATE(), GETDATE()),
    (50, 'INV_B2', 3, GETDATE(), GETDATE()),

    -- Account 4 | investor 2 | PROD_C
    (100, 'INV_C1', 4, GETDATE(), GETDATE()),

    -- Account 5 | investor 3 | PROD_A
    (100, 'INV_A1', 5, GETDATE(), GETDATE()),

    -- Account 6 | investor 3 | PROD_C
    (60, 'INV_C1', 6, GETDATE(), GETDATE()),
    (40, 'INV_C2', 6, GETDATE(), GETDATE()),

    -- Account 7 | investor 4 | PROD_D
    (55, 'INV_D1', 7, GETDATE(), GETDATE()),
    (45, 'INV_D2', 7, GETDATE(), GETDATE()),

    -- Account 8 | investor 5 | PROD_C (inactive)
    (100, 'INV_C1', 8, GETDATE(), GETDATE());
