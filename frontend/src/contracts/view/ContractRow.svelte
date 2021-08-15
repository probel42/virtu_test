<script lang="ts">
    import type {Contract} from "../../data/Types";

    export let contract: Contract;

    const openContract = () => window.location.href = `#/contract/${contract.id}`;
</script>

<style>
    tr {
        cursor: pointer;
    }
    tr td {
        border: 1px solid #5a5a5a;
        border-collapse: collapse;
        padding-left: 5px;
        padding-right: 5px;
    }

    tr:nth-child(odd) td {
        background-color: #D2E1FD;
    }

    tr:nth-child(even) td {
        background-color: #E8EFFF;
    }

    tr:hover td {
        background-color: #E0D3E3;
    }
</style>

<tr on:click={openContract}>
    <td>{contract.number}</td>
    <td>{contract.signDate}</td>
    <td>{contract.insured.surname} {contract.insured.name} {contract.insured.patronymic}</td>
    <td>{contract.calcPremium}</td>
    <td>{contract.dateFrom} - {contract.dateTo}</td>
</tr>
