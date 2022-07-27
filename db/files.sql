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
    "file" ADD CONSTRAINT "file_data_id_unique" UNIQUE("data_id");

COMMENT
ON COLUMN
    "file"."file_name" IS 'Only the name of the file, avoids absolute paths sent by clients.';

COMMENT
ON COLUMN
    "file"."file_size" IS 'File size in bytes.';

CREATE TABLE "file_data"(
    "id" BIGINT NOT NULL,
    "file_data" bytea NOT NULL
);

ALTER TABLE
    "file_data" ADD PRIMARY KEY("id");

ALTER TABLE
    "file" ADD CONSTRAINT "file_file_data" FOREIGN KEY("data_id") REFERENCES "file_data"("id");
