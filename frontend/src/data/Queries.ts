import {gql} from 'apollo-boost'

export const GET_CONTRACTS_NUMBER = gql`
    query GetContractsNumber {
        contractsNumber
    }
`

export const GET_CONTRACTS = gql`
    query GetContracts($page: Int!, $size: Int!) {
        contracts(page: $page, size: $size) {
            id
            number
            signDate
            insured {
                name
                surname
                patronymic
            }
            calcPremium
            dateFrom
            dateTo
        }
    }
`

export const GET_CONTRACT = gql`
    query GetContract($id: ID!) {
        contract(id: $id) {
            id
            number
            signDate
            insured {
                id
                name
                surname
                patronymic
                birthDate
                passportSeries
                passportNumber
            }
            realProperty {
                id
                address {
                    id
                    zipCode
                    country
                    subject
                    district
                    settlement
                    street
                    plot
                    building
                    housing
                    apartment
                }
                type
                year
                area
            }
            insuranceAmount
            dateFrom
            dateTo
            calcDate
            calcPremium
            comment
        }
    }
`

export const GET_REAL_PROPERTY_TYPES = gql`
    query GetRealPropertyTypes {
        realPropertyTypes
    }
`

export const CALC_INSURANCE_PREMIUM = gql`
    query CalcInsurancePremium(
        $insuranceAmount: Int!,
        $realPropertyType: String!,
        $year: Int!,
        $area: Int!,
        $dateFrom: String!,
        $dateTo: String!) {
        calcInsurancePremium(
            insuranceAmount: $insuranceAmount,
            realPropertyType: $realPropertyType,
            year: $year,
            area: $area,
            dateFrom: $dateFrom,
            dateTo: $dateTo
        )
    }
`

export const GET_PERSON = gql`
    query GetPerson($id: ID!) {
        person(id: $id) {
            surname
            name
            patronymic
            birthDate
            passportSeries
            passportNumber
        }
    }
`

export const SEARCH_PERSONS = gql`
    query SearchPersons($search: String!) {
        persons(search: $search) {
            id
            name
            surname
            patronymic
            birthDate
            passportSeries
            passportNumber
        }
    }
`

export const SAVE_PERSON = gql`
    mutation AddEditPerson($person: PersonInput!) {
        addEditPerson(person: $person)
    }
`

export const GET_COUNTRIES = gql`
    query GetCountries {
        countries {
            name
        }
    }
`

export const SAVE_CONTRACT = gql`
    mutation AddEditContract($contract: ContractInput!) {
        addEditContract(contract: $contract)
    }
`
