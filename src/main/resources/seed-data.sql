-- ============================================================
-- SEED DATA FOR XTREAM - SQL SERVER
-- Insert order follows FK dependencies
-- ============================================================

-- 1. FUNDS
SET IDENTITY_INSERT funds ON;
INSERT INTO funds (id, name, status, short_code) VALUES
(1, 'Xtream Growth Fund', 'A', 'XGF'),
(2, 'Xtream Balanced Fund', 'A', 'XBF'),
(3, 'Xtream Conservative Fund', 'A', 'XCF');
SET IDENTITY_INSERT funds OFF;

-- 2. PRODUCT_GROUP
SET IDENTITY_INSERT product_group ON;
INSERT INTO product_group (id, status, short_code, fund_id) VALUES
(1, 'A', 'SUPER', 1),
(2, 'A', 'PENSION', 2),
(3, 'A', 'INVEST', 3);
SET IDENTITY_INSERT product_group OFF;

-- 3. PRODUCTS
SET IDENTITY_INSERT products ON;
INSERT INTO products (id, name, type, status, short_code, product_group_id) VALUES
(1, 'Super Accumulation',    'LOW',    'A', 'SA',  1),
(2, 'Super Income Stream',   'MEDIUM', 'A', 'SIS', 1),
(3, 'Pension Balanced',      'MEDIUM', 'A', 'PB',  2),
(4, 'Direct Investment',     'HIGH',   'A', 'DI',  3),
(5, 'Conservative Savings',  'LOW',    'A', 'CS',  3);
SET IDENTITY_INSERT products OFF;

-- 4. INVESTMENTS
SET IDENTITY_INSERT investments ON;
INSERT INTO investments (id, status, short_code, selection_method, product_id) VALUES
(1, 'A', 'AUS_EQ',   'N', 1),
(2, 'A', 'INT_EQ',   'N', 1),
(3, 'A', 'FIXED_IN', 'N', 1),
(4, 'A', 'CASH',     'N', 2),
(5, 'A', 'PROPERTY', 'N', 2),
(6, 'A', 'BALANCED', 'N', 3),
(7, 'A', 'GROWTH',   'N', 4),
(8, 'A', 'CONSERV',  'N', 5);
SET IDENTITY_INSERT investments OFF;

-- 5. UNIT_PRICES
SET IDENTITY_INSERT unit_prices ON;
INSERT INTO unit_prices (id, unit_price, price_date, investment_id) VALUES
(1,  1.2345, '2026-05-01', 1),
(2,  1.2400, '2026-05-02', 1),
(3,  0.9870, '2026-05-01', 2),
(4,  0.9920, '2026-05-02', 2),
(5,  1.0100, '2026-05-01', 3),
(6,  1.5500, '2026-05-01', 4),
(7,  2.1000, '2026-05-01', 5),
(8,  1.3200, '2026-05-01', 6),
(9,  1.8900, '2026-05-01', 7),
(10, 1.0050, '2026-05-01', 8);
SET IDENTITY_INSERT unit_prices OFF;

-- 6. INVESTORS (address columns are embedded) — 30 investors
SET IDENTITY_INSERT investors ON;
INSERT INTO investors (id, given_names, surname, date_of_birth, status, title, retirement_age, tfn, email,
                       primary_phone, best_contact_method, next_contact_method, mobile, secondary_phone,
                       gender, street_number, street_name_1, street_name_2, city, property_name, district, post_code,
                       created_at, updated_at) VALUES
