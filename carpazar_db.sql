
CREATE TABLE user(
    id varchar(36) NOT NULL,
    user_rating tinyint(4),
    user_name varchar(32) NOT NULL,
    email varchar(64) NOT NULL,
    phone_number varchar(16),
    is_premium bool NOT NULL,
    full_name varchar(64),
    PRIMARY KEY (id)
);

CREATE TABLE listing(
    id varchar(36) NOT NULL,
    user_id varchar(36) NOT NULL,
    listing_datetime datetime NOT NULL,
    title varchar(64) NOT NULL,
    description text NOT NULL,
    price decimal(10,0) NOT NULL,
    is_sponsored bool NOT NULL,
    is_sold bool NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE specification(
    id varchar(36) NOT NULL,
    brand varchar(32) NOT NULL,
    model varchar(32) NOT NULL,
    engine_power tinyint(4) NOT NULL,
    engine_type varchar(16)NOT NULL,
    shifter_type varchar(32) NOT NULL,
    kilometers_travelled int(11) NOT NULL,
    manufacture_year year(4) NOT NULL,
    in_traffic_since year(4),
    door_count tinyint(4),
    gear_count tinyint(4),
    location varchar(64) NOT NULL,
    body_shape varchar(16),
    is_used bool NOT NULL,
    drive_type varchar(32),
    consumption decimal(10,0),
    ac_type varchar(32),
    seat_count tinyint(4),
    registration_until date NOT NULL,
    owner_no tinyint(4) NOT NULL,
    color varchar(32),
    additional_equipment int(11),
    extra_features int(11),
    PRIMARY KEY (id)
);

CREATE TABLE review(
    id varchar(36) NOT NULL,
    review text NOT NULL,
    user_id varchar(36) NOT NULL,
    rating tinyint(4) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE chat(
    id varchar(36) NOT NULL,
    listing_id varchar(36) NOT NULL,
    buyer_id varchar(36) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (listing_id) REFERENCES listing(id),
    FOREIGN KEY (buyer_id) REFERENCES user(id)
);

CREATE TABLE message(
    id varchar(36) NOT NULL,
    chat_id varchar(36) NOT NULL,
    message_content text NOT NULL,
    message_datetime datetime NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (chat_id) REFERENCES chat(id)
);
