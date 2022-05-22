<template>
    <div>
    <el-page-header @back="goBack" content="Enterprise">
    </el-page-header>
      <el-table
    :data="tableData"
    style="width: 100%"
    height="500">
    <el-table-column
      prop="enterprise_id"
      label="id"
      width="300">
    </el-table-column>
    <el-table-column
      prop="name"
      label="name"
      width="300">
    </el-table-column>
    <el-table-column
      prop="country"
      label="country"
      width="300">
    </el-table-column>
    <el-table-column
      prop="city"
      label="city"
      width="300">
    </el-table-column>
    <el-table-column
      prop="supply_center"
      label="center"
      width="300">
    </el-table-column>
    <el-table-column
      prop="industry"
      label="industry"
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
