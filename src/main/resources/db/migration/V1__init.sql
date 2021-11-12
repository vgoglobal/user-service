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

CREATE TABLE IF NOT EXISTS exchange_order
(
    id character varying,
    amount double precision,
    recipient_amount double precision,
    transfer_fee double precision,
    recipient_id character varying,
    user_id character varying,
    from_currency character varying,
    to_currency character varying,
    ref_id character varying,
    status character varying,
    transfer_ref character varying,
    picked_by character varying,
    create_date time with time zone,
    update_date timestamp with time zone,
    order_details character varying,
    PRIMARY KEY (id)
);

--ALTER TABLE exchange_order
  --  ADD COLUMN onshore_miner character varying(255);

--ALTER TABLE exchange_order
  --  ADD COLUMN offshore_miner character varying(255);

--ALTER TABLE exchange_order
  --  ADD CONSTRAINT onshore_miner FOREIGN KEY (onshore_miner)
    --REFERENCES public.miner (id)
    --ON UPDATE NO ACTION
    --ON DELETE NO ACTION
    --NOT VALID;

--ALTER TABLE exchange_order
  --  ADD CONSTRAINT offshore_miner FOREIGN KEY (offshore_miner)
    --REFERENCES public.miner (id)
    --ON UPDATE NO ACTION
    --ON DELETE NO ACTION
    --NOT VALID;

CREATE TABLE IF NOT EXISTS order_details
(
    id character varying NOT NULL,
    order_id character varying(255),
    onshore_miner_id character varying(255),
    offshore_miner_id character varying(255),
    create_date timestamp with time zone,
    update_date timestamp with time zone,
    PRIMARY KEY (id),
    CONSTRAINT order_fk FOREIGN KEY (order_id)
        REFERENCES exchange_order (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID,
    CONSTRAINT local_miner_fk FOREIGN KEY (onshore_miner_id)
        REFERENCES miner (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID,
    CONSTRAINT offshore_miner_fk FOREIGN KEY (offshore_miner_id)
        REFERENCES miner (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID
);

--ALTER TABLE exchange_order
--    ADD CONSTRAINT order_details FOREIGN KEY (order_details)
--    REFERENCES order_details (id)
--    ON UPDATE NO ACTION
 --   ON DELETE NO ACTION
 --   NOT VALID;