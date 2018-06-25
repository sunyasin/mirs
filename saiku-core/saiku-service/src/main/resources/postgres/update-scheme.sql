
-- виды начислений
-- insert into spr_accrual(id, name) VALUES (1, 'Повременная оплата труда и надбавки
-- insert into spr_accrual(id, name) VALUES (1, 'Сдельная оплата труда
-- insert into spr_accrual(id, name) VALUES (1, 'Оплата труда в натуральной форме
-- insert into spr_accrual(id, name) VALUES (1, 'Доход в натуральной форме
-- insert into spr_accrual(id, name) VALUES (1, 'Компенсационные выплаты
-- insert into spr_accrual(id, name) VALUES (1, 'Премия
-- insert into spr_accrual(id, name) VALUES (1, 'Квартальная премия
-- insert into spr_accrual(id, name) VALUES (1, 'Районный коэффициент
-- Северная надбавка
-- Управленческий учет
-- Денежное содержание на период отпуска
-- Денежное содержание компенсация отпуска
-- Оплата отпуска
-- Компенсация отпуска
-- Единовременная выплата к отпуску госслужащего
-- Единовременная выплата к отпуску
-- Материальная помощь к отпуску госслужащего
-- Материальная помощь к отпуску
-- Отпуск без оплаты
-- Неявка по болезни
-- Неявка по невыясненным причинам
-- Прогул
-- Простой по вине работника
-- Оплата простоя по вине работодателя
-- Оплата простоя по независящим от работодателя причинам
-- Денежное содержание на период командировки
-- Оплата командировки
-- Сохраняемое денежное содержание
-- Оплата времени сохраняемого среднего заработка
-- Доплата до среднего заработка
-- Оплата сверхурочных военнослужащим
-- Отпуск по беременности и родам военнослужащего
-- Отпуск по беременности и родам
-- Оплата больничного листа
-- Оплата больничного листа за счет работодателя
-- Оплата больничного листа, профзаболевание
-- Оплата больничного листа, несчастный случай на производстве
-- Доплата до среднего заработка за дни болезни
-- Оплата дней ухода за детьми-инвалидами (ден.содержание)
-- Оплата дней ухода за детьми-инвалидами
-- Пособие по уходу за ребенком до полутора лет военнослужащим
-- Пособие по уходу за ребенком до полутора лет
-- Пособие по уходу за ребенком до трех лет
-- Выходное пособие (месячное денежное содержание)
-- Выходное пособие
-- Доплата за совмещение
-- Болезнь без оплаты
-- Отпуск по беременности и родам без оплаты
-- Заработок на время трудоустройства
-- Материальная помощь
-- Льгота
-- Льгота (выбирается сотрудником)
-- Надбавка за вредность
-- Отпуск (за счет ФСС) на период санаторно-курортного лечения
-- Отгул
-- Оплата предыдущими документами
-- Прочие начисления и выплаты
-- Доплата до денежного содержания за дни болезни
-- Компенсация за неотработанные дни/часы при увольнении
-- Компенсация за неотработанные дни при увольнении госслужащего
-- Доначисление до управленческого учета
-- Компенсация морального вреда

-- ГБРС справочник
INSERT INTO spr_grbs (id, grbs_name) VALUES (1, 'Аппарат Правительства Пермского края');
INSERT INTO spr_grbs (id, grbs_name) VALUES (2, 'Департамент образования администрации города Перми');
INSERT INTO spr_grbs (id, grbs_name) VALUES (3, 'Министерство здравоохранения Пермского края');
INSERT INTO spr_grbs (id, grbs_name) VALUES (4, 'Министерство информационного развития и связи Пермского края');
INSERT INTO spr_grbs (id, grbs_name) VALUES (5, 'Министерство образования Пермского края');
INSERT INTO spr_grbs (id, grbs_name) VALUES (6, 'Министерство по управлению имуществом и земельным отношениям Пермского края');
INSERT INTO spr_grbs (id, grbs_name) VALUES (7, 'Министерство природных ресурсов, лесного хозяйства и экологии Пермского края');
INSERT INTO spr_grbs (id, grbs_name) VALUES (8, 'Министерство транспорта Пермского края');
INSERT INTO spr_grbs (id, grbs_name) VALUES (9, 'Министерство физической культуры, спорта и туризма Пермского края');
INSERT INTO spr_grbs (id, grbs_name) VALUES (10, 'Министерство финансов Пермского края');

INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182516, NULL, 'КГАУ "МУЗЕЙ СОВРЕМЕННОГО ИСКУССТВА"', NULL, NULL, NULL, NULL, 5902217177, 825, 'Министерство культуры Пермского края', 'КРАЕВОЕ ГОСУДАРСТВЕННОЕ АВТОНОМНОЕ УЧРЕЖДЕНИЕ "МУЗЕЙ СОВРЕМЕННОГО ИСКУССТВА"', 590601001, 1, 57000000032009040051, '2015-11-10 00:00:00.0', '', 1, 57209040, 1095902003790, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182519, NULL, 'ГБПОУ "ПТОТ"', NULL, NULL, NULL, NULL, 5904101918, 830, 'Министерство образования и науки Пермского края', 'ГОСУДАРСТВЕННОЕ БЮДЖЕТНОЕ ПРОФЕССИОНАЛЬНОЕ ОБРАЗОВАТЕЛЬНОЕ УЧРЕЖДЕНИЕ "ПЕРМСКИЙ ТЕХНИКУМ ОТРАСЛЕВЫХ ТЕХНОЛОГИЙ"', 590401001, 1, 57000000032004788021, '2015-11-13 00:00:00.0', '', 1, 57204788, 1025900893567, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182527, NULL, 'КГА ПОУ ККП', NULL, NULL, NULL, NULL, 5903003097, 830, 'Министерство образования и науки Пермского края', 'КРАЕВОЕ ГОСУДАРСТВЕННОЕ АВТОНОМНОЕ ПРОФЕССИОНАЛЬНОЕ ОБРАЗОВАТЕЛЬНОЕ УЧРЕЖДЕНИЕ "КРАЕВОЙ КОЛЛЕДЖ ПРЕДПРИНИМАТЕЛЬСТВА"', 590301001, 1, 57000000032009030071, '2015-12-01 00:00:00.0', '', 1, 57209030, 1025900767320, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182537, NULL, 'ГБУЗ ПК "ГСП № 3"', NULL, NULL, NULL, NULL, 5903003643, 820, 'Министерство здравоохранения Пермского края', 'ГОСУДАРСТВЕННОЕ БЮДЖЕТНОЕ УЧРЕЖДЕНИЕ ЗДРАВООХРАНЕНИЯ ПЕРМСКОГО КРАЯ "ГОРОДСКАЯ СТОМАТОЛОГИЧЕСКАЯ ПОЛИКЛИНИКА № 3"', 590301001, 1, 57000000032009441001, '2015-12-01 00:00:00.0', '', 1, 57209441, 1025900758212, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182530, NULL, 'ГАУЗ ПК "ГКБ № 3"', NULL, NULL, NULL, NULL, 5904345865, 820, 'Министерство здравоохранения Пермского края', 'ГОСУДАРСТВЕННОЕ АВТОНОМНОЕ УЧРЕЖДЕНИЕ ЗДРАВООХРАНЕНИЯ ПЕРМСКОГО КРАЯ "ГОРОДСКАЯ КЛИНИЧЕСКАЯ БОЛЬНИЦА № 3"', 590401001, 1, '570000000320Н1746041', '2017-01-25 00:00:00.0', '', 1, '572Н1746', 1165958123385, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182542, NULL, 'МИНТРАНС ПЕРМСКОГО КРАЯ', NULL, NULL, NULL, NULL, 5902291090, 880, 'Министерство транспорта Пермского края', 'МИНИСТЕРСТВО ТРАНСПОРТА ПЕРМСКОГО КРАЯ', 590201001, 1, 57000000012000014001, '2015-10-09 00:00:00.0', '', 1, 57200014, 1035900070678, '', 1, 2, 'Организация не является финансовым органом публично-правового образования, органом управления государственными внебюджетным фондом', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182547, NULL, 'МАУ ДО ЦДТ "ЮНОСТЬ" Г.ПЕРМИ', NULL, NULL, NULL, NULL, 5903003918, 930, 'Департамент образования администрации города Перми', 'МУНИЦИПАЛЬНОЕ АВТОНОМНОЕ УЧРЕЖДЕНИЕ ДОПОЛНИТЕЛЬНОГО ОБРАЗОВАНИЯ "ЦЕНТР ДЕТСКОГО ТВОРЧЕСТВА "ЮНОСТЬ" Г.ПЕРМИ', 590301001, 1, 57701000033108519081, '2015-10-07 00:00:00.0', '', 1, 57308519, 1025900762216, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 31, 'Бюджет городского округа', 56030360, 'Бюджет города Перми', '', 31, '', 'Городской округ', '', 57701000, '', 'город Пермь', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182535, NULL, 'ГБУ "ЦТИ ПК"', NULL, NULL, NULL, NULL, 5902044157, 812, 'Министерство по управлению имуществом и земельным отношениям Пермского края', 'ГОСУДАРСТВЕННОЕ БЮДЖЕТНОЕ УЧРЕЖДЕНИЕ ПЕРМСКОГО КРАЯ "ЦЕНТР ТЕХНИЧЕСКОЙ ИНВЕНТАРИЗАЦИИ И КАДАСТРОВОЙ ОЦЕНКИ ПЕРМСКОГО КРАЯ"', 590201001, 1, '570000000320Н3978091', '2017-07-25 00:00:00.0', '', 1, '572Н3978', 1175958027410, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182548, NULL, 'СГБУ "ПЕРМСКИЙ ЛЕСОПОЖАРНЫЙ ЦЕНТР"', NULL, NULL, NULL, NULL, 5903077035, 816, 'Министерство природных ресурсов, лесного хозяйства и экологии Пермского края', 'СПЕЦИАЛИЗИРОВАННОЕ ГОСУДАРСТВЕННОЕ БЮДЖЕТНОЕ УЧРЕЖДЕНИЕ ПЕРМСКОГО КРАЯ "ПЕРМСКИЙ ЛЕСОПОЖАРНЫЙ ЦЕНТР"', 590401001, 1, 57000000032000289041, '2015-12-07 00:00:00.0', '', 1, 57200289, 1075903000887, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182525, NULL, 'ГКУ ПК "ФХУ"', NULL, NULL, NULL, NULL, 5902995233, 820, 'Министерство здравоохранения Пермского края', 'ГОСУДАРСТВЕННОЕ КАЗЕННОЕ УЧРЕЖДЕНИЕ ПЕРМСКОГО КРАЯ "ФИНАНСОВО-ХОЗЯЙСТВЕННОЕ УПРАВЛЕНИЕ"', 590201001, 1, 57000000032009475091, '2015-11-24 00:00:00.0', '', 1, 57209475, 1145958033825, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', 57200016, 'МИНИСТЕРСТВО ЗДРАВООХРАНЕНИЯ ПЕРМСКОГО КРАЯ');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182546, NULL, 'МАДОУ "ДЕТСКИЙ САД № 409" Г. ПЕРМИ', NULL, NULL, NULL, NULL, 5908030009, 930, 'Департамент образования администрации города Перми', 'МУНИЦИПАЛЬНОЕ АВТОНОМНОЕ ДОШКОЛЬНОЕ ОБРАЗОВАТЕЛЬНОЕ УЧРЕЖДЕНИЕ "ДЕТСКИЙ САД № 409" Г. ПЕРМИ', 590801001, 1, 57701000033108644061, '2015-10-08 00:00:00.0', '', 1, 57308644, 1055904121998, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 31, 'Бюджет городского округа', 56030360, 'Бюджет города Перми', '', 31, '', 'Городской округ', '', 57701000, '', 'город Пермь', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182528, NULL, 'КГАПОУ "ПТПИТ"', NULL, NULL, NULL, NULL, 5904100431, 830, 'Министерство образования и науки Пермского края', 'КРАЕВОЕ ГОСУДАРСТВЕННОЕ АВТОНОМНОЕ ПРОФЕССИОНАЛЬНОЕ ОБРАЗОВАТЕЛЬНОЕ УЧРЕЖДЕНИЕ "ПЕРМСКИЙ ТЕХНИКУМ ПРОМЫШЛЕННЫХ И ИНФОРМАЦИОННЫХ ТЕХНОЛОГИЙ"', 590401001, 1, 57000000032009666081, '2015-12-01 00:00:00.0', '', 1, 57209666, 1025900904974, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182521, NULL, 'КГАПОУ "ПТПТД"', NULL, NULL, NULL, NULL, 5906052769, 830, 'Министерство образования и науки Пермского края', 'КРАЕВОЕ ГОСУДАРСТВЕННОЕ АВТОНОМНОЕ ПРОФЕССИОНАЛЬНОЕ ОБРАЗОВАТЕЛЬНОЕ УЧРЕЖДЕНИЕ "ПЕРМСКИЙ ТЕХНИКУМ ПРОФЕССИОНАЛЬНЫХ ТЕХНОЛОГИЙ И ДИЗАЙНА"', 590401001, 1, 57000000032009028011, '2015-12-01 00:00:00.0', '', 1, 57209028, 1025901380988, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182522, NULL, 'МАОУ "ГИМНАЗИЯ №1" Г. ПЕРМИ', NULL, NULL, NULL, NULL, 5905006199, 930, 'Департамент образования администрации города Перми', 'МУНИЦИПАЛЬНОЕ АВТОНОМНОЕ ОБЩЕОБРАЗОВАТЕЛЬНОЕ УЧРЕЖДЕНИЕ "ГИМНАЗИЯ №1"  Г. ПЕРМИ', 590501001, 1, 57701000033108709081, '2015-10-08 00:00:00.0', '', 1, 57308709, 1025901220950, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 31, 'Бюджет городского округа', 56030360, 'Бюджет города Перми', '', 31, '', 'Городской округ', '', 57701000, '', 'город Пермь', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182545, NULL, 'ГБУЗ ПК "ПЦРБ"', NULL, NULL, NULL, NULL, 5948047591, 820, 'Министерство здравоохранения Пермского края', 'ГОСУДАРСТВЕННОЕ БЮДЖЕТНОЕ УЧРЕЖДЕНИЕ ЗДРАВООХРАНЕНИЯ ПЕРМСКОГО КРАЯ "ПЕРМСКАЯ ЦЕНТРАЛЬНАЯ РАЙОННАЯ БОЛЬНИЦА"', 594801001, 1, 57000000032009704021, '2015-12-25 00:00:00.0', '', 1, 57209704, 1155958058200, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57646419101, 'с Лобаново', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182523, NULL, 'КГБУ "СШОР ПО ДЗЮДО И САМБО"', NULL, NULL, NULL, NULL, 5908025545, 861, 'Министерство физической культуры, спорта и туризма Пермского края', 'КРАЕВОЕ ГОСУДАРСТВЕННОЕ БЮДЖЕТНОЕ УЧРЕЖДЕНИЕ "СПОРТИВНАЯ ШКОЛА ОЛИМПИЙСКОГО РЕЗЕРВА ПО ДЗЮДО И САМБО"', 590801001, 1, 57000000032008497051, '2015-11-20 00:00:00.0', '', 1, 57208497, 1025901603089, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182540, NULL, 'МБОУ "ШКОЛА-ИНТЕРНАТ № 4 ДЛЯ ОБУЧАЮЩИХСЯ С ОГРАНИЧЕННЫМИ ВОЗМОЖНОСТЯМИ ЗДОРОВЬЯ" Г.ПЕРМИ', NULL, NULL, NULL, NULL, 5907013191, 930, 'Департамент образования администрации города Перми', 'МУНИЦИПАЛЬНОЕ БЮДЖЕТНОЕ ОБЩЕОБРАЗОВАТЕЛЬНОЕ УЧРЕЖДЕНИЕ "ШКОЛА-ИНТЕРНАТ № 4 ДЛЯ ОБУЧАЮЩИХСЯ С ОГРАНИЧЕННЫМИ ВОЗМОЖНОСТЯМИ ЗДОРОВЬЯ" Г.ПЕРМИ', 590701001, 1, 57701000033107490031, '2015-10-01 00:00:00.0', '', 1, 57307490, 1025901509150, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 31, 'Бюджет городского округа', 56030360, 'Бюджет города Перми', '', 31, '', 'Городской округ', '', 57701000, '', 'город Пермь', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (45179, NULL, 'ГКУЗ "ПК ТЦМК"', NULL, NULL, NULL, NULL, 5904103489, 820, 'Министерство здравоохранения Пермского края', 'ГОСУДАРСТВЕННОЕ КАЗЁННОЕ УЧРЕЖДЕНИЕ ЗДРАВООХРАНЕНИЯ ПЕРМСКОГО КРАЯ "ПЕРМСКИЙ КРАЕВОЙ ТЕРРИТОРИАЛЬНЫЙ ЦЕНТР МЕДИЦИНЫ  КАТАСТРОФ"', 590401001, 1, 57000000032002051061, '2015-11-20 00:00:00.0', '', 1, 57202051, 1025900916689, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', 57200016, 'МИНИСТЕРСТВО ЗДРАВООХРАНЕНИЯ ПЕРМСКОГО КРАЯ');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182543, NULL, 'КГАОУ ДО "СДЮСШОР "ОЛИМПИЕЦ"', NULL, NULL, NULL, NULL, 5905006520, 861, 'Министерство физической культуры, спорта и туризма Пермского края', 'КРАЕВОЕ ГОСУДАРСТВЕННОЕ АВТОНОМНОЕ ОБРАЗОВАТЕЛЬНОЕ УЧРЕЖДЕНИЕ ДОПОЛНИТЕЛЬНОГО ОБРАЗОВАНИЯ "СПЕЦИАЛИЗИРОВАННАЯ ДЕТСКО-ЮНОШЕСКАЯ СПОРТИВНАЯ ШКОЛА ОЛИМПИЙСКОГО РЕЗЕРВА "ОЛИМПИЕЦ"', 590501001, 1, 57000000032009035021, '2015-12-01 00:00:00.0', '', 1, 57209035, 1025901213667, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182544, NULL, 'ГКУ "СМЭУ ПЕРМСКОГО КРАЯ"', NULL, NULL, NULL, NULL, 5902290882, 880, 'Министерство транспорта Пермского края', 'ГОСУДАРСТВЕННОЕ КРАЕВОЕ УЧРЕЖДЕНИЕ "СПЕЦИАЛИЗИРОВАННОЕ МОНТАЖНО-ЭКСПЛУАТАЦИОННОЕ УПРАВЛЕНИЕ ПЕРМСКОГО КРАЯ"', 590201001, 1, 57000000032018881081, '2015-12-11 00:00:00.0', '', 1, 57218881, 1025900526200, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182533, NULL, 'ГБПОУ "ПХТТ"', NULL, NULL, NULL, NULL, 5908011750, 830, 'Министерство образования и науки Пермского края', 'ГОСУДАРСТВЕННОЕ БЮДЖЕТНОЕ ПРОФЕССИОНАЛЬНОЕ ОБРАЗОВАТЕЛЬНОЕ УЧРЕЖДЕНИЕ "ПЕРМСКИЙ ХИМИКО-ТЕХНОЛОГИЧЕСКИЙ ТЕХНИКУМ"', 590801001, 1, 57000000032008460081, '2015-11-30 00:00:00.0', '', 1, 57208460, 1025901612087, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182534, NULL, 'ГБУК "ПКДБ ИМ. Л. И. КУЗЬМИНА"', NULL, NULL, NULL, NULL, 5902290258, 825, 'Министерство культуры Пермского края', 'ГОСУДАРСТВЕННОЕ БЮДЖЕТНОЕ УЧРЕЖДЕНИЕ КУЛЬТУРЫ "ПЕРМСКАЯ КРАЕВАЯ ДЕТСКАЯ БИБЛИОТЕКА ИМ. Л. И. КУЗЬМИНА"', 590201001, 1, 57000000032008366031, '2015-11-10 00:00:00.0', '', 1, 57208366, 1025900531766, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182532, NULL, 'ГКБУ "УЭАЗ"', NULL, NULL, NULL, NULL, 5902293107, 875, 'Аппарат Правительства Пермского края', 'ГОСУДАРСТВЕННОЕ КРАЕВОЕ БЮДЖЕТНОЕ УЧРЕЖДЕНИЕ "УПРАВЛЕНИЕ ПО ЭКСПЛУАТАЦИИ АДМИНИСТРАТИВНЫХ ЗДАНИЙ"', 590201001, 1, 57000000032008027041, '2015-12-01 00:00:00.0', '', 1, 57208027, 1045900115106, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (15162, NULL, 'ГБПОУ "ЛЫСЬВЕНСКИЙ ПОЛИТЕХНИЧЕСКИЙ КОЛЛЕДЖ"', NULL, NULL, NULL, NULL, 5918214069, 830, 'Министерство образования и науки Пермского края', 'ГОСУДАРСТВЕННОЕ БЮДЖЕТНОЕ ПРОФЕССИОНАЛЬНОЕ ОБРАЗОВАТЕЛЬНОЕ УЧРЕЖДЕНИЕ "ЛЫСЬВЕНСКИЙ ПОЛИТЕХНИЧЕСКИЙ КОЛЛЕДЖ"', 591801001, 1, 57000000032009241021, '2015-11-18 00:00:00.0', '', 1, 57209241, 1135918000943, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57726000001, 'г Лысьва', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (147216, NULL, 'ГКУЗ ПК "МИАЦ"', NULL, NULL, NULL, NULL, 5904329415, 820, 'Министерство здравоохранения Пермского края', 'ГОСУДАРСТВЕННОЕ КАЗЕННОЕ УЧРЕЖДЕНИЕ ЗДРАВООХРАНЕНИЯ ПЕРМСКОГО КРАЯ "МЕДИЦИНСКИЙ ИНФОРМАЦИОННО-АНАЛИТИЧЕСКИЙ ЦЕНТР"', 590401001, 1, 57000000032019229071, '2016-02-19 00:00:00.0', '', 1, 57219229, 1155958129040, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', 57200016, 'МИНИСТЕРСТВО ЗДРАВООХРАНЕНИЯ ПЕРМСКОГО КРАЯ');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (153142, NULL, 'ГБПОУ ПМК', NULL, NULL, NULL, NULL, 5907005112, 830, 'Министерство образования и науки Пермского края', 'ГОСУДАРСТВЕННОЕ БЮДЖЕТНОЕ ПРОФЕССИОНАЛЬНОЕ ОБРАЗОВАТЕЛЬНОЕ УЧРЕЖДЕНИЕ "ПЕРМСКИЙ МАШИНОСТРОИТЕЛЬНЫЙ КОЛЛЕДЖ"', 590701001, 1, 57000000032008234031, '2015-11-30 00:00:00.0', '', 1, 57208234, 1025901510403, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (152646, NULL, 'КГАПОУ "АВИАТЕХНИКУМ"', NULL, NULL, NULL, NULL, 5902290441, 830, 'Министерство образования и науки Пермского края', 'КРАЕВОЕ ГОСУДАРСТВЕННОЕ АВТОНОМНОЕ ПРОФЕССИОНАЛЬНОЕ ОБРАЗОВАТЕЛЬНОЕ УЧРЕЖДЕНИЕ "ПЕРМСКИЙ АВИАЦИОННЫЙ ТЕХНИКУМ ИМ. А.Д. ШВЕЦОВА"', 590201001, 1, '570000000320D4976001', '2015-11-11 00:00:00.0', '', 1, '572D4976', 1025900538344, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (151998, NULL, 'ГБПОУ КАТК', NULL, NULL, NULL, NULL, 5917100510, 830, 'Министерство образования и науки Пермского края', 'ГОСУДАРСТВЕННОЕ БЮДЖЕТНОЕ ПРОФЕССИОНАЛЬНОЕ ОБРАЗОВАТЕЛЬНОЕ УЧРЕЖДЕНИЕ "КУНГУРСКИЙ АВТОТРАНСПОРТНЫЙ КОЛЛЕДЖ"', 591701001, 1, 57000000032000949061, '2015-11-18 00:00:00.0', '', 1, 57200949, 1025901886450, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57722000001, 'г Кунгур', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (150408, NULL, 'КГБОУСУВУ "УРАЛЬСКОЕ ПОДВОРЬЕ"', NULL, NULL, NULL, NULL, 5906031511, 830, 'Министерство образования и науки Пермского края', 'КРАЕВОЕ ГОСУДАРСТВЕННОЕ БЮДЖЕТНОЕ ОБЩЕОБРАЗОВАТЕЛЬНОЕ УЧРЕЖДЕНИЕ "СПЕЦИАЛЬНОЕ УЧЕБНО-ВОСПИТАТЕЛЬНОЕ УЧРЕЖДЕНИЕ ДЛЯ ОБУЧАЮЩИХСЯ С ДЕВИАНТНЫМ (ОБЩЕСТВЕННО-ОПАСНЫМ) ПОВЕДЕНИЕМ "УРАЛЬСКОЕ ПОДВОРЬЕ"', 590501001, 1, 57000000032008229001, '2016-01-28 00:00:00.0', '', 1, 57208229, 1025901366248, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (126134, NULL, 'АППАРАТ ПРАВИТЕЛЬСТВА ПЕРМСКОГО КРАЯ', NULL, NULL, NULL, NULL, 5902290709, 875, 'Аппарат Правительства Пермского края', 'АППАРАТ ПРАВИТЕЛЬСТВА ПЕРМСКОГО КРАЯ', 590201001, 1, 57000000012000004021, '2015-10-09 00:00:00.0', '', 1, 57200004, 1025900524980, '', 1, 2, 'Организация не является финансовым органом публично-правового образования, органом управления государственными внебюджетным фондом', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (98102, NULL, 'КГБУ "УАДИТ"', NULL, NULL, NULL, NULL, 5902192934, 880, 'Министерство транспорта Пермского края', 'КРАЕВОЕ ГОСУДАРСТВЕННОЕ БЮДЖЕТНОЕ УЧРЕЖДЕНИЕ "УПРАВЛЕНИЕ АВТОМОБИЛЬНЫХ ДОРОГ И ТРАНСПОРТА" ПЕРМСКОГО КРАЯ', 590201001, 1, 57000000032007469011, '2015-12-11 00:00:00.0', '', 1, 57207469, 1035900096462, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (75686, NULL, 'ГКУПК "ЦППМСП"', NULL, NULL, NULL, NULL, 8107009755, 830, 'Министерство образования и науки Пермского края', 'ГОСУДАРСТВЕННОЕ КАЗЕННОЕ УЧРЕЖДЕНИЕ ПЕРМСКОГО КРАЯ "ЦЕНТР ПСИХОЛОГО-ПЕДАГОГИЧЕСКОЙ, МЕДИЦИНСКОЙ И СОЦИАЛЬНОЙ ПОМОЩИ"', 590401001, 1, 57000000032001037071, '2015-11-24 00:00:00.0', '', 1, 57201037, 1038102239680, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', 57200007, 'МИНИСТЕРСТВО ОБРАЗОВАНИЯ И НАУКИ ПЕРМСКОГО КРАЯ');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (73260, NULL, 'ГБПОУ "ПККИИК"', NULL, NULL, NULL, NULL, 5905021310, 825, 'Министерство культуры Пермского края', 'ГОСУДАРСТВЕННОЕ БЮДЖЕТНОЕ ПРОФЕССИОНАЛЬНОЕ ОБРАЗОВАТЕЛЬНОЕ УЧРЕЖДЕНИЕ "ПЕРМСКИЙ КРАЕВОЙ КОЛЛЕДЖ ИСКУССТВ И КУЛЬТУРЫ"', 590501001, 1, 57000000032008333031, '2015-11-05 00:00:00.0', '', 1, 57208333, 1025901219860, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182505, NULL, 'МИНИСТЕРСТВО ПО РЕГУЛИРОВАНИЮ КОНТРАКТНОЙ СИСТЕМЫ В СФЕРЕ ЗАКУПОК ПЕРМСКОГО КРАЯ', NULL, NULL, NULL, NULL, 5902293851, 833, 'Министерство по регулированию контрактной системы в сфере закупок Пермского края', 'МИНИСТЕРСТВО ПО РЕГУЛИРОВАНИЮ КОНТРАКТНОЙ СИСТЕМЫ В СФЕРЕ ЗАКУПОК ПЕРМСКОГО КРАЯ', 590201001, 1, 57000000012008213021, '2015-10-02 00:00:00.0', '', 1, 57208213, 1125902006944, '', 1, 2, 'Организация не является финансовым органом публично-правового образования, органом управления государственными внебюджетным фондом', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182508, NULL, 'МИНИСТЕРСТВО ЗДРАВООХРАНЕНИЯ ПЕРМСКОГО КРАЯ', NULL, NULL, NULL, NULL, 5902293308, 820, 'Министерство здравоохранения Пермского края', 'МИНИСТЕРСТВО ЗДРАВООХРАНЕНИЯ ПЕРМСКОГО КРАЯ', 590201001, 1, 57000000012000016081, '2015-11-13 00:00:00.0', '', 1, 57200016, 1065902004629, '', 1, 2, 'Организация не является финансовым органом публично-правового образования, органом управления государственными внебюджетным фондом', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182513, NULL, 'МИНИСТЕРСТВО ОБРАЗОВАНИЯ И НАУКИ ПЕРМСКОГО КРАЯ', NULL, NULL, NULL, NULL, 5902290723, 830, 'Министерство образования и науки Пермского края', 'МИНИСТЕРСТВО ОБРАЗОВАНИЯ И НАУКИ ПЕРМСКОГО КРАЯ', 590201001, 1, 57000000012000007091, '2015-10-09 00:00:00.0', '', 1, 57200007, 1025900530336, '', 1, 2, 'Организация не является финансовым органом публично-правового образования, органом управления государственными внебюджетным фондом', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182514, NULL, 'МИНИСТЕРСТВО ФИЗИЧЕСКОЙ КУЛЬТУРЫ, СПОРТА И ТУРИЗМА ПЕРМСКОГО КРАЯ', NULL, NULL, NULL, NULL, 5902290586, 861, 'Министерство физической культуры, спорта и туризма Пермского края', 'МИНИСТЕРСТВО ФИЗИЧЕСКОЙ КУЛЬТУРЫ, СПОРТА И ТУРИЗМА ПЕРМСКОГО КРАЯ', 590201001, 1, 57000000012000023091, '2015-11-10 00:00:00.0', '', 1, 57200023, 1025900530611, '', 1, 2, 'Организация не является финансовым органом публично-правового образования, органом управления государственными внебюджетным фондом', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182509, NULL, 'МИНПРИРОДЫ ПЕРМСКОГО КРАЯ', NULL, NULL, NULL, NULL, 5902293298, 816, 'Министерство природных ресурсов, лесного хозяйства и экологии Пермского края', 'МИНИСТЕРСТВО ПРИРОДНЫХ РЕСУРСОВ, ЛЕСНОГО ХОЗЯЙСТВА И ЭКОЛОГИИ ПЕРМСКОГО КРАЯ', 590201001, 1, 57000000012000005011, '2015-10-09 00:00:00.0', '', 1, 57200005, 1065902004354, '', 1, 2, 'Организация не является финансовым органом публично-правового образования, органом управления государственными внебюджетным фондом', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182512, NULL, 'МИНФИН ПЕРМСКОГО КРАЯ', NULL, NULL, NULL, NULL, 5902290917, 840, 'Министерство финансов Пермского края', 'МИНИСТЕРСТВО ФИНАНСОВ ПЕРМСКОГО КРАЯ', 590201001, 1, 57000000012000001051, '2015-09-25 00:00:00.0', '', 1, 57200001, 1035900070293, '', 1, 1, 'Организация является финансовым органом публично-правового образования, органом управления государственными внебюджетным фондом', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182504, NULL, 'МИНЭКОНОМРАЗВИТИЯ ПЕРМСКОГО КРАЯ', NULL, NULL, NULL, NULL, 5902993324, 836, 'Министерство экономического развития и инвестиций Пермского края', 'МИНИСТЕРСТВО ЭКОНОМИЧЕСКОГО РАЗВИТИЯ И ИНВЕСТИЦИЙ ПЕРМСКОГО КРАЯ', 590201001, 1, 57000000012009394011, '2015-11-05 00:00:00.0', '', 1, 57209394, 1145958021340, '', 1, 2, 'Организация не является финансовым органом публично-правового образования, органом управления государственными внебюджетным фондом', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182507, NULL, 'МИНИСТЕРСТВО ПРОМЫШЛЕННОСТИ, ПРЕДПРИНИМАТЕЛЬСТВА И ТОРГОВЛИ ПЕРМСКОГО КРАЯ', NULL, NULL, NULL, NULL, 5902293467, 832, 'Министерство промышленности, предпринимательства и торговли Пермского края', 'МИНИСТЕРСТВО ПРОМЫШЛЕННОСТИ, ПРЕДПРИНИМАТЕЛЬСТВА И ТОРГОВЛИ ПЕРМСКОГО КРАЯ', 590201001, 1, 57000000012000037031, '2015-10-12 00:00:00.0', '', 1, 57200037, 1075902000085, '', 1, 2, 'Организация не является финансовым органом публично-правового образования, органом управления государственными внебюджетным фондом', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182529, NULL, 'ГБУЗ ПК "КГБ"', NULL, NULL, NULL, NULL, 5916032419, 820, 'Министерство здравоохранения Пермского края', 'ГОСУДАРСТВЕННОЕ БЮДЖЕТНОЕ УЧРЕЖДЕНИЕ ЗДРАВООХРАНЕНИЯ ПЕРМСКОГО КРАЯ "КРАСНОКАМСКАЯ ГОРОДСКАЯ БОЛЬНИЦА"', 591601001, 1, '570000000320Н1747031', '2017-01-25 00:00:00.0', '', 1, '572Н1747', 1165958123473, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57627101001, 'г Краснокамск', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182531, NULL, 'ГКУ ПК "ПЕРМОХОТА"', NULL, NULL, NULL, NULL, 5902293185, 816, 'Министерство природных ресурсов, лесного хозяйства и экологии Пермского края', 'ГОСУДАРСТВЕННОЕ КАЗЕННОЕ УЧРЕЖДЕНИЕ ПЕРМСКОГО КРАЯ "ПЕРМОХОТА"', 590201001, 1, 57000000032019188061, '2015-12-15 00:00:00.0', '', 1, 57219188, 1055900296418, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', 57200005, 'МИНИСТЕРСТВО ПРИРОДНЫХ РЕСУРСОВ, ЛЕСНОГО ХОЗЯЙСТВА И ЭКОЛОГИИ ПЕРМСКОГО КРАЯ');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182524, NULL, 'КГКОУ "ОЧЕРСКОЕ СУВУ"', NULL, NULL, NULL, NULL, 5947009089, 830, 'Министерство образования и науки Пермского края', 'КРАЕВОЕ ГОСУДАРСТВЕННОЕ КАЗЕННОЕ ОБЩЕОБРАЗОВАТЕЛЬНОЕ УЧРЕЖДЕНИЕ "ОЧЕРСКОЕ СПЕЦИАЛЬНОЕ УЧЕБНО-ВОСПИТАТЕЛЬНОЕ УЧРЕЖДЕНИЕ ДЛЯ ОБУЧАЮЩИХСЯ  С ДЕВИАНТНЫМ (ОБЩЕСТВЕННО ОПАСНЫМ) ПОВЕДЕНИЕМ ЗАКРЫТОГО ТИПА"', 594701001, 1, 57000000032008547051, '2015-11-20 00:00:00.0', '', 1, 57208547, 1025902376598, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57644101001, 'г Очер', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', 57200007, 'МИНИСТЕРСТВО ОБРАЗОВАНИЯ И НАУКИ ПЕРМСКОГО КРАЯ');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182526, NULL, 'ГКУЗОТ "ПКБСМЭ"', NULL, NULL, NULL, NULL, 5904082630, 820, 'Министерство здравоохранения Пермского края', 'ГОСУДАРСТВЕННОЕ КАЗЕННОЕ УЧРЕЖДЕНИЕ ЗДРАВООХРАНЕНИЯ ОСОБОГО ТИПА ПЕРМСКОГО КРАЯ  "ПЕРМСКОЕ КРАЕВОЕ БЮРО  СУДЕБНО-МЕДИЦИНСКОЙ ЭКСПЕРТИЗЫ"', 590401001, 1, 57000000032003338091, '2015-12-23 00:00:00.0', '', 1, 57203338, 1025900917514, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', 57200016, 'МИНИСТЕРСТВО ЗДРАВООХРАНЕНИЯ ПЕРМСКОГО КРАЯ');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182517, NULL, 'ИНСПЕКЦИЯ ПО ОХРАНЕ ОКН ПК', NULL, NULL, NULL, NULL, 5902043202, 826, 'Государственная инспекция по охране объектов культурного наследия Пермского края', 'ГОСУДАРСТВЕННАЯ ИНСПЕКЦИЯ ПО ОХРАНЕ ОБЪЕКТОВ КУЛЬТУРНОГО НАСЛЕДИЯ ПЕРМСКОГО КРАЯ', 590201001, 1, 57000000012019345001, '2017-06-02 00:00:00.0', '', 1, 57219345, 1175958018576, '', 1, 2, 'Организация не является финансовым органом публично-правового образования, органом управления государственными внебюджетным фондом', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182520, NULL, 'МИНИСТЕРСТВО ПО УПРАВЛЕНИЮ ИМУЩЕСТВОМ И ЗЕМЕЛЬНЫМ ОТНОШЕНИЯМ ПЕРМСКОГО КРАЯ', NULL, NULL, NULL, NULL, 5902293192, 812, 'Министерство по управлению имуществом и земельным отношениям Пермского края', 'МИНИСТЕРСТВО ПО УПРАВЛЕНИЮ ИМУЩЕСТВОМ И ЗЕМЕЛЬНЫМ ОТНОШЕНИЯМ ПЕРМСКОГО КРАЯ', 590401001, 1, 57000000012000015091, '2015-10-01 00:00:00.0', '', 1, 57200015, 1055900361835, '', 1, 2, 'Организация не является финансовым органом публично-правового образования, органом управления государственными внебюджетным фондом', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182515, NULL, 'МИНИСТЕРСТВО ИНФОРМАЦИОННОГО РАЗВИТИЯ И СВЯЗИ ПЕРМСКОГО КРАЯ', NULL, NULL, NULL, NULL, 5902221423, 821, 'Министерство информационного развития и связи Пермского края', 'МИНИСТЕРСТВО ИНФОРМАЦИОННОГО РАЗВИТИЯ И СВЯЗИ ПЕРМСКОГО КРАЯ', 590201001, 1, 57000000012008047041, '2015-10-02 00:00:00.0', '', 1, 57208047, 1105902012853, '', 1, 2, 'Организация не является финансовым органом публично-правового образования, органом управления государственными внебюджетным фондом', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182538, NULL, 'КГАУ "ПЕРМСКИЙ КРАЕВОЙ  МФЦ ПГМУ"', NULL, NULL, NULL, NULL, 5902293812, 821, 'Министерство информационного развития и связи Пермского края', 'КРАЕВОЕ ГОСУДАРСТВЕННОЕ АВТОНОМНОЕ УЧРЕЖДЕНИЕ "ПЕРМСКИЙ КРАЕВОЙ МНОГОФУНКЦИОНАЛЬНЫЙ ЦЕНТР ПРЕДОСТАВЛЕНИЯ ГОСУДАРСТВЕННЫХ И МУНИЦИПАЛЬНЫХ УСЛУГ"', 590201001, 1, 57000000032009053091, '2015-12-01 00:00:00.0', '', 1, 57209053, 1115902011950, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182510, NULL, 'АДМИНИСТРАЦИЯ ГУБЕРНАТОРА ПЕРМСКОГО КРАЯ', NULL, NULL, NULL, NULL, 5902293202, 811, 'Администрация губернатора Пермского края', 'АДМИНИСТРАЦИЯ ГУБЕРНАТОРА ПЕРМСКОГО КРАЯ', 590201001, 1, 57000000012000013011, '2015-10-09 00:00:00.0', '', 1, 57200013, 1055900364156, '', 1, 2, 'Организация не является финансовым органом публично-правового образования, органом управления государственными внебюджетным фондом', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182539, NULL, 'ДЕПАРТАМЕНТ ОБРАЗОВАНИЯ АДМИНИСТРАЦИИ ГОРОДА ПЕРМИ', NULL, NULL, NULL, NULL, 5902290434, 930, 'Департамент образования администрации города Перми', 'ДЕПАРТАМЕНТ ОБРАЗОВАНИЯ АДМИНИСТРАЦИИ ГОРОДА ПЕРМИ', 590201001, 1, 57701000013100045021, '2015-09-25 00:00:00.0', '', 1, 57300045, 1025900524066, '', 1, 2, 'Организация не является финансовым органом публично-правового образования, органом управления государственными внебюджетным фондом', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 31, 'Бюджет городского округа', 56030360, 'Бюджет города Перми', 31, '', 'Городской округ', '', 57701000, '', 'город Пермь', '', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182511, NULL, 'МИНИСТЕРСТВО КУЛЬТУРЫ КРАЯ', NULL, NULL, NULL, NULL, 5902290931, 825, 'Министерство культуры Пермского края', 'МИНИСТЕРСТВО КУЛЬТУРЫ ПЕРМСКОГО КРАЯ', 590201001, 1, 57000000012000009071, '2015-10-01 00:00:00.0', '', 1, 57200009, 1025900538993, '', 1, 2, 'Организация не является финансовым органом публично-правового образования, органом управления государственными внебюджетным фондом', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (182536, NULL, 'ГКУЗ "ПКЦ СПИД  И  ИЗ"', NULL, NULL, NULL, NULL, 5902291290, 820, 'Министерство здравоохранения Пермского края', 'ГОСУДАРСТВЕННОЕ КАЗЕННОЕ  УЧРЕЖДЕНИЕ ЗДРАВООХРАНЕНИЯ ПЕРМСКОГО КРАЯ "ПЕРМСКИЙ КРАЕВОЙ ЦЕНТР ПО ПРОФИЛАКТИКЕ И БОРЬБЕ СО СПИД И ИНФЕКЦИОННЫМИ ЗАБОЛЕВАНИЯМИ"', 590501001, 1, 57000000032020025011, '2015-11-20 00:00:00.0', '', 1, 57220025, 1025900520095, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', 57200016, 'МИНИСТЕРСТВО ЗДРАВООХРАНЕНИЯ ПЕРМСКОГО КРАЯ');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (45194, NULL, 'ГБУЗ "ПКСПК"', NULL, NULL, NULL, NULL, 5906107961, 820, 'Министерство здравоохранения Пермского края', 'ГОСУДАРСТВЕННОЕ БЮДЖЕТНОЕ УЧРЕЖДЕНИЕ ЗДРАВООХРАНЕНИЯ "ПЕРМСКАЯ КРАЕВАЯ СТАНЦИЯ ПЕРЕЛИВАНИЯ КРОВИ"', 590601001, 1, 57000000032007053031, '2015-11-30 00:00:00.0', '', 1, 57207053, 1115906003344, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', '', '');
INSERT INTO public.spr_org (id, grbs_id, org_name, related, employee_count, outstaff_count, sort_order, inn, kbk_code, kbk_name, full_name, kpp, status, recordnum, inclusiondate, exclusiondate, updatenum, code, ogrn, isuch, isogv, isobosob, ougvcode, ougvname, regioncode, regionname, oktmocode, oktmoname, okatocode, okatoname, budgetlvlcode, budgetlvlname, budgetcode, budgetname, creatorkindcode, founderkindcode, creatorkindname, founderkindname, сreatorplacecode, founderplacecode, creatorplacename, founderplacename, parentcode, parentname) VALUES (105778, NULL, 'ГКУ ПК "ИМУЩЕСТВЕННОЕ КАЗНАЧЕЙСТВО ПЕРМСКОГО КРАЯ"', NULL, NULL, NULL, NULL, 5902292738, 812, 'Министерство по управлению имуществом и земельным отношениям Пермского края', 'ГОСУДАРСТВЕННОЕ КАЗЕННОЕ УЧРЕЖДЕНИЕ ПЕРМСКОГО КРАЯ "ИМУЩЕСТВЕННОЕ КАЗНАЧЕЙСТВО ПЕРМСКОГО КРАЯ"', 590401001, 1, 57000000032001526051, '2015-10-28 00:00:00.0', '', 1, 57201526, 1025900523153, 0, 0, '', '', 0, 59, 'ПЕРМСКИЙ', 57701000001, 'г Пермь', '', '', 20, 'Бюджет субъекта Российской Федерации', 56020359, 'Бюджет Пермского края', '', 20, '', 'Субъект Российской Федерации', '', 57000000, '', 'Пермский край', 57200015, 'МИНИСТЕРСТВО ПО УПРАВЛЕНИЮ ИМУЩЕСТВОМ И ЗЕМЕЛЬНЫМ ОТНОШЕНИЯМ ПЕРМСКОГО КРАЯ');


INSERT INTO public.spr_subsystem (id, name, sort_order, level1_name, level1_id) VALUES (-2147483648, 'Не определено', 2147483647, 'Не определено', -2147483648);
INSERT INTO public.spr_subsystem (id, name, sort_order, level1_name, level1_id) VALUES (1, 'Бухгалтерский учет зарплаты', 1, 'Зарплата и кадры государственного учреждения', 1);
INSERT INTO public.spr_subsystem (id, name, sort_order, level1_name, level1_id) VALUES (2, 'Бухгалтерский учет', 1, 'Бухгалтерия государственного учреждения', 2);
INSERT INTO public.spr_subsystem (id, name, sort_order, level1_name, level1_id) VALUES (4, 'Кадровый учет', 3, 'Зарплата и кадры государственного учреждения', 1);
INSERT INTO public.spr_subsystem (id, name, sort_order, level1_name, level1_id) VALUES (5, 'Зарплата', 2, 'Зарплата и кадры государственного учреждения', 1);

INSERT INTO spr_object_type (id, name) VALUES (1, 'Документ');
INSERT INTO spr_object_type (id, name) VALUES (2, 'Отчет');
INSERT INTO spr_object_type (id, name) VALUES (3, 'План видов расчета');
INSERT INTO spr_object_type (id, name) VALUES (4, 'План видов характеристик');
INSERT INTO spr_object_type (id, name) VALUES (5, 'План счетов');
INSERT INTO spr_object_type (id, name) VALUES (6, 'Регистр сведений');
INSERT INTO spr_object_type (id, name) VALUES (7, 'Справочник');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Авансовый отчет');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Акт о приемке материалов');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Акт о результатах инвентаризации');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Акт об оказании услуг');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Акт сверки взаиморасчетов');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Заявка на кассовый расход');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Исходящий электронный документ');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Кассовая смена');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Кассовое выбытие');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Кассовое поступление');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Корректировка расчетов с учредителем');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Корректировка регистров');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Корректировочный счет-фактура выданный');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Корректировочный счет-фактура полученный');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Операция (бухгалтерская)');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Отзыв согласия на обработку персональных данных');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Отражение зарплаты в учете');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Отчет кассира (173н)');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Платежное поручение');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Платежное требование');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Принятие к учету ОС, НМА, НПА');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Расходный кассовый ордер');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Сторно');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Счет на оплату');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Счет-фактура выданный');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Больничный лист');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Ввод остатков отпусков');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Ведомость в банк');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Ведомость в кассу');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Ведомость на счета');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Ведомость прочих доходов в банк');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Начисление за первую половину месяца');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Начисление зарплаты и взносов');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Начисление оценочных обязательств по отпускам');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Начисление по договорам');
INSERT INTO spr_object (type_id, name) VALUES (1, 'Начисление прочих доходов');
INSERT INTO spr_object (type_id, name) VALUES (2, 'Анализ кассового и фактического исполнения');
INSERT INTO spr_object (type_id, name) VALUES (2, 'Анализ начислений (образование)');
INSERT INTO spr_object (type_id, name) VALUES (2, 'Анализ начислений (родительская плата)');
INSERT INTO spr_object (type_id, name) VALUES (2, 'Анализ субконто');
INSERT INTO spr_object (type_id, name) VALUES (2, 'Журнал операций (ф.0504071)');
INSERT INTO spr_object (type_id, name) VALUES (2, 'Журнал регистрации кассовых ордеров');
INSERT INTO spr_object (type_id, name) VALUES (2, 'Журнал регистрации обязательств');
INSERT INTO spr_object (type_id, name) VALUES (2, 'Состав кадрового резерва');
INSERT INTO spr_object (type_id, name) VALUES (2, 'Составы семей сотрудников');
INSERT INTO spr_object (type_id, name) VALUES (2, 'Состояние проверок в ФСКН');
INSERT INTO spr_object (type_id, name) VALUES (2, 'Состояние проверок в ФСКН');
INSERT INTO spr_object (type_id, name) VALUES (3, 'Начисления');
INSERT INTO spr_object (type_id, name) VALUES (3, 'Удержания');
INSERT INTO spr_object (type_id, name) VALUES (4, 'Виды характеристик обязательства');
INSERT INTO spr_object (type_id, name) VALUES (4, 'Дополнительные бюджетные классификаторы');
INSERT INTO spr_object (type_id, name) VALUES (4, 'Дополнительные реквизиты и сведения');
INSERT INTO spr_object (type_id, name) VALUES (5, 'ЕПСБУ');
INSERT INTO spr_object (type_id, name) VALUES (6, 'Журнал обмен с банками');
INSERT INTO spr_object (type_id, name) VALUES (6, 'Журнал отправок в контролирующие органы');
INSERT INTO spr_object (type_id, name) VALUES (6, 'Журнал регистрации агента копирования облачного архива');
INSERT INTO spr_object (type_id, name) VALUES (6, 'Журнал событий 1С:ДиректБанк');
INSERT INTO spr_object (type_id, name) VALUES (6, 'Журнал событий ЭДО');
INSERT INTO spr_object (type_id, name) VALUES (6, 'Журнал учета компенсации родительской платы');
INSERT INTO spr_object (type_id, name) VALUES (6, 'Журнал учета счетов-фактур');
INSERT INTO spr_object (type_id, name) VALUES (6, 'График работы сотрудников');
INSERT INTO spr_object (type_id, name) VALUES (6, 'График работы сотрудников интервальный');
INSERT INTO spr_object (type_id, name) VALUES (6, 'График работы сотрудников испр');
INSERT INTO spr_object (type_id, name) VALUES (6, 'Графики работы по видам времени');
INSERT INTO spr_object (type_id, name) VALUES (7, 'Банки');
INSERT INTO spr_object (type_id, name) VALUES (7, 'Банковские и казначейские счета');
INSERT INTO spr_object (type_id, name) VALUES (7, 'Банковские карты');
INSERT INTO spr_object (type_id, name) VALUES (7, 'Банковские символы');
INSERT INTO spr_object (type_id, name) VALUES (7, 'Бланки отчетов');
INSERT INTO spr_object (type_id, name) VALUES (7, 'Бланки строгой отчетности');
INSERT INTO spr_object (type_id, name) VALUES (7, 'Бюджеты');
INSERT INTO spr_object (type_id, name) VALUES (7, 'Валюты');
INSERT INTO spr_object (type_id, name) VALUES (7, 'Виды контактной информации');
INSERT INTO spr_object (type_id, name) VALUES (7, 'Виды мероприятия обучения и развития');
INSERT INTO spr_object (type_id, name) VALUES (7, 'Виды налоговых органов');
INSERT INTO spr_object (type_id, name) VALUES (7, 'Виды образования');
INSERT INTO spr_object (type_id, name) VALUES (7, 'Виды операций расчета зарплаты');

