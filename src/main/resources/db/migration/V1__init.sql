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
    miner_services text[],
    CONSTRAINT miner_pkey PRIMARY KEY (id)
);

create TABLE IF NOT EXISTS wallet
(
    id character varying NOT NULL,
    mobile integer,
    otp character varying,
    otp_confirmed boolean,
    user_id character varying,
    created_date timestamp with time zone,
    update_date timestamp with time zone,
    CONSTRAINT wallet_pkey PRIMARY KEY (id)
);

create TABLE IF NOT EXISTS wallet_details
(
    id character varying  NOT NULL,
    currency character varying ,
    amount double precision,
    hold_amount double precision,
    base_currency boolean,
    resource_type character varying,
    create_date timestamp with time zone,
    update_date timestamp with time zone,
    wallet_id character varying,
    CONSTRAINT wallet_details_pkey PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS public.recipient
(
    id character varying(255),
    address character varying(255),
    code character varying(255),
    create_date timestamp without time zone,
    currency character varying(255),
    institution character varying(255),
    institution_type character varying(255),
    name character varying(255),
    "number" character varying(255),
    update_date timestamp without time zone,
    user_id character varying(255),
    CONSTRAINT recipient_pkey PRIMARY KEY (id)
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
    user_file_upload character varying,
    create_date time with time zone,
    update_date timestamp with time zone,
    order_details character varying,
    PRIMARY KEY (id),
    CONSTRAINT recipeint_fk FOREIGN KEY (recipient_id)
        REFERENCES recipient (id)
        ON update CASCADE
        ON delete CASCADE
        NOT VALID
);

create TABLE IF NOT EXISTS order_details
(
    id character varying NOT NULL,
    order_id character varying(255),
    source_miner_id character varying(255),
    target_miner_id character varying(255),
    create_date timestamp with time zone,
    update_date timestamp with time zone,
    pick_date timestamp with time zone,
    delivery_date timestamp with time zone,
    canceled_date timestamp with time zone,
    remarks character varying,
    status character varying,
    delivery_ref character varying,
    PRIMARY KEY (id),
    CONSTRAINT order_fk FOREIGN KEY (order_id)
        REFERENCES exchange_order (id) MATCH SIMPLE
        ON update CASCADE
        ON delete CASCADE
        NOT VALID,
    CONSTRAINT local_miner_fk FOREIGN KEY (source_miner_id)
        REFERENCES miner (id) MATCH SIMPLE
        ON update CASCADE
        ON delete CASCADE
        NOT VALID,
    CONSTRAINT offshore_miner_fk FOREIGN KEY (target_miner_id)
        REFERENCES miner (id) MATCH SIMPLE
        ON update CASCADE
        ON delete CASCADE
        NOT VALID
);

CREATE TABLE IF NOT EXISTS address
(
    id character varying NOT NULL,
    flat_no character varying(10),
    address1 character varying(100),
    address2 character varying(100),
    city character varying(100),
    state character varying(100),
    country character varying(100),
    pin character varying(15),
    created_date timestamp with time zone,
    update_date timestamp with time zone,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS complaints
(
    id character varying NOT NULL,
    order_id character varying,
    user_id character varying,
    subject character varying,
    comments character varying,
    status boolean,
    create_date timestamp with time zone,
    update_date timestamp with time zone,
    resolved_by character varying,
    CONSTRAINT complaint_pk PRIMARY KEY (id),
    CONSTRAINT "order" FOREIGN KEY (order_id)
        REFERENCES exchange_order (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT "user" FOREIGN KEY (user_id)
        REFERENCES users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

CREATE TABLE IF NOT EXISTS tax
(
    id character varying NOT NULL,
    amount_percent double precision,
    currency character varying(10) NOT NULL,
    amount double precision,
    created_date timestamp with time zone,
    update_date timestamp with time zone,

    PRIMARY KEY (id)
);
