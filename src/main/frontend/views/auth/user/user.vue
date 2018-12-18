<template>
    <div>
        <Panel>
            <table class="table">
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Gender</th>
                    <th>Company ID</th>
                    <th>Phone</th>
                    <th>Email</th>
                    <th>Status</th>
                    <th>Create Time</th>
                    <th>Update Time</th>
                    <th>Operation</th>
                </tr>
                <tr v-for="c in userList" :key="c.id">
                    <td>{{ c.id }}</td>
                    <td>
                        <span v-show="!c.editor">{{ c.userName }}</span>
                        <span v-show="c.editor"><Input class="input-table" v-model="c.userName"></Input></span>
                    </td>
                    <td>
                        <span v-show="!c.editor">{{ c.gender }}</span>
                        <span v-show="c.editor">
                            <Select v-model="c.gender">
                                <Option value="MALE">MALE</Option>
                                <Option value="FEMALE">FEMALE</Option>
                            </Select>
                        </span>
                    </td>
                    <td>
                        <span v-show="!c.editor">{{ c.companyId }}</span>
                        <span v-show="c.editor"><Input class="input-table" v-model="c.companyId"></Input></span>
                    </td>
                    <td>
                        <span v-show="!c.editor">{{ c.phone }}</span>
                        <span v-show="c.editor"><Input class="input-table" v-model="c.phone"></Input></span>
                    </td>
                    <td>
                        <span v-show="!c.editor">{{ c.email }}</span>
                        <span v-show="c.editor"><Input class="input-table" v-model="c.email"></Input></span>
                    </td>
                    <td>
                        <span v-show="!c.editor">{{ c.status }}</span>
                        <span v-show="c.editor">
                            <Select v-model="c.status">
                                <Option value="ACTIVE">ACTIVE</Option>
                                <Option value="INACTIVE">INACTIVE</Option>
                            </Select>
                        </span>
                    </td>
                    <td>{{ formatTime(c.createTime) }}</td>
                    <td>{{ formatTime(c.updateTime) }}</td>
                    <td>
                        <div>
                            <a v-show="!c.editor" @click.stop="editUser(c)">编辑</a>
                            <a v-show="c.editor" @click.stop="updateUser(c)">保存</a>
                            <a @click.stop="deleteUser(c)">删除</a>
                            <a @click.stop="cancelEdit(c)">取消</a>
                        </div>
                    </td>
                </tr>
            </table>
        </Panel>
        <Page :total="userTotal" show-elevator :page-size="10" @on-change="loadPage"></Page>
    </div>
</template>

<script>
    import moment from 'moment'
    export default {
        name: "user",
        data() {
            return {
                userList: [],
                userTotal: 0
            };
        },
        methods: {
            listUser(page) {
                let slf = this;
                let param = {params: {page: page}};
                slf.$http.get('/user/list', param).then(res => {
                    if (res.data) {
                        res.data.forEach(c => {
                            Object.assign(c, c, {editor: false});
                        })
                    }
                    slf.userList = res.data;
                });
            },
            countUser() {
                let slf = this;
                slf.$http.get('/user/count').then(res => {
                    slf.userTotal = res.data;
                });
            },
            formatTime(time) {
                return moment(time).format('YYYY-MM-DD LT')
            },
            editUser(c) {
                c.editor = true;
            },
            deleteUser(c) {
                let slf = this;
                slf.$http.delete('/user/' + c.id + '/delete').then(res => {
                    if (res.data) {
                        slf.listCompany();
                    } else  {
                        slf.$Notice.error({desc: 'Delete Error!'})
                    }
                })
            },
            updateUser(c) {
                let slf = this;
                slf.$http.post('/user/update', c).then(res => {
                    c = res.data;
                    c.editor = false;
                }).catch(error => {
                    slf.$Notice.error({desc: 'Update Company Error!'});
                });
            },
            cancelEdit(c) {
                c.editor = false;
            },
            loadPage(page) {
                this.listUser(page);
            }
        },
        mounted() {
            this.listUser(1);
            this.countUser();
        }
    }
</script>

<style scoped>
    @import "../../../styles/table.less";
    .input-table {
        width: 150px;
    }
</style>