<script lang="ts">
    import type {Contract} from "../../../data/Types";
    import DataLoader from "../../../data/DataLoader";

    export let contract: Contract;

    let countriesPromise = DataLoader.getCountries();

    $: address = contract?.realProperty?.address || {};
</script>

<style>
    #addressFragment {
        margin-top: 20px;
    }
    table label {
        text-align: center;
        font-size: 0.8em;
    }
    table tr td input, table tr td select {
        width: 100%;
        margin-bottom: 0;
        padding-bottom: 0;
    }
</style>

<div id="addressFragment">
    <b>АДРЕС НЕДВИЖИМОСТИ</b>
    <table>
        <tr>
            <td>
                <label>
                    <select bind:value={address.country}>
                        {#await countriesPromise then data}
                            {#each data.countries as country}
                                <option value={country.name}>
                                    {country.name}
                                </option>
                            {/each}
                        {/await}
                    </select>
                    государство
                </label></td>
            <td><label><input type="text" bind:value={address.zipCode}/>индекс</label></td>
            <td><label><input type="text" bind:value={address.subject}/>республика, край, область</label></td>
            <td colspan="4"><label><input type="text" bind:value={address.district}/>район</label></td>
        </tr>
        <tr>
            <td><label><input type="text" bind:value={address.settlement}/>населённый пункт</label></td>
            <td colspan="2"><label><input type="text" bind:value={address.street}/>улица</label></td>
            <td><label><input type="text" bind:value={address.plot}/>дом</label></td>
            <td><label><input type="text" bind:value={address.building}/>корпус</label></td>
            <td><label><input type="text" bind:value={address.housing}/>строение</label></td>
            <td><label><input type="text" bind:value={address.apartment}/>квартира</label></td>
        </tr>
    </table>
</div>