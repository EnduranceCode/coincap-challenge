-- Description: Fix the constraints for the tables asset and token

-- Fix the constraints on the table asset
CREATE INDEX idx_asset_token_symbol ON asset (token_symbol);
ALTER TABLE asset DROP INDEX unique_asset_token_symbol;

-- Make nullable the column updated_at in the table token
ALTER TABLE token MODIFY COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
