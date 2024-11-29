CREATE TABLE exchange_rate (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    from_currency VARCHAR(3) NOT NULL,
    to_currency VARCHAR(3) NOT NULL,
    rate DOUBLE NOT NULL,
    UNIQUE (from_currency, to_currency)
);

CREATE TABLE exchange_audit (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_name VARCHAR(255),
    initial_amount DECIMAL(18, 4),
    final_amount DECIMAL(18, 4),
    exchange_rate DECIMAL(18, 4),
    process_date TIMESTAMP
);