(1,  'Ngoc Tu',      'Tran',    '1995-03-15', 'A', 'Mr',  '65', '111222333', 'ngoctu@example.com',
     '0281234567', 'PHONE', 'EMAIL', '0412345678', '0398765432',
     'Male',   '10',  'Pitt Street',       NULL,         'Sydney',    'Unit 5A',    'CBD',              '2000',
     '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(2,  'Minh Anh',     'Nguyen',  '1988-07-22', 'A', 'Ms',  '60', '444555666', 'minhanh@example.com',
     '0392223344', 'EMAIL', 'PHONE', '0422334455', '0311223344',
     'Female', '25',  'George Street',     'Level 3',    'Melbourne', 'Apt 12B',    'Docklands',        '3008',
     '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(3,  'Hoang Long',   'Le',      '1992-11-05', 'A', 'Mr',  '67', '777888999', 'hoanglong@example.com',
     '0733445566', 'PHONE', 'PHONE', '0455667788', '0744556677',
     'Male',   '8',   'Queen Street',      NULL,         'Brisbane',  'Suite 2',    'Fortitude Valley', '4006',
     '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(4,  'Thanh Huyen',  'Pham',    '2000-01-30', 'I', 'Mrs', '65', '123987456', 'thanhhuyen@example.com',
     '0866778899', 'EMAIL', 'EMAIL', '0477889900', '0855667788',
     'Female', '100', 'Collins Street',    'Building C', 'Perth',     'Office 9',   'Subiaco',          '6008',
     '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(5,  'Duc Minh',     'Vo',      '1985-06-10', 'A', 'Mr',  '65', '222333444', 'ducminh@example.com',
     '0291112233', 'PHONE', 'EMAIL', '0433445566', '0399887766',
     'Male',   '5',   'Elizabeth Street',  NULL,         'Sydney',    'Level 10',   'Surry Hills',      '2010',
     '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(6,  'Thuy Linh',    'Do',      '1993-09-18', 'A', 'Ms',  '62', '333444555', 'thuylinh@example.com',
     '0388776655', 'EMAIL', 'PHONE', '0444556677', '0377665544',
     'Female', '42',  'Swanston Street',   'Apt 3',     'Melbourne', 'Tower A',    'Carlton',          '3053',
     '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(7,  'Quang Huy',    'Bui',     '1990-12-25', 'A', 'Mr',  '67', '555666777', 'quanghuy@example.com',
     '0744332211', 'PHONE', 'PHONE', '0466778899', '0733221100',
     'Male',   '15',  'Adelaide Street',   NULL,         'Brisbane',  'Suite 8',    'Spring Hill',      '4000',
     '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(8,  'Ha My',        'Dang',    '1997-04-02', 'A', 'Ms',  '63', '666777888', 'hamy@example.com',
     '0855443322', 'EMAIL', 'EMAIL', '0488990011', '0844332211',
     'Female', '77',  'Murray Street',     'Floor 2',   'Perth',     'Block B',    'West Perth',       '6005',
     '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(9,  'Van Khanh',    'Hoang',   '1982-08-14', 'A', 'Mr',  '65', '888999000', 'vankhanh@example.com',
     '0266554433', 'PHONE', 'EMAIL', '0400112233', '0355443322',
     'Male',   '200', 'King Street',       NULL,         'Sydney',    'Penthouse',  'Newtown',          '2042',
     '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(10, 'Phuong Thao',  'Ly',      '1999-02-28', 'I', 'Ms',  '60', '999000111', 'phuongthao@example.com',
     '0377889900', 'EMAIL', 'PHONE', '0411223344', '0366778899',
     'Female', '3',   'Flinders Lane',     'Unit 7',    'Melbourne', 'Apt 7C',     'Southbank',        '3006',
     '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(11, 'Tuan Anh',     'Trinh',   '1991-05-20', 'A', 'Mr',  '65', '101202303', 'tuananh@example.com',
     '0282233445', 'PHONE', 'EMAIL', '0413456789', '0397654321',
     'Male',   '18',  'York Street',       NULL,         'Sydney',    'Unit 3B',    'Haymarket',        '2000',
     '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(12, 'Ngoc Lan',     'Truong',  '1994-10-12', 'A', 'Ms',  '62', '202303404', 'ngoclan@example.com',
     '0393344556', 'EMAIL', 'PHONE', '0424567890', '0312345678',
     'Female', '33',  'Bourke Street',     'Level 5',   'Melbourne', 'Apt 22A',    'Richmond',         '3121',
     '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(13, 'Minh Quan',    'Dinh',    '1987-01-08', 'A', 'Mr',  '67', '303404505', 'minhquan@example.com',
     '0734455667', 'PHONE', 'PHONE', '0456789012', '0745566778',
     'Male',   '12',  'Albert Street',     NULL,         'Brisbane',  'Suite 5',    'South Brisbane',   '4101',
     '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(14, 'Thu Ha',       'Ngo',     '1996-06-15', 'A', 'Ms',  '63', '404505606', 'thuha@example.com',
     '0867788990', 'EMAIL', 'EMAIL', '0478901234', '0856677889',
     'Female', '55',  'Hay Street',        'Floor 4',   'Perth',     'Tower C',    'East Perth',       '6004',
     '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(15, 'Quoc Dat',     'Lam',     '1983-03-25', 'A', 'Mr',  '65', '505606707', 'quocdat@example.com',
     '0293344556', 'PHONE', 'EMAIL', '0434567891', '0398876655',
     'Male',   '7',   'Castlereagh St',   NULL,         'Sydney',    'Level 12',   'Chippendale',      '2008',
     '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(16, 'Mai Phuong',   'Luong',   '1998-12-01', 'A', 'Ms',  '60', '606707808', 'maiphuong@example.com',
     '0384455667', 'EMAIL', 'PHONE', '0445678902', '0373344556',
     'Female', '60',  'Chapel Street',     'Apt 9',     'Melbourne', 'Block D',    'Prahran',          '3181',
     '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(17, 'Thanh Son',    'Duong',   '1989-07-19', 'A', 'Mr',  '67', '707808909', 'thanhson@example.com',
     '0745566778', 'PHONE', 'PHONE', '0467890123', '0734455667',
     'Male',   '22',  'Wickham Street',    NULL,         'Brisbane',  'Suite 11',   'New Farm',         '4005',
     '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(18, 'Bich Ngoc',    'Ta',      '2001-04-10', 'A', 'Ms',  '63', '808909010', 'bichngoc@example.com',
     '0878899001', 'EMAIL', 'EMAIL', '0489012345', '0867788990',
     'Female', '88',  'St Georges Tce',    'Level 8',   'Perth',     'Office 3',   'Northbridge',      '6003',
     '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(19, 'Huu Thang',    'Mac',     '1984-09-30', 'A', 'Mr',  '65', '909010121', 'huuthang@example.com',
     '0264455667', 'PHONE', 'EMAIL', '0401234567', '0353344556',
     'Male',   '150', 'Broadway',          NULL,         'Sydney',    'Unit 14',    'Ultimo',           '2007',
     '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(20, 'Kim Ngan',     'Cao',     '1997-11-22', 'I', 'Ms',  '60', '010121232', 'kimngan@example.com',
     '0375566778', 'EMAIL', 'PHONE', '0412345679', '0364455667',
     'Female', '11',  'Lonsdale Street',   'Unit 2',    'Melbourne', 'Apt 5D',     'Fitzroy',          '3065',
     '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(21, 'Trung Kien',   'Vu',      '1990-02-14', 'A', 'Mr',  '65', '121232343', 'trungkien@example.com',
     '0285566778', 'PHONE', 'EMAIL', '0423456780', '0396543210',
     'Male',   '30',  'Market Street',     NULL,         'Sydney',    'Level 6',    'Pyrmont',          '2009',
     '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(22, 'Hai Yen',      'Phan',    '1993-08-05', 'A', 'Ms',  '62', '232343454', 'haiyen@example.com',
     '0396677889', 'EMAIL', 'PHONE', '0434567892', '0385566778',
     'Female', '48',  'Exhibition Street', 'Level 2',   'Melbourne', 'Tower B',    'Collingwood',      '3066',
     '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(23, 'Duy Khang',    'Luu',     '1986-04-17', 'A', 'Mr',  '67', '343454565', 'duykhang@example.com',
     '0756677889', 'PHONE', 'PHONE', '0478901235', '0745566779',
     'Male',   '6',   'Ann Street',        NULL,         'Brisbane',  'Suite 3',    'Kangaroo Point',   '4169',
     '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(24, 'Thanh Tam',    'Ho',      '1999-06-28', 'A', 'Ms',  '63', '454565676', 'thanhtam@example.com',
     '0889900112', 'EMAIL', 'EMAIL', '0490123456', '0878899001',
     'Female', '99',  'William Street',    'Floor 6',   'Perth',     'Block A',    'Leederville',      '6007',
     '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(25, 'Anh Khoa',     'Nham',    '1981-12-03', 'A', 'Mr',  '65', '565676787', 'anhkhoa@example.com',
     '0276677889', 'PHONE', 'EMAIL', '0405678901', '0365566778',
     'Male',   '75',  'Clarence Street',   NULL,         'Sydney',    'Penthouse',  'Darlinghurst',     '2010',
     '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(26, 'Tuyet Mai',    'Dam',     '1995-07-09', 'A', 'Ms',  '60', '676787898', 'tuyetmai@example.com',
     '0387788990', 'EMAIL', 'PHONE', '0446789013', '0376677889',
     'Female', '21',  'Grattan Street',    'Apt 15',    'Melbourne', 'Unit 8',     'Brunswick',        '3056',
     '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(27, 'Cong Thanh',   'Thai',    '1988-10-21', 'A', 'Mr',  '67', '787898909', 'congthanh@example.com',
     '0767788990', 'PHONE', 'PHONE', '0489012346', '0756677890',
     'Male',   '19',  'Edward Street',     NULL,         'Brisbane',  'Suite 7',    'Woolloongabba',    '4102',
     '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(28, 'Quynh Anh',    'Vuong',   '2002-01-16', 'A', 'Ms',  '63', '898909020', 'quynhanh@example.com',
     '0890011223', 'EMAIL', 'EMAIL', '0401234568', '0889900112',
     'Female', '44',  'Stirling Highway',  'Level 1',   'Perth',     'Office 6',   'Claremont',        '6010',
     '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(29, 'Phi Long',     'Tong',    '1980-05-07', 'A', 'Mr',  '65', '909020131', 'philong@example.com',
     '0267788990', 'PHONE', 'EMAIL', '0412345680', '0356677889',
     'Male',   '120', 'Liverpool Street',  NULL,         'Sydney',    'Unit 20',    'Waterloo',         '2017',
     '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(30, 'Dieu Linh',    'Nghiem',  '2000-09-13', 'I', 'Ms',  '60', '020131242', 'dieulinh@example.com',
     '0378899001', 'EMAIL', 'PHONE', '0423456781', '0367788990',
     'Female', '16',  'Spring Street',     'Unit 4',    'Melbourne', 'Apt 3A',     'Abbotsford',       '3067',
     '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00');
