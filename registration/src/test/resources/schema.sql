CREATE TABLE users
(
  id integer NOT NULL,
  email character varying(255),
  enabled boolean NOT NULL,
  password character varying(255) NOT NULL,
  site character varying(255),
  type character varying(255),
  username character varying(255),
  CONSTRAINT users_pkey PRIMARY KEY (id),
  CONSTRAINT uk_r43af9ap4edm43mmtq01oddj6 UNIQUE (username)
);

CREATE TABLE authorities
(
  id integer NOT NULL,
  authority integer,
  username character varying(255),
  CONSTRAINT authorities_pkey PRIMARY KEY (id),
  CONSTRAINT fk_baahryprcge2u172egph1qwur FOREIGN KEY (username)
      REFERENCES users (username) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);