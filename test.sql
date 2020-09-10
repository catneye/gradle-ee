--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.23
-- Dumped by pg_dump version 12.4 (Ubuntu 12.4-1.pgdg16.04+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

--
-- Name: product; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.product (
    id integer NOT NULL,
    name character varying(256),
    price numeric(8,2),
    adddate timestamp with time zone
);


ALTER TABLE public.product OWNER TO test;

--
-- Name: product_id_seq; Type: SEQUENCE; Schema: public; Owner: test
--

CREATE SEQUENCE public.product_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.product_id_seq OWNER TO test;

--
-- Name: product_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: test
--

ALTER SEQUENCE public.product_id_seq OWNED BY public.product.id;


--
-- Name: product id; Type: DEFAULT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.product ALTER COLUMN id SET DEFAULT nextval('public.product_id_seq'::regclass);


--
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.product (id, name, price, adddate) FROM stdin;
1	p11	12.12	2020-09-10 10:05:52.234+03
\.


--
-- Name: product_id_seq; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.product_id_seq', 2, true);


--
-- Name: product pk_product_id; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT pk_product_id PRIMARY KEY (id);


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

