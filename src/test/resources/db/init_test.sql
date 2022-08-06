CREATE TABLE "file"(
    "id" BIGINT NOT NULL,
    "file_name" VARCHAR(255) NOT NULL,
    "file_size" BIGINT NOT NULL,
    "mime_type" VARCHAR(255) NOT NULL,
    "data_id" BIGINT NOT NULL
);

ALTER TABLE
    "file" ADD PRIMARY KEY("id");

ALTER TABLE
    "file" ALTER COLUMN "id" ADD GENERATED ALWAYS AS IDENTITY(START WITH 1 INCREMENT 1);

ALTER TABLE
    "file" ADD CONSTRAINT "file_data_id_unique" UNIQUE("data_id");

ALTER TABLE
    "file" ADD CONSTRAINT "empty_file_name_chk" CHECK(LENGTH("file_name") > 0);

ALTER TABLE
    "file" ADD CONSTRAINT "empty_mime_type_chk" CHECK(LENGTH("mime_type") > 0);

ALTER TABLE
    "file" ADD CONSTRAINT "negative_file_size_chk" CHECK("file_size" > 0);

CREATE TABLE "file_data"(
    "id" BIGINT NOT NULL,
    "file_data" oid NOT NULL
);

ALTER TABLE
    "file_data" ADD PRIMARY KEY("id");

ALTER TABLE
    "file_data" ALTER COLUMN "id" ADD GENERATED ALWAYS AS IDENTITY(START WITH 1 INCREMENT 1);

ALTER TABLE
    "file" ADD CONSTRAINT "file_file_data" FOREIGN KEY("data_id") REFERENCES "file_data"("id");


-- Testcontainers cant handle the postgreSQL dollar quoted syntax so this solution is temporal
-- Exeption: org.testcontainers.ext.ScriptUtils$UncategorizedScriptException: Failed to execute database script...

-- More information: https://github.com/testcontainers/testcontainers-java/issues/4441

-- TODO: replace testcontainers settings in application.yaml by a programatic setup
-- Try the docker compose integration: https://www.testcontainers.org/modules/docker_compose/
-- Or normal containers with volumes mapping: https://www.testcontainers.org/features/files/
CREATE OR REPLACE FUNCTION prevent_data_id_update_fn() RETURNS TRIGGER AS
'
BEGIN
   RAISE EXCEPTION ''Column "data_id" is not editable'';
END;
' LANGUAGE PLPGSQL COST 100;

CREATE TRIGGER prevent_update_trg
BEFORE
UPDATE OF "data_id" ON "file"
FOR EACH ROW EXECUTE PROCEDURE prevent_data_id_update_fn();


INSERT INTO file_data (file_data) VALUES (lo_import('/etc/hostname'));

INSERT INTO file (file_name, file_size, mime_type, data_id) VALUES ('hostname', 8, 'text/plain', 1);
