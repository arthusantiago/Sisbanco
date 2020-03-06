--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.15
-- Dumped by pg_dump version 9.5.15

-- Started on 2018-11-26 22:53:26 -03

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12399)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2206 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 185 (class 1259 OID 16490)
-- Name: agencia; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.agencia (
    id smallint NOT NULL,
    numero_agencia smallint NOT NULL,
    cofre_agencia double precision DEFAULT 0.0 NOT NULL
);


ALTER TABLE public.agencia OWNER TO postgres;

--
-- TOC entry 2207 (class 0 OID 0)
-- Dependencies: 185
-- Name: TABLE agencia; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.agencia IS 'agencias do banco';


--
-- TOC entry 2208 (class 0 OID 0)
-- Dependencies: 185
-- Name: COLUMN agencia.cofre_agencia; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.agencia.cofre_agencia IS 'onde a agencia guarda o dinheiro que obteve sobre as transações';


--
-- TOC entry 183 (class 1259 OID 16486)
-- Name: agencia_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.agencia_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.agencia_id_seq OWNER TO postgres;

--
-- TOC entry 2209 (class 0 OID 0)
-- Dependencies: 183
-- Name: agencia_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.agencia_id_seq OWNED BY public.agencia.id;


--
-- TOC entry 184 (class 1259 OID 16488)
-- Name: agencia_numero_agencia_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.agencia_numero_agencia_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.agencia_numero_agencia_seq OWNER TO postgres;

--
-- TOC entry 2210 (class 0 OID 0)
-- Dependencies: 184
-- Name: agencia_numero_agencia_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.agencia_numero_agencia_seq OWNED BY public.agencia.numero_agencia;


--
-- TOC entry 182 (class 1259 OID 16480)
-- Name: cliente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cliente (
    id smallint NOT NULL,
    nome character(100),
    cpf character(20),
    senha character(50)
);


ALTER TABLE public.cliente OWNER TO postgres;

--
-- TOC entry 181 (class 1259 OID 16478)
-- Name: cliente_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cliente_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cliente_id_seq OWNER TO postgres;

--
-- TOC entry 2211 (class 0 OID 0)
-- Dependencies: 181
-- Name: cliente_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cliente_id_seq OWNED BY public.cliente.id;


--
-- TOC entry 187 (class 1259 OID 16500)
-- Name: conta; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.conta (
    id smallint NOT NULL,
    tipo integer,
    numero_conta bigint,
    saldo double precision,
    limite_credito double precision,
    id_cliente integer,
    id_agencia integer
);


ALTER TABLE public.conta OWNER TO postgres;

--
-- TOC entry 2212 (class 0 OID 0)
-- Dependencies: 187
-- Name: COLUMN conta.tipo; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.conta.tipo IS 'tipo 1 = simples | tipo 2 = especial';


--
-- TOC entry 2213 (class 0 OID 0)
-- Dependencies: 187
-- Name: COLUMN conta.numero_conta; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.conta.numero_conta IS 'numero da conta';


--
-- TOC entry 2214 (class 0 OID 0)
-- Dependencies: 187
-- Name: COLUMN conta.id_cliente; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.conta.id_cliente IS 'dono da conta';


--
-- TOC entry 2215 (class 0 OID 0)
-- Dependencies: 187
-- Name: COLUMN conta.id_agencia; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.conta.id_agencia IS 'agencia ao qual pertence a conta';


--
-- TOC entry 186 (class 1259 OID 16498)
-- Name: conta_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.conta_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.conta_id_seq OWNER TO postgres;

--
-- TOC entry 2216 (class 0 OID 0)
-- Dependencies: 186
-- Name: conta_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.conta_id_seq OWNED BY public.conta.id;


--
-- TOC entry 191 (class 1259 OID 16531)
-- Name: historico_taxa_sobre_transacao; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.historico_taxa_sobre_transacao (
    id smallint NOT NULL,
    valor_taxa double precision,
    id_historico_transacao integer,
    id_agencia_creditada integer
);


ALTER TABLE public.historico_taxa_sobre_transacao OWNER TO postgres;

--
-- TOC entry 2217 (class 0 OID 0)
-- Dependencies: 191
-- Name: TABLE historico_taxa_sobre_transacao; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.historico_taxa_sobre_transacao IS 'Salva a origem da taxa onde o banco lucra';