INSERT INTO spr_cart_value (name) VALUES ('Табельный Номер');
INSERT INTO spr_cart_value (name) VALUES ('Дата Рождения');
INSERT INTO spr_cart_value (name) VALUES ('Пол');
INSERT INTO spr_cart_value (name) VALUES ('ИНН');
INSERT INTO spr_cart_value (name) VALUES ('Страховой Номер ПФР');
INSERT INTO spr_cart_value (name) VALUES ('Место Рождения');
INSERT INTO spr_cart_value (name) VALUES ('Сведения о документе, удостоверяющем личность');
INSERT INTO spr_cart_value (name) VALUES ('Адрес регистрации или проживания');
INSERT INTO spr_cart_value (name) VALUES ('Сведения о специальности');
INSERT INTO spr_cart_value (name) VALUES ('Сведения о трудовой деятельности');
INSERT INTO spr_cart_value (name) VALUES ('Сведения о стаже');
INSERT INTO spr_cart_value (name) VALUES ('Сведения об образовании');
INSERT INTO spr_cart_value (name) VALUES ('Сведения о семье');

-- значения измерений
INSERT INTO public.cube_dimension (cube_dim_id, cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year) VALUES (82, 14, 2, 'calendar_id', 1, 'true', 4, 2014);
INSERT INTO public.cube_dimension (cube_dim_id, cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year) VALUES (85, 15, 2, 'calendar_id', 1, 'true', 4, 2014);
INSERT INTO public.cube_dimension (cube_dim_id, cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year) VALUES (88, 16, 2, 'calendar_id', 1, 'true', 4, 2014);
INSERT INTO public.cube_dimension (cube_dim_id, cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year) VALUES (90, 17, 2, 'calendar_id', 1, 'true', 4, 2014);
INSERT INTO public.cube_dimension (cube_dim_id, cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year) VALUES (73, 11, 2, 'calendar_id', 1, 'true', 5, 2014);
INSERT INTO public.cube_dimension (cube_dim_id, cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year) VALUES (76, 12, 20, 'org_id', 1, 'true', NULL, 2014);
INSERT INTO public.cube_dimension (cube_dim_id, cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year) VALUES (75, 12, 2, 'calendar_id', 1, 'true', 5, 2014);
INSERT INTO public.cube_dimension (cube_dim_id, cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year) VALUES (74, 11, 20, 'org_id', 1, 'true', NULL, 2014);
INSERT INTO public.cube_dimension (cube_dim_id, cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year) VALUES (81, 13, 22, 'object_id', 1, 'true', NULL, 2014);
INSERT INTO public.cube_dimension (cube_dim_id, cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year) VALUES (78, 13, 2, 'calendar_id', 1, 'true', 5, 2014);
INSERT INTO public.cube_dimension (cube_dim_id, cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year) VALUES (80, 13, 21, 'subsystem_id', 1, 'true', NULL, 2014);
INSERT INTO public.cube_dimension (cube_dim_id, cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year) VALUES (77, 12, 21, 'subsystem_id', 1, 'true', NULL, 2014);
INSERT INTO public.cube_dimension (cube_dim_id, cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year) VALUES (79, 13, 20, 'org_id', 1, 'true', NULL, 2014);
INSERT INTO public.cube_dimension (cube_dim_id, cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year) VALUES (89, 16, 20, 'org_id', 1, 'true', NULL, 2014);
INSERT INTO public.cube_dimension (cube_dim_id, cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year) VALUES (86, 15, 20, 'org_id', 1, 'true', NULL, 2014);
INSERT INTO public.cube_dimension (cube_dim_id, cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year) VALUES (84, 14, 21, 'subsystem_id', 1, 'true', NULL, 2014);
INSERT INTO public.cube_dimension (cube_dim_id, cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year) VALUES (83, 14, 20, 'org_id', 1, 'true', NULL, 2014);
INSERT INTO public.cube_dimension (cube_dim_id, cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year) VALUES (92, 17, 23, 'cart_value_id', 1, 'true', NULL, 2014);
INSERT INTO public.cube_dimension (cube_dim_id, cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year) VALUES (91, 17, 20, 'org_id', 1, 'true', NULL, 2014);


