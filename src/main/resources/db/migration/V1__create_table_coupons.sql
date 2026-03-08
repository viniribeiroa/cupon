CREATE TABLE coupons (
    id UUID PRIMARY KEY,
    code VARCHAR(6) NOT NULL UNIQUE,
    discount NUMERIC(10, 2) NOT NULL,
    expiration_date DATE NOT NULL,
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE INDEX idx_coupons_code ON coupons (code);
CREATE INDEX idx_coupons_deleted ON coupons (deleted);
