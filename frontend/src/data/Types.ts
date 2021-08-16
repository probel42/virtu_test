export type Address = {
    zipCode?: String;
    country?: String;
    subject?: String;
    district?: String;
    settlement?: String;
    street?: String;
    plot?: String;
    building?: String;
    housing?: String;
    apartment?: String;
}

export type RealProperty = {
    address?: Address;
    type?: String;
    year?: String;
    area?: number;
}

export type Person = {
    id?: String
    name?: String;
    surname?: String;
    patronymic?: String;
    birthDate?: String;
    passportSeries?: String;
    passportNumber?: String;
}

export type Contract = {
    id?: String
    number?: number;
    signDate?: String;
    insured?: Person;
    realProperty?: RealProperty;
    insuranceAmount?: number;
    dateFrom?: String;
    dateTo?: String;
    calcDate?: String;
    calcPremium?: number;
    comment?: String;
}

export type CalcInsuranceVariables = {
    insuranceAmount?: number;
    realPropertyType?: String;
    year?: String
    area?: number
    dateFrom?: String,
    dateTo?: String
}

export type ContractInput = {
    id?: String
    number: number;
    signDate: String;
    insuredId: String;
    realProperty: RealProperty;
    insuranceAmount: number;
    dateFrom: String;
    dateTo: String;
    calcDate: String;
    calcPremium: number;
    comment?: String;
}

export type PersonInput = {
    id?: String
    name: String;
    surname: String;
    patronymic: String;
    birthDate: String;
    passportSeries: String;
    passportNumber: String;
}
