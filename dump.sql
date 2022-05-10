--
-- PostgreSQL database dump
--

-- Dumped from database version 13.6 (Ubuntu 13.6-1.pgdg20.04+1)
-- Dumped by pg_dump version 14.2

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

SET default_table_access_method = heap;

--
-- Name: tasks_table; Type: TABLE; Schema: public; Owner: bocxxkiemgufar
--

CREATE TABLE public.tasks_table (
    id integer NOT NULL,
    title text,
    author text,
    text text
);


ALTER TABLE public.tasks_table OWNER TO bocxxkiemgufar;

--
-- Name: tasks_table_id_seq; Type: SEQUENCE; Schema: public; Owner: bocxxkiemgufar
--

CREATE SEQUENCE public.tasks_table_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tasks_table_id_seq OWNER TO bocxxkiemgufar;

--
-- Name: tasks_table_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: bocxxkiemgufar
--

ALTER SEQUENCE public.tasks_table_id_seq OWNED BY public.tasks_table.id;


--
-- Name: tasks_table id; Type: DEFAULT; Schema: public; Owner: bocxxkiemgufar
--

ALTER TABLE ONLY public.tasks_table ALTER COLUMN id SET DEFAULT nextval('public.tasks_table_id_seq'::regclass);


--
-- Data for Name: tasks_table; Type: TABLE DATA; Schema: public; Owner: bocxxkiemgufar
--

COPY public.tasks_table (id, title, author, text) FROM stdin;
2	task3	user	test task 3
8	task5	user2	test task 5 
3	task4	user	test task 4
1	task2	user3	test task 2
\.


--
-- Name: tasks_table_id_seq; Type: SEQUENCE SET; Schema: public; Owner: bocxxkiemgufar
--

SELECT pg_catalog.setval('public.tasks_table_id_seq', 10, true);


--
-- Name: tasks_table tasks_table_pk; Type: CONSTRAINT; Schema: public; Owner: bocxxkiemgufar
--

ALTER TABLE ONLY public.tasks_table
    ADD CONSTRAINT tasks_table_pk PRIMARY KEY (id);


--
-- Name: LANGUAGE plpgsql; Type: ACL; Schema: -; Owner: postgres
--

GRANT ALL ON LANGUAGE plpgsql TO bocxxkiemgufar;


--
-- PostgreSQL database dump complete
--

