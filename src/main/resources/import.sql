INSERT INTO VetClinic.veterinarians (degree, email, name, password, active_account) VALUES ('lek. wet', 'jan.kowalski@onet.pl', 'Jan Kowalski', 'haslo', 1), ('dr n. wet.', 'adamkowal@wp.pl', 'Adam Kowal', '123', 1);
INSERT INTO VetClinic.pet_owners(city, discount, email, extra_info, last_visit, name, nip, phone_number, post_code, street, visit_count) VALUES ('Lublin', 0, 'oskarzalas@gmail.com', 'Klient awanturujący sie', null, 'Zalas Oskar', null, '607209454', '20-043', 'Spadochroniarzy 11a', 0);
INSERT INTO VetClinic.animals(animal_kind, breed, chip_number, colour, date_of_birth, distinctive_marks, gender, last_visit, name, species, visit_count, weight, pet_owner_id) VALUES ('dog', 'shih-tzu', null, null, null, null, 'bitch', null, 'Peppa', 'dog', 0, 5, 1);
INSERT INTO VetClinic.medical_records(anamnesis, date_time_of_visit, diagnosis, prescription, used_medication, vet_examination, animal_id, pet_owner_id, vet_id) VALUES ('wywiad', NOW(), 'diagnoza', 'nic nie zalecono', 'simparica', 'W omacywaniu nic nie stwierdzono', '1', '1', 2), ('wywiad 2 ', NOW(), 'diagnoza', 'nic nie zalecono', 'simparica', 'W omacywaniu nic nie stwierdzono', 1, 1, 2);