<script lang="ts">
    import DataLoader from "../../data/DataLoader";
    import TitleFragment from "./fragments/TitleFragment.svelte";
    import CalculationFragment from "./fragments/CalculationFragment.svelte";
    import HeadInfoFragment from "./fragments/HeadInfoFragment.svelte";
    import InsuredFragment from "./fragments/InsuredFragment.svelte";
    import AddressFragment from "./fragments/AddressFragment.svelte";
    import CommentFragment from "./fragments/CommentFragment.svelte";
    import type {Contract} from "../../data/Types";
    import {mutation} from "svelte-apollo";
    import {SAVE_CONTRACT} from "../../data/Queries";
    import Converter from "../../data/Converter";

    export let params = null;

    let contract: Contract;
    $: if (params?.id) {
        DataLoader.loadContract(params?.id).then(data => contract = data.contract);
    } else {
        contract = {};
    }

    const createOrUpdateContract = mutation(SAVE_CONTRACT);

    const backToContracts = () => window.location.href = '';
    const saveContract = async () => {
        const opts = {variables: {contract: Converter.getContractInput(contract)}};
        createOrUpdateContract(opts).then(backToContracts);
    }
</script>

{#if contract}
    <TitleFragment {contract}/>
    <CalculationFragment {contract}/>
    <HeadInfoFragment {contract}/>
    <InsuredFragment {contract}/>
    <AddressFragment {contract}/>
    <CommentFragment {contract}/>
{/if}
<button on:click={saveContract}>Сохранить</button>
<button on:click={backToContracts}>К списку договоров</button>