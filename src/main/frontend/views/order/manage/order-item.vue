<template>
    <div>
        <Panel>
            <table class="table">
                <tr>
                    <th>订单号</th>
                    <th>检测项编号</th>
                    <th>检测项名称</th>
                    <th>检测目的</th>
                    <th>标本类型</th>
                    <th>单价</th>
                    <th>受检人信息</th>
                    <th>预检检测日期</th>
                    <th>创建日期</th>
                    <th>更新日期</th>
                    <th>操作</th>
                </tr>
                <tr v-for="c in orderItemList" :key="c.id">
                    <td>{{c.orderId}}</td>
                    <td>{{ c.itemNo }}</td>
                    <td>
                        <span v-show="!c.editor">{{ c.name }}</span>
                        <span v-show="c.editor"><Input class="input-table" v-model="c.name"></Input></span>
                    </td>
                    <td>
                        <span v-show="!c.editor">{{ c.purpose }}</span>
                        <span v-show="c.editor"><Input class="input-table" v-model="c.purpose"></Input></span>
                    </td>
                    <td>
                        <span v-show="!c.editor">{{ c.sampleType }}</span>
                        <span v-show="c.editor"><Input class="input-table" v-model="c.sampleType"></Input></span>
                    </td>
                    <td>
                        <span v-show="!c.editor">{{ c.unitPrice }}</span>
                        <span v-show="c.editor"><Input class="input-table" v-model="c.unitPrice"></Input></span>
                    </td>
                    <td>
                        <span v-show="!c.editor">{{ c.customerInfo }}</span>
                        <span v-show="c.editor"><Input class="input-table" v-model="c.customerInfo"></Input></span>
                    </td>
                    <td>
                        <span v-show="!c.editor">{{ formatTime(c.testDate) }}</span>
                        <span v-show="c.editor"><Input class="input-table" v-model="c.testDate"></Input></span>
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
        </Panel>
        <Page :total="orderItemTotal" show-elevator :page-size="10" @on-change="loadPage"></Page>
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
                orderItemList: [],
                orderItemTotal: 0
            };
        },
        methods: {
            listOrder(page) {
                let slf = this;
                let param = {params: {page: page}};
                slf.$http.get('/order/item/list', param).then(res => {
                    if (res.data) {
                        res.data.forEach(c => {
                            Object.assign(c, c, {editor: false});
                        })
                    }
                    slf.orderItemList = res.data;
                });
            },
            countOrder() {
                let slf = this;
                slf.$http.get('/order/item/count').then(res => {
                    slf.orderItemTotal = res.data;
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
                slf.$http.delete('/order/item/' + c.id + '/delete').then(res => {
                    if (res.data) {
                        slf.listCompany();
                    } else  {
                        slf.$Notice.error({desc: 'Delete Error!'})
                    }
                })
            },
            updateOrder(c) {
                let slf = this;
                slf.$http.post('/order/item/update', c).then(res => {
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