-- Description: Insert data into the CoinCap Challenge application tables

-- Insert data into the user table
INSERT INTO user (id, nickname) VALUES
(1, 'EnduranceCode');

-- Insert data into the wallet table
INSERT INTO wallet (id, user_id) VALUES
(1, 1);

-- Insert data into the token table
INSERT INTO token (symbol, price, added_at, updated_at) VALUES
('BTC', 100000.00, '2025-01-01 00:00:00', '2025-01-01 00:00:00'),
('ETH', 4000.00, '2025-01-01 00:00:00', '2025-01-01 00:00:00');

-- Insert data into the asset table
INSERT INTO asset (wallet_id, token_symbol, quantity) VALUES
(1, 'BTC', 1.5),
(1, 'ETH', 2.0);
