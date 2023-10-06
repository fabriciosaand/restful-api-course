CREATE TABLE IF NOT EXISTS public.books (
  id SERIAL,
  author character varying(255) NOT NULL,
  launch_date timestamp NOT NULL,
  price decimal(65,2) NOT NULL,
  title character varying(255) NOT NULL,
  CONSTRAINT book_pkey PRIMARY KEY (id)
) 
