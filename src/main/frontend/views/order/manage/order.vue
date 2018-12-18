<template>
    <div>
        <Panel>
            <table class="table">
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Type</th>
                    <th>Status</th>
                    <th>Pay Status</th>
                    <th>Total Price</th>
                    <th>Currency</th>
                    <th>Buyer</th>
                    <th>Create By</th>
                    <th>Create Time</th>
                    <th>Update Time</th>
                    <th>Operation</th>
                </tr>
                <tr v-for="c in orderList" :key="c.id">
                    <td><a @click.stop="openDetail(c)">{{ c.id }}</a></td>
                    <td>
                        <span v-show="!c.editor">{{ c.name }}</span>
                        <span v-show="c.editor"><Input class="input-table" v-model="c.name"></Input></span>
                    </td>
                    <td>
                        <span>{{ c.type }}</span>
                    </td>
                    <td>
                        <span v-show="!c.editor">{{ c.status }}</span>
                        <span v-show="c.editor">
                            <Select v-model="c.status">
                                <Option value="CREATED">已创建</Option>
                                <Option value="PROCESS">处理中</Option>
                                <Option value="CANCEL">已取消</Option>
                            </Select>
                        </span>
                    </td>
                    <td>
                        <span v-show="!c.editor">{{ c.payStatus }}</span>
                        <span v-show="c.editor">
                            <Select v-model="c.payStatus">
                                <Option value="PAYED">已支付</Option>
                                <Option value="CANCEL">已取消</Option>
                                <Option value="REFUND">退款中</Option>
                                <Option value="REFUNDED">已退款</Option>
                            </Select>
                        </span>
                    </td>
                    <td>
                        <span v-show="!c.editor">{{ c.totalPrice }}</span>
                        <span v-show="c.editor"><Input class="input-table" v-model="c.totalPrice"></Input></span>
                    </td>
                    <td>
                        <span v-show="!c.editor">{{ c.currency }}</span>
                        <span v-show="c.editor"><Input class="input-table" v-model="c.currency"></Input></span>
                    </td>
                    <td>
                        <span v-show="!c.editor">{{ c.buyer }}</span>
                        <span v-show="c.editor">
                            <Input v-model="c.buyer"></Input>
                        </span>
                    </td>
                    <td>
                        <span v-show="!c.editor">{{ c.createBy }}</span>
                        <span v-show="c.editor">
                            <Input v-model="c.createBy"></Input>
                        </span>
                    </td>
                    <td>{{ formatTime(c.createTime) }}</td>
                    <td>{{ formatTime(c.updateTime) }}</td>
                    <td>
                        <div>
                            <a v-show="!c.editor" @click.stop="editOrder(c)">编辑</a>
                            <a v-show="c.editor" @click.stop="updateOrder(c)">保存</a>
                            <a @click.stop="deleteOrder(c)">删除</a>
                            <a @click.stop="cancelEdit(c)">取消</a>
                        </div>
                    </td>
                </tr>
            </table>
            <Button type="info" @click="addOrder">添加</Button>
        </Panel>
        <Page :total="orderTotal" show-elevator :page-size="10" @on-change="loadPage"></Page>
        <order-detail :show="showDetail" :order-detail="currentOrder" @on-close="detailClose" :on-refresh="reload"></order-detail>
    </div>
</template>

<script>
    import moment from 'moment'
    import OrderDetail from "./order-detail.vue";
    export default {
        components: {OrderDetail},
        name: "order",
        data() {
            return {
                orderList: [],
                orderTotal: 0,
                currentOrder: {},
                showDetail: false
            };
        },
        methods: {
            listOrder(page) {
                let slf = this;
                let param = {params: {page: page}};
                slf.$http.get('/order/list', param).then(res => {
                    if (res.data) {
                        res.data.forEach(c => {
                            Object.assign(c, c, {editor: false});
                        })
                    }
                    slf.orderList = res.data;
                });
            },
            countOrder() {
                let slf = this;
                slf.$http.get('/order/count').then(res => {
                    slf.orderTotal = res.data;
                });
            },
            formatTime(time) {
                return moment(time).format('YYYY-MM-DD LT')
            },
            editOrder(c) {
                c.editor = true;
            },
            deleteOrder(c) {
                let slf = this;
                slf.$http.delete('/order/' + c.id + '/delete').then(res => {
                    if (res.data) {
                        slf.listCompany();
                    } else  {
                        slf.$Notice.error({desc: 'Delete Error!'})
                    }
                })
            },
            updateOrder(c) {
                let slf = this;
                slf.$http.post('/order/update', c).then(res => {
                    Object.assign(c, res.data, {editor: false});
                }).catch(error => {
                    slf.$Notice.error({desc: 'Update Order Error!'});
                });
            },
            cancelEdit(c) {
                c.editor = false;
            },
            loadPage(page) {
                this.listUser(page);
            },
            openDetail(o) {
                this.showDetail = true;
                this.currentOrder = o;
            },
            detailClose() {
                this.showDetail = false;
                this.currentOrder = {};
            },
            reload() {
                this.listOrder(1);
            },
            addOrder() {
                this.showDetail = true;
                this.currentOrder = {};
            }
        },
        mounted() {
            this.listOrder(1);
            this.countOrder();
        }
    }
</script>

<style scoped>
    @import "../../../styles/table.less";
    .input-table {
        width: 150px;
    }
</style>