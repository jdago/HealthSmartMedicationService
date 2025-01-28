DROP TABLE IF EXISTS medication;
CREATE TABLE medication
(
    patientId          VARCHAR(16) PRIMARY KEY NOT NULL,
    version            integer     NOT NULL,
    medicationName     VARCHAR(64) NOT NULL,
    medicationNDC      VARCHAR(64) UNIQUE NOT NULL,
    expirationDate     DATE        NOT NULL,
    dosageInMilligrams INTEGER NOT NULL,
    availableRefills   INTEGER NOT NULL,
    controlledStatus   VARCHAR(16) NOT NULL,
    medicationClass    VARCHAR(32) NOT NULL,
    status             VARCHAR(16) NOT NULL
);