# virtu_test

Тестовое задание для Virtu Systems

При реализации использовись некоторый технологии, которые изначально не были указаны в рекомендуемом стеке.
Но целью реализации как раз и являлось прежде всего ознакомление с наиболее часто встречающимися требованиями в вакансиях, поэтому перед реализация ознакомился с некоторыми из них, и что-то прицепил к проекту (то, что посчитал полезным). Например: GraphQL, Lucene, Mongo, ...
С частью используемых в проекте технологий я до этого не сталкивался, поэтому немного задержался.

========== ЗАПУСК ==========
Для запуска необходим Linux, Docker и Docker-compose. Все volumes и images создаются с префиксом ibelan_virtu_test, чтобы ни с чем не пересекаться
Запуск:
	./start.sh
Остановка:
	./stop.sh
Зачистка образов и томов (нужно раскомментировать зачистку образова для build-контейнеров, чтобы зачистить и их тоже)):
	./clean.sh

Фронт будет доступен по адресу http://localhost:5000
Бэк по адресу http://localhost:8080/backend
Порты можно кастомизировать в файле ".env", но в сборочном контейнере фронта (frontend/Dockerfile) нужно также проставить соответствующий ENV SERVER_URI (я не разобрался почему docker-compose не может прокинуть environment туда, странно)

========== СТЕК ==========
	Backend:
		Spring Boot 2
		Postgres 13
		MongoDB
		GraphQL
		Lucene
		Flyway
		Lombok
	Frontend:
		Svelte
		TypeScript
		Apollo Client
Всё завёрнуто в Docker

========== ОПИСАНИЕ ==========
Схему РБД можно просмотреть в файле RDB_SCHEMA.png. Сущности "Адрес" (address) и "Объект недвижимости" (real property) разделены, т.к. в типах объектов недвижимость есть тип "Комната" из чего следует, что на одном адресе может быть несколько объектов недвижимости.
Также нулябельность полей не соответствует указанной в ТЗ
В частности:
	1). квартира может быть не указана, если объект - дом
	2). а номер дома наоборот должен быть обязательным
Номера домов/квартир/... также могут быть не числовыми. Тут нужна аналитика, но мне кажется, что как минимум в номарах квартир могут быть буквы.
Т.к. страховая премия автогенерируется, то на клиенте она сделана нередактируемой. Она также проверяется повторно перед сохранением документа, на случай изменения коэффициентов.

Для автоподстановки адресов (а значит - для минимизации ошибок ввода) планировалось использовать MongoDB. Для этого сделана загрузка в Mongo инициализирующих данных со странами и регионами РФ. В процессе эти данные дозаполняются при добавлении договоров. Однако их использование на клиенте не реализовано. Думаю, в идеале нужно писать синхронизацию с каким-нибудь сервисом по выдаче актуальных данных ОКТМО (а они, похоже, все платные).

Всё взаимодействие между сервером и клиентом проходит через GraphQL, потому что он оказался удобнее.

Поиск и редактирование персон реализовано несколько иначе:
	1). поиск персоны объединён со страницей добавления/редактирование договора
	2). редактирование всех данных персоны доступно только в одном отдельном окне
Поиск персон ведётся на сервере с помощью Lucene, в которую на старте загоняются все персоны из РБД. Для проверки скорости Lucene создана миграция, которая загоняет в базу 100К персон. Миграцию запускает Flyway (см. таблицу db_version).

========== PS ==========
Многие мелочи ТЗ не реализованы (нет времени): валидация полей на клиенте, пробрасывание сообщений об ошибке с сервера на клиент, поиск в Lucene по подстроке и без учёта регистра, подстановка адреса с помощью Mongo, ...

Приложение создано для демонстрации, также оставляю за собой право использовать его по просьбе других компаний (в качестве примера кода).
