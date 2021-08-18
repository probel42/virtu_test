<script lang="ts">
    import ContractRow from './ContractRow.svelte';
    import Paginator from '../../Paginator.svelte';
    import Error from '../../Error.svelte';
    import DataLoader from '../../data/DataLoader';

    const title = 'Список договоров';
    let contractsNumberPromise = DataLoader.getContractsNumber();
    const perPage = 15;
    let currentPage = 1;
    $: contractsPromise = DataLoader.getContracts(currentPage, perPage);

    const addContract = () => window.location.href = '#/add'
    const setPage = (pageNumber) => currentPage = pageNumber;
</script>

<style>
    h3 {
        text-align: center;
        margin-top: 0;
    }

    #contracts {
        display: grid
    }

    #addContractButton {
        margin-top: 0.5em;
    }

    table {
        border: 2px solid #5a5a5a;
        cursor: default;
    }

    table, th, td {
        border-collapse: collapse;
    }

    table tr th {
        text-align: center;
        background-color: rgb(177, 177, 177);
        border: 1px solid #5a5a5a;
        padding-left: 5px;
        padding-right: 5px;
    }

    td.emptyRow {
        column-span: 5;
        text-align: center;
    }
</style>

{#await contractsPromise}
    Loading...
{:then data}
    <h3>{title}</h3>
    <div id="contracts">
        {#await contractsNumberPromise then data}
            <Paginator {setPage} {perPage} itemsNumber="{data.contractsNumber}"/>
        {/await}
        <table>
            <tr>
                <th>Номер</th>
                <th>Дата заключения</th>
                <th>Страхователь</th>
                <th>Премия</th>
                <th>Срок действия</th>
            </tr>
            {#if data.contracts.length === 0}
                <tr>
                    <td class="emptyRow" colspan="5">Договоры не найдены</td>
                </tr>
            {:else}
                {#each data.contracts as contract}
                    <ContractRow {contract}/>
                {/each}
            {/if}
        </table>
    </div>
    <div id="contractButtons">
        <button id="addContractButton" on:click={addContract}>Добавить договор</button>
    </div>
{:catch errorMessage}
    <Error {errorMessage}/>
{/await}
