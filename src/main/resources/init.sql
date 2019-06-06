DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS products CASCADE;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS order_rows CASCADE;
DROP TABLE IF EXISTS types CASCADE;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    userName VARCHAR(20) NOT NULL,
		CONSTRAINT user_name_not_empty CHECK (userName <> ''),
    email TEXT UNIQUE NOT NULL,
		CONSTRAINT email_not_empty CHECK (email <> ''),
    password TEXT NOT NULL,
		CONSTRAINT password_not_empty CHECK (password <> ''),
    isAdmin boolean DEFAULT FALSE,
	priceModifier FLOAT DEFAULT 1,
		CONSTRAINT price_modifier_is_in_range CHECK (priceModifier >= 0.5 AND priceModifier <= 1)
);

CREATE TABLE types (
    id SERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    	CONSTRAINT name_not_empty CHECK (name <> '')
);

CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
		CONSTRAINT name_not_empty CHECK (name <> ''),
	manufacturer VARCHAR(30) NOT NULL,
		CONSTRAINT manufacturer_not_empty CHECK (manufacturer <> ''),
	type INTEGER NOT NULL,
		CONSTRAINT type_not_empty CHECK (type <> null),
	FOREIGN KEY (type) REFERENCES types(id) ON DELETE CASCADE ON UPDATE CASCADE,
	price INTEGER NOT NULL,
		CONSTRAINT price_not_empty CHECK (price <> null),
	stock INTEGER NOT NULL DEFAULT 0,
		CONSTRAINT stock_not_empty CHECK (stock <> null),
	stockPrice INTEGER NOT NULL,
		CONSTRAINT stockPrice_not_empty CHECK (stockPrice <> null),
	discount FLOAT DEFAULT 0,
		CONSTRAINT discount_is_in_range CHECK (discount >= 0 AND discount <= 0.5),
		CONSTRAINT price_must_be_higher_than_stock_price CHECK (price - (price * discount) > stockPrice),
	picture TEXT
);

CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    userId INTEGER,
    	FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
    total INTEGER DEFAULT 0,
	dateOfCreation DATE DEFAULT current_timestamp
);

CREATE TABLE order_rows (
	orderId INTEGER,
		FOREIGN KEY (orderID) REFERENCES orders(id) ON DELETE CASCADE ON UPDATE CASCADE,
	productId INTEGER,
		FOREIGN KEY (productID) REFERENCES products(id) ON DELETE CASCADE ON UPDATE CASCADE,
	quantity INTEGER NOT NULL DEFAULT 1,
		CONSTRAINT quantity_not_null_and_not_negative CHECK (quantity <> null AND quantity >= 1)
);

CREATE OR REPLACE FUNCTION count_total_value() RETURNS TRIGGER AS '
    DECLARE
        total_price int;
		product_price int;
		price_multiplier float;
    BEGIN
		SELECT total INTO total_price FROM orders WHERE orders.id = NEW.orderId;
		SELECT price INTO product_price FROM products WHERE NEW.productId = products.id;
		SELECT priceModifier INTO price_multiplier FROM users
		LEFT JOIN orders ON users.id = orders.userId WHERE NEW.orderId = orders.id;
        UPDATE orders SET total = total_price + (product_price * NEW.quantity * price_multiplier) WHERE NEW.orderId = orders.id;
        RETURN NEW;
    END; '
    LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION set_discount_value() RETURNS TRIGGER AS '
	DECLARE
		last_date date;
		current_user int;
		multiplier float;
	BEGIN
		SELECT MAX(dateOfCreation) INTO last_date FROM orders WHERE orders.userId = NEW.userId;
		SELECT id INTO current_user FROM users WHERE id = NEW.userId;
		SELECT priceModifier INTO multiplier FROM users WHERE id = NEW.userId;
		IF last_date + (2 ||'' minutes'')::interval <= current_timestamp AND multiplier >= 0.5 
			THEN 
				UPDATE users SET pricemodifier = multiplier - 0.1 WHERE id = NEW.userId;
		END IF;
	RETURN NEW;
	END; '
	LANGUAGE plpgsql;

CREATE TRIGGER count_total
    AFTER INSERT
    ON order_rows
    FOR EACH ROW
EXECUTE PROCEDURE count_total_value();

CREATE TRIGGER set_discount
	BEFORE INSERT
	ON orders
	FOR EACH ROW
EXECUTE PROCEDURE set_discount_value();

