
CREATE TABLE public.events
(
    id bigint NOT NULL,
    status text NOT NULL,
    init_phone bigint NOT NULL,
    receiving_phone bigint NOT NULL,
    date_created timestamp not null
);

CREATE SEQUENCE public.events_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.events_seq OWNED BY public.events.id;

ALTER TABLE ONLY public.events ALTER COLUMN id SET DEFAULT nextval('public.events_seq'::regclass);

ALTER TABLE ONLY public.events
    ADD CONSTRAINT event_pkey PRIMARY KEY (id);


CREATE TABLE public.subscribers
(
    id bigint NOT NULL,
    name text not null
);


CREATE SEQUENCE public.subscribers_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE ONLY public.subscribers
    ADD CONSTRAINT subscribers_pkey PRIMARY KEY (id);


INSERT INTO subscribers(id, name) VALUES (539390, 'Garry');
INSERT INTO subscribers(id, name) VALUES (123456, 'Danny');
INSERT INTO subscribers(id, name) VALUES (234567, 'Miguel');
INSERT INTO subscribers(id, name) VALUES (345678, 'Jackson');