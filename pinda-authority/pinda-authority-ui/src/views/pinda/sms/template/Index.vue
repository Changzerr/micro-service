<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="queryParams.appId"
        :placeholder="$t('table.smsTemplate.appId')"
        class="filter-item search-item"
      />
      <el-input
        v-model="queryParams.customCode"
        :placeholder="$t('table.smsTemplate.customCode')"
        class="filter-item search-item"
      />
      <el-input
        v-model="queryParams.name"
        :placeholder="$t('table.smsTemplate.name')"
        class="filter-item search-item"
      />
      <el-input
        v-model="queryParams.templateCode"
        :placeholder="$t('table.smsTemplate.templateCode')"
        class="filter-item search-item"
      />
      <el-input
        v-model="queryParams.signName"
        :placeholder="$t('table.smsTemplate.signName')"
        class="filter-item search-item"
      />
      <el-date-picker
        v-model="queryParams.timeRange"
        :range-separator="null"
        class="filter-item search-item date-range-item"
        end-placeholder="结束日期"
        format="yyyy-MM-dd HH:mm:ss"
        start-placeholder="开始日期"
        type="daterange"
        value-format="yyyy-MM-dd HH:mm:ss"
      />
      <el-button class="filter-item" plain type="primary" @click="search">{{ $t('table.search') }}</el-button>
      <el-button class="filter-item" plain type="warning" @click="reset">{{ $t('table.reset') }}</el-button>
      <el-dropdown
        v-has-any-permission="['sms:template:add','sms:template:delete','sms:template:export']"
        class="filter-item"
        trigger="click"
      >
        <el-button>
          {{ $t('table.more') }}
          <i class="el-icon-arrow-down el-icon--right" />
        </el-button>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item
            v-has-permission="['sms:template:add']"
            @click.native="add"
          >{{ $t('table.add') }}</el-dropdown-item>
          <el-dropdown-item
            v-has-permission="['sms:template:delete']"
            @click.native="batchDelete"
          >{{ $t('table.delete') }}</el-dropdown-item>
          <el-dropdown-item
            v-has-permission="['sms:template:export']"
            @click.native="exportExcel"
          >{{ $t('table.export') }}</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>

    <el-table
      :key="tableKey"
      ref="table"
      v-loading="loading"
      :data="tableData.records"
      border
      fit
      style="width: 100%;"
      @filter-change="filterChange"
      @selection-change="onSelectChange"
      @sort-change="sortChange"
    >
      <el-table-column align="center" type="selection" width="40px" />
      <el-table-column
        :filter-multiple="false"
        :filters="providerTypeFilters"
        :label="$t('table.smsTemplate.providerType')"
        :show-overflow-tooltip="true"
        align="center"
        column-key="providerType"
        prop="providerType"
        width="100px"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.providerType.desc }}</span>
        </template>
      </el-table-column>
      <el-table-column
        :label="$t('table.smsTemplate.appId')"
        :show-overflow-tooltip="true"
        align="center"
        prop="appId"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.appId }}</span>
        </template>
      </el-table-column>
      <el-table-column
        :label="$t('table.smsTemplate.appSecret')"
        :show-overflow-tooltip="true"
        align="center"
        prop="appSecret"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.appSecret }}</span>
        </template>
      </el-table-column>
      <el-table-column
        :label="$t('table.smsTemplate.name')"
        :show-overflow-tooltip="true"
        align="center"
        prop="name"
        width="150px"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column
        :label="$t('table.smsTemplate.customCode')"
        :show-overflow-tooltip="true"
        align="center"
        prop="customCode"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.customCode }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.smsTemplate.templateCode')" align="center" width="150px">
        <template slot-scope="scope">
          <span>{{ scope.row.templateCode }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.smsTemplate.signName')" align="center" width="150px">
        <template slot-scope="scope">
          <span>{{ scope.row.signName }}</span>
        </template>
      </el-table-column>
      <el-table-column
        :label="$t('table.smsTemplate.templateDescribe')"
        align="center"
        width="150px"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.templateDescribe }}</span>
        </template>
      </el-table-column>
      <el-table-column
        :label="$t('table.createTime')"
        align="center"
        prop="createTime"
        sortable="custom"
        width="170px"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column
        :label="$t('table.operation')"
        align="center"
        class-name="small-padding fixed-width"
        width="100px"
      >
        <template slot-scope="{row}">
          <i
            v-hasPermission="['sms:template:update']"
            class="el-icon-edit table-operation"
            style="color: #2db7f5;"
            @click="edit(row)"
          />
          <i
            v-hasPermission="['sms:template:delete']"
            class="el-icon-delete table-operation"
            style="color: #f50;"
            @click="singleDelete(row)"
          />
          <el-link
            v-has-no-permission="['sms:template:update','sms:template:delete']"
            class="no-perm"
          >{{ $t('tips.noPermission') }}</el-link>
        </template>
      </el-table-column>
    </el-table>
    <pagination
      v-show="tableData.total>0"
      :limit.sync="pagination.size"
      :page.sync="pagination.current"
      :total="Number(tableData.total)"
      @pagination="fetch"
    />
    <sms-template-edit
      ref="edit"
      :dialog-visible="dialog.isVisible"
      :type="dialog.type"
      @close="editClose"
      @success="editSuccess"
    />
  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
