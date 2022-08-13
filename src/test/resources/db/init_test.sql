CREATE TABLE file(
    id BIGINT NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_size BIGINT NOT NULL,
    mime_type VARCHAR(255) NOT NULL,
    data_id BIGINT NOT NULL
);

ALTER TABLE
    file ADD PRIMARY KEY(id);

ALTER TABLE
    file ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY(START WITH 1 INCREMENT 1);

ALTER TABLE
    file ADD CONSTRAINT file_data_id_unique UNIQUE(data_id);

ALTER TABLE
    file ADD CONSTRAINT empty_file_name_chk CHECK(LENGTH(file_name) > 0);

ALTER TABLE
    file ADD CONSTRAINT empty_mime_type_chk CHECK(LENGTH(mime_type) > 0);

ALTER TABLE
    file ADD CONSTRAINT negative_file_size_chk CHECK(file_size > 0);

CREATE TABLE file_data(
    id BIGINT NOT NULL,
    file_data bytea NOT NULL
);

ALTER TABLE
    file_data ADD PRIMARY KEY(id);

ALTER TABLE
    file_data ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY(START WITH 1 INCREMENT 1);

ALTER TABLE
    file ADD CONSTRAINT file_file_data FOREIGN KEY(data_id) REFERENCES file_data(id) ON DELETE CASCADE;


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

CREATE TRIGGER prevent_data_id_update_trg
BEFORE
UPDATE OF data_id ON file
FOR EACH ROW EXECUTE PROCEDURE prevent_data_id_update_fn();


INSERT INTO file_data (file_data) VALUES (pg_read_file('/etc/hostname')::bytea);
INSERT INTO file_data (file_data) VALUES (pg_read_file('/etc/hostname')::bytea);
INSERT INTO file_data (file_data) VALUES (pg_read_file('/etc/hostname')::bytea);
INSERT INTO file_data (file_data) VALUES (pg_read_file('/etc/hostname')::bytea);
INSERT INTO file_data (file_data) VALUES (pg_read_file('/etc/hostname')::bytea);
INSERT INTO file_data (file_data) VALUES (pg_read_file('/etc/hostname')::bytea);
INSERT INTO file_data (file_data) VALUES (pg_read_file('/etc/hostname')::bytea);
INSERT INTO file_data (file_data) VALUES (pg_read_file('/etc/hostname')::bytea);
INSERT INTO file_data (file_data) VALUES (pg_read_file('/etc/hostname')::bytea);
INSERT INTO file_data (file_data) VALUES (pg_read_file('/etc/hostname')::bytea);
INSERT INTO file_data (file_data) VALUES (pg_read_file('/etc/hostname')::bytea);
INSERT INTO file_data (file_data) VALUES (pg_read_file('/etc/hostname')::bytea);
INSERT INTO file_data (file_data) VALUES (pg_read_file('/etc/hostname')::bytea);
INSERT INTO file_data (file_data) VALUES (pg_read_file('/etc/hostname')::bytea);
INSERT INTO file_data (file_data) VALUES (pg_read_file('/etc/hostname')::bytea);
INSERT INTO file_data (file_data) VALUES (pg_read_file('/etc/hostname')::bytea);
INSERT INTO file_data (file_data) VALUES (pg_read_file('/etc/hostname')::bytea);
INSERT INTO file_data (file_data) VALUES (pg_read_file('/etc/hostname')::bytea);
INSERT INTO file_data (file_data) VALUES (pg_read_file('/etc/hostname')::bytea);
INSERT INTO file_data (file_data) VALUES (pg_read_file('/etc/hostname')::bytea);
INSERT INTO file_data (file_data) VALUES (pg_read_file('/etc/hostname')::bytea);
INSERT INTO file_data (file_data) VALUES (pg_read_file('/etc/hostname')::bytea);
INSERT INTO file_data (file_data) VALUES (pg_read_file('/etc/hostname')::bytea);
INSERT INTO file_data (file_data) VALUES (pg_read_file('/etc/hostname')::bytea);
INSERT INTO file_data (file_data) VALUES (pg_read_file('/etc/hostname')::bytea);

