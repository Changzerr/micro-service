<template>
  <div class="navbar">
    <hamburger
      id="hamburger-container"
      :is-active="sidebar.opened"
      class="hamburger-container"
      @toggleClick="toggleSideBar"
    />

    <breadcrumb id="breadcrumb-container" class="breadcrumb-container" />

    <div class="right-menu">
      <template v-if="device!==&quot;mobile&quot;">
        <search id="header-search" class="right-menu-item" />
      </template>

      <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="click">
        <div class="avatar-wrapper">
          <!-- <img :src='avatar' class='user-avatar' alt='avatar' /> -->
          <el-avatar fit="fill" :src="avatar">
            <el-avatar>{{ name | userAvatarFilter }}</el-avatar>
          </el-avatar>
          <span class="user-name">{{ name }}</span>
        </div>
        <el-dropdown-menu slot="dropdown">
          <router-link to="/profile/index">
            <el-dropdown-item>{{ $t('navbar.profile') }}</el-dropdown-item>
          </router-link>
          <el-dropdown-item>
            <span style="display:block;" @click="setting">{{ $t('navbar.setting') }}</span>
          </el-dropdown-item>
          <el-dropdown-item divided>
            <span style="display:block;" @click="deleteCache">{{ $t('navbar.deleteCache') }}</span>
          </el-dropdown-item>
          <el-dropdown-item divided>
            <span style="display:block;" @click="logout">{{ $t('navbar.logOut') }}</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import db from '@/utils/localstorage'
import Search from '@/components/HeaderSearch'

export default {
  components: {
    Breadcrumb,
    Hamburger,
    Search
  },
  filters: {
    userAvatarFilter(name) {
      return name.charAt(0)
    }
  },
  computed: {
    sidebar() {
      return this.$store.state.setting.sidebar
    },
    avatar() {
      const user = this.$store.state.account.user
      if (!user['avatar']) {
        return require(`@/assets/avatar/default.jpg`)
      } else {
        if (
          user['avatar'].startsWith('http://') ||
          user['avatar'].startsWith('https://')
        ) {
          return user['avatar']
        } else {
          return require(`@/assets/avatar/${user.avatar}`)
        }
      }
    },
    name() {
      return this.$store.state.account.user.name
    },
    device() {
      return this.$store.state.setting.device
    }
  },
  methods: {
    toggleSideBar() {
      this.$store.commit('setting/toggleSidebar')
    },
    setting() {
      this.$store.commit('setting/openSettingBar', true)
    },
    logout() {
      this.clean()
    },
    clean() {
      db.clear()
      location.reload()
    },
    deleteCache() {
      this.$confirm(
        this.$t('tips.confirmDeleteCache'),
        this.$t('common.tips'),
        {
          confirmButtonText: this.$t('common.confirm'),
          cancelButtonText: this.$t('common.cancel'),
          type: 'warning'
        }
      )
        .then(() => {
          db.remove('USER_ROUTER')
          db.remove('PERMISSIONS')
          location.reload()
        })
        .catch(() => {
          // do nothing
        })
    }
  }
}
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  border-bottom: 1px solid #f1f1f1;
  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background 0.3s;
    -webkit-tap-highlight-color: transparent;

    &:hover {
      background: rgba(0, 0, 0, 0.025);
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background 0.3s;

        &:hover {
          background: rgba(0, 0, 0, 0.025);
        }
      }
    }

    .avatar-container {
      margin-right: 10px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;
        .user-name {
          vertical-align: top;
          font-size: 1rem;
          margin-left: 5px;
          margin-top: -4px;
          display: inline-block;
        }
        .user-avatar {
          cursor: pointer;
          width: 2rem;
          height: 2rem;
          border-radius: 50%;
          vertical-align: text-bottom;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
