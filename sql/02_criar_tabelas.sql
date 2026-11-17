-- No pgAdmin, execute no Query Tool do banco gestao_pagamento_dia.
BEGIN;

DO $$
BEGIN
    IF current_database() <> 'gestao_pagamento_dia' THEN
        RAISE EXCEPTION 'Execute este script no banco gestao_pagamento_dia.';
    END IF;
END;
$$;

CREATE TABLE IF NOT EXISTS public.pagamentos (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    data DATE NOT NULL,
    valor NUMERIC NOT NULL,
    CONSTRAINT pagamentos_valor_positivo CHECK (valor > 0 AND valor < 'Infinity'::numeric)
);

CREATE TABLE IF NOT EXISTS public.registros_trabalho (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    data DATE NOT NULL,
    valor NUMERIC NOT NULL,
    descricao TEXT,
    CONSTRAINT registros_trabalho_valor_positivo CHECK (valor > 0 AND valor < 'Infinity'::numeric)
);

COMMIT;

-- Confira a estrutura efetiva, inclusive se as tabelas já existiam.
-- Para ver as duas grades no pgAdmin, execute cada SELECT separadamente.
SELECT table_name, column_name, data_type, is_nullable, is_identity
FROM information_schema.columns
WHERE table_schema = 'public'
    AND table_name IN ('pagamentos', 'registros_trabalho')
ORDER BY table_name, ordinal_position;

SELECT conrelid::regclass AS tabela, conname AS restricao,
    pg_get_constraintdef(oid) AS definicao
FROM pg_constraint
WHERE conrelid IN ('public.pagamentos'::regclass, 'public.registros_trabalho'::regclass)
ORDER BY tabela, restricao;