INSERT INTO file (file_name, file_size, mime_type, data_id) VALUES ('TristiqueFusceCongue.xls', 53, 'application/x-excel', 1);
INSERT INTO file (file_name, file_size, mime_type, data_id) VALUES ('LectusVestibulumQuam.tiff', 159, 'image/tiff', 2);
INSERT INTO file (file_name, file_size, mime_type, data_id) VALUES ('PortaVolutpatErat.mpeg', 615, 'video/mpeg', 3);
INSERT INTO file (file_name, file_size, mime_type, data_id) VALUES ('ConsequatLectusIn.ppt', 470, 'application/mspowerpoint', 4);
INSERT INTO file (file_name, file_size, mime_type, data_id) VALUES ('NonLectus.pdf', 177, 'application/pdf', 5);
INSERT INTO file (file_name, file_size, mime_type, data_id) VALUES ('PenatibusEt.ppt', 124, 'application/x-mspowerpoint', 6);
INSERT INTO file (file_name, file_size, mime_type, data_id) VALUES ('Quam.txt', 409, 'text/plain', 7);
INSERT INTO file (file_name, file_size, mime_type, data_id) VALUES ('AdipiscingElit.tiff', 327, 'image/tiff', 8);
INSERT INTO file (file_name, file_size, mime_type, data_id) VALUES ('Neque.xls', 916, 'application/x-msexcel', 9);
INSERT INTO file (file_name, file_size, mime_type, data_id) VALUES ('Vestibulum.tiff', 908, 'image/x-tiff', 10);
INSERT INTO file (file_name, file_size, mime_type, data_id) VALUES ('Mauris.ppt', 411, 'application/x-mspowerpoint', 11);
INSERT INTO file (file_name, file_size, mime_type, data_id) VALUES ('CongueDiam.png', 242, 'image/png', 12);
INSERT INTO file (file_name, file_size, mime_type, data_id) VALUES ('FusceConsequat.avi', 925, 'video/msvideo', 13);
INSERT INTO file (file_name, file_size, mime_type, data_id) VALUES ('QuisqueErat.jpeg', 403, 'image/jpeg', 14);
INSERT INTO file (file_name, file_size, mime_type, data_id) VALUES ('EstPhasellus.mp3', 766, 'video/x-mpeg', 15);
INSERT INTO file (file_name, file_size, mime_type, data_id) VALUES ('NuncCommodoPlacerat.xls', 570, 'application/excel', 16);
INSERT INTO file (file_name, file_size, mime_type, data_id) VALUES ('Id.mp3', 162, 'video/mpeg', 17);
INSERT INTO file (file_name, file_size, mime_type, data_id) VALUES ('SodalesSed.mp3', 757, 'audio/mpeg3', 18);
INSERT INTO file (file_name, file_size, mime_type, data_id) VALUES ('RutrumNullaNunc.xls', 166, 'application/excel', 19);
INSERT INTO file (file_name, file_size, mime_type, data_id) VALUES ('AliquetPulvinarSed.ppt', 637, 'application/mspowerpoint', 20);
INSERT INTO file (file_name, file_size, mime_type, data_id) VALUES ('IpsumPrimisIn.doc', 488, 'application/msword', 21);
INSERT INTO file (file_name, file_size, mime_type, data_id) VALUES ('Quam.mp3', 620, 'video/x-mpeg', 22);
INSERT INTO file (file_name, file_size, mime_type, data_id) VALUES ('UtNulla.tiff', 863, 'image/tiff', 23);
INSERT INTO file (file_name, file_size, mime_type, data_id) VALUES ('Ut.mov', 241, 'video/quicktime', 24);
INSERT INTO file (file_name, file_size, mime_type, data_id) VALUES ('NisiVolutpat.doc', 189, 'application/msword', 25);
