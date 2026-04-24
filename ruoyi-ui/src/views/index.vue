<template>
  <div class="app-container dashboard">
    <!-- 欢迎区域 -->
    <div class="welcome-section">
      <div class="welcome-bg"></div>
      <div class="welcome-content">
        <div class="welcome-text">
          <h2>欢迎回来，{{ nickName }}</h2>
          <p class="welcome-date">{{ currentDate }}</p>
          <p class="welcome-desc">{{ welcomeMsg }}</p>
        </div>
        <div class="welcome-decoration">
          <svg viewBox="0 0 200 200" xmlns="http://www.w3.org/2000/svg">
            <path fill="rgba(255,255,255,0.15)" d="M45.7,-76.3C58.9,-69.2,69.1,-55.6,76.3,-41.1C83.5,-26.5,87.7,-11,85.3,3.5C82.9,18,73.9,31.5,63.6,43.1C53.3,54.7,41.7,64.4,28.7,70.6C15.7,76.8,1.3,79.5,-12.7,78C-26.7,76.5,-40.3,70.8,-52.1,62.5C-63.9,54.2,-73.9,43.3,-80.4,30.3C-86.9,17.3,-89.9,2.2,-87.6,-12.1C-85.3,-26.4,-77.7,-40,-67.1,-50.7C-56.5,-61.4,-42.9,-69.2,-29,-75.9C-15.1,-82.6,-0.9,-88.2,10.8,-87.9C22.5,-87.6,32.5,-83.4,45.7,-76.3Z" transform="translate(100 100)" />
          </svg>
        </div>
      </div>
    </div>

    <!-- 统计卡片 -->
    <el-row :gutter="20" class="stat-row">
      <el-col :xs="12" :sm="12" :lg="6" v-for="item in statCards" :key="item.title">
        <div class="stat-card" @click="handleStatClick(item)">
          <div class="stat-card-body">
            <div class="stat-icon" :style="{ background: item.bgColor }">
              <i :class="item.icon" :style="{ color: item.iconColor }"></i>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ item.value }}</div>
              <div class="stat-title">{{ item.title }}</div>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>

    <!-- 快捷入口 -->
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="24">
        <el-card class="quick-card" shadow="hover">
          <div slot="header" class="card-header">
            <span class="card-header-title">快捷入口</span>
          </div>
          <div class="quick-links">
            <div class="quick-link-item" v-for="link in quickLinks" :key="link.title" @click="$router.push(link.path)">
              <div class="quick-link-icon" :style="{ background: link.bgColor }">
                <i :class="link.icon" :style="{ color: link.color }"></i>
              </div>
              <span class="quick-link-title">{{ link.title }}</span>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "Index",
  data() {
    return {
      currentDate: '',
      nickName: '',
      welcomeMsg: '',
      statCards: [
        { title: '用户总数', value: '102', icon: 'el-icon-user', bgColor: 'rgba(20, 184, 166, 0.1)', iconColor: '#14b8a6' },
        { title: '角色数量', value: '5', icon: 'el-icon-s-custom', bgColor: 'rgba(16, 185, 129, 0.1)', iconColor: '#10b981' },
        { title: '菜单数量', value: '68', icon: 'el-icon-menu', bgColor: 'rgba(6, 182, 212, 0.1)', iconColor: '#06b6d4' },
        { title: '通知公告', value: '3', icon: 'el-icon-bell', bgColor: 'rgba(245, 158, 11, 0.1)', iconColor: '#f59e0b' }
      ],
      quickLinks: [
        { title: '用户管理', icon: 'el-icon-user', color: '#14b8a6', bgColor: 'rgba(20, 184, 166, 0.08)', path: '/system/user' },
        { title: '角色管理', icon: 'el-icon-s-custom', color: '#10b981', bgColor: 'rgba(16, 185, 129, 0.08)', path: '/system/role' },
        { title: '菜单管理', icon: 'el-icon-menu', color: '#06b6d4', bgColor: 'rgba(6, 182, 212, 0.08)', path: '/system/menu' },
        { title: '部门管理', icon: 'el-icon-office-building', color: '#8b5cf6', bgColor: 'rgba(139, 92, 246, 0.08)', path: '/system/dept' },
        { title: '岗位管理', icon: 'el-icon-suitcase', color: '#f59e0b', bgColor: 'rgba(245, 158, 11, 0.08)', path: '/system/post' },
        { title: '字典管理', icon: 'el-icon-notebook-2', color: '#ef4444', bgColor: 'rgba(239, 68, 68, 0.08)', path: '/system/dict' },
        { title: '参数设置', icon: 'el-icon-setting', color: '#6366f1', bgColor: 'rgba(99, 102, 241, 0.08)', path: '/system/config' },
        { title: '通知公告', icon: 'el-icon-bell', color: '#0ea5e9', bgColor: 'rgba(14, 165, 233, 0.08)', path: '/system/notice' },
        { title: '日志管理', icon: 'el-icon-document', color: '#64748b', bgColor: 'rgba(100, 116, 139, 0.08)', path: '/monitor/operlog' },
        { title: '在线用户', icon: 'el-icon-connection', color: '#ec4899', bgColor: 'rgba(236, 72, 153, 0.08)', path: '/monitor/online' }
      ]
    }
  },
  created() {
    this.updateDateTime()
    setInterval(this.updateDateTime, 60000)
    const user = this.$store.state.user
    if (user && user.nickName) {
      this.nickName = user.nickName
    }
  },
  methods: {
    updateDateTime() {
      const now = new Date()
      const weekDays = ['日', '一', '二', '三', '四', '五', '六']
      const y = now.getFullYear()
      const m = String(now.getMonth() + 1).padStart(2, '0')
      const d = String(now.getDate()).padStart(2, '0')
      const w = weekDays[now.getDay()]
      const h = String(now.getHours()).padStart(2, '0')
      const min = String(now.getMinutes()).padStart(2, '0')
      this.currentDate = `${y}年${m}月${d}日 星期${w} ${h}:${min}`
      const hour = now.getHours()
      if (hour < 6) this.welcomeMsg = '夜深了，注意休息'
      else if (hour < 9) this.welcomeMsg = '早上好，新的一天开始了'
      else if (hour < 12) this.welcomeMsg = '上午好，祝你工作顺利'
      else if (hour < 14) this.welcomeMsg = '中午好，记得午休'
      else if (hour < 18) this.welcomeMsg = '下午好，继续加油'
      else this.welcomeMsg = '晚上好，辛苦了一天'
    },
    handleStatClick(item) {
      // 统计卡片点击事件，可后续扩展
    }
  }
}
</script>

