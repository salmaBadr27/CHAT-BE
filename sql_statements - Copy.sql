--
-- PostgreSQL database dump
--

-- Dumped from database version 10.5 (Ubuntu 10.5-1.pgdg14.04+1)
-- Dumped by pg_dump version 10.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: SCHEMA "public"; Type: COMMENT; Schema: -; Owner: gxhxtpymtmgykq
--

COMMENT ON SCHEMA "public" IS 'standard public schema';


--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS "plpgsql" WITH SCHEMA "pg_catalog";


--
-- Name: EXTENSION "plpgsql"; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION "plpgsql" IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: messages; Type: TABLE; Schema: public; Owner: gxhxtpymtmgykq
--

CREATE TABLE "public"."messages" (
    "msg_id" character varying(100) NOT NULL,
    "msg_body" "text" NOT NULL,
    "sender" character varying(100) NOT NULL,
    "receiver" character varying(100) NOT NULL,
    "created_at" timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE public.messages OWNER TO gxhxtpymtmgykq;

--
-- Name: users; Type: TABLE; Schema: public; Owner: gxhxtpymtmgykq
--

CREATE TABLE "public"."users" (
    "user_id" character varying(100) NOT NULL,
    "user_name" character varying(50) NOT NULL,
    "password" character varying(50) NOT NULL,
    "e_mail" character varying(50) NOT NULL,
    "mobile" character varying(50) NOT NULL
);


ALTER TABLE public.users OWNER TO gxhxtpymtmgykq;

--
-- Data for Name: messages; Type: TABLE DATA; Schema: public; Owner: gxhxtpymtmgykq
--

COPY "public"."messages" ("msg_id", "msg_body", "sender", "receiver", "created_at") FROM stdin;
2207e082-2eae-47a9-8e79-eee6361e7543	bngrb el timestamp	amira	mmmm	2018-06-22 20:49:18
36f297db-0975-40ae-9709-e4c6ad4ef458	bngrb el timestamp tany	amira	mmmm	2018-06-22 21:03:39
4ed5cda3-6271-4622-906e-f2a961275e3c	bngrb el timestamp talt	amira	mmmm	2018-06-22 21:05:17
761620ff-1b83-4107-8fe4-15f31d4709b7	bngrb el timestamp rab3	amira	mmmm	2018-06-22 21:15:04
a831006e-18af-4210-8147-e63d13bb031d\\r\\n	bbb	salma	amira	2018-06-22 20:13:56
c5806889-c5ba-4af5-ba2a-90d049b2772e	hi	amira	salma	2018-06-22 20:13:56
nnncldppdddpdpdpdp	bbbb	mmmm	amira	2018-06-22 20:13:56
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: gxhxtpymtmgykq
--

COPY "public"."users" ("user_id", "user_name", "password", "e_mail", "mobile") FROM stdin;
7aa5b089-59ba-476c-bf8f-ee7203df04f1	amira	123	mervat_mersal@hotmail.com	01228335028
6da7516e-0de0-4ac8-a701-5ad1d619fd93	badr4	444	beauty_saloma@hotmail.com	01285507936
c5806889-c5ba-4af5-ba2a-90d049b2772e	mervat	123	mervat_mersal@hotmail.com	01228335028
d4607b8b-3ac4-4792-9113-0d52bb8721b9	mmmm	123	beauty_saloma@hotmail.com	0122258*9963
6c2f4dd3-522d-4089-b020-744cd72a0a45	moner	king	beauty_saloma@hotmail.com	0122258*9963
7a93a08c-0b2f-4ced-9917-2f4c9fecd15c	noha	sala7	m	0122258*9963
a831006e-18af-4210-8147-e63d13bb031d	salma	123	beauty_saloma@hotmail.com	01285507936
cbb4a092-6563-485e-b218-3beea60d94b3	yasser	456	yasser@hotmail.com	0122258*9963
\.


--
-- Name: messages messages_pkey; Type: CONSTRAINT; Schema: public; Owner: gxhxtpymtmgykq
--

ALTER TABLE ONLY "public"."messages"
    ADD CONSTRAINT "messages_pkey" PRIMARY KEY ("msg_id");


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: gxhxtpymtmgykq
--

ALTER TABLE ONLY "public"."users"
    ADD CONSTRAINT "users_pkey" PRIMARY KEY ("user_id");


--
-- Name: users users_user_name_key; Type: CONSTRAINT; Schema: public; Owner: gxhxtpymtmgykq
--

ALTER TABLE ONLY "public"."users"
    ADD CONSTRAINT "users_user_name_key" UNIQUE ("user_name");


--
-- Name: sender; Type: INDEX; Schema: public; Owner: gxhxtpymtmgykq
--

CREATE INDEX "sender" ON "public"."messages" USING "btree" ("sender", "receiver");


--
-- Name: messages messages_ibfk_1; Type: FK CONSTRAINT; Schema: public; Owner: gxhxtpymtmgykq
--

ALTER TABLE ONLY "public"."messages"
    ADD CONSTRAINT "messages_ibfk_1" FOREIGN KEY ("sender") REFERENCES "public"."users"("user_name");


--
-- Name: messages messages_ibfk_2; Type: FK CONSTRAINT; Schema: public; Owner: gxhxtpymtmgykq
--

ALTER TABLE ONLY "public"."messages"
    ADD CONSTRAINT "messages_ibfk_2" FOREIGN KEY ("receiver") REFERENCES "public"."users"("user_name");


--
-- PostgreSQL database dump complete
--

