CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE IF NOT EXISTS "metamodels" (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),           
    description TEXT NOT NULL,                             
    creator_id UUID NOT NULL,                                
    creation_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  
    update_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  
);

CREATE TABLE IF NOT EXISTS "metamodel_composants" (
    metamodel_id UUID REFERENCES metamodels(id) ON DELETE CASCADE,  
    composant_dossier_id UUID NOT NULL                             
);
