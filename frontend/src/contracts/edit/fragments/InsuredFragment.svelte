<script lang="ts">
    import type {Contract, Person} from "../../../data/Types";
    import DataLoader from "../../../data/DataLoader";

    export let contract: Contract;

    let searchLine: String = '';
    let searchResult: Person[] = [];

    $: person = contract?.insured;
    $: isPersonSet = !!person?.id;
    $: DataLoader.searchPerson(searchLine).then((result) => searchResult = result.persons);

    const changePerson = () => contract.insured = null;

    const setPerson = (person: Person) => {
        contract.insured = person;
        searchLine = '';
        return 0;
    }

    const clearSearch = () => searchLine = '';
</script>

<style>
    #insuredFragment {
        display: grid;
        margin-top: 20px;
    }

    input {
        width: 100%;
        height: 40px;
        margin-bottom: 0;
    }

    table#searchDropList {
        position: absolute;
        z-index: auto;
        background-color: #ebebeb;
        border: 2px solid #0035d4;
        border-top: 0;
        border-collapse: collapse;
        width: 100%;
    }

    table#searchDropList tr {
        cursor: pointer;
    }

    table#searchDropList tr:hover {
        background-color: #E0D3E3;
    }

    table#searchDropList tr td {
        border: solid #5a5a5a;
        border-width: 0 0 1px;
    }

    div#searchDropListWrapper {
        position: relative;
    }

    table#selectedPerson {
        cursor: pointer;
        border: 2px solid #5a5a5a;
        height: 40px;
        padding: 0 0.4em 0 0.4em;
        margin: 0.3em 0 0.3em 0;
        border-spacing: 0;
    }
</style>

<div id="insuredFragment">
    <b>СТРАХОВАТЕЛЬ</b>
    {#if isPersonSet}
        <table id="selectedPerson" on:click={changePerson}>
            <tr>
                <td>{person.surname} {person.name} {person.patronymic}</td>
                <td>Дата рождения: {person.birthDate}</td>
                <td>Пасспорт: {person.passportSeries} {person.passportNumber}</td>
            </tr>
        </table>
    {:else}
        <input type="search"
               placeholder="Поиск персон в базе..."
               spellcheck="false"
               bind:value={searchLine}
        />
        {#if searchResult.length > 0}
            <div id="searchDropListWrapper">
                <table id="searchDropList">
                    {#each searchResult as person}
                        <tr on:click={setPerson(person)}>
                            <td>{person.surname} {person.name} {person.patronymic}</td>
                            <td>Дата рождения: {person.birthDate}</td>
                            <td>Пасспорт: {person.passportSeries} {person.passportNumber}</td>
                        </tr>
                    {/each}
                </table>
            </div>
        {/if}
    {/if}
</div>
