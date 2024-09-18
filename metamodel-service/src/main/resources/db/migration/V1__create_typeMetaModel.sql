DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'typeMetaModel') THEN
        CREATE TYPE typeMetaModel AS ENUM ('ACTE_MEDICAL', 'DOSSIER_MEDICAL', 'SUIVI');
    END IF;
END $$;
