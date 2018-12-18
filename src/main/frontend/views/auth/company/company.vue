<template>
    <div>
        <Panel>
            <table class="table">
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Status</th>
                    <th>Create Time</th>
                    <th>Update Time</th>
                    <th>Operation</th>
                </tr>
                <tr v-for="c in companyList" :key="c.id">
                    <td>{{ c.id }}</td>
                    <td>
                        <span v-show="!c.editor">{{ c.companyName }}</span>
                        <span v-show="c.editor"><Input class="input-table" v-model="c.companyName"></Input></span>
                    </td>
                    <td>{{ c.status }}</td>
                    <td>{{ formatTime(c.createTime) }}</td>
                    <td>{{ formatTime(c.updateTime) }}</td>
                    <td>
                        <div>
                            <a v-show="!c.editor" @click.stop="editCompany(c)">编辑</a>
                            <a v-show="c.editor" @click.stop="updateCompany(c)">保存</a>
                            <a @click.stop="deleteCompany(c)">删除</a>
                            <a @click.stop="cancelEdit(c)">取消</a>
                        </div>
                    </td>
                </tr>
                <tr v-if="addPanel">
                    <td></td>
                    <td>
                        <span><Input class="input-table" v-model="newCompany.companyName"></Input></span>
                    </td>
                    <td>
                        <Select v-model="newCompany.status">
                            <Option value="ACTIVE">ACTIVE</Option>
                            <Option value="INACTIVE">INACTIVE</Option>
                        </Select>
                    </td>
                    <td>{{ formatTime(new Date()) }}</td>
                    <td>{{ formatTime(new Date()) }}</td>
                    <td>
                        <div>
                            <a @click.stop="saveCompany">保存</a>
                            <a @click.stop="addPanel = false;newCompany={};">取消</a>
                        </div>
                    </td>
                </tr>
            </table>
            <Row>
                <Button type="info" @click="addCompany">添加</Button>
            </Row>
        </Panel>
        <Page :total="companyTotal" show-elevator :page-size="10" @on-change="loadPage"></Page>
    </div>
</template>

<script>
    import moment from 'moment'
    export default {
        name: "company",
        data() {
            return {
                companyList: [],
                companyTotal: 0,
                addPanel: false,
                newCompany: {}
            };
        },
        methods: {
            listCompany(page) {
                let slf = this;
                let params = {params: {page: page}};
                slf.$http.get('/company/list', params).then(res => {
                    if (res.data) {
                        res.data.forEach(c => {
                            Object.assign(c, c, {editor: false});
                        })
                    }
                    slf.companyList = res.data;
                });
            },
            countCompany() {
                let slf = this;
                slf.$http.get('/company/count').then(res => {
                    slf.companyTotal = res.data;
                })
            },
            formatTime(time) {
                return moment(time).format('YYYY-MM-DD LT')
            },
            editCompany(c) {
                c.editor = true;
            },
            deleteCompany(c) {
                let slf = this;
                slf.$http.delete('/company/' + c.id + '/delete').then(res => {
                    if (res.data) {
                        slf.listCompany();
                    } else  {
                        slf.$Notice.error({desc: 'Delete Error!'})
                    }
                })
            },
            updateCompany(c) {
                let slf = this;
                slf.$http.post('/company/update', c).then(res => {
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
                this.listCompany(page);
            },
            addCompany() {
                this.addPanel = true;
            },
            saveCompany() {
                let slf = this;
                slf.addPanel = false;
                slf.$http.put('/company/add', slf.newCompany).then(res => {
                    slf.companyList.push(res.data);
                });
            }
        },
        mounted() {
            this.listCompany(1);
            this.countCompany();
        }
    }
</script>

<style scoped>
    @import "../../../styles/table.less";
    .input-table {
        width: 150px;
    }
</style>