SET IDENTITY_INSERT investors OFF;

-- 7. INVESTOR_ACCOUNTS — 20 accounts spread across 30 investors
-- Some investors have 1 account, some have 0 (new investors not yet opened account)
SET IDENTITY_INSERT investor_accounts ON;
INSERT INTO investor_accounts (id, status, product_id, investor_id, created_at, updated_at) VALUES
-- Investor 1: Super Accumulation
(1,  'A', 1, 1,  '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
-- Investor 2: Super Income Stream
(2,  'A', 2, 2,  '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
-- Investor 3: Pension Balanced
(3,  'A', 3, 3,  '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
-- Investor 4: Direct Investment (inactive)
(4,  'I', 4, 4,  '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
-- Investor 5: Conservative Savings
(5,  'A', 5, 5,  '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
-- Investor 6: Super Accumulation
(6,  'A', 1, 6,  '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
-- Investor 7: Super Income Stream
(7,  'A', 2, 7,  '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
-- Investor 8: Pension Balanced
(8,  'A', 3, 8,  '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
-- Investor 9: Direct Investment
(9,  'A', 4, 9,  '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
-- Investor 10: Conservative Savings (inactive)
(10, 'I', 5, 10, '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
-- Investor 11: Super Accumulation
(11, 'A', 1, 11, '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
-- Investor 12: Super Income Stream
(12, 'A', 2, 12, '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
-- Investor 13: Pension Balanced
(13, 'A', 3, 13, '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
-- Investor 14: Direct Investment
(14, 'A', 4, 14, '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
-- Investor 15: Conservative Savings
(15, 'A', 5, 15, '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
-- Investor 17: Super Accumulation
(16, 'A', 1, 17, '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
-- Investor 19: Super Income Stream
(17, 'A', 2, 19, '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
-- Investor 21: Pension Balanced
(18, 'A', 3, 21, '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
-- Investor 25: Direct Investment
(19, 'A', 4, 25, '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
-- Investor 29: Conservative Savings
(20, 'A', 5, 29, '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00');
SET IDENTITY_INSERT investor_accounts OFF;

-- 8. INVESTOR_ACCOUNT_INVESTMENT (strategy totals 100% per account)
-- Products → Investments mapping:
--   Product 1 (Super Accum)     → inv 1 AUS_EQ, 2 INT_EQ, 3 FIXED_IN
--   Product 2 (Super Income)    → inv 4 CASH, 5 PROPERTY
--   Product 3 (Pension Balanced)→ inv 6 BALANCED
--   Product 4 (Direct Invest)   → inv 7 GROWTH
--   Product 5 (Conservative)    → inv 8 CONSERV
SET IDENTITY_INSERT investor_account_investment ON;
INSERT INTO investor_account_investment (id, balance, strategy, units, investment_id, investor_account_id, unit_price_id) VALUES
-- Acct 1 (Inv1 - Product1 Super Accum): 60% AUS_EQ + 40% INT_EQ
(1,  15000.00,  60.00, 12096.77, 1, 1,  2),
(2,  10000.00,  40.00, 10080.64, 2, 1,  4),
-- Acct 2 (Inv2 - Product2 Super Income): 50% CASH + 50% PROPERTY
(3,  20000.00,  50.00, 12903.22, 4, 2,  6),
(4,  20000.00,  50.00,  9523.81, 5, 2,  7),
-- Acct 3 (Inv3 - Product3 Pension Balanced): 100% BALANCED
(5,  50000.00, 100.00, 37878.79, 6, 3,  8),
-- Acct 4 (Inv4 - Product4 Direct Invest, inactive): 100% GROWTH
(6,   5000.00, 100.00,  2645.50, 7, 4,  9),
-- Acct 5 (Inv5 - Product5 Conservative): 100% CONSERV
(7,   8000.00, 100.00,  7960.20, 8, 5,  10),
-- Acct 6 (Inv6 - Product1 Super Accum): 30% AUS_EQ + 30% INT_EQ + 40% FIXED_IN
(8,   9000.00,  30.00,  7258.06, 1, 6,  2),
(9,   9000.00,  30.00,  9072.58, 2, 6,  4),
(10, 12000.00,  40.00, 11881.19, 3, 6,  5),
-- Acct 7 (Inv7 - Product2 Super Income): 70% CASH + 30% PROPERTY
(11, 35000.00,  70.00, 22580.64, 4, 7,  6),
(12, 15000.00,  30.00,  7142.86, 5, 7,  7),
-- Acct 8 (Inv8 - Product3 Pension Balanced): 100% BALANCED
(13, 22000.00, 100.00, 16666.67, 6, 8,  8),
-- Acct 9 (Inv9 - Product4 Direct Invest): 100% GROWTH
(14, 75000.00, 100.00, 39682.54, 7, 9,  9),
-- Acct 10 (Inv10 - Product5 Conservative, inactive): 100% CONSERV
(15,  2000.00, 100.00,  1990.05, 8, 10, 10),
-- Acct 11 (Inv11 - Product1 Super Accum): 50% AUS_EQ + 50% INT_EQ
(16, 25000.00,  50.00, 20161.29, 1, 11, 2),
(17, 25000.00,  50.00, 25201.61, 2, 11, 4),
-- Acct 12 (Inv12 - Product2 Super Income): 40% CASH + 60% PROPERTY
(18, 18000.00,  40.00, 11612.90, 4, 12, 6),
(19, 27000.00,  60.00, 12857.14, 5, 12, 7),
-- Acct 13 (Inv13 - Product3 Pension Balanced): 100% BALANCED
(20, 45000.00, 100.00, 34090.91, 6, 13, 8),
-- Acct 14 (Inv14 - Product4 Direct Invest): 100% GROWTH
(21, 90000.00, 100.00, 47619.05, 7, 14, 9),
-- Acct 15 (Inv15 - Product5 Conservative): 100% CONSERV
(22, 12000.00, 100.00, 11940.30, 8, 15, 10),
-- Acct 16 (Inv17 - Product1 Super Accum): 100% AUS_EQ
(23, 30000.00, 100.00, 24193.55, 1, 16, 2),
-- Acct 17 (Inv19 - Product2 Super Income): 60% CASH + 40% PROPERTY
(24, 24000.00,  60.00, 15483.87, 4, 17, 6),
(25, 16000.00,  40.00,  7619.05, 5, 17, 7),
-- Acct 18 (Inv21 - Product3 Pension Balanced): 100% BALANCED
(26, 55000.00, 100.00, 41666.67, 6, 18, 8),
-- Acct 19 (Inv25 - Product4 Direct Invest): 100% GROWTH
(27, 60000.00, 100.00, 31746.03, 7, 19, 9),
-- Acct 20 (Inv29 - Product5 Conservative): 100% CONSERV
(28,  4500.00, 100.00,  4477.61, 8, 20, 10);
SET IDENTITY_INSERT investor_account_investment OFF;

-- 9. USERS
SET IDENTITY_INSERT users ON;
INSERT INTO users (id, user_name, hashed_password, reset_password, created_at, updated_at) VALUES
(1, 'admin',    'admin123',    0, '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(2, 'operator', 'operator123', 0, '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00');
SET IDENTITY_INSERT users OFF;

-- 10. TOKENS
SET IDENTITY_INSERT tokens ON;
INSERT INTO tokens (id, value, expiration, customer_id, admin_id, created_at, updated_at) VALUES
(1, 'a1b2c3d4e5f6g7h8i9j0', '2026-06-12 10:00:00 +07:00', NULL, 1, '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(2, 'z9y8x7w6v5u4t3s2r1q0', '2026-06-12 10:00:00 +07:00', NULL, 2, '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00');
SET IDENTITY_INSERT tokens OFF;

-- 11. ROLE
SET IDENTITY_INSERT Role ON;
INSERT INTO Role (id, code, description, created_at, updated_at) VALUES
(1, 'ADMIN',    'System Administrator',  '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(2, 'OPERATOR', 'Standard Operator',     '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(3, 'VIEWER',   'Read-only Viewer',      '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00');
SET IDENTITY_INSERT Role OFF;

-- 12. PERMISSION
SET IDENTITY_INSERT permission ON;
INSERT INTO permission (id, action, created_at, updated_at) VALUES
(1, 'INVESTOR_CREATE',  '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(2, 'INVESTOR_READ',    '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(3, 'INVESTOR_UPDATE',  '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(4, 'INVESTOR_DELETE',  '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(5, 'PRODUCT_MANAGE',   '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(6, 'REPORT_VIEW',      '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00');
SET IDENTITY_INSERT permission OFF;

-- 13. ROLE_PERMISSION
INSERT INTO role_permission (role_id, permission_id, created_at, updated_at) VALUES
-- ADMIN has all permissions
(1, 1, '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(1, 2, '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(1, 3, '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(1, 4, '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(1, 5, '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(1, 6, '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
-- OPERATOR: CRUD investor + report
(2, 1, '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(2, 2, '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(2, 3, '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(2, 6, '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
-- VIEWER: read investor + report only
(3, 2, '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00'),
(3, 6, '2026-05-12 10:00:00 +07:00', '2026-05-12 10:00:00 +07:00');