--
-- TOC entry 190 (class 1259 OID 16529)
-- Name: historico_taxa_sobre_transacao_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.historico_taxa_sobre_transacao_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.historico_taxa_sobre_transacao_id_seq OWNER TO postgres;

--
-- TOC entry 2218 (class 0 OID 0)
-- Dependencies: 190
-- Name: historico_taxa_sobre_transacao_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.historico_taxa_sobre_transacao_id_seq OWNED BY public.historico_taxa_sobre_transacao.id;


--
-- TOC entry 189 (class 1259 OID 16518)
-- Name: transacao; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.transacao (
    id smallint NOT NULL,
    tipo integer,
    valor double precision,
    numero_conta bigint NOT NULL
);


ALTER TABLE public.transacao OWNER TO postgres;

--
-- TOC entry 2219 (class 0 OID 0)
-- Dependencies: 189
-- Name: COLUMN transacao.tipo; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.transacao.tipo IS 'saque=1 | deposito=2 | transf=3 | taxa=4';


--
-- TOC entry 2220 (class 0 OID 0)
-- Dependencies: 189
-- Name: COLUMN transacao.valor; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.transacao.valor IS 'valor envolvido na operação';


--
-- TOC entry 2221 (class 0 OID 0)
-- Dependencies: 189
-- Name: COLUMN transacao.numero_conta; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.transacao.numero_conta IS 'conta ao qual pertence o registro de histórico';


--
-- TOC entry 188 (class 1259 OID 16516)
-- Name: transacao_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.transacao_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.transacao_id_seq OWNER TO postgres;

--
-- TOC entry 2222 (class 0 OID 0)
-- Dependencies: 188
-- Name: transacao_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.transacao_id_seq OWNED BY public.transacao.id;


--
-- TOC entry 2050 (class 2604 OID 16493)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.agencia ALTER COLUMN id SET DEFAULT nextval('public.agencia_id_seq'::regclass);


--
-- TOC entry 2051 (class 2604 OID 16494)
-- Name: numero_agencia; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.agencia ALTER COLUMN numero_agencia SET DEFAULT nextval('public.agencia_numero_agencia_seq'::regclass);


--
-- TOC entry 2049 (class 2604 OID 16483)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente ALTER COLUMN id SET DEFAULT nextval('public.cliente_id_seq'::regclass);


--
-- TOC entry 2053 (class 2604 OID 16503)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.conta ALTER COLUMN id SET DEFAULT nextval('public.conta_id_seq'::regclass);


--
-- TOC entry 2055 (class 2604 OID 16534)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.historico_taxa_sobre_transacao ALTER COLUMN id SET DEFAULT nextval('public.historico_taxa_sobre_transacao_id_seq'::regclass);


--
-- TOC entry 2054 (class 2604 OID 16521)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transacao ALTER COLUMN id SET DEFAULT nextval('public.transacao_id_seq'::regclass);


--
-- TOC entry 2191 (class 0 OID 16490)
-- Dependencies: 185
-- Data for Name: agencia; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.agencia (id, numero_agencia, cofre_agencia) FROM stdin;
1	1	10.9000000000000004
2	2	123
3	3	30
4	4	12.3450000000000006
5	5	20000
\.


--
-- TOC entry 2223 (class 0 OID 0)
-- Dependencies: 183
-- Name: agencia_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.agencia_id_seq', 5, true);


--
-- TOC entry 2224 (class 0 OID 0)
-- Dependencies: 184
-- Name: agencia_numero_agencia_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.agencia_numero_agencia_seq', 5, true);


--
-- TOC entry 2188 (class 0 OID 16480)
-- Dependencies: 182
-- Data for Name: cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cliente (id, nome, cpf, senha) FROM stdin;
5	Vinicius                                                                                            	12345               	qwert                                             
6	ArthuTeste                                                                                          	1234                	qwe                                               
8	Demetrio                                                                                            	02899926217         	1q2w3e                                            
\.


--
-- TOC entry 2225 (class 0 OID 0)
-- Dependencies: 181
-- Name: cliente_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cliente_id_seq', 8, true);


--
-- TOC entry 2193 (class 0 OID 16500)
-- Dependencies: 187
-- Data for Name: conta; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.conta (id, tipo, numero_conta, saldo, limite_credito, id_cliente, id_agencia) FROM stdin;
7	1	7165	18.1000000000000014	0	6	5
5	1	123	23.3000000000000007	0	6	5
6	2	6265	90	123	6	5
\.