<style scoped lang="scss">
.dashboard {
  padding: 20px;
}

// 欢迎区域
.welcome-section {
  position: relative;
  border-radius: 12px;
  overflow: hidden;
  margin-bottom: 20px;

  .welcome-bg {
    position: absolute;
    inset: 0;
    background: linear-gradient(135deg, #10b981 0%, #14b8a6 50%, #06b6d4 100%);
    z-index: 0;
  }

  .welcome-content {
    position: relative;
    z-index: 1;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 32px 36px;
    color: #fff;
  }

  .welcome-text {
    h2 {
      font-size: 24px;
      font-weight: 600;
      margin: 0 0 8px 0;
      color: #fff;
    }
  }

  .welcome-date {
    font-size: 14px;
    opacity: 0.85;
    margin: 0 0 4px 0;
  }

  .welcome-desc {
    font-size: 16px;
    opacity: 0.9;
    margin: 0;
  }

  .welcome-decoration {
    svg {
      width: 160px;
      height: 160px;
      opacity: 0.6;
    }
  }
}

// 统计卡片
.stat-row {
  margin-top: 0;

  .el-col {
    margin-bottom: 0;
  }
}

.stat-card {
  background: #fff;
  border-radius: 10px;
  padding: 20px;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);

  &:hover {
    transform: translateY(-4px);
    box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
  }

  .stat-card-body {
    display: flex;
    align-items: center;
  }

  .stat-icon {
    width: 48px;
    height: 48px;
    border-radius: 12px;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-shrink: 0;

    i {
      font-size: 24px;
    }
  }

  .stat-info {
    margin-left: 16px;
  }

  .stat-value {
    font-size: 28px;
    font-weight: 700;
    color: #1f2937;
    line-height: 1.2;
  }

  .stat-title {
    font-size: 13px;
    color: #9ca3af;
    margin-top: 4px;
  }
}

// 快捷入口卡片
.quick-card {
  border-radius: 10px;
  border: none;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);

  .card-header {
    display: flex;
    align-items: center;
    justify-content: space-between;

    .card-header-title {
      font-size: 16px;
      font-weight: 600;
      color: #1f2937;
    }
  }
}

.quick-links {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  gap: 16px;

  @media (max-width: 1200px) {
    grid-template-columns: repeat(4, 1fr);
  }

  @media (max-width: 768px) {
    grid-template-columns: repeat(3, 1fr);
  }

  @media (max-width: 480px) {
    grid-template-columns: repeat(2, 1fr);
  }
}

.quick-link-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px 12px;
  border-radius: 10px;
  cursor: pointer;
  transition: all 0.3s ease;

  &:hover {
    background: #f9fafb;
    transform: translateY(-2px);
  }

  .quick-link-icon {
    width: 44px;
    height: 44px;
    border-radius: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-bottom: 10px;

    i {
      font-size: 22px;
    }
  }

  .quick-link-title {
    font-size: 13px;
    color: #6b7280;
    text-align: center;
  }
}
</style>
