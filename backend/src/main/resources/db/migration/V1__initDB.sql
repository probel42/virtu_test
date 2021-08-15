CREATE SCHEMA address_ref;
ALTER SCHEMA address_ref OWNER TO insurance_app;
COMMENT ON SCHEMA address_ref IS E'Справочная информация для адресов (служит для минимизации ошибок ввода, заполняется в процессе работы приложения, может быть частично инициализирована из данных ОКТМО)';

SET search_path TO pg_catalog,public,address_ref;

CREATE TABLE public.person (
                               id uuid NOT NULL,
                               name varchar(255) NOT NULL,
                               surname varchar(255) NOT NULL,
                               patronymic varchar(255),
                               birth_date date NOT NULL,
                               passport_series char(4) NOT NULL,
                               passport_number char(6) NOT NULL,
                               CONSTRAINT person_pk PRIMARY KEY (id),
                               CONSTRAINT passport_series_template CHECK (passport_series ~* '^\d{4}$'),
	CONSTRAINT passport_number_template CHECK (passport_number ~* '^\d{6}$')

);
COMMENT ON TABLE public.person IS E'Персона';
COMMENT ON COLUMN public.person.id IS E'Идентификатор';
COMMENT ON COLUMN public.person.name IS E'Имя';
COMMENT ON COLUMN public.person.surname IS E'Фамилия';
COMMENT ON COLUMN public.person.patronymic IS E'Отчество';
COMMENT ON COLUMN public.person.birth_date IS E'Дата рождения';
COMMENT ON COLUMN public.person.passport_series IS E'Серия пасспорта';
COMMENT ON COLUMN public.person.passport_number IS E'Номер пасспорта';
COMMENT ON CONSTRAINT passport_series_template ON public.person  IS E'Шаблон серии пасспорта';
COMMENT ON CONSTRAINT passport_number_template ON public.person  IS E'Шаблон номера пасспорта';
ALTER TABLE public.person OWNER TO insurance_app;

CREATE TABLE public.address (
                                id uuid NOT NULL,
                                zipcode varchar(10),
                                country varchar(255) NOT NULL,
                                subject varchar(255) NOT NULL,
                                district varchar(255),
                                settlement varchar(255) NOT NULL,
                                street varchar(255) NOT NULL,
                                plot varchar(5) NOT NULL,
                                building varchar(5),
                                housing varchar(5),
                                apartment varchar(5),
                                CONSTRAINT address_pk PRIMARY KEY (id)

);
COMMENT ON TABLE public.address IS E'Адрес';
COMMENT ON COLUMN public.address.id IS E'Идентификатор';
COMMENT ON COLUMN public.address.zipcode IS E'Индекс';
COMMENT ON COLUMN public.address.country IS E'Государство';
COMMENT ON COLUMN public.address.subject IS E'Республика, край, область';
COMMENT ON COLUMN public.address.district IS E'Район';
COMMENT ON COLUMN public.address.settlement IS E'Населённый пункт';
COMMENT ON COLUMN public.address.street IS E'Улица';
COMMENT ON COLUMN public.address.plot IS E'Дом';
COMMENT ON COLUMN public.address.building IS E'Корпус';
COMMENT ON COLUMN public.address.housing IS E'Строение';
COMMENT ON COLUMN public.address.apartment IS E'Квартира';
ALTER TABLE public.address OWNER TO insurance_app;

CREATE TABLE public.real_property (
                                      id uuid NOT NULL,
                                      address_id uuid NOT NULL,
                                      type varchar(50) NOT NULL,
                                      construction_year char(4) NOT NULL,
                                      area smallint NOT NULL,
                                      CONSTRAINT real_property_pk PRIMARY KEY (id)

);
COMMENT ON TABLE public.real_property IS E'Недвижимость';
COMMENT ON COLUMN public.real_property.id IS E'Идентификатор';
COMMENT ON COLUMN public.real_property.type IS E'Тип';
COMMENT ON COLUMN public.real_property.construction_year IS E'Год постройки';
COMMENT ON COLUMN public.real_property.area IS E'Площадь';
ALTER TABLE public.real_property OWNER TO insurance_app;

ALTER TABLE public.real_property ADD CONSTRAINT address_fk FOREIGN KEY (address_id)
    REFERENCES public.address (id) MATCH FULL
    ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE public.contract (
                                 id uuid NOT NULL,
                                 number integer NOT NULL,
                                 sign_date date NOT NULL,
                                 insured_id uuid NOT NULL,
                                 real_property_id uuid NOT NULL,
                                 insurance_amount integer NOT NULL,
                                 date_from date NOT NULL,
                                 date_to date NOT NULL,
                                 calc_date date NOT NULL,
                                 calc_premium real NOT NULL,
                                 comment text,
                                 CONSTRAINT contract_pk PRIMARY KEY (id)

);
COMMENT ON TABLE public.contract IS E'Договор';
COMMENT ON COLUMN public.contract.id IS E'Идентификатор';
COMMENT ON COLUMN public.contract.number IS E'Номер';
COMMENT ON COLUMN public.contract.sign_date IS E'Дата заключения';
COMMENT ON COLUMN public.contract.insurance_amount IS E'Страховая сумма';
COMMENT ON COLUMN public.contract.date_from IS E'Срок действия с';
COMMENT ON COLUMN public.contract.date_to IS E'Срок действия по';
COMMENT ON COLUMN public.contract.calc_date IS E'Дата расчёта';
COMMENT ON COLUMN public.contract.calc_premium IS E'Премия';
COMMENT ON COLUMN public.contract.comment IS E'Комментарий';
ALTER TABLE public.contract OWNER TO insurance_app;

ALTER TABLE public.contract ADD CONSTRAINT person_fk FOREIGN KEY (insured_id)
    REFERENCES public.person (id) MATCH FULL
    ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE public.contract ADD CONSTRAINT real_property_fk FOREIGN KEY (real_property_id)
    REFERENCES public.real_property (id) MATCH FULL
    ON DELETE CASCADE ON UPDATE CASCADE;


