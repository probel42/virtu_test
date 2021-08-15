<script lang="ts">
    import ApolloClient from 'apollo-boost';
    import {setClient} from 'svelte-apollo';
    import ContractPage from './contracts/view/ContractPage.svelte';
    import ContractAddEdit from "./contracts/edit/ContractAddEdit.svelte";
    import Router from 'svelte-spa-router'

    const client = new ApolloClient({uri: 'http://localhost:8080/'});
    setClient(client);

    const routes = {
        '/': ContractPage,
        '/add': ContractAddEdit,
        '/contract/:id': ContractAddEdit
    }
</script>

<body>
    <Router {routes}/>
</body>

<style>
    main {
        text-align: left;
        padding: 0;
        max-width: 240px;
        margin: 0 auto;
    }

    @media (min-width: 640px) {
        main {
            max-width: none;
        }
    }
</style>