-- новые кубы
INSERT INTO cubes (cube_id, group_id, cube_name, host, database_name, schema_name, table_name, is_visible)
VALUES (11, 1, 'Сведения о подключении пользователей организации', NULL, 'mirs', 'public', 'fact_connections', true);

INSERT INTO cubes (cube_id, group_id, cube_name, host, database_name, schema_name, table_name, is_visible)
VALUES (12, 1, 'Сведения об активности пользователей', NULL, 'foms7', 'public', 'fact_user_activity', true);

INSERT INTO cubes (cube_id, group_id, cube_name, host, database_name, schema_name, table_name, is_visible)
VALUES (13, 1, 'Статистика по объектам Системы', NULL, 'mirs', 'public', 'fact_object_stat', true);

INSERT INTO cubes (cube_id, group_id, cube_name, host, database_name, schema_name, table_name, is_visible)
VALUES (14, 1, 'Дисциплина ведения учета', NULL, 'mirs', 'public', 'fact_cube_buh', true);

INSERT INTO cubes (cube_id, group_id, cube_name, host, database_name, schema_name, table_name, is_visible)
VALUES (15, 1, 'Дисциплина ведения кадрового учета', NULL, 'mirs', 'public', 'fact_staff_stat', true);

INSERT INTO cubes (cube_id, group_id, cube_name, host, database_name, schema_name, table_name, is_visible)
VALUES (16, 1, 'Сведения по кадрам', NULL, 'mirs', 'public', 'fact_staff_details', true);

