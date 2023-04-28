INSERT INTO VetClinic.veterinarians (degree, email, name, password, active_account) VALUES ('lek. wet', 'admin@vetclinic.pl', 'Jan Kowalski', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW', 1), ('dr n. wet.', 'user@vetclinic.pl', 'Adam Kowal', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW', 1);
INSERT INTO VetClinic.pet_owners(city, discount, email, extra_info, last_visit, name, nip, phone_number, post_code, street, visit_count) VALUES ('Warszawa', 0, 'mail@mail.com', null, null, 'Kowal Jan', null, '544666999', '00-520', 'Łódzka 105', 0);
INSERT INTO VetClinic.pet_owners(city, discount, email, extra_info, last_visit, name, nip, phone_number, post_code, street, visit_count) VALUES ('Lublin', 0, 'oskarzalas@gmail.com', 'Klient awanturujący sie', null, 'Zalas Oskar', null, '607209454', '20-043', 'Spadochroniarzy 11a', 0);
INSERT INTO VetClinic.pet_owners(city, discount, email, extra_info, last_visit, name, nip, phone_number, post_code, street, visit_count) VALUES ('Kraków', 0, 'sandra@example.com', null, null, 'Kowalczyk Sandra', null, '600800900', '31-223', 'Miodowa 12', 0);
INSERT INTO VetClinic.pet_owners(city, discount, email, extra_info, last_visit, name, nip, phone_number, post_code, street, visit_count) VALUES ('Gdańsk', 0, 'adamnowak@gmail.com', null, null, 'Nowak Adam', null, '605700800', '80-002', 'Mariacka 17', 0);
INSERT INTO VetClinic.pet_owners(city, discount, email, extra_info, last_visit, name, nip, phone_number, post_code, street, visit_count) VALUES ('Poznań', 0, 'kasia.piotrowska@yahoo.com', null, null, 'Piotrowska Katarzyna', null, '604100200', '61-123', 'Święty Marcin 17', 0);
INSERT INTO VetClinic.pet_owners(city, discount, email, extra_info, last_visit, name, nip, phone_number, post_code, street, visit_count) VALUES ('Katowice', 0, 'piotr.stolarz@wp.pl', null, null, 'Stolarz Piotr', null, '600400300', '40-001', 'Mikołowska 23', 0);
INSERT INTO VetClinic.pet_owners(city, discount, email, extra_info, last_visit, name, nip, phone_number, post_code, street, visit_count) VALUES ('Łódź', 0, 'agnieszka.nowak@onet.pl', null, null, 'Nowak Agnieszka', null, '501800700', '91-123', 'Aleja Politechniki 12', 0);
INSERT INTO VetClinic.pet_owners(city, discount, email, extra_info, last_visit, name, nip, phone_number, post_code, street, visit_count) VALUES ('Wrocław', 0, 'mateusz.wolski@interia.pl', null, null, 'Wolski Mateusz', null, '600300200', '50-001', 'Kościuszki 8', 0);
INSERT INTO VetClinic.pet_owners(city, discount, email, extra_info, last_visit, name, nip, phone_number, post_code, street, visit_count) VALUES ('Kraków', 5, 'anna.nowak@o2.pl', 'Has allergy to dust', null, 'Nowak Anna', null, '601400500', '30-101', 'Mikołajska 14', 3);
INSERT INTO VetClinic.pet_owners(city, discount, email, extra_info, last_visit, name, nip, phone_number, post_code, street, visit_count) VALUES ('Warszawa', 10, 'pawel.kowalski@gmail.com', 'Requires special diet', null, 'Kowalski Paweł', null, '605500600', '00-001', 'Nowy Świat 1', 5);
INSERT INTO VetClinic.pet_owners(city, discount, email, extra_info, last_visit, name, nip, phone_number, post_code, street, visit_count) VALUES ('Poznań', 0, 'agnieszka.adamska@wp.pl', 'Has diabetes', null, 'Adamska Agnieszka', null, '607700800', '61-001', 'Długa 1', 2);
INSERT INTO VetClinic.animals(animal_kind, breed, chip_number, colour, date_of_birth, distinctive_marks, gender, last_visit, name, species, visit_count, weight, pet_owner_id) VALUES ('dog', 'shih-tzu', null, null, null, null, 'bitch', null, 'Peppa', 'dog', 0, 5, 1), ('cat', 'bengal', null, null, null, null, 'dam', null, 'Melania', 'cat', 0, 4.2, 2);
INSERT INTO VetClinic.medical_records(anamnesis, date_time_of_visit, diagnosis, prescription, used_medication, vet_examination, animal_id, pet_owner_id, vet_id) VALUES ('wywiad', NOW(), 'diagnoza', 'nic nie zalecono', 'simparica', 'W omacywaniu nic nie stwierdzono', '1', '1', 2), ('wywiad 2 ', NOW(), 'diagnoza', 'nic nie zalecono', 'simparica', 'W omacywaniu nic nie stwierdzono', 1, 1, 2), ('wywiad 3 ', NOW(), 'diagnoza3', 'nic nie zalecono3', 'kocimiętka', 'Osłuchowo git', 2, 2, 1);
INSERT INTO VetClinic.roles(name) VALUES ('ROLE_USER'), ('ROLE_ADMIN');
INSERT INTO VetClinic.veterinarians_roles(vet_id, role_id) VALUES (1,2), (2,1);
INSERT INTO VetClinic.schedules(closest_available_date_time, date, visit_time, work_end_time, work_start_time, vet_id) VALUES ('2023-04-19 20:00:33', '2023-04-22', 30, '20:00:01', '8:00:00', 1);
INSERT INTO VetClinic.schedules(closest_available_date_time, date, visit_time, work_end_time, work_start_time, vet_id) VALUES ('2023-04-19 20:00:33', '2023-04-20', 30, '20:00:01', '8:00:00', 1);
INSERT INTO VetClinic.schedules(closest_available_date_time, date, visit_time, work_end_time, work_start_time, vet_id) VALUES ('2023-04-19 20:00:33', '2023-04-21', 30, '20:00:01', '8:00:00', 1);
INSERT INTO VetClinic.schedules(closest_available_date_time, date, visit_time, work_end_time, work_start_time, vet_id) VALUES ('2023-04-19 20:00:33', '2023-04-23', 30, '20:00:01', '8:00:00', 1);
INSERT INTO VetClinic.schedules(closest_available_date_time, date, visit_time, work_end_time, work_start_time, vet_id) VALUES ('2023-04-19 20:00:33', '2023-04-19', 30, '20:00:01', '8:00:00', 1);
INSERT INTO VetClinic.schedules(closest_available_date_time, date, visit_time, work_end_time, work_start_time, vet_id) VALUES ('2023-04-19 20:00:33', '2023-04-18', 30, '20:00:01', '8:00:00', 1);
INSERT INTO VetClinic.schedules(closest_available_date_time, date, visit_time, work_end_time, work_start_time, vet_id) VALUES ('2023-04-19 20:00:33', '2023-04-17', 30, '20:00:01', '8:00:00', 1);
INSERT INTO VetClinic.schedules(closest_available_date_time, date, visit_time, work_end_time, work_start_time, vet_id) VALUES (null, '2023-04-24', 30, '16:00:01', '10:00:00', 1);
INSERT INTO VetClinic.schedules(closest_available_date_time, date, visit_time, work_end_time, work_start_time, vet_id) VALUES (null, '2023-04-25', 30, '20:00:01', '15:00:00', 1);
INSERT INTO VetClinic.schedules(closest_available_date_time, date, visit_time, work_end_time, work_start_time, vet_id) VALUES (null, '2023-04-26', 30, '20:00:01', '8:00:00', 1);
INSERT INTO VetClinic.schedules(closest_available_date_time, date, visit_time, work_end_time, work_start_time, vet_id) VALUES (null, '2023-04-27', 30, '20:00:01', '8:00:00', 1);
INSERT INTO VetClinic.schedules(closest_available_date_time, date, visit_time, work_end_time, work_start_time, vet_id) VALUES (null, '2023-04-28', 30, '20:00:01', '8:00:00', 1);
INSERT INTO VetClinic.schedules(closest_available_date_time, date, visit_time, work_end_time, work_start_time, vet_id) VALUES (null, '2023-04-29', 30, '20:00:01', '8:00:00', 1);
INSERT INTO VetClinic.schedules(closest_available_date_time, date, visit_time, work_end_time, work_start_time, vet_id) VALUES (null, '2023-04-30', 30, '20:00:01', '8:00:00', 1);
INSERT INTO VetClinic.appointments(animal_name, email, end_date_time, first_name, last_name, message, phone_number, start_date_time, visit_type, vet_id, schedule_id, is_active) VALUES ('Melania', 'email@mail.com', '2023-04-28 16:30:00', 'Jan', 'Kowalski', 'Coś się dzieje', '666999000', '2023-04-28 15:30:00', 'checkup', 1, 12, 0);
INSERT INTO VetClinic.appointments(animal_name, email, end_date_time, first_name, last_name, message, phone_number, start_date_time, visit_type, vet_id, schedule_id, is_active) VALUES ('Sara', 'jan.kowal@onet.pl', '2023-04-28 18:00:00', 'Jan', 'Kowal', 'Coś tam się dzieje', '666000999', '2023-04-28 17:30:00', 'kontrola', 1, 12, 1);