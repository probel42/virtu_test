import type {Person} from './Types';

export const getPersonFullName = (person: Person) => {
    return person ? (person.surname + ' ' + person.name +
        (person.patronymic ? ' ' + person.patronymic : '')) : '';
}
