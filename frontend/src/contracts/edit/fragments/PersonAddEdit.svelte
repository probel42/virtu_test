<script lang="ts">
    import type {Person} from '../../../data/Types';
    import {mutation} from 'svelte-apollo';
    import {SAVE_PERSON} from '../../../data/Queries';
    import {closeModal} from 'svelte-modals';
    import Converter from '../../../data/Converter';
    import {NewPerson} from '../../../data/DataTemplates';

    export let isOpen;
    export let person: Person;
    export let setPerson: (person: Person) => number;
    if (!person) {
        person = NewPerson;
    }

    const createOrUpdatePerson = mutation(SAVE_PERSON);
    const savePerson = () => {
        const opts = {variables: {person: Converter.getPersonInput(person)}};
        createOrUpdatePerson(opts).then(result => {
            person.id = result.data.addEditPerson;
            setPerson(person);
        }).then(closeModal).catch((message) => alert(message));
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

    .modal {
        position: fixed;
        top: 0;
        bottom: 0;
        right: 0;
        left: 0;
        display: flex;
        justify-content: center;
        align-items: center;
        pointer-events: none;
    }

    .contents {
        min-width: 240px;
        border-radius: 6px;
        padding: 16px;
        background: white;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        pointer-events: auto;
    }
</style>

{#if isOpen}
    <div role="dialog" class="modal">
        <div class="contents">
            <table>
                <tr>
                    <td><label><input type="text" bind:value={person.surname}/>фамилия</label></td>
                    <td><label><input type="text" bind:value={person.name}/>имя</label></td>
                    <td><label><input type="text" bind:value={person.patronymic}/>отчество</label></td>
                </tr>
                <tr>
                    <td><label><input type="date" bind:value={person.birthDate}/>дата рождения</label></td>
                    <td><label><input type="text" bind:value={person.passportSeries}/>серия пасспорта</label>
                    </td>
                    <td><label><input type="text" bind:value={person.passportNumber}/>номер пасспорта</label>
                    </td>
                </tr>
            </table>
            <button on:click={savePerson}>Сохранить</button>
            <button on:click={closeModal}>Отмена</button>
        </div>
    </div>
{/if}
