<script lang="ts">
    import ContractRow from './ContractRow.svelte';
    import Paginator from "../../Paginator.svelte";
    import Error from "../../Error.svelte";
    import DataLoader from "../../data/DataLoader";

    let contractsNumberPromise = DataLoader.getContractsNumber();
    const perPage = 10;
    let currentPage = 1;
    const setPage = (pageNumber) => currentPage = pageNumber;

    $: contractsPromise = DataLoader.getContracts(currentPage, perPage);
</script>

<style>
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

{#await contractsNumberPromise then data}
    <Paginator {setPage} {perPage} itemsNumber="{data.contractsNumber}"
    />
{/await}
{#await contractsPromise then data}
    <table>
        <tr>
            <th>Номер</th>
            <th>Дата заключения</th>
            <th>Страхователь</th>
            <th>Премия</th>
            <th>Срок действия</th>
        </tr>
        {#if data.contracts.length === 0}
            <tr><td class="emptyRow" colspan="5">Договоры не найдены</td></tr>
        {:else}
            {#each data.contracts as contract}
                <ContractRow {contract}/>
            {/each}
        {/if}
    </table>
{:catch errorMessage}
    <Error {errorMessage}/>
{/await}