--
-- TOC entry 2226 (class 0 OID 0)
-- Dependencies: 186
-- Name: conta_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.conta_id_seq', 12, true);


--
-- TOC entry 2197 (class 0 OID 16531)
-- Dependencies: 191
-- Data for Name: historico_taxa_sobre_transacao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.historico_taxa_sobre_transacao (id, valor_taxa, id_historico_transacao, id_agencia_creditada) FROM stdin;
\.


--
-- TOC entry 2227 (class 0 OID 0)
-- Dependencies: 190
-- Name: historico_taxa_sobre_transacao_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.historico_taxa_sobre_transacao_id_seq', 1, false);


--
-- TOC entry 2195 (class 0 OID 16518)
-- Dependencies: 189
-- Data for Name: transacao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.transacao (id, tipo, valor, numero_conta) FROM stdin;
2	2	4	7165
3	2	10	123
4	2	3.29999999999999982	123
\.


--
-- TOC entry 2228 (class 0 OID 0)
-- Dependencies: 188
-- Name: transacao_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.transacao_id_seq', 4, true);


--
-- TOC entry 2059 (class 2606 OID 16496)
-- Name: agencia_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.agencia
    ADD CONSTRAINT agencia_pkey PRIMARY KEY (id);


--
-- TOC entry 2061 (class 2606 OID 16505)
-- Name: chave primaria unica; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.conta
    ADD CONSTRAINT "chave primaria unica" PRIMARY KEY (id);


--
-- TOC entry 2057 (class 2606 OID 16485)
-- Name: chavei primaria; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT "chavei primaria" PRIMARY KEY (id);


--
-- TOC entry 2063 (class 2606 OID 16671)
-- Name: conta unica; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.conta
    ADD CONSTRAINT "conta unica" UNIQUE (numero_conta);


--
-- TOC entry 2067 (class 2606 OID 16536)
-- Name: historico_taxa_sobre_transacao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.historico_taxa_sobre_transacao
    ADD CONSTRAINT historico_taxa_sobre_transacao_pkey PRIMARY KEY (id);


--
-- TOC entry 2065 (class 2606 OID 16523)
-- Name: id da transacao; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transacao
    ADD CONSTRAINT "id da transacao" PRIMARY KEY (id);


--
-- TOC entry 2068 (class 2606 OID 16506)
-- Name: Dono da conta; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.conta
    ADD CONSTRAINT "Dono da conta" FOREIGN KEY (id_cliente) REFERENCES public.cliente(id);


--
-- TOC entry 2229 (class 0 OID 0)
-- Dependencies: 2068
-- Name: CONSTRAINT "Dono da conta" ON conta; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON CONSTRAINT "Dono da conta" ON public.conta IS 'cliente dono da conta';


--
-- TOC entry 2072 (class 2606 OID 16542)
-- Name: agencia creditada; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.historico_taxa_sobre_transacao
    ADD CONSTRAINT "agencia creditada" FOREIGN KEY (id_agencia_creditada) REFERENCES public.agencia(id);


--
-- TOC entry 2230 (class 0 OID 0)
-- Dependencies: 2072
-- Name: CONSTRAINT "agencia creditada" ON historico_taxa_sobre_transacao; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON CONSTRAINT "agencia creditada" ON public.historico_taxa_sobre_transacao IS 'agencia que recebeu em seu cofre a taxa cobrada sobre a transação ';


--
-- TOC entry 2069 (class 2606 OID 16511)
-- Name: agencia da conta; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.conta
    ADD CONSTRAINT "agencia da conta" FOREIGN KEY (id_agencia) REFERENCES public.agencia(id);


--
-- TOC entry 2070 (class 2606 OID 16672)
-- Name: conta envolvida na transação; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transacao
    ADD CONSTRAINT "conta envolvida na transação" FOREIGN KEY (numero_conta) REFERENCES public.conta(numero_conta);


--
-- TOC entry 2071 (class 2606 OID 16537)
-- Name: transação vinculada a taxa; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.historico_taxa_sobre_transacao
    ADD CONSTRAINT "transação vinculada a taxa" FOREIGN KEY (id_historico_transacao) REFERENCES public.transacao(id);


--
-- TOC entry 2205 (class 0 OID 0)
-- Dependencies: 6
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2018-11-26 22:53:26 -03

--
-- PostgreSQL database dump complete
--

