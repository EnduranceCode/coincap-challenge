-- Description: Insert data into the CoinCap Challenge application tables for a second user

-- Insert data into the user table
INSERT INTO user (id, nickname) VALUES
(2, 'EnduranceTrio');

-- Insert data into the wallet table
INSERT INTO wallet (id, user_id) VALUES
(2, 2);

-- Update table token.
UPDATE token SET updated_at = NULL;

-- Insert data into the asset table
INSERT INTO asset (wallet_id, token_symbol, quantity) VALUES
(2, 'BTC', 1.5),
(2, 'ETH', 2.0);