INSERT INTO cubes (cube_id, group_id, cube_name, host, database_name, schema_name, table_name, is_visible)
VALUES (17, 1, 'Статистика по полноте заполнения карточки сотрудника', NULL, 'mirs', 'public', 'fact_employee_cart', true);

--установка счетчика
SELECT setval('cube_dimension_cube_dim_id_seq', 17, true);

-- новые измерения
INSERT INTO dimensions (dimension_id, dim_type_id, d_name, d_key, d_desc, field_id, field_name, field_parent_id, field_order, table_name, where_text)
VALUES (20, 4, 'Организация', 'org_key', NULL, '', 'org_name', NULL, 'sort_order', 'spr_org', NULL);

INSERT INTO dimensions (dimension_id, dim_type_id, d_name, d_key, d_desc, field_id, field_name, field_parent_id, field_order, table_name, where_text)
VALUES (21, 4, 'Подсистема', 'subsystem_key', NULL, 'id', 'name', NULL, 'sort_order', 'spr_subsystem', NULL);

INSERT INTO dimensions (dimension_id, dim_type_id, d_name, d_key, d_desc, field_id, field_name, field_parent_id, field_order, table_name, where_text)
VALUES (22, 4, 'Объект', 'object_key', NULL, 'id', 'name', NULL, 'sort_order', 'spr_object', NULL);

