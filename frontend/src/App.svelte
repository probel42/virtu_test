<script lang="ts">
    import ApolloClient from 'apollo-boost';
    import {setClient} from 'svelte-apollo';
    import ContractView from './contracts/view/ContractView.svelte';
    import ContractAddEdit from './contracts/edit/ContractAddEdit.svelte';
    import Router from 'svelte-spa-router'

    const env = _env;
    const client = new ApolloClient({uri: env.SERVER_URI});
    setClient(client);

    const routes = {
        '/': ContractView,
        '/add': ContractAddEdit,
        '/contract/:id': ContractAddEdit
    }
</script>

<body>
<Router {routes}/>
</body>

<style>
    @media (min-width: 640px) {
    }
</style>
