import type {Contract, ContractInput} from "./Types";

export default class Converter {
    public static getContractInput(contract: Contract): ContractInput {
        return {
            id: contract.id,
            number: contract.number,
            signDate: contract.signDate,
            insuredId: contract.insured.id,
            realProperty: {
                address: {
                    zipCode: contract.realProperty.address.zipCode,
                    country: contract.realProperty.address.country,
                    subject: contract.realProperty.address.subject,
                    district: contract.realProperty.address.district,
                    settlement: contract.realProperty.address.settlement,
                    street: contract.realProperty.address.street,
                    plot: contract.realProperty.address.plot,
                    building: contract.realProperty.address.building,
                    housing: contract.realProperty.address.housing,
                    apartment: contract.realProperty.address.apartment
                },
                type: contract.realProperty.type,
                year: contract.realProperty.year,
                area: contract.realProperty.area
            },
            insuranceAmount: contract.insuranceAmount,
            dateFrom: contract.dateFrom,
            dateTo: contract.dateTo,
            calcDate: contract.calcDate,
            calcPremium: contract.calcPremium,
            comment: contract.comment
        };
    }
}
