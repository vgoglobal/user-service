create TABLE IF NOT EXISTS users
(
    id character varying  NOT NULL,
    first_name character varying ,
    last_name character varying ,
    email character varying,
    password character varying,
    country character varying,
    address character varying ,
    mobile integer,
    confirmation_code character varying ,
    role character varying(50),
    created_date timestamp with time zone,
    update_date timestamp with time zone,
    confirmed boolean,
    user_fk character varying(255) ,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT email_uk UNIQUE (email)
);

create TABLE IF NOT EXISTS miner
(
    id character varying(255) ,
    created_date timestamp without time zone NOT NULL,
    resource_address character varying(255) ,
    resource_code character varying(255) ,
    resource_currency character varying(255) ,
    resource_name character varying(255),
    resource_number character varying(255) ,
    resource_type character varying(255) ,
    update_date timestamp without time zone,
    user_id character varying(255),
    CONSTRAINT miner_pkey PRIMARY KEY (id)
);

create TABLE IF NOT EXISTS wallet
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

create TABLE IF NOT EXISTS country_wallet
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

create TABLE IF NOT EXISTS exchange_order
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

create TABLE IF NOT EXISTS order_details
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
        ON update CASCADE
        ON delete CASCADE
        NOT VALID,
    CONSTRAINT local_miner_fk FOREIGN KEY (onshore_miner_id)
        REFERENCES miner (id) MATCH SIMPLE
        ON update CASCADE
        ON delete CASCADE
        NOT VALID,
    CONSTRAINT offshore_miner_fk FOREIGN KEY (offshore_miner_id)
        REFERENCES miner (id) MATCH SIMPLE
        ON update CASCADE
        ON delete CASCADE
        NOT VALID
);

create TABLE IF NOT EXISTS order_status
(
    id character varying NOT NULL,
    status character varying,
    order_id character varying,
    updated_by character varying,
    create_date timestamp with time zone,
    update_date timestamp with time zone,
    PRIMARY KEY (id),
    CONSTRAINT order_fk FOREIGN KEY (order_id)
        REFERENCES public.exchange_order (id) MATCH SIMPLE
        ON update NO ACTION
        ON delete NO ACTION
        NOT VALID,
    CONSTRAINT user_fk FOREIGN KEY (updated_by)
        REFERENCES public.users (id) MATCH SIMPLE
        ON update NO ACTION
        ON delete NO ACTION
        NOT VALID
);