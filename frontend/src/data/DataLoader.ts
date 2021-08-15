import type {CalcInsuranceVariables, Contract, Person} from "./Types";
import * as Queries from "./Queries";
import {query, ReadableQuery} from "svelte-apollo";

export default class DataLoader {

    public static getContractsNumber(): Promise<{ contractsNumber: number }> {
        return this.createPromise(query(Queries.GET_CONTRACTS_NUMBER));
    }

    public static getContracts(pageNumber: number, perPage: number): Promise<{ contracts: Contract[] }> {
        const opts = {variables: {page: pageNumber - 1, size: perPage}};
        return this.createPromise(query(Queries.GET_CONTRACTS, opts));
    }

    public static loadContract(id: String): Promise<{ contract: Contract }> {
        const opts = {variables: {id: id}};
        return this.createPromise(query(Queries.GET_CONTRACT, opts));
    }

    public static searchPerson(searchLine: String): Promise<{ persons: Person[] }> {
        if (searchLine === null || searchLine.length === 0) {
            return Promise.resolve({persons: []});
        }
        const opts = {variables: {search: searchLine}};
        return this.createPromise(query(Queries.SEARCH_PERSONS, opts));
    }

    public static getRealPropertyTypes(): Promise<{ realPropertyTypes: String[] }> {
        return this.createPromise(query(Queries.GET_REAL_PROPERTY_TYPES));
    }

    public static calcInsurancePremium(variables: CalcInsuranceVariables): Promise<{ calcInsurancePremium: number }> {
        const opts = {variables: variables};
        return this.createPromise(query(Queries.CALC_INSURANCE_PREMIUM, opts));
    }

    public static getCountries(): Promise<{ countries: [{ name: String }] }> {
        return this.createPromise(query(Queries.GET_COUNTRIES));
    }

    private static createPromise<T>(query: ReadableQuery<T>): Promise<T> {
        return new Promise<T>((resolve, reject) => {
            query.subscribe(result => {
                if (!result.loading) {
                    if (result.data)
                        resolve(result.data);
                    else if (result.error)
                        reject(result.error);
                }
            });
        });
    }
}
