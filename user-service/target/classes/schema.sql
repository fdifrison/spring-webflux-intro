-- Create users table
CREATE TABLE IF NOT EXISTS users (
                                     id BIGSERIAL PRIMARY KEY,
                                     name VARCHAR(50),
    balance NUMERIC(20)
    );

-- Create user_transaction table
CREATE TABLE IF NOT EXISTS user_transaction (
                                                id BIGSERIAL PRIMARY KEY,
                                                user_id BIGINT,
                                                amount NUMERIC(20),
                                                transaction_date TIMESTAMP,
                                                FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
    );