import SmsTemplateEdit from './Edit'
import smsTemplateApi from '@/api/SmsTemplate.js'
import { converEnum } from '@/utils/utils'

export default {
  name: 'SmsTemplateManage',
  components: { Pagination, SmsTemplateEdit },
  filters: {
    statusFilter(status) {
      const map = {
        false: 'danger',
        true: 'success'
      }
      return map[status] || 'success'
    }
  },
  data() {
    return {
      dialog: {
        isVisible: false,
        type: 'add'
      },
      tableKey: 0,
      // total: 0,
      queryParams: {},
      sort: {},
      selection: [],
      // 以下已修改
      loading: false,
      tableData: {
        total: 0
      },
      pagination: {
        size: 10,
        current: 1
      }
    }
  },
  computed: {
    providerTypeFilters() {
      return converEnum(this.$store.state.common.enums.ProviderType)
    }
  },
  mounted() {
    this.fetch()
  },
  methods: {
    editClose() {
      this.dialog.isVisible = false
    },
    editSuccess() {
      this.search()
    },
    onSelectChange(selection) {
      this.selection = selection
    },
    search() {
      this.fetch({
        ...this.queryParams,
        ...this.sort
      })
    },
    reset() {
      this.queryParams = {}
      this.sort = {}
      this.$refs.table.clearSort()
      this.$refs.table.clearFilter()
      this.search()
    },
    exportExcel() {
      this.$message({
        message: '待完善',
        type: 'warning'
      })
    },
    singleDelete(row) {
      this.$refs.table.toggleRowSelection(row, true)
      this.batchDelete()
    },
    batchDelete() {
      if (!this.selection.length) {
        this.$message({
          message: this.$t('tips.noDataSelected'),
          type: 'warning'
        })
        return
      }
      this.$confirm(this.$t('tips.confirmDelete'), this.$t('common.tips'), {
        confirmButtonText: this.$t('common.confirm'),
        cancelButtonText: this.$t('common.cancel'),
        type: 'warning'
      })
        .then(() => {
          const ids = []
          this.selection.forEach(u => {
            ids.push(u.id)
          })
          this.delete(ids)
        })
        .catch(() => {
          this.clearSelections()
        })
    },
    clearSelections() {
      this.$refs.table.clearSelection()
    },
    delete(ids) {
      smsTemplateApi.delete({ ids: ids }).then(response => {
        const res = response.data
        if (res.isSuccess) {
          this.$message({
            message: this.$t('tips.deleteSuccess'),
            type: 'success'
          })
        }
        this.search()
      })
    },
    add() {
      this.dialog.type = 'add'
      this.dialog.isVisible = true
      this.$refs.edit.setSmsTemplate(false)
    },
    edit(row) {
      this.$refs.edit.setSmsTemplate(row)
      this.dialog.type = 'edit'
      this.dialog.isVisible = true
    },
    fetch(params = {}) {
      this.loading = true
      params.size = this.pagination.size
      params.current = this.pagination.current
      if (this.queryParams.timeRange) {
        params.startCreateTime = this.queryParams.timeRange[0]
        params.endCreateTime = this.queryParams.timeRange[1]
      }
      smsTemplateApi.page(params).then(response => {
        const res = response.data
        this.loading = false
        if (res.isError) {
          return
        }
        this.tableData = res.data
      })
    },
    sortChange(val) {
      this.sort.field = val.prop
      this.sort.order = val.order
      this.search()
    },
    filterChange(filters) {
      for (const key in filters) {
        this.queryParams[key] = filters[key][0]
      }
      this.search()
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
