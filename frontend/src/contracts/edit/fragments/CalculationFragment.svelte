<script lang="ts">
    import type {Contract} from "../../../data/Types";
    import DataLoader from "../../../data/DataLoader";

    export let contract: Contract;

    let realPropertyTypesPromise = DataLoader.getRealPropertyTypes();

    $: realProperty = contract?.realProperty || {};
    $: calcVariables = {
        insuranceAmount: contract?.insuranceAmount,
        realPropertyType: contract?.realProperty?.type,
        year: contract?.realProperty?.year,
        area: contract?.realProperty?.area,
        dateFrom: contract?.dateFrom,
        dateTo: contract?.dateTo
    }
</script>

<style>
    #calculationFragment {
        border: 2px solid #5a5a5a;
    }
    table {
        width: 100%;
    }
    #calcButtonTd {
        text-align: center;
    }
    table input, table select {
        width: 100%;
    }
    table tr td {
        padding-left: 10px;
        padding-right: 10px;
    }
</style>

<div id="calculationFragment">
    <table>
        <tr>
            <td>Страховая сумма</td>
            <td><input type="text" bind:value={contract.insuranceAmount}/></td>
            <td>Срок действия с</td>
            <td><input type="date" bind:value={contract.dateFrom}></td>
        </tr>
        <tr>
            <td>Тип недвижимости</td>
            <td>
                <select>
                    {#await realPropertyTypesPromise then data}
                        {#each data.realPropertyTypes as realPropertyType}
                            <option value={realPropertyType}>
                                {realPropertyType}
                            </option>
                        {/each}
                    {/await}
                </select>
            </td>
            <td>Срок действия по</td>
            <td><input type="date" bind:value={contract.dateTo}></td>
        </tr>
        <tr>
            <td>Год постройки</td>
            <td><input type="text" bind:value={realProperty.year}/></td>
        </tr>
        <tr>
            <td>Площадь, кв.м</td>
            <td><input type="text" bind:value={realProperty.area}/></td>
        </tr>
        <tr>
            <td colspan="4" id="calcButtonTd">
                <button>Рассчитать</button>
            </td>
        </tr>
        <tr>
            <td>Дата расчёта</td>
            <td><input disabled type="date" bind:value={contract.calcDate}/></td>
            <td>Премия</td>
            <td>
                <input disabled type="text" bind:value={contract.calcPremium}>
            </td>
        </tr>
    </table>
</div>