INSERT INTO dimensions (dimension_id, dim_type_id, d_name, d_key, d_desc, field_id, field_name, field_parent_id, field_order, table_name, where_text)
VALUES (23, 4, 'Показатель карточки', 'cart_value_key', NULL, 'id', 'name', NULL, 'sort_order', 'spr_cart_value', NULL);

-- связать измерения с кубами
-- Куб	-> Измерения (Аналитические разрезы)
------------------------------------------------
-- Сведения о подключении пользователей организации 	-> Календарь Организация
-- Сведения об активности пользователей	->  Календарь Организация Подсистема
-- Сведения об объектах Системы	-> Календарь Организация Подсистема Объект
-- Дисциплина ведения бухгалтерского учета	-> Календарь Организация Подсистема
-- Дисциплина ведения кадрового учета	->  Календарь Организация Подсистема
-- Сведения по кадрам	-> Календарь Организация
-- Статистика по полноте заполнения карточки сотрудника	-> Календарь Организация Показатель карточки
SELECT setval('cube_dimension_cube_dim_id_seq', 72, true);

INSERT INTO cube_dimension ( cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
  VALUES ( 11, 2, 'calendar_id', 1, true, 4, 2014);
INSERT INTO cube_dimension ( cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
  VALUES ( 11, 20, 'org_id', 1, true, 4, 2014);

INSERT INTO cube_dimension ( cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
  VALUES ( 12, 2, 'calendar_id', 1, true, 4, 2014);
INSERT INTO cube_dimension ( cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
  VALUES ( 12, 20, 'org_id', 1, true, 4, 2014);
INSERT INTO cube_dimension ( cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
  VALUES ( 12, 21, 'subsystem_id', 1, true, 4, 2014);

INSERT INTO cube_dimension ( cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
  VALUES ( 13, 2, 'calendar_id', 1, true, 4, 2014);
INSERT INTO cube_dimension ( cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
  VALUES ( 13, 20, 'org_id', 1, true, 4, 2014);
INSERT INTO cube_dimension ( cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
  VALUES ( 13, 21, 'subsystem_id', 1, true, 4, 2014);
INSERT INTO cube_dimension ( cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
  VALUES ( 13, 22, 'object_id', 1, true, 4, 2014);

INSERT INTO cube_dimension ( cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
  VALUES ( 14, 2, 'calendar_id', 1, true, 4, 2014);
INSERT INTO cube_dimension ( cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
  VALUES ( 14, 20, 'org_id', 1, true, 4, 2014);
INSERT INTO cube_dimension ( cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
  VALUES ( 14, 21, 'subsystem_id', 1, true, 4, 2014);

INSERT INTO cube_dimension ( cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
  VALUES ( 15, 2, 'calendar_id', 1, true, 4, 2014);
INSERT INTO cube_dimension ( cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
  VALUES ( 15, 20, 'org_id', 1, true, 4, 2014);

INSERT INTO cube_dimension ( cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
  VALUES ( 16, 2, 'calendar_id', 1, true, 4, 2014);
INSERT INTO cube_dimension ( cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
  VALUES ( 16, 20, 'org_id', 1, true, 4, 2014);

INSERT INTO cube_dimension ( cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
  VALUES ( 17, 2, 'calendar_id', 1, true, 4, 2014);
INSERT INTO cube_dimension ( cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
  VALUES ( 17, 20, 'org_id', 1, true, 4, 2014);
INSERT INTO cube_dimension ( cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
  VALUES ( 17, 23, 'cart_value_id', 1, true, 4, 2014);

-- old measures
-- INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (57, 4, 8, 744, 'Доля исполненной стоимости ВМП', NULL, 'rate_fact_sum', NULL, 7, 2, 'true', '#,#0.00', '2017-08-01', 41.00000, 'IIF(IsEmpty([Measures].[measure_55]),NULL,IIF([Measures].[measure_55]>0,[Measures].[measure_42]*100/[Measures].[measure_55],0))');
-- INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (21, 4, 3, 10001, 'Средняя длительность лечения', 'avg_day', 'avg_day', NULL, 5, 0, 'true', '#,#0.00', '2017-08-01', 41.00000, NULL);
-- INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (41, 4, 7, 10000, 'Исполненные объемы ВМП с начала года', '', 'fact_vol_total', NULL, 8, 0, 'true', '#,#0.00', '2017-08-01', 41.00000, NULL);
-- INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (42, 4, 7, 383, 'Исполненная стоимость ВМП с начала года', NULL, 'fact_sum_total', NULL, 9, 2, 'true', '#,#0.00', '2017-08-01', 41.00000, NULL);
-- INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (43, 4, 8, 383, 'Средняя стоимость одной госпитализации с начала года', NULL, 'avg_sum_total', NULL, 10, 2, 'true', '#,#0.00', '2017-08-01', 41.00000, 'IIF(IsEmpty([Measures].[measure_41]),NULL,IIF([Measures].[measure_41]>0,[Measures].[measure_42]/[Measures].[measure_41],0))');
-- INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (44, 4, 7, 10001, 'Общая длительность лечения с начала года', NULL, 'fact_day_total', NULL, 11, 0, 'true', '#,#0.00', '2017-08-01', 41.00000, NULL);
-- INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (45, 4, 8, 10001, 'Средняя длительность лечения с начала года', NULL, 'avg_day_total', NULL, 12, 0, 'true', '#,#0.00', '2017-08-01', 41.00000, 'IIF(IsEmpty([Measures].[measure_41]),NULL,IIF([Measures].[measure_41]>0,[Measures].[measure_44]/[Measures].[measure_41],0))');
-- INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (18, 4, 1, 383, 'Исполненная стоимость ВМП', 'fact_sum', 'fact_sum', NULL, 2, 2, 'true', '#,#0.00', '2017-08-01', 41.00000, NULL);
-- INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (19, 4, 3, 383, 'Средняя стоимость одной госпитализации', 'avg_sum', 'avg_sum', NULL, 3, 2, 'true', '#,#0.00', '2017-08-01', 41.00000, NULL);
-- INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (56, 4, 8, 744, 'Доля исполненных объемов ВМП', NULL, 'rate_fact_vol', NULL, 6, 2, 'true', '#,#0.00', '2017-08-01', 41.00000, 'IIF(IsEmpty([Measures].[measure_54]),NULL,IIF([Measures].[measure_54]>0,[Measures].[measure_41]*100/[Measures].[measure_54],0))');
-- INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (17, 4, 1, 10000, 'Исполненные объемы ВМП', 'fact_vol', 'fact_vol', NULL, 1, 0, 'true', '#,#0.00', '2017-08-01', 41.00000, NULL);
-- INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (20, 4, 1, 10001, 'Общая длительность лечения', 'fact_day', 'fact_day', NULL, 4, 0, 'true', '#,#0.00', '2017-08-01', 41.00000, NULL);
-- INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (78, 5, 8, 383, 'Утвержденная стоимость одной госпитализации', 'plan_day', 'plan_day', NULL, 3, 2, 'true', '#,#0.00', '2017-01-01', 41.00000, 'IIF(IsEmpty([Measures].[measure_22]),NULL,IIF([Measures].[measure_22]>0,[Measures].[measure_23]/[Measures].[measure_22],0))');
-- INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (54, 5, 7, 10000, 'Утвержденные объемы ВМП с начала года', 'plan_vol_total', 'plan_vol_total', NULL, 3, 0, 'false', '#,#0.00', NULL, 41.00000, NULL);
-- INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (22, 5, 1, 10000, 'Утвержденные объемы ВМП', 'plan_vol', 'plan_vol', NULL, 1, 0, 'true', '#,#0.00', '2017-01-01', 41.00000, NULL);
-- INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (23, 5, 1, 383, 'Утвержденная стоимость ВМП', 'plan_sum', 'plan_sum', NULL, 2, 2, 'true', '#,#0.00', '2017-01-01', 41.00000, NULL);
-- INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (55, 5, 7, 383, 'Утвержденная стоимость ВМП с начала года', 'plan_sum_total', 'plan_sum_total', NULL, 4, 2, 'false', '#,#0.00', NULL, 41.00000, NULL);
-- INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (59, 10, 8, 383, 'Разница между утвержденной и расчетной стоимостью', NULL, 'diff_sum', NULL, 2, 2, 'true', '#,#0.00', '2017-01-01', 41.00000, '[Measures].[measure_78]-[Measures].[measure_58]');
-- INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (58, 10, 1, 383, 'Расчетная стоимость одной госпитализаций', 'calc_sum', 'calc_sum', NULL, 1, 2, 'true', '#,#0.00', '2017-01-01', 41.00000, '');

--new measures

-- 11 для куба "Сведения о подключении пользователей организации "
--   Количество зарегистрированных пользователей всего
--   Количество зарегистрированных пользователей
-- 12 для куба "Сведения об активности пользователей"
--   Количество входов
--   Время работы в системе
--   Количество прошедших дней от последней операции в Системе
-- 13 для куба "Статистика по объектам Системы"
--   Количество созданных объектов
--   Количество измененных объектов
--   Количество выполненных отчетов
-- 14 для куба "Дисциплина ведения бухгалтерского учета"
--   Количество операций
--   Количество операций созданных вручную
--   Процент операций созданных вручную
--   Количество исправительных операций
--   Процент исправительных операций
--   Количество закрытых отчетных периодов
--   Сумма остатков на лицевых счетах на начало года
-- куб 15 "Дисциплина ведения кадрового учета"
-- для куба 17 "Статистика по полноте заполнения карточки сотрудника"
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (17, 4, 1, 10000, 'Исполненные объемы ВМП', 'fact_vol', 'fact_vol', NULL, 1, 0, 'true', '#,#0.00', '2017-08-01', 41.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (18, 4, 1, 383, 'Исполненная стоимость ВМП', 'fact_sum', 'fact_sum', NULL, 2, 2, 'true', '#,#0.00', '2017-08-01', 41.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (19, 4, 3, 383, 'Средняя стоимость одной госпитализации', 'avg_sum', 'avg_sum', NULL, 3, 2, 'true', '#,#0.00', '2017-08-01', 41.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (20, 4, 1, 10001, 'Общая длительность лечения', 'fact_day', 'fact_day', NULL, 4, 0, 'true', '#,#0.00', '2017-08-01', 41.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (21, 4, 3, 10001, 'Средняя длительность лечения', 'avg_day', 'avg_day', NULL, 5, 0, 'true', '#,#0.00', '2017-08-01', 41.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (22, 5, 1, 10000, 'Утвержденные объемы ВМП', 'plan_vol', 'plan_vol', NULL, 1, 0, 'true', '#,#0.00', '2017-01-01', 41.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (23, 5, 1, 383, 'Утвержденная стоимость ВМП', 'plan_sum', 'plan_sum', NULL, 2, 2, 'true', '#,#0.00', '2017-01-01', 41.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (41, 4, 7, 10000, 'Исполненные объемы ВМП с начала года', '', 'fact_vol_total', NULL, 8, 0, 'true', '#,#0.00', '2017-08-01', 41.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (42, 4, 7, 383, 'Исполненная стоимость ВМП с начала года', NULL, 'fact_sum_total', NULL, 9, 2, 'true', '#,#0.00', '2017-08-01', 41.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (43, 4, 8, 383, 'Средняя стоимость одной госпитализации с начала года', NULL, 'avg_sum_total', NULL, 10, 2, 'true', '#,#0.00', '2017-08-01', 41.00000, 'IIF(IsEmpty([Measures].[measure_41]),NULL,IIF([Measures].[measure_41]>0,[Measures].[measure_42]/[Measures].[measure_41],0))');
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (44, 4, 7, 10001, 'Общая длительность лечения с начала года', NULL, 'fact_day_total', NULL, 11, 0, 'true', '#,#0.00', '2017-08-01', 41.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (45, 4, 8, 10001, 'Средняя длительность лечения с начала года', NULL, 'avg_day_total', NULL, 12, 0, 'true', '#,#0.00', '2017-08-01', 41.00000, 'IIF(IsEmpty([Measures].[measure_41]),NULL,IIF([Measures].[measure_41]>0,[Measures].[measure_44]/[Measures].[measure_41],0))');
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (54, 5, 7, 10000, 'Утвержденные объемы ВМП с начала года', 'plan_vol_total', 'plan_vol_total', NULL, 3, 0, 'false', '#,#0.00', NULL, 41.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (55, 5, 7, 383, 'Утвержденная стоимость ВМП с начала года', 'plan_sum_total', 'plan_sum_total', NULL, 4, 2, 'false', '#,#0.00', NULL, 41.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (56, 4, 8, 744, 'Доля исполненных объемов ВМП', NULL, 'rate_fact_vol', NULL, 6, 2, 'true', '#,#0.00', '2017-08-01', 41.00000, 'IIF(IsEmpty([Measures].[measure_54]),NULL,IIF([Measures].[measure_54]>0,[Measures].[measure_41]*100/[Measures].[measure_54],0))');
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (57, 4, 8, 744, 'Доля исполненной стоимости ВМП', NULL, 'rate_fact_sum', NULL, 7, 2, 'true', '#,#0.00', '2017-08-01', 41.00000, 'IIF(IsEmpty([Measures].[measure_55]),NULL,IIF([Measures].[measure_55]>0,[Measures].[measure_42]*100/[Measures].[measure_55],0))');
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (58, 10, 1, 383, 'Расчетная стоимость одной госпитализаций', 'calc_sum', 'calc_sum', NULL, 1, 2, 'true', '#,#0.00', '2017-01-01', 41.00000, '');
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (59, 10, 8, 383, 'Разница между утвержденной и расчетной стоимостью', NULL, 'diff_sum', NULL, 2, 2, 'true', '#,#0.00', '2017-01-01', 41.00000, '[Measures].[measure_78]-[Measures].[measure_58]');
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (78, 5, 8, 383, 'Утвержденная стоимость одной госпитализации', 'plan_day', 'plan_day', NULL, 3, 2, 'true', '#,#0.00', '2017-01-01', 41.00000, 'IIF(IsEmpty([Measures].[measure_22]),NULL,IIF([Measures].[measure_22]>0,[Measures].[measure_23]/[Measures].[measure_22],0))');
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (100, 14, 1, 642, 'Количество операций', 'operation_count', 'operation_count', NULL, 1, 0, 'true', '#,#0.00', '2018-06-20', 86520.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (101, 14, 1, 642, 'Количество операций созданных вручную', 'hand_operation_count', 'hand_operation_count', NULL, 1, 0, 'true', '#,#0.00', '2018-06-20', 1511.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (102, 14, 8, 744, 'Процент операций созданных вручную', 'hand_operation_percent', 'hand_operation_percent', NULL, 1, 2, 'true', '#,#0.00', '2018-06-20', 1.74642, 'IIF(IsEmpty([Measures].[measure_100]) OR [Measures].[measure_100]=0, NULL, [Measures].[measure_101]*100/[Measures].[measure_100])');
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (103, 14, 1, 642, 'Количество исправительных операций', 'correction_count', 'correction_count', NULL, 1, 0, 'true', '#,#0.00', '2018-06-20', 179.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (104, 14, 8, 744, 'Процент исправительных операций', 'correction_percent', 'correction_percent', NULL, 1, 2, 'true', '#,#0.00', '2018-06-20', 0.20689, 'IIF(IsEmpty([Measures].[measure_100]) OR [Measures].[measure_100]=0, NULL, [Measures].[measure_103]*100/[Measures].[measure_100])');
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (105, 14, 4, 642, 'Количество закрытых отчетных периодов', 'closed_periods_count', 'closed_periods_count', NULL, 1, 0, 'true', '#,#0.00', '2018-06-20', 18.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (106, 14, 1, 383, 'Сумма остатков на лицевых счетах на начало года', 'account_balance', 'account_balance', NULL, 1, 2, 'true', '#,#0.00', '2018-06-20', 6654804162.82000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (107, 11, 1, 792, 'Количество зарегистрированных пользователей всего', 'users_count_total', 'users_count_total', NULL, 1, 0, 'true', '#,#0.00', '2018-06-20', 24.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (108, 11, 1, 792, 'Количество зарегистрированных пользователей', 'new_users_count', 'new_users_count', NULL, 1, 0, 'true', '#,#0.00', '2018-06-20', 1.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (109, 12, 1, 642, 'Количество входов', 'login_count', 'login_count', NULL, 1, 0, 'true', '#,#0.00', '2018-06-20', 0.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (110, 12, 1, 356, 'Время работы в системе', 'online_time', 'online_time', NULL, 1, 0, 'true', '#,#0.00', '2018-06-20', 6.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (111, 12, 5, 642, 'Количество прошедших дней от последней операции в Системе', 'days_from_last_activity', 'days_from_last_activity', NULL, 1, 0, 'true', '#,#0.00', '2018-06-20', 144.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (112, 13, 1, 642, 'Количество созданных объектов', 'objects_created', 'objects_created', NULL, 1, 0, 'true', '#,#0.00', '2018-06-20', 17.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (113, 13, 1, 642, 'Количество измененных объектов', 'objects_updated', 'objects_updated', NULL, 1, 0, 'true', '#,#0.00', '2018-06-20', 89.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (114, 13, 1, 642, 'Количество выполненных отчетов', 'reports_generated', 'reports_generated', NULL, 1, 0, 'true', '#,#0.00', '2018-06-20', 0.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (121, 16, 1, 642, 'Плановое количество штатных ставок', 'total_full_time_positions', 'total_full_time_positions', NULL, 1, 0, 'true', '#,#0.00', '2018-06-20', 11213.37000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (122, 16, 1, 642, 'Фактическое количество штатных ставок', 'rate_sum', 'rate_sum', NULL, 1, 0, 'true', '#,#0.00', '2018-06-20', 10835.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (123, 16, 8, 744, 'Доля наполненности штатного расписания', 'staff_fill_percent', 'staff_fill_percent', NULL, 1, 0, 'true', '#,#0.00', '2018-06-20', 96.62572, 'IIF([Measures].[measure_121]=0, 0, [Measures].[measure_122]*100/[Measures].[measure_121])');
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (124, 16, 1, 792, 'Количество штатных сотрудников', 'has_position_count', 'has_position_count', NULL, 1, 0, 'true', '#,#0.00', '2018-06-20', 16807.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (125, 16, 1, 792, 'Количество сотрудников всего', 'employee_count', 'employee_count', NULL, 1, 0, 'true', '#,#0.00', '2018-06-20', 18388.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (127, 17, 8, 744, 'Доля заполненных показателей в карточке штатного сотрудника', 'optional_percent', 'optional_percent', NULL, 1, 0, 'true', '#,#0.00', '2018-06-20', 100.00000, 'IIF(IsEmpty([Measures].[measure_124]) OR [Measures].[measure_124]=0, 0, [Measures].[measure_133]*100/[Measures].[measure_124])');
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (128, 17, 2, 642, 'Количество заполненных показателей в карточке сотрудников', 'cart_value_filled', 'cart_value_filled', NULL, 1, 0, 'true', '#,#0.00', '2018-06-20', 18389.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (129, 17, 8, 744, 'Доля заполненных показателей в карточке сотрудников', 'mandatory_percent', 'mandatory_percent', NULL, 1, 0, 'true', '#,#0.00', '2018-06-20', 100.00000, 'IIF(IsEmpty([Measures].[measure_125]) OR [Measures].[measure_125]=0, 0, [Measures].[measure_132]*100/[Measures].[measure_125])');
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (130, 17, 2, 792, 'Количество сотрудников', 'employee_guid', 'employee_guid', NULL, 1, 0, 'false', '#,#0.00', '2018-06-20', 18389.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (131, 17, 2, 792, 'Количество штатных сотрудников ', 'staff_guid', 'staff_guid', NULL, 1, 0, 'false', '#,#0.00', '2018-06-20', 16816.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (132, 17, 2, 642, 'Заполнено сотрудником', 'has_mandatory_5', 'has_mandatory_5', NULL, 1, 0, 'true', '#,#0.00', '2018-06-20', 18389.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (133, 17, 2, 642, 'Заполнено штатным сотрудником', 'has_mandatory_8', 'has_mandatory_8', NULL, 1, 0, 'true', '#,#0.00', '2018-06-20', 16816.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (135, 16, 8, 744, 'Доля занятых штатных единиц', 'occupied_percent', 'occupied_percent', NULL, 1, 0, 'true', '#,#0.00', '2018-06-20', 308.67887, 'IIF([Measures].[measure_137]=0, 0, [Measures].[measure_136]*100/[Measures].[measure_137])');
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (136, 16, 1, 642, 'Фактическое количество штатных единиц', 'position_guid_count', 'position_guid_count', NULL, 1, 0, 'true', '#,#0.00', '2018-06-20', 4948.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (137, 16, 1, 642, 'Плановое количество штатных единиц', 'total_staff_positions', 'total_staff_positions', NULL, 1, 0, 'true', '#,#0.00', '2018-06-20', 5957.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (140, 15, 2, 792, 'Количество штатных сотрудников, по которым оформлен кадровый документ', 'has_staff_doc', 'has_staff_doc', NULL, 1, 0, 'true', '#,#0.00', '2018-06-20', 7667.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (141, 15, 8, 744, 'Доля штатных сотрудников, по которым оформлен кадровый документ', 'staff_doc_percent', 'staff_doc_percent', NULL, 1, 0, 'true', '#,#0.00', '2018-06-20', 100.00000, 'IIF([Measures].[measure_124]=0, 0, [Measures].[measure_142]*100/[Measures].[measure_124])');
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (142, 15, 2, 792, 'Количество сотрудников, по которым выполнено 2 начисления ЗП', 'two_accrues_count', 'two_accrues_count', NULL, 1, 0, 'true', '#,#0.00', '2018-06-20', 1621.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (143, 15, 8, 744, 'Доля штатных сотрудников, по которым выполнено 2 начисления ЗП', 'two_accrues_percent', 'two_accrues_percent', NULL, 1, 0, 'true', '#,#0.00', '2018-06-20', 19.54661, 'IIF([Measures].[measure_124]=0, 0, [Measures].[measure_142]*100/[Measures].[measure_124])');
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (144, 15, 2, 792, 'Количество сотрудников, по которым выполнено 2 выплаты ЗП', 'two_pays_count', 'two_pays_count', NULL, 1, 0, 'true', '#,#0.00', '2018-06-20', 1228.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (145, 15, 8, 744, 'Доля сотрудников, по которым выполнено 2 выплаты ЗП', 'two_pays_percent', 'two_pays_percent', NULL, 1, 0, 'true', '#,#0.00', '2018-06-20', 0.00000, 'IIF([Measures].[measure_125]=0, 0, [Measures].[measure_144]*100/[Measures].[measure_125])');
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text) VALUES (146, 15, 2, 792, 'Количество сотрудников ', 'employee_guid', 'employee_guid', NULL, 1, 0, 'false', '#,#0.00', '2018-06-20', 8293.00000, NULL);


--cube_measure_link 3 id:  id измерения с формулой - какое измерение - от какого зависит
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (1, 41, 17);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (2, 42, 18);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (3, 43, 41);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (4, 43, 42);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (5, 44, 20);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (6, 45, 44);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (7, 45, 41);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (8, 54, 22);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (9, 55, 23);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (10, 56, 41);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (11, 56, 54);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (12, 57, 42);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (13, 57, 55);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (14, 59, 78);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (15, 59, 58);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (16, 78, 22);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (17, 78, 23);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (18, 143, 124);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (19, 141, 124);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (22, 145, 125);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (23, 127, 133);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (24, 129, 132);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (25, 102, 100);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (26, 102, 101);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (27, 104, 103);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (28, 104, 100);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (29, 143, 142);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (30, 135, 136);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (31, 135, 137);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (32, 123, 121);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (33, 123, 122);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (34, 141, 140);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (35, 145, 144);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (20, 127, 124);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (21, 129, 125);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (37, 154, 191);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (36, 154, 150);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (38, 155, 191);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (39, 155, 150);



-- запросы для заполнения Totals для куба Дисциплина ведения учета
UPDATE cube_measure
SET last_value = (SELECT sum(correction_count)
                  FROM fact_cube_buh)
WHERE m_key = 'correction_count';

UPDATE cube_measure
SET last_value = (SELECT sum(operation_count)
                  FROM fact_cube_buh)
WHERE m_key = 'operation_count';

UPDATE cube_measure
SET last_value = (SELECT sum(hand_operation_count)
                  FROM fact_cube_buh)
WHERE m_key = 'hand_operation_count';

UPDATE cube_measure
SET last_value = (SELECT sum(closed_periods_count)
                  FROM fact_cube_buh)
WHERE m_key = 'closed_periods_count';

UPDATE cube_measure
SET last_value = (SELECT sum(account_balance)
                  FROM fact_cube_buh)
WHERE m_key = 'account_balance';

UPDATE cube_measure
  SET last_value = (SELECT percent((SELECT sum(hand_operation_count) FROM fact_cube_buh), (SELECT sum(operation_count) FROM fact_cube_buh)))
WHERE m_key = 'hand_operation_count';

UPDATE cube_measure
SET last_value = (SELECT percent((SELECT sum(correction_count) FROM fact_cube_buh), (SELECT sum(operation_count) FROM fact_cube_buh)))
WHERE m_key = 'correction_percent';


-- куб id=15
UPDATE cube_measure
SET last_value = (SELECT count(DISTINCT (employee_guid))
                  FROM fact_staff_stat)
WHERE m_key = 'employee_guid';
--
UPDATE cube_measure
SET last_value = (SELECT count(DISTINCT (has_staff_doc))
                  FROM fact_staff_stat)
WHERE m_key = 'has_staff_doc';
--
UPDATE cube_measure
SET last_value = (SELECT count(DISTINCT (two_pays_count))
                  FROM fact_staff_stat)
WHERE m_key = 'two_pays_count';
--
UPDATE cube_measure
SET last_value = (SELECT count(DISTINCT (two_accrues_count))
                  FROM fact_staff_stat)
WHERE m_key = 'two_accrues_count';
--
UPDATE cube_measure
SET last_value = (SELECT percent(last_value, (SELECT count(DISTINCT (guid_with_position))
                                              FROM fact_staff_stat))
                  FROM cube_measure
                  WHERE m_key = 'has_staff_doc')
WHERE m_key = 'staff_doc_percent';
--
UPDATE cube_measure
SET last_value = (SELECT percent((SELECT count(DISTINCT (two_accrues_count))
                                  FROM fact_staff_stat), (SELECT count(DISTINCT (employee_guid))
                                                          FROM fact_staff_stat)))
WHERE m_key = 'two_accrues_percent';
--
UPDATE cube_measure
SET last_value = (SELECT percent((SELECT count(DISTINCT (two_pays_percent))
                                  FROM fact_staff_stat), (SELECT count(DISTINCT (employee_guid))
                                                          FROM fact_staff_stat)))
WHERE m_key = 'two_pays_percent';

-- для проверки анализа по кубам
-- куб fact_staff_details
-- select
--   sum(total_staff_positions) as "Плановое количество штатных единиц",
--   sum(total_full_time_positions) as"Плановое количество штатных ставок",
--   sum(position_guid_count) as "фактическое кол-во штатных единиц",
--   sum(rate_sum) as "Фактическое количество штатных ставок",
--   sum(has_position_count) as "кол-во штатных сотрудников",
--   sum(employee_count) as "кол-во сотрудников всего",
--   (select percent((select sum(employee_count) from fact_staff_details), (select sum(total_staff_positions) from fact_staff_details))) "Доля занятых штатных единиц", -- occupied_percent -- непустые Guid *100/totalStaffPositions
--   (select percent((select sum(rate_sum) from fact_staff_details), (select sum(total_full_time_positions) from fact_staff_details))) "Доля наполненности штатного расписания" -- staff_fill_percent -- rate * 100 / totalFullTimeOisitions
-- FROM fact_staff_details
-- WHERE calendar_id IN (SELECT max(calendar_id)
--                       FROM fact_staff_details);
--
UPDATE cube_measure
SET last_value = (SELECT sum(rate_sum)
                  FROM fact_staff_details
                  WHERE calendar_id IN (SELECT max(calendar_id)
                                        FROM fact_staff_details))
WHERE m_key = 'rate_sum';
--
UPDATE cube_measure
SET last_value = (SELECT sum(has_position_count)
                  FROM fact_staff_details
                  WHERE calendar_id IN (SELECT max(calendar_id)
                                        FROM fact_staff_details))
WHERE m_key = 'has_position_count';

--
UPDATE cube_measure
SET last_value = (SELECT sum(employee_count)
                  FROM fact_staff_details
                  WHERE calendar_id IN (SELECT max(calendar_id)
                                        FROM fact_staff_details))
WHERE m_key = 'employee_count';

--
UPDATE cube_measure
SET last_value = (SELECT sum(position_guid_count)
                  FROM fact_staff_details
                  WHERE calendar_id IN (SELECT max(calendar_id)
                                        FROM fact_staff_details))
WHERE m_key = 'position_guid_count';

--
UPDATE cube_measure
SET last_value = (SELECT sum(total_staff_positions)
                  FROM fact_staff_details
                  WHERE calendar_id IN (SELECT max(calendar_id)
                                        FROM fact_staff_details))
WHERE m_key = 'total_staff_positions';

--
UPDATE cube_measure
SET last_value = (SELECT sum(total_full_time_positions)
                  FROM fact_staff_details
                  WHERE calendar_id IN (SELECT max(calendar_id)
                                        FROM fact_staff_details))
WHERE m_key = 'total_full_time_positions';

--
UPDATE cube_measure
SET last_value = (SELECT percent((SELECT sum(rate_sum)
                                  FROM fact_staff_details
                                  WHERE calendar_id IN (SELECT max(calendar_id)
                                                        FROM fact_staff_details)),
                                 (SELECT sum(total_full_time_positions)
                                  FROM fact_staff_details
                                  WHERE calendar_id IN (SELECT max(calendar_id)
                                                        FROM fact_staff_details))))
WHERE m_key = 'staff_fill_percent';

--
UPDATE cube_measure
SET last_value = (SELECT percent((SELECT sum(employee_count)
                                  FROM fact_staff_details
                                  WHERE calendar_id IN (SELECT max(calendar_id)
                                                        FROM fact_staff_details)), (SELECT sum(total_staff_positions)
                                                                                    FROM fact_staff_details
                                                                                    WHERE calendar_id IN
                                                                                          (SELECT max(calendar_id)
                                                                                           FROM fact_staff_details))))
WHERE m_key = 'occupied_percent';



-- куб "полнота заполнения карточки"
-- select percent((select count(DISTINCT(has_mandatory_8)) from fact_employee_cart), (select count(DISTINCT(staff_guid)) from fact_employee_cart)); --optional_percent has_mandatory_8 * 100 / staff_guid
-- select percent((select count(DISTINCT(has_mandatory_5)) from fact_employee_cart), (select count(DISTINCT(employee_guid)) from fact_employee_cart)); --mandatory_percent has_mandatory_8 * 100 / staff_guid
-- select count(DISTINCT(cart_value_filled)) from fact_employee_cart;
-- select count(DISTINCT(has_mandatory_8)) from fact_employee_cart;
-- select count(DISTINCT(has_mandatory_5)) from fact_employee_cart;
-- select count(DISTINCT(staff_guid)) from fact_employee_cart;
-- select count(DISTINCT(employee_guid)) from fact_employee_cart;
-- SELECT
--   percent((SELECT count(DISTINCT (has_mandatory_8))
--            FROM fact_employee_cart), (SELECT count(DISTINCT (staff_guid))
--                                       FROM fact_employee_cart)) staff_percent,
--   percent((SELECT count(DISTINCT (has_mandatory_5))
--            FROM fact_employee_cart), (SELECT count(DISTINCT (employee_guid))
--                                       FROM fact_employee_cart)) emp_percent,
--   count(DISTINCT (cart_value_filled))                           cart_value_filled,
--   count(DISTINCT (has_mandatory_8))                             has_mandatory_8,
--   count(DISTINCT (has_mandatory_5))                             has_mandatory_5,
--   count(DISTINCT (staff_guid))                                  staff_guid,
--   count(DISTINCT (employee_guid))                               employee_guid
-- FROM fact_employee_cart;

UPDATE cube_measure
SET last_value = (select count(DISTINCT(cart_value_filled)) from fact_employee_cart)
WHERE m_key = 'cart_value_filled';

UPDATE cube_measure
SET last_value = (select count(DISTINCT(has_mandatory_8)) from fact_employee_cart)
WHERE m_key = 'has_mandatory_8';

UPDATE cube_measure
SET last_value = (select count(DISTINCT(has_mandatory_5)) from fact_employee_cart)
WHERE m_key = 'has_mandatory_5';

UPDATE cube_measure
SET last_value = (select count(DISTINCT(staff_guid)) from fact_employee_cart)
WHERE m_key = 'staff_guid';

UPDATE cube_measure
SET last_value = (select count(DISTINCT(employee_guid)) from fact_employee_cart)
WHERE m_key = 'employee_guid';

UPDATE cube_measure
SET last_value = (select percent((select count(DISTINCT(has_mandatory_8)) from fact_employee_cart), (select count(DISTINCT(staff_guid)) from fact_employee_cart)))
WHERE m_key = 'optional_percent';

UPDATE cube_measure
SET last_value = (select percent((select count(DISTINCT(has_mandatory_5)) from fact_employee_cart), (select count(DISTINCT(employee_guid)) from fact_employee_cart)))
WHERE m_key = 'mandatory_percent';

update cube_measure set unit_id=642 where unit_id=796;
update cube_measure set m_name='Плановое количество штатных позиций' where id=137;
update cube_measure set m_name='Фактическое количество штатных позиций' where id=136;

--добавить новые dimension
INSERT INTO public.dimensions (dimension_id, dim_type_id, d_name, d_key, d_desc, field_id, field_name, field_parent_id, field_order, table_name, where_text)
VALUES  (24, 1, 'Подразделение', 'department_key', NULL, 'id', 'name', NULL, 'sort_order', 'public.spr_department', NULL);

INSERT INTO public.dimensions (dimension_id, dim_type_id, d_name, d_key, d_desc, field_id, field_name, field_parent_id, field_order, table_name, where_text)
VALUES (25, 1, 'Должность', 'position_key', NULL, 'id', 'name', NULL, 'sort_order', 'public.spr_position', NULL);

INSERT INTO public.dimensions (dimension_id, dim_type_id, d_name, d_key, d_desc, field_id, field_name, field_parent_id, field_order, table_name, where_text)
VALUES (26, 1, 'Вид начисления', 'accrual_key', NULL, 'id', 'name', NULL, 'sort_order', 'public.spr_accrual', NULL);

INSERT INTO public.dimensions (dimension_id, dim_type_id, d_name, d_key, d_desc, field_id, field_name, field_parent_id, field_order, table_name, where_text)
VALUES (27, 1, 'СНИЛС', 'snils', NULL, 'id', 'name', NULL, 'sort_order', 'public.employee', NULL);

--иерархия Должность-Категория
INSERT INTO public.dimension_level (level_id, dimension_id, level_name, level_order, field_key, field_value)
  VALUES (15, 25, 'Должность', 2, 'id', 'name');
INSERT INTO public.dimension_level (level_id, dimension_id, level_name, level_order, field_key, field_value)
  VALUES (16, 25, 'Категория должности', 1, 'position_group_id', 'position_group');

-- куб "Структура заработной платы" fact_salary
INSERT INTO public.cubes (cube_id, group_id, cube_name, host, database_name, schema_name, table_name, is_visible)
  VALUES (18, 1, 'Структура заработной платы', NULL, 'mirs', 'public', 'fact_salary', 'true');

INSERT INTO cube_dimension (cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
  VALUES (18, 24, 'depart_id', 1, TRUE, NULL, 2014);

INSERT INTO cube_dimension (cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
  VALUES (18, 25, 'position_id', 1, TRUE, NULL, 2014);

INSERT INTO cube_dimension (cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
  VALUES (18, 2, 'calendar_id', 1, TRUE, 4, 2014);

INSERT INTO cube_dimension (cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
  VALUES (18, 20, 'org_id', 1, TRUE, 4, 2014);

INSERT INTO cube_dimension (cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
  VALUES (18, 26, 'accrual_id', 1, TRUE, NULL, 2014);

INSERT INTO cube_dimension (cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
  VALUES (18, 27, 'snils', 1, TRUE, NULL, 2014);

INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text)
VALUES
  (150, 18, 1, 383, 'Сумма произведенных начислений по заработной плате', 'payed_salary', 'payed_salary', NULL, 1, 2,
        'true', '#,#0.00', now(), 0, NULL);

INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text)
VALUES (154, 18, 8, 383, 'Сумма среднемесячной заработной платы', 'month_salary', 'month_salary', NULL, 1, 2, 'true',
        '#,#0.00', now(), 0,
        'IIF(IsEmpty([Measures].[measure_191]) OR [Measures].[measure_191]=0, NULL, [Measures].[measure_150]/[Measures].[measure_191])');

INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text)
VALUES
  (155, 18, 8, 383, 'Отклонение от средней заработной платы по должности', 'salary_deviation', 'salary_deviation', NULL,
        1, 2, 'true', '#,#0.00', now(), 0,
   'IIF(IsEmpty([Measures].[measure_191]) OR [Measures].[measure_191]=0, NULL, [Measures].[measure_150]*100/[Measures].[measure_191])');

-- куб  "учет рабочего времени" fact_worktime
INSERT INTO public.cubes (cube_id, group_id, cube_name, host, database_name, schema_name, table_name, is_visible)
VALUES (19, 1, 'Сведения по учету рабочего времени', NULL, 'mirs', 'public', 'fact_worktime', 'true');

INSERT INTO cube_dimension (cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
VALUES (19, 24, 'depart_id', 1, TRUE, NULL, 2014);

INSERT INTO cube_dimension (cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
VALUES (19, 25, 'position_id', 1, TRUE, NULL, 2014);

INSERT INTO cube_dimension (cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
VALUES (19, 2, 'calendar_id', 1, TRUE, 5, 2014);

INSERT INTO cube_dimension (cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
VALUES (19, 20, 'org_id', 1, TRUE, 4, 2014);

INSERT INTO cube_dimension (cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
VALUES (19, 27, 'snils', 1, TRUE, NULL, 2014);

INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text)
VALUES
  (190, 19, 1, 792, 'Списочная численность', 'listing_count', 'listing_count', NULL, 1, 2, 'true', '#,#0.00', now(),
   0.00000, NULL);
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text)
VALUES (191, 19, 8, 792, 'Среднесписочная численность', 'listing_count_avg', 'listing_count_avg', NULL, 1, 2, 'true',
        '#,#0.00', now(), 0.00000, '[Measures].[measure_190]/30');
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text)
VALUES  (195, 19, 1, 356, 'Фактически отработанное время по табелю', 'work_time', 'work_time', NULL, 1, 2, 'true', '#,#0.00',   now(), 0.00000, NULL);


--новые измерения для "Сведения по кадрам" (16)
--Количество уволенных сотрудников
--Индекс текучести кадров
INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text)
VALUES  (147, 16, 1, 792, 'Количество уволенных сотрудников', 'faired_count', 'faired_count', NULL, 1, 2, 'true', '#,#0.00', now(), 0.00000, NULL);

INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text)
VALUES  (148, 16, 8, 744, 'Индекс текучести кадров', 'staff_flow', 'staff_flow', NULL, 1, 2, 'true', '#,#0.00', now(), 0.00000, 'IIF(IsEmpty([Measures].[measure_191]) OR [Measures].[measure_191]=0, 0, [Measures].[measure_147]*100/[Measures].[measure_191])');

INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text)
VALUES  (149, 16, 8, 642, 'Количество вакантных ставок', 'vacancy_count', 'vacancy_count', NULL, 1, 2, 'true', '#,#0.00', now(), 0.00000, 'IIF(IsEmpty([Measures].[measure_121]) OR [Measures].[measure_121]=0, 0, [Measures].[measure_121]-[Measures].[measure_191])');

INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (40, 148, 191);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (41, 148, 147);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (42, 149, 121);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (43, 149, 191);
INSERT INTO public.cube_measure_link (measure_link_id, measure_id, child_id) VALUES (44, 191, 190);

INSERT INTO cube_dimension (cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
VALUES (16, 24, 'department_id', 1, TRUE, NULL, 2014);

INSERT INTO cube_dimension (cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
VALUES (16, 25, 'position_id', 1, TRUE, NULL, 2014);


-- куб  "структура ЗП по должности"
INSERT INTO public.cubes (cube_id, group_id, cube_name, host, database_name, schema_name, table_name, is_visible)
VALUES (20, 1, 'Структура зароботной платы по должности', NULL, 'mirs', 'public', 'fact_position_salary', 'true');

INSERT INTO cube_dimension (cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
VALUES (20, 2, 'calendar_id', 1, TRUE, 4, 2014);

INSERT INTO cube_dimension (cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
VALUES (20, 25, 'position_id', 1, TRUE, NULL, 2014);

INSERT INTO cube_dimension (cube_id, dimension_id, d_column, d_order, is_visible, dt_level_id, value_min_year)
VALUES (20, 26, 'accrual_id', 1, TRUE, NULL, 2014);

INSERT INTO public.cube_measure (measure_id, cube_id, metod_id, unit_id, m_name, m_column, m_key, m_desc, m_order, bit_depth, is_visible, format_value, last_date, last_value, formula_text)
  VALUES  (200, 20, 3, 383, 'Средняя заработная плата по должности', 'salary', 'salary', NULL, 1, 2, 'true', '#,#0.00', now(), 0.00000, NULL);


INSERT INTO cube_measure_unit (unit_id, unit_group_id, unit_name, unit_name_short, unit_key)
VALUES (10002, 7, 'Операция', 'опер.', NULL);

INSERT INTO measure_decline (decline_id, unit_id, p1, p2, p3) VALUES (1, 10002, 'Операция', 'Операции', 'Операций');
INSERT INTO measure_decline (decline_id, unit_id, p1, p2, p3) VALUES (1, 792, 'Человек', 'Человека', 'Человек');
INSERT INTO measure_decline (decline_id, unit_id, p1, p2, p3) VALUES (1, 356, 'Час', 'Часа', 'Часов');
INSERT INTO measure_decline (decline_id, unit_id, p1, p2, p3) VALUES (1, 796, 'Штука', 'Штуки', 'Штук');