INSERT INTO users(userName, email, password) VALUES ('test', 'test@test.com', '1000:7cf3de71fad3e947c667e7a44764cd8b:94659ae984bb90e8179245a5e9cd92a2b3625572d53ae8d9e98127e152ab26892e6b5921cf87248c6f7e775c6279b5578c7b6e197d2f9e17fe28bab722b5a9a3');
INSERT INTO users(userName, email, password) VALUES ('test2', 'test2@test.com', '1000:73e81d9c6e4ec625975f0cf24a5155c4:84a75cd7084cc1deb644626030f541036e0e992bd2a7c6eb513c20c7f1fa3669b1e23ae426ada699bc7a9d1a6ee5f505c9b09940b0a693209c1c59d64d615339');
INSERT INTO users(userName, email, password, isAdmin) VALUES ('admin', 'admin1@test.com', '1000:ef57da2a955e1c60a25e256a2eba5c9f:8a63c1cfdc980394b167952d24389d579a4420071c903a42b12133b9b3a19d6d27eaa74d26292625fc8017b20892e1595e9d10426067818d3b1a0d3a59146791', true);

INSERT INTO types (name) VALUES
	('electric guitar'),
	('acoustic guitar'),
	('electric bass'),
	('acoustic bass'),
	('valve amplifier'),
	('transistor amplifier'),
	('hybrid amplifier'),
	('string set'),
	('cable');
	
INSERT INTO products (name, manufacturer, type, stock, stockPrice, price, picture) VALUES
	('Stratocaster', 'Fender', 1, 3, 350, 700, 'img/fenderstrat.jpg'),
	('Telecaster', 'Fender', 1, 2, 300, 650, 'img/fendertele.jpg'),
	('Les Paul', 'Gibson', 1, 2, 550, 990, 'img/gibsonlp.jpg'),
	('SG', 'Gibson', 1, 3, 450, 800, 'img/gibsonsg.jpg'),
	('GF30CE', 'Takamine', 2, 2, 350, 550, 'img/takaminegf30.jpg'),
	('GN15CE', 'Takamine', 2, 5, 200, 450, 'img/takaminegn15.jpg'),
	('SFX1', 'Cort', 2, 3, 180, 360, 'img/cortsfx1.jpg'),
	('Earth-70', 'Cort', 2, 7, 140, 250, 'img/cortearth70.jpg'),
	('P-bass', 'Fender', 3, 2, 350, 700, 'img/fenderpbass.jpg'),
	('Jazzbass', 'Fender', 3, 1, 500, 1000, 'img/fenderjazzbass.jpg'),
	('AVCB9CE', 'Ibanez', 4, 2, 250, 500, 'img/ibanezavcb.jpg'),
	('Kingman Bass', 'Fender', 4, 1, 400, 750, 'img/fenderkingman.jpg'),
	('AC30', 'VOX', 5, 1, 600, 1100, 'img/voxac30.jpg'),
	('Origin 20C', 'Marshall', 5, 2, 450, 900, 'img/marshallorigin20.jpg'),
	('RockerVerb 30', 'Orange', 5, 1, 550, 1000, 'img/orangerocker30.jpg'),
	('Blues JR', 'Fender', 5, 3, 350, 600, 'img/fenderbluesjr.jpg'),
	('MK-V', 'MesaBoogie', 5, 1, 1000, 2000, 'img/mesamkv.jpg'),
	('ID-40', 'BlackStar', 6, 3, 150, 300, 'img/blackstarid40.jpg'),
	('Champion40', 'Fender', 6, 2, 200, 400, 'img/fenderchamp40.jpg'),
	('Jazz Chorus 120', 'Roland', 6, 1, 700, 1400, 'img/rolandjc120.jpg'),
	('TR-5', 'Yamaha', 6, 3, 170, 350, 'img/yamahatr5.jpg'),
	('AC15VR', 'VOX', 7, 2, 250, 400, 'img/voxac15vr.jpeg'),
	('VS100', 'Marshall', 7, 2, 300, 600, 'img/marshallvs100.jpg'),
	('0.09 - light', 'Ernie-Ball', 8, 20, 5, 10, 'img/ebl.jpeg'),
	('0.10 - regular', 'Ernie-Ball',  8, 15, 5, 10, 'img/ebr.jpg'),
	('0.11 - heavy', 'Ernie-Ball', 	8, 10, 5, 10, 'img/ebh.jpg'),
	('0.09 - Durable', 'Elixir', 8, 10, 15, 30, 'img/elx.jpg'),
	('0.10 - EQ', 'Cleartone', 8 , 5, 15, 30, 'img/clt.jpeg'),
	('5m regular', 'Klotz', 9, 40, 6, 10, 'img/klotz.jpg'),
	('15m regular', 'Klotz', 9, 20, 20, 30, 'img/klotz.jpg'),
	('30m regular', 'Klotz', 9, 10, 35, 50, 'img/klotz.jpg'),
	('5m shielded', 'Klotz', 9, 30, 14, 20, 'img/klotz.jpg'),
	('15m shielded', 'Klotz', 9, 20, 30, 40, 'img/klotz.jpg'),
	('30m shielded', 'Kloztz', 9, 10, 55, 75, 'img/klotz.jpg');

INSERT INTO orders (userId) VALUES
	(1),
	(1);

INSERT INTO order_rows (orderId, productId, quantity) VALUES
	(1, 2, 3),
	(1, 9, 2),
	(1, 13, 3),
	(2, 3, 1),
	(2, 7, 2);
