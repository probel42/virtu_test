<script lang="ts">
    import type {Contract} from '../../../data/Types';

    export let contract: Contract;
</script>

<style>
    #commentFragment {
        margin-top: 20px;
    }
    table {
        border-spacing: 0;
    }
    #textAreaTd {
        width: 80%;
        padding: 0;
    }
    #textAreaTd textarea {
        width: 100%;
        height: 100px;
        resize: none;
    }
    #commentTd {
        vertical-align: top;
        text-align: right;
        padding-right: 10px;
    }
</style>

<div id="commentFragment">
    <b>КОММЕНТАРИЙ</b>
    <table>
        <tr>
            <td id="commentTd">
                Комментарий к договору (не печатается на полисе)
            </td>
            <td id="textAreaTd">
                <textarea bind:value = {contract.comment}></textarea>
            </td>
        </tr>
    </table>
</div>
