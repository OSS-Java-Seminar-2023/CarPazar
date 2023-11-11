
CREATE TABLE user(
    id varchar(36) NOT NULL,
    user_rating varchar(255),
    user_name varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    phone_number int NOT NULL,
    is_premium bool,
    PRIMARY KEY (id)
);

CREATE TABLE listing(
    id varchar(36),
    user_id varchar(36) NOT NULL,
    category varchar(255) NOT NULL,
    date_of_post date NOT NULL,
    title varchar(255) NOT NULL,
    description varchar(255) NOT NULL,
    price int NOT NULL,
    is_sponsored bool,
    is_sold int NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE specification(
    id varchar(36),
    brand varchar(255) NOT NULL,
    model varchar(255) NOT NULL,
    engine_power int NOT NULL,
    type_of_engine varchar(255)NOT NULL,
    shifter varchar(255) NOT NULL,
    kilometers_traveled int NOT NULL,
    year_of_manufacture year NOT NULL,
    in_traffic_since datetime,
    number_of_doors int,
    location varchar(255) NOT NULL,
    registration_until date NOT NULL,
    color varchar(255),
    owner_num varchar(255),
    extra_features varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE review(
    id varchar(36),
    review varchar(255),
    user_id varchar(36),
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE chat(
    id varchar(36),
    listing_id varchar(36) NOT NULL,
    buyer_id varchar(36) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (listing_id) REFERENCES listing(id),
    FOREIGN KEY (buyer_id) REFERENCES user(id)
);

CREATE TABLE message(
    id varchar(36),
    chat_id varchar(36) NOT NULL,
    message_content varchar(255) NOT NULL,
    message_datetime datetime NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (chat_id) REFERENCES chat(id)
);
