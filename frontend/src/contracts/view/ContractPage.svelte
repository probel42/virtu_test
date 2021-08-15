<script>
    import ContractList from './ContractList.svelte';

    let openContract = () => {
        window.location.href = '#/add'
    }
</script>

<style>
    #contracts {
        display: grid
    }

    #addContractButton {
        margin-top: 0.5em;
    }
</style>

<div id="contracts">
    <ContractList/>
</div>
<div id="contractButtons">
    <button id="addContractButton" on:click={openContract}>Добавить договор</button>
</div>
