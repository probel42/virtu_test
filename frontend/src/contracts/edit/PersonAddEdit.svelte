<script lang="ts">
    import type {Person} from "../../data/Types";
    import DataLoader from "../../data/DataLoader";
    import {NewPerson} from "../../data/DataTemplates";
    import {mutation} from "svelte-apollo";
    import {SAVE_PERSON} from "../../data/Queries";

    export let params = null;

    let person: Person;
    $: if (params?.id) {
        DataLoader.loadPerson(params?.id).then(data => person = data.person);
    } else {
        person = NewPerson;
    }

    const createOrUpdatePerson = mutation(SAVE_PERSON);
    const close = () => {};
    const savePerson = () => {
        const opts = {variables: {person: person}};
        createOrUpdatePerson(opts).then(close).catch((message) => alert(message));
    }
</script>

<style>
    table label {
        text-align: center;
        display: grid;
    }
    table label input {
        margin-bottom: 0;
    }
</style>

{#if person}
    <table>
        <tr>
            <td><label><input type="text" bind:value={person.surname}/>фамилия</label></td>
            <td><label><input type="text" bind:value={person.name}/>имя</label></td>
            <td><label><input type="text" bind:value={person.patronymic}/>отчество</label></td>
        </tr>
        <tr>
            <td><label><input type="date" bind:value={person.birthDate}/>дата рождения</label></td>
            <td><label><input type="text" bind:value={person.passportSeries}/>серия пасспорта</label></td>
            <td><label><input type="text" bind:value={person.passportNumber}/>номер пасспорта</label></td>
        </tr>
    </table>
    <button on:click={savePerson}>Сохранить</button>
    <button on:click={close}>Отмена</button>
{/if}
