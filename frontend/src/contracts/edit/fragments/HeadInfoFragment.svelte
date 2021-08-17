<script lang="ts">
    import type {Contract} from '../../../data/Types';

    export let contract: Contract;
</script>

<style>
    table tr td {
        padding-left: 10px;
        padding-right: 10px;
    }
</style>

<div id="headInfoFragment">
    <table>
        <tr>
            <td>№ договора</td>
            <td><input type="text" bind:value={contract.number}/></td>
            <td>Дата заключения</td>
            <td><input type="date" bind:value={contract.signDate}></td>
        </tr>
    </table>
</div>
