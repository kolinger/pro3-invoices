--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.2
-- Dumped by pg_dump version 9.3.1
-- Started on 2014-05-14 10:50:47

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 171 (class 1259 OID 25152)
-- Name: clients; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE clients (
    id bigserial NOT NULL,
    deleted boolean NOT NULL DEFAULT false,
    city character varying(255) NOT NULL,
    comment text,
    companyin character varying(8),
    email character varying(255),
    name character varying(255) NOT NULL,
    phone character varying(255),
    street character varying(255) NOT NULL,
    vatid character varying(10),
    zip character varying(5) NOT NULL,
    company_id bigint NOT NULL
);


--
-- TOC entry 172 (class 1259 OID 25160)
-- Name: companies; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE companies (
    id bigserial NOT NULL,
    deleted boolean NOT NULL DEFAULT false,
    bankaccount character varying(255),
    city character varying(255) NOT NULL,
    comment text,
    companyin character varying(8) NOT NULL,
    email character varying(255),
    name character varying(255) NOT NULL,
    phone character varying(255),
    street character varying(255) NOT NULL,
    traderegister character varying(255) NOT NULL,
    vatid character varying(10),
    website character varying(255),
    zip character varying(5) NOT NULL
);


--
-- TOC entry 173 (class 1259 OID 25168)
-- Name: invoice_products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE invoice_products (
    id bigserial NOT NULL,
    count integer NOT NULL,
    price numeric(19,2) NOT NULL,
    tax integer NOT NULL,
    warranty integer NOT NULL,
    invoice_id bigint NOT NULL,
    product_id bigint NOT NULL
);


--
-- TOC entry 178 (class 1259 OID 25258)
-- Name: invoices_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE invoices_id_seq
    START WITH 1000000000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 174 (class 1259 OID 25173)
-- Name: invoices; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE invoices (
    id bigint NOT NULL DEFAULT nextval('invoices_id_seq'::regclass),
    deleted boolean NOT NULL DEFAULT false,
    comment text,
    createdate timestamp without time zone NOT NULL,
    enddate timestamp without time zone NOT NULL,
    type integer NOT NULL,
    client_id bigint NOT NULL,
    company_id bigint NOT NULL
);


--
-- TOC entry 175 (class 1259 OID 25181)
-- Name: managers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE managers (
    id bigserial NOT NULL,
    deleted boolean NOT NULL DEFAULT false,
    password character varying(255) NOT NULL,
    username character varying(255) NOT NULL
);


--
-- TOC entry 177 (class 1259 OID 25245)
-- Name: payments; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE payments (
    id bigserial NOT NULL,
    deleted boolean NOT NULL DEFAULT false,
    amount numeric(19,2) NOT NULL,
    comment text,
    date timestamp without time zone NOT NULL,
    invoice_id bigint NOT NULL
);


--
-- TOC entry 179 (class 1259 OID 25260)
-- Name: permissions; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE permissions (
    roleclients boolean NOT NULL,
    rolecompany boolean NOT NULL,
    roleinvoices boolean NOT NULL,
    rolepayments boolean NOT NULL,
    rolepermissions boolean NOT NULL,
    roleproducts boolean NOT NULL,
    manager_id bigint NOT NULL,
    company_id bigint NOT NULL
);


--
-- TOC entry 176 (class 1259 OID 25194)
-- Name: products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE products (
    id bigserial NOT NULL,
    deleted boolean NOT NULL DEFAULT false,
    comment text,
    count integer,
    name character varying(255) NOT NULL,
    price numeric(19,2),
    tax integer,
    warranty integer,
    company_id bigint NOT NULL
);


--
-- TOC entry 180 (class 1259 OID 33043)
-- Name: v_invoices; Type: VIEW; Schema: public; Owner: -
--

CREATE VIEW v_invoices AS
 SELECT invoices.id,
    invoices.deleted,
    invoices.comment,
    invoices.createdate,
    invoices.enddate,
    invoices.type,
    invoices.client_id,
    invoices.company_id,
    COALESCE(( SELECT sum((((p.price / (100)::numeric) * ((100 + p.tax))::numeric) * (p.count)::numeric)) AS sum
           FROM invoice_products p
          WHERE (p.invoice_id = invoices.id)), (0)::numeric) AS amount,
    COALESCE(( SELECT sum(p2.amount) AS sum
           FROM payments p2
          WHERE (p2.invoice_id = invoices.id)), (0)::numeric) AS payed
   FROM invoices;


--
-- TOC entry 1864 (class 2606 OID 25159)
-- Name: clients_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY clients
    ADD CONSTRAINT clients_pkey PRIMARY KEY (id);


--
-- TOC entry 1866 (class 2606 OID 25167)
-- Name: companies_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY companies
    ADD CONSTRAINT companies_pkey PRIMARY KEY (id);


--
-- TOC entry 1868 (class 2606 OID 25172)
-- Name: invoice_products_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY invoice_products
    ADD CONSTRAINT invoice_products_pkey PRIMARY KEY (id);


--
-- TOC entry 1872 (class 2606 OID 25180)
-- Name: invoices_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT invoices_pkey PRIMARY KEY (id);


--
-- TOC entry 1874 (class 2606 OID 25188)
-- Name: managers_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY managers
    ADD CONSTRAINT managers_pkey PRIMARY KEY (id);


--
-- TOC entry 1882 (class 2606 OID 25252)
-- Name: payments_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY payments
    ADD CONSTRAINT payments_pkey PRIMARY KEY (id);


--
-- TOC entry 1885 (class 2606 OID 25264)
-- Name: permissions_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY permissions
    ADD CONSTRAINT permissions_pkey PRIMARY KEY (manager_id, company_id);


