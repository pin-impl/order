<style lang="less">
    @import './login.less';
</style>

<template>
    <div class="login" @keydown.enter="handleSubmit">
        <Row class="logo-row">
            <a :href="labUrl" class="logo"></a>
        </Row>
        <Row class="content-row">
            <Col span="14">
                <div class="banner"><img src="../images/landing_pic.png" class="oc-logo"></div>
            </Col>
            <Col span="10">
                <div class="login-con">
                    <Card :bordered="false">
                        <p slot="title">
                            <Icon type="log-in"></Icon>
                            欢迎登录
                        </p>
                        <div class="form-con">
                            <Form ref="loginForm" :model="form" :rules="rules">
                                <FormItem prop="userName">
                                    <Input v-model="form.username" placeholder="请输入用户名">
                                    <span slot="prepend">
                                        <Icon :size="16" type="person"></Icon>
                                    </span>
                                    </Input>
                                </FormItem>
                                <FormItem prop="password">
                                    <Input type="password" v-model="form.password" placeholder="请输入密码">
                                    <span slot="prepend">
                                        <Icon :size="14" type="locked"></Icon>
                                    </span>
                                    </Input>
                                </FormItem>
                                <FormItem>
                                    <Button @click="handleSubmit" type="primary" long>登录</Button>
                                </FormItem>
                            </Form>
                            <div class="login-tip">
                                忘记密码，请登陆
                                <a :href="labUrl" class="font">览博网</a>
                                进行密码重置,<br>
                                密码重置后第二天在Ocean生效
                            </div>
                        </div>
                    </Card>
                </div>
            </Col>
        </Row>
        <Footer class="layout-footer-center"><strong>2014-2018 &copy; LabNetwork, All Rights Reserved</strong></Footer>
    </div>
</template>

<script>
export default {
    data () {
        return {
            form: {
                username: '',
                password: ''
            },
            rules: {
                username: [
                    { required: true, message: '账号不能为空', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '密码不能为空', trigger: 'blur' }
                ]
            },
            labUrl: ''
        };
    },
    methods: {
        handleSubmit () {
            let vm = this;
            this.$refs.loginForm.validate((valid) => {
                if (valid) {
                    vm.$http.post('/ocean_login', vm.form).then(res => {
                        localStorage.access = Base64.encode(_.join(res.data.roles), ',');
                        vm.$store.commit('setAppRoles', res.data.roles);
                        vm.$store.commit('setUser', res.data.username);
                        vm.$store.commit('switchLang', navigator.language);
                        localStorage.lang = navigator.language;
                        localStorage.username = res.data.username.split('@')[0];
                        localStorage.userEmail = res.data.username;
                        vm.$router.push({
                            name: 'home_index'
                        });
                    });

                }
            });
        },
        getLabUrl() {
            this.$http.get('/resources/login/forward/info').then((res) => {
                this.labUrl = res.data.forgetPasswordUrl;
            });
        }
    },
    mounted() {
        this.getLabUrl();
    }
};
</script>
<style>
    .logo-row {
        margin: 100px 0 50px 0;
    }
    .content-row {
        background-color: #3EB3CF;
        height: 350px;
    }
    .logo {
        background: url("../images/logo_LN.png") no-repeat;
        height: 36px;
        width: auto;
        margin-left: 160px;
        display: block;
    }
    .banner {
        margin-left: 300px;
    }
    .layout-footer-center{
        text-align: center;
        position: fixed;
        bottom: 0;
        width: 100%;
    }
</style>
