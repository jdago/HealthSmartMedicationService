DROP TABLE IF EXISTS Medication;
CREATE TABLE Medication
(
    patientId          VARCHAR(16) PRIMARY KEY NOT NULL,
    version            integer     NOT NULL,
    medicationName     VARCHAR(64) NOT NULL,
    medicationNDC      VARCHAR(64) UNIQUE NOT NULL,
    expirationDate     DATE        NOT NULL,
    dosageInMilligrams INT(10) NOT NULL,
    availableRefills   INT(1) NOT NULL,
    controlledStatus   VARCHAR(16) NOT NULL,
    medicationClass    VARCHAR(32) NOT NULL,
    status             VARCHAR(16) NOT NULL
);