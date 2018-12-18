<template>
    <div>
        <Panel>
            <table class="table">
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Company ID</th>
                    <th>Create Time</th>
                    <th>Update Time</th>
                    <th>Operation</th>
                </tr>
                <tr v-for="c in appList" :key="c.id">
                    <td>{{ c.id }}</td>
                    <td>
                        <span v-show="!c.editor">{{ c.name }}</span>
                        <span v-show="c.editor"><Input class="input-table" v-model="c.name"></Input></span>
                    </td>
                    <td>{{ c.companyId }}</td>
                    <td>{{ formatTime(c.createTime) }}</td>
                    <td>{{ formatTime(c.updateTime) }}</td>
                    <td>
                        <div>
                            <a v-show="!c.editor" @click.stop="editApp(c)">编辑</a>
                            <a v-show="c.editor" @click.stop="updateApp(c)">保存</a>
                            <a @click.stop="deleteApp(c)">删除</a>
                            <a @click.stop="cancelEdit(c)">取消</a>
                        </div>
                    </td>
                </tr>
            </table>
        </Panel>
        <Page :total="appTotal" show-elevator :page-size="10" @on-change="loadPage"></Page>
    </div>
</template>

<script>
    import moment from 'moment'
    export default {
        name: "application",
        data() {
            return {
                appList: [],
                appTotal: 0
            };
        },
        methods: {
            listApp(page) {
                let slf = this;
                let params = {params: {page: page}};
                slf.$http.get('/app/list', params).then(res => {
                    if (res.data) {
                        res.data.forEach(c => {
                            Object.assign(c, c, {editor: false});
                        })
                    }
                    slf.appList = res.data;
                });
            },
            countApp() {
                let slf = this;
                slf.$http.get('/app/count').then(res => {
                    slf.appTotal = res.data;
                })
            },
            formatTime(time) {
                return moment(time).format('YYYY-MM-DD LT')
            },
            editApp(c) {
                c.editor = true;
            },
            deleteApp(c) {
                let slf = this;
                slf.$http.delete('/app/' + c.id + '/delete').then(res => {
                    if (res.data) {
                        slf.listCompany();
                    } else  {
                        slf.$Notice.error({desc: 'Delete Error!'})
                    }
                })
            },
            updateApp(c) {
                let slf = this;
                slf.$http.post('/app/update', c).then(res => {
                    c = res.data;
                    c.editor = false;
                }).catch(error => {
                    slf.$Notice.error({desc: 'Update App Error!'});
                });
            },
            cancelEdit(c) {
                c.editor = false;
            },
            loadPage(page) {
                this.listApp(page);
            }
        },
        mounted() {
            this.listApp(1);
            this.countApp();
        }
    }
</script>

<style scoped>
    @import "../../../styles/table.less";
    .input-table {
        width: 150px;
    }
</style>