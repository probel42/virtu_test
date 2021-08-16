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
    import {NewContract} from "../../data/DataTemplates";
    import {closeModal, Modals} from 'svelte-modals';

    export let params = null;

    let contract: Contract;
    $: if (params?.id) {
        DataLoader.loadContract(params?.id).then(data => contract = data.contract);
    } else {
        contract = NewContract;
    }

    const createOrUpdateContract = mutation(SAVE_CONTRACT);
    const backToContracts = () => window.location.href = '';
    const saveContract = () => {
        const opts = {variables: {contract: Converter.getContractInput(contract)}};
        createOrUpdateContract(opts).then(backToContracts).catch((message) => alert(message));
    }
</script>

<style>
    .backdrop {
        position: fixed;
        top: 0;
        bottom: 0;
        right: 0;
        left: 0;
        background: rgba(0, 0, 0, 0.50)
    }
</style>

<Modals>
    <div slot="backdrop" class="backdrop" on:click={closeModal}></div>
</Modals>
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