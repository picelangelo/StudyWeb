INSERT INTO `studywebdb`.`user`
(
`Version`,
`Email`,
`Password`,
`Vorname`,
`Nachname`)
VALUES
(0, "admin@test.com", "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8", "root", "admininstrator" );


INSERT INTO `studywebdb`.`fragebogen`
(`Version`,
`User_Id`)
VALUES
(0, 1);

INSERT INTO `studywebdb`.`frage`
(`Version`,
`Fragebogen_Id`,
`Frage`,
`IsMultipleChoice`)
VALUES
(0, 1, 'TESTFRAGE', false);

INSERT INTO `studywebdb`.`berechtigt`
(`Version`,
`User_Id`,
`Fragebogen_Id`,
`Darf_bearbeiten`)
VALUES
(0, 1, 1, true);

INSERT INTO `studywebdb`.`antwort`
(`Version`,
`Frage_Id`,
`Antwort`,
`IsCorrect`)
VALUES
(0, 1, 'TESTANTWORT', true);

INSERT INTO `studywebdb`.`beantwortet`
(`Version`,
`Frage_Id`,
`User_Id`,
`Anzahl_richtig`,
`Anzahl_falsch`)
VALUES
(0, 1, 1, 1, 1);

