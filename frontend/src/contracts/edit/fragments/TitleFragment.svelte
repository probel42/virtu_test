<script lang="ts">
    import type {Contract} from '../../../data/Types';

    export let contract: Contract;

    let title = !contract?.id ? 'Новый договор' : `Договор № "${contract?.number}" (${contract?.signDate})`;
</script>

<style>
    h3 {
        text-align: center;
        margin-top: 0;
    }
</style>

<h3>{title}</h3>
