CREATE TABLE funds (
                       name        VARCHAR(255) NOT NULL,
                       code        VARCHAR(50) PRIMARY KEY,
                       status      VARCHAR(10) DEFAULT 'INACTIVE',
                       created_at  DATETIME,
                       updated_at  DATETIME
);

CREATE TABLE product_group (
                               code        VARCHAR(100) PRIMARY KEY,
                               name        VARCHAR(255) NOT NULL,
                               status      VARCHAR(10) DEFAULT 'INACTIVE',
                               fund_code   VARCHAR(100) NOT NULL,
                               created_at  DATETIME,
                               updated_at  DATETIME
);

CREATE TABLE products (
                          code                VARCHAR(100) PRIMARY KEY,
                          name                VARCHAR(255) NOT NULL,
                          type                VARCHAR(100),
                          status              VARCHAR(10) DEFAULT 'INACTIVE',
                          product_group_code  VARCHAR(100) NOT NULL,
                          created_at          DATETIME,
                          updated_at          DATETIME
);

CREATE TABLE investments (
                             code            VARCHAR(50) PRIMARY KEY,
                             name            VARCHAR(255) NOT NULL,
                             status          VARCHAR(10) DEFAULT 'INACTIVE',
                             product_code    VARCHAR(100) NOT NULL,
                             created_at      DATETIME,
                             updated_at      DATETIME
);

CREATE TABLE investor_account_investment (
                                             id                    INT IDENTITY PRIMARY KEY,
                                             strategy              INT,
                                             investment_code       VARCHAR(100) NOT NULL,
                                             investor_account_id   INT NOT NULL,
                                             created_at            DATETIME,
                                             updated_at            DATETIME
);

CREATE TABLE investor_accounts (
                                   id                INT IDENTITY PRIMARY KEY,
                                   status            VARCHAR(10) DEFAULT 'INACTIVE',
                                   investor_id       INT NOT NULL,
                                   product_code      VARCHAR(100) NOT NULL,
                                   start_date        DATE,
                                   created_at        DATETIME,
                                   updated_at        DATETIME
);

CREATE TABLE investors (
                           id                  INT IDENTITY PRIMARY KEY,
                           created_at          DATETIME,
                           updated_at          DATETIME,
                           best_contact_method VARCHAR(50),
                           next_contact_method VARCHAR(50),
                           dbo                 DATE,
                           email               VARCHAR(255),
                           gender              VARCHAR(10),
                           given_names         VARCHAR(255),
                           surname             VARCHAR(255),
                           title               VARCHAR(50),

                           city                VARCHAR(100),
                           district            VARCHAR(100),
                           post_code           VARCHAR(20),
                           property_name       VARCHAR(255),
                           street_name_1       VARCHAR(255),
                           street_name_2       VARCHAR(255),
                           street_number       VARCHAR(50),

                           mobile              VARCHAR(20),
                           primary_phone       VARCHAR(20),
                           secondary_phone     VARCHAR(20),
                           retirement_age      INT,
                           status              VARCHAR(50),
                           tfn                 VARCHAR(255)
);

CREATE TABLE users (
                       id                INT IDENTITY PRIMARY KEY,
                       user_name         VARCHAR(255) NOT NULL UNIQUE,
                       hashed_password   VARCHAR(255) NOT NULL,
                       reset_password    BIT,
                       created_at        DATETIME,
                       updated_at        DATETIME
);