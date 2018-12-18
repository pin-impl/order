<template>
    <Modal v-model="open" width="60" title="订单详情" @on-ok="updateOrder">
        <Row class="title" slot="header">
            <Col span="1" offset="23"><Button @click="editable = true;">编辑</Button></Col>
        </Row>
        <Row>
            <Col span="6">
                医院: <Input :disabled="!editable" v-model="order.hospital"></Input>
            </Col>
            <Col span="6" offset="2">
                销售员: <Input :disabled="!editable" v-model="order.salesman"></Input>
            </Col>
            <Col span="6" offset="2">
                客户名称: <Input :disabled="!editable" v-model="order.customer"></Input>
            </Col>
        </Row>
        <Row>
            <Col span="6">
            科室: <Input :disabled="!editable" v-model="order.department"></Input>
            </Col>
            <Col span="6" :disabled="!editable" offset="2">
            收单人: <Input :disabled="!editable" v-model="order.receiver"></Input>
            </Col>
            <Col span="6" offset="2">
            客户电话: <Input :disabled="!editable" v-model="order.customerPhone"></Input>
            </Col>
        </Row>
        <Row class="blank_line"></Row>
        <table>
            <tr>
                <th>检测项编号</th>
                <th>检测项名称</th>
                <th>检测目的</th>
                <th>标本类型</th>
                <th>单价</th>
                <th>受检人信息</th>
                <th>预检检测日期</th>
                <th v-show="editable">编辑</th>
            </tr>
            <tr v-for="item in orderItems" :key="item.id">
                <td>
                    <span>{{ item.itemNo }}</span>
                </td>
                <td>
                    <span v-show="!item.editable">{{item.name}}</span>
                    <span v-show="item.editable"><Input v-model="item.name"></Input></span>
                </td>
                <td>
                    <span v-show="!item.editable">{{item.purpose}}</span>
                    <span v-show="item.editable"><Input v-model="item.purpose"></Input></span>
                </td>
                <td>
                    <span v-show="!item.editable">{{item.sampleType}}</span>
                    <span v-show="item.editable"><Input v-model="item.sampleType"></Input></span>
                </td>
                <td>
                    <span v-show="!item.editable">{{item.unitPrice}}</span>
                    <span v-show="item.editable"><Input v-model="item.unitPrice"></Input></span>
                </td>
                <td>
                    <span v-show="!item.editable">{{item.customerInfo}}</span>
                    <span v-show="item.editable"><Input v-model="item.customerInfo"></Input></span>
                </td>
                <td>
                    {{formatDate(item.testDate)}}
                </td>
                <td v-show="editable">
                    <a @click.stop="item.editable = true;">编辑</a>
                    <a @click.stop="saveItem(item)">保存</a>
                </td>
            </tr>
            <tr v-show="toAddItem">
                <td>
                    <Input v-model="newItem.itemNo"></Input>
                </td>
                <td>
                    <Input v-model="newItem.name"></Input>
                </td>
                <td>
                    <Input v-model="newItem.purpose"></Input>
                </td>
                <td>
                    <Input v-model="newItem.sampleType"></Input>
                </td>
                <td>
                    <Input v-model="newItem.unitPrice"></Input>
                </td>
                <td>
                    <Input v-model="newItem.customerInfo"></Input>
                </td>
                <td>
                    <DatePicker type="datetime" v-model="newItem.testDate"></DatePicker>
                </td>
                <td>
                    <a @click.stop="addItem">保存</a>
                </td>
            </tr>
        </table>
        <Button v-show="editable" type="info" @click="toAddItem = true;">添加</Button>
    </Modal>
</template>

<script>
    import moment from 'moment'
    export default {
        name: "order-detail",
        data() {
            return {
                order: {},
                orderItems: [],
                open: false,
                toAddItem: false,
                newItem: {},
                editable: false
            };
        },
        props: {
            orderDetail: {},
            show: false,
            onClose: {},
            onRefresh: {}
        },
        watch: {
            orderDetail: {
                handler: function (val) {
                    this.order = val;
                },
                deep: true
            } ,
            show(val) {
                this.open = val;
            },
            open(val) {
                if (!val) {
                    this.orderItems = [];
                    this.$emit('on-close', val);
                } else {
                    this.listOrderItem();
                }
            }
        },
        methods: {
            listOrderItem() {
                let slf = this;
                if (!slf.order.id) {
                    return;
                }
                let params = {orderId: slf.order.id};
                slf.$http.get('/order/item/list', {params: params}).then(res => {
                    res.data.forEach(item => {Object.assign(item, item, {editable: false})});
                    slf.orderItems = res.data;
                });
            },
            formatDate(date) {
                return moment(date).format('YYYY-MM-DD LT');
            },
            updateOrder() {
                let slf = this;
                slf.$http.post('/order/item/update', slf.order).then(res => {
                    slf.open = false;
                });
            },
            saveItem(item) {
                let slf = this;
                slf.$http.post('/order/item/update', item).then(res => {
                    item.editable = false;
                });
            },
            addItem() {
                let slf = this;
                slf.newItem.orderId = slf.order.id;
                slf.newItem.quantity = 1;
                slf.newItem.status = 'CREATED';
                slf.$http.put('/order/item/add', slf.newItem).then(res => {
                    slf.orderItems.push(res.data);
                    slf.toAddItem = false;
                    slf.newItem = {};
                });
            }
        }
    }
</script>

<style scoped>
    @import "../../../styles/table.less";
    .blank_line {
        margin: 10px 0;
    }
    .title {
        width: 80%;
    }
</style>