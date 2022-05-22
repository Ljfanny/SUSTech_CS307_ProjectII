<template>
    <div>
    <el-page-header @back="goBack" content="Contract">
    </el-page-header>
      <el-table
    :data="tableData"
    style="width: 100%"
    height="500">
    <el-table-column
      prop="contract_number"
      label="number"
      width="300">
    </el-table-column>
    <el-table-column
      prop="contract_manager"
      label="manager"
      width="300">
    </el-table-column>
    <el-table-column
      prop="contract_date"
      label="date"
      width="300">
    </el-table-column>
    <el-table-column
      prop="enterprise"
      label="enterprise"
      width="300">
    </el-table-column>
    <el-table-column
      prop="center"
      label="center"
      width="300">
    </el-table-column>
  </el-table>
    </div>
</template>

<script>
export default {
  methods: {
    goBack: function () {
      this.$router.push('Login')
    }
  }
}
</script>