--
-- TOC entry 1878 (class 2606 OID 25201)
-- Name: products_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);


--
-- TOC entry 1876 (class 2606 OID 25203)
-- Name: uk_47i207jqaocudxi77kquurcr4; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY managers
    ADD CONSTRAINT uk_47i207jqaocudxi77kquurcr4 UNIQUE (username);


--
-- TOC entry 1892 (class 2606 OID 25253)
-- Name: fk_24qk9mseaueib5d38726wq6rm; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY payments
    ADD CONSTRAINT fk_24qk9mseaueib5d38726wq6rm FOREIGN KEY (invoice_id) REFERENCES invoices(id);


--
-- TOC entry 1894 (class 2606 OID 25270)
-- Name: fk_4gknoeyqq9a2lop4imdhmqp6a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY permissions
    ADD CONSTRAINT fk_4gknoeyqq9a2lop4imdhmqp6a FOREIGN KEY (company_id) REFERENCES companies(id);


--
-- TOC entry 1891 (class 2606 OID 25239)
-- Name: fk_6tpha91n9hgq9ggopipewe0xj; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_6tpha91n9hgq9ggopipewe0xj FOREIGN KEY (company_id) REFERENCES companies(id);


--
-- TOC entry 1887 (class 2606 OID 25209)
-- Name: fk_dcpdiaf3vdlfdywhis9a4k26; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoice_products
    ADD CONSTRAINT fk_dcpdiaf3vdlfdywhis9a4k26 FOREIGN KEY (invoice_id) REFERENCES invoices(id);


--
-- TOC entry 1890 (class 2606 OID 25224)
-- Name: fk_g4agw9eqgj58cqetp40ywdg0l; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_g4agw9eqgj58cqetp40ywdg0l FOREIGN KEY (company_id) REFERENCES companies(id);


--
-- TOC entry 1893 (class 2606 OID 25265)
-- Name: fk_gdk0pc93p4a9mpui1em4kpc5t; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY permissions
    ADD CONSTRAINT fk_gdk0pc93p4a9mpui1em4kpc5t FOREIGN KEY (manager_id) REFERENCES managers(id);


--
-- TOC entry 1889 (class 2606 OID 25219)
-- Name: fk_gm5ua9ncc9222p2givu82xcqh; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_gm5ua9ncc9222p2givu82xcqh FOREIGN KEY (client_id) REFERENCES clients(id);


--
-- TOC entry 1886 (class 2606 OID 25204)
-- Name: fk_k99vcad52dm2r49d461t3xe9h; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY clients
    ADD CONSTRAINT fk_k99vcad52dm2r49d461t3xe9h FOREIGN KEY (company_id) REFERENCES companies(id);


--
-- TOC entry 1888 (class 2606 OID 25214)
-- Name: fk_lnf1nog21tiq933fvt3y66yhd; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoice_products
    ADD CONSTRAINT fk_lnf1nog21tiq933fvt3y66yhd FOREIGN KEY (product_id) REFERENCES products(id);


-- indexes
-- companies
CREATE INDEX companies_name_fulltext ON companies USING gin(to_tsvector('english', name));
CREATE INDEX companies_address_fulltext ON companies USING gin(to_tsvector('english', street || ' ' || city || ' ' || zip));
CREATE INDEX companies_companyin_idx ON companies (companyin varchar_pattern_ops);
CREATE INDEX companies_vatid_idx ON companies (vatid varchar_pattern_ops);
CREATE INDEX companies_deleted_idx ON companies (deleted);

-- permissions
CREATE INDEX permissions_company_id_idx ON permissions (company_id);
CREATE INDEX permissions_manager_id_idx ON permissions (manager_id);

-- clients
CREATE INDEX clients_company_id_idx ON clients (company_id);
CREATE INDEX clients_name_fulltext ON clients USING gin(to_tsvector('english', name));
CREATE INDEX clients_address_fulltext ON clients USING gin(to_tsvector('english', street || ' ' || city || ' ' || zip));
CREATE INDEX clients_companyin_idx ON clients (companyin varchar_pattern_ops);
CREATE INDEX clients_vatid_idx ON clients (vatid varchar_pattern_ops);
CREATE INDEX clients_deleted_idx ON clients (deleted);

-- invoices
CREATE INDEX invoices_company_id_idx ON invoices (company_id);
CREATE INDEX invoices_client_id_idx ON invoices (client_id);
CREATE INDEX invoices_type_idx ON invoices (type);
CREATE INDEX invoices_createdate_id_idx ON invoices (createdate);
CREATE INDEX invoices_enddate_id_idx ON invoices (enddate);
CREATE INDEX invoices_deleted_idx ON invoices (deleted);

-- invoice_products
CREATE INDEX invoice_products_invoice_id_idx ON invoice_products (invoice_id);
CREATE INDEX invoice_products_product_id_idx ON invoice_products (product_id);

-- products
CREATE INDEX products_company_id_idx ON products (company_id);
CREATE INDEX products_name_fulltext ON products USING gin(to_tsvector('english', name));
CREATE INDEX products_tax_idx ON products (tax);
CREATE INDEX products_price_idx ON products (price);
CREATE INDEX products_deleted_idx ON products (deleted);

-- payments
CREATE INDEX payments_invoice_id_idx ON payments (invoice_id);
CREATE INDEX payments_amount_idx ON payments (amount);
CREATE INDEX payments_date_idx ON payments (date);
CREATE INDEX payments_deleted_idx ON payments (deleted);


-- Completed on 2014-05-14 10:50:47

--
-- PostgreSQL database dump complete
--

