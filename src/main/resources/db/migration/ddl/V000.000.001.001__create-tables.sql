-- Description: Creates the tables for the CoinCap Challenge application

CREATE TABLE IF NOT EXISTS user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nickname VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS wallet (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    INDEX idx_wallet_user_id (user_id)
);

ALTER TABLE wallet ADD FOREIGN KEY (user_id) REFERENCES user(id);

CREATE TABLE IF NOT EXISTS token (
    symbol VARCHAR(10) NOT NULL PRIMARY KEY,
    price DECIMAL(20, 10) NOT NULL,
    added_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS asset (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    wallet_id BIGINT NOT NULL,
    token_symbol VARCHAR(10) NOT NULL,
    quantity DECIMAL(20, 10) NOT NULL,
    FOREIGN KEY (wallet_id) REFERENCES wallet(id),
    FOREIGN KEY (token_symbol) REFERENCES token(symbol),
    INDEX idx_asset_wallet_id (wallet_id),
    UNIQUE INDEX unique_asset_token_symbol (token_symbol)
);
