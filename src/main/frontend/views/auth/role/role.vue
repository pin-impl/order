<template>
    <div>
        <Panel>
            <table class="table">
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Application</th>
                    <th>Create Time</th>
                    <th>Update Time</th>
                    <th>Operation</th>
                </tr>
                <tr v-for="c in roleList" :key="c.id">
                    <td>{{ c.id }}</td>
                    <td>
                        <span v-show="!c.editor">{{ c.name }}</span>
                        <span v-show="c.editor"><Input class="input-table" v-model="c.name"></Input></span>
                    </td>
                    <td>{{ c.applicationId }}</td>
                    <td>{{ formatTime(c.createTime) }}</td>
                    <td>{{ formatTime(c.updateTime) }}</td>
                    <td>
                        <div>
                            <a v-show="!c.editor" @click.stop="editRole(c)">编辑</a>
                            <a v-show="c.editor" @click.stop="updateRole(c)">保存</a>
                            <a @click.stop="deleteRole(c)">删除</a>
                            <a @click.stop="cancelEdit(c)">取消</a>
                        </div>
                    </td>
                </tr>
                <tr v-if="addPanel">
                    <td></td>
                    <td>
                        <span><Input class="input-table" v-model="newRole.name"></Input></span>
                    </td>
                    <td>
                        <Input class="input-table" v-model="newRole.applicationId"></Input>
                    </td>
                    <td>{{ formatTime(new Date()) }}</td>
                    <td>{{ formatTime(new Date()) }}</td>
                    <td>
                        <div>
                            <a @click.stop="saveRole">保存</a>
                            <a @click.stop="addPanel = false;newRole={};">取消</a>
                        </div>
                    </td>
                </tr>
            </table>
            <Row>
                <Button type="info" @click="addRole">添加</Button>
            </Row>
        </Panel>
        <Page :total="roleTotal" show-elevator :page-size="10" @on-change="loadPage"></Page>
    </div>
</template>

<script>
    import moment from 'moment'
    export default {
        name: "role",
        data() {
            return {
                roleList: [],
                roleTotal: 0,
                addPanel: false,
                newRole: {}
            };
        },
        methods: {
            listRole(page) {
                let slf = this;
                let params = {params: {page: page}};
                slf.$http.get('/role/list', params).then(res => {
                    if (res.data) {
                        res.data.forEach(c => {
                            Object.assign(c, c, {editor: false});
                        })
                    }
                    slf.roleList = res.data;
                });
            },
            countRole() {
                let slf = this;
                slf.$http.get('/role/count').then(res => {
                    slf.roleTotal = res.data;
                })
            },
            formatTime(time) {
                return moment(time).format('YYYY-MM-DD LT')
            },
            editRole(c) {
                c.editor = true;
            },
            deleteRole(c) {
                let slf = this;
                slf.$http.delete('/role/' + c.id + '/delete').then(res => {
                    if (res.data) {
                        slf.listRole();
                    } else  {
                        slf.$Notice.error({desc: 'Delete Error!'})
                    }
                })
            },
            updateRole(c) {
                let slf = this;
                slf.$http.post('/role/update', c).then(res => {
                    c = res.data;
                    c.editor = false;
                }).catch(error => {
                    slf.$Notice.error({desc: 'Update Role Error!'});
                });
            },
            cancelEdit(c) {
                c.editor = false;
            },
            loadPage(page) {
                this.listRole(page);
            },
            addRole() {
                this.addPanel = true;
            },
            saveRole() {
                let slf = this;
                slf.addPanel = false;
                slf.$http.put('/role/add', slf.newRole).then(res => {
                    slf.roleList.push(res.data);
                });
            }
        },
        mounted() {
            this.listRole(1);
            this.countRole();
        }
    }
</script>

<style scoped>
    @import "../../../styles/table.less";
    .input-table {
        width: 150px;
    }
</style>