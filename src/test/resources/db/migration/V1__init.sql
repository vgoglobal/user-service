CREATE TABLE IF NOT EXISTS users
(
    id character varying  NOT NULL,
    first_name character varying ,
    last_name character varying ,
    email character varying ,
    country character varying ,
    address character varying ,
    mobile integer,
    confirmation_code character varying ,
    created_date timestamp with time zone,
    update_date timestamp with time zone,
    confirmed boolean,
    user_fk character varying(255) ,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT email_uk UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS wallets
(
    id character varying NOT NULL,
    mobile integer,
    otp character varying,
    otp_confirmed boolean,
    created_date timestamp with time zone,
    update_date timestamp with time zone,
    user_id character varying,
    CONSTRAINT wallet_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS country_wallet
(
    id character varying  NOT NULL,
    currency character varying ,
    amount double precision,
    create_date timestamp with time zone,
    update_date timestamp with time zone,
    wallet_id character varying ,
    wallet_fk character varying(255) ,
    CONSTRAINT country_wallet_pkey PRIMARY KEY (id)
);