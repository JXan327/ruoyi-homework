<template>
  <div class="login">
    <!-- 装饰性元素 -->
    <div class="decoration-dot"></div>
    <div class="decoration-dot"></div>
    <div class="decoration-dot"></div>

    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
      <h3 class="title">{{title}}</h3>
      <el-form-item prop="username">
        <el-input
          v-model="loginForm.username"
          type="text"
          auto-complete="off"
          placeholder="账号"
        >
          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          v-model="loginForm.password"
          type="password"
          auto-complete="off"
          placeholder="密码"
          @keyup.enter.native="handleLogin"
        >
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="code" v-if="captchaEnabled">
        <el-input
          v-model="loginForm.code"
          auto-complete="off"
          placeholder="验证码"
          style="width: 63%"
          @keyup.enter.native="handleLogin"
        >
          <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
        </el-input>
        <div class="login-code">
          <img :src="codeUrl" @click="getCode" class="login-code-img"/>
        </div>
      </el-form-item>
      <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">记住密码</el-checkbox>
      <el-form-item style="width:100%;">
        <el-button
          :loading="loading"
          size="medium"
          type="primary"
          style="width:100%;"
          @click.native.prevent="handleLogin"
        >
          <span v-if="!loading">登 录</span>
          <span v-else>登 录 中...</span>
        </el-button>
        <div style="float: right;" v-if="register">
          <router-link class="link-type" :to="'/register'">立即注册</router-link>
        </div>
      </el-form-item>
    </el-form>
    <!--  底部  -->
    <div class="el-login-footer">
      <span>{{ footerContent }}</span>
    </div>
  </div>
</template>

<script>
import { getCodeImg } from "@/api/login"
import Cookies from "js-cookie"
import { encrypt, decrypt } from '@/utils/jsencrypt'
import defaultSettings from '@/settings'

export default {
  name: "Login",
  data() {
    return {
      title: process.env.VUE_APP_TITLE,
      footerContent: defaultSettings.footerContent,
      codeUrl: "",
      loginForm: {
        username: "admin",
        password: "admin123",
        rememberMe: false,
        code: "",
        uuid: ""
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: "请输入您的账号" }
        ],
        password: [
          { required: true, trigger: "blur", message: "请输入您的密码" }
        ],
        code: [{ required: true, trigger: "change", message: "请输入验证码" }]
      },
      loading: false,
      // 验证码开关
      captchaEnabled: true,
      // 注册开关
      register: false,
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  created() {
    this.getCode()
    this.getCookie()
  },
  methods: {
    getCode() {
      getCodeImg().then(res => {
        this.captchaEnabled = res.captchaEnabled === undefined ? true : res.captchaEnabled
        if (this.captchaEnabled) {
          this.codeUrl = "data:image/gif;base64," + res.img
          this.loginForm.uuid = res.uuid
        }
      })
    },
    getCookie() {
      const username = Cookies.get("username")
      const password = Cookies.get("password")
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      }
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, { expires: 30 })
            Cookies.set("password", encrypt(this.loginForm.password), { expires: 30 })
            Cookies.set('rememberMe', this.loginForm.rememberMe, { expires: 30 })
          } else {
            Cookies.remove("username")
            Cookies.remove("password")
            Cookies.remove('rememberMe')
          }
          this.$store.dispatch("Login", this.loginForm).then(() => {
            this.$router.push({ path: this.redirect || "/" }).catch(()=>{})
          }).catch(() => {
            this.loading = false
            if (this.captchaEnabled) {
              this.getCode()
            }
          })
        }
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
// 清新绿/青色系变量
$color-teal: #14b8a6;
$color-emerald: #10b981;
$color-cyan: #06b6d4;
$color-light-teal: #5eead4;
$color-white: rgba(255, 255, 255, 0.95);

.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  position: relative;
  overflow: hidden;
  background: linear-gradient(135deg, $color-emerald 0%, $color-teal 50%, $color-cyan 100%);
  background-size: 200% 200%;
  animation: gradientShift 15s ease infinite;

  // 装饰性圆形元素
  &::before,
  &::after {
    content: '';
    position: absolute;
    border-radius: 50%;
    background: rgba(255, 255, 255, 0.1);
  }

  &::before {
    width: 500px;
    height: 500px;
    top: -150px;
    left: -100px;
    animation: float 20s ease-in-out infinite;
  }

  &::after {
    width: 400px;
    height: 400px;
    bottom: -100px;
    right: -80px;
    animation: float 18s ease-in-out infinite reverse;
  }

  // 添加额外的装饰圆点
  .decoration-dot {
    position: absolute;
    border-radius: 50%;
    background: rgba(255, 255, 255, 0.08);
  }

  .decoration-dot:nth-child(1) {
    width: 80px;
    height: 80px;
    top: 15%;
    right: 15%;
    animation: pulse 8s ease-in-out infinite;
  }

  .decoration-dot:nth-child(2) {
    width: 120px;
    height: 120px;
    bottom: 20%;
    left: 10%;
    animation: pulse 10s ease-in-out infinite 2s;
  }

  .decoration-dot:nth-child(3) {
    width: 60px;
    height: 60px;
    top: 30%;
    left: 20%;
    animation: pulse 12s ease-in-out infinite 4s;
  }
}

// 渐变动画
@keyframes gradientShift {
  0%, 100% {
    background-position: 0% 50%;
  }
  50% {
    background-position: 100% 50%;
  }
}

// 浮动动画
@keyframes float {
  0%, 100% {
    transform: translate(0, 0) scale(1);
  }
  33% {
    transform: translate(30px, -30px) scale(1.05);
  }
  66% {
    transform: translate(-20px, 20px) scale(0.95);
  }
}

// 脉冲动画
@keyframes pulse {
  0%, 100% {
    transform: scale(1);
    opacity: 0.08;
  }
  50% {
    transform: scale(1.2);
    opacity: 0.15;
  }
}

.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #374151;
  font-size: 28px;
  font-weight: 600;
  letter-spacing: 1px;
}

.login-form {
  border-radius: 16px;
  background: $color-white;
  width: 400px;
  padding: 40px 35px 15px 35px;
  z-index: 10;
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.15),
              0 12px 24px -8px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(10px);

  .el-input {
    height: 44px;
    input {
      height: 44px;
      border-radius: 8px;
      border-color: #e5e7eb;
      transition: all 0.3s ease;

      &:focus {
        border-color: $color-teal;
        box-shadow: 0 0 0 3px rgba(20, 184, 166, 0.1);
      }
    }
  }
  .input-icon {
    height: 44px;
    width: 16px;
    margin-left: 2px;
    color: #9ca3af;
  }

  // 登录按钮样式
  .el-button {
    height: 44px;
    border-radius: 8px;
    font-size: 16px;
    font-weight: 500;
    background: linear-gradient(135deg, $color-teal 0%, $color-emerald 100%);
    border: none;
    transition: all 0.3s ease;

    &:hover {
      transform: translateY(-2px);
      box-shadow: 0 10px 25px -5px rgba(20, 184, 166, 0.4);
    }

    &:active {
      transform: translateY(0);
    }
  }
}
.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.login-code {
  width: 33%;
  height: 38px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}
.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: rgba(255, 255, 255, 0.9);
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
  z-index: 5;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
}
.login-code-img {
  height: 38px;
}
</style>
