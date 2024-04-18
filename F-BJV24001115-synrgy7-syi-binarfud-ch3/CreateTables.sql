-- Tabel Users
CREATE TABLE Users (
    id UUID PRIMARY KEY,
    username VARCHAR(255),
    email_address VARCHAR(255),
    password VARCHAR(255)
);

-- Tabel Merchant
CREATE TABLE Merchant (
    id UUID PRIMARY KEY,
    merchant_name VARCHAR(255),
    merchant_location VARCHAR(255),
    open BOOLEAN
);

-- Tabel Product
CREATE TABLE Product (
    id UUID PRIMARY KEY,
    product_name VARCHAR(255),
    price DECIMAL,
    merchant_id UUID,
    FOREIGN KEY (merchant_id) REFERENCES Merchant(id)
);

-- Tabel Order
CREATE TABLE Order (
    id UUID PRIMARY KEY,
    order_time TIMESTAMP,
    destination_address VARCHAR(255),
    user_id UUID,
    completed BOOLEAN,
    FOREIGN KEY (user_id) REFERENCES Users(id)
);

-- Tabel Order Detail
CREATE TABLE Order_Detail (
    id UUID PRIMARY KEY,
    order_id UUID,
    product_id UUID,
    quantity INTEGER,
    total_price DECIMAL,
    FOREIGN KEY (order_id) REFERENCES Order(id),
    FOREIGN KEY (product_id) REFERENCES Product(id)
);
