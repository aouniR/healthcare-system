CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE IF NOT EXISTS "users" (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ROLE NOT NULL,
    creation_timestamp TIMESTAMP DEFAULT now(),
    update_timestamp TIMESTAMP DEFAULT now()
);
