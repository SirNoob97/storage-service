-- File Table --

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

COMMENT
ON COLUMN
    "file"."file_name" IS 'Only the name of the file, avoids absolute paths sent by clients.';

COMMENT
ON COLUMN
    "file"."file_size" IS 'File size in bytes.';

-- File Constraints --

ALTER TABLE
    file ADD CONSTRAINT empty_file_name_chk CHECK(LENGTH(file_name) > 0);

ALTER TABLE
    file ADD CONSTRAINT empty_mime_type_chk CHECK(LENGTH(mime_type) > 0);

ALTER TABLE
    file ADD CONSTRAINT negative_file_size_chk CHECK(file_size > 0);

-- File_Data Table --

CREATE TABLE file_data(
    id BIGINT NOT NULL,
    file_data oid NOT NULL
);

ALTER TABLE
    file_data ADD PRIMARY KEY(id);

ALTER TABLE
    file_data ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY(START WITH 1 INCREMENT 1);

-- Foreign keys --

ALTER TABLE
    file ADD CONSTRAINT file_file_data FOREIGN KEY(data_id) REFERENCES file_data(id) ON DELETE CASCADE;

-- Procedures --

CREATE OR REPLACE FUNCTION prevent_data_id_update_fn() RETURNS TRIGGER AS
$$
BEGIN
   RAISE EXCEPTION 'Column "data_id" is not editable';
END;
$$ LANGUAGE PLPGSQL COST 100;

-- Triggers --

CREATE TRIGGER prevent_data_id_update_trg
BEFORE
UPDATE OF data_id ON file
FOR EACH ROW EXECUTE PROCEDURE prevent_data_id_update_fn();
