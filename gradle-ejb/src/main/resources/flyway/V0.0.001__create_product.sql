
CREATE TABLE public.product
(
    id serial NOT NULL,
    name character varying(256),
    price numeric(8,2),
    adddate timestamp with time zone,
    CONSTRAINT pk_product_id PRIMARY KEY (id)
)
